/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author jose.vargas
 */
@Controller
public class PortalPacientesController {

  ModelAndView mav = new ModelAndView();

  @RequestMapping(value = "/PortalPacientes", method = RequestMethod.GET)
  public ModelAndView pageLoad() {
    mav.setViewName("/PortalPacientes");
    return mav;
  }
 
}
