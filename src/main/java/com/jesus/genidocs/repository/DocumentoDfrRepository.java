package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.DocumentoDfr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import java.util.List;

public interface DocumentoDfrRepository extends JpaRepository<DocumentoDfr, Long> {

    List<DocumentoDfr> findByProyectoId(Long proyectoId);

    @Query("select d from DocumentoDfr d join fetch d.proyecto p join fetch p.cliente join fetch p.responsable where d.id = ?1")
    Optional<DocumentoDfr> findByIdWithProyecto(Long id);
}
