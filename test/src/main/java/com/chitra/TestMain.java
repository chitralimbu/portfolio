package com.chitra;

import com.chitra.domain.git.GitRepository;
import com.chitra.domain.role.Role;
import com.chitra.domain.user.User;
import com.chitra.repository.role.RoleRepository;
import com.chitra.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
@SpringBootApplication
public class TestMain implements CommandLineRunner {

    public static final String EPOCH = String.valueOf(System.currentTimeMillis());
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private Role roleAdmin;
    private Role roleUser;
    private User userAdmin;
    private User userUser;
    private final PasswordEncoder encoder;

    public TestMain(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Setting up database with roles and users");
        if(roleRepository.findByRole("ROLE_ADMIN") == null){
            log.info("Database does not contain ROLE_ADMIN, adding new ROLE_ADMIN");
            roleAdmin = new Role("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }

        if(roleRepository.findByRole("ROLE_USER") == null){
            log.info("Database does not contain ROLE_USER, adding new ROLE_USER");
            roleUser = new Role("ROLE_USER");
            roleRepository.save(roleUser);
        }

        log.info("Roles findAll: " + roleRepository.findAll());

        userAdmin = User.builder()
                .username("testuseradmin" + EPOCH)
                .accountExpired(true)
                .accountNonLocked(true)
                .roles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ROLE_ADMIN"))))
                .credentialsNonExpired(true)
                .password(encoder.encode("Password"))
                .enabled(true)
                .build();

        userUser = User.builder()
                .username("testuser" + EPOCH)
                .accountExpired(true)
                .accountNonLocked(true)
                .roles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ROLE_USER"))))
                .credentialsNonExpired(true)
                .password(encoder.encode("Password"))
                .enabled(true)
                .build();

        if(userRepository.findAll().isEmpty()){
            log.info("UserRepository is empty, saving 2 users with ROLE_ADMIN and ROLE_USER");
            userRepository.saveAll(Arrays.asList(userAdmin, userUser));
            log.info(String.format("Saving users, %s and %s", userAdmin, userUser));
        }
    }

    @PreDestroy
    public void onExit() {
        log.info("Shutdown requested: removing items from userRepository, and roleRepository");
        log.info(String.format("Deleting %s, %s, %s, %s", roleAdmin, roleUser, userAdmin, userUser));
        roleRepository.deleteById(roleAdmin.getId());
        roleRepository.deleteById(roleUser.getId());
        userRepository.deleteById(userAdmin.getId());
        userRepository.deleteById(userUser.getId());
    }


}
