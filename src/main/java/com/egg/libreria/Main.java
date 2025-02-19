package com.egg.libreria;

import com.egg.libreria.entity.Autor;
import com.egg.libreria.entity.Editorial;
import com.egg.libreria.entity.Libro;
import com.egg.libreria.persistence.AutorDAO;
import com.egg.libreria.persistence.EditorialDAO;
import com.egg.libreria.persistence.LibroDAO;
import com.egg.libreria.service.LibroService;

public class Main
{
    public static void main(String[] args) throws Exception {
        Autor autor = new Autor("Gabriel García Márquez", true);
        Autor autor1 = new Autor("Haruki Murakami", true);
        AutorDAO autorDAO = new AutorDAO();


        Editorial editorial = new Editorial("Planeta",true);
        EditorialDAO editorialDAO = new EditorialDAO();

        LibroDAO libroDAO = new LibroDAO();
        LibroService libroService = new LibroService(libroDAO, autorDAO, editorialDAO);


        try{
            autorDAO.save(autor);
            autorDAO.save(autor1);
            editorialDAO.save(editorial);
            libroService.guardarLibro(
                    9780307474728L,  // ISBN
                    "Cien años de soledad",  // Title
                    1967,  //Año
                    1000,  // Numero de copias
                    1,  // Author ID (Gabriel García Márquez)
                    1  // Editorial ID (Planeta)
            );
            libroService.guardarLibro(
                    9780307389732L,  // ISBN
                    "El amor en los tiempos del cólera",
                    1985,
                    750,
                    1,
                    1
            );
            libroService.guardarLibro(
                    9780241968567L,  // ISBN (unique long identifier)
                    "Crónica de una muerte anunciada",  // Title
                    1981,  // Year of publication
                    300,   // Number of copies
                    1,     // Author ID (Gabriel García Márquez)
                    1      // Editorial ID (Same as previous books)
            );
                        libroService.guardarLibro(
                    9786074214789L,  // ISBN (example, replace with actual if needed)
                    "Los años de peregrinación del chico sin color",  // Title
                    2013,  // Year of publication
                    250,   // Number of copies
                    2,     // Author ID (Haruki Murakami, replace with correct ID)
                    1      // Editorial ID (Same as previous books)
            );

            System.out.println(libroService.listarLibros());

            //before soft delete
            Libro resultado = libroService.buscarPorId(9780241968567L);
            System.out.println(resultado);

            //soft delete
            libroService.eliminarLibroPorIsbn(9780241968567L);

            //Soft Delete test
            Libro resultado2 = libroService.buscarPorId(9780241968567L);

            //Libros
            System.out.println(libroService.listarLibros());

        }catch (Exception e){
            e.getMessage();
        }
    }

}
