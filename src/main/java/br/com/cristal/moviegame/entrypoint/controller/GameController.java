package br.com.cristal.moviegame.entrypoint.controller;

import br.com.cristal.moviegame.business.service.GameService;
import br.com.cristal.moviegame.business.service.RankingService;
import br.com.cristal.moviegame.config.rotes.GameRoteMapping;
import br.com.cristal.moviegame.entrypoint.dto.request.ChoiceRoundRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.GameResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.RankingResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.ResultRoundResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.RoundResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(GameRoteMapping.BASE)
@RequiredArgsConstructor
@Api("Controle do fluxo de jogos")
public class GameController {


    private final GameService gameService;
    private final RankingService rankingService;

    @ApiOperation(value = "Buscar todos os jogos do jogador logado", response = GameResponse.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<GameResponse>> getAll() {
        return ResponseEntity.ok(gameService.getAll());
    }

    @ApiOperation(value = "Cria um jogo para jogador logado caso não tenha um ativo ", response = GameResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<GameResponse> create() {
        return ResponseEntity.ok(gameService.createGame());
    }


    @ApiOperation(value = "Realiza a escolha entre as opções da rodada atual", response = ResultRoundResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(GameRoteMapping.QUIZ)
    public ResponseEntity<ResultRoundResponse> choice(
            @RequestBody ChoiceRoundRequest choiceRoundRequest
    ) {
        return ResponseEntity.ok(gameService.choiceToCurrentRound(choiceRoundRequest));
    }

    @ApiOperation(value = "Busca as opções da rodada Atual", response = RoundResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(GameRoteMapping.QUIZ)
    public ResponseEntity<RoundResponse> currentGame() {

        return ResponseEntity.ok(gameService.currentRoundResponse());
    }

    @ApiOperation(value = "Finaliza o jogo caso exista um jogo ativo", response = GameResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(GameRoteMapping.FINISH)
    public ResponseEntity<GameResponse> finish() {

        return ResponseEntity.ok(gameService.finishGame());
    }


    @ApiOperation(value = "Busca a classificação de todos os jogos finalizados", response = RankingResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(GameRoteMapping.RANKING)
    public ResponseEntity<List<RankingResponse>> getRanking()  {
        return ResponseEntity.ok(rankingService.getRanking());
    }





}
