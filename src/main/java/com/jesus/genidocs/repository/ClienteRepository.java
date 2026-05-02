package com.jesus.genidocs.repository;

import com.jesus.genidocs.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
