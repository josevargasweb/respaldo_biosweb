/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.DatosUsuariosDAO;
import com.grupobios.bioslis.entity.DatosUsuarios;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author eric.nicholls
 */
@Controller 
public class Home_newController {
    
    ModelAndView mav = new ModelAndView();
 
    @RequestMapping(value="/Home_new",method = RequestMethod.GET)
    public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response){
        
        try {
            HttpSession sesion = request.getSession(true);
            DatosUsuariosDAO duDao = new DatosUsuariosDAO();
            DatosUsuarios usuario = duDao.getUsuarioById(1);
            sesion.setAttribute("usuario", usuario);
            
            mav.setViewName("Home_new");
            
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(Home_newController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mav;
    }   
}
