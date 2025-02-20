package com.egg.libreria.persistence;

import com.egg.libreria.entity.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LibroDAO extends DAO<Libro, Long> {

    public LibroDAO() {
        super(Libro.class);
    }

    public List<Libro> findByAttribute(String column, String value){
        EntityManager em = getEntityManagerFactory().createEntityManager();

        Set<String> allowedColumns = new HashSet<>(); //lista de valores permitidos para evitar inyección SQL.
        allowedColumns.add("titulo");
        allowedColumns.add("autor.nombre");
        allowedColumns.add("editorial.nombre");

        if (!allowedColumns.contains(column)) {
            throw new IllegalArgumentException("Invalid column name: " + column);
        }
        try{
            String queryString = "SELECT a FROM Libro a WHERE LOWER(a." + column + ") LIKE :value";

            return em.createQuery(queryString, Libro.class)
                    .setParameter("value","%"+value.toLowerCase() +"%")
                    .getResultList();
        }catch (NoResultException e) {
            System.out.println("No results found");
            return new ArrayList<>(); // Instead of throwing an exception, return empty array.
        } catch (Exception e){
            System.out.println("Error en la consulta " + e.getMessage());
            return new ArrayList<>();
        }finally {
            em.close();
        }
    };
}
