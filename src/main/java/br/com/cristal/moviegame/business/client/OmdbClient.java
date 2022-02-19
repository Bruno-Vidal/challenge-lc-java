package br.com.cristal.moviegame.business.client;

import br.com.cristal.moviegame.business.client.dto.MovieOmdbBase;
import br.com.cristal.moviegame.business.client.dto.ResponseSearchOmdb;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "ombdclient",
        url = "${movies.api.base-path}"
)
public interface OmdbClient {

    @GetMapping()
    ResponseSearchOmdb findList(
            @RequestParam(value="apikey") String key,
            @RequestParam(value="s") String search,
            @RequestParam(value="page") Integer page,
            @RequestParam(value="type") String type

    );

    @GetMapping()
    MovieOmdbBase findById(
            @RequestParam(value="apikey") String key,
            @RequestParam(value="i") String id

    );
}
