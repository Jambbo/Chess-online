package com.kukuxer.chess.web.controller;

import com.kukuxer.chess.domain.user.User;
import com.kukuxer.chess.service.AuthService;
import com.kukuxer.chess.service.UserService;
import com.kukuxer.chess.web.auth.JwtRequest;
import com.kukuxer.chess.web.auth.JwtResponse;
import com.kukuxer.chess.web.dto.UserDto;
import com.kukuxer.chess.web.mappers.UserMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login (@Validated @RequestBody JwtRequest jwtRequest, HttpServletResponse response) throws AccessDeniedException {
        return authService.login(jwtRequest,response);
    }

    @PostMapping("/register")
    public UserDto register(@Validated @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        userDto.setPassword("secret");
        userDto.setId(createdUser.getId());
        return userDto;
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) throws AccessDeniedException {
        return authService.refresh(refreshToken);
    }
}
