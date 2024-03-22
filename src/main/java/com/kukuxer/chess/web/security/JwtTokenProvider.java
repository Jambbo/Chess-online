package com.kukuxer.chess.web.security;

import com.kukuxer.chess.domain.user.Role;
import com.kukuxer.chess.domain.user.User;
import com.kukuxer.chess.service.UserService;
import com.kukuxer.chess.service.props.JwtProperties;
import com.kukuxer.chess.web.auth.JwtResponse;
import io.jsonwebtoken.Jws;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;



import javax.crypto.SecretKey;
import java.nio.channels.AcceptPendingException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


    public String createAccessToken(Long userId, String username, Set<Role> roles){
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id",userId)
                .add("roles",resolveRoles(roles))
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    private List<String> resolveRoles(Set<Role> roles){
        return roles.stream()
                .map(Enum::name)
        .collect(Collectors.toList());
    }

    public String createRefreshToken(Long userId, String username){
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id",userId)
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getRefresh(),ChronoUnit.DAYS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if(!validateToken(refreshToken)){
            System.out.println("JwtTokenProvider 84 stroka");;
        }
        Long userId = Long.valueOf(getId(refreshToken));
        User user = userService.getById(userId);
        jwtResponse.setId(userId);
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(createAccessToken(userId,user.getUsername(),user.getRoles()));
        jwtResponse.setRefreshToken(createRefreshToken(userId,user.getUsername()));
        return jwtResponse;
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return !claims.getPayload().getExpiration().before(new Date());
    }
    public String getId(String token){
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id")
                .toString();
    }
    public String getUsername(String token){
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public Authentication getAuthentication(String token){
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }
}

