/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgPerfilesusuariosDAO;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dao.SistemaConfiguracionesDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.dto.LogEventosUsuariosVistaDTO;
import com.grupobios.bioslis.dto.UsuariosPerfilesDTO;
import com.grupobios.bioslis.entity.CfgPerfilesusuarios;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.common.CifradoMD5;

/**
 *
 * @author Marco Caracciolo
 */
public class RegistroUsuariosServiceImpl implements RegistroUsuariosService {

  private DatosUsuariosDAO datosUsuariosDAO;
  private CfgPerfilesusuariosDAO cfgPerfilesusuariosDAO;
  private static Logger LOGGER = LogManager.getLogger(RegistroUsuariosServiceImpl.class);

  @Override
  public List<DatosUsuarios> getUsuarios() throws BiosLISDAOException {
    return datosUsuariosDAO.getUsuarios();
  }

  @Override
  public List<DatosUsuarios> getUsuariosLikeNombre(String nombre) throws BiosLISDAOException {
    return datosUsuariosDAO.getUsuariosLikeNombre(nombre);
  }

  @Override
  public List<DatosUsuarios> getUsuariosCargo(Short idCargo) throws BiosLISDAOException {
    return datosUsuariosDAO.getUsuariosByCargo(idCargo);
  }

  @Override
  public List<DatosUsuarios> getUsuariosCentro(Byte idCentro) throws BiosLISDAOException {
    return datosUsuariosDAO.getUsuariosByCentro(idCentro);
  }
  @Override
  public List<DatosUsuarios> getUsuariosActivo(String activo) throws BiosLISDAOException {
  	// TODO Auto-generated method stub
	  return datosUsuariosDAO.getUsuariosByActivo(activo);
  }

  @Override
  public UsuariosPerfilesDTO getUsuarioById(Long idUsuario) throws BiosLISDAOException {
    UsuariosPerfilesDTO updto = new UsuariosPerfilesDTO();

    DatosUsuarios du = datosUsuariosDAO.getUsuarioById(idUsuario);
    // String pwdDesencriptada = CifradoMD5.decode(du.getDuPassword());
    // du.setDuPassword(pwdDesencriptada);
    updto.setDatosUsuarios(du);
    updto.setLdvCargosusuarios(du.getLdvCargosusuarios());
    updto.setLdvSexo(du.getLdvSexo());
    updto.setLdvProfesionesusuarios(du.getLdvProfesionesusuarios());
    // updto.setCfgPerfilesusuarios(null);

    try {
      if (du.getDuFoto() != null) {
        byte[] bdata1 = du.getDuFoto().getBytes(1, (int) du.getDuFoto().length());
        byte[] encodeBase64 = Base64.getEncoder().encode(bdata1);
        String base64encoded = new String(encodeBase64, "UTF-8");
        updto.setFotoUsuario(base64encoded);
      } else {
        LOGGER.debug("No hay foto usuario");
      }

      CfgPerfilesusuarios cpu = new CfgPerfilesusuarios();

      cpu = cfgPerfilesusuariosDAO.getPerfilesUsuariosById(idUsuario);

      if (cpu != null) {

        updto.setCfgPerfilesusuarios(cpu);
        updto.setCfgCentrosdesalud(cpu.getCfgCentrosdesalud());

        if (cpu.getCpuUsuariofirma() != null) {
          byte[] bdata2 = cpu.getCpuUsuariofirma().getBytes(1, (int) cpu.getCpuUsuariofirma().length());
          byte[] encodeBase64_2 = Base64.getEncoder().encode(bdata2);
          String base64encoded_2 = new String(encodeBase64_2, "UTF-8");
          updto.setFirmaUsuario(base64encoded_2);
        } else {
          LOGGER.debug("No hay firma usuario");
        }

      }

    } catch (Exception e) {
      LOGGER.debug("Error: " + e.getMessage());
    }
    return updto;
  }

  @Override
  public DatosUsuarios getUsuarioId(Long idUsuario) throws BiosLISDAOException {
    return datosUsuariosDAO.getUsuarioById(idUsuario);
  }

  @Override
  public DatosUsuarios getUsuarioByRun(String run) throws BiosLISDAOException {
    return datosUsuariosDAO.getUsuarioByRun(run);
  }

  @Override
  public DatosUsuarios getUsuarioByUsername(String username) throws BiosLISDAOException {
    return datosUsuariosDAO.getUsuarioByUsername(username);
  }

  @Override
  public void agregarUsuario(UsuariosPerfilesDTO updto) throws BiosLISDAOException {
    DatosUsuarios du = updto.getDatosUsuarios();
    if (updto.getPassword() != null) {
//        if (du.getDuPassword()!=null){
      String encodePassword = CifradoMD5.encode(updto.getPassword());
      du.setDuPassword(encodePassword);
    }
    du.setLdvCargosusuarios(updto.getLdvCargosusuarios());
    du.setLdvSexo(updto.getLdvSexo());
    datosUsuariosDAO.agregarUsuario(du);
    CfgPerfilesusuarios cpu = updto.getCfgPerfilesusuarios();
    cpu.setCpuIdusuario(du.getDuIdusuario());
    if (updto.getCfgCentrosdesalud().getCcdsIdcentrodesalud() == 0) {
      cpu.setCfgCentrosdesalud(null);
    } else {
      cpu.setCfgCentrosdesalud(updto.getCfgCentrosdesalud());
    }
    cpu.setDatosUsuarios(du);
    cfgPerfilesusuariosDAO.agregarPerfilesusuarios(cpu);
  }

  @Override
  public void actualizarUsuario(UsuariosPerfilesDTO updto) throws BiosLISDAOException {
    DatosUsuarios du = updto.getDatosUsuarios();
    if (updto.getPassword() != null) {
//        if (du.getDuPassword()!=null){
      String encodePassword = CifradoMD5.encode(updto.getPassword());
      du.setDuPassword(encodePassword);
    } else {
      String pwd = datosUsuariosDAO.getPassword(du.getDuIdusuario());
      du.setDuPassword(pwd);
    }
    du.setLdvCargosusuarios(updto.getLdvCargosusuarios());
    du.setLdvSexo(updto.getLdvSexo());
    datosUsuariosDAO.actualizarUsuario(du);
    CfgPerfilesusuarios cpu = new CfgPerfilesusuarios();
    CfgPerfilesusuarios cpuExiste = new CfgPerfilesusuarios();
    // variable que se utilizará en caso de que no exista CfgPerfilesusuarios
    // correspondiente al id de usuario
    boolean insert = false;
    // try catch para evitar NullPointerException
    try {
      cpuExiste = cfgPerfilesusuariosDAO.getPerfilesUsuariosById(du.getDuIdusuario());
      if (cpuExiste == null) {
        insert = true;
      }
      cpu = updto.getCfgPerfilesusuarios();
      cpu.setCpuIdusuario(du.getDuIdusuario());
      if (updto.getCfgCentrosdesalud().getCcdsIdcentrodesalud() == 0) {
        cpu.setCfgCentrosdesalud(null);
      } else {
        cpu.setCfgCentrosdesalud(updto.getCfgCentrosdesalud());
      }
      cpu.setDatosUsuarios(du);
      LOGGER.debug("Insert: " + insert);
      if (insert == true) {
        cfgPerfilesusuariosDAO.agregarPerfilesusuarios(cpu);
      } else {
        cfgPerfilesusuariosDAO.actualizarPerfilesusuarios(cpu);
      }

    } catch (Exception e) {
      LOGGER.debug("Exception causada por: " + e.getMessage());
    }
  }

  @Override
  public String validarPassword(Long idUsuario, String password) throws BiosLISDAOException {
    DatosUsuarios du = datosUsuariosDAO.getUsuarioById(idUsuario);
    String passDesencriptada = CifradoMD5.decode(du.getDuPassword());
    if (password.equals(passDesencriptada)) {
      return "S";
    }
    return "N";
  }

  @Override
  public void actualizarPassword(Long idUsuario, String password) throws BiosLISDAOException {
    DatosUsuarios du = datosUsuariosDAO.getUsuarioById(idUsuario);
    String passEncriptada = CifradoMD5.encode(password);
    du.setDuPassword(passEncriptada);
    // Recalcular fecha de caducidad password
    if (du.getDuPasswordexpira().equals("S")) {
      SistemaConfiguracionesDAO scdao = new SistemaConfiguracionesDAO();
      Integer diasCaducaPwd = scdao.getDiasCaducaPassword(idUsuario).intValue();
      LOGGER.debug("Días de caducidad de password: " + diasCaducaPwd);
      if (diasCaducaPwd > 0) {
        Date hoy = BiosLisCalendarService.getInstance().getTS();
        Calendar c = Calendar.getInstance();
        c.setTime(hoy);
        c.add(Calendar.DATE, diasCaducaPwd);
        du.setDuFechacaducapassword(c.getTime());
      }
    } else {
      du.setDuFechacaducapassword(null);
    }
    datosUsuariosDAO.actualizarUsuario(du);
  }

  public DatosUsuariosDAO getDatosUsuariosDAO() {
    return datosUsuariosDAO;
  }

  public void setDatosUsuariosDAO(DatosUsuariosDAO datosUsuariosDAO) {
    this.datosUsuariosDAO = datosUsuariosDAO;
  }

  public CfgPerfilesusuariosDAO getCfgPerfilesusuariosDAO() {
    return cfgPerfilesusuariosDAO;
  }

  public void setCfgPerfilesusuariosDAO(CfgPerfilesusuariosDAO cfgPerfilesusuariosDAO) {
    this.cfgPerfilesusuariosDAO = cfgPerfilesusuariosDAO;
  }

@Override
public List<LogEventosUsuariosVistaDTO> getEventosUsuarioById(Long id, String filtro, int inicio, int fin) throws BiosLISDAOException {
	LogUsuarioDAO logUsuario = new LogUsuarioDAO();
    return logUsuario.getEventosUsuarioById(id, filtro, inicio, fin);
	
	
}



}
