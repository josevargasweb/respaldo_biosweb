/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.util.List;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.CfgExamenesDTO;
import com.grupobios.bioslis.dto.EntryPanel;
import com.grupobios.bioslis.dto.GlosaDTO;
import com.grupobios.bioslis.dto.GlosasExamenDTO;
import com.grupobios.bioslis.dto.ItemPanelExamenDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenOrden;
import com.grupobios.bioslis.entity.CfgAntecedentes;
import com.grupobios.bioslis.entity.CfgConvenios;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.CfgDiagnosticos;
import com.grupobios.bioslis.entity.CfgEnvases;
import com.grupobios.bioslis.entity.CfgEstadosexamenes;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgLocalizaciones;
import com.grupobios.bioslis.entity.CfgMedicos;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.CfgPesquisas;
import com.grupobios.bioslis.entity.CfgPrioridadatencion;
import com.grupobios.bioslis.entity.CfgProcedencias;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.CfgServicios;
import com.grupobios.bioslis.entity.CfgTipoatencion;
import com.grupobios.bioslis.entity.LdvCie10;
import com.grupobios.bioslis.entity.LdvComunas;
import com.grupobios.bioslis.entity.LdvContactospacientesrelacion;
import com.grupobios.bioslis.entity.LdvEstadosciviles;
import com.grupobios.bioslis.entity.LdvFormatos;
import com.grupobios.bioslis.entity.LdvGruposexamenes;
import com.grupobios.bioslis.entity.LdvIndicacionestm;
import com.grupobios.bioslis.entity.LdvLoinc;
import com.grupobios.bioslis.entity.LdvPaises;
import com.grupobios.bioslis.entity.LdvPatologias;
import com.grupobios.bioslis.entity.LdvRegiones;
import com.grupobios.bioslis.entity.LdvSexo;
import com.grupobios.bioslis.entity.LdvTiposdocumentos;
import com.grupobios.bioslis.entity.LdvTiposreceptores;
import com.grupobios.bioslis.entity.LdvViasnotificaciones;

/**
 *
 * @author eric.nicholls
 */
public interface DominiosService {

  public List<CfgServicios> getServicios() throws BiosLISDAOException;

  public List<CfgMuestras> getMuestras() throws BiosLISDAOException;

  public List<GlosaDTO> getGlosasResultados();

  public List<CfgProcedencias> getProcedencias() throws BiosLISDAOException;

  public List<CfgTipoatencion> getTiposAtencion() throws BiosLISDAOException;

  public List<CfgSecciones> getSecciones() throws BiosLISDAOException;

  public List<CfgSecciones> getSeccionesSm() throws BiosLISDAOException;

  public List<CfgEnvases> getEnvases() throws BiosLISDAOException;

  public List<CfgEstadosexamenes> getEstadosExamenes();

  public List<CfgExamenes> getCfgExamenes() throws BiosLISDAOException;

  public List<CfgDerivadores> getDerivadores() throws BiosLISDAOException;

  public List<CfgDerivadores> getDerivadoresSelect() throws BiosLISDAOException;

  public List<CfgEnvases> getCfgEnvases() throws BiosLISDAOException;

  public List<GlosasExamenDTO> getDominiosResultadosExamenesOrden(Long nroOrden,
      ParamResultadoExamenOrden prmResultadoExamenOrden) throws BiosLISDAOException;

  public List<EntryPanel> getPanelesExamenes() throws BiosLISDAOException;

  public List<CfgPrioridadatencion> getPrioridadesAtencion() throws BiosLISDAOException;

  public List<LdvTiposdocumentos> getTiposDeDocumentos();

  public List<CfgLocalizaciones> getLocalizaciones() throws BiosLISDAOException;

  public List<CfgExamenesDTO> getCfgExamenesIncMuestras() throws BiosLISDAOException;

  public List<CfgMuestras> getMuestrasByGrupo(long idExamen);

  public List<LdvSexo> getSexos();

  public List<LdvRegiones> getRegiones();

  public List<LdvComunas> getComunas();

  public List<LdvEstadosciviles> getEstadosCiviles();

  public List<LdvPaises> getPaises();

  public List<LdvPatologias> getPatologias();

  public List<LdvContactospacientesrelacion> getLdvContactospacientesrelacion();

  public List<ItemPanelExamenDTO> getItemPanelesExamenes() throws BiosLISDAOException, BiosLISException;

  public List<CfgMedicos> getTodosLosMedicos();

  public List<LdvTiposreceptores> getTiposReceptores() throws BiosLISDAOException;

  public List<LdvViasnotificaciones> getViasNotificaciones() throws BiosLISDAOException;

  public List<CfgDiagnosticos> getTodosLosDiagnosticos() throws BiosLISDAOException;

  public List<CfgConvenios> getTodosLosConvenios();

  public CfgSecciones getSeccionById(Long idSeccion) throws BiosLISDAOException;

  public List<CfgPesquisas> getListPesquisas() throws BiosLISDAOException;

  public List<LdvLoinc> getListLoinc() throws BiosLISDAOException;

  public List<LdvFormatos> getListFormatos() throws BiosLISDAOException;

  public List<LdvGruposexamenes> getGruposExamenes() throws BiosLISDAOException;

  public List<LdvIndicacionestm> getListIndicacionesTM() throws BiosLISDAOException;

  List<GlosaDTO> getDominiosGlosaxTest() throws BiosLISDAOException;

  public List<CfgAntecedentes> getDominiosAntedentes() throws BiosLISDAOException;

  public List<LdvCie10> getListCie10() throws BiosLISDAOException;

}
