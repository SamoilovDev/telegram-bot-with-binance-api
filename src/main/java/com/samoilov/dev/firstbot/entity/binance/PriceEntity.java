package com.samoilov.dev.firstbot.entity.binance;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PriceId.class)
@Builder(toBuilder = true)
@Table(name = "price_table")
public class PriceEntity {

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "code_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "code_key"))
    private CodeEntity code;

    @Id
    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "value", precision = 20, scale = 4)
    private BigInteger value;

}
