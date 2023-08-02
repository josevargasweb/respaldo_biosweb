/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dto.AntecedentePacienteDTO;
import com.grupobios.bioslis.dto.DatosEditarOrdenDTO;
import com.grupobios.bioslis.dto.DatosPacienteDTO;
import com.grupobios.bioslis.dto.DeltaCheckAptoDTO;
import com.grupobios.bioslis.dto.ExamenValidoDTO;
import com.grupobios.bioslis.dto.LogPacientesDTO;
import com.grupobios.bioslis.dto.OrdenExamenValidoDTO;
import com.grupobios.bioslis.dto.PatologiaDTO;
import com.grupobios.bioslis.dto.PortalDatosPacienteOrdenExamenDTO;
import com.grupobios.bioslis.entity.DatosContactos;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.LdvComunas;
import com.grupobios.bioslis.entity.LdvContactospacientesrelacion;
import com.grupobios.bioslis.entity.LdvEstadosciviles;
import com.grupobios.bioslis.entity.LdvPaises;
import com.grupobios.bioslis.entity.LdvRegiones;
import com.grupobios.bioslis.entity.LdvTiposdocumentos;

/**
 *
 * @author eric.nicholls
 */
public interface PacienteService {

  public List<PatologiaDTO> getPatologias(Long idPaciente) throws BiosLISDAOException;

  public void insertPaciente(DatosPacientes dp, DatosContactos dc, String[] patologias,
      String[] observacionesPatologias, String rutNombre, Long idUsuario) throws BiosLISDAOException;

  public DatosPacienteDTO getPacienteByRut(String rutPaciente) throws BiosLISDAOException, BiosLISDAONotFoundException;

  public List<AntecedentePacienteDTO> getAntecedentesPacienteOrden(BigDecimal nroOrden) throws BiosLISDAOException;

  public List<AntecedentePacienteDTO> getAntecedentesPaciente(Long nroOrden) throws BiosLISDAOException;

  public DatosPacienteDTO getDataRN(BigDecimal idPaciente)
      throws ParseException, BiosLISDAOException, BiosLISDAONotFoundException;

  public void udpatePaciente(DatosPacientes dp, LdvTiposdocumentos tipoDocumento, LdvEstadosciviles estadoCivil,
      LdvPaises pais, LdvRegiones region, LdvComunas comuna, LdvContactospacientesrelacion contactoPaciente,
      DatosContactos dc, HttpServletRequest request)
      throws BiosLISDAONotFoundException, BiosLISDAOException, ParseException;

  public void udpatePaciente(DatosPacienteDTO dp, DatosContactos dc, Long idUsuario)
      throws BiosLISDAONotFoundException, BiosLISDAOException, ParseException, BiosLISException;

  public void guardarAntecedentes(List<AntecedentePacienteDTO> listAntecedentes, Long idUsuario)
      throws BiosLISDAOException;

  public DatosPacienteDTO getDataById(BigDecimal idPaciente)
      throws ParseException, BiosLISDAOException, BiosLISDAONotFoundException;

  public DatosPacienteDTO getPacienteDTOByPasaporte(String pasaportePaciente)
      throws BiosLISDAOException, ParseException, BiosLISDAONotFoundException;

  public DatosPacienteDTO getPacienteDTOByOrden(Long nOrden);

  public List<DatosPacientes> getByFechaCreacionTipoDoc(Timestamp fechaCreacion, Integer tipoDoc)
      throws BiosLISDAOException;

  public List<DatosPacientes> getPacienteByNombreApellidoTipoDoc(String nombre, String primerApellido,
      String segundoApellido, Integer tipoDoc) throws BiosLISDAOException;

  public DatosPacienteDTO getPacienteOrdenesByRut(String upperCase)
      throws BiosLISDAOException, BiosLISDAONotFoundException;

  List<OrdenExamenValidoDTO> getOrdenesConExamenesValidosDeUnPaciente(Integer idPac) throws BiosLISDAOException;

  List<ExamenValidoDTO> getExamenesValidosDeUnPaciente(Integer idPac) throws BiosLISDAOException;

  public DatosPacienteDTO getPacienteOrdenesByPasaporte(String upperCase)
      throws BiosLISDAOException, BiosLISDAONotFoundException;

  public List<DatosPacientes> getPacienteByIdMadre(int dpIdpaciente) throws BiosLISDAOException;

  public DeltaCheckAptoDTO getDeltaCheckTestPaciente(Long idPaciente, Long idTest, Long nOrden)
      throws BiosLISDAOException;

  Object getDatosPacientesExiste(String rutPac, Long nOrden, Long idDocumento) throws BiosLISDAOException;

  PortalDatosPacienteOrdenExamenDTO getdatosPacientesOrden(String rut, Long nOrden, int tDocumento, int tBusqueda)
      throws BiosLISDAOException;

  public String getDatosLoginUsuario(Long idUsuario) throws BiosLISDAOException;

  DatosEditarOrdenDTO getPacienteMedicoExamenesPatologias(Long idPaciente, Long nOrden) throws BiosLISDAOException;

  public List<LogPacientesDTO> getEventosPaciente(Long idPaciente) throws BiosLISDAOException;;
}
