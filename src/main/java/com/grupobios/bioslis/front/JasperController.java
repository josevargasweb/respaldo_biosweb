package com.grupobios.bioslis.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.grupobios.bioslis.bs.PdfService;

@Controller
public class JasperController {

	@Autowired
	PdfService pdfService;
	ModelAndView mav = new ModelAndView();

	// MAIL

//    @RequestMapping(value = "/testPDF", method = RequestMethod.POST, params = "insert")
//    public void send() throws IOException {
//        String destinatario = "daniel.delaguila@grupobios.cl"; //A quien le quieres escribir.
//        String asunto = "Correo de prueba enviado desde Java";
//        String cuerpo = "Esta es una prueba de correo...";
//
//        enviarConGMail(destinatario, asunto, cuerpo);
//
//    }
//
//    private void enviarConGMail(String destinatario, String asunto, String cuerpo) throws IOException {
//        // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
//        String remitente = "correosbios@gmail.com";  //Para la dirección nomcuenta@gmail.com
//
//        Properties props = System.getProperties();
//        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
//        props.put("mail.smtp.user", remitente);
//        props.put("mail.smtp.clave", "bios1482");    //La clave de la cuenta
//        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
//        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
//        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
//
//        Session session = Session.getDefaultInstance(props);
//
//        MimeMessage message = new MimeMessage(session);
//        try {
//            Multipart emailContent = new MimeMultipart();
//
//            //Text body part
//            MimeBodyPart textBodyPart = new MimeBodyPart();
//            textBodyPart.setText("My multipart text");
//
//            //Attachment body part.
//            MimeBodyPart pdfAttachment = new MimeBodyPart();
//            
//            pdfAttachment.attachFile("C:\\Users\\Jan Perkov\\Documents\\pdf.pdf");
//
//            //Attach body parts
//            emailContent.addBodyPart(textBodyPart);
//            emailContent.addBodyPart(pdfAttachment);
//
//            //Attach multipart to message
//            message.setFrom(new InternetAddress(remitente));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
//            message.setSubject(asunto);
//            message.setContent(emailContent);
//            Transport transport = session.getTransport("smtp");
//            transport.connect("smtp.gmail.com", remitente, "bios1482");
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
//        } catch (MessagingException me) {
//            me.printStackTrace();   //Si se produce un error
//        }
//    }
//

//    public HashMap<String, Object> Parametros() {
//
//        conexion con = new conexion();
//        Connection conexionBD = con.conexion("jdbc:oracle:thin:@192.168.3.206:1521:MGOLD");
//
//        DatosPacientesDAO dpDAO = new DatosPacientesDAO();
//        CfgExamenesDAO ceDAO = new CfgExamenesDAO();
//        List<CfgMedicos> listItems = new ArrayList<CfgMedicos>();
//        List<DatosPacientes> listItems2 = new ArrayList<DatosPacientes>();
//
//        //   JRBeanCollectionDataSource itemsJRBean2 = new JRBeanCollectionDataSource(listItems2);
//        HashMap<String, Object> parameters = new HashMap<String, Object>();
//
//        parameters.put("REPORT_CONNECTION", conexionBD);
//        parameters.put("Examenes", 21);
//        //parameters.put("Conexion2", conexionBD);
//
//        return parameters;
//    }

//    public HashMap<String, Object> ParametrosGuardados() throws BiosLISDAOException {
//
//        //  conexion con = new conexion("jdbc:oracle:thin:@192.168.3.206:1521:MGOLD");
//        DatosPacientesDAO dpDAO = new DatosPacientesDAO();
//        CfgExamenesDAO ceDAO = new CfgExamenesDAO();
//        List<CfgMedicos> listItems = new ArrayList<CfgMedicos>();
//        List<DatosPacientes> listItems2 = new ArrayList<DatosPacientes>();
//        DatosPacientes dp = dpDAO.getPacienteByRut("19100247-4");
//        DatosFichasDAO df = new DatosFichasDAO();
//        List<Object[]> a = df.selectOrdenParaInforme(1);
//
//        DatosFichasExamenesTestDAO aa = new DatosFichasExamenesTestDAO();
//        //getDatosFichasExamenesTestByOrden
//
//        int count = 0;
//        //int[] aaaa = {} ;
//
//        List<DatosFichasexamenestest> ss = aa.GetDatosParaPDF(88);
//        List<DatosFichasexamenestest> listItems22 = new ArrayList<DatosFichasexamenestest>();
//
//        for (DatosFichasexamenestest s : ss) {
//            DatosFichasexamenestest testeofinal = new DatosFichasexamenestest();
//            CfgExamenes ce = ceDAO.getExamenById(s.getId().getDfetIdexamen());
//            // s.setNombreExamen(ce.getCeDescripcion());
//            testeofinal.setNombreExamen(ce.getCeDescripcion());
//            testeofinal.setCE_ABREVIADO(s.getCfgExamenestest().getCfgTest().getCtAbreviado());
//            listItems22.add(testeofinal);
//            /*
//            aaaa[count] = s.getCfgExamenestest().getId().getCetIdtest();
//            boolean contains = IntStream.of(aaaa).anyMatch(x -> x == 4);
//            count++;*/
//
//        }
//
//
//        /* Create Items */
//        CfgMedicos jan = new CfgMedicos();
//        jan.setCmNombres("Dc Perkov");
//        jan.setCmRun("19.100.247-4");
//
//        CfgMedicos nacho = new CfgMedicos();
//        nacho.setCmNombres("Dc Sanchez");
//        nacho.setCmRun("19.222.222-4");
//
//        listItems.add(jan);
//        listItems.add(nacho);
//
//        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems22);
//
//        DatosPacientes jan2 = new DatosPacientes();
//        jan2.setDpNombres("testeoJan");
//        jan2.setDpPrimerapellido("testeoPerkov");
//
//        listItems2.add(jan2);
//
//        //   JRBeanCollectionDataSource itemsJRBean2 = new JRBeanCollectionDataSource(listItems2);
//        HashMap<String, Object> parameters = new HashMap<String, Object>();
//
//        // parameters.put("ItemDataSource", itemsJRBean);
//        return parameters;
//    }

}
