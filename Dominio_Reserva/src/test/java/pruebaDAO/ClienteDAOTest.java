package pruebaDAO;

import DAO.ClienteDAO;
import Entidades.Cliente;
import Excepciones.DAOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import javax.persistence.*;

/**
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ClienteDAOTest {

    private ClienteDAO clienteDAO;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("Persistencia"); // Cambiar por tu configuración
        clienteDAO = new ClienteDAO();
    }

    @AfterEach
    void tearDown() {
        emf.close();
    }

    @Test
    void testObtenerCliente() {
        try {
            Cliente cliente = clienteDAO.obtenerCliente(1L); // Cambiar ID según tus datos
            assertNotNull(cliente, "El cliente no debería ser nulo");
            assertEquals("Juan Pérez", cliente.getNombre(), "El nombre no coincide");
        } catch (DAOException e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }

    @Test
    void testObtenerClientes() {
        try {
            List<Cliente> clientes = clienteDAO.obtenerClientes();
            assertNotNull(clientes, "La lista no debería ser nula");
            assertTrue(clientes.size() > 0, "Debería haber al menos un cliente");
        } catch (DAOException e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }
}
