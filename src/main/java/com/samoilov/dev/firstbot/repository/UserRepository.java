package com.samoilov.dev.firstbot.repository;

import com.samoilov.dev.firstbot.entity.telegram.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query("SELECT u FROM UserEntity u WHERE u.telegramId = ?1")
    Optional<UserEntity> findByTelegramId(@Param("telegramId") Long telegramId);

    @Modifying
    @Query("DELETE FROM UserEntity u WHERE u.telegramId = ?1")
    int deleteByTelegramId(@Param("telegramId") Long telegramId);

}
