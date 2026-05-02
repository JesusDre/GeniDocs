package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.VersionRequerimientoFuncional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionRequerimientoFuncionalRepository extends JpaRepository<VersionRequerimientoFuncional, Long> {

    List<VersionRequerimientoFuncional> findByVersionId(Long versionId);
}
