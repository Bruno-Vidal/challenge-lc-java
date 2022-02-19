package br.com.cristal.moviegame.business.mapper;

import br.com.cristal.moviegame.business.client.dto.MovieOmdbBase;
import br.com.cristal.moviegame.business.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")
public abstract class MovieMapper  {

    @Mapping(target = "externalId",source = "imdbID")
    @Mapping(target = "linkPoster",source = "poster")
    @Mapping(target = "year",source = "year")
    public abstract Movie toEntity(MovieOmdbBase movieOmdbBase);

    public abstract void completeEntity(@MappingTarget Movie movie, MovieOmdbBase movieOmdbBase);

}
