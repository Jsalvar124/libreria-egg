package com.egg.libreria;

import com.egg.libreria.entity.Autor;
import com.egg.libreria.entity.Editorial;
import com.egg.libreria.persistence.AutorDAO;
import com.egg.libreria.persistence.EditorialDAO;
import com.egg.libreria.persistence.LibroDAO;
import com.egg.libreria.service.LibroService;

public class Main
{
    public static void main(String[] args) throws Exception {
        Autor autor = new Autor("Gabriel García Márquez", true);
        AutorDAO autorDAO = new AutorDAO();


        Editorial editorial = new Editorial("Planeta",true);
        EditorialDAO editorialDAO = new EditorialDAO();

        LibroDAO libroDAO = new LibroDAO();
        LibroService libroService = new LibroService(libroDAO, autorDAO, editorialDAO);


        try{
//            autorDAO.guardar(autor);
//            editorialDAO.guardar(editorial);
//            libroService.guardarLibro(
//                    9780307474728L,  // ISBN
//                    "Cien años de soledad",  // Title
//                    1967,  //Año
//                    1000,  // Numero de copias
//                    1,  // Author ID (Gabriel García Márquez)
//                    1  // Editorial ID (Planeta)
//            );
            libroService.guardarLibro(
                    9780307389732L,  // ISBN
                    "El amor en los tiempos del cólera",
                    1985,
                    750,
                    1,
                    1
            );
        }catch (Exception e){
            e.getMessage();
        }
    }

}
