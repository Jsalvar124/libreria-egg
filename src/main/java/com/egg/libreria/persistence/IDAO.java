package com.egg.libreria.persistence;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IDAO<T, K> {
        void save(T entity);

        T findById(K id);

        List<T> findAll();

        T update(T entity);

        void delete(K id);

        void softDelete(K id);

}
