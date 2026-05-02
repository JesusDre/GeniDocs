package com.jesus.genidocs.controller;

import com.jesus.genidocs.dto.request.ModeloDatosCreateRequest;
import com.jesus.genidocs.dto.response.ModeloDatosResponse;
import com.jesus.genidocs.mapper.ModeloDatosMapper;
import com.jesus.genidocs.service.ModeloDatosService;
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
@RequestMapping("/modelos-datos")
public class ModeloDatosController {

    private final ModeloDatosService modeloDatosService;
    private final ModeloDatosMapper modeloDatosMapper;

    public ModeloDatosController(ModeloDatosService modeloDatosService, ModeloDatosMapper modeloDatosMapper) {
        this.modeloDatosService = modeloDatosService;
        this.modeloDatosMapper = modeloDatosMapper;
    }

    @PostMapping
    public ResponseEntity<ModeloDatosResponse> crear(@Valid @RequestBody ModeloDatosCreateRequest request,
                                                     @RequestParam Long usuarioId) {
        var modelo = modeloDatosService.guardarPorDocumento(
                request.getDocumentoId(),
                request.getEntidades(),
                request.getRelaciones(),
                request.getDiagrama(),
                usuarioId
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(modeloDatosMapper.toResponse(modelo));
    }

    @GetMapping
    public ResponseEntity<List<ModeloDatosResponse>> listarPorVersion(@RequestParam Long versionId) {
        List<ModeloDatosResponse> response = modeloDatosService.listarPorVersion(versionId).stream()
                .map(modeloDatosMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/documentos/{documentoId}")
    public ResponseEntity<ModeloDatosResponse> guardarPorDocumento(@PathVariable Long documentoId,
                                                                   @RequestParam Long usuarioId,
                                                                   @Valid @RequestBody ModeloDatosCreateRequest request) {
        var modelo = modeloDatosService.guardarPorDocumento(
                documentoId,
                request.getEntidades(),
                request.getRelaciones(),
                request.getDiagrama(),
                usuarioId
        );
        return ResponseEntity.status(HttpStatus.OK).body(modeloDatosMapper.toResponse(modelo));
    }
}
