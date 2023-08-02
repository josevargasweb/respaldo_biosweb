/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgPrioridadAtencionDAO;
import com.grupobios.bioslis.entity.CfgPrioridadatencion;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class PrioridadAtencionServiceImpl implements PrioridadAtencionService {

    CfgPrioridadAtencionDAO cfgPrioridadAtencionDAO;

    public CfgPrioridadAtencionDAO getCfgPrioridadAtencionDAO() {
        return cfgPrioridadAtencionDAO;
    }

    public void setCfgPrioridadAtencionDAO(CfgPrioridadAtencionDAO cfgPrioridadAtencionDAO) {
        this.cfgPrioridadAtencionDAO = cfgPrioridadAtencionDAO;
    }
    
    @Override
    public List<CfgPrioridadatencion> getPrioridadesAtencionFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        return cfgPrioridadAtencionDAO.getPrioridadesAtencionFiltro(filtros);
    }

    @Override
    public String agregarPrioridadAtencion(CfgPrioridadatencion cpa, Long idUsuario) throws BiosLISDAOException {
        return cfgPrioridadAtencionDAO.insertPrio(cpa, idUsuario);
    }

    @Override
    public String actualizarPrioridadAtencion(CfgPrioridadatencion cpa, Long idUsuario) throws BiosLISDAOException {
        return cfgPrioridadAtencionDAO.updatePrio(cpa, idUsuario);
    }

    @Override
    public List<CfgPrioridadatencion> existePrioridadAtencion(String nombre) throws BiosLISDAOException {
        return cfgPrioridadAtencionDAO.existePrioridadAtencion(nombre);
    }

	@Override
	public CfgPrioridadatencion getPrioridadAtencionById(Short id) throws BiosLISDAOException {		
		return cfgPrioridadAtencionDAO.getPrioridadAtencionById(id);
	}
    
}
