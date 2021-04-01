package com.chitra.api.user;

import com.chitra.domain.user.User;
import com.chitra.service.user.UserActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RepositoryRestResource
@ComponentScan(basePackages = "com.chitra.service.user")
public class UserController {

    private final UserActionService userService;

    public UserController(UserActionService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/api/users/new", produces = "application/hal+json")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        try{
            if(user.getPassword() == null) throw new IllegalStateException("password field cannot be null");
            userService.addNewUser(user);
            log.info("Successfully created user: " + user.getUsername());
            return new ResponseEntity<>("Successfully created user",HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value="/api/users/update", produces = "application/hal+json")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody User user){
        userService.updateUser(user);
        log.info("Successfully updated user: " + user.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
