package com.backend.portal.controller;


import com.backend.portal.config.UserNotFoundException;
import com.backend.portal.dao.User;
import com.backend.portal.service.FetchUserService;
import com.backend.portal.service.InputValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private FetchUserService userService;

    @Autowired
    private InputValidatorService inputValidatorService;


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @GetMapping("/load")
    public ResponseEntity<String> loadUsers() throws Exception {

        logger.info("Loading ... in DB");
        userService.loadUsersFromApi();
        return ResponseEntity.ok("Users loaded successfully");
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String query) {

        logger.info("Searching");
       // return userService.searchUsers(query);
        inputValidatorService.validateSearchQuery(query);
        List<User> results = userService.searchUsers(query);
        if (results.isEmpty()) {
            throw new UserNotFoundException("No users matched your search query");
        }

        return results;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {

        inputValidatorService.validateId(id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("No user found with ID: " + id));

    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {

        inputValidatorService.validateEmail(email);
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + email));

    }
}
