package com.example.demo_cssc.controller;

import com.example.demo_cssc.dto.SoldadoAtivoDTO;
import com.example.demo_cssc.service.SoldadoAtivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/soldados")
@Tag(name = "Soldados", description = "Gerenciamento de soldados")
public class SoldadoAtivoController {
    private static final Logger logger = LoggerFactory.getLogger(SoldadoAtivoController.class);
    private final SoldadoAtivoService soldadoService;

    public SoldadoAtivoController(SoldadoAtivoService soldadoService) {
        this.soldadoService = soldadoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os soldados")
    public ResponseEntity<List<SoldadoAtivoDTO>> listarSoldados(HttpServletRequest request) {
        logger.info("[{}] [{}] Usuário: {} buscou todos os soldados",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getRemoteAddr(),
                SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(soldadoService.listarSoldados());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar soldado por ID")
    public ResponseEntity<SoldadoAtivoDTO> buscarPorId(@PathVariable Long id, HttpServletRequest request) {
        logger.info("[{}] [{}] Usuário: {} buscou o soldado ID {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getRemoteAddr(),
                SecurityContextHolder.getContext().getAuthentication().getName(),
                id);
        return ResponseEntity.ok(soldadoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar um novo soldado")
    public ResponseEntity<SoldadoAtivoDTO> criarSoldado(@Valid @RequestBody SoldadoAtivoDTO soldadoDTO, HttpServletRequest request) {
        logger.info("[{}] [{}] Usuário: {} criou um soldado: {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getRemoteAddr(),
                SecurityContextHolder.getContext().getAuthentication().getName(),
                soldadoDTO.getNome());
        return ResponseEntity.ok(soldadoService.criarSoldado(soldadoDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um soldado")
    public ResponseEntity<SoldadoAtivoDTO> atualizarSoldado(@PathVariable Long id, @Valid @RequestBody SoldadoAtivoDTO soldadoDTO, HttpServletRequest request) {
        logger.info("[{}] [{}] Usuário: {} atualizou o soldado ID {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getRemoteAddr(),
                SecurityContextHolder.getContext().getAuthentication().getName(),
                id);
        return ResponseEntity.ok(soldadoService.atualizarSoldado(id, soldadoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um soldado")
    public ResponseEntity<Void> deletarSoldado(@PathVariable Long id, HttpServletRequest request) {
        logger.info("[{}] [{}] Usuário: {} deletou o soldado ID {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getRemoteAddr(),
                SecurityContextHolder.getContext().getAuthentication().getName(),
                id);
        soldadoService.deletarSoldado(id);
        return ResponseEntity.noContent().build();
    }
}
