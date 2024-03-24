package bodydodi.study.interceptor.domain;

import bodydodi.study.interceptor.dto.InterceptorUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class InterceptorJwtTokenProvider {
    private String secretKey = "TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST";
    private long expiredTime = 1000 * 60L * 60L * 12L;

    public String createJwtToken(InterceptorUserDto userDto) {
        Date now = new Date();
        now.setTime(now.getTime() + expiredTime);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setExpiration(now)
                .claim("auth", userDto.getAuth())
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public InterceptorUserAuth getUserAuth(String jwtToken) throws Exception {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        if (claims == null) throw new Exception("토큰이 없습니다.");

        String auth = claims.get("auth", String.class);

        return InterceptorUserAuth.findUserAuth(auth);
    }
}
