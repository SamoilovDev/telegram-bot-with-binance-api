package com.samoilov.dev.firstbot.config;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.samoilov.dev.firstbot.config.properties.BinanceProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BinanceConfiguration {

    @Autowired
    private final BinanceProperties binanceProperties;

    @Bean
    public BinanceApiRestClient binanceApiRestClient() {
        return BinanceApiClientFactory.newInstance(binanceProperties.getApiKey(), binanceProperties.getSecretKey())
                .newRestClient();
    }

}
