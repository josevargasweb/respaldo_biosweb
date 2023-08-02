package com.grupobios.bioslis.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.FiltroTestDTO;

public class DaoHelper {

	private static Logger logger = LogManager.getLogger(DaoHelper.class);

	public DaoHelper() {
	}

	// CAMBIOS REALIZADOS POR CRISTIAN 14-11
	public String genSql(BCFichaFiltroDTO bcFichaFiltroDTO) throws BiosLISException {
		String sCond = "";
		this.normalize(bcFichaFiltroDTO);
		sCond = this.genSqlCondicionBOFull(bcFichaFiltroDTO);
		logger.debug("Condiciones consulta {}", sCond);

		return _sqlOrdenesConDatosExamenes + sCond;
	}

	// AQUI NORMALIZA LOS DATOS
	private void normalize(BCFichaFiltroDTO bcFichaFiltroDTO) throws BiosLISException {

		if (bcFichaFiltroDTO.getBo_apellido() == null || bcFichaFiltroDTO.getBo_apellido().trim().equals("")) {
			bcFichaFiltroDTO.setBo_apellido(null);
		}
		if (bcFichaFiltroDTO.getBo_nombre() == null || bcFichaFiltroDTO.getBo_nombre().trim().equals("")) {
			bcFichaFiltroDTO.setBo_nombre(null);
		}
		if (bcFichaFiltroDTO.getBo_nroDocumento() == null || bcFichaFiltroDTO.getBo_nroDocumento().trim().equals("")) {
			bcFichaFiltroDTO.setBo_nroDocumento(null);
		}

		if (bcFichaFiltroDTO.getBo_host() == null || bcFichaFiltroDTO.getBo_host().trim().equals("")) {
			bcFichaFiltroDTO.setBo_host(null);
		}

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

	}

	// MODIFICADO POR CRISTIAN 10-11 PARA BUSCADORES DE ORDENES POR FILTROS
	public String genSqlBOFull(BCFichaFiltroDTO bcFichaFiltroDTO) throws BiosLISException {

		String sCond = "";
		this.normalize(bcFichaFiltroDTO);
		sCond = this.genSqlCondicionBOFull(bcFichaFiltroDTO);
		logger.debug("Condiciones consulta {} BoFull", sCond);
		return _sqlOrdenesFullxFiltro + sCond;
	}

	// ****************************************************************************************************************************

	// SE REALIZAN CAMBIOS PARA FILTRO POR SECCIONES
	public String genSqlOrdenesConDatosExamenesConSecciones(BCFichaFiltroDTO bcFichaFiltroDTO) throws BiosLISException {

		String sCond = "";

		this.normalize(bcFichaFiltroDTO);

		sCond = this.genSqlCondicionBOFull(bcFichaFiltroDTO);
		logger.debug("Condiciones consulta {} genSqlOrdenesConDatosExamenesConSecciones ", sCond);
		return _sqlOrdenesConDatosExamenesConSecciones + sCond;

	}

	// creado solo devolver condicion --cristian 14-11
	// ************************************
	public String genSqlCondicionBOFull(BCFichaFiltroDTO bcFichaFiltroDTO) throws BiosLISException {

		String sCond = "";
		this.normalize(bcFichaFiltroDTO);

		if (bcFichaFiltroDTO.getBo_nOrdenIni() != null && bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
			sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
			sCond += " df.DF_NORDEN <= :nOrdenFin AND ";
		} else {
			if (bcFichaFiltroDTO.getBo_nOrdenIni() != null || bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
				if (bcFichaFiltroDTO.getBo_nOrdenIni() != null) {
					sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
					sCond += " df.DF_NORDEN <= :nOrdenIni AND ";
				}
				if (bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
					sCond += " df.DF_NORDEN >= :nOrdenFin AND ";
					sCond += " df.DF_NORDEN <= :nOrdenFin AND ";
				}
			}
		}
		if (bcFichaFiltroDTO.getBo_procedencia() != null && bcFichaFiltroDTO.getBo_procedencia() != -1) {
			sCond += " df.DF_IDPROCEDENCIA = :idProcedencia AND ";
		}

		if ((bcFichaFiltroDTO.getBo_nOrdenIni() != null && bcFichaFiltroDTO.getBo_nOrdenFin() != null)
				|| (bcFichaFiltroDTO.getBo_nOrdenIni() == null && bcFichaFiltroDTO.getBo_nOrdenFin() == null)) {

			if (bcFichaFiltroDTO.getBo_fIni() != null && bcFichaFiltroDTO.getBo_fFin() != null) {
				sCond += " df.DF_FECHAORDEN >= TO_DATE(:fini,'DD-MM-YYYY HH24:MI:SS') AND ";
				sCond += " df.DF_FECHAORDEN <= TO_DATE(:ffin,'DD-MM-YYYY HH24:MI:SS') + 1 AND ";
			} else {
				if (bcFichaFiltroDTO.getBo_fIni() != null || bcFichaFiltroDTO.getBo_fFin() != null) {
					if (bcFichaFiltroDTO.getBo_fIni() != null) {
						sCond += " df.DF_FECHAORDEN LIKE TO_DATE(:fini,'DD-MM-YYYY HH24:MI:SS') AND ";
					}
					if (bcFichaFiltroDTO.getBo_fFin() != null) {
						sCond += " df.DF_FECHAORDEN  LIKE TO_DATE(:ffin,'DD-MM-YYYY HH24:MI:SS') AND ";
					}
				}
			}
		}

		if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().equals("")) {
			sCond += " ( UPPER(dp.DP_PRIMERAPELLIDO) like UPPER(:apellido) OR "
					+ " UPPER(dp.DP_SEGUNDOAPELLIDO) like UPPER(:apellido)) AND ";
		}
		if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
			sCond += " UPPER(dp.DP_NOMBRES) like UPPER(:nombre) AND ";
		}

		if (bcFichaFiltroDTO.getBo_tipoDocumento() != null && bcFichaFiltroDTO.getBo_tipoDocumento() != -1) {
			sCond += " dp.DP_IDTIPODOCUMENTO = :tipoDoc AND ";
		}
		if (bcFichaFiltroDTO.getBo_nroDocumento() != null && !bcFichaFiltroDTO.getBo_nroDocumento().trim().isEmpty()) {
			sCond += " dp.DP_NRODOCUMENTO = :nDoc  AND ";
		}
		if (bcFichaFiltroDTO.getBo_tipoAtencion() != null && bcFichaFiltroDTO.getBo_tipoAtencion() != -1) {
			sCond += " df.DF_IDTIPOATENCION = :tipoAtencion AND ";
		}
		if (bcFichaFiltroDTO.getBo_localizacion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1) {
			sCond += " df.DF_IDLOCALIZACION = :idLocalizacion AND ";
		}

		if (bcFichaFiltroDTO.getBo_servicio() != null && bcFichaFiltroDTO.getBo_servicio() != -1) {
			sCond += " df.DF_IDSERVICIO = :idServicio AND ";
		}

		if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1) {
			sCond += "ce.ce_CODIGOEXAMEN= :idExamen AND ";
		}
		if (bcFichaFiltroDTO.getBo_seccion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1) {
			sCond += " ce.CE_IDSECCION= :idSeccion AND ";
		}
		if (bcFichaFiltroDTO.getBo_laboratorio() != null && bcFichaFiltroDTO.getBo_laboratorio() != -1) {
			sCond += " clab.CLAB_IDLABORATORIO= :idLaboratorio AND ";
		}
		if (bcFichaFiltroDTO.getBo_convenio() != null && bcFichaFiltroDTO.getBo_convenio() != -1) {
			sCond += " cc.CC_IDCONVENIO= :idConvenio AND ";
		}

		int pos = sCond.lastIndexOf("AND");

		if (pos != -1) {
			sCond = sCond.substring(0, pos);

		}

		if (!sCond.trim().equals("")) {
			sCond = "where " + sCond;
		}
		logger.debug("Condiciones consulta {} genSqlCondicionBOFull", sCond);
		return sCond;
	}
	// ****************************************************************************************************************************

	private static final String _sqlOrdenesConDatosExamenes = ""
			+ "SELECT  TO_CHAR(DF_FECHAORDEN,'DD-MM HH24:MI') SDF_FECHAORDEN, DF_NORDEN, dp.DP_NOMBRES ,"
			+ "TO_CHAR(dp.DP_FNACIMIENTO,'DD-MM-YYYY ') SDP_FNACIMIENTO , ls.LDVS_DESCRIPCION  ,dp.DP_OBSERVACION, cc.CC_ABREVIADO, "
			+ "lt.LDVTD_DESCRIPCION,dp.DP_NRODOCUMENTO,ct.CTA_DESCRIPCION, cp.CP_DESCRIPCION , cs.CS_DESCRIPCION ,"
			+ "dp.DP_EMAIL, dp.DP_IDPACIENTE,dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO,"
			+ "cd.CD_DESCRIPCION, df.DF_OBSERVACION, cm.CM_NOMBRES,cm.CM_PRIMERAPELLIDO, cm.CM_SEGUNDOAPELLIDO, ce.CE_IDSECCION ,"
			+ "dfe.DFE_CODIGOESTADOEXAMEN,cee.CEE_DESCRIPCIONESTADOEXAMEN, dfe.DFE_EXAMENESURGENTE, df.DF_HOST "
			+ "FROM  DATOS_FICHAS df       " + "JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE)     "
			+ "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
			+ "JOIN LDV_TIPOSDOCUMENTOS lt ON (lt.LDVTD_IDTIPODOCUMENTO = dp.DP_IDTIPODOCUMENTO) "
			+ "JOIN CFG_TIPOATENCION ct ON (ct.CTA_IDTIPOATENCION = df.DF_IDTIPOATENCION)    "
			+ "LEFT OUTER JOIN CFG_PROCEDENCIAS cp ON (cp.CP_IDPROCEDENCIA = df.DF_IDPROCEDENCIA )     "
			+ "LEFT OUTER JOIN  CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.DF_IDLOCALIZACION ) "
			+ "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
			+ "JOIN LDV_SEXO ls ON (ls.LDVS_IDSEXO = dp.DP_IDSEXO) "
			+ "JOIN CFG_ESTADOSEXAMENES cee ON (dfe.DFE_CODIGOESTADOEXAMEN = cee.CEE_CODIGOESTADOEXAMEN) "

			+ "LEFT OUTER JOIN CFG_DIAGNOSTICOS cd ON (cd.CD_IDDIAGNOSTICO = df.DF_IDDIAGNOSTICO) "
			+ "LEFT OUTER JOIN CFG_CONVENIOS cc ON (cc.CC_IDCONVENIO = df.DF_IDCONVENIO) "
			+ "LEFT OUTER JOIN CFG_MEDICOS cm  ON (cm.CM_IDMEDICO = df.DF_IDMEDICO) ";

//cambiado por cristian 10-11 para filtrar ordenes (lo que esta en minuscula) // jose cambia DF_FECHAORDEN DD-MM HH24:MI a HH24:MI DD/MM/YYYY
	private static final String _sqlOrdenesFullxFiltro = "SELECT   DF_NORDEN,TO_CHAR(DF_FECHAORDEN,'HH24:MI DD/MM/YYYY') SDF_FECHAORDEN, df.DF_OBSERVACION,  "
			+ "     dp.DP_IDPACIENTE,lt.LDVTD_DESCRIPCION,dp.DP_NRODOCUMENTO,dp.DP_NOMBRES , dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO,dp.DP_EMAIL, "
			+ "      TO_CHAR(dp.DP_FNACIMIENTO,'DD-MM-YYYY ') SDP_FNACIMIENTO , ls.LDVS_DESCRIPCION  ,dp.DP_OBSERVACION, "
			+ "      ct.CTA_DESCRIPCION, cp.CP_DESCRIPCION , cc.CC_ABREVIADO, df.DF_IDPREVISION,   "
			+ "      df.DF_IDLOCALIZACION,cd.CD_DESCRIPCION,df.DF_IDCENTRODESALUD,df.DF_IDPRIORIDADATENCION, "
			+ "       cs.CS_DESCRIPCION, df.DF_IDFICHAESTADOTM , ldvfet_descripcion , df.df_codigolocalizacion as localizacion , "
			+ "(select  ce.ce_escultivo from cfg_examenes ce  " + "          inner join datos_fichasexamenes dfesel  "
			+ "           on ce.ce_idexamen = dfesel.dfe_idexamen  "
			+ "          where dfesel.dfe_norden = df.df_norden  " + "          order by ce.ce_escultivo desc "
			+ "          FETCH FIRST 1 ROWS Only) as tienecultivo ,"
			+ " ( select dfes.dfe_codigoestadoexamen  from datos_fichas dfs "
			+ "          inner join datos_fichasexamenes dfes  " + "           on dfes.dfe_norden = dfs.df_norden  "
			+ "          where dfes.dfe_norden = df.DF_NORDEN " + "          order by dfes.dfe_codigoestadoexamen desc "
			+ "          FETCH FIRST 1 ROWS Only) as DFE_CODIGOESTADOEXAMEN , "
			+ "( select cee.cee_descripcionestadoexamen  from datos_fichas dfs inner join datos_fichasexamenes dfes  on dfes.dfe_norden = dfs.df_norden  "
			+ "   inner join cfg_estadosexamenes cee on cee.cee_codigoestadoexamen =  dfes.dfe_codigoestadoexamen  "
			+ "where dfes.dfe_norden = df.DF_NORDEN  order by dfes.dfe_codigoestadoexamen desc FETCH FIRST 1 ROWS Only) as CEE_DESCRIPCIONESTADOEXAMEN ,"
			+ "( select dfes.dfe_IDBACESTADO  from datos_fichas dfs "
			+ "          inner join datos_fichasexamenes dfes  " + "           on dfes.dfe_norden = dfs.df_norden  "
			+ "          where dfes.dfe_norden = df.DF_NORDEN  "
			+ "          order by dfes.dfe_codigoestadoexamen desc "
			+ "          FETCH FIRST 1 ROWS Only) AS DFE_IDBACESTADO,  "
			+ "           ( select dfes.dfe_IDBACESTADO  from datos_fichas dfs "
			+ "          inner join datos_fichasexamenes dfes  " + "           on dfes.dfe_norden = dfs.df_norden  "
			+ "          where dfes.dfe_norden = df.DF_NORDEN  "
			+ "          order by dfes.dfe_codigoestadoexamen desc "
			+ "          FETCH FIRST 1 ROWS Only) AS CBE_DESCRIPCION, df.DF_IDPROCEDENCIA "
			+ "       FROM  DATOS_FICHAS df      "
			+ "      JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE)  "
			+ "      JOIN LDV_TIPOSDOCUMENTOS lt ON (lt.LDVTD_IDTIPODOCUMENTO = dp.DP_IDTIPODOCUMENTO)  "
			+ "      JOIN CFG_TIPOATENCION ct ON (ct.CTA_IDTIPOATENCION = df.DF_IDTIPOATENCION)     "
			+ "      JOIN CFG_PROCEDENCIAS cp ON (cp.CP_IDPROCEDENCIA = df.DF_IDPROCEDENCIA )      "
			+ "      jOIN  CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.DF_IDSERVICIO )  "
			+ "      JOIN LDV_SEXO ls ON (ls.LDVS_IDSEXO = dp.DP_IDSEXO)  "
			+ "      JOIN CFG_DIAGNOSTICOS cd ON (cd.CD_IDDIAGNOSTICO = df.DF_IDDIAGNOSTICO)  "
			+ "      JOIN CFG_CONVENIOS cc ON (cc.CC_IDCONVENIO = df.DF_IDCONVENIO)  "
			+ "      join  ldv_fichasestadostm ldvfet on (ldvfet.ldvfet_idfichaestadotm = df.DF_IDFICHAESTADOTM)  ";

	// Añadido por Marco Caracciolo 27/10/2022

	private static final String _sqlOrdenesConDatosExamenesConSecciones = ""
			+ "SELECT  TO_CHAR(DF_FECHAORDEN,'DD-MM HH24:MI') SDF_FECHAORDEN, DF_NORDEN, dp.DP_NOMBRES ,"
			+ "TO_CHAR(dp.DP_FNACIMIENTO,'DD-MM-YYYY ') SDP_FNACIMIENTO , ls.LDVS_DESCRIPCION  ,dp.DP_OBSERVACION, cc.CC_ABREVIADO, "
			+ "lt.LDVTD_DESCRIPCION,dp.DP_NRODOCUMENTO,ct.CTA_DESCRIPCION, cp.CP_DESCRIPCION , cs.CS_DESCRIPCION ,"
			+ "dp.DP_EMAIL, dp.DP_IDPACIENTE,dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO,"
			+ "cd.CD_DESCRIPCION, df.DF_OBSERVACION, cm.CM_NOMBRES,cm.CM_PRIMERAPELLIDO, cm.CM_SEGUNDOAPELLIDO, ce.CE_IDSECCION ,"
			+ "dfe.DFE_CODIGOESTADOEXAMEN,cee.CEE_DESCRIPCIONESTADOEXAMEN, dfe.DFE_EXAMENESURGENTE,ce.CE_CODIGOEXAMEN, ce.CE_ABREVIADO, "
			+ "csec.CSEC_DESCRIPCION,dfe.DFE_IDEXAMEN " + "FROM  DATOS_FICHAS df       "
			+ "JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE)    "
			+ "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
			+ "JOIN LDV_TIPOSDOCUMENTOS lt ON (lt.LDVTD_IDTIPODOCUMENTO = dp.DP_IDTIPODOCUMENTO) "
			+ "JOIN CFG_TIPOATENCION ct ON (ct.CTA_IDTIPOATENCION = df.DF_IDTIPOATENCION)    "
			+ "LEFT OUTER JOIN CFG_PROCEDENCIAS cp ON (cp.CP_IDPROCEDENCIA = df.DF_IDPROCEDENCIA )     "
			+ "LEFT OUTER JOIN  CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.DF_IDSERVICIO ) "
			+ "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
			+ "JOIN LDV_SEXO ls ON (ls.LDVS_IDSEXO = dp.DP_IDSEXO) "
			+ "JOIN CFG_ESTADOSEXAMENES cee ON (dfe.DFE_CODIGOESTADOEXAMEN = cee.CEE_CODIGOESTADOEXAMEN) "

			+ "LEFT OUTER JOIN CFG_DIAGNOSTICOS cd ON (cd.CD_IDDIAGNOSTICO = df.DF_IDDIAGNOSTICO) "
			+ "LEFT OUTER JOIN CFG_SECCIONES csec ON (csec.CSEC_IDSECCION = ce.CE_IDSECCION ) "
			+ "LEFT OUTER JOIN CFG_CONVENIOS cc ON (cc.CC_IDCONVENIO = df.DF_IDCONVENIO) "
			+ "LEFT OUTER JOIN CFG_MEDICOS cm  ON (cm.CM_IDMEDICO = df.DF_IDMEDICO) ";

	private static String sqlGetTestByFiltroTest = ""
			+ "SELECT distinct CT_IDTEST , CT_ABREVIADO, CT_CODIGO ,CT_FORMULA,CT_SORT " + "FROM CFG_TEST ct "
			+ "LEFT OUTER JOIN CFG_SECCIONES csec on (csec.CSEC_IDSECCION = ct.CT_IDSECCION) "
			+ "LEFT OUTER JOIN CFG_LABORATORIOS clab on (csec.CSEC_IDLABORATORIO = clab.CLAB_IDLABORATORIO) ";

	private static String sqlGetTestByFiltroTest2 = ""
			+ "SELECT distinct CT_IDTEST , CT_ABREVIADO, CT_CODIGO ,CT_FORMULA,CT_SORT " + "FROM CFG_TEST ct "
			+ "LEFT OUTER JOIN CFG_SECCIONES csec on (csec.CSEC_IDSECCION = ct.CT_IDSECCION) "
			+ "LEFT OUTER JOIN CFG_LABORATORIOS clab on (csec.CSEC_IDLABORATORIO = clab.CLAB_IDLABORATORIO) "
			+ "LEFT OUTER JOIN CFG_EXAMENESTEST cet ON cet.CET_IDTEST = ct.CT_IDTEST ";

	public static String getSqlTestByFiltroTest(FiltroTestDTO ftdto) {

		logger.debug("Filtor test {}", ftdto.toString());

		StringBuilder sb = new StringBuilder("");

		if (ftdto.getLabId() != null) {
			sb.append(" csec.CSEC_IDLABORATORIO = :labId AND ");
		}
		if (ftdto.getSeccion() != null) {
			sb.append(" ct.CT_IDSECCION = :seccionId AND ");
		}
		if (ftdto.getTestId() != null) {
			sb.append(" ct.CT_IDTEST = :testId AND ");
		}

		String rpta = sb.toString();
		rpta = " WHERE ct.CT_IDTIPORESULTADO = ".concat(Constante.TIPORESULTADO_FORMULA.toString()).concat("  AND ")
				.concat(rpta);
		int pos = rpta.lastIndexOf("AND");
		if (pos != -1) {
			rpta = rpta.substring(0, pos);
		}
		return sqlGetTestByFiltroTest.concat(rpta);
	}

	public static String getSqlTestByFiltroTest2(FiltroTestDTO ftdto) {

		logger.debug("Filtor test {}", ftdto.toString());

		StringBuilder sb = new StringBuilder("");
		/*
		 * if (ftdto.getLabId() != null) {
		 * sb.append(" csec.CSEC_IDLABORATORIO = :labId AND "); } if (ftdto.getSeccion()
		 * != null) { sb.append(" ct.CT_IDSECCION = :seccionId AND "); }
		 */
		if (ftdto.getExamenId() != null) {
			sb.append(" cet.CET_IDEXAMEN = :examenId AND ");
		}
		if (ftdto.getTestId() != null) {
			sb.append(" cet.CET_IDTEST = :testId AND ");
		}

		String rpta = sb.toString();
		rpta = " WHERE ct.CT_IDTIPORESULTADO = ".concat(Constante.TIPORESULTADO_FORMULA.toString()).concat("  AND ")
				.concat(rpta);
		int pos = rpta.lastIndexOf("AND");
		if (pos != -1) {
			rpta = rpta.substring(0, pos);
		}
		return sqlGetTestByFiltroTest2.concat(rpta);
	}

	private static final String _sqlOrdenesConSeccionesCapturaResultados = ""
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

	public static String get_sqlOrdenesConSeccionesCapturaResultadosEstricto(BCFichaFiltroDTO ffDto) {

		String sCond = "";
		if (ffDto.getBo_nOrdenIni() != null && ffDto.getBo_nOrdenFin() != null) {
			sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
			sCond += " df.DF_NORDEN <= :nOrdenFin AND ";
		} else {
			if (ffDto.getBo_nOrdenIni() != null || ffDto.getBo_nOrdenFin() != null) {
				if (ffDto.getBo_nOrdenIni() != null) {
					sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
					sCond += " df.DF_NORDEN <= :nOrdenIni AND ";
				}
				if (ffDto.getBo_nOrdenFin() != null) {
					sCond += " df.DF_NORDEN >= :nOrdenFin AND ";
					sCond += " df.DF_NORDEN <= :nOrdenFin AND ";
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

		if (ffDto.getBo_apellido() != null) {
			sCond = sCond + " ( UPPER(dp.DP_PRIMERAPELLIDO) like :apellido "
					+ "or  UPPER(dp.DP_SEGUNDOAPELLIDO) like :apellido) AND ";
		}

		if (ffDto.getBo_nombre() != null && !ffDto.getBo_nombre().trim().isEmpty()) {
			sCond = sCond + " UPPER(dp.DP_NOMBRES)  like :nombre AND ";
		}
		if (ffDto.getBo_tipoDocumento() != null) {
			sCond = sCond + " dp.DP_IDTIPODOCUMENTO  = :idTipoDocumento AND ";
		}

		if (ffDto.getBo_localizacion() != null) {
			sCond = sCond + " df.DF_IDLOCALIZACION = :idLocalizacion AND ";
		}

		int pos = sCond.lastIndexOf("AND");

		if (pos != -1) {
			sCond = sCond.substring(0, pos);

		}

		if (sCond != "") {
			sCond = " where " + sCond;
		}

		return sCond;
	}

	private static final String _sqlOrdenesConSeccionesCapturaResultadosOLD = ""
			+ "SELECT  TO_CHAR(DF_FECHAORDEN,'HH24:MI DD/MM/YYYY') SDF_FECHAORDEN, DF_NORDEN, dp.DP_NOMBRES ,"
			+ "concat(round((TRUNC(SYSDATE) - dp.DP_FNACIMIENTO) / 365),' años' )  as SDP_FNACIMIENTO , ls.LDVS_DESCRIPCION  ,dp.DP_OBSERVACION, "
			+ "lt.LDVTD_DESCRIPCION,dp.DP_NRODOCUMENTO,ct.CTA_DESCRIPCION,dfe.DFE_IDEXAMEN, "
			+ "dp.DP_EMAIL, dp.DP_IDPACIENTE,dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO,  cl.cl_codigolocalizacion as Localizacion , "
			+ "df.DF_OBSERVACION, cm.CM_NOMBRES,cm.CM_PRIMERAPELLIDO, cm.CM_SEGUNDOAPELLIDO, ce.CE_IDSECCION ,"
			+ "dfe.DFE_CODIGOESTADOEXAMEN,cee.CEE_DESCRIPCIONESTADOEXAMEN, dfe.DFE_EXAMENESURGENTE,ce.CE_CODIGOEXAMEN, ce.CE_ABREVIADO, "
			+ "csec.CSEC_DESCRIPCION, cp.CP_DESCRIPCION , cs.CS_DESCRIPCION, cc.CC_ABREVIADO ,cd.CD_DESCRIPCION"
			+ "FROM DATOS_FICHAS df " + "JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE)    "
			+ "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
			+ "JOIN LDV_TIPOSDOCUMENTOS lt ON (lt.LDVTD_IDTIPODOCUMENTO = dp.DP_IDTIPODOCUMENTO) "
			+ "JOIN CFG_TIPOATENCION ct ON (ct.CTA_IDTIPOATENCION = df.DF_IDTIPOATENCION)    "
			+ "JOIN CFG_LOCALIZACIONES cl ON (cl.CL_IDLOCALIZACION = df.DF_IDLOCALIZACION ) "
			+ "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
			+ "JOIN LDV_SEXO ls ON (ls.LDVS_IDSEXO = dp.DP_IDSEXO) "
			+ "JOIN CFG_ESTADOSEXAMENES cee ON (dfe.DFE_CODIGOESTADOEXAMEN = cee.CEE_CODIGOESTADOEXAMEN) "
			+ "JOIN CFG_SECCIONES csec ON (csec.CSEC_IDSECCION = ce.CE_IDSECCION )";

	private static final String _sqlJoinProcedencias = "JOIN CFG_PROCEDENCIAS cp ON (cp.CP_IDPROCEDENCIA = df.DF_IDPROCEDENCIA )     ";
	private static final String _sqlJoinDiagnostico = "JOIN CFG_DIAGNOSTICOS cd ON (cd.CD_IDDIAGNOSTICO = df.DF_IDDIAGNOSTICO) ";
	private static final String _sqlJoinSecciones = "JOIN CFG_SECCIONES csec ON (csec.CSEC_IDSECCION = ce.CE_IDSECCION ) ";
	private static final String _sqlJoinConvenio = "JOIN CFG_CONVENIOS cc ON (cc.CC_IDCONVENIO = df.DF_IDCONVENIO) ";
	private static final String _sqlJoinMedico = "JOIN CFG_MEDICOS cm  ON (cm.CM_IDMEDICO = df.DF_IDMEDICO) ";
	private static final String _sqlJoinServicios = "LEFT OUTER JOIN  CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.df_IDSERVICIO ) ";

	public static String genSqlOrdenesConSecciones_Urgentes(BCFichaFiltroDTO ffDto) {

		logger.debug(ffDto);
		String sCond = "";
		if (ffDto.getBo_nOrdenIni() != null && ffDto.getBo_nOrdenFin() != null) {
			sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
			sCond += " df.DF_NORDEN <= :nOrdenFin AND ";
		} else {
			if (ffDto.getBo_nOrdenIni() != null || ffDto.getBo_nOrdenFin() != null) {
				if (ffDto.getBo_nOrdenIni() != null) {
					sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
					sCond += " df.DF_NORDEN <= :nOrdenIni AND ";
				}
				if (ffDto.getBo_nOrdenFin() != null) {
					sCond += " df.DF_NORDEN >= :nOrdenFin AND ";
					sCond += " df.DF_NORDEN <= :nOrdenFin AND ";
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
		if (ffDto.getBo_procedencia() != null) {
			if (ffDto.getBo_procedencia() != -1 && ffDto.getBo_procedencia() != 0) {
				sCond += " df.DF_IDPROCEDENCIA = :idProcedencia AND ";
			}
		}
		if (ffDto.getBo_tipoAtencion() != null) {
			if (ffDto.getBo_tipoAtencion() != -1 && ffDto.getBo_tipoAtencion() != 0) {
				sCond += " df.DF_IDTIPOATENCION = :idTipoAtencion AND ";
			}
		}
		if (ffDto.getBo_examen() != null) {
			if (ffDto.getBo_examen() != -1 && ffDto.getBo_examen() != 0) {
				sCond += " dfe.DFE_IDEXAMEN = :idExamen AND ";
			}
		}
		if (ffDto.getBo_seccion() != null) {
			if (ffDto.getBo_seccion() != -1 && ffDto.getBo_seccion() != 0) {
				sCond += " ce.CE_IDSECCION = :idSeccion AND ";
			}
		}
		if (ffDto.getBo_servicio() != null) {
			if (ffDto.getBo_servicio() != -1 && ffDto.getBo_servicio() != 0) {
				sCond += " cs.CS_IDSERVICIO = :idServicio AND ";
			}
		}
		if (ffDto.getBo_nombre() != null) {
			if (!ffDto.getBo_nombre().isEmpty()) {
				sCond += " dp.DP_NOMBRES like :nombres AND ";
			}
		}
		if (ffDto.getBo_apellido() != null) {
			if (!ffDto.getBo_apellido().isEmpty()) {
				sCond += " dp.DP_PRIMERAPELLIDO like :apellido AND ";
			}
		}
		if (ffDto.getBo_tipoDocumento() != null) {
			if (ffDto.getBo_tipoDocumento() != -1 && ffDto.getBo_tipoDocumento() != 0) {
				sCond += " dp.DP_IDTIPODOCUMENTO = :idDocumento AND ";
			}
		}
		if (ffDto.getBo_nroDocumento() != null) {
			if (!ffDto.getBo_nroDocumento().isEmpty()) {
				sCond += " dp.DP_NRODOCUMENTO = :nDocumento AND ";
			}
		}
		

		int pos = sCond.lastIndexOf("AND");

		if (pos != -1) {
			sCond = sCond.substring(0, pos);
		}

		if (sCond != "") {
			sCond = " where " + sCond;
		}

		return _sqlOrdenesMinimoConSeccionesCapturaResultados + sCond
				+ " ORDER BY dfe.DFE_EXAMENESURGENTE , df.DF_FECHAORDEN DESC  ";

	}

  private static final String _sqlOrdenesMinimoConSeccionesCapturaResultados = ""
      + " SELECT  TO_CHAR(DF_FECHAORDEN,'HH24:MI DD/MM/YYYY') SDF_FECHAORDEN, " + " DF_NORDEN , " + " dp.DP_NOMBRES ,  "
      + " dp.DP_IDPACIENTE," + " dp.DP_PRIMERAPELLIDO, " + " dp.DP_SEGUNDOAPELLIDO,"
      + " concat(round((TRUNC(SYSDATE) - dp.DP_FNACIMIENTO) / 365),' años' )  as SDP_FNACIMIENTO, dp.DP_IDSEXO,"
      + " dfe.DFE_CODIGOESTADOEXAMEN," + " dfe.DFE_EXAMENESURGENTE,"
      + " dfe.DFE_IDEXAMEN,  ce.CE_IDSECCION, df.DF_IDTIPOATENCION ,df.DF_IDPROCEDENCIA, "
      + " ("
      + " SELECT"
      + " CAST(MAX(CASE WHEN ct.CT_TIENEANTECEDENTES = 'S' THEN 'S' ELSE 'N' END)AS VARCHAR(1) )"
      + " FROM datos_fichasexamenestest dfet"
      + " INNER JOIN CFG_TEST ct ON dfet.DFET_IDTEST = ct.CT_IDTEST"
      + " WHERE dfet.DFET_NORDEN = df.df_norden"
      + " GROUP BY DFET_NORDEN) AS ESTADOANTECEDENTE,"
      + " cs.CS_IDSERVICIO CS_IDSERVICIO,"
      + " cs.CS_DESCRIPCION  CS_DESCRIPCION,"
      + " TO_CHAR(dp.DP_FNACIMIENTO,'DD/MM/YYYY') DP_FECHANACIMIENTO"
      + " FROM  DATOS_FICHAS df       " + " JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE)    "
      + " JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
      + " JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) AND ce.CE_ESCULTIVO = 'N' LEFT JOIN CFG_SERVICIOS cs ON df.DF_IDSERVICIO = cs.CS_IDSERVICIO ";

}
