/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.DatosOrdenPacienteExamenesDTO;
import com.grupobios.bioslis.dto.MuestrasDTO;
import com.grupobios.bioslis.dto.TransporteMuestrasDTO;
import com.grupobios.bioslis.entity.CfgExamenestest;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasexamenestestId;
import com.grupobios.bioslis.entity.DatosFichasmuestras;

/**
 *
 *
 * 
 * @author Jan Perkov
 * 
 */
public class DatosFichasmuestrasDAO {

  LogCfgTablasDAO logTablas = new LogCfgTablasDAO();
  CfgInstitucionesDeSaludDAO institut = new CfgInstitucionesDeSaludDAO();
  LdvSexoDAO ldvS = new LdvSexoDAO();
  LdvEspecialidadesMedicasDAO especMed = new LdvEspecialidadesMedicasDAO();
  private static Logger logger = LogManager.getLogger(DatosFichasmuestrasDAO.class);

  public void insertDatosFichasExamenesTest(int idOrden, int idExamen)
      throws BiosLISDAONotFoundException, BiosLISDAOException {
    DatosFichasexamenestest dfet = new DatosFichasexamenestest();
    DatosFichasexamenestestId dfei = new DatosFichasexamenestestId();
    CfgExamenesTestDAO cet = new CfgExamenesTestDAO();
    CfgTestDAO tomaMuestra = new CfgTestDAO();
    Date date = new Date(System.currentTimeMillis());

    List<CfgExamenestest> listaTests = cet.getExamenTest(idExamen);

    // dfe.setdfeNorden(idOrden);
    // dfe.setdfeIdexamen(idExamen);
    // sesion.beginTransaction();
    for (CfgExamenestest test : listaTests) {
      // variables
      int idTest = test.getCfgTest().getCtIdtest();
      CfgTest cfgtest = tomaMuestra.getTestById(idTest);
      short idEnvases = (short) test.getCfgEnvases().getCenvIdenvase();
      short idTomaMuestra = (short) cfgtest.getCfgMuestras().getCmueIdtipomuestra();
      int idSeccion = cfgtest.getCfgSecciones();

      // Guardado
      Session sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      dfei.setDfetIdexamen(idExamen);
      dfei.setDfetNorden(idOrden);
      dfei.setDfetIdtest(idTest);

      dfet.setId(dfei);
      dfet.setDfetIdenvase(idEnvases);
      dfet.setDfetIdtipomuestra(idTomaMuestra);
      dfet.setDfetIdseccion((short) idSeccion);
      dfet.setDfetFechaorden(date);
      // dfet.setDfetIdpaciente(1);

      sesion.save(dfet);

      sesion.getTransaction().commit();
      sesion.close();
    }

  }

  public int getLastIdDatosFichasExamenes() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      sesion.beginTransaction();
      Query query = sesion
          .createQuery(
              "select med.dfNorden from com.grupobios.bioslis.entity.DatosFichas med " + "order by med.dfNorden desc")
          .setMaxResults(1);
      int indice;
      try {
        indice = ((int) query.list().get(0));
      } catch (Exception e) {
        indice = 1;
      }
      sesion.getTransaction().commit();
      sesion.close();
      return indice + 1;
    } catch (Exception e) {
      return 1;
    }
  }

  public void updateDatosFichasmuestras(DatosFichasmuestras dfm) throws BiosLISDAOException {
	
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      sesion.update(dfm);
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
  public String updateDatosFichasmuestrasZonaCuerpo(long idMuestra,long idCuerpo, Long idUsuario) throws BiosLISDAOException {
	    Session sesion = null;
	    try {
	    	sesion = HibernateUtil.getSessionFactory().openSession();
	        sesion.beginTransaction();
	        Query query = sesion
	            .createSQLQuery("UPDATE DATOS_FICHASMUESTRAS SET DFM_IDZONACUERPO = :idCuerpo WHERE DFM_IDMUESTRA =:idMuestra ");
	        query.setParameter("idCuerpo", idCuerpo);
	        query.setParameter("idMuestra", idMuestra);
	        query.executeUpdate();
	        sesion.getTransaction().commit();
	        sesion.close();   
	        
	        
	        
	    } catch (HibernateException he) {
	      logger.error(he.getMessage());
	      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
	    } finally {
	      if (sesion != null && sesion.isOpen()) {
	        sesion.close();
	      }
	    }
	    return "";
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
    }finally {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
        }
    }
  }

  public List<DatosFichasmuestras> getMuestrasPendientes() throws Exception {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    DatosFichasExamenesTestDAO dfetDAO = new DatosFichasExamenesTestDAO();
    Query query = sesion.createQuery("select dfm " + "from com.grupobios.bioslis.entity.DatosFichasmuestras dfm "
        + "left join fetch dfm.datosPacientes dp " + "where dfm.dfmEstadorm = :estado "
        + "order by dfm.dfmFechatm asc");
    query.setParameter("estado", "P");
    List<DatosFichasmuestras> listaMuestras = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    for (DatosFichasmuestras muestra : listaMuestras) {
      try {
        String envase = dfetDAO.getDatosFichasExamenesTestByOrden((long) muestra.getDfmNorden()).get(0)
            .getCfgExamenestest().getCfgEnvases().getCenvDescripcion();

        // muestra.setDfmEnvase(envase);

      } catch (Exception ex) {
    	  ex.printStackTrace();
      } /*
         * Date date1 = new
         * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(muestra.getDfmFechatm());
         * DateFormat dateFormat = new SimpleDateFormat("hh:mm"); String strDate =
         * dateFormat.format(date1); muestra.setDfmFechatm(strDate);
         */
    }
    return listaMuestras;
  }

  public List<DatosFichasmuestras> getMuestrasRecepcionadas() throws ParseException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    DatosFichasExamenesTestDAO dfetDAO = new DatosFichasExamenesTestDAO();
    Query query = sesion.createQuery("select dfm " + "from com.grupobios.bioslis.entity.DatosFichasmuestras dfm "
        + "left join fetch dfm.datosPacientes dp " + "where dfm.dfmEstadorm = :estado "
        + "order by dfm.dfmFecharm asc");
    query.setParameter("estado", "R");
    List<DatosFichasmuestras> listaMuestras = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    for (DatosFichasmuestras muestra : listaMuestras) {
      try {
        String envase = dfetDAO.getDatosFichasExamenesTestByOrden((long) muestra.getDfmNorden()).get(0)
            .getCfgExamenestest().getCfgEnvases().getCenvDescripcion();

        // muestra.setDfmEnvase(envase);

      } catch (Exception ex) {

      }
      /*
       * Date date1 = new
       * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(muestra.getDfmFecharm());
       * DateFormat dateFormat = new SimpleDateFormat("hh:mm"); String strDate =
       * dateFormat.format(date1); muestra.setDfmFecharm(strDate);
       */
    }
    return listaMuestras;
  }

  public List<DatosFichasmuestras> getMuestras() throws ParseException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    DatosFichasExamenesTestDAO dfetDAO = new DatosFichasExamenesTestDAO();
    Query query = sesion.createQuery("select dfm " + "from com.grupobios.bioslis.entity.DatosFichasmuestras dfm "
        + "left join fetch dfm.datosUsuariosByDfmIdusuariorm userRM ");
    List<DatosFichasmuestras> listaMuestras = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    for (DatosFichasmuestras muestra : listaMuestras) {
      try {
        String envase = dfetDAO.getDatosFichasExamenesTestByOrden((long) muestra.getDfmNorden()).get(0)
            .getCfgExamenestest().getCfgEnvases().getCenvDescripcion();

        // muestra.setDfmEnvase(envase);

      } catch (Exception ex) {

      } /*
         * Date date1 = new
         * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(muestra.getDfmFechatm());
         * DateFormat dateFormat = new SimpleDateFormat("hh:mm dd/MM"); String strDate =
         * dateFormat.format(date1); muestra.setDfmFechatm(strDate);
         */
    }
    return listaMuestras;
  }

  public DatosFichasmuestras getMuestraById(long idMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createQuery("select dfm " + "from com.grupobios.bioslis.entity.DatosFichasmuestras dfm "
            + "left join fetch dfm.datosPacientes dp "
            /*
             * + "left join fetch dfm.datosUsuariosByDfmIdusuariotra usertra " +
             * "left join fetch dfm.datosUsuariosByDfmIdusuariotm usertm " +
             * "left join fetch dfm. datosUsuariosByDfmIdusuariorm userrm "
             */
            + "left join fetch dfm.cfgBaczonacuerpo cbzc " + "where dfm.dfmIdmuestra = :idmuestra");
        query.setParameter("idmuestra", idMuestra);
        DatosFichasmuestras muestra = (DatosFichasmuestras) query.setMaxResults(1).uniqueResult();
        sesion.close();
        return muestra;
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()) {
            sesion.close();
        }
    }
  }

  public DatosFichasmuestras getMuestraByCodigoBarras(String dfmCodigobarra) throws BiosLISDAOException {
    Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Query query = sesion.createQuery("select dfm " + "from com.grupobios.bioslis.entity.DatosFichasmuestras dfm "
                + "left join fetch dfm.datosPacientes dp "
                /*
                 * + "left join fetch dfm.datosUsuariosByDfmIdusuariotra usertra " +
                 * "left join fetch dfm.datosUsuariosByDfmIdusuariotm usertm " +
                 * "left join fetch dfm. datosUsuariosByDfmIdusuariorm userrm "
                 */
                + "left join fetch dfm.cfgBaczonacuerpo cbzc " + "where dfm.dfmCodigobarra = :codigoBarras");
            query.setParameter("codigoBarras", dfmCodigobarra);
            DatosFichasmuestras muestra = (DatosFichasmuestras) query.setMaxResults(1).uniqueResult();
            sesion.close();
            return muestra;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
  }

  public void insertDatosFichasMuestra(DatosFichasmuestras dfm) throws BiosLISDAOException {
    // public void insertDatosFichasMuestra(int idOrden, int idPaciente, int
    // idUsuario, String prefijo) throws Exception {
    Session sesion = null;
    try {
      /*
       * DatosFichasmuestras DFM = new DatosFichasmuestras(); DatosPacientesDAO DPDAO
       * = new DatosPacientesDAO(); CfgBaczonacuerpoDAO CBCDAO = new
       * CfgBaczonacuerpoDAO(); // DatosUsuariosDAO DUDAO = new DatosUsuariosDAO();
       * DatosPacientes DP = DPDAO.getPacienteById(idPaciente); // DatosUsuarios DU =
       * DUDAO.getUsuarioById((long) 1); CfgBaczonacuerpo CfgB =
       * CBCDAO.BuscarBacZonaCuerpoPorId((short) 1); Date date = new
       * Date(System.currentTimeMillis()); int idMuestra =
       * this.getLastIdDatosFichasMuestras();
       */
      // Guardado
      // ESTADORM = R TIENE QUE TENER FECHARM
      // ESTADORM = P NO ES NECESARIO FECHARM
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      /*
       * DFM.setDfmIdmuestra((long)idMuestra); DFM.setDfmNorden((long)85);
       * DFM.setDatosPacientes(DP); DFM.setDfmPrefijotipomuestra("sd");
       * DFM.setCfgBaczonacuerpo(CfgB); DFM.setDfmIdusuariorm(1);
       * DFM.setDfmIdusuariotra(1); DFM.setDfmIdusuariotm(1); DFM.setDfmFechatm(date);
       * DFM.setDfmCodigobarra("test"); DFM.setDfmEncargadotra("test");
       * DFM.setDfmEstadorm("R"); DFM.setDfmFecharm(date);
       */
      sesion.save(dfm);

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

  public Long getLastIdDatosFichasMuestras() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Long indice = 0L;
      sesion.beginTransaction();
      Query query = sesion.createSQLQuery("select BIOSLIS.SEQ_DATOSFICHASMUESTRAS.NEXTVAL from dual");
      List<Object> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();

      for (Object listaa : lista) {
        // indice = Integer.parseInt(listaa.toString());
        indice = Long.parseLong(listaa.toString());
      }
      return indice;
    } catch (Exception e) {
      throw e;

    }finally {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
        }
    }

  }

  // Marco C.
  public DatosFichasmuestras getDFMbyNordenyPrefijo(Long nOrden, String prefijo) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("from DatosFichasmuestras dfm " + "where dfm.dfmNorden = :nOrden "
        + "and dfm.dfmPrefijotipomuestra = :prefijo");
    query.setParameter("nOrden", nOrden);
    query.setParameter("prefijo", prefijo);
    DatosFichasmuestras dMuestras = (DatosFichasmuestras) query.setMaxResults(1).uniqueResult();
    sesion.getTransaction().commit();
    sesion.close();
    return dMuestras;
  }

  public List<DatosFichasmuestras> getDFMbyCodigoDeBarras(String codigoBarras) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("from DatosFichasmuestras dfm " + "where dfm.dfmCodigobarra = :codigoBarras");
    // query.setParameter("nOrden", nOrden);
    query.setParameter("codigoBarras", codigoBarras);
    List<DatosFichasmuestras> listaMuestras = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaMuestras;
  }

  public List<MuestrasDTO> getMuestrasByEstadoRM(String estado) throws ParseException, BiosLISDAOException {
    Session sesion = null;
    List<MuestrasDTO> listaMuestras = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();

        String sqlQueryMuestras = "SELECT DISTINCT " + "dfm.dfm_codigobarra CODIGOBARRAS, "
            + "to_char(dfm.dfm_fechatm, 'dd/MM/yyyy hh24:mi:ss') FECHATM, to_char(dfm.dfm_fecharm, 'dd/MM/yyyy hh24:mi:ss') FECHARM,"
            + "dfm.dfm_norden NORDEN, mue.cmue_descripcion MUESTRADESC, env.cenv_descripcion ENVASEDESC, du.du_usuario USRRECEPTOR, "
            + "dfm.dfm_estadorm ESTADORM, dfm.dfm_recepcionobs OBSERVACIONRM, dfm.dfm_idmuestra IDMUESTRA "
            + "FROM datos_fichasmuestras dfm " + "left join datos_usuarios du on dfm.dfm_idusuariorm = du.du_idusuario "
            + "left join datos_fichasexamenestest dfet on dfm.dfm_idmuestra = dfet.dfet_idmuestra "
            + "left join cfg_envases env on env.cenv_idenvase = dfet.dfet_idenvase "
            + "left join cfg_muestras mue on mue.cmue_idtipomuestra = dfet.dfet_idtipomuestra "
            + "where dfm.dfm_estadorm=:estado and dfm.dfm_fechatm >= SYSDATE - 1 "
            + " ORDER BY 2 asc";
        // "where dfm.dfm_estadorm=:estado";
    //        if (estado.equals("P")){
    //            sqlQueryMuestras += " or dfm.dfm_estadorm is null";
    //        }
        Query query = sesion.createSQLQuery(sqlQueryMuestras)
            .setResultTransformer(Transformers.aliasToBean(MuestrasDTO.class));
        query.setParameter("estado", estado);
         listaMuestras = query.list();
        
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()) {
            sesion.close();
        }
    }
    return listaMuestras;
  }

  public List<MuestrasDTO> getMuestrasConDerivador() throws ParseException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();

    String sqlQueryMuestras = "SELECT DISTINCT " + "dfm.dfm_codigobarra CODIGOBARRAS, "
        + "to_char(dfm.dfm_fechatm, 'dd/MM/yyyy hh24:mi:ss') FECHATM, to_char(dfm.dfm_fecharm, 'dd/MM/yyyy hh24:mi:ss') FECHARM, "
        + "dfm.dfm_norden NORDEN, mue.cmue_descripcion MUESTRADESC, env.cenv_descripcion ENVASEDESC, du.du_usuario USRRECEPTOR, "
        + "dfm.dfm_estadorm ESTADORM, dfm.dfm_recepcionobs OBSERVACIONRM, dfm.dfm_idmuestra IDMUESTRA, cd.cderiv_descripcion DERIVADOR "
        + "FROM datos_fichasmuestras dfm " + "left join datos_usuarios du on dfm.dfm_idusuariorm = du.du_idusuario "
        + "left join datos_fichasexamenestest dfet on dfm.dfm_idmuestra = dfet.dfet_idmuestra "
        + "left join cfg_examenestest et on dfet.dfet_idexamen=et.cet_idexamen and dfet.dfet_idtest=et.cet_idtest "
        + "left join cfg_envases env on env.cenv_idenvase = dfet.dfet_idenvase "
        + "left join cfg_muestras mue on mue.cmue_idtipomuestra = dfet.dfet_idtipomuestra "
        + "left join datos_fichasexamenes dfe on dfet.dfet_idexamen = dfe.dfe_idexamen and dfet.dfet_norden = dfe.dfe_norden "
        + "left join cfg_derivadores cd on cd.cderiv_idderivador = dfe.dfe_idderivador "
        + "where dfm.dfm_codigobarra is not null and dfe.dfe_idderivador is not null and dfe.dfe_idderivador > 0 "
        + "and dfm.dfm_estadorm='R' and dfm.dfm_fechatm >= SYSDATE - 1 "
        + "ORDER BY 2 asc";
    Query query = sesion.createSQLQuery(sqlQueryMuestras)
        .setResultTransformer(Transformers.aliasToBean(MuestrasDTO.class));
    List<MuestrasDTO> listaMuestras = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaMuestras;
  }

  public List<MuestrasDTO> getMuestrasRM(String fecha) throws ParseException, BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
            String sqlQueryMuestras = "SELECT DISTINCT"
                + " dfm.dfm_codigobarra CODIGOBARRAS, to_char(dfm.dfm_fechatm, 'dd/MM/YYYY hh24:mi') FECHATM, dfm.dfm_norden NORDEN,"
                + " mue.cmue_descripcion MUESTRADESC, env.cenv_descripcion ENVASEDESC, du.du_nombres || ' ' || du.du_primerapellido USRRECEPTOR, du.du_idusuario IDUSRRECEPTOR,"
                + " dfm.dfm_estadorm ESTADORM, dfm.dfm_recepcionobs OBSERVACIONRM, dfm.dfm_idmuestra IDMUESTRA, cd.cderiv_idderivador IDDERIVADOR, cd.cderiv_descripcion DERIVADOR"
                +
                // contabiliza los exámenes no anulados o con el campo en null en las muestras,
                // si no hay ninguno el valor es 'S'
                /*
                 * " CASE WHEN (" + " select count(*) from datos_fichasexamenes dfe" +
                 * " JOIN datos_fichasexamenestest dfet on dfe.dfe_norden = dfet.dfet_norden and dfe.dfe_idexamen = dfet.dfet_idexamen"
                 * +
                 * " where dfe.dfe_norden = dfm.dfm_norden and dfet.dfet_idmuestra = dfm.dfm_idmuestra and (dfe.dfe_examenanulado = 'N' or dfe.dfe_examenanulado is null)) > 0"
                 * + " THEN N ELSE S END allexamenesanulados" +
                 */
                //
                " FROM datos_fichasmuestras dfm" + " left join datos_usuarios du on dfm.dfm_idusuariorm = du.du_idusuario"
                + " left join datos_fichasexamenestest dfet on dfm.dfm_idmuestra = dfet.dfet_idmuestra"
                + " left join cfg_envases env on env.cenv_idenvase = dfet.dfet_idenvase"
                + " left join cfg_muestras mue on dfet.dfet_idtipomuestra=mue.cmue_idtipomuestra"
                + " left join datos_fichasexamenes dfe on dfet.dfet_idexamen = dfe.dfe_idexamen and dfet.dfet_norden = dfe.dfe_norden"
                + " left join cfg_derivadores cd on cd.cderiv_idderivador = dfe.dfe_idderivador"
                + " where dfm.dfm_codigobarra is not null and dfm.dfm_estadorm is not null";
            String fechaFin = "";
            if (fecha.equals("HOY")) { // esto permite que al seleccionarse la fecha actual busque datos de hasta 24
                                       // hrs antes, y no del día solamente
              sqlQueryMuestras += " and dfm.dfm_fechatm >= SYSDATE - 1";
            } else {
              fechaFin = fecha + "23:59:59";
              sqlQueryMuestras += " and dfm.dfm_fechatm BETWEEN :fecha and to_date( :fechaFin, 'dd/MM/yy HH24:mi:ss')";
            }

            sqlQueryMuestras += " order by FECHATM desc";
            logger.debug("sqlQueryMuestras: " + sqlQueryMuestras);

            Query query = sesion.createSQLQuery(sqlQueryMuestras)
                .setResultTransformer(Transformers.aliasToBean(MuestrasDTO.class));
            if (!fecha.equals("HOY")) {
              query.setParameter("fecha", fecha);
              query.setParameter("fechaFin", fechaFin);
            }
            List<MuestrasDTO> listaMuestras = query.list();
            sesion.close();
            return listaMuestras;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
  }

  public DatosOrdenPacienteExamenesDTO getDatosOrdenPaciente(Long idMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        String sqlDatosOP = "SELECT (dp.dp_nombres || ' ' || dp.dp_primerapellido || ' ' || dp.dp_segundoapellido) nombres, "
            + "ls.ldvs_descripcion sexo, dp.dp_fnacimiento FECHA_NAC, df.df_norden norden, to_char(df.df_fechaorden, 'dd/mm/yyyy hh24:mi:ss') fecha_orden, "
            + "df.df_observacion obs_orden, to_char(dfm.dfm_fechatm, 'dd/mm/yyyy hh24:mi:ss') fecha_tm, dfm.dfm_tmobservacion obs_tm, cta.cta_descripcion tipo_atencion, "
            + "CASE WHEN cp.cp_idprocedencia > 0 " + "THEN cp.cp_descripcion ELSE '' " + "END procedencia, "
            + "CASE WHEN cs.cs_idservicio > 0 " + "THEN cs.cs_descripcion ELSE '' " + "END servicio,"
            + " ("
            + " SELECT"
            + " CAST(MAX(CASE WHEN ct.CT_TIENEANTECEDENTES = 'S' THEN 'S' ELSE 'N' END)AS VARCHAR(1) )"
            + " FROM datos_fichasexamenestest dfet"
            + " INNER JOIN CFG_TEST ct ON dfet.DFET_IDTEST = ct.CT_IDTEST"
            + " WHERE dfet.DFET_NORDEN = df.df_norden"
            + " GROUP BY DFET_NORDEN) AS ESTADOANTECEDENTERM"
            + " FROM datos_fichasmuestras dfm " + "JOIN datos_pacientes dp on dfm.dfm_idpaciente = dp.dp_idpaciente "
            + " JOIN datos_fichas df on df.df_norden = dfm.dfm_norden and df.df_idpaciente = dp.dp_idpaciente "
            + " JOIN ldv_sexo ls on ls.ldvs_idsexo = dp.dp_idsexo "
            + " JOIN cfg_tipoatencion cta ON cta.cta_idtipoatencion = df.df_idtipoatencion "
            + " LEFT JOIN cfg_procedencias cp ON cp.cp_idprocedencia = df.df_idprocedencia "
            + " LEFT JOIN cfg_localizaciones cl ON cl.cl_idlocalizacion = df.df_idlocalizacion "
            + " LEFT JOIN cfg_servicios cs ON cs.cs_idservicio = cl.cl_idservicio " + "WHERE dfm.dfm_idmuestra = :idMuestra";
        Query query = sesion.createSQLQuery(sqlDatosOP)
            .setResultTransformer(Transformers.aliasToBean(DatosOrdenPacienteExamenesDTO.class));
        query.setParameter("idMuestra", idMuestra);
        DatosOrdenPacienteExamenesDTO datos = (DatosOrdenPacienteExamenesDTO) query.uniqueResult();
        sesion.close();
        return datos;
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()) {
            sesion.close();
        }
    }
  }

  public List<DatosFichasmuestras> obtenerListaMuestrasPorOrden(long nOrden) throws BiosLISDAOException {
    Session sesion = null;
    List<DatosFichasmuestras> listaMuestras =null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createQuery(
            "from DatosFichasmuestras dfm " + "where dfm.dfmNorden = :nOrden ");
        query.setParameter("nOrden", nOrden);
        listaMuestras = query.list();
        sesion.close();
        
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()) {
            sesion.close();
        }
    }
    return listaMuestras;
  }
  //VALIDA SI DFET TIENE MUESTRAS POR TOMAR
  public Boolean validar_OrdenTM(long nOrden) throws BiosLISDAOException {
	    Session sesion = null;
	    Boolean validadorTM = null;
	    try {
	        sesion = HibernateUtil.getSessionFactory().openSession();
	        Query query = sesion.createSQLQuery("SELECT"
	        		+ " CASE WHEN EXISTS ("
	        		+ " SELECT 1"
	        		+ " FROM DATOS_FICHASMUESTRAS"
	        		+ " WHERE DFM_NORDEN = :nOrden AND DFM_ESTADOTM = 'P'"
	        		+ " ) THEN 'Y' ELSE 'N' END AS HasNulls"
	        		+ " FROM DUAL");
	        query.setParameter("nOrden", nOrden);
	        String a = query.uniqueResult().toString();
	        
	        sesion.close();
	        validadorTM = a.equals("N") ? true : false;
	        
	    } catch (HibernateException he) {
	        logger.error(he.getMessage());
	        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
	    } finally {
	        if (sesion != null && sesion.isOpen()) {
	            sesion.close();
	        }
	    }
	    return validadorTM;
	  }

  public List<MuestrasDTO> obtenerMuestrasRechazo(long nOrden) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        String sqlMuestras = "select distinct dfm.dfm_norden NORDEN, dfm.dfm_idmuestra IDMUESTRA, dfm.dfm_codigobarra CODIGOBARRAS, "
        + "to_char(dfm.dfm_fechatm, 'dd/mm/yyyy hh24:mi:ss') FECHATM, to_char(dfm.dfm_fecharm, 'dd/mm/yyyy hh24:mi:ss') FECHARM, "
        + "dfet.dfet_idtipomuestra IDTIPOMUESTRA, mue.cmue_descripcion MUESTRADESC, dfet.dfet_idenvase IDENVASE, "
        + "env.cenv_descripcion ENVASEDESC, dfm.dfm_estadorm ESTADORM, dfm.dfm_estadotm ESTADOTM, "
        + "usuariotm.du_nombres || ' ' || usuariotm.du_primerapellido || ' ' || usuariorm.du_segundoapellido USRFLEBOTOMISTA, "
        + "usuariorm.du_nombres || ' ' || usuariorm.du_primerapellido || ' ' || usuariorm.du_segundoapellido USRRECEPTOR, "
        + "dfm.dfm_tmobservacion OBSERVACIONTM, dfm.dfm_recepcionobs OBSERVACIONRM, cd.cderiv_descripcion DERIVADOR, dfm.dfm_nrovecestomada nrovecestomada "
        + "from datos_fichasmuestras dfm "
        + "left join datos_usuarios usuariorm on dfm.dfm_idusuariorm = usuariorm.du_idusuario "
        + "left join datos_usuarios usuariotm on dfm.dfm_idusuariotm = usuariotm.du_idusuario "
        + "left join datos_fichasexamenestest dfet on dfm.dfm_idmuestra = dfet.dfet_idmuestra "
        + "left join cfg_envases env on env.cenv_idenvase = dfet.dfet_idenvase "
        + "left join cfg_muestras mue on dfet.dfet_idtipomuestra=mue.cmue_idtipomuestra "
        + "left join datos_fichasexamenes dfe on dfet.dfet_idexamen = dfe.dfe_idexamen and dfet.dfet_norden = dfe.dfe_norden "
        + "left join cfg_derivadores cd on cd.cderiv_idderivador = dfe.dfe_idderivador "
        + "where dfm.dfm_norden = :nOrden and dfm.dfm_idmuestra > 0 and dfm.dfm_codigobarra is not null";
        Query query = sesion.createSQLQuery(sqlMuestras).setResultTransformer(Transformers.aliasToBean(MuestrasDTO.class));
        query.setParameter("nOrden", nOrden);
        List<MuestrasDTO> listaMuestras = query.list();
        sesion.close();
        return listaMuestras;
    } catch (HibernateException he) {
        logger.error(he.getMessage());
        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
        if (sesion != null && sesion.isOpen()) {
            sesion.close();
        }
    }

  }

  public MuestrasDTO obtenerMuestraRechazo(long nOrden, long idMuestra) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    String sqlMuestras = "select distinct dfm.dfm_norden NORDEN, dfm.dfm_idmuestra IDMUESTRA, dfm.dfm_codigobarra CODIGOBARRAS, "
        + "to_char(dfm.dfm_fechatm, 'dd/mm/yyyy hh24:mi:ss') FECHATM, to_char(dfm.dfm_fecharm, 'dd/mm/yyyy hh24:mi:ss') FECHARM, "
        + "dfet.dfet_idtipomuestra IDTIPOMUESTRA, mue.cmue_descripcion MUESTRADESC, dfet.dfet_idenvase IDENVASE, "
        + "env.cenv_descripcion ENVASEDESC, dfm.dfm_estadorm ESTADORM, dfm.dfm_estadotm ESTADOTM, "
        + "usuariotm.du_nombres || ' ' || usuariotm.du_primerapellido USRFLEBOTOMISTA, "
        + "usuariorm.du_nombres || ' ' || usuariorm.du_primerapellido USRRECEPCIONA, "
        + "dfm.dfm_tmobservacion OBSERVACIONTM, dfm.dfm_recepcionobs OBSERVACIONRM, cd.cderiv_descripcion DERIVADOR, dfm.dfm_nrovecestomada nrovecestomada "
        + "from datos_fichasmuestras dfm "
        + "left join datos_usuarios usuariorm on dfm.dfm_idusuariorm = usuariorm.du_idusuario "
        + "left join datos_usuarios usuariotm on dfm.dfm_idusuariotm = usuariotm.du_idusuario "
        + "left join datos_fichasexamenestest dfet on dfm.dfm_idmuestra = dfet.dfet_idmuestra "
        + "left join cfg_examenestest et on dfet.DFET_IDEXAMEN=et.cet_idexamen and dfet.dfet_idtest=et.cet_idtest "
        + "left join cfg_envases env on env.cenv_idenvase = dfet.dfet_idenvase "
        + "left join cfg_muestras mue on ct.ct_idtipomuestra=mue.cmue_idtipomuestra "
        + "left join datos_fichasexamenes dfe on dfet.dfet_idexamen = dfe.dfe_idexamen and dfet.dfet_norden = dfe.dfe_norden "
        + "left join cfg_derivadores cd on cd.cderiv_idderivador = dfe.dfe_idderivador "
        + "where dfm.dfm_norden = :nOrden and dfm.dfm_idmuestra = :idMuestra";
    Query query = sesion.createSQLQuery(sqlMuestras).setResultTransformer(Transformers.aliasToBean(MuestrasDTO.class));
    query.setParameter("nOrden", nOrden);
    query.setParameter("idMuestra", idMuestra);
    MuestrasDTO muestra = (MuestrasDTO) query.uniqueResult();
    sesion.getTransaction().commit();
    sesion.close();
    return muestra;

  }

  public List<Object[]> obtenerMuestrayEnvaseMuestraRechazada(long idMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        String sql = "select distinct mue.cmue_descripcion, env.cenv_descripcion " + "from datos_fichasmuestras dfm "
            + "left join datos_fichasexamenestest dfet on dfm.dfm_idmuestra = dfet.dfet_idmuestra "
            + "left join cfg_envases env on env.cenv_idenvase = dfet.dfet_idenvase "
            + "left join cfg_muestras mue on dfet.dfet_idtipomuestra=mue.cmue_idtipomuestra "
            + "where dfm.dfm_idmuestra = :idMuestra";
        Query query = sesion.createSQLQuery(sql);
        query.setParameter("idMuestra", idMuestra);
        List<Object[]> lista = query.list();
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
  private static final String sql_get_tranporte_muestras = "SELECT  "
  		+ "dfm.DFM_CODIGOBARRA codigobarra,  "
  		+ "dfm.DFM_IDMUESTRA idMuestra, "
  		+ "cm.CMUE_DESCRIPCIONABREVIADA tipoMuestra, "
  		+ "cm.CMUE_IDTIPOMUESTRA idTipoMuestra, "
  		+ "ce.CENV_DESCRIPCION contenedor, "
  		+ "ce.CENV_IDENVASE idEnvase, "
  		+ "du.DU_NOMBRES || ' ' || du.DU_PRIMERAPELLIDO nombreFlebotomista, "
  		+ "du.DU_IDUSUARIO idUsuarioCreacion, "
  		+ "lem.LDVEM_DESCRIPCION estadoTm, "
  		+ "lem.LDVEM_CODIGO codigoEstado "
  		+ "FROM "
  		+ "DATOS_FICHASMUESTRAS dfm "
  		+ "INNER JOIN DATOS_USUARIOS du ON du.DU_IDUSUARIO = dfm.DFM_IDUSUARIOTM  "
  		+ "INNER JOIN DATOS_FICHASEXAMENESTEST dfet ON dfet.DFET_IDMUESTRA = dfm.DFM_IDMUESTRA  "
  		+ "INNER JOIN CFG_MUESTRAS cm ON dfet.DFET_IDTIPOMUESTRA = cm.CMUE_IDTIPOMUESTRA  "
  		+ "INNER JOIN CFG_ENVASES ce ON dfet.DFET_IDENVASE = ce.CENV_IDENVASE  "
  		+ "INNER JOIN LDV_ESTADOSMUESTRAS lem ON lem.LDVEM_CODIGO = dfm.DFM_ESTADOTM  "
  		+ "WHERE dfm.DFM_CODIGOBARRA = :codigoBarra";
  //OBTENER MUESTRAS TOMADAS PARA TRANSPORTE DE MUESTRA
  public TransporteMuestrasDTO getMuestraParaTransporte(String codigoBarra) throws BiosLISDAOException {
	    Session sesion = null;
	    TransporteMuestrasDTO transporteMuestrasDTO = null;
	    try {
	        sesion = HibernateUtil.getSessionFactory().openSession();
	        String sql = sql_get_tranporte_muestras;
	        Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TransporteMuestrasDTO.class));;
	        query.setString("codigoBarra", codigoBarra);
	        transporteMuestrasDTO = (TransporteMuestrasDTO) query.uniqueResult();
	        sesion.close();
	        
	    } catch (HibernateException he) {
	        logger.error(he.getMessage());
	        throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
	    } finally {
	        if (sesion != null && sesion.isOpen()) {
	            sesion.close();
	        }
	    }
	    return transporteMuestrasDTO;
	  }

}
