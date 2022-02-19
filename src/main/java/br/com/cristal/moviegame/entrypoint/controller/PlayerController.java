package br.com.cristal.moviegame.entrypoint.controller;

import br.com.cristal.moviegame.business.service.PlayerBaseService;
import br.com.cristal.moviegame.config.rotes.PlayerRoteMapping;
import br.com.cristal.moviegame.entrypoint.contract.PlayerApiContract;
import br.com.cristal.moviegame.entrypoint.dto.request.PlayerRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.PlayerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PlayerRoteMapping.BASE)
@RequiredArgsConstructor
public class PlayerController implements PlayerApiContract {

    private final PlayerBaseService playerBaseService;

    @Override
    @PostMapping
    public ResponseEntity<PlayerResponse> create(
            @RequestBody PlayerRequest playerResquest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(playerBaseService.save(playerResquest));
    }
}
