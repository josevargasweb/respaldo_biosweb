/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import java.util.List;

import com.grupobios.bioslis.dto.CargosModulosDTO;
import com.grupobios.bioslis.entity.CfgCargosmodulosbl;
import com.grupobios.bioslis.entity.LdvCargosusuarios;

/**
 *
 * @author Marco
 */
public interface CargosModulosService {
  public List<LdvCargosusuarios> listaCargos() throws BiosLISDAOException;

  public List<CargosModulosDTO> listaCargosModulos() throws BiosLISDAOException;

  public CargosModulosDTO obtenerModulosCargo(short idCargo) throws BiosLISDAOException;

  public void guardarCargosModulos(short idCargo, List<CfgCargosmodulosbl> listCmbl) throws BiosLISDAOException;

  public void agregarCargo(String cargo) throws BiosLISDAOException;

  public CargosModulosDTO listarModulosCargo(short idCargo) throws BiosLISDAOException;

  public LdvCargosusuarios obtenerCargoById(short idCargo) throws BiosLISDAOException;

  public void actualizarCargo(short idCargo, String cargo) throws BiosLISDAOException;

  public void eliminarCargo(LdvCargosusuarios cargo) throws BiosLISDAOException;
}
