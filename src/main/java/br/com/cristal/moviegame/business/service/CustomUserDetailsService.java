package br.com.cristal.moviegame.business.service;

import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.business.repository.PlayerRepository;
import br.com.cristal.moviegame.config.movies.OmdbApiProperties;
import br.com.cristal.moviegame.config.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;
    private final TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Player player = getPlayer(email);
        return new CustomUserDetails(player);
    }

    public Player loadUser()  {

        String email = tokenService.getDecodedJWT().getSubject();
        return getPlayer(email);

    }

    private Player getPlayer(String email) {
        return playerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário Ínvalido!"));
    }

}
