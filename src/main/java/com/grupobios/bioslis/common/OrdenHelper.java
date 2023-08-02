package com.grupobios.bioslis.common;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.OrdenExamenCapturaResultadoDTO;

public class OrdenHelper {

  private static Logger logger = LogManager.getLogger(OrdenHelper.class);

  public OrdenHelper() {
  }

  public String genSql(BCFichaFiltroDTO bcFichaFiltroDTO) throws BiosLISException {

    String sql = "";
    String sCond = "";

    this.normalize(bcFichaFiltroDTO);

    if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().equals("")) {
      sCond += " ( UPPER(dp.DP_PRIMERAPELLIDO) like UPPER(:apellido) OR "
          + " UPPER(dp.DP_SEGUNDOAPELLIDO) like UPPER(:apellido)) AND ";
    }
    if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
      sCond += " UPPER(dp.DP_NOMBRES) like UPPER(:nombre) AND ";
    }
    if (bcFichaFiltroDTO.getBo_fFin() != null && !bcFichaFiltroDTO.getBo_fFin().trim().isEmpty()) {
      sCond += " df.DF_FECHAORDEN <= TO_DATE(:ffin,'DD-MM-YYYY HH24:MI:SS') AND ";
    }
    if (bcFichaFiltroDTO.getBo_fIni() != null && !bcFichaFiltroDTO.getBo_fIni().trim().isEmpty()) {

      sCond += " df.DF_FECHAORDEN >= TO_DATE(:fini,'DD-MM-YYYY HH24:MI:SS') AND ";
    }
    if (bcFichaFiltroDTO.getBo_nOrdenIni() != null) {
      sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
    }
    if (bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
      sCond += " df.DF_NORDEN <= :nOrdenFin AND ";
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

    if (bcFichaFiltroDTO.getBo_procedencia() != null && bcFichaFiltroDTO.getBo_procedencia() != -1) {
      sCond += " df.DF_IDPROCEDENCIA = :idProcedencia AND ";
    }

    if (bcFichaFiltroDTO.getBo_servicio() != null && bcFichaFiltroDTO.getBo_servicio() != -1) {
      sCond += " df.DF_IDSERVICIO = :idServicio AND ";
    }
    if (bcFichaFiltroDTO.getBo_seccion() != null && bcFichaFiltroDTO.getBo_seccion() != -1) {
      sCond += " df.DF_IDLOCALIZACION = :idSeccion AND ";
    }
    if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1) {
      sCond += " df.DF_IDEXAMEN= :idExamen AND ";
    }

    int pos = sCond.lastIndexOf("AND");

    if (pos != -1) {
      sCond = sCond.substring(0, pos);
      sCond = " WHERE " + sCond;
    }

    logger.debug("Condiciones consulta {}", sCond);

//    logger.debug("Condiciones recepcion {}", sCondRecepcionMuestra);
//    logger.debug("\n SQL {}", _sqlOrdenesConSeccionesCapturaResultados + sCond + sCondRecepcionMuestra
//        + "ORDER BY dfe.DFE_EXAMENESURGENTE DESC");
//
    List<OrdenExamenCapturaResultadoDTO> lst = null;
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Query qry = sesion.createSQLQuery(_sqlOrdenesConSeccionesCapturaResultados + sCond)
        .setResultTransformer(Transformers.aliasToBean(OrdenExamenCapturaResultadoDTO.class));

    if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().isEmpty()) {
      qry.setParameter("apellido", bcFichaFiltroDTO.getBo_apellido().concat("%"));
    }
    if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
      qry.setParameter("nombre", bcFichaFiltroDTO.getBo_nombre().concat("%"));
    }
    if (bcFichaFiltroDTO.getBo_fFin() != null && !bcFichaFiltroDTO.getBo_fFin().trim().isEmpty()) {
      qry.setParameter("ffin", bcFichaFiltroDTO.getBo_fFin());
    }
    if (bcFichaFiltroDTO.getBo_fIni() != null && !bcFichaFiltroDTO.getBo_fIni().trim().isEmpty()) {
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
    if (bcFichaFiltroDTO.getBo_nroDocumento() != null && !bcFichaFiltroDTO.getBo_nroDocumento().trim().isEmpty()) {
      qry.setParameter("nDoc", bcFichaFiltroDTO.getBo_nroDocumento());
    }
    if (bcFichaFiltroDTO.getBo_tipoAtencion() != null && bcFichaFiltroDTO.getBo_tipoAtencion() != -1) {
      qry.setParameter("tipoAtencion", bcFichaFiltroDTO.getBo_tipoAtencion());
    }
    if (bcFichaFiltroDTO.getBo_localizacion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1) {
      qry.setParameter("idLocalizacion", bcFichaFiltroDTO.getBo_localizacion());
    }

    lst = (List<OrdenExamenCapturaResultadoDTO>) qry.list();

//    for (OrdenExamenCapturaResultadoDTO oecr : lst) {
//      Edad edadActual = new Edad();
//      oecr.setSDP_FNACIMIENTO(Edad.getEdadActual(oecr.getSDP_FNACIMIENTO()).toString());
//    }
//
//    return lst;

    return sql + sCond;

  }

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

    if (bcFichaFiltroDTO.getBo_fFin() == null || bcFichaFiltroDTO.getBo_fFin().trim().equals("")) {
      bcFichaFiltroDTO.setBo_fFin(null);
    } else {
      bcFichaFiltroDTO.setBo_fFin(BiosLisCalendarService.getInstance().estandariza(bcFichaFiltroDTO.getBo_fFin()));
    }
    if (bcFichaFiltroDTO.getBo_fIni() == null || bcFichaFiltroDTO.getBo_fIni().trim().equals("")) {
      bcFichaFiltroDTO.setBo_fIni(null);
    } else {
      bcFichaFiltroDTO.setBo_fIni(BiosLisCalendarService.getInstance().estandariza(bcFichaFiltroDTO.getBo_fIni()));
    }

  }

  private static final String _sqlOrdenesConSeccionesCapturaResultados = ""
      + "SELECT  TO_CHAR(DF_FECHAORDEN,'DD-MM HH24:MI') SDF_FECHAORDEN, DF_NORDEN, dp.DP_NOMBRES ,"
      + "TO_CHAR(dp.DP_FNACIMIENTO,'DD-MM-YYYY ') SDP_FNACIMIENTO , ls.LDVS_DESCRIPCION  ,dp.DP_OBSERVACION, cc.CC_ABREVIADO, "
      + "lt.LDVTD_DESCRIPCION,dp.DP_NRODOCUMENTO,ct.CTA_DESCRIPCION, cp.CP_DESCRIPCION , cs.CS_DESCRIPCION ,"
      + "dp.DP_EMAIL, dp.DP_IDPACIENTE,dp.DP_PRIMERAPELLIDO, dp.DP_SEGUNDOAPELLIDO,"
      + "cd.CD_DESCRIPCION, df.DF_OBSERVACION, cm.CM_NOMBRES,cm.CM_PRIMERAPELLIDO, cm.CM_SEGUNDOAPELLIDO, ce.CE_IDSECCION ,"
      + "dfe.DFE_CODIGOESTADOEXAMEN,cee.CEE_DESCRIPCIONESTADOEXAMEN, dfe.DFE_EXAMENESURGENTE "
      + "FROM  DATOS_FICHAS df       " + "JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE)     "
      + "JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_NORDEN = df.DF_NORDEN) "
      + "JOIN LDV_TIPOSDOCUMENTOS lt ON (lt.LDVTD_IDTIPODOCUMENTO = dp.DP_IDTIPODOCUMENTO) "
      + "JOIN CFG_TIPOATENCION ct ON (ct.CTA_IDTIPOATENCION = df.DF_IDTIPOATENCION)    "
      + "LEFT OUTER JOIN CFG_PROCEDENCIAS cp ON (cp.CP_IDPROCEDENCIA = df.DF_IDPROCEDENCIA )     "
      + "LEFT OUTER JOIN  CFG_SERVICIOS cs ON (cs.CS_IDSERVICIO = df.DF_IDSERVICIO ) "
      + "JOIN CFG_EXAMENES ce ON (ce.CE_IDEXAMEN = dfe.DFE_IDEXAMEN) "
      + "JOIN LDV_SEXO ls ON (ls.LDVS_IDSEXO = dp.DP_IDSEXO) "
      + "JOIN CFG_ESTADOSEXAMENES cee ON (dfe.DFE_CODIGOESTADOEXAMEN = cee.CEE_CODIGOESTADOEXAMEN) "

      + "LEFT OUTER JOIN CFG_DIAGNOSTICOS cd ON (cd.CD_IDDIAGNOSTICO = df.DF_IDDIAGNOSTICO) "
      + "LEFT OUTER JOIN CFG_CONVENIOS cc ON (cc.CC_IDCONVENIO = df.DF_IDCONVENIO) "
      + "LEFT OUTER JOIN CFG_MEDICOS cm  ON (cm.CM_IDMEDICO = df.DF_IDMEDICO) ";

}
