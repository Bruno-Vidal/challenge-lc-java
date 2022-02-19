package br.com.cristal.moviegame.config.movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "movies.api")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OmdbApiProperties {

    private String basePath;
    private String apiKey;
    private List<String> search;
    private Integer limitPage;
    private String type;
}
