package io.github.jerrychin.testbackendjava.controller;


import io.github.jerrychin.testbackendjava.dto.UserDTO;
import io.github.jerrychin.testbackendjava.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PostMapping("")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
       return userService.saveUser(userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

}