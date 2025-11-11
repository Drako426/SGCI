/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.seguridad;

/**
 *
 * @author Juan Pablo
 */
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class EncriptacionAES implements ServicioEncriptacion {

    private static final String ALGO = "AES/GCM/NoPadding";
    private final byte[] KEY;

    public EncriptacionAES(byte[] key) {
        if (key == null || (key.length != 16 && key.length != 24 && key.length != 32)) {
            throw new IllegalArgumentException("Clave AES inválida. Use 16/24/32 bytes.");
        }
        this.KEY = key;
    }

    @Override
    public String encriptar(String data) throws Exception {
        if (data == null) return null;
        Cipher cipher = Cipher.getInstance(ALGO);
        byte[] iv = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        SecretKeySpec keySpec = new SecretKeySpec(KEY, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
        byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    @Override
    public String desencriptar(String data) throws Exception {
        if (data == null) return null;
        byte[] combined = Base64.getDecoder().decode(data);
        byte[] iv = new byte[12];
        System.arraycopy(combined, 0, iv, 0, 12);
        int encLen = combined.length - 12;
        byte[] encrypted = new byte[encLen];
        System.arraycopy(combined, 12, encrypted, 0, encLen);
        Cipher cipher = Cipher.getInstance(ALGO);
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        SecretKeySpec keySpec = new SecretKeySpec(KEY, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);
        byte[] original = cipher.doFinal(encrypted);
        return new String(original, "UTF-8");
    }
}