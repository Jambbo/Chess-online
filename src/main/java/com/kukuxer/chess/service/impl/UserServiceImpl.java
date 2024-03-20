package com.kukuxer.chess.service.impl;

import com.kukuxer.chess.repository.UserRepository;
import com.kukuxer.chess.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;



}
