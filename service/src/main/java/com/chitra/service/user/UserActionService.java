package com.chitra.service.user;

import com.chitra.domain.role.Role;
import com.chitra.domain.user.User;
import com.chitra.repository.role.RoleRepository;
import com.chitra.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserActionService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    //TODO = need to delete
    public UserActionService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public void addNewUser(User user) {
        log.info("Adding new user: " + user.getUsername());
        if(checkUsernameExists(user)){
            throw new IllegalArgumentException(String.format("Username %s already exists", user.getUsername()));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            log.info(String.format("New user %s has no roles defined adding default ROLE_USER", user.getUsername()));
            Set<Role> allRoles = new HashSet<>();
            allRoles.add(roleRepository.findByRole("ROLE_USER"));
            user.setRoles(allRoles);
        } else {
            log.info("New user has roles defined, adding to database");
            Set<Role> userRoles = getRoles(user);
            user.setRoles(userRoles);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private boolean checkUsernameExists(User user){
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());
        return findUser.isPresent() ? true : false;
    }

    //TODO
    public void updateUser(User user) {
        if(!userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("Username does not exist cannot update");
        }else{
            user.setRoles(getRoles(user));
            userRepository.save(checkPassword(user));
        }
    }

    private User checkPassword(User user){
        if(user.getPassword() == null){
            User fromDb = userRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.setPassword(fromDb.getPassword());
        }else{
            log.info("New password provided, encrypting and saving");
            String password = encoder.encode(user.getPassword());
            user.setPassword(password);
        }
        return user;
    }

    private Set<Role> getRoles(User user) {
        Set<Role> userRoles = user.getRoles()
                .stream()
                .map(role -> roleRepository.findByRole(role.getRole()))
                .collect(Collectors.toSet());
        return userRoles;
    }
}
