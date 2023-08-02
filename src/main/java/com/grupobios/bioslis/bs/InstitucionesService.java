/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgConvenios;
import com.grupobios.bioslis.entity.CfgInstitucionesdesalud;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.SistemaConfiguraciones;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface InstitucionesService {
    
    public List<CfgCentrosdesalud> getCentrosdesalud() throws BiosLISDAOException;
    
    public List<CfgCentrosdesalud> getCentrosdesaludLikeCodigo(String codigo) throws BiosLISDAOException;
    
    public List<CfgCentrosdesalud> getCentrosdesaludLikeNombre(String nombre) throws BiosLISDAOException;
    
    public List<CfgCentrosdesalud> getCentrosdesaludFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    
    public CfgCentrosdesalud getCentrosdesaludById(short id) throws BiosLISDAOException;
    
    public List<CfgLaboratorios> getLaboratorios() throws BiosLISDAOException;
    
    public List<CfgLaboratorios> getLaboratoriosByCentro(int idCentro) throws BiosLISDAOException;
    
    public List<CfgSecciones> getSecciones(int idLab) throws BiosLISDAOException;
    
    public List<CfgConvenios> getConvenios() throws BiosLISDAOException;
    
    public void agregarCentrodesalud(CfgCentrosdesalud ccds, Long idUsuario) throws BiosLISDAOException;
    
    public SistemaConfiguraciones getSistemaConfiguracionesByCentro(byte idCentroSalud) throws BiosLISDAOException;
    
    public List<CfgInstitucionesdesalud> getInstitucionesSalud() throws BiosLISDAOException;
    
    public List<CfgCentrosdesalud> existeCentrodesalud(String codigo) throws BiosLISDAOException;
}
