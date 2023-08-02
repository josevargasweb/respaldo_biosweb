/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grupobios.bioslis.bs;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.DatosFichasExamenesDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.FichaFiltroDTO;
import com.grupobios.bioslis.dto.OrdenExamenCapturaResultadoDTO;
import com.grupobios.bioslis.dto.OrdenInformeResultadoDTO;
import com.grupobios.bioslis.dto.SendMailDTO;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.LdvTiposdocumentos;

/**
 *
 * @author eric.nicholls
 */
public interface InformeResultadosService {

  public List<DatosFichas> getOrdenes() throws BiosLISDAOException;

  public List<OrdenInformeResultadoDTO> getOrdenesInformeResultados(FichaFiltroDTO ffDto)
      throws BiosLISException, BiosLISDAOException;

  public List<ExamenOrdenDTO> getExamenesOrden();

  public List<LdvTiposdocumentos> getTiposDocumentos();

  public ModelAndView fill(ModelAndView mav);

  public List<ExamenOrdenDTO> getExamenesOrden(Long nroOrden);

  public List<ExamenOrdenDTO> sendExamenes(Long nroOrden, SendMailDTO sendMailDto)
      throws BiosLISBSException, BiosLISJasperException, BiosLISException, BiosLISDAOException;
  
  public List<ExamenOrdenDTO> imprimirExamenes(Long nroOrden, SendMailDTO sendMailDto)
	      throws BiosLISBSException, BiosLISJasperException, BiosLISException, BiosLISDAOException;

  public List<ExamenOrdenDTO> sendExamen(Long nroOrden, Long idExamen, SendMailDTO sendMailDto)
      throws BiosLISBSException, BiosLISJasperException, BiosLISException, BiosLISDAOException;

public HashMap<String, Object> updateInformeResultado(OrdenExamenCapturaResultadoDTO mOD)
		throws BiosLISBSException, BiosLISJasperException, BiosLISException, BiosLISDAOException, ParseException;

public ExamenOrdenDTO getOrdenesxExamen(Long nroOrden,Long idExamen) throws BiosLISDAOException;
}
