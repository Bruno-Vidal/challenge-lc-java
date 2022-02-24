package br.com.cristal.moviegame.business.repository;

import br.com.cristal.moviegame.business.entity.Player;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("Testes no repositorio de jogadores")
class PlayerRepositoryTest {


    @Autowired
    private PlayerRepository playerRepository;

    @Test
    @DisplayName("busca de jogodor atraves do email ")
    void findByEmail_Sucess() {
        Player player = this.buildMockPlayer();
        player = playerRepository.save(player);

        Player playerFindByEmail = playerRepository.findByEmail(player.getEmail())
                .orElse(null);

        Assertions.assertThat(playerFindByEmail).isNotNull();
        Assertions.assertThat(playerFindByEmail.getId()).isNotNull();
        Assertions.assertThat(playerFindByEmail.getEmail()).isNotNull();
        Assertions.assertThat(playerFindByEmail.getPassword()).isNotNull();
        Assertions.assertThat(playerFindByEmail).isEqualTo(player);


    }


    private Player buildMockPlayer() {
        return Player
                .builder()
                .email("")
                .name("Testison da Silva")
                .password("12345678")
                .build();
    }
}