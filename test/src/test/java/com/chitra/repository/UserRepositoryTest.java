package com.chitra.repository;

import com.chitra.TestMain;
import com.chitra.domain.role.Role;
import com.chitra.domain.user.User;
import com.chitra.repository.role.RoleRepository;
import com.chitra.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.ws.rs.NotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
@SpringBootTest(classes = TestMain.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findUserContaining(){
        List<User> allUser = userRepository.findByUsernameContaining(TestMain.EPOCH).orElseThrow(() -> new UsernameNotFoundException("None found"));
        log.info("All users: " + allUser);
        assertEquals(2, allUser.size());
        allUser.forEach(user -> assertNotNull(user.getRoles()));
    }

    @Test
    public void findUserByRole(){
        Role admin = roleRepository.findByRole("ROLE_ADMIN");
        List<User> users = userRepository.findAllByRolesId(admin.getId()).orElseThrow(() -> new NotFoundException("User with ROLE_ADMIN not round"));
        log.info("Users: " + users);
        assertEquals(1, users.size());
    }
}
