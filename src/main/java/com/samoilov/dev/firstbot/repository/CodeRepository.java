package com.samoilov.dev.firstbot.repository;


import com.samoilov.dev.firstbot.entity.binance.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, String> {

    @Query("SELECT c FROM CodeEntity c WHERE c.name = :name")
    Optional<CodeEntity> getCodeEntityByName(@Param("name") String name);

}
