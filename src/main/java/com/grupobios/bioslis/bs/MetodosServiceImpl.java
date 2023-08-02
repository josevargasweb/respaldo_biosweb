/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgMetodosDAO;
import com.grupobios.bioslis.entity.CfgMetodos;



/**
 *
 * @author Marco Caracciolo
 */
public class MetodosServiceImpl implements MetodosService {


    CfgMetodosDAO cfgMetodosDAO;

	public CfgMetodosDAO getCfgMetodosDAO() {
		return cfgMetodosDAO;
	}

	public void setCfgMetodosDAO(CfgMetodosDAO cfgMetodosDAO) {
		this.cfgMetodosDAO = cfgMetodosDAO;
	}

	@Override
	public String insertMetodo(CfgMetodos cm, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgMetodosDAO.insertMetodos(cm, idUsuario);
	}

	@Override
	public String updateMetodo(CfgMetodos cm, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgMetodosDAO.updateMetodos(cm, idUsuario);
	}


    
}
