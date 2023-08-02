/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.back.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupobios.bioslis.bs.ConfiguracionTestService;
import com.grupobios.bioslis.common.ResponseTemplateGen;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dto.AntecedentePacienteDTO;
import com.grupobios.bioslis.dto.CfgTestDTO;
import com.grupobios.bioslis.dto.CfgTestExcel;
import com.grupobios.bioslis.dto.GlosasTestDTO;
import com.grupobios.bioslis.dto.LaboratorioCentroDTO;
import com.grupobios.bioslis.dto.MetodosTestDTO;
import com.grupobios.bioslis.dto.SeccionLabDTO;
import com.grupobios.bioslis.dto.TestDTO;
import com.grupobios.bioslis.dto.TestExamenSeccionDto;
import com.grupobios.bioslis.dto.TestProcesoDTO;
import com.grupobios.bioslis.entity.CfgAntecedentes;
import com.grupobios.bioslis.entity.CfgBacgrupostest;
import com.grupobios.bioslis.entity.CfgGruposexamenesfonasa;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.CfgTiposderesultado;
import com.grupobios.bioslis.entity.CfgValoresreferencia;
import com.grupobios.bioslis.entity.DatosUsuarios;

/**
 *
 * @author Marco Caracciolo
 */
@RestController
public class ConfiguracionTestRestController {

	ConfiguracionTestService configuracionTestService;

	@GetMapping("/api/test/{idTest}")
	public ResponseEntity<TestDTO> getTestById(@PathVariable("idTest") int idTest,
			@Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return ResponseEntity.status(401).body(null);
		TestDTO test;
		try {
			test = configuracionTestService.getTestById(idTest);
			return ResponseEntity.ok(test);
		} catch (BiosLISDAONotFoundException | BiosLISDAOException e) {

			return ResponseEntity.status(503).body(null);
		}
	}

	@GetMapping("/api/laboratorios/centros/list")
	public ResponseEntity<List<LaboratorioCentroDTO>> getLaboratoriosCentros() {

		try {
			List<LaboratorioCentroDTO> list = configuracionTestService.getLaboratoriosCentros();
			return ResponseEntity.ok(list);
		} catch (BiosLISDAOException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("/api/secciones/laboratorios/list/{idLab}")
	public ResponseEntity<List<SeccionLabDTO>> getSeccionesLaboratorios(@PathVariable("idLab") int idLab) {
		try {
			List<SeccionLabDTO> list = configuracionTestService.getSeccionesLaboratorios(idLab);
			return ResponseEntity.ok(list);
		} catch (BiosLISDAOException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("/api/test/busquedacodigo/{codigo}")
	public ResponseTemplateGen<List<CfgTest>> getTestsByCodigo(@PathVariable("codigo") String codigo,
			@Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			List<CfgTest> listaTest = configuracionTestService.getTestsByCodigo(codigo);
			if (listaTest.isEmpty()) {
				return new ResponseTemplateGen<>(listaTest, 404, "No encontrado");
			}
			return new ResponseTemplateGen<>(listaTest, 200, "Código de test ya existe");
		} catch (BiosLISDAOException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

	@GetMapping("/api/test/antecedentes")
	public ResponseEntity<List<CfgAntecedentes>> getAntecedentesTest() {
		try {
			List<CfgAntecedentes> list = configuracionTestService.listAntecedentes();
			return ResponseEntity.ok(list);
		} catch (BiosLISDAOException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("/api/test/bacGruposTests")
	public ResponseEntity<List<CfgBacgrupostest>> getBacGruposTests() {
		try {
			List<CfgBacgrupostest> list = configuracionTestService.listBacGruposTest();
			return ResponseEntity.ok(list);
		} catch (BiosLISDAOException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("/api/test/gruposExamenesFonasa")
	public ResponseEntity<List<CfgGruposexamenesfonasa>> getGruposExamenesFonasa() {
		try {
			List<CfgGruposexamenesfonasa> list = configuracionTestService.listGruposExamenesFonasa();
			return ResponseEntity.ok(list);
		} catch (BiosLISDAOException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return ResponseEntity.ok(null);
		}
	}

	@PostMapping("/api/test/save")
	public ResponseTemplateGen<CfgTest> guardarTest(@RequestParam("data") String data,
			@RequestParam("antecedentes") List<String> antecedentes,
			@RequestParam("valoresReferencia") String valoresReferencia, @Context HttpServletRequest context)
			throws IOException {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			ObjectMapper om = new ObjectMapper();
			TestDTO testDTO = om.readValue(data, TestDTO.class);
			List<CfgValoresreferencia> listaVR = om.readValue(valoresReferencia,
					om.getTypeFactory().constructCollectionType(List.class, CfgValoresreferencia.class));
			configuracionTestService.agregarTest(testDTO, antecedentes, listaVR, usuario.getDuIdusuario());

			return new ResponseTemplateGen<>(testDTO.getCfgTest(), 200, "Se registró el Test satisfactoriamente");
		} catch (BiosLISDAOException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

	@PostMapping("/api/test/update")
	public ResponseTemplateGen<String> actualizarTest(@RequestParam("data") String data,
			@RequestParam("antecedentes") List<String> antecedentes,
			@RequestParam("valoresReferencia") String valoresReferencia, @Context HttpServletRequest context)
			throws IOException {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			ObjectMapper om = new ObjectMapper();
			TestDTO testDTO = om.readValue(data, TestDTO.class);
			List<CfgValoresreferencia> listaVR = om.readValue(valoresReferencia,
					om.getTypeFactory().constructCollectionType(List.class, CfgValoresreferencia.class));
			configuracionTestService.actualizarTest(testDTO, antecedentes, listaVR, usuario.getDuIdusuario());

			return new ResponseTemplateGen<>(data, 200, "Se modificaron los datos satisfactoriamente");

		} catch (BiosLISDAOException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

	@GetMapping("/api/test/metodosTest/{idTest}")
	public ResponseTemplateGen<List<MetodosTestDTO>> getMetodosTest(@PathVariable("idTest") int idTest,
			@Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			List<MetodosTestDTO> listMetodos = configuracionTestService.listaMetodosTest(idTest);
			// logger.error(e.getMessage());
			return new ResponseTemplateGen<>(listMetodos, 200, "OK");
		} catch (BiosLISDAOException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

	@GetMapping("/api/test/glosasTest/{idTest}/{idSeccion}")
	public ResponseTemplateGen<List<GlosasTestDTO>> getGlosasTest(@PathVariable("idTest") int idTest,
			@PathVariable("idSeccion") int idSeccion, @Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			List<GlosasTestDTO> listGlosas = configuracionTestService.listaGlosasTest(idTest, idSeccion);
			return new ResponseTemplateGen<>(listGlosas, 200, "OK");
		} catch (BiosLISDAOException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseTemplateGen<>(null, 500, "Error: " + ex.getMessage());
		}
	}

	@GetMapping("/api/metodos/test/{idTest}")
	public ResponseTemplateGen<List<MetodosTestDTO>> getMetodosByTest(@PathVariable("idTest") int idTest,
			@Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			List<MetodosTestDTO> listMetodos = configuracionTestService.getMetodosTest(idTest);
			return new ResponseTemplateGen<>(listMetodos, 200, "OK");
		} catch (BiosLISDAOException ex) {
			java.util.logging.Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null,
					ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

//  @GetMapping("/api/test/examenes/{idSeccion}")
//  public ResponseTemplateGen<List<TestDTO>> getTestsExamenesBySeccion(@PathVariable("idSeccion") int idSeccion,
//      @Context HttpServletRequest context) {
//    HttpSession sesion = context.getSession();
//    DatosUsuarios usuario = null;
//    usuario = (DatosUsuarios) sesion.getAttribute("usuario");
//    if (usuario == null)
//      return new ResponseTemplateGen<>(null, 401, "No hay sesion");
//    try {
//      List<TestDTO> listTests = configuracionTestService.getTestDtoBySeccion(idSeccion);
//      return new ResponseTemplateGen<>(listTests, 200, "OK");
//    } catch (BiosLISDAOException ex) {
//      java.util.logging.Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
//      return new ResponseTemplateGen<>(null, 500, ex.getMessage());
//    }
//  }

	@GetMapping("/api/tiposResultado/list")
	public ResponseTemplateGen<List<CfgTiposderesultado>> getTiposResultado(@Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			List<CfgTiposderesultado> listTests = configuracionTestService.listTiposderesultado();
			return new ResponseTemplateGen<>(listTests, 200, "OK");
		} catch (BiosLISDAOException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

	@PostMapping("/api/tests/filtro")
	public ResponseTemplateGen<List<CfgTest>> getTestsFiltro(@RequestBody HashMap<String, String> filtros,
			@Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			List<CfgTest> listaTest = configuracionTestService.getTestsFiltro(filtros);
			return new ResponseTemplateGen<>(listaTest, 200, "Ok");
		} catch (BiosLISDAOException | BiosLISDAONotFoundException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

	@GetMapping("/api/test/examenes/{idSeccion}/{idExamen}")
	public ResponseTemplateGen<List<TestProcesoDTO>> getTestsBusquedaProceso(@PathVariable("idSeccion") int idSeccion,
			@PathVariable("idExamen") long idExamen, @Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			List<TestProcesoDTO> listTests = configuracionTestService.getTestsBusquedaProcesos(idSeccion, idExamen);
			return new ResponseTemplateGen<>(listTests, 200, "OK");
		} catch (BiosLISDAOException ex) {
			java.util.logging.Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null,
					ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

	@GetMapping("/api/test/getParaExcel")
	public ResponseTemplateGen<List<CfgTestExcel>> getTestParaExcel(@Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			List<CfgTestExcel> listaTest = configuracionTestService.getTestParaExcel();
			return new ResponseTemplateGen<>(listaTest, 200, "Ok");
		} catch (BiosLISDAOException | BiosLISDAONotFoundException ex) {
			Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

	public ConfiguracionTestService getConfiguracionTestService() {
		return configuracionTestService;
	}

	public void setConfiguracionTestService(ConfiguracionTestService configuracionTestService) {
		this.configuracionTestService = configuracionTestService;
	}

	@GetMapping("/api/test/examenes/{idSeccion}")
	public ResponseTemplateGen<List<TestExamenSeccionDto>> getTestsExamenesBySeccionNativo(
			@PathVariable("idSeccion") int idSeccion, @Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			List<TestExamenSeccionDto> listTests = configuracionTestService.getTestDtoBySeccionNativo(idSeccion);
			return new ResponseTemplateGen<>(listTests, 200, "OK");
		} catch (BiosLISDAOException ex) {
			java.util.logging.Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null,
					ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

	@GetMapping("/api/{idExamen}/getTestxExamen")
	public ResponseTemplateGen<List<CfgTestDTO>> getTestxExamen(@PathVariable("idExamen") int idExamen,
			@Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			List<CfgTestDTO> listTests = configuracionTestService.getTestByExamen(idExamen);
			return new ResponseTemplateGen<>(listTests, 200, "OK");
		} catch (BiosLISDAOException ex) {
			java.util.logging.Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null,
					ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

	@GetMapping("/api/{idExamen}/getAntecedentexExamen")
	public ResponseTemplateGen<List<AntecedentePacienteDTO>> getAntecedentetxExamen(
			@PathVariable("idExamen") int idExamen, @Context HttpServletRequest context) {
		HttpSession sesion = context.getSession();
		DatosUsuarios usuario = null;
		usuario = (DatosUsuarios) sesion.getAttribute("usuario");
		if (usuario == null)
			return new ResponseTemplateGen<>(null, 401, "No hay sesion");
		try {
			List<AntecedentePacienteDTO> listTests = configuracionTestService.getAntecedenteByExamen(idExamen);
			if (listTests.isEmpty()) {
				return new ResponseTemplateGen<>(listTests, 201, "VACIO");
			} else {
				return new ResponseTemplateGen<>(listTests, 200, "OK");
			}
		} catch (BiosLISDAOException ex) {
			java.util.logging.Logger.getLogger(ConfiguracionTestRestController.class.getName()).log(Level.SEVERE, null,
					ex);
			return new ResponseTemplateGen<>(null, 500, ex.getMessage());
		}
	}

}
