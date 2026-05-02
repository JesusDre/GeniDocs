package com.jesus.genidocs.controller;

import com.jesus.genidocs.dto.request.CriterioAceptacionDwtCreateRequest;
import com.jesus.genidocs.dto.response.CriterioAceptacionDwtResponse;
import com.jesus.genidocs.mapper.CriterioAceptacionDwtMapper;
import com.jesus.genidocs.service.CriterioAceptacionDwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/criterios-aceptacion")
public class CriterioAceptacionDwtController {

    private final CriterioAceptacionDwtService criterioAceptacionDwtService;
    private final CriterioAceptacionDwtMapper criterioAceptacionDwtMapper;

    public CriterioAceptacionDwtController(CriterioAceptacionDwtService criterioAceptacionDwtService,
                                           CriterioAceptacionDwtMapper criterioAceptacionDwtMapper) {
        this.criterioAceptacionDwtService = criterioAceptacionDwtService;
        this.criterioAceptacionDwtMapper = criterioAceptacionDwtMapper;
    }

    @PostMapping
    public ResponseEntity<CriterioAceptacionDwtResponse> crear(@Valid @RequestBody CriterioAceptacionDwtCreateRequest request) {
        var criterio = criterioAceptacionDwtService.crear(
                request.getRequerimientoFuncionalId(),
                request.getDado(),
                request.getCuando(),
                request.getEntonces(),
                request.getEstado()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(criterioAceptacionDwtMapper.toResponse(criterio));
    }

    @GetMapping
    public ResponseEntity<List<CriterioAceptacionDwtResponse>> listarPorRequerimiento(@RequestParam Long requerimientoFuncionalId) {
        List<CriterioAceptacionDwtResponse> response = criterioAceptacionDwtService.listarPorRequerimiento(requerimientoFuncionalId).stream()
                .map(criterioAceptacionDwtMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/requerimientos/{requerimientoFuncionalId}")
    public ResponseEntity<CriterioAceptacionDwtResponse> actualizarPorRequerimiento(
            @PathVariable Long requerimientoFuncionalId,
            @RequestParam Long documentoId,
            @RequestParam Long usuarioId,
            @Valid @RequestBody CriterioAceptacionDwtCreateRequest request) {
        var criterio = criterioAceptacionDwtService.guardarPorDocumento(
                documentoId,
                requerimientoFuncionalId,
                request.getDado(),
                request.getCuando(),
                request.getEntonces(),
                request.getEstado(),
                usuarioId
        );
        return ResponseEntity.status(HttpStatus.OK).body(criterioAceptacionDwtMapper.toResponse(criterio));
    }
}
