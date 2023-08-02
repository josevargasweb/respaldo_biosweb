/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgSeccionesDAO;
import com.grupobios.bioslis.entity.CfgSecciones;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class SeccionesServiceImpl implements SeccionesService {

    CfgSeccionesDAO cfgSeccionesDAO;
    CfgExamenesDAO cfgExamenesDAO;

    public CfgSeccionesDAO getCfgSeccionesDAO() {
        return cfgSeccionesDAO;
    }

    public void setCfgSeccionesDAO(CfgSeccionesDAO cfgSeccionesDAO) {
        this.cfgSeccionesDAO = cfgSeccionesDAO;
    }

    public CfgExamenesDAO getCfgExamenesDAO() {
        return cfgExamenesDAO;
    }

    public void setCfgExamenesDAO(CfgExamenesDAO cfgExamenesDAO) {
        this.cfgExamenesDAO = cfgExamenesDAO;
    }
    
    @Override
    public List<CfgSecciones> getSeccionesFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        return cfgSeccionesDAO.getSeccionesFiltro(filtros);
    }

    @Override
    public CfgSecciones getSeccionById(int idSeccion) throws BiosLISDAOException {
        return cfgSeccionesDAO.getSeccionById(idSeccion);
    }

    @Override
    public String agregarSeccion(CfgSecciones csec, Long idUsuario) throws BiosLISDAOException {
        return cfgSeccionesDAO.insertSeccion(csec, idUsuario);
    }

    @Override
    public String actualizarSeccion(CfgSecciones csec, Long idUsuario) throws BiosLISDAOException {
        return cfgSeccionesDAO.updateSeccion(csec, idUsuario);
    }

    @Override
    public int getCountExamenesBySeccion(int idSeccion) throws BiosLISDAOException {
        return cfgExamenesDAO.getCountExamenesBySeccion(idSeccion);
    }
    
}
