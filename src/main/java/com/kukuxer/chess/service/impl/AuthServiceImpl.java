package com.kukuxer.chess.service.impl;

import com.kukuxer.chess.domain.user.Role;
import com.kukuxer.chess.domain.user.User;
import com.kukuxer.chess.repository.UserRepository;
import com.kukuxer.chess.service.AuthService;
import com.kukuxer.chess.service.UserService;
import com.kukuxer.chess.web.auth.JwtRequest;
import com.kukuxer.chess.web.auth.JwtResponse;
import com.kukuxer.chess.web.dto.UserDto;
import com.kukuxer.chess.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kukuxer.chess.web.security.JwtTokenProvider;

import java.nio.file.AccessDeniedException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public JwtResponse login(JwtRequest loginRequest) throws AccessDeniedException {
        JwtResponse jwtResponse = new JwtResponse();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
            );
            User user = userService.getByUsername(loginRequest.getUsername());
            jwtResponse.setId(user.getId());
            jwtResponse.setUsername(jwtTokenProvider.createAccessToken(user.getId(),user.getUsername(),user.getRoles()));
            jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(),user.getUsername()));
            return jwtResponse;
        }catch(Exception e){
            e.printStackTrace();
            throw new AccessDeniedException("");
        }
    }

    @Override
    public JwtResponse refresh(String refreshToken) throws AccessDeniedException {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
