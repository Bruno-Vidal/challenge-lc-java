package br.com.cristal.moviegame.business.mapper;

import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.business.service.utils.PasswordEncryptService;
import br.com.cristal.moviegame.entrypoint.dto.request.PlayerRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.PlayerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel="spring")
public abstract class PlayerMapper {


    @Autowired
    protected PasswordEncryptService passwordEncryptService;

    @Mapping(target = "password", expression = "java(passwordEncryptService.encode(playerRequest.getPassword()))")
    public abstract Player toEntity(PlayerRequest playerRequest) ;

    public abstract PlayerResponse toResponse(Player player) ;
}
