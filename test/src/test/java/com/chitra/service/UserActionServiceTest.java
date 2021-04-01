package com.chitra.service;

import com.chitra.TestMain;
import com.chitra.domain.user.User;
import com.chitra.repository.user.UserRepository;
import com.chitra.service.user.UserActionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest(classes = TestMain.class)
public class UserActionServiceTest {

    @Autowired
    private UserActionService userActionService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserUpdate(){
        User user = userRepository.findAll().get(0);
        user.setPassword(null);
        user.setEnabled(false);
        log.info("Saving user: " + user);
        userActionService.updateUser(user);

        User fromDb = userRepository.findById(user.getId()).get();
        log.info("From user: " + user);
        assertFalse(fromDb.isEnabled());
        assertNotNull(fromDb.getPassword());
    }

}
