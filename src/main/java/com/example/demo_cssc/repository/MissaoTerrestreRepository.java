package com.example.demo_cssc.repository;


import com.example.demo_cssc.model.MissaoTerrestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MissaoTerrestreRepository extends JpaRepository<MissaoTerrestre, Long> {

    List<MissaoTerrestre> findAll();

    Optional<MissaoTerrestre> findById(Long id);

    @Query("SELECT m FROM MissaoTerrestre m JOIN m.soldados s WHERE s.id = :soldadoId")
    List<MissaoTerrestre> findBySoldadoId(Long soldadoId);
}