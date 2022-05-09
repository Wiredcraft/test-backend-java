package io.github.jerrychin.testbackendjava.service;

import io.github.jerrychin.testbackendjava.SampleBaseTestCase;
import io.github.jerrychin.testbackendjava.model.dto.AccountIdDTO;
import io.github.jerrychin.testbackendjava.model.dto.FindPeopleDTO;
import io.github.jerrychin.testbackendjava.model.entity.PeopleRelation;
import io.github.jerrychin.testbackendjava.model.entity.User;
import io.github.jerrychin.testbackendjava.model.vo.PeopleVO;
import io.github.jerrychin.testbackendjava.mapper.UserMapper;
import io.github.jerrychin.testbackendjava.repository.PeopleRelationRepository;
import io.github.jerrychin.testbackendjava.repository.UserRepository;
import io.github.jerrychin.testbackendjava.util.Coordinate;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PeopleServiceTest extends SampleBaseTestCase {
    @Mock private UserMapper userMapper;

    @Mock private UserRepository userRepository;

    @Mock private PeopleRelationRepository relationRepository;

    @InjectMocks private PeopleService peopleService;

    @Test
    public void whenDistanceFound_thenSortByDistance() {
        List<PeopleVO> peoples = new ArrayList<>(3);

        PeopleVO people = new PeopleVO();
        people.setDistanceInMeters(3L);
        peoples.add(people);

        people = new PeopleVO();
        people.setDistanceInMeters(2L);
        peoples.add(people);

        people = new PeopleVO();
        people.setDistanceInMeters(1L);
        peoples.add(people);

        peoples.sort(peopleService.sortByDistanceAndName());

        assertThat(peoples.get(0).getDistanceInMeters()).isEqualTo(1L);
        assertThat(peoples.get(1).getDistanceInMeters()).isEqualTo(2L);
        assertThat(peoples.get(2).getDistanceInMeters()).isEqualTo(3L);
    }

    @Test
    public void whenDistanceMissing_thenSortByName() {
        List<PeopleVO> peoples = new ArrayList<>(3);

        PeopleVO people = new PeopleVO();
        people.setName("c");
        peoples.add(people);

        people = new PeopleVO();
        people.setName("b");
        peoples.add(people);

        people = new PeopleVO();
        people.setName("a");
        peoples.add(people);

        peoples.sort(peopleService.sortByDistanceAndName());

        assertThat(peoples.get(0).getName()).isEqualTo("a");
        assertThat(peoples.get(1).getName()).isEqualTo("b");
        assertThat(peoples.get(2).getName()).isEqualTo("c");
    }

    @Test
    public void whenUnfollowSomeone_thenRelationShouldBeDeleted() {
        Long currentAccountId = 1L;
        AccountIdDTO accountIdDTO = new AccountIdDTO();
        accountIdDTO.setAccountId(2L);

        peopleService.unfollow(currentAccountId, accountIdDTO);

        verify(relationRepository).delete(currentAccountId, accountIdDTO.getAccountId());
    }

    @Test
    public void whenFollowSomeone_thenRelationShouldBeAdded() {
        Long currentAccountId = 1L;
        AccountIdDTO accountIdDTO = new AccountIdDTO();
        accountIdDTO.setAccountId(2L);

        peopleService.follow(currentAccountId, accountIdDTO);

        verify(relationRepository).save(argThat(peopleRelation ->
                peopleRelation.getCurrentAccountId().equals(currentAccountId) &&
                peopleRelation.getFollowingAccountId().equals(accountIdDTO.getAccountId()) &&
                peopleRelation.getCreatedAt() != null
        ));
    }

    @Test
    public void whenExtractCurrentCoordinate_thenFetchFromProfile() {

        FindPeopleDTO findPeopleDTO = new FindPeopleDTO();

        findPeopleDTO.setCurrentAccountId(1L);
        findPeopleDTO.setNearby(true);

        User user = new User();
        user.setLongitude(new BigDecimal("123.456"));
        user.setLatitude(new BigDecimal("789.123"));
        when(userRepository.findUserByAccountId(findPeopleDTO.getCurrentAccountId())).thenReturn(Optional.of(user));

        Coordinate coordinate = peopleService.extractCurrentCoordinate(findPeopleDTO);

        assertThat(coordinate).isNotNull();

        assertThat(coordinate.longitude).isEqualTo(user.getLongitude());
        assertThat(coordinate.latitude).isEqualTo(user.getLatitude());
    }

}