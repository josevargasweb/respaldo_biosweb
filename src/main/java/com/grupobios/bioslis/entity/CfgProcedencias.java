package com.grupobios.bioslis.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupobios.bioslis.cfg.cache.CfgAbstracto;

/**
 * CfgProcedencias generated by hbm2java
 */
public class CfgProcedencias extends CfgAbstracto {

  private int cpIdprocedencia;
  @JsonIgnore
  private CfgCentrosdesalud cfgCentroDeSalud;
  @JsonIgnore
  private CfgConvenios cfgConvenios;
  private String cpCodigo;
  private String cpDescripcion;
  private Integer cpSort;
  private String cpActivo;
  private String cpTextoInforme;
  private String cpCodigoGrupo;
  private String cpTomamuestraautomatica;
  private String cpMembrete;
  private Short cpIdtipoconexionhost;
  private String cpProcedenciarestringida;
  private String cpHost;
  private String cpHost2;
  private String cpHost3;
  private String cpVentabonos;
  private String cpActivarcuposcitas;
  private Short cpCitascuposlunes;
  private Short cpCitascuposmartes;
  private Short cpCitascuposmiercoles;
  private Short cpCitascuposjueves;
  private Short cpCitascuposviernes;
  private Short cpCitascupossabado;
  private Short cpCitascuposdomingo;
  private String cpEmail;
  @JsonIgnore
  private Set cfgProgramasdesaluds = new HashSet(0);
  @JsonIgnore
  private Set datosFichases = new HashSet(0);
  @JsonIgnore
  private Set cfgCitascuposes = new HashSet(0);
  @JsonIgnore
  private Set cfgServicioses = new HashSet(0);

  public CfgProcedencias() {
  }

  public int getCpIdprocedencia() {
    return this.cpIdprocedencia;
  }

  public void setCpIdprocedencia(int cpIdprocedencia) {
    this.cpIdprocedencia = cpIdprocedencia;
  }

  public CfgCentrosdesalud getCfgCentroDeSalud() {
    return cfgCentroDeSalud;
  }

  public void setCfgCentroDeSalud(CfgCentrosdesalud cfgCentroDeSalud) {
    this.cfgCentroDeSalud = cfgCentroDeSalud;
  }

  public CfgConvenios getCfgConvenios() {
    return this.cfgConvenios;
  }

  public void setCfgConvenios(CfgConvenios cfgConvenios) {
    this.cfgConvenios = cfgConvenios;
  }

  public String getCpCodigo() {
    return this.cpCodigo;
  }

  public void setCpCodigo(String cpCodigo) {
    this.cpCodigo = cpCodigo;
  }

  public String getCpDescripcion() {
    return this.cpDescripcion;
  }

  public void setCpDescripcion(String cpDescripcion) {
    this.cpDescripcion = cpDescripcion;
  }

  public Integer getCpSort() {
    return this.cpSort;
  }

  public void setCpSort(Integer cpSort) {
    this.cpSort = cpSort;
  }

  public String getCpActivo() {
    return this.cpActivo;
  }

  public void setCpActivo(String cpActivo) {
    this.cpActivo = cpActivo;
  }

  public String getCpTextoInforme() {
    return this.cpTextoInforme;
  }

  public void setCpTextoInforme(String cpHorario) {
    this.cpTextoInforme = cpHorario;
  }

  public String getCpCodigoGrupo() {
    return this.cpCodigoGrupo;
  }

  public void setCpCodigoGrupo(String cpCodigoGrupo) {
    this.cpCodigoGrupo = cpCodigoGrupo;
  }

  public String getCpTomamuestraautomatica() {
    return this.cpTomamuestraautomatica;
  }

  public void setCpTomamuestraautomatica(String cpTomamuestraautomatica) {
    this.cpTomamuestraautomatica = cpTomamuestraautomatica;
  }

  public String getCpMembrete() {
    return this.cpMembrete;
  }

  public void setCpMembrete(String cpMembrete) {
    this.cpMembrete = cpMembrete;
  }

  public Short getCpIdtipoconexionhost() {
    return this.cpIdtipoconexionhost;
  }

  public void setCpIdtipoconexionhost(Short cpIdtipoconexionhost) {
    this.cpIdtipoconexionhost = cpIdtipoconexionhost;
  }

  public String getCpProcedenciarestringida() {
    return this.cpProcedenciarestringida;
  }

  public void setCpProcedenciarestringida(String cpProcedenciarestringida) {
    this.cpProcedenciarestringida = cpProcedenciarestringida;
  }

  public String getCpHost() {
    return this.cpHost;
  }

  public void setCpHost(String cpHost) {
    this.cpHost = cpHost;
  }

  public String getCpHost2() {
    return this.cpHost2;
  }

  public void setCpHost2(String cpHost2) {
    this.cpHost2 = cpHost2;
  }

  public String getCpHost3() {
    return this.cpHost3;
  }

  public void setCpHost3(String cpHost3) {
    this.cpHost3 = cpHost3;
  }

  public String getCpVentabonos() {
    return this.cpVentabonos;
  }

  public void setCpVentabonos(String cpVentabonos) {
    this.cpVentabonos = cpVentabonos;
  }

  public String getCpActivarcuposcitas() {
    return this.cpActivarcuposcitas;
  }

  public void setCpActivarcuposcitas(String cpActivarcuposcitas) {
    this.cpActivarcuposcitas = cpActivarcuposcitas;
  }

  public Short getCpCitascuposlunes() {
    return this.cpCitascuposlunes;
  }

  public void setCpCitascuposlunes(Short cpCitascuposlunes) {
    this.cpCitascuposlunes = cpCitascuposlunes;
  }

  public Short getCpCitascuposmartes() {
    return this.cpCitascuposmartes;
  }

  public void setCpCitascuposmartes(Short cpCitascuposmartes) {
    this.cpCitascuposmartes = cpCitascuposmartes;
  }

  public Short getCpCitascuposmiercoles() {
    return this.cpCitascuposmiercoles;
  }

  public void setCpCitascuposmiercoles(Short cpCitascuposmiercoles) {
    this.cpCitascuposmiercoles = cpCitascuposmiercoles;
  }

  public Short getCpCitascuposjueves() {
    return this.cpCitascuposjueves;
  }

  public void setCpCitascuposjueves(Short cpCitascuposjueves) {
    this.cpCitascuposjueves = cpCitascuposjueves;
  }

  public Short getCpCitascuposviernes() {
    return this.cpCitascuposviernes;
  }

  public void setCpCitascuposviernes(Short cpCitascuposviernes) {
    this.cpCitascuposviernes = cpCitascuposviernes;
  }

  public Short getCpCitascupossabado() {
    return this.cpCitascupossabado;
  }

  public void setCpCitascupossabado(Short cpCitascupossabado) {
    this.cpCitascupossabado = cpCitascupossabado;
  }

  public Short getCpCitascuposdomingo() {
    return this.cpCitascuposdomingo;
  }

  public void setCpCitascuposdomingo(Short cpCitascuposdomingo) {
    this.cpCitascuposdomingo = cpCitascuposdomingo;
  }

  public String getCpEmail() {
    return this.cpEmail;
  }

  public void setCpEmail(String cpEmail) {
    this.cpEmail = cpEmail;
  }

  public Set getCfgProgramasdesaluds() {
    return this.cfgProgramasdesaluds;
  }

  public void setCfgProgramasdesaluds(Set cfgProgramasdesaluds) {
    this.cfgProgramasdesaluds = cfgProgramasdesaluds;
  }

  public Set getDatosFichases() {
    return this.datosFichases;
  }

  public void setDatosFichases(Set datosFichases) {
    this.datosFichases = datosFichases;
  }

  public Set getCfgCitascuposes() {
    return this.cfgCitascuposes;
  }

  public void setCfgCitascuposes(Set cfgCitascuposes) {
    this.cfgCitascuposes = cfgCitascuposes;
  }

  public Set getCfgServicioses() {
    return this.cfgServicioses;
  }

  public void setCfgServicioses(Set cfgServicioses) {
    this.cfgServicioses = cfgServicioses;
  }

  @Transient
  @Override
  public Integer getId() {
    return cpIdprocedencia;
  }

  @Transient
  @Override
  public String getDescripcion() {
    return cpDescripcion;
  }

}