package com.samoilov.dev.firstbot.service.domain;

import com.samoilov.dev.firstbot.entity.binance.CodeEntity;
import com.samoilov.dev.firstbot.entity.binance.PriceEntity;
import com.samoilov.dev.firstbot.repository.PriceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class BinancePriceService {

    @Autowired
    private final PriceRepository priceRepository;

    public PriceEntity getLastPrice(CodeEntity codeEntity) {
        log.info("Get last price with {} code", codeEntity.getName());
        return priceRepository.findFirstByCodeOrderByTimeDesc(codeEntity)
                .orElseThrow(() -> {
                    log.error("Price with {} code not found", codeEntity.getName());
                    return new RuntimeException("Price not found");
                });
    }

    public void clear(LocalDateTime upTo) {
        log.info("Clear prices up to {}", upTo);
        priceRepository.deleteAllByTimeLessThan(upTo);
    }

    public void save(CodeEntity codeEntity, BigInteger priceVal, LocalDateTime time) {
        log.info("Save price with {} code", codeEntity.getName());
        priceRepository.save(
                PriceEntity.builder()
                        .code(codeEntity)
                        .time(time)
                        .value(priceVal)
                        .build()
        );
    }

}
