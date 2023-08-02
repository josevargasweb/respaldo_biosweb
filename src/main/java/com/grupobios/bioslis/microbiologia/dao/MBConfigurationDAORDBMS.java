package com.grupobios.bioslis.microbiologia.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.grupobios.bioslis.common.ValidacionMantenedor;
import com.grupobios.bioslis.config.HibernateUtil;

public class MBConfigurationDAORDBMS implements MBConfigurationDAO {

  private static ValidacionMantenedor validador = new ValidacionMantenedor();

  public HashMap<String, String> getMicroorganism(String id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, String> result = new HashMap<String, String>();
    try {
      Query query = session.createSQLQuery("SELECT CBMO_IDBACMICROORGANISMO, CBMO_CODIGO , CBMO_DESCRIPCION, "
          + "CBMO_IDMOTINCION, CBMO_IDMOMORFOLOGIA, CBMO_IDMOGENERO, CBMO_ACTIVO, CBMO_HOST, CBMO_NOTA FROM "
          + "BIOSLIS.CFG_BACMICROORGANISMOS WHERE CBMO_IDBACMICROORGANISMO = :moId");
      query.setParameter("moId", id);
      Object[] mo = (Object[]) query.uniqueResult();
      session.close();

      if (mo != null) {
        result.put("id", mo[0].toString());
        result.put("code", mo[1].toString());
        result.put("name", mo[2].toString());
        result.put("staining", mo[3].toString());
        result.put("morphology", mo[4].toString());
        result.put("gender", mo[5].toString());
        if ("S".equals(mo[6].toString())) {
          result.put("active", "true");
        } else {
          result.put("active", "false");
        }
        if (mo[7] == null) {
          result.put("lisbac", "");
        } else {
          result.put("lisbac", mo[7].toString());
        }
        if (mo[8] == null) {
          result.put("notes", "");
        } else {
          result.put("notes", mo[8].toString());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public List<HashMap<String, String>> getMOList(String code, String name, String active_only) {
    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      String active_query = "";
      if ("true".equals(active_only)) {
        active_query = "AND CBMO_ACTIVO = 'S'";
      }

      Query query = session
          .createSQLQuery("SELECT CBMO_IDBACMICROORGANISMO , CBMO_CODIGO, CBMO_DESCRIPCION, CBMO_ACTIVO "
              + "FROM BIOSLIS.CFG_BACMICROORGANISMOS " + "WHERE CBMO_CODIGO LIKE '%" + code
              + "%' AND CBMO_DESCRIPCION LIKE '%" + name + "%' " + active_query);
      List<Object> lista = query.list();
      session.close();
      for (Object moData : lista) {
        Object[] mo = (Object[]) moData;
        if (mo != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", mo[0].toString());
              put("code", mo[1].toString());
              put("name", mo[2].toString());
              put("active", mo[3].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public List<HashMap<String, String>> getStainingOptions() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    try {
      session.beginTransaction();
      Query query = session
          .createSQLQuery("SELECT LDVBMT_IDMOTINCION , LDVBMT_DESCRIPCION " + "FROM BIOSLIS.LDV_BACMOTINCION");

      List<Object> lista = query.list();
      session.close();
      for (Object moData : lista) {
        Object[] moGender = (Object[]) moData;
        if (moGender != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", moGender[0].toString());
              put("name", moGender[1].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }

    }
    return result;
  }

  public List<HashMap<String, String>> getMorphologyOptions() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    try {
      Query query = session.createSQLQuery(
          "SELECT CBMM_IDMOMORFOLOGIA , CBMM_DESCRIPCIONMOMORFOLOGIA " + "FROM BIOSLIS.CFG_BACMOMORFOLOGIA");

      List<Object> lista = query.list();
      session.close();

      for (Object moData : lista) {
        Object[] moGender = (Object[]) moData;
        if (moGender != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", moGender[0].toString());
              put("name", moGender[1].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public List<HashMap<String, String>> getGenderOptions() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    try {
      Query query = session
          .createSQLQuery("SELECT CBMG_IDMOGENERO , CBMG_DESCRIPCIONMOGENERO " + "FROM BIOSLIS.CFG_BACMOGENEROS");
      List<Object> lista = query.list();
      session.close();
      for (Object moData : lista) {
        Object[] moGender = (Object[]) moData;
        if (moGender != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", moGender[0].toString());
              put("name", moGender[1].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }

    }
    return result;
  }

  public void updateMicroorganism(HashMap<String, String> microorganism, Long idUsuario) {
    HashMap<String, String> oldMO = this.getMicroorganism(microorganism.get("id"));
    HashMap<String, String> oldData = new HashMap<String, String>() {
      {
        put("CBMO_IDBACMICROORGANISMO", oldMO.get("id"));
        put("CBMO_CODIGO", oldMO.get("code").toString());
        put("CBMO_DESCRIPCION", oldMO.get("name").toString());
        put("CBMO_IDMOTINCION", oldMO.get("staining").toString());
        put("CBMO_IDMOMORFOLOGIA", oldMO.get("morphology").toString());
        put("CBMO_IDMOGENERO", oldMO.get("gender").toString());
        put("CBMO_ACTIVO", "true".equals(oldMO.get("active")) ? "S" : "N");
        put("CBMO_HOST", oldMO.get("lisbac").toString());
        put("CBMO_NOTA", oldMO.get("notes").toString());
      }
    };
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "UPDATE BIOSLIS.CFG_BACMICROORGANISMOS SET " + "BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_CODIGO = :code, "
              + "BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_DESCRIPCION = :name, "
              + "BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_IDMOTINCION = :staining, "
              + "BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_IDMOMORFOLOGIA = :morphology, "
              + "BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_IDMOGENERO = :gender, "
              + "BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_ACTIVO = :active, "
              + "BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_HOST = :lisbac, "
              + "BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_NOTA = :notes "
              + "WHERE BIOSLIS.CFG_BACMICROORGANISMOS.CBMO_IDBACMICROORGANISMO = :id");
      query.setString("id", microorganism.get("id"));
      query.setString("code", microorganism.get("code"));
      query.setString("name", microorganism.get("name"));
      query.setString("staining", microorganism.get("staining"));
      query.setString("morphology", microorganism.get("morphology"));
      query.setString("gender", microorganism.get("gender"));
      if ("true".equals(microorganism.get("active"))) {
        query.setString("active", "S");
      } else {
        query.setString("active", "N");
      }
      query.setString("lisbac", microorganism.get("lisbac"));
      query.setString("notes", microorganism.get("notes"));
      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }
    HashMap<String, String> newData = new HashMap<String, String>() {
      {
        put("CBMO_IDBACMICROORGANISMO", microorganism.get("id"));
        put("CBMO_CODIGO", microorganism.get("code").toString());
        put("CBMO_DESCRIPCION", microorganism.get("name").toString());
        put("CBMO_IDMOTINCION", microorganism.get("staining").toString());
        put("CBMO_IDMOMORFOLOGIA", microorganism.get("morphology").toString());
        put("CBMO_IDMOGENERO", microorganism.get("gender").toString());
        put("CBMO_ACTIVO", "true".equals(microorganism.get("active")) ? "S" : "N");
        put("CBMO_HOST", microorganism.get("lisbac").toString());
        put("CBMO_NOTA", microorganism.get("notes").toString());
      }
    };
    this.logUpdate(oldData, newData, "CFG_BACMICROORGANISMOS", microorganism.get("id"), idUsuario);
  }

  public void createMicroorganism(HashMap<String, String> microorganism) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session
          .createSQLQuery("INSERT INTO BIOSLIS.CFG_BACMICROORGANISMOS " + "(CBMO_CODIGO, " + "CBMO_DESCRIPCION, "
              + "CBMO_IDMOTINCION, " + "CBMO_IDMOMORFOLOGIA, " + "CBMO_IDMOGENERO, " + "CBMO_ACTIVO, " + "CBMO_HOST, "
              + "CBMO_NOTA) " + "VALUES ( :code, :name, :staining, :morphology, :gender, :active, :lisbac, :notes)");
      query.setString("code", microorganism.get("code").toString());
      query.setString("name", microorganism.get("name").toString());
      query.setString("staining", microorganism.get("staining").toString());
      query.setString("morphology", microorganism.get("morphology").toString());
      query.setString("gender", microorganism.get("gender").toString());
      query.setString("active", "true".equals(microorganism.get("active")) ? "S" : "N");
      query.setString("lisbac", microorganism.get("lisbac").toString());
      query.setString("notes", microorganism.get("notes").toString());
      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }

    List<HashMap<String, String>> moList = this.getMOList(microorganism.get("code").toString(),
        microorganism.get("name").toString(), microorganism.get("active").toString());
    String lastId = moList.get(moList.size() - 1).get("id");
    HashMap<String, String> changes = new HashMap<String, String>() {
      {
        put("CBMO_IDBACMICROORGANISMO", lastId);
        put("CBMO_CODIGO", microorganism.get("code").toString());
        put("CBMO_DESCRIPCION", microorganism.get("name").toString());
        put("CBMO_IDMOTINCION", microorganism.get("staining").toString());
        put("CBMO_IDMOMORFOLOGIA", microorganism.get("morphology").toString());
        put("CBMO_IDMOGENERO", microorganism.get("gender").toString());
        put("CBMO_ACTIVO", "true".equals(microorganism.get("active")) ? "S" : "N");
        put("CBMO_HOST", microorganism.get("lisbac").toString());
        put("CBMO_NOTA", microorganism.get("notes").toString());
      }
    };
    this.logCreation(changes, "CFG_BACMICROORGANISMOS", lastId);
  }

  public void deleteMicroorganism(String id, String code) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "DELETE FROM BIOSLIS.CFG_BACMICROORGANISMOS WHERE CBMO_IDBACMICROORGANISMO = :id AND CBMO_CODIGO = :code");
      query.setString("id", id);
      query.setString("code", code);
      query.executeUpdate();
      session.getTransaction().commit();
    } finally {
      session.close();
    }
  }

  public HashMap<String, String> getAntibiotic(String id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, String> result = new HashMap<String, String>();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("SELECT CBA_IDBACANTIBIOTICO, CBA_CODIGO , CBA_DESCRIPCION, "
          + "CBA_ACTIVO, CBA_HOST, CBA_SORT, CBA_IDANTIBIOTICOCLASE FROM BIOSLIS.CFG_BACANTIBIOTICOS WHERE CBA_IDBACANTIBIOTICO = :abId");
      query.setParameter("abId", id);
      Object[] ab = (Object[]) (query.uniqueResult());
      session.close();
      if (ab != null) {
        result.put("id", ab[0].toString());
        result.put("code", ab[1].toString());
        result.put("name", ab[2].toString());
        result.put("active", "S".equals(ab[3].toString()) ? "true" : "false");
        result.put("host", ab[4] == null ? "" : ab[4].toString());
        result.put("sort", ab[5] == null ? "" : ab[5].toString());
        result.put("class", ab[6] == null ? "" : ab[6].toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }

    }
    return result;
  }

  public void updateAntibiotic(HashMap<String, String> antibiotic, Long idUsuario) {
    HashMap<String, String> oldAB = this.getAntibiotic(antibiotic.get("id"));
    HashMap<String, String> oldData = new HashMap<String, String>() {
      {
        put("CBA_IDBACANTIBIOTICO", oldAB.get("id"));
        put("CBA_CODIGO", oldAB.get("code").toString());
        put("CBA_DESCRIPCION", oldAB.get("name").toString());
        put("CBA_ACTIVO", "true".equals(oldAB.get("active")) ? "S" : "N");
        put("CBA_HOST", oldAB.get("host").toString());
        put("CBA_SORT", oldAB.get("sort").toString());
        put("CBA_IDANTIBIOTICOCLASE", oldAB.get("class").toString());
      }
    };
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("UPDATE BIOSLIS.CFG_BACANTIBIOTICOS SET "
          + "BIOSLIS.CFG_BACANTIBIOTICOS.CBA_CODIGO = :code, " + "BIOSLIS.CFG_BACANTIBIOTICOS.CBA_DESCRIPCION = :name, "
          + "BIOSLIS.CFG_BACANTIBIOTICOS.CBA_ACTIVO = :active, " + "BIOSLIS.CFG_BACANTIBIOTICOS.CBA_HOST = :host, "
          + "BIOSLIS.CFG_BACANTIBIOTICOS.CBA_SORT = :sort, "
          + "BIOSLIS.CFG_BACANTIBIOTICOS.CBA_IDANTIBIOTICOCLASE = :class "
          + "WHERE BIOSLIS.CFG_BACANTIBIOTICOS.CBA_IDBACANTIBIOTICO = :id");
      query.setString("id", antibiotic.get("id"));
      query.setString("code", antibiotic.get("code"));
      query.setString("name", antibiotic.get("name"));
      query.setString("active", "true".equals(antibiotic.get("active")) ? "S" : "N");
      query.setString("host", antibiotic.get("host"));
      query.setString("sort", antibiotic.get("sort"));
      query.setString("class", antibiotic.get("class"));
      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }
    HashMap<String, String> newData = new HashMap<String, String>() {
      {
        put("CBA_IDBACANTIBIOTICO", antibiotic.get("id"));
        put("CBA_CODIGO", antibiotic.get("code").toString());
        put("CBA_DESCRIPCION", antibiotic.get("name").toString());
        put("CBA_ACTIVO", "true".equals(antibiotic.get("active")) ? "S" : "N");
        put("CBA_HOST", antibiotic.get("host").toString());
        put("CBA_SORT", antibiotic.get("sort").toString());
        put("CBA_IDANTIBIOTICOCLASE", antibiotic.get("class").toString());
      }
    };
    this.logUpdate(oldData, newData, "CFG_BACANTIBIOTICOS", antibiotic.get("id"), idUsuario);
  }

  public void createAntibiotic(HashMap<String, String> antibiotic) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("INSERT INTO BIOSLIS.CFG_BACANTIBIOTICOS " + "( CBA_CODIGO, "
          + "CBA_DESCRIPCION, " + "CBA_ACTIVO, " + "CBA_HOST, " + "CBA_SORT, " + "CBA_IDANTIBIOTICOCLASE ) "
          + "VALUES ( :code, :name, :active, :host, :sort, :class)");
      query.setString("code", antibiotic.get("code"));
      query.setString("name", antibiotic.get("name"));
      query.setString("active", "true".equals(antibiotic.get("active")) ? "S" : "N");
      query.setString("host", antibiotic.get("host"));
      query.setString("sort", antibiotic.get("sort"));
      query.setString("class", antibiotic.get("class"));
      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }
    List<HashMap<String, String>> abList = this.getABList(antibiotic.get("code").toString(),
        antibiotic.get("name").toString(), antibiotic.get("class").toString(), antibiotic.get("active").toString());
    String lastId = abList.get(abList.size() - 1).get("id");
    HashMap<String, String> changes = new HashMap<String, String>() {
      {
        put("CBA_IDBACANTIBIOTICO", lastId);
        put("CBA_CODIGO", antibiotic.get("code").toString());
        put("CBA_DESCRIPCION", antibiotic.get("name").toString());
        put("CBA_ACTIVO", "true".equals(antibiotic.get("active")) ? "S" : "N");
        put("CBA_HOST", antibiotic.get("host").toString());
        put("CBA_SORT", antibiotic.get("sort").toString());
        put("CBA_IDANTIBIOTICOCLASE", antibiotic.get("class").toString());
      }
    };
    this.logCreation(changes, "CFG_BACANTIBIOTICOS", lastId);
  }

  public void deleteAntibiotic(String id, String code) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "DELETE FROM BIOSLIS.CFG_BACANTIBIOTICOS WHERE CBA_IDBACANTIBIOTICO = :id AND CBA_CODIGO = :code");
      query.setString("id", id);
      query.setString("code", code);
      query.executeUpdate();
      session.getTransaction().commit();
    } finally {
      session.close();
    }
  }

  public List<HashMap<String, String>> getABList(String code, String name, String class_, String active_only) {
    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      ;
      String active_query = "true".equals(active_only) ? " AND CBA_ACTIVO = 'S'" : " ";
      String class_query = "".equals(class_) ? " " : " AND CBA_IDANTIBIOTICOCLASE LIKE '%" + class_ + "%' ";
      Query query = session.createSQLQuery(
          "SELECT CBA_IDBACANTIBIOTICO , CBA_CODIGO, CBA_DESCRIPCION, CBA_ACTIVO, CBA_IDANTIBIOTICOCLASE ,"
              + " nvl((select cbad_opcional from cfg_bacantibiogramasantibiot where cbad_idbacantibiotico = cba_idbacantibiotico FETCH FIRST 1 ROWS ONLY ), 'N') as opcional "
              + " FROM BIOSLIS.CFG_BACANTIBIOTICOS WHERE CBA_CODIGO LIKE '%" + code + "%' "
              + " AND CBA_DESCRIPCION LIKE '%" + name + "%' " + class_query + " " + active_query);
      List<Object> lista = query.list();
      session.close();
      for (Object abData : lista) {
        Object[] ab = (Object[]) abData;
        if (ab != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", ab[0].toString());
              put("code", ab[1].toString());
              put("name", ab[2].toString());
              put("active", ab[3].toString());
              put("class", ab[4] == null ? "" : ab[4].toString());
              put("opcional", ab[5].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }

    }
    return result;
  }

  public List<HashMap<String, String>> getABClassOptions() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    try {
      Query query = session.createSQLQuery(
          "SELECT LDVAC_IDANTIBIOTICOCLASE , LDVAC_DESCRIPCION " + "FROM BIOSLIS.LDV_BACANTIBIOTICOSCLASES");
      List<Object> lista = query.list();
      session.close();
      for (Object abData : lista) {
        Object[] abClass = (Object[]) abData;
        if (abClass != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", abClass[0].toString());
              put("name", abClass[1].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public List<HashMap<String, String>> getABResistanceList(String abid) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "SELECT CBAH_IDANTIBIOTICOHALO , CBAH_IDBACMICROORGANISMO , CBAH_IDBACANTIBIOTICO , CBAH_SENSIBLEVALOR ,"
              + " CBAH_SENSIBLEINTERMEDIOSIGNO , CBAH_RESISTENTEVALOR , CBAH_INTERMEDIORESISTENTESIGNO , CBAH_IDMETODOEVALUACIONR "
              + " FROM BIOSLIS.CFG_BACANTIBIOTICOSHALO " + " WHERE CBAH_IDBACANTIBIOTICO = :abId");
      query.setParameter("abId", abid);
      List<Object> lista = query.list();
      session.close();
      for (Object abData : lista) {
        Object[] abClass = (Object[]) abData;
        if (abClass != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", abClass[0].toString());
              put("moId", abClass[1].toString());
              put("abId", abClass[2].toString());
              put("sensible", abClass[3].toString());
              put("sensibleSign", abClass[4].toString());
              put("resistant", abClass[5].toString());
              put("resistantSign", abClass[6].toString());
              put("method", abClass[7].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public HashMap<String, String> getABResistance(String id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, String> result = new HashMap<String, String>();
    try {
      session.beginTransaction();
      Query query = session
          .createSQLQuery("SELECT  CBAH_IDBACMICROORGANISMO , CBAH_IDBACANTIBIOTICO , CBAH_SENSIBLEVALOR ,"
              + " CBAH_SENSIBLEINTERMEDIOSIGNO , CBAH_RESISTENTEVALOR , CBAH_INTERMEDIORESISTENTESIGNO , CBAH_IDMETODOEVALUACIONR "
              + " FROM BIOSLIS.CFG_BACANTIBIOTICOSHALO " + " WHERE CBAH_IDANTIBIOTICOHALO = :id");
      query.setParameter("id", id);
      Object[] abClass = (Object[]) (query.uniqueResult());
      if (abClass != null) {
        result.put("id", id);
        result.put("moId", abClass[0].toString());
        result.put("abId", abClass[1].toString());
        result.put("sensible", abClass[2].toString());
        result.put("sensibleSign", abClass[3].toString());
        result.put("resistant", abClass[4].toString());
        result.put("resistantSign", abClass[5].toString());
        result.put("method", abClass[6].toString());
      }

    } finally {
      session.close();
    }
    return result;
  }

  public HashMap<String, String> updateABResistance(HashMap<String, String> resistance, Long idUsuario) {
    HashMap<String, String> oldABR = this.getABResistance(resistance.get("id"));
    HashMap<String, String> oldData = new HashMap<String, String>() {
      {
        put("CBAH_IDANTIBIOTICOHALO", oldABR.get("id"));
        put("CBAH_IDBACMICROORGANISMO", oldABR.get("moId").toString());
        put("CBAH_IDBACANTIBIOTICO", oldABR.get("abId").toString());
        put("CBAH_SENSIBLEVALOR", oldABR.get("sensible"));
        put("CBAH_SENSIBLEINTERMEDIOSIGNO", oldABR.get("sensibleSign").toString());
        put("CBAH_RESISTENTEVALOR", oldABR.get("resistant").toString());
        put("CBAH_INTERMEDIORESISTENTESIGNO", oldABR.get("resistantSign").toString());
        put("CBAH_IDMETODOEVALUACIONR", oldABR.get("method").toString());
      }
    };
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("UPDATE BIOSLIS.CFG_BACANTIBIOTICOSHALO SET "
          + "BIOSLIS.CFG_BACANTIBIOTICOSHALO.CBAH_IDANTIBIOTICOHALO = :id, "
          + "BIOSLIS.CFG_BACANTIBIOTICOSHALO.CBAH_IDBACMICROORGANISMO = :moId, "
          + "BIOSLIS.CFG_BACANTIBIOTICOSHALO.CBAH_IDBACANTIBIOTICO = :abId, "
          + "BIOSLIS.CFG_BACANTIBIOTICOSHALO.CBAH_SENSIBLEVALOR = :sensible, "
          + "BIOSLIS.CFG_BACANTIBIOTICOSHALO.CBAH_SENSIBLEINTERMEDIOSIGNO = :sensibleSign, "
          + "BIOSLIS.CFG_BACANTIBIOTICOSHALO.CBAH_RESISTENTEVALOR = :resistant, "
          + "BIOSLIS.CFG_BACANTIBIOTICOSHALO.CBAH_INTERMEDIORESISTENTESIGNO = :resistantSign,  "
          + "BIOSLIS.CFG_BACANTIBIOTICOSHALO.CBAH_IDMETODOEVALUACIONR = :method "
          + "WHERE BIOSLIS.CFG_BACANTIBIOTICOSHALO.CBAH_IDANTIBIOTICOHALO = :id");
      query.setString("id", resistance.get("id"));
      query.setString("moId", resistance.get("moId"));
      query.setString("abId", resistance.get("abId"));
      query.setString("sensible", resistance.get("sensible"));
      query.setString("sensibleSign", resistance.get("sensibleSign"));
      query.setString("resistant", resistance.get("resistant"));
      query.setString("resistantSign", resistance.get("resistantSign"));
      query.setString("method", resistance.get("method"));
      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }
    HashMap<String, String> newData = new HashMap<String, String>() {
      {
        put("CBAH_IDANTIBIOTICOHALO", resistance.get("id"));
        put("CBAH_IDBACMICROORGANISMO", resistance.get("moId").toString());
        put("CBAH_IDBACANTIBIOTICO", resistance.get("abId").toString());
        put("CBAH_SENSIBLEVALOR", resistance.get("sensible"));
        put("CBAH_SENSIBLEINTERMEDIOSIGNO", resistance.get("sensibleSign").toString());
        put("CBAH_RESISTENTEVALOR", resistance.get("resistant").toString());
        put("CBAH_INTERMEDIORESISTENTESIGNO", resistance.get("resistantSign").toString());
        put("CBAH_IDMETODOEVALUACIONR", resistance.get("method").toString());
      }
    };
    this.logUpdate(oldData, newData, "CFG_BACANTIBIOTICOSHALO", resistance.get("id"), idUsuario);
    return resistance;
  }

  public HashMap<String, String> getCultureMedium(String id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, String> result = new HashMap<String, String>();
    try {
      Query query = session.createSQLQuery(
          "SELECT CBMC_IDMEDIOCULTIVO, CBMC_CODIGO , CBMC_DESCRIPCION, " + "CBMC_PREFIJO, CBMC_ACTIVO, CBMC_SORT FROM "
              + "BIOSLIS.CFG_BACMEDIOSCULTIVOS WHERE CBMC_IDMEDIOCULTIVO = :cmId");
      query.setParameter("cmId", id);
      Object[] cm = (Object[]) (query.uniqueResult());
      session.close();
      if (cm != null) {
        result.put("id", cm[0].toString());
        result.put("code", cm[1].toString());
        result.put("name", cm[2].toString());
        result.put("prefix", cm[3].toString());
        result.put("active", cm[4].toString());
        result.put("sort", cm[5].toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public List<HashMap<String, String>> getCMList(String code, String name, String active_only) {
    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      String active_query = "true".equals(active_only) ? "AND CBMC_ACTIVO = 'S'" : "";

      Query query = session.createSQLQuery("SELECT CBMC_IDMEDIOCULTIVO , CBMC_CODIGO, CBMC_DESCRIPCION , "
          + " CBMC_ACTIVO FROM BIOSLIS.CFG_BACMEDIOSCULTIVOS " + "WHERE CBMC_CODIGO LIKE '%" + code
          + "%' AND CBMC_DESCRIPCION LIKE '%" + name + "%' " + active_query);
      List<Object> lista = query.list();
      session.close();
      for (Object cmData : lista) {
        Object[] cm = (Object[]) cmData;
        if (cm != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", cm[0].toString());
              put("code", cm[1].toString());
              put("name", cm[2].toString());
              put("active", cm[3].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public void updateCultureMedium(HashMap<String, String> cultureMedium, Long idUsuario) {
    HashMap<String, String> oldCM = this.getCultureMedium(cultureMedium.get("id"));
    HashMap<String, String> oldData = new HashMap<String, String>() {
      {
        put("CBMC_IDMEDIOCULTIVO", oldCM.get("id"));
        put("CBMC_CODIGO", oldCM.get("code").toString());
        put("CBMC_DESCRIPCION", oldCM.get("name").toString());
        put("CBMC_ACTIVO", oldCM.get("active"));
        put("CBMC_PREFIJO", oldCM.get("prefix").toString());
        put("CBMC_SORT", oldCM.get("sort").toString());
      }
    };
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("UPDATE BIOSLIS.CFG_BACMEDIOSCULTIVOS SET "
          + "BIOSLIS.CFG_BACMEDIOSCULTIVOS.CBMC_CODIGO = :code, "
          + "BIOSLIS.CFG_BACMEDIOSCULTIVOS.CBMC_DESCRIPCION = :name, "
          + "BIOSLIS.CFG_BACMEDIOSCULTIVOS.CBMC_PREFIJO = :prefix, "
          + "BIOSLIS.CFG_BACMEDIOSCULTIVOS.CBMC_ACTIVO = :active, " + "BIOSLIS.CFG_BACMEDIOSCULTIVOS.CBMC_SORT = :sort "
          + "WHERE BIOSLIS.CFG_BACMEDIOSCULTIVOS.CBMC_IDMEDIOCULTIVO = :id");
      query.setString("id", cultureMedium.get("id"));
      query.setString("code", cultureMedium.get("code"));
      query.setString("name", cultureMedium.get("name"));
      query.setString("prefix", cultureMedium.get("prefix"));
      query.setString("active", "true".equals(cultureMedium.get("active")) ? "S" : "N");
      query.setString("sort", cultureMedium.get("sort"));
      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }
    HashMap<String, String> newData = new HashMap<String, String>() {
      {
        put("CBMC_IDMEDIOCULTIVO", cultureMedium.get("id"));
        put("CBMC_CODIGO", cultureMedium.get("code").toString());
        put("CBMC_DESCRIPCION", cultureMedium.get("name").toString());
        put("CBMC_ACTIVO", "true".equals(cultureMedium.get("active")) ? "S" : "N");
        put("CBMC_PREFIJO", cultureMedium.get("prefix").toString());
        put("CBMC_SORT", cultureMedium.get("sort").toString());
      }
    };
    this.logUpdate(oldData, newData, "CFG_BACMEDIOSCULTIVOS", cultureMedium.get("id"), idUsuario);
  }

  public void createCultureMedium(HashMap<String, String> cultureMedium) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "INSERT INTO BIOSLIS.CFG_BACMEDIOSCULTIVOS " + "( CBMC_CODIGO, " + "CBMC_DESCRIPCION, " + "CBMC_PREFIJO, "
              + "CBMC_ACTIVO, " + "CBMC_SORT ) " + "VALUES ( :code, :name, :prefix, :active, :sort )");
      query.setString("code", cultureMedium.get("code").toString());
      query.setString("name", cultureMedium.get("name").toString());
      query.setString("prefix", cultureMedium.get("prefix").toString());
      query.setString("active", "true".equals(cultureMedium.get("active")) ? "S" : "N");
      query.setString("sort", cultureMedium.get("sort").toString());
      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }
    List<HashMap<String, String>> cmList = this.getCMList(cultureMedium.get("code").toString(),
        cultureMedium.get("name").toString(), cultureMedium.get("active").toString());
    String lastId = cmList.get(cmList.size() - 1).get("id");
    HashMap<String, String> changes = new HashMap<String, String>() {
      {
        put("CBMC_IDMEDIOCULTIVO", lastId);
        put("CBMC_CODIGO", cultureMedium.get("code").toString());
        put("CBMC_DESCRIPCION", cultureMedium.get("name").toString());
        put("CBMC_ACTIVO", "true".equals(cultureMedium.get("active")) ? "S" : "N");
        put("CBMC_PREFIJO", cultureMedium.get("prefix").toString());
        put("CBMC_SORT", cultureMedium.get("sort").toString());
      }
    };
    this.logCreation(changes, "BIOSLIS.CFG_BACMEDIOSCULTIVOS", lastId);
  }

  public HashMap<String, String> getResistanceMethod(String id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, String> result = new HashMap<String, String>();
    try {

      Query query = session.createSQLQuery("SELECT CBMR_IDMARCADORRESISTENCIA, CBMR_CODIGO , CBMR_DESCRIPCION, "
          + "CBMR_ACTIVO, CBMR_HOST FROM BIOSLIS.CFG_BACMARCADORESRESISTENCIA WHERE CBMR_IDMARCADORRESISTENCIA = :rmId");
      query.setParameter("rmId", id);
      Object[] rm = (Object[]) (query.uniqueResult());
      session.close();
      if (rm != null) {
        result.put("id", rm[0].toString());
        result.put("code", rm[1].toString());
        result.put("name", rm[2].toString());
        result.put("active", rm[3].toString());
        result.put("host", rm[4] == null ? "" : rm[4].toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }

    }
    return result;
  }

  public List<HashMap<String, String>> getRMList(String code, String name, String active_only) {
    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      String active_query = "true".equals(active_only) ? "AND CBMR_ACTIVO = 'S'" : "";

      Query query = session.createSQLQuery("SELECT CBMR_IDMARCADORRESISTENCIA , CBMR_CODIGO, CBMR_DESCRIPCION , "
          + " CBMR_ACTIVO FROM BIOSLIS.CFG_BACMARCADORESRESISTENCIA " + "WHERE CBMR_CODIGO LIKE '%" + code
          + "%' AND CBMR_DESCRIPCION LIKE '%" + name + "%' " + active_query);
      List<Object> lista = query.list();
      session.close();
      for (Object rmData : lista) {
        Object[] rm = (Object[]) rmData;
        if (rm != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", rm[0].toString());
              put("code", rm[1].toString());
              put("name", rm[2].toString());
              put("active", rm[3].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }

    }
    return result;
  }

  public boolean updateResistanceMethod(HashMap<String, String> resistanceMethod, Long idUsuario) {
    boolean vali = false;
    HashMap<String, String> oldRM = this.getResistanceMethod(resistanceMethod.get("id"));
    HashMap<String, String> oldData = new HashMap<String, String>() {
      {
        put("CBMR_IDMARCADORRESISTENCIA", resistanceMethod.get("id"));
        put("CBMR_CODIGO", oldRM.get("code").toString());
        put("CBMR_DESCRIPCION", oldRM.get("name").toString());
        put("CBMR_ACTIVO", oldRM.get("active"));
        put("CBMR_HOST", oldRM.get("host").toString());
      }
    };
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      BigDecimal id = new BigDecimal(resistanceMethod.get("id"));
      if (validador.validarxIdSinAbreviado("CBMR_CODIGO", "CBMR_IDMARCADORRESISTENCIA", "CFG_BACMARCADORESRESISTENCIA",
          resistanceMethod.get("code"), id)) {
        session.beginTransaction();
        Query query = session.createSQLQuery("UPDATE BIOSLIS.CFG_BACMARCADORESRESISTENCIA SET "
            + "BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_CODIGO = :code, "
            + "BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_DESCRIPCION = :name, "
            + "BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_ACTIVO = :active, "
            + "BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_HOST = :host "
            + "WHERE BIOSLIS.CFG_BACMARCADORESRESISTENCIA.CBMR_IDMARCADORRESISTENCIA = :id");
        query.setString("id", resistanceMethod.get("id"));
        query.setString("code", resistanceMethod.get("code"));
        query.setString("name", resistanceMethod.get("name"));
        query.setString("active", "true".equals(resistanceMethod.get("active")) ? "S" : "N");
        query.setString("host", resistanceMethod.get("host"));
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        vali = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    } /*
       * TRAZABILIDAD HashMap<String, String> newData = new HashMap<String, String>()
       * { { put("CBMR_IDMARCADORRESISTENCIA", resistanceMethod.get("id"));
       * put("CBMR_CODIGO", resistanceMethod.get("code").toString());
       * put("CBMR_DESCRIPCION", resistanceMethod.get("name").toString());
       * put("CBMR_ACTIVO", "true".equals(resistanceMethod.get("active")) ? "S" :
       * "N"); put("CBMR_HOST", resistanceMethod.get("host").toString()); } };
       * this.logUpdate(oldData, newData, "CFG_BACMARCADORESRESISTENCIA",
       * resistanceMethod.get("id"), idUsuario);
       */
    return vali;
  }

  public boolean createResistanceMethod(HashMap<String, String> resistanceMethod) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    boolean vali = false;
    try {
      if (validador.validarCodigo("CBMR_CODIGO", "CFG_BACMARCADORESRESISTENCIA",
          resistanceMethod.get("code").toString())) {
        session.beginTransaction();
        Query query = session.createSQLQuery("INSERT INTO BIOSLIS.CFG_BACMARCADORESRESISTENCIA "
            + "(CBMR_CODIGO, CBMR_DESCRIPCION, CBMR_ACTIVO, CBMR_HOST ) " + "VALUES ( :code, :name, :active, :host )");
        query.setString("code", resistanceMethod.get("code").toString());
        query.setString("name", resistanceMethod.get("name").toString());
        query.setString("active", "true".equals(resistanceMethod.get("active")) ? "S" : "N");
        query.setString("host", resistanceMethod.get("host").toString());
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        vali = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    /*
     * TRAZABILIDAD List<HashMap<String, String>> rmList =
     * this.getRMList(resistanceMethod.get("code").toString(),
     * resistanceMethod.get("name").toString(),
     * resistanceMethod.get("active").toString()); String lastId =
     * rmList.get(rmList.size() - 1).get("id"); HashMap<String, String> changes =
     * new HashMap<String, String>() { { put("CBMR_IDMARCADORRESISTENCIA", lastId);
     * put("CBMR_CODIGO", resistanceMethod.get("code").toString());
     * put("CBMR_DESCRIPCION", resistanceMethod.get("name").toString());
     * put("CBMR_ACTIVO", "true".equals(resistanceMethod.get("active")) ? "S" :
     * "N"); put("CBMR_HOST", resistanceMethod.get("host").toString()); } };
     * this.logCreation(changes, "BIOSLIS.CFG_BACMARCADORESRESISTENCIA", lastId);
     */
    return vali;
  }

  public HashMap<String, String> getPruebaManual(String id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, String> result = new HashMap<String, String>();
    try {

      Query query = session.createSQLQuery("SELECT CBP_IDPRUEBA, CBP_CODIGO , CBP_DESCRIPCION, CBP_ACTIVO, "
          + "CBP_SORT FROM BIOSLIS.CFG_BACPRUEBAS WHERE CBP_IDPRUEBA = :pmId ");
      query.setParameter("pmId", id);
      Object[] pm = (Object[]) (query.uniqueResult());
      session.close();
      if (pm != null) {
        result.put("id", pm[0].toString());
        result.put("code", pm[1].toString());
        result.put("name", pm[2].toString());
        result.put("active", pm[3].toString());
        result.put("sort", pm[4] == null ? "" : pm[4].toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public void updatePruebaManual(HashMap<String, String> pruebaManual, Long idUsuario) {
    HashMap<String, String> oldPM = this.getPruebaManual(pruebaManual.get("id"));
    HashMap<String, String> oldData = new HashMap<String, String>() {
      {
        put("CBP_IDPRUEBA", oldPM.get("id"));
        put("CBP_CODIGO", oldPM.get("code").toString());
        put("CBP_DESCRIPCION", oldPM.get("name").toString());
        put("CBP_ACTIVO", oldPM.get("active"));
        put("CBP_SORT", oldPM.get("sort").toString());
        put("CBP_TIPO", "M");
      }
    };
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session
          .createSQLQuery("UPDATE BIOSLIS.CFG_BACPRUEBAS SET " + "BIOSLIS.CFG_BACPRUEBAS.CBP_CODIGO = :code, "
              + "BIOSLIS.CFG_BACPRUEBAS.CBP_DESCRIPCION = :name, " + "BIOSLIS.CFG_BACPRUEBAS.CBP_ACTIVO = :active, "
              + "BIOSLIS.CFG_BACPRUEBAS.CBP_SORT = :sort " + "WHERE BIOSLIS.CFG_BACPRUEBAS.CBP_IDPRUEBA = :id");
      query.setString("id", pruebaManual.get("id"));
      query.setString("code", pruebaManual.get("code"));
      query.setString("name", pruebaManual.get("name"));
      query.setString("active", "true".equals(pruebaManual.get("active")) ? "S" : "N");
      query.setString("sort", pruebaManual.get("sort"));
      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    HashMap<String, String> newData = new HashMap<String, String>() {
      {
        put("CBP_IDPRUEBA", pruebaManual.get("id"));
        put("CBP_CODIGO", pruebaManual.get("code").toString());
        put("CBP_DESCRIPCION", pruebaManual.get("name").toString());
        put("CBP_ACTIVO", "true".equals(pruebaManual.get("active")) ? "S" : "N");
        put("CBP_SORT", pruebaManual.get("sort").toString());
        put("CBP_TIPO", "M");
      }
    };
    this.logUpdate(oldData, newData, "CFG_BACPRUEBAS", pruebaManual.get("id"), idUsuario);
  }

  public void createPruebaManual(HashMap<String, String> pruebaManual) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery(
          "INSERT INTO BIOSLIS.CFG_BACPRUEBAS " + "( CBP_CODIGO, CBP_DESCRIPCION, CBP_ACTIVO, CBP_SORT, CBP_TIPO ) "
              + "VALUES ( :code, :name, :active, :sort, :tipo )");
      query.setString("code", pruebaManual.get("code").toString());
      query.setString("name", pruebaManual.get("name").toString());
      query.setString("active", "true".equals(pruebaManual.get("active")) ? "S" : "N");
      query.setString("sort", pruebaManual.get("sort").toString());
      query.setString("tipo", "M");
      query.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    List<HashMap<String, String>> pmList = this.getPMList(pruebaManual.get("code").toString(),
        pruebaManual.get("name").toString(), pruebaManual.get("active").toString());
    String lastId = pmList.get(pmList.size() - 1).get("id");
    HashMap<String, String> changes = new HashMap<String, String>() {
      {
        put("CBP_IDPRUEBA", lastId);
        put("CBP_CODIGO", pruebaManual.get("code").toString());
        put("CBP_DESCRIPCION", pruebaManual.get("name").toString());
        put("CBP_ACTIVO", "true".equals(pruebaManual.get("active")) ? "S" : "N");
        put("CBP_SORT", pruebaManual.get("sort").toString());
        put("CBP_TIPO", "M");
      }
    };
    this.logCreation(changes, "BIOSLIS.CFG_BACPRUEBAS", lastId);
  }

  public List<HashMap<String, String>> getPMList(String code, String name, String active_only) {
    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {

      String active_query = "true".equals(active_only) ? "AND CBP_ACTIVO = 'S'" : "";

      Query query = session.createSQLQuery("SELECT CBP_IDPRUEBA , CBP_CODIGO, CBP_DESCRIPCION , "
          + " CBP_ACTIVO FROM BIOSLIS.CFG_BACPRUEBAS " + " WHERE ( CBP_TIPO = 'M' OR CBP_TIPO = 'A' ) "
          + " AND CBP_CODIGO LIKE '%" + code + "%' AND CBP_DESCRIPCION LIKE '%" + name + "%' " + active_query);
      List<Object> lista = query.list();
      session.close();
      for (Object pmData : lista) {
        Object[] pm = (Object[]) pmData;
        if (pm != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", pm[0].toString());
              put("code", pm[1].toString());
              put("name", pm[2].toString());
              put("active", pm[3].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public HashMap<String, String> getBodyArea(String id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, String> result = new HashMap<String, String>();
    try {

      Query query = session.createSQLQuery("SELECT CBZC_IDZONACUERPO, CBZC_DESCRIPCION, CBZC_ACTIVO, " // CBZC_CODIGO
          // ,
          + "CBZC_SORT FROM BIOSLIS.CFG_BACZONACUERPO WHERE CBZC_IDZONACUERPO = :baId ");
      query.setParameter("baId", id);
      Object[] ba = (Object[]) (query.uniqueResult());
      if (ba != null) {
        result.put("id", ba[0].toString());
        result.put("code", "");
        result.put("name", ba[1].toString());
        result.put("active", ba[2].toString());
        result.put("sort", ba[3] == null ? "" : ba[3].toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public boolean updateBodyArea(HashMap<String, String> bodyArea, Long idUsuario) {
    boolean vali = false;
    HashMap<String, String> oldBA = this.getBodyArea(bodyArea.get("id"));
    HashMap<String, String> oldData = new HashMap<String, String>() {
      {
        put("CBZC_IDZONACUERPO", oldBA.get("id"));
        put("CBZC_DESCRIPCION", oldBA.get("name").toString());
        put("CBZC_ACTIVO", oldBA.get("active"));
        put("CBZC_SORT", oldBA.get("sort").toString());
      }
    };
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      BigDecimal id = new BigDecimal(bodyArea.get("id"));
      if (validador.validarxIdSinAbreviado("CBZC_DESCRIPCION", "CBZC_IDZONACUERPO", "CFG_BACZONACUERPO",
          bodyArea.get("name"), id)) {
        session.beginTransaction();
        Query query = session.createSQLQuery("UPDATE BIOSLIS.CFG_BACZONACUERPO SET "// +
            // "BIOSLIS.CFG_BACZONACUERPO.CBZC_CODIGO
            // = :code, "
            + "BIOSLIS.CFG_BACZONACUERPO.CBZC_DESCRIPCION = :name, "
            + "BIOSLIS.CFG_BACZONACUERPO.CBZC_ACTIVO = :active, " + "BIOSLIS.CFG_BACZONACUERPO.CBZC_SORT = :sort "
            + "WHERE BIOSLIS.CFG_BACZONACUERPO.CBZC_IDZONACUERPO = :id");
        query.setString("id", bodyArea.get("id"));
        // query.setString("code", bodyArea.get("code"));
        query.setString("name", bodyArea.get("name"));
        query.setString("active", "true".equals(bodyArea.get("active")) ? "S" : "N");
        query.setString("sort", bodyArea.get("sort"));
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        vali = true;
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    } /*
       * TRAZABILIDAD HashMap<String, String> newData = new HashMap<String, String>()
       * { { put("CBZC_IDZONACUERPO", bodyArea.get("id")); put("CBZC_DESCRIPCION",
       * bodyArea.get("name").toString()); put("CBZC_ACTIVO",
       * "true".equals(bodyArea.get("active")) ? "S" : "N"); put("CBZC_SORT",
       * bodyArea.get("sort").toString()); } }; this.logUpdate(oldData, newData,
       * "CFG_BACZONACUERPO", bodyArea.get("id"), idUsuario);
       */
    return vali;
  }

  public boolean createBodyArea(HashMap<String, String> bodyArea) {
    boolean vali = false;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      if (validador.validarCodigo("CBZC_DESCRIPCION", "CFG_BACZONACUERPO", bodyArea.get("name").toString())) {
        session.beginTransaction();
        Query query = session
            .createSQLQuery("INSERT INTO BIOSLIS.CFG_BACZONACUERPO " + "( CBZC_DESCRIPCION, CBZC_ACTIVO, CBZC_SORT ) " // CBZC_CODIGO,
                + "VALUES ( :name, :active, :sort )"); // , :code
        // query.setString("code", bodyArea.get("code").toString());
        query.setString("name", bodyArea.get("name").toString());
        query.setString("active", "true".equals(bodyArea.get("active")) ? "S" : "N");
        query.setString("sort", bodyArea.get("sort").toString());
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        vali = true;
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    /*
     * TRAZABILIDAD List<HashMap<String, String>> baList =
     * this.getBAList(bodyArea.get("code").toString(),
     * bodyArea.get("name").toString(), bodyArea.get("active").toString()); String
     * lastId = baList.get(baList.size() - 1).get("id"); HashMap<String, String>
     * changes = new HashMap<String, String>() { { put("CBZC_IDZONACUERPO", lastId);
     * put("CBZC_DESCRIPCION", bodyArea.get("name").toString()); put("CBZC_ACTIVO",
     * "true".equals(bodyArea.get("active")) ? "S" : "N"); put("CBZC_SORT",
     * bodyArea.get("sort").toString()); } }; this.logCreation(changes,
     * "BIOSLIS.CFG_BACZONACUERPO", lastId);
     */
    return vali;
  }

  public List<HashMap<String, String>> getBAList(String code, String name, String active_only) {
    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {

      String active_query = "true".equals(active_only) ? "AND CBZC_ACTIVO = 'S'" : "";

      Query query = session.createSQLQuery("SELECT CBZC_IDZONACUERPO , CBZC_DESCRIPCION , " // , CBP_CODIGO
          + " CBZC_ACTIVO FROM BIOSLIS.CFG_BACZONACUERPO " + " WHERE " // CBP_CODIGO LIKE '%" + code + "%' AND
          + " CBZC_DESCRIPCION LIKE '%" + name + "%' " + active_query
          + " ORDER BY CBZC_SORT ASC, CBZC_DESCRIPCION ASC");
      List<Object> lista = query.list();
      session.close();
      for (Object pmData : lista) {
        Object[] pm = (Object[]) pmData;
        if (pm != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", pm[0].toString());
              put("code", "");
              put("name", pm[1].toString());
              put("active", pm[2].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public HashMap<String, String> getColonyCount(String id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    HashMap<String, String> result = new HashMap<String, String>();
    try {

      Query query = session
          .createSQLQuery("SELECT CBRC_IDBACRECUENTOCOLONIA, CBRC_CODIGO, CBRC_DESCRIPCION, CBRC_ACTIVO, "
              + "CBRC_SORT, CBRC_HOST FROM BIOSLIS.CFG_BACRECUENTOCOLONIAS WHERE CBRC_IDBACRECUENTOCOLONIA = :ccId ");
      query.setParameter("ccId", id);
      Object[] cc = (Object[]) (query.uniqueResult());
      session.close();
      if (cc != null) {
        result.put("id", cc[0].toString());
        result.put("code", cc[1].toString());
        result.put("name", cc[2].toString());
        result.put("active", cc[3].toString());
        result.put("sort", cc[4] == null ? "" : cc[4].toString());
        result.put("host", cc[5] == null ? "" : cc[5].toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public boolean updateColonyCount(HashMap<String, String> colonyCount, Long idUsuario) {
    boolean vali = false;
    HashMap<String, String> oldBA = this.getColonyCount(colonyCount.get("id"));
    HashMap<String, String> oldData = new HashMap<String, String>() {
      {
        put("CBRC_IDBACRECUENTOCOLONIA", oldBA.get("id"));
        put("CBRC_CODIGO", oldBA.get("code").toString());
        put("CBRC_DESCRIPCION", oldBA.get("name").toString());
        put("CBRC_ACTIVO", oldBA.get("active"));
        put("CBRC_SORT", oldBA.get("sort").toString());
        put("CBRC_HOST", oldBA.get("host").toString());
      }
    };
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      BigDecimal id = new BigDecimal(colonyCount.get("id"));
      if (validador.validarxId("CBRC_CODIGO", "CBRC_IDBACRECUENTOCOLONIA", "CBRC_DESCRIPCION",
          "CFG_BACRECUENTOCOLONIAS", colonyCount.get("code").toString(), colonyCount.get("name").toString(), id)) {
        session.beginTransaction();
        Query query = session.createSQLQuery(
            "UPDATE BIOSLIS.CFG_BACRECUENTOCOLONIAS SET " + "BIOSLIS.CFG_BACRECUENTOCOLONIAS.CBRC_CODIGO = :code, "
                + "BIOSLIS.CFG_BACRECUENTOCOLONIAS.CBRC_DESCRIPCION = :name, "
                + "BIOSLIS.CFG_BACRECUENTOCOLONIAS.CBRC_ACTIVO = :active, "
                + "BIOSLIS.CFG_BACRECUENTOCOLONIAS.CBRC_SORT = :sort, "
                + "BIOSLIS.CFG_BACRECUENTOCOLONIAS.CBRC_HOST = :host "
                + "WHERE BIOSLIS.CFG_BACRECUENTOCOLONIAS.CBRC_IDBACRECUENTOCOLONIA = :id");
        query.setString("id", colonyCount.get("id"));
        query.setString("code", colonyCount.get("code"));
        query.setString("name", colonyCount.get("name"));
        query.setString("active", "true".equals(colonyCount.get("active")) ? "S" : "N");
        query.setString("sort", colonyCount.get("sort"));
        query.setString("host", colonyCount.get("host"));
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        vali = true;
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    } /*
       * TRAZABILIDAD HashMap<String, String> newData = new HashMap<String, String>()
       * { { put("CBRC_IDBACRECUENTOCOLONIA", colonyCount.get("id"));
       * put("CBRC_CODIGO", colonyCount.get("code").toString());
       * put("CBRC_DESCRIPCION", colonyCount.get("name").toString());
       * put("CBRC_ACTIVO", "true".equals(colonyCount.get("active")) ? "S" : "N");
       * put("CBRC_SORT", colonyCount.get("sort").toString()); put("CBRC_HOST",
       * colonyCount.get("host").toString()); } }; this.logUpdate(oldData, newData,
       * "CFG_BACRECUENTOCOLONIAS", colonyCount.get("id"), idUsuario);
       */
    return vali;
  }

  public boolean createColonyCount(HashMap<String, String> colonyCount) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    boolean vali = false;
    try {
      if (validador.validarCodigoyAbreviado("CBRC_CODIGO", "CBRC_DESCRIPCION", "CFG_BACRECUENTOCOLONIAS",
          colonyCount.get("code").toString(), colonyCount.get("name").toString())) {
        session.beginTransaction();
        Query query = session.createSQLQuery("INSERT INTO BIOSLIS.CFG_BACRECUENTOCOLONIAS "
            + "( CBRC_CODIGO, CBRC_DESCRIPCION, CBRC_ACTIVO, CBRC_SORT, CBRC_HOST ) "
            + "VALUES ( :code, :name, :active, :sort, :host )");
        query.setString("code", colonyCount.get("code").toString());
        query.setString("name", colonyCount.get("name").toString());
        query.setString("active", "true".equals(colonyCount.get("active")) ? "S" : "N");
        query.setString("sort", colonyCount.get("sort").toString());
        query.setString("host", colonyCount.get("host").toString());
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        vali = true;
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    } /*
       * TRAZABILIDAD List<HashMap<String, String>> ccList =
       * this.getCCList(colonyCount.get("code").toString(),
       * colonyCount.get("name").toString(), colonyCount.get("active").toString());
       * String lastId = ccList.get(ccList.size() - 1).get("id"); HashMap<String,
       * String> changes = new HashMap<String, String>() { {
       * put("CBRC_IDBACRECUENTOCOLONIA", lastId); put("CBRC_CODIGO",
       * colonyCount.get("code").toString()); put("CBRC_DESCRIPCION",
       * colonyCount.get("name").toString()); put("CBRC_ACTIVO",
       * "true".equals(colonyCount.get("active")) ? "S" : "N"); put("CBRC_SORT",
       * colonyCount.get("sort").toString()); put("CBRC_HOST",
       * colonyCount.get("host").toString()); } }; this.logCreation(changes,
       * "BIOSLIS.CFG_BACRECUENTOCOLONIAS", lastId);
       */
    return vali;
  }

  public List<HashMap<String, String>> getCCList(String code, String name, String active_only) {
    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      String active_query = "true".equals(active_only) ? "AND CBRC_ACTIVO = 'S'" : "";

      Query query = session.createSQLQuery("SELECT CBRC_IDBACRECUENTOCOLONIA , CBRC_CODIGO , CBRC_DESCRIPCION , "
          + " CBRC_ACTIVO FROM BIOSLIS.CFG_BACRECUENTOCOLONIAS " + " WHERE  CBRC_CODIGO LIKE '%" + code
          + "%' AND CBRC_DESCRIPCION LIKE '%" + name + "%' " + active_query);
      List<Object> lista = query.list();
      session.close();

      for (Object pmData : lista) {
        Object[] pm = (Object[]) pmData;
        if (pm != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", pm[0].toString());
              put("code", pm[1].toString());
              put("name", pm[2].toString());
              put("active", pm[3].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }

    }
    return result;
  }

  public HashMap<String, Object> getAntibiogram(String id) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    HashMap<String, Object> result = new HashMap<String, Object>();
    try {
      Query query = session.createSQLQuery("SELECT CBA_IDBACANTIBIOGRAMA, CBA_CODIGO, CBA_DESCRIPCION, CBA_ACTIVO, "
          + "CBA_SORT, CBA_HOST FROM BIOSLIS.CFG_BACANTIBIOGRAMAS WHERE CBA_IDBACANTIBIOGRAMA = :agId ");
      query.setParameter("agId", id);
      Object[] ag = (Object[]) (query.uniqueResult());
      if (ag != null) {
        result.put("id", ag[0].toString());
        result.put("code", ag[1].toString());
        result.put("name", ag[2].toString());
        result.put("active", ag[3].toString());
        result.put("sort", ag[4] == null ? "" : ag[4].toString());
        result.put("host", ag[5] == null ? "" : ag[5].toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }

    Session sessionAb = HibernateUtil.getSessionFactory().openSession();
    try {
      ArrayList<HashMap<String, String>> antibiotics = new ArrayList<HashMap<String, String>>();
      Query queryAb = sessionAb.createSQLQuery(
          "SELECT aga.CBAD_IDBACANTIBIOTICO, " + " aga.CBAD_ACTIVO, " + " ab.CBA_DESCRIPCION, " + " aga.CBAD_OPCIONAL "
              + " FROM BIOSLIS.CFG_BACANTIBIOTICOS ab" + " LEFT JOIN BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT aga "
              + " ON ( aga.CBAD_IDBACANTIBIOTICO = ab.CBA_IDBACANTIBIOTICO )"
              + " WHERE aga.CBAD_IDBACANTIBIOGRAMA = :agId ");
      queryAb.setParameter("agId", id);
      List<Object> lista = queryAb.list();
      sessionAb.close();
      for (Object abData : lista) {
        Object[] ab = (Object[]) abData;
        if (ab != null && "S".equals(ab[1])) {
          antibiotics.add(new HashMap<String, String>() {
            {
              put("id", ab[0].toString());
              put("name", ab[2].toString());
              put("optional", ab[3].toString());
            }
          });
        }
      }
      result.put("antibiotics", antibiotics);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (sessionAb.isOpen()) {
        sessionAb.close();
      }

    }
    return result;
  }

  public boolean updateAntibiogram(HashMap<String, String> antibiogram, Long idUsuario) {
    boolean vali = false;
    HashMap<String, Object> oldAG = this.getAntibiogram(antibiogram.get("id"));
    HashMap<String, String> oldData = new HashMap<String, String>() {
      {
        put("CBA_IDBACANTIBIOGRAMA", oldAG.get("id").toString());
        put("CBA_CODIGO", oldAG.get("code").toString());
        put("CBA_DESCRIPCION", oldAG.get("name").toString());
        put("CBA_ACTIVO", oldAG.get("active").toString());
        put("CBA_SORT", oldAG.get("sort").toString());
        put("CBA_HOST", oldAG.get("host").toString());
      }
    };
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      if (validador.validarxId("CBA_CODIGO", "CBA_IDBACANTIBIOGRAMA", "CBA_DESCRIPCION", "CFG_BACANTIBIOGRAMAS",
          antibiogram.get("code").toString(), antibiogram.get("name").toString(),
          new BigDecimal(antibiogram.get("id")))) {
        session.beginTransaction();
        Query query = session.createSQLQuery(
            "UPDATE BIOSLIS.CFG_BACANTIBIOGRAMAS SET " + "BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_CODIGO = :code, "
                + "BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_DESCRIPCION = :name, "
                + "BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_ACTIVO = :active, "
                + "BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_SORT = :sort, " + "BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_HOST = :host "
                + "WHERE BIOSLIS.CFG_BACANTIBIOGRAMAS.CBA_IDBACANTIBIOGRAMA = :id");
        query.setString("id", antibiogram.get("id"));
        query.setString("code", antibiogram.get("code"));
        query.setString("name", antibiogram.get("name"));
        query.setString("active", "true".equals(antibiogram.get("active")) ? "S" : "N");
        query.setString("sort", antibiogram.get("sort"));
        query.setString("host", antibiogram.get("host"));
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        vali = true;
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    } /*
       * TRAZABILIDAD HashMap<String, String> newData = new HashMap<String, String>()
       * { { put("CBA_IDBACANTIBIOGRAMA", antibiogram.get("id")); put("CBA_CODIGO",
       * antibiogram.get("code").toString()); put("CBA_DESCRIPCION",
       * antibiogram.get("name").toString()); put("CBA_ACTIVO",
       * "true".equals(antibiogram.get("active")) ? "S" : "N"); put("CBA_SORT",
       * antibiogram.get("sort").toString()); put("CBA_HOST",
       * antibiogram.get("host").toString()); } }; this.logUpdate(oldData, newData,
       * "CFG_BACANTIBIOGRAMAS", antibiogram.get("id"), idUsuario);
       */
    return vali;
  }

  public boolean createAntibiogram(HashMap<String, String> antibiogram) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    boolean vali = false;
    try {
      if (validador.validarCodigoyAbreviado("CBA_CODIGO", "CBA_DESCRIPCION", "CFG_BACANTIBIOGRAMAS",
          antibiogram.get("code").toString(), antibiogram.get("name").toString())) {
        session.beginTransaction();
        Query query = session.createSQLQuery("INSERT INTO BIOSLIS.CFG_BACANTIBIOGRAMAS "
            + "( CBA_CODIGO, CBA_DESCRIPCION, CBA_ACTIVO, CBA_SORT, CBA_HOST ) "
            + "VALUES ( :code, :name, :active, :sort, :host )");
        query.setString("code", antibiogram.get("code").toString());
        query.setString("name", antibiogram.get("name").toString());
        query.setString("active", "true".equals(antibiogram.get("active")) ? "S" : "N");
        query.setString("sort", antibiogram.get("sort").toString());
        query.setString("host", antibiogram.get("host").toString());
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        vali = true;
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }

    } /*
       * TRAZABILIDAD List<HashMap<String, String>> agList =
       * this.getAGList(antibiogram.get("code").toString(),
       * antibiogram.get("name").toString(), antibiogram.get("active").toString());
       * String lastId = agList.get(agList.size() - 1).get("id"); HashMap<String,
       * String> changes = new HashMap<String, String>() { {
       * put("CBA_IDBACANTIBIOGRAMA", lastId); put("CBA_CODIGO",
       * antibiogram.get("code").toString()); put("CBA_DESCRIPCION",
       * antibiogram.get("name").toString()); put("CBA_ACTIVO",
       * "true".equals(antibiogram.get("active")) ? "S" : "N"); put("CBA_SORT",
       * antibiogram.get("sort").toString()); put("CBA_HOST",
       * antibiogram.get("host").toString()); } }; this.logCreation(changes,
       * "BIOSLIS.CFG_BACANTIBIOGRAMAS", lastId);
       */
    return vali;
  }

  public List<HashMap<String, String>> getAGList(String code, String name, String active_only) {
    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {

      String active_query = "true".equals(active_only) ? "AND CBA_ACTIVO = 'S'" : "";

      Query query = session.createSQLQuery("SELECT CBA_IDBACANTIBIOGRAMA , CBA_CODIGO , CBA_DESCRIPCION , "
          + " CBA_ACTIVO FROM BIOSLIS.CFG_BACANTIBIOGRAMAS " + " WHERE  CBA_CODIGO LIKE '%" + code
          + "%' AND CBA_DESCRIPCION LIKE '%" + name + "%' " + active_query);

      List<Object> lista = query.list();
      for (Object agData : lista) {
        Object[] ag = (Object[]) agData;
        if (ag != null) {
          result.add(new HashMap<String, String>() {
            {
              put("id", ag[0].toString());
              put("code", ag[1].toString());
              put("name", ag[2].toString());
              put("active", ag[3].toString());
            }
          });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }
    return result;
  }

  public void clearAntibiogramAntibiotics(String antibiogramId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      session.beginTransaction();
      Query query = session.createSQLQuery("UPDATE BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT SET "
          + "CBAD_ACTIVO = :abActive  WHERE CBAD_IDBACANTIBIOGRAMA = :agId");
      query.setString("agId", antibiogramId);
      query.setString("abActive", "N");
      query.executeUpdate();
      session.getTransaction().commit();
    } finally {
      session.close();
    }
  }

  public void updateAntibiogramAntibiotic(String antibiogramId, String antibioticId, String optional) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    int abExists = 0;
    try {
      Query query = session.createSQLQuery("SELECT CBAD_IDBACANTIBIOGRAMA FROM BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT "
          + " WHERE CBAD_IDBACANTIBIOGRAMA = :agId AND CBAD_IDBACANTIBIOTICO = :abId");
      query.setParameter("agId", antibiogramId);
      query.setParameter("abId", antibioticId);
      abExists = query.list().size();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session.isOpen()) {
        session.close();
      }
    }

    session = HibernateUtil.getSessionFactory().openSession();
    if (abExists > 0) {
      try {
        session.beginTransaction();
        Query query = session.createSQLQuery("UPDATE BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT SET "
            + "BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT.CBAD_IDBACANTIBIOGRAMA = :agId, "
            + "BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT.CBAD_IDBACANTIBIOTICO = :abId, "
            + "BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT.CBAD_ACTIVO = :abActive, "
            + "BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT.CBAD_OPCIONAL = :abOptional, "
            + "BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT.CBAD_SORT = 1 "
            + "WHERE CBAD_IDBACANTIBIOGRAMA = :agId AND CBAD_IDBACANTIBIOTICO = :abId");
        query.setString("agId", antibiogramId);
        query.setString("abId", antibioticId);
        query.setString("abOptional", optional.equals("true") ? "S" : "N");
        query.setString("abActive", "S");
        query.executeUpdate();
        session.getTransaction().commit();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (session.isOpen()) {
          session.close();
        }

      }
    } else {
      try {
        session.beginTransaction();
        Query query = session.createSQLQuery("INSERT INTO BIOSLIS.CFG_BACANTIBIOGRAMASANTIBIOT "
            + "(CBAD_IDBACANTIBIOGRAMA, CBAD_IDBACANTIBIOTICO, CBAD_ACTIVO, CBAD_OPCIONAL, CBAD_SORT ) "
            + "VALUES (:agId, :abId, :active, :abOptional, :sort )");
        query.setString("agId", antibiogramId);
        query.setString("abId", antibioticId);
        query.setString("active", "S");
        query.setString("abOptional", optional.equals("true") ? "S" : "N");
        query.setString("sort", "1");
        query.executeUpdate();
        session.getTransaction().commit();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (session.isOpen()) {
          session.close();
        }

      }
    }

  }

  private void logCreation(HashMap<String, String> data, String table, String id) {
    String date = "TO_DATE('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())
        + "','YYYY-MM-DD HH24:MI:SS')";
    String queryStr = "INSERT INTO BIOSLIS.LOG_CFGBACTABLAS (LCBT_NOMBRETABLA, LCBT_NOMBRECAMPO, "
        + " LCBT_IDACCIONDATO, LCBT_VALORNUEVO, LCBT_IDUSUARIO, LCBT_FECHAMODIFICACION, LCBT_IPUSUARIO, "
        + " LCBT_NOMBREEQUIPO, LCBT_IDTABLA) VALUES ";
    for (HashMap.Entry<String, String> entry : data.entrySet()) {
      Session session = HibernateUtil.getSessionFactory().openSession();
      try {
        session.beginTransaction();
        Query query = session.createSQLQuery(queryStr + " ('" + table + "', '" + entry.getKey() + "', '1', '"
            + entry.getValue() + "', '1', " + date + ", '1.1.1.1', '1','" + id + "')");
        query.executeUpdate();
        Transaction transaction = session.getTransaction();
        if (!transaction.wasCommitted())
          transaction.commit();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        session.close();
      }
    }
  }

  private void logUpdate(HashMap<String, String> prevData, HashMap<String, String> newData, String table, String id,
      Long idUsuario) {
    String date = "TO_DATE('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())
        + "','YYYY-MM-DD HH24:MI:SS')";
    String queryStr = "INSERT INTO BIOSLIS.LOG_CFGBACTABLAS (LCBT_NOMBRETABLA, LCBT_NOMBRECAMPO, "
        + " LCBT_IDACCIONDATO, LCBT_VALORANTERIOR, LCBT_VALORNUEVO, LCBT_IDUSUARIO, LCBT_FECHAMODIFICACION, LCBT_IPUSUARIO, "
        + " LCBT_NOMBREEQUIPO, LCBT_IDTABLA) VALUES ";
    for (HashMap.Entry<String, String> entry : newData.entrySet()) {
      if (entry.getValue().equals(prevData.get(entry.getKey())))
        continue;
      Session session = HibernateUtil.getSessionFactory().openSession();

      try {
        session.beginTransaction();
        Query query = session.createSQLQuery(queryStr + " ('" + table + "', '" + entry.getKey() + "', '2', '"
            + prevData.get(entry.getKey()) + "', '" + entry.getValue() + "' , ' " + idUsuario + " ',  " // "',
            // '1',
            // "
            + date + ", '1.1.1.1', '1','" + id + "')");
        query.executeUpdate();
        Transaction transaction = session.getTransaction();
        if (!transaction.wasCommitted())
          transaction.commit();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        session.close();
      }
    }
  }
}
