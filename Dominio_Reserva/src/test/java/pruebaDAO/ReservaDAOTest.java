/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebaDAO;

import DAO.ReservaDAO;
import Entidades.Cliente;
import Entidades.Mesa;
import Entidades.Reserva;
import Excepciones.DAOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void testAgregarReserva_Success() {
        Mesa mesa = new Mesa();
        mesa.setCodigoMesa("A1");

        Reserva reserva = new Reserva();
        reserva.setMesa(mesa);
        reserva.setFechaHoraReserva(LocalDateTime.now());

        assertDoesNotThrow(() -> reservaDAO.agregarReserva(reserva));
    }

    @Test
    void testAgregarReserva_MesaNoExistente() {
        Mesa mesa = new Mesa();
        mesa.setCodigoMesa("Inexistente");

        Reserva reserva = new Reserva();
        reserva.setMesa(mesa);
        reserva.setFechaHoraReserva(LocalDateTime.now());

        assertThrows(DAOException.class, () -> reservaDAO.agregarReserva(reserva));
    }

    @Test
    void testConsultarPorFecha_Success() {
        LocalDateTime inicio = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2024, 12, 31, 23, 59);

        assertDoesNotThrow(() -> {
            List<Reserva> reservas = reservaDAO.consultarPorFecha(inicio, fin);
            assertNotNull(reservas);
            assertTrue(reservas.size() >= 0);
        });
    }

    @Test
    void testVerificarPorDia_Disponible() {
        Mesa mesa = new Mesa();
        mesa.setCodigoMesa("A1");

        LocalDateTime dia = LocalDateTime.now();

        assertDoesNotThrow(() -> {
            boolean disponible = reservaDAO.verificarPorDia(mesa, dia);
            assertTrue(disponible);
        });
    }

    @Test
    void testVerificarPorDia_NoDisponible() {
        Mesa mesa = new Mesa();
        mesa.setCodigoMesa("A1");

        LocalDateTime dia = LocalDateTime.now();

        // Simular que ya existe una reserva en la base de datos
        Reserva reservaExistente = new Reserva();
        reservaExistente.setMesa(mesa);
        reservaExistente.setFechaHoraReserva(dia.minusHours(2));

        assertDoesNotThrow(() -> {
            reservaDAO.agregarReserva(reservaExistente);
            boolean disponible = reservaDAO.verificarPorDia(mesa, dia);
            assertFalse(disponible);
        });
    }

    @Test
    void testBuscarReservasPorFiltros() {
        assertDoesNotThrow(() -> {
            List<Reserva> reservas = reservaDAO.buscarReservasPorFiltros(
                    "Juan", null, LocalDate.now(), null, null, null, null);
            assertNotNull(reservas);
            assertTrue(reservas.size() >= 0);
        });
    }
}
