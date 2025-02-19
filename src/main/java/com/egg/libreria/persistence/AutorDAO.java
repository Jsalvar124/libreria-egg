package com.egg.libreria.persistence;

import com.egg.libreria.entity.Autor;
import com.egg.libreria.entity.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AutorDAO {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibreriaPU");
    private final EntityManager em = emf.createEntityManager();

    public void guardar(Autor autor) throws Exception {
        em.getTransaction().begin();
        em.persist(autor);
        em.getTransaction().commit();
    }

    public Autor buscarPorId(int id) throws Exception {
        return em.find(Autor.class, id);
    }
}
