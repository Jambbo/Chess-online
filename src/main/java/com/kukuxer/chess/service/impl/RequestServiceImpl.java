package com.kukuxer.chess.service.impl;

import com.kukuxer.chess.domain.request.Request;
import com.kukuxer.chess.domain.request.Status;
import com.kukuxer.chess.domain.user.User;
import com.kukuxer.chess.repository.RequestRepository;
import com.kukuxer.chess.repository.UserRepository;
import com.kukuxer.chess.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Override
    public Request createRequest(Long receiverId, Long senderId) {
        User receiver = userRepository.findById(receiverId).orElseThrow(
                ()-> new RuntimeException("Receiver not found.")
        );
        User sender = userRepository.findById(senderId).orElseThrow(
                ()-> new RuntimeException("Sender not found")
        );
         Request request = Request.builder()
                .sender(sender)
                .receiver(receiver)
                .status(Status.PENDING)
                .build();
         requestRepository.save(request);
        return request;
    }

    @Override
    public Request acceptRequest(Long requestId,Long receiverId) {
        Request request = requestRepository.findById(requestId).orElseThrow(
                ()-> new RuntimeException("Request not found.")
        );
        if(!request.getReceiver().getId().equals(receiverId) || (request.getStatus()!=Status.PENDING)){
            throw new RuntimeException("You can not accept the non-pending request.");
        }else{
            request.setStatus(Status.ACCEPTED);
            requestRepository.save(request);
        }
        return request;
    }

    @Override
    public Request rejectRequest(Long requestId, Long receiverId) {
        Request request = requestRepository.findById(requestId).orElseThrow(
                ()-> new RuntimeException("Request not found.")
        );
        if(!request.getReceiver().getId().equals(receiverId) || (request.getStatus()!=Status.PENDING)){
            throw new RuntimeException("You can not accept the non-pending request.");
        }else{
            request.setStatus(Status.REJECTED);
            requestRepository.save(request);
        }
        return request;
    }

}
