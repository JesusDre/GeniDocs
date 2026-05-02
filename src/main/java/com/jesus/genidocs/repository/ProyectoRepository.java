package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    List<Proyecto> findByClienteId(Long clienteId);
}
