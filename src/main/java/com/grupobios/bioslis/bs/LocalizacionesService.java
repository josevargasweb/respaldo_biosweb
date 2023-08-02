/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.CamasSalasDTO;
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
public interface LocalizacionesService {
    
    List<CfgServicios> getServicios(HashMap<String, String> filtros) throws BiosLISDAOException;
    List<SalasServiciosDTO> getSalasServicios(int idServicio) throws BiosLISDAOException;
    List<CamasSalasDTO> getCamasSalas(int idSala) throws BiosLISDAOException;
    CfgSalasservicios guardarSalaServicio(SalasServiciosDTO ssdto, Long idUsuario) throws BiosLISDAOException;
    CfgSalasservicios deleteSalaServicio(CfgSalasservicios css) throws BiosLISDAOException;
    CfgCamassalas guardarCamasSalas(CamasSalasDTO csdto, Long idUsuario) throws BiosLISDAOException;
    CfgCamassalas deleteCamasSalas(CfgCamassalas ccs) throws BiosLISDAOException;
    
}
