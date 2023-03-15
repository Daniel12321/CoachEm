package nl.itvitae.coachem.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

@Getter
public class JWTToken {

    // TODO: Store in properties.yml
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final Key REFRESH_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXP_ADD_DAY = 1000 * 60 * 60;
    private static final long EXP_ADD_MINUTE = 1000 * 60;

    private final boolean valid;
    private final String email;

    private JWTToken(boolean valid, String email) {
        this.valid = valid;
        this.email = email;
    }

    public static String of(Authentication auth) {
        return Jwts.builder()
                .setSubject(((UserDetails) auth.getPrincipal()).getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXP_ADD_DAY))
                .signWith(KEY)
                .compact();
    }

    public static String refresh(Authentication auth) {
        return Jwts.builder()
                .setSubject(((UserDetails) auth.getPrincipal()).getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXP_ADD_DAY))
                .signWith(REFRESH_KEY)
                .compact();
    }

    public static JWTToken validate(String token) {
        try {
            Claims body = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody();
            return new JWTToken(true, body.getSubject());
        } catch (JwtException ignored) {
            return new JWTToken(false, null);
        }
    }

    public static JWTToken validateRefresh(String token) {
        try {
            Claims body = Jwts.parserBuilder().setSigningKey(REFRESH_KEY).build().parseClaimsJws(token).getBody();
            return new JWTToken(true, body.getSubject());
        } catch (JwtException e) {
            return new JWTToken(false, null);
        }
    }
}
