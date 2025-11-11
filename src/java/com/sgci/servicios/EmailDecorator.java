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
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.io.File;

public class EmailDecorator extends HistorialDecorator {

    public EmailDecorator(ServicioHistorial delegate) {
        super(delegate);
    }

    @Override
    protected void extraCrear(HistorialMedico h) throws Exception {
        String rutaPDF = System.getProperty("user.home") + "/Downloads/historial_" + h.getIdHistorial() + ".pdf";
        enviarCorreoConAdjunto(
            "Nuevo historial médico creado",
            "Se ha creado un nuevo historial médico para el paciente con ID " + h.getIdPaciente(),
            rutaPDF
        );
    }

    private void enviarCorreoConAdjunto(String asunto, String cuerpo, String rutaAdjunto) {
        final String remitente = "sgci.notificaciones@gmail.com";
        final String clave = "fssl kood gmvz jfcx"; // la generada desde Google
        final String destinatario = "maria.bedoya3@utp.edu.co";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);

            // Parte de texto
            MimeBodyPart cuerpoPart = new MimeBodyPart();
            cuerpoPart.setText(cuerpo);

            // Parte adjunta (PDF)
            MimeBodyPart adjuntoPart = new MimeBodyPart();
            adjuntoPart.attachFile(new File(rutaAdjunto));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpoPart);
            multipart.addBodyPart(adjuntoPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Correo con PDF enviado correctamente a " + destinatario);

        } catch (Exception e) {
            System.err.println("Error al enviar correo con adjunto: " + e.getMessage());
            e.printStackTrace();
        }
    }
}