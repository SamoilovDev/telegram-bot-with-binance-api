package com.samoilov.dev.firstbot.service.domain;

import com.samoilov.dev.firstbot.entity.binance.CodeEntity;
import com.samoilov.dev.firstbot.repository.CodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BinanceCodeService {

    @Autowired
    private final CodeRepository codeRepository;

    public CodeEntity save(String codeName) {
        return CodeEntity.builder()
                .name(codeName)
                .build();
    }

    public CodeEntity get(String codeName) {
        return codeRepository.getCodeEntityByName(codeName)
                .orElseGet(() -> this.save(codeName));
    }

}
