package BO;

import Convertidores.ClienteCVR;
import Convertidores.MesaCVR;
import Convertidores.ReservaCVR;
import DAO.ReservaDAO;
import DTOs.ClienteDTO;
import DTOs.MesaDTO;
import DTOs.ReservaDTO;
import Entidades.Cliente;
import Entidades.Mesa;
import Entidades.Reserva;
import Excepciones.BOException;
import Excepciones.ConversionException;
import Excepciones.DAOException;
import Interfaces.IReservaBO;
import Interfaces.IReservaDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase intermediaria entre la capa de DAO para la entidad Reserva que 
 * convierte los métodos de la clase DAO a DTO o a Entidad dependiendo del 
 * flujo. Sirve como puente para la lógica de negocio en la gestión de 
 * clientes, coordinando interacciones entre la capa de acceso a datos (DAO) 
 * y la de presentación.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ReservaBO implements IReservaBO {

    // Instancia de logger para realizar la depuración.
    private static final Logger LOG = Logger.getLogger(ReservaBO.class.getName());
    
    // Instancia de la interfaz de ReservaDAO para realizar operaciones con la entidad Reserva.
    private final IReservaDAO reservaDAO; 
   
    // Instancia del convertidor de objetos Reserva entre DTO y entidad.
    private final ReservaCVR reservaCVR; 
    
    // Instancia del convertidor de objetos Cliente entre DTO y entidad.
    private final ClienteCVR clienteCVR;
    
    // Instancia del convertidor de objetos Mesa entre DTO y entidad.
    private final MesaCVR mesaCVR;

    /**
     * Constructor por defecto de la clase ReservaBO.
     * Inicializa las instancias de DAO y convertidores.
     */
    public ReservaBO() {
        this.reservaDAO = new ReservaDAO();
        this.reservaCVR = new ReservaCVR();
        this.clienteCVR = new ClienteCVR();
        this.mesaCVR = new MesaCVR();
    }

    /**
     * Agrega una nueva reserva.
     * 
     * @param reserva Objeto ReservaDTO que representa la reserva a agregar.
     * @throws BOException Si ocurre algún error durante la operación.
     */
    @Override
    public void agregarReserva(ReservaDTO reserva) throws BOException {
        try{
            Reserva reservaEntity = reservaCVR.toEntity(reserva);
            reservaDAO.agregarReserva(reservaEntity);
        } catch(DAOException de){
            LOG.log(Level.SEVERE, "Error al agregar la reserva en DAO", de);
            throw new BOException(de.getMessage());
        }
    }

    /**
     * Consulta reservas entre dos fechas específicas.
     * 
     * @param inicio Fecha de inicio del periodo de consulta.
     * @param fin Fecha de fin del periodo de consulta.
     * @return Lista de objetos ReservaDTO que representan las reservas encontradas.
     * @throws BOException Si ocurre algún error durante la operación.
     */
    @Override
    public List<ReservaDTO> consultarPorFecha(LocalDateTime inicio, 
                                              LocalDateTime fin) throws BOException {
        try {
            List<Reserva> entidades = reservaDAO.consultarPorFecha(inicio, fin);
            List<ReservaDTO> dto = new ArrayList<>();
            
            for (Reserva reserva : entidades) {
                dto.add(reservaCVR.toDTO(reserva));
            }
           
            return dto;
        } catch(DAOException de){
            LOG.log(Level.SEVERE, "Error al consultar reservas por fecha en DAO", de);
            throw new BOException(de.getMessage());
        }
    }

    /**
     * Verifica la disponibilidad de una mesa en una fecha específica.
     * 
     * @param mesa Objeto MesaDTO que representa la mesa a verificar.
     * @param dia Fecha y hora en la cual verificar la disponibilidad.
     * @return true si la mesa está disponible, false en caso contrario.
     * @throws BOException Si ocurre algún error durante la operación.
     */
    @Override
    public boolean verificarPorDia(MesaDTO mesa, LocalDateTime dia) throws BOException {
        try {
            Mesa mesaEntidad = mesaCVR.toEntity(mesa);
            return reservaDAO.verificarPorDia(mesaEntidad, dia);
        } catch(DAOException de) {
            LOG.log(Level.SEVERE, "Error al verificar disponibilidad de la mesa en DAO", de);
            throw new BOException(de.getMessage());
        } catch (ConversionException ex) {
            LOG.log(Level.SEVERE, "Error en la conversión de MesaDTO a entidad", ex);
            throw new BOException("Error al verificar la reserva por día");
        }
    }

    /**
     * Busca reservas aplicando varios filtros de búsqueda.
     * 
     * @param nombreCliente Nombre del cliente.
     * @param telefonoCliente Teléfono del cliente.
     * @param fechaReserva Fecha específica de la reserva.
     * @param areaRestaurante Área del restaurante donde se busca la reserva.
     * @param fechaInicio Rango de inicio de la reserva.
     * @param fechaFin Rango de fin de la reserva.
     * @param tamanoMesa Tamaño de la mesa.
     * @return Lista de objetos ReservaDTO que coinciden con los filtros de búsqueda.
     * @throws BOException Si ocurre algún error durante la operación.
     */
    @Override
    public List<ReservaDTO> buscarReservasPorFiltros(String nombreCliente, 
                                                     String telefonoCliente, 
                                                     LocalDate fechaReserva, 
                                                     String areaRestaurante, 
                                                     LocalDate fechaInicio, 
                                                     LocalDate fechaFin, 
                                                     Integer tamanoMesa) 
            throws BOException {
        
        try {
            List<Reserva> entidades = reservaDAO.
                    buscarReservasPorFiltros(nombreCliente, telefonoCliente, 
                                             fechaReserva, areaRestaurante, 
                                             fechaInicio, fechaFin, tamanoMesa);
            List<ReservaDTO> dto = new ArrayList<>();
            
            for (Reserva reserva : entidades) {
                dto.add(reservaCVR.toDTO(reserva));
            }
           
            return dto;
        } catch(DAOException de) {
            LOG.log(Level.SEVERE, "Error al buscar reservas por filtros en DAO", de);
            throw new BOException(de.getMessage());
        }
    }

    /**
     * Verifica si un cliente tiene reservas activas a partir de una fecha y hora específica.
     * 
     * @param cliente Objeto ClienteDTO que representa el cliente a verificar.
     * @return true si el cliente tiene reservas activas, false en caso contrario.
     * @throws BOException Si ocurre algún error durante la operación.
     */
    @Override
    public boolean verificarReservaciones(ClienteDTO cliente) throws BOException {
        try {
            Cliente clienteEntidad = clienteCVR.toEntity(cliente);
            return reservaDAO.verificarReservaciones(clienteEntidad);
        } catch(ConversionException ce) {
            LOG.log(Level.SEVERE, "Error en la conversión de ClienteDTO a entidad");
            throw new BOException(ce.getMessage());
        } catch (DAOException ex) {
            LOG.log(Level.SEVERE, "Error al verificar reservaciones en DAO");
            throw new BOException(ex.getMessage());
        }
    }

    /**
     * Obtiene todas las reservas.
     * 
     * @return Lista de objetos ReservaDTO que representan todas las reservas.
     * @throws BOException Si ocurre algún error durante la operación.
     */
    @Override
    public List<ReservaDTO> obtenerReservas() throws BOException {
        try {
            List<Reserva> entidades = reservaDAO.obtenerReservas();           
            List<ReservaDTO> dto = new ArrayList<>();
            
            for (Reserva reserva : entidades) {
                dto.add(reservaCVR.toDTO(reserva));
            }
           
            return dto;
        } catch(DAOException de) {
            LOG.log(Level.SEVERE, "Error al obtener reservas en DAO", de);
            throw new BOException(de.getMessage());
        }    
    }

    /**
     * Busca reservas por nombre y fecha de inicio y fin.
     * 
     * @param nombre Nombre del cliente.
     * @param inicio Fecha de inicio de la búsqueda.
     * @param fin Fecha de fin de la búsqueda.
     * @return Lista de objetos ReservaDTO que coinciden con el nombre y fechas dadas.
     * @throws BOException Si ocurre algún error durante la operación.
     */
    @Override
    public List<ReservaDTO> buscarReservas(String nombre, 
                                           LocalDateTime inicio, 
                                           LocalDateTime fin) throws BOException {
        try {
            List<Reserva> entidades = reservaDAO.buscarReservas(nombre, inicio, fin);
            List<ReservaDTO> dto = new ArrayList<>();
            
            for (Reserva reserva : entidades) {
                dto.add(reservaCVR.toDTO(reserva));
            }
           
            return dto;
        } catch(DAOException de) {
            LOG.log(Level.SEVERE, "Error al buscar reservas en DAO", de);
            throw new BOException(de.getMessage());
        }        
    }

    /**
     * Actualiza una reserva existente.
     * 
     * @param reservaDTO Objeto ReservaDTO que contiene los datos de la reserva a actualizar.
     * @throws BOException Si ocurre algún error durante la operación.
     */
    @Override
    public void actualizarReserva(ReservaDTO reservaDTO) throws BOException {
         try {
             Reserva reserva = reservaCVR.toEntity(reservaDTO);
             reservaDAO.actualizarReserva(reserva);
        } catch(DAOException de) {
            LOG.log(Level.SEVERE, "Error al actualizar la reserva en DAO", de);
            throw new BOException(de.getMessage());
        }     
    }
    
}

