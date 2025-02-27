package com.example.demo_cssc.controller;

import com.example.demo_cssc.dto.MissaoTerrestreDTO;
import com.example.demo_cssc.service.MissaoTerrestreService;
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
@RequestMapping("/missoes")
public class MissaoTerrestreController {
    private static final Logger logger = LoggerFactory.getLogger(MissaoTerrestreController.class);
    private final MissaoTerrestreService missaoService;

    public MissaoTerrestreController(MissaoTerrestreService missaoService) {
        this.missaoService = missaoService;
    }

    @GetMapping
    public ResponseEntity<List<MissaoTerrestreDTO>> listarMissoes(HttpServletRequest request) {
        logger.info("[{}] [{}] Usuário: {} buscou todas as missões",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getRemoteAddr(),
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        return ResponseEntity.ok(missaoService.listarMissoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoTerrestreDTO> buscarPorId(@PathVariable Long id, HttpServletRequest request) {
        logger.info("[{}] [{}] Usuário: {} buscou a missão ID {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getRemoteAddr(),
                SecurityContextHolder.getContext().getAuthentication().getName(),
                id
        );
        return ResponseEntity.ok(missaoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<MissaoTerrestreDTO> criarMissao(@Valid @RequestBody MissaoTerrestreDTO missaoDTO, HttpServletRequest request) {
        logger.info("[{}] [{}] Usuário: {} criou uma nova missão: {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getRemoteAddr(),
                SecurityContextHolder.getContext().getAuthentication().getName(),
                missaoDTO.getNomeMissao()
        );
        return ResponseEntity.ok(missaoService.criarMissao(missaoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MissaoTerrestreDTO> atualizarMissao(@PathVariable Long id, @Valid @RequestBody MissaoTerrestreDTO missaoDTO, HttpServletRequest request) {
        logger.info("[{}] [{}] Usuário: {} atualizou a missão ID {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getRemoteAddr(),
                SecurityContextHolder.getContext().getAuthentication().getName(),
                id
        );
        return ResponseEntity.ok(missaoService.atualizarMissao(id, missaoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMissao(@PathVariable Long id, HttpServletRequest request) {
        logger.info("[{}] [{}] Usuário: {} deletou a missão ID {}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getRemoteAddr(),
                SecurityContextHolder.getContext().getAuthentication().getName(),
                id
        );
        missaoService.deletarMissao(id);
        return ResponseEntity.noContent().build();
    }
}


