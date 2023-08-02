package com.grupobios.bioslis.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;


import com.grupobios.bioslis.common.BiosLISException;
import com.grupobios.bioslis.common.DaoHelper;
import com.grupobios.bioslis.config.HibernateUtil;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.DatosLineaTiempoDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.MuestrasRMDTO;
import com.grupobios.bioslis.dto.TestEdicionOrdenDTO;

public class EstadisticaDAO {

    private static Logger logger = LogManager.getLogger(EstadisticaDAO.class);
    
    
    
    
   
    
   private static final String  sqlEstadisticaDatosLineaTiempo ="select  df.df_norden as norden, dfe.dfe_idexamen idexamen , dfe.dfe_fechacreacion as forden,  ce.ce_abreviado as NOMBREEXAMEN,"
           + "(select concat(concat(concat(concat(du_nombres , ' '), du_primerapellido), ' '), du_segundoapellido) as nombre from datos_usuarios where du_idusuario = dfe.dfe_idusuario) as nombreusuarioorden, "
           + "dfe.dfe_fechafirma as ffirma, "
           + "(select concat(concat(concat(concat(du_nombres , ' '), du_primerapellido), ' '), du_segundoapellido) as nombre from datos_usuarios where du_idusuario = dfe.dfe_idusuariofirma) as NOMBREUSUARIOFIRMA, "
           + "dfe.dfe_fechaautoriza as fautoriza,  "
           + "(select concat(concat(concat(concat(du_nombres , ' '), du_primerapellido), ' '), du_segundoapellido) as nombre from datos_usuarios where du_idusuario = dfe.dfe_idusuarioautoriza) as NOMBREUSUARIOAUTORIZA, "
           + "ce.ce_tiempoentreganormal AS TIEMPOTOTAL, "
           + "dfe.dfe_fechaentrega as fechaentrega ,"
           + "dfm.dfm_fechatm as FTOMAMUESTRA ,"
           + "(select concat(concat(concat(concat(du_nombres , ' '), du_primerapellido), ' '), du_segundoapellido) as nombre from datos_usuarios where du_idusuario = dfm.dfm_idusuariotm) as NOMBREUSUARIOMUESTRA "
           + "from datos_fichas df "
           + "inner join datos_fichasexamenes dfe "
           + "on dfe.dfe_norden = df.df_norden "
           + "inner join cfg_examenes ce "
           + "on ce.ce_idexamen = dfe.dfe_idexamen "
           + "left join datos_fichasmuestras dfm "
           + "on dfm.dfm_idmuestra = (select dfet.dfet_idmuestra from datos_fichasexamenestest dfet"
           + " where dfet.dfet_norden = df.df_norden and dfet.dfet_idexamen= dfe.dfe_idexamen order by dfet.dfet_idmuestra asc FETCH FIRST 1 ROWS ONLY  ) "
           + "   inner join cfg_localizaciones cl on cl.cl_idlocalizacion = df.df_idlocalizacion "
           + "   inner join cfg_servicios cs on cs.cs_idservicio = cl.cl_idservicio "
           + "   inner join cfg_secciones csec on csec.csec_idseccion = ce.ce_idseccion  "
           + "   inner join datos_pacientes dp on dp.dp_idpaciente = df.df_idpaciente ";
        
          
   private static final String sqlEstadisticaMuestrasRechazadas= "select dmr.dmr_codigobarra as mr_codigobarras , ccrm.ccrm_descripcion as mr_causarechazo, "
           + "concat(concat(concat(concat(du.du_nombres , ' '), du.du_primerapellido), ' '), du.du_segundoapellido) as mr_usuario, "
           + "to_char(dmr.dmr_fecharechazo, 'dd/mm/yyyy hh:mi' ) as MR_FECHARM ,  dmr.dmr_rechazoparcialototal as mr_tiporechazo ,"
           + "cmue.cmue_descripcionabreviada as mr_muestra , concat(concat(concat(concat(du.du_nombres , ' '), du.du_primerapellido), ' '), du.du_segundoapellido) as mr_usuariotm, "
           + "cenv.cenv_descripcion as mr_envase "
           + "from datos_muestrasrechazadas dmr "
           + "left join cfg_causasrechazosmuestras ccrm "
           + "on ccrm.ccrm_idcausarechazo = dmr.dmr_idcausarechazo "
           + "left  join datos_usuarios du "
           + "on du.du_idusuario = dmr.dmr_idusuariorechazo "
           + "left join cfg_muestras cmue "
           + "on cmue.cmue_idtipomuestra = (select dfet.dfet_idtipomuestra from datos_fichasexamenestest dfet where dfet.dfet_idmuestra = dmr.dmr_idnuevamuestra FETCH FIRST 1 ROW only) "
           + "left join cfg_envases cenv "
           + "on cenv.cenv_idenvase = (select dfet.dfet_idenvase from datos_fichasexamenestest dfet where dfet.dfet_idmuestra = dmr.dmr_idnuevamuestra FETCH FIRST 1 ROW only) "
           + "inner join datos_fichasmuestras dfm "
           + "on dfm.dfm_idmuestra = dmr.dmr_idmuestra "
           + "left  join datos_usuarios dutm "
           + "on dutm.du_idusuario = dfm.dfm_idusuariotm "
           + "inner join datos_fichas df on df.df_norden = dfm.dfm_norden "
           + " inner join datos_fichasexamenes dfe  on dfe.dfe_norden = df.df_norden "
           + " inner join cfg_examenes ce on ce.ce_idexamen = dfe.dfe_idexamen  "
           + " inner join datos_pacientes dp on dp.dp_idpaciente = df.df_idpaciente "
           + " inner join cfg_servicios cs on cs.cs_idservicio = df.df_idservicio "
           + " inner join cfg_secciones csec on csec.csec_idseccion = ce.ce_idseccion ";
   
   private static final String sqlEstadisticaTotalPorExamen = "select ce.ce_idexamen as DFE_IDEXAMEN, ce.ce_codigoexamen as CE_CODIGOEXAMEN, ce.ce_codigofonasa as CE_CODIGOFONASA,  count (dfe.dfe_idexamen) as DFE_CANTIDAD, "
           + "ce.ce_abreviado as  CS_DESCRIPCION "
           + "from datos_fichas df "
           + "inner join datos_fichasexamenes dfe on dfe.dfe_norden = df.df_norden "
           + "inner join cfg_examenes ce on ce.ce_idexamen = dfe.dfe_idexamen "
           + "inner join cfg_servicios cs on cs.cs_idservicio = df.df_idservicio "
           + "inner join cfg_secciones csec on csec.csec_idseccion = ce.ce_idseccion "
           + "inner join datos_pacientes dp on dp.dp_idpaciente = df.df_idpaciente ";
   
   private static final String sqlEstadisticaExamenesDerivados = "select dfe.dfe_norden , ce.ce_abreviado, dfm.dfm_codigobarra as mr_codigobarras ,  cderiv.cderiv_descripcion, "
           + "     to_char(dfm.dfm_fechatm, 'dd/mm/yyyy hh:mi' ) as FECHATM "
           + "    from  datos_fichas df "
           + "    inner join datos_fichasexamenes dfe  on dfe.dfe_norden = df.df_norden "
           + "     left join datos_fichasmuestras dfm  on dfm.dfm_idmuestra = (select dfet.dfet_idmuestra from datos_fichasexamenestest dfet where dfet.dfet_norden = dfe.dfe_norden and dfet.dfet_idexamen = dfe.dfe_idexamen FETCH FIRST 1 ROW only) "
           + "   inner join cfg_examenes ce on ce.ce_idexamen = dfe.dfe_idexamen "               
           + " inner join cfg_servicios cs on cs.cs_idservicio = df.df_idservicio "
           + "  inner join cfg_secciones csec on csec.csec_idseccion = ce.ce_idseccion "
           + "  inner join datos_pacientes dp on dp.dp_idpaciente = df.df_idpaciente "
           + "   inner join cfg_derivadores cderiv  on cderiv.cderiv_idderivador = dfe.dfe_idderivador ";
   
   private static final String sqlEstadisticaResultadosCriticos = "select ct.ct_codigo as codigo,  ct.ct_abreviado as testabreviado, dfet.dfet_resultado as resultado, dfet.dfet_valorcriticohasta as valorcriticohasta , "
           + "dfet.dfet_valorcriticodesde as valorcriticodesde,  dfet.dfet_rcritico as conclusion , "
           + "to_char(dfet.dfet_fechafirma, 'dd/mm/yyyy hh:mi' ) as FFIRMA , to_char(df.df_fechaorden, 'dd/mm/yyyy hh:mi' ) as forden,  "
           + "concat(concat(concat(concat(du.du_nombres , ' '), du.du_primerapellido), ' '), du.du_segundoapellido) as nombre "
           + "from datos_fichas df  "
           + "inner join datos_fichasexamenes dfe on dfe.dfe_norden = df.df_norden "
           + "inner join datos_fichasexamenestest dfet on dfet.dfet_norden = dfe.dfe_norden and dfet.dfet_idexamen = dfe.dfe_idexamen "
           + "inner join cfg_test ct on ct.ct_idtest = dfet.dfet_idtest  "
           + "left join datos_usuarios du on du.du_idusuario = dfet.dfet_idusuariofirma "
           + " inner join datos_pacientes dp on dp.dp_idpaciente = df.df_idpaciente  "
           + " inner join cfg_examenes ce on ce.ce_idexamen = dfe.dfe_idexamen  "
           + " inner join cfg_servicios cs on cs.cs_idservicio = df.df_idservicio  "
           + " inner join cfg_secciones csec on csec.csec_idseccion = ce.ce_idseccion  ";
   
   
    @SuppressWarnings("unchecked")
    public List<DatosLineaTiempoDTO> getEstadisticaDatosLineaTiempo(BCFichaFiltroDTO bcFichaFiltroDTO)  throws BiosLISDAOException{
        bcFichaFiltroDTO.setBo_nroDocumento(bcFichaFiltroDTO.getBo_nroDocumento().replace(".", ""));
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        bcFichaFiltroDTO.setBo_nroDocumento(bcFichaFiltroDTO.getBo_nroDocumento().replace(".", ""));
        String condicion= this.getSqlCondicion(bcFichaFiltroDTO);          
            condicion += " order by norden Asc";
     
        List<DatosLineaTiempoDTO> lineaTiempo;
        try {
            
         
          Query qry = sesion.createSQLQuery(sqlEstadisticaDatosLineaTiempo + condicion)                  
              .setResultTransformer(Transformers.aliasToBean(DatosLineaTiempoDTO.class));
          
          if (bcFichaFiltroDTO.getBo_fIni() != null && !bcFichaFiltroDTO.getBo_fIni().trim().isEmpty()) {         
              qry.setParameter("fini", bcFichaFiltroDTO.getBo_fIni());
            }
          if (bcFichaFiltroDTO.getBo_fFin() != null && !bcFichaFiltroDTO.getBo_fFin().trim().isEmpty()) {                   
              qry.setParameter("ffin", bcFichaFiltroDTO.getBo_fFin());
            }
            if (bcFichaFiltroDTO.getBo_nOrdenIni() != null) {
              qry.setParameter("nOrdenIni", bcFichaFiltroDTO.getBo_nOrdenIni());
            }
            if (bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
              qry.setParameter("nOrdenFin", bcFichaFiltroDTO.getBo_nOrdenFin());
                               
            }
            
            if (bcFichaFiltroDTO.getBo_nroDocumento() != null && !bcFichaFiltroDTO.getBo_nroDocumento().trim().isEmpty()) {
               qry.setParameter("nDoc",bcFichaFiltroDTO.getBo_nroDocumento() );
              }
            
            if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().equals("")) {
                qry.setParameter("apellido", bcFichaFiltroDTO.getBo_apellido());
              }
             if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
                 qry.setParameter("nombre", bcFichaFiltroDTO.getBo_nombre());
             }
              
            if (bcFichaFiltroDTO.getBo_procedencia() != null && bcFichaFiltroDTO.getBo_procedencia() != -1 )  {
                qry.setParameter("idProcedencia", bcFichaFiltroDTO.getBo_procedencia());
              }
            if (bcFichaFiltroDTO.getBo_tipoAtencion() != null && bcFichaFiltroDTO.getBo_tipoAtencion() != -1 )  {
                qry.setParameter("tipoAtencion", bcFichaFiltroDTO.getBo_tipoAtencion());
              }
            if (bcFichaFiltroDTO.getBo_localizacion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1 )  {
                qry.setParameter("idLocalizacion", bcFichaFiltroDTO.getBo_localizacion());
              }
            if (bcFichaFiltroDTO.getBo_servicio() != null && bcFichaFiltroDTO.getBo_servicio() != -1 )  {
                qry.setParameter("idServicio", bcFichaFiltroDTO.getBo_servicio());
              }
            if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1 )  {
                qry.setParameter("idExamen", bcFichaFiltroDTO.getBo_examen());
              }
   
            if (bcFichaFiltroDTO.getBo_seccion() != null && bcFichaFiltroDTO.getBo_seccion() != -1 )  {
                qry.setParameter("idSeccion", bcFichaFiltroDTO.getBo_seccion());
              }
            if (bcFichaFiltroDTO.getBo_laboratorio() != null && bcFichaFiltroDTO.getBo_laboratorio() != -1 )  {
                qry.setParameter("idLaboratorio", bcFichaFiltroDTO.getBo_laboratorio());
              }
            if (bcFichaFiltroDTO.getBo_convenio() != null && bcFichaFiltroDTO.getBo_convenio() != -1 )  {
                qry.setParameter("idConvenio", bcFichaFiltroDTO.getBo_convenio());
              }
                
          lineaTiempo = qry.list();

        } catch (HibernateException he) {
          logger.error("No se pudo obtener datos Estadistica lineaTiempo", he.getMessage());
          throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
          if (sesion.isOpen()) {
            sesion.close();
          }
        }
        return lineaTiempo;        
       
    }
    
    
    @SuppressWarnings("unchecked")
    public List<ExamenOrdenDTO> getEstadisticaTotalPorExamen(BCFichaFiltroDTO bcFichaFiltroDTO)  throws BiosLISDAOException{
        bcFichaFiltroDTO.setBo_nroDocumento(bcFichaFiltroDTO.getBo_nroDocumento().replace(".", ""));
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        bcFichaFiltroDTO.setBo_nroDocumento(bcFichaFiltroDTO.getBo_nroDocumento().replace(".", ""));
        String sCond = this.getSqlCondicion(bcFichaFiltroDTO);      
       sCond += " group by ce.ce_idexamen, ce.ce_codigoexamen , ce.ce_abreviado  , ce.ce_codigofonasa order by ce.ce_codigoexamen Asc";
    
        List<ExamenOrdenDTO> lineaTiempo;
       
        try {
        Query qry = sesion.createSQLQuery(sqlEstadisticaTotalPorExamen + sCond)                  
                .setResultTransformer(Transformers.aliasToBean(ExamenOrdenDTO.class));
        if (bcFichaFiltroDTO.getBo_fIni() != null && !bcFichaFiltroDTO.getBo_fIni().trim().isEmpty()) {          
            qry.setParameter("fini", bcFichaFiltroDTO.getBo_fIni());
          }
        if (bcFichaFiltroDTO.getBo_fFin() != null && !bcFichaFiltroDTO.getBo_fFin().trim().isEmpty()) {        
            qry.setParameter("ffin", bcFichaFiltroDTO.getBo_fFin());
          }
          if (bcFichaFiltroDTO.getBo_nOrdenIni() != null) {
            qry.setParameter("nOrdenIni", bcFichaFiltroDTO.getBo_nOrdenIni());
          }
          if (bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
            qry.setParameter("nOrdenFin", bcFichaFiltroDTO.getBo_nOrdenFin());
                             
          }
       
          if (bcFichaFiltroDTO.getBo_nroDocumento() != null && !bcFichaFiltroDTO.getBo_nroDocumento().trim().isEmpty()) {
             qry.setParameter("nDoc",bcFichaFiltroDTO.getBo_nroDocumento() );
            }
          
          if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().equals("")) {
              qry.setParameter("apellido", bcFichaFiltroDTO.getBo_apellido());
            }
           if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
               qry.setParameter("nombre", bcFichaFiltroDTO.getBo_nombre());
           }
            
          if (bcFichaFiltroDTO.getBo_procedencia() != null && bcFichaFiltroDTO.getBo_procedencia() != -1 )  {
              qry.setParameter("idProcedencia", bcFichaFiltroDTO.getBo_procedencia());
            }
          if (bcFichaFiltroDTO.getBo_tipoAtencion() != null && bcFichaFiltroDTO.getBo_tipoAtencion() != -1 )  {
              qry.setParameter("tipoAtencion", bcFichaFiltroDTO.getBo_tipoAtencion());
            }
          if (bcFichaFiltroDTO.getBo_localizacion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1 )  {
              qry.setParameter("idLocalizacion", bcFichaFiltroDTO.getBo_localizacion());
            }
          if (bcFichaFiltroDTO.getBo_servicio() != null && bcFichaFiltroDTO.getBo_servicio() != -1 )  {
              qry.setParameter("idServicio", bcFichaFiltroDTO.getBo_servicio());
            }
          if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1 )  {
              qry.setParameter("idExamen", bcFichaFiltroDTO.getBo_examen());
            }
 
          if (bcFichaFiltroDTO.getBo_seccion() != null && bcFichaFiltroDTO.getBo_seccion() != -1 )  {
              qry.setParameter("idSeccion", bcFichaFiltroDTO.getBo_seccion());
            }
          if (bcFichaFiltroDTO.getBo_laboratorio() != null && bcFichaFiltroDTO.getBo_laboratorio() != -1 )  {
              qry.setParameter("idLaboratorio", bcFichaFiltroDTO.getBo_laboratorio());
            }
          if (bcFichaFiltroDTO.getBo_convenio() != null && bcFichaFiltroDTO.getBo_convenio() != -1 )  {
              qry.setParameter("idConvenio", bcFichaFiltroDTO.getBo_convenio());
            }
              
       
          lineaTiempo = qry.list();

        } catch (HibernateException he) {
          logger.error("No se pudo obtener datos Estadistica TotalPorExamen", he.getMessage());
          throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
          if (sesion.isOpen()) {
            sesion.close();
          }
        }
        return lineaTiempo;       
       
    }
    
    
    @SuppressWarnings("unchecked")
    public List<MuestrasRMDTO> getEstadisticaMuestrasRechazadas(BCFichaFiltroDTO bcFichaFiltroDTO)  throws BiosLISDAOException{
        bcFichaFiltroDTO.setBo_nroDocumento(bcFichaFiltroDTO.getBo_nroDocumento().replace(".", ""));
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        bcFichaFiltroDTO.setBo_nroDocumento(bcFichaFiltroDTO.getBo_nroDocumento().replace(".", ""));
        String sCond= this.getSqlCondicion(bcFichaFiltroDTO);  
        List<MuestrasRMDTO> muestrasRechazadas;  
        
        try {
        Query qry = sesion.createSQLQuery(sqlEstadisticaMuestrasRechazadas + sCond + " order by dmr.dmr_fecharechazo  Asc" )                  
                .setResultTransformer(Transformers.aliasToBean(MuestrasRMDTO.class));
            
        if (bcFichaFiltroDTO.getBo_fIni() != null && !bcFichaFiltroDTO.getBo_fIni().trim().isEmpty()) {          
            qry.setParameter("fini", bcFichaFiltroDTO.getBo_fIni());
          }
        if (bcFichaFiltroDTO.getBo_fFin() != null && !bcFichaFiltroDTO.getBo_fFin().trim().isEmpty()) {         
            qry.setParameter("ffin", bcFichaFiltroDTO.getBo_fFin());
          }
          if (bcFichaFiltroDTO.getBo_nOrdenIni() != null) {
            qry.setParameter("nOrdenIni", bcFichaFiltroDTO.getBo_nOrdenIni());
          }
          if (bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
            qry.setParameter("nOrdenFin", bcFichaFiltroDTO.getBo_nOrdenFin());
                             
          }
          if (bcFichaFiltroDTO.getBo_nroDocumento() != null && !bcFichaFiltroDTO.getBo_nroDocumento().trim().isEmpty()) {
             qry.setParameter("nDoc",bcFichaFiltroDTO.getBo_nroDocumento() );
            }
          
          if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().equals("")) {
              qry.setParameter("apellido", bcFichaFiltroDTO.getBo_apellido());
            }
           if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
               qry.setParameter("nombre", bcFichaFiltroDTO.getBo_nombre());
           }
            
          if (bcFichaFiltroDTO.getBo_procedencia() != null && bcFichaFiltroDTO.getBo_procedencia() != -1 )  {
              qry.setParameter("idProcedencia", bcFichaFiltroDTO.getBo_procedencia());
            }
          if (bcFichaFiltroDTO.getBo_tipoAtencion() != null && bcFichaFiltroDTO.getBo_tipoAtencion() != -1 )  {
              qry.setParameter("tipoAtencion", bcFichaFiltroDTO.getBo_tipoAtencion());
            }
          if (bcFichaFiltroDTO.getBo_localizacion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1 )  {
              qry.setParameter("idLocalizacion", bcFichaFiltroDTO.getBo_localizacion());
            }
          if (bcFichaFiltroDTO.getBo_servicio() != null && bcFichaFiltroDTO.getBo_servicio() != -1 )  {
              qry.setParameter("idServicio", bcFichaFiltroDTO.getBo_servicio());
            }
          if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1 )  {
              qry.setParameter("idExamen", bcFichaFiltroDTO.getBo_examen());
            }
 
          if (bcFichaFiltroDTO.getBo_seccion() != null && bcFichaFiltroDTO.getBo_seccion() != -1 )  {
              qry.setParameter("idSeccion", bcFichaFiltroDTO.getBo_seccion());
            }
          if (bcFichaFiltroDTO.getBo_laboratorio() != null && bcFichaFiltroDTO.getBo_laboratorio() != -1 )  {
              qry.setParameter("idLaboratorio", bcFichaFiltroDTO.getBo_laboratorio());
            }
          if (bcFichaFiltroDTO.getBo_convenio() != null && bcFichaFiltroDTO.getBo_convenio() != -1 )  {
              qry.setParameter("idConvenio", bcFichaFiltroDTO.getBo_convenio());
            }        
         
         
           muestrasRechazadas = qry.list();

        } catch (HibernateException he) {
          logger.error("No se pudo obtener datos Estadistica Muestras Rechazadas", he.getMessage());
          throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
          if (sesion.isOpen()) {
            sesion.close();
          }
        }
        return muestrasRechazadas;       
       
    }
    
    

    @SuppressWarnings("unchecked")
    public List<ExamenOrdenDTO> getEstadisticaExamenesDerivados(BCFichaFiltroDTO bcFichaFiltroDTO)  throws BiosLISDAOException{
        bcFichaFiltroDTO.setBo_nroDocumento(bcFichaFiltroDTO.getBo_nroDocumento().replace(".", ""));
        Session sesion = HibernateUtil.getSessionFactory().openSession();
       
        String sCond= this.getSqlCondicion(bcFichaFiltroDTO) ;   
        List<ExamenOrdenDTO> examenesDerivados;
    
        try {
        Query qry = sesion.createSQLQuery(sqlEstadisticaExamenesDerivados + sCond + " AND (ce.ce_idderivador != 0 and ce.ce_idderivador is not null)   ORDER BY dfe.dfe_norden DESC " )                  
                .setResultTransformer(Transformers.aliasToBean(ExamenOrdenDTO.class));
            
        if (bcFichaFiltroDTO.getBo_fIni() != null && !bcFichaFiltroDTO.getBo_fIni().trim().isEmpty()) {        
            qry.setParameter("fini", bcFichaFiltroDTO.getBo_fIni());
          }
        if (bcFichaFiltroDTO.getBo_fFin() != null && !bcFichaFiltroDTO.getBo_fFin().trim().isEmpty()) {           
            qry.setParameter("ffin", bcFichaFiltroDTO.getBo_fFin());
          }
          if (bcFichaFiltroDTO.getBo_nOrdenIni() != null) {
            qry.setParameter("nOrdenIni", bcFichaFiltroDTO.getBo_nOrdenIni());
          }
          if (bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
            qry.setParameter("nOrdenFin", bcFichaFiltroDTO.getBo_nOrdenFin());
                             
          }
          if (bcFichaFiltroDTO.getBo_nroDocumento() != null && !bcFichaFiltroDTO.getBo_nroDocumento().trim().isEmpty()) {
             qry.setParameter("nDoc",bcFichaFiltroDTO.getBo_nroDocumento() );
            }
          
          if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().equals("")) {
              qry.setParameter("apellido", bcFichaFiltroDTO.getBo_apellido());
            }
           if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
               qry.setParameter("nombre", bcFichaFiltroDTO.getBo_nombre());
           }
            
          if (bcFichaFiltroDTO.getBo_procedencia() != null && bcFichaFiltroDTO.getBo_procedencia() != -1 )  {
              qry.setParameter("idProcedencia", bcFichaFiltroDTO.getBo_procedencia());
            }
          if (bcFichaFiltroDTO.getBo_tipoAtencion() != null && bcFichaFiltroDTO.getBo_tipoAtencion() != -1 )  {
              qry.setParameter("tipoAtencion", bcFichaFiltroDTO.getBo_tipoAtencion());
            }
          if (bcFichaFiltroDTO.getBo_localizacion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1 )  {
              qry.setParameter("idLocalizacion", bcFichaFiltroDTO.getBo_localizacion());
            }
          if (bcFichaFiltroDTO.getBo_servicio() != null && bcFichaFiltroDTO.getBo_servicio() != -1 )  {
              qry.setParameter("idServicio", bcFichaFiltroDTO.getBo_servicio());
            }
          if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1 )  {
              qry.setParameter("idExamen", bcFichaFiltroDTO.getBo_examen());
            }
 
          if (bcFichaFiltroDTO.getBo_seccion() != null && bcFichaFiltroDTO.getBo_seccion() != -1 )  {
              qry.setParameter("idSeccion", bcFichaFiltroDTO.getBo_seccion());
            }
          if (bcFichaFiltroDTO.getBo_laboratorio() != null && bcFichaFiltroDTO.getBo_laboratorio() != -1 )  {
              qry.setParameter("idLaboratorio", bcFichaFiltroDTO.getBo_laboratorio());
            }
          if (bcFichaFiltroDTO.getBo_convenio() != null && bcFichaFiltroDTO.getBo_convenio() != -1 )  {
              qry.setParameter("idConvenio", bcFichaFiltroDTO.getBo_convenio());
            }
         
         
            examenesDerivados = qry.list();

        } catch (HibernateException he) {
          logger.error("No se pudo obtener datos Estadistica Examenes Derivados", he.getMessage());
          throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
          if (sesion.isOpen()) {
            sesion.close();
          }
        }
        return examenesDerivados;       
       
    }
    
    

    @SuppressWarnings("unchecked")
    public List<TestEdicionOrdenDTO> getEstadisticaResultadosCriticos(BCFichaFiltroDTO bcFichaFiltroDTO)  throws BiosLISDAOException{
        bcFichaFiltroDTO.setBo_nroDocumento(bcFichaFiltroDTO.getBo_nroDocumento().replace(".", ""));    
        Session sesion = HibernateUtil.getSessionFactory().openSession();
       
        String sCond= this.getSqlCondicion(bcFichaFiltroDTO) ;   
        List<TestEdicionOrdenDTO> examenesDerivados;
       
        try {
     
        Query qry = sesion.createSQLQuery(sqlEstadisticaResultadosCriticos + sCond + " and (dfet.dfet_rcritico !='N' and dfet.dfet_rcritico is not null)  ORDER BY dfe.dfe_norden DESC " )                  
                .setResultTransformer(Transformers.aliasToBean(TestEdicionOrdenDTO.class));
            
        if (bcFichaFiltroDTO.getBo_fIni() != null && !bcFichaFiltroDTO.getBo_fIni().trim().isEmpty()) {        
            qry.setParameter("fini", bcFichaFiltroDTO.getBo_fIni());
          }
        if (bcFichaFiltroDTO.getBo_fFin() != null && !bcFichaFiltroDTO.getBo_fFin().trim().isEmpty()) {           
            qry.setParameter("ffin", bcFichaFiltroDTO.getBo_fFin());
          }
          if (bcFichaFiltroDTO.getBo_nOrdenIni() != null) {
            qry.setParameter("nOrdenIni", bcFichaFiltroDTO.getBo_nOrdenIni());
          }
          if (bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
            qry.setParameter("nOrdenFin", bcFichaFiltroDTO.getBo_nOrdenFin());
                             
          }
          if (bcFichaFiltroDTO.getBo_nroDocumento() != null && !bcFichaFiltroDTO.getBo_nroDocumento().trim().isEmpty()) {
             qry.setParameter("nDoc",bcFichaFiltroDTO.getBo_nroDocumento() );
            }
          
          if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().equals("")) {
              qry.setParameter("apellido", bcFichaFiltroDTO.getBo_apellido());
            }
           if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
               qry.setParameter("nombre", bcFichaFiltroDTO.getBo_nombre());
           }
            
          if (bcFichaFiltroDTO.getBo_procedencia() != null && bcFichaFiltroDTO.getBo_procedencia() != -1 )  {
              qry.setParameter("idProcedencia", bcFichaFiltroDTO.getBo_procedencia());
            }
          if (bcFichaFiltroDTO.getBo_tipoAtencion() != null && bcFichaFiltroDTO.getBo_tipoAtencion() != -1 )  {
              qry.setParameter("tipoAtencion", bcFichaFiltroDTO.getBo_tipoAtencion());
            }
          if (bcFichaFiltroDTO.getBo_localizacion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1 )  {
              qry.setParameter("idLocalizacion", bcFichaFiltroDTO.getBo_localizacion());
            }
          if (bcFichaFiltroDTO.getBo_servicio() != null && bcFichaFiltroDTO.getBo_servicio() != -1 )  {
              qry.setParameter("idServicio", bcFichaFiltroDTO.getBo_servicio());
            }
          if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1 )  {
              qry.setParameter("idExamen", bcFichaFiltroDTO.getBo_examen());
            }
 
          if (bcFichaFiltroDTO.getBo_seccion() != null && bcFichaFiltroDTO.getBo_seccion() != -1 )  {
              qry.setParameter("idSeccion", bcFichaFiltroDTO.getBo_seccion());
            }
          if (bcFichaFiltroDTO.getBo_laboratorio() != null && bcFichaFiltroDTO.getBo_laboratorio() != -1 )  {
              qry.setParameter("idLaboratorio", bcFichaFiltroDTO.getBo_laboratorio());
            }
          if (bcFichaFiltroDTO.getBo_convenio() != null && bcFichaFiltroDTO.getBo_convenio() != -1 )  {
              qry.setParameter("idConvenio", bcFichaFiltroDTO.getBo_convenio());
            }
    
            examenesDerivados = qry.list();

        } catch (HibernateException he) {
          logger.error("No se pudo obtener datos Estadistica resultados criticos", he.getMessage());
          throw new BiosLISDAOException(he.getLocalizedMessage());
        } finally {
          if (sesion.isOpen()) {
            sesion.close();
          }
        }
        return examenesDerivados;       
       
    }
    

    
    //CREA CONDICION DE FILTRO PARA LAS CONSULTAS ********************************************************
  private String getSqlCondicion(BCFichaFiltroDTO bcFichaFiltroDTO) {
      String sCond="";
      
      if (bcFichaFiltroDTO.getBo_nOrdenIni() != null && bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
          sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
          sCond += " df.DF_NORDEN <= :nOrdenFin AND ";
        } else {
          if (bcFichaFiltroDTO.getBo_nOrdenIni() != null || bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
            if (bcFichaFiltroDTO.getBo_nOrdenIni() != null) {
              sCond += " df.DF_NORDEN >= :nOrdenIni AND ";
              sCond += " df.DF_NORDEN <= :nOrdenIni AND ";
            }
            if (bcFichaFiltroDTO.getBo_nOrdenFin() != null) {
              sCond += " df.DF_NORDEN >= :nOrdenFin AND ";
              sCond += " df.DF_NORDEN <= :nOrdenFin AND ";
            }
          }
        }
        

        if ( bcFichaFiltroDTO.getBo_fIni().trim().equals("")) {
            bcFichaFiltroDTO.setBo_fIni(null);
        }
        if ( bcFichaFiltroDTO.getBo_fFin().trim().equals("")) {
            bcFichaFiltroDTO.setBo_fFin(null);
        }
        if (bcFichaFiltroDTO.getBo_procedencia() != null && bcFichaFiltroDTO.getBo_procedencia() != -1) {
          sCond += " df.DF_IDPROCEDENCIA = :idProcedencia AND ";
        }

        if ((bcFichaFiltroDTO.getBo_nOrdenIni() != null && bcFichaFiltroDTO.getBo_nOrdenFin() != null)
            || (bcFichaFiltroDTO.getBo_nOrdenIni() == null && bcFichaFiltroDTO.getBo_nOrdenFin() == null)) {

          if (bcFichaFiltroDTO.getBo_fIni() != null && bcFichaFiltroDTO.getBo_fFin() != null) {
            sCond += " df.DF_FECHAORDEN >= TO_DATE(:fini,'DD-MM-YYYY') AND ";
            sCond += " df.DF_FECHAORDEN <= TO_DATE(:ffin,'DD-MM-YYYY') + 1 AND ";
          } else {
            if (bcFichaFiltroDTO.getBo_fIni() != null || bcFichaFiltroDTO.getBo_fFin() != null) {
              if (bcFichaFiltroDTO.getBo_fIni() != null) {
                sCond += " df.DF_FECHAORDEN LIKE TO_DATE(:fini,'DD-MM-YYYY') AND ";
              }
              if (bcFichaFiltroDTO.getBo_fFin() != null) {
                sCond += " df.DF_FECHAORDEN  LIKE TO_DATE(:ffin,'DD-MM-YYYY') AND ";
              }
            }
          }
        }

        if (bcFichaFiltroDTO.getBo_apellido() != null && !bcFichaFiltroDTO.getBo_apellido().trim().equals("")) {
          sCond += " ( UPPER(dp.DP_PRIMERAPELLIDO) like concat(concat('%',UPPER(:apellido)),'%') OR "
              + " UPPER(dp.DP_SEGUNDOAPELLIDO) like concat(concat('%',UPPER(:apellido)), '%')AND ";
        }
        if (bcFichaFiltroDTO.getBo_nombre() != null && !bcFichaFiltroDTO.getBo_nombre().trim().isEmpty()) {
          sCond += " UPPER(dp.DP_NOMBRES) like   concat(concat('%',UPPER(:nombre)),'%') AND ";
        }

        if (bcFichaFiltroDTO.getBo_tipoDocumento() != null && bcFichaFiltroDTO.getBo_tipoDocumento() != -1) {
          sCond += " dp.DP_IDTIPODOCUMENTO = :tipoDoc AND ";
        }
        if (bcFichaFiltroDTO.getBo_nroDocumento() != null && !bcFichaFiltroDTO.getBo_nroDocumento().trim().isEmpty()) {
          sCond += " dp.DP_NRODOCUMENTO = :nDoc  AND ";
        }
        if (bcFichaFiltroDTO.getBo_tipoAtencion() != null && bcFichaFiltroDTO.getBo_tipoAtencion() != -1) {
          sCond += " df.DF_IDTIPOATENCION = :tipoAtencion AND ";
        }
        if (bcFichaFiltroDTO.getBo_localizacion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1) {
          sCond += " df.DF_IDLOCALIZACION = :idLocalizacion AND ";
        }

        if (bcFichaFiltroDTO.getBo_servicio() != null && bcFichaFiltroDTO.getBo_servicio() != -1) {
          sCond += " df.DF_IDSERVICIO = :idServicio AND ";
        }

        if (bcFichaFiltroDTO.getBo_examen() != null && bcFichaFiltroDTO.getBo_examen() != -1) {
          sCond += "ce.ce_CODIGOEXAMEN= :idExamen AND ";
        }
        if (bcFichaFiltroDTO.getBo_seccion() != null && bcFichaFiltroDTO.getBo_localizacion() != -1) {
          sCond += " ce.CE_IDSECCION= :idSeccion AND ";
        }
        if (bcFichaFiltroDTO.getBo_laboratorio() != null && bcFichaFiltroDTO.getBo_laboratorio() != -1) {
            sCond += " csec.csec_IDLABORATORIO= :idLaboratorio AND ";
          }
        if (bcFichaFiltroDTO.getBo_convenio() != null && bcFichaFiltroDTO.getBo_convenio() != -1) {
            sCond += " df.df_IDCONVENIO= :idConvenio AND ";
          }


        int pos = sCond.lastIndexOf("AND");

        if (pos != -1) {
          sCond = sCond.substring(0, pos);

        }

        if (!sCond.trim().equals("")) {
          sCond = "where " + sCond;
        }
        return sCond;
  }
  
}

