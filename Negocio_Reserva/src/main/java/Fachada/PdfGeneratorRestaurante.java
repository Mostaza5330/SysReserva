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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JFileChooser;

/**
 * La clase {@code PdfGeneratorRestaurante} se encarga de generar un documento PDF que
 * contiene un reporte de reservas basado en filtros proporcionados por el
 * usuario. Utiliza la biblioteca iText para la creación del PDF.
 *
 * Esta clase permite a los usuarios seleccionar una ubicación para guardar el
 * archivo PDF y proporciona una interfaz para filtrar reservas por fecha, tipo
 * de mesa y ubicación.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class PdfGeneratorRestaurante {

    /**
     * Genera un documento PDF desde un formulario con filtros específicos de
     * reservas.
     *
     * @param fechaInicio La fecha de inicio del filtro en formato "dd/MM/yyyy".
     * @param fechaFin La fecha de fin del filtro en formato "dd/MM/yyyy".
     * @param tipoMesa El tipo de mesa a filtrar.
     * @param ubicacion La ubicación a filtrar.
     * @param reservas Una lista de objetos {@code ReservaDTO} que contienen la
     * información de reservas.
     * @return {@code true} si el PDF se generó correctamente, {@code false} si
     * hubo un error o si el usuario canceló la operación.
     */
    public boolean generarPDFDesdeFormulario(String fechaInicio,
            String fechaFin, String tipoMesa, String ubicacion,
            List<ReservaDTO> reservas) {
        // Solicita al usuario la ubicación donde guardar el PDF
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar PDF");
        fileChooser.setSelectedFile(new File("Reporte_"
                + System.currentTimeMillis() + ".pdf")); // Nombre por defecto

        // Abre el diálogo de guardado y obtiene la selección del usuario
        int userSelection = fileChooser.showSaveDialog(null);

        // Verifica si el usuario seleccionó un archivo para guardar
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            System.out.println("Guardado cancelado.");
            return false; // Indica que el usuario canceló la operación
        }

        // Se obtiene el archivo seleccionado por el usuario y asegura la 
        // extensión .pdf
        File archivoDestino = obtenerArchivoPDF(fileChooser.getSelectedFile());

        try (PdfWriter writer = new PdfWriter(archivoDestino); PdfDocument pdf = new PdfDocument(writer); Document document = new Document(pdf)) {

            // Agrega título y filtros aplicados al documento
            agregarTituloYFiltros(document, fechaInicio, fechaFin, tipoMesa,
                    ubicacion);

            // Calcular el total ganado
            double totalGanado = calcularTotalGanado(reservas, fechaInicio,
                    fechaFin, tipoMesa, ubicacion);
            document.add(new Paragraph("Total Ganado: $" + String.format("%.2f",
                    totalGanado)).setFontSize(16));

            // Crear y agregar la tabla para reservas
            Table table = crearTablaReservas(reservas, fechaInicio, fechaFin,
                    tipoMesa, ubicacion);
            document.add(new Paragraph("Reservas:").setFontSize(16));
            document.add(table);

            return true; // Indica que el PDF se generó correctamente
        } catch (FileNotFoundException e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
            return false; // Indica que hubo un error al generar el PDF
        } catch (Exception e) { // Captura cualquier otra excepción
            System.err.println("Error inesperado: " + e.getMessage());
            return false; // Indica que hubo un error al generar el PDF
        }
    }

    private File obtenerArchivoPDF(File archivoDestino) {
        if (!archivoDestino.getName().toLowerCase().endsWith(".pdf")) {
            archivoDestino = new File(archivoDestino.getAbsolutePath()
                    + ".pdf");
        }
        return archivoDestino;
    }

    private void agregarTituloYFiltros(Document document, String fechaInicio,
            String fechaFin, String tipoMesa, String ubicacion) {
        document.add(new Paragraph("Reporte de Reservas").setFontSize(20));
        document.add(new Paragraph("Filtros aplicados:").setFontSize(16));
        agregarParrafoSiNoVacio(document, "Fecha de Inicio: ", fechaInicio);
        agregarParrafoSiNoVacio(document, "Fecha de Fin: ", fechaFin);
        agregarParrafoSiNoVacio(document, "Tipo de Mesa: ", tipoMesa);
        agregarParrafoSiNoVacio(document, "Ubicación: ", ubicacion);
    }

    private Table crearTablaReservas(List<ReservaDTO> reservas,
            String fechaInicio, String fechaFin, String tipoMesa, String ubicacion) {
        Table table = new Table(5); // Ahora con 6 columnas
        table.addHeaderCell(createCenteredCell("Cliente"));
        table.addHeaderCell(createCenteredCell("Fecha"));
        table.addHeaderCell(createCenteredCell("Número de Personas"));
        table.addHeaderCell(createCenteredCell("Tipo de Mesa"));
        table.addHeaderCell(createCenteredCell("Ubicación"));

        // Formateador para fechas
        DateTimeFormatter formatter = DateTimeFormatter.
                ofPattern("dd/MM/yyyy HH:mm");

        // Agrega las reservas al documento
        for (ReservaDTO reserva : reservas) {
            // Filtrar reservas directamente durante la adición al PDF
            if (filtrarReserva(reserva, fechaInicio, fechaFin, tipoMesa, ubicacion)) {
                table.addCell(createCenteredCell(reserva.getCliente().getNombre()));
                table.addCell(createCenteredCell(reserva.getFechaHoraReserva().format(formatter)));
                table.addCell(createCenteredCell(String.valueOf(reserva.getNumeroPersonas())));
                table.addCell(createCenteredCell(reserva.getMesa().getTipoMesa()));
                table.addCell(createCenteredCell(reserva.getMesa().getUbicacion()));
            }
        }
        return table;
    }

    /**
     * Método auxiliar para crear una celda centrada en una tabla PDF.
     *
     * @param content El contenido a incluir en la celda.
     * @return Una celda centrada que contiene el contenido proporcionado.
     */
    private Cell createCenteredCell(String content) {
        Cell cell = new Cell();
        cell.add(new Paragraph(content));
        cell.setTextAlignment(TextAlignment.CENTER);
        return cell;
    }

    /**
     * Método auxiliar para agregar un párrafo al documento PDF si el contenido
     * no está vacío.
     *
     * @param document El documento al que se añadirá el párrafo.
     * @param etiqueta La etiqueta que precederá al contenido.
     * @param contenido El contenido a agregar al documento.
     */
    private void agregarParrafoSiNoVacio(Document document, String etiqueta,
            String contenido) {
        if (contenido != null && !contenido.trim().isEmpty()) {
            document.add(new Paragraph(etiqueta + contenido));
        }
    }

    /**
     * Filtra reservas basadas en los criterios proporcionados.
     *
     * @param reserva El objeto {@code ReservaDTO} a evaluar.
     * @param fechaInicio La fecha de inicio del filtro en formato "dd/MM/yyyy".
     * @param fechaFin La fecha de fin del filtro en formato "dd/MM/yyyy".
     * @param tipoMesa El tipo de mesa a filtrar.
     * @param ubicacion La ubicación a filtrar.
     * @return {@code true} si la reserva cumple con todos los criterios,
     * {@code false} de lo contrario.
     */
    private boolean filtrarReserva(ReservaDTO reserva, String fechaInicio, String fechaFin, String tipoMesa, String ubicacion) {
        boolean fechaValida = validarFechas(reserva, fechaInicio, fechaFin);
        boolean tipoMesaValido = tipoMesa.isEmpty() || reserva.getMesa().getTipoMesa().equalsIgnoreCase(tipoMesa);
        boolean ubicacionValida = ubicacion.isEmpty() || reserva.getMesa().getUbicacion().equalsIgnoreCase(ubicacion);

        return fechaValida && tipoMesaValido && ubicacionValida;
    }

    private boolean validarFechas(ReservaDTO reserva, String fechaInicio, String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime inicio = LocalDate.parse(fechaInicio, formatter).atStartOfDay();
        LocalDateTime fin = LocalDate.parse(fechaFin, formatter).atTime(23, 59);
        return !reserva.getFechaHoraReserva().isBefore(inicio) && !reserva.getFechaHoraReserva().isAfter(fin);
    }

    private double calcularTotalGanado(List<ReservaDTO> reservas,
            String fechaInicio, String fechaFin, String tipoMesa,
            String ubicacion) {
        double totalGanado = 0.0;

        for (ReservaDTO reserva : reservas) {
            if (filtrarReserva(reserva, fechaInicio, fechaFin, tipoMesa, ubicacion)) {
                totalGanado += reserva.getCosto(); // Asegúrate de que este método esté en ReservaDTO
            }
        }
        return totalGanado;
    }
}