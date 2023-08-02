/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgUnidadesdemedida;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface UnidadesMedidaService {
    
    List<CfgUnidadesdemedida> getUnidadesMedidaFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    String insertUnidadesM(CfgUnidadesdemedida cfgum, Long idUsuario) throws BiosLISDAOException;
    String updateUnidadesM(CfgUnidadesdemedida cfgum, Long idUsuario) throws BiosLISDAOException;
    
}
