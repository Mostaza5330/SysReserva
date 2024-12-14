package pruebaDAO;


import DAO.RestauranteDAO;
import Entidades.Restaurante;
import Excepciones.DAOException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para RestauranteDAO.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestauranteDAOTest {

    private RestauranteDAO restauranteDAO;

    @BeforeAll
    public void setUpClass() {
        // Inicializar el DAO antes de todas las pruebas
        restauranteDAO = new RestauranteDAO();
    }

    @Test
    public void testConsultarRestauranteExitoso() {
        try {
            Restaurante restaurante = restauranteDAO.consultar();
            assertNotNull(restaurante, "El restaurante no debería ser null");
            System.out.println("Restaurante obtenido: " + restaurante);
        } catch (DAOException e) {
            fail("Error al consultar el restaurante: " + e.getMessage());
        }
    }

    @Test
    public void testActualizarRestauranteExitoso() {
        try {
            // Obtener el restaurante existente
            Restaurante restaurante = restauranteDAO.consultar();
            assertNotNull(restaurante, "El restaurante no debería ser null para actualizarlo");

            // Modificar un campo del restaurante (por ejemplo, el nombre)
            String nuevoNombre = "Nuevo Restaurante Prueba";
            restaurante.setNombre(nuevoNombre);

            // Actualizar el restaurante
            restauranteDAO.actualizar(restaurante);

            // Consultar nuevamente para verificar el cambio
            Restaurante actualizado = restauranteDAO.consultar();
            assertEquals(nuevoNombre, actualizado.getNombre(), "El nombre del restaurante no se actualizó correctamente");
        } catch (DAOException e) {
            fail("Error al actualizar el restaurante: " + e.getMessage());
        }
    }
}
