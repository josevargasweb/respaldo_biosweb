package com.grupobios.bioslis.entity;
// Generated 16-08-2021 14:22:20 by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DatosUsuarios generated by hbm2java
 */
public class DatosUsuarios  implements java.io.Serializable {


     private long duIdusuario;
     @JsonIgnore
     private LdvSexo ldvSexo;
     @JsonIgnore
     private LdvProfesionesusuarios ldvProfesionesusuarios;
     @JsonIgnore
     private LdvCargosusuarios ldvCargosusuarios;
     private String duUsuario;
     private String duRun;
     private String duNombres;
     private String duPrimerapellido;
     private String duSegundoapellido;
     private String duActivo;
     @JsonIgnore
     private Blob duFoto;
     @JsonIgnore
     private String duPassword;
     @Temporal(TemporalType.DATE)
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="America/Santiago")
     private Date duFechacaducapassword;
     private Date duFechaultimaconexion;
     private String duEmail;
     private String duPasswordexpira;
     @JsonIgnore
     private Set logPacientespatologiases = new HashSet(0);
     @JsonIgnore
     private Set logContactospacienteses = new HashSet(0);
     
    public DatosUsuarios() {
    }

	
    public DatosUsuarios(long duIdusuario, LdvSexo ldvSexo, String duUsuario, String duRun, String duNombres, String duPrimerapellido, String duActivo) {
        this.duIdusuario = duIdusuario;
        this.ldvSexo = ldvSexo;
        this.duUsuario = duUsuario;
        this.duRun = duRun;
        this.duNombres = duNombres;
        this.duPrimerapellido = duPrimerapellido;
        this.duActivo = duActivo;
    }
    public DatosUsuarios(long duIdusuario, LdvSexo ldvSexo, LdvProfesionesusuarios ldvProfesionesusuarios, LdvCargosusuarios ldvCargosusuarios, String duUsuario, String duRun, String duNombres, String duPrimerapellido, String duSegundoapellido, String duActivo, Blob duFoto, String duPassword, Date duFechacaducapassword, Date duFechaultimaconexion, Set logPacientespatologiases, Set logContactospacienteses) {
       this.duIdusuario = duIdusuario;
       this.ldvSexo = ldvSexo;
       this.ldvProfesionesusuarios = ldvProfesionesusuarios;
       this.ldvCargosusuarios = ldvCargosusuarios;
       this.duUsuario = duUsuario;
       this.duRun = duRun;
       this.duNombres = duNombres;
       this.duPrimerapellido = duPrimerapellido;
       this.duSegundoapellido = duSegundoapellido;
       this.duActivo = duActivo;
       this.duFoto = duFoto;
       this.duPassword = duPassword;
       this.duFechacaducapassword = duFechacaducapassword;
       this.duFechaultimaconexion = duFechaultimaconexion;
       this.logPacientespatologiases = logPacientespatologiases;
       this.logContactospacienteses = logContactospacienteses;
    }
   
    public long getDuIdusuario() {
        return this.duIdusuario;
    }
    
    public void setDuIdusuario(long duIdusuario) {
        this.duIdusuario = duIdusuario;
    }
    public LdvSexo getLdvSexo() {
        return this.ldvSexo;
    }
    
    public void setLdvSexo(LdvSexo ldvSexo) {
        this.ldvSexo = ldvSexo;
    }
    public LdvProfesionesusuarios getLdvProfesionesusuarios() {
        return this.ldvProfesionesusuarios;
    }
    
    public void setLdvProfesionesusuarios(LdvProfesionesusuarios ldvProfesionesusuarios) {
        this.ldvProfesionesusuarios = ldvProfesionesusuarios;
    }
    public LdvCargosusuarios getLdvCargosusuarios() {
        return this.ldvCargosusuarios;
    }
    
    public void setLdvCargosusuarios(LdvCargosusuarios ldvCargosusuarios) {
        this.ldvCargosusuarios = ldvCargosusuarios;
    }
    public String getDuUsuario() {
        return this.duUsuario;
    }
    
    public void setDuUsuario(String duUsuario) {
        this.duUsuario = duUsuario;
    }
    public String getDuRun() {
        return this.duRun;
    }
    
    public void setDuRun(String duRun) {
        this.duRun = duRun;
    }
    public String getDuNombres() {
        return this.duNombres;
    }
    
    public void setDuNombres(String duNombres) {
        this.duNombres = duNombres;
    }
    public String getDuPrimerapellido() {
        return this.duPrimerapellido;
    }
    
    public void setDuPrimerapellido(String duPrimerapellido) {
        this.duPrimerapellido = duPrimerapellido;
    }
    public String getDuSegundoapellido() {
        return this.duSegundoapellido;
    }
    
    public void setDuSegundoapellido(String duSegundoapellido) {
        this.duSegundoapellido = duSegundoapellido;
    }
    public String getDuActivo() {
        return this.duActivo;
    }
    
    public void setDuActivo(String duActivo) {
        this.duActivo = duActivo;
    }
    public Blob getDuFoto() {
        return this.duFoto;
    }
    
    public void setDuFoto(Blob duFoto) {
        this.duFoto = duFoto;
    }
    public String getDuPassword() {
        return this.duPassword;
    }
    
    public void setDuPassword(String duPassword) {
        this.duPassword = duPassword;
    }
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getDuFechacaducapassword() {
        return this.duFechacaducapassword;
    }
    
    public void setDuFechacaducapassword(Date duFechacaducapassword) {
        this.duFechacaducapassword = duFechacaducapassword;
    }
    public Date getDuFechaultimaconexion() {
        return this.duFechaultimaconexion;
    }
    
    public void setDuFechaultimaconexion(Date duFechaultimaconexion) {
        this.duFechaultimaconexion = duFechaultimaconexion;
    }
    public Set getLogPacientespatologiases() {
        return this.logPacientespatologiases;
    }
    
    public void setLogPacientespatologiases(Set logPacientespatologiases) {
        this.logPacientespatologiases = logPacientespatologiases;
    }
    public Set getLogContactospacienteses() {
        return this.logContactospacienteses;
    }
    
    public void setLogContactospacienteses(Set logContactospacienteses) {
        this.logContactospacienteses = logContactospacienteses;
    }

    public String getDuEmail() {
        return duEmail;
    }

    public void setDuEmail(String duEmail) {
        this.duEmail = duEmail;
    }

    public String getDuPasswordexpira() {
        return duPasswordexpira;
    }

    public void setDuPasswordexpira(String duPasswordexpira) {
        this.duPasswordexpira = duPasswordexpira;
    }




}


