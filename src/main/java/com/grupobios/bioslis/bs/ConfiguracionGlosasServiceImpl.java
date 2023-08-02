/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgGlosasDAO;
import com.grupobios.bioslis.dao.CfgGlosastestDAO;
import com.grupobios.bioslis.entity.CfgGlosas;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class ConfiguracionGlosasServiceImpl implements ConfiguracionGlosasService {
    
    CfgGlosasDAO cfgGlosasDAO;
    CfgGlosastestDAO cfgGlosastestDAO;

    public CfgGlosasDAO getCfgGlosasDAO() {
        return cfgGlosasDAO;
    }

    public void setCfgGlosasDAO(CfgGlosasDAO cfgGlosasDAO) {
        this.cfgGlosasDAO = cfgGlosasDAO;
    }
    
    public CfgGlosastestDAO getCfgGlosastestDAO() {
		return cfgGlosastestDAO;
	}

	public void setCfgGlosastestDAO(CfgGlosastestDAO cfgGlosastestDAO) {
		this.cfgGlosastestDAO = cfgGlosastestDAO;
	}

	@Override
    public List<CfgGlosas> getGlosasFiltro(HashMap<String, String> filtros) throws BiosLISDAOException{
        return cfgGlosasDAO.getGlosasFiltro(filtros);
    }

    @Override
    public String agregarGlosa(CfgGlosas cg, Long idUsuario) throws BiosLISDAOException {
        String codigo = cg.getCgCodigoglosa().toUpperCase();
        cg.setCgCodigoglosa(codigo);
        String descripcion = cg.getCgDescripcion().toUpperCase();
        cg.setCgDescripcion(descripcion);
        return cfgGlosasDAO.insertGlosas(cg, idUsuario);
    }

    @Override
    public String actualizarGlosa(CfgGlosas cg, Long idUsuario) throws BiosLISDAOException {
        return cfgGlosasDAO.updateGlosa(cg, idUsuario);
    }

    @Override
    public CfgGlosas getGlosaById(int idGlosa) throws BiosLISDAOException {
        return cfgGlosasDAO.getGlosaById(idGlosa);
    }

	@Override
	public String getGlosaCritico(int idTest, int idGlosa) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgGlosasDAO.getCriticoGT(idTest,idGlosa);
	}
    
}
