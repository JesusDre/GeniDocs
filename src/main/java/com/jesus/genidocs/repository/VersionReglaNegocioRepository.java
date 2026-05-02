package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.VersionReglaNegocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionReglaNegocioRepository extends JpaRepository<VersionReglaNegocio, Long> {

    List<VersionReglaNegocio> findByVersionId(Long versionId);
}
