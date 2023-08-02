/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgBioslismodulosDAO;
import com.grupobios.bioslis.dao.CfgCargosmodulosblDAO;
import com.grupobios.bioslis.dao.CfgUsuariosModulosDAO;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.dao.LdvCargosUsuariosDAO;
import com.grupobios.bioslis.dto.CargosModulosDTO;
import com.grupobios.bioslis.dto.ItemsMenuDTO;
import com.grupobios.bioslis.entity.CfgBioslismodulos;
import com.grupobios.bioslis.entity.CfgCargosmodulosbl;
import com.grupobios.bioslis.entity.LdvCargosusuarios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marco
 */
@Service
public class CargosModulosServiceImpl implements CargosModulosService {

    private static Logger logger = LogManager.getLogger(CargosModulosServiceImpl.class);
    LdvCargosUsuariosDAO ldvCargosUsuariosDAO;
    CfgCargosmodulosblDAO cfgCargosmodulosblDAO;
    CfgBioslismodulosDAO cfgBioslismodulosDAO;
    CfgUsuariosModulosDAO cfgUsuariosModulosDAO;
    DatosUsuariosDAO datosUsuariosDAO;
    
    public LdvCargosUsuariosDAO getLdvCargosUsuariosDAO() {
        return ldvCargosUsuariosDAO;
    }

    public void setLdvCargosUsuariosDAO(LdvCargosUsuariosDAO ldvCargosUsuariosDAO) {
        this.ldvCargosUsuariosDAO = ldvCargosUsuariosDAO;
    }

    public CfgCargosmodulosblDAO getCfgCargosmodulosblDAO() {
        return cfgCargosmodulosblDAO;
    }

    public void setCfgCargosmodulosblDAO(CfgCargosmodulosblDAO cfgCargosmodulosblDAO) {
        this.cfgCargosmodulosblDAO = cfgCargosmodulosblDAO;
    }

    public CfgBioslismodulosDAO getCfgBioslismodulosDAO() {
        return cfgBioslismodulosDAO;
    }

    public void setCfgBioslismodulosDAO(CfgBioslismodulosDAO cfgBioslismodulosDAO) {
        this.cfgBioslismodulosDAO = cfgBioslismodulosDAO;
    }

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

    @Override
    public List<CargosModulosDTO> listaCargosModulos() throws BiosLISDAOException {
        List<CargosModulosDTO> listCargosModulos = new ArrayList<>();
        List<LdvCargosusuarios> listaCargos = ldvCargosUsuariosDAO.getCargosUsuarios();
        for (LdvCargosusuarios cargo: listaCargos){
            CargosModulosDTO cargosModulos = new CargosModulosDTO();
            cargosModulos.setLdvCargosusuarios(cargo);
            List<ItemsMenuDTO> listItemsNvl1 = new ArrayList<>();
            List<CfgBioslismodulos> lstModulosPrimerNivel = cfgBioslismodulosDAO.listaModulosPrimerNivel();
            for (CfgBioslismodulos modNvl1: lstModulosPrimerNivel){
                ItemsMenuDTO itemNvl1 = new ItemsMenuDTO();
                //itemNvl1.setBioslismodulos(modNvl1);
                itemNvl1.setCBMACTIVO(modNvl1.getCbmActivo());
                itemNvl1.setCBMIDBIOSLISMODULO(new BigDecimal(modNvl1.getCbmIdbioslismodulo()));
                itemNvl1.setCBMNOMBREMODULO(modNvl1.getCbmNombremodulo());
                itemNvl1.setCBMPRIMERNIVEL(new BigDecimal(modNvl1.getCbmPrimernivel()));
                itemNvl1.setCBMSEGUNDONIVEL(new BigDecimal(modNvl1.getCbmSegundonivel()));
                itemNvl1.setCBMTERCERNIVEL(new BigDecimal(modNvl1.getCbmTercernivel()));
                itemNvl1.setTieneAcceso(cfgCargosmodulosblDAO.existeAccesoCargo(cargo.getLdvcuIdcargo(), modNvl1.getCbmIdbioslismodulo()));
                List<ItemsMenuDTO> listItemsNvl2 = new ArrayList<>();
                List<CfgBioslismodulos> lstModulosSegundoNivel = cfgBioslismodulosDAO.listaModulosSegundoNivel(modNvl1.getCbmPrimernivel());
                for (CfgBioslismodulos modNvl2: lstModulosSegundoNivel){
                    ItemsMenuDTO itemNvl2 = new ItemsMenuDTO();
                  //  itemNvl2.setBioslismodulos(modNvl2);
                    itemNvl2.setCBMACTIVO(modNvl2.getCbmActivo());
                    itemNvl2.setCBMIDBIOSLISMODULO(new BigDecimal(modNvl2.getCbmIdbioslismodulo()));
                    itemNvl2.setCBMNOMBREMODULO(modNvl2.getCbmNombremodulo());
                    itemNvl2.setCBMPRIMERNIVEL(new BigDecimal(modNvl2.getCbmPrimernivel()));
                    itemNvl2.setCBMSEGUNDONIVEL(new BigDecimal(modNvl2.getCbmSegundonivel()));
                    itemNvl2.setCBMTERCERNIVEL(new BigDecimal(modNvl2.getCbmTercernivel()));
                    itemNvl2.setTieneAcceso(cfgCargosmodulosblDAO.existeAccesoCargo(cargo.getLdvcuIdcargo(), modNvl2.getCbmIdbioslismodulo()));
                    List<ItemsMenuDTO> listItemsNvl3 = new ArrayList<>();
                    List<CfgBioslismodulos> lstModulosTercerNivel = cfgBioslismodulosDAO.listaModulosTercerNivel(modNvl2.getCbmPrimernivel(), modNvl2.getCbmSegundonivel());
                    for (CfgBioslismodulos modNvl3: lstModulosTercerNivel) {
                        ItemsMenuDTO itemNvl3 = new ItemsMenuDTO();
                    //    itemNvl3.setBioslismodulos(modNvl3);
                        itemNvl3.setCBMACTIVO(modNvl3.getCbmActivo());
                        itemNvl3.setCBMIDBIOSLISMODULO(new BigDecimal(modNvl3.getCbmIdbioslismodulo()));
                        itemNvl3.setCBMNOMBREMODULO(modNvl3.getCbmNombremodulo());
                        itemNvl3.setCBMPRIMERNIVEL(new BigDecimal(modNvl3.getCbmPrimernivel()));
                        itemNvl3.setCBMSEGUNDONIVEL(new BigDecimal(modNvl3.getCbmSegundonivel()));
                        itemNvl3.setCBMTERCERNIVEL(new BigDecimal(modNvl3.getCbmTercernivel()));
                        itemNvl3.setTieneAcceso(cfgCargosmodulosblDAO.existeAccesoCargo(cargo.getLdvcuIdcargo(), modNvl3.getCbmIdbioslismodulo()));
                        listItemsNvl3.add(itemNvl3);
                    }
                    itemNvl2.setSubitems(listItemsNvl3);
                    listItemsNvl2.add(itemNvl2);
                }
                itemNvl1.setSubitems(listItemsNvl2);
                listItemsNvl1.add(itemNvl1);
            }
            cargosModulos.setListItems(listItemsNvl1);
            listCargosModulos.add(cargosModulos);
        }
        return listCargosModulos;
    }

    @Override
    public CargosModulosDTO obtenerModulosCargo(short idCargo) throws BiosLISDAOException {
        LdvCargosusuarios cargoUsr = ldvCargosUsuariosDAO.getCargosById(idCargo);
        
        CargosModulosDTO cargosModulos = new CargosModulosDTO();
        cargosModulos.setLdvCargosusuarios(cargoUsr);
        List<CfgCargosmodulosbl> listCM = cfgCargosmodulosblDAO.listModulosPorCargo(idCargo);
        if (!listCM.isEmpty()){
            List<ItemsMenuDTO> listModulos = new ArrayList<>();
            //List<CfgBioslismodulos> listModulos = new ArrayList<>();
            for (CfgCargosmodulosbl cm : listCM){
                ItemsMenuDTO item = new ItemsMenuDTO();
                CfgBioslismodulos cblm = cfgBioslismodulosDAO.getModuloById(cm.getId().getCcmIdbioslismodulo());
              //  item.setBioslismodulos(cblm);
                item.setCBMACTIVO(cblm.getCbmActivo());
                item.setCBMIDBIOSLISMODULO(new BigDecimal(cblm.getCbmIdbioslismodulo()));
                item.setCBMNOMBREMODULO(cblm.getCbmNombremodulo());
                item.setCBMPRIMERNIVEL(new BigDecimal(cblm.getCbmPrimernivel()));
                item.setCBMSEGUNDONIVEL(new BigDecimal(cblm.getCbmSegundonivel()));
                item.setCBMTERCERNIVEL(new BigDecimal(cblm.getCbmTercernivel()));
                item.setTieneAcceso("S");
                listModulos.add(item);
                //listModulos.add(modNvl1);
            }
            //cargosModulos.setListCfgBioslismodulos(listModulos);
            cargosModulos.setListItems(listModulos);
        }
         
        return cargosModulos;
    }
    
    @Override
    public List<LdvCargosusuarios> listaCargos() throws BiosLISDAOException {
        return ldvCargosUsuariosDAO.getCargosUsuarios();
    }

    @Override
    public void guardarCargosModulos(short idCargo, List<CfgCargosmodulosbl> listCmbl) throws BiosLISDAOException {
        List<CfgCargosmodulosbl> listActualModulos = cfgCargosmodulosblDAO.listModulosPorCargo(idCargo);
        
        boolean existe = false;
        if (!listCmbl.isEmpty()){
            for (CfgCargosmodulosbl ccmblNew: listCmbl){
                existe = false;
                for (CfgCargosmodulosbl ccmblAct : listActualModulos) {
                    logger.debug("*** ID Cargo: " + ccmblAct.getId().getCcmIdcargo() + " ***");
                    logger.debug("*** ID Módulo: " + ccmblAct.getId().getCcmIdbioslismodulo() + " ***");
                    if (ccmblAct.getId().getCcmIdcargo() == ccmblNew.getId().getCcmIdcargo()
                            && ccmblAct.getId().getCcmIdbioslismodulo() == ccmblNew.getId().getCcmIdbioslismodulo()){
                        existe = true;
                        break;
                    }
                    
                }
                if (existe == false){
                    cfgCargosmodulosblDAO.agregarCargoModulo(ccmblNew);
                }
            }
        }
        
        //Esta vez recorre para saber si existe algun modulo en bd que se desmarcó al actualizar
        for (CfgCargosmodulosbl modExistentes : listActualModulos){
            boolean existeEnBd = false;
            for (CfgCargosmodulosbl agregados: listCmbl){
                if (agregados.getId().getCcmIdcargo() == modExistentes.getId().getCcmIdcargo()
                            && agregados.getId().getCcmIdbioslismodulo() == modExistentes.getId().getCcmIdbioslismodulo()){
                    existeEnBd = true;
                    break;
                }
            }
            if (existeEnBd == false){
                cfgCargosmodulosblDAO.eliminarCargoModulo(modExistentes);
            }
        }
        
        
    }

    @Override
    public void agregarCargo(String cargo) throws BiosLISDAOException {
        LdvCargosusuarios newCargo = new LdvCargosusuarios();
        newCargo.setLdvcuDescripcion(cargo);
        ldvCargosUsuariosDAO.agregarCargo(newCargo);
    }

    @Override
    public CargosModulosDTO listarModulosCargo(short idCargo) throws BiosLISDAOException {
        LdvCargosusuarios cargoUsr = ldvCargosUsuariosDAO.getCargosById(idCargo);
        CargosModulosDTO cargosModulos = new CargosModulosDTO();
        cargosModulos.setLdvCargosusuarios(cargoUsr);
        List<ItemsMenuDTO> listItemsNvl1 = new ArrayList<>();
        List<CfgBioslismodulos> lstModulosPrimerNivel = cfgBioslismodulosDAO.listaModulosPrimerNivel();
        for (CfgBioslismodulos modNvl1: lstModulosPrimerNivel){
            ItemsMenuDTO itemNvl1 = new ItemsMenuDTO();
           // itemNvl1.setBioslismodulos(modNvl1);
            itemNvl1.setCBMACTIVO(modNvl1.getCbmActivo());
            itemNvl1.setCBMIDBIOSLISMODULO(new BigDecimal(modNvl1.getCbmIdbioslismodulo()));
            itemNvl1.setCBMNOMBREMODULO(modNvl1.getCbmNombremodulo());
            itemNvl1.setCBMPRIMERNIVEL(new BigDecimal(modNvl1.getCbmPrimernivel()));
            itemNvl1.setCBMSEGUNDONIVEL(new BigDecimal(modNvl1.getCbmSegundonivel()));
            itemNvl1.setCBMTERCERNIVEL(new BigDecimal(modNvl1.getCbmTercernivel()));
            itemNvl1.setTieneAcceso(cfgCargosmodulosblDAO.existeAccesoCargo(idCargo, modNvl1.getCbmIdbioslismodulo()));
            List<ItemsMenuDTO> listItemsNvl2 = new ArrayList<>();
            List<CfgBioslismodulos> lstModulosSegundoNivel = cfgBioslismodulosDAO.listaModulosSegundoNivel(modNvl1.getCbmPrimernivel());
            for (CfgBioslismodulos modNvl2: lstModulosSegundoNivel){
                ItemsMenuDTO itemNvl2 = new ItemsMenuDTO();
               // itemNvl2.setBioslismodulos(modNvl2);
                itemNvl2.setCBMACTIVO(modNvl2.getCbmActivo());
                itemNvl2.setCBMIDBIOSLISMODULO(new BigDecimal(modNvl2.getCbmIdbioslismodulo()));
                itemNvl2.setCBMNOMBREMODULO(modNvl2.getCbmNombremodulo());
                itemNvl2.setCBMPRIMERNIVEL(new BigDecimal(modNvl2.getCbmPrimernivel()));
                itemNvl2.setCBMSEGUNDONIVEL(new BigDecimal(modNvl2.getCbmSegundonivel()));
                itemNvl2.setCBMTERCERNIVEL(new BigDecimal(modNvl2.getCbmTercernivel()));
                itemNvl2.setTieneAcceso(cfgCargosmodulosblDAO.existeAccesoCargo(idCargo, modNvl2.getCbmIdbioslismodulo()));
                List<ItemsMenuDTO> listItemsNvl3 = new ArrayList<>();
                List<CfgBioslismodulos> lstModulosTercerNivel = cfgBioslismodulosDAO.listaModulosTercerNivel(modNvl2.getCbmPrimernivel(), modNvl2.getCbmSegundonivel());
                for (CfgBioslismodulos modNvl3: lstModulosTercerNivel) {
                    ItemsMenuDTO itemNvl3 = new ItemsMenuDTO();
                   // itemNvl3.setBioslismodulos(modNvl3);
                    itemNvl3.setCBMACTIVO(modNvl3.getCbmActivo());
                    itemNvl3.setCBMIDBIOSLISMODULO(new BigDecimal(modNvl3.getCbmIdbioslismodulo()));
                    itemNvl3.setCBMNOMBREMODULO(modNvl3.getCbmNombremodulo());
                    itemNvl3.setCBMPRIMERNIVEL(new BigDecimal(modNvl3.getCbmPrimernivel()));
                    itemNvl3.setCBMSEGUNDONIVEL(new BigDecimal(modNvl3.getCbmSegundonivel()));
                    itemNvl3.setCBMTERCERNIVEL(new BigDecimal(modNvl3.getCbmTercernivel()));
                    itemNvl3.setTieneAcceso(cfgCargosmodulosblDAO.existeAccesoCargo(idCargo, modNvl3.getCbmIdbioslismodulo()));
                    listItemsNvl3.add(itemNvl3);
                }
                itemNvl2.setSubitems(listItemsNvl3);
                listItemsNvl2.add(itemNvl2);
            }
            itemNvl1.setSubitems(listItemsNvl2);
            listItemsNvl1.add(itemNvl1);
        }
        cargosModulos.setListItems(listItemsNvl1);
        return cargosModulos;
    }

    @Override
    public void eliminarCargo(LdvCargosusuarios cargo) throws BiosLISDAOException {
        ldvCargosUsuariosDAO.eliminarCargo(cargo);
    }

    @Override
    public LdvCargosusuarios obtenerCargoById(short idCargo) throws BiosLISDAOException {
        return ldvCargosUsuariosDAO.getCargosById(idCargo);
    }

    @Override
    public void actualizarCargo(short idCargo, String cargo) throws BiosLISDAOException {
        LdvCargosusuarios lcu = ldvCargosUsuariosDAO.getCargosById(idCargo);
        lcu.setLdvcuDescripcion(cargo);
        ldvCargosUsuariosDAO.actualizarCargo(lcu);
    }
    
}
