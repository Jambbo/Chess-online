package com.kukuxer.chess.repository;

import com.kukuxer.chess.domain.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByReceiverId(Long receiverId);


}
