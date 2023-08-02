/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.util.List;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.DatosOrdenPacienteExamenesDTO;
import com.grupobios.bioslis.dto.MuestrasDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author marco.c
 */
public interface RecepcionMuestrasService {

  public List<MuestrasDTO> getListaMuestrasRM(String fecha) throws BiosLISDAOException;

  public DatosOrdenPacienteExamenesDTO getDatosOrden(Long idMuestra) throws BiosLISDAOException;

  public Long recepcionarMuestra(Long idMuestra, Long idUsuario) throws BiosLISDAOException;

  public Long recepcionarMuestraCodigoBarras(String codigoBarras, Long idUsuario) throws BiosLISDAOException;

  List<DatosUsuarios> listaRecepcionistas() throws BiosLISDAOException;

  public List<MuestrasDTO> getListaMuestrasPendientes() throws BiosLISDAOException;

  public List<MuestrasDTO> getListaMuestrasRecepcionadas() throws BiosLISDAOException;

  public List<MuestrasDTO> getListaMuestrasConDerivador() throws BiosLISDAOException;

  public void guardarObservacion(Long idMuestra, String observacion, Long idUsuario) throws BiosLISDAOException;

  public void cambiarDerivador(Long idMuestra, Short idDerivador, Long idUsuario) throws BiosLISDAOException;

  public void cambiarReceptor(Long idMuestra, Long idUsuario, Long usuario) throws BiosLISDAOException;

  public void cambiarEstadoPendiente(Long idMuestra, Long idUsuario) throws BiosLISDAOException;
}
