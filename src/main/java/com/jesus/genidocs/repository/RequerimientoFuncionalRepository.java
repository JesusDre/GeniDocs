package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.RequerimientoFuncional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequerimientoFuncionalRepository extends JpaRepository<RequerimientoFuncional, Long> {

    List<RequerimientoFuncional> findByProyectoId(Long proyectoId);

    Optional<RequerimientoFuncional> findTopByProyectoIdOrderByCodigoDesc(Long proyectoId);

    @Query("select count(r) from RequerimientoFuncional r where r.proyecto.id = ?1")
    long countByProyectoId(Long proyectoId);
}
