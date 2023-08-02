/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgDerivadoresDAO;
import com.grupobios.bioslis.entity.CfgDerivadores;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class DerivadoresServiceImpl implements DerivadoresService {

    CfgDerivadoresDAO cfgDerivadoresDAO;

    public CfgDerivadoresDAO getCfgDerivadoresDAO() {
        return cfgDerivadoresDAO;
    }

    public void setCfgDerivadoresDAO(CfgDerivadoresDAO cfgDerivadoresDAO) {
        this.cfgDerivadoresDAO = cfgDerivadoresDAO;
    }
    
    @Override
    public List<CfgDerivadores> getDerivadoresFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        return cfgDerivadoresDAO.getDerivadoresFiltro(filtros);
    }

	@Override
	public String insertDerivadores(CfgDerivadores cd, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgDerivadoresDAO.agregarDerivador(cd, idUsuario);
	}

	@Override
	public String updateDerivadores(CfgDerivadores cd, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgDerivadoresDAO.actualizarDerivador(cd, idUsuario);
	}
    
}
