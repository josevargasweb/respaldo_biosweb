package com.grupobios.bioslis.bs;

import java.util.List;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.OrdenInformeResultadoDTO;

public interface CapturaResultadosService {

  List<OrdenInformeResultadoDTO> getOrdenesUltimosNDias(Integer rangoultimosdias)
      throws BiosLISException, BiosLISDAOException;

}
