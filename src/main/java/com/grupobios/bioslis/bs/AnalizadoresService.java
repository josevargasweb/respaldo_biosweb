/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.AnalizadorDTO;
import com.grupobios.bioslis.entity.CfgSigmaimagenes;
import com.grupobios.bioslis.entity.SigmaAnalizadores;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marco Caracciolo
 */
public interface AnalizadoresService {
    
    List<SigmaAnalizadores> getAnalizadoresFiltro(HashMap<String, String> filtros) throws BiosLISDAOException;
    
    AnalizadorDTO getAnalizadorById(short idAnalizador) throws BiosLISDAOException;
    
    String insertAnalizador(SigmaAnalizadores sa, Long idUsuario) throws BiosLISDAOException;
    
    void insertSigmaImagen(CfgSigmaimagenes csi) throws BiosLISDAOException;
    
    String updateAnalizador(SigmaAnalizadores sa, Long idUsuario) throws BiosLISDAOException;
    
    void updateSigmaImagen(CfgSigmaimagenes csi, Long idUsuario, Long idAnalizador, String descripcion) throws BiosLISDAOException;
    
    CfgSigmaimagenes getImagenById(short idImagen) throws BiosLISDAOException;
    
}
