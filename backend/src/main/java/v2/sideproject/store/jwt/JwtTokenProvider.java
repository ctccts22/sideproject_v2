package v2.sideproject.store.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import v2.sideproject.store.exception.APIException;
import v2.sideproject.store.user.models.enums.RolesName;
import v2.sideproject.store.user.repository.UsersRepository;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

import static org.springframework.util.StringUtils.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String secretKey;
    @Value("${app.jwt-expiration-milliseconds}")
    private Long accessTokenValid;

    private final RedisTemplate<String, String> redisTemplate;

    public SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Authentication authentication) {
        Date now = new Date();
        Date accessValidDate = new Date(now.getTime() + accessTokenValid);
        return Jwts.builder()
                .claim("email", authentication.getName())
                .claim("role", authentication.getAuthorities())
                .issuedAt(now)
                .expiration(accessValidDate)
                .signWith(getSignKey())
                .compact();
    }

    public String createRefreshToken(String accessToken) {
        UUID refreshTokenUUID = UUID.randomUUID();
        redisTemplate.opsForValue().set(refreshTokenUUID.toString(), accessToken, Duration.ofDays(1));
        return String.valueOf(refreshTokenUUID);
    }

    public String createAccessToken(Authentication authentication) {
        return generateAccessToken(authentication);
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return String.valueOf(claims.get("email"));
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Extract the role string
        String roleString = String.valueOf(claims.get("role"));

        // Decode to get only the role name
        return extractRoleName(roleString);
    }

    private String extractRoleName(String roleString) {
        int start = roleString.indexOf("ROLE_") + 5;
        int end = roleString.indexOf('}', start);

        return roleString.substring(start, end);
    }


    public String getAccessTokenOnHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getAccessTokenByRefreshToken(String refreshTokenUUID) {
        if (refreshTokenUUID != null) {
            return redisTemplate.opsForValue().get(refreshTokenUUID);
        }
        return null;
    }



    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            Date now = new Date();
            return claims.getExpiration().after(now);
        } catch (MalformedJwtException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Invaild JWT token");
        } catch (ExpiredJwtException ex) {
            log.warn("Token expired", ex);
            return false;
        } catch (UnsupportedJwtException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }

    public void createRefreshTokenCookie(HttpServletResponse response, String refreshTokenKey, String refreshTokenValue, long maxAgeForCookie) {
        ResponseCookie cookie = ResponseCookie.from(refreshTokenKey, refreshTokenValue)
                .path("/")
                .httpOnly(true)
                .maxAge(maxAgeForCookie)
                .sameSite("Strict")
                .secure(true)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void deleteRefreshTokenCookie(HttpServletResponse response, String refreshTokenKey) {
        createRefreshTokenCookie(response, refreshTokenKey, "", 0);
    }

    public void deleteRefreshToken(String redisTokenKey) {
        redisTemplate.delete(redisTokenKey);
    }
}
