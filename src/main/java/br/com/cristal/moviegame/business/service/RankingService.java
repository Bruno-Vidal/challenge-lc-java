package br.com.cristal.moviegame.business.service;

import br.com.cristal.moviegame.business.entity.Game;
import br.com.cristal.moviegame.business.mapper.GameMapper;
import br.com.cristal.moviegame.business.repository.GameRepository;
import br.com.cristal.moviegame.entrypoint.dto.response.RankingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public List<RankingResponse> getRanking() {

        List<RankingResponse> ranking = new ArrayList<>();

        List<Game> games = gameRepository.getToRanking();

        for (int i = 0; i < games.size(); i++) {
            RankingResponse rank = gameMapper.toRanking(games.get(i), i+1);
            ranking.add(rank);
        }

        return ranking;
    }


}
