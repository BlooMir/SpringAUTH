package com.example.ch16.config.security;

import com.example.ch16.service.UserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailService userDetailService;

    private String secretKey = "daelimSpring!@#$daelimSpring!@#$daelimSpring!@#$";
    private final long tokenValidMillisecond = 1000L * 60 * 60;

    @PostConstruct
    protected void init() {
        System.out.println("[init] jwtTokenProvider start >>>>");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String createToken(String userUid, List<String> roles) {
        System.out.println("[createToken] 토큰 생성 시작 >>>>");
        Claims claims = Jwts.claims().setSubject(userUid);
        claims.put("roles", roles);
        Date now = new Date();
        String token = Jwts.builder().setClaims(claims).setIssuedAt(now).
                setExpiration(new Date(now.getTime() + tokenValidMillisecond)).
                signWith(SignatureAlgorithm.HS256, secretKey).compact();
        return token;
    }

    public Authentication getAuthentication(String token) {
        System.out.println("[getAuthenication] 토큰 인증 정보 조회");
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getUserName(String token) {
        String info = Jwts.parser().setSigningKey(secretKey).
                parseClaimsJws(token).getBody().getSubject();
        System.out.println("[getUserName] : " + info);
        return info;
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
