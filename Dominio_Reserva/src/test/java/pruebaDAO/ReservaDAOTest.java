/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebaDAO;

import DAO.ReservaDAO;
import Entidades.Cliente;
import Entidades.Mesa;
import Entidades.Reserva;
import Entidades.Restaurante;
import Excepciones.DAOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ReservaDAOTest {

    private ReservaDAO reservaDAO;

    @BeforeEach
    void setUp() {
        reservaDAO = new ReservaDAO();
    }

    @Test
    void testAgregarReserva() {
        // Crear instancias utilizando constructores disponibles
        Cliente cliente = new Cliente("Nombre Cliente", "1234567890");
        Mesa mesa = new Mesa("M001", "Tipo Mesa", 2, 4, "Ubicación", null);
        Restaurante restaurante = new Restaurante("Nombre Restaurante", "Dirección", "1234567890", LocalTime.now(), LocalTime.now().plusHours(8));

        // Crear la reserva con parámetros correctos
        Reserva reserva = new Reserva(
                LocalDateTime.now(), // fechaHoraReserva
                4, // numeroPersonas
                500.0, // costo
                "ACTIVA", // estado
                cliente, // Cliente
                mesa, // Mesa
                restaurante // Restaurante
        );

        // Validar que la reserva se creó correctamente
        assertNotNull(reserva);
        assertEquals("ACTIVA", reserva.getEstado());
        assertEquals(4, reserva.getNumeroPersonas());
        assertEquals(500.0, reserva.getCosto(), 0.01);
        assertNotNull(reserva.getCliente());
        assertNotNull(reserva.getMesa());
        assertNotNull(reserva.getRestaurante());
    }

    @Test
    void testConsultarPorFecha() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(7);
        LocalDateTime fin = LocalDateTime.now();
        try {
            List<Reserva> reservas = reservaDAO.consultarPorFecha(inicio, fin);
            assertNotNull(reservas, "La lista no debería ser nula");
            assertTrue(reservas.size() >= 0, "El tamaño de la lista debería ser mayor o igual a cero");
        } catch (DAOException e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }
}
