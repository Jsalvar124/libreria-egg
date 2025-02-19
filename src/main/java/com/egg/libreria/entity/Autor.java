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
@Table(name= "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private int idAutor;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private boolean activo;

    public Autor(String nombre, boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }
}
