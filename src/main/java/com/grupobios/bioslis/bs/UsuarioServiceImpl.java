/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Base64;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgPerfilesusuariosDAO;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dto.DatosUsuariosDTO;
import com.grupobios.bioslis.entity.CfgPerfilesusuarios;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.common.CifradoMD5;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author jan
 */
public class UsuarioServiceImpl implements UsuarioService {

  DatosUsuariosDAO datosUsuariosDAO;

  public DatosUsuariosDAO getDatosUsuariosDAO() {
    return datosUsuariosDAO;
  }

  public void setDatosUsuariosDAO(DatosUsuariosDAO datosUsuariosDAO) {
    this.datosUsuariosDAO = datosUsuariosDAO;
  }

  @Override
  public Object[] getUsuarioByUsername(DatosUsuariosDTO user, HttpServletRequest context) throws BiosLISDAOException {
    DatosUsuarios usuario = datosUsuariosDAO.getUsuarioByUsername(user.getUsername().toUpperCase().trim());

    return validaUsuario(usuario, user, context);

  }

  public Object[] validaUsuario(DatosUsuarios usuario, DatosUsuariosDTO user, HttpServletRequest context)
      throws BiosLISDAOException {

    Object[] response;

    if (usuario == null) {
      return response = new Object[] { "Usuario o contraseña incorrectos.", 201 };
    } else {
      String activo = usuario.getDuActivo();
      String pwd = CifradoMD5.encode(user.getPassword());

      if (!pwd.equals(usuario.getDuPassword())) {
        // return "Usuario o contraseña incorrectos.";
        return response = new Object[] { "Usuario o contraseña incorrectos.", 201 };
      } else {
        // Si la contraseña ha caducado
        if (usuario.getDuPasswordexpira().equals("S")
            && (usuario.getDuFechacaducapassword().equals(BiosLisCalendarService.getInstance().getDate())
                || usuario.getDuFechacaducapassword().before(BiosLisCalendarService.getInstance().getDate()))) {

          habilitarSesion(context, usuario);
          return response = new Object[] {
              "Su contraseña ha caducado. Ingrese una nueva para poder acceder al sistema.", 203 };
        }
        if (activo.equals("N")) {
          return response = new Object[] { "Usuario inactivo.", 204 };
        }
      }

      habilitarSesion(context, usuario);

      return response = new Object[] { "", 200 };
    }
  }

  public void habilitarSesion(HttpServletRequest context, DatosUsuarios usuario) throws BiosLISDAOException {
    HttpSession sesion = context.getSession();
    sesion.setAttribute("usuario", usuario);
    CfgPerfilesusuariosDAO cpuDAO = new CfgPerfilesusuariosDAO();
    CfgPerfilesusuarios perfilUsuario = cpuDAO.getPerfilesUsuariosById(usuario.getDuIdusuario());
    sesion.setAttribute("perfilUsuario", perfilUsuario);
    String base64Foto = fotoUsuario(usuario);
    sesion.setAttribute("base64Foto", base64Foto);
    // actualiza ultimo inicio de sesion
    usuario.setDuFechaultimaconexion(BiosLisCalendarService.getInstance().getTS());
    datosUsuariosDAO.actualizarUsuario(usuario);
  }

  public String fotoUsuario(DatosUsuarios usuario) {
    String base64Foto = "";
    if (usuario.getDuFoto() != null) {
      byte[] bdata1;
      try {
        bdata1 = usuario.getDuFoto().getBytes(1, (int) usuario.getDuFoto().length());
        byte[] encodeBase64 = Base64.getEncoder().encode(bdata1);

        base64Foto = new String(encodeBase64, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return base64Foto;
  }
}
