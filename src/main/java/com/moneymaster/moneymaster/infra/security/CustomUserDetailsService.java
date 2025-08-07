package com.moneymaster.moneymaster.infra.security;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found.");
        } else {
            return new UserPrincipal(user);
        }
    }
}