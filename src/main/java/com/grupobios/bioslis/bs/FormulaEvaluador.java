package com.grupobios.bioslis.bs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.mXparser;
import org.springframework.stereotype.Service;

import com.grupobios.bioslis.common.Constante;
import com.grupobios.bioslis.dao.BiosLISDAOException;
import com.grupobios.bioslis.dao.CfgAntecedentesDAO;
import com.grupobios.bioslis.dao.DatosFichasAntecedentesDAO;
import com.grupobios.bioslis.dao.DatosFichasDAO;
import com.grupobios.bioslis.entity.DatosFichasantecedentes;
import com.grupobios.bioslis.entity.DatosFichasexamenestest;

@Service
public class FormulaEvaluador {

  private static Logger logger = LogManager.getLogger(FormulaEvaluador.class);
  private CfgAntecedentesDAO cfgaDAO;
  private DatosFichasDAO datosFichasDAO;
  private DatosFichasAntecedentesDAO dfaDAO;

  public DatosFichasAntecedentesDAO getDfaDAO() {
    return dfaDAO;
  }

  public void setDfaDAO(DatosFichasAntecedentesDAO dfaDAO) {
    this.dfaDAO = dfaDAO;
  }

  public CfgAntecedentesDAO getCfgaDAO() {
    return cfgaDAO;
  }

  public void setCfgaDAO(CfgAntecedentesDAO cfgaDAO) {
    this.cfgaDAO = cfgaDAO;
  }

  public static void main(String[] args) {
    FormulaEvaluador fe = new FormulaEvaluador();

//    fe.parse("[12]+ [56]");

    String formula = "f(r,s,e,a)= 141*min(r,1)^(-0.329*(s) - 0.419*(1-s))*max(r,1)^(-1.219)*0.993^e*1.018^s*1.159^a";

    formula = "iff(1=1,1.212*iff(1=1,175*1^-1.1.54*15^-0.203*0.742,175*1^-1.1.54*15^-0.203),iff(1=1,175*1^-1.1.54*15^-0.203*0.742,175*1^-1.1.54*15^-0.203))";

    // fe.calculate();
    Function f = new Function(formula);
    Expression e = new Expression("f(1,0,30,0)", f);
    mXparser.consolePrintln("Res 1: " + e.getExpressionString() + " = " + e.calculate());
    mXparser.consolePrintln("Res 2: f(1,2,3) = " + f.calculate(1, 2, 3));

  }

  public static final String regexTest = "\\[\\d+\\]";
  public static final String regexAntedente = "\\[A\\d+\\]";

  public List<String> parse(String formula) {

    List<String> idTestLst = new ArrayList<>();
    final Pattern pattern = Pattern.compile(regexTest);
    final Matcher matcher = pattern.matcher(formula);

    while (matcher.find()) {
      String idTest = matcher.group();
      idTest = idTest.substring(1, idTest.length() - 1);
      idTestLst.add(idTest);
      logger.debug("IdTest: {}", idTest);
      logger.debug("Fomula: {}", formula);
    }

    return idTestLst;
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

  public void eval(String formula, Map<Long, DatosFichasexamenestest> hmResultadosxTest) {

    for (Long idTest : hmResultadosxTest.keySet()) {
      formula = formula.replace("[".concat(idTest.toString()).concat("]"),
          hmResultadosxTest.get(idTest).getDfetResultadonumerico().toString());
      logger.debug("Fomula: {}", formula);
    }

  }

  public Double eval(String formula, List<DatosFichasexamenestest> lstResultadosxTest, Long nOrden)
      throws BiosLisFormulaResultIsNaNException, BiosLISDAOException {

      
      try {
    for (DatosFichasexamenestest dfet : lstResultadosxTest) {
        if(dfet.getDfetResultadonumerico() == null) {
            dfet.setDfetResultadonumerico(new BigDecimal(0));
        }
      formula = formula.replace("[".concat(Long.toString(dfet.getId().getDfetIdtest())).concat("]"),
          dfet.getDfetResultadonumerico().toString());
      logger.debug("Formula: {}", formula);
    }

    // Buscar si la formula tiene condiciones
    // Buscar si la formula usa datos de antecedentes.
    List<DatosFichasantecedentes> lstDfa = dfaDAO.getAntecedentesPorNorden(nOrden);
    String antAux;
   
    for (DatosFichasantecedentes ant : lstDfa) {

      antAux = this.normalizeValueAntecedente(ant.getDfaValorantecedente());
      formula = formula.replace("[A".concat(Short.valueOf(ant.getId().getDfaIdantecedente()).toString()).concat("]"), antAux);
      logger.debug("Formula: {}", formula);
    }
      }catch(Exception e) {
         
         logger.error(e.getMessage());
      }

    return calculate(formula);

  }

  private String normalizeValueAntecedente(String valAntecedente) {

try {
    if (NumberUtils.isCreatable(valAntecedente)) {
      return valAntecedente;
    }

    String[] parts = valAntecedente.split("(\\s)+");

    if (NumberUtils.isCreatable(parts[0])) {
      return parts[0];
    }

    if (valAntecedente.equals(Constante.VALOR_SEXO_HOMBRE)) {
      return "1";
    }
    if (valAntecedente.equals(Constante.VALOR_SEXO_MUJER)) {
      return "0";
    }

    if (valAntecedente.equals(Constante.VALOR_AFRO)) {
      return "1";
    }
    if (valAntecedente.equals(Constante.VALOR_NOAFRO)) {
      return "0";
    }
}catch(Exception e ) {
    e.printStackTrace();
}

    return null;
  }

//  public getValuesAntecedentes(String formula) {
//    
//  }

  public Double calculate(String formula) throws BiosLisFormulaResultIsNaNException {
      Double rpta = 0D;
    try {
    Expression e = new Expression(formula);
    mXparser.consolePrintln("Res: " + e.getExpressionString() + " = " + e.calculate());
   rpta = e.calculate();
    if (rpta.isNaN()) {
      logger.debug("No se puedo calcular la formula {}", formula);
      throw new BiosLisFormulaResultIsNaNException(
          "No se puedo calcular la formula. Error ".concat(e.getErrorMessage()));
    }
    }catch(Exception e) {
        logger.error(e.getMessage());
        throw new BiosLisFormulaResultIsNaNException(
                "No se puedo calcular la formula. Error ".concat(e.getMessage()));
    }
    return rpta;

  }

  public DatosFichasDAO getDatosFichasDAO() {
    return datosFichasDAO;
  }

  public void setDatosFichasDAO(DatosFichasDAO datosFichasDAO) {
    this.datosFichasDAO = datosFichasDAO;
  }

  public String validateFormula(String formula) throws BiosLisFormulaResultIsNaNException {

    Expression e = new Expression(formula);

    if (e.checkSyntax()) {
      return "Ok";
    }
    logger.debug("No se puedo calcular la formula {}", e.getErrorMessage());
    throw new BiosLisFormulaResultIsNaNException("No se puedo calcular la formula. Error ".concat(e.getErrorMessage()));

  }

}
