package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.CriterioAceptacionDwt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CriterioAceptacionDwtRepository extends JpaRepository<CriterioAceptacionDwt, Long> {

    List<CriterioAceptacionDwt> findByRequerimientoFuncionalId(Long requerimientoFuncionalId);

    List<CriterioAceptacionDwt> findByRequerimientoFuncionalIdIn(Collection<Long> requerimientoFuncionalIds);
}
