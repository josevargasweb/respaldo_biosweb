/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import com.grupobios.bioslis.dao.BiosLISDAOException;

import com.grupobios.bioslis.dto.DatosUsuariosDTO;

/**
 *
 * @author Jan
 */
public interface UsuarioService {
	
	public Object[] getUsuarioByUsername(DatosUsuariosDTO username,HttpServletRequest context) throws BiosLISDAOException;
}
