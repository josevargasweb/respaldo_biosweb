/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgDiagnosticos;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface DiagnosticosService {
    
    List<CfgDiagnosticos> getListDiagnosticosFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    CfgDiagnosticos getDiagnosticoById(short idDiagnostico) throws BiosLISDAOException;
    String insertDiagnostico(CfgDiagnosticos cd, Long idUsuario) throws BiosLISDAOException;
    String updateDiagnostico(CfgDiagnosticos cd, Long idUsuario) throws BiosLISDAOException;
    
}
