package nl.garagemeijer.salesapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.SecretKey}")
    private String SECRET_KEY;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public Date extractExperiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String jwt) {
        return extractExperiration(jwt).before(new Date());
    }

    public GrantedAuthority extractRole(String jwt) {
        String role = extractClaim(jwt, claims -> claims.get("role", String.class));
        return new SimpleGrantedAuthority(role);
    }

    public List<GrantedAuthority> extractSimpleGrantedAuthorities(String jwt) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(extractRole(jwt));
        return authorities;
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        if (!role.isEmpty()) {
            String oneRole = role.getFirst();
            String withoutRole = oneRole.replace("ROLE_", "");
            claims.put("role", withoutRole);
        }
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
    }
}
