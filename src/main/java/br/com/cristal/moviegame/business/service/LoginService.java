package br.com.cristal.moviegame.business.service;

import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.business.mapper.LoginMapper;
import br.com.cristal.moviegame.config.security.CustomUserDetails;
import br.com.cristal.moviegame.entrypoint.dto.request.LoginRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final LoginMapper loginMapper;

    public LoginResponse login(LoginRequest loginRequest) {


        CustomUserDetails userDetails = validateCredentials(loginRequest);
        String token = tokenService.generate(userDetails);

        return loginMapper.toResponse(userDetails, token);
    }

    private CustomUserDetails validateCredentials(LoginRequest loginRequest) {
        return Optional
                .ofNullable(loginRequest)
                .map(this::mapUserPassAuthenticationToken)
                .map(authenticationManager::authenticate)
                .map(Authentication::getPrincipal)
                .map(ob -> (CustomUserDetails) ob)
                .orElseThrow(() -> new BadCredentialsException("Falha nas credenciais"));
    }

    private UsernamePasswordAuthenticationToken mapUserPassAuthenticationToken(LoginRequest lg) {
        return new UsernamePasswordAuthenticationToken(
                lg.getEmail(),
                lg.getPassword()
        );
    }
}
