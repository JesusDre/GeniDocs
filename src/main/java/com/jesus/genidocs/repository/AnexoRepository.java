package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.Anexo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnexoRepository extends JpaRepository<Anexo, Long> {

    List<Anexo> findByVersionId(Long versionId);
}
