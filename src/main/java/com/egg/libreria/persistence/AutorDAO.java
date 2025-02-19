package com.egg.libreria.persistence;

import com.egg.libreria.entity.Autor;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AutorDAO extends DAO<Autor, Integer> {

    public AutorDAO() {
        super(Autor.class);
    }
}
