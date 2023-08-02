/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.util.ArrayList;
import java.util.List;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgBioslismodulosDAO;
import com.grupobios.bioslis.dao.CfgPerfilesusuariosDAO;
import com.grupobios.bioslis.dao.CfgUsuariosModulosDAO;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dto.ItemsMenuDTO;
import com.grupobios.bioslis.dto.UsuariosModulosDTO;
import com.grupobios.bioslis.entity.CfgBioslismodulos;
import com.grupobios.bioslis.entity.CfgPerfilesusuarios;
import com.grupobios.bioslis.entity.CfgUsuariosModulos;
import com.grupobios.bioslis.entity.CfgUsuariosModulosId;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.front.ViewNames;

import java.math.BigDecimal;

/**
 *
 * @author Marco Caracciolo
 */
public class UsuariosModulosServiceImpl implements UsuariosModulosService {

  CfgUsuariosModulosDAO cfgUsuariosModulosDAO;
  DatosUsuariosDAO datosUsuariosDAO;
  CfgBioslismodulosDAO cfgBioslismodulosDAO;
  CfgPerfilesusuariosDAO cfgPerfilesusuariosDAO;

  public CfgUsuariosModulosDAO getCfgUsuariosModulosDAO() {
    return cfgUsuariosModulosDAO;
  }

  public void setCfgUsuariosModulosDAO(CfgUsuariosModulosDAO cfgUsuariosModulosDAO) {
    this.cfgUsuariosModulosDAO = cfgUsuariosModulosDAO;
  }

  public DatosUsuariosDAO getDatosUsuariosDAO() {
    return datosUsuariosDAO;
  }

  public void setDatosUsuariosDAO(DatosUsuariosDAO datosUsuariosDAO) {
    this.datosUsuariosDAO = datosUsuariosDAO;
  }

  public CfgBioslismodulosDAO getCfgBioslismodulosDAO() {
    return cfgBioslismodulosDAO;
  }

  public void setCfgBioslismodulosDAO(CfgBioslismodulosDAO cfgBioslismodulosDAO) {
    this.cfgBioslismodulosDAO = cfgBioslismodulosDAO;
  }

  @Override
  public UsuariosModulosDTO getModulosUsuario(long idUsuario) throws BiosLISDAOException {
    UsuariosModulosDTO usuarioModulos = new UsuariosModulosDTO();
    DatosUsuarios datosUsuarios = datosUsuariosDAO.getUsuarioById(idUsuario);
    usuarioModulos.setDatosUsuarios(datosUsuarios);
    List<CfgUsuariosModulos> listModulosUsuario = cfgUsuariosModulosDAO.listUsuariosModulos(idUsuario);

    if (!listModulosUsuario.isEmpty()) {
      List<CfgBioslismodulos> listModulos = new ArrayList<>();
      for (CfgUsuariosModulos cum : listModulosUsuario) {
        CfgBioslismodulos cblm = cfgBioslismodulosDAO.getModuloById(cum.getId().getCumodIdbioslismodulo());
        listModulos.add(cblm);
      }
      usuarioModulos.setListCfgBioslismodulos(listModulos);
    }

    return usuarioModulos;
  }

  @Override
  public List<ItemsMenuDTO> getItemsMenuUsuario(long idUsuario) throws BiosLISDAOException {
    //List<ItemsMenuDTO> menu = new ArrayList<>();
    List<ItemsMenuDTO> listModulosUsuario = cfgUsuariosModulosDAO.listUsuariosxModulos(idUsuario);
    
    if (listModulosUsuario != null) {

      for (ItemsMenuDTO cum : listModulosUsuario) {  

              BigDecimal idModulo = cum.getCBMIDBIOSLISMODULO();
            //item.setUrl(ViewNames.modulos.get(idModulo.intValue()));
              cum.setUrl(ViewNames.viewModulos.get(idModulo.intValue()));
              cum.setIcono("");
              cum.setTieneAcceso("S");
        }
      }

    return listModulosUsuario;
  }

  @Override
  public List<ItemsMenuDTO> getAccesosUsuario(long idUsuario) throws BiosLISDAOException {
    List<ItemsMenuDTO> listItems = new ArrayList<>();
    List<CfgBioslismodulos> lstModulosPrimerNivel = cfgBioslismodulosDAO.listaModulosPrimerNivel();
    for (CfgBioslismodulos modNvl1 : lstModulosPrimerNivel) {
      ItemsMenuDTO itemNvl1 = new ItemsMenuDTO();
      //itemNvl1.setBioslismodulos(modNvl1);
      itemNvl1.setCBMACTIVO(modNvl1.getCbmActivo());
      itemNvl1.setCBMIDBIOSLISMODULO(new BigDecimal(modNvl1.getCbmIdbioslismodulo()));
      itemNvl1.setCBMNOMBREMODULO(modNvl1.getCbmNombremodulo());
      itemNvl1.setCBMPRIMERNIVEL(new BigDecimal(modNvl1.getCbmPrimernivel()));
      itemNvl1.setCBMSEGUNDONIVEL(new BigDecimal(modNvl1.getCbmSegundonivel()));
      itemNvl1.setCBMTERCERNIVEL(new BigDecimal(modNvl1.getCbmTercernivel()));
      
      itemNvl1.setTieneAcceso(cfgUsuariosModulosDAO.existeAccesoUsuario(idUsuario, modNvl1.getCbmIdbioslismodulo()));
      List<CfgBioslismodulos> lstModulosSegundoNivel = cfgBioslismodulosDAO
          .listaModulosSegundoNivel(modNvl1.getCbmPrimernivel());
      if (!lstModulosSegundoNivel.isEmpty()) {
        List<ItemsMenuDTO> listItemsNvl2 = new ArrayList<>();
        for (CfgBioslismodulos modNvl2 : lstModulosSegundoNivel) {
          ItemsMenuDTO itemNvl2 = new ItemsMenuDTO();
        //  itemNvl2.setBioslismodulos(modNvl2);
          itemNvl2.setCBMACTIVO(modNvl2.getCbmActivo());
          itemNvl2.setCBMIDBIOSLISMODULO(new BigDecimal(modNvl2.getCbmIdbioslismodulo()));
          itemNvl2.setCBMNOMBREMODULO(modNvl2.getCbmNombremodulo());
          itemNvl2.setCBMPRIMERNIVEL(new BigDecimal(modNvl2.getCbmPrimernivel()));
          itemNvl2.setCBMSEGUNDONIVEL(new BigDecimal(modNvl2.getCbmSegundonivel()));
          itemNvl2.setCBMTERCERNIVEL(new BigDecimal(modNvl2.getCbmTercernivel()));
          itemNvl2
              .setTieneAcceso(cfgUsuariosModulosDAO.existeAccesoUsuario(idUsuario, modNvl2.getCbmIdbioslismodulo()));
          List<CfgBioslismodulos> lstModulosTercerNivel = cfgBioslismodulosDAO
              .listaModulosTercerNivel(modNvl2.getCbmPrimernivel(), modNvl2.getCbmSegundonivel());
          if (!lstModulosTercerNivel.isEmpty()) {
            List<ItemsMenuDTO> listItemsNvl3 = new ArrayList<>();
            for (CfgBioslismodulos modNvl3 : lstModulosTercerNivel) {
              ItemsMenuDTO itemNvl3 = new ItemsMenuDTO();
              //itemNvl3.setBioslismodulos(modNvl3);
              itemNvl3.setCBMACTIVO(modNvl3.getCbmActivo());
              itemNvl3.setCBMIDBIOSLISMODULO(new BigDecimal(modNvl3.getCbmIdbioslismodulo()));
              itemNvl3.setCBMNOMBREMODULO(modNvl3.getCbmNombremodulo());
              itemNvl3.setCBMPRIMERNIVEL(new BigDecimal(modNvl3.getCbmPrimernivel()));
              itemNvl3.setCBMSEGUNDONIVEL(new BigDecimal(modNvl3.getCbmSegundonivel()));
              itemNvl3.setCBMTERCERNIVEL(new BigDecimal(modNvl3.getCbmTercernivel()));
              itemNvl3.setTieneAcceso(
                  cfgUsuariosModulosDAO.existeAccesoUsuario(idUsuario, modNvl3.getCbmIdbioslismodulo()));
              listItemsNvl3.add(itemNvl3);
            }
            itemNvl2.setSubitems(listItemsNvl3);
          }
          listItemsNvl2.add(itemNvl2);
        }
        itemNvl1.setSubitems(listItemsNvl2);
      }
      listItems.add(itemNvl1);
    }
    return listItems;
  }

  @Override
  public void guardarUsuarioModulos(long idUsuario, List<Integer> modulos) throws BiosLISDAOException {
    DatosUsuarios usuario = datosUsuariosDAO.getUsuarioById(idUsuario);
    for (Integer modulo : modulos) {
      CfgUsuariosModulos newModulo = new CfgUsuariosModulos();
      newModulo.setDatosUsuarios(usuario);
      CfgUsuariosModulosId id = new CfgUsuariosModulosId();
      id.setCumodIdbioslismodulo(modulo.shortValue());
      id.setCumodIdusuario(idUsuario);
      newModulo.setId(id);
      cfgUsuariosModulosDAO.agregarUsuarioModulo(newModulo);
    }
  }

  @Override
  public void actualizarUsuarioModulos(long idUsuario, List<Integer> modulos) throws BiosLISDAOException {
    List<CfgUsuariosModulos> listActualModulos = cfgUsuariosModulosDAO.listUsuariosModulos(idUsuario);
    DatosUsuarios usuario = datosUsuariosDAO.getUsuarioById(idUsuario);
    boolean existe = false;
    if (!modulos.isEmpty()) {
      for (Integer modulo : modulos) {
        existe = false;
        for (CfgUsuariosModulos cumodAct : listActualModulos) {
          if (cumodAct.getId().getCumodIdbioslismodulo() == modulo) {
            existe = true;
            break;
          }
        }
        if (existe == false) {
          CfgUsuariosModulos newModulo = new CfgUsuariosModulos();
          newModulo.setDatosUsuarios(usuario);
          CfgUsuariosModulosId id = new CfgUsuariosModulosId();
          id.setCumodIdbioslismodulo(modulo.shortValue());
          id.setCumodIdusuario(idUsuario);
          newModulo.setId(id);
          cfgUsuariosModulosDAO.agregarUsuarioModulo(newModulo);
        }
      }
    }

    // Esta vez recorre para saber si existe algun modulo en bd que se desmarcó al
    // actualizar
    for (CfgUsuariosModulos modExistentes : listActualModulos) {
      boolean existeAun = false;
      for (Integer modulo : modulos) {
        if (modExistentes.getId().getCumodIdbioslismodulo() == modulo) {
          existeAun = true;
          break;
        }
      }
      if (existeAun == false) {
        cfgUsuariosModulosDAO.eliminarUsuarioModulo(modExistentes);
      }
    }

  }

  @Override
  public CfgPerfilesusuarios getPerfilUsuario(Long idUsuario) throws BiosLISDAOException {
    return cfgPerfilesusuariosDAO.getPerfilesUsuariosById(idUsuario);
  }

  public CfgPerfilesusuariosDAO getCfgPerfilesusuariosDAO() {
    return cfgPerfilesusuariosDAO;
  }

  public void setCfgPerfilesusuariosDAO(CfgPerfilesusuariosDAO cfgPerfilesusuariosDAO) {
    this.cfgPerfilesusuariosDAO = cfgPerfilesusuariosDAO;
  }

}
