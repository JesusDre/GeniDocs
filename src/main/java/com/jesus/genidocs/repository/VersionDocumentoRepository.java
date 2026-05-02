package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.VersionDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VersionDocumentoRepository extends JpaRepository<VersionDocumento, Long> {

    List<VersionDocumento> findByDocumentoId(Long documentoId);

    List<VersionDocumento> findByDocumentoIdOrderByIdDesc(Long documentoId);

    java.util.Optional<VersionDocumento> findTopByDocumentoIdOrderByIdDesc(Long documentoId);

    @Query("select v from VersionDocumento v join fetch v.documento d join fetch d.proyecto p join fetch p.cliente join fetch p.responsable where v.id = ?1")
    Optional<VersionDocumento> findByIdWithDocumento(Long id);
}
