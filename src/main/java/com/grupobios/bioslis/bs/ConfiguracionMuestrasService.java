/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgMuestras;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface ConfiguracionMuestrasService {
    
    List<CfgMuestras> getMuestrasFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    String insertMuestra(CfgMuestras cmue, Long idUsuario) throws BiosLISDAOException;
    String updateMuestra(CfgMuestras cmue, Long idUsuario) throws BiosLISDAOException;
    
}
