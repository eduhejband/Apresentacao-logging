package com.example.demo_cssc.repository;

import com.example.demo_cssc.model.SoldadoAtivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SoldadoAtivoRepository extends JpaRepository<SoldadoAtivo, Long> {

    List<SoldadoAtivo> findAll();

    Optional<SoldadoAtivo> findById(Long id);

    @Query("SELECT s FROM SoldadoAtivo s JOIN s.missoes m WHERE m.id = :missaoId")
    List<SoldadoAtivo> findByMissaoId(Long missaoId);
}
