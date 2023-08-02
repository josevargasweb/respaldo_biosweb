package com.grupobios.bioslis.back.api;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.ConfiguracionEtiquetasService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.BiosLisEtiquetaBaseDTO;
import com.grupobios.bioslis.dto.EtiquetaCodigoBarraDTO;
import com.grupobios.bioslis.entity.CfgEtiquetacodigobarras;
import com.grupobios.bioslis.entity.DatosUsuarios;

@RestController
public class ConfiguracionEtiquetasRestController {

  private static Logger logger = LogManager.getLogger(ConfiguracionEtiquetasRestController.class);

  @Autowired
  ConfiguracionEtiquetasService configuracionEtiquetasService;

  public ConfiguracionEtiquetasService getConfiguracionEtiquetasService() {
    return configuracionEtiquetasService;
  }

  public void setConfiguracionEtiquetasService(ConfiguracionEtiquetasService configuracionEtiquetasService) {
    this.configuracionEtiquetasService = configuracionEtiquetasService;
  }

  @GetMapping("/api/configuracionetiquetas")
  public ResponseTemplateGen<List<EtiquetaCodigoBarraDTO>> getAllEtiquetas( @Context HttpServletRequest context) {
      HttpSession sesion = context.getSession();
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (usuario == null)
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
    List<EtiquetaCodigoBarraDTO> lstConfgEtiquetas;
    try {
      lstConfgEtiquetas = configuracionEtiquetasService.getAllEtiquetas();
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<List<EtiquetaCodigoBarraDTO>>(null, 505,
          "No se pudo traer informacion Configuracion Etiquetas");
    }

    return new ResponseTemplateGen<List<EtiquetaCodigoBarraDTO>>(lstConfgEtiquetas, 200, "Ok");
  }

  @GetMapping("/api/configuracionetiquetas/codigo/{codigo}")
  public ResponseTemplateGen<EtiquetaCodigoBarraDTO> getEtiquetaByCodigo(@PathVariable String codigo ,  @Context HttpServletRequest context) {
      HttpSession sesion = context.getSession();
      DatosUsuarios usuario = null;
      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (usuario == null)
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
      EtiquetaCodigoBarraDTO confgEtiquetas;
    try {
      confgEtiquetas = configuracionEtiquetasService.getEtiquetaBycodigo(codigo);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<EtiquetaCodigoBarraDTO>(null, 505,
          "No se pudo traer informacion Configuracion Etiquetas");
    }
    return new ResponseTemplateGen<EtiquetaCodigoBarraDTO>(confgEtiquetas, 200, "Ok");
  }

  @PostMapping("/api/configuracionetiquetas/insertUpdate")
  public ResponseTemplateGen<String> insertUpdateEtiqueta(@RequestBody CfgEtiquetacodigobarras etiquetaCodigoBarra,
      @Context HttpServletRequest context) {

    HttpSession sesion = (HttpSession) context.getSession();
    Long idUsuario = 0L;
    DatosUsuarios usuario = null;
    String id = "0";
    try {

      usuario = (DatosUsuarios) sesion.getAttribute("usuario");
      if (!usuario.equals(null)) {
        idUsuario = usuario.getDuIdusuario();
        id = idUsuario.toString();
      }else {
          return new ResponseTemplateGen<>(null, 401, "No hay sesion");
      }

      configuracionEtiquetasService.insertUpdateEtiqueta(etiquetaCodigoBarra, id);
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<String>(null, 505, "No se pudo guardar informacion Configuracion Etiquetas");
    }
    return new ResponseTemplateGen<String>(null, 200, "Ok");

  }

  @GetMapping("/api/net/configuracionetiquetas/codigo/{codigo}")
  public ResponseTemplateGen<BiosLisEtiquetaBaseDTO> getConfigEtiquetaxCodigo(@PathVariable() String codigo,
      @Context HttpServletRequest context) {

    HttpSession sesion = (HttpSession) context.getSession();
    Long idUsuario = 0L;
    DatosUsuarios usuario = null;
    String id = "0";
    EtiquetaCodigoBarraDTO etiqueta;
    BiosLisEtiquetaBaseDTO blEtiqueta;
    try {

    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
     if (usuario != null) {
       idUsuario = usuario.getDuIdusuario();
       id = idUsuario.toString();
    } else {
        return new ResponseTemplateGen<>(null, 401, "No hay sesion");
      }
      etiqueta = configuracionEtiquetasService.getEtiquetaBycodigo(codigo);
      logger.debug("{}", etiqueta);
      blEtiqueta = new BiosLisEtiquetaBaseDTO(etiqueta);

    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());
      return new ResponseTemplateGen<>(null, 505, "No se pudo guardar informacion Configuracion Etiquetas");
    }

    return new ResponseTemplateGen<>(blEtiqueta, 200, "Ok");
  }

}
