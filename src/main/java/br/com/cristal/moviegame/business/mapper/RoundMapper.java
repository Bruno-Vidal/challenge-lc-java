package br.com.cristal.moviegame.business.mapper;

import br.com.cristal.moviegame.business.entity.Game;
import br.com.cristal.moviegame.business.entity.Round;
import br.com.cristal.moviegame.entrypoint.dto.response.GameResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.ResultRoundResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.RoundResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel="spring")
public abstract class RoundMapper {


    public abstract RoundResponse toResponse(Round round);

    @Mapping(target = "firstMovie", source = "currentRound.firstMovie")
    @Mapping(target = "secundMovie", source = "currentRound.secundMovie")
    @Mapping(target = "correct", source = "rigth")
    @Mapping(target = "gameStatus", expression = "java(currentRound.getGame().getGameStatus().name())")
    public abstract ResultRoundResponse toResult(Round currentRound, Boolean rigth);
}
