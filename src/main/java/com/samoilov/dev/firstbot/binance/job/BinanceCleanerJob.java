package com.samoilov.dev.firstbot.binance.job;

import com.samoilov.dev.firstbot.service.domain.BinancePriceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@EnableScheduling
@AllArgsConstructor
public class BinanceCleanerJob {

    private final BinancePriceService binancePriceService;

    @Scheduled(fixedRate = 60000)
    private void clearHourAfter() {
        log.info("Clearing hour after");
        binancePriceService.clear(LocalDateTime.now().minusHours(1));
    }

}
