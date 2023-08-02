package com.grupobios.bioslis.microbiologia.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBConfigurationDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

@RestController
public class MicrobiologiaMantenedoresApiController {

  static Logger log = LogManager.getLogger(MicrobiologiaMantenedoresApiController.class.getName());

  @GetMapping("/Microbiologia/Mantenedores/api/getMOList")
  public List<HashMap<String, String>> getMOList(@RequestParam(value = "code", defaultValue = "") String code,
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "activeOnly", defaultValue = "false") String activeOnly) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    List<HashMap<String, String>> result = dao.getMOList(code, name, activeOnly);
    return result;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getMO")
  public HashMap<String, String> getMO(@RequestParam(value = "id") String id) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getMicroorganism(id);
  }

  @PutMapping("/Microbiologia/Mantenedores/api/putMO")
  public HashMap<String, String> putMO(@RequestBody HashMap<String, Object> data, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");

    HashMap<String, String> mo = new HashMap<String, String>();
    mo.put("id", data.get("id").toString());
    mo.put("code", data.get("code").toString());
    mo.put("name", data.get("name").toString());
    mo.put("staining", data.get("staining").toString());
    mo.put("morphology", data.get("morphology").toString());
    mo.put("gender", data.get("gender").toString());
    mo.put("active", data.get("active").toString());
    mo.put("lisbac", data.get("lisbac") == null ? "" : data.get("lisbac").toString());
    mo.put("notes", data.get("notes") == null ? "" : data.get("notes").toString());

    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    if ("".equals(data.get("id").toString())) {
      dao.createMicroorganism(mo);
    } else {
      dao.updateMicroorganism(mo, usuario.getDuIdusuario() == 0 ? 1 : usuario.getDuIdusuario());
    }
    return mo;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getMOMorphologyOptions")
  public List<HashMap<String, String>> getMOMorphologyOptions() {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getMorphologyOptions();
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getMOGenderOptions")
  public List<HashMap<String, String>> getMOGenderOptions() {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getGenderOptions();
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getMOStainingOptions")
  public List<HashMap<String, String>> getMOStainingOptions() {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getStainingOptions();
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getABList")
  public List<HashMap<String, String>> getABList(@RequestParam(value = "code", defaultValue = "") String code,
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "class", defaultValue = "") String class_,
      @RequestParam(value = "activeOnly", defaultValue = "false") String activeOnly) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    List<HashMap<String, String>> result = dao.getABList(code, name, class_, activeOnly);
    return result;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getAB")
  public HashMap<String, String> getAB(@RequestParam(value = "id") String id) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getAntibiotic(id);
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getABClassOptions")
  public List<HashMap<String, String>> getABClassOptions() {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getABClassOptions();
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getABResistanceList")
  public List<HashMap<String, String>> getABResistanceList(@RequestParam(value = "id") String id) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getABResistanceList(id);
  }

  @PutMapping("/Microbiologia/Mantenedores/api/putABResistance")
  public HashMap<String, String> putABResistance(@RequestBody HashMap<String, String> data,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.updateABResistance(data, usuario.getDuIdusuario() == 0 ? 1 : usuario.getDuIdusuario());
  }

  @PutMapping("/Microbiologia/Mantenedores/api/putAB")
  public HashMap<String, String> putAB(@RequestBody HashMap<String, Object> data, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");

    HashMap<String, String> ab = new HashMap<String, String>();
    ab.put("id", data.get("id").toString());
    ab.put("code", data.get("code").toString());
    ab.put("name", data.get("name").toString());
    ab.put("host", data.get("host") == null ? "" : data.get("host").toString());
    ab.put("sort", data.get("sort") == null ? "" : data.get("sort").toString());
    ab.put("class", data.get("class").toString());
    ab.put("active", data.get("active").toString());

    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");

    if ("".equals(data.get("id").toString())) {
      dao.createAntibiotic(ab);
    } else {
      dao.updateAntibiotic(ab, usuario.getDuIdusuario() == 0 ? 1 : usuario.getDuIdusuario());
    }
    return ab;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getCMList")
  public List<HashMap<String, String>> getCMList(@RequestParam(value = "code", defaultValue = "") String code,
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "activeOnly", defaultValue = "false") String activeOnly) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    List<HashMap<String, String>> result = dao.getCMList(code, name, activeOnly);
    return result;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getCM")
  public HashMap<String, String> getCM(@RequestParam(value = "id") String id) {

    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getCultureMedium(id);
  }

  @PutMapping("/Microbiologia/Mantenedores/api/putCM")
  public HashMap<String, String> putCM(@RequestBody HashMap<String, Object> data, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    HashMap<String, String> cm = new HashMap<String, String>();
    cm.put("id", data.get("id").toString());
    cm.put("code", data.get("code").toString());
    cm.put("name", data.get("name").toString());
    cm.put("prefix", data.get("prefix").toString());
    cm.put("active", data.get("active").toString());
    cm.put("sort", data.get("sort") == null ? "" : data.get("sort").toString());

    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    if ("".equals(data.get("id").toString())) {
      dao.createCultureMedium(cm);
    } else {
      dao.updateCultureMedium(cm, usuario.getDuIdusuario() == 0 ? 1 : usuario.getDuIdusuario());
    }
    return cm;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getRMList")
  public List<HashMap<String, String>> getRMList(@RequestParam(value = "code", defaultValue = "") String code,
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "activeOnly", defaultValue = "false") String activeOnly) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    List<HashMap<String, String>> result = dao.getRMList(code, name, activeOnly);
    return result;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getRM")
  public HashMap<String, String> getRM(@RequestParam(value = "id") String id) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getResistanceMethod(id);
  }

  @PutMapping("/Microbiologia/Mantenedores/api/putRM")
  public ResponseTemplateGen<String> putRM(@RequestBody HashMap<String, Object> data,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    HashMap<String, String> rm = new HashMap<String, String>();
    rm.put("id", data.get("id").toString());
    rm.put("code", data.get("code").toString());
    rm.put("name", data.get("name").toString());
    rm.put("active", data.get("active").toString());
    rm.put("host", data.get("host") == null ? "" : data.get("host").toString());

    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    boolean vali = false;
    if ("".equals(data.get("id").toString())) {
      vali = dao.createResistanceMethod(rm);
      if (vali) {
        return new ResponseTemplateGen<>(null, 200, "Agregado satisfactoriamente");
      } else {
        return new ResponseTemplateGen<>(null, 300, "Codigo repetido");
      }
    } else {
      vali = dao.updateResistanceMethod(rm, usuario.getDuIdusuario() == 0 ? 1 : usuario.getDuIdusuario());
      if (vali) {
        return new ResponseTemplateGen<>(null, 200, "Actualizado satisfactoriamente");
      } else {
        return new ResponseTemplateGen<>(null, 300, "Codigo repetido");
      }
    }
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getPMList")
  public List<HashMap<String, String>> getPMList(@RequestParam(value = "code", defaultValue = "") String code,
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "activeOnly", defaultValue = "false") String activeOnly) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    List<HashMap<String, String>> result = dao.getPMList(code, name, activeOnly);
    return result;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getPM")
  public HashMap<String, String> getPM(@RequestParam(value = "id") String id) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getPruebaManual(id);
  }

  @PutMapping("/Microbiologia/Mantenedores/api/putPM")
  public HashMap<String, String> putPM(@RequestBody HashMap<String, Object> data, @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    HashMap<String, String> rm = new HashMap<String, String>();
    rm.put("id", data.get("id").toString());
    rm.put("code", data.get("code").toString());
    rm.put("name", data.get("name").toString());
    rm.put("active", data.get("active").toString());
    rm.put("sort", data.get("sort") == null ? "" : data.get("sort").toString());

    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    if ("".equals(data.get("id").toString())) {
      dao.createPruebaManual(rm);
    } else {
      dao.updatePruebaManual(rm, usuario.getDuIdusuario() == 0 ? 1 : usuario.getDuIdusuario());
    }
    return rm;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getBAList")
  public List<HashMap<String, String>> getBAList(@RequestParam(value = "code", defaultValue = "") String code,
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "activeOnly", defaultValue = "false") String activeOnly) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    List<HashMap<String, String>> result = dao.getBAList(code, name, activeOnly);
    return result;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getBA")
  public HashMap<String, String> getBA(@RequestParam(value = "id") String id) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getBodyArea(id);
  }

  @PutMapping("/Microbiologia/Mantenedores/api/putBA")
  public ResponseTemplateGen<String> putBA(@RequestBody HashMap<String, Object> data,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    HashMap<String, String> ba = new HashMap<String, String>();
    ba.put("id", data.get("id").toString());
    // ba.put("code", data.get("code").toString());
    ba.put("name", data.get("name").toString());
    ba.put("active", data.get("active").toString());
    ba.put("sort", data.get("sort") == null ? "" : data.get("sort").toString());
    boolean vali = false;
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    if ("".equals(data.get("id").toString())) {
      vali = dao.createBodyArea(ba);
      if (vali) {
        return new ResponseTemplateGen<String>(null, 200, "Creacion satisfactoria");
      } else {
        return new ResponseTemplateGen<String>(null, 300, "Codigo repetido");
      }
    } else {
      vali = dao.updateBodyArea(ba, usuario.getDuIdusuario() == 0 ? 1 : usuario.getDuIdusuario());
      if (vali) {
        return new ResponseTemplateGen<String>(null, 200, "Actualizacion satisfactoria");
      } else {
        return new ResponseTemplateGen<String>(null, 300, "Codigo repetido");
      }
    }
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getCCList")
  public List<HashMap<String, String>> getCCList(@RequestParam(value = "code", defaultValue = "") String code,
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "activeOnly", defaultValue = "false") String activeOnly) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    List<HashMap<String, String>> result = dao.getCCList(code, name, activeOnly);
    return result;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getCC")
  public HashMap<String, String> getCC(@RequestParam(value = "id") String id) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getColonyCount(id);
  }

  @PutMapping("/Microbiologia/Mantenedores/api/putCC")
  public ResponseTemplateGen<String> putCC(@RequestBody HashMap<String, Object> data,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    HashMap<String, String> cc = new HashMap<String, String>();
    cc.put("id", data.get("id").toString());
    cc.put("code", data.get("code").toString());
    cc.put("name", data.get("name").toString());
    cc.put("active", data.get("active").toString());
    cc.put("sort", data.get("sort") == null ? "" : data.get("sort").toString());
    cc.put("host", data.get("host") == null ? "" : data.get("host").toString());
    boolean vali = false;
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    if ("".equals(data.get("id").toString())) {
      vali = dao.createColonyCount(cc);
      if (vali) {
        return new ResponseTemplateGen<String>(null, 200, "Creacion satisfactoria");
      } else {
        return new ResponseTemplateGen<String>(null, 300, "Codigo repetido");
      }
    } else {
      vali = dao.updateColonyCount(cc, usuario.getDuIdusuario() == 0 ? 1 : usuario.getDuIdusuario());
      if (vali) {
        return new ResponseTemplateGen<String>(null, 200, "Creacion satisfactoria");
      } else {
        return new ResponseTemplateGen<String>(null, 300, "Codigo repetido");
      }
    }
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getAGList")
  public List<HashMap<String, String>> getAGList(@RequestParam(value = "code", defaultValue = "") String code,
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "activeOnly", defaultValue = "false") String activeOnly) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    List<HashMap<String, String>> result = dao.getAGList(code, name, activeOnly);
    return result;
  }

  @GetMapping("/Microbiologia/Mantenedores/api/getAG")
  public HashMap<String, Object> getAG(@RequestParam(value = "id") String id) {
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    return dao.getAntibiogram(id);
  }

  @PutMapping("/Microbiologia/Mantenedores/api/putAG")
  public ResponseTemplateGen<String> putAG(@RequestBody HashMap<String, Object> data,
      @Context HttpServletRequest context) {
    HttpSession sesion = context.getSession();
    DatosUsuarios usuario = null;
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    HashMap<String, String> ag = new HashMap<String, String>();
    ag.put("id", data.get("id").toString());
    ag.put("code", data.get("code").toString());
    ag.put("name", data.get("name").toString());
    ag.put("active", data.get("active").toString());
    ag.put("sort", data.get("sort") == null ? "" : data.get("sort").toString());
    ag.put("host", data.get("host") == null ? "" : data.get("host").toString());
    boolean vali = false;
    MBConfigurationDAO dao = (new DAOFactory()).getDAO("MBConfiguration");
    dao.clearAntibiogramAntibiotics(data.get("id").toString());
    ArrayList<Object> antibiotics = (ArrayList<Object>) data.get("antibiotics");
    for (Object abData : antibiotics) {
      ArrayList<Object> ab = (ArrayList<Object>) abData;
      dao.updateAntibiogramAntibiotic(data.get("id").toString(), ab.get(0).toString(), ab.get(2).toString());
    }
    if ("".equals(data.get("id").toString())) {
      vali = dao.createAntibiogram(ag);
      if (vali) {
        return new ResponseTemplateGen<String>(null, 200, "Creacion satisfactoria");
      } else {
        return new ResponseTemplateGen<String>(null, 300, "Codigo repetido");
      }
    } else {
      vali = dao.updateAntibiogram(ag, usuario.getDuIdusuario() == 0 ? 1 : usuario.getDuIdusuario());
      if (vali) {
        return new ResponseTemplateGen<String>(null, 200, "Creacion satisfactoria");
      } else {
        return new ResponseTemplateGen<String>(null, 300, "Codigo repetido");
      }
    }

  }

}
