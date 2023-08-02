/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgMuestrasPrefijosDAO;
import com.grupobios.bioslis.dao.CfgTiposderesultadoDAO;
import com.grupobios.bioslis.dao.LdvOperacionesMathDAO;
import com.grupobios.bioslis.dao.LdvTipoComunicacionDAO;
import com.grupobios.bioslis.dao.LdvTiposCondicionDAO;
import com.grupobios.bioslis.dao.LogCfgTablasDAO;
import com.grupobios.bioslis.dao.SigmaProcesosAlarmasDAO;
import com.grupobios.bioslis.dao.SigmaProcesosDAO;
import com.grupobios.bioslis.dao.SigmaProcesosTestConversionDAO;
import com.grupobios.bioslis.dao.SigmaProcesosTestDAO;
import com.grupobios.bioslis.dao.SigmaTiposMuestrasDAO;
import com.grupobios.bioslis.dto.ProcesosDTO;
import com.grupobios.bioslis.dto.ProcesosTestsConversionDTO;
import com.grupobios.bioslis.dto.ProcesosTestsDTO;
import com.grupobios.bioslis.entity.CfgMuestrasprefijos;
import com.grupobios.bioslis.entity.CfgTiposderesultado;
import com.grupobios.bioslis.entity.LdvOperacionesmath;
import com.grupobios.bioslis.entity.LdvTipocomunicacion;
import com.grupobios.bioslis.entity.LdvTiposcondicion;
import com.grupobios.bioslis.entity.SigmaProcesos;
import com.grupobios.bioslis.entity.SigmaProcesosalarmas;
import com.grupobios.bioslis.entity.SigmaProcesostest;
import com.grupobios.bioslis.entity.SigmaProcesotestconversion;
import com.grupobios.bioslis.entity.SigmaProcesotestconversionId;
import com.grupobios.bioslis.entity.SigmaTiposmuestras;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marco Caracciolo
 */
@Service
public class ProcesosServiceImpl implements ProcesosService{

    private static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(ProcesosServiceImpl.class);
    SigmaProcesosDAO procesosDAO;
    SigmaProcesosTestDAO procesosTestDAO;
    LdvTipoComunicacionDAO tipoComunicacionDAO;
    SigmaTiposMuestrasDAO sigmaTiposMuestrasDAO;
    LdvTiposCondicionDAO tiposCondicionDAO;
    LdvOperacionesMathDAO operacionesMathDAO;
    CfgTiposderesultadoDAO cfgTiposderesultadoDAO;
    CfgMuestrasPrefijosDAO muestrasPrefijosDAO;
    SigmaProcesosTestConversionDAO procesosTestConversionDAO;
    SigmaProcesosAlarmasDAO procesosAlarmasDAO;

    //GETTERS Y SETTERS
    public SigmaProcesosDAO getProcesosDAO() {
        return procesosDAO;
    }

    public void setProcesosDAO(SigmaProcesosDAO procesosDAO) {
        this.procesosDAO = procesosDAO;
    }

    public SigmaProcesosTestDAO getProcesosTestDAO() {
        return procesosTestDAO;
    }

    public void setProcesosTestDAO(SigmaProcesosTestDAO procesosTestDAO) {
        this.procesosTestDAO = procesosTestDAO;
    }

    public LdvTipoComunicacionDAO getTipoComunicacionDAO() {
        return tipoComunicacionDAO;
    }

    public void setTipoComunicacionDAO(LdvTipoComunicacionDAO tipoComunicacionDAO) {
        this.tipoComunicacionDAO = tipoComunicacionDAO;
    }

    public SigmaTiposMuestrasDAO getSigmaTiposMuestrasDAO() {
        return sigmaTiposMuestrasDAO;
    }

    public void setSigmaTiposMuestrasDAO(SigmaTiposMuestrasDAO sigmaTiposMuestrasDAO) {
        this.sigmaTiposMuestrasDAO = sigmaTiposMuestrasDAO;
    }

    public LdvTiposCondicionDAO getTiposCondicionDAO() {
        return tiposCondicionDAO;
    }

    public void setTiposCondicionDAO(LdvTiposCondicionDAO tiposCondicionDAO) {
        this.tiposCondicionDAO = tiposCondicionDAO;
    }

    public LdvOperacionesMathDAO getOperacionesMathDAO() {
        return operacionesMathDAO;
    }

    public void setOperacionesMathDAO(LdvOperacionesMathDAO operacionesMathDAO) {
        this.operacionesMathDAO = operacionesMathDAO;
    }

    public CfgTiposderesultadoDAO getCfgTiposderesultadoDAO() {
        return cfgTiposderesultadoDAO;
    }

    public void setCfgTiposderesultadoDAO(CfgTiposderesultadoDAO cfgTiposderesultadoDAO) {
        this.cfgTiposderesultadoDAO = cfgTiposderesultadoDAO;
    }

    public CfgMuestrasPrefijosDAO getMuestrasPrefijosDAO() {
        return muestrasPrefijosDAO;
    }

    public void setMuestrasPrefijosDAO(CfgMuestrasPrefijosDAO muestrasPrefijosDAO) {
        this.muestrasPrefijosDAO = muestrasPrefijosDAO;
    }

    public SigmaProcesosTestConversionDAO getProcesosTestConversionDAO() {
        return procesosTestConversionDAO;
    }

    public void setProcesosTestConversionDAO(SigmaProcesosTestConversionDAO procesosTestConversionDAO) {
        this.procesosTestConversionDAO = procesosTestConversionDAO;
    }

    public SigmaProcesosAlarmasDAO getProcesosAlarmasDAO() {
        return procesosAlarmasDAO;
    }

    public void setProcesosAlarmasDAO(SigmaProcesosAlarmasDAO procesosAlarmasDAO) {
        this.procesosAlarmasDAO = procesosAlarmasDAO;
    }
    //END GETTERS Y SETTERS
    
    @Override
    public List<SigmaProcesos> getProcesosFiltro(HashMap<String, String> filtros) throws BiosLISDAOException {
        String codigo = filtros.get("codigo").toUpperCase();
        String nombre = filtros.get("nombre").toUpperCase();
        short idSeccion = Short.parseShort(filtros.get("idSeccion"));
        short idEstacion = Short.parseShort(filtros.get("idEstacion"));
        String activo = filtros.get("activo");
        return procesosDAO.getProcesosFiltro(codigo, nombre, idSeccion, idEstacion, activo);
    }

    @Override
    public ProcesosDTO getProcesoById(String codigoProceso) throws BiosLISDAOException {
        ProcesosDTO procesosDTO = new ProcesosDTO();
        SigmaProcesos sp = procesosDAO.getProcesoById(codigoProceso);
        if (sp.getLdvTipocomunicacion() != null){
            LdvTipocomunicacion ltc = tipoComunicacionDAO.getTipoComunicacionById(sp.getLdvTipocomunicacion().getLdvtcIdtipocomunicacion());
            procesosDTO.setLdvTipocomunicacion(ltc);
        }
        List<SigmaProcesostest> listaProcesosTest = procesosTestDAO.getProcesosTestById(sp.getSpIdproceso());
        List<ProcesosTestsDTO> listaProcesosTestDTO = new ArrayList<>();
        for (SigmaProcesostest spt: listaProcesosTest){
            ProcesosTestsDTO pTestsDTO = new ProcesosTestsDTO();
            pTestsDTO.setSigmaProcesostest(spt);
            pTestsDTO.setIdTest(spt.getCfgTest().getCtIdtest());
            if (spt.getSigmaTiposmuestras()!=null){
                pTestsDTO.setIdSigmaTipoMuestra(spt.getSigmaTiposmuestras().getStmIdsigmatipomuestra());
            }
            if (spt.getCfgTiposderesultado()!=null){
                pTestsDTO.setIdtiporesultado(spt.getCfgTiposderesultado().getCtrIdtiporesultado());
            }
            if (spt.getCfgMuestrasprefijos()!=null){
                pTestsDTO.setPrefijoTipoMuestra(spt.getCfgMuestrasprefijos().getCmuepPrefijotipomuestra());
            }
            
            List<SigmaProcesotestconversion> listConversiones = procesosTestConversionDAO.getConversionesFromProcesoTest(sp.getSpIdproceso(), spt.getCfgTest().getCtIdtest());
            List<ProcesosTestsConversionDTO> listConversionesDTO = new ArrayList<>();
            for (SigmaProcesotestconversion sptc : listConversiones){
                ProcesosTestsConversionDTO ptcdto = new ProcesosTestsConversionDTO();
                if (sptc.getLdvOperacionesmath()!=null){
                    ptcdto.setLdvomIdoperadormath(sptc.getLdvOperacionesmath().getLdvomIdoperadormath());
                } else {
                    ptcdto.setLdvomIdoperadormath(Byte.valueOf("0"));
                }
                if (sptc.getLdvTiposcondicion()!=null){
                    ptcdto.setLdvtcondIdtipocondicion(sptc.getLdvTiposcondicion().getLdvtcondIdtipocondicion());
                } else {
                    ptcdto.setLdvtcondIdtipocondicion(Byte.valueOf("0"));
                }
                ptcdto.setSptcNumerocondicion(sptc.getId().getSptcNumerocondicion());
                ptcdto.setSptcBloquearsn(sptc.getSptcBloquearsn());
                ptcdto.setSptcCalculosn(sptc.getSptcCalculosn());
                ptcdto.setSptcCondicionalsn(sptc.getSptcCondicionalsn());
                ptcdto.setSptcCriticosn(sptc.getSptcCriticosn());
                ptcdto.setSptcFirmarsn(sptc.getSptcFirmarsn());
                ptcdto.setSptcReprocesarsn(sptc.getSptcReprocesarsn());
                ptcdto.setSptcValorentrada(sptc.getSptcValorentrada());
                ptcdto.setSptcValoroperando(sptc.getSptcValoroperando());
                ptcdto.setSptcValorsalida(sptc.getSptcValorsalida());
                listConversionesDTO.add(ptcdto);
            }
            ProcesosTestsConversionDTO[] conversionesDTOs = listConversionesDTO.toArray(new ProcesosTestsConversionDTO[0]);
            pTestsDTO.setConversiones(listConversionesDTO.toArray(conversionesDTOs));
            listaProcesosTestDTO.add(pTestsDTO);
        }
        ProcesosTestsDTO[] procesosTestsDTOs = listaProcesosTestDTO.toArray(new ProcesosTestsDTO[0]);
        procesosDTO.setProcesosTests(listaProcesosTestDTO.toArray(procesosTestsDTOs));
        
        procesosDTO.setSigmaProcesos(sp);
        return procesosDTO;
    }

    @Override
    public String insertProceso(ProcesosDTO procesoDTO, Long idUsuario) throws BiosLISDAOException {
        //insertar procesos
        SigmaProcesos sp = procesoDTO.getSigmaProcesos();
        String codigoUpper = sp.getSpCodigoproceso().toUpperCase();
        sp.setSpCodigoproceso(codigoUpper);
        String nombreUpper = sp.getSpDescripcion().toUpperCase();
        sp.setSpDescripcion(nombreUpper);
        sp.setSpIdproceso(procesosDAO.obtenerIdSecuencia());
        if (procesoDTO.getLdvTipocomunicacion().getLdvtcIdtipocomunicacion() > 0){
            sp.setLdvTipocomunicacion(procesoDTO.getLdvTipocomunicacion());
        }
        String msj = procesosDAO.insertProceso(sp, idUsuario);
        
        //insertar procesos tests
        ProcesosTestsDTO[] ptDtos = procesoDTO.getProcesosTests();
        for (ProcesosTestsDTO ptd: ptDtos){
            SigmaProcesostest sigmaProcesostest = ptd.getSigmaProcesostest();
            sigmaProcesostest.getId().setSptIdproceso(sp.getSpIdproceso());
            sigmaProcesostest.setSigmaProcesos(sp);
            if (ptd.getIdtiporesultado()>0){
                CfgTiposderesultado tipoResultado = cfgTiposderesultadoDAO.getTipoResultadoById(ptd.getIdtiporesultado());
                sigmaProcesostest.setCfgTiposderesultado(tipoResultado);
            }
            if (ptd.getIdSigmaTipoMuestra()>0){
                SigmaTiposmuestras sigmaTiposmuestras = sigmaTiposMuestrasDAO.getSigmaTiposMuestrasById(ptd.getIdSigmaTipoMuestra());
                sigmaProcesostest.setSigmaTiposmuestras(sigmaTiposmuestras);
            }
            if (!"".equals(ptd.getPrefijoTipoMuestra())){
                if (muestrasPrefijosDAO.existeMuestraPrefijo(ptd.getPrefijoTipoMuestra())){
                    CfgMuestrasprefijos muestraPrefijo = muestrasPrefijosDAO.getMuestraPrefijo(ptd.getPrefijoTipoMuestra());
                    sigmaProcesostest.setCfgMuestrasprefijos(muestraPrefijo);
                } else {
                    CfgMuestrasprefijos newMuestra = new CfgMuestrasprefijos();
                    newMuestra.setCmuepPrefijotipomuestra(ptd.getPrefijoTipoMuestra());
                    newMuestra.setCmuepIdtipomuestra(ptd.getCmueIdtipomuestra());
                    muestrasPrefijosDAO.insertPrefijo(newMuestra);
                }
                
            }
            procesosTestDAO.insertProcesotest(sigmaProcesostest, idUsuario);
            
            //insertar conversiones procesos tests
            if (ptd.getConversiones()!=null){
                ProcesosTestsConversionDTO[] conversionesDTO = ptd.getConversiones();
                for (ProcesosTestsConversionDTO ptcd: conversionesDTO){
                    SigmaProcesotestconversionId sptcId = new SigmaProcesotestconversionId();
                    sptcId.setSptcIdproceso(sp.getSpIdproceso());
                    sptcId.setSptcIdtest(sigmaProcesostest.getId().getSptIdtest());
                    sptcId.setSptcNumerocondicion(ptcd.getSptcNumerocondicion());
                    LdvTiposcondicion ldvTiposcondicion = null;
                    if (ptcd.getLdvtcondIdtipocondicion() > 0){
                        ldvTiposcondicion = tiposCondicionDAO.getTipoCondicionById(ptcd.getLdvtcondIdtipocondicion());
                    }
                    LdvOperacionesmath ldvOperacionesmath = null;
                    if (ptcd.getLdvomIdoperadormath() > 0){
                        ldvOperacionesmath = operacionesMathDAO.getOperacionesMathById(ptcd.getLdvomIdoperadormath());
                    }
                    SigmaProcesotestconversion sigmaProcesotestconversion = ptcd.toSigmaProcesotestconversion(sptcId, sigmaProcesostest, ldvTiposcondicion, ldvOperacionesmath);
                    procesosTestConversionDAO.insertProceso(sigmaProcesotestconversion, idUsuario);                
                }
            }
        }
        
        SigmaProcesosalarmas[] procesosalarmas = procesoDTO.getProcesosAlarmas();
        for (SigmaProcesosalarmas alarma : procesosalarmas){
            alarma.setSigmaProcesos(sp);
            procesosAlarmasDAO.insertAlarma(alarma, idUsuario);
        }
        
        return msj;
    }

    @Override
    public void updateProceso(ProcesosDTO procesoDTO, Long idUsuario) throws BiosLISDAOException {
        //Editar procesos
        SigmaProcesos sp = procesoDTO.getSigmaProcesos();
        if (procesoDTO.getLdvTipocomunicacion().getLdvtcIdtipocomunicacion() > 0){
            sp.setLdvTipocomunicacion(procesoDTO.getLdvTipocomunicacion());
        }
        
        //Editar procesos test
        ProcesosTestsDTO[] ptDtos = procesoDTO.getProcesosTests();
        for (ProcesosTestsDTO ptd: ptDtos){
            SigmaProcesostest sigmaProcesostest = ptd.getSigmaProcesostest();
            sigmaProcesostest.getId().setSptIdproceso(sp.getSpIdproceso());
            sigmaProcesostest.setSigmaProcesos(sp);
            if (ptd.getIdtiporesultado()>0){
                CfgTiposderesultado tipoResultado = cfgTiposderesultadoDAO.getTipoResultadoById(ptd.getIdtiporesultado());
                sigmaProcesostest.setCfgTiposderesultado(tipoResultado);
            }           
            if (ptd.getIdSigmaTipoMuestra()>0){
                SigmaTiposmuestras sigmaTiposmuestras = sigmaTiposMuestrasDAO.getSigmaTiposMuestrasById(ptd.getIdSigmaTipoMuestra());
                sigmaProcesostest.setSigmaTiposmuestras(sigmaTiposmuestras);
            }
            if (!"".equals(ptd.getPrefijoTipoMuestra())){
                if (muestrasPrefijosDAO.existeMuestraPrefijo(ptd.getPrefijoTipoMuestra())){
                    CfgMuestrasprefijos muestraPrefijo = muestrasPrefijosDAO.getMuestraPrefijo(ptd.getPrefijoTipoMuestra());
                    sigmaProcesostest.setCfgMuestrasprefijos(muestraPrefijo);
                } else {
                    CfgMuestrasprefijos newMuestra = new CfgMuestrasprefijos();
                    newMuestra.setCmuepPrefijotipomuestra(ptd.getPrefijoTipoMuestra());
                    newMuestra.setCmuepIdtipomuestra(ptd.getCmueIdtipomuestra());
                    muestrasPrefijosDAO.insertPrefijo(newMuestra);
                    sigmaProcesostest.setCfgMuestrasprefijos(newMuestra);
                }
                
            }
            if (!procesosTestDAO.existeProcesoTest(sp.getSpIdproceso(), ptd.getIdTest())){
                procesosTestDAO.insertProcesotest(sigmaProcesostest, idUsuario);
            } else {
                procesosTestDAO.updateProcesotest(sigmaProcesostest, idUsuario);
            }
            
            //Editar conversiones procesos test
            if (ptd.getConversiones()!=null){           	
                ProcesosTestsConversionDTO[] conversionesDTO = ptd.getConversiones();
                for (ProcesosTestsConversionDTO ptcd: conversionesDTO){
                	
                	SigmaProcesotestconversion conversionAntigua = procesosTestConversionDAO.getConversionesFromProcesoTest( sp.getSpIdproceso(),sigmaProcesostest.getId().getSptIdtest() , ptcd.getSptcNumerocondicion());
                			
                    SigmaProcesotestconversionId sptcId = new SigmaProcesotestconversionId();
                    sptcId.setSptcIdproceso(sp.getSpIdproceso());
                    sptcId.setSptcIdtest(sigmaProcesostest.getId().getSptIdtest());
                    sptcId.setSptcNumerocondicion(ptcd.getSptcNumerocondicion());
                    LdvTiposcondicion ldvTiposcondicion = null;
                    if (ptcd.getLdvtcondIdtipocondicion() > 0){
                        ldvTiposcondicion = tiposCondicionDAO.getTipoCondicionById(ptcd.getLdvtcondIdtipocondicion());
                    }
                    LdvOperacionesmath ldvOperacionesmath = null;
                    if (ptcd.getLdvomIdoperadormath() > 0){
                        ldvOperacionesmath = operacionesMathDAO.getOperacionesMathById(ptcd.getLdvomIdoperadormath());
                    }
                    SigmaProcesotestconversion sigmaProcesotestconversion = ptcd.toSigmaProcesotestconversion(sptcId, sigmaProcesostest, ldvTiposcondicion, ldvOperacionesmath);
                    
                    if (!procesosTestConversionDAO.existeConversionProcesoTest(sp.getSpIdproceso(), ptd.getIdTest(), ptcd.getSptcNumerocondicion())){
                        procesosTestConversionDAO.insertProceso(sigmaProcesotestconversion, idUsuario);
                        
                    } else {
                        procesosTestConversionDAO.updateProceso(sigmaProcesotestconversion);
                        LogCfgTablasDAO log = new  LogCfgTablasDAO();
                        log.compararTablasSigmaConversion(sigmaProcesotestconversion, conversionAntigua, idUsuario, sp.getSpDescripcion());
                    }
                }
            }
        }
        
        SigmaProcesosalarmas[] procesosalarmas = procesoDTO.getProcesosAlarmas();
        for (SigmaProcesosalarmas alarma : procesosalarmas){
            alarma.setSigmaProcesos(sp);
            if (alarma.getSpaIdprocesoalarma() == 0){
                procesosAlarmasDAO.insertAlarma(alarma, idUsuario);
            } else {
                procesosAlarmasDAO.updateAlarma(alarma, idUsuario);
                
            }
        }
        
        String codigoUpper = sp.getSpCodigoproceso().toUpperCase();
        sp.setSpCodigoproceso(codigoUpper);
        String nombreUpper = sp.getSpDescripcion().toUpperCase();
        sp.setSpDescripcion(nombreUpper);
        procesosDAO.updateProceso(sp, idUsuario);
    }

    @Override
    public List<LdvTipocomunicacion> getListTiposComunicaciones() throws BiosLISDAOException {
        return tipoComunicacionDAO.getTiposComunicaciones();
    }

    @Override
    public List<SigmaTiposmuestras> getListTiposMuestras() throws BiosLISDAOException {
        return sigmaTiposMuestrasDAO.listSigmaTiposMuestras();
    }

    @Override
    public List<LdvTiposcondicion> getListTiposCondicion() throws BiosLISDAOException {
        return tiposCondicionDAO.listTiposCondicion();
    }

    @Override
    public List<LdvOperacionesmath> getListOperacionesMath() throws BiosLISDAOException {
        return operacionesMathDAO.listOperacionesMath();
    }

    @Override
    public List<SigmaProcesos> getProcesoByCodigo(String codigoProceso) throws BiosLISDAOException {
        return procesosDAO.getProcesoByCodigo(codigoProceso);
    }

    @Override
    public List<SigmaProcesosalarmas> getAlarmasByCodigoProceso(String codigoProceso) throws BiosLISDAOException {
        return procesosAlarmasDAO.getAlarmasByProceso(codigoProceso);
    }
    
}
