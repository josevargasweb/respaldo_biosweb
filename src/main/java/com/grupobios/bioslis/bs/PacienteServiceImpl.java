/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.BiosLisCalendarService;
import com.grupobios.bioslis.common.BiosLisLogEventos;
import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.common.DatosPacientesHelper;
import com.grupobios.bioslis.common.PatologiaHelper;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.CfgAntecedentesDAO;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.CfgTipoAtencionDAO;
import com.grupobios.bioslis.dao.DatosContactosDAO;
import com.grupobios.bioslis.dao.DatosFichasAntecedentesDAO;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.dao.DatosPacientesDAO;
import com.grupobios.bioslis.dao.DatosPacientespatologiasDAO;
import com.grupobios.bioslis.dao.LdvContactospacientesrelacionDAO;
import com.grupobios.bioslis.dao.LdvPatologiasDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LdvTiposdocumentosDAO;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;
import com.grupobios.bioslis.dao.LogPacientesDAO;
import com.grupobios.bioslis.dto.AntecedentePacienteDTO;
import com.grupobios.bioslis.dto.DatosEditarOrdenDTO;
import com.grupobios.bioslis.dto.DatosPacienteDTO;
import com.grupobios.bioslis.dto.DatosPacienteOrdenDTO;
import com.grupobios.bioslis.dto.DeltaCheckAptoDTO;
import com.grupobios.bioslis.dto.ExamenValidoDTO;
import com.grupobios.bioslis.dto.LogPacientesDTO;
import com.grupobios.bioslis.dto.OrdenExamenValidoDTO;
import com.grupobios.bioslis.dto.PatologiaDTO;
import com.grupobios.bioslis.dto.PortalDatosPacienteOrdenExamenDTO;
import com.grupobios.bioslis.entity.CfgAntecedentes;
import com.grupobios.bioslis.entity.CfgProcedencias;
import com.grupobios.bioslis.entity.CfgServicios;
import com.grupobios.bioslis.entity.CfgTipoatencion;
import com.grupobios.bioslis.entity.DatosContactos;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosFichasantecedentes;
import com.grupobios.bioslis.entity.DatosFichasantecedentesId;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosPacientespatologias;
import com.grupobios.bioslis.entity.LdvComunas;
import com.grupobios.bioslis.entity.LdvContactospacientesrelacion;
import com.grupobios.bioslis.entity.LdvEstadosciviles;
import com.grupobios.bioslis.entity.LdvPaises;
import com.grupobios.bioslis.entity.LdvPatologias;
import com.grupobios.bioslis.entity.LdvRegiones;
import com.grupobios.bioslis.entity.LdvTiposdocumentos;
import com.grupobios.bioslis.entity.LogEventosfichas;
import com.grupobios.common.DateFormatterHelper;
import com.grupobios.common.Edad;

/**
 *
 * @author eric.nicholls
 */
@Service
public class PacienteServiceImpl implements PacienteService {

  public LdvTiposdocumentosDAO getLdvTiposdocumentos_dao() {
    return ldvTiposdocumentos_dao;
  }

  public void setLdvTiposdocumentos_dao(LdvTiposdocumentosDAO ldvTiposdocumentos_dao) {
    this.ldvTiposdocumentos_dao = ldvTiposdocumentos_dao;
  }

  private OrdenService ordenService;
  private static final Logger logger = LogManager.getLogger(PacienteServiceImpl.class);

  private DatosPacientesDAO datosPacientesDAO;
  private LdvSexoDAO ldvSexosDAO;

  public LdvSexoDAO getLdvSexosDAO() {
    return ldvSexosDAO;
  }

  public void setLdvSexosDAO(LdvSexoDAO ldvSexosDAO) {
    this.ldvSexosDAO = ldvSexosDAO;
  }

  private LdvTiposdocumentosDAO ldvTiposdocumentos_dao;
  private LdvPatologiasDAO ldvPatologiasDAO;
  // MC 13/05/2022
  private CfgProcedenciasDAO cfgProcedenciasDAO;
  private CfgServiciosDAO servicios_dao;
  private DatosFichasDAO datosFichasDAO;

  private BiosLisLogEventos biosLisLogEventos = new BiosLisLogEventos();

  public BiosLisLogEventos getBiosLisLogEventos() {
    return biosLisLogEventos;
  }

  public void setBiosLisLogEventos(BiosLisLogEventos biosLisLogEventos) {
    this.biosLisLogEventos = biosLisLogEventos;
  }

  public LdvPatologiasDAO getLdvPatologiasDAO() {
    return ldvPatologiasDAO;
  }

  public void setLdvPatologiasDAO(LdvPatologiasDAO ldvPatologiasDAO) {
    this.ldvPatologiasDAO = ldvPatologiasDAO;
  }

  private DatosPacientespatologiasDAO dppDAO;
  private DatosContactosDAO dcDAO;

  private CfgAntecedentesDAO cfgaDAO;
  private DatosFichasAntecedentesDAO dfaDAO;

  public DatosPacientespatologiasDAO getDppDAO() {
    return dppDAO;
  }

  public void setDppDAO(DatosPacientespatologiasDAO dppDAO) {
    this.dppDAO = dppDAO;
  }

  public DatosContactosDAO getDcDAO() {
    return dcDAO;
  }

  public void setDcDAO(DatosContactosDAO dcDAO) {
    this.dcDAO = dcDAO;
  }

  public List<PatologiaDTO> getPatologias(Long idPaciente) throws BiosLISDAOException {

    return datosPacientesDAO.getPatologiasPaciente(idPaciente);
  }

  public DatosPacientesDAO getDatosPacientesDAO() {
    return datosPacientesDAO;
  }

  public void setDatosPacientesDAO(DatosPacientesDAO datosPacientesDAO) {
    this.datosPacientesDAO = datosPacientesDAO;
  }

  @Override
  public void insertPaciente(DatosPacientes dp, DatosContactos dc, String[] patologias,
      String[] observacionesPatologias, String rutMadre, Long idUsuario) throws BiosLISDAOException {

    if (dp.getDpReciennacido() == null) {
      dp.setDpReciennacido("N");
    }
    if (dp.getDpRnpartomultiple() == null) {
      dp.setDpRnpartomultiple("N");
    }
    try {

      if (dp.getLdvTiposdocumentos().equals(Constante.LDVTD_RECIENNACIDO)) {

        datosPacientesDAO.insertDatosPacientesRN(dp, rutMadre);
        datosPacientesDAO.updateNroDocTipo(dp.getDpIdpaciente(), "RN");
      } else if (dp.getLdvTiposdocumentos().equals(Constante.LDVTD_SINIDENTIFICACION)) {
        datosPacientesDAO.insertDatosPacientesNN(dp);
        datosPacientesDAO.updateNroDocTipo(dp.getDpIdpaciente(), "NN");
      } else {
        datosPacientesDAO.insertDatosPacientes(dp);
      }

      int cont = 0;

      // PATOLOGIA

      if (patologias != null) {
        for (String codPatologia : patologias) {
          DatosPacientespatologiasDAO dppDAO = new DatosPacientespatologiasDAO();
          LdvPatologias patologia = new LdvPatologias();
          DatosPacientespatologias dpp = new DatosPacientespatologias();
          patologia.setLdvpatIdpatologia(Integer.parseInt(codPatologia));
          dpp.setDatosPacientes(dp);
          dpp.setLdvPatologias(patologia);
          dpp.setDppObservacion(observacionesPatologias[cont]);
          dppDAO.insertDatosPacientesPatologias(dpp);
          cont++;
        }
      }

      // CONTACTO PACIENTE

      if (dc.getDcNombre() != null && !dc.getDcNombre().trim().isEmpty()) {

        DatosContactosDAO dcDAO = new DatosContactosDAO();
        dc.setDatosPacientes(dp);
        dc.setDcNombre(dc.getDcNombre().toUpperCase());
        dcDAO.insertDatosContactos(dc);
      }

      // log paciente
      LogPacientesDAO logPaciente = new LogPacientesDAO();
      // modificado por cristian para log paciente 02-02

      logPaciente.insertLogPaciente("", "", "", 1, idUsuario.intValue(), " ", dp, " ");

    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());

      throw e;
    }

  }

  @Override
  public DatosPacienteDTO getPacienteByRut(String rutPaciente) throws BiosLISDAOException, BiosLISDAONotFoundException {

    DatosPacienteDTO dpto = new DatosPacienteDTO();

    dpto.setDp(datosPacientesDAO.getPacienteByRut(rutPaciente));
    dpto.setListaPatologias(dppDAO.getPatologiasxPacientesList(dpto.getDp()));
    return dpto;
  }

  @Override
  public List<AntecedentePacienteDTO> getAntecedentesPacienteOrden(BigDecimal nroOrden) throws BiosLISDAOException {

    return datosPacientesDAO.getAntecedentesPacienteOrden(nroOrden);

  }

  @Override
  public DatosPacienteDTO getDataRN(BigDecimal idPaciente)
      throws ParseException, BiosLISDAOException, BiosLISDAONotFoundException {
    return datosPacientesDAO.getRnById(idPaciente.intValue());
  }

  @Override
  public void udpatePaciente(DatosPacientes dp, LdvTiposdocumentos tipoDocumento, LdvEstadosciviles estadoCivil,
      LdvPaises pais, LdvRegiones region, LdvComunas comuna,
      LdvContactospacientesrelacion ldvContactospacientesrelacion, DatosContactos dc, HttpServletRequest request)
      throws BiosLISDAOException, ParseException, BiosLISDAONotFoundException {

    try {
      String exitus = request.getParameter("exitus");
      String esPartoMultiple = request.getParameter("esPartoMultiple");
      String ip = request.getParameter("ipEquipo");
      String[] patologias = request.getParameterValues("liPatologias");
      String[] observacionesPatologias = request.getParameterValues("observacionesPatologias");
      String fechaNacimiento = request.getParameter("dpFnacimiento");

      DatosPacientesHelper.estandarizaPaciente(dp, fechaNacimiento, esPartoMultiple, tipoDocumento, estadoCivil, pais,
          region, comuna, exitus);

      if (esPartoMultiple == null) {
        esPartoMultiple = "0";
      }
      if (esPartoMultiple.equals("0")) {
        dp.setDpRnnumero(0);
        dp.setDpRnpartomultiple("N");
      } else {
        dp.setDpRnpartomultiple("S");
      }

      String fechaCodificada = DateFormatterHelper.dateToText(dp.getDpFnacimiento());
      String rut4digitos = "";
      if (dp.getDpNrodocumento() != null) {
        rut4digitos = dp.getDpNrodocumento().substring(dp.getDpNrodocumento().length() - 5,
            dp.getDpNrodocumento().length());
      }

      String segundoApellidoVip = dp.getDpSegundoapellido().trim();
      if (segundoApellidoVip.equals("")) {
        segundoApellidoVip = "#";
      }
      String nombreCodificado = dp.getDpNombres().substring(0, 1) + dp.getDpPrimerapellido().substring(0, 1)
          + segundoApellidoVip.substring(0, 1) + " " + fechaCodificada + " " + rut4digitos;
      dp.setDpNombreencriptado(nombreCodificado);
      datosPacientesDAO.updateDatosPacientes(dp, ip);

// PATOLOGIA 
      int cont = 0;

      if (patologias != null) {
        dppDAO.delPatologiasxPaciente(dp);

        for (String codPatologia : patologias) {
          DatosPacientespatologiasDAO dppDAO = new DatosPacientespatologiasDAO();
          LdvPatologias patologia = new LdvPatologias();
          DatosPacientespatologias dpp = new DatosPacientespatologias();
          patologia.setLdvpatIdpatologia(Integer.parseInt(codPatologia));
          dpp.setDatosPacientes(dp);
          dpp.setLdvPatologias(patologia);
          dpp.setDppObservacion(observacionesPatologias[cont]);
          dppDAO.insertDatosPacientesPatologias(dpp);
          cont++;
        }

      }

// CONTACTO PACIENTE 

      DatosContactos contacto = dcDAO.getContactoxPaciente(dp);
      if (contacto != null) {
        contacto.setDcNombre(dc.getDcNombre().toUpperCase());
        if (ldvContactospacientesrelacion != null
            && ldvContactospacientesrelacion.getLdvcprIdcontactopacienterel() != 0) {
          contacto.setLdvContactospacientesrelacion(ldvContactospacientesrelacion);
        }
        contacto.setDcIdsexo(dc.getDcIdsexo());
        dcDAO.updateDatosContactos(contacto);
      } else if (dc.getDcNombre() != null && !dc.getDcNombre().trim().isEmpty() && dc.getDcTelefono() != null
          && !dc.getDcTelefono().trim().isEmpty()) {
        dc.setDatosPacientes(dp);
        dc.setDcNombre(dc.getDcNombre().toUpperCase());
        dc.setDcIdsexo(dc.getDcIdsexo());
        if (ldvContactospacientesrelacion != null
            && ldvContactospacientesrelacion.getLdvcprIdcontactopacienterel() != 0) {
          dc.setLdvContactospacientesrelacion(ldvContactospacientesrelacion);
        }
        dcDAO.insertDatosContactos(dc);
      }

    } catch (BiosLISDAOException | ParseException be) {
      logger.error(be.getMessage());
      throw be;

    } catch (BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
      throw e;
    } finally {

    }
  }

  @Override
  public void udpatePaciente(DatosPacienteDTO dpDto, DatosContactos dc, Long idUsuario)
      throws BiosLISDAONotFoundException, BiosLISDAOException, ParseException, BiosLISException {

    LogPacientesDAO logPaciente = new LogPacientesDAO();
    try {

      String ip = "";
      DatosPacientes dp = dpDto.getDp();
      // AGREGADO POR CRISTIAN PARA ENVIAR datos paciente antes de modificar A FUNCION
      // COMPARADOROBJETOS.
      DatosPacientes pacienteAntiguo = datosPacientesDAO.getPacienteById(dp.getDpIdpaciente());
      // ********************************************************************
      // se le suma 1 dia a la fecha de nacimiento por que java le resta 1 dia
//      Date date = dp.getDpFnacimiento(); 
//
//      Calendar calendar = Calendar.getInstance();
//      calendar.setTime(date); 
//      calendar.add(Calendar.DAY_OF_MONTH, 1); 
//      calendar.set(Calendar.HOUR_OF_DAY, 0);
//      calendar.set(Calendar.MINUTE, 0);
//      calendar.set(Calendar.SECOND, 0);
//      calendar.set(Calendar.MILLISECOND, 0);
//      Date nextDay = calendar.getTime(); 
//      
//      dp.setDpFnacimiento(nextDay);
      if (dp.getDpExitus() != null && dp.getDpExitus().equals("1")) {
        dp.setDpExitus("S");
      } else {
        dp.setDpExitus("N");
      }

      if (dp.getDpEsvip() != null && dp.getDpEsvip().equals("1")) {
        dp.setDpEsvip("S");
      } else {
        dp.setDpEsvip("N");
      }

      if (dp.getDpEsafroamericano() != null && dp.getDpEsafroamericano().equals("1")) {
        dp.setDpEsafroamericano("S");
      } else {
        dp.setDpEsafroamericano("N");
      }

      if (dp.getDpIdmadre() != null && dp.getDpIdmadre() != 0) {
        if (dp.getDpRnpartomultiple() == null || dp.getDpRnpartomultiple().equals("N")) {
          dp.setDpRnnumero(0);
          dp.setDpRnpartomultiple("N");
        } else {
          dp.setDpRnpartomultiple("S");
        }
      }

      dp.setDpNombreencriptado(DatosPacientesHelper.getNombreencriptado(dp));
      DatosPacientesHelper.estandarizaNombresPaciente(dp);
      dp = datosPacientesDAO.updateDatosPacientes(dp, ip);

// PATOLOGIA 
      int cont = 0;
      List<DatosPacientespatologias> listaPatologias = dpDto.getListaPatologias();
      List<DatosPacientespatologias> lista = dppDAO.getPatologiasxPacientesList(dp);
      if (dp != null) {
        // aqui borra todas las patologias del paciente
        dppDAO.delPatologiasxPaciente(dp);
        // *******************

        List<LdvPatologias> lstVPatologias = ldvPatologiasDAO.getPatologias();
        PatologiaHelper.init(lstVPatologias);
        List<Integer> idpatologias = new ArrayList<Integer>();
        for (DatosPacientespatologias patologia : listaPatologias) {
          DatosPacientespatologias dpat = new DatosPacientespatologias();
          dpat.setDatosPacientes(dp);
          dpat.setDppObservacion(patologia.getDppObservacion());
          dpat.setLdvPatologias(PatologiaHelper.getPatologiaByCod(patologia.getLdvPatologias().getLdvpatIdpatologia()));
          dppDAO.insertDatosPacientesPatologias(dpat);

          // agregado por cristian para revisar cuales fueron agregados , cambiados o
          // eliminados para registrar en logeventospaciente

          int existe = 0;
          for (DatosPacientespatologias p : lista) {
            if (patologia.getLdvPatologias().getLdvpatIdpatologia() == p.getLdvPatologias().getLdvpatIdpatologia()) {
              idpatologias.add(patologia.getLdvPatologias().getLdvpatIdpatologia());
              patologia.getLdvPatologias().setLdvpatDescripcion(p.getLdvPatologias().getLdvpatDescripcion());
              biosLisLogEventos.comparadorObjetos(p, patologia, 2, idUsuario.intValue(), "equipo", "000.000.000");
              existe = 1;
            }
          }

          // aqui se agrega nueva patologia en logpacientes
          if (existe == 0) {
            patologia.setDatosPacientes(dpat.getDatosPacientes());
            patologia.setDppObservacion(null);
            biosLisLogEventos.comparadorObjetos(patologia, dpat, 2, idUsuario.intValue(), "equipo", "000.000.000");
          }

          // ****************************************************************************************

          cont++;
        }
        // aqui se agrega a log las patologias que se eliminaron
        for (DatosPacientespatologias p : lista) {
          if (!idpatologias.contains(p.getLdvPatologias().getLdvpatIdpatologia())) {
            logPaciente.insertLogPaciente("DPP_OBSERVACION", "", p.getDppObservacion(), 3, idUsuario.intValue(),
                "nombreEquipo", dp, "000.000.000");
            logPaciente.insertLogPaciente("LDVPAT_DESCRIPCION", "", p.getLdvPatologias().getLdvpatDescripcion(), 3,
                idUsuario.intValue(), "nombreEquipo", dp, "000.000.000");
          }
        }
        // *********************************************************************
      }

// CONTACTO PACIENTE 
      DatosContactos contacto = dcDAO.getContactoxPaciente(dp);
      // agregado por cristian para no modificar los datos y usarlo de comparador
      DatosContactos contactoAntiguo = dcDAO.getContactoxPaciente(dp);

      if (dc.getDcNombre() == null || dc.getDcNombre().trim().equals("") || dc.getDcTelefono() == null
          || dc.getDcTelefono().trim().equals("")) {
        logger.info("No se actualiza datos de contacto");
      } else {

        if (contacto == null) {
          dc.setDatosPacientes(dp);

          contacto = new DatosContactos();
          contacto.setDatosPacientes(dp);
          logger.debug("Valor de contacto:{} ", contacto);
          LdvContactospacientesrelacionDAO ldcrDao = new LdvContactospacientesrelacionDAO();// Cambiar a bean spring
          LdvContactospacientesrelacion ldvcr = ldcrDao.getContactosRelacionById(
              dpDto.getDc().getLdvContactospacientesrelacion().getLdvcprIdcontactopacienterel());

          if (ldvcr != null) {
            contacto.setLdvContactospacientesrelacion(ldvcr);
          } else {
            contacto.setLdvContactospacientesrelacion(null);
          }
          contacto.setDcIdsexo(dc.getDcIdsexo());
          contacto.setDcNombre(dc.getDcNombre());
          contacto.setDcTelefono(dc.getDcTelefono());
          contactoAntiguo = new DatosContactos();
          contactoAntiguo.setDatosPacientes(dp);
          dcDAO.insertDatosContactos(contacto);
        } else {
          contacto.setDatosPacientes(dp);
          logger.debug("Valor de contacto:{} ", contacto);
          LdvContactospacientesrelacionDAO ldcrDao = new LdvContactospacientesrelacionDAO();// Cambiar a bean spring
          LdvContactospacientesrelacion ldvcr = ldcrDao.getContactosRelacionById(
              dpDto.getDc().getLdvContactospacientesrelacion().getLdvcprIdcontactopacienterel());
          if (ldvcr != null) {
            contacto.setLdvContactospacientesrelacion(ldvcr);
          } else {
            contacto.setLdvContactospacientesrelacion(null);
          }
          contacto.setDcIdsexo(dc.getDcIdsexo());
          contacto.setDcNombre(dc.getDcNombre());
          contacto.setDcTelefono(dc.getDcTelefono());
          dcDAO.updateDatosContactos(contacto);
        }
        biosLisLogEventos.comparadorObjetos(contactoAntiguo, contacto, 2, idUsuario.intValue(), "equipo",
            "000.000.000");

      }

      // aqui se formatea fechas de PACIENTE y se manda a logpacientes agregado por
      // cristian
      String fechaAntigua = "";
      String fechaNueva = "";

      if (pacienteAntiguo.getDpFnacimiento() != null) {
        // Date fecha =
        // biosLisLogEventos.FormatFecha(pacienteAntiguo.getDpFnacimiento());
        Calendar c = Calendar.getInstance();
        c.setTime(pacienteAntiguo.getDpFnacimiento());
        c.add(Calendar.DATE, 1);
        Date fecha = c.getTime();
        fechaAntigua = biosLisLogEventos.FormatFechaATexto(fecha);
      }
      if (dp != null) {
        if (dp.getDpFnacimiento() != null) {
          // Date fecha = biosLisLogEventos.FormatFecha(dp.getDpFnacimiento());
          Calendar c = Calendar.getInstance();
          c.setTime(dp.getDpFnacimiento());
          c.add(Calendar.DATE, 1);
          Date fecha = c.getTime();
          fechaNueva = biosLisLogEventos.FormatFechaATexto(fecha);
        }
      }
      LogPacientesDAO logPacientesDAO = new LogPacientesDAO();
      if (!fechaNueva.equals(fechaAntigua)) {
        if (pacienteAntiguo.getDpFnacimiento() != null) {
          logPacientesDAO.insertLogPaciente("DP_FNACIMIENTO", fechaNueva, fechaAntigua, 2, idUsuario.intValue(), "0",
              dp, "0");
        } else {
          logPacientesDAO.insertLogPaciente("DP_FNACIMIENTO", fechaNueva, fechaAntigua, 1, idUsuario.intValue(), "0",
              dp, "0");
        }
      }
      biosLisLogEventos.comparadorObjetos(pacienteAntiguo, dp, 2, idUsuario.intValue(), "equipo", "000.000.000");
      // ***************************************************
    } catch (BiosLISDAOException be) {
      be.printStackTrace();
      logger.error(be.getMessage());
      throw new BiosLISDAOException("Error  updateDatosPacientes" + be);
    } catch (BiosLISDAONotFoundException e) {
      logger.error(e.getMessage());
      throw new BiosLISDAONotFoundException("Error  updateDatosPacientes" + e);
    } finally {

    }

  }

  @Override
  public List<AntecedentePacienteDTO> getAntecedentesPaciente(Long nOrden) throws BiosLISDAOException {
    List<AntecedentePacienteDTO> listAntecedentes = datosPacientesDAO.getAntecedentesPaciente(nOrden);
    for (AntecedentePacienteDTO apdto : listAntecedentes) {
      if (apdto.getCA_IDANTECEDENTE().equals(BigDecimal.valueOf(10))) {
        try {
          DatosPacienteOrdenDTO pacienteDTO = datosPacientesDAO.getPacienteOrden(BigDecimal.valueOf(nOrden));
          Edad edad = Edad.getEdadActual(pacienteDTO.getDP_FNACIMIENTO());
          apdto.setDFA_VALORANTECEDENTE(edad.toString());
        } catch (BiosLISDAOException ex) {
          java.util.logging.Logger.getLogger(PacienteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }

    return listAntecedentes;
  }

  @SuppressWarnings("unused")
  @Override
  public void guardarAntecedentes(List<AntecedentePacienteDTO> listAntecedentes, Long idUsuario)
      throws BiosLISDAOException {

    // ***** agregado para agregar a eventos ******************
    DatosFichas df = datosFichasDAO.getOrdenxID((int) listAntecedentes.get(0).getDFA_NORDEN().longValue());
    LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
    LogEventosfichas lef = new LogEventosfichas();

    lef.setDatosFichas(df.getDfNorden());
    lef.setLefFechaorden(df.getDfFechaorden());
    lef.setLefNombretabla("DATOS_FICHASANTECEDENTES");
    lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
    lef.setLefIdusuario(idUsuario.intValue());
    lef.setLefIdpaciente(df.getDatosPacientes());

    // *****************************************************

    for (AntecedentePacienteDTO apdto : listAntecedentes) {
      DatosFichasantecedentes dfa = new DatosFichasantecedentes();
      DatosFichasantecedentesId dfaId = new DatosFichasantecedentesId();
      dfaId.setDfaIdantecedente(apdto.getCA_IDANTECEDENTE().shortValue());
      dfaId.setDfaNorden(apdto.getDFA_NORDEN().longValue());
      dfa.setId(dfaId);
      CfgAntecedentes cfgA = cfgaDAO.getAntecedenteById(apdto.getCA_IDANTECEDENTE().intValue());
      dfa.setCfgAntecedentes(cfgA);
      if (apdto.getCA_CODIGOANTECEDENTE().equals("EDAD")) {
        String[] edadSeparada = apdto.getDFA_VALORANTECEDENTE().split(" ");
        dfa.setDfaValorantecedente(edadSeparada[0]);
      } else {
        dfa.setDfaValorantecedente(apdto.getDFA_VALORANTECEDENTE());
      }

      // ***** agregado para agregar a eventos ******************
      DatosFichasAntecedentesDAO dfaDao = new DatosFichasAntecedentesDAO();
      DatosFichasantecedentes dfaAntiguo = dfaDao.getAntecedentesPorIdyNorden(apdto.getCA_IDANTECEDENTE().shortValue(),
          apdto.getDFA_NORDEN().longValue());

      String valorAntiguo = "";
      String nuevo = "";

      if (dfa.getDfaValorantecedente() != null) {
        nuevo = dfa.getDfaValorantecedente();
      }

      if (dfaAntiguo != null) {

        if (dfaAntiguo.getDfaValorantecedente() != null) {
          valorAntiguo = dfaAntiguo.getDfaValorantecedente();
        }

        if (!valorAntiguo.toString().equals(nuevo.toString())) {

          // se realiza update
          lef.setCfgEventos(2);
          lef.setLefNombrecampo("DFA_VALORANTECEDENTE");
          lef.setLefValoranterior(apdto.getCA_CODIGOANTECEDENTE() + " " + valorAntiguo);
          lef.setLefValornuevo(apdto.getCA_CODIGOANTECEDENTE() + " " + nuevo);
          lefDAO.insertLogEventosFichas(lef);
        }

      } else {
        // aqui se realiza insert
        if (!nuevo.toString().equals("")) {
          lef.setCfgEventos(1);
          lef.setLefValornuevo(apdto.getCA_CODIGOANTECEDENTE() + " " + nuevo);
          lefDAO.insertLogEventosFichas(lef);
        }
      }

      // ********************************************************************************
      dfaDAO.insertAntecedentesTest(dfa);
    }

  }

  public CfgAntecedentesDAO getCfgaDAO() {
    return cfgaDAO;
  }

  public void setCfgaDAO(CfgAntecedentesDAO cfgaDAO) {
    this.cfgaDAO = cfgaDAO;
  }

  public DatosFichasAntecedentesDAO getDfaDAO() {
    return dfaDAO;
  }

  public void setDfaDAO(DatosFichasAntecedentesDAO dfaDAO) {
    this.dfaDAO = dfaDAO;
  }

  @Override
  public DatosPacienteDTO getDataById(BigDecimal idPaciente)
      throws ParseException, BiosLISDAOException, BiosLISDAONotFoundException {
    return datosPacientesDAO.getPacienteDTOById(idPaciente.intValue());
  }

  @Override
  public DatosPacienteDTO getPacienteDTOByPasaporte(String pasaportePaciente)
      throws BiosLISDAOException, ParseException, BiosLISDAONotFoundException {
    return datosPacientesDAO.getPacienteDTOByNroDocumento(pasaportePaciente);
  }

  @Override
  public List<DatosPacientes> getByFechaCreacionTipoDoc(Timestamp fechaCreacion, Integer tipoDoc)
      throws BiosLISDAOException {
    return datosPacientesDAO.getByFechaCreacionTipoDoc(fechaCreacion, tipoDoc);
  }

  @Override
  public List<DatosPacientes> getPacienteByNombreApellidoTipoDoc(String nombre, String primerApellido,
      String segundoApellido, Integer tipoDoc) throws BiosLISDAOException {
    return datosPacientesDAO.getPacienteByNombreApellidoTipoDoc(nombre, primerApellido, segundoApellido, tipoDoc);
  }

  @Override
  public DatosPacienteDTO getPacienteDTOByOrden(Long nOrden) {
    DatosPacienteDTO dpDto = null;
    try {
      dpDto = datosPacientesDAO.getPacienteDTOByOrden(nOrden);
      CfgTipoAtencionDAO ctaDao = new CfgTipoAtencionDAO();
      CfgTipoatencion tipoAtencion = ctaDao.getTipoAtencionByNroOrden(nOrden);
      dpDto.setTipoAtencion(tipoAtencion);
      CfgProcedencias procedencia = cfgProcedenciasDAO.getProcedenciasByNroOrden(nOrden);
      dpDto.setProcedencia(procedencia.getCpDescripcion());
      CfgServicios servicio = servicios_dao.getServiciosLocalizacionByNroOrden(nOrden);
      dpDto.setServicio(servicio.getCsDescripcion());

    } catch (ParseException ex) {
      java.util.logging.Logger.getLogger(PacienteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    } catch (BiosLISDAOException ex) {
      java.util.logging.Logger.getLogger(PacienteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    } catch (BiosLISDAONotFoundException ex) {
      java.util.logging.Logger.getLogger(PacienteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return dpDto;
  }

  @Override
  public DatosPacienteDTO getPacienteOrdenesByRut(String rutPaciente)
      throws BiosLISDAOException, BiosLISDAONotFoundException {

    DatosPacienteDTO dpto = new DatosPacienteDTO();

    dpto.setDp(datosPacientesDAO.getPacienteByRut(rutPaciente));
    dpto.setListaPatologias(dppDAO.getPatologiasxPacientesList(dpto.getDp()));

    dpto.setListaOrdenesDelDia(ordenService.selectOrdenxRangoFechas(LocalDate.now(), LocalDate.now().plusDays(1),
        dpto.getDp().getDpIdpaciente()));

    return dpto;
  }

  public CfgProcedenciasDAO getCfgProcedenciasDAO() {
    return cfgProcedenciasDAO;
  }

  public void setCfgProcedenciasDAO(CfgProcedenciasDAO cfgProcedenciasDAO) {
    this.cfgProcedenciasDAO = cfgProcedenciasDAO;
  }

  public CfgServiciosDAO getServicios_dao() {
    return servicios_dao;
  }

  public void setServicios_dao(CfgServiciosDAO servicios_dao) {
    this.servicios_dao = servicios_dao;
  }

  public DatosFichasDAO getDatosFichasDAO() {
    return datosFichasDAO;
  }

  public void setDatosFichasDAO(DatosFichasDAO datosFichasDAO) {
    this.datosFichasDAO = datosFichasDAO;
  }

  public OrdenService getOrdenService() {
    return ordenService;
  }

  public void setOrdenService(OrdenService ordenService) {
    this.ordenService = ordenService;
  }

  public void insertPacientenew(DatosPacientes dp, DatosContactos dc, String[] patologias,
      String[] observacionesPatologias, String rutMadre, Long idUsuario) throws BiosLISDAOException {

    try {

      // Definir tipo de documento asociado que se crea.
      // RUT
      // Pasaporte
      // Recién nacido -Madre y nro de hijo
      // NN Fecha de ingreso

      // Es vip -> nombre social es importante

      // Es afromericano.

      // ¿Qué pasa si es menor de edad siempre contacto es obligatorio?

      // Estandarización informacion
      // Común
      // Obligatorios: Nombre - Primer Apellido - Sexo - Fecha Nacimiento Obligatorios
      // Opcionales: Segundo Apellido - Telefono - email - Región - Comuna - Dirección
      // - Código Postal

      // Datos contacto: Nombre - Relación - Telefono - Sexo

      // Información común

      // Información rn obligatoria

      // Información menor de edad obligatoria

      if (dp.getLdvTiposdocumentos().equals(Constante.LDVTD_RECIENNACIDO)) {
        datosPacientesDAO.insertDatosPacientesRN(dp, rutMadre);
        datosPacientesDAO.updateNroDocTipo(dp.getDpIdpaciente(), "RN");
      } else if (dp.getLdvTiposdocumentos().equals(Constante.LDVTD_SINIDENTIFICACION)) {
        datosPacientesDAO.insertDatosPacientesNN(dp);
        datosPacientesDAO.updateNroDocTipo(dp.getDpIdpaciente(), "NN");
      } else {
        datosPacientesDAO.insertDatosPacientes(dp);
      }

      int cont = 0;

      // PATOLOGIA

      if (patologias != null) {
        for (String codPatologia : patologias) {
          DatosPacientespatologiasDAO dppDAO = new DatosPacientespatologiasDAO();
          LdvPatologias patologia = new LdvPatologias();
          DatosPacientespatologias dpp = new DatosPacientespatologias();
          patologia.setLdvpatIdpatologia(Integer.parseInt(codPatologia));
          dpp.setDatosPacientes(dp);
          dpp.setLdvPatologias(patologia);
          dpp.setDppObservacion(observacionesPatologias[cont]);
          dppDAO.insertDatosPacientesPatologias(dpp);
          cont++;
        }
      }

      // CONTACTO PACIENTE

      if (dc.getDcNombre() != null && !dc.getDcNombre().trim().isEmpty()) {

        DatosContactosDAO dcDAO = new DatosContactosDAO();
        dc.setDatosPacientes(dp);
        dc.setDcNombre(dc.getDcNombre().toUpperCase());
        dcDAO.insertDatosContactos(dc);
      }

      // log paciente
      LogPacientesDAO logPaciente = new LogPacientesDAO();
      logPaciente.insertLogPaciente("", "", "", 1, idUsuario.intValue(), "nombreEquipo", dp, "000.000.000");
    } catch (BiosLISDAOException e) {
      logger.error(e.getMessage());

      throw e;
    }

  }

  @Override
  public List<OrdenExamenValidoDTO> getOrdenesConExamenesValidosDeUnPaciente(Integer idPac) throws BiosLISDAOException {

    return datosPacientesDAO.getOrdenesConExamenesValidosDeUnPaciente(idPac);

  }

  @Override
  public List<ExamenValidoDTO> getExamenesValidosDeUnPaciente(Integer idPac) throws BiosLISDAOException {

    return datosPacientesDAO.getExamenesValidosDeUnPaciente(idPac);

  }

  @Override
  public DatosPacienteDTO getPacienteOrdenesByPasaporte(String pasaporte)
      throws BiosLISDAOException, BiosLISDAONotFoundException {
    DatosPacienteDTO dpto = new DatosPacienteDTO();

    dpto.setDp(datosPacientesDAO.getPacienteByPasaporte(pasaporte));
    dpto.setListaPatologias(dppDAO.getPatologiasxPacientesList(dpto.getDp()));
    dpto.setListaOrdenesDelDia(ordenService.selectOrdenxRangoFechas(LocalDate.now(), LocalDate.now().plusDays(1),
        dpto.getDp().getDpIdpaciente()));

    return dpto;
  }

  @Override
  public List<DatosPacientes> getPacienteByIdMadre(int dpIdpaciente) throws BiosLISDAOException {
    return datosPacientesDAO.getPacienteByIdMadre(dpIdpaciente);
  }

  @Override
  public Object getDatosPacientesExiste(String rutPac, Long nOrden, Long idDocumento) throws BiosLISDAOException {
    return this.datosPacientesDAO.getDatosPacientesExiste(rutPac, nOrden, idDocumento);
  }

  @Override
  public DeltaCheckAptoDTO getDeltaCheckTestPaciente(Long idPaciente, Long idTest, Long nOrden)
      throws BiosLISDAOException {

    List<DeltaCheckAptoDTO> lstAptos = this.datosPacientesDAO.getDeltaCheckTestAptosPaciente(idPaciente, idTest,
        nOrden);

    if (lstAptos.isEmpty())
      return null; // REvisar este curso

    Integer n = lstAptos.get(0).getCT_DELTACANTIDAD().intValueExact();
    Double deltaPctje = lstAptos.get(0).getCT_DELTAPORCENTAJE().doubleValue();
    Double promedio = Double.valueOf(0);
    Double min = Double.valueOf(0);
    Double max = Double.valueOf(0);

    for (DeltaCheckAptoDTO dc : lstAptos) {
      Double aux = dc.getDFET_RESULTADONUMERICO().doubleValue();
      if (aux < min) {
        min = aux;
      }
      if (aux > max) {
        max = aux;
      }
      promedio += aux;
    }
    promedio = promedio / n;

    DeltaCheckAptoDTO dcDto = new DeltaCheckAptoDTO();

    BigDecimal bd = new BigDecimal(deltaPctje);

    dcDto.setCT_DELTACANTIDAD(new BigDecimal(n));
    dcDto.setCT_DELTAPORCENTAJE(new BigDecimal(deltaPctje));
    dcDto.setPROMEDIO(new BigDecimal(promedio));
    dcDto.setVALORMAX(new BigDecimal(max));
    dcDto.setVALORMIN(new BigDecimal(min));

    return dcDto;
  }

  @Override
  public PortalDatosPacienteOrdenExamenDTO getdatosPacientesOrden(String rut, Long nOrden, int tDocumento,
      int tBusqueda) throws BiosLISDAOException {
    // TODO Auto-generated method stub
    return this.datosPacientesDAO.getdatosPacientesOrden(rut, nOrden, tDocumento, tBusqueda);
  }

  @Override
  public String getDatosLoginUsuario(Long idUsuario) throws BiosLISDAOException {
    return this.datosPacientesDAO.getDatosLoginUsuario(idUsuario);
  }

  @Override
  public DatosEditarOrdenDTO getPacienteMedicoExamenesPatologias(Long idPaciente, Long nOrden)
      throws BiosLISDAOException {
    return this.datosPacientesDAO.getPacienteMedicoExamenesPatologias(idPaciente, nOrden);
  }

  @Override
  public List<LogPacientesDTO> getEventosPaciente(Long idPaciente) throws BiosLISDAOException {

    return datosPacientesDAO.getEventosPaciente(idPaciente);
  }

}
