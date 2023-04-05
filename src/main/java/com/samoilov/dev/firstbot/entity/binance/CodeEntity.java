package com.samoilov.dev.firstbot.entity.binance;

import com.samoilov.dev.firstbot.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "code_table", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "unique_name_idx")
})
public class CodeEntity extends AbstractEntity {

    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(mappedBy = "code", fetch = FetchType.LAZY)
    private List<PriceEntity> prices;

}
