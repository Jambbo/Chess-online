package com.kukuxer.chess.service;
import com.kukuxer.chess.domain.request.Request;
import org.springframework.http.ResponseEntity;

public interface RequestService {

    Request createRequest(Long receiverId, Long senderId);

    Request acceptRequest(Long requestId,Long receiverId);

    Request rejectRequest(Long requestId, Long receiverId);
}
