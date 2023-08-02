/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.config.HibernateUtil;

import com.grupobios.bioslis.dto.DatosPacienteOrdenDTO;
import com.grupobios.bioslis.entity.CfgValoresreferencia;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.LdvSexo;

import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.LogCfgtablas;
import com.grupobios.common.Edad;


/**
 *
 * @author Nacho
 */
public class CfgValoresreferenciaDAO {

  private static Logger logger = LogManager.getLogger(CfgValoresreferenciaDAO.class);
  
  LogCfgTablasDAO logCfgTablasDAO;
  CfgTestDAO cfgTestDAO;


  public CfgTestDAO getCfgTestDAO() {
	return cfgTestDAO;
}

public void setCfgTestDAO(CfgTestDAO cfgTestDAO) {
	this.cfgTestDAO = cfgTestDAO;
}

public LogCfgTablasDAO getLogCfgTablasDAO() {
	return logCfgTablasDAO;
}

public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
	this.logCfgTablasDAO = logCfgTablasDAO;
}

public void insertValoresReferencias(CfgValoresreferencia cvr, Long idUsuario) throws BiosLISDAOException  {
	CfgTest test = new CfgTest();
	try {
		test =   cfgTestDAO.getTestById((int) cvr.getCvrIdtest());
	} catch (BiosLISDAONotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (BiosLISDAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
        sesion.beginTransaction();
        sesion.save(cvr);
      
        sesion.getTransaction().commit();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void updateValoresReferencias(CfgValoresreferencia cvr) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();
      sesion.update(cvr);
      sesion.getTransaction().commit();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void deleteValoresReferencias(CfgValoresreferencia cvr) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();
      sesion.delete(cvr);
      sesion.getTransaction().commit();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public List<CfgValoresreferencia> getValoresReferenciasByTest(long cvrIdtest) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<CfgValoresreferencia> listaValores = null;
    try {
      Query query = sesion.createQuery("from CfgValoresreferencia cvr " + "left join fetch cvr.cfgMetodos "
          + "where cvr.cvrIdtest = :idtest "
          + "order by cvr.cvrSexo asc, cvr.cfgMetodos.cmetIdmetodo asc, cvr.cvrAnosdesde asc, cvr.cvrMesesdesde asc, cvr.cvrDiasdesde asc");
      query.setParameter("idtest", cvrIdtest);
      listaValores = query.list();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaValores;
  }

  public CfgValoresreferencia getValorReferencia(BigDecimal idTest, BigDecimal idPaciente)
      throws BiosLISDAONotFoundException, BiosLISDAOException {

    List<CfgValoresreferencia> lstVReferencia = null;
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query qry = sesion.createQuery("select cvr from CfgValoresreferencia cvr where cvr.cvrIdtest = :idTest");
      qry.setBigDecimal("idTest", idTest);
      lstVReferencia = qry.list();
      if (lstVReferencia.isEmpty()) {
        sesion.close();
        throw new BiosLISDAONotFoundException("No se encontraron valores de referencia para el test");
      }
      qry = sesion.createQuery("select dp from DatosPacientes dp where dp.dpIdpaciente = :idPaciente");
      qry.setBigDecimal("idPaciente", idPaciente);
      DatosPacientes dp = (DatosPacientes) qry.uniqueResult();
      if (dp.getDpFnacimiento() == null) {
        return null;
      }
      Edad edadPaciente = Edad.getEdadActual(dp.getDpFnacimiento());
      qry = sesion.createQuery("select ldvs from LdvSexo ldvs where ldvs.ldvsIdsexo= :idSexoPaciente");
      qry.setInteger("idSexoPaciente", dp.getLdvSexo());
      LdvSexo ldvs = (LdvSexo) qry.uniqueResult();
      sesion.close();

      return this.getValorReferenciaxEdadySexo(lstVReferencia, edadPaciente, ldvs.getLdvsCodigo());
    } catch (HibernateException hex) {
      throw new BiosLISDAOException(hex.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen())
        sesion.close();
    }
  }

  public CfgValoresreferencia getValorReferenciaById(int idVr) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

        Query query = sesion.createQuery("from CfgValoresreferencia cvr "
            + "left join fetch cvr.cfgMetodos "
            + "where cvr.cvrIdvalorreferencia = :idVr");
        query.setParameter("idVr", idVr);
        CfgValoresreferencia cvr = (CfgValoresreferencia) query.uniqueResult();
        sesion.close();
        return cvr;

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public CfgValoresreferencia getValorReferenciaxEdadySexo(List<CfgValoresreferencia> lstVReferencia, Edad edadPaciente,
      String ldvsCodigo) {
    for (CfgValoresreferencia cfgValoresreferencia : lstVReferencia) {

      int aDesde = cfgValoresreferencia.getCvrAnosdesde();
      int aHasta = cfgValoresreferencia.getCvrAnoshasta();
      int mDesde = cfgValoresreferencia.getCvrMesesdesde();
      int mHasta = cfgValoresreferencia.getCvrMeseshasta();
      int dDesde = cfgValoresreferencia.getCvrDiasdesde();
      int dHasta = cfgValoresreferencia.getCvrDiashasta();

      Edad edadReferenciaDesde = new Edad(aDesde, mDesde, dDesde);
      Edad edadReferenciaHasta = new Edad(aHasta, mHasta, dHasta);

      if (edadPaciente.compareTo(edadReferenciaDesde) >= 0 && edadPaciente.compareTo(edadReferenciaHasta) < 0) {

        if (cfgValoresreferencia.getCvrSexo().equals(Constante.CVRAMBOSSEXOS)
            || (ldvsCodigo != null && ldvsCodigo.equals(cfgValoresreferencia.getCvrSexo()))) {
          return cfgValoresreferencia;
        }
      }
    }
    return null;
  }

  public CfgValoresreferencia getValorReferenciaPaciente(BigDecimal idTest, DatosPacienteOrdenDTO pacienteDto)
      throws BiosLISDAONotFoundException, BiosLISDAOException {
    List<CfgValoresreferencia> lstVReferencia = null;
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {
      Query qry = sesion.createQuery("select cvr from CfgValoresreferencia cvr where cvr.cvrIdtest = :idTest");
      qry.setBigDecimal("idTest", idTest);
      lstVReferencia = qry.list();
      if (lstVReferencia.isEmpty()) {
        sesion.close();
        throw new BiosLISDAONotFoundException("No se encontraron valores de referencia para el test");
      }
      Edad edadPaciente = Edad.getEdadActual(pacienteDto.getDP_FNACIMIENTO());
      sesion.close();

      return this.getValorReferenciaxEdadySexo(lstVReferencia, edadPaciente, pacienteDto.getLDVS_CODIGO());
    } catch (HibernateException hex) {
      throw new BiosLISDAOException(hex.getMessage());
    } finally {
      if (sesion != null && sesion.isOpen())
        sesion.close();
    }

  }

}
