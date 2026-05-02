package com.jesus.genidocs.controller;

import com.jesus.genidocs.dto.request.ArchivoGeneradoCreateRequest;
import com.jesus.genidocs.dto.response.ArchivoGeneradoResponse;
import com.jesus.genidocs.mapper.ArchivoGeneradoMapper;
import com.jesus.genidocs.service.ArchivoGeneradoService;
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
@RequestMapping("/archivos-generados")
public class ArchivoGeneradoController {

    private final ArchivoGeneradoService archivoGeneradoService;
    private final ArchivoGeneradoMapper archivoGeneradoMapper;

    public ArchivoGeneradoController(ArchivoGeneradoService archivoGeneradoService,
                                     ArchivoGeneradoMapper archivoGeneradoMapper) {
        this.archivoGeneradoService = archivoGeneradoService;
        this.archivoGeneradoMapper = archivoGeneradoMapper;
    }

    @PostMapping
    public ResponseEntity<ArchivoGeneradoResponse> crear(@Valid @RequestBody ArchivoGeneradoCreateRequest request) {
        var archivo = archivoGeneradoService.registrar(
                request.getVersionId(),
                request.getNombreArchivo(),
                request.getRutaArchivo(),
                request.getTipo()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(archivoGeneradoMapper.toResponse(archivo));
    }

    @GetMapping
    public ResponseEntity<List<ArchivoGeneradoResponse>> listarPorVersion(@RequestParam Long versionId) {
        List<ArchivoGeneradoResponse> response = archivoGeneradoService.listarPorVersion(versionId).stream()
                .map(archivoGeneradoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
