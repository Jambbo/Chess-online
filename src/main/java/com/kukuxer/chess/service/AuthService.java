package com.kukuxer.chess.service;

import com.kukuxer.chess.domain.user.User;
import com.kukuxer.chess.web.auth.JwtRequest;
import com.kukuxer.chess.web.auth.JwtResponse;
import com.kukuxer.chess.web.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.file.AccessDeniedException;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest, HttpServletResponse response) throws AccessDeniedException;

    JwtResponse refresh(String refreshToken) throws AccessDeniedException;

}
