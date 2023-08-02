/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.BiosLisLogEventos;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.CfgExamenesDTO;
import com.grupobios.bioslis.dto.DatosFichasDTO;
import com.grupobios.bioslis.dto.DeltaCheckAptoDTO;
import com.grupobios.bioslis.dto.DeltaCheckDTO;
import com.grupobios.bioslis.dto.ExamenEditarOrdenDTO;
import com.grupobios.bioslis.dto.ExamenOrdenMuestraDTO;
import com.grupobios.bioslis.dto.ExamenesResultadosDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.MuestrasDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.dto.ResultadoTestPacienteDTO;
import com.grupobios.bioslis.dto.TestCurvaDTO;
import com.grupobios.bioslis.dto.TestEdicionOrdenDTO;
import com.grupobios.bioslis.dto.TestRepetidosDTO;
import com.grupobios.bioslis.entity.CfgAntecedentes;
import com.grupobios.bioslis.entity.CfgEnvases;
import com.grupobios.bioslis.entity.CfgExamenestest;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.CfgValoresreferencia;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenesId;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasexamenestestId;
import com.grupobios.bioslis.entity.LogEventosfichas;

/**
 *
 * @author Jan Perkov
 */
public class DatosFichasExamenesTestDAO {

  private static Logger logger = LogManager.getLogger(DatosFichasExamenesTestDAO.class);
  private CfgValoresreferenciaDAO cvrDAO;
  private CfgTestDAO cfgTestDAO;
  private CfgExamenesTestDAO cet = new CfgExamenesTestDAO();
  private CfgExamenesDAO cfgExamenesDAO;
  private DatosFichasDAO datosFichasDAO;
  private DatosPacientesDAO datosPacientesDAO;

  private BiosLisLogEventos biosLisLogEventos = new BiosLisLogEventos();

  public BiosLisLogEventos getBiosLisLogEventos() {
    return biosLisLogEventos;
  }

  public void setBiosLisLogEventos(BiosLisLogEventos biosLisLogEventos) {
    this.biosLisLogEventos = biosLisLogEventos;
  }

  public DatosPacientesDAO getDatosPacientesDAO() {
    return datosPacientesDAO;
  }

  public void setDatosPacientesDAO(DatosPacientesDAO datosPacientesDAO) {
    this.datosPacientesDAO = datosPacientesDAO;
  }

  public String insertDatosFichasExamenesTest(int idOrden, int idExamen, int idPaciente, String ipEquipo,
      Long idUsuarioCrea) throws BiosLISDAOException, BiosLISDAONotFoundException {

    if (cvrDAO == null) {
      throw new RuntimeException();
    }
    String mensaje = "";

    List<CfgExamenestest> listaTests = cet.getExamenTest(idExamen);
    logger.debug("Cantidad de test asociados al examen {}: {}", idExamen, listaTests.size());
    Session sesion = null;
    try {

      // agregado por cristian 03-11 para rescatar urgente y tipoatencion y buscar
      // tiempoentrega **********

      Query query;
      sesion = HibernateUtil.getSessionFactory().openSession();
      DatosFichas df = (DatosFichas) sesion.get(DatosFichas.class, idOrden);

      DatosFichasexamenesId dfid = new DatosFichasexamenesId(idOrden, idExamen);
      DatosFichasexamenes dfe = (DatosFichasexamenes) sesion.get(DatosFichasexamenes.class, dfid);

      Long id = (long) idExamen;
      ExamenEditarOrdenDTO result = getTiempoEntrega(dfe.getDfeExamenesurgente(), df.getCfgTipoatencion(), id);

      if (result.getTIEMPOENTREGA().equals(null)) {
        result.setTIEMPOENTREGA(BigDecimal.valueOf(0));
      }

      Timestamp ts = BiosLisCalendarService.getInstance().getTS();
      int tiempo = result.getTIEMPOENTREGA().intValueExact();
      Calendar calendar = this.fechaCalculoEntrega(ts, tiempo);

      dfe.setDfeFechaentrega(calendar.getTime());
      sesion.beginTransaction();
      sesion.save(dfe);
      sesion.getTransaction().commit();
      sesion.close();

      // *****************************************************************************************************
      for (CfgExamenestest test : listaTests) {
        DatosFichasexamenestest dfet = new DatosFichasexamenestest();
        DatosFichasexamenestestId dfei = new DatosFichasexamenestestId();

        logger.debug("Se ingresa test {}", test);
        int idTest = test.getCfgTest().getCtIdtest();
        CfgTest cfgtest = cfgTestDAO.getTestById(idTest);
        if (cfgtest.getCtOpcional().equals("S")) {
          continue;
        }

        short idEnvases = (short) test.getCfgTest().getCfgEnvases().getCenvIdenvase();
        short idTomaMuestra = cfgtest.getCfgMuestras().getCmueIdtipomuestra();
        int idSeccion = cfgtest.getCfgSecciones();
        dfei.setDfetIdexamen(idExamen);
        dfei.setDfetNorden(idOrden);
        dfei.setDfetIdtest(idTest);
        dfet.setId(dfei);
        dfet.setDfetIdenvase(idEnvases);
        dfet.setDfetIdtipomuestra(idTomaMuestra);
        dfet.setDfetIdseccion((short) idSeccion);
        dfet.setDfetFechaorden(BiosLisCalendarService.getInstance().getTS());
        dfet.setDfetIdestadoresultadotest(EstadosSistema.DFET_IDRESULTADOTEST_INGRESADO);
        dfet.setDfetIdunidaddemedida(
            Integer.valueOf(cfgtest.getCfgUnidadesdemedida().getCumIdunidadmedida()).shortValue());
        dfet.setDfetIdpaciente(idPaciente);
        dfet.setDfetFechacreaciontest(BiosLisCalendarService.getInstance().getTS());
        dfet.setDfetIdusuariocreacion(idUsuarioCrea);

        /*
         * ************* YA NO SE GRABA LOS VALORES DE REFERENCIA - AGREGADPOR CRISTIAN
         * 20-02 CfgValoresreferencia vr = null; try {
         * logger.debug("Se busca valores de referencia para {} {}", idTest,
         * idPaciente); vr = cvrDAO.getValorReferencia(BigDecimal.valueOf(idTest),
         * BigDecimal.valueOf(idPaciente)); } catch (BiosLISDAONotFoundException e) {
         * logger.error(e.getMessage()); } if (vr != null) { if
         * (vr.getCvrValorcriticobajo() != null) {
         * dfet.setDfetValorcriticodesde(vr.getCvrValorcriticobajo().toString()); } if
         * (vr.getCvrValorcriticoalto() != null) {
         * dfet.setDfetValorcriticodesde(vr.getCvrValorcriticoalto().toString()); }
         * 
         * if (vr.getCvrValorbajo() != null) {
         * dfet.setDfetReferenciadesde(vr.getCvrValorbajo().toString()); } if
         * (vr.getCvrValoralto() != null) {
         * dfet.setDfetReferenciahasta(vr.getCvrValoralto().toString()); }
         * 
         * }
         */
        // agregado por cristian para almacenar idmetodo ***
        // 04-11******************************

        sesion = HibernateUtil.getSessionFactory().openSession();
        query = sesion.createSQLQuery("select  cmt_idmetodo  from cfg_metodostest "
            + "        where cmt_idTest=:idTest and cmt_esvalorpordefecto = 'S' ");

        query.setLong("idTest", test.getId().getCetIdtest());
        BigDecimal metodo = (BigDecimal) query.uniqueResult();

        dfet.setDfetIdmetodo(metodo);

        // agregado por cristian 20-02 para agregar resultado y fecha de ultimo test
        // firmado ****************
        query = sesion.createSQLQuery(
            "select dfet.dfet_resultado as RESULTADOTEXTO, dfet.dfet_fechaingresoresultado as FECHARESULTADO from datos_fichasexamenestest dfet "
                + "where dfet.dfet_idpaciente =:idPaciente and dfet.dfet_idtest =:idTest and dfet.dfet_idestadoresultadotest = 5 and ROWNUM = 1 "
                + "order by dfet.dfet_norden desc")
            .setResultTransformer(Transformers.aliasToBean(ResultadoNumericoTestExamenOrdenDTO.class));

        query.setParameter("idPaciente", dfet.getDfetIdpaciente());
        query.setParameter("idTest", dfet.getId().getDfetIdtest());

        List<ResultadoNumericoTestExamenOrdenDTO> datosResultados = query.list();
        if (datosResultados.size() > 0) {
          dfet.setDfetfechaultimoresultado(datosResultados.get(0).getFECHARESULTADO());
          dfet.setDfetUltimoresultadoant(datosResultados.get(0).getRESULTADOTEXTO());
        }

        // ***************************************************************************************

        sesion.beginTransaction();
        sesion.save(dfet);
        sesion.getTransaction().commit();
        sesion.close();
        // BiosLisLogger bl = new BiosLisLogger();
        // agregado por cristian 09-02-2023 para agregar test en log
        // por ahora sin funcionar hasta que se consulte si se va a agregar nuevos test
        // a log fichas
        /*
         * LogEventosfichasDAO lefDAO = new LogEventosfichasDAO(); LogEventosfichas lef
         * = new LogEventosfichas();
         * 
         * try {
         * 
         * // lef.setDatosFichas(dfet.getDatosFichas().getDfNorden()); //
         * lef.setLefIdexamen((int) //
         * dfet.getDatosFichasexamenes().getIddatosFichasExamenes().getDfeIdexamen());
         * // lef.setLefIdtest((int) dfet.getId().getDfetIdtest()); //
         * lef.setLefFechaorden(dfet.getDatosFichas().getDfFechaorden()); //
         * lef.setLefIdpaciente((int) dfet.getDfetIdpaciente()); //
         * lef.setLefNombretabla("DATOS_FICHASEXAMENESTEST"); // lef.setCfgEventos(1);
         * // lef.setLefFecharegistro(dfet.getDfetFechacreaciontest()); //
         * lef.setLefIdusuario(dfet.getDfetIdusuariocreacion().intValue() );
         * 
         * // lefDAO.insertLogEventosFichas(lef); /*
         * bl.logInsertDatosFichaTableRecord(DatosFichasexamenestest.class, dfet, new
         * BigDecimal(1), BiosLisCalendarService.getInstance().getTS(), new
         * BigDecimal(dfet.getId().getDfetNorden()), null, new
         * BigDecimal(dfet.getId().getDfetIdexamen()), null, null, new
         * BigDecimal(dfet.getId().getDfetIdtest()), "",
         * Constante.CREACION_DATOS_FICHAS);
         * 
         * } catch (IllegalArgumentException e1) {
         * logger.error("No se pudo insertar registro de log de tabla.\n{}",
         * e1.getMessage()); }
         */
      }
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      he.printStackTrace();
      throw new BiosLISDAOException("No se pudo ingresar Datos Ficha ExamenTest");
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return mensaje;
  }

  public void BorrarExamenesDatosFichasTest(int nOrden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      sesion.beginTransaction();
      Query query = sesion
          .createSQLQuery("DELETE" + " FROM" + "    datos_fichasexamenestest" + " WHERE" + "    dfet_norden =:nOrden");
      query.setParameter("nOrden", nOrden);
      query.executeUpdate();
      sesion.getTransaction().commit();
      sesion.close();
      // executeUpdate
    } catch (Exception e) {
      throw e;
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<Object[]> getTablaMuestraTomaMuestras(int nOrden) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createSQLQuery(
        "select df.dfet_idexamen,df.dfet_idtipomuestra,df.dfet_idenvase,et.cet_idtest,env.cenv_idenvase,env.cenv_descripcion,"
            + "mue.cmue_idtipomuestra,mue.cmue_descripcion,mue.cmue_prefijotipomuestra,dfm.dfm_codigobarra,dfm.dfm_estadotm,dfm.dfm_tmobservacion "
            + "from DATOS_FICHASEXAMENESTEST df "
            + "left join CFG_EXAMENESTEST et on df.DFET_IDEXAMEN=et.CET_IDEXAMEN and df.DFET_IDTEST=et.CET_IDTEST "
            + "left join CFG_ENVASES env on et.CET_IDENVASE=env.CENV_IDENVASE "
            + "left join CFG_TEST test on et.CET_IDTEST=test.CT_IDTEST "
            + "left join CFG_MUESTRAS mue on test.CT_IDTIPOMUESTRA=mue.CMUE_IDTIPOMUESTRA "
            + "left join datos_fichasmuestras dfm on dfm.dfm_norden = df.dfet_norden " + "where df.DFET_NORDEN=:nOrden "
            + "order by df.DFET_NORDEN, mue.CMUE_IDTIPOMUESTRA, env.CENV_IDENVASE");
    query.setParameter("nOrden", (long) nOrden);
    List<Object[]> lista = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return lista;
  }

  // Tabla muestras Toma de muestras
  public static String Sql_TM = "SELECT " + "	DISTINCT dfet.dfet_norden NORDEN, " + "	dfm.dfm_idmuestra IDMUESTRA, "
      + "	ce.CE_IDDERIVADOR IDEXAMEN, " + "	dfet.dfet_idtipomuestra IDTIPOMUESTRA, " + "	dfet.dfet_idenvase IDENVASE, "
      + "	env.cenv_descripcion ENVASEDESC, " + "	mue.cmue_descripcionabreviada MUESTRAABREV, "
      + "	mue.cmue_descripcion MUESTRADESC, " + "	mue.cmue_prefijotipomuestra PREFIJO, "
      + "	dfm.DFM_CODIGOBARRA CODIGOBARRAS, " + "	du.du_nombres || ' ' || du.du_primerapellido USRFLEBOTOMISTA, "
      + "	du.du_idusuario IDUSRFLEBOTOMISTA, " + "	dfm.dfm_estadotm ESTADOTM, " + "	dfm.dfm_estadorm ESTADORM, "
      + "	dfm.dfm_tmobservacion OBSERVACIONTM, " + "	ce.ce_compartemuestra COMPARTEMUESTRA, "
      + "	NVL(dfe.dfe_idderivador, 0) IDDERIVADOR, " + "	NVL(dfm.dfm_nrovecestomada, 0) NROVECESTOMADA, "
      + "	dfm.dfm_idzonacuerpo IDZONACUERPO, " + "	zc.cbzc_descripcion ZONACUERPO, "
      + "	ce.ce_escurvatolerancia ESCURVATOLERANCIA, " + "	ce.ce_TIENEGRUPOMUESTRA AS PUEDECAMBIARMUESTRA " + " FROM "
      + "	DATOS_FICHASEXAMENESTEST dfet " + " LEFT JOIN CFG_ENVASES env ON "
      + "	env.cenv_idenvase = dfet.dfet_idenvase " + " LEFT JOIN CFG_MUESTRAS mue ON "
      + "	dfet.dfet_idtipomuestra = mue.cmue_idtipomuestra " + " LEFT JOIN DATOS_FICHASMUESTRAS dfm ON "
      + "	dfm.dfm_idmuestra = dfet.dfet_idmuestra " + " LEFT JOIN CFG_EXAMENES ce ON "
      + "	ce.ce_idexamen = dfet.dfet_idexamen " + " LEFT JOIN DATOS_FICHASEXAMENES dfe ON "
      + "	dfet.dfet_idexamen = dfe.dfe_idexamen " + "	AND dfet.dfet_norden = dfe.dfe_norden "
      + " LEFT JOIN DATOS_USUARIOS du ON " + "	du.du_idusuario = dfm.dfm_idusuariotm "
      + " LEFT JOIN CFG_BACZONACUERPO zc ON " + "	zc.cbzc_idzonacuerpo = dfm.dfm_idzonacuerpo " + " WHERE "
      + "	dfet.dfet_norden =:nOrden " + "	AND ce.ce_compartemuestra = 'S' " + "	AND dfet.dfet_idtipomuestra > 0 "
      + " UNION " + " SELECT " + "	DISTINCT dfet.dfet_norden NORDEN, " + "	dfm.dfm_idmuestra IDMUESTRA, "
      + "	dfet.dfet_idexamen IDEXAMEN, " + "	dfet.dfet_idtipomuestra IDTIPOMUESTRA, "
      + "	dfet.dfet_idenvase IDENVASE, " + "	env.cenv_descripcion ENVASEDESC, "
      + "	mue.cmue_descripcionabreviada MUESTRAABREV, " + "	mue.cmue_descripcion MUESTRADESC, "
      + "	mue.cmue_prefijotipomuestra PREFIJO, " + "	dfm.DFM_CODIGOBARRA CODIGOBARRAS, "
      + "	du.du_nombres || ' ' || du.du_primerapellido USRFLEBOTOMISTA, " + "	du.du_idusuario IDUSRFLEBOTOMISTA, "
      + "	dfm.dfm_estadotm ESTADOTM, " + "	dfm.dfm_estadorm ESTADORM, " + "	dfm.dfm_tmobservacion OBSERVACIONTM, "
      + "	ce.ce_compartemuestra COMPARTEMUESTRA, " + "	NVL(dfe.dfe_idderivador, 0) IDDERIVADOR, "
      + "	NVL(dfm.dfm_nrovecestomada, 0) NROVECESTOMADA, " + "	dfm.dfm_idzonacuerpo IDZONACUERPO, "
      + "	zc.cbzc_descripcion ZONACUERPO, " + "	ce.ce_escurvatolerancia ESCURVATOLERANCIA, "
      + "	ce.ce_TIENEGRUPOMUESTRA AS PUEDECAMBIARMUESTRA " + " FROM " + "	DATOS_FICHASEXAMENESTEST dfet "
      + " LEFT JOIN CFG_ENVASES env ON " + "	env.cenv_idenvase = dfet.dfet_idenvase "
      + " LEFT JOIN CFG_MUESTRAS mue ON " + "	dfet.dfet_idtipomuestra = mue.cmue_idtipomuestra "
      + " LEFT JOIN DATOS_FICHASMUESTRAS dfm ON " + "	dfm.dfm_idmuestra = dfet.dfet_idmuestra "
      + " LEFT JOIN CFG_EXAMENES ce ON " + "	ce.ce_idexamen = dfet.dfet_idexamen "
      + " LEFT JOIN DATOS_FICHASEXAMENES dfe ON " + "	dfet.dfet_idexamen = dfe.dfe_idexamen "
      + "	AND dfet.dfet_norden = dfe.dfe_norden " + " LEFT JOIN DATOS_USUARIOS du ON "
      + "	du.du_idusuario = dfm.dfm_idusuariotm " + " LEFT JOIN CFG_BACZONACUERPO zc ON "
      + "	zc.cbzc_idzonacuerpo = dfm.dfm_idzonacuerpo " + " WHERE " + "	dfet.dfet_norden = :nOrden "
      + "	AND (ce.ce_compartemuestra = 'N' ) " + "	AND dfet.dfet_idtipomuestra > 0";

  public List<MuestrasDTO> getDatosTablaMuestrasTM(long nOrden)
      throws BiosLISDAOException, SQLException, UnsupportedEncodingException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<MuestrasDTO> MuestrasSeparadas = new ArrayList<>();
    try {
      String sqlQuery = Sql_TM;
      Query query = sesion.createSQLQuery(sqlQuery).setResultTransformer(Transformers.aliasToBean(MuestrasDTO.class));
      query.setParameter("nOrden", nOrden);

      List<MuestrasDTO> MuestrasSinSeparar = query.list();

      for (MuestrasDTO muestrasDTO : MuestrasSinSeparar) {

        if (muestrasDTO.getCOMPARTEMUESTRA().equals("N")) {

          boolean alreadyExists = MuestrasSeparadas.stream()
              .anyMatch(existing -> existing.getNORDEN().equals(muestrasDTO.getNORDEN())
                  && existing.getIDEXAMEN().equals(muestrasDTO.getNORDEN())
                  && existing.getIDENVASE().equals(muestrasDTO.getIDENVASE())
                  && existing.getIDTIPOMUESTRA().equals(muestrasDTO.getIDTIPOMUESTRA()));
          // If the conditions are not met, add the DTO to MuestrasSeparadas
          if (!alreadyExists) {
            MuestrasSeparadas.add(muestrasDTO);
          }
        } else if (muestrasDTO.getCOMPARTEMUESTRA().equals("S")
            && muestrasDTO.getIDDERIVADOR().compareTo(BigDecimal.ZERO) == 0) {
          // Check if the DTO meets the additional conditions of IDENVASE and
          boolean alreadyExists = MuestrasSeparadas.stream()
              .anyMatch(existing -> existing.getNORDEN().equals(muestrasDTO.getNORDEN())
                  && existing.getIDEXAMEN().equals(muestrasDTO.getNORDEN()) &&
                  // existing.getIDMUESTRA().equals(muestrasDTO.getIDMUESTRA()) &&
                  existing.getIDENVASE().equals(muestrasDTO.getIDENVASE())
                  && existing.getIDTIPOMUESTRA().equals(muestrasDTO.getIDTIPOMUESTRA()));

          // If the conditions are not met, add the DTO to MuestrasSeparadas
          if (!alreadyExists) {
            MuestrasSeparadas.add(muestrasDTO);
          }
        } else if (muestrasDTO.getCOMPARTEMUESTRA().equals("S")
            && muestrasDTO.getIDDERIVADOR().compareTo(BigDecimal.ZERO) != 0) {
          // Check if the DTO meets the additional conditions of IDENVASE and
          // IDTIPOMUESTRA

          boolean alreadyExists = MuestrasSeparadas.stream()
              .anyMatch(existing -> existing.getNORDEN().equals(muestrasDTO.getNORDEN())
                  && existing.getIDEXAMEN().equals(muestrasDTO.getNORDEN())
                  && existing.getIDDERIVADOR().equals(muestrasDTO.getIDDERIVADOR())
                  && existing.getIDENVASE().equals(muestrasDTO.getIDENVASE())
                  && existing.getIDTIPOMUESTRA().equals(muestrasDTO.getIDTIPOMUESTRA()));

          // If the conditions are not met, add the DTO to MuestrasSeparadas
          if (!alreadyExists) {
            MuestrasSeparadas.add(muestrasDTO);
          }
        }
      }

      for (MuestrasDTO muestrasDTO : MuestrasSeparadas) {
        String sqlH = "from CfgEnvases ce where ce.cenvIdenvase = :idEnvase";
        Query queryH = sesion.createQuery(sqlH);
        queryH.setParameter("idEnvase", muestrasDTO.getIDENVASE().intValue());
        CfgEnvases cm = (CfgEnvases) queryH.list().get(0);

        if (cm.getCenvImagenenvase() != null) {

          byte[] bdata = cm.getCenvImagenenvase().getBytes(1, (int) cm.getCenvImagenenvase().length());

          byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
          String base64Encoded = new String(encodeBase64, "UTF-8");
          muestrasDTO.setIMGENVASE(base64Encoded);
        }
        // muestrasDTO.setIMGENVASE(cm.getCenvImagenenvase());
      }
      return MuestrasSeparadas;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<ExamenOrdenMuestraDTO> getExamenesOrdenMuestra(Long nOrden) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();
      String queryString = "SELECT DISTINCT CFG_EXAMENES.CE_CODIGOEXAMEN, CFG_MUESTRAS.CMUE_DESCRIPCION, CFG_ENVASES.CENV_DESCRIPCION, CFG_EXAMENES.CE_ABREVIADO CE_DESCRIPCION, "
          + "CFG_CENTROSDESALUD.CCDS_DESCRIPCION, CFG_LABORATORIOS.CLAB_DESCRIPCION, CFG_SECCIONES.CSEC_DESCRIPCION, CFG_EXAMENES.CE_COMPARTEMUESTRA, "
          + "DATOS_FICHAS.DF_OBSERVACION, CFG_DIAGNOSTICOS.CD_DESCRIPCION, CD.CDERIV_DESCRIPCION, CD.CDERIV_IDDERIVADOR, CFG_EXAMENES.CE_IDEXAMEN, "
          + "DFET.DFET_IDMUESTRA, CFG_CENTROSDESALUD.CCDS_IDCENTRODESALUD, CFG_LABORATORIOS.CLAB_IDLABORATORIO, CFG_SECCIONES.CSEC_IDSECCION "
          + "FROM DATOS_FICHASEXAMENESTEST DFET "
          + "INNER JOIN DATOS_FICHASEXAMENES ON DATOS_FICHASEXAMENES.DFE_NORDEN = DFET.DFET_NORDEN AND DATOS_FICHASEXAMENES.DFE_IDEXAMEN = DFET.DFET_IDEXAMEN "
          + "INNER JOIN CFG_EXAMENES ON DFET.DFET_IDEXAMEN = CFG_EXAMENES.CE_IDEXAMEN "
          + "INNER JOIN CFG_TEST ON DFET.DFET_IDTEST = CFG_TEST.CT_IDTEST "
          + "INNER JOIN CFG_SECCIONES ON CFG_SECCIONES.CSEC_IDSECCION = DFET.DFET_IDSECCION "
          + "INNER JOIN CFG_LABORATORIOS ON CFG_SECCIONES.CSEC_IDLABORATORIO = CFG_LABORATORIOS.CLAB_IDLABORATORIO "
          + "INNER JOIN CFG_CENTROSDESALUD ON CFG_CENTROSDESALUD.CCDS_IDCENTRODESALUD = CFG_LABORATORIOS.CLAB_IDLABORATORIO "
          + "INNER JOIN DATOS_FICHAS ON DATOS_FICHAS.DF_NORDEN = DFET.DFET_NORDEN "
          + "INNER JOIN CFG_MUESTRAS ON CFG_MUESTRAS.CMUE_IDTIPOMUESTRA = DFET.DFET_IDTIPOMUESTRA "
          + "INNER JOIN CFG_ENVASES ON CFG_ENVASES.CENV_IDENVASE = DFET.DFET_IDENVASE "
          + "INNER JOIN CFG_DIAGNOSTICOS ON CFG_DIAGNOSTICOS.CD_IDDIAGNOSTICO = DATOS_FICHAS.DF_IDDIAGNOSTICO "
          + "INNER JOIN CFG_DERIVADORES CD ON CD.CDERIV_IDDERIVADOR = DATOS_FICHASEXAMENES.DFE_IDDERIVADOR "
          + "WHERE DFET.DFET_NORDEN = :nOrden " + "ORDER BY 1";
      Query query = sesion.createSQLQuery(queryString)
          .setResultTransformer(Transformers.aliasToBean(ExamenOrdenMuestraDTO.class));
      query.setParameter("nOrden", nOrden);
      List<ExamenOrdenMuestraDTO> lista = query.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<ExamenOrdenMuestraDTO> getDetalleMuestra(Long idMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String queryString = "SELECT DISTINCT CFG_EXAMENES.CE_CODIGOEXAMEN, CFG_MUESTRAS.CMUE_DESCRIPCION, CFG_ENVASES.CENV_DESCRIPCION, CFG_EXAMENES.CE_ABREVIADO CE_DESCRIPCION, "
          + "CFG_EXAMENES.CE_COMPARTEMUESTRA, CD.CDERIV_IDDERIVADOR, CFG_EXAMENES.CE_IDEXAMEN, DFET.DFET_IDMUESTRA, cfg_muestras.cmue_idtipomuestra, cfg_muestras.cmue_idgrupomuestra "
          + "FROM DATOS_FICHASEXAMENESTEST DFET "
          + "INNER JOIN DATOS_FICHASEXAMENES ON DATOS_FICHASEXAMENES.DFE_NORDEN = DFET.DFET_NORDEN AND DATOS_FICHASEXAMENES.DFE_IDEXAMEN = DFET.DFET_IDEXAMEN "
          + "INNER JOIN CFG_EXAMENES ON DFET.DFET_IDEXAMEN = CFG_EXAMENES.CE_IDEXAMEN "
          + "INNER JOIN CFG_TEST ON DFET.DFET_IDTEST = CFG_TEST.CT_IDTEST "
          + "INNER JOIN DATOS_FICHAS ON DATOS_FICHAS.DF_NORDEN = DFET.DFET_NORDEN "
          + "INNER JOIN CFG_MUESTRAS ON CFG_MUESTRAS.CMUE_IDTIPOMUESTRA = DFET.DFET_IDTIPOMUESTRA "
          + "INNER JOIN CFG_ENVASES ON CFG_ENVASES.CENV_IDENVASE = DFET.DFET_IDENVASE "
          + "INNER JOIN CFG_DERIVADORES CD ON CD.CDERIV_IDDERIVADOR = DATOS_FICHASEXAMENES.DFE_IDDERIVADOR "
          + "WHERE DFET.DFET_IDMUESTRA = :idMuestra";
      Query query = sesion.createSQLQuery(queryString)
          .setResultTransformer(Transformers.aliasToBean(ExamenOrdenMuestraDTO.class));
      query.setParameter("idMuestra", idMuestra);
      List<ExamenOrdenMuestraDTO> lista = query.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<ExamenOrdenMuestraDTO> getDetalleMuestraSM(Long nOrden, Short idTipoMuestra, Short idEnvase,
      Short idDerivador, String comparteMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String queryString = "SELECT DISTINCT CFG_EXAMENES.CE_CODIGOEXAMEN, CFG_MUESTRAS.CMUE_DESCRIPCIONABREVIADA, CFG_MUESTRAS.CMUE_DESCRIPCION, CFG_ENVASES.CENV_DESCRIPCION, CFG_EXAMENES.CE_ABREVIADO CE_DESCRIPCION, "
          + "CFG_EXAMENES.CE_COMPARTEMUESTRA, CD.CDERIV_DESCRIPCION, CD.CDERIV_IDDERIVADOR, CFG_EXAMENES.CE_IDEXAMEN, CFG_EXAMENES.CE_TIENEGRUPOMUESTRA, "
          + "DFET.DFET_IDMUESTRA, CFG_MUESTRAS.cmue_idtipomuestra, cfg_muestras.cmue_idgrupomuestra "
          + "FROM DATOS_FICHASEXAMENESTEST DFET "
          + "INNER JOIN DATOS_FICHASEXAMENES ON DATOS_FICHASEXAMENES.DFE_NORDEN = DFET.DFET_NORDEN AND DATOS_FICHASEXAMENES.DFE_IDEXAMEN = DFET.DFET_IDEXAMEN "
          + "INNER JOIN CFG_EXAMENES ON DFET.DFET_IDEXAMEN = CFG_EXAMENES.CE_IDEXAMEN "
          + "INNER JOIN CFG_TEST ON DFET.DFET_IDTEST = CFG_TEST.CT_IDTEST "
          + "INNER JOIN DATOS_FICHAS ON DATOS_FICHAS.DF_NORDEN = DFET.DFET_NORDEN "
          + "INNER JOIN CFG_MUESTRAS ON CFG_MUESTRAS.CMUE_IDTIPOMUESTRA = DFET.DFET_IDTIPOMUESTRA "
          + "INNER JOIN CFG_ENVASES ON CFG_ENVASES.CENV_IDENVASE = DFET.DFET_IDENVASE "
          + "INNER JOIN CFG_DERIVADORES CD ON CD.CDERIV_IDDERIVADOR = DATOS_FICHASEXAMENES.DFE_IDDERIVADOR "
          + "WHERE DFET.DFET_NORDEN = :nOrden and CFG_MUESTRAS.cmue_idtipomuestra = :idTipoMuestra and CFG_ENVASES.cenv_idenvase = :idEnvase "
          + "and cd.cderiv_idderivador = :idDerivador and cfg_examenes.ce_compartemuestra = :comparteMuestra";
      Query query = sesion.createSQLQuery(queryString)
          .setResultTransformer(Transformers.aliasToBean(ExamenOrdenMuestraDTO.class));
      query.setParameter("nOrden", nOrden);
      query.setParameter("idTipoMuestra", idTipoMuestra);
      query.setParameter("idEnvase", idEnvase);
      query.setParameter("idDerivador", idDerivador);
      query.setParameter("comparteMuestra", comparteMuestra);
      List<ExamenOrdenMuestraDTO> lista = query.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public Boolean verificarComparteMuestra(long Examen) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Boolean validadorFinal;
      sesion.beginTransaction();
      Query query = sesion.createQuery("select med.ceCompartemuestra from com.grupobios.bioslis.entity.CfgExamenes med "
          + " where med.ceIdexamen = :Examen").setMaxResults(1);
      query.setParameter("Examen", Examen);

      String validador;
      try {
        validador = ((String) query.list().get(0));
      } catch (Exception e) {
        validador = "F";
      }
      sesion.getTransaction().commit();
      sesion.close();
      if ("S".equals(validador)) {
        validadorFinal = true;
      } else {
        validadorFinal = false;
      }

      return validadorFinal;
    } catch (Exception e) {
      throw e;
    }
  }

  public List<DatosFichasexamenestest> getDatosFichasExamenesTest() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("select med " + "from com.grupobios.bioslis.entity.DatosFichas med ");
    List<DatosFichasexamenestest> listaFichas = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaFichas;
  }

  public List<DatosFichasexamenestest> getDatosFichasExamenesTestByOrden(long orden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("select med " + "from com.grupobios.bioslis.entity.DatosFichasexamenestest med "
        + "left join fetch med.cfgExamenestest cet " + "left join fetch cet.cfgEnvases ce "
        + "left join fetch cet.cfgTest ct " + "left join fetch ct.cfgTiposderesultado ctr "
        + "left join fetch ct.cfgUnidadesdemedida cum " + "where med.id.dfetNorden =:orden");
    query.setParameter("orden", orden);
    List<DatosFichasexamenestest> listaFichas = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaFichas;
  }

  public List<DatosFichasexamenestest> getTestByOrden(long orden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("select med from DatosFichasexamenestest med "
        + "where med.id.dfetNorden =:orden order by med.id.dfetIdexamen");
    query.setParameter("orden", orden);
    List<DatosFichasexamenestest> listaFichas = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaFichas;
  }

  public List<DatosFichasexamenestest> getTestByMuestra(long idMuestra) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("select med from DatosFichasexamenestest med "
        + "where med.dfetIdmuestra =:idMuestra order by med.id.dfetIdexamen");
    query.setParameter("idMuestra", idMuestra);
    List<DatosFichasexamenestest> listaFichas = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaFichas;
  }

  public List<DatosFichasexamenestest> getIndicacionesByOrden(long orden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("select med " + "from com.grupobios.bioslis.entity.DatosFichasexamenestest med "
        + "where med.id.dfetNorden =:orden");
    query.setParameter("orden", orden);
    List<DatosFichasexamenestest> listaFichas = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaFichas;
  }

  public List<DatosFichasexamenestest> GetDatosParaPDF(long orden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("select med " + "from com.grupobios.bioslis.entity.DatosFichasexamenestest med "
        + "left join fetch med.cfgExamenestest cet " + "left join fetch cet.cfgEnvases ce "
        + "left join fetch cet.cfgTest ct " + "left join fetch ct.cfgTiposderesultado ctr "
        + "left join fetch ct.cfgUnidadesdemedida cum " + "where med.id.dfetNorden =:orden");
    query.setParameter("orden", orden);
    List<DatosFichasexamenestest> listaFichas = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaFichas;
  }

  public List<DatosFichasexamenestest> buscarDFETforMuestras(Long nOrden, Short idTipoMuestra, Short idEnvase,
      Short idDerivador, String comparteMuestra, String escurva) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "SELECT dfet.* FROM datos_fichasexamenestest dfet "
          + "LEFT JOIN datos_fichasexamenes dfe on dfet.dfet_idexamen = dfe.dfe_idexamen and dfet.dfet_norden = dfe.dfe_norden "
          + "LEFT JOIN cfg_examenes ce on ce.ce_idexamen = dfet.dfet_idexamen "
          + "where dfet.dfet_norden = :nOrden and dfet.dfet_idtipomuestra = :idTipoMuestra "
          + "and dfet.dfet_idenvase = :idEnvase and dfe.dfe_idderivador = :idDerivador and ce.ce_compartemuestra = :comparteMuestra"
          + " AND ce.CE_ESCURVATOLERANCIA = :escurva" + " AND ce.CE_IDEXAMEN  = :idxamen";
      Query query = sesion.createSQLQuery(sql).addEntity(DatosFichasexamenestest.class);
      query.setParameter("nOrden", nOrden);
      query.setParameter("idTipoMuestra", idTipoMuestra);
      query.setParameter("idEnvase", idEnvase);
      query.setParameter("idDerivador", idDerivador);
      query.setParameter("comparteMuestra", comparteMuestra);
      query.setParameter("escurva", escurva);
      List<DatosFichasexamenestest> listaDfet = query.list();
      sesion.close();
      return listaDfet;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<DatosFichasexamenestest> buscarDFETparGenerarMuestras(Long nOrden, Short idTipoMuestra, Short idEnvase,
      Short idDerivador, String comparteMuestra, String escurva, BigDecimal idExamen) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "SELECT dfet.* FROM datos_fichasexamenestest dfet "
          + "LEFT JOIN datos_fichasexamenes dfe on dfet.dfet_idexamen = dfe.dfe_idexamen and dfet.dfet_norden = dfe.dfe_norden "
          + "LEFT JOIN cfg_examenes ce on ce.ce_idexamen = dfet.dfet_idexamen "
          + "where dfet.dfet_norden = :nOrden and dfet.dfet_idtipomuestra = :idTipoMuestra "
          + "and dfet.dfet_idenvase = :idEnvase and dfe.dfe_idderivador = :idDerivador and ce.ce_compartemuestra = :comparteMuestra"
          + " AND ce.CE_ESCURVATOLERANCIA = :escurva";
      if (comparteMuestra.equals("N")) {
        sql += " AND ce.CE_IDEXAMEN  = :idxamen";
      }
      Query query = sesion.createSQLQuery(sql).addEntity(DatosFichasexamenestest.class);
      query.setParameter("nOrden", nOrden);
      query.setParameter("idTipoMuestra", idTipoMuestra);
      query.setParameter("idEnvase", idEnvase);
      query.setParameter("idDerivador", idDerivador);
      query.setParameter("comparteMuestra", comparteMuestra);
      query.setParameter("escurva", escurva);
      if (comparteMuestra.equals("N")) {
        query.setParameter("idxamen", idExamen);
      }
      List<DatosFichasexamenestest> listaDfet = query.list();
      sesion.close();
      return listaDfet;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void updateDFExamenesTest(DatosFichasexamenestest dfet) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();
      sesion.update(dfet);
      // eliminado ya no se ocupa aca lo que se habia agregado --- cristian 31-01

      // **********************************************************************************************
      sesion.getTransaction().commit();
      sesion.flush();
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  public List<DatosFichasexamenestest> getDFExamenesTestByOrden(long orden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("from DatosFichasexamenestest med " + "left join fetch med.cfgExamenestest cet "
        + "left join fetch cet.cfgEnvases ce " + "where med.id.dfetNorden=:orden and ce.cenvIdenvase>0");
    query.setParameter("orden", orden);
    List<DatosFichasexamenestest> listaFichas = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaFichas;
  }

  public List<MuestrasDTO> getMuestrasRechazo() {
    List<MuestrasDTO> listaMuestras = null;
    return listaMuestras;
  }

  public List<DatosFichasexamenestest> getDFExamenesTestByMuestra(long idMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("from DatosFichasexamenestest dfet " + "where dfet.dfetIdmuestra = :idMuestra");
      query.setParameter("idMuestra", idMuestra);
      List<DatosFichasexamenestest> lista = query.list();

      return lista;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<DatosFichasexamenestest> getDatosFichasExamenesTestByMuestrayExamen(long idMuestra, long idExamen)
      throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery("from DatosFichasexamenestest dfet "
          + "where dfet.dfetIdmuestra = :idMuestra and dfet.id.dfetIdexamen = :idExamen");
      query.setParameter("idMuestra", idMuestra);
      query.setParameter("idExamen", idExamen);
      List<DatosFichasexamenestest> lista = query.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<DatosFichasexamenestest> buscarPorOrdenyExamenList(Long nOrden, long idExamen) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String consulta = "from DatosFichasexamenestest dfet "
        + "where dfet.id.dfetNorden =:nOrden and dfet.id.dfetIdexamen =:idExamen";
    Query query = sesion.createQuery(consulta);
    query.setParameter("nOrden", nOrden);
    query.setParameter("idExamen", idExamen);
    List<DatosFichasexamenestest> dfet = query.list();
    sesion.close();
    return dfet;
  }

  public List<DatosFichasexamenestest> buscarPorOrdenYEnvase(Long nOrden, Short idEnvase) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String consulta = "from DatosFichasexamenestest dfet "
        + "where dfet.id.dfetNorden =:nOrden and dfet.dfetIdenvase =:idEnvase";
    Query query = sesion.createQuery(consulta);
    query.setParameter("nOrden", nOrden);
    query.setParameter("idEnvase", idEnvase);
    List<DatosFichasexamenestest> dfet = query.list();
    sesion.close();
    return dfet;
  }

  public void updateTipoMuestra(MuestrasDTO muestraDto) {
    /*
     * Session sesion = HibernateUtil.getSessionFactory().openSession();
     * sesion.beginTransaction();
     * 
     * muestraDto.getIDTIPOMUESTRA(); List<DatosFichasexamenestest> list =
     * this.buscarDFETforMuestras(muestraDto.getNORDEN().longValue(),
     * muestraDto.getIDTIPOMUESTRA().shortValue(),
     * muestraDto.getIDENVASE().shortValue(),
     * muestraDto.getIDDERIVADOR().shortValue(), muestraDto.getCOMPARTEMUESTRA());
     * DatosFichasexamenestest dfe = list.get(0);
     * 
     * 
     * sesion.getTransaction().commit(); sesion.close();
     */
  }

  public List<TestCurvaDTO> obtenerListaTestsCurva(long nOrden, long idExamen) throws BiosLISDAOException {
    Session sesion = null;
    try {

      sesion = HibernateUtil.getSessionFactory().openSession();
      String sql = "select ct.ct_abreviado TEST,cmue.cmue_curvasminutos MINUTOS, dfm.dfm_codigobarra NROMUESTRA, "
          + "cmue.cmue_descripcion MUESTRA, du.du_nombres || ' ' || du.du_primerapellido FLEBOTOMISTA, "
          + "dfet.dfet_idmuestra IDMUESTRA, to_char(dfm.dfm_fechatm, 'dd/MM/yyyy HH24:mi:ss') TOMADA, dfm.dfm_estadotm ESTADOMUESTRA "
          + "from datos_fichasexamenestest dfet "
          + "join cfg_examenestest cet on dfet.dfet_idexamen=cet.cet_idexamen and dfet.dfet_idtest=cet.cet_idtest "
          + "join cfg_examenes ce on ce.ce_idexamen = cet.cet_idexamen "
          + "join cfg_test ct on cet.cet_idtest=ct.ct_idtest "
          + "join cfg_muestras cmue on dfet.dfet_idtipomuestra=cmue.cmue_idtipomuestra  "
          + "left join datos_fichasmuestras dfm on dfm.dfm_idmuestra = dfet.dfet_idmuestra "
          + "left join datos_usuarios du on du.du_idusuario = dfm.dfm_idusuariotm "
          + "where dfet.dfet_norden = :nOrden and ce.ce_idexamen = :idExamen and ce.ce_escurvatolerancia = 'S' "
          + "ORDER BY 2";
      Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TestCurvaDTO.class));
      query.setParameter("nOrden", nOrden);
      query.setParameter("idExamen", idExamen);
      List<TestCurvaDTO> lista = query.list();
      sesion.close();
      return lista;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  /**
   * @return the cvrDAO
   */
  public CfgValoresreferenciaDAO getCvrDAO() {
    return cvrDAO;
  }

  /**
   * @param cvrDAO the cvrDAO to set
   */
  public void setCvrDAO(CfgValoresreferenciaDAO cvrDAO) {
    this.cvrDAO = cvrDAO;
  }

  public DatosFichasexamenestest getDatosFichasExamenesTestByIdTest(ResultadoNumericoTestExamenOrdenDTO dfetId)
      throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    DatosFichasexamenestest dfet;
    try {
      String hql = "select dfet from DatosFichasexamenestest dfet where dfet.id.dfetIdtest=:dfetIdtest "
          + " and dfet.id.dfetIdexamen = :dfetIdexamen and dfet.id.dfetNorden in :dfetNorden ";

      Query qry = sesion.createQuery(hql);
      qry.setLong("dfetIdtest", dfetId.getDFET_IDTEST().longValue());
      qry.setLong("dfetIdexamen", dfetId.getDFE_IDEXAMEN().longValue());
      qry.setLong("dfetNorden", dfetId.getDF_NORDEN().longValue());
      dfet = (DatosFichasexamenestest) qry.uniqueResult();

    } catch (HibernateException he) {
      logger.error("Error hibernate {}", he.getMessage());
      throw new BiosLISDAOException(he.getMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();

      }

    }
    return dfet;
  }

  // OJO: Por ahora solo recupera los test de una lista, para un nro de orden y
  // examen dado.
  public DatosFichasexamenestest getDatosFichasExamenesTestByIdTest(
      List<ResultadoNumericoTestExamenOrdenDTO> dfetIdList) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String hql = "select dfet from DatosFichasexamenestest dfet where dfet.id.dfetIdtest in :lstDatosFichasexamenestest "
        + " and dfet.id.dfetIdexamen = :dfetIdexamen and dfet.id.dfetNorden in :dfetNorden ";

    int n = dfetIdList.size();
    BigDecimal curIdOrden = null;
    BigDecimal curIdExamen = null;
    BigDecimal oldOrden = curIdOrden;
    BigDecimal oldExamen = curIdExamen;
    boolean mismaOrden = true;
    boolean mismoExamen = true;

    List<BigDecimal> lstDatosFichasexamenestest = new ArrayList<BigDecimal>();

    for (int i = 0; i < n; i++) {

      if (!mismaOrden && !mismoExamen) {
        lstDatosFichasexamenestest = new ArrayList<BigDecimal>();
      }
      lstDatosFichasexamenestest.add(dfetIdList.get(i).getDFET_IDTEST());

      if (!mismaOrden) {
        oldOrden = curIdOrden;
        curIdOrden = dfetIdList.get(i).getDF_NORDEN();
      }
      if (!mismoExamen) {
        oldExamen = curIdExamen;
        curIdExamen = dfetIdList.get(i).getDFE_IDEXAMEN();
        lstDatosFichasexamenestest = new ArrayList<>();
        Query qry = sesion.createQuery(hql);
        qry.setLong("dfetIdexamen", oldExamen.longValue());
        qry.setLong("dfetNorden", oldOrden.longValue());
        qry.setParameterList("lstDatosFichasexamenestest", lstDatosFichasexamenestest);
        List<DatosFichasexamenestest> dfetList = (List<DatosFichasexamenestest>) qry.list();
      }
      mismaOrden = dfetIdList.get(i).getDF_NORDEN().equals(curIdOrden);
      mismoExamen = dfetIdList.get(i).getDF_NORDEN().equals(curIdExamen);
    }

    sesion.close();
    return null;
  }

  // Ver lo de la lista en duro.
  public Boolean readyToSign(Long idExamen, Long nOrden) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      Short[] estadosResultadoOKFirma = { EstadosSistema.DFET_IDRESULTADOTEST_BLOQUEADO,
          EstadosSistema.DFET_IDRESULTADOTEST_FIRMADO };
      Short[] estadosResultadoPdteDigitado = { EstadosSistema.DFET_IDRESULTADOTEST_PENDIENTE,
          EstadosSistema.DFET_IDRESULTADOTEST_INGRESADO };

      String sqlQuery = " Select dfet from DatosFichasexamenestest dfet inner join fetch dfet.cfgExamenestest cet inner join fetch cet.cfgTest ct "
          + "where dfet.id.dfetIdexamen = :dfetIdexamen and dfet.id.dfetNorden = :dfetNorden and "
          + " ( dfet.dfetIdestadoresultadotest not in :listaEstadosOKFirma and ct.ctResultadoobligado like 'S' )";
//      + "(dfet.dfetIdestadoresultadotest not in :listaEstadosOKFirma or not (ct.ctResultadoobligado not like ('S') AND dfet.dfetIdestadoresultadotest in  :listaEstadosPdteDigitado ) ";
      Query query = sesion.createQuery(sqlQuery);
      query.setParameter("dfetIdexamen", idExamen);
      query.setParameter("dfetNorden", nOrden);
      query.setParameterList("listaEstadosOKFirma", estadosResultadoOKFirma);
//            query.setParameterList("listaEstadosPdteDigitado", estadosResultadoPdteDigitado);

      List<DatosFichasexamenestest> lst = (List<DatosFichasexamenestest>) query.list();
      return (lst == null || lst.isEmpty());
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("Error hibernate al chequear firma de examen.");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();

      }
    }

  }

  @SuppressWarnings("unchecked")
  public List<DatosFichasexamenestest> getSignedTestOrden(Long nOrden) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      String sqlQuery = " Select dfet from DatosFichasexamenestest dfet "
          + "where dfet.id.dfetNorden = :dfetNorden and dfet.dfetIdestadoresultadotest = 5)";
      Query query = sesion.createQuery(sqlQuery);
      query.setParameter("dfetNorden", nOrden);
      return (List<DatosFichasexamenestest>) query.list();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("Error hibernate al obtener examenes firmados.");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();

      }
    }

  }

  public List<DatosFichasexamenestest> getSignedTestOrden(Long idExamen, Long nOrden) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      String sqlQuery = " Select dfet from DatosFichasexamenestest dfet "
          + "where dfet.id.dfetIdexamen = :dfetIdexamen and dfet.id.dfetNorden = :dfetNorden and dfet.dfetIdestadoresultadotest = 5)";
      Query query = sesion.createQuery(sqlQuery);
      query.setParameter("dfetIdexamen", idExamen);
      query.setParameter("dfetNorden", nOrden);
      return (List<DatosFichasexamenestest>) query.list();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("Error hibernate al obtener examenes firmados.");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();

      }
    }

  }

  public CfgExamenesDAO getCfgExamenesDAO() {
    return cfgExamenesDAO;
  }

  public void setCfgExamenesDAO(CfgExamenesDAO cfgExamenesDAO) {
    this.cfgExamenesDAO = cfgExamenesDAO;
  }

  public Boolean checkEsTestRepetidoExamenes(Integer idTest, List<TestRepetidosDTO> lstTest)
      throws BiosLISDAOException {

    if (!lstTest.isEmpty()) {
      lstTest.removeIf(t -> (t.getIdTest() == idTest));
    }
    if (!lstTest.isEmpty()) {
      return true;
    }
    return false;

  }

  public String insertDatosFichasExamenesTest(int nOrden, CfgExamenesDTO examen, int datosPacientes, String ipEquipo,
      Long idUsuarioCrea) throws BiosLISDAOException, BiosLISDAONotFoundException {
    return this.insertDatosFichasExamenesTest(nOrden, (int) examen.getCeIdexamen(), datosPacientes, ipEquipo,
        idUsuarioCrea);
  }

  public void insertDatosFichasExamenesTestOpcional(Integer idOrden, Integer idExamen, Integer idTest,
      Integer idPaciente, Long idUsuario) throws BiosLISDAOException, BiosLISDAONotFoundException {

    if (cvrDAO == null) {
      throw new RuntimeException();
    }
    List<CfgExamenestest> listaTests = cet.getExamenTest(idExamen);
    logger.debug("Cantidad de test asociados al examen {}: {}", idExamen, listaTests.size());

    DatosFichasexamenestest dfet = new DatosFichasexamenestest();
    DatosFichasexamenestestId dfei = new DatosFichasexamenestestId();
    CfgTest cfgtest = cfgTestDAO.getTestById(idTest);
    short idEnvases = (short) cfgtest.getCfgEnvases().getCenvIdenvase();
    short idTomaMuestra = cfgtest.getCfgMuestras().getCmueIdtipomuestra();
    int idSeccion = cfgtest.getCfgSecciones();

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      DatosFichas df = (DatosFichas) sesion.get(DatosFichas.class, idOrden);
      dfei.setDfetIdexamen(idExamen);
      dfei.setDfetNorden(idOrden);
      dfei.setDfetIdtest(idTest);
      dfet.setId(dfei);
      dfet.setDfetIdenvase(idEnvases);
      dfet.setDfetIdtipomuestra(idTomaMuestra);
      dfet.setDfetIdseccion((short) idSeccion);
      dfet.setDfetFechaorden(
          df.getDfFechaorden() == null ? BiosLisCalendarService.getInstance().getTS() : df.getDfFechaorden());
      dfet.setDfetIdestadoresultadotest(EstadosSistema.DFET_IDRESULTADOTEST_PENDIENTE);
      dfet.setDfetIdunidaddemedida(
          Integer.valueOf(cfgtest.getCfgUnidadesdemedida().getCumIdunidadmedida()).shortValue());
      dfet.setDfetIdpaciente(idPaciente);
      dfet.setDfetFechacreaciontest(BiosLisCalendarService.getInstance().getTS());
      dfet.setDfetIdusuariocreacion(idUsuario); // agregado por cristian 18-01-2023
      CfgValoresreferencia vr = null;
      try {
        logger.debug("Se busca valores de referencia para {} {}", idTest, idPaciente);
        vr = cvrDAO.getValorReferencia(BigDecimal.valueOf(idTest), BigDecimal.valueOf(idPaciente));
      } catch (BiosLISDAONotFoundException e) {
        logger.error(e.getMessage());
      }
      if (vr != null) {
        if (vr.getCvrValorcriticobajo() != null) {
          dfet.setDfetValorcriticodesde(vr.getCvrValorcriticobajo().toString());
        }
        if (vr.getCvrValorcriticoalto() != null) {
          dfet.setDfetValorcriticodesde(vr.getCvrValorcriticoalto().toString());
        }
        if (vr.getCvrValorbajo() != null) {
          dfet.setDfetReferenciadesde(vr.getCvrValorbajo().toString());
        }
        if (vr.getCvrValoralto() != null) {
          dfet.setDfetReferenciahasta(vr.getCvrValoralto().toString());
        }
      }

      sesion.beginTransaction();
      sesion.save(dfet);
      sesion.getTransaction().commit();
      sesion.close();
      /*
       * BiosLisLogger bl = new BiosLisLogger(); try {
       * bl.logInsertDatosFichaTableRecord(DatosFichasexamenestest.class, dfet, new
       * BigDecimal(1), BiosLisCalendarService.getInstance().getTS(), new
       * BigDecimal(dfet.getId().getDfetNorden()), null, new
       * BigDecimal(dfet.getId().getDfetIdexamen()), null, null, new
       * BigDecimal(dfet.getId().getDfetIdtest()), "",
       * Constante.CREACION_DATOS_FICHAS);
       * 
       * } catch (IllegalArgumentException | IllegalAccessException e1) {
       * logger.error("No se pudo insertar registro de log de tabla.\n{}",
       * e1.getMessage()); }
       */
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("No se pudo ingresar Datos Ficha ExamenTest");
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return;
  }

  public CfgTestDAO getCfgTestDAO() {
    return cfgTestDAO;
  }

  public void setCfgTestDAO(CfgTestDAO cfgTestDAO) {
    this.cfgTestDAO = cfgTestDAO;
  }

  public void getDatosFichasExamenesTestOpcional(Integer idOrden, Integer idExamen, Integer idTest) {

  }

  public void setDatosFichasDAO(DatosFichasDAO datosFichasDAO) {
    this.datosFichasDAO = datosFichasDAO;
  }

  private String sqlSelectDatosFichasExamenesTestOpcional = "";

  @SuppressWarnings("unchecked")
  public Boolean updateFichasExamenesTest(DatosFichasDTO datosFichasExamenTest, Long idUsuario)
      throws BiosLISDAOException {

    DatosFichasDAO dfDao = new DatosFichasDAO();

    Query query;
    Session sesion = null;

    try {
      DatosFichas dfAntiguo = dfDao.getDatosOrden((long) datosFichasExamenTest.getDfNorden());
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      DatosFichas df = (DatosFichas) sesion.get(DatosFichas.class, datosFichasExamenTest.getDfNorden());

      df.setCfgPrioridadatencion(datosFichasExamenTest.getCfgPrioridadatencion());
      df.setCfgTipoatencion(datosFichasExamenTest.getCfgTipoatencion());
      df.setCfgProcedencias(datosFichasExamenTest.getCfgProcedencias());
      df.setCfgConvenios(datosFichasExamenTest.getCfgConvenios());
      df.setDfIdmedico(Integer.parseInt(datosFichasExamenTest.getDfIdmedico()));
      df.setCfgLocalizaciones(datosFichasExamenTest.getCfgLocalizaciones());
      df.setDfCodigolocalizacion(datosFichasExamenTest.getDfCodigolocalizacion());
      df.setCfgDiagnosticos(datosFichasExamenTest.getCfgDiagnosticos());
      df.setDfObservacion(datosFichasExamenTest.getDfObservacion());
      df.setCfgServicios(datosFichasExamenTest.getCfgServicios());
      if (datosFichasExamenTest.getDfEnviarresultadosemail().equals("on")) {
        df.setDfEnviarresultadosemail("S");
      } else {
        df.setDfEnviarresultadosemail("N");
      }

      sesion.update(df);

      List<LogEventosfichas> lefe = new ArrayList<LogEventosfichas>();
      // SE RECORRE EXAMENES QUE VIENEN DEL FRONT PARA AGREGAR O REALIZAR UPDATE A
      // COLUMAN EXAMENESURGENTE DE DATOS_FICHASEXAMENES
      for (CfgExamenesDTO examen : datosFichasExamenTest.getLstExamenesDto()) {

        // se realiza select para verificar si examen ya esta creada y realizar update
        // de lo contrario realizar insert
        query = sesion.createSQLQuery(
            "select dfe.dfe_norden  from datos_fichasexamenes dfe where dfe.dfe_norden=:nOrden and dfe.dfe_idexamen=:idExamen");
        query.setParameter("nOrden", datosFichasExamenTest.getDfNorden());
        query.setParameter("idExamen", examen.getCeIdexamen());

        List<Object[]> lista = query.list();

        ExamenEditarOrdenDTO result = getTiempoEntrega(examen.getCeEsurgente(),
            datosFichasExamenTest.getCfgTipoatencion(), examen.getCeIdexamen());

        if (result.getTIEMPOENTREGA().equals(null)) {
          result.setTIEMPOENTREGA(BigDecimal.valueOf(0));
        }

        // se realiza insert a datos_fichasexamenes debido a que examen no existia en la
        // tabla

        Timestamp ts = BiosLisCalendarService.getInstance().getTS();
        int tiempo = result.getTIEMPOENTREGA().intValueExact();
        Calendar calendar = this.fechaCalculoEntrega(ts, tiempo);

        // ***************************************************************************

        LogEventosfichas lef = new LogEventosfichas();
        if (lista.size() > 0) {

          // update de esurgente de datos_fichasexamenes debido a que examen ya existia

          DatosFichasexamenesId dfi = new DatosFichasexamenesId(datosFichasExamenTest.getDfNorden(),
              examen.getCeIdexamen());
          DatosFichasexamenes dfe = (DatosFichasexamenes) sesion.get(DatosFichasexamenes.class, dfi);

          if (!dfe.getDfeExamenesurgente().equals(examen.getCeEsurgente())
              || !dfe.getDfeExamenAnulado().equals(examen.getCeActivo())) {
            lef.setCfgEventos(2);
            lef.setLefNombretabla("DATOS_FICHASEXAMENES");
            lef.setDatosFichas((int) dfi.getDfeNorden());
            lef.setLefIdexamen((int) dfi.getDfeIdexamen());
            lef.setLefIdpaciente(df.getDatosPacientes());
            lef.setLefIdusuario(idUsuario.intValue());
            lef.setLefFechaorden(df.getDfFechaorden());
            lef.setLefFecharegistro(ts);
            if (!dfe.getDfeExamenesurgente().equals(examen.getCeEsurgente())) {
              lef.setLefNombrecampo("DFE_EXAMENESURGENTE");
              lef.setLefValoranterior(dfe.getDfeExamenesurgente());
              lef.setLefValornuevo(examen.getCeEsurgente());
              lefe.add(lef);
            }
            if (!dfe.getDfeExamenAnulado().equals(examen.getCeActivo())) {
              lef.setLefNombrecampo("DFE_EXAMENANULADO");
              lef.setLefValoranterior(dfe.getDfeExamenAnulado());
              lef.setLefValornuevo(examen.getCeActivo());
              lefe.add(lef);
            }

          }
          dfe.setDfeExamenesurgente(examen.getCeEsurgente());
          // dfe.setDfeExamenAnulado(examen.getCeActivo());

          if ((examen.getCeActivo().equals("S") || examen.getCeActivo().isEmpty())
              && (dfe.getDfeExamenAnulado().equals("N") || dfe.getDfeExamenAnulado().isEmpty())) {

            dfe.setDfeExamenAnulado(examen.getCeActivo()); // en este dato se trae si esta anulado
            dfe.setDfeIdusuarioAnula(idUsuario);
          }
          sesion.update(dfe);

        } else {
          // se realiza insert a datos_fichasexamenes debido a que examen no existia en la
          // tabla
          // se calcula tiempo de entrega ***********************************
          // System.out.println("tiempo entrega " + result.getTIEMPOENTREGA());
          ts = BiosLisCalendarService.getInstance().getTS();
          calendar.setTime(ts); // tuFechaBase es un Date;
          tiempo = result.getTIEMPOENTREGA().intValueExact();
          calendar.add(Calendar.MINUTE, tiempo); // minutosASumar es int.

          // ***************************************************************************

          query = sesion.createSQLQuery(
              "insert into  datos_fichasexamenes (dfe_norden, dfe_idexamen, dfe_fechacreacion, dfe_fechaentrega ,dfe_idusuario, dfe_cantidad, dfe_idderivador, dfe_examenesurgente, "
                  + " dfe_examenanulado,dfe_codigoestadoexamen)"
                  + " values(:nOrden, :idExamen, :fecha, :fechaEntrega ,:idUsuario , :cantidad, :idDerivador, :examenUrgente, :anulado,:estadoExamen)");
          query.setLong("nOrden", datosFichasExamenTest.getDfNorden());
          query.setLong("idExamen", examen.getCeIdexamen());
          query.setParameter("fecha", ts);
          query.setParameter("fechaEntrega", calendar.getTime());
          query.setLong("idUsuario", idUsuario);
          query.setParameter("cantidad", result.getCANTIDAD());
          query.setParameter("idDerivador", result.getIDDERIVADOR());
          query.setString("examenUrgente", examen.getCeEsurgente());
          query.setString("anulado", "N");
          query.setParameter("estadoExamen", "P");
          query.executeUpdate();

          // aqui se envia a gravar enevento de cambio de datos en DATOS_FICHAS

          lef.setDatosFichas(datosFichasExamenTest.getDfNorden());
          lef.setLefFechaorden(datosFichasExamenTest.getDfFechaorden());
          lef.setLefIdpaciente(df.getDatosPacientes());
          lef.setLefIdexamen((int) examen.getCeIdexamen());
          lef.setLefNombretabla("DATOS_FICHASEXAMENES");
          lef.setCfgEventos(1);
          lef.setLefFecharegistro(ts);
          lef.setLefIdusuario(idUsuario.intValue());
          lef.setLefNombrecampo("");
          lef.setLefValoranterior("");
          lef.setLefValornuevo("");
          lefe.add(lef);

          // ***********************************

          // se toman los datos necesarios para insertar en Datos_fichasexamenestest
          query = sesion.createSQLQuery(
              "select ct.ct_idtest as idtest, cet.cet_idexamen as idexamen, ct.ct_idtipomuestra as idtipomuestra, ct.ct_idseccion as idseccion , "
                  + "cmt.cmt_idmetodo as idmetodo, cmt.cmt_esvalorpordefecto as valorpordefecto  , ct.ct_idenvase as idenvase "
                  + "from cfg_test ct " + "inner join cfg_examenestest  cet " + "on cet.cet_idtest = ct.ct_idtest "
                  + "left join cfg_metodostest cmt " + "on cmt.cmt_idtest = cet.cet_idtest "
                  + "where cet.cet_idexamen =:idExamen and (cmt.cmt_esvalorpordefecto ='S' or cmt.cmt_esvalorpordefecto is null) order by ct.ct_idtipomuestra Desc")
              .setResultTransformer(Transformers.aliasToBean(TestEdicionOrdenDTO.class));

          query.setParameter("idExamen", examen.getCeIdexamen());
          List<TestEdicionOrdenDTO> listTest = query.list();

          List<TestEdicionOrdenDTO> listTest2 = null;

          for (TestEdicionOrdenDTO test : listTest) { // lista de test que le corresponden a un examen

            // agregado para tomar datos de examenes test con idenvase e idmuestra
            query = sesion.createSQLQuery("select dfet.dfet_idmuestra as idmuestra from datos_fichasexamenestest dfet "
                + "where dfet.dfet_norden =:nOrden and dfet.dfet_idenvase =:idEnvase and dfet.dfet_idtipomuestra=:idTipoMuestra  order by dfet.dfet_idmuestra Asc")
                .setResultTransformer(Transformers.aliasToBean(TestEdicionOrdenDTO.class));

            query.setParameter("nOrden", datosFichasExamenTest.getDfNorden());
            query.setParameter("idEnvase", test.getIDENVASE());
            query.setParameter("idTipoMuestra", test.getIDTIPOMUESTRA());
            listTest2 = query.list();

            if (listTest2.size() > 0) {
              test.setIDMUESTRA(listTest2.get(0).getIDMUESTRA());

            }

            String queryInsert = "insert into  datos_fichasexamenestest (dfet_norden, dfet_idexamen, dfet_idtest, dfet_idpaciente, "
                + " dfet_fechaorden, dfet_idtipomuestra, dfet_idenvase, dfet_idseccion , dfet_fechacreaciontest, dfet_idusuariocreacion ";
            String queryInsert2 = " ) values(:nOrden, :idExamen, :idTest, :idPaciente , :fechaOrden, :idTipoMuestra, :idEnvase, :idSeccion, :fechaCreacion, :idUsuario ";
            if (test.getIDMETODO() != null) {
              queryInsert += " , dfet_idmetodo  ";
              queryInsert2 += " , :idMetodo ";
            }
            if (test.getIDMUESTRA() != null) {
              queryInsert += " , dfet_idmuestra ";
              queryInsert2 += " , :idMuestra ";
            }

            query = sesion.createSQLQuery(queryInsert + queryInsert2 + " )");

            logger.debug(test.getIDMUESTRA());
            query.setLong("nOrden", datosFichasExamenTest.getDfNorden());
            query.setParameter("idExamen", examen.getCeIdexamen());
            query.setParameter("idTest", test.getIDTEST());
            query.setLong("idPaciente", datosFichasExamenTest.getDatosPacientes());
            query.setParameter("fechaOrden", datosFichasExamenTest.getDfFechaorden());
            query.setParameter("idTipoMuestra", test.getIDTIPOMUESTRA());
            query.setParameter("idEnvase", test.getIDENVASE());
            query.setParameter("idSeccion", test.getIDSECCION());
            query.setParameter("fechaCreacion", ts);
            query.setLong("idUsuario", idUsuario);
            if (test.getIDMUESTRA() != null) {
              query.setParameter("idMuestra", test.getIDMUESTRA());
            }
            if (test.getIDMETODO() != null) {
              query.setParameter("idMetodo", test.getIDMETODO());
            }
            query.executeUpdate();

          }
        }
      } // **** hasta aqui el for

      sesion.getTransaction().commit();
      sesion.close();

      // aqui se envia a gravar evento de cambio de datos en DATOS_FICHAS

      biosLisLogEventos.comparadorObjetos(dfAntiguo, df, 2, idUsuario.intValue(), "equipo", "000.000.000");
      // ***********************************
      // aqui se envian a grabar eventos examenes agregados, anulados o urgentes en
      // DATOS_FICHASEXAMENES

      LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
      for (LogEventosfichas eventos : lefe) {
        lefDAO.insertLogEventosFichas(eventos);
      }
      // **************************************
    } catch (HibernateException he) {
      sesion.getTransaction().rollback();
      logger.error(he.getMessage() + he.getCause());
      he.printStackTrace();
      throw new BiosLISDAOException("No se pudo Actualizar  Datos Ficha ExamenTest");
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return true;
  }

  // Se crean dos funciones para ser llamadas de update e insert de fichasexamenes
  // entrega ExamenEditarOrdenDTO con tiempo de entrega
  private ExamenEditarOrdenDTO getTiempoEntrega(String esUrgente, int tipoAtencion, Long idExamen)
      throws BiosLISDAOException {

    ExamenEditarOrdenDTO result = null;
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String consulta = "select ce.ce_idexamen as idexamen, ce.ce_contablecantidad as cantidad, "
        + " ce.ce_idderivador as idderivador, ";

    if (esUrgente.equals("S") || tipoAtencion == 3) {

      // se toma cantidad urgente;
      consulta = consulta + " CASE " + "        WHEN CE.CE_TIEMPOENTREGAURGENTE IS NOT NULL "
          + "            THEN CE.CE_TIEMPOENTREGAURGENTE " + "        ELSE 0 " + "        END AS tiempoentrega "
          + " from cfg_examenes  ce where ce.ce_idexamen = :idExamen";
    } else {
      if (tipoAtencion == 1 || tipoAtencion == 4) {
        consulta = consulta + " CASE " + "        WHEN CE.CE_TIEMPOENTREGANORMAL IS NOT NULL "
            + "            THEN CE.CE_TIEMPOENTREGANORMAL " + "        ELSE 0 " + "        END AS tiempoentrega "
            + " from cfg_examenes  ce where ce.ce_idexamen = :idExamen";
      } else {
        consulta = consulta + " CASE " + "        WHEN CE.CE_TIEMPOENTREGAHOSPITALIZADO IS NOT NULL "
            + "            THEN CE.CE_TIEMPOENTREGAHOSPITALIZADO " + "        ELSE 0 " + "        END AS tiempoentrega "
            + " from cfg_examenes  ce where ce.ce_idexamen = :idExamen";
      }
    }
    try {

      Query query = sesion.createSQLQuery(consulta)
          .setResultTransformer(Transformers.aliasToBean(ExamenEditarOrdenDTO.class));
      query.setLong("idExamen", idExamen);
      result = (ExamenEditarOrdenDTO) query.uniqueResult();

    } catch (HibernateException he) {

      logger.error(he.getMessage() + he.getCause());
      he.printStackTrace();
      throw new BiosLISDAOException("No se pudo leer datos tiempo entrega");
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }

    return result;
  }

  private Calendar fechaCalculoEntrega(Timestamp ts, int tiempo) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(ts); // tuFechaBase es un Date;
    calendar.add(Calendar.MINUTE, tiempo); // minutosASumar es int.
    return calendar;
  }

  public static final String sqlResultadosTestDeUnaOrden = ""
      + "SELECT df.DF_NORDEN,dfe.DFE_IDEXAMEN,dfet.DFET_IDTEST,ce.CE_ABREVIADO , ct.ct_abreviado, "
      + "ctr.CTR_DESCRIPCION ,ct.CT_FORMULA ,cu.CUM_DESCRIPCION ,ct.CT_IDUNIDADMEDIDA , CE.CE_ABREVIADO2, "
      + "cert.CERT_DESCRIPCIONESTADOTEST,cert.CERT_IDESTADORESULTADOTEST, DFET.DFET_ULTIMORESULTADOANT , DFET.DFET_FECHAULTIMORESULTADO ," // agregado
      // por
      // cristian
      // 21-02
      + "dfet.DFET_RESULTADONUMERICO ,dfet.DFET_IDUNIDADDEMEDIDA,dfet.DFET_REFERENCIADESDE, "
      + "dfet.DFET_REFERENCIAHASTA,ctr.CTR_CODIGO,dfet.DFET_RESULTADO, "
      + "dfet.DFET_RCRITICO, dp.DP_IDPACIENTE, dfet.DFET_IDMUESTRA,ct.CT_DECIMALES ,dfm.DFM_CODIGOBARRA,"
      + "ct.CT_RESULTADOOMISION,ct.CT_RESULTADOOBLIGADO,ct.CT_ESCULTIVO,ct.CT_IDSECCION ,"
      + "(select cg.cg_idglosa from cfg_glosas cg where cg.cg_descripcion = dfet.dfet_resultado and  rownum = 1 ) as CG_IDGLOSA ," // agregado
      // por
      // cristian
      // 30-01
      // para
      // traer
      // id
      // de
      // glosa
      + "  (select cmet.cmet_descripcion from cfg_metodostest cmt inner join cfg_metodos cmet on cmet.cmet_idmetodo = cmt.cmt_idmetodo "
      + "      where cmt.cmt_idtest = dfet.dfet_idtest and cmt.cmt_esvalorpordefecto = 'S') as metodo "
      + " FROM BIOSLIS.DATOS_FICHAS df " + "INNER JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE) "
      + "INNER JOIN DATOS_FICHASEXAMENES dfe ON (df.DF_NORDEN = dfe.DFE_NORDEN) "
      + "INNER JOIN DATOS_FICHASEXAMENESTEST dfet ON (dfet.DFET_NORDEN = dfe.DFE_NORDEN AND dfet.DFET_IDEXAMEN = dfe.DFE_IDEXAMEN) "
      + "INNER JOIN cfg_examenes ce ON (dfe.DFE_IDEXAMEN = ce.CE_IDEXAMEN) "
      + "inner join cfg_test ct on ct.ct_idtest = dfet.DFET_IDTEST "
      + "INNER JOIN BIOSLIS.CFG_TIPOSDERESULTADO ctr ON (ctr.CTR_IDTIPORESULTADO = ct.CT_IDTIPORESULTADO) "
      // + "INNER JOIN BIOSLIS.CFG_ESTADOSRESULTADOSTEST cert ON
      // (cert.CERT_IDESTADORESULTADOTEST = dfet.DFET_IDESTADORESULTADOTEST) "
      + "INNER JOIN CFG_ESTADOSRESULTADOSTEST cert ON (cert.CERT_IDESTADORESULTADOTEST = dfet.DFET_IDESTADORESULTADOTEST) "
      + "INNER JOIN BIOSLIS.CFG_UNIDADESDEMEDIDA cu ON (cu.CUM_IDUNIDADMEDIDA = ct.CT_IDUNIDADMEDIDA) "
      + "LEFT OUTER JOIN BIOSLIS.DATOS_FICHASMUESTRAS dfm ON (dfm.DFM_IDMUESTRA = dfet.DFET_IDMUESTRA)" + " WHERE  "
      + " df.DF_NORDEN = :nOrden AND dfe.DFE_IDEXAMEN = :idExamen and dfet.DFET_IDTEST = :idTest" + " ORDER BY 1";

  public ExamenesResultadosDeUnaOrdenDTO getResultadoTest(Integer nOrden, Integer idExamen, Integer idTest)
      throws BiosLISDAOException, BiosLISDAONotFoundException {

    Session sesion = null;

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query qry = sesion.createSQLQuery(sqlResultadosTestDeUnaOrden)
          .setResultTransformer(Transformers.aliasToBean(ExamenesResultadosDeUnaOrdenDTO.class));
      qry.setInteger("nOrden", nOrden);
      qry.setInteger("idExamen", idExamen);
      qry.setInteger("idTest", idTest);
      ExamenesResultadosDeUnaOrdenDTO resultado = (ExamenesResultadosDeUnaOrdenDTO) qry.uniqueResult();
      sesion.close();

      if (resultado == null) {
        throw new BiosLISDAONotFoundException("No se pudo recuperar resultado de un test. ");
      } else {
        try {
          List<DeltaCheckDTO> deltaCheckLst = this.datosPacientesDAO
              .getDeltaCheckTestPaciente(resultado.getDP_IDPACIENTE().longValue(), idTest.longValue());
          resultado.setDeltaCheckLst(deltaCheckLst);
          DeltaCheckAptoDTO dcaDto = datosPacientesDAO.getDeltaCheckCalculadoTestPaciente(
              resultado.getDP_IDPACIENTE().longValue(), idTest.longValue(), nOrden.longValue());
          if (dcaDto != null) {
            dcaDto.setDFET_RESULTADONUMERICO(resultado.getDFET_RESULTADONUMERICO());
            resultado.setDeltaCheckCalculated(dcaDto.toDeltaCheckDTO());
          }

          if (resultado.getCT_FORMULA() != null) {
            resultado.setFORMULATRADUCIDA(getFormula(resultado.getCT_FORMULA()));
          } else {
            resultado.setFORMULATRADUCIDA(null);
          }
          // agregado por cristian 22-02 14:09 para calcular valores referencia cada vez
          // que se carge la orden el captura resultados
          CfgValoresreferencia vr = null;
          try {
            logger.debug("Se busca valores de referencia para {} {}", resultado.getDFET_IDTEST().longValue(),
                resultado.getDP_IDPACIENTE().longValue());
            vr = cvrDAO.getValorReferencia(BigDecimal.valueOf(resultado.getDFET_IDTEST().longValue()),
                BigDecimal.valueOf(resultado.getDP_IDPACIENTE().longValue()));
          } catch (BiosLISDAONotFoundException e) {
            logger.error(e.getMessage());
          }
          if (vr != null) {

            if (vr.getCvrValorbajo() != null) {
              resultado.setDFET_REFERENCIADESDE(vr.getCvrValorbajo().toString());
            }
            if (vr.getCvrValoralto() != null) {
              resultado.setDFET_REFERENCIAHASTA(vr.getCvrValoralto().toString());
            }

          }
          // ***************** hasta aqui valor referencia
          // /****************************************

        } catch (NullPointerException e) {
          e.printStackTrace();
        }
        CapturaResultadosDAO capturaResultadosDAO = new CapturaResultadosDAO();
        List<ResultadoTestPacienteDTO> lstResultado = capturaResultadosDAO
            .getHistoriaDeUnTestDePaciente(resultado.getDP_IDPACIENTE(), resultado.getDFET_IDTEST());
        logger.debug("Nro de registros de historia {} para test {}", lstResultado != null ? lstResultado.size() : -1,
            resultado.getDFET_IDTEST());
        if (lstResultado != null && lstResultado.size() > 0) {
          logger.debug("Resultado {}", lstResultado.get(0));
          StringBuilder sb = new StringBuilder("");

          for (ResultadoTestPacienteDTO resultadoTestPacienteDTO : lstResultado) {

            sb.append(resultadoTestPacienteDTO.getDFET_RESULTADO());
            sb.append("|");
            sb.append(resultadoTestPacienteDTO.getDFET_FECHAINGRESORESULTADO());
            sb.append(",");

          }

          resultado.setResultadoHistorico(sb.toString());

        }
      }

      return resultado;
    } catch (HibernateException he) {
      logger.error("No se pudo recuperar resultado de un test. {}", he.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo recuperar resultado de un test. :".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }

    }

  }

  @SuppressWarnings("unchecked")
  public List<DatosFichasexamenestest> getResultadosDigitados(Integer nroOrden, Integer idExamen)
      throws BiosLISDAONotFoundException, BiosLISDAOException {
    Session sesion = null;

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();

      Query qry = sesion.createQuery(hqlResultadosEnUnEstadoExamen);
      qry.setInteger("dfetIdexamen", idExamen);
      qry.setInteger("dfetNorden", nroOrden);
      // qry.setInteger("idEstadoResultado",
      // EstadosSistema.DFET_IDRESULTADOTEST_DIGITADO);
      // qry.setInteger("idEstadoResultadoFirmado",
      // EstadosSistema.DFET_IDRESULTADOTEST_FIRMADO);
      List<DatosFichasexamenestest> lst = qry.list();
      sesion.close();
      if (lst == null) {
        throw new BiosLISDAONotFoundException("No hay resultados digitados");
      }
      return lst;
    } catch (HibernateException he) {
      logger.error("No se pudo buscar resultados digitados de examen.", he.getMessage());
      throw new BiosLISDAOException(he.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }
  }

  private static String hqlResultadosEnUnEstadoExamen = "select dfet from DatosFichasexamenestest dfet " + " where "
      + " dfet.id.dfetIdexamen = :dfetIdexamen and dfet.id.dfetNorden = :dfetNorden "; // and
  // (dfet.dfetIdestadoresultadotest
  // = :idEstadoResultado
  // "
  // + " OR dfet.dfetIdestadoresultadotest =:idEstadoResultadoFirmado ) ";

  private static String hqlDatosFichasExamenesTestCriticosByOrden = "select dfet "
      + "from com.grupobios.bioslis.entity.DatosFichasexamenestest dfet " + "left join fetch dfet.cfgExamenestest cet "
      + "left join fetch cet.cfgEnvases ce " + "left join fetch cet.cfgTest ct "
      + "left join fetch ct.cfgTiposderesultado ctr " + "left join fetch ct.cfgUnidadesdemedida cum "
      + "where dfet.id.dfetNorden =:orden and dfet.dfetRcritico not in ('N','A','B') ";

  private static String hqlDatosFichasExamenesTestCriticosByOrdenExamen = "select dfet "
      + "from com.grupobios.bioslis.entity.DatosFichasexamenestest dfet " + "left join fetch dfet.cfgExamenestest cet "
      + "left join fetch cet.cfgEnvases ce " + "left join fetch cet.cfgTest ct "
      + "left join fetch ct.cfgTiposderesultado ctr " + "left join fetch ct.cfgUnidadesdemedida cum "
      + "where dfet.id.dfetNorden =:orden and dfet.id.dfetIdexamen=:examenId and dfet.dfetRcritico not in ('N','A','B')";

  public List<DatosFichasexamenestest> getDatosFichasExamenesTestCriticosByOrden(Long nroOrden)
      throws BiosLISDAOException {

    Session sesion = null;

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery(hqlDatosFichasExamenesTestCriticosByOrden);
      query.setLong("orden", nroOrden);
      List<DatosFichasexamenestest> listaFichas = query.list();
      return listaFichas;
    } catch (HibernateException he) {
      logger.error("");
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }

  }

  public List<BigDecimal> getExamenByOrdenAndTest(Long orden, Long idTest) throws BiosLISDAOException {

    Session sesion = null;
    List<BigDecimal> listaExamen = new ArrayList<BigDecimal>();
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createSQLQuery(
          "Select dfet.dfet_idExamen from Datos_fichasexamenestest dfet where dfet.dfet_norden = :orden and dfet.dfet_idTest =:idTest");
      query.setLong("orden", orden);
      query.setLong("idTest", idTest);
      listaExamen = query.list();
      sesion.close();
    } catch (HibernateException he) {
      logger.error("");
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }
    return listaExamen;
  }

//este codigo tambien esta en capturaresultadosDAO  ---  daba resultado null point por eso se tubo que copiar aca
  public String getFormula(String formula) {
    List<String> valoresId = new ArrayList<String>();

    char[] chars = formula.toCharArray();
    for (int i = 0; i < chars.length; i++) {

      if (chars[i] == '[') {
        String valor = "";
        i++;
        for (int j = i; j < chars.length; j++) {
          if (chars[j] != ']') {
            valor = valor + chars[j];
          } else {
            j = chars.length + 1;
          }
          i++;
        }
        if (valor != null) {
          valoresId.add(valor);
        }

      }
    }
    CfgTest test = new CfgTest();
    String dato = "";

    for (String id : valoresId) {

      try {

        if (id.contains("A")) {
          dato = id;
          dato = dato.substring(1, dato.length());
          CfgAntecedentestestDAO antecedenteDAO = new CfgAntecedentestestDAO();
          CfgAntecedentes antedecente = antecedenteDAO.getAntecedenteById(Integer.parseInt(dato));
          dato = antedecente.getCaDescripcion();

        } else {
          int idTest = Integer.parseInt(id);
          test = getCfgTestDAO().getTestById(idTest);
          dato = test.getCtAbreviado();
        }

        formula = formula.replace("[" + id + "]", "[" + dato + "]");
      } catch (NumberFormatException e) {
        e.printStackTrace();
      } catch (BiosLISDAONotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (BiosLISDAOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return formula;
  }

  public List<DatosFichasexamenestest> getDatosFichasExamenesTestCriticosByOrdenExamen(Long nroOrden, Long examenId)
      throws BiosLISDAOException {
    Session sesion = null;

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery(hqlDatosFichasExamenesTestCriticosByOrdenExamen);
      query.setLong("orden", nroOrden);
      query.setLong("examenId", examenId);
      List<DatosFichasexamenestest> listaFichas = query.list();
      return listaFichas;
    } catch (HibernateException he) {
      logger.error(he.getLocalizedMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }

    }
  }

  public String insertDatosFichasExamenesTest(int nOrden, CfgExamenesDTO examen, int datosPacientes, String ipEquipo,
      Long idUsuarioCrea, Session sesion) throws BiosLISDAOException, BiosLISDAONotFoundException {
    return this.insertDatosFichasExamenesTest(nOrden, (int) examen.getCeIdexamen(), datosPacientes, ipEquipo,
        idUsuarioCrea, sesion);
  }

  private String insertDatosFichasExamenesTest(int idOrden, int idExamen, int idPaciente, String ipEquipo,
      Long idUsuarioCrea, Session sesion) throws BiosLISDAOException, BiosLISDAONotFoundException {
    if (cvrDAO == null) {
      throw new RuntimeException();
    }
    String mensaje = "";

    List<CfgExamenestest> listaTests = cet.getExamenTest(idExamen, sesion);
    logger.debug("Cantidad de test asociados al examen {}: {}", idExamen, listaTests.size());
    logger.debug(listaTests + "////////////////////////////////////////////////////////////////////////////////");
    try {

      // agregado por cristian 03-11 para rescatar urgente y tipoatencion y buscar
      // tiempoentrega **********

      Query query;
//      sesion = HibernateUtil.getSessionFactory().openSession();
      DatosFichas df = (DatosFichas) sesion.get(DatosFichas.class, idOrden);

      DatosFichasexamenesId dfid = new DatosFichasexamenesId(idOrden, idExamen);
      DatosFichasexamenes dfe = (DatosFichasexamenes) sesion.get(DatosFichasexamenes.class, dfid);

      Long id = (long) idExamen;
      ExamenEditarOrdenDTO result = getTiempoEntrega(dfe.getDfeExamenesurgente(), df.getCfgTipoatencion(), id, sesion);

      if (result.getTIEMPOENTREGA().equals(null)) {
        result.setTIEMPOENTREGA(BigDecimal.valueOf(0));
      }

      Timestamp ts = BiosLisCalendarService.getInstance().getTS(sesion);
      int tiempo = result.getTIEMPOENTREGA().intValueExact();
      Calendar calendar = this.fechaCalculoEntrega(ts, tiempo);

      dfe.setDfeFechaentrega(calendar.getTime());
      sesion.update(dfe);

      // *****************************************************************************************************

      for (CfgExamenestest test : listaTests) {

        DatosFichasexamenestest dfet = new DatosFichasexamenestest();
        DatosFichasexamenestestId dfei = new DatosFichasexamenestestId();

        logger.debug("Se ingresa test {}", test);
        int idTest = test.getCfgTest().getCtIdtest();
        logger.debug(
            idTest + "***********************************************************************************************");
        CfgTest cfgtest = cfgTestDAO.getTestById(idTest, sesion);
        if (cfgtest.getCtOpcional().equals("S")) {
          continue;
        }

        short idEnvases = (short) test.getCfgTest().getCfgEnvases().getCenvIdenvase();
        logger.debug(idEnvases
            + "-----------------------------------------------------------------------------------------------");
        short idTomaMuestra = cfgtest.getCfgMuestras().getCmueIdtipomuestra();
        int idSeccion = cfgtest.getCfgSecciones();
        dfei.setDfetIdexamen(idExamen);
        dfei.setDfetNorden(idOrden);
        dfei.setDfetIdtest(idTest);
        dfet.setId(dfei);
        dfet.setDfetIdenvase(idEnvases);
        dfet.setDfetIdtipomuestra(idTomaMuestra);
        dfet.setDfetIdseccion((short) idSeccion);
        dfet.setDfetFechaorden(ts);
        dfet.setDfetIdestadoresultadotest(EstadosSistema.DFET_IDRESULTADOTEST_INGRESADO);
        dfet.setDfetIdunidaddemedida(
            Integer.valueOf(cfgtest.getCfgUnidadesdemedida().getCumIdunidadmedida()).shortValue());
        dfet.setDfetIdpaciente(idPaciente);
        dfet.setDfetFechacreaciontest(ts);
        dfet.setDfetIdusuariocreacion(idUsuarioCrea);

        // agregado por cristian para almacenar idmetodo ***
        // 04-11******************************

        // sesion = HibernateUtil.getSessionFactory().openSession();
        query = sesion.createSQLQuery("select  cmt_idmetodo  from cfg_metodostest "
            + "        where cmt_idTest=:idTest and cmt_esvalorpordefecto = 'S' ");

        query.setLong("idTest", test.getId().getCetIdtest());
        BigDecimal metodo = (BigDecimal) query.uniqueResult();

        dfet.setDfetIdmetodo(metodo);

        // agregado por cristian 20-02 para agregar resultado y fecha de ultimo test
        // firmado ****************
        query = sesion.createSQLQuery(
            "select dfet.dfet_resultado as RESULTADOTEXTO, dfet.dfet_fechaingresoresultado as FECHARESULTADO from datos_fichasexamenestest dfet "
                + "where dfet.dfet_idpaciente =:idPaciente and dfet.dfet_idtest =:idTest and dfet.dfet_idestadoresultadotest = 5 and ROWNUM = 1 "
                + "order by dfet.dfet_norden desc")
            .setResultTransformer(Transformers.aliasToBean(ResultadoNumericoTestExamenOrdenDTO.class));

        query.setParameter("idPaciente", dfet.getDfetIdpaciente());
        query.setParameter("idTest", dfet.getId().getDfetIdtest());

        List<ResultadoNumericoTestExamenOrdenDTO> datosResultados = query.list();
        if (datosResultados.size() > 0) {
          dfet.setDfetfechaultimoresultado(datosResultados.get(0).getFECHARESULTADO());
          dfet.setDfetUltimoresultadoant(datosResultados.get(0).getRESULTADOTEXTO());
        }

        // ***************************************************************************************

//        sesion.beginTransaction();
        logger.debug(dfet);
        sesion.save(dfet);
        sesion.flush();
//        sesion.getTransaction().commit();
//        sesion.close();
      }
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      he.printStackTrace();
      throw new BiosLISDAOException("No se pudo ingresar Datos Ficha ExamenTest");
    } finally {
//      if (sesion != null && sesion.isOpen()) {
//        sesion.close();
//      }
    }
    return mensaje;
  }

  // Se crean dos funciones para ser llamadas de update e insert de fichasexamenes
  // entrega ExamenEditarOrdenDTO con tiempo de entrega
  private ExamenEditarOrdenDTO getTiempoEntrega(String esUrgente, int tipoAtencion, Long idExamen, Session sesion)
      throws BiosLISDAOException {

    ExamenEditarOrdenDTO result = null;
    String consulta = "select ce.ce_idexamen as idexamen, ce.ce_contablecantidad as cantidad, "
        + " ce.ce_idderivador as idderivador, ";

    if (esUrgente.equals("S") || tipoAtencion == 3) {

      // se toma cantidad urgente;
      consulta = consulta + " CASE " + "        WHEN CE.CE_TIEMPOENTREGAURGENTE IS NOT NULL "
          + "            THEN CE.CE_TIEMPOENTREGAURGENTE " + "        ELSE 0 " + "        END AS tiempoentrega "
          + " from cfg_examenes  ce where ce.ce_idexamen = :idExamen";
    } else {
      if (tipoAtencion == 1 || tipoAtencion == 4) {
        consulta = consulta + " CASE " + "        WHEN CE.CE_TIEMPOENTREGANORMAL IS NOT NULL "
            + "            THEN CE.CE_TIEMPOENTREGANORMAL " + "        ELSE 0 " + "        END AS tiempoentrega "
            + " from cfg_examenes  ce where ce.ce_idexamen = :idExamen";
      } else {
        consulta = consulta + " CASE " + "        WHEN CE.CE_TIEMPOENTREGAHOSPITALIZADO IS NOT NULL "
            + "            THEN CE.CE_TIEMPOENTREGAHOSPITALIZADO " + "        ELSE 0 " + "        END AS tiempoentrega "
            + " from cfg_examenes  ce where ce.ce_idexamen = :idExamen";
      }
    }
    try {

      Query query = sesion.createSQLQuery(consulta)
          .setResultTransformer(Transformers.aliasToBean(ExamenEditarOrdenDTO.class));
      query.setLong("idExamen", idExamen);
      result = (ExamenEditarOrdenDTO) query.uniqueResult();

    } catch (HibernateException he) {

      logger.error(he.getMessage() + he.getCause());
      he.printStackTrace();
      throw new BiosLISDAOException("No se pudo leer datos tiempo entrega");
    } finally {
//      if (sesion != null && sesion.isOpen()) {
//        sesion.close();
//      }
    }

    return result;
  }

  public List<DatosFichasexamenestest> buscarDFETforMuestras(Long nOrden, short shortValue, short shortValue0,
      short shortValue1, String comparteMuestra) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

}
