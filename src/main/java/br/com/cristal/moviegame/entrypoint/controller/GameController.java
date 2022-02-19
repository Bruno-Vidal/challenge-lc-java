package br.com.cristal.moviegame.entrypoint.controller;

import br.com.cristal.moviegame.business.entity.Game;
import br.com.cristal.moviegame.business.service.GameService;
import br.com.cristal.moviegame.business.service.RankingService;
import br.com.cristal.moviegame.config.rotes.GameRoteMapping;
import br.com.cristal.moviegame.entrypoint.dto.request.ChoiceRoundRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.GameResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.RankingResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.ResultRoundResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.RoundResponse;
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
public class GameController {


    private final GameService gameService;
    private final RankingService rankingService;

    @GetMapping
    public ResponseEntity<List<GameResponse>> getAll() {
        return ResponseEntity.ok(gameService.getAll());
    }

    @PostMapping
    public ResponseEntity<GameResponse> create() {
        return ResponseEntity.ok(gameService.createGame());
    }

    @PostMapping("/quiz")
    public ResponseEntity<ResultRoundResponse> choice(
            @RequestBody ChoiceRoundRequest choiceRoundRequest
    ) {
        return ResponseEntity.ok(gameService.choiceToCurrentRound(choiceRoundRequest));
    }

    @GetMapping("/quiz")
    public ResponseEntity<RoundResponse> currentGame() {

        return ResponseEntity.ok(gameService.currentRoundResponse());
    }

    @PostMapping("/finish")
    public ResponseEntity<GameResponse> finish() {

        return ResponseEntity.ok(gameService.finishGame());
    }


    @GetMapping("/ranking")
    public ResponseEntity<List<RankingResponse>> getRanking()  {
        return ResponseEntity.ok(rankingService.getRanking());
    }





}
