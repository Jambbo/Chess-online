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

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public User registerUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("Username '" + user.getUsername() + "' is already taken.");
        }
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Set<Role> roles = Set.of(Role.USER);
            user.setRoles(roles);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register user: " + e.getMessage(), e);
        }
        return user;
    }


}
