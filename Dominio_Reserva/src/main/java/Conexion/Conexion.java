/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import Excepciones.ConexionException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase que incluye un solo metodo para establecer conexion con la base de 
 * datos
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class Conexion {
    
    
    private static EntityManagerFactory entityManagerFactory;

    /**
     * Método estático para obtener el EntityManagerFactory.
     * 
     * @return 
     * @throws Excepciones.ConexionException En caso de error en la conexion.
     */
    public EntityManagerFactory getEntityManagerFactory() 
            throws ConexionException{
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.
                    createEntityManagerFactory("com.mycompany_Reservas_Dominio_jar_1.0-SNAPSHOTPU");
        }
        return entityManagerFactory;
    }

    /**
     * Método para obtener el EntityManager
     * 
     * @return 
     * @throws Excepciones.ConexionException En caso de error en la conexion.
     */
    public EntityManager getEntityManager() throws ConexionException{
        return getEntityManagerFactory().createEntityManager();
    }

    /**
     * Método para cerrar el EntityManagerFactory.
     * 
     * @throws Excepciones.ConexionException En caso de error en la conexion.
     */
    public static void closeEntityManagerFactory() throws ConexionException{
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
    
}
