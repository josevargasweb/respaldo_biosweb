/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.BiosLISDAONotFoundException;
import com.grupobios.bioslis.dao.CfgMetodosDAO;
import com.grupobios.bioslis.dao.CfgTestDAO;
import com.grupobios.bioslis.dao.CfgValoresreferenciaDAO;
import com.grupobios.bioslis.dao.LogCfgTablasDAO;
import com.grupobios.bioslis.entity.CfgTest;
import com.grupobios.bioslis.entity.CfgValoresreferencia;
import com.grupobios.bioslis.entity.LogCfgtablas;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marco Caracciolo
 */
@Service
public class ValoresReferenciaServiceImpl implements ValoresReferenciaService {

    CfgValoresreferenciaDAO cvrDAO;
    CfgMetodosDAO cfgMetodosDAO;
    LogCfgTablasDAO logCfgTablasDAO;
    
    

    public LogCfgTablasDAO getLogCfgTablasDAO() {
		return logCfgTablasDAO;
	}

	public void setLogCfgTablasDAO(LogCfgTablasDAO logCfgTablasDAO) {
		this.logCfgTablasDAO = logCfgTablasDAO;
	}

	public CfgValoresreferenciaDAO getCvrDAO() {
        return cvrDAO;
    }

    public void setCvrDAO(CfgValoresreferenciaDAO cvrDAO) {
        this.cvrDAO = cvrDAO;
    }

    public CfgMetodosDAO getCfgMetodosDAO() {
        return cfgMetodosDAO;
    }

    public void setCfgMetodosDAO(CfgMetodosDAO cfgMetodosDAO) {
        this.cfgMetodosDAO = cfgMetodosDAO;
    }
    
    @Override
    public List<CfgValoresreferencia> getValoresReferenciaByTest(long cvrIdtest) throws BiosLISDAOException {
        return cvrDAO.getValoresReferenciasByTest(cvrIdtest);
    }

    @Override
    public void guardarValoresReferencia(List<CfgValoresreferencia> listVR, Long idUsuario) throws BiosLISDAOException {
    	CfgTestDAO testDao = new CfgTestDAO();
    	
        for (CfgValoresreferencia cvr: listVR){
        	CfgTest test = new CfgTest();
			try {
				test = testDao.getTestById((int) cvr.getCvrIdtest());
			} catch (BiosLISDAONotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BiosLISDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (cvr.getCvrIdvalorreferencia()==0){            	
                cvrDAO.insertValoresReferencias(cvr, idUsuario);
                
                
                LogCfgtablas logTabla = new LogCfgtablas();
                logTabla.setCfgAccionesdatos(2);
                logTabla.setLctIdusuario(idUsuario.intValue());
                logTabla.setLctNombretabla("CFG_VALORESREFERENCIA");
                logTabla.setLctNombrecampo("CVR_IDVALORREFERENCIA");        
                logTabla.setLctValornuevo(String.valueOf(cvr.getCvrIdvalorreferencia()));
                logTabla.setLctIdtabla((int) cvr.getCvrIdtest());
                logTabla.setLctDescripcion(test.getCtAbreviado());
                logCfgTablasDAO.insertLogTablas(logTabla);
              //********************************************************************

              
               
            } else {
            	
            	 CfgValoresreferencia cvrAntiguo = cvrDAO.getValorReferenciaById(cvr.getCvrIdvalorreferencia());
                cvrDAO.updateValoresReferencias(cvr);
                logCfgTablasDAO.compararTablasValoresDeReferencia(cvr, cvrAntiguo, idUsuario, test.getCtAbreviado());
                
            }
        }
    }

    @Override
    public void deleteValorReferencia(int idVr, Long idUsuario) throws BiosLISDAOException {
    	CfgTestDAO testDao = new CfgTestDAO();
        CfgValoresreferencia cvr = cvrDAO.getValorReferenciaById(idVr);        
        cvrDAO.deleteValoresReferencias(cvr);
        //Se agrega eliminacion valoresreferencia a log tablas **********************    
        CfgTest test = new CfgTest();
		try {
			test = testDao.getTestById((int) cvr.getCvrIdtest());
		} catch (BiosLISDAONotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiosLISDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        LogCfgtablas logTabla = new LogCfgtablas();
        logTabla.setCfgAccionesdatos(3);
        logTabla.setLctIdusuario(idUsuario.intValue());
        logTabla.setLctNombretabla("CFG_VALORESREFERENCIA");
        logTabla.setLctNombrecampo("CVR_IDVALORREFERENCIA");        
        logTabla.setLctValoranterior(String.valueOf(cvr.getCvrIdvalorreferencia()));
        logTabla.setLctIdtabla((int) cvr.getCvrIdtest());
        logTabla.setLctDescripcion(test.getCtAbreviado());
        logCfgTablasDAO.insertLogTablas(logTabla);
      //********************************************************************
    }
    
}
