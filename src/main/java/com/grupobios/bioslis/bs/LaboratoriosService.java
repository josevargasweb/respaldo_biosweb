/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface LaboratoriosService {
    
    List<CfgLaboratorios> getLaboratoriosFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    CfgLaboratorios getLaboratorioById(int idLab) throws BiosLISDAOException;
    String insertLaboratorio(CfgLaboratorios clab, Long idUsuario) throws BiosLISDAOException;
    String updateLaboratorio(CfgLaboratorios clab, Long idUsuario) throws BiosLISDAOException;
    int getCountExamenesByLab(int idLab) throws BiosLISDAOException;
    
}
