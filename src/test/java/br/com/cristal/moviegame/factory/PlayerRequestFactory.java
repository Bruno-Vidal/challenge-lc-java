package br.com.cristal.moviegame.factory;

import br.com.cristal.moviegame.entrypoint.dto.request.PlayerRequest;

public class PlayerRequestFactory {

    public static PlayerRequest any() {
        return PlayerRequest
                .builder()
                .email("bruno@test.com.br")
                .name("Bruno vidal")
                .password("12345678")
                .build();
    }
}
