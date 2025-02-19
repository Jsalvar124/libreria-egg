package com.egg.libreria.persistence;

import com.egg.libreria.entity.Autor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class AutorDAO extends DAO<Autor, Integer> {

    public AutorDAO() {
        super(Autor.class);
    }


    public Autor findByName(String name){
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try{
            return em.createQuery("SELECT a FROM Autor a WHERE a.nombre like :name", Autor.class)
            .setParameter("name","%"+name.toLowerCase() +"%")
            .getSingleResult();
        }catch (NoResultException e) {
            return null; // Instead of throwing an exception, return null
        } catch (Exception e){
            throw e;
        }finally {
            em.close();
        }
    };
}
