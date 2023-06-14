package com.samoilov.dev.firstbot.entity.binance;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Embeddable
@EqualsAndHashCode
public class PriceId implements Serializable {

    private String code;

    private LocalDateTime time;

}
