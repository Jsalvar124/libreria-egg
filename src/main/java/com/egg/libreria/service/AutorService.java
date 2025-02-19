package com.egg.libreria.service;

import com.egg.libreria.entity.Autor;
import com.egg.libreria.persistence.AutorDAO;

import java.util.List;

public class AutorService {

    private AutorDAO autorDAO;

    public AutorService(AutorDAO autorDAO) {
        this.autorDAO = autorDAO;
    }

    public void guardarAutor(String nombre) {
        nombre = nombre.trim();
        try {
            Autor autor = autorDAO.findByName(nombre);
            if (autor != null) {
                if (autor.getActivo()) {
                    throw new IllegalArgumentException("El autor ya está registrado");
                } else {
                    autor.setActivo(true);
                    autorDAO.update(autor);
                    System.out.println("Autor reactivado.");
                    return;
                }
            }
            Autor nuevoAutor = new Autor(
                    nombre,
                    true
            );

            autorDAO.save(nuevoAutor);
            System.out.println("Autor guardado con éxito.");

        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
