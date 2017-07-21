package com.mode.app.common.utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.util.Base64;

/**
 * Created by tom on 17-7-20.
 */
public class Des3Util {
    // 密钥 长度不得小于24
    private final static String mSecretKey = "123456789012345678901234" ;
    // 向量 可有可无 终端后台也要约定
    private final static String iv = "01234567" ;
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8" ;

    /**
     * 3DES encry
     *
     * @param plainText
     *
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        Key deskey = null ;
        DESedeKeySpec spec = new DESedeKeySpec(mSecretKey .getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec( iv.getBytes());
        cipher.init(Cipher. ENCRYPT_MODE , deskey, ips);
        byte [] encryptData = cipher.doFinal(plainText.getBytes(encoding ));
        return Base64.encodeToString(encryptData,Base64. DEFAULT );
    }
    /**
     * 3DES decode
     *
     * @param encryptText
     *
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
        Key deskey = null ;
        DESedeKeySpec spec = new DESedeKeySpec( mSecretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede" );
        deskey = keyfactory. generateSecret(spec);
        Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS5Padding" );
        IvParameterSpec ips = new IvParameterSpec( iv.getBytes());
        cipher. init(Cipher. DECRYPT_MODE, deskey, ips);

        byte [] decryptData = cipher.doFinal(Base64. decode(encryptText, Base64. DEFAULT));

        return new String (decryptData, encoding);
    }
}
