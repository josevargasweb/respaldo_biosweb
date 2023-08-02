/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgSigmaImagenesDAO;
import com.grupobios.bioslis.dao.SigmaAnalizadoresDAO;
import com.grupobios.bioslis.dto.AnalizadorDTO;
import com.grupobios.bioslis.entity.CfgSigmaimagenes;
import com.grupobios.bioslis.entity.SigmaAnalizadores;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marco Caracciolo
 */
@Service
public class AnalizadoresServiceImpl implements AnalizadoresService {

    private static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(AnalizadoresServiceImpl.class);
    SigmaAnalizadoresDAO analizadoresDAO;
    CfgSigmaImagenesDAO sigmaImagenesDAO;

    public SigmaAnalizadoresDAO getAnalizadoresDAO() {
        return analizadoresDAO;
    }

    public void setAnalizadoresDAO(SigmaAnalizadoresDAO analizadoresDAO) {
        this.analizadoresDAO = analizadoresDAO;
    }

    public CfgSigmaImagenesDAO getSigmaImagenesDAO() {
        return sigmaImagenesDAO;
    }

    public void setSigmaImagenesDAO(CfgSigmaImagenesDAO sigmaImagenesDAO) {
        this.sigmaImagenesDAO = sigmaImagenesDAO;
    }
    
    @Override
    public List<SigmaAnalizadores> getAnalizadoresFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        String codigo = filtros.get("codigo").toUpperCase();
        String nombre = filtros.get("nombre").toUpperCase();
        return analizadoresDAO.getAnalizadoresFiltro(codigo, nombre);
    }

    @Override
    public AnalizadorDTO getAnalizadorById(short idAnalizador) throws BiosLISDAOException {
        AnalizadorDTO adto = new AnalizadorDTO();
        SigmaAnalizadores sa = analizadoresDAO.getAnalizadorById(idAnalizador);
        adto.setSigmaAnalizadores(sa);
        if (sa.getSaIdimagen() != null){
            CfgSigmaimagenes cs = sigmaImagenesDAO.getSigmaImagenById(sa.getSaIdimagen());
            try {
                byte[] bdata1 = cs.getCsiImagen().getBytes(1, (int) cs.getCsiImagen().length());
                byte[] encodeBase64 = Base64.getEncoder().encode(bdata1);
                String base64encoded = new String(encodeBase64, "UTF-8");
                adto.setImagenAnalizador(base64encoded);
            } catch (SQLException | UnsupportedEncodingException ex) {
                Logger.getLogger(AnalizadoresServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                LOGGER.debug("Error: " + ex.getMessage());
            }
        } 
        return adto;
    }

    @Override
    public String insertAnalizador(SigmaAnalizadores sa, Long idUsuario) throws BiosLISDAOException {
       return analizadoresDAO.insertAnalizadores(sa, idUsuario);
    }

    @Override
    public void insertSigmaImagen(CfgSigmaimagenes csi) throws BiosLISDAOException {
        sigmaImagenesDAO.insertSigmaImagenes(csi);
    }

    @Override
    public String updateAnalizador(SigmaAnalizadores sa, Long idUsuario) throws BiosLISDAOException {
        return analizadoresDAO.updateAnalizadores(sa, idUsuario);
    }

    @Override
    public void updateSigmaImagen(CfgSigmaimagenes csi, Long idUsuario, Long idAnalizador, String descripcion) throws BiosLISDAOException {
        sigmaImagenesDAO.updateSigmaImagenes(csi, idUsuario, idAnalizador, descripcion);
    }
    
    @Override
    public CfgSigmaimagenes getImagenById(short idImagen) throws BiosLISDAOException {
        return sigmaImagenesDAO.getSigmaImagenById(idImagen);
    }
    
}
