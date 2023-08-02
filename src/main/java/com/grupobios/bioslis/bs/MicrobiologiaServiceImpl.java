package com.grupobios.bioslis.bs;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.AntibiogramaMicrobiologiaDTO;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.OrdenExamenCapturaResultadoDTO;
import com.grupobios.bioslis.dto.OrdenInformeResultadoDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenOrden;
import com.grupobios.bioslis.microbiologia.dao.MicrobiologiaOrdenDAO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaActionsDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaExamDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaOrdenPacienteDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaTestDTO;

//se crea , pero por el momento no se esta utilizando  **creada 08-11 por cristian
@Service
public class MicrobiologiaServiceImpl implements MicrobiologiaService {

  private static Logger logger = LogManager.getLogger(MicrobiologiaServiceImpl.class);

  private MicrobiologiaOrdenDAO microbiologiaOrdenDAO;

  public MicrobiologiaServiceImpl() {

  }

  public MicrobiologiaOrdenDAO getMicrobiologiaOrdenDAO() {
    return microbiologiaOrdenDAO;
  }

  public void setMicrobiologiaOrdenDAO(MicrobiologiaOrdenDAO microbiologiaOrdenDAO) {
    this.microbiologiaOrdenDAO = microbiologiaOrdenDAO;
  }

  @Override
  public List<MicrobiologiaOrdenPacienteDTO> getListOrder(Long startId, Long endId, Date starDate, Date endDate,
      Long atentionType, Long exam, Long section, String names, String surname, Long documentType, Long documentId,
      Long serviceId) throws BiosLISDAOException {

    return microbiologiaOrdenDAO.getListOrder(startId, endId, starDate, endDate, atentionType, exam, section, names,
        surname, documentType, documentId, serviceId);
  }

  @Override
  public List<MicrobiologiaTestDTO> getTest(long IdOrder) throws BiosLISDAOException {
    // TODO Auto-generated method stub
    return microbiologiaOrdenDAO.getTest(IdOrder);
  }

  @Override
  public List<MicrobiologiaActionsDTO> getAction(long IdOrder, long IdExamen, long IdTest) throws BiosLISDAOException {
    // TODO Auto-generated method stub
    return microbiologiaOrdenDAO.getAction(IdOrder, IdExamen, IdTest);
  }

  @Override
  public MicrobiologiaTestDTO updateTest(MicrobiologiaTestDTO MTDTO) throws BiosLISDAOException {
    // TODO Auto-generated method stub
    return microbiologiaOrdenDAO.updateTest(MTDTO);
  }

  @Override
  public MicrobiologiaActionsDTO updateAction(MicrobiologiaActionsDTO MTADTO) throws BiosLISDAOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HashMap<String, Object> updateBodyTiempos(MicrobiologiaActionsDTO MTDTO)
      throws BiosLISDAOException, ParseException {
    // TODO Auto-generated method stub
    return microbiologiaOrdenDAO.updateTest2(MTDTO);
  }

  @Override
  public HashMap<String, Object> getListStatus() throws BiosLISDAOException, ParseException {
    // TODO Auto-generated method stub
    return microbiologiaOrdenDAO.getListStatus();
  }

  @Override
  public HashMap<String, Object> getListMicro() throws BiosLISDAOException, ParseException {
    // TODO Auto-generated method stub
    return microbiologiaOrdenDAO.getListMICROBIOLOGY();
  }

  @Override
  public MicrobiologiaExamDTO updateExamMicrobiologia(MicrobiologiaExamDTO MED)
      throws BiosLISDAOException, ParseException {
    // TODO Auto-generated method stub
    return microbiologiaOrdenDAO.updateExamMicrobiologia(MED);
  }

  @Override
  public MicrobiologiaExamDTO getExamenPorIds(long IdOrder, long IdExamen) throws BiosLISDAOException, ParseException {
    // TODO Auto-generated method stub
    return microbiologiaOrdenDAO.getExamenPorOrdenIds(IdOrder, IdExamen);
  }

  // creado por cristian 19-12
  @Override
  public AntibiogramaMicrobiologiaDTO getAntibiogramList(Long nOrder, Long idExamen, Long idTest)
      throws BiosLISDAOException {
    return microbiologiaOrdenDAO.getAntibiogramList(nOrder, idExamen, idTest);
  }

  @Override
  public List<Object> getListAntibiogramaAntibiotico(String antibiogramName) throws BiosLISDAOException {
    // TODO Auto-generated method stub
    return microbiologiaOrdenDAO.getListAntibiogramaAntibiotico(antibiogramName);
  }

  @Override
  public List<MicrobiologiaTestDTO> getTestxExamen(long IdOrder, ParamResultadoExamenOrden prmResultadoExamenOrden)
      throws BiosLISDAOException {
    // TODO Auto-generated method stub
    return microbiologiaOrdenDAO.getTestxExamen(IdOrder, prmResultadoExamenOrden);
  }

  // Agregado por Marco caracciolo 03/04/2023
  @Override
  public List<OrdenInformeResultadoDTO> getBOOrdenesMicrobiologia(BCFichaFiltroDTO fichaFiltroDTO)
      throws BiosLISException, BiosLISDAOException {

    List<OrdenExamenCapturaResultadoDTO> lstOrdenesConSecciones = microbiologiaOrdenDAO
        .getOrdenesCapturaResultadosMicrobiologia(fichaFiltroDTO);

    Map<OrdenInformeResultadoDTO, List<BigDecimal>> hm = new HashMap<OrdenInformeResultadoDTO, List<BigDecimal>>();
    List<OrdenInformeResultadoDTO> lstOrdenes = new ArrayList<OrdenInformeResultadoDTO>();

    String curCodExamen = "P";
    Integer curNroOrden = -1;
    Map<Integer, String> hmOrdenesConExamenPendiente = new HashMap<Integer, String>();
    Map<Integer, String> hmOrdenesConExamenUrgente = new HashMap<Integer, String>();
    String tieneCultivo = "";
    String testsEnProceso = "";
    for (OrdenExamenCapturaResultadoDTO ordenExamen : lstOrdenesConSecciones) {

      if (!ordenExamen.getDF_NORDEN().equals(curNroOrden)) {
        curNroOrden = ordenExamen.getDF_NORDEN();
        hmOrdenesConExamenPendiente.put(curNroOrden, ordenExamen.getDFE_CODIGOESTADOEXAMEN());
        hmOrdenesConExamenUrgente.put(curNroOrden, ordenExamen.getDFE_EXAMENESURGENTE());
      }
      if (ordenExamen.getDFE_EXAMENESURGENTE().equals("S")) {
        hmOrdenesConExamenUrgente.put(ordenExamen.getDF_NORDEN(), ordenExamen.getDFE_EXAMENESURGENTE());
      }
      if (ordenExamen.getDFE_CODIGOESTADOEXAMEN().equals(EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE)
          || ordenExamen.getDFE_CODIGOESTADOEXAMEN().equals(EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE)) {
        hmOrdenesConExamenPendiente.put(ordenExamen.getDF_NORDEN(), EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE);
      }

      if (!hm.containsKey(ordenExamen.getOrdenInformeResultadoDTO())) {
        List<BigDecimal> seccionLst = new ArrayList<BigDecimal>();
        // String tienecultivo = ordenExamen.getTIENECULTIVO();
        if (ordenExamen.getTESTSENPROCESO() != null) {
          // int estadoTest =
          // Integer.parseInt(ordenExamen.getTESTSENPROCESO().intValueExact());
          int estadoTest = ordenExamen.getTESTSENPROCESO().intValueExact();
          if (estadoTest == 4) {
            testsEnProceso = "S";
          } else {
            testsEnProceso = "N";
          }
        } else {
          testsEnProceso = "N";
        }
        ordenExamen.getOrdenInformeResultadoDTO().setTESTSENPROCESO(testsEnProceso);
        tieneCultivo = ordenExamen.getTIENECULTIVO();
        ordenExamen.getOrdenInformeResultadoDTO().setTIENECULTIVO(tieneCultivo);
        // seccionLst.add(ordenExamen.getCE_IDSECCION());
        hm.put(ordenExamen.getOrdenInformeResultadoDTO(), seccionLst);

      } else {
        if (testsEnProceso.equals("N") && ordenExamen.getTESTSENPROCESO() != null
            && ordenExamen.getTESTSENPROCESO().equals(4)) {
          testsEnProceso = "S";
          ordenExamen.getOrdenInformeResultadoDTO().setTESTSENPROCESO(testsEnProceso);
        }
        if (tieneCultivo.equals("N") && ordenExamen.getTIENECULTIVO().equals("S")) {
          tieneCultivo = "S";
          ordenExamen.getOrdenInformeResultadoDTO().setTIENECULTIVO("S");
        }
        hm.get(ordenExamen.getOrdenInformeResultadoDTO()).add(ordenExamen.getCE_IDSECCION());
      }
    }

    hm.forEach((k, v) -> {
      k.setLstSecciones(v);
      lstOrdenes.add(k);
    });

    for (OrdenInformeResultadoDTO ordenInformeResultadoDTO : lstOrdenes) {

      Integer nOrden = ordenInformeResultadoDTO.getDF_NORDEN();
      ordenInformeResultadoDTO.setHayExamenesPendientes(hmOrdenesConExamenPendiente.get(nOrden));
      ordenInformeResultadoDTO.setHayExamenesUrgentes(hmOrdenesConExamenUrgente.get(nOrden));
    }

    return lstOrdenes;
  }

}
