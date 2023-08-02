/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.DocumentosOrdenDTO;

/**
 *
 * @author Marco Caracciolo
 */
public interface RegistroDocumentosService {
  public String guardarDocumento(int nOrden, byte idtipodocumento, Blob documento) throws BiosLISDAOException;

  public List<DocumentosOrdenDTO> getDocumentosOrden(int nOrden) throws BiosLISDAOException, SQLException, UnsupportedEncodingException;

  public List<DocumentosOrdenDTO> getDocumentosExamenOrden(Integer nOrden, Integer idExamen)
      throws BiosLISDAOException, SQLException, UnsupportedEncodingException;

  public void eliminarDocumento(int nOrden, byte idtipodocumento) throws BiosLISDAOException;

  public void guardarDocumento(Long nOrden, Long idExamen, byte tipoDocumentoAnexo, Blob docuBlob)
      throws BiosLISDAOException;

  void guardarDocumentoNew(Long nOrden, Long idExamen, byte tipoArchivo, Blob documento) throws BiosLISDAOException;

   String eliminarArchivoxExamen(int nOrden, int idExamen,  byte idtipodocumento) throws BiosLISDAOException;




}
