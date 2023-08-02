package com.grupobios.bioslis.bs;

import java.util.List;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dto.EtiquetaCodigoBarraDTO;
import com.grupobios.bioslis.entity.CfgEtiquetacodigobarras;

// *** realizado por cristian
public interface ConfiguracionEtiquetasService {

  public List<EtiquetaCodigoBarraDTO> getAllEtiquetas() throws BiosLISDAOException;

  public EtiquetaCodigoBarraDTO getEtiquetaBycodigo(String codigo) throws BiosLISDAOException;

  public void insertUpdateEtiqueta(CfgEtiquetacodigobarras etiquetaCodigoBarra, String idUsuario)
      throws BiosLISDAOException;

  public EtiquetaCodigoBarraDTO getConfigEtiquetaxCodigo(String codigo) throws BiosLISDAOException;;
}
