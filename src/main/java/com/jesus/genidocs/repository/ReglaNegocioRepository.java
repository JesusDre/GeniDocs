package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.ReglaNegocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReglaNegocioRepository extends JpaRepository<ReglaNegocio, Long> {

    List<ReglaNegocio> findByProyectoId(Long proyectoId);
}
