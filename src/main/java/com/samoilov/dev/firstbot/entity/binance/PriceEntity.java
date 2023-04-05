package com.samoilov.dev.firstbot.entity.binance;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@IdClass(PriceId.class)
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "price_table")
public class PriceEntity {

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "code_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "code_key"))
    private CodeEntity code;

    @Id
    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "value", precision = 10, scale = 7)
    private BigInteger value;

}
