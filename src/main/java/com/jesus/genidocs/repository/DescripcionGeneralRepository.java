package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.DescripcionGeneral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DescripcionGeneralRepository extends JpaRepository<DescripcionGeneral, Long> {

    List<DescripcionGeneral> findByVersionId(Long versionId);

    java.util.Optional<DescripcionGeneral> findTopByVersionDocumentoIdOrderByIdDesc(Long documentoId);
}
