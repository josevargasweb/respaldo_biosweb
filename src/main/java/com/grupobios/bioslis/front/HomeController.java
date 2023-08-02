/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.entity.DatosUsuarios;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author eric.nicholls
 */
@Controller 
public class HomeController {
    
    ModelAndView mav = new ModelAndView();
    DatosUsuariosDAO duDao = new DatosUsuariosDAO();
    DatosUsuarios usuario = new DatosUsuarios();
 
    @RequestMapping(value="/Home",method = RequestMethod.GET)
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
      
        HttpSession sesion = (HttpSession) request.getSession();
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, 0);
                       
                
        return mav;
    }   
    
    
}
