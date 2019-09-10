package com.evo.belezaonline_2;

public class StringFormate {
    //converte String para UTF-8
    public static String convertUTF8ToString(String s){
        String out = null;
        try {
            out= new String(s.getBytes("ISO-8859-1"), "UTF-8");
        }catch (java.io.UnsupportedEncodingException e){
            return null;
        }
        return out;
    }

    //converte UTF-8 para String
    public static String convertStringUTF8(String s){
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"),"ISO-8859-1");
        }catch (java.io.UnsupportedEncodingException e){
            return null;
        }
        return out;
    }
}
