package com.grupobios.common;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import com.grupobios.bioslis.common.BiosLISException;

public class Edad implements Comparable<Edad> {

  private int year;
  private int month;
  private int day;

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public Edad(int year, int month, int day) {
    super();
    this.year = year;
    this.month = month;
    this.day = day;
  }

  public Edad() {
    this.year = 0;
    this.month = 0;
    this.day = 0;
  }

  public int normalize() {

    return this.year * 10000 + this.month * 100 + this.day;

  }

  public static Edad getEdadActual(String fechaddmmaaaa) throws BiosLISException {

    fechaddmmaaaa = fechaddmmaaaa.substring(0, 10); // Solo interesa fecha por ahora
    int fechaNac = Integer.parseInt(fechaddmmaaaa.replaceAll("\\-", "").replaceAll("/", ""));
    int dia = fechaNac / 1000000;
    fechaNac = fechaNac - dia * 1000000;
    int mes = fechaNac / 10000;
    fechaNac = fechaNac - mes * 10000;
    int ano = fechaNac;

    LocalDate ldt = LocalDate.now();
    LocalDate ldtnac = LocalDate.of(ano, mes, dia);
    Period p = Period.between(ldtnac, ldt);

    return new Edad(p.getYears(), p.getMonths(), p.getDays());

  }

  public static Edad getEdadActual(Date fechaNacimiento) {

    Calendar c = Calendar.getInstance();
    c.setTime(fechaNacimiento);
    LocalDate ldtnac = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
    LocalDate ldt = LocalDate.now();
    Period p = Period.between(ldtnac, ldt);
    return new Edad(p.getYears(), p.getMonths(), p.getDays());

  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("");

    if (year != 0) {
      sb.append(year + " años ");
    } else if (month != 0) {
      sb.append(month + " meses ");
    } else if (day != 0) {
      sb.append(day + " días ");
    }

    return sb.toString();
  }

  @Override
  public int compareTo(Edad o) {
    return (this.normalize() - o.normalize());
  }

  @Override
  public int hashCode() {
    return Objects.hash(day, month, year);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Edad other = (Edad) obj;
    return day == other.day && month == other.month && year == other.year;
  }

}
