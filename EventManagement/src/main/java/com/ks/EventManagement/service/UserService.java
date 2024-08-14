package com.ks.EventManagement.service;

import com.ks.EventManagement.dto.UserDto;
import com.ks.EventManagement.entity.User;
import com.ks.EventManagement.entity.authority.Role;
import com.ks.EventManagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user with that username: " + username));
    }

    //Save user (registration)
    public int saveUser(UserDto dto){
        try{
            userRepository.save(User.builder()
                            .username(dto.getUsername())
                            .first_name(dto.getFirst_name())
                            .last_name(dto.getLast_name())
                            .email(dto.getEmail())
                            .role(Role.USER)
                            .password(new BCryptPasswordEncoder(12).encode(dto.getPassword()))
                            .active(false)
                    .build());
            return 200;
        }catch (Exception e){
            return 400;
        }
    }

    //Update user status to active
    public void updateUserStatus(String username, boolean status){
        User user = loadUserByUsername(username);
        user.setActive(status);
        userRepository.save(user);
    }

    // return user by email
    public User returnUserByEmail(String email){
        return userRepository.findUserByUsername(email)
                .orElseThrow(() -> new RuntimeException("No such email"));
    }
}
