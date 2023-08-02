package com.grupobios.bioslis.common;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.LdvComunas;
import com.grupobios.bioslis.entity.LdvEstadosciviles;
import com.grupobios.bioslis.entity.LdvPaises;
import com.grupobios.bioslis.entity.LdvRegiones;
import com.grupobios.bioslis.entity.LdvTiposdocumentos;
import com.grupobios.common.DateFormatterHelper;

public class DatosPacientesHelper {

  private static final Logger logger = LogManager.getLogger(DatosPacientesHelper.class);

  private DatosPacientesHelper() {
  }

  public static void estandarizaPaciente(DatosPacientes dp, String fechaNacimiento, String esPartoMultiple,
      LdvTiposdocumentos tipoDocumento, LdvEstadosciviles estadoCivil, LdvPaises pais, LdvRegiones region,
      LdvComunas comuna, String exitus) throws ParseException {

    estandarizaNombresPaciente(dp);

    if (dp.getDpDireccion() != null && !dp.getDpDireccion().trim().isEmpty()) {
      dp.setDpDireccion(dp.getDpDireccion().toUpperCase());
    }

    logger.debug("Paciente nombre:{} ap  {} am {}", dp.getDpNombresB(), dp.getDpPrimerapellidoB(),
        dp.getDpSegundoapellidoB());

    if (fechaNacimiento == null || fechaNacimiento.trim().equals("")) {
      dp.setDpFnacimiento(null);
    } else {
      dp.setDpFnacimiento((Date) DateFormatterHelper.textISO8601DateToDate(fechaNacimiento));
    }

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

    if (!dp.getDpSegundoapellido().trim().isEmpty()) {
      dp.setDpSegundoapellido(dp.getDpSegundoapellido().toUpperCase());
    }

    if (estadoCivil.getLdvecIdestadocivil() != 0) {
      dp.setLdvEstadosciviles(estadoCivil.getLdvecIdestadocivil());
    }
    if (pais.getLdvpIdpais() != 0) {
      dp.setLdvPaises(pais.getLdvpIdpais());
    }
    if (region.getLdvrIdregion() != 0) {
      dp.setLdvRegiones(region.getLdvrIdregion());
    }
    if (comuna.getLdvcIdcomuna() != 0) {
      dp.setLdvComunas(comuna.getLdvcIdcomuna());
    }

    if (dp.getDpRun() != null && !dp.getDpRun().trim().isEmpty()) {
      dp.setDpNrodocumento(dp.getDpRun());
    }

    if (esPartoMultiple != null && esPartoMultiple.equals("S")) {

      dp.setDpRnpartomultiple("S");
    }

    String nombreCodificado;
    try {
      nombreCodificado = getNombreencriptado(dp);
    } catch (BiosLISException e) {
      logger.error(e.getMessage());
      nombreCodificado = "###########";
    }
    dp.setDpNombreencriptado(nombreCodificado);

  }

  public static String getNombreencriptado(DatosPacientes dp) throws BiosLISException {

    String fechaCodificada = "";

    String rut4digitos = "";

    switch (dp.getLdvTiposdocumentos()) {

    case Constante.LDVTD_RUN:
      if (dp.getDpNrodocumento() == null || dp.getDpNrodocumento().trim().isEmpty()) {
        throw new BiosLISException("No se encontró nro. de documento.");
      }

      if (dp.getDpNrodocumento().length() >= 5) {
        rut4digitos = dp.getDpNrodocumento().substring(dp.getDpNrodocumento().length() - 5,
            dp.getDpNrodocumento().length());
      } else {
        rut4digitos = dp.getDpNrodocumento();
      }
      break;
    case Constante.LDVTD_PASAPORTE:
      rut4digitos = Constante.DIGITOS_ENC_PASAPORTE;
      break;
    case Constante.LDVTD_RECIENNACIDO:
      rut4digitos = Constante.DIGITOS_ENC_RN;
      break;
    case Constante.LDVTD_SINIDENTIFICACION:
      rut4digitos = Constante.DIGITOS_ENC_NN;
      break;
    default:
      throw new BiosLISException("No se encontró tipo de documento.");

    }

    if (!dp.getLdvTiposdocumentos().equals(Constante.LDVTD_SINIDENTIFICACION)) {
      fechaCodificada = DateFormatterHelper.dateToText(dp.getDpFnacimiento());
    } else {
      fechaCodificada = DateFormatterHelper.dateToText(Timestamp.valueOf(LocalDateTime.now()));
    }
    fechaCodificada = fechaCodificada.substring(0, 2) + fechaCodificada.substring(3, 5)
        + fechaCodificada.substring(8, 10);
    logger.debug("fechaCodificada --> {}", fechaCodificada);
    String segundoApellidoVip = dp.getDpSegundoapellido().trim();
    if (segundoApellidoVip.equals("")) {
      segundoApellidoVip = "#";
    }

    String nombreCodificado = dp.getDpNombres().substring(0, 1) + dp.getDpPrimerapellido().substring(0, 1)
        + segundoApellidoVip.substring(0, 1) + fechaCodificada + "" + rut4digitos;
    dp.setDpNombreencriptado(nombreCodificado);

    return nombreCodificado;
  }

  public static Boolean checkObligatoriosDatosPacientes(DatosPacientes dp, String rutMadre, String esPartoMultiple)
      throws BiosLISException {

    Boolean isNOK = false;

    if (dp.getLdvTiposdocumentos() == null)
      return isNOK;

    // Estandarización informacion
    // Común
    // Obligatorios: Nombre - Primer Apellido - Sexo - Fecha Nacimiento Obligatorios

    isNOK = dp.getDpNombres() == null || dp.getDpNombres().trim().isEmpty() || dp.getDpFnacimiento() == null
        || dp.getLdvSexo() == null || dp.getDpPrimerapellido() == null || dp.getDpPrimerapellido().trim().isEmpty();

    logger.debug("Mínimos ".concat(isNOK.toString()));// Recién nacido debe tener madre

    if (dp.getLdvTiposdocumentos().equals(Constante.LDVTD_RECIENNACIDO)) {
      logger.debug("Es rn --> ".concat(isNOK.toString()).concat(" <--"));// Recién nacido debe tener madre
      isNOK = isNOK || (dp.getDpNrodocumento() != null && !dp.getDpNrodocumento().trim().isEmpty())
          || (dp.getDpRun() != null && !dp.getDpRun().trim().isEmpty());
      logger.debug("Nro y RUN".concat(isNOK.toString()));// Recién nacido debe tener madre
      isNOK = isNOK || rutMadre.trim().isEmpty();
      logger.debug("Rut madre".concat(isNOK.toString()));// Recién nacido debe tener madre
      if (esPartoMultiple != null && esPartoMultiple.equals("S")) { // Si es parto
        // hijo. Falt chequear el orden si es parto
        // múltiple
        isNOK = isNOK || !(dp.getDpRnnumero() != null && dp.getDpRnnumero() > 0);
        logger.debug("Caso multiple".concat(isNOK.toString()));// Recién nacido debe tener madre
      }

    } else if (dp.getLdvTiposdocumentos().equals(Constante.LDVTD_SINIDENTIFICACION)) {
      isNOK = isNOK || ((dp.getDpNrodocumento() != null && !dp.getDpNrodocumento().trim().isEmpty())
          && (dp.getDpRun() != null && !dp.getDpRun().trim().isEmpty()));
    }

    if (isNOK) {
      logger.error("Datos obligatorios de paciente incompletos: {}", dp);
      throw new BiosLISException("Datos obligatorios de paciente incompletos");
    } else {
      return true;
    }

  }

  public static void estandarizaNombresPaciente(DatosPacientes dp) {

    String nombresB = StringUtils.stripAccents(dp.getDpNombres()).toUpperCase().trim().replace("Ñ", "N").replace('-',
        ' ');
    String primerApellidoB = StringUtils.stripAccents(dp.getDpPrimerapellido()).toUpperCase().trim().replace("Ñ", "N")
        .replace('-', ' ');

    dp.setDpNombresB(nombresB);
    dp.setDpPrimerapellidoB(primerApellidoB);

    if (dp.getDpSegundoapellido() != null && !dp.getDpSegundoapellido().trim().isEmpty()) {
      String segundoApellidoB = StringUtils.stripAccents(dp.getDpSegundoapellido()).toUpperCase().trim()
          .replace("Ñ", "N").replace('-', ' ');
      dp.setDpSegundoapellidoB(segundoApellidoB);
    }
    dp.setDpNombres(dp.getDpNombres().toUpperCase());
    dp.setDpPrimerapellido(dp.getDpPrimerapellido().toUpperCase());

    logger.debug("Paciente nombre:{} ap  {} am {}", dp.getDpNombresB(), dp.getDpPrimerapellidoB(),
        dp.getDpSegundoapellidoB());

    if (!dp.getDpSegundoapellido().trim().isEmpty()) {
      dp.setDpSegundoapellido(dp.getDpSegundoapellido().toUpperCase());
    }
  }

}
