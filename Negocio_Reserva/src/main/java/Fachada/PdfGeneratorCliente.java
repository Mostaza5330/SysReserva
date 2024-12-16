package Fachada;

import DTOs.ReservaDTO;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JFileChooser;

/**
 * Clase responsable de generar un PDF con el historial de reservaciones de un
 * cliente. Incluye información del cliente, una tabla con los detalles de sus
 * reservaciones y estadísticas generales sobre estas.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class PdfGeneratorCliente {

    /**
     * Genera un PDF con el historial de reservaciones de un cliente, incluyendo
     * información del cliente, detalles de las reservas y estadísticas.
     *
     * @param nombreCliente el nombre del cliente
     * @param telefono el número de teléfono del cliente
     * @param reservas lista de objetos {@link ReservaDTO} con las reservaciones
     * del cliente
     * @return true si el PDF se generó correctamente, false en caso de error o
     * cancelación
     */
    public boolean generarPDFCliente(String nombreCliente, String telefono, List<ReservaDTO> reservas) {
        // Solicita al usuario la ubicación donde guardar el PDF
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar PDF");
        fileChooser.setSelectedFile(new File("Historial_Cliente_" + nombreCliente.replace(" ", "_") + ".pdf"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        File archivoDestino = obtenerArchivoPDF(fileChooser.getSelectedFile());

        try (PdfWriter writer = new PdfWriter(archivoDestino); PdfDocument pdf = new PdfDocument(writer); Document document = new Document(pdf)) {

            // Agregar información del cliente
            agregarInformacionCliente(document, nombreCliente, telefono);

            // Crear y agregar la tabla de reservas
            Table table = crearTablaReservasCliente(reservas);
            document.add(new Paragraph("Historial de Reservaciones:").setFontSize(16));
            document.add(table);

            // Agregar estadísticas del cliente
            agregarEstadisticasCliente(document, reservas);

            return true;
        } catch (FileNotFoundException e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica y ajusta el nombre del archivo proporcionado para asegurarse de
     * que tenga la extensión ".pdf".
     *
     * @param archivoDestino el archivo proporcionado por el usuario
     * @return un objeto {@link File} que garantiza la extensión ".pdf"
     */
    private File obtenerArchivoPDF(File archivoDestino) {
        if (!archivoDestino.getName().toLowerCase().endsWith(".pdf")) {
            archivoDestino = new File(archivoDestino.getAbsolutePath() + ".pdf");
        }
        return archivoDestino;
    }

    /**
     * Agrega al documento la información del cliente, incluyendo nombre y
     * teléfono.
     *
     * @param document el documento PDF donde se agregará la información
     * @param nombreCliente el nombre del cliente
     * @param telefono el número de teléfono del cliente
     */
    private void agregarInformacionCliente(Document document, String nombreCliente, String telefono) {
        document.add(new Paragraph("Historial de Cliente").setFontSize(24).setBold());
        document.add(new Paragraph("Información del Cliente:").setFontSize(16));
        document.add(new Paragraph("Nombre: " + nombreCliente).setFontSize(12));
        document.add(new Paragraph("Teléfono: " + telefono).setFontSize(12));
        document.add(new Paragraph("\n")); // Espacio en blanco
    }

    /**
     * Crea una tabla que contiene los detalles de las reservas del cliente.
     * Cada fila incluye fecha y hora, tipo de mesa, ubicación y número de
     * personas.
     *
     * @param reservas lista de objetos {@link ReservaDTO} con las reservaciones
     * del cliente
     * @return una tabla ({@link Table}) con los datos de las reservas
     */
    private Table crearTablaReservasCliente(List<ReservaDTO> reservas) {
        Table table = new Table(4);

        // Encabezados de la tabla
        table.addHeaderCell(createHeaderCell("Fecha y Hora"));
        table.addHeaderCell(createHeaderCell("Mesa"));
        table.addHeaderCell(createHeaderCell("Ubicación"));
        table.addHeaderCell(createHeaderCell("Número de Personas"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Agregar las reservas a la tabla
        for (ReservaDTO reserva : reservas) {
            table.addCell(createCenteredCell(reserva.getFechaHoraReserva().format(formatter)));
            table.addCell(createCenteredCell(reserva.getMesa().getTipoMesa()));
            table.addCell(createCenteredCell(reserva.getMesa().getUbicacion()));
            table.addCell(createCenteredCell(String.valueOf(reserva.getNumeroPersonas())));
        }

        return table;
    }

    /**
     * Agrega estadísticas generales de las reservaciones al documento, como el
     * total de reservas, el monto total gastado y el promedio de personas por
     * reserva.
     *
     * @param document el documento PDF donde se agregarán las estadísticas
     * @param reservas lista de objetos {@link ReservaDTO} con las reservaciones
     * del cliente
     */
    private void agregarEstadisticasCliente(Document document, List<ReservaDTO> reservas) {
        document.add(new Paragraph("\n")); // Espacio en blanco
        document.add(new Paragraph("Estadísticas del Cliente:").setFontSize(16));

        // Total de reservas
        document.add(new Paragraph("Total de reservaciones: " + reservas.size()));

        // Total gastado
        double totalGastado = reservas.stream()
                .mapToDouble(ReservaDTO::getCosto)
                .sum();
        document.add(new Paragraph("Total gastado: $" + String.format("%.2f", totalGastado)));

        // Promedio de personas por reserva
        double promedioPersonas = reservas.stream()
                .mapToInt(ReservaDTO::getNumeroPersonas)
                .average()
                .orElse(0.0);
        document.add(new Paragraph("Promedio de personas por reserva: "
                + String.format("%.1f", promedioPersonas)));
    }

    /**
     * Crea una celda para los encabezados de la tabla con estilo centrado y
     * negrita.
     *
     * @param content el contenido de la celda
     * @return un objeto {@link Cell} con el estilo de encabezado
     */
    private Cell createHeaderCell(String content) {
        Cell cell = new Cell();
        cell.add(new Paragraph(content));
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setBold();
        return cell;
    }

    /**
     * Crea una celda con contenido centrado para las filas de la tabla.
     *
     * @param content el contenido de la celda
     * @return un objeto {@link Cell} con el contenido centrado
     */
    private Cell createCenteredCell(String content) {
        Cell cell = new Cell();
        cell.add(new Paragraph(content));
        cell.setTextAlignment(TextAlignment.CENTER);
        return cell;
    }
}
