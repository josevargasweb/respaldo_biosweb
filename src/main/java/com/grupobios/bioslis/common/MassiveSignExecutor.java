package com.grupobios.bioslis.common;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.DatosFichasExamenesDAO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;

public class MassiveSignExecutor implements ActionExamenExecutor {

  @Autowired
  private DatosFichasExamenesDAO datosFichasExamenesDAO;

  @Override
  public void exec(List<ExamenOrdenDTO> listaExamenes, Long idUsuario) {

    List<DatosFichasexamenestest> lstDfet = null;

//    for (ExamenOrdenDTO examenOrdenDTO : listaExamenes) {
//
//      lstDfet = datosFichasExamenesDAO.getResultados(examenOrdenDTO.getDFE_NORDEN(), examenOrdenDTO.getDFE_IDEXAMEN());
//
//    }
  }

  public DatosFichasExamenesDAO getDatosFichasExamenesDAO() {
    return datosFichasExamenesDAO;
  }

  public void setDatosFichasExamenesDAO(DatosFichasExamenesDAO datosFichasExamenesDAO) {
    this.datosFichasExamenesDAO = datosFichasExamenesDAO;
  }

  @Override
  public void exec(ResultadoNumericoTestExamenOrdenDTO dfetId, Long idUsuario) throws BiosLISDAOException,
      BiosLISException, BiosLISDAONotFoundException, BiosLisExamenSignerException, IOException {
    // TODO Auto-generated method stub

  }

}
