package com.samoilov.dev.firstbot;

import com.samoilov.dev.firstbot.config.properties.BinanceProperties;
import com.samoilov.dev.firstbot.config.properties.TelegramProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        TelegramProperties.class,
        BinanceProperties.class
})
public class FirstBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstBotApplication.class, args);
    }

}
