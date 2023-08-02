/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupobios.bioslis.bs.RegistroUsuariosService;
import com.grupobios.bioslis.bs.UsuariosModulosService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.LogEventosUsuariosVistaDTO;
import com.grupobios.bioslis.dto.UsuariosPerfilesDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.common.Resizer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class RegistroUsuariosRestController {

  @Autowired
  RegistroUsuariosService registroUsuariosService;

  @Autowired
  UsuariosModulosService usuariosModulosService;

  private static Logger logger = LogManager.getLogger(RegistroUsuariosRestController.class);

  @GetMapping("/api/usuarios")
  public ResponseTemplateGen<List<DatosUsuarios>> getUsuarios() {

    try {
      List<DatosUsuarios> lista = registroUsuariosService.getUsuarios();
      if (lista.isEmpty()) {
        return new ResponseTemplateGen<>(lista, 404, "No encontrado");
      }
      return new ResponseTemplateGen<>(lista, 200, "OK");
    } catch (BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/usuario/{id}")
  public ResponseTemplateGen<UsuariosPerfilesDTO> getUsuarioById(@PathVariable("id") Long idUsuario) {
    try {
      UsuariosPerfilesDTO user = registroUsuariosService.getUsuarioById(idUsuario);
      if (user == null) {
        return new ResponseTemplateGen<>(user, 404, "No encontrado");
      }
      return new ResponseTemplateGen<>(user, 200, "OK");
    } catch (BiosLISDAOException ex) {
      logger.error(ex.getMessage());
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/usuario/run/{run}")
  public ResponseTemplateGen<DatosUsuarios> getUsuarioByRun(@PathVariable("run") String run) {
    try {
      DatosUsuarios user = registroUsuariosService.getUsuarioByRun(run);
      if (user == null) {
        return new ResponseTemplateGen<>(user, 404, "No encontrado");
      }
      return new ResponseTemplateGen<>(user, 200, "OK");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(RegistroUsuariosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/usuario/username/{username}")
  public ResponseTemplateGen<DatosUsuarios> getUsuarioByUsername(@PathVariable("username") String username) {
    try {
      DatosUsuarios user = registroUsuariosService.getUsuarioByUsername(username);
      if (user == null) {
        return new ResponseTemplateGen<>(user, 404, "No encontrado");
      }
      return new ResponseTemplateGen<>(user, 200, "OK");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(RegistroUsuariosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/usuarios/nombre/{nombre}")
  public ResponseTemplateGen<List<DatosUsuarios>> getUsuariosLikeNombre(@PathVariable("nombre") String nombre) {
    try {
      nombre = nombre.toUpperCase();
      List<DatosUsuarios> lista = registroUsuariosService.getUsuariosLikeNombre(nombre);
      if (lista.isEmpty()) {
        return new ResponseTemplateGen<>(lista, 404, "No encontrado");
      }
      return new ResponseTemplateGen<>(lista, 200, "OK");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(RegistroUsuariosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/usuarios/centro/{centro}")
  public ResponseTemplateGen<List<DatosUsuarios>> getUsuariosByCentro(@PathVariable("centro") Byte centro) {
    try {
      List<DatosUsuarios> lista = registroUsuariosService.getUsuariosCentro(centro);
      if (lista.isEmpty()) {
        return new ResponseTemplateGen<>(lista, 404, "No encontrado");
      }
      return new ResponseTemplateGen<>(lista, 200, "OK");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(RegistroUsuariosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/usuarios/cargo/{cargo}")
  public ResponseTemplateGen<List<DatosUsuarios>> getUsuariosByCargo(@PathVariable("cargo") Short cargo) {
    try {
      List<DatosUsuarios> lista = registroUsuariosService.getUsuariosCargo(cargo);
      if (lista.isEmpty()) {
        return new ResponseTemplateGen<>(lista, 404, "No encontrado");
      }
      return new ResponseTemplateGen<>(lista, 200, "OK");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(RegistroUsuariosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @GetMapping("/api/usuarios/activo/{activo}")
  public ResponseTemplateGen<List<DatosUsuarios>> getUsuariosByActivo(@PathVariable("activo") String activo) {
    try {
      List<DatosUsuarios> lista = registroUsuariosService.getUsuariosActivo(activo);
      if (lista.isEmpty()) {
        return new ResponseTemplateGen<>(lista, 404, "No encontrado");
      }
      return new ResponseTemplateGen<>(lista, 200, "OK");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(RegistroUsuariosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/usuario/save")
  public ResponseTemplateGen<String> guardarUsuario(@RequestParam("data") String data,
      @RequestParam("foto") MultipartFile foto, @RequestParam("firma") MultipartFile firma,
      @RequestParam("accesosUsuario") List<Integer> accesosUsuario) throws IOException, SQLException {
    try {
      ObjectMapper om = new ObjectMapper();
      UsuariosPerfilesDTO updto = om.readValue(data, UsuariosPerfilesDTO.class);

      if (!foto.isEmpty()) {
        byte[] fotoBytes = foto.getBytes();
        Resizer resizer = new Resizer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage buffImg = resizer.genResizedImage(fotoBytes, 640, 640);

        ImageIO.write(buffImg, "jpg", baos);

        Blob fotoBlob = new SerialBlob(baos.toByteArray());
        updto.getDatosUsuarios().setDuFoto(fotoBlob);
        logger.debug("Nombre de archivo (foto): " + foto.getOriginalFilename());
        logger.debug("Tipo de archivo (foto): " + foto.getContentType());
      } else {
        updto.getDatosUsuarios().setDuFoto(null);
      }

      if (!firma.isEmpty()) {
        byte[] firmaBytes = firma.getBytes();
        Blob firmaBlob = new SerialBlob(firmaBytes);
        updto.getCfgPerfilesusuarios().setCpuUsuariofirma(firmaBlob);
        logger.debug("Nombre de archivo (firma): " + firma.getOriginalFilename());
        logger.debug("Tipo de archivo (firma): " + firma.getContentType());
      } else {
        updto.getCfgPerfilesusuarios().setCpuUsuariofirma(null);
      }

      registroUsuariosService.agregarUsuario(updto);
      usuariosModulosService.guardarUsuarioModulos(updto.getDatosUsuarios().getDuIdusuario(), accesosUsuario);
      return new ResponseTemplateGen<>(data, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(RegistroUsuariosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/usuario/update")
  public ResponseTemplateGen<String> actualizarUsuario(@RequestParam("data") String data,
      @RequestParam(value = "foto") MultipartFile foto, @RequestParam("firma") MultipartFile firma,
      @RequestParam("accesosUsuario") List<Integer> accesosUsuario) throws IOException, SQLException {
    try {
      ObjectMapper om = new ObjectMapper();
      UsuariosPerfilesDTO updto = om.readValue(data, UsuariosPerfilesDTO.class);
      // lo usaremos para conservar imagen en caso de que esta falle
      UsuariosPerfilesDTO upAux = registroUsuariosService.getUsuarioById(updto.getDatosUsuarios().getDuIdusuario());

      if ("sinFoto.txt".equals(foto.getOriginalFilename())) {
        updto.getDatosUsuarios().setDuFoto(null);
      }
      if ("conservarFoto.txt".equals(foto.getOriginalFilename())) {
        updto.getDatosUsuarios().setDuFoto(upAux.getDatosUsuarios().getDuFoto());
      }

      //
      if (!foto.isEmpty()) {
        byte[] fotoBytes = foto.getBytes();
        Resizer resizer = new Resizer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage buffImg = resizer.genResizedImage(fotoBytes, 640, 640);

        ImageIO.write(buffImg, "jpg", baos);

        Blob fotoBlob = new SerialBlob(baos.toByteArray());
        updto.getDatosUsuarios().setDuFoto(fotoBlob);
        logger.debug("Nombre de archivo (foto): " + foto.getOriginalFilename());
        logger.debug("Tipo de archivo (foto): " + foto.getContentType());
      } else {
        // updto.getDatosUsuarios().setDuFoto(null);
      }
      /*
       * Blob firmaBlob = validaFoto(firma);
       * updto.getCfgPerfilesusuarios().setCpuUsuariofirma(firmaBlob);
       */
      if (!firma.isEmpty()) {
        byte[] firmaBytes = firma.getBytes();
        Blob firmaBlob = new SerialBlob(firmaBytes);
        updto.getCfgPerfilesusuarios().setCpuUsuariofirma(firmaBlob);
        logger.debug("Nombre de archivo (firma): " + firma.getOriginalFilename());
        logger.debug("Tipo de archivo (firma): " + firma.getContentType());
      }

      if ("sinFirma.txt".equals(firma.getOriginalFilename())) {
        updto.getCfgPerfilesusuarios().setCpuUsuariofirma(null);
      }
      if ("conservarFirma.txt".equals(firma.getOriginalFilename())) {
        updto.getCfgPerfilesusuarios().setCpuUsuariofirma(upAux.getCfgPerfilesusuarios().getCpuUsuariofirma());
      }

      registroUsuariosService.actualizarUsuario(updto);
      usuariosModulosService.actualizarUsuarioModulos(updto.getDatosUsuarios().getDuIdusuario(), accesosUsuario);
      return new ResponseTemplateGen<>(data, 200, "Ok");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(RegistroUsuariosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  @PostMapping("/api/usuario/passwordCoincide/{passwordActual}/{idUsuario}")
  public ResponseEntity<String> validarPassword(@PathVariable("passwordActual") String passwordActual,
      @PathVariable("idUsuario") Long idUsuario) {
    try {
      logger.debug("Validando password de usuario con id " + idUsuario);
      String validaPassword = registroUsuariosService.validarPassword(idUsuario, passwordActual);
      return ResponseEntity.ok(validaPassword);
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(RegistroUsuariosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return ResponseEntity.status(500).body(null);
    }
  }

  @PutMapping("/api/usuario/actualizaPassword/{passwordNueva}/{idUsuario}")
  ResponseTemplateGen<String> actualizarPassword(@PathVariable("passwordNueva") String passwordNueva,
      @PathVariable("idUsuario") Long idUsuario) {
    try {
      logger.debug("Cambiando password de usuario con id " + idUsuario);
      registroUsuariosService.actualizarPassword(idUsuario, passwordNueva);
      return new ResponseTemplateGen<>("actualizarPassword", 200, "Contraseña actualizada correctamente");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(RegistroUsuariosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  public RegistroUsuariosService getRegistroUsuariosService() {
    return registroUsuariosService;
  }

  public void setRegistroUsuariosService(RegistroUsuariosService registroUsuariosService) {
    this.registroUsuariosService = registroUsuariosService;
  }

  public UsuariosModulosService getUsuariosModulosService() {
    return usuariosModulosService;
  }

  public void setUsuariosModulosService(UsuariosModulosService usuariosModulosService) {
    this.usuariosModulosService = usuariosModulosService;
  }

  /*
   * @Bean public CommonsMultipartResolver multipartResolver() {
   * CommonsMultipartResolver multipart = new CommonsMultipartResolver();
   * multipart.setMaxUploadSize(3 * 1024 * 1024); return multipart; }
   * 
   * @Bean
   * 
   * @Order(0) public MultipartFilter multipartFilter() { MultipartFilter
   * multipartFilter = new MultipartFilter();
   * multipartFilter.setMultipartResolverBeanName("multipartReso‌​lver"); return
   * multipartFilter; }
   */
  private Blob validaFoto(MultipartFile foto) throws IOException, SQLException {
    if (!foto.isEmpty()) {
      logger.debug("Nombre de archivo: " + foto.getOriginalFilename());
      logger.debug("Tipo de archivo: " + foto.getContentType());
      byte[] fotoBytes = foto.getBytes();
      Resizer resizer = new Resizer();
      BufferedImage buffImg = resizer.genResizedImage(fotoBytes, 640, 640);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(buffImg, foto.getContentType(), baos);
      byte[] bytes = baos.toByteArray();
      return new SerialBlob(bytes);
    } else {
      return null;
    }
  }

  // creado por cristian 17-11 solo entrega nombre de usuario.
  @SuppressWarnings("unused")
  @GetMapping("/api/datosusuario/{id}")
  public ResponseTemplateGen<Map<String, String>> getDatosUsuarioById(@PathVariable("id") Long idUsuario) {
    try {
      UsuariosPerfilesDTO user = registroUsuariosService.getUsuarioById(idUsuario);
      Map<String, String> DatoUsuario = new HashMap<String, String>();
      DatoUsuario.put("nombre", user.getDatosUsuarios().getDuNombres());
      DatoUsuario.put("primerApellido", user.getDatosUsuarios().getDuPrimerapellido());
      DatoUsuario.put("segundoApellido", user.getDatosUsuarios().getDuSegundoapellido());
      if (user == null) {
        return new ResponseTemplateGen<Map<String, String>>(null, 404, "No encontrado");
      }
      return new ResponseTemplateGen<Map<String, String>>(DatoUsuario, 200, "OK");
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(RegistroUsuariosRestController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
    }
  }

  // creado por cristian11_04 para listar eventos usuario.
  @GetMapping(value = "/api/usuario/eventos/usuarios")
  public ResponseTemplateGen<List<LogEventosUsuariosVistaDTO>> getEventosFichasByOrder(
      @RequestParam(value = "id", defaultValue = "0") String id,
      @RequestParam(value = "filtro", defaultValue = "-1") String filtro,
      @RequestParam(value = "inicio", defaultValue = "0") int inicio,
      @RequestParam(value = "fin", defaultValue = "0") int fin, @Context HttpServletRequest req) {

    List<LogEventosUsuariosVistaDTO> lst;

    try {
      /*
       * Long idUsuario = 0L; DatosUsuarios usuario = null; HttpSession sesion =
       * req.getSession();
       * 
       * if (sesion.getAttribute("usuario") != null) { usuario = (DatosUsuarios)
       * sesion.getAttribute("usuario"); idUsuario = usuario.getDuIdusuario();
       * 
       * } else { return new ResponseTemplateGen<List<LogEventoUsuarioDTO>>(null, 500,
       * "Error: Sesión ha expirado"); }
       */
      lst = registroUsuariosService.getEventosUsuarioById(Long.parseLong(id), filtro, inicio, fin);
      return new ResponseTemplateGen<List<LogEventosUsuariosVistaDTO>>(lst, 200, "OK");
    } catch (BiosLISDAOException e) {
      logger.error(e.getLocalizedMessage());
      return new ResponseTemplateGen<List<LogEventosUsuariosVistaDTO>>(null, 503, e.getMessage());
    }
  }

}
