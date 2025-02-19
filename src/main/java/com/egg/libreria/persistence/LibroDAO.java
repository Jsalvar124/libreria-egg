package com.egg.libreria.persistence;

import com.egg.libreria.entity.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class LibroDAO {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibreriaPU");
    private final EntityManager em = emf.createEntityManager();

    public void guardarLibro(Libro libro) throws Exception {
        em.getTransaction().begin();
        em.persist(libro);
        em.getTransaction().commit();
    }

    public Libro buscarLibroPorIsbn(int isbn) throws Exception {
        return em.find(Libro.class, isbn);
    }

    public void actualizarLibro(Libro libro) throws Exception {
        em.getTransaction().begin();
        em.merge(libro);
        em.getTransaction().commit();
    }

    public void eliminarLibroPorIsbn(int isbn) throws Exception {
        Libro libro = em.find(Libro.class, isbn);
        if (libro != null) {
            em.getTransaction().begin();
//            em.remove(libro);
            em.merge(libro);
            em.getTransaction().commit();
        }
    }

    public List<Libro> listar() throws Exception {
        return em.createQuery("SELECT l FROM Libros l", Libro.class)
                .getResultList();
    }

//    public List<Cliente> listarClientesPorNombre(String nombreABuscar) throws Exception {
//        return em.createQuery("SELECT c FROM Cliente c WHERE c.nombreContacto LIKE :nombre", Cliente.class)
//                .setParameter("nombre", "%" + nombreABuscar + "%")
//                .getResultList();
//    }
//
//    public List<Cliente> listarClientesPorCiudad(String nombreABuscar) throws Exception {
//        return em.createQuery("SELECT c FROM Cliente c WHERE c.ciudad LIKE :ciudad", Cliente.class)
//                .setParameter("ciudad", "%" + nombreABuscar + "%")
//                .getResultList();
//    }

}
