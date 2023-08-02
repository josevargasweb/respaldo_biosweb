/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Marco Caracciolo
 */
public class CifradoMD5 {

  private static final Logger logger = LogManager.getLogger(CifradoMD5.class);
  private static final String SECRET_KEY = "biosintelligence";

  public static String encode(String input) {
    String encriptacion = "";
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      byte[] llavePassword = md5.digest(SECRET_KEY.getBytes("UTF-8"));
      byte[] BytesKey = Arrays.copyOf(llavePassword, 24);
      SecretKey key = new SecretKeySpec(BytesKey, "DESede");
      Cipher cifrado = Cipher.getInstance("DESede");
      cifrado.init(Cipher.ENCRYPT_MODE, key);
      byte[] plainTextBytes = input.getBytes("UTF-8");
      byte[] buf = cifrado.doFinal(plainTextBytes);
      byte[] base64Bytes = Base64.encodeBase64(buf);
      encriptacion = new String(base64Bytes);
    } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException
        | IllegalBlockSizeException | NoSuchPaddingException ex) {
      logger.debug("Algo salió mal: " + ex.getMessage());
    }
    return encriptacion;
  }

  public static String decode(String input) {
    String desencriptacion = "";
    try {
      byte[] message = Base64.decodeBase64(input.getBytes("UTF-8"));
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      byte[] digestOfPassword = md5.digest(SECRET_KEY.getBytes("UTF-8"));
      byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
      SecretKey key = new SecretKeySpec(keyBytes, "DESede");
      Cipher decipher = Cipher.getInstance("DESede");
      decipher.init(Cipher.DECRYPT_MODE, key);
      byte[] plainText = decipher.doFinal(message);
      desencriptacion = new String(plainText, "UTF-8");
    } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException
        | IllegalBlockSizeException | NoSuchPaddingException ex) {
      logger.debug("Algo salió mal: " + ex.getMessage());
    }
    return desencriptacion;
  }

}
