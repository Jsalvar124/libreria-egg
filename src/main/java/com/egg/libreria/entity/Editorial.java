package com.egg.libreria.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "editoriales")
public class Editorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_editorial")
    private int idEditorial;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private boolean activo;

    public Editorial(String nombre, boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }

}
