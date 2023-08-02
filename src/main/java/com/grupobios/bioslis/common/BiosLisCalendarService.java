package com.grupobios.bioslis.common;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.grupobios.bioslis.dao.RelojDAO;
import com.grupobios.common.Edad;

public class BiosLisCalendarService {

  private RelojDAO relojaDAO;
  private static Logger logger = LogManager.getLogger(BiosLisCalendarService.class);

  private static BiosLisCalendarService theInstance = null;

  private BiosLisCalendarService() {
    this.relojaDAO = new RelojDAO();
  }

  public static BiosLisCalendarService getInstance() {
    if (theInstance == null) {
      theInstance = new BiosLisCalendarService();
    }
    return theInstance;
  }

  public int getYear() {
    return relojaDAO.getTs().toLocalDateTime().getYear();
  }

  public int getMonth() {
    return relojaDAO.getTs().toLocalDateTime().getMonthValue();
  }

  public int getDay() {
    return relojaDAO.getTs().toLocalDateTime().getDayOfMonth();
  }

  public int getHour() {
    return relojaDAO.getTs().toLocalDateTime().getHour();
  }

  public int getMinute() {
    return relojaDAO.getTs().toLocalDateTime().getMinute();
  }

  public int getSecond() {
    return relojaDAO.getTs().toLocalDateTime().getSecond();
  }

  public Date getDate() {
    Calendar cal = Calendar.getInstance();
    cal.set(getYear(), getMonth() - 1, getDay());
    return cal.getTime();
  }

  public Timestamp getTS() {
    return relojaDAO.getTs();
  }

  public LocalDateTime getLocalDateTime() {
    return relojaDAO.getTs().toLocalDateTime();

  }

  public Edad getEdadActual(Date fechaNacimiento) throws BiosLISException {
    return Edad.getEdadActual(fechaNacimiento);
  }

  public Timestamp getTS(int year, int month, int day) {

    LocalDateTime ld = LocalDateTime.of(year, month, day, 0, 0, 0);

    return Timestamp.valueOf(ld);
  }

  public Timestamp getTS(int year, int month, int day, int hour, int min, int sec) {

    LocalDateTime ld = LocalDateTime.of(year, month, day, hour, min, sec);

    return Timestamp.valueOf(ld);
  }

  public Timestamp getTSFromLoca(int year, int month, int day) {

    LocalDateTime ld = LocalDateTime.of(year, month, day, 0, 0, 0);

    return Timestamp.valueOf(ld);
  }

  public Timestamp getTS(String sFecha) throws BiosLISException {

    if (sFecha.trim().equals("")) {
      return null;
    } else {
      Pattern p = Pattern.compile(regexddmmyyyy0);
      Matcher m = p.matcher(sFecha.trim());
      boolean b = m.matches();
      if (b) {

        int day = Integer.valueOf(sFecha.substring(0, 2));
        int month = Integer.valueOf(sFecha.trim().substring(2, 4));
        int year = Integer.valueOf(sFecha.trim().substring(4, 9));
        return BiosLisCalendarService.getInstance().getTS(year, month, day);

      } else {

        p = Pattern.compile(regexddmmyyyy1);
        m = p.matcher(sFecha.trim());
        b = m.matches();
        p = Pattern.compile(regexddmmyyyy2);
        m = p.matcher(sFecha.trim());
        b = m.matches() || b;
        if (b) {
          int day = Integer.valueOf(sFecha.substring(0, 2));
          int month = Integer.valueOf(sFecha.trim().substring(3, 5));
          int year = Integer.valueOf(sFecha.trim().substring(6, 10));
          return BiosLisCalendarService.getInstance().getTS(year, month, day);
        } else {
          p = Pattern.compile(regexddmmyyyyhhmmss1);
          m = p.matcher(sFecha.trim());
          b = m.matches();

          if (b) {

            int day = Integer.valueOf(sFecha.substring(0, 2));
            int month = Integer.valueOf(sFecha.trim().substring(3, 5));
            int year = Integer.valueOf(sFecha.trim().substring(6, 10));
            int hour = Integer.valueOf(sFecha.substring(11, 13));
            int min = Integer.valueOf(sFecha.trim().substring(15, 17));
            int sec = Integer.valueOf(sFecha.trim().substring(19, 20));
            return BiosLisCalendarService.getInstance().getTS(year, month, day, hour, min, sec);

          }
        }

      }
    }
    throw new BiosLISException("String de Fecha en formato inválido");
  }

  // El formato dd-mm-yyyy se considera el estandar
  public String estandariza(String sFecha) throws BiosLISException {

    logger.debug(sFecha);
    if (sFecha.trim().equals("")) {
      return null;
    } else {
      Pattern p = Pattern.compile(regexddmmyyyy0);
      Matcher m = p.matcher(sFecha.trim());
      boolean b = m.matches();
      if (b) {
        return sFecha.trim().substring(0, 2).concat("-").concat(sFecha.trim().substring(2, 4)).concat("-")
            .concat(sFecha.substring(4, 8));

      } else {

        p = Pattern.compile(regexddmmyyyy2);
        m = p.matcher(sFecha.trim());
        b = m.matches();
        if (b) {
          return sFecha.trim().substring(0, 2).concat("-").concat(sFecha.trim().substring(3, 5)).concat("-")
              .concat(sFecha.substring(6, 10));
        } else {
          p = Pattern.compile(regexddmmyyyy1);
          m = p.matcher(sFecha.trim());
          b = m.matches();
          if (b) {
            return sFecha.trim();
          } else {
            p = Pattern.compile(regexddmmyyyyhhmmss1);
            m = p.matcher(sFecha.trim());
            b = m.matches();

            if (b) {
              return sFecha.trim();

            }
          }
        }

      }
    }
    logger.error("No se pudo parsear ", sFecha);
    throw new BiosLISException("String de Fecha en formato inválido");
  }

//  private final static String regexddmmyyyy = "((0[1-9])|((1|2)[0-9])|30|31)((0[1-9])|([10|11|12]))((\\d){4})";
  private final static String regexddmmyyyy0 = "((0[1-9])|((1|2)[0-9])|30|31)((0[1-9])|(10|11|12))((\\d){4})";;
  private final static String regexddmmyyyy1 = "((0[1-9])|((1|2)[0-9])|30|31)(\\-)((0[1-9])|(10|11|12))(\\-)((\\d){4})";
  private final static String regexddmmyyyy2 = "((0[1-9])|((1|2)[0-9])|30|31)(\\/)((0[1-9])|(10|11|12))(\\/)((\\d){4})";
  private final static String regexddmmyyyyhhmmss1 = "((0[1-9])|((1|2)[0-9])|30|31)(\\-)((0[1-9])|(10|11|12))(\\-)((\\d){4})(\\s)(([01][0-9])|(2[0-3]))(:)([0-5][0-9])(:)([0-5][0-9])";// (:)((\\d){2})((\\d){2})";//
  private final static String regexddmmyyyyhhmmss2 = "((0[1-9])|((1|2)[0-9])|30|31)(\\/)((0[1-9])|(10|11|12))(\\/)((\\d){4})(\\s)(([01][0-9])|(2[0-3]))(:)([0-5][0-9])(:)([0-5][0-9])";// (:)((\\d){2})((\\d){2})";//

  public Date toDate(LocalDate ld) {
    Calendar cal = Calendar.getInstance();
    cal.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth(), 0, 0, 0);
    return cal.getTime();
  }

  public Timestamp getTS(Session sesion) {
    return relojaDAO.getTs(sesion);
  }

}
