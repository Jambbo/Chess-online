package com.kukuxer.chess.web.controller;

import com.kukuxer.chess.domain.request.Request;
import com.kukuxer.chess.domain.user.User;
import com.kukuxer.chess.service.RequestService;
import com.kukuxer.chess.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {
    private final UserService userService;
    private final RequestService requestService;

    @PostMapping("/{receiverId}")
    public ResponseEntity<?> createRequest(@PathVariable("receiverId") Long receiverId,
                                 @CookieValue(value = "user")String username){
        User user = userService.getByUsername(username);
        try{
            System.out.println("receiverId: " + receiverId);
            System.out.println("senderId: " + user.getId());
            requestService.createRequest(receiverId,user.getId());
            return ResponseEntity.ok().body("The request was sent successfully.");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending request.");
        }
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<?> acceptRequest(@PathVariable("requestId") Long requestId,
                                           @CookieValue(value="user") String username){
        User user = userService.getByUsername(username);
            requestService.acceptRequest(requestId,user.getId());
            return ResponseEntity.ok().body("Successfully accepted.");
        }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<?> rejectRequest(@PathVariable("requestId")Long requestId,
                                            @CookieValue(value = "user")String username){
        User user = userService.getByUsername(username);
        requestService.rejectRequest(requestId, user.getId());
        return ResponseEntity.ok().body("Successfully rejected.");
    }
}
