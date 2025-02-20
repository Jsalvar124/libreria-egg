package com.egg.libreria.service;

import com.egg.libreria.entity.Autor;
import com.egg.libreria.entity.Editorial;
import com.egg.libreria.entity.Libro;
import com.egg.libreria.persistence.AutorDAO;
import com.egg.libreria.persistence.EditorialDAO;
import com.egg.libreria.persistence.LibroDAO;
import jakarta.persistence.NoResultException;

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

    public List<Libro> buscarPorTitulo(String titulo) throws Exception{
        try {
            List<Libro> resultado = libroDAO.findByAttribute("titulo", titulo);
            if(resultado==null || resultado.isEmpty()){
                System.out.println("No se encontraron resultados para la busqueda "+ titulo);
                return new ArrayList<>();
            }
            System.out.println("Se encontraron "+ resultado.size() +" para la busqueda "+ titulo);
            return resultado;
        } catch (Exception e){
            throw new RuntimeException("Error buscar el libro con titulo " + titulo + ": "  + e.getMessage(), e);
        }
    }

    public List<Libro> buscarPorAutor(String autor) throws Exception{
        try {
            List<Libro> resultado = libroDAO.findByAttribute("autor.nombre", autor);
            if(resultado==null || resultado.isEmpty()){
                System.out.println("No se encontraron resultados para la busqueda "+ autor);
                return new ArrayList<>();
            }
             List<Libro> activos = filtrarActivos(resultado);

            System.out.println("Se encontraron "+ activos.size() +" para la busqueda "+ autor);
            return activos;
        } catch (Exception e){
            throw new RuntimeException("Error buscar el libro con autor " + autor + ": "  + e.getMessage(), e);
        }
    }

    public List<Libro> buscarPorEditorial(String editorial) throws Exception{
        try {
            List<Libro> resultado = libroDAO.findByAttribute("editorial.nombre", editorial);
            if(resultado==null || resultado.isEmpty()){
                System.out.println("No se encontraron resultados para la busqueda "+ editorial);
                return new ArrayList<>();
            }
            List<Libro> activos = filtrarActivos(resultado);

            System.out.println("Se encontraron "+ activos.size() +" para la busqueda "+ editorial);
            return activos;
        } catch (Exception e){
            throw new RuntimeException("Error buscar el libro con editorial " + editorial + ": "  + e.getMessage(), e);
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
        return filtrarActivos(libros);
    }

    public List<Libro> filtrarActivos(List<Libro> libros){
        List<Libro> activos = new ArrayList<>();
        for (Libro libro:libros) {
            if(libro.getActivo()){
                activos.add(libro);
            }
        }
        return activos;
    }
}
