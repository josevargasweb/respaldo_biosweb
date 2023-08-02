/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgCamasSalasDAO;
import com.grupobios.bioslis.dao.CfgSalasServiciosDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dto.CamasSalasDTO;
import com.grupobios.bioslis.dto.CfgServiciosDTO;
import com.grupobios.bioslis.dto.SalasServiciosDTO;
import com.grupobios.bioslis.entity.CfgCamassalas;
import com.grupobios.bioslis.entity.CfgSalasservicios;
import com.grupobios.bioslis.entity.CfgServicios;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public class LocalizacionesServiceImpl implements LocalizacionesService {

    CfgServiciosDAO servicios_dao;
    CfgSalasServiciosDAO salasServiciosDAO;
    CfgCamasSalasDAO camasSalasDAO;

    public CfgServiciosDAO getServicios_dao() {
        return servicios_dao;
    }

    public void setServicios_dao(CfgServiciosDAO servicios_dao) {
        this.servicios_dao = servicios_dao;
    }

    public CfgSalasServiciosDAO getSalasServiciosDAO() {
        return salasServiciosDAO;
    }

    public void setSalasServiciosDAO(CfgSalasServiciosDAO salasServiciosDAO) {
        this.salasServiciosDAO = salasServiciosDAO;
    }

    public CfgCamasSalasDAO getCamasSalasDAO() {
        return camasSalasDAO;
    }

    public void setCamasSalasDAO(CfgCamasSalasDAO camasSalasDAO) {
        this.camasSalasDAO = camasSalasDAO;
    }
    
    @Override
    public List<CfgServicios> getServicios(HashMap<String, String> filtros) throws BiosLISDAOException {
        return servicios_dao.getServiciosLocalizacion(filtros);
    }

    @Override
    public List<SalasServiciosDTO> getSalasServicios(int idServicio) throws BiosLISDAOException {
        return salasServiciosDAO.getSalasServicios(idServicio);
    }

    @Override
    public List<CamasSalasDTO> getCamasSalas(int idSala) throws BiosLISDAOException {
        return camasSalasDAO.getCamasSala(idSala);
    }

    @Override
    public CfgSalasservicios guardarSalaServicio(SalasServiciosDTO ssdto, Long idUsuario) throws BiosLISDAOException {
        return salasServiciosDAO.guardarSalaServicio(ssdto, idUsuario);
    }

    @Override
    public CfgSalasservicios deleteSalaServicio(CfgSalasservicios css) throws BiosLISDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CfgCamassalas guardarCamasSalas(CamasSalasDTO csdto, Long idUsuario) throws BiosLISDAOException {
        return camasSalasDAO.guardarCamaSala(csdto, idUsuario);
    }

    @Override
    public CfgCamassalas deleteCamasSalas(CfgCamassalas ccs) throws BiosLISDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
