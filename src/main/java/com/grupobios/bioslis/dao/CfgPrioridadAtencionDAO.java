/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.EnvaseDTO;
import com.grupobios.bioslis.entity.CfgPrioridadatencion;
import com.grupobios.bioslis.entity.LogCfgtablas;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class CfgPrioridadAtencionDAO {
    
    private static Logger logger = LogManager.getLogger(CfgPrioridadAtencionDAO.class);
    private static ValidacionMantenedor validador = new ValidacionMantenedor();

    public List<CfgPrioridadatencion> getPrior() throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = sesion.createQuery("select prior from com.grupobios.bioslis.entity.CfgPrioridadatencion prior"
            		+ " order by prior.cpaSort asc, prior.cpaDescripcion asc");
            List<CfgPrioridadatencion> lista = query.list();
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

    public String insertPrio(CfgPrioridadatencion prio, Long idUsuario) throws BiosLISDAOException {

        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "E";
        try {
        	if (validador.validarCodigo("CPA_DESCRIPCION", "CFG_PRIORIDADATENCION", prio.getCpaDescripcion())) {
                sesion.beginTransaction();
                sesion.save(prio);
                
                //Se agregan priorida de atencion a log tablas **********************
	            LogCfgtablas logTabla = new LogCfgtablas();
	            LogCfgTablasDAO log = new LogCfgTablasDAO();
	            logTabla.setCfgAccionesdatos(1);
	            logTabla.setLctIdusuario(idUsuario.intValue());
	            logTabla.setLctNombretabla("CFG_PRIORIDADATENCION");                  
	            logTabla.setLctIdtabla(prio.getCpaIdprioridadatencion());
	            logTabla.setLctDescripcion(prio.getCpaDescripcion());
	            
	            log.insertLogTablas(logTabla, sesion);
		        //*************************************************************
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
/*
    public byte getLastIdPrioAten() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {

            sesion.beginTransaction();
            Query query = sesion.createQuery("select med.cpaIdprioridadatencion from com.grupobios.bioslis.entity.CfgPrioridadatencion med "
                    + "order by med.cpaIdprioridadatencion desc").setMaxResults(1);
            byte indice;
            try {
                indice = ((byte) query.list().get(0));
            } catch (Exception e) {
                indice = 1;
            }
            sesion.getTransaction().commit();
            sesion.close();

            return (byte) (indice + 1);
        } catch (Exception e) {
            return 1;
        }
    }

    public List<CfgPrioridadatencion> getPrioAtencionDescripcion(String Descripcion) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {

            sesion.beginTransaction();
            Query query = sesion.createQuery("select med from com.grupobios.bioslis.entity.CfgPrioridadatencion med "
                    + " where med.cpaDescripcion = :Descripcion"
                    + " order by med.cpaIdprioridadatencion desc");
             query.setParameter("Descripcion", Descripcion);
            List<CfgPrioridadatencion> lista = query.list();
            sesion.getTransaction().commit();
            sesion.close();
            return lista;
        } catch (Exception e) {
           throw e;
        }
    }
    */
    public List<CfgPrioridadatencion> getPrioAtencionid(short id) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {

            sesion.beginTransaction();
            Query query = sesion.createQuery("select med from com.grupobios.bioslis.entity.CfgPrioridadatencion med "
                    + " where med.cpaIdprioridadatencion = :id"
                    + " order by med.cpaIdprioridadatencion desc");
             query.setParameter("id", id);
            List<CfgPrioridadatencion> lista = query.list();
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
        
    public List<CfgPrioridadatencion> getPrioridadesAtencionFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String descripcion = filtros.get("descripcion").toUpperCase();
        String activo = filtros.get("activo");
        try {
            String sqlPrioridadAtencion = "from CfgPrioridadatencion cpa"
                    + " where cpa.cpaDescripcion like :descripcion";
            if (activo.equals(Constante.ESTADO_ACTIVO)){
                sqlPrioridadAtencion += " and cpa.cpaActivo ='S'";
            }
            sqlPrioridadAtencion += " order by cpa.cpaSort asc, cpa.cpaDescripcion asc";
            Query query = sesion.createQuery(sqlPrioridadAtencion);
            query.setParameter("descripcion", "%" + descripcion + "%");
            List<CfgPrioridadatencion> lista = query.list();
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

    public String updatePrio(CfgPrioridadatencion prio, Long idUsuario) throws BiosLISDAOException {

    	CfgPrioridadatencion prioAntiguo = new CfgPrioridadatencion(); 	
    	prioAntiguo = this.getPrioridadAtencionById(prio.getCpaIdprioridadatencion());
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String mensaje = "E";
        try {
        	BigDecimal id = new BigDecimal(prio.getCpaIdprioridadatencion());
        	if (validador.validarxIdSinAbreviado("CPA_DESCRIPCION", "CPA_IDPRIORIDADATENCION", "CFG_PRIORIDADATENCION", prio.getCpaDescripcion(), id)) {
                sesion.beginTransaction();
                sesion.update(prio);
                sesion.getTransaction().commit();
                mensaje = "C";
                sesion.close();
                
                
	              
	            String base64Img =null;
	            String base64ImgAntiguo =null;
	            if (prio.getCpaIconoprioridad() != null) {
                    byte[] bdata= null;
					try {
						bdata = prio.getCpaIconoprioridad().getBytes(1, (int) prio.getCpaIconoprioridad().length());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
                    String base64Encoded;
					try {
						base64Encoded = new String(encodeBase64, "UTF-8");
						base64Img =base64Encoded;
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                  
                  
                }
                
               
                //  CfgEnvases envase = configuracionEnvasesService.getEnvaseById(idEnvase);
                  
                  if (prioAntiguo.getCpaIconoprioridad() != null) {
                      byte[] bdata= null;
						try {
							bdata = prioAntiguo.getCpaIconoprioridad() .getBytes(1, (int)prioAntiguo.getCpaIconoprioridad().length());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                      byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
                      String base64Encoded;
						try {
							base64Encoded = new String(encodeBase64, "UTF-8");
							base64ImgAntiguo = base64Encoded;
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    
                     
                  }
                  
                  
                  prio.setCpaIconoprioridad(null);
    	            prioAntiguo.setCpaIconoprioridad(null);
                  // aqui se agrega datos prioridad atencion en log tablas
                  LogCfgTablasDAO log = new LogCfgTablasDAO();
  	            log.comparadorObjetos(prioAntiguo, prio, 2, idUsuario.intValue(), prio.getCpaIdprioridadatencion(), prio.getCpaDescripcion(), null);  
  	            
  	           if( base64Img != null ) { 
  	        	   	base64Img = base64Img.length() > 90 ?base64Img.substring(base64Img.length() -90 ) : base64Img;
  	           }
  	         if( base64ImgAntiguo != null ) { 
  	        	 base64ImgAntiguo =base64ImgAntiguo.length() > 90 ? base64ImgAntiguo.substring(base64ImgAntiguo.length() -90)  :base64ImgAntiguo;
  	         }
  	          //aqui se manda la imagen la cual no se puede agregar con el comparador de objetos    
                  log.validarDatos(base64Img ,base64ImgAntiguo, idUsuario, "CPA_ICONOPRIORIDAD", prio.getCpaIdprioridadatencion(), "CFG_PRIORIDADATENCION", prio.getCpaDescripcion());
                 //***********************************************************
                
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
    
    public List<CfgPrioridadatencion> existePrioridadAtencion(String nombre) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("select prior"
                    + " from com.grupobios.bioslis.entity.CfgPrioridadatencion prior"
                    + " where prior.cpaDescripcion = :nombre");
            query.setParameter("nombre", nombre);
            List<CfgPrioridadatencion> lista = query.list();
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
    
    public CfgPrioridadatencion getPrioridadAtencionById(Short id) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Query query = sesion.createQuery("select prior"
                    + " from com.grupobios.bioslis.entity.CfgPrioridadatencion prior"
                    + " where prior.cpaIdprioridadatencion = :id");
            query.setParameter("id", id);
            CfgPrioridadatencion prioridad= (CfgPrioridadatencion) query.uniqueResult();
            sesion.getTransaction().commit();
            sesion.close();
            return prioridad;
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
}
