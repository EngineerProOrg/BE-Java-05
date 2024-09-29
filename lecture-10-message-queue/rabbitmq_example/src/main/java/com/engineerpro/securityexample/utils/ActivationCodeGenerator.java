package com.engineerpro.securityexample.utils;

import java.security.SecureRandom;

public class ActivationCodeGenerator {
  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final SecureRandom random = new SecureRandom();

  public static String generateActivationCode(int length) {
    StringBuilder activationCode = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int index = random.nextInt(CHARACTERS.length());
      activationCode.append(CHARACTERS.charAt(index));
    }
    return activationCode.toString();
  }
}
