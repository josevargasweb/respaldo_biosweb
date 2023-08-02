package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.common.BiosLISException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.AntibiogramaMicrobiologiaDTO;
import com.grupobios.bioslis.dto.AntibioticoMicrobiologiaDTO;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.OrdenInformeResultadoDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenOrden;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaActionsDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaCapturaResultadoDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaExamDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaOrdenPacienteDTO;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaTestDTO;
import com.grupobios.bioslis.microbiologia.rest.MicrobiologiaApiController;



public interface MicrobiologiaService {

    List<MicrobiologiaOrdenPacienteDTO> getListOrder(Long  startId, Long endId, Date starDate, Date endDate, Long atentionType,
            Long exam, Long section, String names, String surname, Long documentType, Long documentId, Long serviceId) throws BiosLISDAOException;
    //GET TEST
    public List<MicrobiologiaTestDTO> getTest(long IdOrder) throws BiosLISDAOException;
    //GET ACTIONS
    public List<MicrobiologiaActionsDTO> getAction(long IdOrder,long IdExamen,long IdTest) throws BiosLISDAOException;
    //GET  STATUS
    public HashMap<String, Object> getListStatus() throws BiosLISDAOException, ParseException;
    //GET  Microbiology
    public HashMap<String, Object> getListMicro() throws BiosLISDAOException, ParseException;
    //GET EXAMENES  POR ID getExamenPorIds
    public MicrobiologiaExamDTO getExamenPorIds(long IdOrder,long IdExamen) throws BiosLISDAOException, ParseException;
    //update TEST
    public MicrobiologiaTestDTO updateTest(MicrobiologiaTestDTO MTDTO) throws BiosLISDAOException;
    //update ACTIONS
    public MicrobiologiaActionsDTO updateAction(MicrobiologiaActionsDTO MTADTO) throws BiosLISDAOException;
    //update body part
    public HashMap<String, Object> updateBodyTiempos(MicrobiologiaActionsDTO MTADTO) throws BiosLISDAOException, ParseException;
    //update EXAM
    public MicrobiologiaExamDTO updateExamMicrobiologia(MicrobiologiaExamDTO MED) throws BiosLISDAOException, ParseException;
  //creado por cristian 19-12
    public AntibiogramaMicrobiologiaDTO getAntibiogramList(Long nOrder, Long idExamen , Long idTest) throws BiosLISDAOException;
    public List<Object> getListAntibiogramaAntibiotico(String antibiogramName) throws BiosLISDAOException;
  //GET TEST X EXAMEN
    public List<MicrobiologiaTestDTO> getTestxExamen(long IdOrder, ParamResultadoExamenOrden prmResultadoExamenOrden) throws BiosLISDAOException;
    //AGREGADO POR MARCO CARACCIOLO 03/04/2023
    public List<OrdenInformeResultadoDTO> getBOOrdenesMicrobiologia(BCFichaFiltroDTO fichaFiltroDTO) throws BiosLISException, BiosLISDAOException;
}

