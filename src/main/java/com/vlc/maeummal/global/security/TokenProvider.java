package com.vlc.maeummal.global.security;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "DKAMDAKFEOQKRRLDNJS";

    //jwt token 생성 메소드
    public String createToken(MemberEntity memberEntity){
     //만료일은 현재로부터 1일
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .setSubject(memberEntity.getEmail())
                .setIssuer("maeummal app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    // token 검증
    public String validateAndGetMemberId(String token){
        Claims claims = Jwts.parser()
                            .setSigningKey(SECRET_KEY)
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getSubject();
    }
}
