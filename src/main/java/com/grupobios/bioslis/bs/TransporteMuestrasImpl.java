/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Base64;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.grupobios.bioslis.common.BiosLisCalendarService;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.DatosFichasmuestrasDAO;
import com.grupobios.bioslis.dto.TransporteMuestrasDTO;



/**
 *
 * @author jan
 */
public class TransporteMuestrasImpl implements TransporteMuestrasService {
	
	DatosFichasmuestrasDAO datosFichasmuestrasDAO;

	public DatosFichasmuestrasDAO getDatosFichasmuestrasDAO() {
		return datosFichasmuestrasDAO;
	}

	public void setDatosFichasmuestrasDAO(DatosFichasmuestrasDAO datosFichasmuestrasDAO) {
		this.datosFichasmuestrasDAO = datosFichasmuestrasDAO;
	}

	@Override
	public TransporteMuestrasDTO getMuestraxCodigoBarra(String codigoBarra) throws BiosLISDAOException {
		// TODO Auto-generated method stub
		return datosFichasmuestrasDAO.getMuestraParaTransporte(codigoBarra);
	}
	
	
}
