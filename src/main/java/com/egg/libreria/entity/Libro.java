package com.egg.libreria.entity;


import com.egg.libreria.persistence.SoftDeletable;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name= "libros")
public class Libro implements SoftDeletable {
    @Id
    @Column(name = "isbn")
    @EqualsAndHashCode.Include
    private Long isbn;

    @Column(name = "titulo")
    @EqualsAndHashCode.Include
    private String titulo;

    @Column(name = "anio")
    @EqualsAndHashCode.Include
    private Integer anio;

    @Column(name = "ejemplares")
    private Integer ejemplares;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "id_editorial")
    private Editorial editorial;
}
