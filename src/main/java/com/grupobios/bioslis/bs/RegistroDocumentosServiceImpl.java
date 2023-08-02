/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosFichasDocumentosDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesArchivosDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesDAO;
import com.grupobios.bioslis.dao.LdvTiposarchivosDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosanexosDAO;
import com.grupobios.bioslis.dto.DocumentosOrdenDTO;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasdocumentos;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenesarchivos;
import com.grupobios.bioslis.entity.LdvTiposarchivos;
import com.grupobios.bioslis.entity.LdvTiposdocumentosanexos;

/**
 *
 * @author Marco Caracciolo
 */
@Service
public class RegistroDocumentosServiceImpl implements RegistroDocumentosService {

  DatosFichasDocumentosDAO documentosDAO;
  DatosFichasDAO datosFichasDAO;
  DatosFichasExamenesDAO datosFichasExamenesDAO;
  DatosFichasExamenesArchivosDAO datosFichasexamenesarchivosDAO;

  LdvTiposdocumentosanexosDAO tiposdocumentosanexosDAO;
  LdvTiposarchivosDAO tiposarchivosDAO;

  public DatosFichasDocumentosDAO getDocumentosDAO() {
    return documentosDAO;
  }

  public void setDocumentosDAO(DatosFichasDocumentosDAO documentosDAO) {
    this.documentosDAO = documentosDAO;
  }

  public DatosFichasDAO getDatosFichasDAO() {
    return datosFichasDAO;
  }

  public void setDatosFichasDAO(DatosFichasDAO datosFichasDAO) {
    this.datosFichasDAO = datosFichasDAO;
  }

  public LdvTiposdocumentosanexosDAO getTiposdocumentosanexosDAO() {
    return tiposdocumentosanexosDAO;
  }

  public void setTiposdocumentosanexosDAO(LdvTiposdocumentosanexosDAO tiposdocumentosanexosDAO) {
    this.tiposdocumentosanexosDAO = tiposdocumentosanexosDAO;
  }

  @Override
  public String guardarDocumento(int nOrden, byte idtipodocumento, Blob documento) throws BiosLISDAOException {
    DatosFichas df = datosFichasDAO.getOrdenxID(nOrden);
    LdvTiposdocumentosanexos tda = tiposdocumentosanexosDAO.getTiposDocumentosAnexos(idtipodocumento);
    documentosDAO.saveOrUpdateDocumento(df, tda, documento);
    return "";
  }

  /**
   *
   * @param nOrden
   * @return
   * @throws com.grupobios.bioslis.dao.BiosLISDAOException
   * @throws SQLException
   * @throws UnsupportedEncodingException
   */
  @Override
  public List<DocumentosOrdenDTO> getDocumentosOrden(int nOrden) throws BiosLISDAOException, SQLException, UnsupportedEncodingException {
    List<DatosFichasdocumentos> listDatosFichasdocumentos = documentosDAO.getDocumentosOrden(nOrden);
    List<DocumentosOrdenDTO> lstDtoDocumentos = new ArrayList<>();
    if (!listDatosFichasdocumentos.isEmpty()) {
    	if (listDatosFichasdocumentos.get(0).getDfdDocumento() == null) {
			return lstDtoDocumentos;
		}
	    for (DatosFichasdocumentos dfd : listDatosFichasdocumentos) {
	      DocumentosOrdenDTO doc = new DocumentosOrdenDTO();
	      doc.setIddocumentoficha(dfd.getDfdIddocumentoficha());
	      doc.setIdtipodocumentoanexo(dfd.getLdvTiposdocumentosanexos().getLdvtdaIdtipodocumentoanexo());
	      if (dfd.getDfdDocumento() != null) {
	        byte[] bdata = dfd.getDfdDocumento().getBytes(1, (int) dfd.getDfdDocumento().length());
	        byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
	        String base64Encoded = new String(encodeBase64, "UTF-8");
	        doc.setDocumento(base64Encoded);
	      }
	      // doc.setTipoArchivo(mf.getContentType());
	      lstDtoDocumentos.add(doc);
	    }
    }
    return lstDtoDocumentos;
  }

  @Override
  public void eliminarDocumento(int nOrden, byte idtipodocumento) throws BiosLISDAOException {
    DatosFichasdocumentos dfd = documentosDAO.getDocumento(nOrden, idtipodocumento);
    documentosDAO.deleteDocumento(dfd);
  }
  
  @Override
  public String eliminarArchivoxExamen(int nOrden,int idExamen, byte idtipodocumento) throws BiosLISDAOException {

    datosFichasexamenesarchivosDAO.deleteExamenesOrden(Long.valueOf(nOrden), Long.valueOf(idExamen), idtipodocumento);
    return "Bien";
    
  }

  @Override
  public List<DocumentosOrdenDTO> getDocumentosExamenOrden(Integer nOrden, Integer idExamen)
      throws BiosLISDAOException, SQLException, UnsupportedEncodingException {
    List<DatosFichasexamenesarchivos> listDatosFichasexamenesarchivos = datosFichasexamenesarchivosDAO
        .getDocumentosExamenOrden(nOrden, idExamen);
    List<DocumentosOrdenDTO> lstDtoDocumentos = new ArrayList<>();
    if (listDatosFichasexamenesarchivos != null) {
	    for (DatosFichasexamenesarchivos dfea : listDatosFichasexamenesarchivos) {
	      DocumentosOrdenDTO doc = new DocumentosOrdenDTO();
	      doc.setIddocumentoficha(dfea.getDfeaIdfichaexamenarchivo());
	      doc.setIdtipodocumentoanexo(dfea.getLdvTiposarchivos().getLdvtaIdtipoarchivo());
	      if (dfea.getDfeaDocumento() != null) {
	        byte[] bdata = dfea.getDfeaDocumento().getBytes(1, (int) dfea.getDfeaDocumento().length());
	        byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
	        String base64Encoded = new String(encodeBase64, "UTF-8");
	        doc.setDocumento(base64Encoded);
	      }
	      // doc.setTipoArchivo(mf.getContentType());
	      lstDtoDocumentos.add(doc);
	    }
    }
    return lstDtoDocumentos;
  }

  @Override
  public void guardarDocumento(Long nOrden, Long idExamen, byte tipoArchivo, Blob documento)
      throws BiosLISDAOException {
    DatosFichasexamenes dfe = datosFichasExamenesDAO.getDatosFichasExamenesByExamenyOrden(idExamen, nOrden);
    LdvTiposarchivos tda = tiposarchivosDAO.findById(tipoArchivo);
    datosFichasexamenesarchivosDAO.saveOrUpdateDocumento(nOrden, idExamen, tipoArchivo, documento);
  }

  public DatosFichasExamenesDAO getDatosFichasExamenesDAO() {
    return datosFichasExamenesDAO;
  }

  public void setDatosFichasExamenesDAO(DatosFichasExamenesDAO datosFichasExamenesDAO) {
    this.datosFichasExamenesDAO = datosFichasExamenesDAO;
  }

  public DatosFichasExamenesArchivosDAO getDatosFichasexamenesarchivosDAO() {
    return datosFichasexamenesarchivosDAO;
  }

  public void setDatosFichasexamenesarchivosDAO(DatosFichasExamenesArchivosDAO datosFichasexamenesarchivosDAO) {
    this.datosFichasexamenesarchivosDAO = datosFichasexamenesarchivosDAO;
  }

  public LdvTiposarchivosDAO getTiposarchivosDAO() {
    return tiposarchivosDAO;
  }

  public void setTiposarchivosDAO(LdvTiposarchivosDAO tiposarchivosDAO) {
    this.tiposarchivosDAO = tiposarchivosDAO;
  }

  @Override
  public void guardarDocumentoNew(Long nOrden, Long idExamen, byte tipoArchivo, Blob documento)
      throws BiosLISDAOException {
    DatosFichasexamenes dfe = datosFichasExamenesDAO.getDatosFichasExamenesByExamenyOrden(idExamen, nOrden);
    LdvTiposarchivos tda = tiposarchivosDAO.findById(tipoArchivo);
    datosFichasexamenesarchivosDAO.saveOrUpdateDocumento(dfe, tda, documento);
  }

}
