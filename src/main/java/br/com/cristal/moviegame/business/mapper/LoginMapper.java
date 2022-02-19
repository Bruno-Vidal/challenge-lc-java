package br.com.cristal.moviegame.business.mapper;

import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.business.service.utils.PasswordEncryptService;
import br.com.cristal.moviegame.config.security.CustomUserDetails;
import br.com.cristal.moviegame.entrypoint.dto.request.PlayerRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.LoginResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.PlayerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel="spring")
public abstract class LoginMapper {


    @Mapping(target = "email", source = "userDetails.player.email")
    @Mapping(target = "name", source = "userDetails.player.name")
    @Mapping(target = "roles", expression = "java(this.mapRoles(userDetails))")
    public abstract LoginResponse toResponse(CustomUserDetails userDetails, String token) ;

    public List<String> mapRoles(UserDetails userDetails) {
        return userDetails.
                getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
