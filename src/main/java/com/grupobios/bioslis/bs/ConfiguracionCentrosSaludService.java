/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;



public interface ConfiguracionCentrosSaludService {
    
	String insertCentroSalud(CfgCentrosdesalud ccds, Long idUsuario) throws BiosLISDAOException;
	String updateCentroSalud(CfgCentrosdesalud ccds, Long idUsuario) throws BiosLISDAOException;
}
