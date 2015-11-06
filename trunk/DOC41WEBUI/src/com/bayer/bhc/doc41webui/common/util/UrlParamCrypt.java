package com.bayer.bhc.doc41webui.common.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.bayer.bhc.doc41webui.common.exception.Doc41RuntimeExceptionBase;
import com.bayer.ecim.foundation.basic.StringTool;

/**
 * Class to Crypt/Decrypt URL Parameters
 * Afaik Blowfish Implemenentation is free of characters that usually have to be escaped.
 * 
 * @author imokl
 *
 */
public class UrlParamCrypt {

    private static final String KEY = "d0c41d0c1d5f0c4a65819f9845c8b7df";

    private static String algKey = "Blowfish";
    private static String alg  = "Blowfish/ECB/PKCS5Padding";

    private UrlParamCrypt() {
        //do not instantiate
    }

    private static String encrypt(String cleartext) throws DecoderException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        SecretKey blowfishKey = new SecretKeySpec(Hex.decodeHex(KEY.toCharArray()), algKey);

        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, blowfishKey);

        byte[] ciphertext = cipher.doFinal(cleartext.getBytes());
        return new String(Hex.encodeHex(ciphertext));
    }

    private static String decrypt(String ciphertext) throws DecoderException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKey blowfishKey = new SecretKeySpec(Hex.decodeHex(KEY.toCharArray()), algKey);

        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.DECRYPT_MODE, blowfishKey);

        byte[] decryptedText = cipher.doFinal(Hex.decodeHex(ciphertext.toCharArray()));
        String decrypted = new String(decryptedText);

        return decrypted;
    }	

    public static String encryptParameters(Map<String,String> params){
        try{
            StringBuilder sb = new StringBuilder();
            for (Entry<String, String> entry : params.entrySet()) {
                if(sb.length()>0){
                    sb.append('&');
                }
                sb.append(StringTool.encodeURLWithDefaultFileEnc(entry.getKey()));
                sb.append('=');
                sb.append(StringTool.encodeURLWithDefaultFileEnc(entry.getValue()));	
            }
            return encrypt(sb.toString());
        } catch (Exception e){
            throw new Doc41RuntimeExceptionBase("Encrypt Parameters failed!\n" + params, e);
        }
    }

    public static Map<String, String> decryptParameters(String encrypted){
        try{
            final String decrypted = decrypt(encrypted);
            StringTokenizer mSt 		= new StringTokenizer(decrypted,"&");
            Map<String, String> params = new LinkedHashMap<String, String>();
            while(mSt.hasMoreTokens()){
                String param			= mSt.nextToken();
                String paramName		= param.substring(0,param.indexOf('='));
                String paramValue	= param.substring(param.indexOf('=')+1);
                params.put(StringTool.decodeURLWithDefaultFileEnc(paramName), StringTool.decodeURLWithDefaultFileEnc(paramValue));
            }
            return params;
        } catch (Exception e){
            throw new Doc41RuntimeExceptionBase("Decrypt Parameters failed!\n" + encrypted, e);
        }
    }
}
