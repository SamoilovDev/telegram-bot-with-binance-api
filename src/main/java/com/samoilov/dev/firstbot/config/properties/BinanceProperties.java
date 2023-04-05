package com.samoilov.dev.firstbot.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "api.binance")
public class BinanceProperties {

    @Value("${api.binance.key}")
    private String apiKey;

    @Value("${api.binance.secret.key}")
    private String secretKey;

    @Value("${api.binance.multiply.value}")
    private BigDecimal multiplyValue;

    private Set<String> tickets = new LinkedHashSet<>();

}
