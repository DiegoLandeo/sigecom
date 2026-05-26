package com.diego.sistemacontrolcomputadoras.repository;

import com.diego.sistemacontrolcomputadoras.model.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsableRepository extends JpaRepository<Responsable, Long> {
}
