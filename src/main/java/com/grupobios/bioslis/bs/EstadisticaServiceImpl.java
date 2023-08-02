package com.grupobios.bioslis.bs;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grupobios.bioslis.back.api.EstadisticaRestController;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.EstadisticaDAO;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.DatosLineaTiempoDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.MuestrasRMDTO;
import com.grupobios.bioslis.dto.TestEdicionOrdenDTO;


//creado por cristian para portal estaditica 22-11 **********************************
public class EstadisticaServiceImpl implements EstadisticaService {

    private static Logger logger = LogManager.getLogger(EstadisticaServiceImpl.class);
    private EstadisticaServiceImpl() {
        
    }
    
    private EstadisticaDAO estadisticaDAO;

    public EstadisticaDAO getEstadisticaDAO() {
        return estadisticaDAO;
    }

    public void setEstadisticaDAO(EstadisticaDAO estadisticaDAO) {
        this.estadisticaDAO = estadisticaDAO;
    }

    @Override
    public List<DatosLineaTiempoDTO> getEstadisticaDatosLineaTiempo(BCFichaFiltroDTO bcFichaFiltroDTO) throws BiosLISDAOException {       
        return estadisticaDAO.getEstadisticaDatosLineaTiempo(bcFichaFiltroDTO);
    }

    @Override
    public List<ExamenOrdenDTO> getEstadisticaTotalPorExamen(BCFichaFiltroDTO bcFichaFiltroDTO)
            throws BiosLISDAOException {        
        return estadisticaDAO.getEstadisticaTotalPorExamen(bcFichaFiltroDTO);
    }

    @Override
    public List<MuestrasRMDTO> getEstadisticaMuestrasRechazadas(BCFichaFiltroDTO bcFichaFiltroDTO)
            throws BiosLISDAOException {
        // TODO Auto-generated method stub
        return estadisticaDAO.getEstadisticaMuestrasRechazadas(bcFichaFiltroDTO);
    }

    @Override
    public List<ExamenOrdenDTO> getEstadisticaExamenesDerivados(BCFichaFiltroDTO bcFichaFiltroDTO)
            throws BiosLISDAOException {
        // TODO Auto-generated method stub
        return estadisticaDAO.getEstadisticaExamenesDerivados(bcFichaFiltroDTO);
    }

    @Override
    public List<TestEdicionOrdenDTO> getEstadisticaResultadosCriticos(BCFichaFiltroDTO bcFichaFiltroDTO)
            throws BiosLISDAOException {
        // TODO Auto-generated method stub
        return estadisticaDAO.getEstadisticaResultadosCriticos(bcFichaFiltroDTO);
    }
    
    
    
    
}
