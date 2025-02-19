package com.egg.libreria.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class DAO<T, K> implements IDAO<T, K>{
    private final Class<T> entityClass;
    private EntityManagerFactory emf;

    {
        try {
            emf = Persistence.createEntityManagerFactory(System.getenv("PERSISTENCE_UNIT"));
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Error initializing EntityManagerFactory: " + e);
        }

    }


    public DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
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
            em.getTransaction().begin();
            if (entityToRemove != null) {
                em.remove(entityToRemove);
            }
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
    public void softDelete(K id) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            T entityToRemove = em.find(entityClass, id);
            if (entityToRemove != null) {
                entityToRemove.getClass().getMethod("setActivo", Boolean.class).invoke(entityToRemove, false);
                em.merge(entityToRemove);
            }
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
