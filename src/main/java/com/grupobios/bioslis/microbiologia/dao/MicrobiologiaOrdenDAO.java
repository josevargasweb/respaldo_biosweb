package com.grupobios.bioslis.microbiologia.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.AntibiogramaMicrobiologiaDTO;
import com.grupobios.bioslis.dto.AntibioticoMicrobiologiaDTO;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.DatosAntibiogramMicrobiologiaDTO;
import com.grupobios.bioslis.dto.DatosAntibioticosMicrobiologiaDTO;
import com.grupobios.bioslis.dto.MethodsMicrobiologiaDTO;
import com.grupobios.bioslis.dto.OrdenExamenCapturaResultadoDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenOrden;
import com.grupobios.bioslis.entity.CfgBaczonacuerpo;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenesId;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasexamenestestId;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaActionsDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaExamDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaOrdenPacienteDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaTestDTO;
import com.grupobios.bioslis.microbiologia.dto.MicroorganismoMicrobiologiaDTO;

//se crea  pero por el momento no se esta utilizando  **creada 08-11 por cristian

public class MicrobiologiaOrdenDAO {

  private static Logger logger = LogManager.getLogger(MicrobiologiaOrdenDAO.class);

  private static final String sqlGetMicrobiologiaOrden = "select df.df_norden, df.df_fechaorden, dp.dp_nombres , dp.dp_primerapellido, dp.dp_segundoapellido, dp.dp_fnacimiento, ldvs.ldvs_descripcion ,"
      + "cta.cta_descripcion, cs.cs_descripcion, (select  ce.ce_escultivo from cfg_examenes ce "
      + "inner join datos_fichasexamenes dfesel " + "on ce.ce_idexamen = dfesel.dfe_idexamen "
      + "where dfesel.dfe_norden = df.df_norden " + "order by ce.ce_escultivo DESC "
      + "FETCH FIRST 1 ROWS Only) as ce_escultivo " + "from datos_fichas df " + "inner join datos_pacientes dp "
      + "on dp.dp_idpaciente = df.df_idpaciente " + "inner join ldv_sexo ldvs " + "on ldvs.ldvs_idsexo = dp.dp_idsexo "
      + "inner join cfg_tipoatencion cta " + "on cta.cta_idtipoatencion = df.df_idtipoatencion "
      + "inner join cfg_servicios cs " + "on cs.cs_idservicio = df.df_idservicio " + "where  ";
  private static final String sqlGetTestMicrobiologia = "select  dfet.dfet_norden as orderid , dfet.dfet_idexamen as examid, dfet.dfet_idtest as testid , dfet.dfet_idtest as id , ct.ct_abreviado as test ,  "
      + "cbet.cbet_descripcion as microbiologyStatus ,nvl(dfet.dfet_idbacestadotest , '1')  as idMicrobiologyStatus,  "
      + "ct.ct_escultivo as isCulture , "
      + "ctr.ctr_descripcion as resultType , dfm.dfm_codigobarra as labelCode , cert.cert_descripcionestadotest as status,dfet.dfet_idestadoresultadotest idstatus,  "
      + "cbgt.cbgt_descripcion as cultivo , dfe.dfe_codigoestadoexamen as estado ,dfet.DFET_RESULTADO as result ,ctr.ctr_CODIGO AS ctrcodigo, "
      + "ct.ct_RESULTADOOBLIGADO AS resultadoobligado,ct.ct_RESULTADOOMISION AS resultadomision ,ct.CT_DECIMALES AS decimales,dfet.dfet_RESULTADONUMERICO AS resultadonumerico "
      + "from datos_fichasexamenestest dfet  " + "left join datos_fichasexamenes dfe  "
      + "on dfe.dfe_norden = dfet.dfet_norden AND dfe.DFE_IDEXAMEN =dfet.DFET_IDEXAMEN  " + "left join cfg_test ct  "
      + "on ct.ct_idtest = dfet.dfet_idtest  " + "left join cfg_bacestadostest cbet   "
      + "on cbet.cbet_idbacestadotest = nvl(dfet.dfet_idbacestadotest , '1')  " + "left join cfg_tiposderesultado ctr  "
      + "on ctr.ctr_idtiporesultado = ct.ct_idtiporesultado  " + "left join datos_fichasmuestras dfm   "
      + "on dfm.dfm_norden = dfet.dfet_norden and dfm.dfm_idmuestra = dfet.dfet_idmuestra  "
      + "left join cfg_estadosresultadostest cert   "
      + "on cert.cert_idestadoresultadotest = dfet.dfet_idestadoresultadotest  " + "left join cfg_bacgrupostest cbgt  "
      + "on cbgt.cbgt_idbacgrupotest = nvl(ct.ct_idbacgrupotest , '4' )  " + "left join cfg_test ct  "
      + "on ct.ct_idtest = dfet.DFET_IDTEST  " + "INNER JOIN CFG_TIPOSDERESULTADO ctr  "
      + "ON (ctr.CTR_IDTIPORESULTADO = ct.CT_IDTIPORESULTADO)    " + "where dfet.DFET_NORDEN = :norden ";
  private static final String sqlGetTestxExamenMicrobiologia = "select  dfet.dfet_norden as orderid , dfet.dfet_idexamen as examid, dfet.dfet_idtest as testid , dfet.dfet_idtest as id , ct.ct_abreviado as test ,  "
      + "cbet.cbet_descripcion as microbiologyStatus ,nvl(dfet.dfet_idbacestadotest , '1')  as idMicrobiologyStatus,  "
      + "ct.ct_escultivo as isCulture , "
      + "ctr.ctr_descripcion as resultType , dfm.dfm_codigobarra as labelCode , cert.cert_descripcionestadotest as status,dfet.dfet_idestadoresultadotest idstatus,  "
      + "cbgt.cbgt_descripcion as cultivo , dfe.dfe_codigoestadoexamen as estado ,dfet.DFET_RESULTADO as result ,ctr.ctr_CODIGO AS ctrcodigo, "
      + "ct.ct_RESULTADOOBLIGADO AS resultadoobligado,ct.ct_RESULTADOOMISION AS resultadomision ,ct.CT_DECIMALES AS decimales,dfet.dfet_RESULTADONUMERICO AS resultadonumerico, "
      // agregado por marco 24/04/23}
      + "dfet.dfet_rcritico as resultadocritico "
      //
      + "from datos_fichasexamenestest dfet  " + "left join datos_fichasexamenes dfe  "
      + "on dfe.dfe_norden = dfet.dfet_norden AND dfe.DFE_IDEXAMEN =dfet.DFET_IDEXAMEN  " + "left join cfg_test ct  "
      + "on ct.ct_idtest = dfet.dfet_idtest  " + "left join cfg_bacestadostest cbet   "
      + "on cbet.cbet_idbacestadotest = nvl(dfet.dfet_idbacestadotest , '1')  " + "left join cfg_tiposderesultado ctr  "
      + "on ctr.ctr_idtiporesultado = ct.ct_idtiporesultado  " + "left join datos_fichasmuestras dfm   "
      + "on dfm.dfm_norden = dfet.dfet_norden and dfm.dfm_idmuestra = dfet.dfet_idmuestra  "
      + "left join cfg_estadosresultadostest cert   "
      + "on cert.cert_idestadoresultadotest = dfet.dfet_idestadoresultadotest  " + "left join cfg_bacgrupostest cbgt  "
      + "on cbgt.cbgt_idbacgrupotest = nvl(ct.ct_idbacgrupotest , '4' )  "
      + "where dfet.DFET_NORDEN = :norden AND dfet.dfet_idexamen in :idExamen";
  private static final String getActionMicrobiologia = " select dfet.dfet_norden as orderid , dfet.dfet_idtest as id, dfet.dfet_idexamen as examid, dfet.dfet_idtest as testid,cbzc.CBZC_DESCRIPCION bodypart,cbzc.CBZC_IDZONACUERPO bodypartid, "
      + "		TO_CHAR(dfe.DFE_BACFECHAINICIO, 'dd-mm-yyyy') seedingdate,TO_CHAR(dfe.DFE_BACFECHAINICIO, 'hh:MM:ss') seedingtime,TO_CHAR(dfe.DFE_BACFECHAINICIO2, 'dd-mm-yyyy') reseedingdate,TO_CHAR(dfe.DFE_BACFECHAINICIO2, 'hh:MM:ss') reseedingtime,cmue.CMUE_DESCRIPCION as sampletype "
      + "		from datos_fichasexamenestest dfet   " + "		inner join datos_fichasexamenes dfe  "
      + "		on dfe.dfe_norden = dfet.dfet_norden  AND dfe.DFE_IDEXAMEN =dfet.DFET_IDEXAMEN  "
      + "		inner join cfg_test ct   " + "		on ct.ct_idtest = dfet.dfet_idtest   "
      + "		INNER JOIN DATOS_FICHASMUESTRAS dfm  "
      + "		ON dfet.DFET_IDMUESTRA =dfm.DFM_IDMUESTRA AND dfet.DFET_NORDEN  = dfm.DFM_NORDEN   "
      + "		LEFT JOIN CFG_BACZONACUERPO cbzc   " + "		ON dfm.DFM_IDZONACUERPO = cbzc.CBZC_IDZONACUERPO  "
      + "   	LEFT JOIN CFG_MUESTRAS cmue " + "   	ON dfet.DFET_IDTIPOMUESTRA = cmue.CMUE_IDTIPOMUESTRA "
      + "		WHERE dfet.DFET_NORDEN =  :norden AND dfet.DFET_IDEXAMEN = :idexam AND dfet.DFET_IDTEST = :idtest ";
  private static final String getExamenesMicrobiologia = "SELECT  dfe.DFE_NORDEN orderid ,dfe.DFE_IDEXAMEN id, ce.CE_ABREVIADO exam,dfe.DFE_CODIGOESTADOEXAMEN estado,dfe.DFE_EXAMENESURGENTE urgency, "
      + " to_char(dfe.DFE_BACFECHAINICIO, 'dd/MM/yyyy HH24:mi:ss') as seedingdate , to_char(dfe.DFE_BACFECHAINICIO2, 'dd/MM/yyyy HH24:mi:ss') as reseedingdate,ce.CE_ESCULTIVO SEENDINGCHK "
      + ",cbzc.CBZC_DESCRIPCION bodyPart,cbzc.CBZC_IDZONACUERPO bodyPartid,cbgt.CBGT_DESCRIPCION, "
      + "cmue.CMUE_DESCRIPCION " + "FROM DATOS_FICHASEXAMENESTEST dfet	 " + "INNER JOIN DATOS_FICHASEXAMENES dfe "
      + "ON dfe.DFE_NORDEN = dfet.DFET_NORDEN AND  dfe.DFE_IDEXAMEN  = dfet.DFET_IDEXAMEN  "
      + "INNER JOIN CFG_EXAMENES ce " + "ON dfet.DFET_IDEXAMEN  = ce.CE_IDEXAMEN  "
      + "INNER JOIN DATOS_FICHASMUESTRAS dfm "
      + "ON dfet.DFET_IDMUESTRA = dfm.DFM_IDMUESTRA AND dfet.DFET_NORDEN = dfm.DFM_NORDEN "
      + "LEFT JOIN CFG_MUESTRAS cmue " + "ON dfet.DFET_IDTIPOMUESTRA = cmue.CMUE_IDTIPOMUESTRA  "
      + "LEFT JOIN CFG_BACZONACUERPO cbzc " + "ON dfm.DFM_IDZONACUERPO = cbzc.CBZC_IDZONACUERPO  "
      + "LEFT JOIN CFG_BACGRUPOSTEST  cbgt " + "ON  dfe.DFE_IDBACESTADO = cbgt.CBGT_IDBACGRUPOTEST "
      + "	WHERE dfet.DFET_NORDEN =  :norden AND dfet.DFET_IDEXAMEN = :idexam AND dfet.DFET_IDTEST = :idtest";
  private static final String getListStatus = "SELECT CERT_IDESTADORESULTADOTEST,CERT_DESCRIPCIONESTADOTEST FROM cfg_estadosresultadostest WHERE CERT_ACTIVO = 'S'";
  private static final String getListMicrobiology = "SELECT CBET_IDBACESTADOTEST,CBET_DESCRIPCION FROM cfg_bacestadostest";
  private static final String getBodyPartId = "SELECT CBZC_IDZONACUERPO FROM CFG_BACZONACUERPO WHERE CBZC_DESCRIPCION = :bodypart";
  private static final String getIdTipoMuestra = "SELECT CMUE_IDTIPOMUESTRA FROM CFG_MUESTRAS WHERE CMUE_DESCRIPCION = :descripMuestra";
  private static final String getEstadoExamen = "SELECT CEE_CODIGOESTADOEXAMEN FROM CFG_ESTADOSEXAMENES WHERE CEE_DESCRIPCIONESTADOEXAMEN = :estado";
  private static final String getExamenesConEstado = "SELECT  dfe.DFE_NORDEN orderid ,dfe.DFE_IDEXAMEN id, ce.CE_ABREVIADO exam,dfe.DFE_EXAMENESURGENTE urgency, "
      + "   		to_char(dfe.DFE_BACFECHAINICIO, 'dd/MM/yyyy HH24:mi:ss') as seedingdate , to_char(dfe.DFE_BACFECHAINICIO2, 'dd/MM/yyyy HH24:mi:ss') as reseedingdate,ce.CE_ESCULTIVO AS cultivo "
      + "   		,cbzc.CBZC_IDZONACUERPO bodyPartid,cbgt.CBGT_DESCRIPCION AS bodypart, "
      + "   		cmue.CMUE_DESCRIPCION AS sample, cee.CEE_DESCRIPCIONESTADOEXAMEN AS processstatus,CERT_ESTADORESULTADOTEST AS estado"
      + "   		FROM DATOS_FICHASEXAMENESTEST dfet	 " + "   		INNER JOIN DATOS_FICHASEXAMENES dfe "
      + "   		ON dfe.DFE_NORDEN = dfet.DFET_NORDEN AND  dfe.DFE_IDEXAMEN  = dfet.DFET_IDEXAMEN  "
      + "   		INNER JOIN CFG_EXAMENES ce " + "   		ON dfet.DFET_IDEXAMEN  = ce.CE_IDEXAMEN  "
      + "   		INNER JOIN DATOS_FICHASMUESTRAS dfm "
      + "   		ON dfet.DFET_IDMUESTRA = dfm.DFM_IDMUESTRA AND dfet.DFET_NORDEN = dfm.DFM_NORDEN "
      + "   		LEFT JOIN CFG_MUESTRAS cmue " + "   		ON dfet.DFET_IDTIPOMUESTRA = cmue.CMUE_IDTIPOMUESTRA  "
      + "   		LEFT JOIN CFG_BACZONACUERPO cbzc " + "   		ON dfm.DFM_IDZONACUERPO = cbzc.CBZC_IDZONACUERPO  "
      + "   		LEFT JOIN CFG_BACGRUPOSTEST  cbgt " + "   		ON  dfe.DFE_IDBACESTADO = cbgt.CBGT_IDBACGRUPOTEST "
      + "   		LEFT JOIN CFG_ESTADOSEXAMENES cee "
      + "   		ON cee.CEE_CODIGOESTADOEXAMEN  = dfe.DFE_CODIGOESTADOEXAMEN"
      + "   		LEFT JOIN  CFG_ESTADOSRESULTADOSTEST cert"
      + "   		ON  cert.CERT_IDESTADORESULTADOTEST = dfet.DFET_IDESTADORESULTADOTEST AND cert.CERT_ACTIVO = 'S'"
      + "   		WHERE dfet.DFET_NORDEN =  :norden AND dfet.DFET_IDEXAMEN = :idExamen";
  // getIdTipoMuestra descripMuestra

  @SuppressWarnings("unchecked")
  public List<MicrobiologiaOrdenPacienteDTO> getListOrder(Long startId, Long endId, Date startDate, Date endDate,
      Long atentionType, Long exam, Long section, String names, String surname, Long documentType, Long documentId,
      Long serviceId) throws BiosLISDAOException {

    String sql = sqlGetMicrobiologiaOrden;
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<MicrobiologiaOrdenPacienteDTO> mopDTO = new ArrayList<MicrobiologiaOrdenPacienteDTO>();

    try {

      Query query = sesion.createSQLQuery(sql)
          .setResultTransformer(Transformers.aliasToBean(MicrobiologiaOrdenPacienteDTO.class));
      query.setParameter("startId", startId);
      if (endId != null) {
        query.setParameter("endId", endId);
      }
      if (startDate != null) {
        query.setDate("startDate", startDate);
      }
      if (endDate != null) {
        query.setDate("endDate", endDate);
      }

      // query.setParameter("endId", endId);
      // query.setDate("startDate", startDate);
      // query.setDate("endDate",endDate);
      mopDTO = query.list();

    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      he.printStackTrace();
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return mopDTO;
  }

  @SuppressWarnings({ "null", "unchecked" })
  public List<MicrobiologiaTestDTO> getTest(long IdOrder) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<MicrobiologiaTestDTO> listtestDTO = new ArrayList<MicrobiologiaTestDTO>();

    try {
      Query query = sesion.createSQLQuery(sqlGetTestMicrobiologia)
          .setResultTransformer(Transformers.aliasToBean(MicrobiologiaTestDTO.class));
      query.setParameter("norden", IdOrder);

      listtestDTO = query.list();
      sesion.close();
      for (MicrobiologiaTestDTO mt : listtestDTO) {
        if (mt.getISCULTURE().equals("S") && mt.getCULTIVO().equals("OTROS")) {
          mt.setCULTIVO("CULTIVOS");
        }

      }

    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      he.printStackTrace();
      // throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return listtestDTO;
  }

  @SuppressWarnings({ "null", "unchecked" })
  public List<MicrobiologiaTestDTO> getTestxExamen(long IdOrder, ParamResultadoExamenOrden prmResultadoExamenOrden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<MicrobiologiaTestDTO> listtestDTO = null;

    if (IdOrder != -1) {
      try {
        List<Long> paramLst = null;
        if (prmResultadoExamenOrden.getExamenes().isEmpty()) {
          /*
           * Query qry = sesion.createQuery(
           * "select dfe.IddatosFichasExamenes.dfeIdexamen from DatosFichasexamenes dfe where dfe.IddatosFichasExamenes.dfeNorden = :nroOrden"
           * );
           */
          // Query modificada por Marco Caracciolo, para que solo contemple los exámenes
          // de microbiología
          Query qry = sesion.createSQLQuery("SELECT dfe.DFE_IDEXAMEN" + " FROM BIOSLIS.DATOS_FICHASEXAMENES dfe"
              + " JOIN BIOSLIS.CFG_EXAMENES ce ON ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN"
              + " WHERE dfe.DFE_NORDEN = :nroOrden AND ce.CE_IDTIPOEXAMEN = :idTipoExamen");
          qry.setLong("nroOrden", IdOrder);
          qry.setInteger("idTipoExamen", Constante.TIPOEXAMEN_MICROBIOLOGIA);
          paramLst = (List<Long>) qry.list();
        } else {
          paramLst = new ArrayList<Long>(prmResultadoExamenOrden.getExamenes().size());

          for (ParamResultadoExamenDTO fila : prmResultadoExamenOrden.getExamenes()) {
            paramLst.add(fila.getDFE_IDEXAMEN().longValue());
            logger.debug("ID examen {}", fila.getDFE_IDEXAMEN().longValue());
          }
        }

        Query query = sesion.createSQLQuery(sqlGetTestxExamenMicrobiologia)
            .setResultTransformer(Transformers.aliasToBean(MicrobiologiaTestDTO.class));
        query.setParameter("norden", IdOrder);
        query.setParameterList("idExamen", paramLst);

        listtestDTO = query.list();
        sesion.close();
        for (MicrobiologiaTestDTO mt : listtestDTO) {
          if (mt.getISCULTURE().equals("S") && mt.getCULTIVO().equals("OTROS")) {
            mt.setCULTIVO("CULTIVOS");
          }

        }

      } catch (HibernateException he) {
        logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
        he.printStackTrace();
        // throw new BiosLISDAOException(he.getLocalizedMessage());
      } finally {
        if (sesion.isOpen()) {
          sesion.close();
        }
      }
    }

    return listtestDTO;
  }

  @SuppressWarnings({ "null", "unchecked" })
  public List<MicrobiologiaActionsDTO> getAction(long IdOrder, long IdExamen, long IdTest) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    List<MicrobiologiaActionsDTO> actionDTO = new ArrayList<MicrobiologiaActionsDTO>();
    try {
      Query query = sesion.createSQLQuery(getActionMicrobiologia)
          .setResultTransformer(Transformers.aliasToBean(MicrobiologiaActionsDTO.class));
      query.setParameter("norden", IdOrder);
      query.setParameter("idexam", IdExamen);
      query.setParameter("idtest", IdTest);

      actionDTO = query.list();
      sesion.close();
    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      he.printStackTrace();
      // throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return (List<MicrobiologiaActionsDTO>) actionDTO;
  }
  // GET LIST STATUS

  @SuppressWarnings("unchecked")
  public HashMap<String, Object> getListStatus() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, Object> result = new HashMap<String, Object>();

    try {
      Query query = sesion.createSQLQuery(getListStatus);
      List<Object[]> lista = query.list();
      sesion.close();
      for (Object[] objects : lista) {
        result.put(objects[0].toString(), objects[1]);
      }

    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      he.printStackTrace();
      // throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return result;
  }

  // GET LIST MICROBIOLOGY
  @SuppressWarnings("unchecked")
  public HashMap<String, Object> getListMICROBIOLOGY() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, Object> result = new HashMap<String, Object>();

    try {
      Query query = sesion.createSQLQuery(getListMicrobiology);
      List<Object[]> lista = query.list();
      sesion.close();
      for (Object[] objects : lista) {
        result.put(objects[0].toString(), objects[1]);
      }

    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      he.printStackTrace();
      // throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return result;
  }
  // GET EXAMENES CON ORDEN Y EXAMEN

  public MicrobiologiaExamDTO getExamenPorOrdenIds(long IdOrder, long IdExamen) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    MicrobiologiaExamDTO MED = new MicrobiologiaExamDTO();
    try {
      Query querySelectExamen = sesion.createSQLQuery(getExamenesConEstado)
          .setResultTransformer(Transformers.aliasToBean(MicrobiologiaExamDTO.class));
      querySelectExamen.setParameter("norden", IdOrder);
      querySelectExamen.setParameter("idExamen", IdExamen);

      MED = (MicrobiologiaExamDTO) querySelectExamen.list().get(0);

    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      he.printStackTrace();
      // throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return MED;
  }

  public MicrobiologiaTestDTO updateTest(MicrobiologiaTestDTO MTDTO) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      // DatosFichas df = (DatosFichas) sesion.get(DatosFichas.class,
      // MTDTO.getORDERID());

      DatosFichasexamenestestId dfid = new DatosFichasexamenestestId(MTDTO.getORDERID().longValue(),
          MTDTO.getEXAMID().longValue(), MTDTO.getTESTID().longValue());
      DatosFichasexamenestest dfe = (DatosFichasexamenestest) sesion.get(DatosFichasexamenestest.class, dfid);
      // PARA QUE JOSE AVANCE CAMBIO EN PROCESO
      switch (MTDTO.getSTATUS()) {
      case "PENDIENTE": {
        dfe.setDfetIdestadoresultadotest((short) 1);
        break;
      }
      case "DIGITADO": {

        dfe.setDfetIdestadoresultadotest((short) 2);
        break;
      }
      case "TRANSMITIDO": {

        dfe.setDfetIdestadoresultadotest((short) 3);
        break;
      }
      case "BLOQUEADO": {

        dfe.setDfetIdestadoresultadotest((short) 4);
        break;
      }
      case "FIRMADO": {

        dfe.setDfetIdestadoresultadotest((short) 5);
        break;
      }
      case "INGRESADO": {

        dfe.setDfetIdestadoresultadotest((short) 7);
        break;
      }

      }
      // PARA QUE JOSE AVANCE CAMBIO EN PROCESO
      switch (MTDTO.getMICROBIOLOGYSTATUS()) {
      case "PENDIENTE": {
        dfe.setDfetidbacestadotest(BigDecimal.valueOf(1));
        break;
      }
      case "POSITIVO": {
        dfe.setDfetidbacestadotest(BigDecimal.valueOf(2));
        break;
      }
      case "NEGATIVO": {
        dfe.setDfetidbacestadotest(BigDecimal.valueOf(3));
        break;
      }
      case "EN PROCESO": {
        dfe.setDfetidbacestadotest(BigDecimal.valueOf(4));
        break;
      }
      }

      // dfe.setDfetIdestadoresultadotest(MTDTO.getIDSTATUS().shortValueExact());
      dfe.setDfetResultado(MTDTO.getRESULT());
      // dfe.setDfetidbacestadotest(MTDTO.getIDMICROBIOLOGYSTATUS());

      sesion.beginTransaction();
      sesion.save(dfe);
      sesion.getTransaction().commit();
      sesion.close();

    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      he.printStackTrace();

      // throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return MTDTO;
  }

  public MicrobiologiaActionsDTO updateTest(MicrobiologiaActionsDTO MTADTO) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      he.printStackTrace();
      // throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return MTADTO;
  }

  public HashMap<String, Object> updateTest2(MicrobiologiaActionsDTO MTDTO) throws BiosLISDAOException, ParseException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, Object> result = new HashMap<String, Object>();

    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      // DatosFichas df = (DatosFichas) sesion.get(DatosFichas.class,
      // MTDTO.getORDERID());

      DatosFichasexamenestestId dfid = new DatosFichasexamenestestId(MTDTO.getORDERID().longValue(),
          MTDTO.getEXAMID().longValue(), MTDTO.getTESTID().longValue());
      DatosFichasexamenestest dfet = (DatosFichasexamenestest) sesion.get(DatosFichasexamenestest.class, dfid);
      DatosFichasmuestras dfm = new DatosFichasmuestras();
      DatosFichasexamenesId dfeId = new DatosFichasexamenesId(MTDTO.getORDERID().longValue(),
          MTDTO.getEXAMID().longValue());
      DatosFichasexamenes dfe = (DatosFichasexamenes) sesion.get(DatosFichasexamenes.class, dfeId);

      try {
        dfm = (DatosFichasmuestras) sesion.get(DatosFichasmuestras.class, Long.valueOf(dfet.getDfetIdmuestra()));

      } catch (Exception e) {
        e.printStackTrace();
        // TODO: handle exception
      }
      if (MTDTO.getBODYPART() == null || MTDTO.getBODYPART() == "") {
      } else {
        try {
          Query getbodypartid = sesion.createSQLQuery(getBodyPartId);
          getbodypartid.setParameter("bodypart", MTDTO.getBODYPART());
          BigDecimal idBodyType = (BigDecimal) getbodypartid.list().get(0);
          CfgBaczonacuerpo cbzn = new CfgBaczonacuerpo((idBodyType).shortValueExact());
          dfm.setCfgBaczonacuerpo(cbzn);
        } catch (Exception e) {
          e.printStackTrace();
          // TODO: handle exception
        }
      }
      try {

        Query getsampletype = sesion.createSQLQuery(getIdTipoMuestra);
        getsampletype.setParameter("descripMuestra", MTDTO.getSAMPLETYPE());
        BigDecimal idSample = (BigDecimal) getsampletype.list().get(0);
        dfet.setDfetIdtipomuestra(idSample.shortValueExact());

      } catch (Exception e) {
        e.printStackTrace();
        // TODO: handle exception
      }
      //
      try {
        if (MTDTO.getSEEDINGDATE() == null || MTDTO.getSEEDINGDATE() == "") {
          dfe.setDfeBacfechainicio(null);
        } else {
          dfe.setDfeBacfechainicio(
              new SimpleDateFormat("dd-MM-yyyy hh:mm").parse(MTDTO.getSEEDINGDATE() + " " + MTDTO.getSEEDINGTIME()));
        }

      } catch (Exception e) {
        e.printStackTrace();
        // TODO: handle exception
      }
      try {
        if (MTDTO.getRESEEDINGDATE() == null || MTDTO.getRESEEDINGDATE() == "") {
          dfe.setDfeBacfechainicio2(null);
        } else {
          dfe.setDfeBacfechainicio2(new SimpleDateFormat("dd-MM-yyyy hh:mm")
              .parse(MTDTO.getRESEEDINGDATE() + " " + MTDTO.getRESEEDINGTIME()));
        }

      } catch (Exception e) {
        e.printStackTrace();
        // TODO: handle exception
      }

      try {
        sesion.beginTransaction();
        sesion.update(dfe);
        sesion.update(dfm);
        sesion.getTransaction().commit();

      } catch (HibernateException he) {
        logger.error("error en updateTest2 " + he.getMessage());
      }
      /*
       * Object[] objectoQuery = (Object[]) new Object(); try { Query querySelect =
       * sesion.createSQLQuery(getExamenesMicrobiologia);
       * querySelect.setParameter("norden", MTDTO.getORDERID());
       * querySelect.setParameter("idexam", MTDTO.getEXAMID());
       * querySelect.setParameter("idtest", MTDTO.getTESTID()); //result =
       * (HashMap<String, Object>) querySelect.list().get(0); objectoQuery =
       * (Object[])querySelect.list().get(0);
       * 
       * result.put("orderid", objectoQuery[0].toString()); result.put("id",
       * objectoQuery[1].toString()); result.put("exam", objectoQuery[2].toString());
       * result.put("estado", objectoQuery[3].toString()); result.put("urgency",
       * objectoQuery[4].toString());
       * 
       * } catch (HibernateException he) {
       * logger.error("error en updateTest2 "+he.getMessage()); }
       * 
       * try { result.put("seedingdate",objectoQuery[5].toString()); } catch
       * (Exception e) { result.put("seedingdate",""); } try {
       * result.put("reseedingdate",objectoQuery[6].toString()); } catch (Exception e)
       * { result.put("reseedingdate", ""); } result.put("cultivo",
       * objectoQuery[7].toString()); try { result.put("bodypart",
       * objectoQuery[8].toString()); MTDTO.setBODYPART(objectoQuery[8].toString()); }
       * catch (Exception e) { result.put("bodypart",""); } try {
       * result.put("bodypartid", objectoQuery[9].toString()); } catch (Exception e) {
       * result.put("bodypartid",""); } try { result.put("sample",
       * objectoQuery[11].toString());
       * MTDTO.setSAMPLETYPE(objectoQuery[11].toString()); } catch (Exception e) {
       * result.put("sample", ""); }
       * 
       * result.put("test", MTDTO);
       */
      // dfm.setCfgBaczonacuerpo(MTDTO.getBODYPART());
      sesion.close();

    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      he.printStackTrace();

      // throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return result;
  }

  public MicrobiologiaExamDTO updateExamMicrobiologia(MicrobiologiaExamDTO MED) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      DatosFichasexamenesId dfeId = new DatosFichasexamenesId(MED.getORDERID().longValue(), MED.getID().longValue());
      DatosFichasexamenes dfe = (DatosFichasexamenes) sesion.get(DatosFichasexamenes.class, dfeId);
      try {
        Query getEstadoE = sesion.createSQLQuery(getEstadoExamen);
        getEstadoE.setParameter("estado", MED.getPROCESSSTATUS());
        String ee = (String) getEstadoE.list().get(0).toString();
        dfe.setDfeExamenesurgente(MED.getURGENCY());
        dfe.setDfeCodigoestadoexamen(ee);
      } catch (Exception e) {
        e.printStackTrace();
      }
      sesion.beginTransaction();
      sesion.update(dfe);
      sesion.getTransaction().commit();

      Query querySelectExamen = sesion.createSQLQuery(getExamenesConEstado)
          .setResultTransformer(Transformers.aliasToBean(MicrobiologiaExamDTO.class));
      querySelectExamen.setParameter("norden", MED.getORDERID());
      querySelectExamen.setParameter("idExamen", MED.getID());

      MED = (MicrobiologiaExamDTO) querySelectExamen.list().get(0);

    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      he.printStackTrace();
      // throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return MED;
  }

  // creado por cristian 19-12
  public AntibiogramaMicrobiologiaDTO getAntibiogramList(Long nOrder, Long idExamen, Long idTest)
      throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    AntibiogramaMicrobiologiaDTO getAntibiograma = new AntibiogramaMicrobiologiaDTO();

    Query query = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      // consulta datos por orden - examen - test
      String q1 = "select dfeta.dfeta_norden as orderid , dfeta.dfeta_idexamen as examnId, dfeta.dfeta_idtest as testid , "
          + "dfeta.dfeta_idtest as id ,  ct.ct_abreviado as test , cbti.cbti_descripcion as infectiontype "
          + ", cbrc.cbrc_descripcion  as colonycount " + "from datos_fichasexamenestestatb dfeta "
          + "inner join datos_fichasexamenestestmo dfetmo "
          + "on dfetmo.dfetmo_norden = dfeta.dfeta_norden and dfetmo.dfetmo_idexamen = dfeta.dfeta_idexamen and     dfetmo.dfetmo_idtest   = dfeta.dfeta_idtest "
          + "left join  cfg_bactiposinfecciones cbti "
          + "on cbti.cbti_idbactipoinfeccion = dfetmo.dfetmo_idbactipoinfeccion " + "left join cfg_test ct  "
          + "on ct.ct_idtest = dfeta.dfeta_idtest " + "left join cfg_bacrecuentocolonias cbrc "
          + "on cbrc.cbrc_idbacrecuentocolonia = dfetmo.dfetmo_idbacrecuentocolonias "
          + "where dfeta.dfeta_norden = :nOrder and dfeta.dfeta_idexamen = :idExamen and dfeta.dfeta_idtest = :idTest  "
          + "FETCH FIRST 1 ROWS ONLY ";

      query = sesion.createSQLQuery(q1)
          .setResultTransformer(Transformers.aliasToBean(AntibiogramaMicrobiologiaDTO.class));
      query.setParameter("nOrder", nOrder);
      query.setParameter("idExamen", idExamen);
      query.setParameter("idTest", idTest);

      getAntibiograma = (AntibiogramaMicrobiologiaDTO) query.uniqueResult();

      // consulta para buscar lista de microorganismos
      q1 = " select dfetmo.dfetmo_idbacmicroorganismo " + "from datos_fichasexamenestestmo  dfetmo "
          + "where dfetmo.dfetmo_norden = :nOrder and dfetmo.dfetmo_idexamen = :idExamen and dfetmo.dfetmo_idtest = :idTest ";

      query = sesion.createSQLQuery(q1);

      query.setParameter("nOrder", nOrder);
      query.setParameter("idExamen", idExamen);
      query.setParameter("idTest", idTest);

      List<BigDecimal> listaAntibioticos = query.list();

      // RECORRIDO DE CADA MICRORGANISMO
      int indice = 1;
      for (BigDecimal lista : listaAntibioticos) {
        AntibioticoMicrobiologiaDTO antibiotic = new AntibioticoMicrobiologiaDTO();

        // CONSULTA PARA NOMBRE E ID DE MICRORGANISMO
        q1 = " select cbmo.cbmo_descripcion as name, cbmo.cbmo_codigo as id "
            + "from datos_fichasexamenestestmo  dfetmo " + "inner join cfg_bacmicroorganismos cbmo "
            + "on cbmo.cbmo_idbacmicroorganismo = dfetmo.dfetmo_idbacmicroorganismo "
            + "where dfetmo.dfetmo_norden = :nOrder and dfetmo.dfetmo_idexamen = :idExamen and dfetmo.dfetmo_idtest = :idTest and "
            + " dfetmo.dfetmo_idbacmicroorganismo =:idMicroorganismo";

        query = sesion.createSQLQuery(q1)
            .setResultTransformer(Transformers.aliasToBean(MicroorganismoMicrobiologiaDTO.class));
        ;

        query.setParameter("nOrder", nOrder);
        query.setParameter("idExamen", idExamen);
        query.setParameter("idTest", idTest);
        query.setParameter("idMicroorganismo", lista);

        antibiotic.setMicroorganism((MicroorganismoMicrobiologiaDTO) query.uniqueResult());

        // CONSULTA NOMBRE E ID DE ANTIBIOGRAMA
        q1 = " select cba.cba_descripcion as name , cba.cba_codigo as id " + "from datos_fichasexamenestestmo  dfetmo "
            + "inner join cfg_bacantibiogramas cba " + "on cba.cba_idbacantibiograma = dfetmo.dfetmo_idbacantibiograma "
            + "where dfetmo.dfetmo_norden = :nOrder and dfetmo.dfetmo_idexamen = :idExamen and dfetmo.dfetmo_idtest = :idTest and "
            + " dfetmo.dfetmo_idbacmicroorganismo =:idMicroorganismo";

        query = sesion.createSQLQuery(q1)
            .setResultTransformer(Transformers.aliasToBean(DatosAntibiogramMicrobiologiaDTO.class));
        ;

        query.setParameter("nOrder", nOrder);
        query.setParameter("idExamen", idExamen);
        query.setParameter("idTest", idTest);
        query.setParameter("idMicroorganismo", lista);

        antibiotic.setAntibiogram((DatosAntibiogramMicrobiologiaDTO) query.uniqueResult());

        // CONSULTA LISTA DE METODOS QUE TRAE -->RESULT Y METHOD
        q1 = " select cbmr.cbmr_descripcion as method, ldvbrmr.LDVBRMR_DESCRIPCIÓN as result "
            + "from datos_fichasexamenestestmo  dfetmo " + "left join datos_bactestmomarcadoresr dbtmmr "
            + "on dbtmmr.dbtmmr_idtestmomarcadorr = dfetmo.dfetmo_idtestmomarcadorr "
            + "left join cfg_bacmarcadoresresistencia cbmr "
            + "on cbmr.cbmr_idmarcadorresistencia = dbtmmr.dbtmmr_idmarcadorresistencia "
            + "left join ldv_bacresultadosmarcadorr ldvbrmr "
            + "on ldvbrmr.ldvbrmr_idresultadomarcadorr = dbtmmr.dbtmmr_idresultadomarcadorr "
            + "where dfetmo.dfetmo_norden = :nOrder and dfetmo.dfetmo_idexamen = :idExamen and dfetmo.dfetmo_idtest = :idTest and "
            + " dfetmo.dfetmo_idbacmicroorganismo =:idMicroorganismo";

        query = sesion.createSQLQuery(q1).setResultTransformer(Transformers.aliasToBean(MethodsMicrobiologiaDTO.class));
        ;

        query.setParameter("nOrder", nOrder);
        query.setParameter("idExamen", idExamen);
        query.setParameter("idTest", idTest);
        query.setParameter("idMicroorganismo", lista);

        antibiotic.setMethods(query.list());

        // CONULTA QUE TRAE LOS DATOS DE LOS MICROORGANISMOS QUE YA ESTAN INFORMADOS
        q1 = " select dfeta.dfeta_idantibiotico as id , cba.cba_codigo as code , cba.cba_descripcion as name, cba.cba_activo as active, ldvac.ldvac_descripcion as clase , "
            + "cbad.cbad_opcional opcional, dfeta.dfeta_incluireninforme incluir , dfeta.dfeta_cim as cim , dfeta.dfeta_kirbybauer as diameter , dfeta.dfeta_etest as etest , "
            + "dfeta.dfeta_interpretacion as interpretation , dfeta.dfeta_incluireninforme as includeinreport "
            + "from  datos_fichasexamenestestatb dfeta " + "left join cfg_bacantibioticos cba "
            + "on cba.cba_idbacantibiotico = dfeta.dfeta_idantibiotico " + "left join ldv_bacantibioticosclases ldvac "
            + "on ldvac.ldvac_idantibioticoclase = cba.cba_idantibioticoclase " + "left join cfg_bacantibioticos cba "
            + "on cba.cba_idbacantibiotico = dfeta.dfeta_idantibiotico "
            + "left join cfg_bacantibiogramasantibiot cbad "
            + "on cbad.cbad_IDBACANTIBIOGRAMA = dfeta.dfeta_idantibiograma and  cbad.cbad_idbacantibiotico = dfeta.dfeta_idantibiotico "
            + "where dfeta.dfeta_norden = :nOrder and dfeta.dfeta_idexamen = :idExamen and dfeta.dfeta_idtest = :idTest and "
            + " dfeta.dfeta_idmicroorganismo =:idMicroorganismo ";

        query = sesion.createSQLQuery(q1)
            .setResultTransformer(Transformers.aliasToBean(DatosAntibioticosMicrobiologiaDTO.class));
        ;

        query.setParameter("nOrder", nOrder);
        query.setParameter("idExamen", idExamen);
        query.setParameter("idTest", idTest);
        query.setParameter("idMicroorganismo", lista);

        antibiotic.setAntibiotics(query.list());

        if (getAntibiograma != null) {
          getAntibiograma.setAntibioticLists(antibiotic, indice);
        }

        indice++;

      }
      if (getAntibiograma != null) {
        for (int i = indice; i <= 4; i++) {
          AntibioticoMicrobiologiaDTO antibiotic = new AntibioticoMicrobiologiaDTO();
          getAntibiograma.setAntibioticLists(antibiotic, i);
        }
      } else {
        getAntibiograma = new AntibiogramaMicrobiologiaDTO();
        getAntibiograma.setORDERID(new BigDecimal(nOrder));
        getAntibiograma.setEXAMNID(new BigDecimal(idExamen));
        getAntibiograma.setTESTID(new BigDecimal(idTest));
        getAntibiograma.setID(new BigDecimal(idTest));
        q1 = "select cfg_test.ct_abreviado from cfg_test " + "where cfg_test.ct_idtest = :idTest";

        query = sesion.createSQLQuery(q1);
        query.setParameter("idTest", idTest);

        String nombreTest = (String) query.uniqueResult();
        getAntibiograma.setTEST(nombreTest);

        for (int i = 1; i <= 4; i++) {
          AntibioticoMicrobiologiaDTO antibiotic = new AntibioticoMicrobiologiaDTO();
          getAntibiograma.setAntibioticLists(antibiotic, i);
        }
      }
      sesion.close();

    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return getAntibiograma;
  }

  public List<Object> getListAntibiogramaAntibiotico(String antibiogramName) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    List<Object> listaResult = new ArrayList<Object>();
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();

      Query query = sesion.createSQLQuery(
          "SELECT CFG_BACANTIBIOTICOS.CBA_CODIGO, CFG_BACANTIBIOTICOS.CBA_DESCRIPCION , CFG_BACANTIBIOTICOS.CBA_ACTIVO,"
              + " CFG_BACANTIBIOTICOS.CBA_IDBACANTIBIOTICO, "
              + "LDV_BACANTIBIOTICOSCLASES.LDVAC_DESCRIPCION , CFG_BACANTIBIOGRAMASANTIBIOT.CBAD_OPCIONAL "
              + "FROM CFG_BACANTIBIOGRAMASANTIBIOT  "
              + "INNER JOIN CFG_BACANTIBIOTICOS ON CFG_BACANTIBIOTICOS.CBA_IDBACANTIBIOTICO = CFG_BACANTIBIOGRAMASANTIBIOT.CBAD_IDBACANTIBIOTICO  "
              + "INNER JOIN CFG_BACANTIBIOGRAMAS ON CFG_BACANTIBIOGRAMASANTIBIOT.CBAD_IDBACANTIBIOGRAMA = CFG_BACANTIBIOGRAMAS.CBA_IDBACANTIBIOGRAMA "
              + "LEFT JOIN LDV_BACANTIBIOTICOSCLASES ON LDV_BACANTIBIOTICOSCLASES.LDVAC_IDANTIBIOTICOCLASE = CFG_BACANTIBIOTICOS.CBA_IDANTIBIOTICOCLASE "
              + "WHERE CFG_BACANTIBIOGRAMAS.CBA_DESCRIPCION = :antibiogramName");
      query.setParameter("antibiogramName", antibiogramName);

      List<Object[]> objetoQuery = (List<Object[]>) query.list();

      for (Object[] parameter : objetoQuery) {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("code", parameter[0] == null ? "" : parameter[0].toString());
        result.put("name", parameter[1] == null ? "" : parameter[1].toString());
        result.put("active", parameter[2] == null ? "" : parameter[2].toString());
        result.put("id", parameter[3] == null ? "" : parameter[3].toString());
        result.put("class", parameter[4] == null ? "" : parameter[4].toString());
        result.put("opcional", parameter[5] == null ? "" : parameter[5].toString());
        listaResult.add(result);
      }

    } catch (HibernateException he) {
      logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
      he.printStackTrace();

      // throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return listaResult;
  }

  public List<OrdenExamenCapturaResultadoDTO> getOrdenesCapturaResultadosMicrobiologia(BCFichaFiltroDTO ffDto)
      throws BiosLISException, BiosLISDAOException {
    String sCond = "";
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<OrdenExamenCapturaResultadoDTO> lst = null;
    try {

      if (ffDto.getBo_nOrdenIni() != null && ffDto.getBo_nOrdenFin() != null) {
        sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
        sCond += " df.DF_NORDEN <= :nOrdenFin AND ";
      } else {
        if (ffDto.getBo_nOrdenIni() != null || ffDto.getBo_nOrdenFin() != null) {
          if (ffDto.getBo_nOrdenIni() != null) {
            sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
            sCond += " df.DF_NORDEN <= :nOrdenIni AND ";
          }
          if (ffDto.getBo_nOrdenFin() != null) {
            sCond += " df.DF_NORDEN >= :nOrdenFin AND ";
            sCond += " df.DF_NORDEN <= :nOrdenFin AND ";
          }
        }
      }

      if ((ffDto.getBo_nOrdenIni() != null && ffDto.getBo_nOrdenFin() != null)
          || (ffDto.getBo_nOrdenIni() == null && ffDto.getBo_nOrdenFin() == null)) {

        if (ffDto.getBo_fIni() != null && !ffDto.getBo_fIni().trim().isEmpty() && ffDto.getBo_fFin() != null
            && !ffDto.getBo_fFin().trim().isEmpty()) {
          sCond += " df.DF_FECHAORDEN >= TO_DATE(:fini,'DD-MM-YYYY HH24:MI:SS') AND ";
          sCond += " df.DF_FECHAORDEN <= TO_DATE(:ffin,'DD-MM-YYYY HH24:MI:SS') + 1 AND ";
        } else {
          if ((ffDto.getBo_fIni() != null && !ffDto.getBo_fIni().trim().isEmpty())
              || (ffDto.getBo_fFin() != null && !ffDto.getBo_fFin().trim().isEmpty())) {
            if (ffDto.getBo_fIni() != null && !ffDto.getBo_fIni().trim().isEmpty()) {
              sCond += " df.DF_FECHAORDEN LIKE TO_DATE(:fini,'DD-MM-YYYY HH24:MI:SS') AND ";
            }
            if (ffDto.getBo_fFin() != null && !ffDto.getBo_fFin().trim().isEmpty()) {
              sCond += " df.DF_FECHAORDEN  LIKE TO_DATE(:ffin,'DD-MM-YYYY HH24:MI:SS') And ";
            }
          }
        }
      }

      String apellido = "";
      if (ffDto.getBo_apellido() != null) {
        apellido = ffDto.getBo_apellido().toUpperCase();
      }

      String nombre = "";
      if (ffDto.getBo_nombre() != null && !ffDto.getBo_nombre().trim().isEmpty()) {
        nombre = ffDto.getBo_nombre().toUpperCase();
      }

      sCond = sCond + " ce.CE_IDTIPOEXAMEN = " + Constante.TIPOEXAMEN_MICROBIOLOGIA + " "
          + " AND ( UPPER(dp.DP_PRIMERAPELLIDO) like '%" + apellido + "%'  or  UPPER(dp.DP_PRIMERAPELLIDO_B) like '%"
          + apellido + "%' " + " or  UPPER(dp.DP_SEGUNDOAPELLIDO) like '%" + apellido
          + "%'  or  UPPER(dp.DP_SEGUNDOAPELLIDO_B) like '%" + apellido + "%') "
          + " and ( UPPER(dp.DP_NOMBRES)  like '%" + nombre + "%' or  UPPER(dp.DP_NOMBRES)  like '%" + nombre + "%' ) ";

      if (ffDto.getBo_procedencia() != null) {
        sCond = sCond + " and  df.DF_IDPROCEDENCIA = " + ffDto.getBo_procedencia();
      }
      if (ffDto.getBo_tipoDocumento() != null) {
        sCond = sCond + " and dp.DP_IDTIPODOCUMENTO  =" + ffDto.getBo_tipoDocumento();
      }
      if (ffDto.getBo_nroDocumento() != null && !ffDto.getBo_nroDocumento().trim().isEmpty()) {
        sCond = sCond + " and dp.DP_NRODOCUMENTO  ='" + ffDto.getBo_nroDocumento() + "'";
      }

      if (ffDto.getBo_localizacion() != null) {
        sCond = sCond + " and df.DF_IDLOCALIZACION =" + ffDto.getBo_localizacion();
      }
      if (ffDto.getBo_servicio() != null) {
        sCond = sCond + " and df.DF_IDSERVICIO  =" + ffDto.getBo_servicio();
      }
      if (ffDto.getBo_seccion() != null) {
        sCond = sCond + " and ce.CE_IDSECCION =" + ffDto.getBo_seccion();
      }
      if (ffDto.getBo_convenio() != null) {
        sCond = sCond + " and  cc.CC_IDCONVENIO =" + ffDto.getBo_convenio();
      }

      if (sCond != "")
        sCond = " where " + sCond;

      Query qry = sesion
          .createSQLQuery(_sqlOrdenesConSeccionesCapturaResultadosMicrobiologia + sCond
              + " ORDER BY dfe.DFE_EXAMENESURGENTE , df.DF_FECHAORDEN DESC  ")
          .setResultTransformer(Transformers.aliasToBean(OrdenExamenCapturaResultadoDTO.class));

      if (ffDto.getBo_fFin() != null && !ffDto.getBo_fFin().trim().isEmpty()) {
        qry.setParameter("ffin", ffDto.getBo_fFin());

      }
      if (ffDto.getBo_fIni() != null && !ffDto.getBo_fIni().trim().isEmpty()) {
        qry.setParameter("fini", ffDto.getBo_fIni());
      }
      if (ffDto.getBo_nOrdenIni() != null) {
        qry.setParameter("nOrdenIni", ffDto.getBo_nOrdenIni());
      }
      if (ffDto.getBo_nOrdenFin() != null) {
        qry.setParameter("nOrdenFin", ffDto.getBo_nOrdenFin());
      }
      logger.debug("Entrar a query");
      lst = (List<OrdenExamenCapturaResultadoDTO>) qry.list();

    } catch (HibernateException e) {
      logger.error(e.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo recuperar información de ordenes: " + e.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return lst;
  }

  private static final String _sqlOrdenesConSeccionesCapturaResultadosMicrobiologia = "SELECT df.DF_NORDEN, TO_CHAR(df.DF_FECHAORDEN,'HH24:MI DD/MM/YYYY') SDF_FECHAORDEN, dp.DP_NOMBRES,dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO,"
      + " concat(round((TRUNC(SYSDATE) - dp.DP_FNACIMIENTO) / 365),' años' )  as SDP_FNACIMIENTO, dp.DP_OBSERVACION, dp.DP_NRODOCUMENTO, dp.DP_EMAIL, dp.DP_IDPACIENTE, "
      + " df.DF_OBSERVACION, ls.LDVS_DESCRIPCION, ltd.LDVTD_DESCRIPCION, ct.CTA_DESCRIPCION, cl.cl_codigolocalizacion as LOCALIZACION, cc.CC_ABREVIADO, cp.CP_DESCRIPCION,"
      + " cs.CS_DESCRIPCION, cd.CD_DESCRIPCION, dfe.DFE_CODIGOESTADOEXAMEN, dfe.DFE_EXAMENESURGENTE, ce.CE_CODIGOEXAMEN, ce.CE_ABREVIADO, to_char(dfet.dfet_idbacestadotest) AS TESTSENPROCESO,"
      + " ce.ce_escultivo  as TIENECULTIVO" + " FROM BIOSLIS.DATOS_FICHAS df "
      + " JOIN BIOSLIS.DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE=DF.DF_IDPACIENTE)"
      + " JOIN BIOSLIS.DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
      + " LEFT JOIN DATOS_FICHASEXAMENESTEST dfet ON (dfet.DFET_NORDEN = df.DF_NORDEN) and dfet.dfet_idbacestadotest = 4 "
      + " JOIN BIOSLIS.LDV_TIPOSDOCUMENTOS ltd ON (ltd.LDVTD_IDTIPODOCUMENTO = dp.DP_IDTIPODOCUMENTO)"
      + " JOIN BIOSLIS.LDV_SEXO ls ON (ls.LDVS_IDSEXO = dp.DP_IDSEXO)"
      + " JOIN BIOSLIS.CFG_TIPOATENCION ct ON (ct.CTA_IDTIPOATENCION = df.DF_IDTIPOATENCION)   "
      + " JOIN BIOSLIS.CFG_LOCALIZACIONES cl ON (cl.CL_IDLOCALIZACION = df.DF_IDLOCALIZACION ) "
      + " LEFT OUTER JOIN BIOSLIS.CFG_PROCEDENCIAS cp ON (cp.CP_IDPROCEDENCIA = df.DF_IDPROCEDENCIA ) "
      + " LEFT OUTER JOIN BIOSLIS.CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.df_IDSERVICIO ) "
      + " JOIN BIOSLIS.CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
      + " LEFT OUTER JOIN BIOSLIS.CFG_CONVENIOS cc ON (cc.CC_IDCONVENIO = df.DF_IDCONVENIO)"
      + " LEFT OUTER JOIN BIOSLIS.CFG_DIAGNOSTICOS cd ON  (cd.CD_IDDIAGNOSTICO = df.DF_IDDIAGNOSTICO)" + " ";

}
