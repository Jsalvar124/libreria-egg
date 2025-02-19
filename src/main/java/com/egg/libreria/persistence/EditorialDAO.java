package com.egg.libreria.persistence;
import com.egg.libreria.entity.Autor;
import com.egg.libreria.entity.Editorial;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class EditorialDAO extends DAO<Editorial, Integer>{

    public EditorialDAO() {
        super(Editorial.class);
    }


    public Editorial findByName(String name){
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try{
            return em.createQuery("SELECT a FROM Editorial a WHERE a.nombre like :name", Editorial.class)
                    .setParameter("name","%"+name.toLowerCase() +"%")
                    .getSingleResult();
        }catch (NoResultException e) {
            return null; // Instead of throwing an exception, return null
        }catch (Exception e){
            throw e;
        }finally {
            em.close();
        }
    };
}
