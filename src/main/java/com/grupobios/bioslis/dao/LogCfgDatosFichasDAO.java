/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.entity.LogCfgtablas;
import com.grupobios.bioslis.entity.LogEventosfichas;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import org.hibernate.Session;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import static org.javers.core.JaversBuilder.logger;
import org.javers.core.diff.Diff;
import static org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE;

import org.javers.core.diff.changetype.ValueChange;

/**
 *
 * @author Jan Perkov
 */
public class LogCfgDatosFichasDAO {

    public void insertTablasLogUpdate(String tablaUso) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
           
            LogCfgtablas logCfg = new LogCfgtablas();
          
            sesion.beginTransaction();
            logCfg.setLctNombretabla(tablaUso);

            sesion.save(logCfg);
            sesion.getTransaction().commit();
            sesion.close();
        } catch (HibernateException he) {
            logger.error("error en insertTablasLogUpdate "+he.getMessage());   
             } finally {
               if (sesion != null && sesion.isOpen()) {
                 sesion.close();
               }
             }
    }


    public void comparadorObjetos(Object sinCambios, Object conCambios, String tablaUso, int accion, int usuario, String nombreEquipo, int PK, String ip, Date fechaOrden, String[] examenes, int idPaciente) {
        try {
            //med, updateMed, "Cfg_Medicos", 2, 1, "Equipo", updateMed.getCmIdmedico(), ip
            //Definir javers
            Javers javers = JaversBuilder.javers()
                    .withListCompareAlgorithm(LEVENSHTEIN_DISTANCE)
                    .build();

            //hacer comparacion entre arreglos y convertilo a array
            Diff diff = javers.compare(sinCambios, conCambios);
            ArrayList<ValueChange> diffs = (ArrayList) diff.getChangesByType(ValueChange.class);
            String campo;
            String sinCambio;
            String conCambio;
            //recorrer array 
            for (ValueChange v : diffs) {
                //Insertar campo cambiado
                campo = v.getPropertyName();
                //Insertar antes del cambio
                if (v.getLeft() == null) {
                    sinCambio = null;
                } else {
                    sinCambio = v.getLeft().toString();
                }
                if (v.getRight() == null) {
                    conCambio = null;
                } else {
                    conCambio = v.getRight().toString();
                }
                //Insertar despues del cambio
                //AGREGAR SET ACA Y GUARDAR
                insertLog(campo, sinCambio, conCambio, tablaUso, accion, usuario, nombreEquipo, PK, ip, fechaOrden, tablaUso, examenes, idPaciente);
            }

            // change.getPropertyName().equalsIgnoreCase(anotherString);
        } catch (HibernateException he) {
            logger.error("error en comparadorObjetos "+he.getMessage());   
             } 
    }

    public void insertLog(String campo, String sinCambios, String conCambios, String tablaUso, int accion, int usuario, String nombreEquipo, int PK, String ip, Date fechaOrden, String NombreTabla, String[] examenes, int idPaciente) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        LogEventosfichas log = new LogEventosfichas();
        log.setLefIdlogeventoficha(Long.valueOf(getLastIdLog()));
        try {

           // SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());

            if (sinCambios == null) {
                sinCambios = "";
            }
            if (conCambios == null) {
                conCambios = "";
            }
            if (campo.length() > 5) {
                campo = campo.substring(0, 2) + "_" + campo.substring(2, campo.length());
            }

          

            log.setDatosFichas(PK);
            log.setCfgEventos(accion);
            log.setLefFechaorden(fechaOrden);
            log.setLefFecharegistro(date);
            log.setLefNombretabla(nombreEquipo);
            log.setLefIdpaciente(idPaciente);
           // log.setLefIdtest(idPaciente);
            log.setLefIdusuario(usuario);
            log.setLefIpestacion(ip);
            log.setLefNombrecampo(campo);
            log.setLefValoranterior(sinCambios);
            log.setLefValornuevo(conCambios);

           // 
            sesion.beginTransaction();
            sesion.save(log);
            sesion.getTransaction().commit();
            sesion.close();
        } catch (HibernateException he) {
            logger.error("error en insertLog "+he.getMessage());   
             } finally {
               if (sesion != null && sesion.isOpen()) {
                 sesion.close();
               }
             }
    }

    public int getLastIdLog() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        
        Query query = sesion.createQuery("select med.lefIdlogeventoficha from com.grupobios.bioslis.entity.LogEventosfichas med "
                + "order by med.lefIdlogeventoficha desc").setMaxResults(1);
        int indice;
        try {
            indice = ((int) query.list().get(0));
        } catch (HibernateException he) {
            logger.error("error en getLastIdLog "+he.getMessage());   
            indice = 1;
        } finally {
            if (sesion != null && sesion.isOpen()) {
              sesion.close();
            }     
        }
        return indice + 1;
    }

}
