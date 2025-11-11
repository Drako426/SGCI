/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.servicios;

/**
 *
 * @author Juan Pablo
 */
import com.sgci.modelo.HistorialMedico;
import com.sgci.repositorio.MedicoRepositorio;
import com.sgci.repositorio.PacienteRepositorio;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFDecorator extends HistorialDecorator {

    private final String outputDir;

    public PDFDecorator(ServicioHistorial servicioBase) {
        super(servicioBase);
        this.outputDir = System.getProperty("user.home") + "/Downloads/"; // Carpeta Descargas
    }

    @Override
    protected void extraCrear(HistorialMedico h) throws Exception {
        generarPDF(h);
    }

    private void generarPDF(HistorialMedico h) throws Exception {
        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        MedicoRepositorio medicoRepo = new MedicoRepositorio();

        // Obtener nombres
        String nombrePaciente = pacienteRepo.obtenerNombrePorId(h.getIdPaciente());
        String nombreMedico = medicoRepo.obtenerNombrePorId(h.getIdMedico());

        // 🔐 Desencriptar antes de imprimir en PDF
        if (h.getDiagnostico() != null) h.setDiagnostico(cifrador.desencriptar(h.getDiagnostico()));
        if (h.getTratamiento() != null) h.setTratamiento(cifrador.desencriptar(h.getTratamiento()));
        if (h.getNotas() != null) h.setNotas(cifrador.desencriptar(h.getNotas()));

        // Crear carpeta si no existe
        File carpeta = new File(outputDir);
        if (!carpeta.exists()) carpeta.mkdirs();

        // Crear documento PDF
        String ruta = outputDir + "historial_" + h.getIdHistorial() + ".pdf";
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(ruta));
        doc.open();

        doc.add(new Paragraph("HISTORIAL MÉDICO", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Paciente: " + nombrePaciente));
        doc.add(new Paragraph("Médico: " + nombreMedico));
        doc.add(new Paragraph("Fecha de consulta: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(h.getFechaConsulta())));
        doc.add(new Paragraph("Diagnóstico: " + h.getDiagnostico()));
        doc.add(new Paragraph("Tratamiento: " + h.getTratamiento()));
        doc.add(new Paragraph("Notas: " + h.getNotas()));
        doc.add(new Paragraph("Estado actual: " + h.getEstadoActual()));

        doc.close();

        System.out.println("PDF generado correctamente en: " + ruta);
    }
}