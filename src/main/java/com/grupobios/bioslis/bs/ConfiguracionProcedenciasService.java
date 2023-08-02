/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.CfgProcedenciasDTO;
import com.grupobios.bioslis.entity.CfgProcedencias;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface ConfiguracionProcedenciasService {
    
    public CfgProcedencias getProcedenciaById(int idProcedencia) throws BiosLISDAOException;
    public List<CfgProcedenciasDTO> getProcedenciasxCentro(int idCentro) throws BiosLISDAOException;
    public List<CfgProcedencias> getProcedenciasFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    public String insertProcedencia(CfgProcedenciasDTO cfg, Long idUsuario) throws BiosLISDAOException;
    public String updateProcedencia(CfgProcedenciasDTO cfg, Long idUsuario) throws BiosLISDAOException;
    
}
