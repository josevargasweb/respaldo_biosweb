package com.grupobios.bioslis.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.DatosFichasAntecedentesDAO;
import com.grupobios.bioslis.dto.ResultadoNumericoTestExamenOrdenDTO;
import com.grupobios.bioslis.entity.DatosFichasantecedentes;

public class AntedenteServiceImpl implements AntecedenteService {

  public static final String regexAntedente = "\\[A\\d+\\]";
  private static Logger logger = LogManager.getLogger(AntedenteServiceImpl.class);

  private DatosFichasAntecedentesDAO datosFichasAntecedentesDAO;

  public AntedenteServiceImpl() {
  }

  public List<String> parseAntecedentesFormula(String formula) {

    List<String> idAntLst = new ArrayList<>();
    final Pattern pattern = Pattern.compile(regexAntedente);
    final Matcher matcher = pattern.matcher(formula);

    while (matcher.find()) {
      String idAntecedente = matcher.group();
      idAntecedente = idAntecedente.substring(2, idAntecedente.length() - 1);
      idAntLst.add(idAntecedente);
      logger.debug("idAntecedente: {}", idAntecedente);
      logger.debug("Fomula: {}", formula);
    }
    return idAntLst;
  }

  public String instanciarAntecedentesFormula(String formula, ResultadoNumericoTestExamenOrdenDTO resultado) {

    List<String> idAntLst = this.parseAntecedentesFormula(formula);

    Long nOrden = resultado.getDF_NORDEN().longValueExact();
    resultado.getDFE_IDEXAMEN();
    resultado.getDFET_IDTEST();

    for (String sIdAnt : idAntLst) {
      try {
        DatosFichasantecedentes dfa = datosFichasAntecedentesDAO.getAntecedentesPorIdyNorden(Short.valueOf(sIdAnt),
            nOrden);
        formula = formula.replaceAll("[A" + sIdAnt + "]", dfa.getDfaValorantecedente());
      } catch (NumberFormatException | BiosLISDAOException e) {
        logger.error("No se pudo obtener valor antecedente de la orden: {}", e.getLocalizedMessage());
      }
    }

    return formula;

  }

}
