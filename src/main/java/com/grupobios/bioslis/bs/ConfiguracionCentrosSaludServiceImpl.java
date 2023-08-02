/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCentrosDeSaludDAO;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;


/**
 *
 * @author Marco Caracciolo
 */
public class ConfiguracionCentrosSaludServiceImpl implements ConfiguracionCentrosSaludService {
	
	CfgCentrosDeSaludDAO cfgCentrosDeSaludDAO;
	
	
	public CfgCentrosDeSaludDAO getCfgCentrosDeSaludDAO() {
		return cfgCentrosDeSaludDAO;
	}


	public void setCfgCentrosDeSaludDAO(CfgCentrosDeSaludDAO cfgCentrosDeSaludDAO) {
		this.cfgCentrosDeSaludDAO = cfgCentrosDeSaludDAO;
	}


	@Override
	public String insertCentroSalud(CfgCentrosdesalud ccds, Long idUsuario) throws BiosLISDAOException {		
		return cfgCentrosDeSaludDAO.agregarCentroDeSalud(ccds, idUsuario);
	}


	@Override
	public String updateCentroSalud(CfgCentrosdesalud ccds, Long idUsuario) throws BiosLISDAOException {		
		return cfgCentrosDeSaludDAO.actualizarCentroDeSalud(ccds, idUsuario);
	}

    
}
