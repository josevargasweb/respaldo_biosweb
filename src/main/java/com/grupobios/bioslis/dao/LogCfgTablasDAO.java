/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.ExamenFullDTO;
import com.grupobios.bioslis.entity.CfgBacgrupostest;
import com.grupobios.bioslis.entity.CfgCentrosdesalud;
import com.grupobios.bioslis.entity.CfgConvenios;
import com.grupobios.bioslis.entity.CfgDerivadores;
import com.grupobios.bioslis.entity.CfgEnvases;
import com.grupobios.bioslis.entity.CfgGruposexamenesfonasa;
import com.grupobios.bioslis.entity.CfgInstitucionesdesalud;
import com.grupobios.bioslis.entity.CfgLaboratorios;
import com.grupobios.bioslis.entity.CfgMetodos;
import com.grupobios.bioslis.entity.CfgMuestras;
import com.grupobios.bioslis.entity.CfgProcedencias;
import com.grupobios.bioslis.entity.CfgSecciones;
import com.grupobios.bioslis.entity.CfgServicios;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.CfgTiposderesultado;
import com.grupobios.bioslis.entity.CfgUnidadesdemedida;
import com.grupobios.bioslis.entity.CfgValoresreferencia;
import com.grupobios.bioslis.entity.LdvEspecialidadesmedicas;
import com.grupobios.bioslis.entity.LdvFormatos;
import com.grupobios.bioslis.entity.LdvGruposexamenes;
import com.grupobios.bioslis.entity.LdvIndicacionesexamenes;
import com.grupobios.bioslis.entity.LdvOperacionesmath;
import com.grupobios.bioslis.entity.LdvSexo;
import com.grupobios.bioslis.entity.LdvTipocomunicacion;
import com.grupobios.bioslis.entity.LdvTiposcondicion;
import com.grupobios.bioslis.entity.LdvTiposexamenes;
import com.grupobios.bioslis.entity.LogCfgtablas;
import com.grupobios.bioslis.entity.SigmaProcesos;
import com.grupobios.bioslis.entity.SigmaProcesosalarmas;
import com.grupobios.bioslis.entity.SigmaProcesostest;
import com.grupobios.bioslis.entity.SigmaProcesotestconversion;
import com.grupobios.bioslis.entity.SigmaTiposmuestras;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LazyInitializationException;

import org.hibernate.Session;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import static org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE;
import org.javers.core.diff.changetype.ValueChange;

/**
 *
 * @author Jan Perkov
 */
public class LogCfgTablasDAO {

	private static Logger logger = LogManager.getLogger(LogCfgTablasDAO.class);

	LdvIndicacionesExamenesDAO indicacionesExamenesDAO;

	LdvFormatosDAO ldvFormatosDAO;
	LdvTiposExamenesDAO tiposExamenesDAO;
	CfgMuestrasDAO cfgMuestrasDAO;
	CfgSeccionesDAO cfgSeccionesDAO;
	CfgGruposExamenesFonasaDAO gruposExamenesFonasaDAO;

	LdvGruposExamenesDAO ldvGruposExamenesDAO;
	CfgDerivadoresDAO cfgDerivadoresDAO;
	CfgMetodosDAO cfgMetodosDAO;
	CfgEnvasesDAO cfgEnvasesDAO;
	CfgUnidadesdemedidaDAO cfgUnidadesdemedidaDAO;
	CfgTiposderesultadoDAO cfgTiposderesultadoDAO;
	CfgBacgrupostestDAO cfgBacgrupostestDAO;

	LdvSexoDAO ldvSexosDAO;
	LdvEspecialidadesMedicasDAO ldvEspecialidadesMedicasDAO;

	CfgCentrosDeSaludDAO cfgCentrosDeSaludDAO;

	CfgConvenioDAO cfgConvenioDAO;	

	LdvTipoComunicacionDAO tipoComunicacionDAO;
	SigmaTiposMuestrasDAO sigmaTiposMuestrasDAO;
	LdvTiposCondicionDAO tiposCondicionDAO;
	LdvOperacionesMathDAO operacionesMathDAO;
	
	

	public LdvOperacionesMathDAO getOperacionesMathDAO() {
		return operacionesMathDAO;
	}

	public void setOperacionesMathDAO(LdvOperacionesMathDAO operacionesMathDAO) {
		this.operacionesMathDAO = operacionesMathDAO;
	}

	public LdvTiposCondicionDAO getTiposCondicionDAO() {
		return tiposCondicionDAO;
	}

	public void setTiposCondicionDAO(LdvTiposCondicionDAO tiposCondicionDAO) {
		this.tiposCondicionDAO = tiposCondicionDAO;
	}

	public SigmaTiposMuestrasDAO getSigmaTiposMuestrasDAO() {
		return sigmaTiposMuestrasDAO;
	}

	public void setSigmaTiposMuestrasDAO(SigmaTiposMuestrasDAO sigmaTiposMuestrasDAO) {
		this.sigmaTiposMuestrasDAO = sigmaTiposMuestrasDAO;
	}

	public LdvTipoComunicacionDAO getTipoComunicacionDAO() {
		return tipoComunicacionDAO;
	}

	public void setTipoComunicacionDAO(LdvTipoComunicacionDAO tipoComunicacionDAO) {
		this.tipoComunicacionDAO = tipoComunicacionDAO;
	}

	public CfgConvenioDAO getCfgConvenioDAO() {
		return cfgConvenioDAO;
	}

	public void setCfgConvenioDAO(CfgConvenioDAO cfgConvenioDAO) {
		this.cfgConvenioDAO = cfgConvenioDAO;
	}

	public CfgCentrosDeSaludDAO getCfgCentrosDeSaludDAO() {
		return cfgCentrosDeSaludDAO;
	}

	public void setCfgCentrosDeSaludDAO(CfgCentrosDeSaludDAO cfgCentrosDeSaludDAO) {
		this.cfgCentrosDeSaludDAO = cfgCentrosDeSaludDAO;
	}

	public LdvEspecialidadesMedicasDAO getLdvEspecialidadesMedicasDAO() {
		return ldvEspecialidadesMedicasDAO;
	}

	public void setLdvEspecialidadesMedicasDAO(LdvEspecialidadesMedicasDAO ldvEspecialidadesMedicasDAO) {
		this.ldvEspecialidadesMedicasDAO = ldvEspecialidadesMedicasDAO;
	}

	public LdvSexoDAO getLdvSexosDAO() {
		return ldvSexosDAO;
	}

	public void setLdvSexosDAO(LdvSexoDAO ldvSexosDAO) {
		this.ldvSexosDAO = ldvSexosDAO;
	}

	public CfgBacgrupostestDAO getCfgBacgrupostestDAO() {
		return cfgBacgrupostestDAO;
	}

	public void setCfgBacgrupostestDAO(CfgBacgrupostestDAO cfgBacgrupostestDAO) {
		this.cfgBacgrupostestDAO = cfgBacgrupostestDAO;
	}

	public CfgTiposderesultadoDAO getCfgTiposderesultadoDAO() {
		return cfgTiposderesultadoDAO;
	}

	public void setCfgTiposderesultadoDAO(CfgTiposderesultadoDAO cfgTiposderesultadoDAO) {
		this.cfgTiposderesultadoDAO = cfgTiposderesultadoDAO;
	}

	public CfgUnidadesdemedidaDAO getCfgUnidadesdemedidaDAO() {
		return cfgUnidadesdemedidaDAO;
	}

	public void setCfgUnidadesdemedidaDAO(CfgUnidadesdemedidaDAO cfgUnidadesdemedidaDAO) {
		this.cfgUnidadesdemedidaDAO = cfgUnidadesdemedidaDAO;
	}

	public CfgEnvasesDAO getCfgEnvasesDAO() {
		return cfgEnvasesDAO;
	}

	public void setCfgEnvasesDAO(CfgEnvasesDAO cfgEnvasesDAO) {
		this.cfgEnvasesDAO = cfgEnvasesDAO;
	}

	public CfgMetodosDAO getCfgMetodosDAO() {
		return cfgMetodosDAO;
	}

	public void setCfgMetodosDAO(CfgMetodosDAO cfgMetodosDAO) {
		this.cfgMetodosDAO = cfgMetodosDAO;
	}

	public CfgDerivadoresDAO getCfgDerivadoresDAO() {
		return cfgDerivadoresDAO;
	}

	public void setCfgDerivadoresDAO(CfgDerivadoresDAO cfgDerivadoresDAO) {
		this.cfgDerivadoresDAO = cfgDerivadoresDAO;
	}

	public LdvGruposExamenesDAO getLdvGruposExamenesDAO() {
		return ldvGruposExamenesDAO;
	}

	public void setLdvGruposExamenesDAO(LdvGruposExamenesDAO ldvGruposExamenesDAO) {
		this.ldvGruposExamenesDAO = ldvGruposExamenesDAO;
	}

	public CfgGruposExamenesFonasaDAO getGruposExamenesFonasaDAO() {
		return gruposExamenesFonasaDAO;
	}

	public void setGruposExamenesFonasaDAO(CfgGruposExamenesFonasaDAO gruposExamenesFonasaDAO) {
		this.gruposExamenesFonasaDAO = gruposExamenesFonasaDAO;
	}

	public CfgSeccionesDAO getCfgSeccionesDAO() {
		return cfgSeccionesDAO;
	}

	public void setCfgSeccionesDAO(CfgSeccionesDAO cfgSeccionesDAO) {
		this.cfgSeccionesDAO = cfgSeccionesDAO;
	}

	public CfgMuestrasDAO getCfgMuestrasDAO() {
		return cfgMuestrasDAO;
	}

	public void setCfgMuestrasDAO(CfgMuestrasDAO cfgMuestrasDAO) {
		this.cfgMuestrasDAO = cfgMuestrasDAO;
	}

	public LdvTiposExamenesDAO getTiposExamenesDAO() {
		return tiposExamenesDAO;
	}

	public void setTiposExamenesDAO(LdvTiposExamenesDAO tiposExamenesDAO) {
		this.tiposExamenesDAO = tiposExamenesDAO;
	}

	public LdvFormatosDAO getLdvFormatosDAO() {
		return ldvFormatosDAO;
	}

	public void setLdvFormatosDAO(LdvFormatosDAO ldvFormatosDAO) {
		this.ldvFormatosDAO = ldvFormatosDAO;
	}

	public LdvIndicacionesExamenesDAO getIndicacionesExamenesDAO() {
		return indicacionesExamenesDAO;
	}

	public void setIndicacionesExamenesDAO(LdvIndicacionesExamenesDAO indicacionesExamenesDAO) {
		this.indicacionesExamenesDAO = indicacionesExamenesDAO;
	}
	/*
	 * public void insertTablasLogUpdate(String tablaUso) { Session sesion = null;
	 * try { LogCfgtablas logCfg = new LogCfgtablas(); sesion =
	 * HibernateUtil.getSessionFactory().openSession(); sesion.beginTransaction();
	 * logCfg.setLctNombretabla(tablaUso);
	 * 
	 * sesion.save(logCfg); sesion.getTransaction().commit(); sesion.close(); }
	 * catch (HibernateException he) { logger.error(he.getMessage()); } finally { if
	 * (sesion != null && sesion.isOpen()){ sesion.close(); } } }
	 */

	// funcion que compara objectos e informa de campos que han sido modificado
	public void comparadorObjetos(Object objetosinCambios, Object objetoConCambios, int accion, int usuario,
			int idTabla, String idDescripcion, String ip) throws LazyInitializationException {

		String[] ipLocal = new String[2];

		try {
			InetAddress address = InetAddress.getLocalHost();

			// IP en formato String
			String paso = address.toString();
			ipLocal = paso.split("/");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		logger.debug("tipo de objeto " + objetoConCambios.getClass().getSimpleName());
		logger.debug("tipo de objeto " + objetosinCambios.getClass().getSimpleName());
		try {
			// Definir javers
			Javers javers = JaversBuilder.javers().withListCompareAlgorithm(LEVENSHTEIN_DISTANCE).build();

			// hacer comparacion entre arreglos y convertilo a array

			Diff diff = javers.compare(objetosinCambios, objetoConCambios);

			ArrayList<ValueChange> diffs = (ArrayList<ValueChange>) diff.getChangesByType(ValueChange.class);
			String campo;
			String sinCambio = new String();
			String conCambio = new String();
			// recorrer array

			for (ValueChange v : diffs) {

				logger.debug("entre a for  valores diferentes cambiar  " + v);

				if (v.getRight() != "" || v.getLeft() != null) {

					int cont = 0;
					// Insertar campo cambiado
					campo = v.getPropertyName();
					String[] campoSerarado = campo.split("(?=[A-Z])");
					String nuevoCampo = nombreCampo(campoSerarado);

					if (v.getLeft() != null) {
						sinCambio = v.getLeft().toString();

						sinCambio = caseTablas(sinCambio, nuevoCampo.toUpperCase());

					} else {
						sinCambio = null;
					}

					if (v.getRight() != null) {
						conCambio = v.getRight().toString();
						conCambio = caseTablas(conCambio, nuevoCampo.toUpperCase());
					} else {
						conCambio = null;
					}

					// AQUI SE COLOCAN LOS CAMPOS QUE NO SE QUIERE QUE SE GRABEN EN las TABLAs de
					// LOG

					if (objetoConCambios.getClass().getSimpleName().equals("CfgExamenestablasanexas")) {
						validarDatos(conCambio, sinCambio, (long) usuario, "CETA_" + nuevoCampo, idTabla,
								"CFG_EXAMENESTABLASANEXAS", idDescripcion);
					}

					if (objetoConCambios.getClass().getSimpleName().equals("CfgEnvases")) {
						validarDatos(conCambio, sinCambio, (long) usuario, "CENV_" + nuevoCampo, idTabla, "CFG_ENVASES",
								idDescripcion);
					}

					if (objetoConCambios.getClass().getSimpleName().equals("CfgUnidadesdemedida")) {
						validarDatos(conCambio, sinCambio, (long) usuario, "CUM_" + nuevoCampo, idTabla,
								"CFG_UNIDADESDEMEDIDA", idDescripcion);
					}

					if (objetoConCambios.getClass().getSimpleName().equals("CfgMetodos")) {
						validarDatos(conCambio, sinCambio, (long) usuario, "CMET" + nuevoCampo, idTabla, "CFG_METODOS",
								idDescripcion);
					}

					if (objetoConCambios.getClass().getSimpleName().equals("CfgGlosas")) {

						validarDatos(conCambio, sinCambio, (long) usuario, "CFG_" + nuevoCampo, idTabla, "CFG_GLOSAS",
								idDescripcion);
					}

					if (objetoConCambios.getClass().getSimpleName().equals("CfgMedicos")) {

						validarDatos(conCambio, sinCambio, (long) usuario, "CM_" + nuevoCampo, idTabla, "CFG_MEDICOS",
								idDescripcion);
					}

					if (objetoConCambios.getClass().getSimpleName().equals("CfgDiagnosticos")) {

						//if(!nuevoCampo.equals("DESCRIPCION")) {
							validarDatos(conCambio, sinCambio, (long) usuario, "CD_" + nuevoCampo, idTabla,
								"CFG_DIAGNOSTICOS", idDescripcion);
						//}
					}
					if (objetoConCambios.getClass().getSimpleName().equals("CfgPrioridadatencion")) {

						validarDatos(conCambio, sinCambio, (long) usuario, "CPA_" + nuevoCampo, idTabla,
								"CFG_PRIORIDADATENCION", idDescripcion);
					}
					if (objetoConCambios.getClass().getSimpleName().equals("CfgCausasrechazosmuestras")) {

						validarDatos(conCambio, sinCambio, (long) usuario, "CCRM_" + nuevoCampo, idTabla,
								"CFG_CAUSASRECHAZOSMUESTRAS", idDescripcion);
					}

					if (objetoConCambios.getClass().getSimpleName().equals("CfgCentrosdesalud")) {

						validarDatos(conCambio, sinCambio, (long) usuario, "CCDS_" + nuevoCampo, idTabla,
								"CFG_CENTROSDESALUD", idDescripcion);
					}

					if (objetoConCambios.getClass().getSimpleName().equals("CfgLaboratorios")) {

						validarDatos(conCambio, sinCambio, (long) usuario, "CLAB_" + nuevoCampo, idTabla,
								"CFG_LABORATORIOS", idDescripcion);
					}
					if (objetoConCambios.getClass().getSimpleName().equals("CfgDerivadores")) {

						validarDatos(conCambio, sinCambio, (long) usuario, "CDERIV_" + nuevoCampo, idTabla,
								"CFG_DERIVADORES", idDescripcion);
					}

					if (objetoConCambios.getClass().getSimpleName().equals("SigmaAnalizadores")) {

						validarDatos(conCambio, sinCambio, (long) usuario, "SA_" + nuevoCampo, idTabla,
								"SIGMA_ANALIZADORES", idDescripcion);
					}

					if (objetoConCambios.getClass().getSimpleName().equals("CfgEtiquetacodigobarras")) {

						validarDatos(conCambio, sinCambio, (long) usuario, "CECB_" + nuevoCampo, idTabla,
								"CFG_ETIQUETACODIGOBARRAS", idDescripcion);
					}
					
					if (objetoConCambios.getClass().getSimpleName().equals("SigmaProcesos")) {

						validarDatos(conCambio, sinCambio, (long) usuario, "SP_" + nuevoCampo, idTabla,
								"SIGMA_PROCESOS", idDescripcion);
			}
					
				}

			}

		} catch (Exception he) {
			he.printStackTrace();
			logger.error("error en comparadorObjetos ");
			// throw he;
		}
	}

	public String nombreCampo(String[] nombreCampo) {
		String nuevoCampo = "";
	
		switch (nombreCampo[1].toUpperCase()) {
		case "REGISTRODESALUD": {
			nombreCampo[1] = "IDREGISTRODESALUD";
			break;
		}
		case "SEXO": {
			nombreCampo[1] = "IDSEXO";
			break;
		}
		case "ESPECIALIDADESMEDICAS": {
			nombreCampo[1] = "IDESPECIALIDADMEDICA";
			break;
		}
		case "INSTITUCIONESDESALUD": {
			nombreCampo[1] = "IDINSTITUCIONDESALUD";
			break;

		}
		case "IDCENTRO": {
			nombreCampo[1] = "IDCENTRODESALUD";
			break;

		}
		}

		if (nombreCampo.length > 2) {
			if (nombreCampo[2].equals("B")) {
				nuevoCampo = "-1";
			} else {
				nuevoCampo = nombreCampo[1] + nombreCampo[2];
			}
		} else {
			nuevoCampo = nombreCampo[1];
		}

		if (nuevoCampo.toUpperCase().equals("IDCENTRO")) {
			nuevoCampo = "IDCENTRODESALUD";
		}

	

		return nuevoCampo.toUpperCase();

	}

	/*
	 * no se utiliza este metodo - comentado por cristian 10-05 public void
	 * insertLog(String campo, String sinCambios, String conCambios, String
	 * tablaUso, int accion, int usuario, String nombreEquipo, int idTabla, String
	 * ip) throws ParseException { try { Calendar fechaCalendar =
	 * GregorianCalendar.getInstance(); Date date = fechaCalendar.getTime();
	 * 
	 * if (sinCambios == null) { sinCambios = ""; } if (conCambios == null) {
	 * conCambios = ""; } if (campo.length() > 5) { campo = campo.substring(0, 2) +
	 * "_" + campo.substring(2, campo.length()); }
	 * 
	 * LogCfgtablas logCfg = new LogCfgtablas(); Session sesion =
	 * HibernateUtil.getSessionFactory().openSession(); sesion.beginTransaction();
	 * logCfg.setLctNombretabla(tablaUso);
	 * logCfg.setLctNombrecampo(campo.toUpperCase());
	 * logCfg.setLctValoranterior(conCambios.toUpperCase());
	 * logCfg.setLctValornuevo(sinCambios.toUpperCase());
	 * logCfg.setLctIpusuario(ip); logCfg.setCfgAccionesdatos(accion);
	 * logCfg.setLctNombreequipo(nombreEquipo); logCfg.setLctIdusuario(1);
	 * logCfg.setLctFechamodificacion(date); logCfg.setLctIdtabla(idTabla);
	 * sesion.save(logCfg); sesion.getTransaction().commit(); sesion.close();
	 * 
	 * } catch (HibernateException e) {
	 * 
	 * logger.error("No se pudo insertar log");
	 * 
	 * throw e;
	 * 
	 * /** Marco C. Tuve que realizar el catch debido a la conversion de String a
	 * Date que se hace al principio
	 * 
	 * } }
	 */

	public void insertLogTablas(LogCfgtablas logCfg, Session sesion) throws BiosLISDAOException {
		try {

			String[] ipLocal = new String[2];
			try {
				InetAddress address = InetAddress.getLocalHost();

				// IP en formato String
				String paso = address.toString();
				ipLocal = paso.split("/");

			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			logCfg.setLctIpusuario(ipLocal[1]);
			if (ipLocal[0].length() > 50) {
				logCfg.setLctNombreequipo(ipLocal[0].substring(0, 50));
			} else {
				logCfg.setLctNombreequipo(ipLocal[0]);
			}

			logCfg.setLctFechamodificacion(BiosLisCalendarService.getInstance().getTS());
			sesion.save(logCfg);

		} catch (HibernateException he) {
			logger.error(he.getMessage());
			throw new BiosLISDAOException(he.getLocalizedMessage());
		}
	}

	public void insertLogTablas(LogCfgtablas logCfg) throws BiosLISDAOException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
	
		try {
			sesion.beginTransaction();
			String[] ipLocal = new String[2];
			try {
				InetAddress address = InetAddress.getLocalHost();

				// IP en formato String
				String paso = address.toString();
				ipLocal = paso.split("/");

			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			logCfg.setLctIpusuario(ipLocal[1]);
			if (ipLocal[0].length() > 50) {
				logCfg.setLctNombreequipo(ipLocal[0].substring(0, 50));
			} else {
				logCfg.setLctNombreequipo(ipLocal[0]);
			}

			logCfg.setLctFechamodificacion(BiosLisCalendarService.getInstance().getTS());
			sesion.save(logCfg);
			sesion.getTransaction().commit();
			sesion.close();
		} catch (HibernateException he) {
			logger.error(he.getMessage());
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
	}
	

	public void compararTablasLaboratorio(CfgLaboratorios nuevo, CfgLaboratorios antiguo, Long idUsuario,
			String idDescripcion) {
		
	validarDatos(nuevo.getClabCodigo(), antiguo.getClabCodigo(), (long) idUsuario, "CLAB_CODIGO" , nuevo.getClabIdlaboratorio(),
				"CFG_LABORATORIOS", idDescripcion);
	
	validarDatos(nuevo.getClabDescripcion(), antiguo.getClabDescripcion(), (long) idUsuario, "CLAB_DESCRIPCION" , nuevo.getClabIdlaboratorio(),
				"CFG_LABORATORIOS", idDescripcion);
	validarDatos(nuevo.getClabActivo(), antiguo.getClabActivo(), (long) idUsuario, "CLAB_ACTIVO" , nuevo.getClabIdlaboratorio(),
			"CFG_LABORATORIOS", idDescripcion);
	validarDatos(nuevo.getClabPreinforme(), antiguo.getClabPreinforme(), (long) idUsuario, "CLAB_PREINFORME" , nuevo.getClabIdlaboratorio(),
			"CFG_LABORATORIOS", idDescripcion);
	validarDatos(String.valueOf(nuevo.getClabSort()), String.valueOf(antiguo.getClabSort()), (long) idUsuario, "CLAB_SORT" , nuevo.getClabIdlaboratorio(),
			"CFG_LABORATORIOS", idDescripcion);
	validarDatos(nuevo.getClabHost(), antiguo.getClabHost(), (long) idUsuario, "CLAB_HOST" , nuevo.getClabIdlaboratorio(),
			"CFG_LABORATORIOS", idDescripcion);
	validarDatos(nuevo.getClabDerivador(), antiguo.getClabDerivador(), (long) idUsuario, "CLAB_DERIVADOR" , nuevo.getClabIdlaboratorio(),
			"CFG_LABORATORIOS", idDescripcion);
	
	
	// Agregando CP_IDCENTRODESALUD
	String valorNuevo = "";
	String valorAnterior = "";
	CfgCentrosdesalud centroNuevo = new CfgCentrosdesalud();
	CfgCentrosDeSaludDAO centro = new CfgCentrosDeSaludDAO();

	if (nuevo.getClabIdCentroSalud() != null) {
		try {
			centroNuevo = centro.getCentrosDeSaludById(nuevo.getClabIdCentroSalud().shortValue());
		} catch (BiosLISDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (centroNuevo != null) {
			valorNuevo = centroNuevo.getCcdsDescripcion();
		}
	}

	if (antiguo.getClabIdCentroSalud() != null) {
		try {
			centroNuevo = centro.getCentrosDeSaludById(antiguo.getClabIdCentroSalud().shortValue());
		} catch (BiosLISDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (centroNuevo != null) {
			valorAnterior = centroNuevo.getCcdsDescripcion();
		}
	}

	// Agregando CP_IDCENTRODESALUD
	validarDatos(valorNuevo, valorAnterior, idUsuario, "CLAB_IDCENTRODESALUD", (int) nuevo.getClabIdlaboratorio(),
			"CFG_LABORATORIOS", idDescripcion);

	}
	

	public void compararTablasSigmaProcesoAlarmas(SigmaProcesosalarmas nuevo, SigmaProcesosalarmas antiguo, Long idUsuario,
			String idDescripcion) {
		
	validarDatos(nuevo.getSpaCodigoalarma(), antiguo.getSpaCodigoalarma(), (long) idUsuario, "SPA_CODIGOALARMA" , nuevo.getSigmaProcesos().getSpIdproceso(),
				"SIGMA_PROCESOSALARMAS", idDescripcion);
	validarDatos(nuevo.getSpaAlarmadescripcion(), antiguo.getSpaAlarmadescripcion(), (long) idUsuario, "SPA_ALARMADESCRIPCION"
			,  nuevo.getSigmaProcesos().getSpIdproceso(),	"SIGMA_PROCESOSALARMAS", idDescripcion);
	validarDatos(nuevo.getSpaActivo(), antiguo.getSpaActivo(), (long) idUsuario, "SPA_ACTIVO"
			, nuevo.getSigmaProcesos().getSpIdproceso(),	"SIGMA_PROCESOSALARMAS", idDescripcion);
	validarDatos(nuevo.getSpaBloquear(), antiguo.getSpaBloquear(), (long) idUsuario, "SPA_BLOQUEAR"
			,  nuevo.getSigmaProcesos().getSpIdproceso(),	"SIGMA_PROCESOSALARMAS", idDescripcion);
	}
	
	public void compararTablasProcedencia(CfgProcedencias nuevo, CfgProcedencias antiguo, Long idUsuario,
			String idDescripcion) {
		// Agregando CS_CODIGO
		validarDatos(nuevo.getCpCodigo(), antiguo.getCpCodigo(), idUsuario, "CP_CODIGO",
				(int) nuevo.getCpIdprocedencia(), "CFG_PROCEDENCIAS", idDescripcion);
		// Agregando CP_DESCRIPCION
		validarDatos(nuevo.getCpDescripcion(), antiguo.getCpDescripcion(), idUsuario, "CP_DESCRIPCION",
				(int) nuevo.getCpIdprocedencia(), "CFG_PROCEDENCIAS", idDescripcion);
		// Agregando CP_SORT
		validarDatos(nuevo.getCpSort() != null ? nuevo.getCpSort().toString() : "",
				antiguo.getCpSort() != null ? antiguo.getCpSort().toString() : "", idUsuario, "CP_SORT",
				(int) nuevo.getCpIdprocedencia(), "CFG_PROCEDENCIAS", idDescripcion);
		// Agregando CP_ACTIVO
		validarDatos(nuevo.getCpActivo(), antiguo.getCpActivo(), idUsuario, "CP_ACTIVO",
				(int) nuevo.getCpIdprocedencia(), "CFG_PROCEDENCIAS", idDescripcion);

		// Agregando CP_IDCONVENIO
		String valorNuevo = "";
		String valorAnterior = "";
		CfgConvenios convenioNuevo = new CfgConvenios();

		if (nuevo.getCfgConvenios() != null) {
			convenioNuevo = cfgConvenioDAO.getConveniosById(nuevo.getCfgConvenios().getCcIdconvenio());

			if (convenioNuevo != null) {
				valorNuevo = convenioNuevo.getCcDescripcion();
			}
		}

		if (antiguo.getCfgConvenios() != null) {
			convenioNuevo = cfgConvenioDAO.getConveniosById(antiguo.getCfgConvenios().getCcIdconvenio());
			if (convenioNuevo != null) {
				valorAnterior = convenioNuevo.getCcDescripcion();
			}
		}

		// Agregando CP_IDCONVENIO
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CP_IDCONVENIO", (int) nuevo.getCpIdprocedencia(),
				"CFG_PROCEDENCIAS", idDescripcion);

		// Agregando CP_TOMAMUESTRAAUTOMATICA
		validarDatos(nuevo.getCpTomamuestraautomatica(), antiguo.getCpTomamuestraautomatica(), idUsuario,
				"CP_TOMAMUESTRAAUTOMATICA", (int) nuevo.getCpIdprocedencia(), "CFG_PROCEDENCIAS", idDescripcion);
		// Agregando CP_MEMBRETE
		validarDatos(nuevo.getCpMembrete(), antiguo.getCpMembrete(), idUsuario, "CP_MEMBRETE",
				(int) nuevo.getCpIdprocedencia(), "CFG_PROCEDENCIAS", idDescripcion);
		// Agregando CP_IDTIPOCONEXIONHOST
		validarDatos(nuevo.getCpIdtipoconexionhost() != null ? String.valueOf(nuevo.getCpIdtipoconexionhost()) : "",
				antiguo.getCpIdtipoconexionhost() != null ? String.valueOf(antiguo.getCpIdtipoconexionhost()) : "",
				idUsuario, "CP_IDTIPOCONEXIONHOST", (int) nuevo.getCpIdprocedencia(), "CFG_PROCEDENCIAS",
				idDescripcion);
		// Agregando CP_PROCEDENCIARESTRINGIDA
		validarDatos(nuevo.getCpProcedenciarestringida(), antiguo.getCpProcedenciarestringida(), idUsuario,
				"CP_PROCEDENCIARESTRINGIDA", (int) nuevo.getCpIdprocedencia(), "CFG_PROCEDENCIAS", idDescripcion);

		// Agregando CP_IDCENTRODESALUD
		valorNuevo = "";
		valorAnterior = "";
		CfgCentrosdesalud centroNuevo = new CfgCentrosdesalud();
		CfgCentrosDeSaludDAO centro = new CfgCentrosDeSaludDAO();

		if (nuevo.getCfgCentroDeSalud() != null) {
			try {
				centroNuevo = centro.getCentrosDeSaludById(nuevo.getCfgCentroDeSalud().getCcdsIdcentrodesalud());
			} catch (BiosLISDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (centroNuevo != null) {
				valorNuevo = centroNuevo.getCcdsDescripcion();
			}
		}

		if (antiguo.getCfgCentroDeSalud() != null) {
			try {
				centroNuevo = centro.getCentrosDeSaludById(antiguo.getCfgCentroDeSalud().getCcdsIdcentrodesalud());
			} catch (BiosLISDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (centroNuevo != null) {
				valorAnterior = centroNuevo.getCcdsDescripcion();
			}
		}

		// Agregando CP_IDCENTRODESALUD
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CP_IDCENTRODESALUD", (int) nuevo.getCpIdprocedencia(),
				"CFG_PROCEDENCIAS", idDescripcion);

		// Agregando CP_HOST
		validarDatos(nuevo.getCpHost(), antiguo.getCpHost(), idUsuario, "CP_HOST", (int) nuevo.getCpIdprocedencia(),
				"CFG_PROCEDENCIAS", idDescripcion);
		// Agregando CP_HOST2
		validarDatos(nuevo.getCpHost2(), antiguo.getCpHost2(), idUsuario, "CP_HOST2", (int) nuevo.getCpIdprocedencia(),
				"CFG_PROCEDENCIAS", idDescripcion);
		// Agregando CP_HOST3
		validarDatos(nuevo.getCpHost3(), antiguo.getCpHost3(), idUsuario, "CP_HOST3", (int) nuevo.getCpIdprocedencia(),
				"CFG_PROCEDENCIAS", idDescripcion);
		// Agregando CP_HOST3
				validarDatos(nuevo.getCpEmail(), antiguo.getCpEmail(), idUsuario, "CP_EMAIL", (int) nuevo.getCpIdprocedencia(),
						"CFG_PROCEDENCIAS", idDescripcion);


	}

	public void compararTablasSigmaProcesos(SigmaProcesos procesoNuevo, SigmaProcesos procesoAntiguo, Long idUsuario,
			String idDescripcion) {
		// Agregando SP_CODIGOPROCESO
		validarDatos(procesoNuevo.getSpCodigoproceso(), procesoAntiguo.getSpCodigoproceso(), idUsuario,
				"SP_CODIGOPROCESO", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agrega SP_DESCRIPCION
		validarDatos(procesoNuevo.getSpDescripcion(), procesoAntiguo.getSpDescripcion(), idUsuario, "SP_DESCRIPCION",
				(int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_ESTACIONSIGMA
		validarDatos(procesoNuevo.getSpEstacionsigma() != null ? procesoNuevo.getSpEstacionsigma().toString() : "",
				procesoAntiguo.getSpEstacionsigma() != null ? procesoAntiguo.getSpEstacionsigma().toString() : "",
				idUsuario, "SP_ESTACIONSIGMA", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_PARAMETROS
		validarDatos(procesoNuevo.getSpParametros(), procesoAntiguo.getSpParametros(), idUsuario, "SP_PARAMETROS",
				(int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		
		// Agregando SP_IDTIPOCOMUNICACION

		String valorNuevo = "";
		String valorAnterior = "";
		LdvTipocomunicacion tipoComunicacion = new LdvTipocomunicacion();

		if (procesoNuevo.getLdvTipocomunicacion() != null) {
			try {
				tipoComunicacion = tipoComunicacionDAO
						.getTipoComunicacionById(procesoNuevo.getLdvTipocomunicacion().getLdvtcIdtipocomunicacion());
			} catch (BiosLISDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (tipoComunicacion != null) {
				valorNuevo = tipoComunicacion.getLdvtcDescripcion();
			}
		}

		if (procesoAntiguo.getLdvTipocomunicacion() != null) {
			try {
				tipoComunicacion = tipoComunicacionDAO
						.getTipoComunicacionById(procesoAntiguo.getLdvTipocomunicacion().getLdvtcIdtipocomunicacion());
			} catch (BiosLISDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (tipoComunicacion != null) {
				valorAnterior = tipoComunicacion.getLdvtcDescripcion();
			}
		}

		validarDatos(valorNuevo, valorAnterior, idUsuario, "SP_IDTIPOCOMUNICACION", (int) procesoNuevo.getSpIdproceso(),
				"SIGMA_PROCESOS", idDescripcion);

		// Agregando SP_CARGATEMPORAL
		validarDatos(procesoNuevo.getSpCargatemporal(), procesoAntiguo.getSpCargatemporal(), idUsuario,
				"SP_CARGATEMPORAL", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_TIEMPOCARGATEMPORALSEG
		validarDatos(
				procesoNuevo.getSpTiempocargatemporalseg() != null
						? procesoNuevo.getSpTiempocargatemporalseg().toString()
						: "",
				procesoAntiguo.getSpTiempocargatemporalseg() != null
						? procesoAntiguo.getSpTiempocargatemporalseg().toString()
						: "",
				idUsuario, "SP_TIEMPOCARGATEMPORALSEG", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS",
				idDescripcion);
		// Agregando SP_CARGAPROCESO
		validarDatos(procesoNuevo.getSpCargaproceso(), procesoAntiguo.getSpCargaproceso(), idUsuario, "SP_CARGAPROCESO",
				(int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_TIEMPOCARGAPROCESOSEG
		validarDatos(
				procesoNuevo.getSpTiempocargaprocesoseg() != null ? procesoNuevo.getSpTiempocargaprocesoseg().toString()
						: "",
				procesoAntiguo.getSpTiempocargaprocesoseg() != null
						? procesoAntiguo.getSpTiempocargaprocesoseg().toString()
						: "",
				idUsuario, "SP_TIEMPOCARGAPROCESOSEG", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS",
				idDescripcion);
		// Agregando SP_ACTIVO
		validarDatos(procesoNuevo.getSpActivo(), procesoAntiguo.getSpActivo(), idUsuario, "SP_ACTIVO",
				(int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_CODIGOHOST
		validarDatos(procesoNuevo.getSpCodigohost(), procesoAntiguo.getSpCodigohost(), idUsuario, "SP_CODIGOHOST",
				(int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_CODIGOBARRAS
		validarDatos(procesoNuevo.getSpCodigobarras(), procesoAntiguo.getSpCodigobarras(), idUsuario, "SP_CODIGOBARRAS",
				(int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_RESULTADOSTEMPORALES
		validarDatos(procesoNuevo.getSpResultadostemporales(), procesoAntiguo.getSpResultadostemporales(), idUsuario,
				"SP_RESULTADOSTEMPORALES", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_NMUESTRALARGO12
		validarDatos(procesoNuevo.getSpNmuestralargo12(), procesoAntiguo.getSpNmuestralargo12(), idUsuario,
				"SP_RESULTADOSTEMPORALES", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_MUESTRARECEPCIONADA
		validarDatos(procesoNuevo.getSpMuestrarecepcionada(), procesoAntiguo.getSpMuestrarecepcionada(), idUsuario,
				"SP_MUESTRARECEPCIONADA", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_CONTROLALARMAS
		validarDatos(procesoNuevo.getSpControlalarmas(), procesoAntiguo.getSpControlalarmas(), idUsuario,
				"SP_CONTROLALARMAS", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_SORT
		validarDatos(String.valueOf(procesoNuevo.getSpSort()), String.valueOf(procesoAntiguo.getSpSort()), idUsuario,
				"SP_SORT", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_CODIGOQC
		validarDatos(procesoNuevo.getSpCodigoqc(), procesoAntiguo.getSpCodigoqc(), idUsuario, "SP_CODIGOQC",
				(int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_QC
		validarDatos(procesoNuevo.getSpQc(), procesoAntiguo.getSpQc(), idUsuario, "SP_QC",
				(int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_FIRMAEXAMENES
		validarDatos(procesoNuevo.getSpFirmaexamenes(), procesoAntiguo.getSpFirmaexamenes(), idUsuario,
				"SP_FIRMAEXAMENES", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_AUTORIZAEXAMENES
		validarDatos(procesoNuevo.getSpAutorizaexamenes(), procesoAntiguo.getSpAutorizaexamenes(), idUsuario,
				"SP_AUTORIZAEXAMENES", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_STDFECHADESDE
		validarDatos(procesoNuevo.getSpStdfechadesde() != null ? procesoNuevo.getSpStdfechadesde().toString() : "",
				procesoAntiguo.getSpStdfechadesde() != null ? procesoAntiguo.getSpStdfechadesde().toString() : "",
				idUsuario, "SP_STDFECHADESDE", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_STDFECHAHASTA
		validarDatos(procesoNuevo.getSpStdfechahasta() != null ? procesoNuevo.getSpStdfechahasta().toString() : "",
				procesoAntiguo.getSpStdfechahasta() != null ? procesoAntiguo.getSpStdfechahasta().toString() : "",
				idUsuario, "SP_STDFECHAHASTA", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_STDFECHAPROCESOACTUAL
		validarDatos(
				procesoNuevo.getSpStdfechaprocesoactual() != null ? procesoNuevo.getSpStdfechaprocesoactual().toString()
						: "",
				procesoAntiguo.getSpStdfechaprocesoactual() != null
						? procesoAntiguo.getSpStdfechaprocesoactual().toString()
						: "",
				idUsuario, "SP_STDFECHAHASTA", (int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_SENDERID
		validarDatos(procesoNuevo.getSpSenderid(), procesoAntiguo.getSpSenderid(), idUsuario, "SP_SENDERID",
				(int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);
		// Agregando SP_RECEIVERID
		validarDatos(procesoNuevo.getSpReceiverid(), procesoAntiguo.getSpReceiverid(), idUsuario, "SP_RECEIVERID",
				(int) procesoNuevo.getSpIdproceso(), "SIGMA_PROCESOS", idDescripcion);

		// agregando SP_IDSECCION
		valorNuevo = "";
		valorAnterior = "";
		if (procesoNuevo.getSpIdseccion() != null) {

			CfgSecciones seccionNueva = new CfgSecciones();
			try {
				seccionNueva = cfgSeccionesDAO.getSeccionById(procesoNuevo.getSpIdseccion());
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (seccionNueva != null) {
				valorNuevo = seccionNueva.getCsecDescripcion();
			}
		}

		if (procesoAntiguo.getSpIdseccion() != null) {
			CfgSecciones seccionAntiguo = new CfgSecciones();
			try {
				seccionAntiguo = cfgSeccionesDAO.getSeccionById(procesoAntiguo.getSpIdseccion());
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (seccionAntiguo != null) {
				valorAnterior = seccionAntiguo.getCsecDescripcion();
			}
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "SP_IDSECCION ", (int) procesoNuevo.getSpIdproceso(),
				"SIGMA_PROCESOS", idDescripcion);

		valorNuevo = "";
		valorAnterior = "";
		CfgCentrosdesalud centroNueva = new CfgCentrosdesalud();
		CfgCentrosDeSaludDAO centro = new CfgCentrosDeSaludDAO();
		if (procesoNuevo.getSpIdcentrodesalud() != null) {
			try {

				centroNueva = centro.getCentrosDeSaludById(procesoNuevo.getSpIdcentrodesalud());
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (centroNueva != null) {
				valorNuevo = centroNueva.getCcdsDescripcion();
			}
		}
		if (procesoAntiguo.getSpIdcentrodesalud() != null) {
			try {

				centroNueva = centro.getCentrosDeSaludById(procesoAntiguo.getSpIdcentrodesalud());
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (centroNueva != null) {
				valorAnterior = centroNueva.getCcdsDescripcion();
			}
		}

		// Agregando SP_IDCENTRODESALUD
		validarDatos(valorNuevo, valorAnterior, idUsuario, "SP_IDCENTRODESALUD ", (int) procesoNuevo.getSpIdproceso(),
				"SIGMA_PROCESOS", idDescripcion);

	}

	public void compararTablasSigmaProcesosTest(SigmaProcesostest procesoNuevo, SigmaProcesostest procesoAntiguo,
			Long idUsuario, String idDescripcion) {
		// Agregando SPT_CODIGORECEPCION
		validarDatos(procesoNuevo.getSptCodigorecepcion(), procesoAntiguo.getSptCodigorecepcion(), idUsuario,
				"SPT_CODIGORECEPCION", (int) procesoNuevo.getId().getSptIdproceso(), "SIGMA_PROCESOSTEST",
				idDescripcion);
		// Agregando SPT_CODIGOENVIO
		validarDatos(procesoNuevo.getSptCodigoenvio(), procesoAntiguo.getSptCodigoenvio(), idUsuario, "SPT_CODIGOENVIO",
				(int) procesoNuevo.getId().getSptIdproceso(), "SIGMA_PROCESOSTEST", idDescripcion);

		// Agregando SPT_IDTIPORESULTADO
		String valorNuevo = "";
		String valorAnterior = "";

		if (procesoNuevo.getCfgTiposderesultado() != null) {
			int valorResultado = procesoNuevo.getCfgTiposderesultado().getCtrIdtiporesultado();
			CfgTiposderesultado resultadoNueva = new CfgTiposderesultado();
			try {
				resultadoNueva = cfgTiposderesultadoDAO.getTipoResultadoById(valorResultado);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (resultadoNueva != null) {
				valorNuevo = resultadoNueva.getCtrDescripcion();
			}
		}

		if (procesoAntiguo.getCfgTiposderesultado() != null) {
			CfgTiposderesultado resultadoAntigua = new CfgTiposderesultado();

			int valorAntiguo = procesoAntiguo.getCfgTiposderesultado().getCtrIdtiporesultado();
			try {
				resultadoAntigua = cfgTiposderesultadoDAO.getTipoResultadoById(valorAntiguo);
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (resultadoAntigua != null) {
				valorAnterior = resultadoAntigua.getCtrDescripcion();
			}
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "SPT_IDTIPORESULTADO ",
				(int) procesoNuevo.getId().getSptIdproceso(), "SIGMA_PROCESOSTEST", idDescripcion);
		

		// Agregando SPT_IDSIGMATIPOMUESTRA 
		  valorNuevo = "";
		  valorAnterior = "";

		if (procesoNuevo.getSigmaTiposmuestras() != null) {
			int valorResultado = procesoNuevo.getSigmaTiposmuestras().getStmIdsigmatipomuestra();
			SigmaTiposmuestras sigmaMuesdtra = new SigmaTiposmuestras();
			try {
				sigmaMuesdtra = sigmaTiposMuestrasDAO.getSigmaTiposMuestrasById((byte) valorResultado);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (sigmaMuesdtra != null) {
				valorNuevo = sigmaMuesdtra.getStmDescripcion();
			}
		}

		if (procesoAntiguo.getSigmaTiposmuestras() != null) {
			SigmaTiposmuestras sigmaMuesdtra = new SigmaTiposmuestras();

			int valorAntiguo = procesoAntiguo.getSigmaTiposmuestras().getStmIdsigmatipomuestra();
			try {
				sigmaMuesdtra = sigmaTiposMuestrasDAO.getSigmaTiposMuestrasById((byte) valorAntiguo);
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (sigmaMuesdtra != null) {
				valorAnterior = sigmaMuesdtra.getStmDescripcion();
			}
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "SPT_IDSIGMATIPOMUESTRA",
				(int) procesoNuevo.getId().getSptIdproceso(), "SIGMA_PROCESOSTEST", idDescripcion);
		
		// Agregando SPT_PREFIJOTIPOMUESTRA
				validarDatos(procesoNuevo.getCfgMuestrasprefijos() != null ? procesoNuevo.getCfgMuestrasprefijos().getCmuepPrefijotipomuestra() : "" ,procesoAntiguo.getCfgMuestrasprefijos() != null ? procesoAntiguo.getCfgMuestrasprefijos().getCmuepPrefijotipomuestra() : "" , idUsuario,
						"SPT_PREFIJOTIPOMUESTRA", (int) procesoNuevo.getId().getSptIdproceso(), "SIGMA_PROCESOSTEST",
						idDescripcion);
				// Agregando SPT_ACTIVO
				validarDatos(procesoNuevo.getSptActivo(), procesoAntiguo.getSptActivo(), idUsuario, "SPT_ACTIVO",
						(int) procesoNuevo.getId().getSptIdproceso(), "SIGMA_PROCESOSTEST", idDescripcion);
				// Agregando SPT_CONVERSION
				validarDatos(procesoNuevo.getSptConversion(), procesoAntiguo.getSptConversion(), idUsuario, "SPT_CONVERSION",
						(int) procesoNuevo.getId().getSptIdproceso(), "SIGMA_PROCESOSTEST", idDescripcion);
				
		      
		        
				
				

	}
	
	
	public void compararTablasSigmaConversion(SigmaProcesotestconversion nuevo, SigmaProcesotestconversion antiguo,
			Long idUsuario, String idDescripcion) {
		// Agregando SPTC_NUMEROCONDICION
		
				validarDatos( Byte.toString( nuevo.getId().getSptcNumerocondicion()) ,Byte.toString(antiguo.getId().getSptcNumerocondicion()), idUsuario, "SPTC_NUMEROCONDICION",
						(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
				// Agregando SPTC_IDTIPOCONDICION
				String condicion ="";
				String condicionantigua = "";
				LdvTiposCondicionDAO condiDAO = new LdvTiposCondicionDAO();
				if(nuevo.getLdvTiposcondicion() != null ) {
					condicion = nuevo.getLdvTiposcondicion().getLdvtcondDescripcion() != null ? nuevo.getLdvTiposcondicion().getLdvtcondDescripcion() : "";
				}
				
				if(antiguo.getLdvTiposcondicion() != null ) {
					
					LdvTiposcondicion sigmaConversion = new LdvTiposcondicion();

					
					try {
						if(antiguo.getLdvTiposcondicion().getLdvtcondIdtipocondicion() != 0) {
							sigmaConversion = condiDAO.getTipoCondicionById(antiguo.getLdvTiposcondicion().getLdvtcondIdtipocondicion());
						}
					} catch (BiosLISDAOException e1) {
						e1.printStackTrace();
					}
					if (sigmaConversion != null) {
						condicionantigua =sigmaConversion.getLdvtcondDescripcion();
					}
					
				}
				
				
				validarDatos(condicion, condicionantigua, idUsuario, "SPTC_IDTIPOCONDICION",
						(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
		// Agregando SPTC_CRITICOSN
		validarDatos(nuevo.getSptcCriticosn(), antiguo.getSptcCriticosn(), idUsuario, "SPTC_CRITICOSN",
				(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
		// Agregando SPTC_VALORENTRADA
				validarDatos(nuevo.getSptcValorentrada(), antiguo.getSptcValorentrada(), idUsuario, "SPTC_VALORENTRADA",
						(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
				// Agregando SPTC_VALORSALIDA
				validarDatos(nuevo.getSptcValorsalida(), antiguo.getSptcValorsalida(), idUsuario, "SPTC_VALORSALIDA",
						(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
				// Agregando SPTC_REPROCESARSN
				validarDatos(nuevo.getSptcReprocesarsn(), antiguo.getSptcReprocesarsn(), idUsuario, "SPTC_REPROCESARSN",
						(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
				// Agregando SPTC_BLOQUEARSN
				validarDatos(nuevo.getSptcBloquearsn(), antiguo.getSptcBloquearsn(), idUsuario, "SPTC_BLOQUEARSN",
						(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
				// Agregando SPTC_FIRMARSN
				validarDatos(nuevo.getSptcFirmarsn(), antiguo.getSptcFirmarsn(), idUsuario, "SPTC_FIRMARSN",
						(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
				// Agregando SPTC_CONDICIONALSN
				validarDatos(nuevo.getSptcCondicionalsn(), antiguo.getSptcCondicionalsn(), idUsuario, "SPTC_CONDICIONALSN",
						(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
				// Agregando SPTC_CALCULOSN
				validarDatos(nuevo.getSptcCalculosn(), antiguo.getSptcCalculosn(), idUsuario, "SPTC_CALCULOSN",
						(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
				// Agregando SPTC_IDOPERACIONRESULTADO
				
				condicionantigua= "";
				LdvOperacionesMathDAO operaciones = new LdvOperacionesMathDAO();
				if(antiguo.getLdvOperacionesmath() != null ){ 
				
					LdvOperacionesmath sigmacondicion = new LdvOperacionesmath();
	
					
					try {
						sigmacondicion = operaciones.getOperacionesMathById(antiguo.getLdvOperacionesmath().getLdvomIdoperadormath());
					} catch (BiosLISDAOException e1) {
						e1.printStackTrace();
					}
					if (sigmacondicion != null) {
						condicionantigua = sigmacondicion.getLdvomDescripcion();
					}
				}	
				
				validarDatos(nuevo.getLdvOperacionesmath() != null ? nuevo.getLdvOperacionesmath().getLdvomDescripcion(): "" ,condicionantigua , idUsuario, "SPTC_IDOPERACIONRESULTADO",
						(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
				// SPTC_VALOROPERANDO
				validarDatos(nuevo.getSptcValoroperando() != null ? nuevo.getSptcValoroperando().toString() : "", antiguo.getSptcValoroperando() != null ? antiguo.getSptcValoroperando().toString() : "", idUsuario, "SPTC_VALOROPERANDO",
						(int) nuevo.getId().getSptcIdproceso(), "SIGMA_PROCESOTESTCONVERSION", idDescripcion);
														
		      
      
		
		
	}
	

	public void compararTablasServicios(CfgServicios servicioNuevo, CfgServicios servicioAntiguo, Long idUsuario,
			String idDescripcion) {
		// Agregando CS_CODIGO
		validarDatos(servicioNuevo.getCsCodigo(), servicioAntiguo.getCsCodigo(), idUsuario, "CS_CODIGO",
				(int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS", idDescripcion);
		// Agregando CS_DESCRIPCION
		validarDatos(servicioNuevo.getCsDescripcion(), servicioAntiguo.getCsDescripcion(), idUsuario, "CS_DESCRIPCION",
				(int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS", idDescripcion);
		// Agregando CS_EMAIL
		validarDatos(servicioNuevo.getCsEmail(), servicioAntiguo.getCsEmail(), idUsuario, "CS_EMAIL",
				(int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS", idDescripcion);
		// Agregando CS_ACTIVO
		validarDatos(servicioNuevo.getCsActivo(), servicioAntiguo.getCsActivo(), idUsuario, "CS_ACTIVO",
				(int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS", idDescripcion);
		// Agregando CS_SORT
		validarDatos(String.valueOf(servicioNuevo.getCsSort()), String.valueOf(servicioAntiguo.getCsSort()), idUsuario,
				"CS_SORT", (int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS", idDescripcion);
		// Agregando CS_HOST
		validarDatos(servicioNuevo.getCsHost(), servicioAntiguo.getCsHost(), idUsuario, "CS_HOST",
				(int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS", idDescripcion);
		// Agregando CS_HOSTMICRO
		validarDatos(servicioNuevo.getCsHostmicro(), servicioAntiguo.getCsHostmicro(), idUsuario, "CS_HOSTMICRO",
				(int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS", idDescripcion);
		// Agregando CS_HOST2
		validarDatos(servicioNuevo.getCsHost2(), servicioAntiguo.getCsHost2(), idUsuario, "CS_HOST2",
				(int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS", idDescripcion);
		// Agregando CS_HOST3
		validarDatos(servicioNuevo.getCsHost3(), servicioAntiguo.getCsHost3(), idUsuario, "CS_HOST3",
				(int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS", idDescripcion);
		// Agregando CS_URGENTE
		validarDatos(servicioNuevo.getCsUrgente(), servicioAntiguo.getCsUrgente(), idUsuario, "CS_URGENTE",
				(int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS", idDescripcion);
		// Agregando CS_INDICADOR
		validarDatos(servicioNuevo.getCsIndicador(), servicioAntiguo.getCsIndicador(), idUsuario, "CS_INDICADOR",
				(int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS", idDescripcion);
		// Agregando CS_IMPRIMECONFIDENCIALES
		validarDatos(servicioNuevo.getCsImprimeconfidenciales(), servicioAntiguo.getCsImprimeconfidenciales(),
				idUsuario, "CS_IMPRIMECONFIDENCIALES", (int) servicioNuevo.getCsIdservicio(), "CFG_SERVICIOS",
				idDescripcion);

		String valorNuevo = "";
		String valorAnterior = "";
		CfgCentrosdesalud centroNueva = new CfgCentrosdesalud();
		CfgCentrosDeSaludDAO centro = new CfgCentrosDeSaludDAO();
		if (servicioNuevo.getCfgCentrosdesalud() != null) {
			try {

				centroNueva = centro
						.getCentrosDeSaludById(servicioNuevo.getCfgCentrosdesalud().getCcdsIdcentrodesalud());
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (centroNueva != null) {
				valorNuevo = centroNueva.getCcdsDescripcion();
			}
		}
		if (servicioAntiguo.getCfgCentrosdesalud() != null) {
			try {

				centroNueva = centro
						.getCentrosDeSaludById(servicioAntiguo.getCfgCentrosdesalud().getCcdsIdcentrodesalud());
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (centroNueva != null) {
				valorAnterior = centroNueva.getCcdsDescripcion();
			}
		}

		// Agregando CS_IDCENTRODESALUD
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CS_IDCENTRODESALUD", (int) servicioNuevo.getCsIdservicio(),
				"CFG_SERVICIOS", idDescripcion);

	}

	public void compararTablasSecciones(CfgSecciones seccionNuevo, CfgSecciones seccionAntiguo, Long idUsuario,
			String idDescripcion) {
		// Agregando CSEC_CODIGO
		validarDatos(seccionNuevo.getCsecCodigo(), seccionAntiguo.getCsecCodigo(), idUsuario, "CSEC_CODIGO",
				(int) seccionNuevo.getCsecIdseccion(), "CFG_SECCIONES", idDescripcion);
		// Agregando CSEC_DESCRIPCION
		validarDatos(seccionNuevo.getCsecDescripcion(), seccionAntiguo.getCsecDescripcion(), idUsuario,
				"CSEC_DESCRIPCION", (int) seccionNuevo.getCsecIdseccion(), "CFG_SECCIONES", idDescripcion);
		// Agregando CSEC_IDLABORATORIO

		String valorNuevo = "";
		String valorAnterior = "";
		CfgLaboratoriosDAO laboratorio = new CfgLaboratoriosDAO();
		CfgLaboratorios lab = new CfgLaboratorios();
		try {
			lab = laboratorio.getLaboratorioById(seccionNuevo.getCfgLaboratorios().getClabIdlaboratorio());
		} catch (BiosLISDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		valorNuevo = lab.getClabDescripcion();

		try {
			lab = laboratorio.getLaboratorioById(seccionAntiguo.getCfgLaboratorios().getClabIdlaboratorio());
		} catch (BiosLISDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		valorAnterior = lab.getClabDescripcion();

		validarDatos(valorNuevo, valorAnterior, idUsuario, "CSEC_IDLABORATORIO", (int) seccionNuevo.getCsecIdseccion(),
				"CFG_SECCIONES", idDescripcion);
				
		// Agregando CSEC_ACTIVA
		validarDatos(seccionNuevo.getCsecActiva(), seccionAntiguo.getCsecActiva(), idUsuario, "CSEC_ACTIVA",
				(int) seccionNuevo.getCsecIdseccion(), "CFG_SECCIONES", idDescripcion);
		// Agregando CSEC_SORT
		validarDatos(String.valueOf(seccionNuevo.getCsecSort()), String.valueOf(seccionAntiguo.getCsecSort()),
				idUsuario, "CSEC_SORT", (int) seccionNuevo.getCsecIdseccion(), "CFG_SECCIONES", idDescripcion);
		// Agregando CSEC_COLOR
		validarDatos(seccionNuevo.getCsecColor(), seccionAntiguo.getCsecColor(), idUsuario, "CSEC_COLOR",
				(int) seccionNuevo.getCsecIdseccion(), "CFG_SECCIONES", idDescripcion);

	}

	public void compararTablasExamenes(ExamenFullDTO examenNuevo, ExamenFullDTO examenAntiguo, Long idUsuario,
			String idDescripcion) {

		// Agregando CE_CODIGOEXAMEN
		validarDatos(examenNuevo.getCfgExamenes().getCeCodigoexamen(),
				examenAntiguo.getCfgExamenes().getCeCodigoexamen(), idUsuario, "CE_CODIGOEXAMEN",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_DESCRIPCION
		validarDatos(examenNuevo.getCfgExamenes().getCeDescripcion(), examenAntiguo.getCfgExamenes().getCeDescripcion(),
				idUsuario, "CE_DESCRIPCION", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_ABREVIADO
		validarDatos(examenNuevo.getCfgExamenes().getCeAbreviado(), examenAntiguo.getCfgExamenes().getCeAbreviado(),
				idUsuario, "CE_ABREVIADO", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_ABREVIADO2
		validarDatos(examenNuevo.getCfgExamenes().getCeAbreviado2(), examenAntiguo.getCfgExamenes().getCeAbreviado2(),
				idUsuario, "CE_ABREVIADO2", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);

		// agregando CE_IDINDICACIONESEXAMEN
		String valorNuevo = "";
		String valorAnterior = "";
		if (examenNuevo.getLdvIndicacionesexamenes() != null) {
			Long valor = examenNuevo.getLdvIndicacionesexamenes().getLdvieIdindicacionesexamen();
			LdvIndicacionesexamenes indicacion = new LdvIndicacionesexamenes();
			try {
				indicacion = indicacionesExamenesDAO.getIndicacionesExamen(valor);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = indicacion.getLdvieDescripcion();
		}
		if (examenAntiguo.getLdvIndicacionesexamenes() != null) {
			valorAnterior = examenAntiguo.getLdvIndicacionesexamenes().getLdvieDescripcion();
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CE_IDINDICACIONESEXAMEN",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_ACTIVO
		validarDatos(examenNuevo.getCfgExamenes().getCeActivo(), examenAntiguo.getCfgExamenes().getCeActivo(),
				idUsuario, "CE_ACTIVO", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_IMPRIMIRSEPARADO
		validarDatos(examenNuevo.getCfgExamenes().getCeImprimirseparado(),
				examenAntiguo.getCfgExamenes().getCeImprimirseparado(), idUsuario, "CE_IMPRIMIRSEPARADO",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_IMPRIMIRDESCRIPCION
		validarDatos(examenNuevo.getCfgExamenes().getCeImprimirdescripcion(),
				examenAntiguo.getCfgExamenes().getCeImprimirdescripcion(), idUsuario, "CE_IMPRIMIRDESCRIPCION",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_IMPRIMIRPORSECCION
		validarDatos(examenNuevo.getCfgExamenes().getCeImprimirporseccion(),
				examenAntiguo.getCfgExamenes().getCeImprimirporseccion(), idUsuario, "CE_IMPRIMIRPORSECCION",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_IMPRIMIRTABLA
		validarDatos(examenNuevo.getCfgExamenes().getCeImprimirtabla(),
				examenAntiguo.getCfgExamenes().getCeImprimirtabla(), idUsuario, "CE_IMPRIMIRTABLA",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_MULTIFORMATO
		validarDatos(examenNuevo.getCfgExamenes().getCeMultiformato(),
				examenAntiguo.getCfgExamenes().getCeMultiformato(), idUsuario, "CE_MULTIFORMATO",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_IDFORMATO
		valorNuevo = "";
		valorAnterior = "";
		if (examenNuevo.getLdvFormatos() != null) {
			Long valor = (long) examenNuevo.getLdvFormatos().getLdvfIdformato();
			LdvFormatos formato = new LdvFormatos();
			try {
				formato = ldvFormatosDAO.getFormatoById(valor);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = formato.getLdvfDescripcion();
		}
		if (examenAntiguo.getLdvFormatos() != null) {
			valorAnterior = examenAntiguo.getLdvFormatos().getLdvfDescripcion();
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CE_IDFORMATO",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_IDTIPOEXAMEN
		valorNuevo = "";
		valorAnterior = "";
		if (examenNuevo.getLdvTiposexamenes() != null) {
			Long valor = (long) examenNuevo.getLdvTiposexamenes().getLdvteIdtipoexamen();
			LdvTiposexamenes tipoExamen = new LdvTiposexamenes();
			try {
				tipoExamen = tiposExamenesDAO.getTipoExamenById(valor);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = tipoExamen.getLdvteDescripcion();
		}
		LdvTiposexamenes tipoExamenAntiguo = new LdvTiposexamenes();
		try {
			tipoExamenAntiguo = tiposExamenesDAO.getTipoExamen(examenAntiguo.getCfgExamenes().getCeIdexamen());
		} catch (BiosLISDAOException e1) {
			e1.printStackTrace();
		}

		if (tipoExamenAntiguo != null) {
			valorAnterior = tipoExamenAntiguo.getLdvteDescripcion();
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CE_IDTIPOEXAMEN",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_HOST
		validarDatos(examenNuevo.getCfgExamenes().getCeHost(), examenAntiguo.getCfgExamenes().getCeHost(), idUsuario,
				"CE_HOST", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_HOST2
		validarDatos(examenNuevo.getCfgExamenes().getCeHost2(), examenAntiguo.getCfgExamenes().getCeHost2(), idUsuario,
				"CE_HOST2", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_HOST3
		validarDatos(examenNuevo.getCfgExamenes().getCeHost3(), examenAntiguo.getCfgExamenes().getCeHost3(), idUsuario,
				"CE_HOST3", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_HOSTMICRO
		validarDatos(examenNuevo.getCfgExamenes().getCeHostmicro(), examenAntiguo.getCfgExamenes().getCeHostmicro(),
				idUsuario, "CE_HOSTMICRO", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_CODIGOFONASA
		validarDatos(examenNuevo.getCfgExamenes().getCeCodigofonasa(),
				examenAntiguo.getCfgExamenes().getCeCodigofonasa(), idUsuario, "CE_CODIGOFONASA",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_IDSECCION
		valorNuevo = "";
		valorAnterior = "";
		if (examenNuevo.getCfgSecciones() != null) {
			Short valor = (short) examenNuevo.getCfgSecciones().getCsecIdseccion();
			CfgSecciones seccionNueva = new CfgSecciones();
			try {
				seccionNueva = cfgSeccionesDAO.getSeccionById(valor);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = seccionNueva.getCsecDescripcion();
		}
		CfgSecciones seccionAntiguo = new CfgSecciones();
		try {
			seccionAntiguo = cfgSeccionesDAO.getSeccionById(examenAntiguo.getCfgSecciones().getCsecIdseccion());
		} catch (BiosLISDAOException e1) {
			e1.printStackTrace();
		}
		if (seccionAntiguo != null) {
			valorAnterior = seccionAntiguo.getCsecDescripcion();
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CE_IDSECCION",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_IDTIPOMUESTRA
		valorNuevo = "";
		valorAnterior = "";
		if (examenNuevo.getCfgMuestras() != null) {

			Short valor = (short) examenNuevo.getCfgMuestras().getCmueIdtipomuestra();
			CfgMuestras muestraNueva = new CfgMuestras();
			try {
				muestraNueva = cfgMuestrasDAO.getMuestraById(valor);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = muestraNueva.getCmueDescripcionabreviada();
		}
		CfgMuestras muestraAntiguo = new CfgMuestras();
		try {
			muestraAntiguo = cfgMuestrasDAO.getMuestraById(examenAntiguo.getCfgMuestras().getCmueIdtipomuestra());
		} catch (BiosLISDAOException e1) {
			e1.printStackTrace();
		}
		if (muestraAntiguo != null) {
			valorAnterior = muestraAntiguo.getCmueDescripcionabreviada();
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CE_IDTIPOMUESTRA",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_ESTADISTICA
		validarDatos(examenNuevo.getCfgExamenes().getCeEstadisticas(),
				examenAntiguo.getCfgExamenes().getCeEstadisticas(), idUsuario, "CE_ESTADISTICA",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_NOTA
		validarDatos(examenNuevo.getCfgExamenes().getCeNota(), examenAntiguo.getCfgExamenes().getCeNota(), idUsuario,
				"CE_NOTA", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_NOTA
		validarDatos(examenNuevo.getCfgExamenes().getCeEspesquisa(), examenAntiguo.getCfgExamenes().getCeEspesquisa(),
				idUsuario, "CE_ESPESQUISA", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_PESQUISAOBLIGATORIA
		validarDatos(examenNuevo.getCfgExamenes().getCePesquisaobligatoria(),
				examenAntiguo.getCfgExamenes().getCePesquisaobligatoria(), idUsuario, "CE_PESQUISAOBLIGATORIA",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_DATOSMUESTRA
		validarDatos(examenNuevo.getCfgExamenes().getCeDatosmuestra(),
				examenAntiguo.getCfgExamenes().getCeDatosmuestra(), idUsuario, "CE_DATOSMUESTRA",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_TIEMPOENTREGANORMAL
		validarDatos(examenNuevo.getCfgExamenes().getCeTiempoentreganormal().toString(),
				examenAntiguo.getCfgExamenes().getCeTiempoentreganormal().toString(), idUsuario,
				"CE_TIEMPOENTREGANORMAL", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_TIEMPOENTREGAURGENTE
		validarDatos(examenNuevo.getCfgExamenes().getCeTiempoentregaurgente().toString(),
				examenAntiguo.getCfgExamenes().getCeTiempoentregaurgente().toString(), idUsuario,
				"CE_TIEMPOENTREGAURGENTE", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_TIEMPOENTREGAHOSPITALIZADO
		validarDatos(examenNuevo.getCfgExamenes().getCeTiempoentregahospitalizado().toString(),
				examenAntiguo.getCfgExamenes().getCeTiempoentregahospitalizado().toString(), idUsuario,
				"CE_TIEMPOENTREGAHOSPITALIZADO", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_DIAPROCESOLUNES
		validarDatos(examenNuevo.getCfgExamenes().getCeDiaprocesolunes(),
				examenAntiguo.getCfgExamenes().getCeDiaprocesolunes(), idUsuario, "CE_DIAPROCESOLUNES",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_DIAPROCESOMARTES
		validarDatos(examenNuevo.getCfgExamenes().getCeDiaprocesomartes(),
				examenAntiguo.getCfgExamenes().getCeDiaprocesomartes(), idUsuario, "CE_DIAPROCESOMARTES",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_DIAPROCESOMIERCOLES
		validarDatos(examenNuevo.getCfgExamenes().getCeDiaprocesomiercoles(),
				examenAntiguo.getCfgExamenes().getCeDiaprocesomiercoles(), idUsuario, "CE_DIAPROCESOMIERCOLES",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_DIAPROCESOJUEVES
		validarDatos(examenNuevo.getCfgExamenes().getCeDiaprocesojueves(),
				examenAntiguo.getCfgExamenes().getCeDiaprocesojueves(), idUsuario, "CE_DIAPROCESOJUEVES",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_DIAPROCESOVIERNES
		validarDatos(examenNuevo.getCfgExamenes().getCeDiaprocesoviernes(),
				examenAntiguo.getCfgExamenes().getCeDiaprocesoviernes(), idUsuario, "CE_DIAPROCESOVIERNES",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_DIAPROCESOSABADO
		validarDatos(examenNuevo.getCfgExamenes().getCeDiaprocesosabado(),
				examenAntiguo.getCfgExamenes().getCeDiaprocesosabado(), idUsuario, "CE_DIAPROCESOSABADO",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_DIAPROCESODOMINGO
		validarDatos(examenNuevo.getCfgExamenes().getCeDiaprocesodomingo(),
				examenAntiguo.getCfgExamenes().getCeDiaprocesodomingo(), idUsuario, "CE_DIAPROCESODOMINGO",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_VOLUMENMUESTRAADULTO
		validarDatos(String.valueOf(examenNuevo.getCfgExamenes().getCeVolumenmuestraadulto()),
				String.valueOf(examenAntiguo.getCfgExamenes().getCeVolumenmuestraadulto()), idUsuario,
				"CE_VOLUMENMUESTRAADULTO", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_VOLUMENMUESTRAPEDIATRICA
		validarDatos(String.valueOf(examenNuevo.getCfgExamenes().getCeVolumenmuestrapediatrica()),
				String.valueOf(examenAntiguo.getCfgExamenes().getCeVolumenmuestrapediatrica()), idUsuario,
				"CE_VOLUMENMUESTRAPEDIATRICA", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_ESTABILIDADAMBIENTAL
		validarDatos(examenNuevo.getCfgExamenes().getCeEstabilidadambiental(),
				examenAntiguo.getCfgExamenes().getCeEstabilidadambiental(), idUsuario, "CE_ESTABILIDADAMBIENTAL",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_ESTABILIDADREFRIGERADO
		validarDatos(examenNuevo.getCfgExamenes().getCeEstabilidadrefrigerado(),
				examenAntiguo.getCfgExamenes().getCeEstabilidadrefrigerado(), idUsuario, "CE_ESTABILIDADREFRIGERADO",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_ESTABILIDADCONGELADO
		validarDatos(examenNuevo.getCfgExamenes().getCeEstabilidadcongelado(),
				examenAntiguo.getCfgExamenes().getCeEstabilidadcongelado(), idUsuario, "CE_ESTABILIDADCONGELADO",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_CONTABLECANTIDAD
		validarDatos(examenNuevo.getCfgExamenes().getCeContablecantidad().toString(),
				examenAntiguo.getCfgExamenes().getCeContablecantidad().toString(), idUsuario, "CE_CONTABLECANTIDAD",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_ESEXAMEN
		validarDatos(examenNuevo.getCfgExamenes().getCeEsexamen(), examenAntiguo.getCfgExamenes().getCeEsexamen(),
				idUsuario, "CE_ESEXAMEN", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);

		// agregando CE_IDGRUPOEXAMENFONASA
		valorNuevo = "";
		valorAnterior = "";
		if (examenNuevo.getCfgGruposexamenesfonasa().getCgefIdgrupoexamenfonasa() != 0) {

			Short valor = (short) examenNuevo.getCfgGruposexamenesfonasa().getCgefIdgrupoexamenfonasa();
			CfgGruposexamenesfonasa grupoNueva = new CfgGruposexamenesfonasa();
			try {
				grupoNueva = gruposExamenesFonasaDAO.getGrupoExamenesFonasaById(valor);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = grupoNueva.getCgefDescripcion();
		}
		CfgGruposexamenesfonasa grupoAntiguo = new CfgGruposexamenesfonasa();
		if (examenAntiguo.getCfgGruposexamenesfonasa() != null) {
			Short valor = (short) examenAntiguo.getCfgGruposexamenesfonasa().getCgefIdgrupoexamenfonasa();
			try {
				grupoAntiguo = gruposExamenesFonasaDAO.getGrupoExamenesFonasaById(valor);
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (grupoAntiguo != null) {
				valorAnterior = grupoAntiguo.getCgefDescripcion();
			}
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CE_IDGRUPOEXAMENFONASA",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_TIENETESTOPCIONALES
		validarDatos(examenNuevo.getCfgExamenes().getCeTienetestopcionales(),
				examenAntiguo.getCfgExamenes().getCeTienetestopcionales(), idUsuario, "CE_TIENETESTOPCIONALES",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_ESURGENTE
		validarDatos(examenNuevo.getCfgExamenes().getCeEsurgente(), examenAntiguo.getCfgExamenes().getCeEsurgente(),
				idUsuario, "CE_ESURGENTE", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_ESCULTIVO
		validarDatos(examenNuevo.getCfgExamenes().getCeEscultivo(), examenAntiguo.getCfgExamenes().getCeEscultivo(),
				idUsuario, "CE_ESCULTIVO", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_NUMERODEETIQUETAS
		validarDatos(examenNuevo.getCfgExamenes().getCeNumerodeetiquetas().toString(),
				examenAntiguo.getCfgExamenes().getCeNumerodeetiquetas().toString(), idUsuario, "CE_NUMERODEETIQUETAS",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_ESCONFIDENCIAL
		validarDatos(examenNuevo.getCfgExamenes().getCeEsconfidencial(),
				examenAntiguo.getCfgExamenes().getCeEsconfidencial(), idUsuario, "CE_ESCONFIDENCIAL",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_IDGRUPOEXAMEN
		valorNuevo = "";
		valorAnterior = "";
		if (examenNuevo.getLdvGruposexamenes() != null && examenNuevo.getLdvGruposexamenes().getLdvgeIdgrupoexamen() != 0) {

			Long valor = (Long) examenNuevo.getLdvGruposexamenes().getLdvgeIdgrupoexamen();
			LdvGruposexamenes grupoNueva = new LdvGruposexamenes();
			try {
				grupoNueva = ldvGruposExamenesDAO.getGrupoExamenesByID(valor);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = grupoNueva.getLdvgeDescripcion();
		}
		LdvGruposexamenes grupoExamenAntiguo = new LdvGruposexamenes();
		if (examenAntiguo.getLdvGruposexamenes() != null) {
			Long valor = (Long) examenAntiguo.getLdvGruposexamenes().getLdvgeIdgrupoexamen();
			try {
				grupoExamenAntiguo = ldvGruposExamenesDAO.getGrupoExamenesByID(valor);
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (grupoExamenAntiguo != null) {
				valorAnterior = grupoExamenAntiguo.getLdvgeDescripcion();
			}
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CE_IDGRUPOEXAMEN",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_IDDERIVADOR
		valorNuevo = "";
		valorAnterior = "";

		Short valor = examenNuevo.getIdDerivador();
		CfgDerivadores derivadorNueva = new CfgDerivadores();
		try {
			derivadorNueva = cfgDerivadoresDAO.getDerivadorById(valor);
		} catch (BiosLISDAOException e) {
			e.printStackTrace();
		}
		valorNuevo = derivadorNueva.getCderivDescripcion();

		CfgDerivadores derivadorExamenAntiguo = new CfgDerivadores();
		Short valorAntiguo = examenAntiguo.getCfgDerivadores().getCderivIdderivador();
		try {
			derivadorExamenAntiguo = cfgDerivadoresDAO.getDerivadorById(valorAntiguo);
		} catch (BiosLISDAOException e1) {
			e1.printStackTrace();
		}
		if (derivadorExamenAntiguo != null) {
			valorAnterior = derivadorExamenAntiguo.getCderivDescripcion();
		}

		validarDatos(valorNuevo, valorAnterior, idUsuario, "CE_IDDERIVADOR",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_ESINDICADOR
		validarDatos(examenNuevo.getCfgExamenes().getCeEsindicador(), examenAntiguo.getCfgExamenes().getCeEsindicador(),
				idUsuario, "CE_ESINDICADOR", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_NOMBREWEB
		validarDatos(examenNuevo.getCfgExamenes().getCeNombreweb(), examenAntiguo.getCfgExamenes().getCeNombreweb(),
				idUsuario, "CE_NOMBREWEB", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_DISPONIBLEWEB
		validarDatos(examenNuevo.getCfgExamenes().getCeDisponibleweb(),
				examenAntiguo.getCfgExamenes().getCeDisponibleweb(), idUsuario, "CE_DISPONIBLEWEB",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_DIAGNOSTICOOBLIGATORIO
		validarDatos(examenNuevo.getCfgExamenes().getCeDiagnosticoobligatorio(),
				examenAntiguo.getCfgExamenes().getCeDiagnosticoobligatorio(), idUsuario, "CE_DIAGNOSTICOOBLIGATORIO",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_HOJATRABAJO
		validarDatos(examenNuevo.getCfgExamenes().getCeHojatrabajo(), examenAntiguo.getCfgExamenes().getCeHojatrabajo(),
				idUsuario, "CE_HOJATRABAJO", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_ESCURVATOLERANCIA
		validarDatos(examenNuevo.getCfgExamenes().getCeEscurvatolerancia(),
				examenAntiguo.getCfgExamenes().getCeEscurvatolerancia(), idUsuario, "CE_ESCURVATOLERANCIA",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_PLANILLAHISTORICA
		validarDatos(examenNuevo.getCfgExamenes().getCePlanillahistorica(),
				examenAntiguo.getCfgExamenes().getCePlanillahistorica(), idUsuario, "CE_PLANILLAHISTORICA",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_DIASDEVALIDEZ
		validarDatos(examenNuevo.getCfgExamenes().getCeDiasdevalidez().toString(),
				examenAntiguo.getCfgExamenes().getCeDiasdevalidez().toString(), idUsuario, "CE_DIASDEVALIDEZ",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_DIASDEVALIDEZHOSPITALIZADO
		validarDatos(examenNuevo.getCfgExamenes().getCeDiasvalidezhospitalizado().toString(),
				examenAntiguo.getCfgExamenes().getCeDiasvalidezhospitalizado().toString(), idUsuario,
				"CE_DIASVALIDEZHOSPITALIZADO", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_DIASDEVALIDEZHOSPITALIZADOBLQ
		validarDatos(examenNuevo.getCfgExamenes().getCeDiasvalidezhospitalizadoblq(),
				examenAntiguo.getCfgExamenes().getCeDiasvalidezhospitalizadoblq(), idUsuario,
				"CE_DIASVALIDEZHOSPITALIZADOBLQ", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_DIASDEVALIDEZAMBULATORIOBLQ
		validarDatos(examenNuevo.getCfgExamenes().getCeDiasvalidezambulatorioblq(),
				examenAntiguo.getCfgExamenes().getCeDiasvalidezambulatorioblq(), idUsuario,
				"CE_DIASDEVALIDEZAMBULATORIOBLQ", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_ESEXAMENMULTISECCION
		validarDatos(examenNuevo.getCfgExamenes().getCeEsexamenmultiseccion(),
				examenAntiguo.getCfgExamenes().getCeEsexamenmultiseccion(), idUsuario, "CE_ESEXAMENMULTISECCION",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_AUTOVALIDAR
		validarDatos(examenNuevo.getCfgExamenes().getCeAutovalidar(), examenAntiguo.getCfgExamenes().getCeAutovalidar(),
				idUsuario, "CE_AUTOVALIDAR", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);
		// agregando CE_COMPARTEMUESTRA
		validarDatos(examenNuevo.getCfgExamenes().getCeCompartemuestra(),
				examenAntiguo.getCfgExamenes().getCeCompartemuestra(), idUsuario, "CE_COMPARTEMUESTRA",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);
		// agregando CE_TIENEGRUPOMUESTRAS
		validarDatos(examenNuevo.getCfgExamenes().getCeTienegrupomuestra(),
				examenAntiguo.getCfgExamenes().getCeTienegrupomuestra(), idUsuario, "CE_TIENEGRUPOMUESTRAS",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_IDMETODO
		valorNuevo = "";
		valorAnterior = "";

		if (examenNuevo.getCfgMetodos() != null) {
			int valorMetodo = examenNuevo.getCfgMetodos().getCmetIdmetodo();
			CfgMetodos metodoNueva = new CfgMetodos();
			try {
				metodoNueva = cfgMetodosDAO.getMetodoById(valorMetodo);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = metodoNueva.getCmetDescripcion();
		}

		if (examenAntiguo.getCfgExamenes().getCfgMetodos() != null) {
			CfgMetodos metodoExamenAntiguo = new CfgMetodos();

			int metodoAntiguo = examenAntiguo.getCfgExamenes().getCfgMetodos().getCmetIdmetodo();
			try {
				metodoExamenAntiguo = cfgMetodosDAO.getMetodoById(metodoAntiguo);
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (metodoExamenAntiguo != null) {
				valorAnterior = metodoExamenAntiguo.getCmetDescripcion();
			}
		}
		// agregando CE_IDMETODO
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CE_IDMETODO",
				(int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES", idDescripcion);

		// agregando CE_CELLCOUNTER
		validarDatos(examenNuevo.getCfgExamenes().getCeCellcounter(), examenAntiguo.getCfgExamenes().getCeCellcounter(),
				idUsuario, "CE_CELLCOUNTER", (int) examenAntiguo.getCfgExamenes().getCeIdexamen(), "CFG_EXAMENES",
				idDescripcion);

	}

	public void compararTablasTest(CfgTest testNuevo, CfgTest testAntiguo, Long idUsuario, String idDescripcion) {

		validarDatos(testNuevo.getCtCodigo(), testAntiguo.getCtCodigo(), idUsuario, "CT_CODIGO",
				(int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtDescripcion(), testAntiguo.getCtDescripcion(), idUsuario, "CT_DESCRIPCION",
				(int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtAbreviado(), testAntiguo.getCtAbreviado(), idUsuario, "CT_ABREVIADO",
				(int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);

		// agregando CE_IDMETODO

		String valorNuevo = "";
		String valorAnterior = "";

		if (testNuevo.getCfgEnvases() != null) {
			int valorEnvase = testNuevo.getCfgEnvases().getCenvIdenvase();
			CfgEnvases envaseNueva = new CfgEnvases();
			try {
				envaseNueva = cfgEnvasesDAO.getEnvaseById(valorEnvase);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = envaseNueva.getCenvDescripcion();
		}

		if (testAntiguo.getCfgEnvases() != null) {
			CfgEnvases envaseAntiguo = new CfgEnvases();

			int valorAntiguo = testAntiguo.getCfgEnvases().getCenvIdenvase();
			try {
				envaseAntiguo = cfgEnvasesDAO.getEnvaseById(valorAntiguo);
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (envaseAntiguo != null) {
				valorAnterior = envaseAntiguo.getCenvDescripcion();
			}
		}

		validarDatos(valorNuevo, valorAnterior, idUsuario, "CT_IDENVASE", (int) testAntiguo.getCtIdtest(), "CFG_TEST",
				idDescripcion);

		valorNuevo = "";
		valorAnterior = "";

		if (testNuevo.getCfgMuestras() != null) {
			int valorMuestra = testNuevo.getCfgMuestras().getCmueIdtipomuestra();
			CfgMuestras muestraNueva = new CfgMuestras();
			try {
				muestraNueva = cfgMuestrasDAO.getMuestraById((short) valorMuestra);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = muestraNueva.getCmueDescripcionabreviada();
		}

		if (testAntiguo.getCfgMuestras() != null) {
			CfgMuestras muestraAntiguo = new CfgMuestras();

			int valorAntiguo = testAntiguo.getCfgMuestras().getCmueIdtipomuestra();
			try {
				muestraAntiguo = cfgMuestrasDAO.getMuestraById((short) valorAntiguo);
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (muestraAntiguo != null) {
				valorAnterior = muestraAntiguo.getCmueDescripcionabreviada();
			}
		}

		validarDatos(valorNuevo, valorAnterior, idUsuario, "CT_IDTIPOMUESTRA", (int) testAntiguo.getCtIdtest(),
				"CFG_TEST", idDescripcion);

		validarDatos(testNuevo.getCtResultadoobligado(), testAntiguo.getCtResultadoobligado(), idUsuario,
				"CT_RESULTADOOBLIGADO", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtResultadoomision(), testAntiguo.getCtResultadoomision(), idUsuario,
				"CT_RESULTADOOMISION", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);

		valorNuevo = "";
		valorAnterior = "";

		if (testNuevo.getCfgUnidadesdemedida() != null) {
			int valorUnidad = testNuevo.getCfgUnidadesdemedida().getCumIdunidadmedida();
			CfgUnidadesdemedida unidadNueva = new CfgUnidadesdemedida();
			try {
				unidadNueva = cfgUnidadesdemedidaDAO.getUnidadesdemedidaById(valorUnidad);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = unidadNueva.getCumDescripcion();
		}

		if (testAntiguo.getCfgUnidadesdemedida() != null) {
			CfgUnidadesdemedida unidadAntigua = new CfgUnidadesdemedida();

			int valorAntiguo = testAntiguo.getCfgUnidadesdemedida().getCumIdunidadmedida();
			try {
				unidadAntigua = cfgUnidadesdemedidaDAO.getUnidadesdemedidaById(valorAntiguo);
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (unidadAntigua != null) {
				valorAnterior = unidadAntigua.getCumDescripcion();
			}
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CT_IDUNIDADMEDIDA", (int) testAntiguo.getCtIdtest(),
				"CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtDecimales() != null ? testNuevo.getCtDecimales().toString() : "",
				testAntiguo.getCtDecimales() != null ? testAntiguo.getCtDecimales().toString() : "", idUsuario,
				"CT_DECIMALES", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);

		valorNuevo = "";
		valorAnterior = "";

		if (testNuevo.getCfgTiposderesultado() != null) {
			int valorResultado = testNuevo.getCfgTiposderesultado().getCtrIdtiporesultado();
			CfgTiposderesultado resultadoNueva = new CfgTiposderesultado();
			try {
				resultadoNueva = cfgTiposderesultadoDAO.getTipoResultadoById(valorResultado);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = resultadoNueva.getCtrDescripcion();
		}

		if (testAntiguo.getCfgTiposderesultado() != null) {
			CfgTiposderesultado resultadoAntigua = new CfgTiposderesultado();

			int valorAntiguo = testAntiguo.getCfgTiposderesultado().getCtrIdtiporesultado();
			try {
				resultadoAntigua = cfgTiposderesultadoDAO.getTipoResultadoById(valorAntiguo);
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (resultadoAntigua != null) {
				valorAnterior = resultadoAntigua.getCtrDescripcion();
			}
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CT_IDTIPORESULTADO", (int) testAntiguo.getCtIdtest(),
				"CFG_TEST", idDescripcion);

		validarDatos(testNuevo.getCtCondicion(), testAntiguo.getCtCondicion(), idUsuario, "CT_CONDICION",
				(int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtOpcional(), testAntiguo.getCtOpcional(), idUsuario, "CT_OPCIONAL",
				(int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtTieneantecedentes(), testAntiguo.getCtTieneantecedentes(), idUsuario,
				"CT_TIENEANTECEDENTES", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtTestreporte() != null ? testNuevo.getCtTestreporte().toString() : "",
				testAntiguo.getCtTestreporte() != null ? testAntiguo.getCtTestreporte().toString() : "", idUsuario,
				"CT_TESTREPORTE", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtHost(), testAntiguo.getCtHost(), idUsuario, "CT_HOST",
				(int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtHostmicro(), testAntiguo.getCtHostmicro(), idUsuario, "CT_HOSTMICRO",
				(int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtSort() != null ? testNuevo.getCtSort().toString() : "",
				testAntiguo.getCtSort() != null ? testAntiguo.getCtSort().toString() : "", idUsuario, "CT_SORT",
				(int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtActivo(), testAntiguo.getCtActivo(), idUsuario, "CT_ACTIVO",
				(int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtTesthojatrabajo(), testAntiguo.getCtTesthojatrabajo(), idUsuario,
				"CT_TESTHOJATRABAJO", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtCodigoloinc(), testAntiguo.getCtCodigoloinc(), idUsuario, "CT_CODIGOLOINC",
				(int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtPlanillahistorica(), testAntiguo.getCtPlanillahistorica(), idUsuario,
				"CT_PLANILLAHISTORICA", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtVolumenmuestra() != null ? testNuevo.getCtVolumenmuestra().toString() : "",
				testAntiguo.getCtVolumenmuestra() != null ? testAntiguo.getCtVolumenmuestra().toString() : "",
				idUsuario, "CT_VOLUMENMUESTRA", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);

		// agregando CE_IDSECCION
		valorNuevo = "";
		valorAnterior = "";
		if (testNuevo.getCfgSecciones() != null) {
			Short valor = testNuevo.getCfgSecciones().shortValue();
			CfgSecciones seccionNueva = new CfgSecciones();
			try {
				seccionNueva = cfgSeccionesDAO.getSeccionById(valor);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = seccionNueva.getCsecDescripcion();
		}
		CfgSecciones seccionAntiguo = new CfgSecciones();
		try {
			seccionAntiguo = cfgSeccionesDAO.getSeccionById(testAntiguo.getCfgSecciones());
		} catch (BiosLISDAOException e1) {
			e1.printStackTrace();
		}
		if (seccionAntiguo != null) {
			valorAnterior = seccionAntiguo.getCsecDescripcion();
		}
		validarDatos(valorNuevo, valorAnterior, idUsuario, "CT_IDSECCION", (int) testAntiguo.getCtIdtest(), "CFG_TEST",
				idDescripcion);

		// agregando CE_IDGRUPOEXAMEN
		valorNuevo = "";
		valorAnterior = "";
		if (testNuevo.getCtIdgrupoexamenesfonasa() != null) {

			Short valor = testNuevo.getCtIdgrupoexamenesfonasa().shortValue();
			LdvGruposexamenes grupoNueva = new LdvGruposexamenes();
			try {
				grupoNueva = ldvGruposExamenesDAO.getGrupoExamenesByID(valor);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (grupoNueva != null) {
				valorNuevo = grupoNueva.getLdvgeDescripcion();
			}

		}
		LdvGruposexamenes grupoExamenAntiguo = new LdvGruposexamenes();
		if (testAntiguo.getCtIdgrupoexamenesfonasa() != null) {
			Short valor = testAntiguo.getCtIdgrupoexamenesfonasa().shortValue();
			try {
				grupoExamenAntiguo = ldvGruposExamenesDAO.getGrupoExamenesByID(valor);
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (grupoExamenAntiguo != null) {
				valorAnterior = grupoExamenAntiguo.getLdvgeDescripcion();
			}
		}

		validarDatos(valorNuevo, valorAnterior, idUsuario, "CT_IDGRUPOEXAMENESFONASA", (int) testAntiguo.getCtIdtest(),
				"CFG_TEST", idDescripcion);

		validarDatos(testNuevo.getCtValorinferior() != null ? testNuevo.getCtValorinferior().toString() : "",
				testAntiguo.getCtValorinferior() != null ? testAntiguo.getCtValorinferior().toString() : "", idUsuario,
				"CT_VALORINFERIOR", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtValorsuperior() != null ? testNuevo.getCtValorsuperior().toString() : "",
				testAntiguo.getCtValorsuperior() != null ? testAntiguo.getCtValorsuperior().toString() : "", idUsuario,
				"CT_VALORSUPERIOR", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtDeltacantidad() != null ? testNuevo.getCtDeltacantidad().toString() : "",
				testAntiguo.getCtDeltacantidad() != null ? testAntiguo.getCtDeltacantidad().toString() : "", idUsuario,
				"CT_DELTACANTIDAD", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtDeltaporcentaje() != null ? testNuevo.getCtDeltaporcentaje().toString() : "",
				testAntiguo.getCtDeltaporcentaje() != null ? testAntiguo.getCtDeltaporcentaje().toString() : "",
				idUsuario, "CT_DELTAPORCENTAJE", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtDiasevaluables() != null ? testNuevo.getCtDiasevaluables().toString() : "",
				testAntiguo.getCtDiasevaluables() != null ? testAntiguo.getCtDiasevaluables().toString() : "",
				idUsuario, "CT_DIASEVALUABLES", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);
		validarDatos(testNuevo.getCtEscultivo() != null ? testNuevo.getCtEscultivo().toString() : "",
				testAntiguo.getCtEscultivo() != null ? testAntiguo.getCtEscultivo().toString() : "", idUsuario,
				"CT_ESCULTIVO", (int) testAntiguo.getCtIdtest(), "CFG_TEST", idDescripcion);

		// agregando CT_IDBACGRUPOTEST
		valorNuevo = "";
		valorAnterior = "";
		if (testNuevo.getCfgBacgrupostest() != null) {

			Byte valor = testNuevo.getCfgBacgrupostest().getCbgtIdbacgrupotest();
			CfgBacgrupostest bacGrupoNueva = new CfgBacgrupostest();
			try {
				bacGrupoNueva = cfgBacgrupostestDAO.getBacGruposTestById(valor);
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (bacGrupoNueva != null) {
				valorNuevo = bacGrupoNueva.getCbgtDescripcion();
			}

		}
		CfgBacgrupostest bacGrupoExamenAntiguo = new CfgBacgrupostest();
		if (testAntiguo.getCfgBacgrupostest() != null) {
			Byte valor = testAntiguo.getCfgBacgrupostest().getCbgtIdbacgrupotest();
			try {
				bacGrupoExamenAntiguo = cfgBacgrupostestDAO.getBacGruposTestById(valor);
			} catch (BiosLISDAOException e1) {
				e1.printStackTrace();
			}
			if (bacGrupoExamenAntiguo != null) {
				valorAnterior = bacGrupoExamenAntiguo.getCbgtDescripcion();
			}
		}

		validarDatos(valorNuevo, valorAnterior, idUsuario, "CT_IDBACGRUPOTEST", (int) testAntiguo.getCtIdtest(),
				"CFG_TEST", idDescripcion);

	}

	public void compararTablasValoresDeReferencia(CfgValoresreferencia nuevo, CfgValoresreferencia antiguo,
			Long idUsuario, String idDescripcion) {

		// agregando CT_IDBACGRUPOTEST
		String valorNuevo = "";
		String valorAnterior = "";
		if (nuevo.getCvrSexo().equals("A")) {
			valorNuevo = "AMBOS";
		} else if (nuevo.getCvrSexo().equals("F")) {
			valorNuevo = "FEMENINO";
		} else {
			valorNuevo = "MASCULINO";
		}
		if (antiguo.getCvrSexo().equals("A")) {
			valorAnterior = "AMBOS";
		} else if (antiguo.getCvrSexo().equals("F")) {
			valorAnterior = "FEMENINO";
		} else {
			valorAnterior = "MASCULINO";
		}

		validarDatos(valorNuevo, valorAnterior, idUsuario, "CVR_SEXO", (int) nuevo.getCvrIdtest(),
				"CFG_VALORESREFERENCIA", idDescripcion);

		validarDatos(nuevo.getCvrAnosdesde().toString(), antiguo.getCvrAnosdesde().toString(), idUsuario,
				"CVR_ANOSDESDE", (int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);
		validarDatos(nuevo.getCvrAnoshasta().toString(), antiguo.getCvrAnoshasta().toString(), idUsuario,
				"CVR_ANOSHASTA", (int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);
		validarDatos(nuevo.getCvrMesesdesde().toString(), antiguo.getCvrMesesdesde().toString(), idUsuario,
				"CVR_MESESDESDE", (int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);
		validarDatos(nuevo.getCvrMeseshasta().toString(), antiguo.getCvrMeseshasta().toString(), idUsuario,
				"CVR_MESESHASTA", (int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);
		validarDatos(nuevo.getCvrDiasdesde().toString(), antiguo.getCvrDiasdesde().toString(), idUsuario,
				"CVR_DIASDESDE", (int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);
		validarDatos(nuevo.getCvrDiashasta().toString(), antiguo.getCvrDiashasta().toString(), idUsuario,
				"CVR_DIASHASTA", (int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);
		validarDatos(nuevo.getCvrValorcriticobajo() != null ? nuevo.getCvrValorcriticobajo().toString() : "",
				antiguo.getCvrValorcriticobajo() != null ? antiguo.getCvrValorcriticobajo().toString() : "", idUsuario,
				"CVR_VALORCRITICOBAJO", (int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);
		validarDatos(nuevo.getCvrValorbajo() != null ? nuevo.getCvrValorbajo().toString() : "",
				antiguo.getCvrValorbajo() != null ? antiguo.getCvrValorbajo().toString() : "", idUsuario,
				"CVR_VALORBAJO", (int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);
		validarDatos(nuevo.getCvrValoralto() != null ? nuevo.getCvrValoralto().toString() : "",
				antiguo.getCvrValoralto() != null ? antiguo.getCvrValoralto().toString() : "", idUsuario,
				"CVR_VALORALTO", (int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);
		validarDatos(nuevo.getCvrValorcriticoalto() != null ? nuevo.getCvrValorcriticoalto().toString() : "",
				antiguo.getCvrValorcriticoalto() != null ? antiguo.getCvrValorcriticoalto().toString() : "", idUsuario,
				"CVR_VALORCRITICOALTO", (int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);
		validarDatos(nuevo.getCvrValortexto(), antiguo.getCvrValortexto(), idUsuario, "CVR_VALORTEXTO",
				(int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);

		valorNuevo = "";
		if (nuevo.getCfgMetodos() != null) {
			CfgMetodos metodo = new CfgMetodos();
			try {
				metodo = cfgMetodosDAO.getMetodoById(nuevo.getCfgMetodos().getCmetIdmetodo());
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			valorNuevo = metodo.getCmetDescripcion();
		}

		validarDatos(valorNuevo, antiguo.getCfgMetodos().getCmetDescripcion(), idUsuario, "CVR_IDMETODO",
				(int) nuevo.getCvrIdtest(), "CFG_VALORESREFERENCIA", idDescripcion);

	}

	public void compararTablasMuestras(CfgMuestras nuevo, CfgMuestras antiguo, Long idUsuario, String idDescripcion) {
		validarDatos(nuevo.getCmueDescripcion(), antiguo.getCmueDescripcion(), idUsuario, "CMUE_DESCRIPCION",
				(int) nuevo.getCmueIdtipomuestra(), "CFG_MUESTRAS", idDescripcion);
		validarDatos(nuevo.getCmueDescripcionabreviada(), antiguo.getCmueDescripcionabreviada(), idUsuario,
				"CMUE_DESCRIPCIONABREVIADA", (int) nuevo.getCmueIdtipomuestra(), "CFG_MUESTRAS", idDescripcion);
		validarDatos(nuevo.getCmuePrefijotipomuestra(), antiguo.getCmuePrefijotipomuestra(), idUsuario, "CMUE_PREFIJO",
				(int) nuevo.getCmueIdtipomuestra(), "CFG_MUESTRAS", idDescripcion);
		validarDatos(nuevo.getCmueActivo(), antiguo.getCmueActivo(), idUsuario, "CMUE_ACTIVO",
				(int) nuevo.getCmueIdtipomuestra(), "CFG_MUESTRAS", idDescripcion);
		validarDatos(nuevo.getCmueSort() != null ? nuevo.getCmueSort().toString() : "",
				antiguo.getCmueSort() != null ? antiguo.getCmueSort().toString() : "", idUsuario, "CMUE_SORT",
				(int) nuevo.getCmueIdtipomuestra(), "CFG_MUESTRAS", idDescripcion);
		validarDatos(nuevo.getCmueHost(), antiguo.getCmueHost(), idUsuario, "CMUE_HOST",
				(int) nuevo.getCmueIdtipomuestra(), "CFG_MUESTRAS", idDescripcion);
		validarDatos(nuevo.getCmueHostmicro(), antiguo.getCmueHostmicro(), idUsuario, "CMUE_HOSTMICRO",
				(int) nuevo.getCmueIdtipomuestra(), "CFG_MUESTRAS", idDescripcion);
		validarDatos(nuevo.getCmueEsmicrobiologia(), antiguo.getCmueEsmicrobiologia(), idUsuario,
				"CMUE_ESMICROBIOLOGIA", (int) nuevo.getCmueIdtipomuestra(), "CFG_MUESTRAS", idDescripcion);
		validarDatos(nuevo.getCmueEstipocurva(), antiguo.getCmueEstipocurva(), idUsuario, "CMUE_ESTIPOCURVA",
				(int) nuevo.getCmueIdtipomuestra(), "CFG_MUESTRAS", idDescripcion);
		validarDatos(nuevo.getCmueCurvasminutos() != null ? nuevo.getCmueCurvasminutos().toString() : "",
				antiguo.getCmueCurvasminutos() != null ? antiguo.getCmueCurvasminutos().toString() : "", idUsuario,
				"CMUE_CURVASMINUTOS", (int) nuevo.getCmueIdtipomuestra(), "CFG_MUESTRAS", idDescripcion);
		validarDatos(nuevo.getCmueColor(), antiguo.getCmueColor(), idUsuario, "CMUE_COLOR",
				(int) nuevo.getCmueIdtipomuestra(), "CFG_MUESTRAS", idDescripcion);

	}

	public void validarDatos(String valorNuevo, String valorAntiguo, Long idUsuario, String nombreCampo, int idTabla,
			String tabla, String idDescaripcion) {
		System.out.println(valorNuevo + " ***** " + valorAntiguo);
		LogCfgtablas logCfg = new LogCfgtablas();

		logCfg.setLctIdusuario(idUsuario.intValue());
		logCfg.setLctNombretabla(tabla);
		logCfg.setCfgAccionesdatos(2);
		logCfg.setLctIdtabla(idTabla);
		logCfg.setLctDescripcion(idDescaripcion);
		List<String> valores = new ArrayList<String>();
		valores.add(0, "");
		valores.add(1, "");
		if (valorNuevo != null && valorAntiguo != null) {
			if (!valorNuevo.toUpperCase().equals(valorAntiguo.toUpperCase())) {
				valores.set(0, valorNuevo);
				valores.set(1, valorAntiguo);
			}
		} else {
			if (valorNuevo == null) {
				valores.set(1, valorAntiguo);
			} else {
				if (valorAntiguo == null) {
					valores.set(0, valorNuevo);
				}
			}

		}

		if (!valores.get(0).equals(valores.get(1))) {
			if ((valores.get(0) != "" && valores.get(0) != null) || (valores.get(1) != "" && valores.get(1) != null)) {
				logCfg.setLctValornuevo(valores.get(0));
				logCfg.setLctValoranterior(valores.get(1));
				logCfg.setLctNombrecampo(nombreCampo);
				try {
					this.insertLogTablas(logCfg);
				} catch (BiosLISDAOException e) {
					e.printStackTrace();
				}

			}
		}

	}

	public String caseTablas(String cambio, String nuevoCampo) {
		switch (nuevoCampo) {
		// ejemplo de lo que se realiza aca, si tiene algun id, se buscar la descripcion
		case "IDTIPOMUESTRA": {
			CfgMuestras muestra = new CfgMuestras();
			// if(cambio != null) {
			try {
				muestra = cfgMuestrasDAO.getMuestraById(Short.parseShort(cambio));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (muestra != null) {
				cambio = muestra.getCmueDescripcionabreviada();
			}

			break;
		}
		case "IDSECCION": {
			CfgSecciones seccionNueva = new CfgSecciones();
			try {
				seccionNueva = cfgSeccionesDAO.getSeccionById(Short.parseShort(cambio));
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (seccionNueva != null) {
				cambio = seccionNueva.getCsecDescripcion();
			}

			break;
		}
		case "IDSEXO": {
			LdvSexo sexoNueva = new LdvSexo();
			LdvSexoDAO ldv = new LdvSexoDAO();
			try {
				sexoNueva = ldv.getSexoById(Integer.parseInt(cambio));
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (sexoNueva != null) {
				cambio = sexoNueva.getLdvsDescripcion();
			}

			break;
		}
		case "IDESPECIALIDADMEDICA": {
			LdvEspecialidadesmedicas especialidadNueva = new LdvEspecialidadesmedicas();
			LdvEspecialidadesMedicasDAO lvd = new LdvEspecialidadesMedicasDAO();
			try {
				especialidadNueva = lvd.getEspecialidadesMedicasById(Integer.parseInt(cambio));
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (especialidadNueva != null) {
				cambio = especialidadNueva.getLdvemDescripcion();
			}

			break;
		}
		case "IDINSTITUCIONDESALUD": {
			CfgInstitucionesdesalud instituciondNueva = new CfgInstitucionesdesalud();
			CfgInstitucionesDeSaludDAO lvd = new CfgInstitucionesDeSaludDAO();
			try {
				instituciondNueva = lvd.getInstitutById(Integer.parseInt(cambio));
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (instituciondNueva != null) {
				cambio = instituciondNueva.getCidsDescripcion();
			}

			break;
		}
		case "IDCENTRODESALUD": {
			CfgCentrosdesalud centroNueva = new CfgCentrosdesalud();
			CfgCentrosDeSaludDAO centro = new CfgCentrosDeSaludDAO();
			try {
				centroNueva = centro.getCentrosDeSaludById(Short.parseShort(cambio));
			} catch (BiosLISDAOException e) {
				e.printStackTrace();
			}
			if (centroNueva != null) {
				cambio = centroNueva.getCcdsDescripcion();
			}

			break;
		}
		default: {
			logger.debug("no se encontro ningun caso ");
		}
		}
		return cambio;

	}

}
