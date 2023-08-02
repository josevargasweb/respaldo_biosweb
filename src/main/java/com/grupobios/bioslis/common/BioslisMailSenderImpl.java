package com.grupobios.bioslis.common;

import java.io.ByteArrayOutputStream;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.grupobios.bioslis.bs.PdfServiceImpl;
import com.grupobios.bioslis.dto.NotificaResultadosDTO;

/**
 *
 * @author eric.nicholls
 */
public class BioslisMailSenderImpl implements BioslisMailSender {

  private static final Logger logger = LogManager.getLogger(PdfServiceImpl.class);

  // @Autowired
  private JavaMailSender bioslisJavaMailSender;

  public JavaMailSender getBioslisJavaMailSender() {
    return bioslisJavaMailSender;
  }

  public void setBioslisJavaMailSender(JavaMailSender bioslisJavaMailSender) {
    this.bioslisJavaMailSender = bioslisJavaMailSender;
  }

  @Override
  public void enviarMail(String destinatario, String asunto, String cuerpo, ByteArrayOutputStream output)
      throws BiosLISException {

    try {
      MimeMessage mimeMessage = bioslisJavaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      //String sContentType = "application/pdf";
      String remitente = "biosintelligence@gmail.com"; // Para la dirección nomcuenta@gmail.com
      helper.setTo(destinatario);
      helper.setSubject(asunto);
      helper.setText(cuerpo);
      helper.setFrom(remitente);
      InputStreamSource iss = new ByteArrayResource(output.toByteArray());
      //helper.addAttachment("Resultado examen.pdf", iss, sContentType);
      bioslisJavaMailSender.send(mimeMessage);
    } catch (MessagingException ex) {
      logger.error(ex.getMessage());
      throw new BiosLISException("No se pudo enviar correo.");
    } finally {
    }
  }

  @Override
  public void enviarMail(String destinatario, String asunto, String cuerpo) throws BiosLISException {

    try {
      MimeMessage mimeMessage = bioslisJavaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      String remitente = "biosintelligence@gmail.com"; // Para la dirección nomcuenta@gmail.com
      helper.setTo(destinatario);
      helper.setSubject(asunto);
      helper.setText(cuerpo);
      helper.setFrom(remitente);
      bioslisJavaMailSender.send(mimeMessage);
    } catch (MessagingException ex) {
    	ex.printStackTrace();
      logger.error(ex.getMessage());
      throw new BiosLISException("No se pudo enviar correo.");
    } finally {

    }
  }

  /*
   * public JavaMailSender getJavaMailSender() { JavaMailSenderImpl mailSender =
   * new JavaMailSenderImpl(); mailSender.setHost("smtp.gmail.com");
   * mailSender.setPort(587);
   * 
   * mailSender.setUsername("my.gmail@gmail.com");
   * mailSender.setPassword("password");
   * 
   * Properties props = mailSender.getJavaMailProperties();
   * props.put("mail.transport.protocol", "smtp"); props.put("mail.smtp.auth",
   * "true"); props.put("mail.smtp.starttls.enable", "true");
   * props.put("mail.debug", "true");
   * 
   * return mailSender; }
   * 
   * 
   * String NOREPLY_ADDRESS = null;
   * 
   * @Override public void sendSimpleMessage(String to, String subject, String
   * text) { try { SimpleMailMessage message = new SimpleMailMessage();
   * message.setFrom(NOREPLY_ADDRESS); message.setTo(to);
   * message.setSubject(subject); message.setText(text);
   * 
   * emailSender.send(message); } catch (MailException exception) {
   * exception.printStackTrace(); } }
   * 
   * @Override public void sendSimpleMessageUsingTemplate(String to, String
   * subject, String ...templateModel) { String text =
   * String.format(template.getText(), templateModel); sendSimpleMessage(to,
   * subject, text); }
   * 
   * @Override public void sendMessageWithAttachment(String to, String subject,
   * String text, String pathToAttachment) { try { MimeMessage message =
   * emailSender.createMimeMessage(); // pass 'true' to the constructor to create
   * a multipart message MimeMessageHelper helper = new MimeMessageHelper(message,
   * true);
   * 
   * helper.setFrom(NOREPLY_ADDRESS); helper.setTo(to);
   * helper.setSubject(subject); helper.setText(text);
   * 
   * FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
   * helper.addAttachment("Invoice", file);
   * 
   * emailSender.send(message); } catch (MessagingException e) {
   * e.printStackTrace(); } }
   */

  @Override
  public void sendSimpleMessage(String to, String subject, String text) {
    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
    // Tools | Templates.
  }

  @Override
  public void sendSimpleMessageUsingTemplate(String to, String subject, String... templateModel) {
    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
    // Tools | Templates.
  }

  @Override
  public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
    // Tools | Templates.
  }

  @Override
  public void notificarResultadoCritico(NotificaResultadosDTO notificaResultadosDTO) throws BiosLISException {
    this.enviarMail(notificaResultadosDTO.getNotificationsSendTo(), "Asunto resultado crítico",
        notificaResultadosDTO.getMessage());
  }
  @Override
  public void notificarExamenAutorizado(NotificaResultadosDTO notificaResultadosDTO) throws BiosLISException {
    this.enviarMail(notificaResultadosDTO.getNotificationsSendTo(), "Asunto resultado crítico",
        notificaResultadosDTO.getMessage());
  }

}