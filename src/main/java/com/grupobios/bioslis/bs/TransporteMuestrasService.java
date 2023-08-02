/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;



import com.grupobios.bioslis.dao.BiosLISDAOException;

import com.grupobios.bioslis.dto.TransporteMuestrasDTO;

/**
 *
 * @author Jan
 */
public interface TransporteMuestrasService {
	
	TransporteMuestrasDTO getMuestraxCodigoBarra(String codigoBarra) throws BiosLISDAOException;
}
