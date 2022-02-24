package br.com.cristal.moviegame.entrypoint.controller;

import br.com.cristal.moviegame.business.service.PlayerService;
import br.com.cristal.moviegame.entrypoint.dto.request.PlayerRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.PlayerResponse;
import br.com.cristal.moviegame.factory.PlayerRequestFactory;
import br.com.cristal.moviegame.factory.PlayerResponseFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PlayerControllerTest {

    @InjectMocks
    private PlayerController playerController;

    @Mock
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        PlayerResponse playerResponse = PlayerResponseFactory.any();

        BDDMockito.when(playerService.save(ArgumentMatchers.any())).thenReturn(playerResponse);
    }

    @Test
    @DisplayName("Criando um player")
    void createPlayer() {
        PlayerRequest playerRequest = PlayerRequestFactory.any();
        PlayerResponse playerResponse = playerController.create(playerRequest).getBody();

        Assertions.assertThat(playerResponse).isNotNull();
        Assertions.assertThat(playerResponse).isEqualTo(PlayerResponseFactory.any());
    }

}