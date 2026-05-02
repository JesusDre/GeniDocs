package com.jesus.genidocs.controller;

import com.jesus.genidocs.dto.request.ProyectoCreateRequest;
import com.jesus.genidocs.dto.response.ProyectoResponse;
import com.jesus.genidocs.entity.Proyecto;
import com.jesus.genidocs.mapper.ProyectoMapper;
import com.jesus.genidocs.service.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;
    private final ProyectoMapper proyectoMapper;

    public ProyectoController(ProyectoService proyectoService, ProyectoMapper proyectoMapper) {
        this.proyectoService = proyectoService;
        this.proyectoMapper = proyectoMapper;
    }

    @PostMapping
    public ResponseEntity<ProyectoResponse> crearProyecto(@Valid @RequestBody ProyectoCreateRequest request) {
        Proyecto proyecto = proyectoService.crearProyecto(
                request.getNombre(),
                request.getDescripcion(),
                request.getClienteId(),
                request.getResponsableId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(proyectoMapper.toResponse(proyecto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponse> obtenerProyecto(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.obtenerProyecto(id);
        return ResponseEntity.ok(proyectoMapper.toResponse(proyecto));
    }
}
