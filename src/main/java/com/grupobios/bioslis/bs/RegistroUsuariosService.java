/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.dto.LogEventosUsuariosVistaDTO;
import com.grupobios.bioslis.dto.UsuariosPerfilesDTO;
import com.grupobios.bioslis.entity.DatosUsuarios;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface RegistroUsuariosService {
    public List<DatosUsuarios> getUsuarios() throws BiosLISDAOException;
    public List<DatosUsuarios> getUsuariosLikeNombre(String nombre) throws BiosLISDAOException;
    public List<DatosUsuarios> getUsuariosCargo(Short idCargo) throws BiosLISDAOException;
    public List<DatosUsuarios> getUsuariosCentro(Byte idCentro) throws BiosLISDAOException;
    public List<DatosUsuarios> getUsuariosActivo(String activo) throws BiosLISDAOException;
    public UsuariosPerfilesDTO getUsuarioById(Long idUsuario) throws BiosLISDAOException;
    public DatosUsuarios getUsuarioId(Long idUsuario) throws BiosLISDAOException;
    public DatosUsuarios getUsuarioByRun(String run) throws BiosLISDAOException;
    public DatosUsuarios getUsuarioByUsername(String username) throws BiosLISDAOException;
    public void agregarUsuario(UsuariosPerfilesDTO updto) throws BiosLISDAOException;
    public void actualizarUsuario(UsuariosPerfilesDTO updto) throws BiosLISDAOException;
    public String validarPassword(Long idUsuario, String password) throws BiosLISDAOException;
    public void actualizarPassword(Long idUsuario, String password) throws BiosLISDAOException;
    public List<LogEventosUsuariosVistaDTO> getEventosUsuarioById(Long id, String filtro, int inicio, int fin) throws BiosLISDAOException;
    
}
