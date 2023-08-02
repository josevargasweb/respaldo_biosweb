package com.grupobios.bioslis.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.EtiquetaCodigoBarraDTO;
import com.grupobios.bioslis.entity.CfgEtiquetacodigobarras;
import com.grupobios.bioslis.entity.LogCfgtablas;

//**** creado por cristian ****
public class CfgEtiquetaCodigoBarrasDAO {

  private static Logger logger = LogManager.getLogger(CfgEtiquetaCodigoBarrasDAO.class);

  LogCfgTablasDAO logCfgTablasDAO;
  
  
  
  
  
  public LogCfgTablasDAO getLogCfgTablasDAO() {
	return logCfgTablasDAO;
}

public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
	this.logCfgTablasDAO = logCfgTablasDAO;
}

@SuppressWarnings("unchecked")
  public List<EtiquetaCodigoBarraDTO> getAllEtiquetas() throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    List<EtiquetaCodigoBarraDTO> listEtiquetas;
    try {
      Query query = sesion.createSQLQuery("select * from cfg_etiquetacodigobarras")
          .setResultTransformer(Transformers.aliasToBean(EtiquetaCodigoBarraDTO.class));

      listEtiquetas = query.list();

    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
      logger.error("No se pudo obtener datos Etiquetas", he.getMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return listEtiquetas;
  }

  public EtiquetaCodigoBarraDTO getEtiquetaBycodigo(String codigo) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    EtiquetaCodigoBarraDTO etiqueta;
    try {
      Query query = sesion.createSQLQuery("select * from cfg_etiquetacodigobarras where cecb_codigo=:codigo")
          .setResultTransformer(Transformers.aliasToBean(EtiquetaCodigoBarraDTO.class));
      query.setString("codigo", codigo);

      etiqueta = (EtiquetaCodigoBarraDTO) query.uniqueResult();

    } catch (HibernateException he) {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
      logger.error("No se pudo obtener datos Etiquetas", he.getMessage());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return etiqueta;
  }

  public void insertUpdateEtiqueta(CfgEtiquetacodigobarras etiquetaCodigoBarra, String idUsuario)
      throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();

    Timestamp ts = BiosLisCalendarService.getInstance().getTS();
    CfgEtiquetacodigobarras etiqueta;
    try {
      sesion.beginTransaction();

      etiqueta = (CfgEtiquetacodigobarras) sesion.get(CfgEtiquetacodigobarras.class,
          etiquetaCodigoBarra.getCecbCodigo());

      sesion.evict(etiqueta);
      etiquetaCodigoBarra.setCecbUsuarioregistro(idUsuario);
      etiquetaCodigoBarra.setCecbFecharegistro(ts);

      if (etiqueta.equals(null)) {
        sesion.save(etiquetaCodigoBarra);
        
        //Se agregan causa de rechazo muestra a log tablas **********************
        LogCfgtablas logTabla = new LogCfgtablas();
       
        logTabla.setCfgAccionesdatos(1);
        logTabla.setLctIdusuario(Integer.parseInt(idUsuario));
        logTabla.setLctNombretabla("CFG_ETIQUETACODIGOBARRAS");                  
        logTabla.setLctNombrecampo("CECB_CODIGO");       
        logTabla.setLctDescripcion(etiquetaCodigoBarra.getCecbDescripcion());
        
        logCfgTablasDAO.insertLogTablas(logTabla, sesion);
        //*************************************************************
        
        
        sesion.getTransaction().commit();
        sesion.close();
        
        
      } else {    	  
        etiquetaCodigoBarra.setCecbTexto0fila(etiqueta.getCecbTexto0fila());
        etiquetaCodigoBarra.setCecbTexto0columna(etiqueta.getCecbTexto0columna());
        etiquetaCodigoBarra.setCecbActivotexto0(etiqueta.getCecbActivotexto0());
        etiquetaCodigoBarra.setCecbNegrita0(etiqueta.getCecbNegrita0());
        etiquetaCodigoBarra.setCecbtamano0(etiqueta.getCecbtamano0());
        etiquetaCodigoBarra.setCecbSort(etiqueta.getCecbSort());
        etiquetaCodigoBarra.setCecbTexto1largo(etiqueta.getCecbTexto1largo());
        etiquetaCodigoBarra.setCecbTexto12largo(etiqueta.getCecbTexto12largo());
        etiquetaCodigoBarra.setCecbTexto12tamano(etiqueta.getCecbTexto12tamano());
        
        sesion.update(etiquetaCodigoBarra);
        sesion.getTransaction().commit();
        sesion.close();
        
        LogCfgTablasDAO log = new LogCfgTablasDAO();
        logCfgTablasDAO.comparadorObjetos(etiqueta, etiquetaCodigoBarra, 2, Integer.parseInt(idUsuario) , 0, etiquetaCodigoBarra.getCecbDescripcion(), null);
        
      }

     
      
    } catch (HibernateException he) {
      sesion.getTransaction().rollback();
      if (sesion != null && sesion.isOpen()) {
          sesion.close();
        }
      logger.error("No se pudo grabar datos Etiquetas", he.getMessage());
      logger.error(he.getStackTrace());
      throw new BiosLISDAOException(he.getLocalizedMessage());
    } finally {
    	if (sesion != null && sesion.isOpen()) {
            sesion.close();
          }
    }

  }

}
