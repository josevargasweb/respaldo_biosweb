/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgMuestrasDAO;
import com.grupobios.bioslis.entity.CfgMuestras;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class ConfiguracionMuestrasServiceImpl implements ConfiguracionMuestrasService {

    CfgMuestrasDAO cfgMuestrasDAO;

    public CfgMuestrasDAO getCfgMuestrasDAO() {
        return cfgMuestrasDAO;
    }

    public void setCfgMuestrasDAO(CfgMuestrasDAO cfgMuestrasDAO) {
        this.cfgMuestrasDAO = cfgMuestrasDAO;
    }
    
    @Override
    public List<CfgMuestras> getMuestrasFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        return cfgMuestrasDAO.getMuestrasFiltro(filtros);
    }

	@Override
	public String insertMuestra(CfgMuestras cmue, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgMuestrasDAO.agregarMuestra(cmue, idUsuario);
	}

	@Override
	public String updateMuestra(CfgMuestras cmue, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgMuestrasDAO.actualizarTipoMuestra(cmue, idUsuario);
	}
    
}
