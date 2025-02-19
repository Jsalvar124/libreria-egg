package com.egg.libreria.service;

import com.egg.libreria.entity.Autor;
import com.egg.libreria.entity.Editorial;
import com.egg.libreria.entity.Libro;
import com.egg.libreria.persistence.AutorDAO;
import com.egg.libreria.persistence.EditorialDAO;
import com.egg.libreria.persistence.LibroDAO;

import java.util.ArrayList;
import java.util.List;

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
            Autor autor = autorDAO.findById(idAutor);
            if (autor == null) {
                throw new IllegalArgumentException("El autor con ID " + idAutor + " no existe.");
            }
            Editorial editorial = editorialDAO.findById(idEditorial);
            if (editorial == null) {
                throw new IllegalArgumentException("La editorial con ID " + idEditorial + " no existe.");
            }
            Libro libro = libroDAO.findById(isbn);
            if(libro != null && libro.getActivo()){
                throw new IllegalArgumentException("El libro ya se encuentra registrado.");
            }
            if(libro != null){
                System.out.println("Restaurando libro eliminado");
                libro.setActivo(true);
                libroDAO.update(libro);
            } else {
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
                libroDAO.save(nuevoLibro);
            }
        } catch(Exception e){
            System.out.println("Error al guardar el libro: " + e.getMessage());
        }
    }

    public Libro buscarPorIsbn(Long isbn) throws Exception{
        try{
            Libro libro = libroDAO.findById(isbn);
            if(libro== null || !libro.getActivo()){
                System.out.println("Libro no encontrado con ISBN: " + isbn);
                return null;
            }
            System.out.println("Libro encontrado con ISBN" + isbn);
            return libro;
        } catch(Exception e){
            throw new RuntimeException("Error buscar el libro con ISBN " + isbn + ": "  + e.getMessage(), e);
        }
    }

    public void eliminarLibroPorIsbn(Long isbn) throws Exception{
        try{
            libroDAO.softDelete(isbn);
        }catch (Exception e){
            throw e;
        }
    }

    public List<Libro> listarLibros(){
        List<Libro> libros = libroDAO.findAll();
        List<Libro> activos = new ArrayList<>();
        for (Libro libro:libros) {
            if(libro.getActivo()){
                activos.add(libro);
            }
        }
        return activos;
    }
}
