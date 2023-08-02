package com.grupobios.bioslis;

import org.junit.jupiter.api.Test;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.mXparser;

class FuctionEvaluatorTest {
  /*
   * @Test final void testParse() { FormulaEvaluador fe = new FormulaEvaluador();
   * assertTrue(fe.parse("[123]").size() == 1);
   * assertTrue(fe.parse("t uytyututut [123]!#$&/()=? [ttttt] [556").size() == 2);
   * assertTrue(fe.parse("tuytyututut [123]!#$&/()=? ").size() == 1);
   * assertFalse(fe.parse("tuytyututut [123!#$&/()=? ").size() == 1);
   * assertFalse(fe.parse("tuytyututut [ttttt] ").size() == 1);
   * 
   * }
   *
   */

  @Test
  final void testParse() {
//    FormulaEvaluador fe = new FormulaEvaluador();

//    fe.parse("[12]+ [56]");

//    String formula = "f(r,s,e,a)= 141*min(r,1)^(-0.329*(s) - 0.419*(1-s))*max(r,1)^(-1.219)*0.993^e*1.018^s*1.159^a";
//    String formula = "f(r)= 141*min(r,1)*max(r,1)";
//  Expression e = new Expression("f(0.9)", f);
//    String formula = "f(r)= 141*min(r,1)^(-0.329)*max(r,1)^(-1.219)";
//  Expression e = new Expression("f(0.9)", f);
//    String formula = "f(r,s)= 141*min(r,1)^(-0.329*s - 0.419*(1-s))*max(r,1)^(-1.219)*1.018^s";
//  Expression e = new Expression("f(0.9,1)", f);
//    String formula = "f(r,s,d)= 141*min(r,1)^(-0.329*s - 0.419*(1-s))*max(r,1)^(-1.219)*1.018^s*0.993^d";
//    Expression e = new Expression("f(4.9,1,0)", f);

//    String formula = "f(r,s,d)= r*s*d";

//    String formula = "f(r,s,d,a)= 141*min(r,1)^(-0.329*(s) - 0.419*(1-s))*max(r,1)^(-1.219)*0.993^d*1.018^s*1.159^a";
//    Function f = new Function(formula);
//    Expression e = new Expression("f(4.9,1,0,1)", f);

//  String formula = "if(1=1,1.5,2.01)";
//    String formula2 = "175*1^-1.1.54*15^-0.203*0.742";
//    String formula = "1.212*if(1=1,175*1^-1.154*15^-0.203*0.742,175*1^-1.154*15^-0.203)";
    String formula3 = "if(1=0,1.212*if(1=1,175*1^-1.154*15^-0.203*0.742,175*1^-1.154*15^-0.203),if(1=0,175*1^-1.154*15^-0.203*0.742,175*1^-1.154*15^-0.203))";

    Expression e = new Expression(formula3);
    mXparser.consolePrintln("Res: " + e.getExpressionString() + " = " + e.calculate());

    String valAntecedente = "";
    String[] parts = valAntecedente.split("(\\s)+");

    if (parts != null && parts.length > 0) {
      System.out.println(parts[0]);
    }

    valAntecedente = " ";
    parts = valAntecedente.split("(\\s)+");
    if (parts != null && parts.length > 0) {

      System.out.println(parts[0]);
    }

    valAntecedente = "  ";
    parts = valAntecedente.split("(\\s)+");
    if (parts != null && parts.length > 0) {
      System.out.println(parts[0]);
    }

    valAntecedente = "15 años ";
    parts = valAntecedente.split("(\\s)+");
    if (parts != null && parts.length > 0) {
      System.out.println(parts[0]);
    }

    valAntecedente = "M ";
    parts = valAntecedente.split("(\\s)+");
    if (parts != null && parts.length > 0) {
      System.out.println(parts[0]);
    }

  }

}
