/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgAntecedentesDAO;
import com.grupobios.bioslis.dao.CfgConvenioDAO;
import com.grupobios.bioslis.dao.CfgDerivadoresDAO;
import com.grupobios.bioslis.dao.CfgDiagnosticosDAO;
import com.grupobios.bioslis.dao.CfgEnvasesDAO;
import com.grupobios.bioslis.dao.CfgEstadosexamenesDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgLocalizacionesDAO;
import com.grupobios.bioslis.dao.CfgMedicosDAO;
import com.grupobios.bioslis.dao.CfgMuestrasDAO;
import com.grupobios.bioslis.dao.CfgPanelesExamenesDAO;
import com.grupobios.bioslis.dao.CfgPesquisasDAO;
import com.grupobios.bioslis.dao.CfgPrioridadAtencionDAO;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.CfgSeccionesDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.CfgTipoAtencionDAO;
import com.grupobios.bioslis.dao.DominiosDAO;
import com.grupobios.bioslis.dao.LdvCie10DAO;
import com.grupobios.bioslis.dao.LdvComunasDAO;
import com.grupobios.bioslis.dao.LdvContactospacientesrelacionDAO;
import com.grupobios.bioslis.dao.LdvEstadoscivilesDAO;
import com.grupobios.bioslis.dao.LdvFormatosDAO;
import com.grupobios.bioslis.dao.LdvGruposExamenesDAO;
import com.grupobios.bioslis.dao.LdvIndicacionesTMDAO;
import com.grupobios.bioslis.dao.LdvLoincDAO;
import com.grupobios.bioslis.dao.LdvPaisesDAO;
import com.grupobios.bioslis.dao.LdvPatologiasDAO;
import com.grupobios.bioslis.dao.LdvRegionesDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;
import com.grupobios.bioslis.dao.LdvTiposreceptoresDAO;
import com.grupobios.bioslis.dao.LdvViasnotificacionesDAO;
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

public class DominiosServiceImpl implements DominiosService {

  private static Logger logger = LogManager.getLogger(DominiosServiceImpl.class);

  private CfgServiciosDAO servicios_dao;
  private DominiosDAO dominiosDAO;
  private CfgSeccionesDAO cfgSeccionesDAO;
  private CfgTipoAtencionDAO cfgTipoAtencionDAO;
  private CfgProcedenciasDAO cfgProcedenciasDAO;
  private CfgMuestrasDAO cfgMuestrasDAO;
  private CfgEnvasesDAO cfgEnvasesDAO;
  private CfgEstadosexamenesDAO cfgEstadosexamenesDAO;
  private CfgExamenesDAO cfgExamenesDAO;
  private CfgDerivadoresDAO cfgDerivadoresDAO;
  private CfgPanelesExamenesDAO cfgPanelesExamenesDAO;
  private CfgPrioridadAtencionDAO cfgPrioridadAtencionDAO;
  private CfgLocalizacionesDAO cfgLocalizacionDAO;
  private LdvTiposdocumentosDAO ldvTiposdocumentos_dao;
  private LdvSexoDAO ldvSexosDAO;
  private LdvRegionesDAO ldvRegionesDAO;
  private LdvComunasDAO ldvComunasDAO;
  private LdvContactospacientesrelacionDAO ldvContactospacientesrelacionDAO;
  private LdvEstadoscivilesDAO ldvEstadoscivilesDAO;
  private LdvPaisesDAO ldvPaisesDAO;
  private LdvPatologiasDAO ldvPatologiasDAO;
  private CfgMedicosDAO cfgMedicosDAO;
  private CfgConvenioDAO cfgConvenioDAO;
  private CfgDiagnosticosDAO cfgDiagnosticosDAO;
  private LdvTiposreceptoresDAO ldvTiposreceptoresDAO;
  private LdvViasnotificacionesDAO ldvViasnotificacionesDAO;
  private CfgPesquisasDAO cfgPesquisasDAO;
  private LdvLoincDAO ldvLoincDAO;
  private LdvFormatosDAO ldvFormatosDAO;
  private LdvGruposExamenesDAO ldvGruposExamenesDAO;
  private LdvIndicacionesTMDAO ldvIndicacionesTMDAO;
  private CfgAntecedentesDAO cfgaDAO;

  @Override
  public List<CfgSecciones> getSecciones() throws BiosLISDAOException {

    List<CfgSecciones> lst = cfgSeccionesDAO.getSecciones();
    /*
     * CfgSecciones seccionNoMicroBiologia = new CfgSecciones();
     * 
     * seccionNoMicroBiologia.setCsecActiva("N");
     * seccionNoMicroBiologia.setCsecCodigo(Constante.CODIGO_NO_MICROBIOLOGIA);
     * seccionNoMicroBiologia.setCsecDescripcion(Constante.
     * DESCRIPCION_NO_MICROBIOLOGIA);
     * seccionNoMicroBiologia.setCsecIdseccion(99);
     * lst.add(seccionNoMicroBiologia);
     */

    return lst;
  }

  @Override
  public List<CfgSecciones> getSeccionesSm() throws BiosLISDAOException {
    return cfgSeccionesDAO.getSeccionesSM();
  }

  public CfgSeccionesDAO getCfgSeccionesDAO() {
    return cfgSeccionesDAO;
  }

  public void setCfgSeccionesDAO(CfgSeccionesDAO cfgSeccionesDAO) {
    this.cfgSeccionesDAO = cfgSeccionesDAO;
  }

  @Override
  public List<CfgServicios> getServicios() throws BiosLISDAOException {
    return servicios_dao.getServicios();
  }

  public CfgServiciosDAO getServicios_dao() {
    return servicios_dao;
  }

  public void setServicios_dao(CfgServiciosDAO servicios_dao) {
    this.servicios_dao = servicios_dao;
  }

  @Override
  public List<GlosaDTO> getGlosasResultados() {

    return this.dominiosDAO.getGlosasResultados();
  }

  public DominiosDAO getDominiosDAO() {
    return dominiosDAO;
  }

  public void setDominiosDAO(DominiosDAO dominiosDAO) {
    this.dominiosDAO = dominiosDAO;
  }

  @Override
  public List<CfgTipoatencion> getTiposAtencion() throws BiosLISDAOException {
    return this.cfgTipoAtencionDAO.getTiposAtencion();
  }

  public CfgTipoAtencionDAO getCfgTipoAtencionDAO() {
    return cfgTipoAtencionDAO;
  }

  public void setCfgTipoAtencionDAO(CfgTipoAtencionDAO cfgTipoAtencionDAO) {
    this.cfgTipoAtencionDAO = cfgTipoAtencionDAO;
  }

  @Override
  public List<CfgProcedencias> getProcedencias() throws BiosLISDAOException {
    return cfgProcedenciasDAO.getProcedencias();
  }

  @Override
  public List<CfgEnvases> getEnvases() throws BiosLISDAOException {
    return this.cfgEnvasesDAO.getSelectListEnvases();
  }

  @Override
  public List<CfgDerivadores> getDerivadores() throws BiosLISDAOException {
    return this.cfgDerivadoresDAO.getDerivadores();
  }

  @Override
  public List<CfgDerivadores> getDerivadoresSelect() throws BiosLISDAOException {
    return this.cfgDerivadoresDAO.getDerivadoresSelect();
  }

  public CfgEnvasesDAO getCfgEnvasesDAO() {
    return cfgEnvasesDAO;
  }

  public void setCfgEnvasesDAO(CfgEnvasesDAO cfgEnvasesDAO) {
    this.cfgEnvasesDAO = cfgEnvasesDAO;
  }

  public CfgDerivadoresDAO getCfgDerivadoresDAO() {
    return cfgDerivadoresDAO;
  }

  public void setCfgDerivadoresDAO(CfgDerivadoresDAO cfgDerivadoresDAO) {
    this.cfgDerivadoresDAO = cfgDerivadoresDAO;
  }

  @Override
  public List<CfgExamenes> getCfgExamenes() throws BiosLISDAOException {
    return cfgExamenesDAO.getExamenes();
  }

  /**
   * @return the cfgExamenesDAO
   */
  public CfgExamenesDAO getCfgExamenesDAO() {
    return cfgExamenesDAO;
  }

  /**
   * @param cfgExamenesDAO the cfgExamenesDAO to set
   */
  public void setCfgExamenesDAO(CfgExamenesDAO cfgExamenesDAO) {
    this.cfgExamenesDAO = cfgExamenesDAO;
  }

  @Override
  public List<CfgEstadosexamenes> getEstadosExamenes() {

    return cfgEstadosexamenesDAO.getEstados();
  }

  public CfgEstadosexamenesDAO getCfgEstadosexamenesDAO() {
    return cfgEstadosexamenesDAO;
  }

  public void setCfgEstadosexamenesDAO(CfgEstadosexamenesDAO cfgEstadosexamenesDAO) {
    this.cfgEstadosexamenesDAO = cfgEstadosexamenesDAO;
  }

  @Override
  public List<CfgMuestras> getMuestras() throws BiosLISDAOException {
    return this.cfgMuestrasDAO.getListMuestras();
  }

  public CfgMuestrasDAO getCfgMuestrasDAO() {
    return cfgMuestrasDAO;
  }

  @Override
  public List<CfgEnvases> getCfgEnvases() throws BiosLISDAOException {
    return cfgEnvasesDAO.getSelectListEnvases();
  }

  public void setCfgProcedenciasDAO(CfgProcedenciasDAO cfgProcedenciasDAO) {
    this.cfgProcedenciasDAO = cfgProcedenciasDAO;
  }

  public void setCfgMuestrasDAO(CfgMuestrasDAO cfgMuestrasDAO) {
    this.cfgMuestrasDAO = cfgMuestrasDAO;
  }

  public CfgProcedenciasDAO getCfgProcedenciasDAO() {
    return cfgProcedenciasDAO;
  }

  @Override
  public List<GlosasExamenDTO> getDominiosResultadosExamenesOrden(Long nroOrden,
      ParamResultadoExamenOrden prmResultadoExamenOrden) throws BiosLISDAOException {
    return dominiosDAO.getDominiosResultadosExamenesOrden(nroOrden, prmResultadoExamenOrden);

  }

  public CfgPanelesExamenesDAO getCfgPanelesExamenesDAO() {
    return cfgPanelesExamenesDAO;
  }

  public void setCfgPanelesExamenesDAO(CfgPanelesExamenesDAO cfgPanelesExamenesDAO) {
    this.cfgPanelesExamenesDAO = cfgPanelesExamenesDAO;
  }

  @Override
  public List<EntryPanel> getPanelesExamenes() throws BiosLISDAOException {

    Map<String, ArrayList<HashMap<BigDecimal, ArrayList<BigDecimal>>>> hmPanelesExamenes = cfgPanelesExamenesDAO
        .getAll();

    List<EntryPanel> lstEP = new ArrayList<EntryPanel>();
    for (String panelName : hmPanelesExamenes.keySet()) {
      EntryPanel ep = new EntryPanel(panelName, hmPanelesExamenes.get(panelName));
      lstEP.add(ep);
    }

    return lstEP;
  }

  @Override
  public List<GlosaDTO> getDominiosGlosaxTest() throws BiosLISDAOException {

    return this.dominiosDAO.getDominiosGlosaxTest();
  }

  @Override
  public List<CfgPrioridadatencion> getPrioridadesAtencion() throws BiosLISDAOException {
    return cfgPrioridadAtencionDAO.getPrior();
  }

  public CfgPrioridadAtencionDAO getCfgPrioridadAtencionDAO() {
    return cfgPrioridadAtencionDAO;
  }

  public void setCfgPrioridadAtencionDAO(CfgPrioridadAtencionDAO cfgPrioridadAtencionDAO) {
    this.cfgPrioridadAtencionDAO = cfgPrioridadAtencionDAO;
  }

  @Override
  public List<LdvTiposdocumentos> getTiposDeDocumentos() {
    return ldvTiposdocumentos_dao.getTiposDocumentos();
  }

  @Override
  public List<CfgLocalizaciones> getLocalizaciones() throws BiosLISDAOException {
    return cfgLocalizacionDAO.getLocalizaciones();
  }

  public CfgLocalizacionesDAO getCfgLocalizacionDAO() {
    return cfgLocalizacionDAO;
  }

  public void setCfgLocalizacionDAO(CfgLocalizacionesDAO cfgLocalizacionDAO) {
    this.cfgLocalizacionDAO = cfgLocalizacionDAO;
  }

  public LdvTiposdocumentosDAO getLdvTiposdocumentos_dao() {
    return ldvTiposdocumentos_dao;
  }

  public void setLdvTiposdocumentos_dao(LdvTiposdocumentosDAO ldvTiposdocumentos_dao) {
    this.ldvTiposdocumentos_dao = ldvTiposdocumentos_dao;
  }

  @Override
  public List<CfgExamenesDTO> getCfgExamenesIncMuestras() throws BiosLISDAOException {
    return cfgExamenesDAO.getCfgExamenesIncMuestras();
  }

  @Override
  public List<LdvSexo> getSexos() {
    return ldvSexosDAO.getSexo();
  }

  public LdvSexoDAO getLdvSexosDAO() {
    return ldvSexosDAO;
  }

  public void setLdvSexosDAO(LdvSexoDAO ldvSexosDAO) {
    this.ldvSexosDAO = ldvSexosDAO;
  }

  @Override
  public List<LdvRegiones> getRegiones() {

    return ldvRegionesDAO.getRegiones();
  }

  @Override
  public List<LdvComunas> getComunas() {
    return ldvComunasDAO.getComunas();
  }

  public LdvRegionesDAO getLdvRegionesDAO() {
    return ldvRegionesDAO;
  }

  public void setLdvRegionesDAO(LdvRegionesDAO ldvRegionesDAO) {
    this.ldvRegionesDAO = ldvRegionesDAO;
  }

  public LdvComunasDAO getLdvComunasDAO() {
    return ldvComunasDAO;
  }

  public void setLdvComunasDAO(LdvComunasDAO ldvComunasDAO) {
    this.ldvComunasDAO = ldvComunasDAO;
  }

  public List<LdvContactospacientesrelacion> getLdvContactospacientesrelacion() {

    return ldvContactospacientesrelacionDAO.getContactosRelacion();
  }

  public LdvContactospacientesrelacionDAO getLdvContactospacientesrelacionDAO() {
    return ldvContactospacientesrelacionDAO;
  }

  public void setLdvContactospacientesrelacionDAO(LdvContactospacientesrelacionDAO ldvContactospacientesrelacionDAO) {
    this.ldvContactospacientesrelacionDAO = ldvContactospacientesrelacionDAO;
  }

  public List<LdvEstadosciviles> getEstadosCiviles() {

    return ldvEstadoscivilesDAO.getEstadosCiviles();
  }

  public LdvEstadoscivilesDAO getLdvEstadoscivilesDAO() {
    return ldvEstadoscivilesDAO;
  }

  public void setLdvEstadoscivilesDAO(LdvEstadoscivilesDAO ldvEstadoscivilesDAO) {
    this.ldvEstadoscivilesDAO = ldvEstadoscivilesDAO;
  }

  @Override
  public List<LdvPaises> getPaises() {
    return ldvPaisesDAO.getPaises();
  }

  public LdvPaisesDAO getLdvPaisesDAO() {
    return ldvPaisesDAO;
  }

  public void setLdvPaisesDAO(LdvPaisesDAO ldvPaisesDAO) {
    this.ldvPaisesDAO = ldvPaisesDAO;
  }

  public LdvPatologiasDAO getLdvPatologiasDAO() {
    return ldvPatologiasDAO;
  }

  public void setLdvPatologiasDAO(LdvPatologiasDAO ldvPatologiasDAO) {
    this.ldvPatologiasDAO = ldvPatologiasDAO;
  }

  public List<LdvPatologias> getPatologias() {
    return ldvPatologiasDAO.getPatologias();

  }

  @Override
  public List<ItemPanelExamenDTO> getItemPanelesExamenes() throws BiosLISDAOException, BiosLISException {
    return cfgPanelesExamenesDAO.getExamenesxPanel();
  }

  @Override
  public List<CfgMuestras> getMuestrasByGrupo(long idExamen) {
    return cfgMuestrasDAO.getMuestrasByGrupo(idExamen);
  }

  public List<CfgMedicos> getTodosLosMedicos() {
    return cfgMedicosDAO.getAllMedics();
  }

  public CfgMedicosDAO getCfgMedicosDAO() {
    return this.cfgMedicosDAO;
  }

  public void setCfgMedicosDAO(CfgMedicosDAO cfgMedicosDAO) {
    this.cfgMedicosDAO = cfgMedicosDAO;
  }

  public List<CfgConvenios> getTodosLosConvenios() {
    return cfgConvenioDAO.getConvenios();
  }

  public CfgConvenioDAO getCfgConvenioDAO() {
    return this.cfgConvenioDAO;
  }

  public void setCfgConvenioDAO(CfgConvenioDAO cfgConvenioDAO) {
    this.cfgConvenioDAO = cfgConvenioDAO;
  }

  public List<CfgDiagnosticos> getTodosLosDiagnosticos() throws BiosLISDAOException {
    return cfgDiagnosticosDAO.getDiagnosticos();
  }

  public CfgDiagnosticosDAO getCfgDiagnosticosDAO() {
    return this.cfgDiagnosticosDAO;
  }

  public void setCfgDiagnosticosDAO(CfgDiagnosticosDAO cfgDiagnosticosDAO) {
    this.cfgDiagnosticosDAO = cfgDiagnosticosDAO;
  }

  @Override
  public List<LdvTiposreceptores> getTiposReceptores() throws BiosLISDAOException {
    return ldvTiposreceptoresDAO.getTiposReceptores();
  }

  public LdvTiposreceptoresDAO getLdvTiposreceptoresDAO() {
    return ldvTiposreceptoresDAO;
  }

  public void setLdvTiposreceptoresDAO(LdvTiposreceptoresDAO ldvTiposreceptoresDAO) {
    this.ldvTiposreceptoresDAO = ldvTiposreceptoresDAO;
  }

  @Override
  public List<LdvViasnotificaciones> getViasNotificaciones() throws BiosLISDAOException {
    return ldvViasnotificacionesDAO.getViasNotificaciones();
  }

  public LdvViasnotificacionesDAO getLdvViasnotificacionesDAO() {
    return ldvViasnotificacionesDAO;
  }

  public void setLdvViasnotificacionesDAO(LdvViasnotificacionesDAO ldvViasnotificacionesDAO) {
    this.ldvViasnotificacionesDAO = ldvViasnotificacionesDAO;
  }

  @Override
  public CfgSecciones getSeccionById(Long idSeccion) throws BiosLISDAOException {
    return cfgSeccionesDAO.getSeccionById(idSeccion.intValue());
  }

  public CfgPesquisasDAO getCfgPesquisasDAO() {
    return cfgPesquisasDAO;
  }

  public void setCfgPesquisasDAO(CfgPesquisasDAO cfgPesquisasDAO) {
    this.cfgPesquisasDAO = cfgPesquisasDAO;
  }

  public LdvLoincDAO getLdvLoincDAO() {
    return ldvLoincDAO;
  }

  public void setLdvLoincDAO(LdvLoincDAO ldvLoincDAO) {
    this.ldvLoincDAO = ldvLoincDAO;
  }

  public LdvFormatosDAO getLdvFormatosDAO() {
    return ldvFormatosDAO;
  }

  public void setLdvFormatosDAO(LdvFormatosDAO ldvFormatosDAO) {
    this.ldvFormatosDAO = ldvFormatosDAO;
  }

  public LdvGruposExamenesDAO getLdvGruposExamenesDAO() {
    return ldvGruposExamenesDAO;
  }

  public void setLdvGruposExamenesDAO(LdvGruposExamenesDAO ldvGruposExamenesDAO) {
    this.ldvGruposExamenesDAO = ldvGruposExamenesDAO;
  }

  public LdvIndicacionesTMDAO getLdvIndicacionesTMDAO() {
    return ldvIndicacionesTMDAO;
  }

  public void setLdvIndicacionesTMDAO(LdvIndicacionesTMDAO ldvIndicacionesTMDAO) {
    this.ldvIndicacionesTMDAO = ldvIndicacionesTMDAO;
  }

  @Override
  public List<CfgPesquisas> getListPesquisas() throws BiosLISDAOException {
    return cfgPesquisasDAO.getListPesquisas();
  }

  @Override
  public List<LdvLoinc> getListLoinc() throws BiosLISDAOException {
    return ldvLoincDAO.getListLoinc();
  }

  @Override
  public List<LdvFormatos> getListFormatos() throws BiosLISDAOException {
    return ldvFormatosDAO.getListFormatos();
  }

  @Override
  public List<LdvGruposexamenes> getGruposExamenes() throws BiosLISDAOException {
    return ldvGruposExamenesDAO.getGruposExamenes();
  }

  @Override
  public List<LdvIndicacionestm> getListIndicacionesTM() throws BiosLISDAOException {
    return ldvIndicacionesTMDAO.getListIndicacionesTM();
  }

  @Override
  public List<CfgAntecedentes> getDominiosAntedentes() throws BiosLISDAOException {
    return cfgaDAO.getAntecedentes();
  }

  public CfgAntecedentesDAO getCfgaDAO() {
    return cfgaDAO;
  }

  public void setCfgaDAO(CfgAntecedentesDAO cfgaDAO) {
    this.cfgaDAO = cfgaDAO;
  }

  LdvCie10DAO cie10DAO;

  public LdvCie10DAO getCie10DAO() {
    return cie10DAO;
  }

  public void setCie10DAO(LdvCie10DAO cie10DAO) {
    this.cie10DAO = cie10DAO;
  }

  @Override
  public List<LdvCie10> getListCie10() throws BiosLISDAOException {
    return cie10DAO.getListCie10();
  }

}
