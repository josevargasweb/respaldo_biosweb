/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.cfg.cache.CfgCacheCfgSecciones;
import com.grupobios.bioslis.cfg.cache.CfgCacheSexo;
import com.grupobios.bioslis.cfg.cache.CfgProcedenciasAbsDao;
import com.grupobios.bioslis.cfg.cache.CfgTipoAtencionAbsDao;
import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.DaoHelper;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.config.SpringContext;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.DatosFichasExamenesDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.ExamenPacienteDTO;
import com.grupobios.bioslis.dto.FichaFiltroDTO;
import com.grupobios.bioslis.dto.OrdenExamenCapturaResultadoDTO;
import com.grupobios.bioslis.dto.OrdenFullDTO;
import com.grupobios.bioslis.dto.OrdenInformeResultadoDTO;
import com.grupobios.bioslis.dto.OrdenesTMDTO;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasexamenes;
import com.grupobios.bioslis.entity.DatosFichasexamenesId;
import com.grupobios.bioslis.entity.LogEventosfichas;
import com.grupobios.bioslis.microbiologia.dao.BiosLisDAO;
import com.grupobios.common.Edad;

/**
 *
 * @author Jan Perkov
 */
public class DatosFichas_DAO implements BiosLisDAO {

	private static final Logger logger = LogManager.getLogger(DatosFichas_DAO.class);

	/************************************
	 * Desde aquí no se ocupa
	 **************************************/

	public DatosFichas getOrdenxID(int nOrden) throws BiosLISDAOException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		DatosFichas paciente = null;

		try {
			sesion.beginTransaction();
			Query query = sesion.createQuery("select med " + "from com.grupobios.bioslis.entity.DatosFichas med "
					+ "where med.dfNorden = :nOrden");
			query.setParameter("nOrden", nOrden);

			List<DatosFichas> listaMedicos = query.list();
			sesion.getTransaction().commit();
			sesion.close();
			paciente = listaMedicos.get(0);

		} catch (Exception e) {
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}
		return paciente;
	}

	public boolean verificarMasDeUnaOrden(int id) throws BiosLISDAOException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<DatosFichas> lista = null;
		try {
			// sumar un dia a fecha ingresada
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			String today = sdf.format(c.getTime());
			c.add(Calendar.DAY_OF_MONTH, 1);
			String newDate = sdf.format(c.getTime());

			sesion.beginTransaction();
			Query query = sesion
					.createQuery("select med.datosPacientes from com.grupobios.bioslis.entity.DatosFichas med "
							+ "where med.dfFechaorden  BETWEEN '" + today + "' and '" + newDate + "' AND "
							+ "med.datosPacientes = :id")
					.setMaxResults(1);
			query.setParameter("id", id);
			lista = query.list();
			sesion.getTransaction().commit();
			sesion.close();
		} catch (Exception e) {
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lista.isEmpty();

	}

	public boolean verificarExamenxPaciente(int idPaciente, int idExamen) throws BiosLISDAOException {
		CfgExamenesDAO exadao = new CfgExamenesDAO();
		int diasValidar = exadao.diasValidacionExamen(idExamen);

		Session sesion = HibernateUtil.getSessionFactory().openSession();

		try {
			sesion.beginTransaction();
			Query query = sesion.createSQLQuery("SELECT a.df_fechaorden from bioslis.datos_fichas a "
					+ "INNER JOIN bioslis.datos_fichasexamenes b " + "ON a.df_norden = b.dfe_norden "
					+ "where a.df_idpaciente = :idPaciente AND b.dfe_idexamen = :idExamen "
					+ "AND a.df_fechaorden > (sysdate-:diasValidar)");
			query.setParameter("idPaciente", idPaciente);
			query.setParameter("idExamen", idExamen);
			query.setParameter("diasValidar", diasValidar);
			List<DatosFichas> lista = query.list();
			sesion.getTransaction().commit();
			sesion.close();
			return lista.isEmpty();
		} catch (Exception e) {
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}

	}

	public List<DatosFichas> OrdenxPaciente(int idPaciente) throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<DatosFichas> lista = null;

		try {

			sesion.beginTransaction();
			Query query = sesion.createQuery("select list from com.grupobios.bioslis.entity.DatosFichas list "
					+ "where list.datosPacientes = :idPaciente order by list.dfFechaorden asc");
			query.setParameter("idPaciente", idPaciente);
			lista = query.list();
			sesion.getTransaction().commit();
			sesion.close();
		} catch (Exception e) {
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}
		return lista;

	}

	public void updateOrdenxPaciente(int idPaciente1, int idPaciente2, String ordenes) throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();

		try {

			sesion.beginTransaction();
			Query query = sesion.createSQLQuery("update datos_fichas " + "set df_idpaciente = :idPaciente2 "
					+ "where df_idpaciente = :idPaciente1 " + "AND df_norden IN (" + ordenes + ")");
			query.setParameter("idPaciente1", idPaciente1);
			query.setParameter("idPaciente2", idPaciente2);
			query.executeUpdate();
			sesion.getTransaction().commit();
		} catch (Exception e) {
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}

	}

	public List<Object[]> selectOrdenNombreApellidoFechas(String nombre, String apellido, String fechaInicio,
			String fechaFin) throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<Object[]> lista = null;
		try {

			sesion.beginTransaction();
			Query query = sesion.createSQLQuery(
					"select datos_fichas.df_fechaorden,datos_fichas.df_norden,CONCAT(CONCAT(datos_pacientes.dp_nombres,' '),datos_pacientes.dp_primerapellido),ldv_tiposdocumentos.ldvtd_descripcion,datos_pacientes.dp_nrodocumento, cfg_tipoatencion.cta_descripcion,cfg_procedencias.cp_descripcion,cfg_servicios.cs_descripcion "
							+ " ,datos_fichas.df_idmedico,cfg_localizaciones.cl_idservicio,cfg_localizaciones.cl_idsalaservicio,cfg_localizaciones.cl_idcamasala,datos_fichas.df_enviarresultadosemail"
							+ " ,datos_fichas.df_iddiagnostico,datos_fichas.df_idconvenio,datos_fichas.df_idprioridadatencion,datos_fichas.df_observacion"
							+ " from datos_fichas" + " INNER JOIN datos_pacientes ON"
							+ "    datos_pacientes.dp_idpaciente = datos_fichas.df_idpaciente"
							+ " INNER JOIN ldv_tiposdocumentos ON"
							+ "    ldv_tiposdocumentos.ldvtd_idtipodocumento = datos_pacientes.dp_idtipodocumento"
							+ " INNER JOIN cfg_tipoatencion ON"
							+ "   cfg_tipoatencion.cta_idtipoatencion = datos_fichas.df_idtipoatencion"
							+ " INNER JOIN cfg_procedencias ON"
							+ "  cfg_procedencias.cp_idprocedencia = datos_fichas.df_idprocedencia"
							+ " INNER JOIN cfg_localizaciones ON"
							+ "  cfg_localizaciones.cl_idlocalizacion = datos_fichas.df_idlocalizacion"
							+ " INNER JOIN cfg_servicios ON"
							+ " cfg_servicios.cs_idservicio = cfg_localizaciones.cl_idservicio"
							+ " where datos_pacientes.dp_nombres like '" + nombre.toUpperCase() + "%' "
							+ " and datos_pacientes.dp_primerapellido like '" + apellido.toUpperCase() + "%'"
							+ " and datos_fichas.df_fechaorden BETWEEN '" + fechaInicio + "' and to_date('" + fechaFin
							+ " 23:59:59', 'dd/mm/yy HH24:mi:ss')" + " order by datos_fichas.df_fechaorden asc");

			lista = query.list();
			sesion.getTransaction().commit();
			sesion.close();
		} catch (Exception e) {
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lista;

	}

	public void updateTMClick(int nOrden, String ipEquipo) throws BiosLISDAOException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			Boolean validadorTM = this.validarTMClick(nOrden);
			String tmClick = null;
			if (validadorTM) {
				tmClick = "N";

				sesion.beginTransaction();
				Query query = sesion.createSQLQuery(
						"update datos_fichas " + "set df_tmclick = :tmClick " + "where df_norden = :nOrden ");
				query.setParameter("nOrden", nOrden);
				query.setParameter("tmClick", tmClick);
				query.executeUpdate();
				sesion.getTransaction().commit();
			} else {
				tmClick = "S";
				sesion.beginTransaction();
				Query query = sesion.createSQLQuery(
						"update datos_fichas " + "set df_tmclick = :tmClick " + "where df_norden = :nOrden ");
				query.setParameter("nOrden", nOrden);
				query.setParameter("tmClick", tmClick);
				query.executeUpdate();
				sesion.getTransaction().commit();
				sesion.close();
			}
			LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
			LogEventosfichas lef = new LogEventosfichas();
			Date date = new Date(System.currentTimeMillis());
			// SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			// Date fecha = new Date();
			lef.setLefIpestacion(ipEquipo);
			lef.setDatosFichas(nOrden);
			lef.setCfgEventos(2);
			lef.setLefFechaorden(date);
			lef.setLefNombretabla("DATOS_FICHAS");
			lef.setLefNombrecampo("DF_TMCLICK");
			lef.setLefFecharegistro(date);
			lef.setLefIdusuario(1);
			lefDAO.insertLogEventosFichas(lef);

		} catch (Exception e) {
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}

	}

	/************************************
	 * Hast aquí lo de arriba no se ocupa revisar y borrar
	 **************************************/

	public Boolean validarTMClick(int nOrden) {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			Boolean validadorFinal;
			sesion.beginTransaction();
			Query query = sesion.createQuery("select med.dfTmclick from com.grupobios.bioslis.entity.DatosFichas med "
					+ " where med.dfNorden = :nOrden" + " order by med.dfNorden desc").setMaxResults(1);
			query.setParameter("nOrden", nOrden);

			String validador;
			try {
				validador = ((String) query.list().get(0));
			} catch (Exception e) {
				validador = "F";
			}
			sesion.getTransaction().commit();
			sesion.close();
			if ("S".equals(validador)) {
				validadorFinal = true;
			} else {
				validadorFinal = false;
			}

			return validadorFinal;
		} catch (Exception e) {
			throw e;
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
	}

	public void volverANormlidad() throws BiosLISDAOException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			sesion.beginTransaction();
			Query query = sesion.createSQLQuery("UPDATE DATOS_FICHAS SET DF_TMCLICK = 'N'");
			query.executeUpdate();
			sesion.getTransaction().commit();
			sesion.close();
		} catch (Exception e) {
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}

	}

	public void updateEstadoUrgente(int nOrden) throws BiosLISDAOException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			sesion.beginTransaction();
			Query query = sesion
					.createSQLQuery("update datos_fichas set df_idprioridadatencion = 12 where df_norden = :nOrden ");
			query.setParameter("nOrden", nOrden);
			query.executeUpdate();
			sesion.getTransaction().commit();
			sesion.close();

		} catch (Exception e) {
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}

	}

	public List<DatosFichas> getOrdenes() throws BiosLISDAOException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<DatosFichas> listaOrdenes = null;
		try {
			sesion.beginTransaction();
			Query query = sesion.createQuery("select df " + "from com.grupobios.bioslis.entity.DatosFichas df");
			listaOrdenes = query.list();
			sesion.getTransaction().commit();
			sesion.close();
		} catch (Exception e) {
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return listaOrdenes;
	}

	public List<Object[]> selectOrdenParaInforme(int nOrden) throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();

		try {

			Query query = sesion.createSQLQuery(
					"select CONCAT(CONCAT(CONCAT(CONCAT(dp.dp_nombres,' '),dp.dp_primerapellido),' '),dp.dp_segundoapellido) datosPacientes,"
							+ "CONCAT(CONCAT(med.cm_nombres,' '),med.cm_primerapellido) dfIdmedico,"
							+ "dp.dp_run,dp.dp_fnacimiento," + "pro.cp_descripcion," + "serv.cs_descripcion "
							+ "from datos_fichas df "
							+ "INNER JOIN datos_pacientes dp ON  dp.dp_idpaciente = df.df_idpaciente "
							+ "INNER JOIN cfg_medicos med ON med.cm_idmedico = df.df_idmedico "
							+ "INNER JOIN cfg_procedencias pro ON  pro.cp_idprocedencia = df.df_idprocedencia "
							+ "INNER JOIN cfg_localizaciones loc ON loc.cl_idlocalizacion = df.df_idlocalizacion "
							+ "INNER JOIN cfg_servicios serv ON  serv.cs_idservicio = loc.cl_idservicio "
							+ "where df_norden = 121");
			List<Object[]> lista = query.list();

			sesion.close();
			return lista;
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}

	}

	private static String getAllQuery = "select orden from DatosFichasexamenes orden";

	public List<DatosFichasexamenes> getAll() throws BiosLISDAOException {

		List<DatosFichasexamenes> lst = null;
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {

			Criteria cr = sesion.createCriteria(DatosFichasexamenes.class);
			lst = cr.list();
			sesion.close();
		} catch (HibernateException e) {
			logger.error(e.getMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}

		return lst;

	}

	// CAMBIO REALIZADOS POR CRISTIAN 14-11 SE VA A BUSCAR CONDICION DE FILTRO A
	// DAOHELPER
	@SuppressWarnings("unchecked")
	public List<OrdenInformeResultadoDTO> getOrdenesInformeResultados(FichaFiltroDTO ffDto)
			throws BiosLISException, BiosLISDAOException {

		String sCond = "";
		BCFichaFiltroDTO bcFichaFiltroDTO = new BCFichaFiltroDTO();
		bcFichaFiltroDTO.setBo_nOrdenIni(ffDto.getnOrden());
		bcFichaFiltroDTO.setBo_fIni(ffDto.getfIni());
		bcFichaFiltroDTO.setBo_fFin(ffDto.getfFin());
		bcFichaFiltroDTO.setBo_nombre(ffDto.getNombre());
		bcFichaFiltroDTO.setBo_apellido(ffDto.getApellido());
		bcFichaFiltroDTO.setBo_tipoDocumento(ffDto.getTipoDocumento());
		bcFichaFiltroDTO.setBo_nroDocumento(ffDto.getNroDocumento());
		bcFichaFiltroDTO.setBo_tipoAtencion(ffDto.getTipoAtencion());
		bcFichaFiltroDTO.setBo_localizacion(ffDto.getLocalizacion());
		List<OrdenInformeResultadoDTO> lst = null;
		Session sesion = null;
		Query qry = null;
		try {
			sesion = HibernateUtil.getSessionFactory().openSession();

			DaoHelper dh = new DaoHelper();

			sCond = dh.genSqlCondicionBOFull(bcFichaFiltroDTO);
			sCond = sCond + "order by DF_NORDEN DESC";

			logger.debug("Condiciones consulta {}", sCond);

			// Se agrega condicion de que no estén los anulados

			sesion = HibernateUtil.getSessionFactory().openSession();
			qry = sesion.createSQLQuery(_sqlOrdenesInformeResultados + sCond)
					.setResultTransformer(Transformers.aliasToBean(OrdenInformeResultadoDTO.class));

			if (ffDto.getApellido() != null && !ffDto.getApellido().trim().isEmpty()) {
				qry.setParameter("apellido", ffDto.getApellido().concat("%"));
			}
			if (ffDto.getNombre() != null && !ffDto.getNombre().trim().isEmpty()) {
				qry.setParameter("nombre", ffDto.getNombre().concat("%"));
			}
			if (ffDto.getnOrden() != null) {
				qry.setParameter("nOrdenIni", ffDto.getnOrden());
			} else {
				if (ffDto.getfFin() != null && !ffDto.getfFin().trim().isEmpty()) {
					qry.setParameter("ffin", ffDto.getfFin());
				}
				if (ffDto.getfIni() != null && !ffDto.getfIni().trim().isEmpty()) {
					qry.setParameter("fini", ffDto.getfIni());
				}
			}

			if (ffDto.getTipoDocumento() != null && ffDto.getTipoDocumento() != -1) {
				qry.setParameter("tipoDoc", ffDto.getTipoDocumento());
			}
			if (ffDto.getNroDocumento() != null && !ffDto.getNroDocumento().trim().isEmpty()) {
				qry.setParameter("nDoc", ffDto.getNroDocumento());
			}
			if (ffDto.getTipoAtencion() != null && ffDto.getTipoAtencion() != -1) {
				qry.setParameter("tipoAtencion", ffDto.getTipoAtencion());
			}
			if (ffDto.getLocalizacion() != null && ffDto.getLocalizacion() != -1) {
				qry.setParameter("idLocalizacion", ffDto.getLocalizacion());
			}

			if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1) {

				qry.setParameter("idExamen", bcFichaFiltroDTO.getBo_examen());
			}
			lst = (List<OrdenInformeResultadoDTO>) qry.list();
		} catch (HibernateException he) {
			logger.error("No se pudo recuperar ordenes {}", he.getMessage());
			throw new BiosLISDAOException(sCond);
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}

		}
		return lst;

	}

	public <T, U> List<T> getByFilter(U u) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
		// Tools | Templates.
	}

	public List<ExamenOrdenDTO> getExamenesOrden() {
		List<ExamenOrdenDTO> lst = null;
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {

			Query qry = sesion.createSQLQuery(sqlExamenesOrden)
					.setResultTransformer(Transformers.aliasToBean(ExamenOrdenDTO.class));

			lst = (List<ExamenOrdenDTO>) qry.list();

			sesion.close();
		} catch (HibernateException he) {
			logger.error("No se pudo recuperar examens de orden {}", he.getMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lst;
	}

	public List<ExamenOrdenDTO> getExamenesOrden(Long nroOrden) {

		Session sesion = null;
		List<ExamenOrdenDTO> lst = null;

		try {
			sesion = HibernateUtil.getSessionFactory().openSession();
			Query qry = sesion.createSQLQuery(sqlExamenesOrden)
					.setResultTransformer(Transformers.aliasToBean(ExamenOrdenDTO.class));
			qry.setLong("nOrden", nroOrden);
			lst = (List<ExamenOrdenDTO>) qry.list();
			sesion.close();
		} catch (HibernateException he) {
			logger.error("No se pudo recuperar examens de orden {}", he.getMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}

		return lst;

	}

	// CAMBIO REALIZADOS POR CRISTIAN 14-11 SE VA A BUSCAR CONDICION DE FILTRO A
	// DAOHELPER
	public List<OrdenExamenCapturaResultadoDTO> getOrdenesConSeccionesCapturaResultados(FichaFiltroDTO ffDto)
			throws BiosLISException, BiosLISDAOException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<OrdenExamenCapturaResultadoDTO> lst = null;
		try {

			String sCond = "";
			BCFichaFiltroDTO bcFichaFiltroDTO = new BCFichaFiltroDTO();
			bcFichaFiltroDTO.setBo_nOrdenIni(ffDto.getnOrden());
			bcFichaFiltroDTO.setBo_fIni(ffDto.getfIni());
			bcFichaFiltroDTO.setBo_fFin(ffDto.getfFin());
			bcFichaFiltroDTO.setBo_nombre(ffDto.getNombre());
			bcFichaFiltroDTO.setBo_apellido(ffDto.getApellido());
			bcFichaFiltroDTO.setBo_tipoDocumento(ffDto.getTipoDocumento());
			bcFichaFiltroDTO.setBo_nroDocumento(ffDto.getNroDocumento());
			bcFichaFiltroDTO.setBo_tipoAtencion(ffDto.getTipoAtencion());
			bcFichaFiltroDTO.setBo_localizacion(ffDto.getLocalizacion());
			DaoHelper dh = new DaoHelper();

			try {
				sCond = dh.genSqlCondicionBOFull(bcFichaFiltroDTO);

			} catch (BiosLISException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * if (ffDto.getApellido() != null && !ffDto.getApellido().trim().equals("")) {
			 * sCond += " ( UPPER(dp.DP_PRIMERAPELLIDO) like UPPER(:apellido) OR " +
			 * " UPPER(dp.DP_SEGUNDOAPELLIDO) like UPPER(:apellido)) AND "; } if
			 * (ffDto.getNombre() != null && !ffDto.getNombre().trim().isEmpty()) { sCond +=
			 * " UPPER(dp.DP_NOMBRES) like UPPER(:nombre) AND "; } if (ffDto.getfFin() !=
			 * null && !ffDto.getfFin().trim().isEmpty()) { sCond +=
			 * " df.DF_FECHAORDEN <= TO_DATE(:ffin,'DD-MM-YYYY HH24:MI:SS') AND "; } if
			 * (ffDto.getfIni() != null && !ffDto.getfIni().trim().isEmpty()) {
			 * 
			 * sCond += " df.DF_FECHAORDEN >= TO_DATE(:fini,'DD-MM-YYYY HH24:MI:SS') AND ";
			 * } if (ffDto.getnOrden() != null) { sCond += " df.DF_NORDEN = :nOrden AND "; }
			 * if (ffDto.getTipoDocumento() != null && ffDto.getTipoDocumento() != -1) {
			 * sCond += " dp.DP_IDTIPODOCUMENTO = :tipoDoc AND "; } if
			 * (ffDto.getNroDocumento() != null &&
			 * !ffDto.getNroDocumento().trim().isEmpty()) { sCond +=
			 * " dp.DP_NRODOCUMENTO = :nDoc  AND "; }
			 * 
			 * if (ffDto.getTipoAtencion() != null && ffDto.getTipoAtencion() != -1) { sCond
			 * += " df.DF_IDTIPOATENCION = :tipoAtencion AND "; } if
			 * (ffDto.getLocalizacion() != null && ffDto.getLocalizacion() != -1) { sCond +=
			 * " df.DF_IDLOCALIZACION = :idLocalizacion AND "; }
			 * 
			 * int pos = sCond.lastIndexOf("AND");
			 * 
			 * if (pos != -1) { sCond = sCond.substring(0, pos); sCond = " WHERE " + sCond;
			 * }
			 */
			String sCondRecepcionMuestra = "";

			if (ffDto.getfFin() != null && !ffDto.getfFin().trim().isEmpty()) {
				sCondRecepcionMuestra += " dfm.DFM_FECHARM <= TO_DATE(:ffin,'DD-MM-YYYY HH24:MI:SS') AND ";
			}
			if (ffDto.getfIni() != null && !ffDto.getfIni().trim().isEmpty()) {

				sCondRecepcionMuestra += " dfm.DFM_FECHARM >= TO_DATE(:fini,'DD-MM-YYYY HH24:MI:SS') AND  ";
			}

			int pos = sCondRecepcionMuestra.lastIndexOf("AND");

			if (pos != -1) {
				sCondRecepcionMuestra = sCondRecepcionMuestra.substring(0, pos);
			}

			if (!sCondRecepcionMuestra.trim().equals("")) {
				sCondRecepcionMuestra = " OR df.DF_NORDEN IN ( SELECT DISTINCT dfm.DFM_NORDEN "
						+ "  FROM BIOSLIS.DATOS_FICHASMUESTRAS dfm WHERE  " + sCondRecepcionMuestra + "  )";
			}

			logger.debug("Condiciones consulta {}", sCond);
			logger.debug("Condiciones recepcion {}", sCondRecepcionMuestra);
			logger.debug("\n SQL {}", _sqlOrdenesConSeccionesCapturaResultados + sCond + sCondRecepcionMuestra
					+ "ORDER BY dfe.DFE_EXAMENESURGENTE DESC");

			Query qry = sesion.createSQLQuery(_sqlOrdenesConSeccionesCapturaResultados + sCond + sCondRecepcionMuestra)
					.setResultTransformer(Transformers.aliasToBean(OrdenExamenCapturaResultadoDTO.class));

			if (ffDto.getApellido() != null && !ffDto.getApellido().trim().isEmpty()) {
				qry.setParameter("apellido", ffDto.getApellido().concat("%"));
			}
			if (ffDto.getNombre() != null && !ffDto.getNombre().trim().isEmpty()) {
				qry.setParameter("nombre", ffDto.getNombre().concat("%"));
			}
			if (ffDto.getfFin() != null && !ffDto.getfFin().trim().isEmpty()) {
				qry.setParameter("ffin", ffDto.getfFin());
			}
			if (ffDto.getfIni() != null && !ffDto.getfIni().trim().isEmpty()) {
				qry.setParameter("fini", ffDto.getfIni());
			}
			if (ffDto.getnOrden() != null) {
				qry.setParameter("nOrden", ffDto.getnOrden());
			}
			if (ffDto.getTipoDocumento() != null && ffDto.getTipoDocumento() != -1) {
				qry.setParameter("tipoDoc", ffDto.getTipoDocumento());
			}
			if (ffDto.getNroDocumento() != null && !ffDto.getNroDocumento().trim().isEmpty()) {
				qry.setParameter("nDoc", ffDto.getNroDocumento());
			}
			if (ffDto.getTipoAtencion() != null && ffDto.getTipoAtencion() != -1) {
				qry.setParameter("tipoAtencion", ffDto.getTipoAtencion());
			}

			if (ffDto.getLocalizacion() != null && ffDto.getLocalizacion() != -1) {

				qry.setParameter("idLocalizacion", ffDto.getLocalizacion());
			}

			if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1) {

				qry.setParameter("idExamen", bcFichaFiltroDTO.getBo_examen());
			}

			lst = (List<OrdenExamenCapturaResultadoDTO>) qry.list();

			for (OrdenExamenCapturaResultadoDTO oecr : lst) {

				if (oecr.getSDP_FNACIMIENTO() != null) {
					oecr.setSDP_FNACIMIENTO(Edad.getEdadActual(oecr.getSDP_FNACIMIENTO()).toString());
				}

			}
		} catch (BiosLISException e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lst;

	}

	public List<OrdenInformeResultadoDTO> getOrdenesCapturaResultados(FichaFiltroDTO ffDto)
			throws BiosLISException, BiosLISDAOException {

		List<OrdenExamenCapturaResultadoDTO> lstOrdenesConSecciones = this
				.getOrdenesConSeccionesCapturaResultados(ffDto);

		Map<OrdenInformeResultadoDTO, List<BigDecimal>> hm = new HashMap<OrdenInformeResultadoDTO, List<BigDecimal>>();
		List<OrdenInformeResultadoDTO> lstOrdenes = new ArrayList<OrdenInformeResultadoDTO>();
		try {

			for (OrdenExamenCapturaResultadoDTO ordenExamen : lstOrdenesConSecciones) {

				logger.debug("Codigo examen 0 {}", ordenExamen.getOrdenInformeResultadoDTO().toString());
				logger.debug("Examen con resultado urgente {}", ordenExamen.getDFE_EXAMENESURGENTE());
				logger.debug("Examen con informe resultado urgente {}",
						ordenExamen.getOrdenInformeResultadoDTO().getDFE_EXAMENESURGENTE());

				if (!hm.containsKey(ordenExamen.getOrdenInformeResultadoDTO())) {

					List<BigDecimal> seccionLst = new ArrayList<BigDecimal>();
					seccionLst.add(ordenExamen.getCE_IDSECCION());

					hm.put(ordenExamen.getOrdenInformeResultadoDTO(), seccionLst);

				} else {

					hm.get(ordenExamen.getOrdenInformeResultadoDTO()).add(ordenExamen.getCE_IDSECCION());

				}

				logger.debug("Codigo examen 1 {}", ordenExamen.getCEE_DESCRIPCIONESTADOEXAMEN());
			}

			hm.forEach((k, v) -> {
				k.setLstSecciones(v);
				logger.debug("Codigo examen 2 {}", k.getCEE_DESCRIPCIONESTADOEXAMEN());
				lstOrdenes.add(k);
			});

		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		}

		return lstOrdenes;
	}

	// CAMBIO REALIZADOS POR CRISTIAN 14-11 SE VA A BUSCAR CONDICION DE FILTRO A
	// DAOHELPER
	public List<OrdenesTMDTO> getOrdenesRechazoMuestras(FichaFiltroDTO ffDto) throws BiosLISDAOException {

		String sCond = "";
		BCFichaFiltroDTO bcFichaFiltroDTO = new BCFichaFiltroDTO();
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<OrdenesTMDTO> lista = null;
		try {
			bcFichaFiltroDTO.setBo_nOrdenIni(ffDto.getnOrden());
			bcFichaFiltroDTO.setBo_fIni(ffDto.getfIni());
			bcFichaFiltroDTO.setBo_fFin(ffDto.getfFin());
			bcFichaFiltroDTO.setBo_nombre(ffDto.getNombre());
			bcFichaFiltroDTO.setBo_apellido(ffDto.getApellido());
			bcFichaFiltroDTO.setBo_tipoDocumento(ffDto.getTipoDocumento());
			bcFichaFiltroDTO.setBo_nroDocumento(ffDto.getNroDocumento());
			bcFichaFiltroDTO.setBo_tipoAtencion(ffDto.getTipoAtencion());
			bcFichaFiltroDTO.setBo_localizacion(ffDto.getLocalizacion());
			DaoHelper dh = new DaoHelper();

			try {
				sCond = dh.genSqlCondicionBOFull(bcFichaFiltroDTO);
			} catch (BiosLISException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * if (ffDto.getApellido() != null && !ffDto.getApellido().trim().equals("")) {
			 * sCond += " ( UPPER(dp.DP_PRIMERAPELLIDO) like UPPER(:apellido) OR " +
			 * " UPPER(dp.DP_SEGUNDOAPELLIDO) like UPPER(:apellido)) AND "; } if
			 * (ffDto.getNombre() != null && !ffDto.getNombre().trim().isEmpty()) { sCond +=
			 * " UPPER(dp.DP_NOMBRES) like UPPER(:nombre) AND "; } if (ffDto.getfIni() !=
			 * null && !ffDto.getfIni().trim().isEmpty()) { ffDto.setfFin(ffDto.getfIni() +
			 * " 23:59:59"); sCond +=
			 * " df.DF_FECHAORDEN BETWEEN TO_DATE(:fini,'DD-MM-YYYY HH24:MI:SS') AND TO_DATE(:ffin,'DD-MM-YYYY HH24:MI:SS') AND "
			 * ; } if (ffDto.getnOrden() != null) { sCond += " df.DF_NORDEN = :nOrden AND ";
			 * } if (ffDto.getTipoDocumento() != null && ffDto.getTipoDocumento() != -1) {
			 * sCond += " dp.DP_IDTIPODOCUMENTO = :tipoDoc AND "; } if
			 * (ffDto.getNroDocumento() != null &&
			 * !ffDto.getNroDocumento().trim().isEmpty()) { sCond +=
			 * " dp.DP_NRODOCUMENTO = :nDoc  AND "; }
			 * 
			 * int pos = sCond.lastIndexOf("AND");
			 * 
			 * if (pos != -1) { sCond = sCond.substring(0, pos); sCond = " WHERE " + sCond;
			 * }
			 */
			String sqlQuery = "SELECT df.df_norden NORDEN, to_char(df.df_fechaorden, 'dd/MM/yyyy hh24:mi') FECHAORDEN, dp.dp_idpaciente idpaciente,"
					+ "to_char(dp.dp_nombres || ' ' || dp.dp_primerapellido || ' ' || dp.dp_segundoapellido) nombres, "
					+ "to_char(dp.dp_fnacimiento, 'DD/MM/YYYY') FECHANAC, ldvfet.ldvfet_descripcion estado "
					+ "FROM datos_fichas df " + "left join datos_pacientes dp ON dp.dp_idpaciente = df.df_idpaciente "
					+ "left JOIN ldv_fichasestadostm ldvfet ON ldvfet.ldvfet_idfichaestadotm = df.df_idfichaestadotm";
			Query query = sesion.createSQLQuery(sqlQuery + sCond)
					.setResultTransformer(Transformers.aliasToBean(OrdenesTMDTO.class));

			if (ffDto.getApellido() != null && !ffDto.getApellido().trim().isEmpty()) {
				query.setParameter("apellido", ffDto.getApellido().concat("%"));
			}
			if (ffDto.getNombre() != null && !ffDto.getNombre().trim().isEmpty()) {
				query.setParameter("nombre", ffDto.getNombre().concat("%"));
			}
			if (ffDto.getfIni() != null && !ffDto.getfIni().trim().isEmpty()) {
				query.setParameter("fini", ffDto.getfIni());
				query.setParameter("ffin", ffDto.getfFin());
			}
			if (ffDto.getnOrden() != null) {
				query.setParameter("nOrdenIni", ffDto.getnOrden());
			}
			if (ffDto.getTipoDocumento() != null && ffDto.getTipoDocumento() != -1) {
				query.setParameter("tipoDoc", ffDto.getTipoDocumento());
			}
			if (ffDto.getNroDocumento() != null && !ffDto.getNroDocumento().trim().isEmpty()) {
				query.setParameter("nDoc", ffDto.getNroDocumento());
			}

			if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1) {

				query.setParameter("idExamen", bcFichaFiltroDTO.getBo_examen());
			}

			lista = query.list();
			sesion.close();
		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lista;
	}

	public List<ExamenOrdenDTO> readyToSign(Long nroOrden) throws BiosLISDAOException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		int count = 0;
		List<ExamenOrdenDTO> lstExamenesOrden = null;
		List<ExamenOrdenDTO> lstExamenesOrdenXFirmar = new ArrayList<>();

		try {
			lstExamenesOrden = this.getExamenesOrden(nroOrden);
			for (ExamenOrdenDTO examenOrdenDTO : lstExamenesOrden) {
				switch (examenOrdenDTO.getDFE_CODIGOESTADOEXAMEN()) {
				case EstadosSistema.DFE_CODIGOESTADOEXAMEN_FIRMADO:
					count++;
					lstExamenesOrdenXFirmar.add(examenOrdenDTO);
					break;
				case EstadosSistema.DFE_CODIGOESTADOEXAMEN_BLOQUEADO:
					count++;
					break;
				default:
					break;
				}
			}
		} catch (HibernateException he) {
			logger.error(he.getMessage());
			throw new BiosLISDAOException(
					"Error hibernate. No se pudo determiar si hay que firmar examen.".concat(he.getLocalizedMessage()));
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}
		if (count == lstExamenesOrden.size()) {

			return lstExamenesOrdenXFirmar;
		} else {
			return null;
		}

	}

	private static final String _hqlExamenesEnEstados = "SELECT dfe.DFE_NORDEN ,dfe.DFE_IDEXAMEN ,dfe.DFE_CODIGOESTADOEXAMEN "
			+ "  FROM DatosFichasexamenes dfe " + "  WHERE dfe.IddatosFichasExamenes.dfeNorden = :nroOrden "
			+ "    AND dfe.dfeCodigoestadoexamen   in :lstEstados";

	public List<OrdenExamenCapturaResultadoDTO> getOrdenesxFiltro(BCFichaFiltroDTO bcFichaFiltroDTO)
			throws BiosLISException, BiosLISDAOException {
		List<OrdenExamenCapturaResultadoDTO> lst = null;
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			DaoHelper dh = new DaoHelper();
			String sql;
			sql = dh.genSql(bcFichaFiltroDTO);

			Query qry = sesion.createSQLQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(OrdenExamenCapturaResultadoDTO.class));

			if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().isEmpty()) {
				qry.setParameter("apellido", bcFichaFiltroDTO.getBo_apellido().concat("%"));
			}
			if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
				qry.setParameter("nombre", bcFichaFiltroDTO.getBo_nombre().concat("%"));
			}
			if (bcFichaFiltroDTO.getBo_fFin() != null && !bcFichaFiltroDTO.getBo_fFin().trim().isEmpty()) {
				logger.debug("" + bcFichaFiltroDTO.getBo_fFin());
				qry.setParameter("ffin", bcFichaFiltroDTO.getBo_fFin());
			}
			if (bcFichaFiltroDTO.getBo_fIni() != null && !bcFichaFiltroDTO.getBo_fIni().trim().isEmpty()) {
				logger.debug("fini" + bcFichaFiltroDTO.getBo_fIni());
				qry.setParameter("fini", bcFichaFiltroDTO.getBo_fIni());
			}
			if (bcFichaFiltroDTO.getBo_nOrdenIni() != null) {
				qry.setParameter("nOrdenIni", bcFichaFiltroDTO.getBo_nOrdenIni());
			}
			if (bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
				qry.setParameter("nOrdenFin", bcFichaFiltroDTO.getBo_nOrdenFin());
			}
			if (bcFichaFiltroDTO.getBo_tipoDocumento() != null && bcFichaFiltroDTO.getBo_tipoDocumento() != -1) {
				qry.setParameter("tipoDoc", bcFichaFiltroDTO.getBo_tipoDocumento());
			}
			if (bcFichaFiltroDTO.getBo_nroDocumento() != null
					&& !bcFichaFiltroDTO.getBo_nroDocumento().trim().isEmpty()) {
				qry.setParameter("nDoc", bcFichaFiltroDTO.getBo_nroDocumento());
			}
			if (bcFichaFiltroDTO.getBo_tipoAtencion() != null && bcFichaFiltroDTO.getBo_tipoAtencion() != -1) {
				qry.setParameter("tipoAtencion", bcFichaFiltroDTO.getBo_tipoAtencion());
			}
			if (bcFichaFiltroDTO.getBo_localizacion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1) {
				qry.setParameter("idLocalizacion", bcFichaFiltroDTO.getBo_localizacion());
			}
			if (bcFichaFiltroDTO.getBo_host() != null && !bcFichaFiltroDTO.getBo_host().trim().isEmpty()) {
				qry.setParameter("host", bcFichaFiltroDTO.getBo_host());
			}

			if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1) {

				qry.setParameter("idExamen", bcFichaFiltroDTO.getBo_examen());
			}

			lst = qry.list();
		} catch (BiosLISException e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}

		return lst;
	}

	// ************ pe cambio ordenfulldto a OrdenInformeResultadoDTO 10-11 por
	// cristian
	public List<OrdenInformeResultadoDTO> getSoloOrdenesxFiltro(BCFichaFiltroDTO bcFichaFiltroDTO)
			throws BiosLISException {
		List<OrdenInformeResultadoDTO> lst = null;
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			DaoHelper dh = new DaoHelper();
			String sql;
			sql = dh.genSqlBOFull(bcFichaFiltroDTO);
			sql = sql + " order by DF_NORDEN DESC";

			Query qry = sesion.createSQLQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(OrdenInformeResultadoDTO.class));

			if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().isEmpty()) {
				qry.setParameter("apellido", bcFichaFiltroDTO.getBo_apellido().concat("%"));
			}
			if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
				qry.setParameter("nombre", bcFichaFiltroDTO.getBo_nombre().concat("%"));
			}

			if ((bcFichaFiltroDTO.getBo_nOrdenIni() != null && bcFichaFiltroDTO.getBo_nOrdenFin() != null)
					|| (bcFichaFiltroDTO.getBo_nOrdenIni() == null && bcFichaFiltroDTO.getBo_nOrdenFin() == null)) {
				if (bcFichaFiltroDTO.getBo_fFin() != null && !bcFichaFiltroDTO.getBo_fFin().trim().isEmpty()) {
					logger.debug("" + bcFichaFiltroDTO.getBo_fFin());
					qry.setParameter("ffin", bcFichaFiltroDTO.getBo_fFin());
				}

				if (bcFichaFiltroDTO.getBo_fIni() != null && !bcFichaFiltroDTO.getBo_fIni().trim().isEmpty()) {
					logger.debug("fini" + bcFichaFiltroDTO.getBo_fIni());
					qry.setParameter("fini", bcFichaFiltroDTO.getBo_fIni());
				}
			}

			if (bcFichaFiltroDTO.getBo_nOrdenIni() != null) {
				qry.setParameter("nOrdenIni", bcFichaFiltroDTO.getBo_nOrdenIni());
			}
			if (bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
				qry.setParameter("nOrdenFin", bcFichaFiltroDTO.getBo_nOrdenFin());
			}
			if (bcFichaFiltroDTO.getBo_tipoDocumento() != null && bcFichaFiltroDTO.getBo_tipoDocumento() != -1) {
				qry.setParameter("tipoDoc", bcFichaFiltroDTO.getBo_tipoDocumento());
			}
			if (bcFichaFiltroDTO.getBo_nroDocumento() != null
					&& !bcFichaFiltroDTO.getBo_nroDocumento().trim().isEmpty()) {
				qry.setParameter("nDoc", bcFichaFiltroDTO.getBo_nroDocumento());
			}
			if (bcFichaFiltroDTO.getBo_tipoAtencion() != null && bcFichaFiltroDTO.getBo_tipoAtencion() != -1) {
				qry.setParameter("tipoAtencion", bcFichaFiltroDTO.getBo_tipoAtencion());
			}
			if (bcFichaFiltroDTO.getBo_localizacion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1) {
				qry.setParameter("idLocalizacion", bcFichaFiltroDTO.getBo_localizacion());
			}
			if (bcFichaFiltroDTO.getBo_servicio() != null && bcFichaFiltroDTO.getBo_servicio() != -1) {
				qry.setParameter("idServicio", bcFichaFiltroDTO.getBo_servicio());
			}
			if (bcFichaFiltroDTO.getBo_procedencia() != null && bcFichaFiltroDTO.getBo_procedencia() != -1) {
				qry.setParameter("idProcedencia", bcFichaFiltroDTO.getBo_procedencia());
			}

			if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1) {

				qry.setParameter("idExamen", bcFichaFiltroDTO.getBo_examen());
			}

			lst = qry.list();
			sesion.close();
		} catch (BiosLISException e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}

		return lst;
	}

	public List<OrdenInformeResultadoDTO> getOrdenesCapturaResultados(List<Integer> lstNroOrdenes)
			throws BiosLISDAOException {

		String sCond = " where df.DF_NORDEN in :listaOrdenes";
		List<OrdenExamenCapturaResultadoDTO> lstOrdenesConSecciones = null;
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<OrdenInformeResultadoDTO> lstOrdenes = new ArrayList<OrdenInformeResultadoDTO>();
		try {

			Query qry = sesion.createSQLQuery(_sqlOrdenesConSeccionesCapturaResultados + sCond)
					.setResultTransformer(Transformers.aliasToBean(OrdenExamenCapturaResultadoDTO.class));
			qry.setParameterList("listaOrdenes", lstNroOrdenes);
			lstOrdenesConSecciones = qry.list();

			Map<OrdenInformeResultadoDTO, List<BigDecimal>> hm = new HashMap<OrdenInformeResultadoDTO, List<BigDecimal>>();

			for (OrdenExamenCapturaResultadoDTO ordenExamen : lstOrdenesConSecciones) {

				logger.debug("Codigo examen 0 {}", ordenExamen.getOrdenInformeResultadoDTO().toString());
				logger.debug("Examen con resultado urgente {}", ordenExamen.getDFE_EXAMENESURGENTE());
				logger.debug("Examen con informe resultado urgente {}",
						ordenExamen.getOrdenInformeResultadoDTO().getDFE_EXAMENESURGENTE());

				if (!hm.containsKey(ordenExamen.getOrdenInformeResultadoDTO())) {

					List<BigDecimal> seccionLst = new ArrayList<BigDecimal>();
					seccionLst.add(ordenExamen.getCE_IDSECCION());
					hm.put(ordenExamen.getOrdenInformeResultadoDTO(), seccionLst);
				} else {
					hm.get(ordenExamen.getOrdenInformeResultadoDTO()).add(ordenExamen.getCE_IDSECCION());
				}
				logger.debug("Codigo examen 1 {}", ordenExamen.getCEE_DESCRIPCIONESTADOEXAMEN());
			}

			hm.forEach((k, v) -> {
				k.setLstSecciones(v);
				logger.debug("Codigo examen 2 {}", k.getCEE_DESCRIPCIONESTADOEXAMEN());
				lstOrdenes.add(k);
			});

		} catch (HibernateException he) {
			logger.error(he.getLocalizedMessage());
			throw new BiosLISDAOException(he.getMessage());
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}
		return lstOrdenes;
	}

	public List<OrdenExamenCapturaResultadoDTO> getOrdenesExamenesCapturaResultados(List<Integer> lstNroOrdenes)
			throws BiosLISDAOException {

		String sCond = " where df.DF_NORDEN in :listaOrdenes";
		List<OrdenExamenCapturaResultadoDTO> lstOrdenesConSecciones = null;
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {

			Query qry = sesion.createSQLQuery(_sqlOrdenesConSeccionesCapturaResultados + sCond)
					.setResultTransformer(Transformers.aliasToBean(OrdenExamenCapturaResultadoDTO.class));
			qry.setParameterList("listaOrdenes", lstNroOrdenes);
			lstOrdenesConSecciones = qry.list();

		} catch (HibernateException he) {
			logger.error(he.getLocalizedMessage());
			throw new BiosLISDAOException(he.getMessage());
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}
		return lstOrdenesConSecciones;
	}

	public List<DatosFichasexamenes> getDatosFichasexamenesOrden(Long nroOrden) throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<DatosFichasexamenes> lst = null;
		try {
			Query qry = sesion.createQuery(hqlExamenesOrden);
			qry.setLong("nroOrden", nroOrden);
			lst = (List<DatosFichasexamenes>) qry.list();

		} catch (HibernateException he) {
			logger.error("Hibernate exception {}", he.getLocalizedMessage());
			throw new BiosLISDAOException("Hibernate exception: ".concat(he.getMessage()));
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}
		return lst;

	}

	public List<ExamenPacienteDTO> getListaExamenesPaciente(long idMuestra) throws BiosLISDAOException {
		Session sesion = null;
		try {
			sesion = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT DISTINCT CE.CE_IDEXAMEN, ce.CE_ABREVIADO, dp.DP_IDPACIENTE, "
					+ "CASE WHEN DP.dp_esvip = 'S' and DP.dp_nombreencriptado is not null "
					+ "THEN DP.dp_nombreencriptado " + "ELSE " + "CASE when DP.dp_nombresocial is not null "
					+ "THEN DP.dp_nombresocial "
					+ "ELSE to_char(DP.dp_nombres || ' ' || DP.dp_primerapellido || ' ' || DP.dp_segundoapellido) "
					+ "END " + "END NOMBRES, dfe.DFE_EXAMENANULADO " + "FROM DATOS_FICHAS df "
					+ "JOIN DATOS_FICHASEXAMENES dfe ON dfe.DFE_NORDEN = df.DF_NORDEN "
					+ "JOIN DATOS_FICHASEXAMENESTEST DFET ON dfet.DFET_NORDEN = dfe.DFE_NORDEN and dfe.DFE_IDEXAMEN = dfet.DFET_IDEXAMEN "
					+ "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
					+ "JOIN DATOS_PACIENTES dp ON( df.DF_IDPACIENTE = dp.DP_IDPACIENTE) "
					+ "WHERE dfet.dfet_idmuestra = :idMuestra";
			Query query = sesion.createSQLQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(ExamenPacienteDTO.class));
			query.setParameter("idMuestra", idMuestra);
			List<ExamenPacienteDTO> listaExamenes = query.list();
			sesion.close();
			return listaExamenes;
		} catch (HibernateException he) {
			logger.error(he.getMessage());
			throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
	}

	public OrdenFullDTO getDatosOrdenFull(int nOrden) throws BiosLISDAOException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		OrdenFullDTO ofDto = null;
		try {
			String sql = ""
					+ " SELECT   DF_NORDEN,TO_CHAR(DF_FECHAORDEN,'dd/mm/yyyy hh24:mi:ss') SDF_FECHAORDEN, df.DF_OBSERVACION, "
					+ " dp.DP_IDPACIENTE,lt.LDVTD_DESCRIPCION,dp.DP_NRODOCUMENTO,dp.DP_NOMBRES , dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO,dp.DP_EMAIL,"
					+ " TO_CHAR(dp.DP_FNACIMIENTO,'DD-MM-YYYY ') SDP_FNACIMIENTO , ls.LDVS_DESCRIPCION  ,dp.DP_OBSERVACION,"
					+ " ct.CTA_DESCRIPCION, cp.CP_DESCRIPCION , " + "cc.CC_ABREVIADO, " + "df.DF_IDPREVISION,  "
					+ " df.DF_IDLOCALIZACION," + "cd.CD_DESCRIPCION," + "df.DF_IDCENTRODESALUD,"
					+ "df.DF_IDPRIORIDADATENCION," + " cs.CS_DESCRIPCION, df.DF_HOST," + " CAST(" + " CASE"
					+ " WHEN dfd.DFD_NORDEN IS NOT NULL THEN 'S'" + " ELSE 'N'"
					+ " END AS VARCHAR(1)) AS ESTADODOCUMENTOTM" + " FROM  DATOS_FICHAS df       "
					+ " JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE)     "
					// + "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
					+ "JOIN LDV_TIPOSDOCUMENTOS lt ON (lt.LDVTD_IDTIPODOCUMENTO = dp.DP_IDTIPODOCUMENTO) "
					+ "JOIN CFG_TIPOATENCION ct ON (ct.CTA_IDTIPOATENCION = df.DF_IDTIPOATENCION)    "
					+ "LEFT OUTER JOIN CFG_PROCEDENCIAS cp ON (cp.CP_IDPROCEDENCIA = df.DF_IDPROCEDENCIA )     "
					+ "LEFT OUTER JOIN  CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.DF_IDLOCALIZACION ) "
					// + "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
					+ "JOIN LDV_SEXO ls ON (ls.LDVS_IDSEXO = dp.DP_IDSEXO) "
					// + "JOIN CFG_ESTADOSEXAMENES cee ON (dfe.DFE_CODIGOESTADOEXAMEN =
					// cee.CEE_CODIGOESTADOEXAMEN) "
					+ "LEFT OUTER JOIN CFG_DIAGNOSTICOS cd ON (cd.CD_IDDIAGNOSTICO = df.DF_IDDIAGNOSTICO) "
					+ "LEFT OUTER JOIN CFG_CONVENIOS cc ON (cc.CC_IDCONVENIO = df.DF_IDCONVENIO) "
					+ " LEFT JOIN DATOS_FICHASDOCUMENTOS dfd ON dfd.DFD_NORDEN = df.DF_NORDEN"
					+ " WHERE DF_NORDEN = :nOrden";
			Query query = sesion.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(OrdenFullDTO.class));
			query.setParameter("nOrden", nOrden);
			ofDto = (OrdenFullDTO) query.uniqueResult();
			sesion.close();

		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return ofDto;
	}

	private String hqlExamenesOrden = "select dfe from DatosFichasexamenes dfe where dfe.IddatosFichasExamenes.dfeNorden = :nroOrden";

	public List<OrdenExamenCapturaResultadoDTO> getOrdenesExamenesSeccionesxFiltro(BCFichaFiltroDTO bcFichaFiltroDTO)
			throws BiosLISException, BiosLISDAOException {

		List<OrdenExamenCapturaResultadoDTO> lst = null;
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			DaoHelper dh = new DaoHelper();
			String sql = dh.genSqlOrdenesConDatosExamenesConSecciones(bcFichaFiltroDTO);

			if (bcFichaFiltroDTO.getBo_fIni() == null || bcFichaFiltroDTO.getBo_fIni().trim().equals("")) {
				bcFichaFiltroDTO.setBo_fIni(null);
			} else {
				logger.debug(BiosLisCalendarService.getInstance().estandariza(bcFichaFiltroDTO.getBo_fIni()));
				bcFichaFiltroDTO
						.setBo_fIni(BiosLisCalendarService.getInstance().estandariza(bcFichaFiltroDTO.getBo_fIni()));
			}
			if (bcFichaFiltroDTO.getBo_fFin() == null || bcFichaFiltroDTO.getBo_fFin().trim().equals("")) {
				bcFichaFiltroDTO.setBo_fFin(null);
			} else {
				bcFichaFiltroDTO
						.setBo_fFin(BiosLisCalendarService.getInstance().estandariza(bcFichaFiltroDTO.getBo_fFin()));
			}
			Query qry = sesion.createSQLQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(OrdenExamenCapturaResultadoDTO.class));

			if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().isEmpty()) {
				qry.setParameter("apellido", bcFichaFiltroDTO.getBo_apellido().concat("%"));
			}
			if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
				qry.setParameter("nombre", bcFichaFiltroDTO.getBo_nombre().concat("%"));
			}
			if (bcFichaFiltroDTO.getBo_fFin() != null && !bcFichaFiltroDTO.getBo_fFin().trim().isEmpty()) {
				logger.debug("" + bcFichaFiltroDTO.getBo_fFin());
				qry.setParameter("ffin", bcFichaFiltroDTO.getBo_fFin());
			}
			if (bcFichaFiltroDTO.getBo_fIni() != null && !bcFichaFiltroDTO.getBo_fIni().trim().isEmpty()) {
				logger.debug("fini" + bcFichaFiltroDTO.getBo_fIni());
				qry.setParameter("fini", bcFichaFiltroDTO.getBo_fIni());
			}
			if (bcFichaFiltroDTO.getBo_nOrdenIni() != null) {
				qry.setParameter("nOrdenIni", bcFichaFiltroDTO.getBo_nOrdenIni());
			}
			if (bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
				qry.setParameter("nOrdenFin", bcFichaFiltroDTO.getBo_nOrdenFin());
			}
			if (bcFichaFiltroDTO.getBo_tipoDocumento() != null && bcFichaFiltroDTO.getBo_tipoDocumento() != -1) {
				qry.setParameter("tipoDoc", bcFichaFiltroDTO.getBo_tipoDocumento());
			}
			if (bcFichaFiltroDTO.getBo_nroDocumento() != null
					&& !bcFichaFiltroDTO.getBo_nroDocumento().trim().isEmpty()) {
				qry.setParameter("nDoc", bcFichaFiltroDTO.getBo_nroDocumento());
			}
			if (bcFichaFiltroDTO.getBo_tipoAtencion() != null && bcFichaFiltroDTO.getBo_tipoAtencion() != -1) {
				qry.setParameter("tipoAtencion", bcFichaFiltroDTO.getBo_tipoAtencion());
			}
			if (bcFichaFiltroDTO.getBo_localizacion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1) {
				qry.setParameter("idLocalizacion", bcFichaFiltroDTO.getBo_localizacion());
			}
			if (bcFichaFiltroDTO.getBo_host() != null && !bcFichaFiltroDTO.getBo_host().trim().isEmpty()) {
				qry.setParameter("host", bcFichaFiltroDTO.getBo_host());
			}

			if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1) {

				qry.setParameter("idExamen", bcFichaFiltroDTO.getBo_examen());
			}

			lst = qry.list();

			Map<OrdenExamenCapturaResultadoDTO, List<BigDecimal>> hm = new HashMap<OrdenExamenCapturaResultadoDTO, List<BigDecimal>>();

			for (OrdenExamenCapturaResultadoDTO ordenExamen : lst) {

				logger.debug("Codigo examen 0 {}", ordenExamen.getOrdenInformeResultadoDTO().toString());
				logger.debug("Examen con resultado urgente {}", ordenExamen.getDFE_EXAMENESURGENTE());
				logger.debug("Examen con informe resultado urgente {}",
						ordenExamen.getOrdenInformeResultadoDTO().getDFE_EXAMENESURGENTE());

				if (!hm.containsKey(ordenExamen.getOrdenInformeResultadoDTO())) {

					List<BigDecimal> seccionLst = new ArrayList<BigDecimal>();
					seccionLst.add(ordenExamen.getCE_IDSECCION());
					hm.put(ordenExamen, seccionLst);
				} else {
					hm.get(ordenExamen).add(ordenExamen.getCE_IDSECCION());
				}
				logger.debug("Codigo examen 1 {}", ordenExamen.getCEE_DESCRIPCIONESTADOEXAMEN());
			}
			List<OrdenExamenCapturaResultadoDTO> lstOrdenes = new ArrayList<OrdenExamenCapturaResultadoDTO>();
			hm.forEach((k, v) -> {
				k.setLstSecciones(v);
				logger.debug("Codigo examen 2 {}", k.getCEE_DESCRIPCIONESTADOEXAMEN());
				lstOrdenes.add(k);
			});

		} catch (BiosLISException e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}

		return lst;
	}

	public List<OrdenInformeResultadoDTO> getBOOrdenesConSecciones(BCFichaFiltroDTO fichaFiltroDTO)
			throws BiosLISException, BiosLISDAOException {

		logger.debug("\nIN getBOOrdenesConSecciones {}", LocalDateTime.now());

		List<OrdenExamenCapturaResultadoDTO> lstOrdenesConSecciones = this
				.getOrdenesConSeccionesCapturaResultados(fichaFiltroDTO);

		Map<OrdenInformeResultadoDTO, List<BigDecimal>> hm = new HashMap<OrdenInformeResultadoDTO, List<BigDecimal>>();
		List<OrdenInformeResultadoDTO> lstOrdenes = new ArrayList<OrdenInformeResultadoDTO>();
		// Setear si tienen un examen en estado pendiente.
		try {
//      String curCodExamen = "P";
			Integer curNroOrden = -1;
			Map<Integer, String> hmOrdenesConExamenPendiente = new HashMap<Integer, String>();
			Map<Integer, String> hmOrdenesConExamenUrgente = new HashMap<Integer, String>();
			for (OrdenExamenCapturaResultadoDTO ordenExamen : lstOrdenesConSecciones) {

				if (!ordenExamen.getDF_NORDEN().equals(curNroOrden)) {
					curNroOrden = ordenExamen.getDF_NORDEN();
					hmOrdenesConExamenPendiente.put(curNroOrden, ordenExamen.getDFE_CODIGOESTADOEXAMEN());
					hmOrdenesConExamenUrgente.put(curNroOrden, ordenExamen.getDFE_EXAMENESURGENTE());
				}
				if (ordenExamen.getDFE_EXAMENESURGENTE().equals("S")) {
					hmOrdenesConExamenUrgente.put(ordenExamen.getDF_NORDEN(), ordenExamen.getDFE_EXAMENESURGENTE());
				}
				if (ordenExamen.getDFE_CODIGOESTADOEXAMEN().equals(EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE)
						|| ordenExamen.getDFE_CODIGOESTADOEXAMEN()
								.equals(EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE)) {
					hmOrdenesConExamenPendiente.put(ordenExamen.getDF_NORDEN(),
							EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE);
				}

				if (!hm.containsKey(ordenExamen.getOrdenInformeResultadoDTO())) {

					List<BigDecimal> seccionLst = new ArrayList<BigDecimal>();
					String tienecultivo = ordenExamen.getTIENECULTIVO();
					seccionLst.add(ordenExamen.getCE_IDSECCION());
					hm.put(ordenExamen.getOrdenInformeResultadoDTO(), seccionLst);
				} else {
					hm.get(ordenExamen.getOrdenInformeResultadoDTO()).add(ordenExamen.getCE_IDSECCION());
				}
			}

			hm.forEach((k, v) -> {
				k.setLstSecciones(v);
				lstOrdenes.add(k);
			});

			for (OrdenInformeResultadoDTO ordenInformeResultadoDTO : lstOrdenes) {

				Integer nOrden = ordenInformeResultadoDTO.getDF_NORDEN();
				ordenInformeResultadoDTO.setHayExamenesPendientes(hmOrdenesConExamenPendiente.get(nOrden));
				ordenInformeResultadoDTO.setHayExamenesUrgentes(hmOrdenesConExamenUrgente.get(nOrden));
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		} finally {
			logger.debug("\nOUT getBOOrdenesConSecciones {}", LocalDateTime.now());
		}
		return lstOrdenes;
	}

	// CAMBIO REALIZADOS POR CRISTIAN 14-11 SE VA A BUSCAR CONDICION DE FILTRO A
	// DAOHELPER

	@SuppressWarnings("unchecked")
	private List<OrdenExamenCapturaResultadoDTO> getOrdenesConSeccionesCapturaResultados2(BCFichaFiltroDTO ffDto)
			throws BiosLISException, BiosLISDAOException {
		String sCond = "";
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<OrdenExamenCapturaResultadoDTO> lst = null;
		DaoHelper dh = new DaoHelper();
		try {

			sCond = dh.genSqlCondicionBOFull(ffDto);

			String sCondRecepcionMuestra = "";

			int pos = sCondRecepcionMuestra.lastIndexOf("AND");

			if (pos != -1) {
				sCondRecepcionMuestra = sCondRecepcionMuestra.substring(0, pos);
			}

			if (!sCondRecepcionMuestra.trim().equals("")) {
				sCondRecepcionMuestra = " OR df.DF_NORDEN IN ( SELECT DISTINCT dfm.DFM_NORDEN "
						+ "  FROM BIOSLIS.DATOS_FICHASMUESTRAS dfm WHERE  " + sCondRecepcionMuestra + "  )";
			}

			logger.debug("Condiciones consulta {}", sCond);
			logger.debug("Condiciones recepcion {}", sCondRecepcionMuestra);
			logger.debug("\n SQL {}", _sqlOrdenesConSeccionesCapturaResultados + sCond + sCondRecepcionMuestra
					+ "ORDER BY dfe.DFE_EXAMENESURGENTE DESC");

			if (ffDto.getBo_fIni() == null || ffDto.getBo_fIni().trim().equals("")) {
				ffDto.setBo_fIni(null);
			} else {
				logger.debug(BiosLisCalendarService.getInstance().estandariza(ffDto.getBo_fIni()));
				ffDto.setBo_fIni(BiosLisCalendarService.getInstance().estandariza(ffDto.getBo_fIni()));
			}
			if (ffDto.getBo_fFin() == null || ffDto.getBo_fFin().trim().equals("")) {
				ffDto.setBo_fFin(null);
			} else {
				ffDto.setBo_fFin(BiosLisCalendarService.getInstance().estandariza(ffDto.getBo_fFin()));
			}

			Query qry = sesion.createSQLQuery(_sqlOrdenesConSeccionesCapturaResultados + sCond // +
																								// sCondRecepcionMuestra
					+ "ORDER BY dfe.DFE_EXAMENESURGENTE , df.DF_FECHAORDEN DESC  ")
					.setResultTransformer(Transformers.aliasToBean(OrdenExamenCapturaResultadoDTO.class));

			if (ffDto.getBo_apellido() != null && !ffDto.getBo_apellido().trim().isEmpty()) {
				qry.setParameter("apellido", ffDto.getBo_apellido().concat("%"));
			}
			if (ffDto.getBo_nombre() != null && !ffDto.getBo_nombre().trim().isEmpty()) {
				qry.setParameter("nombre", ffDto.getBo_nombre().concat("%"));
			}
			if (ffDto.getBo_fFin() != null && !ffDto.getBo_fFin().trim().isEmpty()) {
				qry.setParameter("ffin", ffDto.getBo_fFin());
			}
			if (ffDto.getBo_fIni() != null && !ffDto.getBo_fIni().trim().isEmpty()) {
				qry.setParameter("fini", ffDto.getBo_fIni());
			}
			if (ffDto.getBo_nOrdenIni() != null) {
				qry.setParameter("nOrdenIni", ffDto.getBo_nOrdenIni());
			}
			if (ffDto.getBo_nOrdenFin() != null) {
				qry.setParameter("nOrdenFin", ffDto.getBo_nOrdenFin());
			}
			if (ffDto.getBo_tipoDocumento() != null && ffDto.getBo_tipoDocumento() != -1) {
				qry.setParameter("tipoDoc", ffDto.getBo_tipoDocumento());
			}
			if (ffDto.getBo_nroDocumento() != null && !ffDto.getBo_nroDocumento().trim().isEmpty()) {
				qry.setParameter("nDoc", ffDto.getBo_nroDocumento());
			}
			if (ffDto.getBo_tipoAtencion() != null && ffDto.getBo_tipoAtencion() != -1) {
				qry.setParameter("tipoAtencion", ffDto.getBo_tipoAtencion());
			}
			if (ffDto.getBo_localizacion() != null && ffDto.getBo_localizacion() != -1) {
				qry.setParameter("idLocalizacion", ffDto.getBo_localizacion());
			}
			if (ffDto.getBo_examen() != null) {
				qry.setParameter("idExamen", ffDto.getBo_examen().toString());
			}

			if (ffDto.getBo_seccion() != null) {
				qry.setParameter("idSeccion", ffDto.getBo_seccion());
			}
			if (ffDto.getBo_servicio() != null) {
				qry.setParameter("idServicio", ffDto.getBo_servicio());
			}
			if (ffDto.getBo_procedencia() != null) {
				qry.setParameter("idProcedencia", ffDto.getBo_procedencia());
			}

			lst = (List<OrdenExamenCapturaResultadoDTO>) qry.list();
			sesion.close();

			for (OrdenExamenCapturaResultadoDTO oecr : lst) {
				if (oecr.getSDP_FNACIMIENTO() != null) {
					oecr.setSDP_FNACIMIENTO(Edad.getEdadActual(oecr.getSDP_FNACIMIENTO()).toString());
				}

			}
		} catch (BiosLISException e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lst;
	}

	private List<OrdenExamenCapturaResultadoDTO> getOrdenesConSeccionesCapturaResultados(BCFichaFiltroDTO ffDto)
			throws BiosLISException, BiosLISDAOException {
		String sCond = "";
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<OrdenExamenCapturaResultadoDTO> lst = null;

		try {

			if (ffDto.getBo_nOrdenIni() != null && ffDto.getBo_nOrdenFin() != null) {
				sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
				sCond += " df.DF_NORDEN <= :nOrdenFin  AND";
			} else {
				if (ffDto.getBo_nOrdenIni() != null || ffDto.getBo_nOrdenFin() != null) {
					if (ffDto.getBo_nOrdenIni() != null) {
						sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
						sCond += " df.DF_NORDEN <= :nOrdenIni  AND";
					}
					if (ffDto.getBo_nOrdenFin() != null) {
						sCond += " df.DF_NORDEN >= :nOrdenFin AND ";
						sCond += " df.DF_NORDEN <= :nOrdenFin  AND";
					}
				}
			}

			if ((ffDto.getBo_nOrdenIni() != null && ffDto.getBo_nOrdenFin() != null)
					|| (ffDto.getBo_nOrdenIni() == null && ffDto.getBo_nOrdenFin() == null)) {

				if (ffDto.getBo_fIni() != null && !ffDto.getBo_fIni().trim().isEmpty() && ffDto.getBo_fFin() != null
						&& !ffDto.getBo_fFin().trim().isEmpty()) {
					sCond += " df.DF_FECHAORDEN >= TO_DATE(:fini,'DD-MM-YYYY HH24:MI:SS') AND ";
					sCond += " df.DF_FECHAORDEN <= TO_DATE(:ffin,'DD-MM-YYYY HH24:MI:SS') + 1 AND ";
				} else {
					if ((ffDto.getBo_fIni() != null && !ffDto.getBo_fIni().trim().isEmpty())
							|| (ffDto.getBo_fFin() != null && !ffDto.getBo_fFin().trim().isEmpty())) {
						if (ffDto.getBo_fIni() != null && !ffDto.getBo_fIni().trim().isEmpty()) {
							sCond += " df.DF_FECHAORDEN >= TO_DATE(:fini,'DD-MM-YYYY HH24:MI:SS') AND ";
						}
						if (ffDto.getBo_fFin() != null && !ffDto.getBo_fFin().trim().isEmpty()) {
							sCond += " df.DF_FECHAORDEN  <= TO_DATE(:ffin,'DD-MM-YYYY HH24:MI:SS') AND ";
						}
					}
				}
			}

			String apellido = "";
			if (ffDto.getBo_apellido() != null) {
				if (!ffDto.getBo_apellido().trim().isEmpty()) {
					apellido = ffDto.getBo_apellido().toUpperCase();
					sCond = sCond + " ( UPPER(dp.DP_PRIMERAPELLIDO_B) like '%" + apellido + "%') AND ";
					// + "%' or UPPER(dp.DP_SEGUNDOAPELLIDO) like '%" + apellido + "%') ";
				}
			}
			if (ffDto.getBo_segundoApellido() != null) {
				if (!ffDto.getBo_segundoApellido().trim().isEmpty()) {
					apellido = ffDto.getBo_segundoApellido().toUpperCase();
					sCond = sCond + " UPPER(dp.DP_SEGUNDOAPELLIDO_B) like '%" + apellido + "%') AND ";
				}
			}
			String nombre = "";
			if (ffDto.getBo_nombre() != null && !ffDto.getBo_nombre().trim().isEmpty()) {
				nombre = ffDto.getBo_nombre().toUpperCase();
				sCond = sCond + "  UPPER(dp.DP_NOMBRES_B)  like '%" + nombre + "%' AND ";
			}

			if (ffDto.getBo_procedencia() != null && ffDto.getBo_procedencia() != 0) {
				sCond = sCond + " df.DF_IDPROCEDENCIA = " + ffDto.getBo_procedencia() + " AND ";
			}
			if (ffDto.getBo_tipoDocumento() != null && ffDto.getBo_tipoDocumento() != 0) {
				sCond = sCond + " dp.DP_IDTIPODOCUMENTO  =" + ffDto.getBo_tipoDocumento() + " AND ";
			}

			if (ffDto.getBo_localizacion() != null && ffDto.getBo_localizacion() != 0) {
				sCond = sCond + " df.DF_IDLOCALIZACION =" + ffDto.getBo_localizacion() + " AND ";
			}
			if (ffDto.getBo_servicio() != null && ffDto.getBo_servicio() != 0) {
				sCond = sCond + " df.DF_IDSERVICIO  =" + ffDto.getBo_servicio() + " AND ";
			}
			if (ffDto.getBo_seccion() != null && ffDto.getBo_seccion() != 0) {
				sCond = sCond + " ce.CE_IDSECCION =" + ffDto.getBo_seccion() + " AND ";
			}
			if (ffDto.getBo_convenio() != null && ffDto.getBo_examen() != 0) {
				sCond = sCond + " cc.CC_IDCONVENIO =" + ffDto.getBo_convenio() + " AND ";
			} /*
				 * if (ffDto.isBo_estadoPendiente2()) { sCond = sCond +
				 * " and  dfe.DFE_CODIGOESTADOEXAMEN = 'P' AND"; } if
				 * (ffDto.isBo_estadoFirmar()) { sCond = sCond +
				 * " and  dfe.DFE_CODIGOESTADOEXAMEN = 'F' AND"; } if (ffDto.isBo_estadoFirmar()
				 * == false && ffDto.isBo_estadoPendiente2() == false) { sCond = sCond +
				 * " and  dfe.DFE_CODIGOESTADOEXAMEN IN ('P','F') AND"; }
				 */
			if (ffDto.getBo_examen() != null && ffDto.getBo_examen() != 0) {
				sCond = sCond + " dfe.DFE_IDEXAMEN = " + ffDto.getBo_examen() + " AND ";
			}
			if (ffDto.getBo_nroDocumento() != null) {
				if (!ffDto.getBo_nroDocumento().isEmpty()) {
					sCond = sCond + " dp.DP_NRODOCUMENTO = '" + ffDto.getBo_nroDocumento().trim() + "' AND ";
				}
			}
			if (ffDto.getBo_tipoAtencion() != null && ffDto.getBo_tipoAtencion() != 0) {
				sCond = sCond + " ct.CTA_IDTIPOATENCION = " + ffDto.getBo_tipoAtencion() + " AND ";
			}
			int pos = sCond.lastIndexOf("AND");

			if (pos != -1) {
				sCond = sCond.substring(0, pos);

			}

			if (sCond != "") {
				sCond = " where " + sCond;
			}

			Query qry = sesion
					.createSQLQuery(_sqlOrdenesConSeccionesCapturaResultados + sCond
							+ "ORDER BY dfe.DFE_EXAMENESURGENTE , df.DF_FECHAORDEN DESC  ")
					.setResultTransformer(Transformers.aliasToBean(OrdenExamenCapturaResultadoDTO.class));

			if (ffDto.getBo_fFin() != null && !ffDto.getBo_fFin().trim().isEmpty()) {
				qry.setParameter("ffin", ffDto.getBo_fFin());
			}
			if (ffDto.getBo_fIni() != null && !ffDto.getBo_fIni().trim().isEmpty()) {
				qry.setParameter("fini", ffDto.getBo_fIni());
			}
			if (ffDto.getBo_nOrdenIni() != null) {
				qry.setParameter("nOrdenIni", ffDto.getBo_nOrdenIni());
			}
			if (ffDto.getBo_nOrdenFin() != null) {
				qry.setParameter("nOrdenFin", ffDto.getBo_nOrdenFin());
			}
			logger.debug("\nIN getOrdenesConSeccionesCapturaResultados {}", LocalDateTime.now());
			lst = qry.list();
		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			logger.debug("\nOUT getOrdenesConSeccionesCapturaResultados {}", LocalDateTime.now());
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lst;
	}

	private static final String sqlExamenesOrden = "" +

			"SELECT ce.CE_ABREVIADO,ce.CE_CODIGOEXAMEN, \r\n"
			+ "dfe.DFE_NORDEN,dfe.DFE_IDEXAMEN ,TO_CHAR(dfe.DFE_FECHAEMAIL,'DD-MM-YYYY HH24:MI:SS') SDFE_FECHAEMAIL ,\r\n"
			+ "TO_CHAR(dfe.DFE_FECHAIMPRESION,'DD-MM-YYYY HH24:MI:SS') SDFE_FECHAIMPRESION , \r\n"
			+ "dfe.DFE_IDUSUARIOEMAIL, dfe.DFE_IDUSUARIOIMPRIME, dfe.DFE_CODIGOESTADOEXAMEN, dfe.DFE_CANTIDAD,  \r\n"
			+ "dp.DP_NOMBRES,  dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO, dp.DP_EMAIL, cs.CS_DESCRIPCION, cs.CS_EMAIL, \r\n"
			+ " cee.CEE_DESCRIPCIONESTADOEXAMEN, TO_CHAR(dfe.DFE_FECHARETIRAEXAMEN,'DD-MM-YYYY HH24:MI:SS') SDFE_FECHARETIRAEXAMEN,\r\n"
			+ "SUBSTR(lvs.LDVS_DESCRIPCION,0,1) as LDVS_DESCRIPCION , dfe.DFE_EXAMENANULADO, CONCAT(CONCAT(du.DU_NOMBRES,' '),du.DU_PRIMERAPELLIDO) NOMBREUSUARIOIMPRIME,dfe.DFE_IMPRESO DFE_IMPRESO,du.DU_RUN RUTUSUARIOIMPRIME\r\n"
			+ "FROM  DATOS_FICHAS df  \r\n" + "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) \r\n"
			+ "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) \r\n"
			+ "JOIN DATOS_PACIENTES dp ON( df.DF_IDPACIENTE = dp.DP_IDPACIENTE) \r\n"
			+ "JOIN LDV_SEXO lvs ON( lvs.LDVS_IDSEXO = dp.DP_IDSEXO) \r\n"
			+ "LEFT OUTER JOIN CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.DF_IDLOCALIZACION) \r\n"
			+ "LEFT OUTER JOIN CFG_ESTADOSEXAMENES cee ON (cee.CEE_CODIGOESTADOEXAMEN = dfe.DFE_CODIGOESTADOEXAMEN) \r\n"
			+ "LEFT JOIN DATOS_USUARIOS du ON dfe.DFE_IDUSUARIOIMPRIME = du.DU_IDUSUARIO   "
			+ "WHERE DF_NORDEN = :nOrden";
	private static final String sqlExamenesOrdenxExamen = "" +

			"SELECT ce.CE_ABREVIADO,ce.CE_CODIGOEXAMEN, \r\n"
			+ "dfe.DFE_NORDEN,dfe.DFE_IDEXAMEN ,TO_CHAR(dfe.DFE_FECHAEMAIL,'DD-MM-YYYY HH24:MI:SS') SDFE_FECHAEMAIL ,\r\n"
			+ "TO_CHAR(dfe.DFE_FECHAIMPRESION,'DD-MM-YYYY HH24:MI:SS') SDFE_FECHAIMPRESION , \r\n"
			+ "dfe.DFE_IDUSUARIOEMAIL, dfe.DFE_IDUSUARIOIMPRIME, dfe.DFE_CODIGOESTADOEXAMEN, dfe.DFE_CANTIDAD,  \r\n"
			+ "dp.DP_NOMBRES,  dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO, dp.DP_EMAIL, cs.CS_DESCRIPCION, cs.CS_EMAIL, \r\n"
			+ " cee.CEE_DESCRIPCIONESTADOEXAMEN, TO_CHAR(dfe.DFE_FECHARETIRAEXAMEN,'DD-MM-YYYY HH24:MI:SS') SDFE_FECHARETIRAEXAMEN,\r\n"
			+ "SUBSTR(lvs.LDVS_DESCRIPCION,0,1) as LDVS_DESCRIPCION , dfe.DFE_EXAMENANULADO, CONCAT(CONCAT(du.DU_NOMBRES,' '),du.DU_PRIMERAPELLIDO) NOMBREUSUARIOIMPRIME,dfe.DFE_IMPRESO DFE_IMPRESO,du.DU_RUN RUTUSUARIOIMPRIME\r\n"
			+ "FROM  DATOS_FICHAS df  \r\n" + "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) \r\n"
			+ "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) \r\n"
			+ "JOIN DATOS_PACIENTES dp ON( df.DF_IDPACIENTE = dp.DP_IDPACIENTE) \r\n"
			+ "JOIN LDV_SEXO lvs ON( lvs.LDVS_IDSEXO = dp.DP_IDSEXO) \r\n"
			+ "LEFT OUTER JOIN CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.DF_IDLOCALIZACION) \r\n"
			+ "LEFT OUTER JOIN CFG_ESTADOSEXAMENES cee ON (cee.CEE_CODIGOESTADOEXAMEN = dfe.DFE_CODIGOESTADOEXAMEN) \r\n"
			+ "LEFT JOIN DATOS_USUARIOS du ON dfe.DFE_IDUSUARIOIMPRIME = du.DU_IDUSUARIO   "
			+ "WHERE DF_NORDEN = :nOrden AND dfe.DFE_IDEXAMEN = :idExamen  ";

	private static final String sqlUpdateDatosExamenesOrden = "" +

			"UPDATE  " + "ce.CE_ABREVIADO,ce.CE_CODIGOEXAMEN,"
			+ "dfe.DFE_NORDEN,dfe.DFE_IDEXAMEN ,TO_CHAR(dfe.DFE_FECHAEMAIL,'DD-MM-YYYY HH24:MI:SS') SDFE_FECHAEMAIL ,\n"
			+ "TO_CHAR(dfe.DFE_FECHAIMPRESION,'DD-MM-YYYY HH24:MI:SS') SDFE_FECHAIMPRESION ,\n"
			+ "dfe.DFE_IDUSUARIOEMAIL, dfe.DFE_IDUSUARIOIMPRIME, dfe.DFE_CODIGOESTADOEXAMEN, dfe.DFE_CANTIDAD,  "
			+ "dp.DP_NOMBRES,  dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO, dp.DP_EMAIL, "
			+ "TO_CHAR(dp.DP_FNACIMIENTO,'DD-MM-YYYY HH24:MI:SS') SDP_FNACIMIENTO,cs.CS_DESCRIPCION, cs.CS_EMAIL "
			+ "FROM  DATOS_FICHAS df  " + "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
			+ "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
			+ "JOIN DATOS_PACIENTES dp ON( df.DF_IDPACIENTE = dp.DP_IDPACIENTE)"
			+ "JOIN CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.DF_IDLOCALIZACION)" + "WHERE DF_NORDEN = :nOrden";

	private static final String _sqlOrdenesInformeResultados = ""
			+ "SELECT  TO_CHAR(DF_FECHAORDEN,'DD-MM HH24:MI') SDF_FECHAORDEN, DF_NORDEN, dp.DP_NOMBRES ,"
			+ "TO_CHAR(dp.DP_FNACIMIENTO,'DD-MM-YYYY HH24:MI') SDP_FNACIMIENTO , SUBSTR(ls.LDVS_DESCRIPCION,0,1) as LDVS_DESCRIPCION  ,dp.DP_OBSERVACION, cc.CC_ABREVIADO, "
			+ "lt.LDVTD_DESCRIPCION,dp.DP_NRODOCUMENTO,ct.CTA_DESCRIPCION, cp.CP_DESCRIPCION , cs.CS_DESCRIPCION ,"
			+ "dp.DP_EMAIL, dp.DP_IDPACIENTE,dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO,"
			+ "cd.CD_DESCRIPCION, df.DF_OBSERVACION, cm.CM_NOMBRES,cm.CM_PRIMERAPELLIDO, cm.CM_SEGUNDOAPELLIDO  "
			+ "FROM  DATOS_FICHAS df       " + "JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE)     "
			+ "JOIN LDV_TIPOSDOCUMENTOS lt ON (lt.LDVTD_IDTIPODOCUMENTO = dp.DP_IDTIPODOCUMENTO)"
			+ "LEFT OUTER JOIN CFG_TIPOATENCION ct ON (ct.CTA_IDTIPOATENCION = df.DF_IDTIPOATENCION)     "
			+ "LEFT OUTER JOIN CFG_PROCEDENCIAS cp ON (cp.CP_IDPROCEDENCIA = df.DF_IDPROCEDENCIA )     "
			+ "LEFT OUTER JOIN CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.DF_IDLOCALIZACION ) "
			+ "LEFT OUTER JOIN LDV_SEXO ls ON (ls.LDVS_IDSEXO = dp.DP_IDSEXO) "
			+ "LEFT OUTER JOIN CFG_DIAGNOSTICOS cd ON (cd.CD_IDDIAGNOSTICO = df.DF_IDDIAGNOSTICO) "
			+ "LEFT OUTER JOIN CFG_CONVENIOS cc ON (cc.CC_IDCONVENIO = df.DF_IDCONVENIO) "
			+ "LEFT OUTER JOIN CFG_MEDICOS cm  ON (cm.CM_IDMEDICO = df.DF_IDMEDICO) ";

	private static final String _sqlOrdenesConSeccionesCapturaResultadosNUEVA = ""
			+ "SELECT  TO_CHAR(DF_FECHAORDEN,'HH24:MI DD/MM/YYYY') SDF_FECHAORDEN, DF_NORDEN, dp.DP_NOMBRES ,"
			+ "  concat(round((TRUNC(SYSDATE) - dp.DP_FNACIMIENTO) / 365),' años' )  as SDP_FNACIMIENTO , ls.LDVS_DESCRIPCION  ,dp.DP_OBSERVACION,  "
			+ "lt.LDVTD_DESCRIPCION,dp.DP_NRODOCUMENTO,ct.CTA_DESCRIPCION,"
			+ "dp.DP_EMAIL, dp.DP_IDPACIENTE,dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO,"
			+ "df.DF_OBSERVACION,ce.CE_IDSECCION ,"
			+ "dfe.DFE_CODIGOESTADOEXAMEN,cee.CEE_DESCRIPCIONESTADOEXAMEN, dfe.DFE_EXAMENESURGENTE,ce.CE_CODIGOEXAMEN, ce.CE_ABREVIADO, "
			+ "dfe.DFE_IDEXAMEN,  cl.cl_codigolocalizacion as Localizacion " + "FROM  DATOS_FICHAS df       "
			+ "JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE)    "
			+ "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
			+ "JOIN LDV_TIPOSDOCUMENTOS lt ON (lt.LDVTD_IDTIPODOCUMENTO = dp.DP_IDTIPODOCUMENTO) "
			+ "JOIN CFG_TIPOATENCION ct ON (ct.CTA_IDTIPOATENCION = df.DF_IDTIPOATENCION)    "
			+ "JOIN CFG_LOCALIZACIONES cl ON (cl.CL_IDLOCALIZACION = df.DF_IDLOCALIZACION ) "
			+ "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
			+ "JOIN LDV_SEXO ls ON (ls.LDVS_IDSEXO = dp.DP_IDSEXO) "
			+ "JOIN CFG_ESTADOSEXAMENES cee ON (dfe.DFE_CODIGOESTADOEXAMEN = cee.CEE_CODIGOESTADOEXAMEN) ";

	private static final String _sqlOrdenesConSeccionesCapturaResultados = ""
			+ "SELECT  TO_CHAR(DF_FECHAORDEN,'HH24:MI DD/MM/YYYY') SDF_FECHAORDEN, DF_NORDEN, dp.DP_NOMBRES ,"
			+ "  concat(round((TRUNC(SYSDATE) - dp.DP_FNACIMIENTO) / 365),' años' )  as SDP_FNACIMIENTO , ls.LDVS_DESCRIPCION  ,dp.DP_OBSERVACION, cc.CC_ABREVIADO, "
			+ "lt.LDVTD_DESCRIPCION,dp.DP_NRODOCUMENTO,ct.CTA_DESCRIPCION, cp.CP_DESCRIPCION , cs.CS_DESCRIPCION ,"
			+ "dp.DP_EMAIL, dp.DP_IDPACIENTE,dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO,"
			+ "cd.CD_DESCRIPCION, df.DF_OBSERVACION, cm.CM_NOMBRES,cm.CM_PRIMERAPELLIDO, cm.CM_SEGUNDOAPELLIDO, ce.CE_IDSECCION ,"
			+ "dfe.DFE_CODIGOESTADOEXAMEN,cee.CEE_DESCRIPCIONESTADOEXAMEN, dfe.DFE_EXAMENESURGENTE,ce.CE_CODIGOEXAMEN, ce.CE_ABREVIADO, "
			+ "csec.CSEC_DESCRIPCION,dfe.DFE_IDEXAMEN,  cl.cl_codigolocalizacion as Localizacion "
			+ "FROM  DATOS_FICHAS df       " + "JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE)    "
			+ "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
			+ "JOIN LDV_TIPOSDOCUMENTOS lt ON (lt.LDVTD_IDTIPODOCUMENTO = dp.DP_IDTIPODOCUMENTO) "
			+ "JOIN CFG_TIPOATENCION ct ON (ct.CTA_IDTIPOATENCION = df.DF_IDTIPOATENCION)    "
			+ "JOIN CFG_LOCALIZACIONES cl ON (cl.CL_IDLOCALIZACION = df.DF_IDLOCALIZACION ) "
			+ "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
			+ "JOIN LDV_SEXO ls ON (ls.LDVS_IDSEXO = dp.DP_IDSEXO) "
			+ "JOIN CFG_ESTADOSEXAMENES cee ON (dfe.DFE_CODIGOESTADOEXAMEN = cee.CEE_CODIGOESTADOEXAMEN) "
			+ "JOIN CFG_PROCEDENCIAS cp ON (cp.CP_IDPROCEDENCIA = df.DF_IDPROCEDENCIA )     "
			+ "JOIN CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.df_IDSERVICIO ) "
			+ "JOIN CFG_DIAGNOSTICOS cd ON (cd.CD_IDDIAGNOSTICO = df.DF_IDDIAGNOSTICO) "
			+ "JOIN CFG_SECCIONES csec ON (csec.CSEC_IDSECCION = ce.CE_IDSECCION ) "
			+ "JOIN CFG_CONVENIOS cc ON (cc.CC_IDCONVENIO = df.DF_IDCONVENIO) "
			+ "JOIN CFG_MEDICOS cm  ON (cm.CM_IDMEDICO = df.DF_IDMEDICO) ";

	// **********************************************************hasta aqui
	// ***********************
	// agregado x jan

	public static HashMap<String, Object> updateInformeResultado(OrdenExamenCapturaResultadoDTO MOD)
			throws BiosLISException, BiosLISDAOException, ParseException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			sesion = HibernateUtil.getSessionFactory().openSession();

			sesion.beginTransaction();

			for (DatosFichasExamenesDTO DFE_list : MOD.getExamenesLst()) {
				java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
				String dateString = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());

				if (!validacionExamenAutorizado(MOD.getDF_NORDEN().longValue(),
						DFE_list.getDFE_IDEXAMEN().longValue())) {
					DFE_list.setDfe_FECHARETIRAEXAMEN("");
				} else {
					DatosFichasexamenesId dfeId = new DatosFichasexamenesId(MOD.getDF_NORDEN().longValue(),
							DFE_list.getDFE_IDEXAMEN().longValue());
					DatosFichasexamenes dfe = (DatosFichasexamenes) sesion.get(DatosFichasexamenes.class, dfeId);

					dfe.setDfeImpreso("S");
					dfe.setDfeExamenretirado("S");
					dfe.setDfeRutretiraexamen(MOD.getDP_NRODOCUMENTO());
					dfe.setDfeNombreretiraexamen(MOD.getDP_NOMBRES());
					dfe.setDfeFecharetiraexamen(date);
					if (MOD.getDFE_IDUSUARIOIMPRIME() != null) {
						dfe.setDfeIdusuarioimprime(MOD.getDFE_IDUSUARIOIMPRIME().longValue());
					}
					DFE_list.setDfe_IMPRESO(dfe.getDfeImpreso());
					// DFE_list.setCeCodigoExamen(dfe.get);

					DFE_list.setDfe_FECHARETIRAEXAMEN(dateString);
					sesion.update(dfe);
				}
			}
			sesion.getTransaction().commit();
			result.put("examenes", MOD.getExamenesLst());
			result.put("dp_NOMBRES", MOD.getDP_NOMBRES());
			result.put("dp_NRODOCUMENTO", MOD.getDP_NRODOCUMENTO());
			result.put("df_NORDEN", MOD.getDF_NORDEN());
			// result.put("dfe_FECHARETIRAEXAMEN",new SimpleDateFormat("dd-MM-yyyy
			// hh:mm").parse();
			// vnew SimpleDateFormat("dd-MM-yyyy hh:mm").parse(MTDTO.getRESEEDINGDATE()
			sesion.close();
		} catch (HibernateException he) {
			logger.error("No se pudo obtener datos microbiologiaOrden", he.getMessage());
			he.printStackTrace();
			// throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}

		return result;
	}

	public static Boolean validacionExamenAutorizado(long Norden, long idExamen) {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		Boolean validar = false;

		try {
			Query query = sesion.createSQLQuery(
					"SELECT DFE_AUTORIZADO FROM datos_fichasexamenes  WHERE DFE_NORDEN =:nOrden AND DFE_IDEXAMEN = :idExamen");
			query.setParameter("nOrden", Norden);
			query.setParameter("idExamen", idExamen);

			String validadorCastString = (String) query.list().get(0);
			validar = (validadorCastString.equals("S")) ? true : false;
			sesion.close();
		} catch (HibernateException he) {
			logger.error("No se pudo validar examen autorizado", he.getMessage());
			he.printStackTrace();
			// throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}
		return validar;
	}

	public static ExamenOrdenDTO getOrdenesxExamen(Long nroOrden, Long idExamen) throws BiosLISDAOException {
		Session sesion = null;
		ExamenOrdenDTO lst = null;

		try {
			sesion = HibernateUtil.getSessionFactory().openSession();
			Query qry = sesion.createSQLQuery(sqlExamenesOrdenxExamen)
					.setResultTransformer(Transformers.aliasToBean(ExamenOrdenDTO.class));
			qry.setLong("nOrden", nroOrden);
			qry.setLong("idExamen", idExamen);
			lst = (ExamenOrdenDTO) qry.list().get(0);
			sesion.close();
		} catch (HibernateException he) {
			logger.error("No se pudo recuperar examens de orden {}", he.getMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}

		return lst;
	}

	private List<OrdenExamenCapturaResultadoDTO> getOrdenesConSeccionesCapturaResultadosEstricto(BCFichaFiltroDTO ffDto)
			throws BiosLISException, BiosLISDAOException {
		String sCond = "";
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<OrdenExamenCapturaResultadoDTO> lst = null;
		logger.debug("\nIN getOrdenesConSeccionesCapturaResultados {}", LocalDateTime.now());

		try {

			sCond = DaoHelper.get_sqlOrdenesConSeccionesCapturaResultadosEstricto(ffDto);
			Query qry = sesion
					.createSQLQuery(_sqlOrdenesConSeccionesCapturaResultados + sCond
							+ "ORDER BY dfe.DFE_EXAMENESURGENTE , df.DF_FECHAORDEN DESC  ")
					.setResultTransformer(Transformers.aliasToBean(OrdenExamenCapturaResultadoDTO.class));

			if (ffDto.getBo_fFin() != null && !ffDto.getBo_fFin().trim().isEmpty()) {
				qry.setParameter("ffin", ffDto.getBo_fFin());
			}
			if (ffDto.getBo_fIni() != null && !ffDto.getBo_fIni().trim().isEmpty()) {
				qry.setParameter("fini", ffDto.getBo_fIni());
			}
			if (ffDto.getBo_nOrdenIni() != null) {
				qry.setParameter("nOrdenIni", ffDto.getBo_nOrdenIni());
			}
			if (ffDto.getBo_nOrdenFin() != null) {
				qry.setParameter("nOrdenFin", ffDto.getBo_nOrdenFin());
			}

			if (ffDto.getBo_tipoDocumento() != null) {
				qry.setParameter("idTipoDocumento", ffDto.getBo_tipoDocumento());
			}

			if (ffDto.getBo_localizacion() != null) {
				qry.setParameter("idLocalizacion", ffDto.getBo_localizacion());
			}

			if (ffDto.getBo_apellido() != null) {
				qry.setParameter("apellido", ffDto.getBo_apellido());
			}

			if (ffDto.getBo_nombre() != null && !ffDto.getBo_nombre().trim().isEmpty()) {
				qry.setParameter("nombre", ffDto.getBo_nOrdenFin());
			}
			lst = qry.list();
		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			logger.debug("\nOUT getOrdenesConSeccionesCapturaResultados {}", LocalDateTime.now());
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lst;
	}

	public List<OrdenInformeResultadoDTO> getBOOrdenesConSecciones_Urgentes(BCFichaFiltroDTO fichaFiltroDTO)
			throws BiosLISException, BiosLISDAOException {

		logger.debug("\nIN getBOOrdenesConSecciones_Urgentes {}", LocalDateTime.now());

		List<OrdenExamenCapturaResultadoDTO> lstOrdenesConSecciones = this
				.getOrdenesConSecciones_Urgentes(fichaFiltroDTO);

		Map<OrdenInformeResultadoDTO, List<BigDecimal>> hm = new HashMap<OrdenInformeResultadoDTO, List<BigDecimal>>();
		List<OrdenInformeResultadoDTO> lstOrdenes = new ArrayList<OrdenInformeResultadoDTO>();
		// Setear si tienen un examen en estado pendiente.
		try {
//      String curCodExamen = "P";
			Integer curNroOrden = -1;
			Map<Integer, String> hmOrdenesConExamenPendiente = new HashMap<Integer, String>();
			Map<Integer, String> hmOrdenesConExamenUrgente = new HashMap<Integer, String>();
			Map<Integer, String> hmOrdenesConAntecedentes = new HashMap<Integer, String>();
			Map<Integer, BigDecimal> hmOrdenesServicio = new HashMap<Integer, BigDecimal>();
			Map<Integer, String> hmFechaNacimiento = new HashMap<Integer, String>();
			CfgCacheSexo<LdvSexoDAO> cache = new CfgCacheSexo<>(LdvSexoDAO.class);
			CfgTipoAtencionAbsDao.init(SpringContext.getBean(CfgTipoAtencionDAO.class));
			CfgProcedenciasAbsDao.init(SpringContext.getBean(CfgProcedenciasDAO.class));
			for (OrdenExamenCapturaResultadoDTO ordenExamen : lstOrdenesConSecciones) {

				ordenExamen.setLDVS_DESCRIPCION(
						cache.getById(ordenExamen.getDP_IDSEXO().intValueExact()).getLdvsDescripcion());

				int idx = ordenExamen.getCE_IDSECCION().intValueExact();
				String descripcion = CfgCacheCfgSecciones.getInstance().getById(idx) == null ? "Nulo"
						: CfgCacheCfgSecciones.getInstance().getById(idx).getCsecDescripcion();
				ordenExamen.setCSEC_DESCRIPCION(descripcion);

				idx = ordenExamen.getDF_IDTIPOATENCION().intValueExact();
				ordenExamen.setCTA_DESCRIPCION(CfgTipoAtencionAbsDao.getDescripcionById(idx));

				idx = ordenExamen.getDF_IDPROCEDENCIA().intValueExact();
				ordenExamen.setCP_DESCRIPCION(CfgProcedenciasAbsDao.getDescripcionById(idx));

				if (!ordenExamen.getDF_NORDEN().equals(curNroOrden)) {
					curNroOrden = ordenExamen.getDF_NORDEN();
					hmOrdenesConExamenPendiente.put(curNroOrden, ordenExamen.getDFE_CODIGOESTADOEXAMEN());
					hmOrdenesConExamenUrgente.put(curNroOrden, ordenExamen.getDFE_EXAMENESURGENTE());
				}
				if (ordenExamen.getDFE_EXAMENESURGENTE().equals("S")) {
					hmOrdenesConExamenUrgente.put(ordenExamen.getDF_NORDEN(), ordenExamen.getDFE_EXAMENESURGENTE());
				}
				if (ordenExamen.getDFE_CODIGOESTADOEXAMEN().equals(EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE)
						|| ordenExamen.getDFE_CODIGOESTADOEXAMEN()
								.equals(EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE)) {
					hmOrdenesConExamenPendiente.put(ordenExamen.getDF_NORDEN(),
							EstadosSistema.DFE_CODIGOESTADOEXAMEN_PENDIENTE);
				}
				hmOrdenesConAntecedentes.put(ordenExamen.getDF_NORDEN(), ordenExamen.getESTADOANTECEDENTE());
				hmOrdenesServicio.put(ordenExamen.getDF_NORDEN(), ordenExamen.getCS_IDSERVICIO());
				hmFechaNacimiento.put(ordenExamen.getDF_NORDEN(), ordenExamen.getDP_FECHANACIMIENTO());
				if (!hm.containsKey(ordenExamen.getOrdenInformeResultadoDTO())) {

					List<BigDecimal> seccionLst = new ArrayList<BigDecimal>();
					String tienecultivo = ordenExamen.getTIENECULTIVO();
					seccionLst.add(ordenExamen.getCE_IDSECCION());
					hm.put(ordenExamen.getOrdenInformeResultadoDTO(), seccionLst);
				} else {
					hm.get(ordenExamen.getOrdenInformeResultadoDTO()).add(ordenExamen.getCE_IDSECCION());
				}

			}

			hm.forEach((k, v) -> {
				k.setLstSecciones(v);
				lstOrdenes.add(k);
			});
			logger.debug(lstOrdenes);
			for (OrdenInformeResultadoDTO ordenInformeResultadoDTO : lstOrdenes) {

				Integer nOrden = ordenInformeResultadoDTO.getDF_NORDEN();
				ordenInformeResultadoDTO.setHayExamenesPendientes(hmOrdenesConExamenPendiente.get(nOrden));
				ordenInformeResultadoDTO.setHayExamenesUrgentes(hmOrdenesConExamenUrgente.get(nOrden));
				ordenInformeResultadoDTO.setESTADOANTECEDENTE(hmOrdenesConAntecedentes.get(nOrden));
				ordenInformeResultadoDTO.setCS_IDSERVICIO(hmOrdenesServicio.get(nOrden));
				ordenInformeResultadoDTO.setDP_FECHANACIMIENTO(hmFechaNacimiento.get(nOrden));
				
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		} finally {
			logger.debug("\nOUT getBOOrdenesConSecciones {}", LocalDateTime.now());
		}
		return lstOrdenes;
	}

	private List<OrdenExamenCapturaResultadoDTO> getOrdenesConSecciones_Urgentes(BCFichaFiltroDTO ffDto)
			throws BiosLISException, BiosLISDAOException {
		String sQuery = "";
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<OrdenExamenCapturaResultadoDTO> lst = null;
		try {

			sQuery = DaoHelper.genSqlOrdenesConSecciones_Urgentes(ffDto);
			Query qry = sesion.createSQLQuery(sQuery)
					.setResultTransformer(Transformers.aliasToBean(OrdenExamenCapturaResultadoDTO.class));

			if (ffDto.getBo_fFin() != null && !ffDto.getBo_fFin().trim().isEmpty()) {
				qry.setParameter("ffin", ffDto.getBo_fFin());
			}
			if (ffDto.getBo_fIni() != null && !ffDto.getBo_fIni().trim().isEmpty()) {
				qry.setParameter("fini", ffDto.getBo_fIni());
			}
			if (ffDto.getBo_nOrdenIni() != null) {
				qry.setParameter("nOrdenIni", ffDto.getBo_nOrdenIni());
			}
			if (ffDto.getBo_nOrdenFin() != null) {
				qry.setParameter("nOrdenFin", ffDto.getBo_nOrdenFin());
			}
			if (ffDto.getBo_procedencia() != null) {
				if (ffDto.getBo_procedencia() != -1 && ffDto.getBo_procedencia() != 0) {
					logger.debug("entre procedencia");
					qry.setParameter("idProcedencia", ffDto.getBo_procedencia());
				}
			}
			if (ffDto.getBo_tipoAtencion() != null) {
				if (ffDto.getBo_tipoAtencion() != -1 && ffDto.getBo_tipoAtencion() != 0) {
					logger.debug("entre tipo atencion");
					qry.setParameter("idTipoAtencion", ffDto.getBo_tipoAtencion());
				}
			}
			if (ffDto.getBo_examen() != null) {
				if (ffDto.getBo_examen() != -1 && ffDto.getBo_examen() != 0) {
					logger.debug("entre examen");
					qry.setParameter("idExamen", ffDto.getBo_examen());
				}
			}
			if (ffDto.getBo_seccion() != null) {
				if (ffDto.getBo_seccion() != -1 && ffDto.getBo_seccion() != 0) {
					logger.debug("entre seccion");
					qry.setParameter("idSeccion", ffDto.getBo_seccion());
				}
			}
			if (ffDto.getBo_servicio() != null) {
				if (ffDto.getBo_servicio() != -1 && ffDto.getBo_servicio() != 0) {
					logger.debug("entre servicio");
					qry.setParameter("idServicio", ffDto.getBo_servicio());
				}
			}
			if (ffDto.getBo_nombre() != null) {
				if (!ffDto.getBo_nombre().isEmpty()) {
					qry.setParameter("nombres", "%" + ffDto.getBo_nombre() + "%");
				}
			}
			if (ffDto.getBo_apellido() != null) {
				if (!ffDto.getBo_apellido().isEmpty()) {
					qry.setParameter("apellido", "%" + ffDto.getBo_apellido() + "%");
				}
			}
			if (ffDto.getBo_tipoDocumento() != null) {
				if (ffDto.getBo_tipoDocumento() != -1 && ffDto.getBo_tipoDocumento() != 0) {
					qry.setParameter("idDocumento", ffDto.getBo_tipoDocumento());
				}
			}
			if (ffDto.getBo_nroDocumento() != null) {
				if (!ffDto.getBo_nroDocumento().isEmpty()) {
					qry.setParameter("nDocumento", ffDto.getBo_nroDocumento());
				}
			}
			logger.debug("\nIN getOrdenesConSecciones_Urgentes {}", LocalDateTime.now());
			lst = qry.list();
		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			logger.debug("\nOUT getOrdenesConSecciones_Urgentes {}", LocalDateTime.now());
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lst;
	}

}
