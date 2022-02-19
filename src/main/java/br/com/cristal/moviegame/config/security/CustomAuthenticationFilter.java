package br.com.cristal.moviegame.config.security;

import br.com.cristal.moviegame.business.service.TokenService;
import br.com.cristal.moviegame.config.rotes.LoginRoteMapping;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();

        if (!path.equals(LoginRoteMapping.BASE)) {
            verifyToken(request, response);
        }

        filterChain.doFilter(request, response);
    }

    private void verifyToken(HttpServletRequest request, HttpServletResponse response) {
        DecodedJWT decodedJWT = tokenService.getDecodedJWT();

        if (decodedJWT != null) {


            String subject = decodedJWT.getSubject();

            Collection<SimpleGrantedAuthority> authorities = Arrays.stream(decodedJWT.getClaim("roles").asArray(String.class))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(subject, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
    }
}
