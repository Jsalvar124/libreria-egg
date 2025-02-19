package com.egg.libreria.entity;

import com.egg.libreria.persistence.SoftDeletable;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name= "editoriales")
public class Editorial implements SoftDeletable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_editorial")
    @EqualsAndHashCode.Include
    private int idEditorial;

    @Column(name = "nombre")
    @EqualsAndHashCode.Include
    private String nombre;

    @Column(name = "activo")
    @EqualsAndHashCode.Include
    private Boolean activo;

    public Editorial(String nombre, Boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }

}
