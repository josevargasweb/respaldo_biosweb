/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author eric.nicholls
 */
@Controller
public class OrdenBuscadorController {

  ModelAndView mav = new ModelAndView();

  @RequestMapping(value = "/OrdenBuscador", method = RequestMethod.GET)
  public ModelAndView pageLoad() {
    mav.setViewName("/OrdenBuscador");
    return mav;
  }

}
