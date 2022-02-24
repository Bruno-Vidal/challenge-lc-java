package br.com.cristal.moviegame.entrypoint.controller;

import br.com.cristal.moviegame.business.service.PlayerService;
import br.com.cristal.moviegame.config.rotes.PlayerRoteMapping;
import br.com.cristal.moviegame.entrypoint.dto.request.PlayerRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.LoginResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.PlayerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(PlayerRoteMapping.BASE)
@RequiredArgsConstructor
@Api(value="Controle de jogadores")
public class PlayerController{

    private final PlayerService playerService;


    @ApiOperation(value = "Cria um novo jogador", response = PlayerResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<PlayerResponse> create(
            @RequestBody @Valid PlayerRequest playerResquest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(playerService.save(playerResquest));
    }
}
