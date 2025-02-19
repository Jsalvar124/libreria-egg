package com.egg.libreria.persistence;

import com.egg.libreria.entity.Libro;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class LibroDAO extends DAO<Libro, Long> {

    public LibroDAO() {
        super(Libro.class);
    }
}
