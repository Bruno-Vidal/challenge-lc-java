package br.com.cristal.moviegame.config.security;

import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.business.repository.PlayerRepository;
import br.com.cristal.moviegame.business.service.utils.PasswordEncryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitPlayersConfig {

    private final PlayerRepository playerRepository;
    private final PasswordEncryptService passwordEncryptService;

    @Bean
    public void run() {
        playerRepository.save(
                new Player(
                        null ,
                        "Primeiro Jogador",
                        "player1@test.com",
                        passwordEncryptService.encode("12345678")
                )
        );

        playerRepository.save(
                new Player(
                        null ,
                        "Segundo Jogador",
                        "player2@test.com",
                        passwordEncryptService.encode("12345678")
                )
        );
    }

}
