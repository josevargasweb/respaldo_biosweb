package com.grupobios.bioslis.entity;
// Generated 30-03-2020 9:50:08 by Hibernate Tools 4.3.1

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Objects;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DatosPacientes generated by hbm2java
 */
@JsonInclude(value = Include.NON_NULL)
public class DatosPacientes {

  private int dpIdpaciente;
  private Integer dpIdmadre;
  private Integer ldvComunas;
  private Integer ldvTiposdocumentos;
  private Integer ldvEstadosciviles;
  private Integer ldvSexo;
  private Integer ldvPaises;
  private Integer ldvRegiones;
  private String dpNombres;
  private String dpPrimerapellido;
  private String dpSegundoapellido;
  private String dpNombresocial;
  private String dpRun;
  private String dpNrodocumento;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "GMT-4")
  private Date dpFnacimiento;
  private String dpDireccion;
  private String dpDireccodpostal;
  private String dpTelefono;
  private String dpEmail;
  private String dpObservacion;
  private String dpExitus;
  private String dpAbo;
  private String dpRh;
  private String dpReciennacido;
  private String dpRnpartomultiple;
  private Integer dpRnnumero;
  private String dpEsvip;
  private String dpEsafroamericano;
  private String dpNombreencriptado;
  private String dpCampolibre1;
  private String dpCampolibre2;
  private String dpNombresB;
  private String dpPrimerapellidoB;
  private String dpSegundoapellidoB;
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
  private Timestamp dpFechacreacion;

  public Timestamp getDpFechacreacion() {
    return dpFechacreacion;
  }

  public void setDpFechacreacion(Timestamp dpFechacreacion) {
    this.dpFechacreacion = dpFechacreacion;
  }

  @Transient
  private String ipEquipo;

  public DatosPacientes() {
  }

  public int getDpIdpaciente() {
    return dpIdpaciente;
  }

  public void setDpIdpaciente(int dpIdpaciente) {
    this.dpIdpaciente = dpIdpaciente;
  }

  public Integer getLdvComunas() {
    return ldvComunas;
  }

  public void setLdvComunas(Integer ldvComunas) {
    this.ldvComunas = ldvComunas;
  }

  public Integer getLdvTiposdocumentos() {
    return ldvTiposdocumentos;
  }

  public void setLdvTiposdocumentos(Integer ldvTiposdocumentos) {
    this.ldvTiposdocumentos = ldvTiposdocumentos;
  }

  public Integer getLdvEstadosciviles() {
    return ldvEstadosciviles;
  }

  public void setLdvEstadosciviles(Integer ldvEstadosciviles) {
    this.ldvEstadosciviles = ldvEstadosciviles;
  }

  public Integer getLdvSexo() {
    return ldvSexo;
  }

  public void setLdvSexo(Integer ldvSexo) {
    this.ldvSexo = ldvSexo;
  }

  public Integer getLdvPaises() {
    return ldvPaises;
  }

  public void setLdvPaises(Integer ldvPaises) {
    this.ldvPaises = ldvPaises;
  }

  public Integer getLdvRegiones() {
    return ldvRegiones;
  }

  public void setLdvRegiones(Integer ldvRegiones) {
    this.ldvRegiones = ldvRegiones;
  }

  public String getDpNombres() {
    return this.dpNombres;
  }

  public void setDpNombres(String dpNombres) {
    this.dpNombres = dpNombres;
  }

  public String getDpPrimerapellido() {
    return this.dpPrimerapellido;
  }

  public void setDpPrimerapellido(String dpPrimerapellido) {
    this.dpPrimerapellido = dpPrimerapellido;
  }

  public String getDpSegundoapellido() {
    return this.dpSegundoapellido;
  }

  public void setDpSegundoapellido(String dpSegundoapellido) {
    this.dpSegundoapellido = dpSegundoapellido;
  }

  public String getDpNombresocial() {
    return this.dpNombresocial;
  }

  public void setDpNombresocial(String dpNombresocial) {
    this.dpNombresocial = dpNombresocial;
  }

  public String getDpRun() {
    return this.dpRun;
  }

  public void setDpRun(String dpRun) {
    this.dpRun = dpRun;
  }

  public String getDpNrodocumento() {
    return this.dpNrodocumento;
  }

  public void setDpNrodocumento(String dpNrodocumento) {
    this.dpNrodocumento = dpNrodocumento;
  }

  public Date getDpFnacimiento() {
    return dpFnacimiento;
  }

  public void setDpFnacimiento(Date dpFnacimiento) {
    this.dpFnacimiento = dpFnacimiento;
  }

//  public void setDpFnacimiento(String dpFnacimiento) {
//    if (dpFnacimiento != null && dpFnacimiento.equals("")) {
//      this.dpFnacimiento = null;
//    } else {
//      this.dpFnacimiento = DateFormatterHelper.textDateToSqlDate(dpFnacimiento);
//    }
//
//  }

  public String getDpDireccion() {
    return this.dpDireccion;
  }

  public void setDpDireccion(String dpDireccion) {
    this.dpDireccion = dpDireccion;
  }

  public String getDpDireccodpostal() {
    return this.dpDireccodpostal;
  }

  public void setDpDireccodpostal(String dpDireccodpostal) {
    this.dpDireccodpostal = dpDireccodpostal;
  }

  public String getDpTelefono() {
    return this.dpTelefono;
  }

  public void setDpTelefono(String dpTelefono) {
    this.dpTelefono = dpTelefono;
  }

  public String getDpEmail() {
    return this.dpEmail;
  }

  public void setDpEmail(String dpEmail) {
    this.dpEmail = dpEmail;
  }

  public String getDpObservacion() {
    return this.dpObservacion;
  }

  public void setDpObservacion(String dpObservacion) {
    this.dpObservacion = dpObservacion;
  }

  public String getDpExitus() {
    return this.dpExitus;
  }

  public void setDpExitus(String dpExitus) {
    this.dpExitus = dpExitus;
  }

  public String getDpAbo() {
    return this.dpAbo;
  }

  public void setDpAbo(String dpAbo) {
    this.dpAbo = dpAbo;
  }

  public String getDpRh() {
    return this.dpRh;
  }

  public void setDpRh(String dpRh) {
    this.dpRh = dpRh;
  }

  public String getDpReciennacido() {
    return this.dpReciennacido;
  }

  public void setDpReciennacido(String dpReciennacido) {
    this.dpReciennacido = dpReciennacido;
  }

  public String getDpRnpartomultiple() {
    return this.dpRnpartomultiple;
  }

  public void setDpRnpartomultiple(String dpRnpartomultiple) {
    this.dpRnpartomultiple = dpRnpartomultiple;
  }

  public Integer getDpRnnumero() {
    return dpRnnumero;
  }

  public void setDpRnnumero(Integer dpRnnumero) {
    this.dpRnnumero = dpRnnumero;
  }

  public String getDpEsvip() {
    return this.dpEsvip;
  }

  public void setDpEsvip(String dpEsvip) {
    this.dpEsvip = dpEsvip;
  }

  public String getDpEsafroamericano() {
    return this.dpEsafroamericano;
  }

  public void setDpEsafroamericano(String dpEsafroamericano) {
    this.dpEsafroamericano = dpEsafroamericano;
  }

  public String getDpNombreencriptado() {
    return this.dpNombreencriptado;
  }

  public void setDpNombreencriptado(String dpNombreencriptado) {
    this.dpNombreencriptado = dpNombreencriptado;
  }

  public String getDpCampolibre1() {
    return this.dpCampolibre1;
  }

  public void setDpCampolibre1(String dpCampolibre1) {
    this.dpCampolibre1 = dpCampolibre1;
  }

  public String getDpCampolibre2() {
    return this.dpCampolibre2;
  }

  public void setDpCampolibre2(String dpCampolibre2) {
    this.dpCampolibre2 = dpCampolibre2;
  }

  public String getDpNombresB() {
    return dpNombresB;
  }

  public void setDpNombresB(String dpNombresB) {
    this.dpNombresB = dpNombresB;
  }

  public String getDpPrimerapellidoB() {
    return dpPrimerapellidoB;
  }

  public void setDpPrimerapellidoB(String dpPrimerapellidoB) {
    this.dpPrimerapellidoB = dpPrimerapellidoB;
  }

  public String getDpSegundoapellidoB() {
    return dpSegundoapellidoB;
  }

  public void setDpSegundoapellidoB(String dpSegundoapellidoB) {
    this.dpSegundoapellidoB = dpSegundoapellidoB;
  }

  public Integer getDpIdmadre() {
    return dpIdmadre;
  }

  public void setDpIdmadre(Integer dpIdmadre) {
    this.dpIdmadre = dpIdmadre;
  }
  /*
   * @Override public String toString() { return "DatosPacientes [dpIdpaciente=" +
   * dpIdpaciente + "]"; }
   */

  @Override
  public String toString() {
    return "DatosPacientes [dpIdpaciente=" + dpIdpaciente + ", dpIdmadre=" + dpIdmadre + ", ldvSexo=" + ldvSexo
        + ", dpNombres=" + dpNombres + ", dpPrimerapellido=" + dpPrimerapellido + ", dpNrodocumento=" + dpNrodocumento
        + ", dpFnacimiento=" + dpFnacimiento + ", dpExitus=" + dpExitus + ", dpReciennacido=" + dpReciennacido
        + ", dpEsvip=" + dpEsvip + ", dpEsafroamericano=" + dpEsafroamericano + ", dpNombreencriptado="
        + dpNombreencriptado + ", dpNombresB=" + dpNombresB + ", dpPrimerapellidoB=" + dpPrimerapellidoB
        + ", dpFechacreacion=" + dpFechacreacion + ", dpRun=" + dpRun + ", dpRnnumero=" + dpRnnumero
        + ", dpRnpartomultiple=" + dpRnpartomultiple + "]";
  }

  public String getIpEquipo() {
    return ipEquipo;
  }

  public void setIpEquipo(String ipEquipo) {
    this.ipEquipo = ipEquipo;
  }

  @Transient
  @JsonIgnore
  public String getEdad() {
    java.util.Date d = new Date(this.getDpFnacimiento().getTime());
    LocalDate from = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate to = LocalDate.now();
    Period period = Period.between(from, to);
    return period.toString();

  }

  @Override
  public int hashCode() {
    return Objects.hash(dpAbo, dpCampolibre1, dpCampolibre2, dpDireccion, dpDireccodpostal, dpEmail, dpEsafroamericano,
        dpEsvip, dpExitus, dpFechacreacion, dpFnacimiento, dpIdmadre, dpIdpaciente, dpNombreencriptado, dpNombres,
        dpNombresB, dpNombresocial, dpNrodocumento, dpObservacion, dpPrimerapellido, dpPrimerapellidoB, dpReciennacido,
        dpRh, dpRnnumero, dpRnpartomultiple, dpRun, dpSegundoapellido, dpSegundoapellidoB, dpTelefono, ipEquipo,
        ldvComunas, ldvEstadosciviles, ldvPaises, ldvRegiones, ldvSexo, ldvTiposdocumentos);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DatosPacientes other = (DatosPacientes) obj;
    return Objects.equals(dpAbo, other.dpAbo) && Objects.equals(dpCampolibre1, other.dpCampolibre1)
        && Objects.equals(dpCampolibre2, other.dpCampolibre2) && Objects.equals(dpDireccion, other.dpDireccion)
        && Objects.equals(dpDireccodpostal, other.dpDireccodpostal) && Objects.equals(dpEmail, other.dpEmail)
        && Objects.equals(dpEsafroamericano, other.dpEsafroamericano) && Objects.equals(dpEsvip, other.dpEsvip)
        && Objects.equals(dpExitus, other.dpExitus) && Objects.equals(dpFechacreacion, other.dpFechacreacion)
        && Objects.equals(dpFnacimiento, other.dpFnacimiento) && Objects.equals(dpIdmadre, other.dpIdmadre)
        && dpIdpaciente == other.dpIdpaciente && Objects.equals(dpNombreencriptado, other.dpNombreencriptado)
        && Objects.equals(dpNombres, other.dpNombres) && Objects.equals(dpNombresB, other.dpNombresB)
        && Objects.equals(dpNombresocial, other.dpNombresocial) && Objects.equals(dpNrodocumento, other.dpNrodocumento)
        && Objects.equals(dpObservacion, other.dpObservacion)
        && Objects.equals(dpPrimerapellido, other.dpPrimerapellido)
        && Objects.equals(dpPrimerapellidoB, other.dpPrimerapellidoB)
        && Objects.equals(dpReciennacido, other.dpReciennacido) && Objects.equals(dpRh, other.dpRh)
        && Objects.equals(dpRnnumero, other.dpRnnumero) && Objects.equals(dpRnpartomultiple, other.dpRnpartomultiple)
        && Objects.equals(dpRun, other.dpRun) && Objects.equals(dpSegundoapellido, other.dpSegundoapellido)
        && Objects.equals(dpSegundoapellidoB, other.dpSegundoapellidoB) && Objects.equals(dpTelefono, other.dpTelefono)
        && Objects.equals(ipEquipo, other.ipEquipo) && Objects.equals(ldvComunas, other.ldvComunas)
        && Objects.equals(ldvEstadosciviles, other.ldvEstadosciviles) && Objects.equals(ldvPaises, other.ldvPaises)
        && Objects.equals(ldvRegiones, other.ldvRegiones) && Objects.equals(ldvSexo, other.ldvSexo)
        && Objects.equals(ldvTiposdocumentos, other.ldvTiposdocumentos);
  }

  public void copyChanges(DatosPacientes other) {

    if (!(Objects.equals(dpAbo, other.dpAbo))) {
      dpAbo = other.dpAbo;
    }
    if (!(Objects.equals(dpCampolibre1, other.dpCampolibre1))) {
      dpCampolibre1 = other.dpCampolibre1;
    }

    if (!(Objects.equals(dpCampolibre2, other.dpCampolibre2))) {
      dpCampolibre2 = other.dpCampolibre2;
    }

    if (!(Objects.equals(dpDireccion, other.dpDireccion))) {
      dpDireccion = other.dpDireccion;
    }

    if (!(Objects.equals(dpDireccodpostal, other.dpDireccodpostal))) {
      dpDireccodpostal = other.dpDireccodpostal;
    }

    if (!(Objects.equals(dpEmail, other.dpEmail))) {
      dpEmail = other.dpEmail;
    }

    if (!(Objects.equals(dpEsafroamericano, other.dpEsafroamericano))) {
      dpEsafroamericano = other.dpEsafroamericano;
    }

    if (!(Objects.equals(dpEsvip, other.dpEsvip))) {
      dpEsvip = other.dpEsvip;
    }

    if (!(Objects.equals(dpExitus, other.dpExitus))) {
      dpExitus = other.dpExitus;
    }

    if (other.dpFechacreacion != null && !(Objects.equals(dpFechacreacion, other.dpFechacreacion))) {
      dpFechacreacion = other.dpFechacreacion;
    }

    if (!(Objects.equals(dpFnacimiento, other.dpFnacimiento))) {
      dpFnacimiento = other.dpFnacimiento;
    }

    if (!(Objects.equals(dpIdmadre, other.dpIdmadre))) {
      dpIdmadre = other.dpIdmadre;
    }

    if (!(Objects.equals(dpNombreencriptado, other.dpNombreencriptado))) {
      dpNombreencriptado = other.dpNombreencriptado;
    }

    if (!(Objects.equals(dpNombres, other.dpNombres))) {
      dpNombres = other.dpNombres;
    }

    if (!(Objects.equals(dpNombresB, other.dpNombresB))) {
      dpNombresB = other.dpNombresB;
    }

    if (!(Objects.equals(dpNombresocial, other.dpNombresocial))) {
      dpNombresocial = other.dpNombresocial;
    }

//    if (!(Objects.equals(dpNrodocumento, other.dpNrodocumento))) {
//      dpNrodocumento = other.dpNrodocumento;
//    }

    if (!(Objects.equals(dpObservacion, other.dpObservacion))) {
      dpObservacion = other.dpObservacion;
    }

    if (!(Objects.equals(dpPrimerapellido, other.dpPrimerapellido))) {
      dpPrimerapellido = other.dpPrimerapellido;
    }

    if (!(Objects.equals(dpPrimerapellidoB, other.dpPrimerapellidoB))) {
      dpPrimerapellidoB = other.dpPrimerapellidoB;
    }

    if (!(Objects.equals(dpReciennacido, other.dpReciennacido))) {
      dpReciennacido = other.dpReciennacido;
    }

    if (!(Objects.equals(dpRh, other.dpRh))) {
      dpRh = other.dpRh;
    }

    if (!(Objects.equals(dpRnnumero, other.dpRnnumero))) {
      dpRnnumero = other.dpRnnumero;
    }

    if (!(Objects.equals(dpRnpartomultiple, other.dpRnpartomultiple))) {
      dpRnpartomultiple = other.dpRnpartomultiple;
    }

//    if (!(Objects.equals(dpRun, other.dpRun))) {
//      dpRun = other.dpRun;
//    }

    if (!(Objects.equals(dpSegundoapellido, other.dpSegundoapellido))) {
      dpSegundoapellido = other.dpSegundoapellido;
    }

    if (!(Objects.equals(dpSegundoapellidoB, other.dpSegundoapellidoB))) {
      dpSegundoapellidoB = other.dpSegundoapellidoB;
    }

    if (!(Objects.equals(dpTelefono, other.dpTelefono))) {
      dpTelefono = other.dpTelefono;
    }

    if (!(Objects.equals(ipEquipo, other.ipEquipo))) {
      ipEquipo = other.ipEquipo;
    }

    if (!(Objects.equals(ldvComunas, other.ldvComunas))) {
      ldvComunas = other.ldvComunas;
    }

    if (!(Objects.equals(ldvEstadosciviles, other.ldvEstadosciviles))) {
      ldvEstadosciviles = other.ldvEstadosciviles;
    }

    if (!(Objects.equals(ldvPaises, other.ldvPaises))) {
      ldvPaises = other.ldvPaises;
    }

    if (!(Objects.equals(ldvRegiones, other.ldvRegiones))) {
      ldvRegiones = other.ldvRegiones;
    }

    if (!(Objects.equals(ldvSexo, other.ldvSexo))) {
      ldvSexo = other.ldvSexo;
    }

//    if (!(Objects.equals(ldvTiposdocumentos, other.ldvTiposdocumentos))) {
//      ldvTiposdocumentos = other.ldvTiposdocumentos;
//    }

  }

}