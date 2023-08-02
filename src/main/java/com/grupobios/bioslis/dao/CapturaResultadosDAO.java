package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.EstadosSistema;
import com.grupobios.bioslis.common.RangoValorResultado;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.DatosLineaTiempoDTO;
import com.grupobios.bioslis.dto.DatosPacienteOrdenDTO;
import com.grupobios.bioslis.dto.DeltaCheckAptoDTO;
import com.grupobios.bioslis.dto.DeltaCheckDTO;
import com.grupobios.bioslis.dto.ExamenNotasDTO;
import com.grupobios.bioslis.dto.ExamenesMuestrasDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.ExamenesResultadosDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.FichaFiltroDTO;
import com.grupobios.bioslis.dto.FilaExamenesMuestrasDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.ObservacionCRDTO;
import com.grupobios.bioslis.dto.OrdenInformeResultadoDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenDTO;
import com.grupobios.bioslis.dto.ParamResultadoExamenOrden;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.dto.ResultadoTestNotificacionDeUnaOrdenDTO;
import com.grupobios.bioslis.dto.ResultadoTestPacienteDTO;
import com.grupobios.bioslis.dto.datosfichastestnotificacionesDTO;
import com.grupobios.bioslis.entity.CfgAntecedentes;
import com.grupobios.bioslis.entity.CfgCellcounter;
import com.grupobios.bioslis.entity.CfgEstadosresultadostest;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.CfgValoresreferencia;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;
import com.grupobios.bioslis.entity.DatosFichasexamenestestId;
import com.grupobios.bioslis.entity.DatosFichasmuestras;
import com.grupobios.bioslis.entity.DatosUsuarios;
import com.grupobios.bioslis.entity.LogEventosfichas;
import com.grupobios.common.DateFormatterHelper;
import com.grupobios.common.Edad;

public class CapturaResultadosDAO {

	public static final String sqlUpdateResultadoEstadoTransmitidoExamenesDeUnaOrden = "UPDATE DATOS_FICHASEXAMENESTEST dfet "
			+ "SET " + " dfet.DFET_IDESTADORESULTADOTEST = :estado " + " WHERE dfet.DFET_NORDEN = :nOrden "
			+ "AND  dfet.DFET_IDEXAMEN = :idExamen " + "AND  dfet.DFET_IDTEST = :idTest ";

	public static final String sqlExamenesMuestrasDeUnaOrden = ""
			+ "SELECT dfe.DFE_NORDEN,ce.CE_ABREVIADO,cm.CMUE_DESCRIPCIONABREVIADA , cenv.CENV_DESCRIPCION , cs.CSEC_DESCRIPCION"
			+ ",cte.CT_DESCRIPCION, cte.CT_CODIGO,cte.CT_IDTIPOMUESTRA ,dfe.DFE_IDEXAMEN"
			+ ",dfe.DFE_CODIGOESTADOEXAMEN, dfe.DFE_CANTIDAD,dfe.DFE_EXAMENESURGENTE,cee.CEE_DESCRIPCIONESTADOEXAMEN,"
			+ "dfm.DFM_CODIGOBARRA, ce.CE_CODIGOEXAMEN, cte.CT_FORMULA,dfe.DFE_EXAMENANULADO,ce.CE_IDSECCION , dfm.dfm_idmuestra,"
			+ "ce.CE_TIENETESTOPCIONALES, " + "CAST( " + "CASE " + "WHEN dfm.DFM_TMOBSERVACION IS NOT NULL "
			+ "OR dp.DP_OBSERVACION IS NOT NULL " + "OR dfm.DFM_RECEPCIONOBS IS NOT NULL "
			+ "OR df.DF_OBSERVACION IS NOT NULL  " + "THEN 'S' " + "ELSE 'N' " + "END AS VARCHAR2(1) "
			+ ") AS ESTADOOBSERVACION, " + "CAST( " + "CASE " + "	WHEN ce.CE_NOTA IS NOT NULL THEN 'S' " + "ELSE 'N' "
			+ "END AS VARCHAR2(1) ) AS ESTADONOTA, ce.CE_CELLCOUNTER ESTADOCONTADOR " + "FROM  DATOS_FICHAS df "
			+ "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
			+ "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
			+ "JOIN DATOS_FICHASEXAMENESTEST dfet ON(dfet.DFET_IDEXAMEN = dfe.DFE_IDEXAMEN AND dfet.DFET_NORDEN = dfe.DFE_NORDEN ) "
			+ "JOIN CFG_EXAMENESTEST cet ON (cet.CET_IDEXAMEN = dfet.DFET_IDEXAMEN AND cet.CET_IDTEST = dfet.DFET_IDTEST) "
			// + "JOIN BIOSLIS.CFG_MUESTRAS cm ON (cm.CMUE_IDTIPOMUESTRA =
			// dfet.DFET_IDTIPOMUESTRA) "
			+ "JOIN CFG_TEST cte ON (cet.CET_IDTEST = cte.CT_IDTEST) "
			+ "JOIN CFG_SECCIONES cs ON (cs.CSEC_IDSECCION = cte.CT_IDSECCION) "
			+ "JOIN CFG_MUESTRAS cm ON (cm.CMUE_IDTIPOMUESTRA = dfet.DFET_IDTIPOMUESTRA) "
			+ "JOIN CFG_TIPOSDERESULTADO ctr ON (ctr.CTR_IDTIPORESULTADO = cte.CT_IDTIPORESULTADO)"
			+ "JOIN CFG_ENVASES cenv ON (cenv.CENV_IDENVASE = dfet.DFET_IDENVASE) "
			+ "JOIN CFG_ESTADOSEXAMENES cee ON(cee.CEE_CODIGOESTADOEXAMEN = dfe.DFE_CODIGOESTADOEXAMEN) "
			+ "LEFT OUTER JOIN DATOS_FICHASMUESTRAS dfm ON (dfm.DFM_IDMUESTRA = dfet.DFET_IDMUESTRA) "
			+ "INNER JOIN DATOS_PACIENTES dp ON dp.DP_IDPACIENTE = df.DF_IDPACIENTE " + "WHERE "
			+ "df.DF_NORDEN = :nOrden " + "ORDER BY dfe.DFE_EXAMENESURGENTE,ce.CE_ABREVIADO desc ";

	public static final String sqlUpdateResultadosExamenesDeUnaOrden = "UPDATE DATOS_FICHASEXAMENESTEST dfet "
			+ "SET dfet.DFET_RESULTADO = :resultado " + " WHERE dfet.DFET_NORDEN = :nOrden "
			+ "AND  dfet.DFET_IDEXAMEN = :idExamen " + "AND  dfet.DFET_IDTEST = :idTest ";
	
	public static final String sqlUpdateDfe_Notas = ""
			+ " UPDATE"
			+ "	DATOS_FICHASEXAMENES"
			+ " SET"
			+ "	DFE_NOTAINTERNA = :notaInterna,"
			+ "	DFE_NOTAEXAMEN = :notaExamen,"
			+ "	DFE_NOTAINFORME = :notaInforme"
			+ " WHERE"
			+ "	DFE_NORDEN = :nOrden"
			+ "	AND DFE_IDEXAMEN = :idExamen";

	public static final String sqlUpdateEstadoResultadosExamenesDeUnaOrden = "UPDATE DATOS_FICHASEXAMENESTEST dfet "
			+ "SET dfet.DFET_IDESTADORESULTADOTEST = :estado" + " WHERE dfet.DFET_NORDEN = :nOrden "
			+ "AND  dfet.DFET_IDEXAMEN = :idExamen " + "AND  dfet.DFET_IDTEST = :idTest ";

	public static final String hsqlGetResultadosExamenesDeUnaOrden = "select dfet from DatosFichasexamenestest dfet "
			+ "WHERE dfet.dfetNorden = :nOrden AND  dfet.dfetIdexamen = :idExamen " + "AND  dfet.dfetIdtest = :idTest ";

	public static final String sqlResultadosSelExamenesDeUnaOrden = ""
			+ "SELECT df.DF_NORDEN,dfe.DFE_IDEXAMEN,dfet.DFET_IDTEST,ce.CE_ABREVIADO , ct.ct_abreviado,  ct.ct_descripcion , " // se
																																// agrega
																																// ct.ct_descripcion
																																// por
																																// cristian
																																// 21-02
			+ "ctr.CTR_DESCRIPCION ,ct.CT_FORMULA ,cu.CUM_DESCRIPCION ,ct.CT_IDUNIDADMEDIDA ,dfet.DFET_FECHAULTIMORESULTADO , dfet.DFET_ULTIMORESULTADOANT ,"
			+ "cert.CERT_DESCRIPCIONESTADOTEST,cert.CERT_IDESTADORESULTADOTEST,  CE.CE_ABREVIADO2 ,"
			+ "dfet.DFET_RESULTADONUMERICO ,dfet.DFET_IDUNIDADDEMEDIDA,dfet.DFET_REFERENCIADESDE,  "
			+ "dfet.DFET_REFERENCIAHASTA,ctr.CTR_CODIGO,dfet.DFET_RESULTADO, "
			+ "dfet.DFET_RCRITICO, dp.DP_IDPACIENTE, dfet.DFET_IDMUESTRA,ct.CT_DECIMALES ,dfm.DFM_CODIGOBARRA,"
			+ "ct.CT_RESULTADOOMISION,ct.CT_RESULTADOOBLIGADO,ct.CT_ESCULTIVO,ct.CT_IDSECCION,dfe.DFE_EXAMENANULADO  , ct.CT_SORT ,"
			// + "(select cg.cg_idglosa from cfg_glosas cg where (cg.cg_descripcion =
			// dfet.dfet_resultado or cg.cg_descripcion = ct.ct_resultadoomision) and rownum
			// = 1 ) as CG_IDGLOSA ,"
			// agregado x cristian y sacado por jan

			+ "  (select cmet.cmet_descripcion from cfg_metodostest cmt inner join cfg_metodos cmet on cmet.cmet_idmetodo = cmt.cmt_idmetodo "
			+ "      where cmt.cmt_idtest = dfet.dfet_idtest and cmt.cmt_esvalorpordefecto = 'S') as metodo "
			+ " FROM DATOS_FICHAS df " + "INNER JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE) "
			+ "INNER JOIN DATOS_FICHASEXAMENES dfe ON (df.DF_NORDEN = dfe.DFE_NORDEN) "
			+ "INNER JOIN DATOS_FICHASEXAMENESTEST dfet ON (dfet.DFET_NORDEN = dfe.DFE_NORDEN AND dfet.DFET_IDEXAMEN = dfe.DFE_IDEXAMEN) "
			+ "INNER JOIN cfg_examenes ce ON (dfe.DFE_IDEXAMEN = ce.CE_IDEXAMEN) "
			+ "inner join cfg_test ct on ct.ct_idtest = dfet.DFET_IDTEST "
			+ "INNER JOIN BIOSLIS.CFG_TIPOSDERESULTADO ctr ON (ctr.CTR_IDTIPORESULTADO = ct.CT_IDTIPORESULTADO) "
			// + "INNER JOIN BIOSLIS.CFG_ESTADOSRESULTADOSTEST cert ON
			// (cert.CERT_IDESTADORESULTADOTEST = dfet.DFET_IDESTADORESULTADOTEST) "
			+ "INNER JOIN CFG_ESTADOSRESULTADOSTEST cert ON (cert.CERT_IDESTADORESULTADOTEST = dfet.DFET_IDESTADORESULTADOTEST) "
			+ "INNER JOIN BIOSLIS.CFG_UNIDADESDEMEDIDA cu ON (cu.CUM_IDUNIDADMEDIDA = ct.CT_IDUNIDADMEDIDA) "
			+ "LEFT OUTER JOIN BIOSLIS.DATOS_FICHASMUESTRAS dfm ON (dfm.DFM_IDMUESTRA = dfet.DFET_IDMUESTRA)"
			+ " WHERE " + " df.DF_NORDEN = :nOrden AND dfe.DFE_IDEXAMEN IN (:lista) " + "ORDER BY 1";

	public static final String sqlResultadosExamenesDeUnaOrden = ""
			+ "SELECT df.DF_NORDEN,dfe.DFE_IDEXAMEN,dfet.DFET_IDTEST,ce.CE_ABREVIADO , ct.ct_abreviado, ct.ct_descripcion , "
			+ "ctr.CTR_DESCRIPCION ,ct.CT_FORMULA ,cu.CUM_DESCRIPCION ,ct.CT_IDUNIDADMEDIDA ,dfet.DFET_FECHAULTIMORESULTADO , dfet.DFET_ULTIMORESULTADOANT ,"
			+ "cert.CERT_DESCRIPCIONESTADOTEST,cert.CERT_IDESTADORESULTADOTEST, CE.CE_ABREVIADO2 ,"
			+ "dfet.DFET_RESULTADONUMERICO ,dfet.DFET_IDUNIDADDEMEDIDA,dfet.DFET_REFERENCIADESDE, "
			+ "dfet.DFET_REFERENCIAHASTA,ctr.CTR_CODIGO,dfet.DFET_RESULTADO, "
			+ "cvr.CVR_VALORCRITICOBAJO,cvr.CVR_VALORCRITICOALTO,cvr.CVR_VALORBAJO,cvr.CVR_VALORALTO,cvr.CVR_VALORTEXTO, cvr.CVR_DIASDESDE,"
			+ "cvr.CVR_MESESDESDE," + "cvr.CVR_ANOSDESDE," + "cvr.CVR_DIASHASTA," + "cvr.CVR_MESESHASTA,"
			+ "cvr.CVR_ANOSHASTA," + "ce.CE_CODIGOEXAMEN " + "FROM DATOS_FICHAS df "
			+ "INNER JOIN DATOS_FICHASEXAMENES dfe ON (df.DF_NORDEN = dfe.DFE_NORDEN) "
			+ "INNER JOIN DATOS_FICHASEXAMENESTEST dfet ON (dfet.DFET_NORDEN = dfe.DFE_NORDEN AND dfet.DFET_IDEXAMEN = dfe.DFE_IDEXAMEN) "
			+ "INNER JOIN cfg_examenes ce ON (dfe.DFE_IDEXAMEN = ce.CE_IDEXAMEN) "
			+ "inner join cfg_test ct on ct.ct_idtest = dfet.DFET_IDTEST "
			+ "INNER JOIN BIOSLIS.CFG_VALORESREFERENCIA cvr ON (cvr.CVR_IDTEST= ct.ct_idtest) "
			+ "INNER JOIN BIOSLIS.CFG_TIPOSDERESULTADO ctr ON (ctr.CTR_IDTIPORESULTADO = ct.CT_IDTIPORESULTADO) "
			+ "INNER JOIN BIOSLIS.CFG_ESTADOSRESULTADOSTEST cert ON (cert.CERT_IDESTADORESULTADOTEST = dfet.DFET_IDESTADORESULTADOTEST) "
			+ "INNER JOIN BIOSLIS.CFG_UNIDADESDEMEDIDA cu ON (cu.CUM_IDUNIDADMEDIDA = ct.CT_IDUNIDADMEDIDA)" + " WHERE "
			+ " DF_NORDEN = :nOrden " + "ORDER BY 1";

	public static final String sqlUpdateResultadosNumericosExamenesDeUnaOrden = "UPDATE DATOS_FICHASEXAMENESTEST dfet "
			+ "SET dfet.DFET_RESULTADO = :resultado , dfet.DFET_RESULTADONUMERICO = :resultadonro, dfet.DFET_RCRITICO = :rcritico, dfet.DFET_IDESTADORESULTADOTEST = :estado ,"
			+ "dfet.DFET_IDUSUARIODIGITA=:idUsuario , dfet.DFET_FECHAINGRESORESULTADO=:fecha "
			+ " WHERE dfet.DFET_NORDEN = :nOrden " + "AND  dfet.DFET_IDEXAMEN = :idExamen "
			+ "AND  dfet.DFET_IDTEST = :idTest ";

	public static final String sqlHistoriaDeUnTestDePaciente = "SELECT dfet.DFET_IDTEST,dfet.DFET_FECHAINGRESORESULTADO, dfet.DFET_RESULTADO,df.DF_NORDEN,dfe.DFE_IDEXAMEN, "
			+ " dfet.DFET_RESULTADONUMERICO ,dfet.DFET_IDUNIDADDEMEDIDA " + "  FROM DATOS_FICHAS df  "
			+ "INNER JOIN DATOS_FICHASEXAMENES dfe ON (df.DF_NORDEN = dfe.DFE_NORDEN)  "
			+ "INNER JOIN DATOS_FICHASEXAMENESTEST dfet ON (dfet.DFET_NORDEN = dfe.DFE_NORDEN AND dfet.DFET_IDEXAMEN = dfe.DFE_IDEXAMEN)  "
			+ "INNER JOIN cfg_examenes ce ON (dfe.DFE_IDEXAMEN = ce.CE_IDEXAMEN)  "
			+ "inner join cfg_test ct on ct.ct_idtest = dfet.DFET_IDTEST  "
			+ "INNER JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE) "
			+ "INNER JOIN CFG_ESTADOSRESULTADOSTEST cert ON (cert.CERT_IDESTADORESULTADOTEST = dfet.DFET_IDESTADORESULTADOTEST) "
			+ "WHERE  df.DF_IDPACIENTE =  :idPaciente  "
			+ "AND dfet.DFET_IDTEST = :idTest  AND (cert.CERT_ESTADORESULTADOTEST LIKE 'F' or cert.CERT_ESTADORESULTADOTEST LIKE 'D' or cert.CERT_ESTADORESULTADOTEST LIKE 'T') "
			+ "ORDER BY dfet.DFET_FECHAINGRESORESULTADO ";

	public static final String NRO_REGEX = "(([1-9]([0-9])*)|0)(,([0-9])+)*";
	private static final String AMBOS_SEXOS = "A";
	private static final BigDecimal TRANSMITIDO = new BigDecimal(3);
	private DatosFichasExamenesTestDAO datosFichasExamenesTestDAO;
	private CfgValoresreferenciaDAO cvrDAO;
	private DatosFichas_DAO datosFichas_dao;
	private DatosPacientesDAO datosPacientesDAO;
	private DatosFichasDAO datosFichasDAO;
//  private CfgGlosasDAO cfgGlosasDao;

	public DatosFichasDAO getDatosFichasDAO() {
		return datosFichasDAO;
	}

	public void setDatosFichasDAO(DatosFichasDAO datosFichasdao) {
		this.datosFichasDAO = datosFichasdao;
	}

	private static Logger logger = LogManager.getLogger(CapturaResultadosDAO.class);

	public DatosFichasExamenesTestDAO getDatosFichasExamenesTestDAO() {
		return datosFichasExamenesTestDAO;
	}

	public void setDatosFichasExamenesTestDAO(DatosFichasExamenesTestDAO dfeDAO) {
		this.datosFichasExamenesTestDAO = dfeDAO;
	}

	public CfgValoresreferenciaDAO getCvrDAO() {
		return cvrDAO;
	}

	public void setCvrDAO(CfgValoresreferenciaDAO cvrDAO) {
		this.cvrDAO = cvrDAO;
	}

	public List<OrdenInformeResultadoDTO> getOrdenesUltimosNDias(Integer rangoultimosdias)
			throws BiosLISException, BiosLISDAOException {
		FichaFiltroDTO ffDto = new FichaFiltroDTO();
		try {

			Calendar c = Calendar.getInstance();
			ffDto.setfFin(DateFormatterHelper.dateToText(c.getTime()));
			c.add(Calendar.DAY_OF_MONTH, -rangoultimosdias);
			ffDto.setfIni(DateFormatterHelper.dateToText(c.getTime()));
			ffDto.setApellido(null);
			ffDto.setLocalizacion(null);
			ffDto.setNombre(null);
			ffDto.setnOrden(null);
			ffDto.setNroDocumento(null);
			ffDto.setTipoAtencion(null);
			ffDto.setTipoDocumento(null);
		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		}
		return this.datosFichas_dao.getOrdenesInformeResultados(ffDto);
	}

	public DatosFichas_DAO getDatosFichas_dao() {
		return datosFichas_dao;
	}

	public void setDatosFichas_dao(DatosFichas_DAO datosFichas_dao) {
		this.datosFichas_dao = datosFichas_dao;
	}

	public List<ExamenesMuestrasDeUnaOrdenDTO> getMuestrasExamenesDeUnaOrden(Long nroOrden) throws BiosLISDAOException {

		List<ExamenesMuestrasDeUnaOrdenDTO> lst = new ArrayList<ExamenesMuestrasDeUnaOrdenDTO>();
		if (nroOrden != -1) {
			Session sesion = HibernateUtil.getSessionFactory().openSession();
			try {

				Query qry = sesion.createSQLQuery(sqlExamenesMuestrasDeUnaOrden)
						.setResultTransformer(Transformers.aliasToBean(ExamenesMuestrasDeUnaOrdenDTO.class));
				qry.setLong("nOrden", nroOrden);
				lst = (List<ExamenesMuestrasDeUnaOrdenDTO>) qry.list();
				sesion.close();
			} catch (HibernateException e) {

				logger.error(e.getLocalizedMessage());
				throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
			} finally {
				if (sesion != null && sesion.isOpen()) {
					sesion.close();
				}
			}
		}
		return lst;
	}

	public List<FilaExamenesMuestrasDeUnaOrdenDTO> getFilasMuestrasExamenesDeUnaOrden(
			List<ExamenesMuestrasDeUnaOrdenDTO> lst) throws BiosLISDAOException {

		Set<String> muestras = new HashSet<>();
		Set<String> envases = new HashSet<>();
		Set<String> secciones = new HashSet<>();
		Set<String> muestrasCB = new HashSet<>();

		BigDecimal curIdExamen = new BigDecimal(-1L);
		String curEnvase = "";
		FilaExamenesMuestrasDeUnaOrdenDTO fila = null;
		final List<FilaExamenesMuestrasDeUnaOrdenDTO> lstFilas = new ArrayList<>();
		try {
			for (ExamenesMuestrasDeUnaOrdenDTO e : lst) {

				if (e.getDFE_EXAMENANULADO().equals("S")
						&& e.getDFE_CODIGOESTADOEXAMEN().equals(EstadosSistema.DFE_CODIGOESTADOEXAMEN_FIRMADO)) {
					e.setDFE_EXAMENANULADO("F");
				}
				if (!e.getDFE_IDEXAMEN().equals(curIdExamen)) {
					if (fila != null) {

						StringBuilder sbMuestraCB = new StringBuilder("");
						muestrasCB.forEach(m -> {
							sbMuestraCB.append(m).append("<BR/>");
						});
						fila.setDFM_CODIGOBARRA(sbMuestraCB.toString());
						StringBuilder sbMuestra = new StringBuilder("");
						muestras.forEach(m -> {
							sbMuestra.append(m).append("<BR/>");
						});
						fila.setCMUE_DESCRIPCIONABREVIADA(sbMuestra.toString());
						StringBuilder sbEnvase = new StringBuilder("");
						envases.forEach(m -> {
							if (!curEnvase.equals(m)) {
								sbEnvase.append(m).append("<BR/>");
							}
						});
						fila.setCENV_DESCRIPCION(sbEnvase.toString());
						StringBuilder sbSeccion = new StringBuilder("");
						secciones.forEach(m -> {
							sbSeccion.append(m).append("<BR/>");
						});
						fila.setCSEC_DESCRIPCION(sbSeccion.toString());
						muestrasCB = new HashSet<>();
						muestras = new HashSet<>();
						envases = new HashSet<>();
						secciones = new HashSet<>();
					}
					fila = new FilaExamenesMuestrasDeUnaOrdenDTO();
					lstFilas.add(fila);
					fila.setDFE_IDEXAMEN(e.getDFE_IDEXAMEN());
					fila.setDFE_NORDEN(e.getDFE_NORDEN());
					fila.setCE_ABREVIADO(e.getCE_ABREVIADO());
					fila.setCT_CODIGO(e.getCT_CODIGO());
					fila.setDFE_CODIGOESTADOEXAMEN(e.getDFE_CODIGOESTADOEXAMEN());
					fila.setDFE_EXAMENESURGENTE(e.getDFE_EXAMENESURGENTE());
					fila.setCEE_DESCRIPCIONESTADOEXAMEN(e.getCEE_DESCRIPCIONESTADOEXAMEN());
					fila.setCE_CODIGOEXAMEN(e.getCE_CODIGOEXAMEN());
					fila.setCT_FORMULA(e.getCT_FORMULA());
					fila.setDFE_EXAMENANULADO(e.getDFE_EXAMENANULADO());
					fila.setCE_IDSECCION(e.getCE_IDSECCION());
					fila.setCE_TIENETESTOPCIONALES(e.getCE_TIENETESTOPCIONALES());
					fila.setESTADOOBSERVACION(e.getESTADOOBSERVACION());
					fila.setESTADONOTA(e.getESTADONOTA());
					fila.setESTADOCONTADOR(e.getESTADOCONTADOR());
					curIdExamen = e.getDFE_IDEXAMEN();
				}
				muestrasCB.add(e.getDFM_CODIGOBARRA());
				muestras.add(e.getCMUE_DESCRIPCIONABREVIADA());
				envases.add(e.getCENV_DESCRIPCION());
				secciones.add(e.getCSEC_DESCRIPCION());
			}
			if (fila != null) {
				StringBuilder sbMuestra = new StringBuilder("");
				muestras.forEach(m -> {
					sbMuestra.append(m).append("<BR/>");
				});
				fila.setCMUE_DESCRIPCIONABREVIADA(sbMuestra.toString());
				StringBuilder sbMuestraCB = new StringBuilder("");
				muestrasCB.forEach(m -> {
					sbMuestraCB.append(m).append("<BR/>");
				});
				fila.setDFM_CODIGOBARRA(sbMuestraCB.toString());
				StringBuilder sbEnvase = new StringBuilder("");
				envases.forEach(m -> {
					sbEnvase.append(m).append("<BR/>");
				});
				fila.setCENV_DESCRIPCION(sbEnvase.toString());
				StringBuilder sbSeccion = new StringBuilder("");
				secciones.forEach(m -> {
					sbSeccion.append(m).append("<BR/>");
				});
				fila.setCSEC_DESCRIPCION(sbSeccion.toString());
			}
		} catch (HibernateException e) {
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		}
		return lstFilas;

	}

	public List<FilaExamenesMuestrasDeUnaOrdenDTO> getFilasMuestrasExamenesDeUnaOrden(long nroOrden)
			throws BiosLISDAOException {
		List<FilaExamenesMuestrasDeUnaOrdenDTO> lst = new ArrayList<FilaExamenesMuestrasDeUnaOrdenDTO>();
		if (nroOrden != -1) {
			try {
				lst = this.getFilasMuestrasExamenesDeUnaOrden(this.getMuestrasExamenesDeUnaOrden(nroOrden));

				FilaExamenesMuestrasDeUnaOrdenDTO fe = new FilaExamenesMuestrasDeUnaOrdenDTO();
				FilaExamenesMuestrasDeUnaOrdenDTO.Comparador c = fe.new Comparador();
				lst.sort(c);
			} catch (HibernateException e) {
				logger.error(e.getLocalizedMessage());
				throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
			}
		}
		return lst;

	}

	public List<ExamenesResultadosDeUnaOrdenDTO> getResultadosExamenesOrden(Long nroOrden) throws BiosLISDAOException {

		List<ExamenesResultadosDeUnaOrdenDTO> lst = null;
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {

			Query qry = sesion.createSQLQuery(sqlResultadosExamenesDeUnaOrden)
					.setResultTransformer(Transformers.aliasToBean(ExamenesResultadosDeUnaOrdenDTO.class));
			qry.setLong("nOrden", nroOrden);
			lst = (List<ExamenesResultadosDeUnaOrdenDTO>) qry.list();
			sesion.close();
		} catch (HibernateException e) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error(e.getLocalizedMessage());
			throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lst;

	}

	public void updateResultadosExamenesOrdenes(List<ExamenesResultadosDeUnaOrdenDTO> lstResultados)
			throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {

			Query qry = sesion.createSQLQuery(sqlUpdateResultadosExamenesDeUnaOrden);

			sesion.beginTransaction();
			for (ExamenesResultadosDeUnaOrdenDTO eResultado : lstResultados) {
				qry.setString("resultado", eResultado.getDFET_RESULTADO());
				qry.setBigDecimal("nOrden", eResultado.getDF_NORDEN());
				qry.setBigDecimal("idExamen", eResultado.getDFE_IDEXAMEN());
				qry.setBigDecimal("idTest", eResultado.getDFET_IDTEST());
				qry.executeUpdate();

			}
			sesion.getTransaction().commit();

		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error(he.getMessage());
			throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}

	}

	public List<ExamenesResultadosDeUnaOrdenDTO> getResultadosExamenesOrden(Long nroOrden,
			ParamResultadoExamenOrden prmResultadoExamenOrden) throws BiosLISDAOException {
		List<ExamenesResultadosDeUnaOrdenDTO> lst = null;
		if (nroOrden != -1) {

			lst = this.getExamenesResultadosDeUnaOrdenDTO(prmResultadoExamenOrden, nroOrden);

			DatosPacienteOrdenDTO pacienteDto = datosPacientesDAO.getPacienteOrden(new BigDecimal(nroOrden));

			List<ResultadoTestPacienteDTO> lstResultado;

			for (ExamenesResultadosDeUnaOrdenDTO resultado : lst) {
				try {

					resultado.setFORMULATRADUCIDA(null);
					if (resultado.getCT_FORMULA() != null) {
						resultado.setFORMULATRADUCIDA(getFormula(resultado.getCT_FORMULA()));
					}

					if (resultado.getDFE_EXAMENANULADO().equals("S") && resultado.getCERT_IDESTADORESULTADOTEST()
							.equals(EstadosSistema.DFE_IDESTADOEXAMEN_FIRMADO)) {
						resultado.setDFE_EXAMENANULADO("F");
					}
					lstResultado = this.getHistoriaDeUnTestDePaciente(resultado.getDP_IDPACIENTE(),
							resultado.getDFET_IDTEST());

					if (lstResultado != null && lstResultado.size() > 0) {
						this.setResultadoHistorico(lstResultado, resultado);
						this.setDeltaCheck(lstResultado, resultado);
					}

					// agregado por cristian 22-02 14:09 para calcular valores referencia cada vez
					// que se carge la orden el captura resultados
					CfgValoresreferencia vr = null;
					logger.debug("Se busca valores de referencia para {} {}", resultado.getDFET_IDTEST().longValue(),
							resultado.getDP_IDPACIENTE().longValue());
//          vr = cvrDAO.getValorReferencia(BigDecimal.valueOf(resultado.getDFET_IDTEST().longValue()),
//              BigDecimal.valueOf(resultado.getDP_IDPACIENTE().longValue()));
					vr = cvrDAO.getValorReferenciaPaciente(BigDecimal.valueOf(resultado.getDFET_IDTEST().longValue()),
							pacienteDto);

					if (vr != null) {

						if (vr.getCvrValorbajo() != null) {

							String valorBajoComa = vr.getCvrValorbajo().toString().replace(".", ",");
							resultado.setDFET_REFERENCIADESDE(valorBajoComa);
						}
						if (vr.getCvrValoralto() != null) {
							String valorAltaComa = vr.getCvrValoralto().toString().replace(".", ",");
							resultado.setDFET_REFERENCIAHASTA(valorAltaComa);
						}
					}
				} catch (BiosLISDAONotFoundException e) {

					logger.info(e.getMessage());
				}
			}
			logger.debug("Se recuperaron {} resultados ", lst.size());
		}
		logger.debug("RESULTADO LIST RETURN {}", lst);
		return lst;

	}

	// REVISAR ESTO EN TOMCAT -validado 11-11
	// aqui se realiza cambios para agregar usuariodigita resultado y fecha digita
	public RangoValorResultado updateResultadoTestExamenOrden(ResultadoNumericoTestExamenOrdenDTO resultado)
			throws BiosLISDAOException, ParseException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();

		// agregado por cristian para recuperar valores anteriores ****************
		DatosFichasexamenestestId dfetid = new DatosFichasexamenestestId(resultado.getDF_NORDEN().longValue(),
				resultado.getDFE_IDEXAMEN().longValue(), resultado.getDFET_IDTEST().longValue());
		DatosFichasexamenestest dfet = (DatosFichasexamenestest) sesion.get(DatosFichasexamenestest.class, dfetid);
		String valorNuevo = "";
		// *********************************************************

		RangoValorResultado result = RangoValorResultado.N;
		logger.debug("INI ResultadoNumericoTestExamenOrdenDTO a ingresar {}", resultado.toString());

		// modificacion por cristian 07-11 agregar idusuariodigita y fecharesultado
		Timestamp ts = BiosLisCalendarService.getInstance().getTS();

		try {
			Query qry = sesion.createSQLQuery(sqlUpdateResultadosNumericosExamenesDeUnaOrden);
			sesion.beginTransaction();

			// agregado por cristian para grabar nombre de glosa y no el id.
			if (resultado.getTextResultado() != null && resultado.getTextResultado() != "") {
				qry.setString("resultado", resultado.getTextResultado());
			} else {
				qry.setString("resultado", resultado.getDFET_RESULTADO());
			}
			// *******************************************************************
			qry.setBigDecimal("nOrden", resultado.getDF_NORDEN());
			qry.setBigDecimal("idExamen", resultado.getDFE_IDEXAMEN());
			qry.setBigDecimal("idTest", resultado.getDFET_IDTEST());

			if (resultado.getTipoResultado() != null) {
				qry.setInteger("estado", EstadosSistema.DFET_IDRESULTADOTEST_TRANSMITIDO);
			} else {
				qry.setInteger("estado", EstadosSistema.DFET_IDRESULTADOTEST_DIGITADO);
			}

			if (resultado.getDFET_RESULTADO() == "" || resultado.getDFET_RESULTADO() == null) {
				qry.setInteger("estado", 1);
			}
			if (!resultado.getCTR_CODIGO().equals("N") && !resultado.getCTR_CODIGO().equals("F")) {
				qry.setBigDecimal("resultadonro", null);
			} else {
				logger.debug("entre a formatear numero ");
				NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("es-ES"));
				Pattern p = Pattern.compile(NRO_REGEX);
				if (resultado.getDFET_RESULTADO() == null || resultado.getDFET_RESULTADO().trim().equals("")) {
					qry.setBigDecimal("resultadonro", null);
				} else {

					Matcher m = p.matcher(resultado.getDFET_RESULTADO().replaceAll(Constante.SEPARADOR_MILES, ""));
					boolean b = m.matches();
					logger.debug("String:{} Nro:{}",
							resultado.getDFET_RESULTADO().replaceAll(Constante.SEPARADOR_MILES, ""),
							nf.parse(resultado.getDFET_RESULTADO()));
					if (!b) {

						if (sesion != null && sesion.isOpen()) {
							sesion.close();
						}
						throw new BiosLISException("Formato número inválido");
					}

					try {
						result = this.checkValoresreferencia(resultado, sesion);
					} catch (BiosLISDAONotFoundException e) {
						logger.debug("No se encontró valores de referencia");
					} catch (BiosLISDAOException | ParseException e) {
						if (sesion != null && sesion.isOpen()) {
							sesion.close();
						}
						logger.debug("Error en nusqueda de valores de referencia");
						throw e;
					}
					Number number = nf.parse(resultado.getDFET_RESULTADO().replaceAll(Constante.SEPARADOR_MILES, ""));
					qry.setBigDecimal("resultadonro", BigDecimal.valueOf(number.doubleValue()));
					logger.debug("Resultado numerico a ingresar {}", BigDecimal.valueOf(number.doubleValue()));

					valorNuevo = number.toString();
				}
			}

			// agregar aca select
			if (resultado.getCTR_CODIGO().equals("G")) {

				CfgGlosasDAO cfgt = new CfgGlosasDAO();
				String glosaCritico = cfgt.getCriticoGT(resultado.getDFET_IDTEST().intValue(),
						Integer.parseInt(resultado.getDFET_RESULTADO()));
				logger.debug("GLOSA CRITICA jan {} ", glosaCritico);
				if (glosaCritico.equals("S")) {
					qry.setString("rcritico", "CA");
				} else {
					qry.setString("rcritico", null);
				}
			} else {
				if (resultado.getDFET_RESULTADO() == "") {
					qry.setString("rcritico", null);
				} else {
					logger.debug("RESULTADO jan {} ", result);
					qry.setString("rcritico", result.toString());
				}
			}
			qry.setParameter("idUsuario", resultado.getIdUsuarioDigita());
			qry.setParameter("fecha", ts);

			logger.debug("ResultadoNumericoTestExamenOrdenDTO a ingresar {}", resultado.toString());

			qry.executeUpdate();
			sesion.getTransaction().commit();
			sesion.close();

			// aqui se realiza agregar datos a log eventos fichas.. creado por cristian
			// 14_02-2023 ***************************

			// if (!resultado.getCTR_CODIGO().equals("F")) { // esto se realiza para no
			// grabar totales formula en logeventos..
			// porque lo realiza varias veces.
			LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
			LogEventosfichas lef = new LogEventosfichas();

			lef.setDatosFichas(resultado.getDF_NORDEN().intValue());
			lef.setLefFechaorden(dfet.getDfetFechaorden());
			lef.setLefIdpaciente((int) dfet.getDfetIdpaciente());
			lef.setLefNombretabla("DATOS_FICHASEXAMENESTESTS");
			lef.setLefIdexamen(resultado.getDFE_IDEXAMEN().intValue());
			lef.setLefIdtest(resultado.getDFET_IDTEST().intValue());
			lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
			lef.setLefIdusuario(resultado.getIdUsuarioDigita().intValue());
			lef.setLefIdmuestra(dfet.getDfetIdmuestra().intValue());
			DatosFichasmuestrasDAO datosFichasmuestrasDAO = new DatosFichasmuestrasDAO();
			DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraById(dfet.getDfetIdmuestra());
			lef.setLefCodigobarra(dfm.getDfmCodigobarra());
			lef.setCfgEventos(2);
			/// ********************************************
			lef.setLefNombrecampo("DFET_RESULTADO");
			if (valorNuevo != null && !valorNuevo.isEmpty()) {
				lef.setLefValornuevo(valorNuevo);
			} else {
				if (resultado.getTextResultado() != null && resultado.getTextResultado() != "") {
					lef.setLefValornuevo(resultado.getTextResultado());
				} else {
					lef.setLefValornuevo(resultado.getDFET_RESULTADO());
				}
			}

			lef.setLefValoranterior(dfet.getDfetResultado());

			if (!lef.getLefValornuevo().equals(lef.getLefValoranterior())) {
				if (!lef.getLefValornuevo().trim().isEmpty() || lef.getLefValoranterior() != null) {
					logEventosfichasDAO.insertLogEventosFichas(lef);

					// ***************************************
					lef.setLefNombrecampo("DFET_IDESTADORESULTADOTEST");
					if (resultado.getDFET_RESULTADO() == "" || resultado.getDFET_RESULTADO() == null) {
						lef.setLefValornuevo("PENDIENTE");
					} else {
						if (resultado.getTipoResultado() != null && resultado.getTipoResultado() != "") {
							lef.setLefValornuevo("TRANSMITIDO");
						} else {
							lef.setLefValornuevo("DIGITADO");
						}

					}

					sesion = HibernateUtil.getSessionFactory().openSession();
					CfgEstadosresultadostest estadoResultado = new CfgEstadosresultadostest();
					estadoResultado = (CfgEstadosresultadostest) sesion.get(CfgEstadosresultadostest.class,
							dfet.getDfetIdestadoresultadotest().byteValue());
					sesion.close();

					lef.setLefValoranterior(estadoResultado.getCertDescripcionestadotest());
					if (!lef.getLefValornuevo().equals(lef.getLefValoranterior())) {
						logEventosfichasDAO.insertLogEventosFichas(lef);
					}

					// ***************************************
					lef.setLefNombrecampo("DFET_RCRITICO");
					if (resultado.getDFET_RESULTADO() == "") {
						lef.setLefValornuevo(new String());
					} else {
						lef.setLefValornuevo(result.toString() == null ? new String() : result.toString());
					}
					lef.setLefValoranterior(dfet.getDfetRcritico() == null ? new String() : dfet.getDfetRcritico());
					if (!lef.getLefValornuevo().equals(lef.getLefValoranterior())) {
						if (lef.getLefValoranterior().equals("A")) {
							lef.setLefValoranterior("ALTO");
						} else if (lef.getLefValoranterior().equals("N")) {
							lef.setLefValoranterior("NORMAL");
						} else if (lef.getLefValoranterior().equals("B")) {
							lef.setLefValoranterior("BAJO");
						} else if (lef.getLefValoranterior().equals("CA")) {
							lef.setLefValoranterior("CRITICO ALTO");
						} else if (lef.getLefValoranterior().equals("CB")) {
							lef.setLefValoranterior("CRITICO BAJO");
						} else {
							lef.setLefValoranterior("");
						}

						if (lef.getLefValornuevo().equals("A")) {
							lef.setLefValornuevo("ALTO");
						} else if (lef.getLefValornuevo().equals("N")) {
							lef.setLefValornuevo("NORMAL");
						} else if (lef.getLefValornuevo().equals("B")) {
							lef.setLefValornuevo("BAJO");
						} else if (lef.getLefValornuevo().equals("CA")) {
							lef.setLefValornuevo("CRITICO ALTO");
						} else if (lef.getLefValornuevo().equals("CB")) {
							lef.setLefValornuevo("CRITICO BAJO");
						} else {
							lef.setLefValornuevo("");
						}
						logEventosfichasDAO.insertLogEventosFichas(lef);
					}
				}
			}

			// ********************************hasta aca es la trazabilidad
			// ****************************************

			// } else {
			// logger.debug(
			// "resyultado anterior formula *** para validar como grabar solo una vez la
			// formula -se debe realizar logica ");
			// }

		} catch (HibernateException | BiosLISException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error(he.getMessage());
			he.printStackTrace();
			throw new BiosLISDAOException("No se pudo actualizar resultado.");
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return result;
	}

	// no cerrar esta sesion. porque la sesion se devuelve a funcion que la llama
	// ********************************
	public List<CfgValoresreferencia> getValoresReferencia(Session sesion, BigDecimal idTest, BigDecimal nOrden)
			throws BiosLISDAONotFoundException, BiosLISDAOException {

		List<CfgValoresreferencia> lstVReferencia = null;
		try {
			Query qry = sesion.createQuery("select cvr from CfgValoresreferencia cvr where cvr.cvrIdtest = :idTest");
			logger.debug("Se busca valores de referencia para test: {}", idTest);
			qry.setBigDecimal("idTest", idTest);
			lstVReferencia = qry.list();
			if (lstVReferencia.isEmpty()) {
				throw new BiosLISDAONotFoundException("No se encontraron valores de referencia para el test");
			}
		} catch (HibernateException hex) {

			throw new BiosLISDAOException(hex.getMessage());
		}
		return lstVReferencia;
	}
//*****************************************************************************************************

	public RangoValorResultado checkValoresreferencia(ResultadoNumericoTestExamenOrdenDTO resultado, Session sesion)
			throws BiosLISException, BiosLISDAONotFoundException, BiosLISDAOException, ParseException {

		if (!resultado.getCTR_CODIGO().equals("N")) {
			return RangoValorResultado.N;
		}
		DatosPacienteOrdenDTO dpDto = datosPacientesDAO.getPacienteOrden(resultado.getDF_NORDEN());
		List<CfgValoresreferencia> lstVRef = getValoresReferencia(sesion, resultado.getDFET_IDTEST(),
				resultado.getDF_NORDEN());
		NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("es-ES"));
		Number number = nf.parse(resultado.getDFET_RESULTADO());
		Edad edadActual = Edad.getEdadActual(dpDto.getDP_FNACIMIENTO());
		logger.debug("Edad actual {} y sexo {}.", edadActual, dpDto.getLDV_INISEXO());

		for (CfgValoresreferencia cfgValoresreferencia : lstVRef) {

			Edad edadInicial = new Edad(cfgValoresreferencia.getCvrAnosdesde(), cfgValoresreferencia.getCvrMesesdesde(),
					cfgValoresreferencia.getCvrDiasdesde());
			Edad edadFinal = new Edad(cfgValoresreferencia.getCvrAnoshasta(), cfgValoresreferencia.getCvrMeseshasta(),
					cfgValoresreferencia.getCvrDiashasta());

			logger.debug("Condiciones edad minima, maxima,sexo.{} {} {}", edadInicial, edadFinal,
					cfgValoresreferencia.getCvrSexo());
			logger.debug("Valores: {} {}  ", edadActual, dpDto.getLDV_INISEXO());
			logger.debug("Comparaciones: {} {}  ", edadInicial.compareTo(edadActual), edadFinal.compareTo(edadActual));

			if ((edadInicial.compareTo(edadActual) <= 0 && edadFinal.compareTo(edadActual) >= 0)
					&& (cfgValoresreferencia.getCvrSexo().equals(dpDto.getLDV_INISEXO())
							|| cfgValoresreferencia.getCvrSexo().equals(AMBOS_SEXOS))) {
				logger.debug("Satisface condiciones de edad y sexo.", edadActual, cfgValoresreferencia.getCvrSexo());
				if (cfgValoresreferencia.getCvrValorcriticobajo() != null && cfgValoresreferencia
						.getCvrValorcriticobajo().compareTo(BigDecimal.valueOf(number.doubleValue())) > 0) {
					return RangoValorResultado.CB;
				} else if (cfgValoresreferencia.getCvrValorbajo() != null && cfgValoresreferencia.getCvrValorbajo()
						.compareTo(BigDecimal.valueOf(number.doubleValue())) > 0) {
					return RangoValorResultado.B;
				} else if (cfgValoresreferencia.getCvrValorcriticoalto() != null && cfgValoresreferencia
						.getCvrValorcriticoalto().compareTo(BigDecimal.valueOf(number.doubleValue())) < 0) {
					logger.debug("JAN VALORES DE REFERENCIA CRITICO ALGO COMPARE {} ",
							cfgValoresreferencia.getCvrValorcriticoalto());
					logger.debug("JAN VALORES DE REFERENCIA CRITICO ALGO {} ",
							BigDecimal.valueOf(number.doubleValue()));
					logger.debug("JAN VALORES DE REFERENCIA RESULTADO {} ", cfgValoresreferencia
							.getCvrValorcriticoalto().compareTo(BigDecimal.valueOf(number.doubleValue())) < 0);
					return RangoValorResultado.CA;
				} else if (cfgValoresreferencia.getCvrValoralto() != null && cfgValoresreferencia.getCvrValoralto()
						.compareTo(BigDecimal.valueOf(number.doubleValue())) < 0) {
					return RangoValorResultado.A;
				}

				return RangoValorResultado.N;
			}
		}
		return RangoValorResultado.N;
	}

	/**
	 * @return the datosPacientesDAO
	 */
	public DatosPacientesDAO getDatosPacientesDAO() {
		return datosPacientesDAO;
	}

	/**
	 * @param datosPacientesDAO the datosPacientesDAO to set
	 */
	public void setDatosPacientesDAO(DatosPacientesDAO datosPacientesDAO) {
		this.datosPacientesDAO = datosPacientesDAO;
	}

	public List<ResultadoTestPacienteDTO> getHistoriaDeUnTestDePaciente(BigDecimal idPaciente, BigDecimal idTest)
			throws BiosLISDAONotFoundException, BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<ResultadoTestPacienteDTO> lstResult;
		try {
			Query qry = sesion.createSQLQuery(sqlHistoriaDeUnTestDePaciente)
					.setResultTransformer(Transformers.aliasToBean(ResultadoTestPacienteDTO.class));
			qry.setBigDecimal("idPaciente", idPaciente);
			qry.setBigDecimal("idTest", idTest);
			lstResult = qry.list();
			sesion.close();

			logger.debug("Nro de registros de historia {} para test {}", lstResult != null ? lstResult.size() : -1,
					idTest);

			/*
			 * este codigo mandaba error si no encontraba historia del paciente, pacientes
			 * con primera orden y test mandaba error-- ahora manda null -- modificado por
			 * cristian 23-02 if (lstResult.isEmpty()) { // throw new
			 * BiosLISDAONotFoundException( //
			 * "No se encontró historia de valores de resultados de un test para un paciente"
			 * ); }
			 */
		} catch (HibernateException hex) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			throw new BiosLISDAOException(hex.getMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lstResult;

	}

	private void insertLogRecord(ResultadoNumericoTestExamenOrdenDTO resultado) {
		/*
		 * try { LogEventoFichaDtoId lefdid = new
		 * LogEventoFichaDtoId(resultado.getDF_NORDEN(), resultado.getDFE_IDEXAMEN(),
		 * resultado.getDFET_IDTEST(), null); BiosLisLogger bl = new BiosLisLogger();
		 * Timestamp ts = BiosLisCalendarService.getInstance().getTS();
		 * bl.logInsertDatosDTOFichaTableRecord(resultado, new BigDecimal(1), ts,
		 * resultado.getDF_NORDEN(), "ipEstacion", Constante.CREACION_DATOS_FICHAS,
		 * lefdid); } catch (IllegalArgumentException e) {
		 * logger.error("No se pudo insertar registro de log de tabla.\n{}",
		 * e.getMessage()); throw e; }
		 */

	}

	public static final String sqlResultadosTestExamenDeUnaOrden = ""
			+ "SELECT df.DF_NORDEN,dfe.DFE_IDEXAMEN,dfet.DFET_IDTEST,ce.CE_ABREVIADO , ct.ct_abreviado, "
			+ "ctr.CTR_DESCRIPCION ,ct.CT_FORMULA ,cu.CUM_DESCRIPCION ,ct.CT_IDUNIDADMEDIDA ,"
			+ "cert.CERT_DESCRIPCIONESTADOTEST,cert.CERT_IDESTADORESULTADOTEST,"
			+ "dfet.DFET_RESULTADONUMERICO ,dfet.DFET_IDUNIDADDEMEDIDA,dfet.DFET_REFERENCIADESDE, "
			+ "dfet.DFET_REFERENCIAHASTA,ctr.CTR_CODIGO,dfet.DFET_RESULTADO, "
			+ "dfet.DFET_RCRITICO, dp.DP_IDPACIENTE, dfet.DFET_IDMUESTRA,ct.CT_DECIMALES ,dfm.DFM_CODIGOBARRA,"
			+ "dp.DP_TELEFONO, dp.DP_EMAIL,dp.DP_NOMBRES,dp.DP_PRIMERAPELLIDO,dp.DP_SEGUNDOAPELLIDO,"
			+ "cm.CM_IDMEDICO,cm.CM_NOMBRES,cm.CM_PRIMERAPELLIDO,cm.CM_SEGUNDOAPELLIDO,cm.CM_EMAIL,cm.CM_TELEFONO , dfet.DFET_IDFICHATESTNOTIFICACION ,"
			+ " dfet.DFET_VALORCRITICODESDE , dfet.DFET_VALORCRITICOHASTA " + " FROM DATOS_FICHAS df "
			+ "INNER JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE) "
			+ "INNER JOIN DATOS_FICHASEXAMENES dfe ON (df.DF_NORDEN = dfe.DFE_NORDEN) "
			+ "INNER JOIN DATOS_FICHASEXAMENESTEST dfet ON (dfet.DFET_NORDEN = dfe.DFE_NORDEN AND dfet.DFET_IDEXAMEN = dfe.DFE_IDEXAMEN) "
			+ "INNER JOIN cfg_examenes ce ON (dfe.DFE_IDEXAMEN = ce.CE_IDEXAMEN) "
			+ "inner join cfg_test ct on ct.ct_idtest = dfet.DFET_IDTEST "
			+ "INNER JOIN BIOSLIS.CFG_TIPOSDERESULTADO ctr ON (ctr.CTR_IDTIPORESULTADO = ct.CT_IDTIPORESULTADO) "
			+ "INNER JOIN CFG_ESTADOSRESULTADOSTEST cert ON (cert.CERT_IDESTADORESULTADOTEST = dfet.DFET_IDESTADORESULTADOTEST) "
			+ "INNER JOIN BIOSLIS.CFG_UNIDADESDEMEDIDA cu ON (cu.CUM_IDUNIDADMEDIDA = ct.CT_IDUNIDADMEDIDA) "
			+ "LEFT OUTER JOIN BIOSLIS.CFG_MEDICOS cm ON (df.DF_IDMEDICO = cm.CM_IDMEDICO) "
			+ "LEFT OUTER JOIN BIOSLIS.DATOS_FICHASMUESTRAS dfm ON (dfm.DFM_IDMUESTRA = dfet.DFET_IDMUESTRA) "
			+ " WHERE " + " df.DF_NORDEN = :nOrden AND dfe.DFE_IDEXAMEN = :idExamen AND dfet.DFET_IDTEST=:idTest "
			+ "ORDER BY 1";

//REALIZADO POR CRISTIAN 05-09-2022 Modifica fecha resultados   SOLO EJEMPLO ********************
	public void updateContadorFichaExamenTest(Long nroOrden, Long examenId, Long testId, String fechaResultado)
			throws BiosLISDAOException {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy");

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			Date fecha = formato.parse(fechaResultado);
			Query qry = sesion.createSQLQuery(sqlUpdateContadorFichasExamenTest);

			sesion.beginTransaction();
			qry.setDate("fechaResultado", fecha);
			qry.setLong("nOrden", nroOrden);
			qry.setLong("idExamen", examenId);
			qry.setLong("idTest", testId);
			qry.executeUpdate();

			sesion.getTransaction().commit();

		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error(he.getMessage());
			throw new BiosLISDAOException("No se pudo actualizar estado ".concat(he.getLocalizedMessage()));
		} catch (ParseException e) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
	}

	public ResultadoTestNotificacionDeUnaOrdenDTO getDatosTestNotificacion(Long nroOrden, Long examenId, Long testId)
			throws BiosLISDAOException {

		ResultadoTestNotificacionDeUnaOrdenDTO rslt = null;
		List<datosfichastestnotificacionesDTO> resultado = null;
		Session sesion = HibernateUtil.getSessionFactory().openSession();

		try {
			Query qry = sesion.createSQLQuery(sqlResultadosTestExamenDeUnaOrden)
					.setResultTransformer(Transformers.aliasToBean(ResultadoTestNotificacionDeUnaOrdenDTO.class));
			qry.setLong("nOrden", nroOrden);
			qry.setLong("idExamen", examenId);
			qry.setLong("idTest", testId);
			rslt = (ResultadoTestNotificacionDeUnaOrdenDTO) qry.uniqueResult();

			if (rslt.getDFET_IDFICHATESTNOTIFICACION() != null) {
				qry = sesion.createSQLQuery(
						"select TO_CHAR(dftn.dftn_numerointentos) as notificacionid , TO_CHAR(dftn.dftn_fechaenvio) as notificaciondate , dftn.dftn_mensajenotificacion as notificacionmesage, "
								+ "dftn.dftn_valorcriticonotificado as notificacionconfirm , "
								+ " dftn.dftn_nombrereceptor as notificacionname , TO_CHAR(dftn.dftn_idusuarioenvia) as notificacionuser , "
								+ "dftn.dftn_nota as notificacionnote , TO_CHAR(dftn.dftn_idtiporeceptor) as notificacionreceptor, dftn.dftn_emailenvio notificacionsendto , "
								+ "TO_CHAR(dftn.dftn_idvianotificacion) as notificacionchannel,"
								+ "dftn.dftn_estadoexcepcion as notificaciontyperror , dftn.dftn_notaestadoexcepcion as notificacionerrornote "
								+ "from datos_fichastestnotificaciones dftn "
								+ " where dftn.dftn_idfichatestnotificacion = :idNotificacion order by dftn.dftn_numerointentos ")
						.setResultTransformer(Transformers.aliasToBean(datosfichastestnotificacionesDTO.class));

				qry.setParameter("idNotificacion", rslt.getDFET_IDFICHATESTNOTIFICACION());
				resultado = qry.list();
			}
			rslt.setNOTIFICACION(resultado);
		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			he.printStackTrace();
			logger.error(he.getMessage());
			throw new BiosLISDAOException(he.getMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return rslt;
	}

//  public static final String UltimoIdNotificacion = "SELECT DFTN_IDFICHATESTNOTIFICACION FROM DATOS_FICHASTESTNOTIFICACIONES  WHERE  rownum = 1 order BY 1 desc ";
//
//  public String insertDatosFichasNotificacionTest(Long nroOrden, Long examenId, Long testId,
//      ResultadoTestNotificacionDeUnaOrdenDTO dftnot, Long idUsuario) throws BiosLISDAOException, ParseException {
//
//    Session sesion = HibernateUtil.getSessionFactory().openSession();
//    BigDecimal idNotificacion = BigDecimal.ZERO;
//    String mensaje = "";
//    try {
//
//      sesion.beginTransaction();
//
//      // Se actualiza DATOS_FICHASEXAMENESTEST con el id de FICHATESTNOTIFICACION.
//
//      // ¿Hay notificaciones anteriores?
//
//      // Hay notificacaciones confirmadas?
//
//      // Si no hay crear nueva notificacion
//
//      // Si hay actualizar nro de intentos.
//
//      // Si es confirmada actualizar datos fichas examenes.
//
//      if (dftnot.getDFET_IDFICHATESTNOTIFICACION() == null || dftnot.getDFET_IDFICHATESTNOTIFICACION().equals("")) {
//        Query qry = sesion.createSQLQuery(UltimoIdNotificacion);
//        idNotificacion = (BigDecimal) qry.uniqueResult();
//        if (idNotificacion == null) {
//          idNotificacion = new BigDecimal(0);
//        }
//
//        Query queryDATOS_FICHASEXAMENESTEST = sesion.createSQLQuery(
//            "UPDATE DATOS_FICHASEXAMENESTEST SET DFET_IDFICHATESTNOTIFICACION = :idnotificacion WHERE DFET_NORDEN = :norden AND DFET_IDEXAMEN =  :idexamen  AND  DFET_IDTEST = :idtest");
//
//        queryDATOS_FICHASEXAMENESTEST.setInteger("idnotificacion", (idNotificacion).intValue() + 1);
//        queryDATOS_FICHASEXAMENESTEST.setParameter("norden", nroOrden);
//        queryDATOS_FICHASEXAMENESTEST.setParameter("idexamen", examenId);
//        queryDATOS_FICHASEXAMENESTEST.setParameter("idtest", testId);
//        queryDATOS_FICHASEXAMENESTEST.executeUpdate();
//
//        dftnot.setDFET_IDFICHATESTNOTIFICACION(BigDecimal.valueOf((idNotificacion).intValue() + 1));
//
//      }
//
//      // **************************************************************************************************
//
//      for (datosfichastestnotificacionesDTO noti : dftnot.getNOTIFICACION()) {
//
//        Query query = sesion.createSQLQuery(
//            "select TO_CHAR(dftn.DFTN_NUMEROINTENTOS) AS INTENTOS from DATOS_FICHASTESTNOTIFICACIONES dftn "
//                + "where dftn.DFTN_IDFICHATESTNOTIFICACION = :idNotificacion and dftn.DFTN_NUMEROINTENTOS = :idIntentos ");
//
//        query.setParameter("idNotificacion", dftnot.getDFET_IDFICHATESTNOTIFICACION());
//        query.setParameter("idIntentos", noti.getNOTIFICACIONID());
//        String intentos = "";
//        intentos = (String) query.uniqueResult();
//
//        if (intentos == null || intentos.equals("")) {
//          if (noti.getNOTIFICACIONCONFIRM().equals("S")) {
//            Query queryDATOS_FICHASEXAMENESTEST = sesion.createSQLQuery("UPDATE DATOS_FICHASEXAMENESTEST SET "
//                + " DFET_VALORCRITICONOTIFICADO = :dfet_valorcriticonotificado,  "
//                + " DFET_NOTAVALORCRITICO = :dfet_notavalorcritico, " + " DFET_FECHAVALORCRITICO = SYSDATE,  "
//                + " DFET_IDUSUARIONOTIFICACRITICO = :dfet_idusuarionotificacritico, "
//                + " DFET_TIPONOTIFICACION = :dfet_tiponotificacion, "
//                + " DFET_PROFESIONALNOTIFICADO = :dfet_profesionalnotificado  "
//                + " WHERE DFET_NORDEN = :norden AND DFET_IDEXAMEN =  :idexamen  AND  DFET_IDTEST = :idtest");
//
//            queryDATOS_FICHASEXAMENESTEST.setParameter("dfet_valorcriticonotificado", noti.getNOTIFICACIONCONFIRM());
//            queryDATOS_FICHASEXAMENESTEST.setParameter("dfet_notavalorcritico", noti.getNOTIFICACIONNOTE());
////                queryDATOS_FICHASEXAMENESTEST.setParameter("dfet_fechavalorcritico", examenId);
//            queryDATOS_FICHASEXAMENESTEST.setParameter("dfet_idusuarionotificacritico", noti.getNOTIFICACIONRECEPTOR());
//            queryDATOS_FICHASEXAMENESTEST.setParameter("dfet_tiponotificacion", noti.getNOTIFICACIONCHANNEL());
//            queryDATOS_FICHASEXAMENESTEST.setParameter("dfet_profesionalnotificado", noti.getNOTIFICACIONRECEPTOR());
//            //
//            queryDATOS_FICHASEXAMENESTEST.setParameter("norden", nroOrden);
//            queryDATOS_FICHASEXAMENESTEST.setParameter("idexamen", examenId);
//            queryDATOS_FICHASEXAMENESTEST.setParameter("idtest", testId);
//            queryDATOS_FICHASEXAMENESTEST.executeUpdate();
//
//          }
//          // INSERTA DATOS TABLA DATOS_FICHASTESTNOTIFICACIONES
//          query = sesion.createSQLQuery(
//              "INSERT  INTO DATOS_FICHASTESTNOTIFICACIONES (DFTN_IDFICHATESTNOTIFICACION, DFTN_NUMEROINTENTOS,  DFTN_MENSAJENOTIFICACION, DFTN_EMAILENVIO,"
//                  + " DFTN_IDTIPORECEPTOR,  DFTN_IDUSUARIOENVIA,  DFTN_VALORCRITICONOTIFICADO, "
//                  + "DFTN_FECHAENVIO, DFTN_FECHARECEPCION,  DFTN_NOMBRERECEPTOR,  DFTN_IDVIANOTIFICACION, DFTN_NOTA,  DFTN_ESTADOEXCEPCION, DFTN_NOTAESTADOEXCEPCION) "
//                  + "VALUES (:DftnIdfichatestnotificacion,:DftnNumerointentos,:Dftnmensajenotificacion,:DftnEmailenvio,:DftnIdTipoReceptor,"
//                  + ":DftnIDUsuario,:DftnNotificacionConfirm,SYSDATE,NULL,:DftnNombreReceptor,:DftnIDNotificacion,:DftnNota,:DftnEstadoExcepcion,:DftnEstadoExcepcionNota)");
//
//          query.setParameter("DftnIdfichatestnotificacion", dftnot.getDFET_IDFICHATESTNOTIFICACION());
//          query.setParameter("DftnNumerointentos", noti.getNOTIFICACIONID());
//          query.setParameter("Dftnmensajenotificacion", noti.getNOTIFICACIONMESAGE());
//          query.setParameter("DftnEmailenvio", noti.getNOTIFICACIONSENDTO());
//          query.setInteger("DftnIdTipoReceptor", Integer.valueOf(noti.getNOTIFICACIONRECEPTOR()));
//          query.setInteger("DftnIDUsuario", Integer.valueOf(noti.getNOTIFICACIONUSER()));
//          query.setParameter("DftnNotificacionConfirm", noti.getNOTIFICACIONCONFIRM());
//          // query.setParameter("DftnFechaEnvio", noti.getNOTIFICACIONDATE());
//          // query.setParameter("DftnFechaRecepcion",new SimpleDateFormat("dd/MM/yyyy
//          // hh:mm").parse(noti.getNOTIFICACIONDATE()));
//          query.setParameter("DftnNombreReceptor", noti.getNOTIFICACIONNAME());
//          query.setParameter("DftnIDNotificacion", noti.getNOTIFICACIONCHANNEL());
//          query.setParameter("DftnNota", noti.getNOTIFICACIONNOTE());
//          if (noti.getNOTIFICACIONTYPERROR() == null || noti.getNOTIFICACIONTYPERROR().equals("")) {
//            query.setParameter("DftnEstadoExcepcion", null);
//          } else {
//            query.setParameter("DftnEstadoExcepcion", noti.getNOTIFICACIONTYPERROR());
//          }
//          query.setParameter("DftnEstadoExcepcionNota", noti.getNOTIFICACIONERRORNOTE());
//
//          query.executeUpdate();
//          mensaje = "Notificaciones ingresadas Correctamente";
//
//        } else {
//          query = sesion.createSQLQuery(
//              "UPDATE DATOS_FICHASTESTNOTIFICACIONES SET DFTN_ESTADOEXCEPCION  = :DftnEstadoExcepcion,DFTN_NOTAESTADOEXCEPCION = :DftnEstadoExcepcionNota "
//                  + "WHERE DFTN_IDFICHATESTNOTIFICACION = :DftnIdfichatestnotificacion AND DFTN_NUMEROINTENTOS  = :DftnNumerointentos");
//
//          query.setParameter("DftnIdfichatestnotificacion", (dftnot.getDFET_IDFICHATESTNOTIFICACION()));
//          query.setParameter("DftnNumerointentos", noti.getNOTIFICACIONID());
//          if (noti.getNOTIFICACIONTYPERROR() == null || noti.getNOTIFICACIONTYPERROR().equals("")) {
//            query.setParameter("DftnEstadoExcepcion", null);
//          } else {
//            query.setParameter("DftnEstadoExcepcion", noti.getNOTIFICACIONTYPERROR());
//          }
//          query.setParameter("DftnEstadoExcepcionNota", noti.getNOTIFICACIONERRORNOTE());
//          query.executeUpdate();
//
//          mensaje = "Notificaciones actualizadas Correctamente";
//        }
//      }
//      sesion.getTransaction().commit();
//
//    } catch (HibernateException he) {
//      logger.error(he.getMessage());
//      mensaje = "Notificaciones  ingresadas INCORRECTAMENTE";
//      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
//    } finally {
//      if (sesion.isOpen()) {
//        sesion.close();
//      }
//    }
//    return mensaje;
//  }

	private void updateDatosFichasExamenesTest(ResultadoTestNotificacionDeUnaOrdenDTO dftnot, Session sesion,
			Integer idNotificacion) {

		if (dftnot.getDFET_IDFICHATESTNOTIFICACION() == null || dftnot.getDFET_IDFICHATESTNOTIFICACION().equals("")) {

			Query queryDATOS_FICHASEXAMENESTEST = sesion.createSQLQuery(
					"UPDATE DATOS_FICHASEXAMENESTEST SET DFET_IDFICHATESTNOTIFICACION = :idnotificacion WHERE DFET_NORDEN = :norden AND DFET_IDEXAMEN =  :idexamen  AND  DFET_IDTEST = :idtest");

			queryDATOS_FICHASEXAMENESTEST.setInteger("idnotificacion", idNotificacion);
			queryDATOS_FICHASEXAMENESTEST.setParameter("norden", dftnot.getDF_NORDEN());
			queryDATOS_FICHASEXAMENESTEST.setParameter("idexamen", dftnot.getDFE_IDEXAMEN());
			queryDATOS_FICHASEXAMENESTEST.setParameter("idtest", dftnot.getDFET_IDTEST());
			queryDATOS_FICHASEXAMENESTEST.executeUpdate();
		}

	}

	public static final String ValidadrNumeroNoti = "SELECT dftn.DFTN_NUMEROINTENTOS FROM DATOS_FICHASTESTNOTIFICACIONES dftn "
			+ "INNER  JOIN DATOS_FICHASEXAMENESTEST dfet  "
			+ "ON dftn.DFTN_IDFICHATESTNOTIFICACION  = dfet.DFET_IDFICHATESTNOTIFICACION  "
			+ "WHERE dfet.DFET_NORDEN = :norden AND dfet.DFET_IDEXAMEN = :idexamen  AND  dfet.DFET_IDTEST = :idtest AND dftn.DFTN_NUMEROINTENTOS = :idnotificacion ";

	public Boolean ValidarNumeroNotificaciones(Long nroOrden, Long examenId, Long testId, Long notificacionId)
			throws BiosLISDAOException, ParseException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();

		Boolean validador = null;
		try {
			Query qry = sesion.createSQLQuery(ValidadrNumeroNoti);
			qry.setParameter("norden", nroOrden);
			qry.setParameter("idexamen", examenId);
			qry.setParameter("idtest", testId);
			qry.setParameter("idnotificacion", notificacionId);
			validador = !qry.list().isEmpty();

		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error(he.getMessage());

			throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return validador;
	}

	public void updateEstadoResultadoExamenesOrdenes(DatosFichasexamenestest dfet) throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			sesion.beginTransaction();
			sesion.update(dfet);
			sesion.getTransaction().commit();

		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error(he.getMessage());
			throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}

	}

	public void updateEstadoResultadoExamenesOrdenes(Long nroOrden, Long examenId, Long testId, String estado,
			Long idUsuario) throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {

			Query qry = sesion.createSQLQuery(sqlUpdateResultadosExamenesDeUnaOrden);

			sesion.beginTransaction();
			qry.setString("estado", estado);
			qry.setLong("nOrden", nroOrden);
			qry.setLong("idExamen", examenId);
			qry.setLong("idTest", testId);
			qry.executeUpdate();

			sesion.getTransaction().commit();

		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error(he.getMessage());
			throw new BiosLISDAOException("No se pudo actualizar estado ".concat(he.getLocalizedMessage()));
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
	}

	public RangoValorResultado resetResultadoTestExamenOrden(ResultadoNumericoTestExamenOrdenDTO resultado,
			DatosUsuarios usuario) throws BiosLISDAOException, ParseException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		RangoValorResultado result = RangoValorResultado.N;

		// modificacion por cristian 07-11 agregar idusuariodigita y fecharesultado
		Timestamp ts = BiosLisCalendarService.getInstance().getTS();
		try {
			Query qry = sesion.createSQLQuery(sqlUpdateResultadosNumericosExamenesDeUnaOrden);

			sesion.beginTransaction();
			DatosFichasexamenestestId dfetid = new DatosFichasexamenestestId(resultado.getDF_NORDEN().longValue(),
					resultado.getDFE_IDEXAMEN().longValue(), resultado.getDFET_IDTEST().longValue());
			DatosFichasexamenestest dfet = (DatosFichasexamenestest) sesion.get(DatosFichasexamenestest.class, dfetid);

			qry.setString("resultado", resultado.getDFET_RESULTADO());
			qry.setBigDecimal("nOrden", resultado.getDF_NORDEN());
			qry.setBigDecimal("idExamen", resultado.getDFE_IDEXAMEN());
			qry.setBigDecimal("idTest", resultado.getDFET_IDTEST());
			qry.setInteger("estado", EstadosSistema.DFET_IDRESULTADOTEST_PENDIENTE);
			qry.setBigDecimal("resultadonro", null);
			qry.setString("rcritico", "");
			qry.setLong("idUsuario", usuario.getDuIdusuario());
			qry.setTimestamp("fecha", ts);
			qry.executeUpdate();

			// BUSCAR UNA MANERA DE agregar a logeventos SI TEST es FORMULA !!!!!
			// datos anteriores para enviar a log_eventos ***********

			LogEventosfichasDAO logEventosfichasDAO = new LogEventosfichasDAO();
			LogEventosfichas lef = new LogEventosfichas();

			sesion.getTransaction().commit();
			sesion.close();

			lef.setDatosFichas(resultado.getDF_NORDEN().intValue());
			lef.setLefFechaorden(dfet.getDfetFechaorden());
			lef.setLefIdpaciente((int) dfet.getDfetIdpaciente());
			lef.setLefNombretabla("DATOS_FICHASEXAMENESTESTS");
			lef.setLefIdexamen(resultado.getDFE_IDEXAMEN().intValue());
			lef.setLefIdtest(resultado.getDFET_IDTEST().intValue());
			lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
			lef.setLefIdusuario((int) usuario.getDuIdusuario());
			lef.setLefIdmuestra(dfet.getDfetIdmuestra().intValue());
			DatosFichasmuestrasDAO datosFichasmuestrasDAO = new DatosFichasmuestrasDAO();
			DatosFichasmuestras dfm = datosFichasmuestrasDAO.getMuestraById(dfet.getDfetIdmuestra());
			lef.setLefCodigobarra(dfm.getDfmCodigobarra());
			lef.setCfgEventos(2);
			lef.setLefNombrecampo("DFET_RESULTADO");
			lef.setLefValornuevo("");
			lef.setLefValoranterior(dfet.getDfetResultado());
			logEventosfichasDAO.insertLogEventosFichas(lef);

			lef.setLefNombrecampo("DFET_IDETSTADORESULTADOTEST");
			lef.setLefValoranterior("DIGITADO");
			lef.setLefValornuevo("PENDIENTE");
			logEventosfichasDAO.insertLogEventosFichas(lef);

			lef.setLefNombrecampo("DFET_RCRITICO");
			lef.setLefValoranterior(dfet.getDfetRcritico());
			// if null agregado por Marco Caracciolo
			if (dfet.getDfetRcritico() != null) {
				if (dfet.getDfetRcritico().equals("A")) {
					lef.setLefValoranterior("ALTO");
				} else if (dfet.getDfetRcritico().equals("N")) {
					lef.setLefValoranterior("NORMAL");
				} else if (dfet.getDfetRcritico().equals("B")) {
					lef.setLefValoranterior("BAJO");
				} else if (dfet.getDfetRcritico().equals("CA")) {
					lef.setLefValoranterior("CRITICO ALTO");
				} else if (dfet.getDfetRcritico().equals("CB")) {
					lef.setLefValoranterior("CRITICO BAJO");
				} else {
					lef.setLefValoranterior("");
				}
			} else {
				lef.setLefValoranterior("");
			}

			lef.setLefValornuevo("");
			logEventosfichasDAO.insertLogEventosFichas(lef);

		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error(he.getMessage());
			throw new BiosLISDAOException("No se pudo actualizar resultado.");
		} finally {
			if (sesion.isOpen() || sesion == null)
				sesion.close();
		}
		return result;
	}

	// REALIZADO POR CRISTIAN 05-09-2022 INSERTA DATOS EN CELLCOUNTER
	// ********************
	public void insertDatosContadorCelulas(CfgCellcounter cellCounter) throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			sesion.beginTransaction();
			sesion.saveOrUpdate(cellCounter);
			sesion.getTransaction().commit();
			sesion.close();
		} catch (HibernateException e) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error(e.getMessage());
			throw new BiosLISDAOException("Error en insertar datos cellCounter");
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
	}

	public CfgCellcounter getCellCounter() {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		CfgCellcounter result = new CfgCellcounter();
		try {
			Query qry = sesion.createQuery(
					"select cc from com.grupobios.bioslis.entity.CfgCellcounter cc where cc.cccIdcellcounter= 1");
			List<CfgCellcounter> listacounter = qry.list();
			result = listacounter.get(0);
			sesion.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return result;
	}

	public void updateEstadoTransmitidoResultadoTestExamenOrden(ResultadoNumericoTestExamenOrdenDTO resultado)
			throws BiosLISDAOException, ParseException {

		// modificacion por cristian 07-11 agregar idusuariodigita y fecharesultado
		Timestamp ts = BiosLisCalendarService.getInstance().getTS();

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query qry = sesion.createSQLQuery(sqlUpdateResultadoEstadoTransmitidoExamenesDeUnaOrden);

		try {
			Double number = Double.parseDouble(resultado.getDFET_RESULTADO());
			sesion.beginTransaction();

			qry.setBigDecimal("nOrden", resultado.getDF_NORDEN());
			qry.setBigDecimal("idExamen", resultado.getDFE_IDEXAMEN());
			qry.setBigDecimal("idTest", resultado.getDFET_IDTEST());
			qry.setBigDecimal("estado", TRANSMITIDO);

			qry.executeUpdate();
			sesion.getTransaction().commit();
			sesion.close();
		} catch (HibernateException e) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error(e.getMessage());
			throw new BiosLISDAOException("Error en insertar datos Orden Examen Test");
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
	}

	// realizado por cristian, para modelo resultados historicos
	public static final String sqlHistoriaPacienteTest = "SELECT dfet.DFET_IDTEST,dfet.DFET_FECHAINGRESORESULTADO, dfet.DFET_RESULTADO,dfet.DFET_NORDEN AS DF_NORDEN,dfet.DFET_IDEXAMEN AS DFE_IDEXAMEN, "
			+ " dfet.DFET_RESULTADONUMERICO ,dfet.DFET_IDUNIDADDEMEDIDA FROM DATOS_FICHASEXAMENESTEST dfet  "
			+ "WHERE  dfet.DFET_IDPACIENTE =  :idPaciente  " + "AND dfet.DFET_IDTEST = :idTest "
			+ "ORDER BY dfet.DFET_FECHAINGRESORESULTADO ";

	public static final String sqlUpdateContadorFichasExamenTest = "UPDATE DATOS_FICHASEXAMENESTEST dfet "
			+ "SET dfet.DFET_FECHAINGRESORESULTADO = :fechaResultado" + " WHERE dfet.DFET_NORDEN = :nOrden "
			+ "AND  dfet.DFET_IDEXAMEN = :idExamen " + "AND  dfet.DFET_IDTEST = :idTest ";

	public List<Object> getPacienteTest(Long idPaciente, Long idTest) throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<Object> lstResult;
		try {
			sesion.beginTransaction();
			Query qry = sesion.createSQLQuery(sqlHistoriaPacienteTestDescripcion);

			qry.setLong("idPaciente", idPaciente);
			qry.setLong("idTest", idTest);
			lstResult = qry.list();
			sesion.getTransaction().commit();
			sesion.close();
		} catch (HibernateException hex) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			throw new BiosLISDAOException(hex.getMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}

		}
		return lstResult;

	}

	public Object datosExamenById(Long examen) throws BiosLISDAOException, BiosLISDAONotFoundException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();

		Object list = null;
		try {
			Query query = sesion.createQuery(
					"select ce.ceIdexamen, ce.ceCellcounter from CfgExamenes ce where ce.ceIdexamen =:examen");
			query.setLong("examen", examen);
			list = query.uniqueResult();
			sesion.close();
		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error("No se pudo obtener test opcionales en lista de examenes {}.", he.getMessage());
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return list;
	}

	public Object unidadMedidaById(int idMedida) throws BiosLISDAOException {
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		Object unidadMedida = null;
		try {

			sesion.beginTransaction();
			Query query = sesion.createQuery(
					"select cum.cumCodigo from com.grupobios.bioslis.entity.CfgUnidadesdemedida cum where cum.cumIdunidadmedida = :idMedida");
			query.setParameter("idMedida", idMedida);
			unidadMedida = query.uniqueResult();
			sesion.getTransaction().commit();

		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error("No se pudo obtener test opcionales en lista de examenes {}.", he.getMessage());
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return unidadMedida;

	}

	public List<Object> getDatosOrdenTestUnidad(Long nOrden) throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<Object> unidadMedida = new ArrayList<Object>();
		try {
			sesion.beginTransaction();
			Query query = sesion.createSQLQuery(sqlDatosOrdenTest);
			query.setParameter("nOrden", nOrden);
			unidadMedida = query.list();
			sesion.getTransaction().commit();
		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error("No se pudo obtener informacion de ", he.getMessage());
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return unidadMedida;

	}

	public static final String sqlHistoriaPacienteTestDescripcion = "SELECT dfet.DFET_IDTEST,dfet.DFET_FECHAINGRESORESULTADO, dfet.DFET_RESULTADO,dfet.DFET_NORDEN AS DF_NORDEN,dfet.DFET_IDEXAMEN AS DFE_IDEXAMEN, "
			+ " dfet.DFET_RESULTADONUMERICO ,dfet.DFET_IDUNIDADDEMEDIDA, cfu.CUM_CODIGO, dfet.DFET_TESTFIRMADO , dfet.DFET_IDESTADORESULTADOTEST, ctest.ct_idtiporesultado "
			+ " , ctest.ct_deltacantidad, ctest.ct_deltaporcentaje  FROM DATOS_FICHASEXAMENESTEST dfet  "
			+ " inner join CFG_TEST ctest      on dfet.dfet_idTest = ctest.ct_idtest "
			+ " inner join CFG_UNIDADESDEMEDIDA cfu " + " on dfet.DFET_IDUNIDADDEMEDIDA = cfu.CUM_IDUNIDADMEDIDA "
			+ "WHERE  dfet.DFET_IDPACIENTE =  :idPaciente  " + "AND dfet.DFET_IDTEST = :idTest "
			+ "ORDER BY dfet.DFET_FECHAINGRESORESULTADO Desc";

	public static final String sqlDatosOrdenTest = "SELECT dfet.DFET_FECHAORDEN ,ct.CT_ABREVIADO,  dfet.DFET_RESULTADONUMERICO, cum.CUM_CODIGO, ce.CE_ABREVIADO  "
			+ " FROM DATOS_FICHASEXAMENESTEST dfet   " + "   inner join CFG_TEST ct  "
			+ "   on ct.CT_IDTEST = dfet.DFET_IDTEST  " + " inner join CFG_UNIDADESDEMEDIDA cum "
			+ "  on cum.CUM_IDUNIDADMEDIDA = dfet.DFET_IDUNIDADDEMEDIDA  " + "   inner JOIN CFG_EXAMENES ce  "
			+ "   on ce.CE_IDEXAMEN = dfet.DFET_IDEXAMEN  " + "   WHERE  dfet.DFET_NORDEN =:nOrden ";

	// lista todos los Test*********************************
	public List<CfgTest> getTestAll() throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		List<CfgTest> test = new ArrayList<CfgTest>();
		try {
			sesion.beginTransaction();
			Query query = sesion.createQuery("select cf from com.grupobios.bioslis.entity.CfgTest cf");

			test = query.list();
			sesion.getTransaction().commit();
		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error("No se pudo obtener informacion de ", he.getMessage());
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return test;

	}

	// *****************************************************
	// REALIZADO POR CRISTIAN 12_10 ********* LINEA DE TIEMPO********
	// trae nombre examen y test, con fechas y usuarios, de orden, tomamuestra,
	// recepcionmuestra, registroresultado, firmaresultado,autorizacion, impresion.

	public static final String sqlDatosLineaTiempo = "select e.ce_abreviado as nombreexamen, t.ct_abreviado as nombretest, df.df_fechaorden as forden, "
			+ "du.du_nombres as nombreusuarioorden , du.du_primerapellido as apellidousuarioorden ,"
			+ "dfm.dfm_fechatm as ftomamuestra," + "(select du.du_nombres  from datos_usuarios du "
			+ "where du.du_idusuario = dfm.dfm_idusuariotm) as nombreusuariomuestra, "
			+ "(select du.du_primerapellido  from datos_usuarios du "
			+ "where du.du_idusuario = dfm.dfm_idusuariotm) as apellidousuariomuestra, "
			+ "dfm.dfm_fecharm as frecepcionmuestra, " + "(select du.du_nombres from datos_usuarios du "
			+ "where du.du_idusuario = dfm.dfm_idusuariorm) as nombreusuariorecepcionmuestra, "
			+ "(select du.du_primerapellido from datos_usuarios du "
			+ "where du.du_idusuario = dfm.dfm_idusuariorm) as apellidousuariorecepcionmuestra, "
			+ "f.dfet_fechaingresoresultado as fingresoresultado, " + "(select du.du_nombres  from datos_usuarios du "
			+ "where du.du_idusuario = f.dfet_idusuariodigita) as nombreusuariodigita, "
			+ "(select du.du_primerapellido  from datos_usuarios du "
			+ "where du.du_idusuario = f.dfet_idusuariodigita) as apellidousuariodigita, "
			+ "f.dfet_fechafirma as ffirma, " + "(select du.du_nombres  from datos_usuarios du "
			+ "where du.du_idusuario = f.dfet_idusuariofirma) as nombreusuariofirma, "
			+ "(select du.du_primerapellido  from datos_usuarios du "
			+ "where du.du_idusuario = f.dfet_idusuariofirma) as apellidousuariofirma, "
			+ "dfe.dfe_fechaautoriza as fautoriza, " + "(select du.du_nombres from datos_usuarios du "
			+ "where du.du_idusuario = dfe.dfe_idusuarioautoriza) as nombreusuarioautoriza, "
			+ "(select du.du_primerapellido from datos_usuarios du "
			+ "where du.du_idusuario = dfe.dfe_idusuarioautoriza) as apellidousuarioautoriza, "
			+ "dfe.dfe_fechaimpresion as fimpresion, " + "(select  du.du_nombres   from datos_usuarios du "
			+ "where du.du_idusuario = dfe.dfe_idusuarioimprime) as nombreusuarioimprime, "
			+ "(select  du.du_primerapellido   from datos_usuarios du "
			+ "where du.du_idusuario = dfe.dfe_idusuarioimprime) as apellidousuarioimprime "
			+ "from datos_fichasexamenestest f " + "inner join cfg_examenes e " + "on e.ce_idexamen = f.dfet_idexamen "
			+ "inner join cfg_test t " + "on t.ct_idtest = f.dfet_idtest " + "inner join datos_fichas df "
			+ "on df.df_norden = f.dfet_norden " + "inner join datos_fichasexamenes dfe "
			+ "on dfe.dfe_norden = f.dfet_norden and dfe.dfe_idexamen = f.dfet_idexamen "
			+ "inner join datos_usuarios du " + "on du.du_idusuario = dfe.dfe_idusuario "
			+ "left join datos_fichasmuestras dfm  " + "on dfm.dfm_idmuestra = f.dfet_idmuestra  "
			+ "where f.dfet_norden =:nOrden and f.dfet_idexamen =:idExamen and f.dfet_idtest =:idTest";

	public DatosLineaTiempoDTO getDatosLineaTiempo(Long nOrden, Long idExamen, Long idTest) throws BiosLISDAOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();

		DatosLineaTiempoDTO lineaTiempo;
		try {
			Query query = sesion.createSQLQuery(sqlDatosLineaTiempo)
					.setResultTransformer(Transformers.aliasToBean(DatosLineaTiempoDTO.class));
			query.setLong("nOrden", nOrden);
			query.setLong("idExamen", idExamen);
			query.setLong("idTest", idTest);
			lineaTiempo = (DatosLineaTiempoDTO) query.uniqueResult();
			sesion.close();
		} catch (HibernateException he) {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
			logger.error("No se pudo obtener datos lineaTiempo", he.getMessage());
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return lineaTiempo;
	}

	public String getFormula(String formula) {

		List<String> valoresId = new ArrayList<String>();

		char[] chars = formula.toCharArray();
		for (int i = 0; i < chars.length; i++) {

			if (chars[i] == '[') {
				String valor = "";
				i++;
				for (int j = i; j < chars.length; j++) {
					if (chars[j] != ']') {
						valor = valor + chars[j];
					} else {
						j = chars.length + 1;
					}
					i++;
				}
				if (valor != null) {
					valoresId.add(valor);
				}

			}
		}
		CfgTest test = new CfgTest();
		String dato = "";

		logger.debug("valores " + valoresId);
		for (String id : valoresId) {

			try {

				if (id.contains("A")) {
					dato = id;
					dato = dato.substring(1, dato.length());
					CfgAntecedentestestDAO antecedenteDAO = new CfgAntecedentestestDAO();
					CfgAntecedentes antedecente = antecedenteDAO.getAntecedenteById(Integer.parseInt(dato));
					dato = antedecente.getCaDescripcion();

				} else {
					int idTest = Integer.parseInt(id);
					test = datosFichasExamenesTestDAO.getCfgTestDAO().getTestById(idTest);
					dato = test.getCtAbreviado();
				}

				formula = formula.replace("[" + id + "]", "[" + dato + "]");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (BiosLISDAONotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BiosLISDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return formula;
	}

	public String insertDatosFichasNotificacionOrdenTestRelacionados(Long nroOrden, Long examenId, Long testId,
			ResultadoTestNotificacionDeUnaOrdenDTO notif, Long idUsuario) throws BiosLISDAOException, ParseException {

		List<DatosFichasexamenestest> lstNotificaciones = datosFichasDAO.getDatosTestCriticos(nroOrden);

		for (DatosFichasexamenestest datosFichasexamenestest : lstNotificaciones) {
			notif.setCE_ABREVIADO(datosFichasexamenestest.getCE_ABREVIADO());
//      notif.setCERT_IDESTADORESULTADOTEST(datosFichasexamenestest.getDfetIdestadoresultadotest());
//      notif.setCT_ABREVIADO(datosFichasexamenestest.get);
//      notif.setCVR_VALORALTO(datosFichasexamenestest.getValorCriticoAlto());  
//      notif.setCVR_VALORBAJO(datosFichasexamenestest.getValorCriticoBajo());
//      notif.setCVR_VALORCRITICOALTO(datosFichasexamenestest.getValorCriticoAlto() );
//      notif.setCVR_VALORCRITICOBAJO(datosFichasexamenestest.getValorCriticoBajo());
//      notif.setCT_DECIMALES(datosFichasexamenestest.get);
//      notif.setCT_DECIMALES(TRANSMITIDO);
//      notif.setCT_FORMULA(AMBOS_SEXOS);
//      notif.setC
			this.insertDatosFichasNotificacionTest(nroOrden, datosFichasexamenestest.getId().getDfetIdexamen(),
					datosFichasexamenestest.getId().getDfetIdtest(), notif, idUsuario);
		}

		return "Se registraron todas notificaciones de la orden.";
	}

	public String insertDatosFichasNotificacionExamenTestRelacionados(Long nroOrden, Long examenId, Long testId,
			ResultadoTestNotificacionDeUnaOrdenDTO notif, Long idUsuario) throws BiosLISDAOException, ParseException {

		List<DatosFichasexamenestest> lstNotificaciones = datosFichasDAO.getDatosTestCriticos(nroOrden, examenId);

		for (DatosFichasexamenestest datosFichasexamenestest : lstNotificaciones) {
			notif.setCE_ABREVIADO(datosFichasexamenestest.getCE_ABREVIADO());
			notif.setCERT_IDESTADORESULTADOTEST(
					BigDecimal.valueOf(datosFichasexamenestest.getDfetIdestadoresultadotest()));
//      notif.setCVR_VALORALTO(new BigDecimal(datosFichasexamenestest.getValorCriticoAlto()));
//      notif.setCVR_VALORBAJO(new BigDecimal(datosFichasexamenestest.getValorCriticoBajo()));
//      notif.setCVR_VALORCRITICOALTO(new BigDecimal(datosFichasexamenestest.getValorCriticoAlto()));
//      notif.setCVR_VALORCRITICOBAJO(new BigDecimal(datosFichasexamenestest.getValorCriticoBajo()));
			notif.setCT_DECIMALES(TRANSMITIDO);
			notif.setCT_FORMULA(AMBOS_SEXOS);
			this.insertDatosFichasNotificacionTest(nroOrden, datosFichasexamenestest.getId().getDfetIdexamen(),
					datosFichasexamenestest.getId().getDfetIdtest(), notif, idUsuario);
		}

		return "Se registraron todas notificaciones del examen.";
	}

	public static final String UltimoIdNotificacion = "SELECT DFTN_IDFICHATESTNOTIFICACION FROM DATOS_FICHASTESTNOTIFICACIONES  WHERE  rownum = 1 order BY 1 desc ";

	public String insertDatosFichasNotificacionTest(Long nroOrden, Long examenId, Long testId,
			ResultadoTestNotificacionDeUnaOrdenDTO dftnot, Long idUsuario) throws BiosLISDAOException, ParseException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		String mensaje = "Notificaciones ingresadas correctamente.";
		try {

			sesion.beginTransaction();
			datosfichastestnotificacionesDTO ultimo = getNotificacionNueva(dftnot);
			UltimaNoticacion ultNotConf = new UltimaNoticacion(nroOrden, examenId, testId, sesion);
			Long ultimoId = null;
			Integer ultimoIntento = ultNotConf.lastValue;

			if (ultNotConf.idDatosFichasTestNotificacion == null) { // es la primera de orden,examen,test
				ultimoId = this.insertaNotificacion(sesion, ultimo, true, null, dftnot, ultimoIntento);
				this.actualizaDatosTest(sesion, nroOrden, examenId, testId, ultimoId);
			}

			if (ultNotConf.idDatosFichasTestNotificacion != null) { // es nueva pero no es la
																	// primera
				this.insertaNotificacion(sesion, ultimo, false, ultNotConf.idDatosFichasTestNotificacion, dftnot,
						ultimoIntento);
			}

			sesion.getTransaction().commit();
		} catch (HibernateException he) {
			logger.error(he.getMessage());
			mensaje = "Notificaciones  ingresadas INCORRECTAMENTE";
			throw new BiosLISDAOException(
					"No se pudo ingresar notificación correctamente".concat(he.getLocalizedMessage()));
		} finally {
			if (sesion.isOpen()) {
				sesion.close();
			}
		}
		return mensaje;
	}

	private datosfichastestnotificacionesDTO getNotificacionNueva(ResultadoTestNotificacionDeUnaOrdenDTO dftnot) {

		List<datosfichastestnotificacionesDTO> lista = dftnot.getNOTIFICACION();
		int i = 0;
		int max = -1;
		datosfichastestnotificacionesDTO ultimo = null;
		for (datosfichastestnotificacionesDTO dftnotDto : lista) {
			i = Integer.valueOf(dftnotDto.getNOTIFICACIONID());
			if (i > max) {
				max = i;
				ultimo = dftnotDto;
			}
		}

		return ultimo;
	}

	private void actualizaDatosTest(Session sesion, datosfichastestnotificacionesDTO noti, Long nroOrden, Long examenId,
			Long testId) throws BiosLISDAOException {

		try {

			Query query = sesion.createSQLQuery(sqlActualizaDatosTest);

			query.setParameter("dfet_valorcriticonotificado", noti.getNOTIFICACIONCONFIRM());
			query.setParameter("dfet_notavalorcritico", noti.getNOTIFICACIONNOTE());
			query.setParameter("dfet_idusuarionotificacritico", noti.getNOTIFICACIONRECEPTOR());
			query.setParameter("dfet_tiponotificacion", noti.getNOTIFICACIONCHANNEL());
			query.setParameter("dfet_profesionalnotificado", noti.getNOTIFICACIONRECEPTOR());
			query.setParameter("norden", nroOrden);
			query.setParameter("idexamen", examenId);
			query.setParameter("idtest", testId);
			query.executeUpdate();
		} catch (HibernateException he) {
			logger.error(he.getLocalizedMessage());
			throw new BiosLISDAOException(he.getLocalizedMessage());
		}

	}

	private void actualizaDatosTest(Session sesion, Long nroOrden, Long examenId, Long testId,
			Long idDatosFichastestnotificacion) throws BiosLISDAOException {

		try {

			Query query = sesion.createSQLQuery(sqlActualizaIdNotificacionDatosTest);

			query.setParameter("idNotificacion", idDatosFichastestnotificacion);
			query.setParameter("norden", nroOrden);
			query.setParameter("idexamen", examenId);
			query.setParameter("idtest", testId);
			query.executeUpdate();
		} catch (HibernateException he) {
			logger.error("");
			throw new BiosLISDAOException(he.getLocalizedMessage());
		}

	}

	private Long insertaNotificacion(Session sesion, datosfichastestnotificacionesDTO noti, Boolean esPrimera,
			Long idFichaTestNotificacion, ResultadoTestNotificacionDeUnaOrdenDTO dftnot, Integer ultimoIntento)
			throws BiosLISDAOException {

		try {

			String sql = sqlInsertaNotificacion;
			ultimoIntento++;
			if (esPrimera) {
				sql = sqlInsertaPrimeraNotificacion;
			}
			Query query = sesion.createSQLQuery(sql);
			if (!esPrimera) {
				query.setLong("DftnIdfichatestnotificacion", idFichaTestNotificacion);

			}

			query.setLong("DftnNumerointentos", ultimoIntento);
			query.setString("Dftnmensajenotificacion", noti.getNOTIFICACIONMESAGE());
			query.setString("DftnEmailenvio", noti.getNOTIFICACIONSENDTO());
			query.setInteger("DftnIdTipoReceptor", Integer.valueOf(noti.getNOTIFICACIONRECEPTOR()));
			query.setInteger("DftnIDUsuario", Integer.valueOf(noti.getNOTIFICACIONUSER()));
			query.setString("DftnNotificacionConfirm", noti.getNOTIFICACIONCONFIRM());
			query.setString("DftnNombreReceptor", noti.getNOTIFICACIONNAME());
			query.setString("DftnIDNotificacion", noti.getNOTIFICACIONCHANNEL());
			query.setString("DftnNota", noti.getNOTIFICACIONNOTE());
			if (noti.getNOTIFICACIONTYPERROR() == null || noti.getNOTIFICACIONTYPERROR().equals("")) {
				query.setParameter("DftnEstadoExcepcion", null);
			} else {
				query.setParameter("DftnEstadoExcepcion", noti.getNOTIFICACIONTYPERROR());
			}
			query.setParameter("DftnEstadoExcepcionNota", noti.getNOTIFICACIONERRORNOTE());
			query.executeUpdate();

			if (noti.getNOTIFICACIONCONFIRM() == null || noti.getNOTIFICACIONCONFIRM().equals("")) {
				throw new BiosLISDAOException("Falta parámetro confirmar notificacion ");
			}

			if (noti.getNOTIFICACIONCONFIRM().toUpperCase().equals("S")) {
				this.actualizaDatosTest(sesion, noti, dftnot.getDF_NORDEN().longValueExact(),
						dftnot.getDFE_IDEXAMEN().longValueExact(), dftnot.getDFET_IDTEST().longValueExact());
			}
			if (esPrimera) {
				sql = "SELECT BIOSLIS.SEQ_DATOS_FICHASTESTNOTIFICACI.CURRVAL FROM DUAL";
				query = sesion.createSQLQuery(sql);
				Long res = ((BigDecimal) query.uniqueResult()).longValueExact();
				return res;
			}

			if (dftnot.getDFET_IDFICHATESTNOTIFICACION() != null) {
				return dftnot.getDFET_IDFICHATESTNOTIFICACION().longValueExact();
			} else {
				return idFichaTestNotificacion;
			}
		} catch (HibernateException he) {
			logger.error("No se pudo insertar notificacion {} intento {} ", idFichaTestNotificacion,
					noti.getNOTIFICACIONID());
			throw new BiosLISDAOException(he.getLocalizedMessage());
		}
	}

	private void actualizaNotificacion(Session sesion, datosfichastestnotificacionesDTO noti,
			ResultadoTestNotificacionDeUnaOrdenDTO dftnot) throws BiosLISDAOException {

		try {
			Query query = sesion.createSQLQuery(sqlActualizaNotificacion);
			query.setParameter("DftnIdfichatestnotificacion", (dftnot.getDFET_IDFICHATESTNOTIFICACION()));
			query.setParameter("DftnNumerointentos", noti.getNOTIFICACIONID());
			if (noti.getNOTIFICACIONTYPERROR() == null || noti.getNOTIFICACIONTYPERROR().equals("")) {
				query.setParameter("DftnEstadoExcepcion", null);
			} else {
				query.setParameter("DftnEstadoExcepcion", noti.getNOTIFICACIONTYPERROR());
			}
			query.setParameter("DftnEstadoExcepcionNota", noti.getNOTIFICACIONERRORNOTE());
			query.executeUpdate();
		} catch (HibernateException he) {
			logger.error("");
			throw new BiosLISDAOException(he.getLocalizedMessage());
		}
	}

	private static final String sqlConfirmaNotificacion = "UPDATE DATOS_FICHASEXAMENESTEST SET DFET_IDFICHATESTNOTIFICACION = :idnotificacion WHERE DFET_NORDEN = :norden AND DFET_IDEXAMEN =  :idexamen  AND  DFET_IDTEST = :idtest";
	private static final String sqlActualizaNotificacion = "UPDATE DATOS_FICHASTESTNOTIFICACIONES SET DFTN_ESTADOEXCEPCION  = :DftnEstadoExcepcion,DFTN_NOTAESTADOEXCEPCION = :DftnEstadoExcepcionNota "
			+ "WHERE DFTN_IDFICHATESTNOTIFICACION = :DftnIdfichatestnotificacion AND DFTN_NUMEROINTENTOS  = :DftnNumerointentos";
	private static final String sqlInsertaNotificacion = "INSERT  INTO DATOS_FICHASTESTNOTIFICACIONES (DFTN_IDFICHATESTNOTIFICACION, DFTN_NUMEROINTENTOS,  DFTN_MENSAJENOTIFICACION, DFTN_EMAILENVIO,"
			+ " DFTN_IDTIPORECEPTOR,  DFTN_IDUSUARIOENVIA,  DFTN_VALORCRITICONOTIFICADO, "
			+ "DFTN_FECHAENVIO, DFTN_FECHARECEPCION,  DFTN_NOMBRERECEPTOR,  DFTN_IDVIANOTIFICACION, DFTN_NOTA,  DFTN_ESTADOEXCEPCION, DFTN_NOTAESTADOEXCEPCION) "
			+ "VALUES (:DftnIdfichatestnotificacion,:DftnNumerointentos,:Dftnmensajenotificacion,:DftnEmailenvio,:DftnIdTipoReceptor,"
			+ ":DftnIDUsuario,:DftnNotificacionConfirm,SYSDATE,NULL,:DftnNombreReceptor,:DftnIDNotificacion,:DftnNota,:DftnEstadoExcepcion,:DftnEstadoExcepcionNota)";
	private static final String sqlInsertaPrimeraNotificacion = "INSERT  INTO DATOS_FICHASTESTNOTIFICACIONES (DFTN_NUMEROINTENTOS,  DFTN_MENSAJENOTIFICACION, DFTN_EMAILENVIO,"
			+ " DFTN_IDTIPORECEPTOR,  DFTN_IDUSUARIOENVIA,  DFTN_VALORCRITICONOTIFICADO, "
			+ "DFTN_FECHAENVIO, DFTN_FECHARECEPCION,  DFTN_NOMBRERECEPTOR,  DFTN_IDVIANOTIFICACION, DFTN_NOTA,  DFTN_ESTADOEXCEPCION, DFTN_NOTAESTADOEXCEPCION) "
			+ "VALUES (:DftnNumerointentos,:Dftnmensajenotificacion,:DftnEmailenvio,:DftnIdTipoReceptor,"
			+ ":DftnIDUsuario,:DftnNotificacionConfirm,SYSDATE,NULL,:DftnNombreReceptor,:DftnIDNotificacion,:DftnNota,:DftnEstadoExcepcion,:DftnEstadoExcepcionNota)";
	private static final String sqlActualizaDatosTest = "" + "UPDATE DATOS_FICHASEXAMENESTEST SET "
			+ " DFET_VALORCRITICONOTIFICADO = :dfet_valorcriticonotificado,  "
			+ " DFET_NOTAVALORCRITICO = :dfet_notavalorcritico, " + " DFET_FECHAVALORCRITICO = SYSDATE,  "
			+ " DFET_IDUSUARIONOTIFICACRITICO = :dfet_idusuarionotificacritico, "
			+ " DFET_TIPONOTIFICACION = :dfet_tiponotificacion, "
			+ " DFET_PROFESIONALNOTIFICADO = :dfet_profesionalnotificado  "
			+ " WHERE DFET_NORDEN = :norden AND DFET_IDEXAMEN =  :idexamen  AND  DFET_IDTEST = :idtest";

	private static final String sqlActualizaIdNotificacionDatosTest = "" + "UPDATE DATOS_FICHASEXAMENESTEST SET "
			+ " DFET_IDFICHATESTNOTIFICACION = :idNotificacion "
			+ " WHERE DFET_NORDEN = :norden AND DFET_IDEXAMEN =  :idexamen  AND  DFET_IDTEST = :idtest";

	private static final String sqlUltimaNotificacion = "SELECT  dftn.DFTN_IDFICHATESTNOTIFICACION,MAX(dftn.DFTN_NUMEROINTENTOS) DFTN_NUMEROINTENTOS "
			+ " FROM BIOSLIS.DATOS_FICHASEXAMENESTEST dfet"
			+ "   LEFT OUTER JOIN  DATOS_FICHASTESTNOTIFICACIONES dftn ON (dfet.DFET_IDFICHATESTNOTIFICACION = dftn.DFTN_IDFICHATESTNOTIFICACION)"
			+ " WHERE dfet.DFET_NORDEN = :nOrden AND dfet.DFET_IDEXAMEN = :idExamen AND dfet.DFET_IDTEST = :idTest "
			+ "GROUP BY dftn.DFTN_IDFICHATESTNOTIFICACION";
	private static final String sqlgetEstadoDocumento = "SELECT" + " CAST(" + " CASE"
			+ " WHEN COUNT(*) <> 0" + "THEN 'S'" + " ELSE 'N'"
			+ " END AS VARCHAR(1)" + ") AS ESTADODOCUMENTO"+ " FROM " + " DATOS_FICHASDOCUMENTOS"
			+ " WHERE " + " DFD_NORDEN = :nOrden";
	private static final String sqlgetObsExamenes =""
			+ " SELECT "
			+ " df.DF_OBSERVACION OBSDF"
			+ " ,dp.DP_OBSERVACION OBSDP"
			+ " ,dfm.DFM_RECEPCIONOBS OBSRECEPCION"
			+ " ,dfm.DFM_TMOBSERVACION OBSTM"
			+ " ,dp.DP_NOMBRES || ' '|| dp.DP_PRIMERAPELLIDO || ' '|| dp.DP_SEGUNDOAPELLIDO NOMBREDP"
			+ " ,df.DF_NORDEN NORDEN"
			+ " ,'USUARIODF' USUARIODF"
			+ " ,TO_CHAR(df.DF_FECHAORDEN,'DD-MM-YYYY') FECHADF"
			+ " ,ce.CE_ABREVIADO EXAMEN"
			+ " ,dfm.DFM_ESTADOTM ESTADOTM"
			+ " ,TO_CHAR(dfm.DFM_FECHATM,'DD-MM-YYYY') FECHATM"
			+ " ,dutm.DU_NOMBRES || ' '|| dutm.DU_PRIMERAPELLIDO || ' '|| dutm.DU_SEGUNDOAPELLIDO USUARIOTM"
			+ " ,dfm.DFM_ESTADORM ESTADORM"
			+ " ,TO_CHAR(dfm.DFM_FECHARM,'DD-MM-YYYY') FECHARM"
			+ " ,durm.DU_NOMBRES || ' '|| durm.DU_PRIMERAPELLIDO || ' '|| durm.DU_SEGUNDOAPELLIDO USUARIORM"
			+ " FROM DATOS_FICHAS df"
			+ " INNER JOIN DATOS_PACIENTES dp ON df.DF_IDPACIENTE = dp.DP_IDPACIENTE"
			+ " INNER JOIN DATOS_FICHASMUESTRAS dfm ON df.DF_NORDEN = dfm.DFM_NORDEN"
			+ " INNER JOIN DATOS_FICHASEXAMENES dfe ON df.DF_NORDEN = dfe.DFE_NORDEN "
			+ " INNER JOIN CFG_EXAMENES ce ON dfe.DFE_IDEXAMEN = ce.CE_IDEXAMEN "
			+ " INNER JOIN DATOS_USUARIOS dutm ON dfm.DFM_IDUSUARIOTM = dutm.DU_IDUSUARIO "
			+ " INNER JOIN DATOS_USUARIOS durm ON dfm.DFM_IDUSUARIOTM = durm.DU_IDUSUARIO "
			+ " WHERE "
			+ " df.DF_NORDEN = :nOrden"
			+ " AND dfe.DFE_IDEXAMEN = :idExamen";
	private static final String sqlgetNotasExamen =""
			+ "SELECT dfe.DFE_NOTAINTERNA NOTAINTERNA ,dfe.DFE_NOTAEXAMEN NOTAEXAMEN ,dfe.DFE_NOTAINFORME NOTAPIE FROM DATOS_FICHASEXAMENES dfe WHERE dfe.DFE_NORDEN = :nOrden AND dfe.DFE_IDEXAMEN = :idExamen ";
	class UltimaNoticacion {

		private Integer lastValue;
		private Boolean isConfirmed;
		private Long idDatosFichasTestNotificacion;

		public UltimaNoticacion(Long nroOrden, Long examenId, Long testId, Session sesion) throws BiosLISDAOException {

			try {
				Query qry = sesion.createSQLQuery(sqlUltimaNotificacion)
						.setResultTransformer(Transformers.aliasToBean(ResultadoTestNotificacionDeUnaOrdenDTO.class));

				qry.setLong("nOrden", nroOrden);
				qry.setLong("idExamen", examenId);
				qry.setLong("idTest", testId);

				ResultadoTestNotificacionDeUnaOrdenDTO dftn = (ResultadoTestNotificacionDeUnaOrdenDTO) qry
						.uniqueResult();

				this.lastValue = Integer.valueOf(0);
				this.isConfirmed = false;

				if (dftn.getDFTN_IDFICHATESTNOTIFICACION() != null) {

					this.idDatosFichasTestNotificacion = dftn.getDFTN_IDFICHATESTNOTIFICACION().longValueExact();
					this.lastValue = dftn.getDFTN_NUMEROINTENTOS().toBigIntegerExact().intValueExact();
//          this.isConfirmed = dftn.
//          this.isConfirmed = 
				}
			} catch (HibernateException he) {
				logger.error(he.getLocalizedMessage());
				throw new BiosLISDAOException(he.getLocalizedMessage());
			}
		}

	}

	public String anulaoYerraNotificacion(ResultadoTestNotificacionDeUnaOrdenDTO notif) throws BiosLISDAOException {

		List<Integer> listIntentos = this
				.getListaAnulacionRechazoNotificacion(notif.getDFET_IDFICHATESTNOTIFICACION().longValueExact());

		Pair<List<datosfichastestnotificacionesDTO>, List<datosfichastestnotificacionesDTO>> parListas = this
				.getListasAnulacionRechazo(notif, listIntentos);

		List<datosfichastestnotificacionesDTO> listaAnulados = parListas.getLeft();
		List<datosfichastestnotificacionesDTO> listaErrados = parListas.getRight();

		return null;
	}

	private static String hqlQuery = "select dftn.dftnNumerointentos from DatosFichaTestNotificaciones dftn where dftn.dftn_estadoexcepcion not in ('A','E') order by dftn.dftnNumerointentos ";

	private void anularErrar() {

	}

	private List<Integer> getListaAnulacionRechazoNotificacion(Long idDatosFichatestnotificacion)
			throws BiosLISDAOException {

		Session sesion = null;
		try {
			sesion = HibernateUtil.getSessionFactory().openSession();
			return sesion.createQuery(hqlQuery).list();
		} catch (HibernateException he) {
			logger.error("");
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
	}

	private Pair<List<datosfichastestnotificacionesDTO>, List<datosfichastestnotificacionesDTO>> getListasAnulacionRechazo(
			ResultadoTestNotificacionDeUnaOrdenDTO dftnot, List<Integer> intentos) {

		List<datosfichastestnotificacionesDTO> lst = dftnot.getNOTIFICACION();
		List<datosfichastestnotificacionesDTO> listaAnulaciones = new ArrayList<>();
		List<datosfichastestnotificacionesDTO> listaErrores = new ArrayList<>();

		for (Integer nroIntento : intentos) {
			if (lst.get(nroIntento).getNOTIFICACIONTYPERROR().equals(Constante.NOTIFICA_ANULACION)) {
				listaAnulaciones.add(lst.get(nroIntento));
			} else if (lst.get(0).getNOTIFICACIONTYPERROR().equals(Constante.NOTIFICA_ERROR)) {
				listaErrores.add(lst.get(nroIntento));
			}
		}

		return Pair.of(listaAnulaciones, listaErrores);
	}

	private List<ExamenesResultadosDeUnaOrdenDTO> getExamenesResultadosDeUnaOrdenDTO(
			ParamResultadoExamenOrden prmResultadoExamenOrden, long nroOrden) throws BiosLISDAOException {

		Session sesion = null;

		try {
			sesion = HibernateUtil.getSessionFactory().openSession();

			List<Long> paramLst = null;

			if (prmResultadoExamenOrden.getExamenes().isEmpty()
					|| prmResultadoExamenOrden.getPaciente().getFnac().equals("")) {

				Query qry = sesion.createQuery(
						"select dfe.IddatosFichasExamenes.dfeIdexamen from DatosFichasexamenes dfe where dfe.IddatosFichasExamenes.dfeNorden = :nroOrden");
				qry.setLong("nroOrden", nroOrden);
				paramLst = (List<Long>) qry.list();
			} else {
				paramLst = new ArrayList<Long>(prmResultadoExamenOrden.getExamenes().size());

				for (ParamResultadoExamenDTO fila : prmResultadoExamenOrden.getExamenes()) {
					paramLst.add(fila.getDFE_IDEXAMEN().longValue());
					logger.debug("ID examen {}", fila.getDFE_IDEXAMEN().longValue());
				}
			}
			Query qry = sesion.createSQLQuery(sqlResultadosSelExamenesDeUnaOrden)
					.setResultTransformer(Transformers.aliasToBean(ExamenesResultadosDeUnaOrdenDTO.class));
			qry.setLong("nOrden", nroOrden);

			qry.setParameterList("lista", paramLst);
			List<ExamenesResultadosDeUnaOrdenDTO> lst = (List<ExamenesResultadosDeUnaOrdenDTO>) qry.list();
			logger.debug("-------------------------------------------------------------------------");
			logger.debug(lst);
			return lst;
		} catch (HibernateException he) {
			logger.error("");
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
	}

	void setResultadoHistorico(List<ResultadoTestPacienteDTO> lstResultado, ExamenesResultadosDeUnaOrdenDTO resultado)
			throws BiosLISDAOException {

		StringBuilder sb = new StringBuilder("");

		for (ResultadoTestPacienteDTO resultadoTestPacienteDTO : lstResultado) {

			sb.append(resultadoTestPacienteDTO.getDFET_RESULTADO());
			sb.append("|");
			sb.append(resultadoTestPacienteDTO.getDFET_FECHAINGRESORESULTADO());
			sb.append(",");

		}
		resultado.setResultadoHistorico(sb.toString());

	}

	void setDeltaCheck(List<ResultadoTestPacienteDTO> lstResultado, ExamenesResultadosDeUnaOrdenDTO resultado)
			throws BiosLISDAOException {

		// Mejorar: solo calcular deltacheck si correponde.

		List<DeltaCheckDTO> deltaCheckLst = this.datosPacientesDAO.getDeltaCheckTestPaciente(
				resultado.getDP_IDPACIENTE().longValue(), lstResultado.get(0).getDFET_IDTEST().longValue());
		resultado.setDeltaCheckLst(deltaCheckLst);
		DeltaCheckAptoDTO dcaDto = this.datosPacientesDAO.getDeltaCheckCalculadoTestPaciente(
				resultado.getDP_IDPACIENTE().longValue(), lstResultado.get(0).getDFET_IDTEST().longValue(),
				resultado.getDF_NORDEN().longValue());
		if (dcaDto != null) {
			dcaDto.setDFET_RESULTADONUMERICO(resultado.getDFET_RESULTADONUMERICO());
			resultado.setDeltaCheckCalculated(dcaDto.toDeltaCheckDTO());
		}

	}

	public String getEstadoDocumento(Long nOrden) throws BiosLISDAOException {
		Session sesion = null;
		String estadoDocumento;
		try {
			sesion = HibernateUtil.getSessionFactory().openSession();
			Query query  = sesion.createSQLQuery(sqlgetEstadoDocumento);
			query.setLong("nOrden", nOrden);
			estadoDocumento = (String) query.uniqueResult();
			logger.debug(estadoDocumento);
			sesion.close();
		} catch (HibernateException he) {
			logger.error("");
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return estadoDocumento;
	}

	public ObservacionCRDTO getObsExamenes(Long nOrden,Long idExamen) throws BiosLISDAOException {
		Session sesion = null;
		ObservacionCRDTO OBS = null;
		try {
			sesion = HibernateUtil.getSessionFactory().openSession();
			Query query  = sesion.createSQLQuery(sqlgetObsExamenes).setResultTransformer(Transformers.aliasToBean(ObservacionCRDTO.class));
			query.setLong("nOrden", nOrden);
			query.setLong("idExamen", idExamen);
			OBS =  (ObservacionCRDTO) query.uniqueResult();
			sesion.close();
		} catch (HibernateException he) {
			logger.error("");
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return OBS;
	}
	public ExamenNotasDTO getNotasExamen(Long nOrden,Long idExamen) throws BiosLISDAOException {
		Session sesion = null;
		ExamenNotasDTO notas = null;
		try {
			sesion = HibernateUtil.getSessionFactory().openSession();
			Query query  = sesion.createSQLQuery(sqlgetNotasExamen).setResultTransformer(Transformers.aliasToBean(ExamenNotasDTO.class));
			query.setLong("nOrden", nOrden);
			query.setLong("idExamen", idExamen);
			notas =  (ExamenNotasDTO) query.uniqueResult();
			sesion.close();
		} catch (HibernateException he) {
			logger.error("");
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return notas;
	}
	
	public Boolean updateNotasExamen(HashMap<String, Object> notes) throws BiosLISDAOException {
		Session sesion = null;
		Boolean validador = false;
		try {//sqlUpdateDfe_Notas
			sesion = HibernateUtil.getSessionFactory().openSession();
			sesion.beginTransaction();
			Query query = sesion.createSQLQuery(sqlUpdateDfe_Notas);
			query.setParameter("notaInterna", notes.get("internalNote").toString());
			query.setParameter("notaExamen", notes.get("examNote").toString());
			query.setParameter("notaInforme", notes.get("footerNote").toString());
			query.setParameter("nOrden", notes.get("orderId").toString());
			query.setParameter("idExamen", notes.get("examId").toString());
			query.executeUpdate();
			sesion.getTransaction().commit();
			sesion.close();
		} catch (HibernateException he) {
			logger.error("");
			throw new BiosLISDAOException(he.getLocalizedMessage());
		} finally {
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
		return validador;
	}
}