package com.samoilov.dev.firstbot.repository;

import com.samoilov.dev.firstbot.entity.binance.CodeEntity;
import com.samoilov.dev.firstbot.entity.binance.PriceEntity;
import com.samoilov.dev.firstbot.entity.binance.PriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, PriceId> {

    @Modifying
    @Query("DELETE FROM PriceEntity p WHERE p.time < :time")
    void deleteAllByTimeLessThan(@Param("time") LocalDateTime time);

    @Query("SELECT p FROM PriceEntity p WHERE p.code = :code ORDER BY p.time DESC LIMIT 1")
    Optional<PriceEntity> findFirstByCodeOrderByTimeDesc(@Param("code") CodeEntity code);

}
