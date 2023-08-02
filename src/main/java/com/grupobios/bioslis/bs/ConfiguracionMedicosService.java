/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgDiagnosticos;
import com.grupobios.bioslis.entity.CfgMedicos;
import com.grupobios.bioslis.entity.CfgMuestras;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface ConfiguracionMedicosService {
    
	void insertMedico(CfgMedicos med, Long idUsuario) throws BiosLISDAOException;
	void updateMedico(CfgMedicos med, Long idUsuario) throws BiosLISDAOException, ParseException;
    
}
