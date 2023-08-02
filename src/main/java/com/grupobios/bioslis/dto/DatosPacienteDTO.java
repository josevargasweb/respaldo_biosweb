package com.grupobios.bioslis.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.grupobios.bioslis.entity.CfgTipoatencion;
import com.grupobios.bioslis.entity.DatosContactos;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosPacientespatologias;

@JsonInclude(value = Include.NON_NULL)
public class DatosPacienteDTO {

  // tipo atención y localización agregados por Marco Caracciolo el 13/05/2022
  private DatosPacientes dp;
  private DatosContactos dc;
  private DatosPacientes dmadre;
  private CfgTipoatencion tipoAtencion;
  private String procedencia;
  private String servicio;
  private List<DatosPacientespatologias> listaPatologias;
  private List<DatosFichas> listaOrdenesDelDia;

  public DatosPacientes getDp() {
    return dp;
  }

  public void setDp(DatosPacientes dp) {
    this.dp = dp;
  }

  private String rutMadre;

  public DatosPacienteDTO() {
    super();
  }

  public String getRutMadre() {
    return rutMadre;
  }

  public void setRutMadre(String rutMadre) {
    this.rutMadre = rutMadre;
  }

  public DatosContactos getDc() {
    return dc;
  }

  public void setDc(DatosContactos dc) {
    this.dc = dc;
  }

  public DatosPacientes getDmadre() {
    return dmadre;
  }

  public void setDmadre(DatosPacientes dmadre) {
    this.dmadre = dmadre;
  }

  public List<DatosPacientespatologias> getListaPatologias() {
    return listaPatologias;
  }

  public void setListaPatologias(List<DatosPacientespatologias> listaPatologias) {
    this.listaPatologias = listaPatologias;
  }

  public CfgTipoatencion getTipoAtencion() {
    return tipoAtencion;
  }

  public void setTipoAtencion(CfgTipoatencion tipoAtencion) {
    this.tipoAtencion = tipoAtencion;
  }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

  public List<DatosFichas> getListaOrdenesDelDia() {
    return listaOrdenesDelDia;
  }

  public void setListaOrdenesDelDia(List<DatosFichas> listaOrdenes) {
    this.listaOrdenesDelDia = listaOrdenes;
  }

}
