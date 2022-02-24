package br.com.cristal.moviegame.factory;

import br.com.cristal.moviegame.entrypoint.dto.response.PlayerResponse;

public class PlayerResponseFactory {

    public static PlayerResponse any() {
        return PlayerResponse
                .builder()
                .email("bruno@test.com.br")
                .name("Bruno vidal")
                .id(1L)
                .build();
    }
}
