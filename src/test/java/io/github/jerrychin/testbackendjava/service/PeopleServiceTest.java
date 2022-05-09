package io.github.jerrychin.testbackendjava.service;

import io.github.jerrychin.testbackendjava.SampleBaseTestCase;
import io.github.jerrychin.testbackendjava.model.dto.AccountIdDTO;
import io.github.jerrychin.testbackendjava.model.entity.PeopleRelation;
import io.github.jerrychin.testbackendjava.model.vo.PeopleVO;
import io.github.jerrychin.testbackendjava.mapper.UserMapper;
import io.github.jerrychin.testbackendjava.repository.PeopleRelationRepository;
import io.github.jerrychin.testbackendjava.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

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

}