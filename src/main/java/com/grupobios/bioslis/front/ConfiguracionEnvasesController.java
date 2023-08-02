package com.grupobios.bioslis.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Base64;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgEnvasesDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgEnvases;
import com.grupobios.bioslis.entity.DatosUsuarios;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ConfiguracionEnvasesController {

  ModelAndView mav = new ModelAndView();
  private static final org.apache.logging.log4j.Logger logger = LogManager
      .getLogger(ConfiguracionEnvasesController.class);

  @RequestMapping("/ConfiguracionEnvases")
  public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response,
      RedirectAttributes attributes) {
    HttpSession sesion = (HttpSession) request.getSession();
    DatosUsuarios usuario = new DatosUsuarios();
    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
    mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes,
        ViewNames.ID_CONFIGURACION_ENVASES);

    // aqui se inserta la accion que realiza usuario en modulos ***********
    if (usuario != null) {
      LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
      LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();

      leu.setLeuIdusuario((int) usuario.getDuIdusuario());
      leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
      leu.setLeuIdevento(11);
      leu.setLeuIdacciondato(0);
      leu.setLeuValornuevo("accede a Configuración de contenedores");
      logUsuarioDao.insertLogEventosUsuario(leu);
    }
    // *-************************************************************
    return mav;
  }

  @RequestMapping(value = "/ConfiguracionEnvases", method = RequestMethod.POST, params = "filtroId")
  public void buscarGlosaId(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
    try {
      String idEnvase = request.getParameter("idEnvase");
      CfgEnvasesDAO cenvDAO = new CfgEnvasesDAO();
      CfgEnvases cenv = cenvDAO.getEnvaseById(Integer.parseInt(idEnvase));
      PrintWriter writer = response.getWriter();
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

      JsonObjectBuilder envaseBuilder = Json.createObjectBuilder();
      JsonObject envaseJson;
      envaseBuilder.add("IdEnvase", cenv.getCenvIdenvase()).add("CodigoEnvase", cenv.getCenvCodigo())
          .add("DescripcionEnvase", cenv.getCenvDescripcion()).add("TipoMuestra", cenv.getCenvIdtipomuestra())
          .add("OrdenEnvase", cenv.getCenvSort())
          // .add("TotalEnvase", cenv.getCenvVolumenml()!=null ? cenv.getCenvVolumenml() :
          // 0)
          .add("TotalEnvase", cenv.getCenvVolumenml() != null ? cenv.getCenvVolumenml() : new BigDecimal(0))
          .add("UtilEnvase", cenv.getCenvVolumenutilmicrolt()).add("EstadoEnvase", cenv.getCenvActivo());
      // codificacion imagen base64
      if (cenv.getCenvImagenenvase() != null) {
        byte[] bdata = cenv.getCenvImagenenvase().getBytes(1, (int) cenv.getCenvImagenenvase().length());
        byte[] encodeBase64 = Base64.getEncoder().encode(bdata);
        String base64Encoded = new String(encodeBase64, "UTF-8");
        envaseBuilder.add("imagen", base64Encoded);
      }
      // termino codificacion base64
      envaseJson = envaseBuilder.build();
      arrayBuilder.add(envaseJson);
      JsonObject root = rootBuilder.add("envase", arrayBuilder).build();
      writer.print(root);
    } catch (BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
      JsonObject root = rootBuilder.add("error", ex.getMessage()).build();
      PrintWriter writer = response.getWriter();
      writer.print(root);
    }
  }

}
