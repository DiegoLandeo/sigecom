package com.diego.sistemacontrolcomputadoras.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "computadoras")
public class Computadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos técnicos
    @NotBlank(message = "El nombre del equipo es obligatorio")
    private String nombreEquipo;

    @NotBlank(message = "La dirección IP es obligatoria")
    @Column(unique = true)
    private String ip;

    private String mac;

    private String procesador;

    private String ram;

    private Integer discoDuro;

    private Integer discoLibre;

    private String sistemaOperativo;

    private String ultimaDeteccion;

    // Datos administrativos
    private String local;

    private Boolean ambienteDocente;

    private Boolean tieneInternet;

    private String horaInicioNavegacion;

    private String horaFinNavegacion;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Responsable responsable;

    public Computadora() {
        this.estado = "Activa";
    }

    public Computadora(Long id, String nombreEquipo, String ip, String mac, String procesador, String ram,
                       Integer discoDuro, Integer discoLibre, String sistemaOperativo, String ultimaDeteccion,
                       String local, Boolean ambienteDocente, Boolean tieneInternet,
                       String horaInicioNavegacion, String horaFinNavegacion, String estado,
                       Responsable responsable) {
        this.id = id;
        this.nombreEquipo = nombreEquipo;
        this.ip = ip;
        this.mac = mac;
        this.procesador = procesador;
        this.ram = ram;
        this.discoDuro = discoDuro;
        this.discoLibre = discoLibre;
        this.sistemaOperativo = sistemaOperativo;
        this.ultimaDeteccion = ultimaDeteccion;
        this.local = local;
        this.ambienteDocente = ambienteDocente;
        this.tieneInternet = tieneInternet;
        this.horaInicioNavegacion = horaInicioNavegacion;
        this.horaFinNavegacion = horaFinNavegacion;
        this.estado = estado;
        this.responsable = responsable;
    }

    public Long getId() {
        return id;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public Integer getDiscoDuro() {
        return discoDuro;
    }

    public void setDiscoDuro(Integer discoDuro) {
        this.discoDuro = discoDuro;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Boolean getAmbienteDocente() {
        return ambienteDocente;
    }

    public void setAmbienteDocente(Boolean ambienteDocente) {
        this.ambienteDocente = ambienteDocente;
    }

    public Boolean getTieneInternet() {
        return tieneInternet;
    }

    public void setTieneInternet(Boolean tieneInternet) {
        this.tieneInternet = tieneInternet;
    }

    public String getHoraInicioNavegacion() {
        return horaInicioNavegacion;
    }

    public void setHoraInicioNavegacion(String horaInicioNavegacion) {
        this.horaInicioNavegacion = horaInicioNavegacion;
    }

    public String getHoraFinNavegacion() {
        return horaFinNavegacion;
    }

    public void setHoraFinNavegacion(String horaFinNavegacion) {
        this.horaFinNavegacion = horaFinNavegacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public Integer getDiscoLibre() {
        return discoLibre;
    }

    public void setDiscoLibre(Integer discoLibre) {
        this.discoLibre = discoLibre;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getUltimaDeteccion() {
        return ultimaDeteccion;
    }

    public void setUltimaDeteccion(String ultimaDeteccion) {
        this.ultimaDeteccion = ultimaDeteccion;
    }
}