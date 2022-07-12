package com.lyt.backend.controllers;

import com.lyt.backend.daos.UserCRUDRepository;
import com.lyt.backend.models.Response;
import com.lyt.backend.models.ResponseMessage;
import com.lyt.backend.models.User;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.net.URI;

@RestController
@RequestMapping("/v1")
@Api(
        tags = {"Documents of homework test-backend, containing all crud apis for user"}
)
public class UserCRUDControllerV1 {
    @Autowired
    private UserCRUDRepository userCRUDRepository;

    @Operation(summary = "Create a new user, name is required, with other fields optional, no need to fill created_at and id",
            description = "Create a new user",
            responses = {
                    @ApiResponse(description = "the user created with its id", responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "data": {
                                                "address": "河南省洛阳市 XXXXX",
                                                "createdAt": "2022-07-12T16:03:38.850Z",
                                                "description": "今天天气不错",
                                                "dob": "1993-02-09T00:00:00.000Z",
                                                "id": 1,
                                                "name": "张三"
                                              },
                                              "message": "User successfully created",
                                              "succeed": true
                                            }"""))
                    ),
                    @ApiResponse(description = "fail to create user", responseCode = "500",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "data": null,
                                               "message": "An internal error happened on your request",
                                              "succeed": false
                                            }""")))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The user to create.",
                    content = @Content(schema = @Schema(implementation = User.class), examples = @ExampleObject(value = """
                            {
                              "address": "北京市昌平区XXXX",
                              "description": "今天天气有雨",
                              "dob": "2022-07-12T16:30:34.488Z",
                              "name": "王五"
                            }"""))
            ),
            method = "POST"
    )
    @RequestMapping(value = "users", method = {RequestMethod.POST})
    public ResponseEntity<Response> createUser(@RequestBody User input) {
        try {
            User inserted = userCRUDRepository.save(input);
            return ResponseEntity.created(URI.create("./v1/users/" + inserted.getId())).
                    body(Response.ofSucceed(ResponseMessage.SUCCEED_CREATED_USER.message, inserted));
        } catch (Exception e) {
            //log here
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Response.ofFailure(ResponseMessage.REQUEST_FAIL.message));
        }
    }

    @Operation(
            summary = "Retrieve a user with its id",
            description = "Retrieve a user with its id",
            parameters = {
                    @Parameter(name = "id", description = "id of user you want to retrieve", in = ParameterIn.PATH, required = true)
            },
            responses = {
                    @ApiResponse(description = "The user corresponding to id provided", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "data": {
                                                "address": "河南省洛阳市 XXXXX",
                                                "createdAt": "2022-07-12T16:03:38.850Z",
                                                "description": "今天天气不错",
                                                "dob": "1993-02-09T00:00:00.000Z",
                                                "id": 1,
                                                "name": "张三"
                                              },
                                              "message": "User information successfully retrieved",
                                              "succeed": true
                                            }"""))
                    )
            },
            method = "GET"
    )
    @RequestMapping(value = "users/{id}", method = {RequestMethod.GET})
    public ResponseEntity<Response> getUser(@PathVariable int id) {
        return userCRUDRepository.findById(id)
                .map(user -> ResponseEntity.ok(Response.ofSucceed(ResponseMessage.SUCCEED_RETRIEVED_USER_INFO.message, user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update a user with fields provided",
            description = "If a field is null, this field is ignored, or it is updated",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The user to create.",
                    content = @Content(schema = @Schema(implementation = User.class),
                            examples = @ExampleObject(value = """
                                    {
                                    "id": 1,\s
                                      "description": "今天电闪雷鸣",
                                    }"""))
            ),
            responses = {
                    @ApiResponse(description = "The information of updated user", responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """

                                              "succeed": true,
                                              "message": "User information successfully updated",
                                              "data": {
                                                "id": 1,
                                                "name": "张三",
                                                "dob": "2022-07-13T00:43:57.733+08:00",
                                                "address": "北京市昌平区 XXXX",
                                                "description": "今天有雨",
                                                "createdAt": "2022-07-13T08:44:15.306+08:00"
                                              }
                                            }"""))
                    )
            },
            method = "POST"
    )
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
                            if (o != null) {
                                declaredField.set(user, o);
                            }
                        }
                    } catch (Exception e) {
                        //ignore that
                    }
                    user = userCRUDRepository.save(user);
                    return ResponseEntity.created(URI.create("./v1/users/" + user.getId()))
                            .body(Response.ofSucceed(ResponseMessage.SUCCEED_UPDATED_USER_INFO.message, user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete user of corresponding id",
            method = "DELETE",
            parameters = {
                    @Parameter(name = "id", description = "id of the user you want to delete", in = ParameterIn.PATH, required = true)
            },
            responses = {
                    @ApiResponse(description = "The corresponding user has been successfully deleted", responseCode = "204")
            }
    )
    @RequestMapping(value = "/users/{id}", method = {RequestMethod.DELETE})
    public ResponseEntity<Response> deleteUser(@PathVariable int id) {
        if (userCRUDRepository.existsById(id)) {
            userCRUDRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
