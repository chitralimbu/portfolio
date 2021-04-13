package com.chitra.service.rest;

import com.chitra.domain.role.Role;
import com.chitra.domain.user.User;
import com.chitra.repository.role.RoleRepository;
import com.chitra.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RepositoryEventHandler
public class UserEventHandler {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @HandleBeforeCreate
    public void handleUserCreate(User user){
        if(checkUserExists(user)){
            throw new IllegalArgumentException(String.format("Username %s: already exists", user.getUsername()));
        }
        Set<Role> roles;
        if(user.getRoles() == null || user.getRoles().isEmpty()){
            log.debug("Role does not exist for new requested user: " + user.getUsername() + " giving ROLE_USER");
            Role role = roleRepository.findByRole("ROLE_USER");
            roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        }else{
            user.setRoles(getRoles(user));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        log.debug("Saving user: " + user);
    }

    @HandleBeforeSave
    public void handleUserSave(User user){
        log.info("HandleUserSave: " + user);
        User fromDb = userRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found in database"));
        String userPassword = user.getPassword();

        if(!user.getPassword().equals(fromDb.getPassword())){
            log.info("Password is not the same: updating password");
            user.setPassword(encoder.encode(userPassword));
        }
    }

    private boolean checkUserExists(User user){
        Optional<User> checkUser = userRepository.findByUsername(user.getUsername());
        return checkUser.isPresent() ? true : false;
    }

    private Set<Role> getRoles(User user) {
        return user.getRoles()
                .stream()
                .map(role -> roleRepository.findByRole(role.getRole()))
                .collect(Collectors.toSet());
    }
}
