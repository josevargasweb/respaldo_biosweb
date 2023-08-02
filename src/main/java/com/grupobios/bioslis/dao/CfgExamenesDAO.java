/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.grupobios.bioslis.cfg.cache.CfgCentrosDeSaludAbsDao;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.config.SpringContext;
import com.grupobios.bioslis.dto.CfgExamenesDTO;
import com.grupobios.bioslis.dto.ExamenFullDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.TestRepetidosDTO;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.LogCfgtablas;

public class CfgExamenesDAO {

  private static Logger logger = LogManager.getLogger(CfgExamenesDAO.class);
  private static ValidacionMantenedor validador = new ValidacionMantenedor();

  @Autowired
  LogCfgTablasDAO logCfgTablasDAO;

  public LogCfgTablasDAO getLogCfgTablasDAO() {
    return logCfgTablasDAO;
  }

  public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
    this.logCfgTablasDAO = logCfgTablasDAO;
  }

  public String insertExamenes(CfgExamenes ce) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String mensaje = "E";
    try {
      if (validador.validarCodigoyAbreviado("CE_CODIGOEXAMEN", "CE_ABREVIADO", "CFG_EXAMENES", ce.getCeCodigoexamen(),
          ce.getCeAbreviado())) {
        sesion.beginTransaction();
        sesion.save(ce);
        sesion.flush();
        sesion.getTransaction().commit();
        mensaje = "C";
      }

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  public Long insertExamenesNativo(ExamenFullDTO efdto, short idDerivador, Long idUsuario) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    BigDecimal newIdExamen = new BigDecimal("0");

    try {
      CfgExamenes ce = efdto.getCfgExamenes();

      String insertFields2 = "";
      String insertValues2 = "";
      if (efdto.getCfgExamenesconjuntos() != null) {
        insertFields2 += ", CE_IDEXAMENESCONJUNTOS";
        insertValues2 += ", :CE_IDEXAMENESCONJUNTOS";
      }
      if (efdto.getLdvIndicacionesexamenes() != null) {
        insertFields2 += ", CE_IDINDICACIONEXAMEN";
        insertValues2 += ", :CE_IDINDICACIONEXAMEN";
      }
      if (efdto.getLdvFormatos() != null) {
        insertFields2 += ", CE_IDFORMATO";
        insertValues2 += ", :CE_IDFORMATO";
      }
      if (efdto.getLdvTiposexamenes() != null) {
        insertFields2 += ", CE_IDTIPOEXAMEN";
        insertValues2 += ", :CE_IDTIPOEXAMEN";
      }
      if (efdto.getCfgGruposexamenesfonasa() != null) {
        insertFields2 += ", CE_IDGRUPOEXAMENFONASA";
        insertValues2 += ", :CE_IDGRUPOEXAMENFONASA";
      }
      // if (efdto.getLdvLoinc()!=null){
      // insertFields2 += ", CE_CODIGOLOINC";
      // insertValues2 += ", :CE_CODIGOLOINC";
      // }
      if (efdto.getCfgMuestras() != null) {
        insertFields2 += ", CE_IDTIPOMUESTRA";
        insertValues2 += ", :CE_IDTIPOMUESTRA";
      }
      if (efdto.getLdvGruposexamenes().getLdvgeIdgrupoexamen() != null) {
        logger.debug(efdto.getLdvGruposexamenes().getLdvgeIdgrupoexamen());
        insertFields2 += ", CE_IDGRUPOEXAMEN";
        insertValues2 += ", :CE_IDGRUPOEXAMEN";
      }
      if (efdto.getCfgMetodos() != null) {
        insertFields2 += ", CE_IDMETODO";
        insertValues2 += ", :CE_IDMETODO";
      }
      Query query = sesion
          .createSQLQuery(insertIntoFields + insertFields2 + ") " + insertIntoValues + insertValues2 + ")");
      sesion.beginTransaction();
      query.setString("CE_CODIGOEXAMEN", ce.getCeCodigoexamen());
      query.setString("CE_DESCRIPCION", ce.getCeDescripcion());
      query.setString("CE_ACTIVO", ce.getCeActivo());
      query.setString("CE_ABREVIADO", ce.getCeAbreviado());
      query.setString("CE_ABREVIADO2", ce.getCeAbreviado2());
      query.setString("CE_IMPRIMIRSEPARADO", ce.getCeImprimirseparado());
      query.setString("CE_IMPRIMIRDESCRIPCION", ce.getCeImprimirdescripcion());
      query.setString("CE_IMPRIMIRPORSECCION", ce.getCeImprimirporseccion());
      query.setString("CE_IMPRIMIRTABLA", ce.getCeImprimirtabla());
      query.setString("CE_MULTIFORMATO", ce.getCeMultiformato());
      // query.setParameter("CE_IDTABLAREFERENCIAEXAMEN", null);
      query.setString("CE_SELECCIONABLE", ce.getCeSeleccionable());
      query.setString("CE_HOST", ce.getCeHost());
      query.setString("CE_HOST2", ce.getCeHost2());
      query.setString("CE_HOST3", ce.getCeHost3());
      query.setString("CE_HOSTMICRO", ce.getCeHostmicro());
      query.setString("CE_CODIGOFONASA", ce.getCeCodigofonasa());
      query.setInteger("CE_IDSECCION", efdto.getCfgSecciones().getCsecIdseccion());
      query.setShort("CE_SORTSECCION", ce.getCeSortseccion());
      // query.setParameter("CE_IDGRUPOMUESTRA", 0);
      // query.setShort("CE_IDTIPOMUESTRA", efdto.getCfgMuestras()!=null?
      // efdto.getCfgMuestras().getCmueIdtipomuestra(): 0);
      query.setString("CE_ESTADISTICAS", ce.getCeEstadisticas());
      query.setString("CE_NOTA", ce.getCeNota());
      query.setString("CE_ESPESQUISA", ce.getCeEspesquisa());
      query.setString("CE_PESQUISAOBLIGATORIA", ce.getCePesquisaobligatoria());
      query.setShort("CE_IDPESQUISA",
          efdto.getCfgPesquisas() != null ? efdto.getCfgPesquisas().getCpeIdpesquisa() : Short.valueOf("0"));
      query.setString("CE_DATOSMUESTRA", ce.getCeDatosmuestra());
      query.setInteger("CE_TIEMPOENTREGANORMAL", ce.getCeTiempoentreganormal());
      query.setInteger("CE_TIEMPOENTREGAURGENTE", ce.getCeTiempoentregaurgente());
      query.setInteger("CE_TIEMPOENTREGAHOSPITALIZADO", ce.getCeTiempoentregahospitalizado());
      query.setString("CE_DIAPROCESOLUNES", ce.getCeDiaprocesolunes());
      query.setString("CE_DIAPROCESOMARTES", ce.getCeDiaprocesomartes());
      query.setString("CE_DIAPROCESOMIERCOLES", ce.getCeDiaprocesomiercoles());
      query.setString("CE_DIAPROCESOJUEVES", ce.getCeDiaprocesojueves());
      query.setString("CE_DIAPROCESOVIERNES", ce.getCeDiaprocesoviernes());
      query.setString("CE_DIAPROCESOSABADO", ce.getCeDiaprocesosabado());
      query.setString("CE_DIAPROCESODOMINGO", ce.getCeDiaprocesodomingo());

      // if (ce.getCeVolumenmuestraadulto() == null ||
      // ce.getCeVolumenmuestraadulto().equals("")) {
      // query.setInteger("CE_VOLUMENMUESTRAADULTO",0);
      // }else {
      query.setParameter("CE_VOLUMENMUESTRAADULTO", (float) ce.getCeVolumenmuestraadulto());
      // }
      // if (ce.getCeVolumenmuestrapediatrica() == null ||
      // ce.getCeVolumenmuestrapediatrica().equals("")) {
      // query.setInteger("CE_VOLUMENMUESTRAPEDIATRICA",0);
      // }else {
      query.setParameter("CE_VOLUMENMUESTRAPEDIATRICA", (float) ce.getCeVolumenmuestraadulto());
      // }
      query.setString("CE_ESTABILIDADAMBIENTAL", ce.getCeEstabilidadambiental());
      query.setString("CE_ESTABILIDADREFRIGERADO", ce.getCeEstabilidadrefrigerado());
      query.setString("CE_ESTABILIDADCONGELADO", ce.getCeEstabilidadcongelado());
      query.setShort("CE_CONTABLECANTIDAD", ce.getCeContablecantidad());
      query.setString("CE_ESEXAMEN", ce.getCeEsexamen());
      query.setString("CE_TIENETESTOPCIONALES", ce.getCeTienetestopcionales());
      query.setString("CE_ESURGENTE", ce.getCeEsurgente());
      query.setString("CE_ESCULTIVO", ce.getCeEscultivo());
      query.setShort("CE_NUMERODEETIQUETAS", ce.getCeNumerodeetiquetas());
      query.setString("CE_ESCONFIDENCIAL", ce.getCeEsconfidencial());
      // query.setShort("CE_IDGRUPOEXAMEN", ce.getLdvGruposexamenes()!=null ?
      // ce.getLdvGruposexamenes().getLdvgeIdgrupoexamen() : new Short("1"));
      query.setShort("CE_IDDERIVADOR", idDerivador);
      query.setString("CE_ESINDICADOR", ce.getCeEsindicador());
      query.setString("CE_NOMBREWEB", ce.getCeNombreweb());
      query.setString("CE_DISPONIBLEWEB", ce.getCeDisponibleweb());
      query.setString("CE_DIAGNOSTICOOBLIGATORIO", ce.getCeDiagnosticoobligatorio());
      query.setString("CE_HOJATRABAJO", ce.getCeHojatrabajo());
      query.setString("CE_ESCURVATOLERANCIA", ce.getCeEscurvatolerancia());
      query.setString("CE_PLANILLAHISTORICA", ce.getCePlanillahistorica());
      query.setInteger("CE_DIASDEVALIDEZ", ce.getCeDiasdevalidez());
      query.setString("CE_DIASVALIDEZAMBULATORIOBLQ", ce.getCeDiasvalidezambulatorioblq());
      query.setShort("CE_DIASVALIDEZHOSPITALIZADO", ce.getCeDiasvalidezhospitalizado());
      query.setString("CE_DIASVALIDEZHOSPITALIZADOBLQ", ce.getCeDiasvalidezhospitalizadoblq());
      query.setString("CE_ESEXAMENMULTISECCION", ce.getCeEsexamenmultiseccion());
      query.setString("CE_AUTOVALIDAR", ce.getCeAutovalidar());
      query.setString("CE_COMPARTEMUESTRA", ce.getCeCompartemuestra());
      query.setString("CE_TIENEGRUPOMUESTRA", ce.getCeTienegrupomuestra());
      query.setString("CE_CELLCOUNTER", ce.getCeCellcounter());
      if (efdto.getCfgExamenesconjuntos() != null) {
        query.setLong("CE_IDEXAMENESCONJUNTOS", efdto.getCfgExamenesconjuntos().getCecIdexamenesconjuntos());
      }

      if (efdto.getLdvIndicacionesexamenes() != null) {
        query.setParameter("CE_IDINDICACIONEXAMEN", efdto.getLdvIndicacionesexamenes().getLdvieIdindicacionesexamen());
      }
      if (efdto.getLdvFormatos() != null) {
        query.setShort("CE_IDFORMATO", efdto.getLdvFormatos().getLdvfIdformato());
      }
      if (efdto.getLdvTiposexamenes() != null) {
        query.setShort("CE_IDTIPOEXAMEN", efdto.getLdvTiposexamenes().getLdvteIdtipoexamen());
      }
      if (efdto.getCfgGruposexamenesfonasa() != null) {
        query.setShort("CE_IDGRUPOEXAMENFONASA", efdto.getCfgGruposexamenesfonasa().getCgefIdgrupoexamenfonasa());
      }
      // if (efdto.getLdvLoinc()!=null){
      // query.setString("CE_CODIGOLOINC", efdto.getLdvLoinc().getLdvlCodigoloinc());
      // }
      if (efdto.getCfgMuestras() != null) {
        query.setShort("CE_IDTIPOMUESTRA", efdto.getCfgMuestras().getCmueIdtipomuestra());
      }
      if (efdto.getLdvGruposexamenes() != null) {

        if (efdto.getLdvGruposexamenes().getLdvgeIdgrupoexamen() != null) {
          query.setParameter("CE_IDGRUPOEXAMEN", efdto.getLdvGruposexamenes().getLdvgeIdgrupoexamen());
          logger.debug("not null");
        } else {
          logger.debug("null");
          // query.setParameter("CE_IDGRUPOEXAMEN", null);
        }
      }
      logger.debug(efdto.getLdvGruposexamenes());
      if (efdto.getCfgMetodos() != null) {
        query.setInteger("CE_IDMETODO", efdto.getCfgMetodos().getCmetIdmetodo());
      }
      query.executeUpdate();

      newIdExamen = this.getNewIdExamen();

      // aqui se realiza insert de log tablas
      LogCfgtablas lct = new LogCfgtablas();
      lct.setLctIdtabla(newIdExamen.intValue());
      lct.setCfgAccionesdatos(1);
      lct.setLctNombretabla("CFG_EXAMENES");
      lct.setLctValornuevo("");
      lct.setLctIdusuario(idUsuario.intValue());
      lct.setLctValoranterior("");
      lct.setLctDescripcion(ce.getCeAbreviado());

      logCfgTablasDAO.insertLogTablas(lct, sesion);

      // ***********************************************************
      sesion.getTransaction().commit();
      sesion.close();

      return newIdExamen.longValue();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  public void updateExamenesNativo(ExamenFullDTO efdto, short idDerivador) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      CfgExamenes ce = efdto.getCfgExamenes();
      String updateFieldsNotNull = "";
      if (efdto.getCfgExamenesconjuntos() != null) {
        updateFieldsNotNull += ", CE_IDEXAMENESCONJUNTOS=:CE_IDEXAMENESCONJUNTOS";
      }
      if (efdto.getLdvIndicacionesexamenes() != null) {
        updateFieldsNotNull += ", CE_IDINDICACIONEXAMEN=:CE_IDINDICACIONEXAMEN";
      }
      if (efdto.getLdvFormatos() != null) {
        updateFieldsNotNull += ", CE_IDFORMATO=:CE_IDFORMATO";
      } else {
        updateFieldsNotNull += ", CE_IDFORMATO=NULL ";
      }
      if (efdto.getLdvTiposexamenes() != null) {
        updateFieldsNotNull += ", CE_IDTIPOEXAMEN=:CE_IDTIPOEXAMEN";
      } else {
        updateFieldsNotNull += ", CE_IDTIPOEXAMEN=NULL ";
      }
      if (efdto.getCfgGruposexamenesfonasa() != null) {
        updateFieldsNotNull += ", CE_IDGRUPOEXAMENFONASA=:CE_IDGRUPOEXAMENFONASA";
      }
      // if (efdto.getLdvLoinc()!=null){
      // updateFieldsNotNull += ", CE_CODIGOLOINC=:CE_CODIGOLOINC";
      // }
      if (efdto.getCfgMuestras() != null) {
        updateFieldsNotNull += ", CE_IDTIPOMUESTRA=:CE_IDTIPOMUESTRA";
      }
      if (efdto.getLdvGruposexamenes() != null && efdto.getLdvGruposexamenes().getLdvgeIdgrupoexamen() != 0) {
        updateFieldsNotNull += ", CE_IDGRUPOEXAMEN=:CE_IDGRUPOEXAMEN";
      }
      if (efdto.getCfgMetodos() != null) {
        updateFieldsNotNull += ", CE_IDMETODO=:CE_IDMETODO";
      }
      Query query = sesion.createSQLQuery(updateExamenSql + updateFieldsNotNull + " WHERE CE_IDEXAMEN=:CE_IDEXAMEN");
      sesion.beginTransaction();
      query.setString("CE_CODIGOEXAMEN", ce.getCeCodigoexamen());
      query.setString("CE_DESCRIPCION", ce.getCeDescripcion());
      query.setString("CE_ACTIVO", ce.getCeActivo());
      query.setString("CE_ABREVIADO", ce.getCeAbreviado());
      query.setString("CE_ABREVIADO2", ce.getCeAbreviado2());
      query.setString("CE_IMPRIMIRSEPARADO", ce.getCeImprimirseparado());
      query.setString("CE_IMPRIMIRDESCRIPCION", ce.getCeImprimirdescripcion());
      query.setString("CE_IMPRIMIRPORSECCION", ce.getCeImprimirporseccion());
      query.setString("CE_IMPRIMIRTABLA", ce.getCeImprimirtabla());
      query.setString("CE_MULTIFORMATO", ce.getCeMultiformato());
      query.setString("CE_SELECCIONABLE", ce.getCeSeleccionable());
      query.setString("CE_HOST", ce.getCeHost());
      query.setString("CE_HOST2", ce.getCeHost2());
      query.setString("CE_HOST3", ce.getCeHost3());
      query.setString("CE_HOSTMICRO", ce.getCeHostmicro());
      query.setString("CE_CODIGOFONASA", ce.getCeCodigofonasa());
      query.setInteger("CE_IDSECCION", efdto.getCfgSecciones().getCsecIdseccion());
      query.setShort("CE_SORTSECCION", ce.getCeSortseccion());
      query.setString("CE_ESTADISTICAS", ce.getCeEstadisticas());
      query.setString("CE_NOTA", ce.getCeNota());
      query.setString("CE_ESPESQUISA", ce.getCeEspesquisa());
      query.setString("CE_PESQUISAOBLIGATORIA", ce.getCePesquisaobligatoria());
      query.setShort("CE_IDPESQUISA",
          efdto.getCfgPesquisas() != null ? efdto.getCfgPesquisas().getCpeIdpesquisa() : Short.valueOf("0"));
      query.setString("CE_DATOSMUESTRA", ce.getCeDatosmuestra());
      query.setInteger("CE_TIEMPOENTREGANORMAL", ce.getCeTiempoentreganormal());
      query.setInteger("CE_TIEMPOENTREGAURGENTE", ce.getCeTiempoentregaurgente());
      query.setInteger("CE_TIEMPOENTREGAHOSPITALIZADO", ce.getCeTiempoentregahospitalizado());
      query.setString("CE_DIAPROCESOLUNES", ce.getCeDiaprocesolunes());
      query.setString("CE_DIAPROCESOMARTES", ce.getCeDiaprocesomartes());
      query.setString("CE_DIAPROCESOMIERCOLES", ce.getCeDiaprocesomiercoles());
      query.setString("CE_DIAPROCESOJUEVES", ce.getCeDiaprocesojueves());
      query.setString("CE_DIAPROCESOVIERNES", ce.getCeDiaprocesoviernes());
      query.setString("CE_DIAPROCESOSABADO", ce.getCeDiaprocesosabado());
      query.setString("CE_DIAPROCESODOMINGO", ce.getCeDiaprocesodomingo());
      query.setParameter("CE_VOLUMENMUESTRAADULTO", ce.getCeVolumenmuestraadulto());
      query.setParameter("CE_VOLUMENMUESTRAPEDIATRICA", ce.getCeVolumenmuestrapediatrica());
      query.setString("CE_ESTABILIDADAMBIENTAL", ce.getCeEstabilidadambiental());
      query.setString("CE_ESTABILIDADREFRIGERADO", ce.getCeEstabilidadrefrigerado());
      query.setString("CE_ESTABILIDADCONGELADO", ce.getCeEstabilidadcongelado());
      query.setShort("CE_CONTABLECANTIDAD", ce.getCeContablecantidad());
      query.setString("CE_ESEXAMEN", ce.getCeEsexamen());
      query.setString("CE_TIENETESTOPCIONALES", ce.getCeTienetestopcionales());
      query.setString("CE_ESURGENTE", ce.getCeEsurgente());
      query.setString("CE_ESCULTIVO", ce.getCeEscultivo());
      query.setShort("CE_NUMERODEETIQUETAS", ce.getCeNumerodeetiquetas());
      query.setString("CE_ESCONFIDENCIAL", ce.getCeEsconfidencial());
      query.setShort("CE_IDDERIVADOR", idDerivador);
      query.setString("CE_ESINDICADOR", ce.getCeEsindicador());
      query.setString("CE_NOMBREWEB", ce.getCeNombreweb());
      query.setString("CE_DISPONIBLEWEB", ce.getCeDisponibleweb());
      query.setString("CE_DIAGNOSTICOOBLIGATORIO", ce.getCeDiagnosticoobligatorio());
      query.setString("CE_HOJATRABAJO", ce.getCeHojatrabajo());
      query.setString("CE_ESCURVATOLERANCIA", ce.getCeEscurvatolerancia());
      query.setString("CE_PLANILLAHISTORICA", ce.getCePlanillahistorica());
      query.setInteger("CE_DIASDEVALIDEZ", ce.getCeDiasdevalidez());
      query.setString("CE_DIASVALIDEZAMBULATORIOBLQ", ce.getCeDiasvalidezambulatorioblq());
      query.setShort("CE_DIASVALIDEZHOSPITALIZADO", ce.getCeDiasvalidezhospitalizado());
      query.setString("CE_DIASVALIDEZHOSPITALIZADOBLQ", ce.getCeDiasvalidezhospitalizadoblq());
      query.setString("CE_ESEXAMENMULTISECCION", ce.getCeEsexamenmultiseccion());
      query.setString("CE_AUTOVALIDAR", ce.getCeAutovalidar());
      query.setString("CE_COMPARTEMUESTRA", ce.getCeCompartemuestra());
      query.setString("CE_TIENEGRUPOMUESTRA", ce.getCeTienegrupomuestra());
      query.setString("CE_CELLCOUNTER", ce.getCeCellcounter());
      query.setLong("CE_IDEXAMEN", ce.getCeIdexamen());
      if (efdto.getCfgExamenesconjuntos() != null) {
        query.setLong("CE_IDEXAMENESCONJUNTOS", efdto.getCfgExamenesconjuntos().getCecIdexamenesconjuntos());
      }
      if (efdto.getLdvIndicacionesexamenes() != null) {
        query.setParameter("CE_IDINDICACIONEXAMEN", efdto.getLdvIndicacionesexamenes().getLdvieIdindicacionesexamen());
      }
      if (efdto.getLdvFormatos() != null) {
        query.setShort("CE_IDFORMATO", efdto.getLdvFormatos().getLdvfIdformato());
      }
      if (efdto.getLdvTiposexamenes() != null) {
        query.setShort("CE_IDTIPOEXAMEN", efdto.getLdvTiposexamenes().getLdvteIdtipoexamen());
      }

      if (efdto.getCfgGruposexamenesfonasa() != null) {
        query.setShort("CE_IDGRUPOEXAMENFONASA", efdto.getCfgGruposexamenesfonasa().getCgefIdgrupoexamenfonasa());
      }
      // if (efdto.getLdvLoinc()!=null){
      // query.setString("CE_CODIGOLOINC", efdto.getLdvLoinc().getLdvlCodigoloinc());
      // }
      if (efdto.getCfgMuestras() != null) {
        query.setShort("CE_IDTIPOMUESTRA", efdto.getCfgMuestras().getCmueIdtipomuestra());
      }
      if (efdto.getLdvGruposexamenes() != null && efdto.getLdvGruposexamenes().getLdvgeIdgrupoexamen() != 0) {
        query.setParameter("CE_IDGRUPOEXAMEN", efdto.getLdvGruposexamenes().getLdvgeIdgrupoexamen());
      }
      logger.error(efdto.getCfgMetodos().getCmetIdmetodo());
      if (efdto.getCfgMetodos() != null) {
        query.setInteger("CE_IDMETODO", efdto.getCfgMetodos().getCmetIdmetodo());
      }

      query.executeUpdate();
      sesion.getTransaction().commit();
    } catch (HibernateException he) {

      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void updateExamenes(CfgExamenes ce) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();
      sesion.update(ce);
      sesion.getTransaction().commit();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void updateDerivador(CfgExamenes ce, short idderivador) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    try {
      String sqlUpdate = "UPDATE CFG_EXAMENES" + " SET CE_IDDERIVADOR = :idderivador"
          + " WHERE CE_IDEXAMEN = :idExamen";
      Query query = sesion.createSQLQuery(sqlUpdate);
      query.setLong("idExamen", ce.getCeIdexamen());
      query.setShort("idderivador", idderivador);
      query.executeUpdate();
      sesion.getTransaction().commit();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void updateIdExamenesConjuntos(long idExamen, long idexamenesconjuntos) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    try {
      String sqlUpdate = "UPDATE CFG_EXAMENES" + " SET CE_IDEXAMENESCONJUNTOS = :idexamenesconjuntos"
          + " WHERE CE_IDEXAMEN = :idExamen";
      Query query = sesion.createSQLQuery(sqlUpdate);
      query.setLong("idExamen", idExamen);

      query.setParameter("idexamenesconjuntos", idexamenesconjuntos);

      query.executeUpdate();
      sesion.getTransaction().commit();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void updateTablaReferenciaExamen(long idExamen, short idTabla) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    try {
      String sqlUpdate = "UPDATE CFG_EXAMENES" + " SET CE_IDTABLAREFERENCIAEXAMEN = :idTabla"
          + " WHERE CE_IDEXAMEN = :idExamen";
      Query query = sesion.createSQLQuery(sqlUpdate);
      query.setLong("idExamen", idExamen);
      query.setShort("idTabla", idTabla);
      query.executeUpdate();
      sesion.getTransaction().commit();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  // Esto es para que retorne el ultimo id de examen creado (no debo usar
  // secuencia)
  public BigDecimal getNewIdExamen() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createSQLQuery("select max(ce_idexamen) maxid from cfg_examenes").addScalar("maxid");
      BigDecimal id = (BigDecimal) query.uniqueResult();
      sesion.close();
      return id;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgExamenes> getExamenes() throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      Query query = sesion
          .createQuery("select list from com.grupobios.bioslis.entity.CfgExamenes list where list.ceActivo = 'S' "
              + "order by list.ceAbreviado");
      List<CfgExamenes> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public int diasValidacionExamen(long idExamen) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Integer indice = 0;
    Query query = sesion.createQuery("select list.ceDiasdevalidez " + "from CfgExamenes list "
        + "where list.ceActivo = 'S' AND " + "list.ceIdexamen = :idexamen");
    query.setParameter("idexamen", idExamen);
    try {
      indice = (Integer) query.uniqueResult();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("error en diasValidacionExamen " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return indice.intValue();
  }

  public List<CfgExamenes> getExamenesByCodigoLike(String codigo, String activo) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgExamenes> lista;
    try {

      String sql = "select list from com.grupobios.bioslis.entity.CfgExamenes list "
          + "where list.ceCodigoexamen like :codigo";
      if (activo.equals("S")) {
        sql += " and list.ceActivo = 'S'";
      }
      Query query = sesion.createQuery(sql);
      query.setParameter("codigo", "%" + codigo + "%");
      lista = query.list();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public List<CfgExamenes> getExamenesByCodigoLikeActivo(String codigo) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select list " + "from com.grupobios.bioslis.entity.CfgExamenes list "
          + "where list.ceCodigoexamen like :codigo and " + "list.ceActivo = 'S'");
      query.setParameter("codigo", "%" + codigo + "%");
      List<CfgExamenes> lista = query.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgExamenes> getExamenesByDescLike(String descripcion, int idSeccion, String activo)
      throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgExamenes> lista;
    try {
      String sql = "select list from com.grupobios.bioslis.entity.CfgExamenes list "
          + "where list.ceAbreviado like :descripcion or list.ceDescripcion like :descripcion ";
      if (idSeccion != 0) {
        sql += " and list.cfgSecciones.csecIdseccion = :idSeccion";
      }
      if (activo.equals("S")) {
        sql += " and list.ceActivo = 'S'";
      }
      Query query = sesion.createQuery(sql);
      query.setParameter("descripcion", "%" + descripcion + "%");
      if (idSeccion != 0) {
        query.setParameter("idSeccion", idSeccion);
      }
      lista = query.list();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

  public List<CfgExamenes> getExamenesFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String codigo = filtros.get("codigo").toUpperCase();
    String nombre = filtros.get("nombre").toUpperCase();
    int idSeccion = Integer.parseInt(filtros.get("idSeccion"));
    String activo = filtros.get("activo");

    try {
      String sql = "select list from com.grupobios.bioslis.entity.CfgExamenes list"
          + " where list.ceCodigoexamen like :codigo"
          + " and (list.ceAbreviado like :nombre or list.ceDescripcion like :nombre)";
      if (idSeccion > 0) {
        sql += " and list.cfgSecciones.csecIdseccion = :idSeccion";
      }
      if (activo.equals("S")) {
        sql += " and list.ceActivo = 'S'";
      }
      sql += " order by list.ceAbreviado";
      Query query = sesion.createQuery(sql);
      query.setParameter("codigo", "%" + codigo + "%");
      query.setParameter("nombre", "%" + nombre + "%");
      if (idSeccion > 0) {
        query.setParameter("idSeccion", idSeccion);
      }
      List<CfgExamenes> lista = query.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  public List<ExamenOrdenDTO> getExamenesExcel() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<ExamenOrdenDTO> lista = null;

    try {
      String sql = "SELECT "
          + "ce.CE_ACTIVO,ce.CE_CODIGOEXAMEN,ce.CE_ABREVIADO,ce.CE_DESCRIPCION CS_DESCRIPCION,CE_ABREVIADO2,ce.CE_CODIGOFONASA,cd.CDERIV_DESCRIPCION "
          + "FROM CFG_EXAMENES ce " + "INNER JOIN CFG_DERIVADORES cd ON cd.CDERIV_IDDERIVADOR = ce.CE_IDDERIVADOR "
          + "ORDER BY 1,3";

      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ExamenOrdenDTO.class));

      lista = query.list();
      sesion.close();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;

  }

  public int getCountExamenesBySeccion(int idSeccion) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgExamenes> listaExamenes = null;
    try {
      Query query = sesion.createQuery("select ce from CfgExamenes ce" + " join fetch ce.cfgSecciones csec"
          + " where csec.csecIdseccion = :idSeccion");
      query.setParameter("idSeccion", idSeccion);
      listaExamenes = query.list();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaExamenes.size();
  }

  public int getCountExamenesByLab(int idLab) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgExamenes> listaExamenes = null;
    try {

      String sqlCountExamenes = "select ce.* from cfg_examenes ce"
          + " join cfg_secciones cs on cs.csec_idseccion = ce.ce_idseccion"
          + " join cfg_laboratorios cl on cl.clab_idlaboratorio = cs.csec_idlaboratorio"
          + " where cl.clab_idlaboratorio = :idLab";
      Query query = sesion.createSQLQuery(sqlCountExamenes);
      query.setParameter("idLab", idLab);
      listaExamenes = query.list();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaExamenes.size();
  }

  public CfgExamenes getExamenById(long idExamen) throws BiosLISDAONotFoundException, BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = sesion.createQuery(
          "select list " + "from com.grupobios.bioslis.entity.CfgExamenes list " + "where list.ceIdexamen = :idExamen");
      query.setParameter("idExamen", idExamen);
      CfgExamenes examen = (CfgExamenes) query.uniqueResult();
      sesion.close();
      if (examen == null) {
        if (sesion != null && sesion.isOpen()) {
          sesion.close();
        }
        throw new BiosLISDAONotFoundException("Examen no se encontró");
      }

      return examen;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public CfgExamenes getExamenById(long idExamen, Session sesion)
      throws BiosLISDAONotFoundException, BiosLISDAOException {

    try {
      Query query = sesion.createQuery(
          "select list " + "from com.grupobios.bioslis.entity.CfgExamenes list " + "where list.ceIdexamen = :idExamen");
      query.setParameter("idExamen", idExamen);
      CfgExamenes examen = (CfgExamenes) query.uniqueResult();
      // sesion.close();
      if (examen == null) {
        if (sesion != null && sesion.isOpen()) {
          sesion.close();
        }
        throw new BiosLISDAONotFoundException("Examen no se encontró");
      }

      return examen;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    }
  }

  public List<CfgExamenes> getExamenByCodigo(String codigoExamen) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgExamenes> examenes = null;
    try {
      Query query = sesion.createQuery("select list " + "from com.grupobios.bioslis.entity.CfgExamenes list "
          + "where list.ceCodigoexamen = :codigoExamen");
      query.setParameter("codigoExamen", codigoExamen);
      examenes = query.list();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return examenes;
  }

  public List<CfgExamenes> getIndicacionesXexamen(long idExamen) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgExamenes> listaExamenes = null;
    try {
      Query query = sesion.createQuery("select ce from com.grupobios.bioslis.entity.CfgExamenes ce "
          + "left join fetch ce.ldvIndicacionesexamenes csec " + "where ce.ceIdexamen = :idExamen "
          + "order by ce.ceIdexamen");
      query.setParameter("idExamen", idExamen);
      listaExamenes = query.list();
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("error en getExamenes " + he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaExamenes;
  }

  // new metodo from Marco C 22/06/21
  public JsonArrayBuilder getExamenesMuestraById(short idMuestra) throws BiosLISDAOException {
    // 2 querys ya que se malogra cuando solamente se hace una consulta a
    // cfgExamenes
    Session sesion = null;
    JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select ce " + "from CfgExamenes ce "
          + "where ce.cfgMuestras.cmueIdtipomuestra = :idMuestra " + " order by ce.ceIdexamen");
      query.setParameter("idMuestra", idMuestra);
      Query query2 = sesion.createQuery("select ce " + "from CfgMuestras ce "
          + "where ce.cmueIdtipomuestra = :idMuestra order by ce.cmueIdtipomuestra");
      query2.setParameter("idMuestra", idMuestra);
      List<CfgExamenes> listaExamenes = query.list();
      List<CfgMuestras> listaExamenes2 = query2.list();

      if (listaExamenes.isEmpty()) {
        JsonObjectBuilder examenBuilder = Json.createObjectBuilder();
        JsonObject examenJson;
        examenBuilder.add("prefijo", (listaExamenes2.get(0).getCmuePrefijotipomuestra()));
        examenJson = examenBuilder.build();
        arrayBuilder.add(examenJson);
      } else {
        for (CfgExamenes ce : listaExamenes) {

          JsonObjectBuilder examenBuilder = Json.createObjectBuilder();
          JsonObject examenJson;
          examenBuilder.add("id", ce.getCeIdexamen())
              .add("descripcion", (ce.getCeDescripcion() == null) ? "" : ce.getCeDescripcion())
              .add("prefijo", (listaExamenes2.get(0).getCmuePrefijotipomuestra()));
          examenJson = examenBuilder.build();
          arrayBuilder.add(examenJson);
        }
      }
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return arrayBuilder;
  }

  public JsonArrayBuilder getExamenesMuestraLikePrefijo(String prefijo) throws BiosLISDAOException {
    Session sesion = null;
    JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      Query query = sesion.createQuery(
          "select ce " + "from CfgExamenes ce " + "where ce.cfgMuestras.cmuePrefijotipomuestra like :prefijo "
          // + " order by ce.ceIdexamen");
              + " order by ce.ceSort asc, ce.ceDescripcion asc");
      query.setParameter("prefijo", "%" + prefijo + "%");

      List<CfgExamenes> listaExamenes = query.list();
      Query query2 = sesion
          .createQuery("select ce " + "from CfgMuestras ce " + "where ce.cmuePrefijotipomuestra = :prefijo "
          // + "order by ce.cmueIdtipomuestra");cmueSort
              + "order by ce.cmueSort asc, ce.ceAbreviado asc");
      query2.setParameter("prefijo", prefijo.toUpperCase());
      List<CfgMuestras> listaExamenes2 = query2.list();

      if (listaExamenes.isEmpty()) {
        if (!listaExamenes2.isEmpty()) {

          JsonObjectBuilder examenBuilder = Json.createObjectBuilder();
          JsonObject examenJson;
          examenBuilder.add("muestra", (String.valueOf(listaExamenes2.get(0).getCmueIdtipomuestra())));
          examenJson = examenBuilder.build();
          arrayBuilder.add(examenJson);
        } else {
          JsonObjectBuilder examenBuilder = Json.createObjectBuilder();
          JsonObject examenJson;
          examenBuilder.add("muestra", ("vacio"));
          examenJson = examenBuilder.build();
          arrayBuilder.add(examenJson);

        }

        /*
         * JsonObjectBuilder examenBuilder = Json.createObjectBuilder(); JsonObject
         * examenJson; examenBuilder .add("muestra",
         * (String.valueOf(listaExamenes2.get(0).getCmueIdtipomuestra()))); examenJson =
         * examenBuilder.build(); arrayBuilder.add(examenJson);
         */
      } else {
        for (CfgExamenes ce : listaExamenes) {
          // System.out.println(ce.getCfgMuestras().getCmuePrefijotipomuestra());
          JsonObjectBuilder examenBuilder = Json.createObjectBuilder();
          JsonObject examenJson;
          examenBuilder.add("id", ce.getCeIdexamen())
              // .add("descripcion", (ce.getCeDescripcion() == null) ? "" :
              // ce.getCeDescripcion())
              .add("descripcion", (ce.getCeAbreviado() == null) ? "" : ce.getCeAbreviado())
              .add("muestra", (String.valueOf(listaExamenes2.get(0).getCmueIdtipomuestra())));
          examenJson = examenBuilder.build();
          arrayBuilder.add(examenJson);
        }
      }
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return arrayBuilder;
  }

  public List<ExamenOrdenDTO> getExamenesByMuestra(long idMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "SELECT DISTINCT dfe.DFE_IDEXAMEN, ce.ce_abreviado, ce.CE_CODIGOEXAMEN, dfe.dfe_examenanulado FROM cfg_examenes ce"
          + " LEFT JOIN datos_fichasexamenestest dfet on dfet.dfet_idexamen = ce.ce_idexamen"
          + " LEFT JOIN datos_fichasexamenes dfe on dfe.dfe_idexamen = ce.ce_idexamen and dfe.dfe_norden = dfet.dfet_norden"
          + " WHERE dfet.dfet_idmuestra = :idMuestra";
      // Query query = sesion.createSQLQuery(sql).addEntity("ce", CfgExamenes.class);
      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ExamenOrdenDTO.class));
      // .addJoin("cd", "ce.cfgDerivadores")
      // .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
      // query.setParameter("nOrden", nOrden);
      query.setParameter("idMuestra", idMuestra);
      List<ExamenOrdenDTO> listaExamenes = query.list();
      sesion.close();
      return listaExamenes;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  // MC 02/05/2022
  public List<ExamenOrdenDTO> getExamenesMuestraByIdMuestra(long idMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "SELECT DISTINCT ex.ce_codigoexamen, ex.ce_abreviado, dfe.dfe_idexamen, dfe.dfe_examenanulado FROM datos_fichasexamenestest dfet "
          + "INNER JOIN datos_fichasexamenes dfe ON dfe.dfe_norden = dfet.dfet_norden and dfe.dfe_idexamen = dfet.dfet_idexamen "
          + "INNER JOIN cfg_examenes ex ON dfet.dfet_idexamen = ex.ce_idexamen "
          + "INNER JOIN cfg_test ON dfet.dfet_idtest = cfg_test.ct_idtest "
          + "INNER JOIN cfg_muestras cmue ON cmue.cmue_idtipomuestra = DFET.DFET_IDTIPOMUESTRA "
          + "INNER JOIN cfg_envases env ON env.cenv_idenvase = dfet.dfet_idenvase "
          + "INNER JOIN cfg_derivadores cd on cd.cderiv_idderivador = dfe.dfe_idderivador "
          + "WHERE dfet.dfet_idmuestra = :idMuestra";
      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ExamenOrdenDTO.class));
      query.setParameter("idMuestra", idMuestra);
      List<ExamenOrdenDTO> listaExamenes = query.list();
      sesion.close();
      return listaExamenes;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<ExamenOrdenDTO> getExamenesMuestraSinIdMuestra(Long nOrden, Short idTipoMuestra, Short idEnvase,
      Short idDerivador, String comparteMuestra, Long idExamen) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "SELECT DISTINCT ex.ce_codigoexamen, ex.ce_abreviado, dfe.dfe_idexamen, dfe.dfe_examenanulado FROM datos_fichasexamenestest dfet "
          + "INNER JOIN datos_fichasexamenes dfe ON dfe.dfe_norden = dfet.dfet_norden and dfe.dfe_idexamen = dfet.dfet_idexamen "
          + "INNER JOIN cfg_examenes ex ON dfet.dfet_idexamen = ex.ce_idexamen "
          + "INNER JOIN cfg_test ON dfet.dfet_idtest = cfg_test.ct_idtest "
          + "INNER JOIN cfg_muestras cmue ON cmue.cmue_idtipomuestra = DFET.DFET_IDTIPOMUESTRA "
          + "INNER JOIN cfg_envases env ON env.cenv_idenvase = dfet.dfet_idenvase "
          + "INNER JOIN cfg_derivadores cd on cd.cderiv_idderivador = dfe.dfe_idderivador "
          + "WHERE dfet.dfet_norden = :nOrden and cmue.cmue_idtipomuestra = :idTipoMuestra and env.cenv_idenvase = :idEnvase "
          + "and cd.cderiv_idderivador = :idDerivador and ex.ce_compartemuestra = :comparteMuestra";
      logger.debug(idExamen);

      if (comparteMuestra.equals("N")) {
        sql += " AND ex.CE_IDEXAMEN  = :idExamen";
      }
      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ExamenOrdenDTO.class));
      query.setParameter("nOrden", nOrden);
      query.setParameter("idTipoMuestra", idTipoMuestra);
      query.setParameter("idEnvase", idEnvase);
      query.setParameter("idDerivador", idDerivador);
      query.setParameter("comparteMuestra", comparteMuestra);
      if (comparteMuestra.equals("N")) {
        query.setParameter("idExamen", idExamen);
      }
      List<ExamenOrdenDTO> listaExamenes = query.list();
      sesion.close();
      return listaExamenes;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  // MC 02/05/2022
  public List<CfgExamenes> getExamenesMuestraByIdMuestraAntiguo(long idMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "SELECT DISTINCT ex.* FROM datos_fichasexamenestest dfet "
          + "INNER JOIN datos_fichasexamenes dfe ON dfe.dfe_norden = dfet.dfet_norden and dfe.dfe_idexamen = dfet.dfet_idexamen "
          + "INNER JOIN cfg_examenes ex ON dfet.dfet_idexamen = ex.ce_idexamen "
          + "INNER JOIN cfg_test ON dfet.dfet_idtest = cfg_test.ct_idtest "
          + "INNER JOIN cfg_muestras cmue ON cmue.cmue_idtipomuestra = cfg_test.ct_idtipomuestra "
          + "INNER JOIN cfg_envases env ON env.cenv_idenvase = dfet.dfet_idenvase "
          + "INNER JOIN cfg_derivadores cd on cd.cderiv_idderivador = dfe.dfe_idderivador "
          + "WHERE dfet.dfet_idmuestra = :idMuestra";
      Query query = sesion.createSQLQuery(sql).addEntity("ex", CfgExamenes.class);
      query.setParameter("idMuestra", idMuestra);
      List<CfgExamenes> listaExamenes = query.list();
      sesion.close();
      for (CfgExamenes examen : listaExamenes) {
        logger.debug("muestra: " + examen.getCfgMuestras().getCmueDescripcion());
      }
      return listaExamenes;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgExamenes> getExamenesMuestraSinIdMuestraAntiguo(Long nOrden, Short idTipoMuestra, Short idEnvase,
      Short idDerivador, String comparteMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "SELECT DISTINCT ex.* FROM datos_fichasexamenestest dfet "
          + "INNER JOIN datos_fichasexamenes dfe ON dfe.dfe_norden = dfet.dfet_norden and dfe.dfe_idexamen = dfet.dfet_idexamen "
          + "INNER JOIN cfg_examenes ex ON dfet.dfet_idexamen = ex.ce_idexamen "
          + "INNER JOIN cfg_test ON dfet.dfet_idtest = cfg_test.ct_idtest "
          + "INNER JOIN cfg_muestras cmue ON cmue.cmue_idtipomuestra = cfg_test.ct_idtipomuestra "
          + "INNER JOIN cfg_envases env ON env.cenv_idenvase = dfet.dfet_idenvase "
          + "INNER JOIN cfg_derivadores cd on cd.cderiv_idderivador = dfe.dfe_idderivador "
          + "WHERE dfet.dfet_norden = :nOrden and cmue.cmue_idtipomuestra = :idTipoMuestra and env.cenv_idenvase = :idEnvase "
          + "and cd.cderiv_idderivador = :idDerivador and ex.ce_compartemuestra = :comparteMuestra";
      Query query = sesion.createSQLQuery(sql).addEntity("ex", CfgExamenes.class);
      query.setParameter("nOrden", nOrden);
      query.setParameter("idTipoMuestra", idTipoMuestra);
      query.setParameter("idEnvase", idEnvase);
      query.setParameter("idDerivador", idDerivador);
      query.setParameter("comparteMuestra", comparteMuestra);
      List<CfgExamenes> listaExamenes = query.list();
      sesion.close();
      return listaExamenes;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<TestRepetidosDTO> getTestRepetidosExamenes() throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<TestRepetidosDTO> list = null;
    try {
      Query query = sesion.createQuery(hqlTestRepetidosEnExamenes);
      query.setResultTransformer(Transformers.aliasToBean(TestRepetidosDTO.class));
      list = query.list();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("No se pudo otner test repetidos en lista de examenes {}.", he.getMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return list;
  }

  public List<TestRepetidosDTO> getTestRepetidosExamenes(Integer[] lstExamenes) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<TestRepetidosDTO> list = null;
    try {
      Query query = sesion.createQuery(hqlTestRepetidosEnListaExamenes);
      query.setResultTransformer(Transformers.aliasToBean(TestRepetidosDTO.class));
      query.setParameterList("lstExamenes", lstExamenes);
      list = query.list();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("No se pudo obtener test repetidos en lista de examenes {}.", he.getMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return list;
  }

  private final static String hqlExamenesIncMuestrasSeccionesLaboratorios = ""
      + "SELECT ce,clab,cds from CfgExamenes ce, CfgLaboratorios clab, CfgCentrosdesalud cds "
      + "inner join fetch  ce.cfgMuestras cm " + "inner join fetch  ce.cfgSecciones csec "
      + "left outer join fetch ce.ldvIndicacionesexamenes ie " + "where ce.ceActivo like 'S' "
      + "and csec.csecActiva like 'S' " + "and cfgLaboratorios.clabIdlaboratorio = clab.clabIdlaboratorio "
      + "and clab.clabActivo like 'S' " + "and clab.clabIdCentroSalud = cds.ccdsIdcentrodesalud "
      + "and cds.ccdsActivo like 'S' " + "and ce.ceActivo like 'S' order by ce.ceAbreviado";

  private final static String hqlTestRepetidosEnExamenes = "SELECT cet.cfgTest.ctIdtest as idTest,cet.cfgTest.ctDescripcion as descripcionTest, count(*) as cantidad  "
      + "from CfgExamenestest cet, CfgTest ct where cet.cfgTest.ctIdtest = ct.ctIdtest " + " and ct.ctActivo like 'S' "
      + "group by cet.cfgTest.ctIdtest,cet.cfgTest.ctDescripcion " + "having count(*) >1 ";

  private final static String hqlTestRepetidosEnListaExamenes = "SELECT cet.cfgTest.ctIdtest as idTest,cet.cfgTest.ctAbreviado as descripcionTest, count(*) as cantidad  "
      + "from CfgExamenestest cet, CfgTest ct where cet.cfgTest.ctIdtest = ct.ctIdtest " + " and ct.ctActivo like 'S' "
      + "and  cet.id.cetIdexamen in :lstExamenes " + "group by cet.cfgTest.ctIdtest,cet.cfgTest.ctAbreviado "
      + "having count(*) >1 ";

  private final static String hqlTestOpcionalesExamen = "SELECT ct  " + "from CfgExamenestest cet, CfgTest ct "
      + "where cet.cfgTest.ctIdtest = ct.ctIdtest " + " and ct.ctActivo like 'S' " + " and ct.ctOpcional like 'S' "
      + " and  cet.id.cetIdexamen = :idExamen ";

  public List<CfgTest> getTestOpcionalesExamenes(Long idExamen) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgTest> list = null;
    try {
      Query query = sesion.createQuery(hqlTestOpcionalesExamen);
      query.setLong("idExamen", idExamen);
      list = query.list();

    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error("No se pudo obtener test opcionales en lista de examenes {}.", he.getMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return list;
  }

  private String insertIntoFields = "INSERT INTO BIOSLIS.CFG_EXAMENES ("
      + " CE_CODIGOEXAMEN, CE_DESCRIPCION, CE_ABREVIADO, CE_ABREVIADO2, CE_ACTIVO, CE_IMPRIMIRSEPARADO, CE_IMPRIMIRDESCRIPCION, CE_IMPRIMIRPORSECCION,"
      + " CE_IMPRIMIRTABLA, CE_MULTIFORMATO, CE_SELECCIONABLE, CE_HOST, CE_HOSTMICRO, CE_HOST2, CE_HOST3,"
      + " CE_CODIGOFONASA, CE_IDSECCION, CE_SORTSECCION, CE_ESTADISTICAS, CE_NOTA, CE_ESPESQUISA, CE_PESQUISAOBLIGATORIA,"
      + " CE_IDPESQUISA, CE_DATOSMUESTRA, CE_TIEMPOENTREGANORMAL, CE_TIEMPOENTREGAURGENTE, CE_TIEMPOENTREGAHOSPITALIZADO, CE_DIAPROCESOLUNES, CE_DIAPROCESOMARTES, CE_DIAPROCESOMIERCOLES,"
      + " CE_DIAPROCESOJUEVES, CE_DIAPROCESOVIERNES, CE_DIAPROCESOSABADO, CE_DIAPROCESODOMINGO, CE_VOLUMENMUESTRAADULTO, CE_VOLUMENMUESTRAPEDIATRICA, CE_ESTABILIDADAMBIENTAL,"
      + " CE_ESTABILIDADREFRIGERADO, CE_ESTABILIDADCONGELADO, CE_CONTABLECANTIDAD, CE_ESEXAMEN, CE_TIENETESTOPCIONALES, CE_ESURGENTE, CE_ESCULTIVO,"
      + " CE_NUMERODEETIQUETAS, CE_ESCONFIDENCIAL, CE_IDDERIVADOR, CE_ESINDICADOR, CE_NOMBREWEB, CE_DISPONIBLEWEB, CE_DIAGNOSTICOOBLIGATORIO,"
      + " CE_HOJATRABAJO, CE_ESCURVATOLERANCIA, CE_PLANILLAHISTORICA, CE_DIASDEVALIDEZ, CE_DIASVALIDEZHOSPITALIZADO, CE_DIASVALIDEZAMBULATORIOBLQ, CE_DIASVALIDEZHOSPITALIZADOBLQ,"
      + " CE_ESEXAMENMULTISECCION, CE_AUTOVALIDAR, CE_COMPARTEMUESTRA, CE_TIENEGRUPOMUESTRA, CE_CELLCOUNTER";

  private String insertIntoValues = " VALUES (:CE_CODIGOEXAMEN, :CE_DESCRIPCION, :CE_ABREVIADO, :CE_ABREVIADO2, :CE_ACTIVO, :CE_IMPRIMIRSEPARADO, :CE_IMPRIMIRDESCRIPCION,"
      + " :CE_IMPRIMIRPORSECCION, :CE_IMPRIMIRTABLA, :CE_MULTIFORMATO, :CE_SELECCIONABLE, :CE_HOST, :CE_HOSTMICRO, :CE_HOST2,"
      + " :CE_HOST3, :CE_CODIGOFONASA, :CE_IDSECCION, :CE_SORTSECCION, :CE_ESTADISTICAS, :CE_NOTA, :CE_ESPESQUISA,"
      + " :CE_PESQUISAOBLIGATORIA, :CE_IDPESQUISA, :CE_DATOSMUESTRA, :CE_TIEMPOENTREGANORMAL, :CE_TIEMPOENTREGAURGENTE, :CE_TIEMPOENTREGAHOSPITALIZADO, :CE_DIAPROCESOLUNES,"
      + " :CE_DIAPROCESOMARTES, :CE_DIAPROCESOMIERCOLES, :CE_DIAPROCESOJUEVES, :CE_DIAPROCESOVIERNES, :CE_DIAPROCESOSABADO, :CE_DIAPROCESODOMINGO, :CE_VOLUMENMUESTRAADULTO,"
      + " :CE_VOLUMENMUESTRAPEDIATRICA, :CE_ESTABILIDADAMBIENTAL, :CE_ESTABILIDADREFRIGERADO, :CE_ESTABILIDADCONGELADO, :CE_CONTABLECANTIDAD, :CE_ESEXAMEN, "
      + " :CE_TIENETESTOPCIONALES, :CE_ESURGENTE, :CE_ESCULTIVO, :CE_NUMERODEETIQUETAS, :CE_ESCONFIDENCIAL, :CE_IDDERIVADOR, :CE_ESINDICADOR, :CE_NOMBREWEB,"
      + " :CE_DISPONIBLEWEB, :CE_DIAGNOSTICOOBLIGATORIO, :CE_HOJATRABAJO, :CE_ESCURVATOLERANCIA, :CE_PLANILLAHISTORICA, :CE_DIASDEVALIDEZ, :CE_DIASVALIDEZHOSPITALIZADO,"
      + " :CE_DIASVALIDEZAMBULATORIOBLQ, :CE_DIASVALIDEZHOSPITALIZADOBLQ, :CE_ESEXAMENMULTISECCION, :CE_AUTOVALIDAR, :CE_COMPARTEMUESTRA, :CE_TIENEGRUPOMUESTRA, :CE_CELLCOUNTER";

  private String updateExamenSql = "UPDATE BIOSLIS.CFG_EXAMENES"
      + " SET CE_CODIGOEXAMEN=:CE_CODIGOEXAMEN, CE_DESCRIPCION=:CE_DESCRIPCION, CE_ABREVIADO=:CE_ABREVIADO, CE_ABREVIADO2=:CE_ABREVIADO2, CE_ACTIVO=:CE_ACTIVO,"
      + " CE_IMPRIMIRSEPARADO=:CE_IMPRIMIRSEPARADO, CE_IMPRIMIRDESCRIPCION=:CE_IMPRIMIRDESCRIPCION, CE_IMPRIMIRPORSECCION=:CE_IMPRIMIRPORSECCION, CE_IMPRIMIRTABLA=:CE_IMPRIMIRTABLA,"
      + " CE_MULTIFORMATO=:CE_MULTIFORMATO, CE_SELECCIONABLE=:CE_SELECCIONABLE, CE_HOST=:CE_HOST, CE_HOSTMICRO=:CE_HOSTMICRO, CE_HOST2=:CE_HOST2, CE_HOST3=:CE_HOST3, CE_CODIGOFONASA=:CE_CODIGOFONASA,"
      + " CE_IDSECCION=:CE_IDSECCION, CE_SORTSECCION=:CE_SORTSECCION, CE_ESTADISTICAS=:CE_ESTADISTICAS , CE_NOTA=:CE_NOTA, CE_ESPESQUISA=:CE_ESPESQUISA,"
      + " CE_PESQUISAOBLIGATORIA=:CE_PESQUISAOBLIGATORIA, CE_IDPESQUISA=:CE_IDPESQUISA, CE_DATOSMUESTRA=:CE_DATOSMUESTRA, CE_TIEMPOENTREGANORMAL=:CE_TIEMPOENTREGANORMAL,"
      + " CE_TIEMPOENTREGAURGENTE=:CE_TIEMPOENTREGAURGENTE, CE_TIEMPOENTREGAHOSPITALIZADO=:CE_TIEMPOENTREGAHOSPITALIZADO, CE_DIAPROCESOLUNES=:CE_DIAPROCESOLUNES,"
      + " CE_DIAPROCESOMARTES=:CE_DIAPROCESOMARTES, CE_DIAPROCESOMIERCOLES=:CE_DIAPROCESOMIERCOLES, CE_DIAPROCESOJUEVES=:CE_DIAPROCESOJUEVES, CE_DIAPROCESOVIERNES=:CE_DIAPROCESOVIERNES,"
      + " CE_DIAPROCESOSABADO=:CE_DIAPROCESOSABADO, CE_DIAPROCESODOMINGO=:CE_DIAPROCESODOMINGO, CE_VOLUMENMUESTRAADULTO=:CE_VOLUMENMUESTRAADULTO, CE_VOLUMENMUESTRAPEDIATRICA=:CE_VOLUMENMUESTRAPEDIATRICA,"
      + " CE_ESTABILIDADAMBIENTAL=:CE_ESTABILIDADAMBIENTAL, CE_ESTABILIDADREFRIGERADO=:CE_ESTABILIDADREFRIGERADO, CE_ESTABILIDADCONGELADO=:CE_ESTABILIDADCONGELADO, CE_CONTABLECANTIDAD=:CE_CONTABLECANTIDAD,"
      + " CE_ESEXAMEN=:CE_ESEXAMEN, CE_TIENETESTOPCIONALES=:CE_TIENETESTOPCIONALES, CE_ESURGENTE=:CE_ESURGENTE, CE_ESCULTIVO=:CE_ESCULTIVO, CE_NUMERODEETIQUETAS=:CE_NUMERODEETIQUETAS,"
      + " CE_ESCONFIDENCIAL=:CE_ESCONFIDENCIAL, CE_IDDERIVADOR=:CE_IDDERIVADOR, CE_ESINDICADOR=:CE_ESINDICADOR, CE_NOMBREWEB=:CE_NOMBREWEB, CE_DISPONIBLEWEB=:CE_DISPONIBLEWEB,"
      + " CE_DIAGNOSTICOOBLIGATORIO=:CE_DIAGNOSTICOOBLIGATORIO, CE_HOJATRABAJO=:CE_HOJATRABAJO, CE_ESCURVATOLERANCIA=:CE_ESCURVATOLERANCIA, CE_PLANILLAHISTORICA=:CE_PLANILLAHISTORICA,"
      + " CE_DIASDEVALIDEZ=:CE_DIASDEVALIDEZ, CE_DIASVALIDEZHOSPITALIZADO=:CE_DIASVALIDEZHOSPITALIZADO, CE_DIASVALIDEZAMBULATORIOBLQ=:CE_DIASVALIDEZAMBULATORIOBLQ,"
      + " CE_DIASVALIDEZHOSPITALIZADOBLQ=:CE_DIASVALIDEZHOSPITALIZADOBLQ, CE_ESEXAMENMULTISECCION=:CE_ESEXAMENMULTISECCION, CE_AUTOVALIDAR=:CE_AUTOVALIDAR, CE_COMPARTEMUESTRA=:CE_COMPARTEMUESTRA,"
      + " CE_TIENEGRUPOMUESTRA=:CE_TIENEGRUPOMUESTRA, CE_CELLCOUNTER=:CE_CELLCOUNTER ";

  private final static String hqlExamenesIncMuestrasSecciones = "SELECT ce from CfgExamenes ce "
      + "inner join fetch  ce.cfgMuestras cm " + "inner join fetch  ce.cfgSecciones csec "
      + "inner join fetch  csec.cfgLaboratorios clab " + "left outer join fetch ce.ldvIndicacionesexamenes ie "
      + "where ce.ceActivo like 'S' "
//      + "and clab.clabActivo like 'S'  and csec.csecActiva like 'S' "
      + " order by ce.ceAbreviado";

  public List<CfgExamenesDTO> getCfgExamenesIncMuestras() throws BiosLISDAOException {
    Session sesion = null;
    try {

      logger.debug("Inicio getCfgExamenesIncMuestras {}", LocalDateTime.now());
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery(hqlExamenesIncMuestrasSecciones);
      logger.debug("Inicio getCfgExamenesIncMuestras consulta  {}", LocalDateTime.now());
      List<CfgExamenes> lstExamenes = query.list();
      logger.debug("Fin getCfgExamenesIncMuestras consulta {}", LocalDateTime.now());
      List<CfgExamenesDTO> listaCeDto = new ArrayList<CfgExamenesDTO>();
      CfgCentrosDeSaludAbsDao.init(SpringContext.getBean(CfgCentrosDeSaludDAO.class));

      for (CfgExamenes ce : lstExamenes) {
        CfgExamenesDTO ceDto = new CfgExamenesDTO(ce);
        ceDto.setDerivador(ce.getCfgDerivadores().getCderivIdderivador());
        if (ce.getCfgSecciones() != null && ce.getCfgSecciones().getCfgLaboratorios() != null) {
          ceDto.setCfgLaboratorio(ce.getCfgSecciones().getCfgLaboratorios());
          Integer idCds = ce.getCfgSecciones().getCfgLaboratorios().getClabIdlaboratorio();
          ceDto.setCfgCentrosdesalud(getCds4Lab(idCds));
        }
        listaCeDto.add(ceDto);
      }
      logger.debug("Return getCfgExamenesIncMuestras {}", LocalDateTime.now());

      return listaCeDto;
    } catch (HibernateException he) {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
      logger.debug("Finally getCfgExamenesIncMuestras {}", LocalDateTime.now());
    }
  }

  private static List<CfgCentrosdesalud> cacheCDS = null;

  private static List<CfgCentrosdesalud> getCds() throws BiosLISDAOException {

    if (cacheCDS == null) {
      cacheCDS = getCentrosSalud();
    }

    return cacheCDS;

  }

  private static List<CfgCentrosdesalud> getCentrosSalud() throws BiosLISDAOException {

    Session sesion = null;

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("select cds from CfgCentrosdesalud cds");
      return query.list();

    } catch (HibernateException he) {
      logger.error("");
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  private CfgCentrosdesalud getCds4Lab(Integer idCds) {

    try {
      for (CfgCentrosdesalud cds : getCds()) {

        if (idCds.equals(cds.getCcdsIdcentrodesalud())) {

          return cds;
        }

      }
    } catch (BiosLISDAOException e) {

      logger.error("No se pudo obtener centro de salud");
    }

    return null;

  }

  public List<CfgExamenes> getExamenesBySeccion(int idSeccion) throws BiosLISDAOException {
    Session sesion = null;
    List<CfgExamenes> list = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery(
          "from CfgExamenes ce where ce.cfgSecciones.csecIdseccion = :idSeccion" + " order by ce.ceAbreviado asc");
      query.setParameter("idSeccion", idSeccion);
      list = query.list();

    } catch (HibernateException he) {
      logger.error("No se pudo obtener lista de examenes by seccion {}.", he.getMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return list;
  }

}
//private final static String hqlSeccionesLaboratorios = ""
//+ "SELECT ce,clab,cds from CfgExamenes ce, CfgLaboratorios clab, CfgCentrosdesalud cds "
//+ "inner join fetch  ce.cfgMuestras cm " + "inner join fetch  ce.cfgSecciones csec "
//+ "left outer join fetch ce.ldvIndicacionesexamenes ie " + "where ce.ceActivo like 'S' "
//+ "and csec.csecActiva like 'S' " + "and csec.cfgLaboratorio = clab.clabIdlaboratorio "
//+ "and clab.clabActivo like 'S' " + "and clab.clabIdCentroSalud = cds.ccdsIdcentrodesalud "
//+ "and cds.ccdsActivo like 'S' " + "and ce.ceActivo like 'S' order by ce.ceAbreviado";
