package com.senac.BarAppWeb.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {
    
        public static String getMd5(String texto) {
            
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");

                byte[] messageDisgest = md.digest(texto.getBytes());

                BigInteger nr = new BigInteger(1, messageDisgest);

                String hashtext = nr.toString(16);
                while (hashtext.length() < 32) {
                    hashtext = "0" + hashtext;
                }

                return hashtext;

            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
     }
}
