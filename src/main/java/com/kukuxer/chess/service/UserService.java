package com.kukuxer.chess.service;

import com.kukuxer.chess.domain.user.User;


public interface UserService {

    User create(User user);
    User getById(Long id);
    User getByUsername(String username);

    boolean isRequestReceiver(Long userId, Long requestId);
}
