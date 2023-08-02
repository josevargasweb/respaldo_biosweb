/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgDiagnosticosDAO;
import com.grupobios.bioslis.entity.CfgDiagnosticos;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class DiagnosticosServiceImpl implements DiagnosticosService {

    CfgDiagnosticosDAO CfgDiagnosticosDAO;

    public CfgDiagnosticosDAO getCfgDiagnosticosDAO() {
        return CfgDiagnosticosDAO;
    }

    public void setCfgDiagnosticosDAO(CfgDiagnosticosDAO CfgDiagnosticosDAO) {
        this.CfgDiagnosticosDAO = CfgDiagnosticosDAO;
    }
    
    @Override
    public List<CfgDiagnosticos> getListDiagnosticosFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        return CfgDiagnosticosDAO.getDiagnosticosFiltro(filtros);
    }

    @Override
    public CfgDiagnosticos getDiagnosticoById(short idDiagnostico) throws BiosLISDAOException {
        return CfgDiagnosticosDAO.getDiagnosticoById(idDiagnostico);
    }

    @Override
    public String insertDiagnostico(CfgDiagnosticos cd, Long idUsuario) throws BiosLISDAOException {
        return CfgDiagnosticosDAO.insertDiagnostico(cd, idUsuario);
    }

    @Override
    public String updateDiagnostico(CfgDiagnosticos cd, Long idUsuario) throws BiosLISDAOException {
        return CfgDiagnosticosDAO.updateDiagnostico(cd, idUsuario);
    }
    
}
