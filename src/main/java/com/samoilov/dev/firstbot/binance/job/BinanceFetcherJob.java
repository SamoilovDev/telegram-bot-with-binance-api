package com.samoilov.dev.firstbot.binance.job;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerPrice;
import com.samoilov.dev.firstbot.config.properties.BinanceProperties;
import com.samoilov.dev.firstbot.service.domain.BinanceCodeService;
import com.samoilov.dev.firstbot.service.domain.BinancePriceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
@EnableScheduling
public class BinanceFetcherJob {

    @Autowired
    private final BinanceCodeService binanceCodeService;

    @Autowired
    private final BinancePriceService binancePriceService;

    @Autowired
    private final BinanceApiRestClient binanceApiRestClient;

    @Autowired
    private final BinanceProperties binanceProperties;

    @Scheduled(fixedRate = 60000)
    private void call() {
        List<TickerPrice> tickerPrices = binanceApiRestClient.getAllPrices();
        tickerPrices.forEach(
            tickerPrice -> {
                BigDecimal price = BigDecimal.ZERO;
                try {
                    price = new BigDecimal(tickerPrice.getPrice());
                    binancePriceService.save(
                            binanceCodeService.get(tickerPrice.getSymbol()),
                            price.multiply(binanceProperties.getMultiplyValue()).toBigInteger(),
                            LocalDateTime.now()
                    );
                } catch (Exception e) {
                    log.error("Code: {}, Price: {}, Exception: {}",
                            tickerPrice.getSymbol(), tickerPrice.getPrice(), e.getMessage());
                }
            });
    }

}
