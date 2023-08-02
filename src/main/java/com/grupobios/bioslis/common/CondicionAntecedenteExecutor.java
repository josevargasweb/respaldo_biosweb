package com.grupobios.bioslis.common;

import java.math.BigDecimal;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.DatosFichasAntecedentesDAO;
import com.grupobios.bioslis.dao.DatosFichasExamenesTestDAO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.entity.CfgCondicionesformulas;
import com.grupobios.bioslis.entity.DatosFichasantecedentes;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;

public class CondicionAntecedenteExecutor {

  private DatosFichasAntecedentesDAO datosFichasAntecedentesDAO;
  private DatosFichasExamenesTestDAO datosFichasExamenesTestDAO;

  public CondicionAntecedenteExecutor() {
  }

  public void exec(ResultadoNumericoTestExamenOrdenDTO resultado, CfgCondicionesformulas t) throws BiosLISDAOException {

    // Obtener antecedente a evaluar.

    Long idAntecedente = t.getCcfCondicionvar1();
    // obtener valor antcedente de datosfichasantecedentes

    DatosFichasantecedentes daf = getDatosFichasAntecedentesDAO()
        .getAntecedentesPorIdyNorden(idAntecedente.shortValue(), resultado.getDF_NORDEN().intValueExact());
    //

    if (daf != null) {

      Double dValorAntecedente = Double.valueOf(daf.getDfaValorantecedente());
      if (compare(daf.getDfaValorantecedente(), t.getCcfCondicionoperador(), t.getCcfCondicionvar2())) {

        if (t.getCcfFallaestado() != null && t.getCcfFallaestado().trim().equals("")) {
//          this.cambiarEstado(resultado,daf);

        }
        if (t.getCcfFallanota() != null && t.getCcfFallanota().trim().equals("")) {
//        this.agregarNota(resultado,daf);
        }

        if (t.getCcfFallaresultado() != null && t.getCcfFallaresultado().trim().equals("")) {
//        this.cambiarResultado(resultado,daf);
        }

      }
      ;

    }

    // evaluar si cumple o no condicion
    // si no está en datos fichas antecedentes, verificar
    // edad
    // sexo
    // afro

  }

  private Boolean compare(String dfaValorantecedente, String ccfCondicionoperador, BigDecimal ccfCondicionvar2) {

    BigDecimal bdVal = new BigDecimal(dfaValorantecedente);

    switch (ccfCondicionoperador) {
    case Constante.OPERADOR_DISTINTO:
      return !bdVal.equals(ccfCondicionvar2);
    case Constante.OPERADOR_IGUAL:
      return bdVal.equals(ccfCondicionvar2);

    case Constante.OPERADOR_MAYOR:
      return bdVal.compareTo(ccfCondicionvar2) < 0;
    case Constante.OPERADOR_MAYOROIGUAL:
      return bdVal.compareTo(ccfCondicionvar2) <= 0;
    case Constante.OPERADOR_MENOR:
      return bdVal.compareTo(ccfCondicionvar2) > 0;
    case Constante.OPERADOR_MENOROIGUAL:
      return bdVal.compareTo(ccfCondicionvar2) >= 0;
    }
    return false;
  }

  public DatosFichasAntecedentesDAO getDatosFichasAntecedentesDAO() {
    return datosFichasAntecedentesDAO;
  }

  public void setDatosFichasAntecedentesDAO(DatosFichasAntecedentesDAO datosFichasAntecedentesDAO) {
    this.datosFichasAntecedentesDAO = datosFichasAntecedentesDAO;
  }

  private void agregarNota(ResultadoNumericoTestExamenOrdenDTO resultado, String newvalue) throws BiosLISDAOException {

    DatosFichasexamenestest dfet = datosFichasExamenesTestDAO.getDatosFichasExamenesTestByIdTest(resultado);
//    dfet.s
    datosFichasExamenesTestDAO.updateDFExamenesTest(dfet);

  }

  private void cambiarEstado(ResultadoNumericoTestExamenOrdenDTO resultado, String newvalue)
      throws BiosLISDAOException {

    DatosFichasexamenestest dfet = datosFichasExamenesTestDAO.getDatosFichasExamenesTestByIdTest(resultado);
    dfet.setDfetIdestadoresultadotest(null);
    datosFichasExamenesTestDAO.updateDFExamenesTest(dfet);

  }

  private void cambiarResultado(ResultadoNumericoTestExamenOrdenDTO resultado, String newvalue)
      throws BiosLISDAOException {

    DatosFichasexamenestest dfet = datosFichasExamenesTestDAO.getDatosFichasExamenesTestByIdTest(resultado);
    dfet.setDfetResultado(newvalue);
    datosFichasExamenesTestDAO.updateDFExamenesTest(dfet);

  }

}
