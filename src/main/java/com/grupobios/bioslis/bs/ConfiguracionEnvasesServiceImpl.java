/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgEnvasesDAO;
import com.grupobios.bioslis.entity.CfgEnvases;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class ConfiguracionEnvasesServiceImpl implements ConfiguracionEnvasesService {
    
    CfgEnvasesDAO cfgEnvasesDAO;

    public CfgEnvasesDAO getCfgEnvasesDAO() {
        return cfgEnvasesDAO;
    }

    public void setCfgEnvasesDAO(CfgEnvasesDAO cfgEnvasesDAO) {
        this.cfgEnvasesDAO = cfgEnvasesDAO;
    }

    @Override
    public List<CfgEnvases> getEnvasesFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        return cfgEnvasesDAO.getEnvasesFiltro(filtros);
    }

    @Override
    public String agregarEnvase(CfgEnvases cenv, Long idUsuario) throws BiosLISDAOException {
        return cfgEnvasesDAO.insertEnvase(cenv, idUsuario);
    }

    @Override
    public String actualizarEnvase(CfgEnvases cenv, Long idUsuario) throws BiosLISDAOException {
        return cfgEnvasesDAO.updateEnvase(cenv, idUsuario);
    }

    @Override
    public CfgEnvases getEnvaseById(int idEnvase) throws BiosLISDAOException {
        return cfgEnvasesDAO.getEnvaseById(idEnvase);
    }
    
}
