package pruebaDAO;

import DAO.MesaDAO;
import Entidades.Mesa;
import Excepciones.DAOException;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para RestauranteDAO.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class MesaDAOTest {

    private MesaDAO mesaDAO;

    @BeforeEach
    void setUp() {
        mesaDAO = new MesaDAO();
    }

    @Test
    void testConsultarMesas() {
        try {
            List<Mesa> mesas = mesaDAO.consultarMesas();
            assertNotNull(mesas, "La lista de mesas no debería ser nula");
            assertTrue(mesas.size() > 0, "Debería haber al menos una mesa registrada");
        } catch (DAOException e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }

    @Test
    void testCantidadMesasPorUbicacion() {
        try {
            int cantidad = mesaDAO.cantidadMesasPorUbicacion("Terraza");
            assertTrue(cantidad >= 0, "La cantidad de mesas debería ser positiva o cero");
        } catch (DAOException e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }
}
