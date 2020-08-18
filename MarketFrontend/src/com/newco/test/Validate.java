package com.newco.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validate

{
        public static void main(String args[])
        {
                /*ValidatePhoneNumber("6323247378");
                ValidatePhoneNumber("9999999999");
                ValidatePhoneNumber("23344jkjhg");
                ValidatePhoneNumber("jhhhhhjkli");*/
                ValidatePhoneNumber("00000");
                ValidatePhoneNumber("0000034");
                ValidatePhoneNumber("gghjg");
//                ValidatePhoneNumber("1.555-555-5555");
//                ValidatePhoneNumber("555-555-5555");
//                ValidatePhoneNumber("555 555-5555");
//                ValidatePhoneNumber("1 555 5555");
//                ValidatePhoneNumber("555-WinNow");
//                ValidatePhoneNumber("1-555-555");
//                ValidatePhoneNumber("555-5555");
        }

        public static boolean ValidatePhoneNumber(String phNumber)
        {
                String msgResult = "";
               // boolean valResult = false;
               /* //String numPattern = "(\\d)?(\\d{3})?\\d{3}\\d{4}";
                String numPattern = "(\\d{3})?\\d{3}\\d{4}";
               Pattern p = Pattern.compile(".+@.+\\.[a-z]+");;
                valResult = phNumber.matches(numPattern);*/
                String email = "shoukath@alicom";
                Pattern p = Pattern.compile(".+@.+\\.[a-z]+");;
                Matcher m = p.matcher(email);
                boolean valResult = m.matches();
                if (valResult)
                {
                       msgResult = "The phone number validates.";
                }
                else
                {
                        msgResult = "The phone number does not validate";
                }
                System.out.println(msgResult);
                System.out.println(valResult);
                return valResult;
        }
}