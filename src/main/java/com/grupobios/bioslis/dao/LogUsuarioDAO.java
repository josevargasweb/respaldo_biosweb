package com.grupobios.bioslis.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.dto.LogEventosUsuariosVistaDTO;
import com.grupobios.bioslis.dto.LogFichasExamenesTestDTO;


public class LogUsuarioDAO {

	
	 private static final Logger logger = LogManager.getLogger(LogUsuarioDAO.class);
	 
	 
	 
	 public void insertLogEventosUsuario(LogEventoUsuarioDTO leu) {
		    Session sesion = HibernateUtil.getSessionFactory().openSession();
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
		   
		    leu.setLeuIpusuario(ipLocal[1]);
		    if(ipLocal[0].length() > 50) {
		        leu.setLeuNombreequipo(ipLocal[0].substring(0,50));      
		    }else {
		    	leu.setLeuNombreequipo(ipLocal[0]);
		    }
		  
		    try {
		    	
		    sesion.beginTransaction();
		   
		    Query qry = sesion.createSQLQuery(
		              "insert into LOG_EVENTOSUSUARIOS  (leu_idusuario, leu_fechaevento, leu_idevento,leu_idacciondato, leu_nombretabla , leu_nombrecampo , "
		              + " leu_valoranterior, leu_valornuevo, leu_idtabla , leu_ipusuario, leu_nombreequipo ) "
		              + " values (:idUsuario, :fechaEvento ,:idEvento,:idAccionDato, :nombreTabla, :nombreCampo, :valorAnterior, :valorNuevo ,:idTabla, :ipUsuario, :nombreEquipo )");
		          qry.setParameter("idUsuario", leu.getLeuIdusuario());
		          qry.setParameter("fechaEvento", leu.getLeuFechaevento());
		          qry.setParameter("idEvento", leu.getLeuIdevento());
		          qry.setParameter("idAccionDato", leu.getLeuIdacciondato());
		          qry.setParameter("nombreTabla", leu.getLeuNombretabla());		          
		          qry.setParameter("nombreCampo", leu.getLeuNombrecampo());
		          qry.setParameter("valorAnterior", leu.getLeuValoranterior());
		          qry.setParameter("valorNuevo", leu.getLeuValornuevo());
		          qry.setParameter("idTabla", leu.getLeuIdtabla());
		          qry.setParameter("ipUsuario", leu.getLeuIpusuario());
		          qry.setParameter("nombreEquipo", leu.getLeuNombreequipo());
		    qry.executeUpdate();
		    sesion.getTransaction().commit();
		    sesion.close();
		    } catch (HibernateException he) {
		    	he.printStackTrace();
		    	if (sesion != null && sesion.isOpen()) {
		            sesion.close();
		          }
		        logger.error("error en insertLogEventosUsuarios "+he.getMessage());   
		     } finally {
		    	 if (sesion != null && sesion.isOpen()) {
		             sesion.close();
		           }
		         }
		    logger.debug("insertando eventos en insertLogEventosUsuarios");
		  }
	 	
	 
	 
	 
	 public List<LogEventosUsuariosVistaDTO> getEventosUsuarioById(Long id, String filtro, int inicio, int fin) throws BiosLISDAOException {
	     Session sesion = HibernateUtil.getSessionFactory().openSession();   
	     List<LogEventosUsuariosVistaDTO> lstEventosUsuario = null;
	  	   
	     try {
	         String query ="select leu.leu_fechaevento, leu.leu_idevento, leu.leu_idacciondato, leu.leu_valoranterior, leu.leu_valornuevo, leu.leu_ipusuario, "
	         		+ "concat(concat(concat(concat(du.du_nombres , ' ' ), du.du_primerapellido ), ' ' ) ,du.du_segundoapellido) as NOMBREUSUARIO , leu.leu_nombrecampo "
	         		+ "from log_eventosusuarios leu "
	         		+ "INNER JOIN datos_usuarios du on du.du_idusuario = leu.leu_idusuario "
	         		+ " inner join cfg_eventos ceven on ceven.ceven_idevento = leu.leu_idevento "
	         		+ "where leu.leu_idusuario = :id ";
	      
	         
	         if(!filtro.equals("-1")) {
	             filtro = filtro.toUpperCase();
	          
	            query = query + "    and ( (upper(leu.leu_valoranterior) like '%"+ filtro + "%' or upper(leu.leu_valornuevo) like '%"+ filtro +  "%'"
	                    + " or leu.leu_nombrecampo like '%" +filtro + "%'"
	                    + " or du.du_nombres like '%" +filtro + "%'  or du.du_primerapellido like '%" +filtro + "%' or ceven.ceven_descripcion like '%"+ filtro + "%') "
	                    + "  )  ";
	         }
	         
	         		query = query +  " order by leu.leu_ideventousuario Desc ";
	         		
	         		if(fin != 0) {
	        		 query = query + "OFFSET "+ inicio+ " ROWS FETCH NEXT " + fin + " ROWS ONLY";
	         		}
	       Query qry = sesion.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(LogEventosUsuariosVistaDTO.class));
	         		
	       qry.setLong("id", id);
	      
	       lstEventosUsuario = qry.list();
	       sesion.close();       
	     
	      
	     } catch (HibernateException he) {
	    	 if (sesion != null && sesion.isOpen()) {
	             sesion.close();
	           }
	         logger.error("error en getEventosUsuarioById "+he.getMessage()); 
	       throw new BiosLISDAOException("No hay eventos asociados al usuario");
	     } finally {
	    	 if (sesion != null && sesion.isOpen()) {
	             sesion.close();
	           }
	     }
	     return lstEventosUsuario;
	      
	   
	  }
	  
	 
	 
}
