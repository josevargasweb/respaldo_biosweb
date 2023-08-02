/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.EnvaseDTO;
import com.grupobios.bioslis.entity.CfgGruposmuestrasexa;
import com.grupobios.bioslis.entity.CfgSigmaimagenes;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marco Caracciolo
 */
public class CfgSigmaImagenesDAO {
    
    private static Logger logger = LogManager.getLogger(CfgSigmaImagenesDAO.class);
    
    public void insertSigmaImagenes(CfgSigmaimagenes sigmaImagen) throws BiosLISDAOException{
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.save(sigmaImagen);
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
    
    public void updateSigmaImagenes(CfgSigmaimagenes sigmaImagen, Long idUsuario, Long idAnalizador, String descripcion) throws BiosLISDAOException{
    	
    	CfgSigmaimagenes imagenAntigua = this.getSigmaImagenById(sigmaImagen.getCsiIdimagen());
    	
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            sesion.update(sigmaImagen);
            sesion.getTransaction().commit();
            sesion.close();
            
            
           
            String base64Encoded= null;
            String base64EncodedAntiguo = null;
            
            if (sigmaImagen != null) {
                byte[] bdata= null;
				try {
					bdata =sigmaImagen.getCsiImagen().getBytes(1, (int) sigmaImagen.getCsiImagen().length());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
                
				try {
					base64Encoded = new String(encodeBase64, "UTF-8");
					 
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              
              
            }
            
              if (imagenAntigua != null) {
                  byte[] bdata= null;
					try {
						bdata = imagenAntigua.getCsiImagen().getBytes(1, (int) imagenAntigua.getCsiImagen().length());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                  byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
                  
					try {
						base64EncodedAntiguo = new String(encodeBase64, "UTF-8");				 
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                
                 
              }
              
     
           
              LogCfgTablasDAO log = new LogCfgTablasDAO();
        
          //aqui se manda la imagen la cual no se puede agregar con el comparador de objetos   
           String base64Img = "";
           String base64ImgAntiguo ="";
           if(base64Encoded != null) {
        	   base64Img =base64Encoded.length() > 90 ? ( base64Encoded.substring(base64Encoded.length() -90 )) : base64Encoded;
           }
           if(base64EncodedAntiguo != null) {
        	   base64ImgAntiguo = base64EncodedAntiguo.length() > 90 ? base64EncodedAntiguo.substring(base64EncodedAntiguo.length() -90)  : base64EncodedAntiguo;	
           }
        	   log.validarDatos(base64Img ,  base64ImgAntiguo, idUsuario, "SA_IDIMAGEN", idAnalizador.intValue(), "SIGMA_ANALIZADORES", descripcion);
          //***********************************************************
		
            
            
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public CfgSigmaimagenes getSigmaImagenById(short idImagen) throws BiosLISDAOException {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            String sql = "from CfgSigmaimagenes where csiIdimagen = :idImagen";
            Query query = sesion.createQuery(sql);
            query.setParameter("idImagen", idImagen);
            CfgSigmaimagenes sigmaimagen = (CfgSigmaimagenes) query.uniqueResult();
            sesion.getTransaction().commit();
            sesion.close();
            return sigmaimagen;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
        } finally {
            if (sesion.isOpen()) {
                sesion.close();
            }
        }
    }
}
