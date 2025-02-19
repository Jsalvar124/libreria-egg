package com.egg.libreria.service;

import com.egg.libreria.entity.Autor;
import com.egg.libreria.entity.Editorial;
import com.egg.libreria.persistence.EditorialDAO;

public class EditorialService {
    private EditorialDAO editorialDAO;

    public EditorialService(EditorialDAO editorialDAO) {
        this.editorialDAO = editorialDAO;
    }

    public void guardarEditorial(String nombre) {
        nombre = nombre.trim();
        try {
            Editorial editorial = editorialDAO.findByName(nombre);
            if (editorial != null) {
                if (editorial.getActivo()) {
                    throw new IllegalArgumentException("La Editorial ya está registrada");
                } else {
                    editorial.setActivo(true);
                    editorialDAO.update(editorial);
                    System.out.println("Editorial reactivada.");
                    return;
                }
            }
            Editorial nuevaEditorial = new Editorial(
                    nombre,
                    true
            );

            editorialDAO.save(nuevaEditorial);
            System.out.println("Editorial guardada con éxito.");

        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
