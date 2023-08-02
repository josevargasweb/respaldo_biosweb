package com.grupobios.common;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

public class PorcentajeAleatorio {

  public static int porcentajeEntero() {

    Random rnd = new Random(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    int rpta = rnd.nextInt(100);
    return rpta;
  }

  public static double porcentajeDecimal() {

    Random rnd = new Random(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    return rnd.nextInt(100) / 100.0;
  }

}
