/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.text.ParseException;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgMedicosDAO;
import com.grupobios.bioslis.entity.CfgMedicos;

/**
 *
 * @author Marco Caracciolo
 */
public class ConfiguracionMedicosServiceImpl implements ConfiguracionMedicosService {

	CfgMedicosDAO cfgMedicosDAO;

	public CfgMedicosDAO getCfgMedicosDAO() {
		return cfgMedicosDAO;
	}

	public void setCfgMedicosDAO(CfgMedicosDAO cfgMedicosDAO) {
		this.cfgMedicosDAO = cfgMedicosDAO;
	}

	@Override
	public void insertMedico(CfgMedicos med, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		 cfgMedicosDAO.insertDatosMedicos(med, idUsuario);
	}

	@Override
	public void updateMedico(CfgMedicos med, Long idUsuario) throws BiosLISDAOException, ParseException {
		// TODO Auto-generated method stub
		 cfgMedicosDAO.updateDatosMedicos(med, idUsuario); 
	}
	
	
    
}
