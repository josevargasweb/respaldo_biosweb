/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dto.CfgProcedenciasDTO;
import com.grupobios.bioslis.entity.CfgProcedencias;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marco Caracciolo
 */
@Service
public class ConfiguracionProcedenciasServiceImpl implements ConfiguracionProcedenciasService {

    private CfgProcedenciasDAO cfgProcedenciasDAO;

    public CfgProcedenciasDAO getCfgProcedenciasDAO() {
        return cfgProcedenciasDAO;
    }

    public void setCfgProcedenciasDAO(CfgProcedenciasDAO cfgProcedenciasDAO) {
        this.cfgProcedenciasDAO = cfgProcedenciasDAO;
    }

    @Override
    public CfgProcedencias getProcedenciaById(int idProcedencia) throws BiosLISDAOException {
        return cfgProcedenciasDAO.getProcedenciaById(idProcedencia);
    }

    @Override
    public List<CfgProcedencias> getProcedenciasFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        return cfgProcedenciasDAO.getProcedenciasFiltro(filtros);
    }

	@Override
	public List<CfgProcedenciasDTO> getProcedenciasxCentro(int idCentro) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgProcedenciasDAO.getProcedenciasxCentro(idCentro);
	}

	@Override
	public String insertProcedencia(CfgProcedenciasDTO cfg, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgProcedenciasDAO.insertProcedencia(cfg, idUsuario); 
	}

	@Override
	public String updateProcedencia(CfgProcedenciasDTO cfg, Long idUsuario) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return cfgProcedenciasDAO.updateProcedencia(cfg, idUsuario);
	}
    
}
