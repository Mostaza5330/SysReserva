package BO;

import Convertidores.MesaCVR;
import DAO.MesaDAO;
import DTOs.MesaDTO;
import Entidades.Mesa;
import Excepciones.BOException;
import Excepciones.ConversionException;
import Excepciones.DAOException;
import Interfaces.IMesaBO;
import Interfaces.IMesaDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase intermediaria entre la capa de DAO para la entidad Mesa que 
 * convierte los métodos de la clase DAO a DTO o a Entidad dependiendo del 
 * flujo. Sirve como puente para la lógica de negocio en la gestión de 
 * clientes, coordinando interacciones entre la capa de acceso a datos (DAO) 
 * y la de presentación.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class MesaBO implements IMesaBO {

    private static final Logger LOG = Logger.
            getLogger(MesaBO.class.getName());
    
    private final IMesaDAO mesaDAO; //instancia de la interfaz de MesaDAO
    private final MesaCVR mesaCVR; // instnacia del convertidor de mesa

    /**
     * Constructor por defecto de la clase que inicializa los atributos 
     * del convertidor y del acceso al DAO.
     */
    public MesaBO() {
        this.mesaDAO = new MesaDAO();   
        this.mesaCVR = new MesaCVR();
    }

    /**
     * Metodo intermediario que convierte una mesa de DTO a Entidad para 
     * poder ser agregada en la capa de dominio.
     * 
     * @param mesas Mesa a agregar.
     * @throws BOException En caso de Excepcion en la capa de dominio o en 
     * la conversion.
     */
    @Override
    public void agregarMesas(List<MesaDTO> mesas) throws BOException {
        try{
           List<Mesa> entidades = new ArrayList<>();
           
            for (MesaDTO mesaDTO : mesas) {
                entidades.add(mesaCVR.toEntity(mesaDTO));
            }
           
            LOG.log(Level.INFO, "Exito al agregar las mesas en BO");
            
            mesaDAO.agregarMesas(entidades);
        }
        catch(DAOException de){
            LOG.log(Level.SEVERE, "Error al agregar las mesas en BO", de);
            throw new BOException(de.getMessage());
        } catch (ConversionException ex) {
            Logger.getLogger(MesaBO.class.getName()).
                    log(Level.SEVERE, null, ex);
            throw new BOException("Error al agregar las mesas en BO");
        }
      
    }

    /**
     * Metodo intermediario que consulta las mesas de la capa de dominio y 
     * Las convierte a DTO para usarse en capaz superiores.
     * 
     * @return Lista de mesas en DTO.
     * @throws BOException En caso de Excepcion en la capa de dominio o en 
     * la conversion.
     */
    @Override
    public List<MesaDTO> consultarMesas() throws BOException {
        try{
           List<Mesa> entidades = mesaDAO.consultarMesas();
           List<MesaDTO> dto = new ArrayList<>();
           
            for (Mesa mesa : entidades) {
                dto.add(mesaCVR.toDTO(mesa));
            }
            
            LOG.log(Level.INFO, "Exito al consultar las mesas en BO");
           
            return dto;
        }
        catch(DAOException de){
            LOG.log(Level.SEVERE, "Error al consultar las mesas en BO");
            throw new BOException(de.getMessage());
        } catch (ConversionException ex) {
            Logger.getLogger(MesaBO.class.getName()).
                    log(Level.SEVERE, null, ex);
            throw new BOException("Error al consultar las mesas");
        }
    }

    /**
     * Metodo intermediario que consulta las mesas por ubicacion y las convierte
     * a DTO.
     * 
     * @param ubicacion Zona del restaurante.
     * @return Lista de mesas en DTO filtradas por ubicacion.
     * @throws BOException En caso de Excepcion en la capa de dominio o en 
     * la conversion.
     */
    @Override
    public int cantidadMesasPorUbicacion(String ubicacion) throws BOException {
        try{
            return mesaDAO.cantidadMesasPorUbicacion(ubicacion);
        }
        catch(DAOException de){
            LOG.log(Level.SEVERE, "Error al consultar la cantidad de mesas "
                    + "por tipo en BO", de);
            throw new BOException(de.getMessage());
        }
    }

    /**
     * Metodo intermediario que consulta las mesas por ubicacion y las convierte
     * a DTO.
     * 
     * @param tipo Tipo de mesa.
     * @return Lista de mesas en DTO filtradas por ubicacion.
     * @throws BOException  En caso de Excepcion en la capa de dominio o en 
     * la conversion.
     */
    @Override
    public List<MesaDTO> obtenerMesasPorTipo(String tipo) throws BOException {
        try{
           List<Mesa> entidades = mesaDAO.obtenerMesasPorTipo(tipo);
           List<MesaDTO> dto = new ArrayList<>();
           
            for (Mesa mesa : entidades) {
                dto.add(mesaCVR.toDTO(mesa));
            }
           
            LOG.log(Level.INFO, "Exito al consultar las mesas por tipo en BO");
            
            return dto;
        }
        catch(DAOException de){
            LOG.log(Level.SEVERE, "Error al consultar las mesas por "
                    + "tipo en BO", de);
            throw new BOException(de.getMessage());
        } catch (ConversionException ex) {
            Logger.getLogger(MesaBO.class.getName()).
                    log(Level.SEVERE, null, ex);
            throw new BOException("Error al consultar las mesas");
        }
    }

}
