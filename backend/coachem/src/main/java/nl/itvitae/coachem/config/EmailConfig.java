package nl.itvitae.coachem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String username;

    @Bean
    public SimpleMailMessage simpleMailMessage() {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(this.username);
        return mail;
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
