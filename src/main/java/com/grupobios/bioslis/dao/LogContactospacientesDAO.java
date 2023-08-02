/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Nacho
 */
public class LogContactospacientesDAO {
    
    private static Logger logger = LogManager.getLogger(LogContactospacientesDAO.class);

//    public void comparadorObjetos(DatosContactos sinCambios, DatosContactos conCambios, int accion, int usuario, String nombreEquipo, DatosContactos dc, String ip) {
//        try {
//            //Definir javers
//            Javers javers = JaversBuilder.javers()
//                    .withListCompareAlgorithm(LEVENSHTEIN_DISTANCE)
//                    .build();
//
//            //hacer comparacion entre arreglos y convertilo a array
//            Diff diff = javers.compare(sinCambios, conCambios);
//            ArrayList<ValueChange> diffs = (ArrayList) diff.getChangesByType(ValueChange.class);
//            String campo;
//            String sinCambio;
//            String conCambio;
//            //recorrer array 
//            for (ValueChange v : diffs) {
//                //Insertar campo cambiado
//                campo = v.getPropertyName();
//                String[] campoSerarado = campo.split("(?=[A-Z])");
//                String nuevoCampo = campoSerarado[0] + "_" + campoSerarado[1];
//                //Insertar antes del cambio
//                if (v.getLeft() == null) {
//                    sinCambio = null;
//                } else {
//                    sinCambio = v.getLeft().toString();
//                }
//                if (v.getRight() == null) {
//                    conCambio = null;
//                } else {
//                    conCambio = v.getRight().toString();
//                }
//                //Insertar despues del cambio
//                //AGREGAR SET ACA Y GUARDAR
//                this.insertLogContactosPacientes(nuevoCampo.toUpperCase(), conCambio, sinCambio, accion, usuario, nombreEquipo, dc, ip);
//            }
//            // change.getPropertyName().equalsIgnoreCase(anotherString);
//        } catch (Exception e) {
//            throw e;
//        }
//    }

//    public void insertLogContactosPacientes(String campo, String conCambios, String sinCambios, int accion, int usuario, String nombreEquipo, DatosContactos dc, String ip) {
//        Date date = new Date(System.currentTimeMillis());
//        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
//        String fechaHoy = dateFormat.format(date);
//        if (sinCambios == null) {
//            sinCambios = "";
//        }
//        if (conCambios == null) {
//            conCambios = "";
//        }
//        LogContactospacientes lcp = new LogContactospacientes();
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//        lp.setLpCampomodificado(campo);
//        lp.setLpFechamodificacion(date);
//        lp.setLpIdacciondato(accion);
//        lp.setLpIdusuario(usuario);
//        lp.setLpNombreequipo(nombreEquipo);
//        lp.setLpValoranterior(conCambios);
//        lp.setLpValornuevo(sinCambios);
//        lp.setLpIpusuario(ip);
//        lp.setDatosPacientes(dc);
//        long indice = this.getNewIdLogContactosPacientes();
//        lp.setLpId(indice);
//        session.save(lp);
//        session.getTransaction().commit();
//        session.close();
//    }

    public long getNewIdLogContactosPacientes() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
      
        Query query = sesion.createQuery("select lcp.lcpIdlogcontactopaciente from com.grupobios.bioslis.entity.LogContactospacientes lcp "
                + "order by lcp.lcpIdlogcontactopaciente desc").setMaxResults(1);
        long indice;
        try {
            indice = ((int) query.list().get(0)) + 1;
        } catch (HibernateException he) {
            logger.error("error en getNewIdLogContactosPacientes "+he.getMessage()); 
            indice = 1;
        } finally {
            if (sesion != null && sesion.isOpen()) {
              sesion.close();
            }
        }
        return indice;
    }

}
