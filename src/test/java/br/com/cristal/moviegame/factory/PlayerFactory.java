package br.com.cristal.moviegame.factory;

import br.com.cristal.moviegame.business.entity.Player;

public class PlayerFactory {

    public static Player any() {
        return Player
                .builder()
                .password("12345678")
                .name("Bruno Vidal")
                .email("bruno@test.com")
                .build();
    }
}
