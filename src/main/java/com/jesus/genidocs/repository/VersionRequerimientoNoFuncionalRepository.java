package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.VersionRequerimientoNoFuncional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionRequerimientoNoFuncionalRepository extends JpaRepository<VersionRequerimientoNoFuncional, Long> {

    List<VersionRequerimientoNoFuncional> findByVersionId(Long versionId);
}
