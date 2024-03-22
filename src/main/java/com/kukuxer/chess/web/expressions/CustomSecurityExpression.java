package com.kukuxer.chess.web.expressions;

import com.kukuxer.chess.service.RequestService;
import com.kukuxer.chess.service.UserService;
import com.kukuxer.chess.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;

    private final RequestService requestService;
    public boolean canAccessRequest(Long requestId){
        JwtEntity user = getPrincipal();
        Long userId = user.getId();
        System.out.println(userId);
        return userService.isRequestReceiver(userId,requestId);
    }

    public JwtEntity getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtEntity) authentication.getPrincipal();
    }
}
