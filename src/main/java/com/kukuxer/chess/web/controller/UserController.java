package com.kukuxer.chess.web.controller;

import com.kukuxer.chess.domain.user.User;
import com.kukuxer.chess.service.UserService;
import com.kukuxer.chess.web.mappers.UserMapper;
import com.kukuxer.chess.web.security.JwtEntity;
import com.kukuxer.chess.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/request/{receiverId}")
//    @PreAuthorize("isAuthenticated() AND hasRole(com.kukuxer.chess.user.Role.USER)")
    public ResponseEntity<?> sendRequest(@PathVariable("receiverId") Long receiverId,
                                         @CookieValue(value = "user")String username)
    {
            try{
                User user = userService.getByUsername(username);
                System.out.println("receiverId: " + receiverId);
                System.out.println("senderId: " + user.getId());
                return ResponseEntity.ok("Request sent successfully.");
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending request.");
            }
    }
}
