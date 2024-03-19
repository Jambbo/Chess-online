package com.kukuxer.chess.repository;

import com.kukuxer.chess.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT m.id FROM Match m WHERE m.sender.id = :userId")
    List<Long> findMatchIdsBySenderId(Long userId);

    @Query("SELECT m.id FROM Match m WHERE m.receiver.id = :userId")
    List<Long> findMatchIdsByReceiverId(Long userId);

}
