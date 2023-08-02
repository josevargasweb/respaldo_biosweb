/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.common.Constante;
import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.CfgMedicos;
import com.grupobios.bioslis.entity.LogCfgtablas;

import java.util.logging.Level;

public class CfgMedicosDAO {

  private static Logger logger = LogManager.getLogger(CfgMedicosDAO.class);

  LogCfgTablasDAO logTablas = new LogCfgTablasDAO();
  CfgInstitucionesDeSaludDAO institut = new CfgInstitucionesDeSaludDAO();
  LdvSexoDAO ldvS = new LdvSexoDAO();
  LdvEspecialidadesMedicasDAO especMed = new LdvEspecialidadesMedicasDAO();

  public CfgMedicos getMedicoByRut(String rutMedico) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createQuery(
            "select med " + "from com.grupobios.bioslis.entity.CfgMedicos med " + "where med.cmRun = :rutMedico");
        query.setParameter("rutMedico", rutMedico);
        List<CfgMedicos> listaMedicos = query.list();
        sesion.close();
        if (listaMedicos.size() == 0) {
          CfgMedicos paciente2 = new CfgMedicos();
          paciente2.setCmRun("0");
         
          return paciente2;
        }
        CfgMedicos paciente = listaMedicos.get(0);
       
        return this.nullToSpaceAll(paciente);

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

  public CfgMedicos getMedicoById(int idMedic) throws BiosLISDAOException {
    Session sesion = null;
    try {
        sesion = HibernateUtil.getSessionFactory().openSession();
        Query query = sesion.createQuery(
            "select med " + "from com.grupobios.bioslis.entity.CfgMedicos med " + "where med.cmIdmedico = :idMedic");
        query.setParameter("idMedic", idMedic);

        List<CfgMedicos> listaMedicos = query.list();
        CfgMedicos paciente = listaMedicos.get(0);
        sesion.close();
        return paciente;

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

  public CfgMedicos nullToSpaceAll(CfgMedicos medico) {

    if (medico.getCfgInstitucionesdesalud() == null) {
      medico.setCfgInstitucionesdesalud(0);
    }
    if (medico.getLdvEspecialidadesmedicas() == null) {
      medico.setLdvEspecialidadesmedicas(0);
    }
    if (medico.getLdvSexo() == null) {
      medico.setLdvSexo(0);
    }
    if (medico.getCmSegundoapellido() == null) {
      medico.setCmSegundoapellido("");
    }
    if (medico.getCmTelefono() == null) {
      medico.setCmTelefono("");
    }
    if (medico.getCmEmail() == null) {
      medico.setCmEmail("");
    }
    if (medico.getCmRegistrodesalud() == null) {
      medico.setCmRegistrodesalud("");
    }
    return medico;
  }

  public CfgMedicos nullToSpace(CfgMedicos medico) {

    if (medico.getCmSegundoapellido() == null) {
      medico.setCmSegundoapellido("");
    }
    if (medico.getCmTelefono() == null) {
      medico.setCmTelefono("");
    }
    if (medico.getCmEmail() == null) {
      medico.setCmEmail("");
    }
    if (medico.getCmRegistrodesalud() == null) {
      medico.setCmRegistrodesalud("");
    }
    return medico;
  }

  public List<CfgMedicos> getMedicoByNombreApellido(String nombre, String primerApellido, String segundoApellido, String rut, String activo) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    List<CfgMedicos> listaMedicos = null;
  
    try {
        /*
      if (!nombre.equals("") && !primerApellido.equals("")) {
        Query query = sesion.createQuery("select med " + "from com.grupobios.bioslis.entity.CfgMedicos med "
            + "where med.cmNombres like :nombre and " + "med.cmPrimerapellido like :primerApellido order by med.cmNombres ");
        query.setParameter("nombre", "%" + nombre.toUpperCase() + "%");
        query.setParameter("primerApellido", "%" + primerApellido.toUpperCase() + "%");
        listaMedicos = query.list();
      }
      if (!nombre.equals("") && !primerApellido.equals("") && !segundoApellido.equals("")) {
          Query query = sesion.createQuery("select med " + "from com.grupobios.bioslis.entity.CfgMedicos med "
              + "where med.cmNombres like :nombre and " + "med.cmPrimerapellido like :primerApellido and med.cmSegundoapellido like :segundoApellido"
                      + " order by med.cmNombres ");
          query.setParameter("nombre", "%" + nombre.toUpperCase() + "%");
          query.setParameter("primerApellido", "%" + primerApellido.toUpperCase() + "%");
          query.setParameter("segundoApellido", "%" + segundoApellido.toUpperCase() + "%");
          listaMedicos = query.list();
        }
      if (!nombre.equals("") && !segundoApellido.equals("") && primerApellido.equals("")) {
          Query query = sesion.createQuery("select med " + "from com.grupobios.bioslis.entity.CfgMedicos med "
              + "where med.cmNombres like :nombre and " + "med.cmSegundoapellido like :segundoApellido order by med.cmNombres" );
          query.setParameter("nombre", "%" + nombre.toUpperCase() + "%");
          query.setParameter("segundoApellido", "%" + segundoApellido.toUpperCase() + "%");
          listaMedicos = query.list();
        }
      
      if (nombre.equals("") && primerApellido.equals("") && !segundoApellido.equals("")) {
        Query query = sesion.createQuery("select med " + "from com.grupobios.bioslis.entity.CfgMedicos med "
            + "where med.cmSegundoapellido like :segundoApellido order by med.cmNombres");
        query.setParameter("segundoApellido", "%" + segundoApellido.toUpperCase() + "%");
        listaMedicos = query.list();
      }
      if (nombre.equals("") && segundoApellido.equals("") && !primerApellido.equals("") ) {
        Query query = sesion.createQuery("select med " + "from com.grupobios.bioslis.entity.CfgMedicos med "
            + "where med.cmPrimerapellido like :primerApellido order by med.cmNombres ");
        query.setParameter("primerApellido", "%" + primerApellido.toUpperCase() + "%");
        listaMedicos = query.list();
      }
      if (!nombre.equals("") && primerApellido.equals("") && segundoApellido.equals("") ) {
        Query query = sesion.createQuery(
            "select med " + "from com.grupobios.bioslis.entity.CfgMedicos med " + "where med.cmNombres like :nombre order by med.cmNombres");
        query.setParameter("nombre", "%" + nombre.toUpperCase() + "%");
        listaMedicos = query.list();
      }
      
      if (nombre.equals("") && primerApellido.equals("") && segundoApellido.equals("")  && rut.equals("") ) {
          Query query = sesion.createQuery(
              "select med " + "from com.grupobios.bioslis.entity.CfgMedicos med order by med.cmNombres " );         
          listaMedicos = query.list();
        }
   
      if (nombre.equals("") && primerApellido.equals("") && segundoApellido.equals("")  && !rut.equals("") ) {
          Query query = sesion.createQuery(
              "select med " + "from com.grupobios.bioslis.entity.CfgMedicos med where med.cmRun like :rutMedico order by med.cmNombres" );    
          query.setParameter("rutMedico", "%" + rut + "%");
          listaMedicos = query.list();
        }
      */
    	if (segundoApellido.equals("")) {
    		//segundoApellido = (segundoApellido.equals(""))?"":segundoApellido;
    		
		}

    	
    
    	
        String sqlFiltroMedicos = "select med" 
                + " from com.grupobios.bioslis.entity.CfgMedicos med"
                + " where med.cmNombres like :nombre" 
                + " and med.cmPrimerapellido like :primerApellido"

                + " and med.cmRun like :rutMedico";
        if (activo.equals(Constante.ESTADO_ACTIVO)){
            sqlFiltroMedicos += " and med.cmActivo = 'S'";
        }
        if (!segundoApellido.equals("")) {
        	sqlFiltroMedicos +=  " and med.cmSegundoapellido like :segundoApellido";
		}
        sqlFiltroMedicos += " order by med.cmNombres";
        Query query = sesion.createQuery(sqlFiltroMedicos);
        query.setParameter("nombre", "%" + nombre.toUpperCase() + "%");
        query.setParameter("primerApellido", "%" + primerApellido.toUpperCase() + "%");
        if (!segundoApellido.equals("")) {
        	query.setParameter("segundoApellido", "%" + segundoApellido.toUpperCase() + "%");
		}
        query.setParameter("rutMedico", "%" + rut + "%");
        listaMedicos = query.list();
        sesion.close();
        return listaMedicos;
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

  public void insertDatosMedicos(CfgMedicos med, Long idUsuario) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      
      sesion.beginTransaction();
      sesion.save(med);
      String id = String.valueOf(med.getCmIdmedico());
      
      
      //Se agregan medicos a log tablas **********************
      LogCfgtablas logTabla = new LogCfgtablas();
     
      logTabla.setCfgAccionesdatos(1);
      logTabla.setLctIdusuario(idUsuario.intValue());
      logTabla.setLctNombretabla("CFG_MEDICOS");                  
      logTabla.setLctIdtabla(med.getCmIdmedico());
      logTabla.setLctDescripcion(med.getCmRun());
      
      logTablas.insertLogTablas(logTabla, sesion);
      //*************************************************************
      
      
      
    
      sesion.getTransaction().commit();
      sesion.close();
      
    } catch (HibernateException  he) {
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

  public void updateDatosMedicos(CfgMedicos med, Long idUsuario) throws ParseException, BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
    	CfgMedicos medicoAntiguo = this.getMedicoById(med.getCmIdmedico());
      CfgMedicos updateMed = this.getMedicoById(med.getCmIdmedico());
     
      updateMed = this.nullToSpace(updateMed);
      med = this.nullToSpace(med);
     
    //  logTablas.comparadorObjetos(med, updateMed, "Cfg_Medicos", 2, 1, "Equipo", updateMed.getCmIdmedico(), ip);
      updateMed.setCfgInstitucionesdesalud(med.getCfgInstitucionesdesalud());
      updateMed.setCmActivo(med.getCmActivo());
      updateMed.setCmEmail(med.getCmEmail());
      updateMed.setCmHost(med.getCmHost());
      updateMed.setCmNombres(med.getCmNombres());
      // updateMed.setCmNombres("SDFJHSD");
      updateMed.setCmPrimerapellido(med.getCmPrimerapellido());
      updateMed.setCmSegundoapellido(med.getCmSegundoapellido());
      updateMed.setCmRegistrodesalud(med.getCmRegistrodesalud());
      updateMed.setCmRun(med.getCmRun());
      updateMed.setCmTelefono(med.getCmTelefono());
      // updateMed.setCmTelefono("123123213");
      updateMed.setLdvEspecialidadesmedicas(med.getLdvEspecialidadesmedicas());
      updateMed.setLdvSexo(med.getLdvSexo());
      updateMed.setCmRegistrodesalud(med.getCmRegistrodesalud());

      
      sesion.beginTransaction();
      sesion.update(updateMed);
      sesion.getTransaction().commit();
      sesion.close();
      
      logTablas.comparadorObjetos(medicoAntiguo, updateMed, 2, idUsuario.intValue() ,med.getCmIdmedico(), med.getCmRun(), null);
      
      
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

  public int getLastIdMedico() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    sesion.beginTransaction();
    Query query = sesion
        .createQuery(
            "select med.cmIdmedico from com.grupobios.bioslis.entity.CfgMedicos med " + "order by med.cmIdmedico desc")
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
  }

  public boolean ifMedicExist(String rut) throws BiosLISDAOException {
    CfgMedicos paciente2 = this.getMedicoByRut(rut);
    boolean a = !"0".equals(paciente2.getCmRun());
    return a;

  }

  public List<CfgMedicos> getAllMedics() {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query query = sesion.createQuery(
          "select med.cmIdmedico,med.cmNombres,med.cmPrimerapellido from com.grupobios.bioslis.entity.CfgMedicos med "+/*"where med.cmIdmedico <>0"+*/" order by med.cmIdmedico asc");
      List<CfgMedicos> listaMedics = query.list();
      sesion.close();
      return listaMedics;

    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
      logger.error(he.getMessage());
    } finally {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
    }
    return null;
  }
}
