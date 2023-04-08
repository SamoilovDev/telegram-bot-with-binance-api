package com.samoilov.dev.firstbot.entity.telegram;

import com.samoilov.dev.firstbot.entity.AbstractEntity;
import com.samoilov.dev.firstbot.enums.ActiveType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user_table SET active_type = 'DISABLED' WHERE id = ?")
@Entity
@Table(name = "user_table", uniqueConstraints = {
        @UniqueConstraint(columnNames = "telegram_id", name = "user_table_unique_telegram_id_idx")
})
public class UserEntity extends AbstractEntity {

    @Column(name = "telegram_id", length = 50)
    private Long telegramId;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "active_type")
    private ActiveType activeType = ActiveType.ENABLED;

    @Builder.Default
    @Column(name = "command_counter")
    private Long commandCounter = 0L;

}
