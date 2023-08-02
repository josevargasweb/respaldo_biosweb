/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import java.util.List;

/**
 *
 * @author marco.cortez
 */
public interface ConfigPrefijosService {
    
    public String asignarPrefijo() throws BiosLISDAOException;
    public List<String> generarPrefijos() throws BiosLISDAOException;
    public List<Object[]> listarPrefijosMuestras() throws BiosLISDAOException;
    
}
