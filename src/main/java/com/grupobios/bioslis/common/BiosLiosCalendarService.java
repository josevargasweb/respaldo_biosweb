package com.grupobios.bioslis.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import com.grupobios.bioslis.dao.RelojDAO;
import com.grupobios.common.Edad;

public class BiosLiosCalendarService {

  private RelojDAO relojaDAO;

  private static BiosLiosCalendarService theInstance = null;

  private BiosLiosCalendarService() {
    this.relojaDAO = new RelojDAO();
  }

  public static BiosLiosCalendarService getInstance() {
    if (theInstance == null) {
      theInstance = new BiosLiosCalendarService();
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

}
