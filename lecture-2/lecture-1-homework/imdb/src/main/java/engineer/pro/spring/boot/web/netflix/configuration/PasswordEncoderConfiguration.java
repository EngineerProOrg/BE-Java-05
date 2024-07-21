package engineer.pro.spring.boot.web.netflix.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {
  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
