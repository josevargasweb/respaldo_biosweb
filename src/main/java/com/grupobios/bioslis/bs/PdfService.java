/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.io.OutputStream;
import java.util.List;

import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author eric.nicholls
 */
public interface PdfService {

  JasperPrint fillReportFromJasperFile(Long pNroOrden, List<Long> lstIdExamen) throws BiosLISJasperException;

  public void genFileReport(Long PNroOrden, List<Long> lstIdExamen) throws BiosLISJasperException;

  public void fillByteArrayOutputStreamReport(Long PNroOrden, List<Long> lstIdExamen, OutputStream output)
      throws BiosLISJasperException;

//  public void fillByteArrayOutputStreamCrReport(Integer PNroOrden, Integer PIdExamen, OutputStream output)
//      throws BioLisCrException;
//
//  void fillByteArrayOutputStreamCrReport(Long PNroOrden, List<Long> lstIdExamen, OutputStream output);

}
