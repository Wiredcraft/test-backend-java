package io.github.jerrychin.testbackendjava.service;

import io.github.jerrychin.testbackendjava.dto.FindPeopleDTO;
import io.github.jerrychin.testbackendjava.dto.PeopleVO;
import io.github.jerrychin.testbackendjava.dto.AccountIdDTO;
import io.github.jerrychin.testbackendjava.entity.PeopleRelation;
import io.github.jerrychin.testbackendjava.entity.User;
import io.github.jerrychin.testbackendjava.mapper.UserMapper;
import io.github.jerrychin.testbackendjava.repository.PeopleRelationRepository;
import io.github.jerrychin.testbackendjava.repository.UserRepository;
import io.github.jerrychin.testbackendjava.util.Coordinate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PeopleService {
    private static final Logger log = LoggerFactory.getLogger(PeopleService.class);

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final PeopleRelationRepository relationRepository;

    public PeopleService(UserMapper userMapper, UserRepository userRepository,
                         PeopleRelationRepository relationRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.relationRepository = relationRepository;
    }

    public List<PeopleVO> listPeople(FindPeopleDTO findPeopleDTO) {
        final Set<Long> followingAccountIdSet;

        // find current user's following accounts, empty if not sign in.
        if(findPeopleDTO.getCurrentAccountId() != null) {
            followingAccountIdSet = relationRepository
                    .findPeopleRelationByCurrentAccountId(findPeopleDTO.getCurrentAccountId()).stream()
                    .map(PeopleRelation::getFollowingAccountId).collect(Collectors.toSet());
        } else {
            followingAccountIdSet = Collections.emptySet();
        }

        Coordinate currentCoordinate = extractCurrentCoordinate(findPeopleDTO);

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(user -> !Objects.equals(user.getAccountId(), findPeopleDTO.getCurrentAccountId()))
                .map(user -> {
                    PeopleVO peopleVO = userMapper.userToPeopleVO(user, currentCoordinate);
                    if(findPeopleDTO.getCurrentAccountId() != null) {
                        peopleVO.setFollowed(followingAccountIdSet.contains(user.getAccountId()));
                    }

                    return peopleVO;
                }).sorted(sortByDistanceAndName()).collect(Collectors.toList());
    }

    public List<PeopleVO> listFollowers(FindPeopleDTO findPeopleDTO) {

       List<Long> followerAccountIdList = relationRepository.findPeopleRelationByFollowingAccountId(
               findPeopleDTO.getCurrentAccountId()).stream()
               .map(PeopleRelation::getCurrentAccountId).collect(Collectors.toList());

        Coordinate currentCoordinate = extractCurrentCoordinate(findPeopleDTO);
        return userRepository.findUserByAccountIdIn(followerAccountIdList).stream()
                .map(user -> userMapper.userToPeopleVO(user, currentCoordinate))
                .sorted(sortByDistanceAndName()).collect(Collectors.toList());
    }

    public List<PeopleVO> listFollowing(FindPeopleDTO findPeopleDTO) {

        List<Long> followingAccountIdList = relationRepository.findPeopleRelationByCurrentAccountId(
                        findPeopleDTO.getCurrentAccountId()).stream()
                .map(PeopleRelation::getFollowingAccountId).collect(Collectors.toList());

        Coordinate currentCoordinate = extractCurrentCoordinate(findPeopleDTO);
        return userRepository.findUserByAccountIdIn(followingAccountIdList).stream()
                .map(user -> userMapper.userToPeopleVO(user, currentCoordinate))
                .sorted(sortByDistanceAndName()).collect(Collectors.toList());
    }

    Comparator<PeopleVO> sortByDistanceAndName() {
        return (o1, o2) -> {

            // Prefer sorting by distance
            if(o1.getDistanceInMeters() != null && o2.getDistanceInMeters() != null) {
                return o1.getDistanceInMeters().compareTo(o2.getDistanceInMeters());
            }

            // in case distance data is missing
            if (StringUtils.isNotBlank(o1.getName()) && StringUtils.isNotBlank(o2.getName())) {
                return o1.getName().compareTo(o2.getName());
            }

            // in case name is also missing.
            return 0;
        };
    }

    private Coordinate extractCurrentCoordinate(FindPeopleDTO findPeopleDTO) {
        if(Boolean.TRUE.equals(findPeopleDTO.getNearby()) && findPeopleDTO.getCurrentAccountId() != null) {

           User user = userRepository.findUserByAccountId(findPeopleDTO.getCurrentAccountId())
                    .orElseThrow(IllegalArgumentException::new);

            if(user.getLongitude() != null && user.getLatitude() != null) {
                return new Coordinate(user.getLongitude(), user.getLatitude());
            }
        }

        return null;
    }

    @Transactional
    public void follow(Long followerAccountId, AccountIdDTO accountIdDTO) {
        log.trace("{} follows {}", followerAccountId, accountIdDTO.getAccountId());

        PeopleRelation relation = new PeopleRelation();
        relation.setCreatedAt(LocalDateTime.now());
        relation.setCurrentAccountId(followerAccountId);
        relation.setFollowingAccountId(accountIdDTO.getAccountId());
        relationRepository.save(relation);
    }

    @Transactional
    public void unfollow(Long currentAccountId, AccountIdDTO accountIdDTO) {
        log.trace("{} unfollows {}", currentAccountId, accountIdDTO.getAccountId());

        relationRepository.delete(currentAccountId, accountIdDTO.getAccountId());
    }
}