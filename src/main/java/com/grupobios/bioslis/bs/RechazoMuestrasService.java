/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.FichaFiltroDTO;
import com.grupobios.bioslis.dto.MuestraRechazadaDTO;
import com.grupobios.bioslis.dto.MuestrasDTO;
import com.grupobios.bioslis.dto.OrdenesTMDTO;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.entity.DatosUsuarios;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface RechazoMuestrasService {
    List<OrdenesTMDTO> getOrdenesRechazoMuestras (FichaFiltroDTO ffDto) throws BiosLISDAOException;
    List<MuestrasDTO> getListaMuestrasRechazo(long nOrden) throws BiosLISDAOException;
    MuestraRechazadaDTO getMuestraRechazo(long idMuestra) throws BiosLISDAOException;
    DatosFichasmuestras rechazoTotalMuestra(long idMuestra, long idUsuario, byte causaRechazo, String observacion) throws BiosLISDAOException;
    DatosFichasmuestras rechazoParcialMuestra(long idMuestra, long idUsuario, byte causaRechazo, String observacion, List<String> examenesRechazados, List<String> examenesNoRechazados) throws BiosLISDAOException;
}
