package com.jesus.genidocs.controller;

import com.jesus.genidocs.dto.request.AnexoCreateRequest;
import com.jesus.genidocs.dto.response.AnexoResponse;
import com.jesus.genidocs.mapper.AnexoMapper;
import com.jesus.genidocs.service.AnexoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/anexos")
public class AnexoController {

    private final AnexoService anexoService;
    private final AnexoMapper anexoMapper;

    public AnexoController(AnexoService anexoService, AnexoMapper anexoMapper) {
        this.anexoService = anexoService;
        this.anexoMapper = anexoMapper;
    }

    @PostMapping
    public ResponseEntity<AnexoResponse> crear(@Valid @RequestBody AnexoCreateRequest request) {
        var anexo = anexoService.crear(
                request.getVersionId(),
                request.getTipo(),
                request.getNombre(),
                request.getDescripcion(),
                request.getRutaArchivo()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(anexoMapper.toResponse(anexo));
    }

    @GetMapping
    public ResponseEntity<List<AnexoResponse>> listarPorVersion(@RequestParam Long versionId) {
        List<AnexoResponse> response = anexoService.listarPorVersion(versionId).stream()
                .map(anexoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
