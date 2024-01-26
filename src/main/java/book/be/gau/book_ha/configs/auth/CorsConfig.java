package book.be.gau.book_ha.configs.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class CorsConfig {

  @Bean
  public CorsConfiguration corsConfiguration() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowCredentials(true);
    corsConfig.addAllowedOriginPattern("*"); // hoặc cụ thể là "http://allowed-origin.com"
    corsConfig.addAllowedHeader("*");
    corsConfig.addAllowedMethod("*");
    return corsConfig;
  }
}
