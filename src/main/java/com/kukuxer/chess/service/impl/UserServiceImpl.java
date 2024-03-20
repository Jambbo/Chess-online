package com.kukuxer.chess.service.impl;

import com.kukuxer.chess.domain.user.Role;
import com.kukuxer.chess.domain.user.User;
import com.kukuxer.chess.repository.UserRepository;
import com.kukuxer.chess.service.UserService;
import com.kukuxer.chess.web.dto.UserDto;
import com.kukuxer.chess.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    @Transactional
    public User create(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("User already exists.");
        }
        if(!user.getPassword().equals(user.getPasswordConfirmation())){
            throw new IllegalStateException("Password and password confirmation do not match.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.USER);
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                RuntimeException::new
        );
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                ()->new RuntimeException("User not found.")
        );
    }

}
