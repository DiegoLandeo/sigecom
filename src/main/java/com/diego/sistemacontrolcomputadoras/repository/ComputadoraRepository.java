package com.diego.sistemacontrolcomputadoras.repository;

import com.diego.sistemacontrolcomputadoras.model.Computadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComputadoraRepository extends JpaRepository<Computadora, Long> {

    Optional<Computadora> findByIp(String ip);

    void deleteByIp(String ip);

    List<Computadora> findByTieneInternet(Boolean tieneInternet);

    @Query("SELECT c.local, SUM(c.discoDuro) FROM Computadora c GROUP BY c.local")
    List<Object[]> obtenerAlmacenamientoPorLocal();

    long countByAmbienteDocente(Boolean ambienteDocente);

    long countByAmbienteDocenteAndTieneInternet(Boolean ambienteDocente, Boolean tieneInternet);

    @Query("SELECT c FROM Computadora c WHERE c.tieneInternet = true AND c.horaInicioNavegacion <= :hora AND c.horaFinNavegacion >= :hora")
    List<Computadora> buscarComputadorasPorHorario(String hora);

    List<Computadora> findByResponsableIdAndAmbienteDocenteAndTieneInternet(Long responsableId, Boolean ambienteDocente, Boolean tieneInternet);

    long countByTieneInternet(Boolean tieneInternet);

    long countByEstado(String estado);

    List<Computadora> findByEstado(String estado);

    List<Computadora> findByNombreEquipoContainingIgnoreCaseOrIpContainingIgnoreCase(
            String nombreEquipo,
            String ip
    );

    List<Computadora> findTop5ByOrderByIdDesc();

    List<Computadora> findByUltimaDeteccionLessThan(String fechaLimite);

    List<Computadora> findByUltimaDeteccionIsNull();

}
