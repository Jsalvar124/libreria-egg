package com.egg.libreria.persistence;

import com.egg.libreria.entity.Libro;

public class LibroDAO extends DAO<Libro, Long> {

    public LibroDAO() {
        super(Libro.class);
    }


}
