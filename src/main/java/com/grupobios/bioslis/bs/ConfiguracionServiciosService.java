/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.CfgServiciosDTO;
import com.grupobios.bioslis.entity.CfgServicios;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface ConfiguracionServiciosService {
    List<CfgServicios> getServiciosFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    String agregarServicio(CfgServiciosDTO cs, Long idUsuario) throws BiosLISDAOException;
    String updateServicio(CfgServiciosDTO cs, Long idUsuario) throws BiosLISDAOException;
    
}
