package com.kukuxer.chess.web.security;

import com.kukuxer.chess.domain.user.Role;
import com.kukuxer.chess.service.UserService;
import com.kukuxer.chess.service.props.JwtProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;



import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private SecretKey key;
    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }
    /*
 сегодня делай, завтра обьґсниш
тут создаем с помощью json web token dev  мне включить чекнуть?
          TODO
    public String createAccessToken(Long userId, String username,Set<Role>roles){
        Claims claims = Jwts.claims()
                .subject
    } */
}

