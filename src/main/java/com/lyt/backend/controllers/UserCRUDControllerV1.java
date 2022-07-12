package com.lyt.backend.controllers;

import com.lyt.backend.daos.UserCRUDRepository;
import com.lyt.backend.models.Response;
import com.lyt.backend.models.ResponseMessage;
import com.lyt.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.net.URI;

@RestController
@RequestMapping("/v1")
public class UserCRUDControllerV1 {
    @Autowired
    private UserCRUDRepository userCRUDRepository;
    @RequestMapping(value = "users", method = {RequestMethod.POST})
    public ResponseEntity<Response> createUser(@RequestBody User input) {
        try {
            User inserted = userCRUDRepository.save(input);
            return ResponseEntity.created(URI.create("./v1/users/" + inserted.getId())).body(Response.ofSucceed(ResponseMessage.SUCCEED_CREATED_USER.message,inserted));
        } catch (Exception e) {
            //log here
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Response.ofFailure(ResponseMessage.REQUEST_FAIL.message));
        }
    }

    @RequestMapping(value = "users/{id}", method = {RequestMethod.GET})
    public ResponseEntity<Response> getUser(@PathVariable int id) {
        return userCRUDRepository.findById(id)
                .map(user -> ResponseEntity.ok(Response.ofSucceed(ResponseMessage.SUCCEED_RETRIEVED_USER_INFO.message, user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "users/update", method = {RequestMethod.POST})
    public ResponseEntity<Response> updateUser(@RequestBody User userToUpdate) {
        return userCRUDRepository.findById(userToUpdate.getId())
                .map(user -> {
                    //homemade BeanUtils.copyProperties
                    try {
                        //there should be blacklist for created_at
                        for (Field declaredField : User.class.getDeclaredFields()) {
                            declaredField.setAccessible(true);
                            Object o = declaredField.get(userToUpdate);
                            if(o != null) {
                                declaredField.set(user, o);
                            }
                        }
                    } catch (Exception e) {
                        //ignore that
                    }
                    user = userCRUDRepository.save(user);
                    return ResponseEntity.created(URI.create("./v1/users/" + user.getId())).body(Response.ofSucceed(ResponseMessage.SUCCEED_UPDATED_USER_INFO.message, user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/users/{id}", method = {RequestMethod.DELETE})
    public ResponseEntity<Response> deleteUser(@PathVariable int id) {
        if(userCRUDRepository.existsById(id)) {
            userCRUDRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
