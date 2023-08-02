package com.grupobios.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateFormatterHelper {

  private static final Logger logger = LogManager.getLogger(DateFormatterHelper.class);

  private DateFormatterHelper() {
  }

  private static final String DATETIME_FORMAT = "dd/MM/yyyy hh:mm:ss";
  private static final String DATE_FORMAT = "dd/MM/yyyy";
  private static final String ISO8601_DATE_FORMAT = "YYYY-MM-DD";

  public static String dtimeToText(java.util.Date d) {

    Calendar c = Calendar.getInstance();
    c.setTime(d);
    LocalDateTime ldt = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH),
        c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    return ldt.format(dtFormatter);
  }

  public static String dateToText(java.util.Date d) {

    Calendar c = Calendar.getInstance();
    c.setTime(d);
    LocalDate ldt = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return ldt.format(dtFormatter);
  }

  public static String tsToText(Timestamp ts) {
    LocalDateTime ldt = ts.toLocalDateTime();
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return ldt.format(dtFormatter);
  }

  public static String tsToDatetimeText(Timestamp ts) {
    LocalDateTime ldt = ts.toLocalDateTime();
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    return ldt.format(dtFormatter);
  }

  public static java.util.Date textDateToDate(String sDate) throws ParseException {

    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
    return formatter.parse(sDate);
  }

  public static java.sql.Date textISO8601DateToDate(String sDate) throws ParseException {

    // falta validar que string esé en formato ISO
    return java.sql.Date.valueOf(sDate);
  }

  public static Timestamp textDateToTS(String sDate) {

    String[] fecha = sDate.split("/");
    String nuevaFecha = fecha[2].concat("-").concat(fecha[1]).concat("-").concat(fecha[2]);
    return Timestamp.valueOf(nuevaFecha);
  }

  public static java.util.Date textDateTimeToDate(String sDate) throws ParseException {

    SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
    return formatter.parse(sDate);
  }

  public static LocalDate textDateToLocalDate(String sDate) {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    return LocalDate.parse(sDate, dtf);
  }

  public static java.sql.Date textDateToSqlDate(String sDate) {

    String[] fecha = sDate.split("/");
    String nuevaFecha = fecha[2].concat("-").concat(fecha[1]).concat("-").concat(fecha[2]);
    logger.debug("Fecha {}", nuevaFecha);
    return java.sql.Date.valueOf(nuevaFecha);
  }

}
