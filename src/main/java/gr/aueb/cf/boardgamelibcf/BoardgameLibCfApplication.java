package gr.aueb.cf.boardgamelibcf;

import gr.aueb.cf.boardgamelibcf.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class BoardgameLibCfApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardgameLibCfApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
