package com.kukuxer.chess.web.controller;

import com.kukuxer.chess.domain.user.User;
import com.kukuxer.chess.service.UserService;
import com.kukuxer.chess.web.dto.UserDto;
import com.kukuxer.chess.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {


    private UserService userService;
    private final UserMapper userMapper;



}
