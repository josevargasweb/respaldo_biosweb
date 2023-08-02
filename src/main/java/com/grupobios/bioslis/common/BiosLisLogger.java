package com.grupobios.bioslis.common;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;
import com.grupobios.bioslis.dto.AppPersistenceClassMap;
import com.grupobios.bioslis.dto.DtoAsociadoEntityEntry;
import com.grupobios.bioslis.dto.DtoAsociadoEntityImpl;
import com.grupobios.bioslis.dto.LogEventoFichaDtoId;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.LogCfgtablas;
import com.grupobios.bioslis.entity.LogEventosfichas;
import com.grupobios.bioslis.entity.LogPacientes;

public class BiosLisLogger {

  private static Logger logger = LogManager.getLogger(BiosLisLogger.class);

  LogEventosfichasDAO logEventosfichasDAO;

  public LogEventosfichasDAO getLogEventosfichasDAO() {
    return logEventosfichasDAO;
  }

  public void setLogEventosfichasDAO(LogEventosfichasDAO logEventosfichasDAO) {
    this.logEventosfichasDAO = logEventosfichasDAO;
  }

  public BiosLisLogger() {

    this.logEventosfichasDAO = new LogEventosfichasDAO();
  }

  public void logCfgInsertTableRecord(Class<?> clazz, Object hibernateObject, BigDecimal idAccionDato,
      BigDecimal idUsuario, Timestamp fechaHoraMod, BigDecimal idRegistro)
      throws IllegalArgumentException, IllegalAccessException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {
      Class<?> cls = hibernateObject.getClass();
      Field[] fields = cls.getDeclaredFields();
      Map<String, String> fieldColNameMap = AppPersistenceClassMap.getInstance().getMap(clazz).getFieldColMap();
      String tableName = AppPersistenceClassMap.getInstance().getTableNameForEntityClass(clazz); //
      logger.debug("Nombre tabla {}", tableName);

      LogCfgtablas logTab;
      for (Field field : fields) {
        field.setAccessible(true);
        if (field.get(hibernateObject) == null || fieldColNameMap.get(field.getName()) == null
            || fieldColNameMap.get(field.getName()).trim().equals("")) {
          continue;
        } else {
          logTab = new LogCfgtablas();
          logTab.setLctValornuevo(field.get(hibernateObject).toString()); //
          logger.debug("Valor: {}", field.get(hibernateObject).toString());
        }
        logTab.setLctFechamodificacion(fechaHoraMod);
        logTab.setLctIdtabla(idRegistro.intValue());
//        logTab.setLctNombrecampo(fieldColNameMap.get(field.getName()));
        logTab.setLctNombretabla(tableName);
        logTab.setLctIdtabla(idRegistro.intValue());
        logTab.setLctIdusuario(idUsuario.intValue());
        logTab.setCfgAccionesdatos(idAccionDato.intValue());
        logTab.setLctValoranterior("");
        sesion.beginTransaction();
        sesion.save(logTab);
        sesion.getTransaction().commit();
      }
    } catch (HibernateException he) {
      logger.error(he.getMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void logInsertDatosFichaTableRecord(Class<?> clazz, Object hibernateObject, BigDecimal idUsuario,
      Timestamp fechaHoraMod, BigDecimal idRegistro, Date fechaOrden, BigDecimal idExamen, BigDecimal idMuestra,
      BigDecimal idPaciente, BigDecimal idTest, String ipEstacion, BigDecimal idEvento)
      throws IllegalArgumentException, IllegalAccessException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {

      Class<?> cls = hibernateObject.getClass();
      Field[] fields = cls.getDeclaredFields();
      Map<String, String> fieldColNameMap = AppPersistenceClassMap.getInstance().getMap(clazz).getFieldColMap();
      String tableName = AppPersistenceClassMap.getInstance().getTableNameForEntityClass(clazz); //
      // List<Column> lstPkCol =
      // AppPersistenceClassMap.getInstance().getMap(clazz).isInPk(tableName);

      logger.debug("Nombre tabla " + tableName);
      LogEventosfichas logEvF;

      for (Field field : fields) {
        field.setAccessible(true);
        // //
        // logger.debug("\n"); // logger.debug("Campo {}",
        // field.getName()); // logger.debug("Tipo {}", field.getType().getName()); //
        logger.debug("Columna: {}", fieldColNameMap.get(field.getName()));
        String colName = fieldColNameMap.get(field.getName());
        if (field.get(hibernateObject) == null || fieldColNameMap.get(field.getName()) == null
            || fieldColNameMap.get(field.getName()).trim().equals("")
            || field.get(hibernateObject).toString() == null) {
          logger.debug("Valor: Nulo o campo no mapeado");
          continue;
        } else if (AppPersistenceClassMap.getInstance().getMap(clazz).isInPk(tableName)) {
          //
          logger.debug("Columna {} forma parte de la Pk", colName);
          continue;
        } else {
          logEvF = new LogEventosfichas();
          logEvF.setLefValornuevo(field.get(hibernateObject).toString()); //
          logger.debug("Valor: >>{}<<", field.get(hibernateObject).toString());
        } //
        logger.debug("\n");
        logEvF.setLefFecharegistro(fechaHoraMod);
        logEvF.setLefFechaorden(fechaOrden);
        logEvF.setLefIdestacion(null);
        logEvF.setLefIdexamen(idExamen != null ? idExamen.intValue() : null);
        logEvF.setLefIdmuestra(idMuestra != null ? idMuestra.intValue() : null);
        logEvF.setLefIdpaciente(idPaciente != null ? idPaciente.intValue() : null);
        logEvF.setLefIdtest(idTest != null ? idTest.intValue() : null);
        logEvF.setLefIdusuario(idUsuario.intValue());
        logEvF.setLefIpestacion(ipEstacion);
        logEvF.setLefValoranterior("");
        logEvF.setLefNombrecampo(fieldColNameMap.get(field.getName()));
        logEvF.setLefNombretabla(tableName);
        logEvF.setCfgEventos(idEvento.intValue());
        logEvF.setDatosFichas(idRegistro.intValue());
        sesion.beginTransaction();
        sesion.save(logEvF);
        sesion.getTransaction().commit();
      }
    } catch (HibernateException he) {
      logger.error(he.getMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public <T> void logInsertDatosDTOFichaTableRecord(T typeDtoObject, BigDecimal idUsuario, Timestamp fechaHoraMod,
      BigDecimal idRegistro, String ipEstacion, BigDecimal evento, LogEventoFichaDtoId lefdid)
      throws IllegalArgumentException {

    Map<String, LogEventosfichas> mapLef = null;
    DtoAsociadoEntityImpl<T> daei = new DtoAsociadoEntityImpl<>(typeDtoObject, lefdid);
    try {
      Long idExamen = lefdid.getIdExamen() == null ? null : lefdid.getIdExamen().longValue();
      Long idTest = lefdid.getIdTest() == null ? null : lefdid.getIdTest().longValue();
      Long idMuestra = lefdid.getIdMuestra() == null ? null : lefdid.getIdMuestra().longValue();

      mapLef = logEventosfichasDAO.getLogUltimosEventosOrden(idRegistro.longValue(), daei.getTableAsocName(), idExamen,
          idTest, idMuestra);
    } catch (BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
    }

    List<DtoAsociadoEntityEntry<T>> listEntr = daei.getListEntr();
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      for (DtoAsociadoEntityEntry<T> daeei : listEntr) {

        if (mapLef.get(daeei.getColName()) != null) {
//          logger.debug("Valor antiguo para columna {}: {}", daeei.getColName(),
//              mapLef.get(daeei.getColName()).getLefValornuevo());
          if (!mapLef.get(daeei.getColName()).getLefValornuevo().equals(daeei.getLef().getLefValornuevo())) {
            sesion.beginTransaction();
            daeei.getLef().setLefValoranterior(mapLef.get(daeei.getColName()).getLefValornuevo());
            sesion.save(daeei.getLef());
            sesion.getTransaction().commit();
          }
        } else {
          sesion.beginTransaction();
          sesion.save(daeei.getLef());
          sesion.getTransaction().commit();
        }
//        logger.debug("Se registra lef");
      }
    } catch (HibernateException e) {
      logger.error(e.getMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

//  public Map<String, String> getColNamesForDtoEntityClass(Class<?> clazz) {
//    Map<String, String> hm = new HashMap<>();
//    ClassMetadata cm = HibernateUtil.getSessionFactory().getClassMetadata(clazz);
//    String[] propertyNames = cm.getPropertyNames();
//    Configuration configuration = HibernateUtil.getConfiguration();
//    String[] colNames = new String[propertyNames.length];
//    PersistentClass persistentClass = configuration.getClassMapping(clazz.getName());
//    int i = 0;
//    for (String property : propertyNames) {
//      logger.debug(property);
//      try {
//        Column col = (Column) persistentClass.getProperty(property).getColumnIterator().next();
//        hm.put(property, col.getName());
//        logger.debug(((Column) persistentClass.getProperty(property).getColumnIterator().next()).getName());
//        colNames[i] = ((Column) persistentClass.getProperty(property).getColumnIterator().next()).getName();
//        i++;
//      } catch (java.util.NoSuchElementException e) {
//        logger.error(e.getMessage());
//      }
//    }
//    return hm;
//  }

  public void logUpdateDatosFichaTableRecord(Class<?> clazz, Object hibernateObject, BigDecimal idUsuario,
      Timestamp fechaHoraMod, BigDecimal nroOrden, Date fechaOrden, BigDecimal idExamen, BigDecimal idMuestra,
      BigDecimal idPaciente, BigDecimal idTest, String ipEstacion, BigDecimal idEvento)
      throws IllegalArgumentException, IllegalAccessException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {

      Map<String, LogEventosfichas> mapLef = null;
      String tableName = AppPersistenceClassMap.getInstance().getTableNameForEntityClass(clazz);
      try {
        mapLef = logEventosfichasDAO.getLogUltimosEventosOrden(nroOrden.longValue(), tableName, idExamen.longValue(),
            idTest.longValue(), idMuestra.longValue());
      } catch (BiosLISDAONotFoundException e) {
        logger.error(e.getMessage());
      } catch (BiosLISDAOException e) {
        logger.error(e.getMessage());
      }
      Class<?> cls = hibernateObject.getClass();
      Field[] fields = cls.getDeclaredFields();
      Map<String, String> fieldColNameMap = AppPersistenceClassMap.getInstance().getMap(clazz).getFieldColMap();
      int pos = tableName.indexOf(Constante.SCHEMA.concat("."));

      logger.debug("Posicion esquema {} es {}", Constante.SCHEMA.concat("."), pos);

      if (pos != -1) {
        tableName = tableName.substring(Constante.SCHEMA.concat(".").length());
      }
      logger.debug("Nombre tabla " + tableName);
      LogEventosfichas logEvF;
      LogEventosfichas ultimoLogEvF;

      for (Field field : fields) {
        field.setAccessible(true);

//        logger.debug("\n********************");
//        logger.debug("Campo {}", field.getName());
//        logger.debug("Tipo {}", field.getType().getName());
//        logger.debug("Columna: {}", fieldColNameMap.get(field.getName()));
        if (field.get(hibernateObject) == null || fieldColNameMap.get(field.getName()) == null
            || fieldColNameMap.get(field.getName()).trim().equals("")
            || field.get(hibernateObject).toString() == null) {
          logger.debug("Valor: Nulo o campo no mapeado");
          continue;
        } else {
          logEvF = new LogEventosfichas();
          logEvF.setLefValornuevo(field.get(hibernateObject).toString());
          logger.debug("Valor: >>{}<<", field.get(hibernateObject).toString());
        }
        ultimoLogEvF = mapLef.get(fieldColNameMap.get(field.getName()));

        if (ultimoLogEvF == null) {
          logger.debug("ultimoLogEvF es nulo.");
        } else {
          logger.debug("ultimoLogEvF es {}.", ultimoLogEvF);
        }

        logEvF.setLefFecharegistro(fechaHoraMod);
        logEvF.setLefFechaorden(fechaOrden);
        logEvF.setLefIdestacion(null);
        logEvF.setLefIdexamen(idExamen != null ? idExamen.intValue() : null);
        logEvF.setLefIdmuestra(idMuestra != null ? idMuestra.intValue() : null);
        logEvF.setLefIdpaciente(idPaciente != null ? idPaciente.intValue() : null);
        logEvF.setLefIdtest(idTest != null ? idExamen.intValue() : null);
        logEvF.setLefIdusuario(idUsuario.intValue());
        logEvF.setLefIpestacion(ipEstacion);
        logEvF.setLefValoranterior("");
        logEvF.setLefNombrecampo(fieldColNameMap.get(field.getName()));
        logEvF.setLefNombretabla(tableName);
        logEvF.setCfgEventos(idEvento.intValue());
        logEvF.setDatosFichas(nroOrden.intValue());
        sesion.beginTransaction();
        sesion.save(logEvF);
        sesion.getTransaction().commit();
      }
    } catch (HibernateException he) {
      logger.error(he.getMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  public void logInsertDatosPacienteTableRecord(Class<?> clazz, Object hibernateObject, BigDecimal idUsuario,
      Timestamp fechaHoraMod, BigDecimal idRegistro, Date fechaOrden, BigDecimal idExamen, BigDecimal idMuestra,
      BigDecimal idPaciente, BigDecimal idTest, String ipEstacion, BigDecimal idEvento)
      throws IllegalArgumentException, IllegalAccessException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();

    try {

      Class<?> cls = hibernateObject.getClass();
      Field[] fields = cls.getDeclaredFields();
      Map<String, String> fieldColNameMap = AppPersistenceClassMap.getInstance().getMap(clazz).getFieldColMap();
      String tableName = AppPersistenceClassMap.getInstance().getTableNameForEntityClass(clazz); //
      // List<Column> lstPkCol =
      // AppPersistenceClassMap.getInstance().getMap(clazz).isInPk(tableName);

      logger.debug("Nombre tabla " + tableName);
      LogPacientes logP;

      for (Field field : fields) {
        field.setAccessible(true);
        // //
        // logger.debug("\n"); // logger.debug("Campo {}",
        // field.getName()); // logger.debug("Tipo {}", field.getType().getName()); //
        logger.debug("Columna: {}", fieldColNameMap.get(field.getName()));
        String colName = fieldColNameMap.get(field.getName());
        if (field.get(hibernateObject) == null || fieldColNameMap.get(field.getName()) == null
            || fieldColNameMap.get(field.getName()).trim().equals("")
            || field.get(hibernateObject).toString() == null) {
          logger.debug("Valor: Nulo o campo no mapeado");
          continue;
        } else if (AppPersistenceClassMap.getInstance().getMap(clazz).isInPk(tableName)) {
          //
          logger.debug("Columna {} forma parte de la Pk", colName);
          continue;
        } else {
          logP = new LogPacientes();
          if (field.get(hibernateObject).toString() != null) {
            logP.setLpValornuevo(field.get(hibernateObject).toString()); //
          }
          logger.debug("Valor: >>{}<<", field.get(hibernateObject).toString());
        } //
        logger.debug("\n");
        logP.setLpFechamodificacion(fechaHoraMod);
//        logEvF.setLp(fechaOrden);
//        logEvF.setLp(null);
//        logEvF.setLp(idExamen != null ? idExamen.intValue() : null);
//        logEvF.setLefIdmuestra(idMuestra != null ? idMuestra.intValue() : null);
//        logEvF.setLpIdpaciente(idPaciente != null ? idPaciente.intValue() : null);
//        logEvF.setLefIdtest(idTest != null ? idTest.intValue() : null);
//        logEvF.setL(idUsuario.intValue());
//        logEvF.setLefIpestacion(ipEstacion);
        logP.setDatosPacientes((DatosPacientes) hibernateObject);
        logP.setLpValoranterior("");
        logP.setLpCampomodificado(fieldColNameMap.get(field.getName()));
//        logEvF.setLefNombretabla(tableName);
        logP.setLpIdacciondato(idEvento.intValue());
        sesion.beginTransaction();
        sesion.save(logP);
        sesion.getTransaction().commit();
      }
    } catch (HibernateException he) {
      logger.error(he.getMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

}
