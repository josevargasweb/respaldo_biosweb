
package com.grupobios.bioslis.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.AntecedentePacienteDTO;
import com.grupobios.bioslis.dto.DatosEditarOrdenDTO;
import com.grupobios.bioslis.dto.DatosPacienteDTO;
import com.grupobios.bioslis.dto.DatosPacienteOrdenDTO;
import com.grupobios.bioslis.dto.DeltaCheckAptoDTO;
import com.grupobios.bioslis.dto.DeltaCheckDTO;
import com.grupobios.bioslis.dto.ExamenEditarOrdenDTO;
import com.grupobios.bioslis.dto.ExamenValidoDTO;
import com.grupobios.bioslis.dto.LogPacientesDTO;
import com.grupobios.bioslis.dto.OrdenExamenValidoDTO;
import com.grupobios.bioslis.dto.PacientePatologiaEditarOrdenDTO;
import com.grupobios.bioslis.dto.PatologiaDTO;
import com.grupobios.bioslis.dto.PortalDatosPacienteOrdenExamenDTO;
import com.grupobios.bioslis.dto.PortalExamenesDTO;
import com.grupobios.bioslis.dto.TestEdicionOrdenDTO;
import com.grupobios.bioslis.entity.DatosContactos;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosPacientespatologias;
import com.grupobios.bioslis.microbiologia.dao.BiosLisDAO;

public class DatosPacientesDAO implements BiosLisDAO {
  private static Logger logger = LogManager.getLogger(DatosPacientesDAO.class);

  private DatosContactosDAO dcDAO;
  private DatosPacientespatologiasDAO dppDAO;
  private CapturaResultadosDAO capturaResultadosDAO;

  public CapturaResultadosDAO getCapturaResultadosDAO() {
    return capturaResultadosDAO;
  }

  public void setCapturaResultadosDAO(CapturaResultadosDAO capturaResultadosDAO) {
    this.capturaResultadosDAO = capturaResultadosDAO;
  }

// ACA VAN LOS CRUD (select, insert, update, delete )
  public void insertDatosPacientes(DatosPacientes dp) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      dp.setDpFechacreacion(BiosLisCalendarService.getInstance().getTS());
      sesion.beginTransaction();
      sesion.save(dp);
      sesion.getTransaction().commit();
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en insertar datos pacientes");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void insertDatosPacientesRN(DatosPacientes dp, String rutMadre) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      sesion.beginTransaction();

      Integer idMadre = this.getIdByRut(rutMadre);
      if (idMadre == null || idMadre.equals(-1)) {
        throw new BiosLISDAOException("Error en insertar datos pacientes");
      }
      dp.setDpIdmadre(idMadre);
      dp.setDpNrodocumento("RN" + dp.getDpNombresB() + BiosLisCalendarService.getInstance().getTS());
      dp.setDpReciennacido("S");

      sesion.save(dp);
      sesion.getTransaction().commit();
      sesion.close();

    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en insertar datos pacientes");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public void insertDatosPacientesNN(DatosPacientes dp) throws BiosLISDAOException {
    Session sesion = null;
    try {
      sesion = HibernateUtil.getSessionFactory().openSession();
      sesion.beginTransaction();
      dp.setDpNrodocumento("NN" + dp.getDpNombresB() + BiosLisCalendarService.getInstance().getTS());
      dp.setDpFnacimiento(null);
      sesion.save(dp);
      sesion.getTransaction().commit();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en insertar datos pacientes NN");
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
  }

  public DatosPacientes getPacienteByRut(String rutPaciente) throws BiosLISDAOException, BiosLISDAONotFoundException {

    return this.getPacienteByNroTipoDocumento(rutPaciente, Constante.LDVTD_RUN);
  }

  public DatosPacientes getPacienteByPasaporte(String pasaporte)
      throws BiosLISDAOException, BiosLISDAONotFoundException {

    return this.getPacienteByNroTipoDocumento(pasaporte, Constante.LDVTD_PASAPORTE);
  }

  public DatosPacientes getPacienteByNroDocumento(String nroDocumento) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    DatosPacientes paciente = null;
    try {
      Query query = sesion.createQuery("select dp " + "from com.grupobios.bioslis.entity.DatosPacientes dp "
          + "where dp.dpNrodocumento = :nroDocumento");
      query.setParameter("nroDocumento", nroDocumento);
      paciente = (DatosPacientes) query.uniqueResult();
      if (paciente == null) {
        paciente = new DatosPacientes();
        paciente.setDpNrodocumento("0");
      }
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en la busqueda PacienteID");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return paciente;
  }

  public List<DatosPacientes> getPacienteByNombreApellido(String nombre, String primerApellido, String segundoApellido)
      throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<DatosPacientes> listaPacientes = null;
    try {
      logger.debug("Parametros  {} {} {} ", nombre, primerApellido, segundoApellido);
      Query query = null;
      String sQry = "select dp from DatosPacientes dp where ";
      if (!nombre.trim().equals("")) {
        sQry += " dp.dpNombresB like :nombre and ";
      }
      if (!primerApellido.trim().equals("")) {
        sQry += " dp.dpPrimerapellidoB like :primerApellido and ";
      }
      if (!segundoApellido.trim().equals("")) {
        sQry += " dp.dpSegundoapellidoB like :segundoApellido and ";
      }

      int n = sQry.lastIndexOf("and");

      sQry = sQry.substring(0, n - 1);

      sQry = sQry.concat(" order by dp.dpNombres, dp.dpPrimerapellido ");
      query = sesion.createQuery(sQry);

      if (!nombre.trim().equals("")) {
        query.setParameter("nombre", "%" + nombre + "%");
      }
      if (!primerApellido.trim().equals("")) {
        query.setParameter("primerApellido", "%" + primerApellido + "%");
      }
      if (!segundoApellido.trim().equals("")) {
        query.setParameter("segundoApellido", "%" + segundoApellido + "%");
      }
      listaPacientes = query.list();
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en la busqueda getPacienteByNombreApellido");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaPacientes;
  }

  public List<DatosPacientes> getPacienteByNombreApellidoTipoDoc(String nombre, String primerApellido,
      String segundoApellido, int tipoDocumento) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<DatosPacientes> listaPacientes = null;
    try {

      if (tipoDocumento == Constante.LDVTD_SINTIPO) {

        return this.getPacienteByNombreApellido(nombre, primerApellido, segundoApellido);
      }
      logger.debug("Parametros  {} {} {} {} ", nombre, primerApellido, segundoApellido, tipoDocumento);
      Query query = null;

      String sQry = "select dp " + "from DatosPacientes dp where dp.ldvTiposdocumentos = :tipoDocumento";
      if (nombre != null && !nombre.equals("")) {
        sQry += " and dp.dpNombresB like :nombre ";
      }
      if (primerApellido != null && !primerApellido.equals("")) {
        sQry += " and dp.dpPrimerapellidoB like :primerApellido";
      }
      if (segundoApellido != null && !segundoApellido.equals("")) {
        sQry += " and dp.dpSegundoapellidoB like :segundoApellido";
      }

      sQry = sQry.concat(" order by dp.dpNombres, dp.dpPrimerapellido");
      query = sesion.createQuery(sQry);
      query.setParameter("tipoDocumento", tipoDocumento);

      if (nombre != null && !nombre.equals("")) {
        query.setParameter("nombre", "%" + nombre + "%");
      }
      if (primerApellido != null && !primerApellido.equals("")) {
        query.setParameter("primerApellido", "%" + primerApellido + "%");
      }
      if (segundoApellido != null && !segundoApellido.equals("")) {
        query.setParameter("segundoApellido", "%" + segundoApellido + "%");
      }
      listaPacientes = query.list();
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en la busqueda getPacienteByNombreApellido");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaPacientes;
  }

  public Integer getIdByRut(String rutPaciente) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Integer indice = -1;
    try {
      indice = -1;
      Query query = sesion.createQuery("select dp.dpIdpaciente from com.grupobios.bioslis.entity.DatosPacientes dp "
          + "where dp.dpNrodocumento = :rutPaciente " + "order by dp.dpIdpaciente desc").setMaxResults(1);
      query.setParameter("rutPaciente", rutPaciente);
      List<DatosPacientes> listaPaciente = query.list();
      if (!listaPaciente.isEmpty()) {
        indice = ((Integer) query.list().get(0));
      }
      sesion.close();

    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en la busqueda PacienteID");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return indice;
  }

  public DatosPacientes getByRut(String rutPaciente) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    DatosPacientes dp = null;
    try {

      Query query = sesion.createQuery("select dp from com.grupobios.bioslis.entity.DatosPacientes dp "
          + "where dp.dpNrodocumento = :rutPaciente " + "order by dp.dpIdpaciente desc").setMaxResults(1);
      query.setParameter("rutPaciente", rutPaciente);
      List<DatosPacientes> listaPaciente = query.list();
      if (!listaPaciente.isEmpty()) {
        dp = ((DatosPacientes) query.list().get(0));
      }
      sesion.close();
      logger.debug("Resultado busqueda por rut {} -> {}", rutPaciente, dp);

    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en la busqueda PacienteID");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return dp;
  }

  public List<DatosPacientes> getPacienteByIdMadre(int madre) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<DatosPacientes> listaPaciente = null;
    try {

      Query query = sesion.createQuery("select dp " + "from DatosPacientes dp "
          + "where dp.dpIdmadre = :idMadre order by dp.dpFnacimiento,dp.dpRnnumero");
      query.setParameter("idMadre", madre);
      listaPaciente = query.list();
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en la busqueda getPacienteByIdMadre");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaPaciente;
  }

  public DatosPacientes getPacienteById(int idPaciente)
      throws ParseException, BiosLISDAOException, BiosLISDAONotFoundException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    DatosPacientes paciente = null;
    try {
      Query query = sesion.createQuery("select dp " + "from DatosPacientes dp " + "where dp.dpIdpaciente = :idPaciente "
          + "order by dp.dpIdpaciente desc");
      query.setParameter("idPaciente", idPaciente);
      paciente = (DatosPacientes) query.uniqueResult();
      if (paciente == null) {
        throw new BiosLISDAONotFoundException("No hay paciente");
      }

    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en la busqueda PacienteID");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return paciente;
  }

  public List<DatosPacientes> getPacienteRnnumero(int madre, int rnnumero)
      throws ParseException, HibernateException, BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<DatosPacientes> listaPacientes = null;
    try {
      Query query = sesion.createQuery("select dp " + "from com.grupobios.bioslis.entity.DatosPacientes dp "
          + "where dp.dpIdmadre = :madre " + "and dp.dpRnnumero = :rnnumero");
      query.setParameter("madre", madre);
      query.setParameter("rnnumero", rnnumero);
      listaPacientes = query.list();
      sesion.close();

    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en la busqueda getPacienteRnnumero");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return listaPacientes;
  }

  public DatosPacientes updateDatosPacientes(DatosPacientes dp, String ip)
      throws BiosLISDAOException, ParseException, BiosLISDAONotFoundException {

    DatosPacientes updatePaciente = this.getPacienteById(dp.getDpIdpaciente());
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      // updatePaciente = this.getPacienteById(dp.getDpIdpaciente());
      logger.debug("tiporel antes {}", updatePaciente.getLdvTiposdocumentos());
      updatePaciente.copyChanges(dp);
      logger.debug("tipodoc despues {}", updatePaciente.getLdvTiposdocumentos());
      sesion.beginTransaction();
      sesion.update(updatePaciente);
      sesion.getTransaction().commit();
      sesion.close();

    } catch (HibernateException e) {
      logger.error(e.getMessage());
      logger.error(e.getLocalizedMessage());

      throw new BiosLISDAOException("Error en la busqueda updateDatosPacientes");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return updatePaciente;

  }

  public DatosPacientes nullToSpace(DatosPacientes dp) {
    if (dp.getDpExitus() == null) {
      dp.setDpExitus("");
    }
    if (dp.getDpObservacion() == null) {
      dp.setDpObservacion("");
    }
    if (dp.getDpRnpartomultiple() == null) {
      dp.setDpRnpartomultiple("");
    }
    if (dp.getDpSegundoapellido() == null) {
      dp.setDpSegundoapellido("");
    }
    if (dp.getDpSegundoapellidoB() == null) {
      dp.setDpSegundoapellidoB("");
    }
    if (dp.getDpNombresocial() == null) {
      dp.setDpNombresocial("");
    }
    if (dp.getDpRun() == null) {
      dp.setDpRun("");
    }
    if (dp.getDpDireccion() == null) {
      dp.setDpDireccion("");
    }
    if (dp.getDpDireccodpostal() == null) {
      dp.setDpDireccodpostal("");
    }
    if (dp.getDpTelefono() == null) {
      dp.setDpTelefono("");
    }
    if (dp.getDpEmail() == null) {
      dp.setDpEmail("");
    }
    if (dp.getDpEmail() == null) {
      dp.setDpEmail("");
    }
    if (dp.getDpExitus() == null) {
      dp.setDpExitus("");
    }
    if (dp.getDpAbo() == null) {
      dp.setDpAbo("");
    }
    if (dp.getDpRh() == null) {
      dp.setDpRh("");
    }
    if (dp.getDpReciennacido() == null) {
      dp.setDpReciennacido("");
    }
    if (dp.getDpEsvip() == null) {
      dp.setDpEsvip("");
    }
    if (dp.getDpEsafroamericano() == null) {
      dp.setDpEsafroamericano("");
    }
    if (dp.getDpCampolibre1() == null) {
      dp.setDpCampolibre1("");
    }
    if (dp.getDpCampolibre2() == null) {
      dp.setDpCampolibre2("");
    }
    return dp;
  }

  private static final String sqlPatologiasPaciente = "" +

      "SELECT " + "lp.LDVPAT_IDPATOLOGIA, lp.LDVPAT_DESCRIPCION , dpp.DPP_OBSERVACION "
      + "FROM  DATOS_PACIENTESPATOLOGIAS dpp "
      + "JOIN LDV_PATOLOGIAS lp ON( lp.LDVPAT_IDPATOLOGIA = dpp.DPP_IDPATOLOGIA) "
      + "WHERE dpp.DPP_IDPACIENTE = :idPaciente";

  public List<PatologiaDTO> getPatologiasPaciente(Long idPaciente) throws BiosLISDAOException {
    List<PatologiaDTO> lstPatologias = null;
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query qry = sesion.createSQLQuery(sqlPatologiasPaciente)
          .setResultTransformer(Transformers.aliasToBean(PatologiaDTO.class));
      qry.setLong("idPaciente", idPaciente);
      lstPatologias = ((List<PatologiaDTO>) qry.list());
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return lstPatologias;

  }

  public List<AntecedentePacienteDTO> getAntecedentesPacienteOrden(BigDecimal nOrden) throws BiosLISDAOException {

    List<AntecedentePacienteDTO> lstAntecedentes = null;

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      Query qry = sesion.createSQLQuery(sqlAntecedentesPacienteOrden)
          .setResultTransformer(Transformers.aliasToBean(AntecedentePacienteDTO.class));
      qry.setLong("nOrden", nOrden.longValue());
      lstAntecedentes = ((List<AntecedentePacienteDTO>) qry.list());
      sesion.close();

    } catch (HibernateException hex) {
      logger.error(hex.getMessage());
      throw new BiosLISDAOException("No se pudo recuperar paciente de una orden");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lstAntecedentes;

  }

  public DatosPacienteOrdenDTO getPacienteOrden(BigDecimal nOrden) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    DatosPacienteOrdenDTO paciente = null;
    try {
      Query qry = sesion.createSQLQuery(sqlDatosPacienteOrden)
          .setResultTransformer(Transformers.aliasToBean(DatosPacienteOrdenDTO.class));
      qry.setLong("nOrden", nOrden.longValue());
      paciente = (DatosPacienteOrdenDTO) qry.uniqueResult();
      sesion.close();

    } catch (HibernateException hex) {
      logger.error(hex.getMessage());
      throw new BiosLISDAOException("No se pudo recuperar paciente de una orden");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return paciente;
  }

  private static final String sqlDatosPacienteOrden = ""
      + " SELECT  dp.DP_NOMBRES , dp.DP_PRIMERAPELLIDO , dp.DP_SEGUNDOAPELLIDO  ,"
      + "	 dp.DP_IDSEXO ,dp.DP_FNACIMIENTO, TO_CHAR(dp.DP_FNACIMIENTO,'dd/mm/yyyy HH24:mi:ss') SDP_FNACIMIENTO, "
      + " SUBSTR(ldvs.LDVS_DESCRIPCION,0,1) LDV_INISEXO ,ldvs.LDVS_CODIGO FROM  DATOS_FICHAS df "
      + "	JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE = df.DF_IDPACIENTE) "
      + "	JOIN LDV_SEXO ldvs ON (dp.DP_IDSEXO = ldvs.LDVS_IDSEXO) " + "WHERE df.DF_NORDEN = :nOrden ";

  private static final String sqlAntecedentesPacienteOrden = "" +

      "SELECT dfa.DFA_NORDEN, ca.CA_DESCRIPCION ,dfa.DFA_VALORANTECEDENTE, ca.CA_CODIGOUM ,ca.CA_FORMATOUM,ca.CA_CODIGOANTECEDENTE "
      + "FROM BIOSLIS.DATOS_FICHASANTECEDENTES dfa "
      + "INNER JOIN BIOSLIS.CFG_ANTECEDENTES ca ON (dfa.DFA_IDANTECEDENTE = ca.CA_IDANTECEDENTE) "
      + "WHERE dfa.DFA_NORDEN = :nOrden"

      + " UNION "
      // UNIMOS LA QUERY ANTERIOR CON LA EDAD DEL PACIENTE
      + "SELECT DF.DF_NORDEN DFA_NORDEN, 'EDAD' CA_DESCRIPCION, "
      + "CASE WHEN TRUNC(MONTHS_BETWEEN(SYSDATE, DP_FNACIMIENTO) / 12) > 0 "
      + "THEN TRUNC(MONTHS_BETWEEN(SYSDATE, DP_FNACIMIENTO) / 12) || '' "
      + "ELSE CASE WHEN TRUNC(MOD( MONTHS_BETWEEN(SYSDATE, DP_FNACIMIENTO),12 )) > 0 "
      + "THEN TRUNC(MOD( MONTHS_BETWEEN(SYSDATE, DP_FNACIMIENTO),12 )) || '' "
      + "ELSE TRUNC( SYSDATE - TRUNC(ADD_MONTHS ( DP_FNACIMIENTO, TRUNC( MONTHS_BETWEEN(SYSDATE, DP_FNACIMIENTO) ) ) ) ) || '' "
      + "END END AS DFA_VALORANTECEDENTE," + "CASE WHEN TRUNC(MONTHS_BETWEEN(SYSDATE, DP_FNACIMIENTO) / 12) > 0 "
      + "THEN 'Años' " + "ELSE CASE WHEN TRUNC(MOD( MONTHS_BETWEEN(SYSDATE, DP_FNACIMIENTO),12 )) > 0 "
      + "THEN 'Meses' ELSE 'Días' " + "END END AS CA_CODIGOUM," + "'999' CA_FORMATOUM, 'EDAD' CA_CODIGOANTECEDENTE "
      + "FROM DATOS_PACIENTES DP " + "INNER JOIN DATOS_FICHAS DF ON DP.DP_IDPACIENTE = DF.DF_IDPACIENTE "
      + "WHERE df.df_norden = :nOrden";

  private static final String sqlAntecedentesPaciente = "SELECT DFET.DFET_NORDEN DFA_NORDEN, "
      + "CA.CA_DESCRIPCION, DFA.DFA_VALORANTECEDENTE, CA.CA_CODIGOUM, CA.CA_FORMATOUM, CA.CA_CODIGOANTECEDENTE, CA.CA_IDANTECEDENTE, "
      + "CA.CA_SOLICITARTM, CA.CA_VALIDARESNUMERICO, CA.CA_OBLIGATORIO, CA.CA_ACTIVO " // linea agregada por Marco
                                                                                       // Caracciolo 04/01/2023
      + "FROM CFG_ANTECEDENTESTEST CAT " + "JOIN CFG_ANTECEDENTES CA ON CAT.CAT_IDANTECEDENTE = CA.CA_IDANTECEDENTE "
      + "JOIN DATOS_FICHASEXAMENESTEST DFET ON DFET.DFET_IDTEST = CAT.CAT_IDTEST "
      + "LEFT JOIN DATOS_FICHASANTECEDENTES DFA ON DFA.DFA_IDANTECEDENTE = CA.CA_IDANTECEDENTE AND DFA.DFA_NORDEN = DFET.DFET_NORDEN "
      + "WHERE DFET.DFET_NORDEN = :nOrden";

  private final static String sqlNNPorFecha = "SELECT dp.* \r\n" + "FROM BIOSLIS.DATOS_PACIENTES dp \r\n"
      + "WHERE dp.DP_IDTIPODOCUMENTO = 5 \r\n" + "AND TRUNC(dp.DP_FECHACREACION) = TO_DATE('16-11-2021','dd-MM-yyyy')";

  private final static String hqlNNPorFecha = "SELECT dp FROM DatosPacientes dp WHERE dp.ldvTiposdocumentos = 5 AND trunc(dp.dpFechacreacion) = trunc(:fechaCreacion)";

  public DatosPacienteDTO getRnById(int idPaciente)
      throws ParseException, BiosLISDAOException, BiosLISDAONotFoundException {

    try {
      DatosPacientes dpRn = this.getPacienteById(idPaciente);
      logger.debug(dpRn.getDpFnacimiento());
      if (dpRn.getDpIdmadre() == null) {
        throw new BiosLISDAONotFoundException("Datos madre recien nacido no encontrados");
      }
      DatosPacienteDTO dpPac = new DatosPacienteDTO();
      DatosPacientes dpMadre = this.getPacienteById(dpRn.getDpIdmadre());
      DatosContactos dc = dcDAO.getContactoxPaciente(dpRn);
      List<DatosPacientespatologias> listaPatologias = dppDAO.getPatologiasxPacientesList(dpRn);

      dpPac.setDp(dpRn);
      dpPac.setRutMadre(dpMadre.getDpRun());
      dpPac.setDc(dc);
      dpPac.setListaPatologias(listaPatologias);
      return dpPac;

    } catch (BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAONotFoundException("Datos recien nacido no encontrados");
    }
  }

  public List<DatosPacientes> getByRegDate(Timestamp d) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<DatosPacientes> pacientes = null;
    try {
      Query qry = sesion.createQuery(hqlNNPorFecha);
      qry.setTimestamp("fechaCreacion", d);
      pacientes = (List<DatosPacientes>) qry.list();
      sesion.close();

    } catch (HibernateException hex) {
      logger.error(hex.getMessage());
      throw new BiosLISDAOException("No se pudo recuperar paciente de una orden");
    } finally {

      if (sesion.isOpen()) {
        sesion.close();
      }

    }
    return pacientes;
  }

  public DatosContactosDAO getDcDAO() {
    return dcDAO;
  }

  public void setDcDAO(DatosContactosDAO dcDAO) {
    this.dcDAO = dcDAO;
  }

  public DatosPacientespatologiasDAO getDppDAO() {
    return dppDAO;
  }

  public void setDppDAO(DatosPacientespatologiasDAO dppDAO) {
    this.dppDAO = dppDAO;
  }

  public List<AntecedentePacienteDTO> getAntecedentesPaciente(Long nOrden) throws BiosLISDAOException {

    List<AntecedentePacienteDTO> lstAntecedentes = null;
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      Query qry = sesion.createSQLQuery(sqlAntecedentesPaciente)
          .setResultTransformer(Transformers.aliasToBean(AntecedentePacienteDTO.class));
      qry.setLong("nOrden", nOrden);
      lstAntecedentes = ((List<AntecedentePacienteDTO>) qry.list());
      sesion.close();
      return lstAntecedentes;

    } catch (HibernateException hex) {
      logger.error(hex.getMessage());
      throw new BiosLISDAOException("No se pudo recuperar antecedente de paciente de una orden");
    } finally {

      if (sesion.isOpen()) {
        sesion.close();
      }

    }

  }

  public DatosPacienteDTO getPacienteDTOById(int idPaciente)
      throws ParseException, BiosLISDAOException, BiosLISDAONotFoundException {
    DatosPacientes dpRn = null;
    DatosPacienteDTO dpPac = new DatosPacienteDTO();
    try {

      try {
        dpRn = this.getPacienteById(idPaciente);
      } catch (ParseException | BiosLISDAOException e) {
        logger.error("Error en la recuperacion de paciente", e.getMessage());
        throw e;
      } catch (BiosLISDAONotFoundException e) {
        logger.error("Paciente no encontrado", e.getMessage());
        throw e;
      }
      logger.debug(dpRn.getDpFnacimiento());

      if (dpRn.getDpIdmadre() != null && !dpRn.getDpIdmadre().equals("")) {
        try {
          DatosPacientes dpMadre = this.getPacienteById(dpRn.getDpIdmadre());
          dpPac.setRutMadre(dpMadre.getDpRun());
        } catch (BiosLISDAONotFoundException e) {
          logger.error("Datos madre paciente no encontrado", e.getMessage());
        }
      }
      DatosContactos dc = dcDAO.getContactoxPaciente(dpRn);
      List<DatosPacientespatologias> listaPatologias = dppDAO.getPatologiasxPacientesList(dpRn);
      dpPac.setDp(dpRn);
      dpPac.setDc(dc);
      dpPac.setListaPatologias(listaPatologias);

    } catch (HibernateException e) {
      logger.error(e.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
    }
    return dpPac;
  }

  public DatosPacienteDTO getPacienteDTOByNroDocumento(String nroDocumento)
      throws ParseException, BiosLISDAOException, BiosLISDAONotFoundException {

    DatosPacientes dpRn = null;
    DatosPacienteDTO dpPac = new DatosPacienteDTO();
    try {

      try {
        dpRn = this.getPacienteByNroDocumento(nroDocumento);
      } catch (BiosLISDAOException e) {
        logger.error("Error en la recuperacion de paciente", e.getMessage());
        throw e;
      }
      logger.debug(dpRn.getDpFnacimiento());

      if (dpRn.getDpIdmadre() != null && !dpRn.getDpIdmadre().equals("")) {
        try {
          DatosPacientes dpMadre = this.getPacienteById(dpRn.getDpIdmadre());
          dpPac.setRutMadre(dpMadre.getDpRun());
        } catch (BiosLISDAONotFoundException e) {
          logger.error("Datos madre paciente no encontrado", e.getMessage());
        }
      }
      DatosContactos dc = dcDAO.getContactoxPaciente(dpRn);
      List<DatosPacientespatologias> listaPatologias = dppDAO.getPatologiasxPacientesList(dpRn);
      dpPac.setDp(dpRn);
      dpPac.setDc(dc);
      dpPac.setListaPatologias(listaPatologias);

    } catch (HibernateException e) {
      logger.error(e.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
    }
    return dpPac;
  }

  public DatosPacienteDTO getPacienteDTOByOrden(Long nOrden)
      throws ParseException, BiosLISDAOException, BiosLISDAONotFoundException {
    DatosPacienteDTO dpDto = new DatosPacienteDTO();
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {
      String sql = "SELECT dp.* FROM datos_pacientes dp "
          + "JOIN datos_fichas df on df.df_idpaciente = dp.dp_idpaciente " + "WHERE df.df_norden = :nOrden";
      Query query = sesion.createSQLQuery(sql).addEntity(DatosPacientes.class);
      query.setParameter("nOrden", nOrden);
      DatosPacientes dp = (DatosPacientes) query.uniqueResult();

      List<DatosPacientespatologias> listaPatologias = dppDAO.getPatologiasxPacientesList(dp);
      dpDto.setDp(dp);
      dpDto.setListaPatologias(listaPatologias);
      sesion.close();
    } catch (HibernateException e) {
      logger.error(e.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
    } finally {
      if (sesion != null && sesion.isOpen()) {
        sesion.close();
      }
    }
    return dpDto;
  }

  public void updateNroDocTipo(Integer idPaciente, String prefijo) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    try {

      sesion.beginTransaction();
      DatosPacientes dp = (DatosPacientes) sesion.load(DatosPacientes.class, idPaciente);
      logger.debug("Fec: {}", dp.getDpFechacreacion());
      logger.debug("Fec nac: {}", dp.getDpFnacimiento());
      dp.setDpNrodocumento(prefijo + idPaciente);
      sesion.update(dp);
      sesion.getTransaction().commit();
      sesion.close();
    } catch (HibernateException he) {
      logger.error("{}", he.getMessage());
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }

  }

  public void updateNroDocRN(Integer idPaciente) {
    this.updateNroDocTipo(idPaciente, "RN");
  }

  public void updateNroDocNN(Integer idPaciente) {
    this.updateNroDocTipo(idPaciente, "NN");
  }

  final static private String _hqlGetByFechaCreacionTipoDoc = "select dp from DatosPacientes dp where trunc(dp.dpFechacreacion) = :dpFechacreacion and dp.ldvTiposdocumentos = :ldvTiposdocumentos";

  public List<DatosPacientes> getByFechaCreacionTipoDoc(Timestamp fechaCreacion, Integer tipoDoc)
      throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<DatosPacientes> lstDp = null;
    try {
      Query qry = sesion.createQuery(_hqlGetByFechaCreacionTipoDoc);
      qry.setTimestamp("dpFechacreacion", fechaCreacion);
      qry.setInteger("ldvTiposdocumentos", tipoDoc);
      lstDp = (List<DatosPacientes>) qry.list();

      sesion.close();
    } catch (HibernateException he) {
      logger.error("", he.getLocalizedMessage());

      throw new BiosLISDAOException(he.getMessage());

    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lstDp;

  }

  public List<OrdenExamenValidoDTO> getOrdenesConExamenesValidosDeUnPaciente(Integer idPac) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<OrdenExamenValidoDTO> lista = null;
    try {
      Query query = sesion.createSQLQuery(sqlOrdenesConExamenesValidosDeUnPaciente)
          .setResultTransformer(Transformers.aliasToBean(OrdenExamenValidoDTO.class));
      query.setInteger("idPaciente", idPac);
      lista = query.list();
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo buscar ordenes");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;

  }

  public List<ExamenValidoDTO> getExamenesValidosDeUnPaciente(Integer idPac) throws BiosLISDAOException {

    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<ExamenValidoDTO> lista = null;
    try {
      Query query = sesion.createSQLQuery(sqlExamenesValidosDeUnPaciente)
          .setResultTransformer(Transformers.aliasToBean(ExamenValidoDTO.class));
      query.setInteger("idPaciente", idPac);
      lista = query.list();

    } catch (HibernateException he) {
      logger.error(he.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo buscar ordenes");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;

  }

  public DatosPacientes getPacienteByNroTipoDocumento(String nroDocumento, Integer tipoDoc) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    DatosPacientes paciente = null;
    try {
      Query query = sesion.createQuery("select dp " + "from com.grupobios.bioslis.entity.DatosPacientes dp "
          + "where dp.dpNrodocumento = :nroDocumento and dp.ldvTiposdocumentos = :tipoDoc ");
      query.setParameter("nroDocumento", nroDocumento);
      query.setInteger("tipoDoc", tipoDoc);

      paciente = (DatosPacientes) query.uniqueResult();
      if (paciente == null) {
        paciente = new DatosPacientes();
        paciente.setDpNrodocumento("0");
      }

    } catch (HibernateException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAOException("Error en la busqueda Paciente por tipo y número de documento.");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return paciente;
  }

  private String sqlOrdenesConExamenesValidosDeUnPaciente = "SELECT df.DF_IDPACIENTE, dfe.DFE_NORDEN ,ce.CE_IDEXAMEN , ce.CE_ABREVIADO, ce.CE_DIASDEVALIDEZ, ce.CE_DIASVALIDEZHOSPITALIZADO, ce.CE_DIASVALIDEZAMBULATORIOBLQ, ce.CE_DIASVALIDEZHOSPITALIZADOBLQ "
      + "FROM " + "BIOSLIS.DATOS_FICHASEXAMENES dfe  JOIN BIOSLIS.DATOS_FICHAS df ON (dfe.DFE_NORDEN  = df.DF_NORDEN) "
      + " JOIN BIOSLIS.CFG_EXAMENES ce ON (dfe.DFE_IDEXAMEN = ce.CE_IDEXAMEN)" + " WHERE "
      + "df.DF_IDPACIENTE = :idPaciente " + "AND "
      + "((ce.CE_DIASDEVALIDEZ <> 0 AND trunc(dfe.DFE_FECHACREACION) BETWEEN (TRUNC(SYSDATE) - ce.CE_DIASDEVALIDEZ ) AND TRUNC(SYSDATE))"
      + "OR "
      + "(ce.CE_DIASVALIDEZHOSPITALIZADO  <> 0 AND trunc(dfe.DFE_FECHACREACION) BETWEEN (TRUNC(SYSDATE) - ce.CE_DIASVALIDEZHOSPITALIZADO ) AND TRUNC(SYSDATE))) "
      + "ORDER BY dfe.DFE_NORDEN ,ce.CE_IDEXAMEN " + "";

  private String sqlExamenesValidosDeUnPaciente = "SELECT distinct df.DF_IDPACIENTE, ce.CE_IDEXAMEN , ce.CE_ABREVIADO, ce.CE_DIASDEVALIDEZ, ce.CE_DIASVALIDEZHOSPITALIZADO, ce.CE_DIASVALIDEZAMBULATORIOBLQ, ce.CE_DIASVALIDEZHOSPITALIZADOBLQ "
      + "FROM " + "BIOSLIS.DATOS_FICHASEXAMENES dfe  JOIN BIOSLIS.DATOS_FICHAS df ON (dfe.DFE_NORDEN  = df.DF_NORDEN) "
      + " JOIN BIOSLIS.CFG_EXAMENES ce ON (dfe.DFE_IDEXAMEN = ce.CE_IDEXAMEN)" + " WHERE "
      + "df.DF_IDPACIENTE = :idPaciente " + "AND "
      + "((ce.CE_DIASDEVALIDEZ <> 0 AND trunc(dfe.DFE_FECHACREACION) BETWEEN (TRUNC(SYSDATE) - ce.CE_DIASDEVALIDEZ ) AND TRUNC(SYSDATE))"
      + "OR "
      + "(ce.CE_DIASVALIDEZHOSPITALIZADO  <> 0 AND trunc(dfe.DFE_FECHACREACION) BETWEEN (TRUNC(SYSDATE) - ce.CE_DIASVALIDEZHOSPITALIZADO ) AND TRUNC(SYSDATE))) "
      + "ORDER BY  ce.CE_IDEXAMEN ";

  public Object getDatosPacientesExiste(String rutPac, Long nOrden, Long idDocumento) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Object dato = null;
    Query query;
    try {
      if (idDocumento == 1) {
        query = sesion.createSQLQuery(sqlDatosPacientesExisteRut);
      } else {
        query = sesion.createSQLQuery(sqlDatosPacientesExistePasaporte);
      }
      query.setString("rutPac", rutPac);
      query.setLong("nOrden", nOrden);
      dato = query.uniqueResult();

    } catch (HibernateException he) {
      logger.error(he.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo buscar ordenes");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return dato;
  }

  public List<DeltaCheckDTO> getDeltaCheckTestPaciente(Long idPaciente, Long idTest) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Query qry = null;
    List<DeltaCheckDTO> lst = new ArrayList<DeltaCheckDTO>();
    try {
      qry = sesion.createSQLQuery(sqlGetDeltaCheckTestPaciente)
          .setResultTransformer(Transformers.aliasToBean(DeltaCheckDTO.class));
      qry.setLong("idPaciente", idPaciente);
      qry.setLong("idTest", idTest);

      lst = qry.list();
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      he.printStackTrace();
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lst;
  }

  public List<DeltaCheckAptoDTO> getDeltaCheckTestAptosPaciente(Long idPaciente, Long idTest, Long nOrden)
      throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Query qry = null;
    List<DeltaCheckAptoDTO> lst = null;
    try {
      qry = sesion.createSQLQuery(sqlGetDeltaCheckTestAptos)
          .setResultTransformer(Transformers.aliasToBean(DeltaCheckAptoDTO.class));
      qry.setLong("idPaciente", idPaciente);
      qry.setLong("idTest", idTest);
      qry.setParameter("nOrden", nOrden);
      lst = qry.list();
      sesion.close();
    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lst;
  }

  public DeltaCheckAptoDTO getDeltaCheckCalculadoTestPaciente(Long idPaciente, Long idTest, Long nOrden)
      throws BiosLISDAOException {

    List<DeltaCheckAptoDTO> lstAptos = this.getDeltaCheckTestAptosPaciente(idPaciente, idTest, nOrden);
    DeltaCheckAptoDTO dcDto = new DeltaCheckAptoDTO();
    try {

      if (lstAptos.isEmpty())
        return dcDto; // REvisar este curso

      Integer n = lstAptos.get(0).getCT_DELTACANTIDAD().intValueExact();
      Double deltaPctje = lstAptos.get(0).getCT_DELTAPORCENTAJE().doubleValue();
      Double promedio = 0D;
      Double min = 0D;
      Double max = 0D;

      int contador = (lstAptos.size() < n) ? lstAptos.size() : n;

      for (int i = 0; i < contador; i++) {
        promedio = promedio + lstAptos.get(i).getDFET_RESULTADONUMERICO().doubleValue();

      }

      promedio = promedio / contador;

      min = promedio - ((promedio * deltaPctje) / 100);
      max = promedio + ((promedio * deltaPctje) / 100);

      min = Math.round(min * 100.0) / 100.0;
      max = Math.round(max * 100.0) / 100.0;

      dcDto.setCT_DELTACANTIDAD(new BigDecimal(n));
      dcDto.setCT_DELTAPORCENTAJE(new BigDecimal(deltaPctje));
      dcDto.setPROMEDIO(new BigDecimal(promedio));
      dcDto.setVALORMAX(new BigDecimal(max));
      dcDto.setVALORMIN(new BigDecimal(min));
    } catch (HibernateException e) {
      logger.error(e.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo recuperar información de ordenes" + e.getLocalizedMessage());
    }
    return dcDto;
  }

  private static final String sqlDatosPacientesExisteRut = "select dfic.DF_IDPACIENTE , dpac.DP_NRODOCUMENTO , dfic.DF_NORDEN from DATOS_PACIENTES dpac "
      + " inner join DATOS_FICHAS dfic " + " on dfic.DF_IDPACIENTE = dpac.DP_IDPACIENTE "
      + " where dpac.DP_RUN=:rutPac and dfic.DF_NORDEN =:nOrden";

  private static final String sqlDatosPacientesExistePasaporte = "select dfic.DF_IDPACIENTE , dpac.DP_NRODOCUMENTO , dfic.DF_NORDEN from DATOS_PACIENTES dpac "
      + " inner join DATOS_FICHAS dfic " + " on dfic.DF_IDPACIENTE = dpac.DP_IDPACIENTE "
      + " where dpac.DP_NRODOCUMENTO =:rutPac and dfic.DF_NORDEN =:nOrden";

  private static final String sqlPortalDatosPacienteExamenesRut = "select concat(concat(concat(concat(dpac.dp_nombres ,' ' ) ,dpac.dp_primerapellido), ' ' ), dpac.dp_segundoapellido ) nombrePaciente , "
      + " ccon.cc_descripcion as convenio, dpac.dp_run as nroDocumento, dfic.df_fechaorden as fecha, concat(concat(cme.cm_nombres, ' '), cme.cm_primerapellido ) medico, "
      + "dfic.df_norden as orden from datos_pacientes dpac  inner join datos_fichas dfic  on dfic.df_idpaciente = dpac.dp_idpaciente "
      + "  inner join cfg_convenios ccon on ccon.cc_idconvenio = dfic.df_idconvenio  inner join cfg_medicos cme  on cme.cm_idmedico = dfic.df_idmedico "
      + " where dpac.dp_run =:run  and dfic.df_norden =:nOrden ";

  private static final String sqlPortalDatosPacienteExamenesNroDocumento = "select concat(concat(concat(concat(dpac.dp_nombres ,' ' ) ,dpac.dp_primerapellido), ' ' ), dpac.dp_segundoapellido ) nombrePaciente , "
      + " ccon.cc_descripcion as convenio, dpac.dp_nrodocumento as nroDocumento, dfic.df_fechaorden as fecha, concat(concat(cme.cm_nombres, ' '), cme.cm_primerapellido ) medico, "
      + "dfic.df_norden as orden from datos_pacientes dpac  inner join datos_fichas dfic  on dfic.df_idpaciente = dpac.dp_idpaciente "
      + "  inner join cfg_convenios ccon on ccon.cc_idconvenio = dfic.df_idconvenio  inner join cfg_medicos cme  on cme.cm_idmedico = dfic.df_idmedico "
      + "  where dpac.dp_nrodocumento =:run  and dfic.df_norden =:nOrden ";

  private static final String sqlPortalDatosExamenesListaMuestra = "select df.dfe_idexamen as idexamen," + " CASE "
      + " WHEN ce.CE_NOMBREWEB = '' THEN ce.ce_abreviado" + " WHEN ce.CE_NOMBREWEB is null  THEN ce.ce_abreviado"
      + " ELSE ce.CE_NOMBREWEB " + " END  AS nombre " + ", cee.cee_descripcionestadoexamen as estado ,  "
      + "(select NVL2(dm.dmr_idcausarechazo, 'Rechazada', ' ') as muest from BIOSLIS.datos_muestrasrechazadas dm where dm.dmr_idmuestra = (select d.dfet_idmuestra from datos_fichasexamenestest d  "
      + "where df.dfe_norden = d.dfet_norden and df.dfe_idexamen = d.dfet_idexamen "
      + "FETCH FIRST 1 ROWS ONLY) ) as muestra , ce.ce_disponibleweb as disponibleweb, ce.ce_nombreweb as nombreweb , df.dfe_examenanulado as examenanulado,df.DFE_WEBDISPONIBLEFECHA fechawebdisponible "
      + "from datos_fichasexamenes df " + "inner join  cfg_examenes ce  on ce.ce_idexamen = df.dfe_idexamen "
      + "inner join cfg_estadosexamenes  cee  on cee.cee_codigoestadoexamen = df.dfe_codigoestadoexamen  "
      + " where df.dfe_norden =:nOrden ";
  private static final String sqlPortalDatosExamenesListaVisualizacion = "select df.dfe_idexamen as idexamen,  "
      + " CASE " + " WHEN ce.CE_NOMBREWEB = '' THEN ce.ce_abreviado"
      + " WHEN ce.CE_NOMBREWEB is null  THEN ce.ce_abreviado" + " ELSE ce.CE_NOMBREWEB "
      + " END  AS nombre,  cee.cee_descripcionestadoexamen as estado,"
      + "df.dfe_impreso as muestra, ce.ce_disponibleweb as disponibleweb, ce.ce_nombreweb as nombreweb , df.dfe_examenanulado as examenanulado "
      + " , CE.CE_IDDERIVADOR as idderivador,df.DFE_WEBDISPONIBLEFECHA fechawebdisponible "
      + "from datos_fichasexamenes df  inner join  cfg_examenes ce  on ce.ce_idexamen = df.dfe_idexamen "
      + " inner join cfg_estadosexamenes  cee  on cee.cee_codigoestadoexamen = df.dfe_codigoestadoexamen "
      + " where df.dfe_norden =:nOrden ";

  public PortalDatosPacienteOrdenExamenDTO getdatosPacientesOrden(String run, Long nOrden, int tDocumento,
      int tBusqueda) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Query query = null;
    List<PortalExamenesDTO> datoLista = null;
    PortalDatosPacienteOrdenExamenDTO dato = null;
    Calendar calendar = Calendar.getInstance();

    try {

      if (tDocumento == 1) {
        query = sesion.createSQLQuery(sqlPortalDatosPacienteExamenesRut)
            .setResultTransformer(Transformers.aliasToBean(PortalDatosPacienteOrdenExamenDTO.class));

      } else {
        query = sesion.createSQLQuery(sqlPortalDatosPacienteExamenesNroDocumento)
            .setResultTransformer(Transformers.aliasToBean(PortalDatosPacienteOrdenExamenDTO.class));
      }

      query.setString("run", run);
      query.setLong("nOrden", nOrden);
      dato = (PortalDatosPacienteOrdenExamenDTO) query.uniqueResult();

      if (tBusqueda == 1) {

        query = sesion.createSQLQuery(sqlPortalDatosExamenesListaMuestra)
            .setResultTransformer(Transformers.aliasToBean(PortalExamenesDTO.class));
      } else {

        query = sesion.createSQLQuery(sqlPortalDatosExamenesListaVisualizacion)
            .setResultTransformer(Transformers.aliasToBean(PortalExamenesDTO.class));
      }
      query.setLong("nOrden", nOrden);

      datoLista = query.list();

      dato.setExamenes(datoLista);
      // *** agregado por cristian 29-11 para ver si muestras no han sido
      // recepcionadas

      int index = 0;
      List<Object> test = new ArrayList<Object>();
      for (PortalExamenesDTO dt : datoLista) {
        dato.getExamenes().get(index).setESTADOMUESTRA("T");
        dato.getExamenes().get(index).setMUESTRARECEPCION("R");

        Date fechaDisponibleweb = dt.getFECHAWEBDISPONIBLE();
        Date fechaActual = calendar.getTime();
        if (fechaActual.compareTo(fechaDisponibleweb) < 0) {
          dt.setESTADO("PENDIENTE");
        }
        query = sesion
            .createSQLQuery("select  dfet.dfet_norden , dfet.dfet_idexamen, dfet.dfet_idmuestra, dfm.dfm_estadorm "
                + "from datos_fichasexamenestest dfet " + "left join datos_fichasmuestras dfm "
                + "on dfm.dfm_idmuestra = dfet.dfet_idmuestra "
                + "where  (dfet.dfet_norden =:nOrden and dfet.dfet_idexamen =:idExamen)  and ((dfm.dfm_estadorm = 'P' or dfm.dfm_estadorm is  null) or dfet.dfet_idmuestra is null )");

        query.setLong("nOrden", nOrden);
        query.setParameter("idExamen", dt.getIDEXAMEN());
        test = query.list();

        if (test.size() > 0) {
          dato.getExamenes().get(index).setMUESTRARECEPCION("P");
        }

        query = sesion
            .createSQLQuery("select  dfet.dfet_norden , dfet.dfet_idexamen, dfet.dfet_idmuestra, dfm.dfm_estadorm "
                + "from datos_fichasexamenestest dfet " + "left join datos_fichasmuestras dfm "
                + "on dfm.dfm_idmuestra = dfet.dfet_idmuestra "
                + "where  (dfet.dfet_norden =:nOrden and dfet.dfet_idexamen =:idExamen)  and ((dfm.dfm_estadoTM = 'P' or dfm.dfm_estadoTM = 'B' or dfm.dfm_estadoTM = 'R'  or dfm.dfm_estadorm is  null) or dfet.dfet_idmuestra is null )");

        query.setLong("nOrden", nOrden);
        query.setParameter("idExamen", dt.getIDEXAMEN());
        test = query.list();

        if (test.size() > 0) {
          dato.getExamenes().get(index).setESTADOMUESTRA("P");
        }

        index++;
      }

      // *****************************************************************************************

      sesion.close();

    } catch (HibernateException he) {
      logger.error(he.getLocalizedMessage());
      throw new BiosLISDAOException("No se pudo buscar ordenes");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return dato;
  }

  public String getDatosLoginUsuario(Long idUsuario) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    String dato = null;
    try {
      Query query = sesion.createSQLQuery(
          "select cpro.cp_codigo from cfg_perfilesusuarios cp inner join cfg_procedencias cpro  on cpro.cp_idprocedencia = cp.cpu_idprocedencia "
              + " where cp.cpu_idusuario =:idUsuario");
      query.setLong("idUsuario", idUsuario);
      dato = (String) query.uniqueResult();

      sesion.close();
    } catch (HibernateException he) {

      throw new BiosLISDAOException("No se pudo buscar usuario");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return dato;
  }

  private static String sqlGetDeltaCheckTestPaciente = ""
      + "SELECT valorMax, valorMin, 100*(dfet.DFET_RESULTADONUMERICO- Promedio.valorPromedio)/Promedio.valorPromedio PROMEDIO,dfet.DFET_RESULTADONUMERICO, ct.CT_DELTAPORCENTAJE,df.DF_NORDEN "
      + "FROM DATOS_FICHASEXAMENESTEST dfet " + "INNER JOIN CFG_TEST ct ON (ct.CT_IDTEST = dfet.DFET_IDTEST) "
      + "INNER JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_IDEXAMEN = dfet.DFET_IDEXAMEN AND dfe.DFE_NORDEN  = dfet.DFET_NORDEN) "
      + "INNER JOIN DATOS_FICHAS df ON (df.DF_NORDEN  = dfe.DFE_NORDEN) "
      + "INNER JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE  = df.DF_IDPACIENTE ) " + " CROSS JOIN  "
      + "(SELECT  AVG(dfet.DFET_RESULTADONUMERICO) valorPromedio, MAX(dfet.DFET_RESULTADONUMERICO) valorMax,MIN(dfet.DFET_RESULTADONUMERICO) valorMin  "
      + "FROM DATOS_FICHASEXAMENESTEST dfet " + "INNER JOIN CFG_TEST ct ON (ct.CT_IDTEST = dfet.DFET_IDTEST) "
      + "INNER JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_IDEXAMEN = dfet.DFET_IDEXAMEN AND dfe.DFE_NORDEN  = dfet.DFET_NORDEN) "
      + "INNER JOIN DATOS_FICHAS df ON (df.DF_NORDEN  = dfe.DFE_NORDEN) "
      + "INNER JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE  = df.DF_IDPACIENTE ) " + " WHERE  "
      + "dfet.DFET_IDTEST = :idTest " + "    AND  " + "     dfet.DFET_IDPACIENTE  = :idPaciente " + "     AND  "
      + "     ct.CT_DELTACANTIDAD IS NOT NULL  " + "     AND ct.CT_DELTAPORCENTAJE IS NOT NULL  "
      + "     AND ct.CT_DIASEVALUABLES IS NOT NULL  " + "     AND dfet.DFET_FECHAINGRESORESULTADO IS NOT NULL "
      + "     AND trunc(SYSDATE - dfet.DFET_FECHAINGRESORESULTADO) < ct.CT_DIASEVALUABLES ) promedio "

      + "WHERE  " + "dfet.DFET_IDTEST = :idTest " + "    AND  " + "     dfet.DFET_IDPACIENTE  = :idPaciente "
      + "     AND  " + "     ct.CT_DELTACANTIDAD IS NOT NULL  " + "     AND ct.CT_DELTAPORCENTAJE IS NOT NULL  "
      + "     AND ct.CT_DIASEVALUABLES IS NOT NULL  " + "     AND dfet.DFET_FECHAINGRESORESULTADO IS NOT NULL "
      + " and dfet.dfet_resultadonumerico > 0 "
      + "     AND trunc(SYSDATE - dfet.DFET_FECHAINGRESORESULTADO) < ct.CT_DIASEVALUABLES ";

  private static String sqlGetDeltaCheckTestAptos = ""
      + "SELECT  dfet.DFET_RESULTADONUMERICO,ct.CT_DELTACANTIDAD, ct.CT_DELTAPORCENTAJE "
      + "FROM DATOS_FICHASEXAMENESTEST dfet " + "INNER JOIN CFG_TEST ct ON (ct.CT_IDTEST = dfet.DFET_IDTEST) "
      + "INNER JOIN DATOS_FICHASEXAMENES dfe ON (dfe.DFE_IDEXAMEN = dfet.DFET_IDEXAMEN AND dfe.DFE_NORDEN  = dfet.DFET_NORDEN) "
      + "INNER JOIN DATOS_FICHAS df ON (df.DF_NORDEN  = dfe.DFE_NORDEN) "
      + "INNER JOIN DATOS_PACIENTES dp ON (dp.DP_IDPACIENTE  = df.DF_IDPACIENTE ) " + " WHERE  "
      + "dfet.DFET_IDTEST = :idTest " + "    AND  " + "     dfet.DFET_IDPACIENTE  = :idPaciente " + "     AND  "
      + "     ct.CT_DELTACANTIDAD IS NOT NULL  " + " AND ct.CT_DELTAPORCENTAJE IS NOT NULL  "
      + " AND ct.CT_IDTIPORESULTADO=3 " + "     AND ct.CT_DIASEVALUABLES IS NOT NULL  "
      + "     AND dfet.DFET_FECHAINGRESORESULTADO IS NOT NULL " + "  AND  (dfet.DFET_IDESTADORESULTADOTEST = 5  ) "
      + "     AND trunc(SYSDATE - dfet.DFET_FECHAINGRESORESULTADO) < ct.CT_DIASEVALUABLES and dfet.dfet_norden != :nOrden  "
      + " ORDER BY dfet.DFET_FECHAINGRESORESULTADO DESC";

//***********************************************************************************

  // **** AGREGADO POR CRISTIAN 13-10 DATOS PARA MOSTRAR EN EDITAR ORDEN.
  private static final String sqlDatosPacienteMedicoEditarOrden = "select df.df_fechaorden as fechaorden, dp.dp_idtipodocumento as idtipodocumento, dp.dp_run as runpaciente, "
      + "dp.dp_nrodocumento as nrdocumentopaciente ,concat(concat(concat(concat(dp.dp_nombres, ' ' ), dp.dp_primerapellido), ' '), dp.dp_segundoapellido) as nombrepaciente, "
      + "dp.dp_idsexo as sexopaciente, (select ldv_sexo.ldvs_descripcion from ldv_sexo where ldv_sexo.ldvs_idsexo = dp.dp_idsexo) as sexodescripcion,"
      + " dp.dp_fnacimiento as fnacimientopaciente , trunc((SYSDATE - dp.dp_fnacimiento)/365, 0) AS edadpaciente, dp.dp_observacion as observacionpaciente, "
      + "dp.dp_email as emailpaciente, df.df_idtipoatencion as ctaIdtipoatencion, df.df_idprocedencia as idprocedencia, df.DF_IDSERVICIO as idservicio, "
      + "cl.cl_idsalaservicio as idsalaservicio, cl.cl_idcamasala as idcamasala, "
      + "df.df_idprioridadatencion as idprioridadatencion , df.df_idmedico as idmedico, "
      + "concat(concat(concat(concat(cm.cm_nombres, ' ' ), cm.cm_primerapellido), ' '), cm.cm_segundoapellido) as nombremedico, "
      + "cm.cm_run as runmedico, df.df_idconvenio as idconvenio,  df.df_iddiagnostico as iddiagnostico, df.df_observacion as observacionorden ,"
      + " df.df_enviarresultadosemail as ENVIARRESULTADOSEMAIL " + "from datos_fichas df "
      + "inner join datos_pacientes  dp " + "on dp.dp_idpaciente = df.df_idpaciente "
      + "inner join cfg_localizaciones cl " + "on cl.cl_idlocalizacion = df.df_idlocalizacion "
      + "inner join cfg_medicos cm " + "on cm.cm_idmedico = df.df_idmedico "
      + "where df.df_norden =:nOrden and df.df_idpaciente = :idPaciente";

  private static final String sqlPacientePatologiasEditarOrden = "select dpp.dpp_idpacientepatologia as idpacientepatologia ,dpp.dpp_idpatologia as idpatologia, "
      + "lvdpat.ldvpat_descripcion as patologia, dpp.dpp_observacion as observacion from datos_pacientespatologias dpp "
      + "inner join ldv_patologias lvdpat " + "on lvdpat.ldvpat_idpatologia = dpp.dpp_idpatologia "
      + "where dpp.dpp_idpaciente =:idPaciente ";

  private static final String sqlExamenesEditarOrden = "select ce.ce_idexamen as idexamen, ce.ce_codigoexamen as codexamen, ce.ce_abreviado as examenabreviado,"
      + " ce.ce_idtipomuestra as idmuestra, ce.ce_descripcion as cedescripcion, dfe.dfe_codigoestadoexamen as CODESTADOEXAMEN ,dfe.dfe_examenanulado as EXAMENANULADO ,"
      + "DFE.DFE_IDUSUARIOANULA IDUSUARIOANULA ,"
      + " cmue.cmue_descripcionabreviada as descripcionmuestra, ldvie.ldvie_descripcion as ldviedescripcion, ldvie.ldvie_idindicacionesexamen as ldvieidindicacionesexamen ,"
      + "ce.ce_idtipoexamen as idtipoexamen, ce.ce_idseccion as idseccion , csec.csec_descripcion as descripcionseccion, csec.csec_idlaboratorio as idlaboratorio,"
      + "clab.clab_descripcion as descripcionlaboratorio, clab.clab_idcentrodesalud as idcentrosalud, ccds.ccds_descripcion as descripcioncentrosalud, dfe.dfe_examenesurgente as ceesurgente "
      + "from datos_fichasexamenes dfe " + "inner join cfg_examenes ce " + "on ce.ce_idexamen = dfe.dfe_idexamen "
      + "inner join cfg_muestras cmue " + "on cmue.cmue_idtipomuestra = ce.ce_idtipomuestra "
      + "inner join cfg_secciones csec " + "on csec.csec_idseccion = ce.ce_idseccion "
      + "inner join cfg_laboratorios clab " + "on clab.clab_idlaboratorio = csec.csec_idlaboratorio "
      + "inner join cfg_centrosdesalud ccds " + "on ccds.ccds_idcentrodesalud = clab.clab_idcentrodesalud "
      + "left join ldv_indicacionesexamenes ldvie " + "on ldvie.ldvie_idindicacionesexamen = ce.ce_idindicacionexamen "
      + "where dfe.dfe_norden =:nOrden";

  public DatosEditarOrdenDTO getPacienteMedicoExamenesPatologias(Long idPaciente, Long nOrden)
      throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    DatosEditarOrdenDTO result;
    List<PacientePatologiaEditarOrdenDTO> result2;
    List<ExamenEditarOrdenDTO> result3;
    List<TestEdicionOrdenDTO> result4 = new ArrayList<TestEdicionOrdenDTO>();
    List<TestEdicionOrdenDTO> result5 = new ArrayList<TestEdicionOrdenDTO>();
    try {
      Query qry = sesion.createSQLQuery(sqlDatosPacienteMedicoEditarOrden)
          .setResultTransformer(Transformers.aliasToBean(DatosEditarOrdenDTO.class));
      qry.setLong("idPaciente", idPaciente);
      qry.setLong("nOrden", nOrden);
      result = (DatosEditarOrdenDTO) qry.uniqueResult();

      qry = sesion.createSQLQuery(sqlPacientePatologiasEditarOrden)
          .setResultTransformer(Transformers.aliasToBean(PacientePatologiaEditarOrdenDTO.class));
      qry.setLong("idPaciente", idPaciente);
      result2 = qry.list();
      result.setPatologias(result2);

      qry = sesion.createSQLQuery(sqlExamenesEditarOrden)
          .setResultTransformer(Transformers.aliasToBean(ExamenEditarOrdenDTO.class));
      qry.setLong("nOrden", nOrden);
      result3 = qry.list();
      result.setExamenes(result3);

      // agregado por cristian 21-11 *****************************

      qry = sesion.createSQLQuery(
          "select dfet_idmuestra as idmuestra, dfet_idenvase as idenvase , dfet_idtipomuestra as idtipomuestra from datos_fichasexamenestest "
              + " where dfet_norden =:nOrden GROUP BY dfet_idmuestra,  dfet_idenvase, dfet_idtipomuestra")
          .setResultTransformer(Transformers.aliasToBean(TestEdicionOrdenDTO.class));
      qry.setLong("nOrden", nOrden);
      result4 = qry.list();
      result.setTests(result4);

      String consulta = "select  ce.ce_idexamen as idexamen ,ct.ct_idtest as idtest, ct.ct_idenvase as idenvase, ct.ct_idtipomuestra as idtipomuestra "
          + "from cfg_test ct " + "join cfg_examenestest cet on cet.cet_idtest = ct.ct_idtest "
          + "inner join cfg_examenes ce on ce.ce_idexamen = cet.cet_idexamen " + "where (";
      String consulta2 = "and ce.ce_idexamen not in " + "(select  ce.ce_idexamen "
          + "from cfg_test ct  join cfg_examenestest cet on cet.cet_idtest = ct.ct_idtest  "
          + "inner join cfg_examenes ce on ce.ce_idexamen = cet.cet_idexamen  " + "where ";
      int contador = 0;

      if (result.getTests().get(0).getIDMUESTRA() == null) {
        result.setMUESTRATOMADA("N");
      } else {
        result.setMUESTRATOMADA("S");
        for (TestEdicionOrdenDTO test : result4) {

          if (contador > 0) {
            consulta += " or ";
            consulta2 += " and ";
          }

          consulta += "  (ct.ct_idenvase =" + test.getIDENVASE() + " and ct.ct_idtipomuestra ="
              + test.getIDTIPOMUESTRA() + " )";
          consulta2 += " (ct.ct_idenvase !=" + test.getIDENVASE() + " and ct.ct_idtipomuestra !="
              + test.getIDTIPOMUESTRA() + " ) ";
          contador++;
        }
        consulta += ")" + consulta2 + ")";

        qry = sesion.createSQLQuery(consulta).setResultTransformer(Transformers.aliasToBean(TestEdicionOrdenDTO.class));
        result5 = qry.list();

      }

      for (TestEdicionOrdenDTO resp : result4) {

        contador = 0;
        for (TestEdicionOrdenDTO resp2 : result5) {
          if (resp.getIDENVASE().equals(resp2.getIDENVASE())
              && resp.getIDTIPOMUESTRA().equals(resp2.getIDTIPOMUESTRA())) {
            resp2.setIDMUESTRA(resp.getIDMUESTRA());
            result5.get(contador).setIDMUESTRA(resp.getIDMUESTRA());

          }
          contador++;
        }
      }
      result.setTests(result5);
      sesion.close();

    } catch (HibernateException he) {
      logger.error(he.getMessage());
      throw new BiosLISDAOException("".concat(he.getLocalizedMessage()));
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return result;
  }

  public List<LogPacientesDTO> getEventosPaciente(Long idPaciente) throws BiosLISDAOException {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    List<LogPacientesDTO> lista = new ArrayList<LogPacientesDTO>();
    try {
      Query query = sesion.createSQLQuery(
          "Select lp.lp_idlogpacientes, lp.lp_idusuario, lp.lp_fechamodificacion, lp.lp_idpaciente, lp.lp_idacciondato, "
              + "lp.lp_valoranterior, lp.lp_valornuevo, lp.lp_campomodificado, lp.lp_ipusuario, lp.lp_nombreequipo , du.du_nombres , du.du_primerapellido ,"
              + "du.du_segundoapellido , dp_nrodocumento " + " from  LOG_PACIENTES lp "
              + " INNER JOIN datos_usuarios du " + " on du.du_idusuario = lp.lp_idusuario"
              + " INNER JOIN datos_pacientes dp on dp.dp_idpaciente = lp.lp_idpaciente  "
              + "where lp.lp_idpaciente =:idPaciente " + " order by lp.lp_idlogpacientes DESC")
          .setResultTransformer(Transformers.aliasToBean(LogPacientesDTO.class));

      query.setParameter("idPaciente", idPaciente);
      lista = query.list();

      sesion.close();
    } catch (HibernateException he) {
      he.printStackTrace();
      throw new BiosLISDAOException("No se pudo buscar evento");
    } finally {
      if (sesion.isOpen()) {
        sesion.close();
      }
    }
    return lista;
  }

}
