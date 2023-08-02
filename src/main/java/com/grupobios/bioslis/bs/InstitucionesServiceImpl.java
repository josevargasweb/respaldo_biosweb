/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCentrosDeSaludDAO;
import com.grupobios.bioslis.dao.CfgConvenioDAO;
import com.grupobios.bioslis.dao.CfgInstitucionesDeSaludDAO;
import com.grupobios.bioslis.dao.CfgLaboratoriosDAO;
import com.grupobios.bioslis.dao.CfgSeccionesDAO;
import com.grupobios.bioslis.dao.SistemaConfiguracionesDAO;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgConvenios;
import com.grupobios.bioslis.entity.CfgInstitucionesdesalud;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.SistemaConfiguraciones;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marco Caracciolo
 */
@Service
public class InstitucionesServiceImpl implements InstitucionesService {

    private CfgCentrosDeSaludDAO cfgCentrosDeSaludDAO;
    private CfgLaboratoriosDAO cfgLaboratoriosDAO;
    private CfgSeccionesDAO cfgSeccionesDAO;
    private CfgConvenioDAO cfgConvenioDAO;
    private SistemaConfiguracionesDAO sistemaConfiguracionesDAO;
    private CfgInstitucionesDeSaludDAO institucionesSaludDAO;
    
    @Override
    public List<CfgCentrosdesalud> getCentrosdesalud() throws BiosLISDAOException {
        return cfgCentrosDeSaludDAO.getCentrosDeSalud();
    }

    @Override
    public List<CfgCentrosdesalud> getCentrosdesaludLikeCodigo(String codigo) throws BiosLISDAOException {
        return cfgCentrosDeSaludDAO.getCentrosDeSaludLikeCodigo(codigo);
    }

    @Override
    public List<CfgCentrosdesalud> getCentrosdesaludLikeNombre(String nombre) throws BiosLISDAOException {
        return cfgCentrosDeSaludDAO.getCentrosDeSaludLikeNombre(nombre);
    }

    @Override
    public CfgCentrosdesalud getCentrosdesaludById(short id) throws BiosLISDAOException {
        return cfgCentrosDeSaludDAO.getCentrosDeSaludById(id);
    }

    @Override
    public List<CfgLaboratorios> getLaboratorios() throws BiosLISDAOException{
        return cfgLaboratoriosDAO.getLaboratorios();
    }

    @Override
    public List<CfgLaboratorios> getLaboratoriosByCentro(int idCentro) throws BiosLISDAOException {
        return cfgLaboratoriosDAO.getLaboratoriosByCentro(idCentro);
    }

    @Override
    public List<CfgSecciones> getSecciones(int idLab) throws BiosLISDAOException {
        return cfgSeccionesDAO.getSeccionesByLab(idLab);
    }

    @Override
    public List<CfgConvenios> getConvenios() throws BiosLISDAOException {
        return cfgConvenioDAO.getConvenios();
    }

    @Override
    public void agregarCentrodesalud(CfgCentrosdesalud ccds, Long idUsuario) throws BiosLISDAOException {
        cfgCentrosDeSaludDAO.agregarCentroDeSalud(ccds, idUsuario);
    }

    @Override
    public SistemaConfiguraciones getSistemaConfiguracionesByCentro(byte idCentroSalud) throws BiosLISDAOException {
        return sistemaConfiguracionesDAO.getSistemaConfiguracionesByCentro(idCentroSalud);
    }

    public CfgCentrosDeSaludDAO getCfgCentrosDeSaludDAO() {
        return cfgCentrosDeSaludDAO;
    }

    public void setCfgCentrosDeSaludDAO(CfgCentrosDeSaludDAO cfgCentrosDeSaludDAO) {
        this.cfgCentrosDeSaludDAO = cfgCentrosDeSaludDAO;
    }

    public CfgLaboratoriosDAO getCfgLaboratoriosDAO() {
        return cfgLaboratoriosDAO;
    }

    public void setCfgLaboratoriosDAO(CfgLaboratoriosDAO cfgLaboratoriosDAO) {
        this.cfgLaboratoriosDAO = cfgLaboratoriosDAO;
    }

    public CfgSeccionesDAO getCfgSeccionesDAO() {
        return cfgSeccionesDAO;
    }

    public void setCfgSeccionesDAO(CfgSeccionesDAO cfgSeccionesDAO) {
        this.cfgSeccionesDAO = cfgSeccionesDAO;
    }

    public CfgConvenioDAO getCfgConvenioDAO() {
        return cfgConvenioDAO;
    }

    public void setCfgConvenioDAO(CfgConvenioDAO cfgConvenioDAO) {
        this.cfgConvenioDAO = cfgConvenioDAO;
    }

    public SistemaConfiguracionesDAO getSistemaConfiguracionesDAO() {
        return sistemaConfiguracionesDAO;
    }

    public void setSistemaConfiguracionesDAO(SistemaConfiguracionesDAO sistemaConfiguracionesDAO) {
        this.sistemaConfiguracionesDAO = sistemaConfiguracionesDAO;
    }

    public CfgInstitucionesDeSaludDAO getInstitucionesSaludDAO() {
        return institucionesSaludDAO;
    }

    public void setInstitucionesSaludDAO(CfgInstitucionesDeSaludDAO institucionesSaludDAO) {
        this.institucionesSaludDAO = institucionesSaludDAO;
    }

    @Override
    public List<CfgInstitucionesdesalud> getInstitucionesSalud() throws BiosLISDAOException {
        return institucionesSaludDAO.getInstitucionesDeSalud();
    }

    @Override
    public List<CfgCentrosdesalud> getCentrosdesaludFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        return cfgCentrosDeSaludDAO.getCentrosDeSaludFiltro(filtros);
    }

    @Override
    public List<CfgCentrosdesalud> existeCentrodesalud(String codigo) throws BiosLISDAOException {
        return cfgCentrosDeSaludDAO.existeCentrosdesalud(codigo);
    }
    
}
