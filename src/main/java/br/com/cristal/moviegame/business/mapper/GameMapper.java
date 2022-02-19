package br.com.cristal.moviegame.business.mapper;

import br.com.cristal.moviegame.business.entity.Game;
import br.com.cristal.moviegame.business.entity.RoundStatus;
import br.com.cristal.moviegame.business.mapper.utils.GameUtilMapper;
import br.com.cristal.moviegame.entrypoint.dto.response.GameResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.RankingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel="spring", imports = {RoundStatus.class})
public abstract class GameMapper {

    @Autowired
    protected GameUtilMapper gameUtilMapper;

    @Mapping(target = "currentRound", expression = "java(gameUtilMapper.findAndMapCurrentRound(game))")
    @Mapping(target = "status", expression = "java(game.getGameStatus().name())")
    @Mapping(target = "amountRound", expression = "java(game.getRounds().size())")
    @Mapping(target = "amountCorrect", expression = "java(gameUtilMapper.mapAmountStatus(game, RoundStatus.CORRERCT))")
    @Mapping(target = "amountWrong", expression = "java(gameUtilMapper.mapAmountStatus(game, RoundStatus.WRONG))")
    public abstract GameResponse toResponse(Game game);

    @Mapping(target = "player" , source = "game.player.name")
    @Mapping(target = "amountRound" , expression = "java(game.getRounds().size())")
    public abstract RankingResponse toRanking(Game game, Integer rank) ;
}
