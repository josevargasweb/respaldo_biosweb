package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.bs.PdfService;
import com.grupobios.bioslis.entity.CfgMedicos;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReportController {

    @Autowired
    private PdfService pdfHelper; 

    public PdfService getPdfHelper() {
        return pdfHelper;
    }

    public void setPdfHelper(PdfService pdfHelper) {
        this.pdfHelper = pdfHelper;
    }
    
    ModelAndView mav = new ModelAndView();
    private static Logger logger = LogManager.getLogger(ReportController.class);

    @RequestMapping(value="/PdfInformeResultados", method= RequestMethod.GET )
    public ModelAndView pageLoad(@RequestParam("pNroOrden") Long pNroOrden,@RequestParam("pLstExId") List<Integer> lstIdExamen, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) {
        // HttpSession sesion = (HttpSession) request.getSession();
        // mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, 0);
        // if (sesion.getAttribute("usuario")!=null){
        //     if (pdfHelper == null) {
        //         logger.error("pdfHelper es nulo");
        //         throw new RuntimeException("pdfHelper es nulo");
        //     }
        //     else{
        //         logger.debug("pdfHelper no es nulo");
        //     }  
        //     mav.addObject("pdfHelper", pdfHelper);
        //     mav.addObject("pNroOrden", pNroOrden);
        //     mav.addObject("pLstExId",lstIdExamen);
        // }
  
        mav.setViewName("PdfInformeResultados");
        mav.addObject("pdfHelper", pdfHelper);
        mav.addObject("pNroOrden", pNroOrden);
        mav.addObject("pLstExId",lstIdExamen);
        return mav;
    }


    //MAIL
    @RequestMapping(value = "/testPDF", method = RequestMethod.POST, params = "insert")
    public void send() throws IOException {
        String destinatario = "daniel.delaguila@grupobios.cl"; //A quien le quieres escribir.
        String asunto = "Correo de prueba enviado desde Java";
        String cuerpo = "Esta es una prueba de correo...";

        enviarConGMail(destinatario, asunto, cuerpo);

    }

    private void enviarConGMail(String destinatario, String asunto, String cuerpo) throws IOException {
        // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
        String remitente = "correosbios@gmail.com";  //Para la dirección nomcuenta@gmail.com

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", "bios1482");    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);

        MimeMessage message = new MimeMessage(session);
        try {
            Multipart emailContent = new MimeMultipart();

            //Text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("My multipart text");

            //Attachment body part.
            MimeBodyPart pdfAttachment = new MimeBodyPart();
            
            pdfAttachment.attachFile("C:\\Users\\Jan Perkov\\Documents\\pdf.pdf");

            //Attach body parts
            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(pdfAttachment);

            //Attach multipart to message
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(asunto);
            message.setContent(emailContent);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, "bios1482");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            logger.error(me.getMessage());   //Si se produce un error
        }
    }

    //PDF
    @RequestMapping(value = "/testPDF", method = RequestMethod.POST, params = "inserta")
    public void a() {
        logger.debug("com.grupobios.bioslis.controller.jasperC.a()");
    }

    public List<CfgMedicos> findAll() {
        List<CfgMedicos> listItems = new ArrayList<CfgMedicos>();

        /* Create Items */
        CfgMedicos jan = new CfgMedicos();
        jan.setCmNombres("Dc Perkov");
        jan.setCmRun("19.100.247-4");

        CfgMedicos nacho = new CfgMedicos();
        nacho.setCmNombres("Dc Sanchez");
        nacho.setCmRun("19.222.222-4");

        /* Add Items to List */
        listItems.add(jan);
        listItems.add(nacho);
        return listItems;
    }

}
