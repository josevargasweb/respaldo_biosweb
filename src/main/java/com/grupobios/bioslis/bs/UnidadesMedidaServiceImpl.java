/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgUnidadesdemedidaDAO;
import com.grupobios.bioslis.entity.CfgUnidadesdemedida;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class UnidadesMedidaServiceImpl implements UnidadesMedidaService {

    CfgUnidadesdemedidaDAO cfgUnidadesdemedidaDAO;

    public CfgUnidadesdemedidaDAO getCfgUnidadesdemedidaDAO() {
        return cfgUnidadesdemedidaDAO;
    }

    public void setCfgUnidadesdemedidaDAO(CfgUnidadesdemedidaDAO cfgUnidadesdemedidaDAO) {
        this.cfgUnidadesdemedidaDAO = cfgUnidadesdemedidaDAO;
    }
    
    @Override
    public List<CfgUnidadesdemedida> getUnidadesMedidaFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        return cfgUnidadesdemedidaDAO.getUnidadesMedidaFiltro(filtros);
    }

	@Override
	public String insertUnidadesM(CfgUnidadesdemedida cfgum, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgUnidadesdemedidaDAO.insertUnidadMedida(cfgum, idUsuario);
	}

	@Override
	public String updateUnidadesM(CfgUnidadesdemedida cfgum, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgUnidadesdemedidaDAO.updateUnidadMedida(cfgum, idUsuario);
	}
    
}
