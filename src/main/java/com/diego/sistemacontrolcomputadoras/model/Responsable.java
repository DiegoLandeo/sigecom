package com.diego.sistemacontrolcomputadoras.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "responsables")
public class Responsable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del responsable es obligatorio")
    private String nombre;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "La edad debe ser mayor o igual a 18")
    private Integer edad;

    @NotBlank(message = "El nivel profesional es obligatorio")
    private String nivelProfesional;

    public Responsable() {
    }

    public Responsable(Long id, String nombre, Integer edad, String nivelProfesional) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.nivelProfesional = nivelProfesional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getNivelProfesional() {
        return nivelProfesional;
    }

    public void setNivelProfesional(String nivelProfesional) {
        this.nivelProfesional = nivelProfesional;
    }
}
