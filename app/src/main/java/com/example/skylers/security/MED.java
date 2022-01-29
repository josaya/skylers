package com.example.skylers.security;

import android.util.Base64;
import android.util.Log;

import java.security.Key;

import javax.crypto.Cipher;

public class MED {

    public String encrypt(Key privateKey, String targetString){
        // Encode the original data with the RSA private key
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");

            c.init(Cipher.ENCRYPT_MODE, privateKey);
            encodedBytes = c.doFinal(targetString.getBytes());
        } catch (Exception e) {
            Log.e("Crypto", "RSA encryption error");
        }

        return new String(Base64.encodeToString(encodedBytes, Base64.DEFAULT));
    }
}
