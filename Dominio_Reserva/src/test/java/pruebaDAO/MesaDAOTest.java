package pruebaDAO;

import DAO.MesaDAO;
import DAO.RestauranteDAO;
import Entidades.Mesa;
import Entidades.Restaurante;
import Excepciones.DAOException;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        mesaDAO = new MesaDAO();
        RestauranteDAO restauranteDAO = new RestauranteDAO();

        // Crear un restaurante de prueba y persistirlo
        restaurante = new Restaurante();
        restaurante.setNombre("Restaurante de Prueba");
        restaurante.setDireccion("Calle Falsa 123");
        restaurante.setTelefono("555-1234");
        restaurante.setHoraApertura(LocalTime.of(9, 0));
        restaurante.setHoraCierre(LocalTime.of(22, 0));

        try {
            restauranteDAO.agregar(restaurante);
        } catch (DAOException e) {
            fail("Error al inicializar el restaurante: " + e.getMessage());
        }
    }

    @Test
    void testConsultarMesas() {
        try {
            List<Mesa> mesas = mesaDAO.consultarMesas();
            assertNotNull(mesas, "La lista de mesas no debería ser nula");
            assertTrue(mesas.size() > 0, "Debería haber al menos una mesa registrada");
        } catch (DAOException e) {
            fail("Error inesperado al consultar mesas: " + e.getMessage());
        }
    }

    @Test
    void testCantidadMesasPorUbicacion() {
        try {
            int cantidadTerraza = mesaDAO.cantidadMesasPorUbicacion("TERRAZA");
            assertTrue(cantidadTerraza >= 0, "La cantidad de mesas en terraza debe ser positiva o cero");

            int cantidadVentana = mesaDAO.cantidadMesasPorUbicacion("VENTANA");
            assertTrue(cantidadVentana >= 0, "La cantidad de mesas en ventana debe ser positiva o cero");
        } catch (DAOException e) {
            fail("Error inesperado al contar mesas por ubicación: " + e.getMessage());
        }
    }

    @Test
    void testAgregarMesasConDuplicados() {
        try {
            // Preparar mesas para prueba
            List<Mesa> mesas = new ArrayList<>();

            Mesa mesa1 = new Mesa("TER-2-001", "PEQUEÑA", 1, 2, "TERRAZA", restaurante);
            Mesa mesa2 = new Mesa("TER-2-001", "PEQUEÑA", 1, 2, "TERRAZA", restaurante); // Duplicado
            Mesa mesa3 = new Mesa("TER-2-002", "PEQUEÑA", 1, 2, "TERRAZA", restaurante);

            mesas.add(mesa1);
            mesas.add(mesa2);
            mesas.add(mesa3);

            // Intentar agregar mesas
            mesaDAO.agregarMesas(mesas);

            // Consultar las mesas en la base de datos
            List<Mesa> mesasActuales = mesaDAO.consultarMesas();

            // Verificar que solo se insertaron las mesas no duplicadas
            assertTrue(mesasActuales.stream().anyMatch(m -> m.getCodigoMesa().equals("TER-2-001")), "La mesa con código TER-2-001 debería existir.");
            assertTrue(mesasActuales.stream().anyMatch(m -> m.getCodigoMesa().equals("TER-2-002")), "La mesa con código TER-2-002 debería existir.");
        } catch (DAOException e) {
            fail("Error inesperado al agregar mesas con duplicados: " + e.getMessage());
        }
    }

    @Test
    void testObtenerMesasPorTipo() {
        try {
            List<Mesa> mesasPequenas = mesaDAO.obtenerMesasPorTipo("PEQUEÑA");
            assertNotNull(mesasPequenas, "La lista de mesas pequeñas no debería ser nula");
            assertTrue(mesasPequenas.size() > 0, "Debería haber al menos una mesa pequeña");

            List<Mesa> mesasMedianas = mesaDAO.obtenerMesasPorTipo("MEDIANA");
            assertNotNull(mesasMedianas, "La lista de mesas medianas no debería ser nula");
            assertTrue(mesasMedianas.size() > 0, "Debería haber al menos una mesa mediana");
        } catch (DAOException e) {
            fail("Error inesperado al obtener mesas por tipo: " + e.getMessage());
        }
    }
}
