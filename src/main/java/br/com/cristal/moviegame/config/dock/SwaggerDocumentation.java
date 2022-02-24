package br.com.cristal.moviegame.config.dock;

import br.com.cristal.moviegame.MoviegameApplication;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableSwagger2
@Log4j2
public class SwaggerDocumentation {

    @Value("${swagger.path}")
    private String swaggerPath;

    @Value("${swagger.apiinfo.title}")
    private String title;

    @Value("${swagger.apiinfo.description}")
    private String description;

    @Value("${swagger.apiinfo.termsOfServiceUrl}")
    private String termsOfService;

    @Value("${swagger.apiinfo.license}")
    private String license;

    @Value("${swagger.apiinfo.licenseUrl}")
    private String licenseUrl;

    @Value("${swagger.apiinfo.version}")
    private String version;

    @Bean
    public Docket allApi(){
        //Adding Header
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());

        Set<String> protocols = new HashSet<>();
        protocols.add("http");

        return new Docket(DocumentationType.SWAGGER_2).host(swaggerPath)
                .groupName("All")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.cristal.moviegame.entrypoint.controller"))
                .build()
                .protocols(protocols)
                .ignoredParameterTypes(ApiIgnore.class)
                .enableUrlTemplating(true).globalOperationParameters(aParameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfService)
                .license(license)
                .licenseUrl(licenseUrl)
                .version(version)
                .build();
    }
}
