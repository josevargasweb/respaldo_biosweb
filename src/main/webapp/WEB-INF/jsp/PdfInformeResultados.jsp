 <%@page import="org.apache.logging.log4j.Logger"%>
<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="com.grupobios.bioslis.bs.PdfService"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page import="net.sf.jasperreports.engine.*"%>
<%@ page import="net.sf.jasperreports.engine.data.*"%>
<%@page contentType="application/pdf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%
 
            
            Logger logger = LogManager.getLogger("ReporttestLogger");
             
            try {
               
                Long PNroOrden = (Long)request.getAttribute("pNroOrden");
                PdfService pdfService = (PdfService)request.getAttribute("pdfHelper");
                List<Long> lstIdExamen = (List<Long> )request.getAttribute("pLstExId");

                if (pdfService == null) {
                   logger.error("pdfHelper es nulo");
                   throw new RuntimeException("pdfHelper es nulo");
               }
               else{
                   logger.debug("pdfHelper no es nulo");
               }                 
                JasperPrint jp = pdfService.fillReportFromJasperFile(PNroOrden,lstIdExamen);
                OutputStream output = new FileOutputStream(new File("C:/tmp/JasperReport.pdf"));
                JasperExportManager.exportReportToPdfStream(jp, output);
                output.close();
                OutputStream outputPage =  response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jp, outputPage);

                outputPage.flush();
                outputPage.close();

            } catch (Exception e) {
                 logger.error("generacion de PDF {}",e.getMessage() );
                throw e;
            }
        %>
    </body>
</html>
