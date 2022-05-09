package io.github.jerrychin.testbackendjava.controller;


import io.github.jerrychin.testbackendjava.model.dto.AccountIdDTO;
import io.github.jerrychin.testbackendjava.model.dto.FindPeopleDTO;
import io.github.jerrychin.testbackendjava.model.entity.Account;
import io.github.jerrychin.testbackendjava.exception.RestApiException;
import io.github.jerrychin.testbackendjava.model.vo.PeopleVO;
import io.github.jerrychin.testbackendjava.model.vo.UserVO;
import io.github.jerrychin.testbackendjava.service.PeopleService;
import io.github.jerrychin.testbackendjava.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import java.util.List;

@Api(tags="People")
@Slf4j
@RestController
@RequestMapping("/api/v1/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ProfileService profileService;

    public PeopleController(PeopleService peopleService, ProfileService profileService) {
        this.peopleService = peopleService;
        this.profileService = profileService;
    }

    @ApiOperation("Find all people.")
    @GetMapping("")
    public List<PeopleVO> list(@ApiIgnore @AuthenticationPrincipal Account account,
                               @RequestParam(defaultValue = "true") Boolean nearby) {


        FindPeopleDTO findPeopleDTO = new FindPeopleDTO();

        if(account != null) {
            findPeopleDTO.setCurrentAccountId(account.getId());
        }

        findPeopleDTO.setNearby(nearby);

        return peopleService.listPeople(findPeopleDTO);
    }

    @ApiOperation("Find my followers.")
    @GetMapping("/followers")
    public List<PeopleVO> listFollowers(@ApiIgnore @AuthenticationPrincipal Account account,
                               @RequestParam(defaultValue = "true") Boolean nearby) {


        FindPeopleDTO findPeopleDTO = new FindPeopleDTO();

        if(account != null) {
            findPeopleDTO.setCurrentAccountId(account.getId());
        }

        findPeopleDTO.setNearby(nearby);

        return peopleService.listFollowers(findPeopleDTO);
    }

    @ApiOperation("Find my following.")
    @GetMapping("/following")
    public List<PeopleVO> listFollowing(@ApiIgnore @AuthenticationPrincipal Account account,
                                        @RequestParam(defaultValue = "true") Boolean nearby) {


        FindPeopleDTO findPeopleDTO = new FindPeopleDTO();

        if(account != null) {
            findPeopleDTO.setCurrentAccountId(account.getId());
        }

        findPeopleDTO.setNearby(nearby);

        return peopleService.listFollowing(findPeopleDTO);
    }

    @ApiOperation("Follow someone")
    @PostMapping("/following")
    @Transactional
    public void follow(@ApiIgnore @AuthenticationPrincipal Account account, @RequestBody AccountIdDTO accountIdDTO) {
        if (accountIdDTO.getAccountId() == null) {
            throw new RestApiException(HttpStatus.BAD_REQUEST, "accountId is required.");
        }

        peopleService.follow(account.getId(), accountIdDTO);
    }

    @ApiOperation("Unfollow someone")
    @DeleteMapping("/following")
    @Transactional
    public void unfollow(@ApiIgnore @AuthenticationPrincipal Account account, @RequestBody AccountIdDTO accountIdDTO) {
        if (accountIdDTO.getAccountId() == null) {
            throw new RestApiException(HttpStatus.BAD_REQUEST, "accountId is required.");
        }

        peopleService.unfollow(account.getId(), accountIdDTO);
    }

    @ApiOperation("Show people profile.")
    @GetMapping("/{id}/profile")
    public UserVO getProfile(@PathVariable Long id) {
        return profileService.getProfile(id);
    }

}