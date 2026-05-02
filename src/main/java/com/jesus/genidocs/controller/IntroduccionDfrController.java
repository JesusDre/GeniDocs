package com.jesus.genidocs.controller;

import com.jesus.genidocs.dto.request.IntroduccionDfrCreateRequest;
import com.jesus.genidocs.dto.response.IntroduccionDfrResponse;
import com.jesus.genidocs.mapper.IntroduccionDfrMapper;
import com.jesus.genidocs.service.IntroduccionDfrService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/introducciones")
public class IntroduccionDfrController {

    private final IntroduccionDfrService introduccionDfrService;
    private final IntroduccionDfrMapper introduccionDfrMapper;

    public IntroduccionDfrController(IntroduccionDfrService introduccionDfrService,
                                     IntroduccionDfrMapper introduccionDfrMapper) {
        this.introduccionDfrService = introduccionDfrService;
        this.introduccionDfrMapper = introduccionDfrMapper;
    }

    @PostMapping
    public ResponseEntity<IntroduccionDfrResponse> crear(@Valid @RequestBody IntroduccionDfrCreateRequest request,
                                                         @RequestParam Long usuarioId) {
        var introduccion = introduccionDfrService.guardarPorDocumento(
                request.getDocumentoId(),
                request.getProposito(),
                request.getAlcanceIncluye(),
                request.getAlcanceExcluye(),
                request.getDefiniciones(),
                usuarioId
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(introduccionDfrMapper.toResponse(introduccion));
    }

    @GetMapping
    public ResponseEntity<List<IntroduccionDfrResponse>> listarPorVersion(@RequestParam Long versionId) {
        List<IntroduccionDfrResponse> response = introduccionDfrService.listarPorVersion(versionId).stream()
                .map(introduccionDfrMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/documentos/{documentoId}")
    public ResponseEntity<IntroduccionDfrResponse> guardarPorDocumento(@PathVariable Long documentoId,
                                                                       @RequestParam Long usuarioId,
                                                                       @Valid @RequestBody IntroduccionDfrCreateRequest request) {
        var introduccion = introduccionDfrService.guardarPorDocumento(
                documentoId,
                request.getProposito(),
                request.getAlcanceIncluye(),
                request.getAlcanceExcluye(),
                request.getDefiniciones(),
                usuarioId
        );
        return ResponseEntity.ok(introduccionDfrMapper.toResponse(introduccion));
    }
}
