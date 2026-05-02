package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.IntroduccionDfr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntroduccionDfrRepository extends JpaRepository<IntroduccionDfr, Long> {

    List<IntroduccionDfr> findByVersionId(Long versionId);

    java.util.Optional<IntroduccionDfr> findTopByVersionDocumentoIdOrderByIdDesc(Long documentoId);
}
