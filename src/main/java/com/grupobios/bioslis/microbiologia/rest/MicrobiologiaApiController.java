package com.grupobios.bioslis.microbiologia.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.microbiologia.bs.AntibiogramEditor;
import com.grupobios.bioslis.microbiologia.bs.ExamEditor;
import com.grupobios.bioslis.microbiologia.bs.ExamSearcher;
import com.grupobios.bioslis.microbiologia.bs.OrderSearcher;
import com.grupobios.bioslis.microbiologia.bs.ParameterSearcher;
import com.grupobios.bioslis.microbiologia.bs.TestEditor;
import com.grupobios.bioslis.microbiologia.bs.TestSearcher;
import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBAntibiogramDAO;
import com.grupobios.bioslis.microbiologia.dao.MBOrderDAO;
import com.grupobios.bioslis.microbiologia.dao.MBTaskDAO;
import com.grupobios.bioslis.microbiologia.entity.MBOrder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class MicrobiologiaApiController {

  static Logger log = LogManager.getLogger(MicrobiologiaApiController.class.getName());

  @GetMapping("/Microbiologia/api/getOrderList")
  public List<HashMap<String, String>> getOrderList(@RequestParam(value = "startId", defaultValue = "") String startId,
      @RequestParam(value = "endId", defaultValue = "") String endId,
      @RequestParam(value = "startDate", defaultValue = "") String startDate,
      @RequestParam(value = "endDate", defaultValue = "") String endDate,
      @RequestParam(value = "atentionType", defaultValue = "") String atentionType,
      @RequestParam(value = "exam", defaultValue = "") String exam,
      @RequestParam(value = "section", defaultValue = "") String section,
      @RequestParam(value = "names", defaultValue = "") String names,
      @RequestParam(value = "surname", defaultValue = "") String surname,
      @RequestParam(value = "documentType", defaultValue = "") String documentType,
      @RequestParam(value = "documentId", defaultValue = "") String documentId,
      @RequestParam(value = "serviceId", defaultValue = "") String serviceId,
      @RequestParam(value = "statusPending", defaultValue = "") String statusPending,
      @RequestParam(value = "statusForSignature", defaultValue = "") String statusForSignature) {

    return (new OrderSearcher()).searchList(startId, endId, startDate, endDate, atentionType, exam, section, names,
        surname, documentType, documentId, serviceId, statusPending, statusForSignature);
  }

  // API creadas por JAN
  @GetMapping("/Microbiologia/api/getExamenesByOrderId")
  public HashMap<String, Object> get_Examenes_By_OrderId(
      @RequestParam(value = "orderId", defaultValue = "") String orderId) {
    return (new OrderSearcher()).searchOrder_Examenes_Only(orderId);
  }

  @GetMapping("/Microbiologia/api/getExamData2")
  public HashMap<String, Object> getExamData2(@RequestParam(value = "orderId", defaultValue = "") String orderId,
      @RequestParam(value = "examId", defaultValue = "") String examId) {
    return (new ExamSearcher()).searchExam2(orderId);
  }
  // FIN

  @GetMapping("/Microbiologia/api/getOrderData")
  public HashMap<String, Object> getOrderData(@RequestParam(value = "orderId", defaultValue = "") String orderId) {
    return (new OrderSearcher()).searchOrder(orderId);
  }

  @GetMapping("/Microbiologia/api/getSearchOptions")
  public HashMap<String, Object> getSearchOptions() {
    return (new ParameterSearcher()).getSearchParameters();
  }

  @GetMapping("/Microbiologia/api/getBodyPartOptions")
  public List<String> getBodyPartOptions() {
    return (new ParameterSearcher()).getBodyPartParameters();
  }

  @GetMapping("/Microbiologia/api/getSampleOptions")
  public List<String> getSampleOptions() {
    return (new ParameterSearcher()).getSampleTypeParameters();
  }

  @GetMapping("/Microbiologia/api/getExamData")
  public HashMap<String, Object> getExamData(@RequestParam(value = "orderId", defaultValue = "") String orderId,
      @RequestParam(value = "examId", defaultValue = "") String examId) {
    return (new ExamSearcher()).searchExam(orderId, examId);
  }

  @PutMapping("/Microbiologia/api/putExamNotes")
  public HashMap<String, Object> putExamNotes(@RequestBody HashMap<String, Object> notes,
      @Context HttpServletRequest contex) {
    HttpSession sesion = contex.getSession();
    Long idUsuario = 0L;
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    idUsuario = usuario.getDuIdusuario();
    String id = idUsuario.toString();

    ExamEditor editor = new ExamEditor(notes.get("orderId").toString(), notes.get("examId").toString());
    editor.updateNotes(notes.get("internalNote").toString(), notes.get("examNote").toString(),
        notes.get("footerNote").toString(), id);
    return (new ExamSearcher()).searchExam(notes.get("orderId").toString(), notes.get("examId").toString());
  }

  @GetMapping("/Microbiologia/api/getTestData")
  public HashMap<String, Object> getTestData(@RequestParam(value = "orderId", defaultValue = "") String orderId,
      @RequestParam(value = "examId", defaultValue = "") String examId,
      @RequestParam(value = "testId", defaultValue = "") String testId/*
                                                                       * ,
                                                                       * 
                                                                       * @Context HttpServletRequest contex
                                                                       */) {
    // HttpSession sesion = contex.getSession();
    // DatosUsuarios usuario = null;
    // usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    // if (usuario == null)
    // return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    return (new TestSearcher()).searchTest(orderId, examId, testId);
  }

  @PutMapping("/Microbiologia/api/putTestData")
  public HashMap<String, Object> putTestData(@RequestBody HashMap<String, Object> data) {

    TestEditor editor = new TestEditor(data.get("orderId").toString(), data.get("examId").toString(),
        data.get("testId").toString());

    Date seedingDate = null;
    try {
      seedingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(data.get("seedingDate").toString());
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Date reseedingDate = null;
    try {
      reseedingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(data.get("reseedingDate").toString());
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Date resultDate = null;
    try {
      resultDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(data.get("resultDate").toString());
    } catch (ParseException e) {
      e.printStackTrace();
    }

    editor.updateData(data.get("result") == null ? null : data.get("result").toString(),
        data.get("microbiologyStatus") == null ? null : data.get("microbiologyStatus").toString(),
        data.get("sampleType") == null ? null : data.get("sampleType").toString(),
        data.get("bodyPart") == null ? null : data.get("bodyPart").toString(), seedingDate, reseedingDate, resultDate);

    return (new TestSearcher()).searchTest(data.get("orderId").toString(), data.get("examId").toString(),
        data.get("testId").toString());

  }

  @PutMapping("/Microbiologia/api/putTestSampleType")
  public HashMap<String, Object> putTestSampleType(@RequestBody HashMap<String, Object> data) {
    String orderId = data.get("orderId").toString();
    String examId = data.get("examId").toString();
    String testId = data.get("testId").toString();
    TestEditor editor = new TestEditor(orderId, examId, testId);
    editor.updateSampleType(data.get("sampleType").toString());
    return (new TestSearcher()).searchTest(orderId, examId, testId);
  }

  @PutMapping("/Microbiologia/api/putTestBodyPart")
  public HashMap<String, Object> putTestBodyPart(@RequestBody HashMap<String, Object> data) {
    String orderId = data.get("orderId").toString();
    String examId = data.get("examId").toString();
    String testId = data.get("testId").toString();
    TestEditor editor = new TestEditor(orderId, examId, testId);
    editor.updateBodyPart(data.get("bodyPart").toString());
    return (new TestSearcher()).searchTest(orderId, examId, testId);
  }

  @PutMapping("/Microbiologia/api/putSignTest")
  public HashMap<String, Object> putSignTest(@RequestBody HashMap<String, Object> data) {

    TestEditor editor = new TestEditor(data.get("orderId").toString(), data.get("examId").toString(),
        data.get("testId").toString());

    editor.signTest();

    return (new TestSearcher()).searchTest(data.get("orderId").toString(), data.get("examId").toString(),
        data.get("testId").toString());

  }

  @PutMapping("/Microbiologia/api/putTestCultureMedium")
  public HashMap<String, Object> putTestCultureMedium(@RequestBody HashMap<String, Object> data) {
    String orderId = data.get("orderId").toString();
    String examId = data.get("examId").toString();
    String testId = data.get("testId").toString();
    TestEditor editor = new TestEditor(orderId, examId, testId);
    editor.createOrUpdateCultureMedium(data);
    return (new TestSearcher()).searchTest(orderId, examId, testId);
  }

  @GetMapping("/Microbiologia/api/getAntibiogramOptions")
  public HashMap<String, Object> getAntibiogramOptions() {
    return (new ParameterSearcher()).getAntibiogramParameters();
  }

  @GetMapping("/Microbiologia/api/getAntibiogramAntibiotics")
  public List<String> getAntibiogramAntibiotics(
      @RequestParam(value = "antibiogramName", defaultValue = "") String antibiogramName) {
    return (new ParameterSearcher()).getAntibiogramAntibiotics(antibiogramName);
  }

  @PutMapping("/Microbiologia/api/putAntibiogram")
  public HashMap<String, Object> putAntibiogram(@RequestBody HashMap<String, Object> data) {
    new AntibiogramEditor(data.get("orderId").toString(), data.get("examId").toString(), data.get("testId").toString())
        .createOrUpdate(data);
    return (new TestSearcher()).searchTest(data.get("orderId").toString(), data.get("examId").toString(),
        data.get("testId").toString());
  }

  @GetMapping("/Microbiologia/api/getAntibiogramResistance")
  public HashMap<String, String> getAntibiogramResistance(@RequestParam(value = "antibioticId") String antibioticId,
      @RequestParam(value = "microorganismId") String microorganismId,
      @RequestParam(value = "radio", defaultValue = "") String radio,
      @RequestParam(value = "cim", defaultValue = "") String cim) {
    DAOFactory daoFactory = new DAOFactory();
    MBAntibiogramDAO dao = daoFactory.getDAO("MBAntibiogram");
    HashMap<String, String> result = new HashMap<String, String>() {
      {
        put("interpretation", "");
      }
    };
    if (!"".equals(radio))
      result.put("interpretation", dao.getAntibioticResistance(antibioticId, microorganismId, "3", radio));
    if (!"".equals(cim))
      result.put("interpretation", dao.getAntibioticResistance(antibioticId, microorganismId, "2", cim));
    return result;
  };

  @GetMapping("/Microbiologia/api/getPendingTasksList")
  public List<HashMap<String, String>> getPendingTasksList(
      @RequestParam(value = "orderId", defaultValue = "") String orderId,
      @RequestParam(value = "startDate", defaultValue = "") String startDate,
      @RequestParam(value = "endDate", defaultValue = "") String endDate,
      @RequestParam(value = "names", defaultValue = "") String names,
      @RequestParam(value = "surname", defaultValue = "") String surname,
      @RequestParam(value = "documentType", defaultValue = "") String documentType,
      @RequestParam(value = "documentId", defaultValue = "") String documentId,
      @RequestParam(value = "atentionType", defaultValue = "") String atentionType,
      @RequestParam(value = "serviceId", defaultValue = "") String serviceId) {

    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    List<HashMap<String, String>> result = dao.getTaskList(orderId, startDate, endDate, names, surname, documentType,
        documentId, atentionType, serviceId);
    return result;
  }

  @GetMapping("/Microbiologia/api/getTask")
  public HashMap<String, String> getTask(@RequestParam(value = "sampleId") String sampleId,
      @RequestParam(value = "examId") String examId, @RequestParam(value = "testId") String testId) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    HashMap<String, String> result = dao.getTask(sampleId, examId, testId);
    return result;
  }

  @PutMapping("/Microbiologia/api/putTask")
  public HashMap<String, String> putTask(@RequestBody HashMap<String, String> data) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    if ("INICIO".equals(data.get("task")))
      dao.createTask(data);
    else
      dao.updateTask(data);
    return data;
  }

  @GetMapping("/Microbiologia/api/getOxigenations")
  public HashMap<String, String> getOxigenation() {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    return dao.getOxigenations();
  }

  @GetMapping("/Microbiologia/api/getTemperatures")
  public HashMap<String, String> getTemperature() {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    return dao.getTemperatures();
  }

  @GetMapping("/Microbiologia/api/getRevisionTimes")
  public HashMap<String, String> getRevisionTimes() {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    return dao.getRevisionTimes();
  }

  @PutMapping("/Microbiologia/api/putSeedingDates")
  public HashMap<String, String> putSeedingDates(@RequestBody HashMap<String, String> data) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    dao.updateSeedingDates(data);
    return data;
  }

  @GetMapping("/Microbiologia/api/getRevisionPlacasList")
  public List<HashMap<String, String>> getRevisionPlacasList(
      @RequestParam(value = "seedingId", defaultValue = "") String seedingId) {

    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    String[] seedingIds = seedingId.split(",");
    for (String id : seedingIds) {
      result.addAll(dao.getRevisionPlacasList(id));
    }
    return result;
  }

  @PutMapping("/Microbiologia/api/putRevisionPlacas")
  public HashMap<String, String> putRevisionPlacas(@RequestBody HashMap<String, String> data) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    dao.updateRevisionPlacas(data);
    return data;
  }

  @PutMapping("/Microbiologia/api/delRevisionPlacas")
  public HashMap<String, String> delRevisionPlacas(@RequestBody HashMap<String, String> data) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    dao.delRevisionPlacas(data);
    return data;
  }

  @GetMapping("/Microbiologia/api/getSampleTestList")
  public List<HashMap<String, String>> getSampleTestList(
      @RequestParam(value = "sampleId", defaultValue = "") String sampleId,
      @RequestParam(value = "examId", defaultValue = "") String examId,
      @RequestParam(value = "testId", defaultValue = "") String testId) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    List<HashMap<String, String>> result = dao.getSampleTestList(sampleId, examId, testId);
    return result;
  }

  @PutMapping("/Microbiologia/api/putSampleTest")
  public HashMap<String, String> putSampleTest(@RequestBody HashMap<String, String> data) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    dao.updateSampleTest(data);
    return data;
  }

  @GetMapping("/Microbiologia/api/getInfectionTypeList")
  public List<String> getInfectionTypeList() {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    List<String> result = dao.getInfectionType();
    return result;
  }

  @GetMapping("/Microbiologia/api/getTestMOList")
  public List<HashMap<String, String>> getTestMOList(@RequestParam(value = "orderId") String orderId,
      @RequestParam(value = "examId") String examId, @RequestParam(value = "testId") String testId) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    List<HashMap<String, String>> result = dao.getTestMOList(orderId, examId, testId);
    return result;
  }

  @PutMapping("/Microbiologia/api/putTestMO")
  public HashMap<String, String> putTestMO(@RequestBody HashMap<String, String> data) {
    MBOrderDAO daoOrder = (new DAOFactory()).getDAO("MBOrder");
    MBOrder order = daoOrder.getById(Integer.parseInt(data.get("orderId")));

    String orderDate = (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(order.getDate());

    data.put("orderDate", orderDate);

    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    dao.updateTestMO(data);
    return data;
  }

  @PutMapping("/Microbiologia/api/delTestMO")
  public HashMap<String, String> delTestMO(@RequestBody HashMap<String, String> data) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    dao.delTestMO(data);
    return data;
  }

  @GetMapping("/Microbiologia/api/getResistanceMethodResultList")
  public List<HashMap<String, String>> getResistanceMethodResultList() {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    List<HashMap<String, String>> result = dao.getResistanceMethodResultList();
    return result;
  }

  @GetMapping("/Microbiologia/api/getAntibioticResultList")
  public List<HashMap<String, String>> getAntibioticResultList() {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    List<HashMap<String, String>> result = dao.getAntibioticResultList();
    return result;
  }

  @GetMapping("/Microbiologia/api/getMOAntibioticsList")
  public List<HashMap<String, String>> getMOAntibioticsList(@RequestParam(value = "orderId") String orderId,
      @RequestParam(value = "examId") String examId, @RequestParam(value = "testId") String testId,
      @RequestParam(value = "moId") String moId) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    List<HashMap<String, String>> result = dao.getMOAntibioticsList(orderId, examId, testId, moId);
    return result;
  }

  @PutMapping("/Microbiologia/api/putMOAntibiotic")
  public HashMap<String, String> putMOAntibiotic(@RequestBody HashMap<String, String> data) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    dao.updateMOAntibiotic(data);
    return data;
  }

  @PutMapping("/Microbiologia/api/delMOAntibiotics")
  public HashMap<String, String> delMOAntibiotics(@RequestBody HashMap<String, String> data) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    dao.delMOAntibiotic(data);
    return data;
  }

  @GetMapping("/Microbiologia/api/getMOResistanceList")
  public List<HashMap<String, String>> getMOResistanceList(
      @RequestParam(value = "moResistanceId") String moResistanceId) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    List<HashMap<String, String>> result = dao.getMOResistanceList(moResistanceId);
    return result;
  }

  @PutMapping("/Microbiologia/api/putMOResistance")
  public HashMap<String, String> putMOResistance(@RequestBody HashMap<String, String> data) {
    DAOFactory daoFactory = new DAOFactory();
    MBTaskDAO dao = daoFactory.getDAO("MBTask");
    if (data.getOrDefault("resistanceId", "").isEmpty()) {
      for (HashMap<String, String> mo : dao.getTestMOList(data.get("orderId"), data.get("examId"),
          data.get("testId"))) {
        if (mo.get("microorganismId").equals(data.get("microorganismId"))) {
          if (mo.get("resistanceId").isEmpty()) {
            mo.put("resistanceId", dao.getMethodListNewIndex());
            dao.updateTestMO(mo);
          }
          data.put("resistanceId", mo.get("resistanceId"));
          break;
        }
      }
    }
    dao.updateMOResistance(data);
    return data;
  }
}