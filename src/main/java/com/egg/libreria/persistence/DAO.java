package com.egg.libreria.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public abstract class DAO<T extends SoftDeletable, K> implements IDAO<T , K>{
    private final Class<T> entityClass;
    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(System.getenv("PERSISTENCE_UNIT"));
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Error initializing EntityManagerFactory: " + e);
        }

    }


    public DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    @Override
    public void save(T entity) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public T findById(K id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(entityClass, id);
        }catch (Exception e){
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("Finding all for class: " + entityClass.getSimpleName());
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
        }catch (Exception e){
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public T update(T entity) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            T mergedEntity = em.merge(entity);
            em.getTransaction().commit();
            System.out.println("Entity Merged!");
            return mergedEntity;
        }catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public void delete(K id) {
        EntityManager em = emf.createEntityManager();

        try{
            T entityToRemove = em.find(entityClass, id);
            if (entityToRemove == null || !entityToRemove.getActivo()) {
                throw new IllegalArgumentException("Entity with id " + id + " not found.");
            }
            em.getTransaction().begin();
            em.remove(entityToRemove);
            System.out.println("Entity Removed!");
            em.getTransaction().commit();
        }catch (Exception e){
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public void softDelete(K id) {
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            T entityToRemove = em.find(entityClass, id);
            if (entityToRemove == null || !entityToRemove.getActivo()) {
                throw new IllegalArgumentException("Entity with id " + id + " not found.");
            }
            entityToRemove.setActivo(false); // esto me parece más claro, como obligo al tipo T a implementar la interfaz soft deletable, aseguro que el método exista.
            em.merge(entityToRemove);
            System.out.println("Entity Softly Removed!");
            em.getTransaction().commit();
        }catch (Exception e ){
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw e;
        }finally {
            em.close();
        }
    }

}
