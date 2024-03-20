package com.kukuxer.chess.service;

import com.kukuxer.chess.domain.user.User;
import com.kukuxer.chess.repository.UserRepository;
import com.kukuxer.chess.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface UserService {

    User create(User user);
    User getById(Long id);
    User getByUsername(String username);
}
