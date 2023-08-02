package com.grupobios.bioslis.bs;

import java.util.List;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CapturaResultadosDAO;
import com.grupobios.bioslis.dto.OrdenInformeResultadoDTO;

public class CapturaResultadosServiceImpl implements CapturaResultadosService {

  public CapturaResultadosServiceImpl() {
  }

  private CapturaResultadosDAO capturaResultadosDAO;

  public CapturaResultadosDAO getCapturaResultadosDAO() {
    return capturaResultadosDAO;
  }

  public void setCapturaResultadosDAO(CapturaResultadosDAO capturaResultadosDAO) {
    this.capturaResultadosDAO = capturaResultadosDAO;
  }

  @Override
  public List<OrdenInformeResultadoDTO> getOrdenesUltimosNDias(Integer rangoultimosdias)
      throws BiosLISException, BiosLISDAOException {
    return capturaResultadosDAO.getOrdenesUltimosNDias(rangoultimosdias);
  }

}
