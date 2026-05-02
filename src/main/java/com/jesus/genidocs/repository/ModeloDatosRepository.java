package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.ModeloDatos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeloDatosRepository extends JpaRepository<ModeloDatos, Long> {

    List<ModeloDatos> findByVersionId(Long versionId);

    java.util.Optional<ModeloDatos> findTopByVersionDocumentoIdOrderByIdDesc(Long documentoId);
}
