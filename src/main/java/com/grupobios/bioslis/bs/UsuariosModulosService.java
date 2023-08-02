/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.util.List;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.ItemsMenuDTO;
import com.grupobios.bioslis.dto.UsuariosModulosDTO;
import com.grupobios.bioslis.entity.CfgPerfilesusuarios;

/**
 *
 * @author Marco Caracciolo
 */
public interface UsuariosModulosService {

  public UsuariosModulosDTO getModulosUsuario(long idUsuario) throws BiosLISDAOException;

  public List<ItemsMenuDTO> getItemsMenuUsuario(long idUsuario) throws BiosLISDAOException;

  public List<ItemsMenuDTO> getAccesosUsuario(long idUsuario) throws BiosLISDAOException;

  public void guardarUsuarioModulos(long idUsuario, List<Integer> modulos) throws BiosLISDAOException;

  public void actualizarUsuarioModulos(long idUsuario, List<Integer> modulos) throws BiosLISDAOException;

  public CfgPerfilesusuarios getPerfilUsuario(Long idUsuario) throws BiosLISDAOException;
  


}
