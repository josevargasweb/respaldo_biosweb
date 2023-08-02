/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.front;

import com.grupobios.bioslis.bs.ConfigSingletonSession;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgAntecedentesDAO;
import com.grupobios.bioslis.dao.CfgEnvasesDAO;
import com.grupobios.bioslis.dao.CfgExamenesDAO;
import com.grupobios.bioslis.dao.CfgGlosasDAO;
import com.grupobios.bioslis.dao.CfgMetodosDAO;
import com.grupobios.bioslis.dao.CfgMuestrasDAO;
import com.grupobios.bioslis.dao.CfgSeccionesDAO;
import com.grupobios.bioslis.dao.CfgTestDAO;
import com.grupobios.bioslis.dao.CfgTiposderesultadoDAO;
import com.grupobios.bioslis.dao.CfgUnidadesdemedidaDAO;
import com.grupobios.bioslis.dao.LogUsuarioDAO;
import com.grupobios.bioslis.dto.LogEventoUsuarioDTO;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.DatosUsuarios;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
 * @author Nacho
 */
@Controller
public class ConfiguracionTestController {

    ModelAndView mav = new ModelAndView();

    @RequestMapping("/ConfiguracionTest")
        public ModelAndView pageLoad(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        HttpSession sesion = (HttpSession) request.getSession();
        DatosUsuarios usuario = new DatosUsuarios();
        usuario = (DatosUsuarios) sesion.getAttribute("usuario");
        mav = ConfigSingletonSession.getInstance().validarSesionUsuario(mav, sesion, attributes, ViewNames.ID_CONFIGURACION_TEST);
        if (sesion.getAttribute("usuario")!=null){
            try {
                CfgMuestrasDAO cmueDAO = new CfgMuestrasDAO();
                CfgUnidadesdemedidaDAO cmuDAO = new CfgUnidadesdemedidaDAO();
                CfgTiposderesultadoDAO ctrDAO = new CfgTiposderesultadoDAO();
                CfgEnvasesDAO cenvDAO = new CfgEnvasesDAO();
                CfgSeccionesDAO csecDAO = new CfgSeccionesDAO();
                CfgMetodosDAO cmetDAO = new CfgMetodosDAO();
                CfgExamenesDAO ceDAO = new CfgExamenesDAO();
                CfgAntecedentesDAO caDAO = new CfgAntecedentesDAO();
                CfgGlosasDAO cgDAO = new CfgGlosasDAO();
                mav.addObject("listaMuestras", cmueDAO.getMuestras());
                mav.addObject("listaUnidadesMedida", cmuDAO.getUnidadesMedida());
                mav.addObject("listaTiposResultado", ctrDAO.getTiposResultado());
                mav.addObject("listaEnvases", cenvDAO.getEnvases());
                // mav.addObject("listaSecciones", csecDAO.getSecciones());
                mav.addObject("listaMetodos", cmetDAO.getMetodos());
                mav.addObject("listaExamenes", ceDAO.getExamenes());
                mav.addObject("listaAntecedentes", caDAO.getAntecedentes());
                mav.addObject("listaGlosas", cgDAO.getGlosas());
            } catch (BiosLISDAOException ex) {
                Logger.getLogger(ConfiguracionTestController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //aqui se inserta la accion que realiza usuario en modulos ***********     
        if(usuario != null) {
	        LogUsuarioDAO logUsuarioDao = new LogUsuarioDAO();
	        LogEventoUsuarioDTO leu = new LogEventoUsuarioDTO();
	        
	        leu.setLeuIdusuario( (int) usuario.getDuIdusuario());
	        leu.setLeuFechaevento(BiosLisCalendarService.getInstance().getTS());
	        leu.setLeuIdevento(11);              
	        leu.setLeuIdacciondato(0);               
	        leu.setLeuValornuevo("accede a Configuración de test");
	        logUsuarioDao.insertLogEventosUsuario(leu);
        }
       //*-************************************************************ 
        return mav;
    }
/*
    @RequestMapping(value = "/ConfiguracionTest", method = RequestMethod.POST)
    public ModelAndView agregarTest(CfgTest ct, CfgMetodos cm, CfgMuestras cmue, CfgSecciones csec, CfgEnvases ce, CfgUnidadesdemedida cmu, HttpServletRequest request, RedirectAttributes attributes) {
        String[] antecedentes = request.getParameterValues("liAntecedentes");
        CfgTestDAO ctDAO = new CfgTestDAO();
        ct.setCtAbreviado(StringUtils.stripAccents(ct.getCtAbreviado()).toUpperCase().trim());
        ct.setCtDescripcion(StringUtils.stripAccents(ct.getCtDescripcion()).toUpperCase().trim());
        ct.setCfgEnvases(ce);
        ct.setCfgMuestras(cmue);
        ct.setCfgUnidadesdemedida(cmu);
        ctDAO.insertTest(ct);
        ct.setCtIdtest(ctDAO.getLastIdTest());
        // INSERT ANTECEDENTESTEST
        for (String antecedente : antecedentes) {
            CfgAntecedentesDAO caDAO = new CfgAntecedentesDAO();
            CfgAntecedentes antecedent = caDAO.getAntecedenteById(Integer.parseInt(antecedente));
            CfgAntecedentestestId caid = new CfgAntecedentestestId();
            CfgAntecedentestest cat = new CfgAntecedentestest();
            CfgAntecedentestestDAO catDAO = new CfgAntecedentestestDAO();
            caid.setCatIdantecedente(Integer.parseInt(antecedente));
            caid.setCatIdtest(ct.getCtIdtest());
            cat.setId(caid);
            cat.setCfgAntecedentes(antecedent);
            catDAO.insertAntecedentesTest(cat);
        }
        attributes.addFlashAttribute("mensaje", "Test ingresado correctamente");
        return new ModelAndView("redirect:/ConfiguracionTest");
    }

    @RequestMapping(value = "/ConfiguracionTest", method = RequestMethod.POST, params="update")
    public ModelAndView updateTest(CfgTest ct, CfgMetodos cm, CfgMuestras cmue, CfgSecciones csec, CfgEnvases ce, CfgUnidadesdemedida cmu, HttpServletRequest request, RedirectAttributes attributes) {
        int idTest = Integer.parseInt(request.getParameter("idTest"));
        ct.setCtIdtest(idTest);
        String[] antecedentes = request.getParameterValues("liAntecedentes");
        CfgTestDAO ctDAO = new CfgTestDAO();
        ct.setCtAbreviado(StringUtils.stripAccents(ct.getCtAbreviado()).toUpperCase().trim());
        ct.setCtDescripcion(StringUtils.stripAccents(ct.getCtDescripcion()).toUpperCase().trim());
        ct.setCfgEnvases(ce);
        ct.setCfgMuestras(cmue);
        ct.setCfgUnidadesdemedida(cmu);
        ctDAO.updateTest(ct);
        ct.setCtIdtest(ctDAO.getLastIdTest());
        
        // INSERT ANTECEDENTESTEST
        /*
        for (String antecedente : antecedentes) {
            CfgAntecedentesDAO caDAO = new CfgAntecedentesDAO();
            CfgAntecedentes antecedent = caDAO.getAntecedenteById(Integer.parseInt(antecedente));
            CfgAntecedentestestId caid = new CfgAntecedentestestId();
            CfgAntecedentestest cat = new CfgAntecedentestest();
            CfgAntecedentestestDAO catDAO = new CfgAntecedentestestDAO();
            caid.setCatIdantecedente(Integer.parseInt(antecedente));
            caid.setCatIdtest(ct.getCtIdtest());
            cat.setId(caid);
            cat.setCfgAntecedentes(antecedent);
            catDAO.insertAntecedentesTest(cat);
        }
*/ 
        /*
        attributes.addFlashAttribute("mensaje", "Test actualizado correctamente");
        return new ModelAndView("redirect:/ConfiguracionTest");
    }
*/
    @RequestMapping(value = "/ConfiguracionTest", method = RequestMethod.POST, params = "codigoFiltro")
    public void filtroCodigoTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String codigo = request.getParameter("codigoTest");
            codigo = codigo.toUpperCase().trim();
            CfgTestDAO ctDAO = new CfgTestDAO();
            List<CfgTest> listaTest = ctDAO.getTestbyCodigo(codigo);
            CfgTest test = listaTest.get(0);
            PrintWriter writer = response.getWriter();
            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            JsonObjectBuilder testBuilder = Json.createObjectBuilder();
            JsonObject testJson;
            testBuilder.add("CodigoTest", test.getCtCodigo())
                    .add("NombreTest", test.getCtDescripcion())
                    .add("IdTest", test.getCtIdtest())
                    .add("TipoMuestra", test.getCfgMuestras().getCmueIdtipomuestra())
                    .add("Seccion", test.getCfgSecciones())
                    .add("Envase", test.getCfgEnvases().getCenvIdenvase())
                    .add("TipoResultado", test.getCfgTiposderesultado().getCtrIdtiporesultado());
            
            
            testJson = testBuilder.build();
            arrayBuilder.add(testJson);
            JsonObject root = rootBuilder.add("test", arrayBuilder).build();
            writer.print(root);
        } catch (BiosLISDAOException ex) {
            Logger.getLogger(ConfiguracionTestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/*
    @RequestMapping(value = "/ConfiguracionTest", method = RequestMethod.POST, params = "filtro")
    public void filtroTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigo = request.getParameter("codigoTest");
        String nombre = request.getParameter("nombreFiltro");
        codigo = StringUtils.stripAccents(codigo).toUpperCase().trim();
        nombre = StringUtils.stripAccents(nombre).toUpperCase().trim();
        String idExamen = request.getParameter("idExamen");
        String idSeccion = request.getParameter("idSeccion");
        PrintWriter writer = response.getWriter();
        JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        CfgTestDAO ctDAO = new CfgTestDAO();
        CfgExamenesTestDAO cetDAO = new CfgExamenesTestDAO();
        List<CfgTest> listaTest = new ArrayList<CfgTest>();
        List<CfgExamenestest> lista;
        if (codigo.length() > 0) {
            listaTest = ctDAO.getTestsByLikeCodigo(codigo);
        } else {
            if (!"N".equals(idExamen)) {
                int idEx = Integer.parseInt(idExamen);
                lista = cetDAO.getTestByExamen(idEx, nombre);
                for (CfgExamenestest cfgExamenestest : lista) {
                    listaTest.add(cfgExamenestest.getCfgTest());
                }
            } else {
                if (!"N".equals(idSeccion)) {
                    int idSecc = Integer.parseInt(idSeccion);
                    listaTest = ctDAO.getTestBySeccion(idSecc, nombre);
                } else {
                    listaTest = ctDAO.getTestByLikeNombre(nombre);
                }
            }
        }
        for (CfgTest test : listaTest) {
            JsonObjectBuilder testBuilder = Json.createObjectBuilder();
            JsonObject testJson;
            testBuilder.add("CodigoTest", test.getCtCodigo())
                    .add("NombreTest", test.getCtDescripcion())
                    .add("Activo", test.getCtActivo());
            testJson = testBuilder.build();
            arrayBuilder.add(testJson);
        }
        JsonObject root = rootBuilder.add("test", arrayBuilder).build();
        writer.print(root);
    }
    */
   
   
	
    
}
