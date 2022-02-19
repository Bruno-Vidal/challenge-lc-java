package br.com.cristal.moviegame.business.service;

import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.business.mapper.LoginMapper;
import br.com.cristal.moviegame.config.security.CustomUserDetails;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class TokenService {


    @Value("${jwt.expirate}")
    private Integer JWT_TOKEN_VALIDITY;

    @Value("${jwt.secret}")
    private String JWT_SECRET_KEY;

    private final LoginMapper loginMapper;

    private final HttpServletRequest request;

    public static final String PREFIX_TOKEN = "Bearer ";

    public TokenService(LoginMapper loginMapper, HttpServletRequest request) {
        this.loginMapper = loginMapper;
        this.request = request;
    }

    public String generate(UserDetails userDetails) {

        List<String> authorities = buildAuthorites(userDetails);

        String subject = buildSubject(userDetails);

        Date expireteDate = buildDate(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000);
        Date inssuedDate = buildDate(System.currentTimeMillis());

        Algorithm algorithm = getAlgorithm();

        return JWT.create()
                .withClaim("roles", authorities)
                .withSubject(subject)
                .withExpiresAt(expireteDate)
                .withIssuedAt(inssuedDate)
                .sign(algorithm);
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET_KEY);
    }

    public Date buildDate(Long millis) {
        return new Date(millis);
    }

    public String buildSubject(UserDetails userDetails) {
        return userDetails.getUsername();
    }

    public List<String> buildAuthorites(UserDetails userDetails) {
        return loginMapper.mapRoles(userDetails);
    }

    public DecodedJWT getDecodedJWT() {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (
                authorization != null &&
                        authorization.startsWith(PREFIX_TOKEN)
        ) {
            String clearToken = authorization.substring(PREFIX_TOKEN.length());
            Algorithm algorithm = this.getAlgorithm();
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(clearToken);
            return decodedJWT;
        }
        return null;
    }


}
