package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.RequerimientoNoFuncional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequerimientoNoFuncionalRepository extends JpaRepository<RequerimientoNoFuncional, Long> {

    List<RequerimientoNoFuncional> findByProyectoId(Long proyectoId);

    Optional<RequerimientoNoFuncional> findTopByProyectoIdOrderByCodigoDesc(Long proyectoId);

    @Query("select count(r) from RequerimientoNoFuncional r where r.proyecto.id = ?1")
    long countByProyectoId(Long proyectoId);
}
