package br.com.cristal.moviegame.factory;

import br.com.cristal.moviegame.business.entity.Game;

import java.util.Arrays;
import java.util.List;

public class GameFactory {

    public static Game any() {
        return Game
                .builder()
                .build();
    }

    public static Game withSocre(Double score) {
        Game game = any();
        game.finish();
        game.setScore(score);

        return game;
    }

    public static List<Game> games(){
        return Arrays.asList(
                any(),
                withSocre(10.0),
                withSocre(3.0)
        );
    }
}
