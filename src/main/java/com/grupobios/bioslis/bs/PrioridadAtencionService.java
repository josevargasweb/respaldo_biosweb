/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgPrioridadatencion;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface PrioridadAtencionService {
    
    List<CfgPrioridadatencion> getPrioridadesAtencionFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    String agregarPrioridadAtencion(CfgPrioridadatencion cpa, Long idUsuario) throws BiosLISDAOException;
    String actualizarPrioridadAtencion(CfgPrioridadatencion cpa, Long idUsuario) throws BiosLISDAOException;
    List<CfgPrioridadatencion> existePrioridadAtencion(String nombre) throws BiosLISDAOException;
    CfgPrioridadatencion getPrioridadAtencionById(Short id) throws BiosLISDAOException;
    
}
