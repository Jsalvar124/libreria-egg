package com.egg.libreria.persistence;
import com.egg.libreria.entity.Editorial;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class EditorialDAO extends DAO<Editorial, Integer>{

    public EditorialDAO() {
        super(Editorial.class);
    }
}
