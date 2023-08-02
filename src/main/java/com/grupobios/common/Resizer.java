/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.common;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;

/**
 *
 * @author Eric Nicholls Código proporcionado desde su GitHub a Marco Caracciolo
 *         para su uso
 */
public class Resizer {

  private static final Logger LOGGER = LogManager.getLogger(Resizer.class);

  public BufferedImage loadImage(String filename) throws IOException {

    File f = new File(filename);
    BufferedImage bi = null;
    try {
      bi = ImageIO.read(f);
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
    return bi;
  }

  public void saveImage(String filename, RenderedImage image, String extension) throws IOException {

    File output = new File(filename);
    try {
      ImageIO.write(image, extension, output);
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
  }

  public void saveImageAsCode64(String outfilename, RenderedImage image, String extension) throws IOException {

    File output = new File(outfilename);
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); FileWriter fw = new FileWriter(output)) {
      ImageIO.write(image, extension, baos);
      String s = Base64.getEncoder().encodeToString(baos.toByteArray());
      fw.write(s);
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
      throw e;
    }

  }

  public void saveImageAsCode64(byte[] baos, String outfilename) throws IOException {

    File output = new File(outfilename);
    String s = Base64.getEncoder().encodeToString(baos);
    try (FileWriter fw = new FileWriter(output)) {
      fw.write(s);
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
  }

  public BufferedImage genResizedImage(BufferedImage originalImage, int targetWidth, int targetHeight) {

    BufferedImage bi = Scalr.resize(originalImage, targetWidth, targetHeight);
    return bi;
  }

  public BufferedImage genResizedImage(byte[] baos, int targetWidth, int targetHeight) throws IOException {

    try (InputStream bais = new ByteArrayInputStream(baos)) {
      BufferedImage bi = ImageIO.read(bais);
      BufferedImage rpta = this.genResizedImage(bi, targetWidth, targetHeight);
//      RenderedImage rpta = bi.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_DEFAULT);
      return (BufferedImage) rpta;
    } catch (IOException e) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
      throw e;
    }

  }

  public String getImageAsCode64(RenderedImage image, String extension) throws IOException {

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      ImageIO.write(image, extension, baos);
      return Base64.getEncoder().encodeToString(baos.toByteArray());
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
  }

  public String getResizedImageAsCode64(byte[] baos, int targetWidth, int targetHeight, String extension)
      throws IOException {

    try {
      return this.getImageAsCode64(this.genResizedImage(baos, targetWidth, targetHeight), extension);
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
  }

  public static void main(String[] args) {

    String filename = "C:\\tmp\\imagenes\\planococina.png";

    try (InputStream is = new FileInputStream(filename)) {

      Resizer resizer = new Resizer();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      int b;
      while ((b = is.read()) != -1) {
        baos.write(b);
      }
      resizer.saveImageAsCode64(baos.toByteArray(), "C:\\tmp\\imagenes\\planococinaresizedbyte.png");

    } catch (Exception e) {
      LOGGER.error(e.getMessage());

    }

  }

}