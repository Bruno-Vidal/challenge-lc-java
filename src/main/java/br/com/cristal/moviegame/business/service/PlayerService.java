package br.com.cristal.moviegame.business.service;

import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.business.mapper.PlayerMapper;
import br.com.cristal.moviegame.business.repository.PlayerRepository;
import br.com.cristal.moviegame.entrypoint.dto.request.PlayerRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.PlayerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerBaseService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerResponse save(PlayerRequest playerRequest) {

        Player player = playerMapper.toEntity(playerRequest);
        player = playerRepository.save(player);
        return playerMapper.toResponse(player);
    }

    public List<PlayerResponse> findAll() {
        return playerRepository
                .findAll()
                .stream()
                .map(playerMapper::toResponse)
                .collect(Collectors.toList());
    }

}
