package br.com.cristal.moviegame.business.mapper.utils;

import br.com.cristal.moviegame.business.entity.Game;
import br.com.cristal.moviegame.business.entity.Round;
import br.com.cristal.moviegame.business.entity.RoundStatus;
import br.com.cristal.moviegame.business.mapper.RoundMapper;
import br.com.cristal.moviegame.entrypoint.dto.response.RoundResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameUtilMapper {

    private final RoundMapper roundMapper;

    public Long mapAmountStatus(Game game, RoundStatus status) {
        return game
                .getRounds()
                .stream()
                .map(Round::getRoundStatus)
                .filter(status::equals)
                .count()
                ;
    }
    public RoundResponse findAndMapCurrentRound(Game game) {
        Round currentRound = game.getRounds()
                .stream()
                .filter(round -> round.getRoundStatus().equals(RoundStatus.WAITING_REPONSE))
                .findFirst()
                .orElse(null);

        return roundMapper.toResponse(currentRound);
    }
}
