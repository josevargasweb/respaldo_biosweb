package com.grupobios.bioslis.common;

import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dao.BiosLISDAOException;

public class ValidacionMantenedor {
//VALIDA MANTENEDORES
  // VALIDA CODIGO Y REPETIDOS

  // VALIDAS CODIGOS Y DESCRIPCION REPETIDOS
  public boolean validarCodigoyAbreviado(String campoCode, String campoAbre, String campoTabla, String codigo,
      String Abreviado) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    boolean validador = false;
    String getCodigo = "SELECT " + campoCode + " FROM " + campoTabla + " WHERE " + campoCode + " = :codigo ";
    String getAbreviado = "SELECT " + campoAbre + " FROM " + campoTabla + " WHERE " + campoAbre + " = :abreviado";
    try {
      Query queryCodigo = sesion.createSQLQuery(getCodigo);
      queryCodigo.setString("codigo", codigo);// codigo que llega

      // si existe codigo tira false
      if (queryCodigo.list().isEmpty()) {
        Query queryAbreviado = sesion.createSQLQuery(getAbreviado);
        queryAbreviado.setString("abreviado", Abreviado);// Abreviado que llega
        // si existe nombre igual tira false
        validador = queryAbreviado.list().isEmpty();
      }
      sesion.close();

    } catch (HibernateException he) {
      // logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return validador;
  }

  // VALIDAS CODIGOS Y DESCRIPCION REPETIDOS
  // HAY QUE LIMPIAR CODIGO
  public boolean validarxId(String campoCode, String campoId, String campoAbre, String campoTabla, String codigo,
      String Abreviado, BigDecimal idEnviado) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    boolean validador = true;
    String getId = "SELECT " + campoId + " FROM " + campoTabla + " WHERE " + campoCode + " = :codigo ";
    String getAbreviado = "SELECT " + campoAbre + " FROM " + campoTabla + " WHERE " + campoAbre + " = :abreviado";
    String getAbreviadoxId = "SELECT " + campoId + " FROM " + campoTabla + " WHERE " + campoAbre + " = :abreviado";
    try {
      Query queryId = sesion.createSQLQuery(getId);
      queryId.setParameter("codigo", codigo);

      if (!queryId.list().isEmpty()) {
        BigDecimal idDB = (BigDecimal) queryId.uniqueResult();
        BigDecimal idEnviada = idEnviado;
        validador = false;
        if (idDB.compareTo(idEnviada) == 0) {
          Query queryAbreviado = sesion.createSQLQuery(getAbreviado);
          queryAbreviado.setString("abreviado", Abreviado);// Abreviado que llega
          if (queryAbreviado.list().isEmpty()) {
            validador = true;
          } else {
            String abreviadoDB = (String) queryAbreviado.uniqueResult();
            Query queryAbreviadoxId = sesion.createSQLQuery(getAbreviadoxId);
            queryAbreviadoxId.setString("abreviado", Abreviado);
            BigDecimal idxAbreviado = (BigDecimal) queryAbreviadoxId.uniqueResult();
            if (!abreviadoDB.equals(Abreviado)) {
              validador = true;
            } else {
              // si existe nombre igual tira false
              validador = false;
            }
            // si los id buscando x abreviado son iguales devuelve true
            if (idxAbreviado.compareTo(idDB) == 0) {
              validador = true;
            }
          }
        }
      }
      sesion.close();
    } catch (HibernateException he) {
      // logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

    return validador;
  }


  //VALIDAS CODIGOS Y DESCRIPCION REPETIDOS
  public boolean validarxIdSinAbreviado(String campoCode,String campoId,String campoTabla,String codigo,BigDecimal idEnviado) throws BiosLISDAOException {
      Session sesion = HibernateUtil.getSessionFactory().openSession();
      boolean validador = true;
      String getId = "SELECT "+campoId+" FROM "+campoTabla+" WHERE "+campoCode+" = :codigo ";
      try {
        	Query queryId = sesion.createSQLQuery(getId);
        	queryId.setParameter("codigo", codigo);   
      	if (!queryId.list().isEmpty()) {
      		validador = false;
	            BigDecimal idDB = (BigDecimal) queryId.list().get(0);
	    		BigDecimal idEnviada = idEnviado;
	    		if (idDB.compareTo(idEnviada) == 0) {
	            	validador = true;
	    		}
      	}
      } catch (HibernateException he) {
          //logger.error(he.getMessage());
          throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
      } finally {
          if (sesion.isOpen()) {
              sesion.close();
          }
      }
      return validador;
  }
 

  // VALIDAS CODIGOS Y DESCRIPCION REPETIDOS
  public boolean validarCodigo(String campoCode, String campoTabla, String codigo) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    boolean validador = false;
    String getCodigo = "SELECT " + campoCode + " FROM " + campoTabla + " WHERE " + campoCode + " = :codigo ";
    try {
      Query queryCodigo = sesion.createSQLQuery(getCodigo);
      queryCodigo.setString("codigo", codigo);// codigo que llega
      validador = queryCodigo.list().isEmpty();

    } catch (HibernateException he) {
      // logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return validador;
  }

}
