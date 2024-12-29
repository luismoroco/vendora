package com.vendora.engine.common.util;

import com.vendora.engine.common.session.exc.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

public class JwtUtil {
  private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
  private static final Long JWT_EXPIRATION = 86400000L;
  private static final String BEARER_AUTH_PREFIX = "Bearer ";
  private static final Key SIGN_IN_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

  public static Optional<String> getHeaderToken(String header) {
    if (StringUtils.hasText(header) && header.startsWith(BEARER_AUTH_PREFIX)) {
      return Optional.of(header.substring(BEARER_AUTH_PREFIX.length()));
    }

    return Optional.empty();
  }

  public static String buildToken(String subject) {
    return Jwts.builder()
      .setClaims(new HashMap<>())
      .setSubject(subject)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
      .signWith(SIGN_IN_KEY, SignatureAlgorithm.HS256)
      .compact();
  }

  public static boolean isValid(String token, String subject) {
    return getSubject(token).equals(subject) && !getClaimGateway(token, Claims::getExpiration).before(new Date());
  }

  public static String getSubject(String token) {
    return getClaimGateway(token, Claims::getSubject);
  }

  private static <T> T getClaimGateway(
    String token,
    Function<Claims, T> resolver
  ) {
    try {
      var claims = Jwts
        .parserBuilder()
        .setSigningKey(SIGN_IN_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody();

      return resolver.apply(claims);
    } catch (MalformedJwtException ex) {
      throw new InvalidJwtException("Invalid jwt token");
    } catch (SignatureException ex) {
      throw new InvalidJwtException("Invalid jwt signature");
    } catch (IllegalArgumentException ex) {
      throw new InvalidJwtException("Invalid jwt claim");
    }
  }
}
