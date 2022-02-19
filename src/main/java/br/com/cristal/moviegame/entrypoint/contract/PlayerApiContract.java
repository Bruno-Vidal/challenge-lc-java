package br.com.cristal.moviegame.entrypoint.contract;

import br.com.cristal.moviegame.entrypoint.dto.request.PlayerRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.PlayerResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlayerApiContract {

    ResponseEntity<PlayerResponse> create(PlayerRequest playerResquest);
}
