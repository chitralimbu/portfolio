package com.chitra.repository;

import com.chitra.TestMain;
import com.chitra.domain.user.User;
import com.chitra.repository.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestMain.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @Before
    public void setUp(){
        user = User.builder()
                .username("test")
                .accountExpired(false)
                .accountNonLocked(false)
                .roles(null)
                .credentialsNonExpired(false)
                .password("Password")
                .enabled(true)
                .build();
    }

    @Test
    public void testUserSaveAndLoad(){
        userRepository.save(user);
        User loaded = userRepository.findByUsername("test").orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        assertTrue(loaded instanceof User);
    }

    @After
    public void cleanUp(){
        userRepository.deleteAll();
    }
}
