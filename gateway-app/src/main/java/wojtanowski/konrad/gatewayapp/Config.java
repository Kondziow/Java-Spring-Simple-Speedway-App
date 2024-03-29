package wojtanowski.konrad.gatewayapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder,
                                     @Value("${gateway.app.host}") String host,
                                     @Value("${club.app.url}") String clubUrl,
                                     @Value("${player.app.url}") String playerUrl
    ) {
        return builder.routes()
                .route("player-app", r -> r
                        .host(host)
                        .and()
                        .path("/api/v1/players")
                        .or()
                        .path("/api/v1/players/**")
                        .or()
                        .path("/api/v1/players/{playerId}")
                        .or()
                        .path("/api/v1/players/{playerId}/clubs")
                        .or()
                        .path("/api/v1/clubs/{clubId}/players")
                        .uri(playerUrl)
                )
                .route("club-app", r -> r
                        .host(host)
                        .and()
                        .path("/api/v1/clubs")
                        .or()
                        .path("/api/v1/clubs/**")
                        .or()
                        .path("/api/v1/clubs/{clubId}")
                        .uri(clubUrl)
                )
                .build();
    }
}
