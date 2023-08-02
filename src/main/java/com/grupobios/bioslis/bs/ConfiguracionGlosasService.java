/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgGlosas;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface ConfiguracionGlosasService {
    
    List<CfgGlosas> getGlosasFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    String agregarGlosa(CfgGlosas cg, Long idUsuario) throws BiosLISDAOException;
    String actualizarGlosa(CfgGlosas cg, Long idUsuario) throws BiosLISDAOException;
    CfgGlosas getGlosaById(int idGlosa) throws BiosLISDAOException;
    String getGlosaCritico(int idTest,int idGlosa) throws BiosLISDAOException;
}
