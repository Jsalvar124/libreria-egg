package com.egg.libreria.persistence;

import com.egg.libreria.entity.Autor;
import com.egg.libreria.entity.Editorial;
import com.egg.libreria.entity.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EditorialDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibreriaPU");
    private final EntityManager em = emf.createEntityManager();

    public void guardar(Editorial editorial) throws Exception {
        em.getTransaction().begin();
        em.persist(editorial);
        em.getTransaction().commit();
    }

    public Editorial buscarPorId(int id) throws Exception {
        return em.find(Editorial.class, id);
    }
}
