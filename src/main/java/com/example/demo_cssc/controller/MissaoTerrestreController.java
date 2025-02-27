package com.example.demo_cssc.controller;

import com.example.demo_cssc.dto.MissaoTerrestreDTO;
import com.example.demo_cssc.service.MissaoTerrestreService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissaoTerrestreController {
    private static final Logger logger = LoggerFactory.getLogger(MissaoTerrestreController.class);
    private final MissaoTerrestreService missaoService;

    public MissaoTerrestreController(MissaoTerrestreService missaoService) {
        this.missaoService = missaoService;
    }

    @GetMapping
    public ResponseEntity<List<MissaoTerrestreDTO>> listarMissoes() {
        logger.info("Buscando todas as missões...");
        return ResponseEntity.ok(missaoService.listarMissoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoTerrestreDTO> buscarPorId(@PathVariable Long id) {
        logger.info("Buscando missão ID {}", id);
        return ResponseEntity.ok(missaoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<MissaoTerrestreDTO> criarMissao(@Valid @RequestBody MissaoTerrestreDTO missaoDTO) {
        logger.info("Criando nova missão: {}", missaoDTO.getNomeMissao());
        return ResponseEntity.ok(missaoService.criarMissao(missaoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MissaoTerrestreDTO> atualizarMissao(@PathVariable Long id, @Valid @RequestBody MissaoTerrestreDTO missaoDTO) {
        logger.info(" Atualizando missão ID {}", id);
        return ResponseEntity.ok(missaoService.atualizarMissao(id, missaoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMissao(@PathVariable Long id) {
        logger.warn("Deletando missão ID {}", id);
        missaoService.deletarMissao(id);
        return ResponseEntity.noContent().build();
    }

}
