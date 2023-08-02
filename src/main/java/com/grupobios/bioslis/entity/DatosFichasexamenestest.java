package com.grupobios.bioslis.entity;
// Generated 22-04-2020 16:13:26 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * DatosFichasexamenestest generated by hbm2java
 */
public class DatosFichasexamenestest implements java.io.Serializable {

  private DatosFichasexamenestestId id;
  @JsonIgnore
  private DatosFichas datosFichas;
  @JsonIgnore
  private DatosFichasexamenes datosFichasexamenes;
  @JsonIgnore
  private CfgExamenestest cfgExamenestest;
  private long dfetIdpaciente;
  private Date dfetFechaorden;
  private Short dfetIdtipomuestra;
  private Short dfetIdenvase;
  private Short dfetIdseccion;
  private Long dfetIdmuestra;
  private String dfetResultado;
  private BigDecimal dfetResultadonumerico;
  private Short dfetIdestadoresultadotest;
  private String dfetRcritico;
  private Date dfetFechacreaciontest;
  private Long dfetIdusuariocreacion;
  private String dfetAnalizadorcargaresultado;
  private Date dfetFechaingresoresultado;
  private Long dfetIdusuariodigita;
  private Date dfetFechabloqueotest;
  private Long dfetIdusuariobloqueo;
  private String dfetReferenciadesde;
  private String dfetReferenciahasta;
  private String dfetReferenciatexto;
  private Short dfetIdunidaddemedida;
  private String dfetValorcriticonotificado;
  private String dfetNotavalorcritico;
  private Date dfetFechavalorcritico;
  private Long dfetIdusuarionotificacritico;
  private String dfetResultadoenviadohost;
  private String dfetValorcriticoanalizador;
  private String dfetNotificado;
  private String dfetValorcriticodesde;
  private String dfetValorcriticohasta;
  private String dfetTiponotificacion;
  private String dfetProfesionalnotificado;
  private Date dfetFechafirma;
  private Long dfetIdusuariofirma;
  private String dfetTestfirmado;
  private String dfetComentarionadie;
  private String dfetComentariootro;
  // agregado por cristian 04-11
  private BigDecimal dfetIdmetodo;
  // agregado por jan 12-22
  private BigDecimal dfetidbacestadotest;
  // agregado por cristian 20-02
  private String dfetUltimoresultadoant;
  private Date dfetfechaultimoresultado;

  private Long dfetidfichatestnotificacion;

  public Long getDfetidfichatestnotificacion() {
    return dfetidfichatestnotificacion;
  }

  public void setDfetidfichatestnotificacion(Long dfetidfichatestnotificacion) {
    this.dfetidfichatestnotificacion = dfetidfichatestnotificacion;
  }

  // AGREGADO A MANO, NO TRAE DESDE BD
  @Transient
  private String valoresReferencias;
  @Transient
  private String valorCriticoBajo;
  @Transient
  private String valorCriticoAlto;
  @Transient
  private String nombreExamen;
  @Transient
  private String CE_ABREVIADO;

  public DatosFichasexamenestest() {
  }

  public DatosFichasexamenestestId getId() {
    return this.id;
  }

  public void setId(DatosFichasexamenestestId id) {
    this.id = id;
  }

  public DatosFichas getDatosFichas() {
    return this.datosFichas;
  }

  public void setDatosFichas(DatosFichas datosFichas) {
    this.datosFichas = datosFichas;
  }

  public DatosFichasexamenes getDatosFichasexamenes() {
    return this.datosFichasexamenes;
  }

  public void setDatosFichasexamenes(DatosFichasexamenes datosFichasexamenes) {
    this.datosFichasexamenes = datosFichasexamenes;
  }

  public CfgExamenestest getCfgExamenestest() {
    return this.cfgExamenestest;
  }

  public void setCfgExamenestest(CfgExamenestest cfgExamenestest) {
    this.cfgExamenestest = cfgExamenestest;
  }

  public long getDfetIdpaciente() {
    return this.dfetIdpaciente;
  }

  public void setDfetIdpaciente(long dfetIdpaciente) {
    this.dfetIdpaciente = dfetIdpaciente;
  }

  public Date getDfetFechaorden() {
    return this.dfetFechaorden;
  }

  public void setDfetFechaorden(Date dfetFechaorden) {
    this.dfetFechaorden = dfetFechaorden;
  }

  public Short getDfetIdtipomuestra() {
    return this.dfetIdtipomuestra;
  }

  public void setDfetIdtipomuestra(Short dfetIdtipomuestra) {
    this.dfetIdtipomuestra = dfetIdtipomuestra;
  }

  public Short getDfetIdenvase() {
    return this.dfetIdenvase;
  }

  public void setDfetIdenvase(Short dfetIdenvase) {
    this.dfetIdenvase = dfetIdenvase;
  }

  public Short getDfetIdseccion() {
    return this.dfetIdseccion;
  }

  public void setDfetIdseccion(Short dfetIdseccion) {
    this.dfetIdseccion = dfetIdseccion;
  }

  public Long getDfetIdmuestra() {
    return this.dfetIdmuestra;
  }

  public void setDfetIdmuestra(Long dfetIdmuestra) {
    this.dfetIdmuestra = dfetIdmuestra;
  }

  public String getDfetResultado() {
    return this.dfetResultado;
  }

  public void setDfetResultado(String dfetResultado) {
    this.dfetResultado = dfetResultado;
  }

  public BigDecimal getDfetResultadonumerico() {
    return this.dfetResultadonumerico;
  }

  public void setDfetResultadonumerico(BigDecimal dfetResultadonumerico) {
    this.dfetResultadonumerico = dfetResultadonumerico;
  }

  public Short getDfetIdestadoresultadotest() {
    return this.dfetIdestadoresultadotest;
  }

  public void setDfetIdestadoresultadotest(Short dfetIdestadoresultadotest) {
    this.dfetIdestadoresultadotest = dfetIdestadoresultadotest;
  }

  public String getDfetRcritico() {
    return this.dfetRcritico;
  }

  public void setDfetRcritico(String dfetRcritico) {
    this.dfetRcritico = dfetRcritico;
  }

  public Date getDfetFechacreaciontest() {
    return this.dfetFechacreaciontest;
  }

  public void setDfetFechacreaciontest(Date dfetFechacreaciontest) {
    this.dfetFechacreaciontest = dfetFechacreaciontest;
  }

  public Long getDfetIdusuariocreacion() {
    return this.dfetIdusuariocreacion;
  }

  public void setDfetIdusuariocreacion(Long dfetIdusuariocreacion) {
    this.dfetIdusuariocreacion = dfetIdusuariocreacion;
  }

  public String getDfetAnalizadorcargaresultado() {
    return this.dfetAnalizadorcargaresultado;
  }

  public void setDfetAnalizadorcargaresultado(String dfetAnalizadorcargaresultado) {
    this.dfetAnalizadorcargaresultado = dfetAnalizadorcargaresultado;
  }

  public Date getDfetFechaingresoresultado() {
    return this.dfetFechaingresoresultado;
  }

  public void setDfetFechaingresoresultado(Date dfetFechaingresoresultado) {
    this.dfetFechaingresoresultado = dfetFechaingresoresultado;
  }

  public Long getDfetIdusuariodigita() {
    return this.dfetIdusuariodigita;
  }

  public void setDfetIdusuariodigita(Long dfetIdusuariodigita) {
    this.dfetIdusuariodigita = dfetIdusuariodigita;
  }

  public Date getDfetFechabloqueotest() {
    return this.dfetFechabloqueotest;
  }

  public void setDfetFechabloqueotest(Date dfetFechabloqueotest) {
    this.dfetFechabloqueotest = dfetFechabloqueotest;
  }

  public Long getDfetIdusuariobloqueo() {
    return this.dfetIdusuariobloqueo;
  }

  public void setDfetIdusuariobloqueo(Long dfetIdusuariobloqueo) {
    this.dfetIdusuariobloqueo = dfetIdusuariobloqueo;
  }

  public String getDfetReferenciadesde() {
    return this.dfetReferenciadesde;
  }

  public void setDfetReferenciadesde(String dfetReferenciadesde) {
    this.dfetReferenciadesde = dfetReferenciadesde;
  }

  public String getDfetReferenciahasta() {
    return this.dfetReferenciahasta;
  }

  public void setDfetReferenciahasta(String dfetReferenciahasta) {
    this.dfetReferenciahasta = dfetReferenciahasta;
  }

  public String getDfetReferenciatexto() {
    return this.dfetReferenciatexto;
  }

  public void setDfetReferenciatexto(String dfetReferenciatexto) {
    this.dfetReferenciatexto = dfetReferenciatexto;
  }

  public Short getDfetIdunidaddemedida() {
    return this.dfetIdunidaddemedida;
  }

  public void setDfetIdunidaddemedida(Short dfetIdunidaddemedida) {
    this.dfetIdunidaddemedida = dfetIdunidaddemedida;
  }

  public String getDfetValorcriticonotificado() {
    return this.dfetValorcriticonotificado;
  }

  public void setDfetValorcriticonotificado(String dfetValorcriticonotificado) {
    this.dfetValorcriticonotificado = dfetValorcriticonotificado;
  }

  public String getDfetNotavalorcritico() {
    return this.dfetNotavalorcritico;
  }

  public void setDfetNotavalorcritico(String dfetNotavalorcritico) {
    this.dfetNotavalorcritico = dfetNotavalorcritico;
  }

  public Date getDfetFechavalorcritico() {
    return this.dfetFechavalorcritico;
  }

  public void setDfetFechavalorcritico(Date dfetFechavalorcritico) {
    this.dfetFechavalorcritico = dfetFechavalorcritico;
  }

  public Long getDfetIdusuarionotificacritico() {
    return this.dfetIdusuarionotificacritico;
  }

  public void setDfetIdusuarionotificacritico(Long dfetIdusuarionotificacritico) {
    this.dfetIdusuarionotificacritico = dfetIdusuarionotificacritico;
  }

  public String getDfetResultadoenviadohost() {
    return this.dfetResultadoenviadohost;
  }

  public void setDfetResultadoenviadohost(String dfetResultadoenviadohost) {
    this.dfetResultadoenviadohost = dfetResultadoenviadohost;
  }

  public String getDfetValorcriticoanalizador() {
    return this.dfetValorcriticoanalizador;
  }

  public void setDfetValorcriticoanalizador(String dfetValorcriticoanalizador) {
    this.dfetValorcriticoanalizador = dfetValorcriticoanalizador;
  }

  public String getDfetNotificado() {
    return this.dfetNotificado;
  }

  public void setDfetNotificado(String dfetNotificado) {
    this.dfetNotificado = dfetNotificado;
  }

  public String getDfetValorcriticodesde() {
    return this.dfetValorcriticodesde;
  }

  public void setDfetValorcriticodesde(String dfetValorcriticodesde) {
    this.dfetValorcriticodesde = dfetValorcriticodesde;
  }

  public String getDfetValorcriticohasta() {
    return this.dfetValorcriticohasta;
  }

  public void setDfetValorcriticohasta(String dfetValorcriticohasta) {
    this.dfetValorcriticohasta = dfetValorcriticohasta;
  }

  public String getDfetTiponotificacion() {
    return this.dfetTiponotificacion;
  }

  public void setDfetTiponotificacion(String dfetTiponotificacion) {
    this.dfetTiponotificacion = dfetTiponotificacion;
  }

  public String getDfetProfesionalnotificado() {
    return this.dfetProfesionalnotificado;
  }

  public void setDfetProfesionalnotificado(String dfetProfesionalnotificado) {
    this.dfetProfesionalnotificado = dfetProfesionalnotificado;
  }

  public Date getDfetFechafirma() {
    return this.dfetFechafirma;
  }

  public void setDfetFechafirma(Date dfetFechafirma) {
    this.dfetFechafirma = dfetFechafirma;
  }

  public Long getDfetIdusuariofirma() {
    return this.dfetIdusuariofirma;
  }

  public void setDfetIdusuariofirma(Long dfetIdusuariofirma) {
    this.dfetIdusuariofirma = dfetIdusuariofirma;
  }

  public String getDfetTestfirmado() {
    return this.dfetTestfirmado;
  }

  public void setDfetTestfirmado(String dfetTestfirmado) {
    this.dfetTestfirmado = dfetTestfirmado;
  }

  public String getDfetComentarionadie() {
    return this.dfetComentarionadie;
  }

  public void setDfetComentarionadie(String dfetComentarionadie) {
    this.dfetComentarionadie = dfetComentarionadie;
  }

  public String getDfetComentariootro() {
    return this.dfetComentariootro;
  }

  public void setDfetComentariootro(String dfetComentariootro) {
    this.dfetComentariootro = dfetComentariootro;
  }

  public String getValoresReferencias() {
    return valoresReferencias;
  }

  public void setValoresReferencias(String valoresReferencias) {
    this.valoresReferencias = valoresReferencias;
  }

  public String getValorCriticoBajo() {
    return valorCriticoBajo;
  }

  public void setValorCriticoBajo(String valorCriticoBajo) {
    this.valorCriticoBajo = valorCriticoBajo;
  }

  public String getValorCriticoAlto() {
    return valorCriticoAlto;
  }

  public void setValorCriticoAlto(String valorCriticoAlto) {
    this.valorCriticoAlto = valorCriticoAlto;
  }

  public String getNombreExamen() {
    return this.nombreExamen;
  }

  public void setNombreExamen(String nombreExamen) {
    this.nombreExamen = nombreExamen;
  }

  public String getCE_ABREVIADO() {
    return this.CE_ABREVIADO;
  }

  public void setCE_ABREVIADO(String CE_ABREVIADO) {
    this.CE_ABREVIADO = CE_ABREVIADO;
  }

  @Override
  public String toString() {
    return "DatosFichasexamenestest{" + "id=" + id.toString() + '}';
  }

//agregado por cristian 04-11  
  public BigDecimal getDfetIdmetodo() {
    return dfetIdmetodo;
  }

  public void setDfetIdmetodo(BigDecimal dfetIdmetodo) {
    this.dfetIdmetodo = dfetIdmetodo;
  }

  // agregado por jan 19-12-2022
  public BigDecimal getDfetidbacestadotest() {
    return dfetidbacestadotest;
  }

  public void setDfetidbacestadotest(BigDecimal dfetidbacestadotest) {
    this.dfetidbacestadotest = dfetidbacestadotest;
  }

  public String getDfetUltimoresultadoant() {
    return dfetUltimoresultadoant;
  }

  public void setDfetUltimoresultadoant(String dfetUltimoresultadoant) {
    this.dfetUltimoresultadoant = dfetUltimoresultadoant;
  }

  public Date getDfetfechaultimoresultado() {
    return dfetfechaultimoresultado;
  }

  public void setDfetfechaultimoresultado(Date dfetfechaultimoresultado) {
    this.dfetfechaultimoresultado = dfetfechaultimoresultado;
  }

}