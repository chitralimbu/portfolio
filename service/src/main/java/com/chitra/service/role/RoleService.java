package com.chitra.service.role;

import com.chitra.domain.role.Role;
import com.chitra.domain.user.User;
import com.chitra.repository.role.RoleRepository;
import com.chitra.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Component
public class RoleService implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Role> allRoles = roleRepository.findAll();
        if(allRoles.isEmpty()){
            log.info("No roles detected: adding minimum role ADMIN and USER");
            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleUser = new Role("ROLE_USER");
            roleRepository.saveAll(Arrays.asList(roleAdmin, roleUser));
        }

        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            log.info("User Repo is empty, adding new ones");
            User userAdmin = User.builder()
                    .username("testuseradmin")
                    .accountExpired(true)
                    .accountNonLocked(true)
                    .roles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ROLE_ADMIN"))))
                    .credentialsNonExpired(true)
                    .password(encoder.encode("Password"))
                    .enabled(true)
                    .build();

            User userUser = User.builder()
                    .username("testuser")
                    .accountExpired(true)
                    .accountNonLocked(true)
                    .roles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ROLE_USER"))))
                    .credentialsNonExpired(true)
                    .password(encoder.encode("Password"))
                    .enabled(true)
                    .build();
            userRepository.save(userAdmin);
            userRepository.save(userUser);
        }
    }
}
