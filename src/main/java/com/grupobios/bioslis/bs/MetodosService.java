/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgMetodos;
import com.grupobios.bioslis.entity.CfgMuestras;

/**
 *
 * @author Marco Caracciolo
 */
public interface MetodosService {
    
    String insertMetodo(CfgMetodos cm, Long idUsuario) throws BiosLISDAOException;
    String updateMetodo(CfgMetodos cm , Long idUsuario) throws BiosLISDAOException;

    
}
