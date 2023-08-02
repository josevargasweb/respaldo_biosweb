package com.grupobios.bioslis.dao;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.DatosPacientes;

import com.grupobios.bioslis.entity.LogPacientes;

public class LogPacientesDAO {

    private static final Logger logger = LogManager.getLogger(LogPacientesDAO.class);
    

  public void insertLogPaciente(String campo, String conCambios, String sinCambios, int accion, int usuario,
      String nombreEquipo, DatosPacientes dp, String ip) {
    Session session = HibernateUtil.getSessionFactory().openSession();
   String[] ipLocal = new String[2];

    try {
        InetAddress address = InetAddress.getLocalHost();
            
                   
        // IP en formato String
        String paso = address.toString();
        ipLocal =paso.split("/");
   
        
    } catch (UnknownHostException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
    try {
      Date date = new Date(System.currentTimeMillis());
      if (sinCambios == null) {
        sinCambios = "";
      }
      if (conCambios == null) {
        conCambios = "";
      }
      LogPacientes lp = new LogPacientes();    
    
      lp.setLpCampomodificado(campo);
      lp.setLpFechamodificacion(date);
      lp.setLpIdacciondato(accion);
      lp.setLpIdusuario(usuario);
      lp.setLpNombreequipo(ipLocal[0]);
      lp.setLpValoranterior(sinCambios);
      lp.setLpValornuevo(conCambios);
      lp.setLpIpusuario(ipLocal[1]);
      lp.setDatosPacientes(dp);
      
      session.beginTransaction();
      session.save(lp);
      session.getTransaction().commit();
      session.close();
    } catch (HibernateException he) {
    	if (session != null && session.isOpen()) {
            session.close();
          }
        logger.error("error en insertLogPaciente "+he.getMessage());   
     } finally {
           if (session != null && session.isOpen()) {
             session.close();
           }
     }
  }

  @SuppressWarnings("unchecked")
public List<LogPacientes> getLogByDate(Date fecha) {

    Calendar cal = Calendar.getInstance();
    cal.setTime(fecha);
    cal.add(Calendar.DAY_OF_MONTH, 1);
    List<LogPacientes> listaLogPacientes = null;
    Session sesion = HibernateUtil.getSessionFactory().openSession();
   
try {
    Query query = sesion.createQuery("select lp " + "from LogPacientes lp " + "left join fetch lp.datosPacientes dp "
        + "where lp.lpFechamodificacion BETWEEN :fini  and :ffin  AND dp.ldvTiposdocumentos = 5");

    query.setDate("fini", fecha);
    query.setDate("ffin", cal.getTime());

    listaLogPacientes = query.list();
    sesion.close();
} catch (HibernateException he) {
	if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    logger.error("error en getLogByDate "+he.getMessage());   
 } finally {
       if (sesion != null && sesion.isOpen()) {
         sesion.close();
       }
  }
    return listaLogPacientes;

  }

 
@SuppressWarnings("unchecked")
public LogPacientes getIngresoByPaciente(DatosPacientes dp) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<LogPacientes> listaLog = new ArrayList<LogPacientes>();
    try {
        Query query = sesion.createQuery(
            "select lp " + "from com.grupobios.bioslis.entity.LogPacientes lp " + "left join fetch lp.datosPacientes dp "
                + "where lp.lpIdacciondato = 1 and " + "lp.datosPacientes = :datosPacientes");
        query.setParameter("datosPacientes", dp);
         listaLog = query.list();
        sesion.close();
    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
        logger.error("error en getIngresoByPaciente "+he.getMessage());   
     } finally {
       if (sesion != null && sesion.isOpen()) {
         sesion.close();
       }
     }    
    
    if (!listaLog.isEmpty()) {
      return listaLog.get(0);
    }else {
        throw new BiosLISDAOException("Paciente no se encuentra");
    }    
  }

}
