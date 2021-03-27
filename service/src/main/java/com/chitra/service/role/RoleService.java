package com.chitra.service.role;

import com.chitra.domain.role.Role;
import com.chitra.repository.role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RoleService implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
    }
}
