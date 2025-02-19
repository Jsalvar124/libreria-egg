package com.egg.libreria.service;

import com.egg.libreria.entity.Autor;
import com.egg.libreria.entity.Editorial;
import com.egg.libreria.entity.Libro;
import com.egg.libreria.persistence.AutorDAO;
import com.egg.libreria.persistence.EditorialDAO;
import com.egg.libreria.persistence.LibroDAO;

public class LibroService {
    LibroDAO libroDAO;
    AutorDAO autorDAO;
    EditorialDAO editorialDAO;

    public LibroService(LibroDAO libroDAO, AutorDAO autorDAO, EditorialDAO editorialDAO) {
        this.libroDAO = new LibroDAO();
        this.autorDAO = new AutorDAO();
        this.editorialDAO = new EditorialDAO();
    }

    public void guardarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer idAutor, Integer idEditorial) throws Exception{

        try{
            Autor autor = autorDAO.buscarPorId(idAutor);
            Editorial editorial = editorialDAO.buscarPorId(idEditorial);

            Libro nuevoLibro = new Libro(
                    isbn,
                    titulo,
                    anio,
                    ejemplares,
                    true, // valor por default
                    autor, // Autor, no autorId
                    editorial // Editorial no Editorial Id
            );
            // Crear el libro
            libroDAO.guardarLibro(nuevoLibro);
        } catch(Exception e){
            throw new RuntimeException("Error al guardar el libro: " + e.getMessage(), e);
        }

    }

    public Libro buscarPorId(int id) throws Exception{
        try{
            return libroDAO.buscarLibroPorIsbn(id);
        } catch(Exception e){
            throw new RuntimeException("Error buscar el libro con id " + id + ": "  + e.getMessage(), e);

        }

    }
}
