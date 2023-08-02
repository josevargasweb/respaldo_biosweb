/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.entity.CfgSecciones;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface SeccionesService {
    
    List<CfgSecciones> getSeccionesFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    CfgSecciones getSeccionById(int idSeccion) throws BiosLISDAOException;
    String agregarSeccion(CfgSecciones csec, Long idUsuario) throws BiosLISDAOException;
    String actualizarSeccion(CfgSecciones csec, Long idUsuario) throws BiosLISDAOException;
    int getCountExamenesBySeccion(int idSeccion) throws BiosLISDAOException;
}
