/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupobios.bioslis.dto;

import com.grupobios.bioslis.entity.LdvCargosusuarios;
import java.util.List;

/**
 *
 * @author Marco
 */
public class CargosModulosDTO {
    
    private LdvCargosusuarios ldvCargosusuarios;
    private List<ItemsMenuDTO> listItems;

    public LdvCargosusuarios getLdvCargosusuarios() {
        return ldvCargosusuarios;
    }

    public void setLdvCargosusuarios(LdvCargosusuarios ldvCargosusuarios) {
        this.ldvCargosusuarios = ldvCargosusuarios;
    }
    
    public List<ItemsMenuDTO> getListItems() {
        return listItems;
    }

    public void setListItems(List<ItemsMenuDTO> listItems) {
        this.listItems = listItems;
    }

}
