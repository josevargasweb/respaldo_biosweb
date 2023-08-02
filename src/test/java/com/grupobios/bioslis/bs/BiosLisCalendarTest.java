package com.grupobios.bioslis.bs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class BiosLisCalendarTest {

  private final static String regexdd = "(0[1-9])|((1|2)[0-9])|30|31";
  private final static String regexmm = "((0[1-9])|([10|11|12]))";
  private final static String regexddmm = "((0[1-9])|((1|2)[0-9])|30|31)((0[1-9])|(10|11|12))";
  private final static String regexyyyy = "(\\d){4}";
  private final static String regexddmmyyyy = "((0[1-9])|((1|2)[0-9])|30|31)((0[1-9])|(10|11|12))((\\d){4})";
  private final static String regexddmmyyyy0 = "((0[1-9])|((1|2)[0-9])|30|31)((0[1-9])|(10|11|12))((\\d){4})";;
  private final static String regexddmmyyyy1 = "((0[1-9])|((1|2)[0-9])|30|31)(\\-)((0[1-9])|(10|11|12))(\\-)((\\d){4})";
  private final static String regexddmmyyyy2 = "((0[1-9])|((1|2)[0-9])|30|31)(\\/)((0[1-9])|(10|11|12))(\\/)((\\d){4})";
  private final static String regexddmmyyyyhhmmss = "((0[1-9])|((1|2)[0-9])|30|31)(\\/)((0[1-9])|(10|11|12))(\\/)((\\d){4})(\\s)(([01][0-9])|(2[0-3]))(:)([0-5][0-9])(:)([0-5][0-9])";// (:)((\\d){2})((\\d){2})";//
  // ((0[0-9])|(1[0-9])|(2[0-4]))\\:([0-5][0-9])\\:([0-5][0-9])";

  @Test
  void test() {

    String sDia = "03";
    Pattern p = Pattern.compile(regexdd);
    Matcher m = p.matcher(sDia.trim());
    boolean b = m.matches();
    System.out.println(b);
    sDia = "13";
    m = p.matcher(sDia.trim());
    b = m.matches();
    System.out.println(b);
//    p = Pattern.compile("30");
    sDia = "30";
    m = p.matcher(sDia.trim());
    b = m.matches();
    System.out.println(b);

    String sMes = "12";
    p = Pattern.compile(regexmm);
    m = p.matcher(sMes.trim());
    b = m.matches();
    System.out.println(b);

    String sDiaMes = "2312";
    p = Pattern.compile(regexddmm);
    m = p.matcher(sDiaMes.trim());
    b = m.matches();
    System.out.println(sDiaMes);
    System.out.println(b);

    String sYear = "2022";
    p = Pattern.compile(regexyyyy);
    m = p.matcher(sYear.trim());
    b = m.matches();
    System.out.println(sYear);
    System.out.println(b);

    String sFecha = "30012022";
    p = Pattern.compile(regexddmmyyyy);
    m = p.matcher(sFecha.trim());
    b = m.matches();
    System.out.println(sFecha);
    System.out.println(b);

    sFecha = "30-12-2022";
    p = Pattern.compile(regexddmmyyyy1);
    m = p.matcher(sFecha.trim());
    b = m.matches();
    System.out.println(sFecha);
    System.out.println(b);

    sFecha = "30/12/2022";
    p = Pattern.compile(regexddmmyyyy2);
    m = p.matcher(sFecha.trim());
    b = m.matches();
    System.out.println(sFecha.concat("<<"));
    System.out.println(b);

    sFecha = "30/12/2022 23:59:55";
    p = Pattern.compile(regexddmmyyyyhhmmss);
    m = p.matcher(sFecha.trim());
    b = m.matches();
    System.out.println(sFecha);
    System.out.println(b);

    assertEquals(true, b);

  }

}
