package com.chitra.security.config;

import com.chitra.domain.role.Role;
import com.chitra.domain.user.User;
import com.chitra.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User loadedUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
        loadedUser.setAuthorities(getUserAuthority(loadedUser.getRoles()));
        return loadedUser;
    }

    private Set<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
        return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());
    }
}
