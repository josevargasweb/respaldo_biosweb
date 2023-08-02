/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgValoresreferencia;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface ValoresReferenciaService {
    List<CfgValoresreferencia> getValoresReferenciaByTest(long cvrIdtest) throws BiosLISDAOException;
    public void guardarValoresReferencia(List<CfgValoresreferencia> listVR, Long idUsuario) throws BiosLISDAOException;
    public void deleteValorReferencia(int idVr, Long idUsuario) throws BiosLISDAOException;
}
