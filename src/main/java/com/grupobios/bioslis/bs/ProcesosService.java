/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.ProcesosDTO;
import com.grupobios.bioslis.entity.LdvOperacionesmath;
import com.grupobios.bioslis.entity.LdvTipocomunicacion;
import com.grupobios.bioslis.entity.LdvTiposcondicion;
import com.grupobios.bioslis.entity.SigmaProcesos;
import com.grupobios.bioslis.entity.SigmaProcesosalarmas;
import com.grupobios.bioslis.entity.SigmaTiposmuestras;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface ProcesosService {
    
    List<SigmaProcesos> getProcesosFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    
    ProcesosDTO getProcesoById(String codigoProceso) throws BiosLISDAOException;
    
    String insertProceso(ProcesosDTO procesoDTO, Long idUsuario) throws BiosLISDAOException;
    
    void updateProceso(ProcesosDTO procesoDTO, Long idUsuario) throws BiosLISDAOException;
    
    List<LdvTipocomunicacion> getListTiposComunicaciones() throws BiosLISDAOException;
    
    List<SigmaTiposmuestras> getListTiposMuestras() throws BiosLISDAOException;
    
    List<LdvTiposcondicion> getListTiposCondicion() throws BiosLISDAOException;
    
    List<LdvOperacionesmath> getListOperacionesMath() throws BiosLISDAOException;
    
    List<SigmaProcesos> getProcesoByCodigo(String codigoProceso) throws BiosLISDAOException;
    
    List<SigmaProcesosalarmas> getAlarmasByCodigoProceso(String codigoProceso) throws BiosLISDAOException;
}
