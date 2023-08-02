/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgLaboratoriosDAO;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class LaboratoriosServiceImpl implements LaboratoriosService {

    CfgLaboratoriosDAO cfgLaboratoriosDAO;
    CfgExamenesDAO cfgExamenesDAO;

    public CfgLaboratoriosDAO getCfgLaboratoriosDAO() {
        return cfgLaboratoriosDAO;
    }

    public void setCfgLaboratoriosDAO(CfgLaboratoriosDAO cfgLaboratoriosDAO) {
        this.cfgLaboratoriosDAO = cfgLaboratoriosDAO;
    }

    public CfgExamenesDAO getCfgExamenesDAO() {
        return cfgExamenesDAO;
    }

    public void setCfgExamenesDAO(CfgExamenesDAO cfgExamenesDAO) {
        this.cfgExamenesDAO = cfgExamenesDAO;
    }
    
    @Override
    public List<CfgLaboratorios> getLaboratoriosFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        return cfgLaboratoriosDAO.getLaboratoriosFiltro(filtros);
    }

    @Override
    public CfgLaboratorios getLaboratorioById(int idLab) throws BiosLISDAOException {
        return cfgLaboratoriosDAO.getLaboratorioById(idLab);
    }

    @Override
    public String insertLaboratorio(CfgLaboratorios clab, Long idUsuario) throws BiosLISDAOException {
       return cfgLaboratoriosDAO.insertLab(clab, idUsuario);
    }

    @Override
    public String updateLaboratorio(CfgLaboratorios clab, Long idUsuario) throws BiosLISDAOException {
       return cfgLaboratoriosDAO.updateLab(clab, idUsuario);
    }

    @Override
    public int getCountExamenesByLab(int idLab) throws BiosLISDAOException {
        return cfgExamenesDAO.getCountExamenesByLab(idLab);
    }
    
}
