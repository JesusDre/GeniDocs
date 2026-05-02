package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.ArchivoGenerado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchivoGeneradoRepository extends JpaRepository<ArchivoGenerado, Long> {

    List<ArchivoGenerado> findByVersionId(Long versionId);
}
