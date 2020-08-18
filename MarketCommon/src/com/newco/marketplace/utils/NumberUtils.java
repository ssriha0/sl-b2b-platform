package com.newco.marketplace.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtils {
	
	public static void main(String args[]){
		try{
			System.out.println(formatDecimalNumber(1072.123456578901,2));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
	}
	public static double formatDecimalNumber(double number,int precision){
									
		NumberFormat numerFormat = new DecimalFormat("#");//NumberFormat.getInstance();
		numerFormat.setMaximumFractionDigits(precision);
		 
		return Double.parseDouble(numerFormat.format(number));		
	}

}
