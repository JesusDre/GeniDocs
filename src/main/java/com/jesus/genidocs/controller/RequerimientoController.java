package com.jesus.genidocs.controller;

import com.jesus.genidocs.dto.request.RequerimientoCreateRequest;
import com.jesus.genidocs.dto.response.RequerimientoResponse;
import com.jesus.genidocs.entity.RequerimientoFuncional;
import com.jesus.genidocs.entity.RequerimientoNoFuncional;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.mapper.RequerimientoMapper;
import com.jesus.genidocs.service.RequerimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requerimientos")
@Tag(name = "Requerimientos", description = "API para gestionar requerimientos funcionales y no funcionales")
public class RequerimientoController {

    private final RequerimientoService requerimientoService;
    private final RequerimientoMapper requerimientoMapper;

    public RequerimientoController(RequerimientoService requerimientoService, RequerimientoMapper requerimientoMapper) {
        this.requerimientoService = requerimientoService;
        this.requerimientoMapper = requerimientoMapper;
    }

    @PostMapping
    @Operation(
        summary = "Crear nuevo requerimiento",
        description = "Crea un requerimiento funcional o no funcional. El código se genera automáticamente y es único por proyecto."
    )
    @ApiResponse(
        responseCode = "201",
        description = "Requerimiento creado exitosamente",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RequerimientoResponse.class),
            examples = {
                @ExampleObject(
                    name = "Requerimiento Funcional Creado",
                    value = """
                        {
                          "id": 1,
                          "proyectoId": 1,
                          "tipo": "FUNCIONAL",
                          "codigo": "RF-001",
                          "titulo": "Autenticación de usuarios",
                          "descripcion": "El sistema debe permitir que los usuarios se autentiquen usando correo electrónico y contraseña",
                          "tipoNoFuncional": null,
                          "prioridad": "ALTA",
                          "estado": "PENDIENTE"
                        }
                        """
                ),
                @ExampleObject(
                    name = "Requerimiento No Funcional Creado",
                    value = """
                        {
                          "id": 2,
                          "proyectoId": 1,
                          "tipo": "NO_FUNCIONAL",
                          "codigo": "RNF-001",
                          "titulo": null,
                          "descripcion": "Las contraseñas deben estar encriptadas con bcrypt",
                          "tipoNoFuncional": "SEGURIDAD",
                          "prioridad": "ALTA",
                          "estado": "PENDIENTE"
                        }
                        """
                )
            }
        )
    )
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    public ResponseEntity<RequerimientoResponse> crearRequerimiento(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del requerimiento a crear. El código se genera automáticamente.",
            required = true,
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "Requerimiento Funcional",
                        value = """
                            {
                              "proyectoId": 1,
                              "tipo": "FUNCIONAL",
                              "titulo": "Autenticación de usuarios",
                              "descripcion": "El sistema debe permitir autenticación con correo y contraseña",
                              "prioridad": "ALTA",
                              "estado": "PENDIENTE"
                            }
                            """
                    ),
                    @ExampleObject(
                        name = "Requerimiento No Funcional",
                        value = """
                            {
                              "proyectoId": 1,
                              "tipo": "NO_FUNCIONAL",
                              "tipoNoFuncional": "SEGURIDAD",
                              "descripcion": "Las contraseñas se encriptan con bcrypt",
                              "prioridad": "ALTA",
                              "estado": "PENDIENTE"
                            }
                            """
                    )
                }
            )
        )
        @Valid @RequestBody RequerimientoCreateRequest request) {
        
        String tipo = request.getTipo() == null ? "" : request.getTipo().trim().toUpperCase();
        if ("FUNCIONAL".equals(tipo)) {
            RequerimientoFuncional rf = requerimientoService.agregarRequerimientoFuncional(
                    request.getProyectoId(),
                    null,
                    request.getTitulo(),
                    request.getDescripcion(),
                    request.getPrioridad(),
                    request.getEstado()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(requerimientoMapper.toResponse(rf));
        }
        if ("NO_FUNCIONAL".equals(tipo) || "NOFUNCIONAL".equals(tipo)) {
            RequerimientoNoFuncional rnf = requerimientoService.agregarRequerimientoNoFuncional(
                    request.getProyectoId(),
                    request.getTipoNoFuncional(),
                    request.getDescripcion(),
                    request.getPrioridad(),
                    request.getEstado()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(requerimientoMapper.toResponse(rnf));
        }
        throw new BusinessValidationException("El tipo debe ser FUNCIONAL o NO_FUNCIONAL");
    }
}
