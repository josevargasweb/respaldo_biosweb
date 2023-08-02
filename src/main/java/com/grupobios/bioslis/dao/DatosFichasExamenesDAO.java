/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.CfgExamenesDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.SendMailDTO;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.CfgExamenes;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenesId;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.LogEventosfichas;

/**
 *
 * @author Jan Perkov
 */
public class DatosFichasExamenesDAO {

  private static Logger logger = LogManager.getLogger(DatosFichasExamenesDAO.class);

  private CfgExamenesDAO cfgExamenesDAO;
  private DatosFichasExamenesTestDAO datosFichasExamenesTestDAO;

  public void updateDatosFichasExamenes(int idOrden, String[] idExamenes, int idPaciente, String ipEquipo,
      Long idUsuarioActualiza) throws NumberFormatException, BiosLISDAOException, BiosLISDAONotFoundException {
    try {
      datosFichasExamenesTestDAO.BorrarExamenesDatosFichasTest(idOrden);
      this.BorrarExamenesDatosFichas(idOrden);

      for (String examen : idExamenes) {
        this.insertDatosFichasExamenes(idOrden, Integer.parseInt(examen));
        datosFichasExamenesTestDAO.insertDatosFichasExamenesTest(idOrden, Integer.parseInt(examen), idPaciente,
            ipEquipo, idUsuarioActualiza);
      }

    } catch (HibernateException | BiosLISDAOException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException(he.getMessage());

    }
  }

  public void insertDatosFichasExamenes(int idOrden, int idExamen) throws BiosLISDAOException {

    DatosFichasexamenes dfe = new DatosFichasexamenes();
    DatosFichasexamenesId dfei = new DatosFichasexamenesId();
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    CfgExamenes examen;
    try {
      logger.debug("Se busca examen con id:{}", idExamen);
      examen = cfgExamenesDAO.getExamenById(idExamen);
      CfgDerivadores derivador = examen.getCfgDerivadores();

      dfei.setDfeIdexamen(idExamen);
      dfei.setDfeNorden(idOrden);
      dfe.setIddatosFichasExamenes(dfei);
      dfe.setDfeFechacreacion(BiosLisCalendarService.getInstance().getTS());
      dfe.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_INGRESADO);
      if (derivador != null) {
        dfe.setDfeIdderivador(derivador.getCderivIdderivador());
      }
      dfe.setDfeExamenesurgente("");
      dfe.setDfeIdusuario(0);
      sesion.beginTransaction();
      sesion.save(dfe);
      sesion.getTransaction().commit();
      sesion.close();

    } catch (BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("No se pudo agregar examen");
    } finally {

      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void insertDatosFichasExamenes(int idOrden, int idExamen, Session sesion) throws BiosLISDAOException {

    DatosFichasexamenes dfe = new DatosFichasexamenes();
    DatosFichasexamenesId dfei = new DatosFichasexamenesId();

    CfgExamenes examen;
    Timestamp currenTs = BiosLisCalendarService.getInstance().getTS(sesion);
    try {
      logger.debug("Se busca examen con id:{}", idExamen);
      examen = cfgExamenesDAO.getExamenById(idExamen, sesion);
      CfgDerivadores derivador = examen.getCfgDerivadores();

      dfei.setDfeIdexamen(idExamen);
      dfei.setDfeNorden(idOrden);
      dfe.setIddatosFichasExamenes(dfei);
      dfe.setDfeFechacreacion(currenTs);
      dfe.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_INGRESADO);
      if (derivador != null) {
        dfe.setDfeIdderivador(derivador.getCderivIdderivador());
      }
      dfe.setDfeExamenesurgente("");
      dfe.setDfeIdusuario(0);
      sesion.save(dfe);

    } catch (BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("No se pudo agregar examen");
    }
  }

  public List<Object[]> selectExamenesPorOrden(int nOrden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      sesion.beginTransaction();
      Query query = sesion.createSQLQuery(
          "select datos_fichasexamenes.dfe_idexamen,cfg_examenes.ce_codigoexamen,cfg_examenes.ce_descripcion"
              + " from datos_fichasexamenes" + " inner join cfg_examenes on"
              + "    cfg_examenes.ce_idexamen=datos_fichasexamenes.dfe_idexamen" + " where dfe_norden = :nOrden");
      query.setParameter("nOrden", nOrden);
      List<Object[]> lista = query.list();
      sesion.getTransaction().commit();
      sesion.close();
      return lista;
    } catch (Exception e) {
      throw e;
    }
  }

  public void BorrarExamenesDatosFichas(int nOrden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      sesion.beginTransaction();
      Query query = sesion
          .createSQLQuery("DELETE" + " FROM" + "    datos_fichasexamenes" + " WHERE" + "    dfe_norden =:nOrden");
      query.setParameter("nOrden", nOrden);
      query.executeUpdate();
      sesion.getTransaction().commit();
      sesion.close();
      // executeUpdate
    } catch (Exception e) {
      throw e;
    }
  }

  public List<BigDecimal> getDatosFichasexamenesByNroOrdenMalo(long NroOrden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion
        .createSQLQuery("SELECT DFE_IDEXAMEN " + "from DATOS_FICHASEXAMENES " + "WHERE DFE_NORDEN = :nroOrden");
    query.setParameter("nroOrden", NroOrden);
    List<BigDecimal> listaFichas = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaFichas;
  }

  public List<Object[]> getDatosFichasexamenesByNroOrdenMalo2(int NroOrden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createSQLQuery("SELECT DFE_IDEXAMEN, datos_fichasexamenestest.DFET_IDENVASE "
        + "from DATOS_FICHASEXAMENES "
        + "LEFT JOIN datos_fichasexamenestest on datos_fichasexamenestest.dfet_norden = DATOS_FICHASEXAMENES.dfe_norden and datos_fichasexamenestest.dfet_idexamen = DATOS_FICHASEXAMENES.dfe_idexamen "
        + "WHERE DFE_NORDEN = :nroOrden");
    query.setParameter("nroOrden", NroOrden);
    List<Object[]> listaFichas = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaFichas;
  }

  public List<DatosFichasexamenes> getDatosFichasexamenesByNroOrden(long NroOrden) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion.createQuery("select dfe " + "from com.grupobios.bioslis.entity.DatosFichasexamenes dfe "
        + "where dfe.IddatosFichasExamenes.dfeNorden = :nroOrden");
    query.setParameter("nroOrden", NroOrden);
    List<DatosFichasexamenes> listaFichas = query.list();
    sesion.getTransaction().commit();
    sesion.close();
    return listaFichas;
  }

  public void updateByMail(Long nroOrden, SendMailDTO sendMailDto) {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    int n = sendMailDto.getLstIdExamenes().length;
    
    //si se envia resultado x paciente
    if (sendMailDto.getTipoDeEnvio() == 1) {
        for (int i = 0; i < n; i++) {
            DatosFichasexamenesId dfeID = new DatosFichasexamenesId();
            dfeID.setDfeIdexamen(Long.parseLong(sendMailDto.getLstIdExamenes()[i]));
            dfeID.setDfeNorden(nroOrden);
            DatosFichasexamenes dfe = (DatosFichasexamenes) sesion.load(DatosFichasexamenes.class, dfeID);
            if (dfe.getDfeImpreso().equals("N")) {
                dfe.setDfeEnviadoporemail("S");
                dfe.setDfeFechaemail(Calendar.getInstance().getTime());
                dfe.setDfeIdusuarioemail(1L);
                dfe.setDfeExamenretirado("S");
                dfe.setDfeFecharetiraexamen(Calendar.getInstance().getTime());
                dfe.setDfeImpreso("S");
                dfe.setDfeNumerocopias((short)i);
                sesion.merge(dfe);
			}

          }
	}
    //si se envia resultado x servicio
    if (sendMailDto.getTipoDeEnvio() == 2) {
        for (int i = 0; i < n; i++) {
            DatosFichasexamenesId dfeID = new DatosFichasexamenesId();
            dfeID.setDfeIdexamen(Long.parseLong(sendMailDto.getLstIdExamenes()[i]));
            dfeID.setDfeNorden(nroOrden);
            DatosFichasexamenes dfe = (DatosFichasexamenes) sesion.load(DatosFichasexamenes.class, dfeID);
            if (dfe.getDfeImpreso().equals("N")) {
	            dfe.setDfePdfenviado("S");
	            dfe.setDfePdffechaenvio(Calendar.getInstance().getTime());
	            dfe.setDfeImpreso("S");
	            dfe.setDfeNumerocopias((short)i);
	            sesion.merge(dfe);
            }
          }
	}


    sesion.getTransaction().commit();
    sesion.close();
  }
  
  public void updateByImprimir(Long nroOrden, SendMailDTO sendMailDto) {

	    Session sesion = HibernateUtil.getSessionFactory().openSession();
	    sesion.beginTransaction();
	    int n = sendMailDto.getLstIdExamenes().length;
	    
	    //si imprime o visualiza resultado
	    if (sendMailDto.getTipoDeEnvio() == 1) {
	        for (int i = 0; i < n; i++) {
	            DatosFichasexamenesId dfeID = new DatosFichasexamenesId();
	            dfeID.setDfeIdexamen(Long.parseLong(sendMailDto.getLstIdExamenes()[i]));
	            dfeID.setDfeNorden(nroOrden);
	            DatosFichasexamenes dfe = (DatosFichasexamenes) sesion.load(DatosFichasexamenes.class, dfeID);
	            if (dfe.getDfeImpreso().equals("N")) {
		            dfe.setDfeIdusuarioimprime(1L);
		            dfe.setDfeFechaimpresion(Calendar.getInstance().getTime());
		            dfe.setDfeExamenretirado("S");
		            dfe.setDfeFecharetiraexamen(Calendar.getInstance().getTime());
		            dfe.setDfeImpreso("S");
		            dfe.setDfeNumerocopias((short)i);
		            sesion.merge(dfe);
				}
	          }
		}
	    //si se guarde PDF
	    if (sendMailDto.getTipoDeEnvio() == 2) {
	        for (int i = 0; i < n; i++) {
	            DatosFichasexamenesId dfeID = new DatosFichasexamenesId();
	            dfeID.setDfeIdexamen(Long.parseLong(sendMailDto.getLstIdExamenes()[i]));
	            dfeID.setDfeNorden(nroOrden);
	            DatosFichasexamenes dfe = (DatosFichasexamenes) sesion.load(DatosFichasexamenes.class, dfeID);
	            if (dfe.getDfeImpreso().equals("N")) {
		            dfe.setDfePdfenviado("S");
		            dfe.setDfePdffechaenvio(Calendar.getInstance().getTime());
		            dfe.setDfeImpreso("S");
		            dfe.setDfeNumerocopias((short)i);
		            sesion.merge(dfe);
	            }
	          }
		}
	    //portal paciente
	    if (sendMailDto.getTipoDeEnvio() == 3) {
	        for (int i = 0; i < n; i++) {
	            DatosFichasexamenesId dfeID = new DatosFichasexamenesId();
	            dfeID.setDfeIdexamen(Long.parseLong(sendMailDto.getLstIdExamenes()[i]));
	            dfeID.setDfeNorden(nroOrden);
	            DatosFichasexamenes dfe = (DatosFichasexamenes) sesion.load(DatosFichasexamenes.class, dfeID);
	            if (dfe.getDfeImpreso().equals("N")) {
		            dfe.setDfeImpreso("S");
		           // dfe.setDfeWebfechaimpresion((String)Calendar.getInstance().getTime());
		            dfe.setDfeNumerocopias((short)i);
		            sesion.merge(dfe);
	            }
	          }
		}


	    sesion.getTransaction().commit();
	    sesion.close();
	  }


  public void updateDatosFichasExamenes(DatosFichasexamenes dfe) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      sesion.update(dfe);
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
  }

  public List<DatosFichasexamenes> getDatosFichasExamenesByMuestra(long idMuestra) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      String sqlQuery = "SELECT distinct dfe.* FROM datos_fichasexamenes dfe "
          + "LEFT JOIN datos_fichasexamenestest dfet on dfet.dfet_norden = dfe.dfe_norden and dfet.dfet_idexamen = dfe.dfe_idexamen "
          + "where dfet.dfet_idmuestra = :idMuestra";
      Query query = sesion.createSQLQuery(sqlQuery).addEntity(DatosFichasexamenes.class);
      query.setParameter("idMuestra", idMuestra);
      List<DatosFichasexamenes> dfe = query.list();
      return dfe;
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public boolean updateUrgenciaExamen(DatosFichasexamenes examen) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      sesion.beginTransaction();
      DatosFichasexamenes dfe = this.getDatosFichasexamenesById(examen.getIddatosFichasExamenes());
      dfe.setDfeExamenesurgente(examen.getDfeExamenesurgente());
      sesion.update(examen);
      sesion.getTransaction().commit();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      sesion.getTransaction().rollback();
      throw new BiosLISDAOException("No se pudo actualizar examen.");
    } finally {
      if (sesion != null) {
        sesion.close();
      }
    }
    return false;

  }

  public boolean updateExamen(DatosFichasexamenes examen) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      sesion.beginTransaction();
      DatosFichasexamenes dfe = this.getDatosFichasexamenesById(examen.getIddatosFichasExamenes());
      dfe.setDfeExamenesurgente(examen.getDfeExamenesurgente());
      sesion.update(dfe);
      sesion.getTransaction().commit();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      sesion.getTransaction().rollback();
      throw new BiosLISDAOException("No se pudo actualizar examen.");
    } finally {
      if (sesion != null) {
        sesion.close();
      }
    }
    return false;

  }

  private DatosFichasexamenes getDatosFichasexamenesById(DatosFichasexamenesId iddatosFichasExamenes)
      throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      Query qry = sesion.createQuery("select dfe from DatosFichasexamenes dfe where dfe.IddatosFichasExamenes =:dfeid");
      qry.setParameter("dfeid", iddatosFichasExamenes);
      DatosFichasexamenes res = (DatosFichasexamenes) qry.uniqueResult();
      return res;
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      sesion.getTransaction().rollback();
      throw new BiosLISDAOException("No se pudo recuperar examen.");
    } finally {
      if (sesion != null) {
        sesion.close();
      }
    }

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

  public List<DatosFichasexamenes> getAll() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      Query qry = sesion.createQuery("select dfe from DatosFichasexamenes dfe ");
      List<DatosFichasexamenes> res = (List<DatosFichasexamenes>) qry.list();
      return res;
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("No se pudo recuperar examen.");
    } finally {
      if (sesion != null) {
        sesion.close();
      }
    }

  }

  public DatosFichasexamenes getDatosFichasExamenesByExamenyOrden(long idExamen, long nOrden)
      throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      Query query = sesion.createQuery(_hqlSelectExamenByIdOrdenIdExamen);
      query.setLong("dfeIdexamen", idExamen);
      query.setLong("dfeNorden", nOrden);
      DatosFichasexamenes dfe = (DatosFichasexamenes) query.uniqueResult();
      return dfe;
    } catch (HibernateException he) {
      logger.error("No se pudo guardar condición para test {}", he.getLocalizedMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public Boolean readyToSign(Long nOrden) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      String sqlQuery = " Select dfet from DatosFichasexamenestest dfet "
          + "where dfet.id.dfetNorden = :dfetNorden and dfet.dfetIdestadoresultadotest not in (4,5)";
      Query query = sesion.createQuery(sqlQuery);
      query.setParameter("dfetNorden", nOrden);
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

  public void signExamen(Long dfeNorden, Long dfeIdexamen, Long idUsuario)
      throws BiosLISDAONotFoundException, BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<String> dfet = new ArrayList<String>();
    try {
      String sqlQuery = " select dfe from DatosFichasexamenes dfe "
          + "where dfe.IddatosFichasExamenes.dfeNorden = :dfeNorden and dfe.IddatosFichasExamenes.dfeIdexamen = :dfeIdexamen";
      sesion.beginTransaction();
      Query qry = sesion.createQuery(sqlQuery);
      qry.setLong("dfeNorden", dfeNorden);
      qry.setLong("dfeIdexamen", dfeIdexamen);
      DatosFichasexamenes dfe = (DatosFichasexamenes) qry.uniqueResult();
      // agregado por cristian para logeventos
      String codigoEstado = dfe.getDfeCodigoestadoexamen();

      DatosFichas df = new DatosFichas();
      df = (DatosFichas) sesion.get(DatosFichas.class, dfeNorden.intValue());
      // *******************
      if (dfe == null) {
        throw new BiosLISDAONotFoundException("No se encontró examen.");
      }

      sqlQuery = " select dfet.dfet_testfirmado from Datos_Fichasexamenestest dfet "
          + "where dfet.dfet_norden = :dfeNorden and dfet.dfet_idexamen =:dfeIdexamen and (dfet.dfet_idestadoresultadotest != 5 and dfet.dfet_idestadoresultadotest != 4)";

      qry = sesion.createSQLQuery(sqlQuery);
      qry.setLong("dfeNorden", dfeNorden);
      qry.setLong("dfeIdexamen", dfeIdexamen);
      dfet = qry.list();

      dfe.setDfeIdusuariofirma(idUsuario);
      dfe.setDfeFechafirma(BiosLisCalendarService.getInstance().getTS());

      if (dfet.size() != 0) {
        dfe.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE);

      } else {
        dfe.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_FIRMADO);
      }

      /*
       * if(dfe.getDfeCodigoestadoexamen().equals("F")) {
       * dfe.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE)
       * ; }else {
       * dfe.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_FIRMADO);
       * }
       */
      sesion.save(dfe);
      sesion.getTransaction().commit();

      if (!codigoEstado.equals(dfe.getDfeCodigoestadoexamen())) {
        LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
        LogEventosfichas lef = new LogEventosfichas();

        lef.setDatosFichas((int) dfe.getIddatosFichasExamenes().getDfeNorden());
        lef.setLefFechaorden(df.getDfFechaorden());
        lef.setLefIdpaciente(df.getDatosPacientes());
        lef.setLefNombretabla("DATOS_FICHASEXAMENES");
        lef.setLefIdexamen((int) dfe.getIddatosFichasExamenes().getDfeIdexamen());
        lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
        lef.setLefIdusuario(idUsuario.intValue());
        lef.setCfgEventos(2);
        lef.setLefNombrecampo("DFE_CODIGOESTADOEXAMEN");

        String estado = "";
        qry = sesion.createSQLQuery(
            "Select cee.cee_descripcionestadoexamen from cfg_estadosexamenes cee where cee.cee_codigoestadoexamen = :id");
        qry.setParameter("id", dfe.getDfeCodigoestadoexamen());
        estado = (String) qry.uniqueResult();
        lef.setLefValornuevo(estado);
        qry = sesion.createSQLQuery(
            "Select cee.cee_descripcionestadoexamen from cfg_estadosexamenes cee where cee.cee_codigoestadoexamen = :id");
        qry.setParameter("id", codigoEstado);
        estado = (String) qry.uniqueResult();
        lef.setLefValoranterior(estado);
        sesion.close();
        logEventosfichasDAO.insertLogEventosFichas(lef);
      } else {
        sesion.close();
      }

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("Error hibernate al  firmar  examen.");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();

      }
    }
  }

  public List<DatosFichasexamenes> getDatosFichasExamenesByIdOrdenExamen(List<ExamenOrdenDTO> listaExamenes)
      throws BiosLISDAONotFoundException, BiosLISDAOException {

    List<DatosFichasexamenes> lstResultados = new ArrayList<>();
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {
      for (ExamenOrdenDTO examenOrdenDTO : listaExamenes) {
        Query qry = sesion.createQuery(_hqlSelectExamenByIdOrdenIdExamen);
        qry.setLong("dfeNorden", examenOrdenDTO.getDFE_NORDEN().longValue());
        qry.setLong("dfeIdexamen", examenOrdenDTO.getDFE_IDEXAMEN().longValue());
        DatosFichasexamenes dfe = (DatosFichasexamenes) qry.uniqueResult();

        if (dfe != null) {
          lstResultados.add(dfe);
        } else {
          logger.debug("No se encontró examen con orden {} y examen {}", examenOrdenDTO.getDFE_NORDEN().longValue(),
              examenOrdenDTO.getDFE_IDEXAMEN().longValue());
          throw new BiosLISDAONotFoundException("No se encontró examen.");
        }
      }
    } catch (HibernateException he) {
      logger.error("Error hibernate {}", he.getMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return lstResultados;
  }

  private String _hqlSelectExamenByIdOrdenIdExamen = " select dfe from DatosFichasexamenes dfe "
      + "where dfe.IddatosFichasExamenes.dfeNorden = :dfeNorden and dfe.IddatosFichasExamenes.dfeIdexamen = :dfeIdexamen";

  public void updateEstados(List<DatosFichasexamenes> lstDatosFichasexamenes) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();
      for (DatosFichasexamenes datosFichasexamenes : lstDatosFichasexamenes) {
    	if (datosFichasexamenes.getDfeCodigoestadoexamen().equals("A")) {
    		//disponibilidad web
    		datosFichasexamenes.setDfeWebdisponible("S");
    	    Calendar calendar = Calendar.getInstance();
    	    calendar.setTime(new Date());
    	    calendar.add(Calendar.HOUR_OF_DAY, 1);
    	    Date datePlusTiempo = calendar.getTime();
    		datosFichasexamenes.setDfeWebdisponiblefecha(datePlusTiempo);
    		//fecha de impresion de informes null
    		datosFichasexamenes.setDfeFechaimpresion(null);
    		datosFichasexamenes.setDfeImpreso("N");
    		datosFichasexamenes.setDfeIdusuarioimprime(null);
		}
        sesion.update(datosFichasexamenes);
      }
      sesion.getTransaction().commit();
    } catch (HibernateException he) {
      logger.error(he.getLocalizedMessage());
      throw new BiosLISDAOException(he.getMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  public void insertDatosFichasExamenes(int nOrden, CfgExamenesDTO examenDto, Long idUsuario)
      throws BiosLISDAOException {
    DatosFichasexamenes dfe = new DatosFichasexamenes();
    DatosFichasexamenesId dfei = new DatosFichasexamenesId();
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    CfgExamenes examen;
    try {
      logger.debug("Se busca examen con id:{}", examenDto.getCeIdexamen());
      examen = cfgExamenesDAO.getExamenById(examenDto.getCeIdexamen());
      CfgDerivadores derivador = examen.getCfgDerivadores();

      dfei.setDfeIdexamen(examenDto.getCeIdexamen());
      dfei.setDfeNorden(nOrden);
      dfe.setIddatosFichasExamenes(dfei);
      dfe.setDfeFechacreacion(BiosLisCalendarService.getInstance().getTS());
      dfe.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_INGRESADO);
      dfe.setDfeIdderivador(derivador.getCderivIdderivador());
      dfe.setDfeExamenesurgente(examenDto.getCeEsurgente());
      dfe.setDfeIdusuario(idUsuario);

      sesion.beginTransaction();
      sesion.save(dfe);
      sesion.getTransaction().commit();
      sesion.close();
      // se elimino por cristian 24-02 codigo de trazabilidad antigua

    } catch (BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("No se pudo agregar examen");
    } finally {

      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void unsignExamen(long dfeNorden, long dfeIdexamen) {
    // TODO Auto-generated method stub

  }

  private String sql = "SELECT DFE_NORDEN, DFE_IDEXAMEN, DFE_SORT, DFE_FECHACREACION, DFE_IDUSUARIO, DFE_CODIGOESTADOEXAMEN, DFE_IMPRESO, DFE_AUTORIZADO, DFE_LISTATRABAJOGENERADA, DFE_LISTATRABAJONUMERO, DFE_LISTATRABAJOFECHA, DFE_IDUSUARIOLISTATRABAJO, DFE_FECHAENTREGA, DFE_IDUSUARIOFIRMA, DFE_FECHAFIRMA, DFE_IDUSUARIOIMPRIME, DFE_FECHAIMPRESION, DFE_NUMEROCOPIAS, DFE_NOTAINTERNA, DFE_NOTAEXAMEN, DFE_NOTAINFORME, DFE_NOTA, DFE_IDBACESTADO, DFE_BACFECHAINICIO, DFE_BACFECHARESULTADO, DFE_BACLIS, DFE_BACLISFECHA, DFE_CANTIDAD, DFE_EXAMENRETIRADO, DFE_RUTRETIRAEXAMEN, DFE_NOMBRERETIRAEXAMEN, DFE_FECHARETIRAEXAMEN, DFE_IDUSUARIOAUTORIZA, DFE_FECHAAUTORIZA, DFE_ENVIADOPOREMAIL, DFE_IDUSUARIOEMAIL, DFE_FECHAEMAIL, DFE_IDDERIVADOR, DFE_ENVIADOAHOST, DFE_FECHAENVIADOAHOST, DFE_WEBDISPONIBLE, DFE_WEBDISPONIBLEFECHA, DFE_PDFENVIADO, DFE_PDFFECHAENVIO, DFE_TIENEPREINFORME, DFE_PDFENVIAREPROCESO, DFE_WEBFECHAIMPRESION, DFE_IDUSUARIODT, DFE_RECHAZOEXAMEN, DFE_IDUSUARIORECHAZO, DFE_EXAMENESURGENTE, DFE_BACFECHAINICIO2\r\n"
      + "FROM BIOSLIS.DATOS_FICHASEXAMENES  WHERE DFE_NORDEN=:dfeNorden and DFE_IDEXAMEN=:dfeIdexamen ";

  public void autorizar(List<ExamenOrdenDTO> listaExamenes, Long idUsuario) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query qry = sesion.createSQLQuery(sqlAutoriza);
      sesion.beginTransaction();
      for (ExamenOrdenDTO datosFichasexamenes : listaExamenes) {
        qry.setInteger("dfeNorden", datosFichasexamenes.getDFE_NORDEN().intValueExact());
        qry.setInteger("dfeIdexamen", datosFichasexamenes.getDFE_IDEXAMEN().intValueExact());
        qry.setLong("idUsuario", idUsuario);
        qry.executeUpdate();
      }
      sesion.getTransaction().commit();

      sesion.close();
      for (ExamenOrdenDTO datosFichasexamenes : listaExamenes) {
        DatosFichas ordenPaciente = new DatosFichas();
        sesion = HibernateUtil.getSessionFactory().openSession();

        ordenPaciente = (DatosFichas) sesion.get(DatosFichas.class, datosFichasexamenes.getDFE_NORDEN().intValue());
        sesion.close();
        LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
        LogEventosfichas lef = new LogEventosfichas();

        lef.setDatosFichas(ordenPaciente.getDfNorden());
        lef.setLefFechaorden(ordenPaciente.getDfFechaorden());
        lef.setLefIdpaciente(ordenPaciente.getDatosPacientes());
        lef.setLefNombretabla("DATOS_FICHASEXAMENES");
        lef.setLefIdexamen(datosFichasexamenes.getDFE_IDEXAMEN().intValueExact());
        lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
        lef.setLefIdusuario(idUsuario.intValue());
        lef.setCfgEventos(2);
        lef.setLefNombrecampo("DFE_CODIGOESTADOEXAMEN");
        lef.setLefValoranterior("FIRMADO");
        lef.setLefValornuevo("AUTORIZADO");
        logEventosfichasDAO.insertLogEventosFichas(lef);
      }
    } catch (HibernateException he) {
      logger.error(he.getLocalizedMessage());
      throw new BiosLISDAOException(he.getMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }

    }
  }

  private String sqlAutoriza = "UPDATE BIOSLIS.DATOS_FICHASEXAMENES  "
      + " SET DFE_AUTORIZADO = 'S', DFE_IDUSUARIOAUTORIZA = :idUsuario , DFE_FECHAAUTORIZA = SYSDATE "
      + "WHERE DFE_NORDEN=:dfeNorden and DFE_IDEXAMEN=:dfeIdexamen ";

  public void insertDatosFichasExamenes(int nOrden, CfgExamenesDTO examenDto, Long idUsuario, Session sesion)
      throws BiosLISDAOException {
    DatosFichasexamenes dfe = new DatosFichasexamenes();
    DatosFichasexamenesId dfei = new DatosFichasexamenesId();

    CfgExamenes examen;
    try {
      logger.debug("Se busca examen con id:{}", examenDto.getCeIdexamen());
      examen = cfgExamenesDAO.getExamenById(examenDto.getCeIdexamen(), sesion);
      CfgDerivadores derivador = examen.getCfgDerivadores();

      dfei.setDfeIdexamen(examenDto.getCeIdexamen());
      dfei.setDfeNorden(nOrden);
      dfe.setIddatosFichasExamenes(dfei);
      dfe.setDfeFechacreacion(BiosLisCalendarService.getInstance().getTS(sesion));
      dfe.setDfeCodigoestadoexamen(EstadosSistema.DFE_CODIGOESTADOEXAMEN_INGRESADO);
      dfe.setDfeIdderivador(derivador.getCderivIdderivador());
      dfe.setDfeExamenesurgente(examenDto.getCeEsurgente());
      dfe.setDfeIdusuario(idUsuario);
      dfe.setDfeImpreso("N");
      dfe.setDfeAutorizado("N");
      dfe.setDfeBaclis("N");
      dfe.setDfeExamenretirado("N");
      dfe.setDfeExamenAnulado("N");
      dfe.setDfePdfenviado("N");
      dfe.setDfeWebdisponible("N");
      dfe.setDfeEnviadoahost("N");
      dfe.setDfeExamenretirado("N");
      dfe.setDfeListatrabajogenerada("N");
      dfe.setDfeEnviadoporemail("N");
      dfe.setDfeCantidad((byte) 1);
      dfe.setDfeNumerocopias((short) 0);
      //dfe.setDfeIdderivador((short) 0);

//      sesion.beginTransaction();
      sesion.save(dfe);
//      sesion.getTransaction().commit();
//      sesion.close();
      // se elimino por cristian 24-02 codigo de trazabilidad antigua

    } catch (BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("No se pudo agregar examen");
    } finally {

//      if (sesion.isOpen()) {
//        sesion.close();
//      }
    }
  }

}
