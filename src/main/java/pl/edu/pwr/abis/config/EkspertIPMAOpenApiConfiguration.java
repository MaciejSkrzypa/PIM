package pl.edu.pwr.abis.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EkspertIPMAOpenApiConfiguration {

    @Bean
    public GroupedOpenApi ekspertIPMAOpenApi() {
        return GroupedOpenApi.builder().group("ekspert-ipma").pathsToMatch("/EkspertIPMA", "/EkspertIPMA/**").build();
    }
}
