package Conexion;

import Excepciones.ConexionException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase responsable de gestionar la conexión con la base de datos utilizando JPA (Java Persistence API).
 * Proporciona métodos para obtener instancias de `EntityManagerFactory` y `EntityManager`,
 * y para cerrar la conexión de manera adecuada.
 * 
 * Esta clase encapsula la lógica necesaria para configurar y administrar la conexión
 * con el sistema de persistencia definido en el archivo de configuración de JPA.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class Conexion {
    
    
    private static EntityManagerFactory entityManagerFactory;

    /**
     * Obtiene la instancia de `EntityManagerFactory`.
     * Si no existe una instancia previamente creada, se inicializa utilizando
     * la unidad de persistencia definida en `persistence.xml`.
     * 
     * @return una instancia de `EntityManagerFactory` activa para gestionar las conexiones con la base de datos.
     * @throws ConexionException si ocurre algún problema al crear o acceder a la `EntityManagerFactory`.
     */
    public EntityManagerFactory getEntityManagerFactory() 
            throws ConexionException{
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.
                    createEntityManagerFactory("Persistencia");
        }
        return entityManagerFactory;
    }

    /**
     * Obtiene una instancia de `EntityManager` para realizar operaciones de persistencia en la base de datos.
     * Este método utiliza la `EntityManagerFactory` para crear la instancia.
     * 
     * @return una nueva instancia de `EntityManager` para interactuar con la base de datos.
     * @throws ConexionException si ocurre un problema al obtener el `EntityManager`.
     */
    public EntityManager getEntityManager() throws ConexionException{
        return getEntityManagerFactory().createEntityManager();
    }

    /**
     * Cierra la instancia de `EntityManagerFactory` si está abierta, liberando así los recursos asociados.
     * Este método debe ser invocado al finalizar la aplicación o cuando ya no se necesite la conexión a la base de datos.
     * 
     * @throws ConexionException si ocurre un problema al cerrar el `EntityManagerFactory`.
     */
    public static void closeEntityManagerFactory() throws ConexionException{
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
    
}
