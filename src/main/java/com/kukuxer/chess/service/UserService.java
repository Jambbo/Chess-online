package com.kukuxer.chess.service;

import com.kukuxer.chess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Long> findMatchIdsForUser(Long userId) {
        List<Long> matchIds = new ArrayList<>();
        matchIds.addAll(userRepository.findMatchIdsBySenderId(userId));
        matchIds.addAll(userRepository.findMatchIdsByReceiverId(userId));
        return matchIds;
    }
}
