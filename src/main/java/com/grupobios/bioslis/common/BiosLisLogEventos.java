package com.grupobios.bioslis.common;

import static org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LazyInitializationException;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.CfgConvenioDAO;
import com.grupobios.bioslis.dao.CfgDiagnosticosDAO;
import com.grupobios.bioslis.dao.CfgLocalizacionesDAO;
import com.grupobios.bioslis.dao.CfgMedicosDAO;
import com.grupobios.bioslis.dao.CfgPrioridadAtencionDAO;
import com.grupobios.bioslis.dao.CfgProcedenciasDAO;
import com.grupobios.bioslis.dao.CfgServiciosDAO;
import com.grupobios.bioslis.dao.CfgTipoAtencionDAO;
import com.grupobios.bioslis.dao.DatosPacientesDAO;
import com.grupobios.bioslis.dao.LdvComunasDAO;
import com.grupobios.bioslis.dao.LdvEstadoscivilesDAO;
import com.grupobios.bioslis.dao.LdvPaisesDAO;
import com.grupobios.bioslis.dao.LdvRegionesDAO;
import com.grupobios.bioslis.dao.LdvSexoDAO;
import com.grupobios.bioslis.dao.LogEventosfichasDAO;
import com.grupobios.bioslis.dao.LogPacientesDAO;
import com.grupobios.bioslis.entity.CfgConvenios;
import com.grupobios.bioslis.entity.CfgDiagnosticos;
import com.grupobios.bioslis.entity.CfgLocalizaciones;
import com.grupobios.bioslis.entity.CfgMedicos;
import com.grupobios.bioslis.entity.CfgPrioridadatencion;
import com.grupobios.bioslis.entity.CfgProcedencias;
import com.grupobios.bioslis.entity.CfgServicios;
import com.grupobios.bioslis.entity.CfgTipoatencion;
import com.grupobios.bioslis.entity.DatosContactos;
import com.grupobios.bioslis.entity.DatosFichas;
import com.grupobios.bioslis.entity.DatosPacientes;
import com.grupobios.bioslis.entity.DatosPacientespatologias;
import com.grupobios.bioslis.entity.LdvComunas;
import com.grupobios.bioslis.entity.LdvEstadosciviles;
import com.grupobios.bioslis.entity.LdvPaises;
import com.grupobios.bioslis.entity.LdvRegiones;
import com.grupobios.bioslis.entity.LdvSexo;
import com.grupobios.bioslis.entity.LogEventosfichas;

public class BiosLisLogEventos {

    

    private static final Logger logger = LogManager.getLogger(BiosLisLogEventos.class);
    
   private LdvComunasDAO comunasDAO = new LdvComunasDAO();    
   private  LdvRegionesDAO regionesDAO = new LdvRegionesDAO();
   private  LdvPaisesDAO paisesDAO = new LdvPaisesDAO();
   private LdvSexoDAO sexoDAO = new LdvSexoDAO();
   private LdvEstadoscivilesDAO estadoCivilDAO = new LdvEstadoscivilesDAO();
   private  DatosPacientesDAO datosPacientesDAO = new  DatosPacientesDAO();
   private LogPacientesDAO  logPacientesDAO = new LogPacientesDAO();
   private CfgConvenioDAO cfgConvenioDAO = new CfgConvenioDAO();
   private CfgPrioridadAtencionDAO cfgPrioridadAtencionDAO = new CfgPrioridadAtencionDAO();
  private CfgMedicosDAO cfgMedicosDAO = new CfgMedicosDAO();
  private CfgTipoAtencionDAO cfgTipoAtencionDAO = new CfgTipoAtencionDAO();
  private CfgProcedenciasDAO cfgProcedenciasDAO = new CfgProcedenciasDAO();
  private CfgServiciosDAO cfgServiciosDAO = new CfgServiciosDAO();
  private CfgLocalizacionesDAO cfgLocalizacionesDAO = new CfgLocalizacionesDAO();
  
  
  

public CfgLocalizacionesDAO getCfgLocalizacionesDAO() {
	return cfgLocalizacionesDAO;
}


public void setCfgLocalizacionesDAO(CfgLocalizacionesDAO cfgLocalizacionesDAO) {
	this.cfgLocalizacionesDAO = cfgLocalizacionesDAO;
}


public CfgProcedenciasDAO getCfgProcedenciasDAO() {
	return cfgProcedenciasDAO;
}


public void setCfgProcedenciasDAO(CfgProcedenciasDAO cfgProcedenciasDAO) {
	this.cfgProcedenciasDAO = cfgProcedenciasDAO;
}


public CfgServiciosDAO getCfgServiciosDAO() {
	return cfgServiciosDAO;
}


public void setCfgServiciosDAO(CfgServiciosDAO cfgServiciosDAO) {
	this.cfgServiciosDAO = cfgServiciosDAO;
}


public CfgMedicosDAO getCfgMedicosDAO() {
	return cfgMedicosDAO;
}


public void setCfgMedicosDAO(CfgMedicosDAO cfgMedicosDAO) {
	this.cfgMedicosDAO = cfgMedicosDAO;
}


public CfgTipoAtencionDAO getCfgTipoAtencionDAO() {
	return cfgTipoAtencionDAO;
}


public void setCfgTipoAtencionDAO(CfgTipoAtencionDAO cfgTipoAtencionDAO) {
	this.cfgTipoAtencionDAO = cfgTipoAtencionDAO;
}


public CfgPrioridadAtencionDAO getCfgPrioridadAtencionDAO() {
	return cfgPrioridadAtencionDAO;
}


public void setCfgPrioridadAtencionDAO(CfgPrioridadAtencionDAO cfgPrioridadAtencionDAO) {
	this.cfgPrioridadAtencionDAO = cfgPrioridadAtencionDAO;
}


public CfgConvenioDAO getCfgConvenioDAO() {
	return cfgConvenioDAO;
}


public void setCfgConvenioDAO(CfgConvenioDAO cfgConvenioDAO) {
	this.cfgConvenioDAO = cfgConvenioDAO;
}


public DatosPacientesDAO getDatosPacientesDAO() {
    return datosPacientesDAO;
}


public void setDatosPacientesDAO(DatosPacientesDAO datosPacientesDAO) {
    this.datosPacientesDAO = datosPacientesDAO;
}


public LogPacientesDAO getLogPacientesDAO() {
    return logPacientesDAO;
}


public void setLogPacientesDAO(LogPacientesDAO logPacientesDAO) {
    this.logPacientesDAO = logPacientesDAO;
}


public LdvComunasDAO getComunasDAO() {
    return comunasDAO;
}


public void setComunasDAO(LdvComunasDAO comunasDAO) {
    this.comunasDAO = comunasDAO;
}


public LdvRegionesDAO getRegionesDAO() {
    return regionesDAO;
}


public void setRegionesDAO(LdvRegionesDAO regionesDAO) {
    this.regionesDAO = regionesDAO;
}


public LdvPaisesDAO getPaisesDAO() {
    return paisesDAO;
}


public void setPaisesDAO(LdvPaisesDAO paisesDAO) {
    this.paisesDAO = paisesDAO;
}


public LdvSexoDAO getSexoDAO() {
    return sexoDAO;
}


public void setSexoDAO(LdvSexoDAO sexoDAO) {
    this.sexoDAO = sexoDAO;
}


public LdvEstadoscivilesDAO getEstadoCivilDAO() {
    return estadoCivilDAO;
}


public void setEstadoCivilDAO(LdvEstadoscivilesDAO estadoCivilDAO) {
    this.estadoCivilDAO = estadoCivilDAO;
}


//funcion que compara objectos e informa de campos que han sido modificado 
public void comparadorObjetos( Object objetosinCambios, Object objetoConCambios, int accion, int usuario, 
     String nombreEquipo, String ip) {  
	
    String[] ipLocal = new String[2];
   
    try {
        InetAddress address = InetAddress.getLocalHost();
            
                   
        // IP en formato String
        String paso = address.toString();
        ipLocal =paso.split("/");
   
        
    } catch (UnknownHostException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
    logger.debug("tipo de objeto " + objetoConCambios.getClass().getSimpleName());
    logger.debug("tipo de objeto " + objetosinCambios.getClass().getSimpleName());
   try {
     // Definir javers
     Javers javers = JaversBuilder.javers().withListCompareAlgorithm(LEVENSHTEIN_DISTANCE).build();

     // hacer comparacion entre arreglos y convertilo a array

     Diff diff = javers.compare(objetosinCambios, objetoConCambios);
   
     ArrayList<ValueChange> diffs = (ArrayList<ValueChange>) diff.getChangesByType(ValueChange.class);
     String campo;
     String sinCambio = new String();
     String conCambio = new String();
     // recorrer array
     
      
     for (ValueChange v : diffs) {
    	
         logger.debug("entre a for  valores diferentes cambiar  " + v);
         
             if(v.getRight() != ""  || v.getLeft() != null)  {               
            
                     int cont = 0;
                   // Insertar campo cambiado
                   campo = v.getPropertyName();                  
                   String[] campoSerarado = campo.split("(?=[A-Z])");
                   String nuevoCampo = nombreCampo(campoSerarado);
                  
                                     
                   
                   //AQUI SE COLOCAN LOS CAMPOS QUE NO SE QUIERE QUE SE GRABEN EN las TABLAs de  LOG 
                  
                
                   if(!nuevoCampo.toUpperCase().equals("FNACIMIENTO") && !nuevoCampo.toUpperCase().equals("IDPACIENTEPATOLOGIA")
                           &&  !nuevoCampo.toUpperCase().equals("NOMBREENCRIPTADO") && !nuevoCampo.equals("-1") && 
                           !nuevoCampo.toUpperCase().equals("IDCONTACTO")  && !nuevoCampo.toUpperCase().equals("IDMADRE")   ) {   
                                         
                           if (v.getLeft() != null) {         
                             sinCambio = v.getLeft().toString();    
                           
                             sinCambio = caseTablas(sinCambio , nuevoCampo.toUpperCase());
                            
                           } else {
                               sinCambio = null;
                           }
                           
                           if (v.getRight() != null) {
                               conCambio = v.getRight().toString();
                               conCambio = caseTablas(conCambio , nuevoCampo.toUpperCase());
                           } else {         
                             conCambio = null;
                           }
                          
                            
                    //aqui se manda a cada insert de objetos segun nombre de objeto// deberian ir varios if
                           if(objetoConCambios.getClass().getSimpleName().equals("DatosPacientes")) {                              
                                nuevoCampo = "DP_" + nuevoCampo;
                                   DatosPacientes dp = (DatosPacientes) objetosinCambios;                         
                                   logPacientesDAO.insertLogPaciente(nuevoCampo.toUpperCase(), conCambio, sinCambio, accion, usuario, ipLocal[0], dp , ipLocal[1]);  
                             
                           }                       
                           if(objetoConCambios.getClass().getSimpleName().equals("DatosContactos")) {
                               nuevoCampo = "DC_"+ nuevoCampo;
                               DatosContactos dp = (DatosContactos) objetosinCambios;
                               logPacientesDAO.insertLogPaciente(nuevoCampo.toUpperCase(), conCambio, sinCambio, accion, usuario, ipLocal[0], dp.getDatosPacientes(), ipLocal[1]);                           
                           } 
                           
                           if(objetoConCambios.getClass().getSimpleName().equals("DatosPacientespatologias")) {                           
                               DatosPacientespatologias dp = (DatosPacientespatologias) objetosinCambios;
                               logPacientesDAO.insertLogPaciente(nuevoCampo.toUpperCase(), conCambio, sinCambio, accion, usuario, ipLocal[0], dp.getDatosPacientes(), ipLocal[1]);                           
                           }
                           if(objetoConCambios.getClass().getSimpleName().equals("DatosFichas") && !nuevoCampo.toUpperCase().equals("LOCALIZACIONES")) {   
                        	   nuevoCampo = "DF_"+ nuevoCampo;
                        	   LogEventosfichasDAO lefDAO = new LogEventosfichasDAO();
                        	   LogEventosfichas lef = new LogEventosfichas();   
                        	   DatosFichas df =  (DatosFichas) objetosinCambios;

                               lef.setDatosFichas(df.getDfNorden());          
                             
                             lef.setLefFechaorden(df.getDfFechaorden());
                               lef.setLefIdpaciente(df.getDatosPacientes());
                               lef.setLefNombretabla("DATOS_FICHAS");
                               lef.setCfgEventos(2);
                               lef.setLefFecharegistro(BiosLisCalendarService.getInstance().getTS());
                              lef.setLefIdusuario(usuario);
                              lef.setLefNombrecampo(nuevoCampo.toUpperCase());
                              lef.setLefValoranterior(sinCambio);
                              lef.setLefValornuevo(conCambio);
                              

                                lefDAO.insertLogEventosFichas(lef);
                        	   
                           }
                           
                           if(objetoConCambios.getClass().getSimpleName().equals("CfgExamenestablasanexas")) {                        	  
                        	   //no se esta usando
                        	   
                           }
                  
                  }    
             
                 
            } 
         }
         
       } catch (LazyInitializationException he) {
           logger.error("error en comparadorObjetos "+he.getMessage());            
         throw he;
       }
  }



public String  nombreCampo(String[] nombreCampo) {
    String nuevoCampo = "";

   
   
    switch (nombreCampo[1].toUpperCase()){
        case "PAISES":{
           nombreCampo[1] = "IDPAIS";            
            break;
        }
    case "COMUNAS":{        
        nombreCampo[1] = "IDCOMUNA";        
         break;
     }
    case "SEXO":{        
        nombreCampo[1] = "IDSEXO";         
         break;
     }
    case "REGIONES":{        
        nombreCampo[1] = "IDREGION";         
         break;
     }
    case "ESTADOSCIVILES":{        
        nombreCampo[1] = "IDESTADOCIVIL";         
         break;
     }
    case "DIAGNOSTICOS":{        
        nombreCampo[1] = "IDDIAGNOSTICO";         
         break;
     }
    case "CONVENIOS":{        
        nombreCampo[1] = "IDCONVENIO";         
         break;
     }
    case "PRIORIDADATENCION":{        
        nombreCampo[1] = "IDPRIORIDADATENCION";         
         break;
     }
    case "TIPOATENCION":{        
        nombreCampo[1] = "IDTIPOATENCION";         
         break;
     }
    case "PROCEDENCIAS":{        
        nombreCampo[1] = "IDPROCEDENCIA";         
         break;
     }
    case "SERVICIOS":{        
        nombreCampo[1] = "IDSERVICIO";         
         break;
     }
    case "CODIGOLOCALIZACION":{        
        nombreCampo[1] = "IDLOCALIZACION";         
         break;
     }
    }
  
    String campo = nombreCampo[0]+nombreCampo[1];
    if(campo.equals("ldvcprDescripcion")) {        
        nombreCampo[1] = "IDCONTACTOPACIENTEREL";
    }
    
    if(nombreCampo.length > 2) {
        if(nombreCampo[2].equals("B")) {
            nuevoCampo = "-1";
        }else {
            nuevoCampo =  nombreCampo[1] +  nombreCampo[2];
        }
    }else {
        nuevoCampo =  nombreCampo[1];
    }
    
    
    
    return nuevoCampo;
}

//funcion que sirve para buscar nombres de campos por id ************************

public String caseTablas(String cambio , String nuevoCampo) {
    
    switch (nuevoCampo){
        case "IDCOMUNA":{
            LdvComunas comuna = new LdvComunas();            
            if(cambio != null) {
              comuna =   comunasDAO.getComunaById(Integer.parseInt(cambio)) ;
              if(comuna != null) { 
                  cambio = comuna.getLdvcDescripcion();
              }
            }
            break;
        }
        case "IDREGION":{
            LdvRegiones region = new LdvRegiones();            
            if(cambio != null) {
              region =   regionesDAO.getRegionById(Integer.parseInt(cambio)) ;
              if(region != null) { 
                  cambio = region.getLdvrDescripcion();
              }
            }

            break;
        }
        case "IDPAIS":{
            LdvPaises pais = new LdvPaises();            
            if(cambio != null) {
              pais =   paisesDAO.getPaisById(Integer.parseInt(cambio)) ;
              if(pais != null) { 
                  cambio = pais.getLdvpDescripcion();
              }
            }
            break;
        }
        case "IDSEXO":{
            LdvSexo sexo = new LdvSexo();            
            if(cambio != null) {
              try {
                sexo =   sexoDAO.getSexoById(Integer.parseInt(cambio)) ;
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (BiosLISDAOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
              if(sexo != null) { 
                  cambio = sexo.getLdvsDescripcion();
              }
            }
            break;
        }
        case "IDESTADOCIVIL":{
            LdvEstadosciviles estadoCivil = new LdvEstadosciviles();            
            if(cambio != null) {
                estadoCivil = estadoCivilDAO.getEstadoCivilById(Integer.parseInt(cambio)) ;
              if(estadoCivil != null) { 
                  cambio = estadoCivil.getLdvecDescripcion();
              }
            }
            break;
        }
        case "IDMADRE":{   
            DatosPacientes dp = null;
            if(cambio != null) {
                 try {
                    dp =   datosPacientesDAO.getPacienteById(Integer.parseInt(cambio));
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (BiosLISDAOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (BiosLISDAONotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }     
              if(dp != null) { 
                  cambio = dp.getDpNrodocumento(); 
              }
            }
            break;
        }
        case "IDDIAGNOSTICO":{
        	CfgDiagnosticosDAO diagnosticoDAO = new CfgDiagnosticosDAO();            
            if(cambio != null) {
            	CfgDiagnosticos diagnostico = new CfgDiagnosticos() ;
				try {
					diagnostico = diagnosticoDAO.getDiagnosticoById(Short.parseShort(cambio));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BiosLISDAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              if(diagnostico != null) { 
                  cambio = diagnostico.getCdDescripcion();
              }
            }
            break;
        }
        case "IDTIPOATENCION":{
        	CfgTipoatencion tipoAtencion = new CfgTipoatencion();            
            if(cambio != null) {
            	try {
					tipoAtencion = cfgTipoAtencionDAO.getTipoAtencionById(Integer.parseInt(cambio));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BiosLISDAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              if(tipoAtencion != null) { 
                  cambio = tipoAtencion.getCtaDescripcion();
              }
            }
            break;
        }
        case "IDPRIORIDADATENCION":{
        	CfgPrioridadatencion prioridad = new CfgPrioridadatencion();            
            if(cambio != null) {
            	try {
					prioridad = cfgPrioridadAtencionDAO.getPrioridadAtencionById(Short.parseShort(cambio));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BiosLISDAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              if(prioridad != null) { 
                  cambio = prioridad.getCpaDescripcion();
              }
            }
            break;
        }
        case "IDMEDICO":{
        	CfgMedicos medico = new CfgMedicos();            
            if(cambio != null) {
              try {
				medico = cfgMedicosDAO.getMedicoById(Short.parseShort(cambio));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BiosLISDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              if(medico != null) { 
                  cambio = medico.getCmNombres()+" "+medico.getCmPrimerapellido()+" "+medico.getCmSegundoapellido();
              }
            }
            break;
        }
        case "IDCONVENIO":{
        	CfgConvenios convenio = new CfgConvenios();            
            if(cambio != null) {
              convenio = cfgConvenioDAO.getConveniosById(Short.parseShort(cambio));
              if(convenio != null) { 
                  cambio = convenio.getCcDescripcion();
              }
            }
            break;
        } 
        case "IDPROCEDENCIA":{
        	CfgProcedencias procedencia = new CfgProcedencias();            
            if(cambio != null) {
              try {
				procedencia = cfgProcedenciasDAO.getProcedenciaById(Integer.parseInt(cambio));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BiosLISDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              if(procedencia != null) { 
                  cambio = procedencia.getCpDescripcion();
              }
            }
            break;
        }
        case "IDSERVICIO":{      
        	
        	CfgServicios servicio = new CfgServicios();            
            if(cambio != null) {            	
              try {
				servicio = cfgServiciosDAO.getServiciosById(Integer.parseInt(cambio));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BiosLISDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              if(servicio != null) { 
                  cambio = servicio.getCsDescripcion();
              }
            }
            break;
        } 
       
        
        default: {
            logger.debug("no se encontro ningun caso ");
        }
  }    
    return cambio;
}

//funcion para formatear fechas antes de enviar a comparadpor
public Date FormatFecha(Date fecha) {  
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");  
       
   String fechaTexto= formatter.format(fecha);   
   Date fec= null;
try {
    fec = formatter.parse(fechaTexto);   
} catch (ParseException e) {    
    e.printStackTrace();
}
   return fec ;
  
}
    
//funcion para formatear fechas para grabar a texto
public String FormatFechaATexto(Date fecha) {  
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");      
    String fechaTexto= formatter.format(fecha);    
    Date fec= null;
    String fe="";
 try {
     fec = formatter.parse(fechaTexto);   
     fe = formatter.format(fec);    
 } catch (ParseException e) {
     
     e.printStackTrace();
 }
    return fe ;
}

}
