package com.jesus.genidocs.controller;

import com.jesus.genidocs.dto.request.ReglaNegocioCreateRequest;
import com.jesus.genidocs.dto.response.ReglaNegocioResponse;
import com.jesus.genidocs.entity.ReglaNegocio;
import com.jesus.genidocs.mapper.ReglaNegocioMapper;
import com.jesus.genidocs.service.ReglaNegocioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reglas-negocio")
public class ReglaNegocioController {

    private final ReglaNegocioService reglaNegocioService;
    private final ReglaNegocioMapper reglaNegocioMapper;

    public ReglaNegocioController(ReglaNegocioService reglaNegocioService,
                                  ReglaNegocioMapper reglaNegocioMapper) {
        this.reglaNegocioService = reglaNegocioService;
        this.reglaNegocioMapper = reglaNegocioMapper;
    }

    @PostMapping
    public ResponseEntity<ReglaNegocioResponse> crearRegla(@Valid @RequestBody ReglaNegocioCreateRequest request) {
        ReglaNegocio regla = reglaNegocioService.crearRegla(
                request.getProyectoId(),
                request.getCodigo(),
                request.getDescripcion(),
                request.getEstado()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(reglaNegocioMapper.toResponse(regla));
    }
}
