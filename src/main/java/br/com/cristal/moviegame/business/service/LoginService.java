package br.com.cristal.moviegame.business.service;

import br.com.cristal.moviegame.business.mapper.LoginMapper;
import br.com.cristal.moviegame.config.security.CustomUserDetails;
import br.com.cristal.moviegame.entrypoint.dto.request.LoginRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

        UsernamePasswordAuthenticationToken authenticationToken = this.mapUserPassAuthenticationToken(loginRequest);
        Authentication authenticate = this.authenticationManager.authenticate(authenticationToken);
        return (CustomUserDetails) authenticate.getPrincipal();
    }

    private UsernamePasswordAuthenticationToken mapUserPassAuthenticationToken(LoginRequest lg) {
        return new UsernamePasswordAuthenticationToken(
                lg.getEmail(),
                lg.getPassword()
        );
    }
}
