package com.lotuas;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class CryptoTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str="A";
        byte[] bts=Base64.encodeBase64(str.getBytes());
        String result=new String(bts);
        System.out.println(result);
    }
}
