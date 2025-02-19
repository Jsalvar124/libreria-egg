package com.egg.libreria.entity;


import com.egg.libreria.persistence.SoftDeletable;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name= "autores")
public class Autor implements SoftDeletable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    @EqualsAndHashCode.Include
    private Integer idAutor;

    @Column(name = "nombre")
    @EqualsAndHashCode.Include
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    public Autor(String nombre, Boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }
}
