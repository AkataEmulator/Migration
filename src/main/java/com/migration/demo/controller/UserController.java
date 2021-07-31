package com.migration.demo.controller;

import com.migration.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        log.info("Add {} success!", user.toString());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        log.info("Fetch user id is {}", id);
        User user = User.builder()
                        .id(id)
                        .name("tom")
                        .birthDate(new Date())
                        .build();
        return ResponseEntity.ok(user);
    }
}
