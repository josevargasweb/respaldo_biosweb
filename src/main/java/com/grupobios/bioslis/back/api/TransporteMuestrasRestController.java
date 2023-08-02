package com.grupobios.bioslis.back.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import com.grupobios.bioslis.bs.TransporteMuestrasService;

import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;

import com.grupobios.bioslis.dto.TransporteMuestrasDTO;

import com.grupobios.bioslis.entity.DatosUsuarios;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jan
 */
@RestController
public class TransporteMuestrasRestController {

	private static Logger logger = LogManager.getLogger(TomaMuestrasRestController.class);
	TransporteMuestrasService transporteMuestrasService;

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		TransporteMuestrasRestController.logger = logger;
	}

	public TransporteMuestrasService getTransporteMuestrasService() {
		return transporteMuestrasService;
	}

	public void setTransporteMuestrasService(TransporteMuestrasService transporteMuestrasService) {
		this.transporteMuestrasService = transporteMuestrasService;
	}

	@GetMapping("/api/transporteMuestras/{codigoBarra}/search")
	public ResponseTemplateGen<TransporteMuestrasDTO> searhMuestraxCodigoBarra(
			@PathVariable("codigoBarra") String codigoBarra, @Context HttpServletRequest context) {
		//
		HttpSession sesion = (HttpSession) context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");

		try {
			TransporteMuestrasDTO response = transporteMuestrasService.getMuestraxCodigoBarra(codigoBarra);
			if (response == null) {
				return new ResponseTemplateGen<>(response, 404, "Codigo no existe");
			}
			return new ResponseTemplateGen<>(response, 200, "Muestra encontrada");
		} catch (BiosLISDAOException ex) {
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

}
