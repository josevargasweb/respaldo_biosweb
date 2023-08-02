/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Marco Caracciolo
 */
public interface SessionService {

  public ModelAndView validarSesionUsuario(ModelAndView mav, HttpSession sesion, RedirectAttributes attributes,
      int idModulo);

}
