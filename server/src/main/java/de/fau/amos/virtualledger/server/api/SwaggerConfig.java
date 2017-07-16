package de.fau.amos.virtualledger.server.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.fau.amos.virtualledger.server.api"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securitySchema()));
    }

    private OAuth securitySchema() {
        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorizationScopeList.add(new AuthorizationScope("multibanking-client", "Multibanking Client"));

        List<GrantType> grantTypes = new ArrayList<>();
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant("https://multibanking-keycloak.dev.adorsys.de/auth/realms/multibanking/protocol/openid-connect/token");
        grantTypes.add(grantType);

        return new OAuthBuilder()
                .name("oauth2")
                .grantTypes(grantTypes)
                .scopes(authorizationScopeList)
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration(
                "multibanking-client", "foo", "multibanking", "foo", "foo", ApiKeyVehicle.HEADER, "api_key", ",");
    }
}
