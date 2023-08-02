/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dto.CfgServiciosDTO;
import com.grupobios.bioslis.entity.CfgServicios;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class ConfiguracionServiciosServiceImpl implements ConfiguracionServiciosService {

    CfgServiciosDAO servicios_dao;

    public CfgServiciosDAO getServicios_dao() {
        return servicios_dao;
    }

    public void setServicios_dao(CfgServiciosDAO servicios_dao) {
        this.servicios_dao = servicios_dao;
    }
    
    @Override
    public List<CfgServicios> getServiciosFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        return servicios_dao.getServiciosFiltro(filtros);
    }

	@Override
	public String agregarServicio(CfgServiciosDTO cs, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return servicios_dao.agregarServicio(cs, idUsuario);
	}

	@Override
	public String updateServicio(CfgServiciosDTO cs, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return servicios_dao.actualizarServicio(cs, idUsuario);
	}
    
}
