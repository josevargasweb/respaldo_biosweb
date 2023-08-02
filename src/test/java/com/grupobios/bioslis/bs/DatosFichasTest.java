package com.grupobios.bioslis.bs;

import org.junit.jupiter.api.Test;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;

class DatosFichasTest {
  /*
   * @Test void testCapturaResultadosMuestras() {
   * 
   * List<ExamenesMuestrasDeUnaOrdenDTO> lst = null; Session sesion =
   * HibernateUtil.getSessionFactory().openSession(); Query qry =
   * sesion.createSQLQuery(CapturaResultadosDAO.sqlExamenesMuestrasDeUnaOrden)
   * .setResultTransformer(Transformers.aliasToBean(ExamenesMuestrasDeUnaOrdenDTO.
   * class)); qry.setParameter("nOrden", 184); lst =
   * (List<ExamenesMuestrasDeUnaOrdenDTO>) qry.list();
   * 
   * lst.forEach((e) -> { System.out.println(e); }); sesion.close(); assert
   * (lst.size() > 0);
   * 
   * }

  @Test
  void eventosFichas() throws BiosLISDAONotFoundException, BiosLISDAOException {

    LogEventosfichasDAO lefDao = new LogEventosfichasDAO();

    lefDao.getLogUltimosEventosOrden(366L, "DATOS_FICHASEXAMENESTEST", 36L, 43L, null);

  }
   */

}
