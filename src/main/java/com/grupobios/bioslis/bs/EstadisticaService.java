package com.grupobios.bioslis.bs;

import java.util.List;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.BCFichaFiltroDTO;
import com.grupobios.bioslis.dto.DatosLineaTiempoDTO;
import com.grupobios.bioslis.dto.ExamenOrdenDTO;
import com.grupobios.bioslis.dto.MuestrasRMDTO;
import com.grupobios.bioslis.dto.TestEdicionOrdenDTO;

public interface EstadisticaService {

  
   List<DatosLineaTiempoDTO> getEstadisticaDatosLineaTiempo(BCFichaFiltroDTO bcFichaFiltroDTO)  throws BiosLISDAOException ;
   List<ExamenOrdenDTO> getEstadisticaTotalPorExamen(BCFichaFiltroDTO bcFichaFiltroDTO)  throws BiosLISDAOException ;
   List<MuestrasRMDTO> getEstadisticaMuestrasRechazadas(BCFichaFiltroDTO bcFichaFiltroDTO)  throws BiosLISDAOException ;
   List<ExamenOrdenDTO> getEstadisticaExamenesDerivados(BCFichaFiltroDTO bcFichaFiltroDTO)  throws BiosLISDAOException ;
   List<TestEdicionOrdenDTO> getEstadisticaResultadosCriticos(BCFichaFiltroDTO bcFichaFiltroDTO)  throws BiosLISDAOException ;
}
