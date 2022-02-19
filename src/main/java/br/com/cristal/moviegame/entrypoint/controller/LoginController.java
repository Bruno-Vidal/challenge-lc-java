package br.com.cristal.moviegame.entrypoint.controller;

import br.com.cristal.moviegame.business.service.LoginService;
import br.com.cristal.moviegame.config.rotes.LoginRoteMapping;
import br.com.cristal.moviegame.entrypoint.dto.request.LoginRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(LoginRoteMapping.BASE)
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity
                .ok()
                .body(loginService.login(loginRequest));
    }
}
