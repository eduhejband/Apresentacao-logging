package com.example.demo_cssc.controller;

import com.example.demo_cssc.dto.SoldadoAtivoDTO;
import com.example.demo_cssc.exception.RecursoNaoEncontradoException;
import com.example.demo_cssc.model.SoldadoAtivo;
import com.example.demo_cssc.repository.SoldadoAtivoRepository;
import com.example.demo_cssc.service.SoldadoAtivoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/soldados")
public class SoldadoAtivoController {
    private static final Logger logger = LoggerFactory.getLogger(SoldadoAtivoController.class);
    private final SoldadoAtivoService soldadoService;
    private final SoldadoAtivoRepository soldadoAtivoRepository;
    public SoldadoAtivoController(SoldadoAtivoService soldadoService, SoldadoAtivoRepository soldadoAtivoRepository) {
        this.soldadoService = soldadoService;
        this.soldadoAtivoRepository = soldadoAtivoRepository;
    }

    @GetMapping
    public ResponseEntity<List<SoldadoAtivoDTO>> listarSoldados() {
        logger.info("Buscando todos os soldados...");
        return ResponseEntity.ok(soldadoService.listarSoldados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoldadoAtivoDTO> buscarPorId(@PathVariable Long id) {
        logger.info("Buscando soldado ID {}", id);
        return ResponseEntity.ok(soldadoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<SoldadoAtivoDTO> criarSoldado(@Valid @RequestBody SoldadoAtivoDTO soldadoDTO) {
        logger.info("Criando novo soldado: {}", soldadoDTO.getNome());
        return ResponseEntity.ok(soldadoService.criarSoldado(soldadoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SoldadoAtivoDTO> atualizarSoldado(@PathVariable Long id, @Valid @RequestBody SoldadoAtivoDTO soldadoDTO) {
        logger.info("Atualizando soldado ID {}", id);
        return ResponseEntity.ok(soldadoService.atualizarSoldado(id, soldadoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSoldado(@PathVariable Long id) {
        logger.warn("Deletando soldado ID {}", id);
        soldadoService.deletarSoldado(id);
        return ResponseEntity.noContent().build();
    }


}
