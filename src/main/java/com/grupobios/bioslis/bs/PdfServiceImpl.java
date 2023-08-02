/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grupobios.bioslis.cfg.cache.LdvFormatosAbsDao;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.config.SpringContext;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.LdvFormatosDAO;

import net.sf.jasperreports.data.DataAdapterParameterContributorFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author eric.nicholls
 */

public class PdfServiceImpl implements PdfService {

  private final static Logger logger = LogManager.getLogger(PdfServiceImpl.class);
  private final static Map<Long, JasperReport> hmExamenJasperSrc = new HashMap<Long, JasperReport>();

  public JasperPrint fillReportFromJasperFile(Long pNroOrden, List<Long> lstIdExamen) throws BiosLISJasperException {

    try {
      String mainJasperResource = "FichaExamenesOrden.jasper";
      String xmlDataAdapterName = "BLDataAdapter.xml";
      InputStream fis = getClass().getClassLoader().getResourceAsStream(mainJasperResource);
      JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fis);
      jasperReport.setProperty(DataAdapterParameterContributorFactory.PROPERTY_DATA_ADAPTER_LOCATION,
          xmlDataAdapterName);
      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("PNroOrden", pNroOrden);
      parameters.put("PLstExId", lstIdExamen);

      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
      return jasperPrint;
    } catch (JRException ex) {
      logger.error(ex.getMessage(), ex);
    }
    throw new BiosLISJasperException("No se pudo obtener reporte de archivo jasper.");
  }

  @Override
  public void genFileReport(Long PNroOrden, List<Long> lstIdExamen) throws BiosLISJasperException {

    try (OutputStream output = new FileOutputStream(new File("C:/tmp/JasperReport.pdf"))) {
      JasperPrint jp = this.fillReportFromJasperFile(PNroOrden, lstIdExamen);
      JasperExportManager.exportReportToPdfStream(jp, output);
    } catch (FileNotFoundException ex) {
      logger.error(ex.getMessage());
      throw new BiosLISJasperException(ex);
    } catch (IOException | JRException ex) {
      logger.error(ex.getMessage());
      throw new BiosLISJasperException(ex);
    }
  }

  @Override
  public void fillByteArrayOutputStreamReport(Long PNroOrden, List<Long> lstIdExamen, OutputStream output)
      throws BiosLISJasperException {

    try {
      JasperPrint jp = this.fillReportFromJasperFile(PNroOrden, lstIdExamen);
      JasperExportManager.exportReportToPdfStream(jp, output);
    } catch (JRException ex) {
      logger.error(ex.getMessage());
      throw new BiosLISJasperException(ex);
    }
  }

  private JasperReport getJasperResource(List<Long> lstIdExamen) {

    for (Long idExamen : lstIdExamen) {

    }

    return null;

  }

  private JasperReport getJasperResource(Long idExamen) throws JRException, BiosLISDAOException, BiosLISException {

    LdvFormatosAbsDao.init(SpringContext.getBean(LdvFormatosDAO.class));
    String mainJasperResource = LdvFormatosAbsDao.getDescripcionById(idExamen.intValue());
    String xmlDataAdapterName = "BLDataAdapter.xml";

    JasperReport jp = hmExamenJasperSrc.get(idExamen);
    if (jp == null) {
      InputStream fis = getClass().getClassLoader().getResourceAsStream(mainJasperResource);
      jp = (JasperReport) JRLoader.loadObject(fis);
      jp.setProperty(DataAdapterParameterContributorFactory.PROPERTY_DATA_ADAPTER_LOCATION, xmlDataAdapterName);
      hmExamenJasperSrc.put(idExamen, jp);
    }
    return jp;

  }

}
