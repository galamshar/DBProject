package com.CarSaleWebsite.Kolesa.Functions;


import java.util.Arrays;
import java.util.List;

public class StringConfigurerFunctions {

    public static String replaceWhiteSpaceWithMinus(String word){
        return word.replaceAll("\\s+", "-");
    }
    public static List<String> allColorsinBootstrap(){
        return Arrays.asList("primary","success","danger","warning","info","secondary","warning","info");
    } 

}
