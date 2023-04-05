package com.samoilov.dev.firstbot.entity.binance;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class PriceId implements Serializable {

    private Long code;

    private LocalDateTime time;

}
