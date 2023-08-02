package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;

/**
 * DatosFichasmuestras generated by hbm2java
 */
public class DatosFichasmuestras implements java.io.Serializable {

	private long dfmIdmuestra;
	private DatosPacientes datosPacientes;
	private CfgBaczonacuerpo cfgBaczonacuerpo;
	private long dfmNorden;
	private String dfmPrefijotipomuestra;
	private String dfmCodigobarra;
	private Date dfmFechacreacion;
	private String dfmEstadotm;
	private long dfmIdusuariotm;
	private Date dfmFechatm;
	private BigDecimal dfmidcajatransporte;
	private String dfmEstadorm;
	private long dfmIdusuariorm;
	private Date dfmFecharm;
	private String dfmTmobservacion;
	private String dfmRecepcionobs;
	private DatosMuestrasrechazadas datosMuestrasrechazadas;
	private Byte dfmNrovecestomada;

	public DatosFichasmuestras() {
	}

	public DatosFichasmuestras(long dfmIdmuestra) {
		this.dfmIdmuestra = dfmIdmuestra;
	}

	public DatosFichasmuestras(long dfmIdmuestra, long dfmNorden, String dfmPrefijotipomuestra, long dfmIdusuariotm,
			long dfmIdusuariorm) {
		this.dfmIdmuestra = dfmIdmuestra;
		this.dfmNorden = dfmNorden;
		this.dfmPrefijotipomuestra = dfmPrefijotipomuestra;
		this.dfmIdusuariotm = dfmIdusuariotm;
		this.dfmIdusuariorm = dfmIdusuariorm;
	}

	public DatosFichasmuestras(long dfmIdmuestra, DatosPacientes datosPacientes, CfgBaczonacuerpo cfgBaczonacuerpo,
			long dfmNorden, String dfmPrefijotipomuestra, String dfmCodigobarra, Date dfmFechacreacion,
			String dfmEstadotm, long dfmIdusuariotm, Date dfmFechatm, BigDecimal dfmidcajatransporte,
			String dfmEstadorm, long dfmIdusuariorm, Date dfmFecharm, String dfmTmobservacion, String dfmRecepcionobs,
			DatosMuestrasrechazadas datosMuestrasrechazadas) {
		this.dfmIdmuestra = dfmIdmuestra;
		this.datosPacientes = datosPacientes;
		this.cfgBaczonacuerpo = cfgBaczonacuerpo;
		this.dfmNorden = dfmNorden;
		this.dfmPrefijotipomuestra = dfmPrefijotipomuestra;
		this.dfmCodigobarra = dfmCodigobarra;
		this.dfmFechacreacion = dfmFechacreacion;
		this.dfmEstadotm = dfmEstadotm;
		this.dfmIdusuariotm = dfmIdusuariotm;
		this.dfmFechatm = dfmFechatm;
		this.dfmidcajatransporte = dfmidcajatransporte;
		this.dfmEstadorm = dfmEstadorm;
		this.dfmIdusuariorm = dfmIdusuariorm;
		this.dfmFecharm = dfmFecharm;
		this.dfmTmobservacion = dfmTmobservacion;
		this.dfmRecepcionobs = dfmRecepcionobs;
		this.datosMuestrasrechazadas = datosMuestrasrechazadas;
	}

	public long getDfmIdmuestra() {
		return this.dfmIdmuestra;
	}

	public void setDfmIdmuestra(long dfmIdmuestra) {
		this.dfmIdmuestra = dfmIdmuestra;
	}

	public DatosPacientes getDatosPacientes() {
		return this.datosPacientes;
	}

	public void setDatosPacientes(DatosPacientes datosPacientes) {
		this.datosPacientes = datosPacientes;
	}

	public CfgBaczonacuerpo getCfgBaczonacuerpo() {
		return this.cfgBaczonacuerpo;
	}

	public void setCfgBaczonacuerpo(CfgBaczonacuerpo cfgBaczonacuerpo) {
		this.cfgBaczonacuerpo = cfgBaczonacuerpo;
	}

	public long getDfmNorden() {
		return this.dfmNorden;
	}

	public void setDfmNorden(long dfmNorden) {
		this.dfmNorden = dfmNorden;
	}

	public String getDfmPrefijotipomuestra() {
		return this.dfmPrefijotipomuestra;
	}

	public void setDfmPrefijotipomuestra(String dfmPrefijotipomuestra) {
		this.dfmPrefijotipomuestra = dfmPrefijotipomuestra;
	}

	public String getDfmCodigobarra() {
		return this.dfmCodigobarra;
	}

	public void setDfmCodigobarra(String dfmCodigobarra) {
		this.dfmCodigobarra = dfmCodigobarra;
	}

	public Date getDfmFechacreacion() {
		return this.dfmFechacreacion;
	}

	public void setDfmFechacreacion(Date dfmFechacreacion) {
		this.dfmFechacreacion = dfmFechacreacion;
	}

	public String getDfmEstadotm() {
		return this.dfmEstadotm;
	}

	public void setDfmEstadotm(String dfmEstadotm) {
		this.dfmEstadotm = dfmEstadotm;
	}

	public long getDfmIdusuariotm() {
		return this.dfmIdusuariotm;
	}

	public void setDfmIdusuariotm(long dfmIdusuariotm) {
		this.dfmIdusuariotm = dfmIdusuariotm;
	}

	public Date getDfmFechatm() {
		return this.dfmFechatm;
	}

	public void setDfmFechatm(Date dfmFechatm) {
		this.dfmFechatm = dfmFechatm;
	}

	public BigDecimal getDfmidcajatransporte() {
		return dfmidcajatransporte;
	}

	public void setDfmidcajatransporte(BigDecimal dfmidcajatransporte) {
		this.dfmidcajatransporte = dfmidcajatransporte;
	}

	public String getDfmEstadorm() {
		return this.dfmEstadorm;
	}

	public void setDfmEstadorm(String dfmEstadorm) {
		this.dfmEstadorm = dfmEstadorm;
	}

	public long getDfmIdusuariorm() {
		return this.dfmIdusuariorm;
	}

	public void setDfmIdusuariorm(long dfmIdusuariorm) {
		this.dfmIdusuariorm = dfmIdusuariorm;
	}

	public Date getDfmFecharm() {
		return this.dfmFecharm;
	}

	public void setDfmFecharm(Date dfmFecharm) {
		this.dfmFecharm = dfmFecharm;
	}

	public String getDfmTmobservacion() {
		return this.dfmTmobservacion;
	}

	public void setDfmTmobservacion(String dfmTmobservacion) {
		this.dfmTmobservacion = dfmTmobservacion;
	}

	public String getDfmRecepcionobs() {
		return this.dfmRecepcionobs;
	}

	public void setDfmRecepcionobs(String dfmRecepcionobs) {
		this.dfmRecepcionobs = dfmRecepcionobs;
	}

	public DatosMuestrasrechazadas getDatosMuestrasrechazadas() {
		return this.datosMuestrasrechazadas;
	}

	public void setDatosMuestrasrechazadas(DatosMuestrasrechazadas datosMuestrasrechazadas) {
		this.datosMuestrasrechazadas = datosMuestrasrechazadas;
	}

	public Byte getDfmNrovecestomada() {
		return dfmNrovecestomada;
	}

	public void setDfmNrovecestomada(Byte dfmNrovecestomada) {
		this.dfmNrovecestomada = dfmNrovecestomada;
	}

}