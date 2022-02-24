package br.com.cristal.moviegame.entrypoint.controller;

import br.com.cristal.moviegame.business.service.LoginService;
import br.com.cristal.moviegame.config.rotes.LoginRoteMapping;
import br.com.cristal.moviegame.entrypoint.dto.request.LoginRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.GameResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.LoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(LoginRoteMapping.BASE)
@RequiredArgsConstructor
@Api(value="Controle de acesso")
public class LoginController {

    private final LoginService loginService;

    @ApiOperation(value = "Realiza o login e disponibilizar o token de acesso", response = LoginResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity
                .ok()
                .body(loginService.login(loginRequest));
    }
}
