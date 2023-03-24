package com.sashafilth.controller;

import com.sashafilth.dao.User;
import com.sashafilth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/fill")
    public ResponseEntity<String> fill() {
        userService.fillWithUsers();
        return new ResponseEntity<>(HttpStatus.CREATED.toString(), HttpStatus.CREATED);
    }

    @GetMapping(path = "/find-random")
    public ResponseEntity<User> findRandom() {
        User user = userService.findRandom();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
