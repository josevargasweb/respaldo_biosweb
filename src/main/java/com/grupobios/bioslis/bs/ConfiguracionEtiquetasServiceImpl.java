package com.grupobios.bioslis.bs;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgEtiquetaCodigoBarrasDAO;
import com.grupobios.bioslis.dto.EtiquetaCodigoBarraDTO;
import com.grupobios.bioslis.entity.CfgEtiquetacodigobarras;

public class ConfiguracionEtiquetasServiceImpl implements ConfiguracionEtiquetasService {

  private static Logger logger = LogManager.getLogger(ConfiguracionEtiquetasServiceImpl.class);

  @Autowired
  CfgEtiquetaCodigoBarrasDAO cfgEtiquetaCodigoBarrasDAO;

  public CfgEtiquetaCodigoBarrasDAO getCfgEtiquetaCodigoBarrasDAO() {
    return cfgEtiquetaCodigoBarrasDAO;
  }

  public void setCfgEtiquetaCodigoBarrasDAO(CfgEtiquetaCodigoBarrasDAO cfgEtiquetaCodigoBarrasDAO) {
    this.cfgEtiquetaCodigoBarrasDAO = cfgEtiquetaCodigoBarrasDAO;
  }

  @Override
  public List<EtiquetaCodigoBarraDTO> getAllEtiquetas() throws BiosLISDAOException {
    return cfgEtiquetaCodigoBarrasDAO.getAllEtiquetas();
  }

  @Override
  public EtiquetaCodigoBarraDTO getEtiquetaBycodigo(String codigo) throws BiosLISDAOException {
    return cfgEtiquetaCodigoBarrasDAO.getEtiquetaBycodigo(codigo);
  }

  @Override
  public void insertUpdateEtiqueta(CfgEtiquetacodigobarras etiquetaCodigoBarra, String idUsuario)
      throws BiosLISDAOException {
    cfgEtiquetaCodigoBarrasDAO.insertUpdateEtiqueta(etiquetaCodigoBarra, idUsuario);
  }

  @Override
  public EtiquetaCodigoBarraDTO getConfigEtiquetaxCodigo(String codigo) throws BiosLISDAOException {

    EtiquetaCodigoBarraDTO configEtiqueta = cfgEtiquetaCodigoBarrasDAO.getEtiquetaBycodigo(codigo);
    logger.debug("Configuración etiqueta %s ", configEtiqueta);
    return configEtiqueta;
  }

}
