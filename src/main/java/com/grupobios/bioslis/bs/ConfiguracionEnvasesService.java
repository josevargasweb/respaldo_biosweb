/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgEnvases;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface ConfiguracionEnvasesService {
    
    List<CfgEnvases> getEnvasesFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    String agregarEnvase(CfgEnvases cenv, Long idUsuario) throws BiosLISDAOException;
    String actualizarEnvase(CfgEnvases cenv, Long idUsuario) throws BiosLISDAOException;
    CfgEnvases getEnvaseById(int idEnvase) throws BiosLISDAOException;
}
