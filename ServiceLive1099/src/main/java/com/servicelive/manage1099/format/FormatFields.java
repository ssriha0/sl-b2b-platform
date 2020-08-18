package com.servicelive.manage1099.format;

import com.servicelive.manage1099.constants.DatumConstants;

public class FormatFields {

	public FormatFields() {
		super();
	}

	/**
	 * @param dba_name
	 * @param business_name
	 * @param street1
	 * @param street2
	 */
	protected String getNumberOfLinesForOName(final String dba_name, final String business_name,
			final String street1, final String street2) {
				int oname_no_of_lines = 0;
				boolean nameExists = false;
				boolean dba_nameExists = false;
				boolean street1Exists = false;
				boolean street2Exists =false;
				
				if(business_name !=null && business_name.length()>0){
					nameExists=true;
				}
				if(dba_name !=null && dba_name.length()>0){
					dba_nameExists=true;
				}
				if(street1 !=null && street1.length()>0){
					street1Exists=true;
				}
				if(street2 !=null && street2.length()>0){
					street2Exists=true;
				}
				
				if(nameExists && !dba_nameExists  && !street1Exists && !street2Exists){
					oname_no_of_lines =1;
				}
				if(nameExists && !dba_nameExists && street1Exists && !street2Exists){
					oname_no_of_lines =2;
				}
				if(nameExists && dba_nameExists && !street1Exists && !street2Exists){
					oname_no_of_lines =2;
				}
				if(nameExists && dba_nameExists && street1Exists){
					oname_no_of_lines =3;
				}
				if(nameExists && street1Exists && street2Exists){
					oname_no_of_lines =3;
				}
				
				return String.valueOf(oname_no_of_lines);
			}

	/**
	 * @param dba_name
	 * @param business_name
	 * @param street1
	 * @param street2
	 */
	public String getOName(final String dba_name, final String business_name, final String street1, final String street2) {
		String oname = "";
		boolean nameExists = false;
		boolean dba_nameExists = false;
		boolean street1Exists = false;
		boolean street2Exists =false;
		
		if(business_name !=null && business_name.length()>0){
			nameExists=true;
		}
		if(dba_name !=null && dba_name.length()>0){
			dba_nameExists=true;
		}
		if(street1 !=null && street1.length()>0){
			street1Exists=true;
		}
		if(street2 !=null && street2.length()>0){
			street2Exists=true;
		}
		
		if(nameExists){
			oname = oname+addBlankPadding(business_name, 34);
		}else{
			oname = addBlankPadding(DatumConstants.SINGLEBLANK, 34);
		}
		// if dba_name( dba - Do Business As) exists, then use that for second line, street1 becomes line 3.
		// else if dba_name is not present line 2 becomes street1 and line 3 becomes street2
		if(dba_nameExists){
			oname=oname+addBlankPadding(dba_name, 34);
			if(street1Exists){
				oname=oname+addBlankPadding(street1, 34);
			}
		}else{
			if(street1Exists){
				oname=oname+addBlankPadding(street1, 34);
			}
			if(street2Exists){
				oname=oname+addBlankPadding(street2, 34);
			}
		}
		
		oname = addBlankPadding(oname, 102);
		
		return oname;
	}

	/**
	 * 	
	 * @param str String which we want to pad with blank spaces
	 * @param requiredLength The length of the string after adding the blank spaces.
	 * @return the string after blank spaces added.
	 */
	public String addBlankPadding(String str, int requiredLength) {

		int padding = 0;
		String blankPadding = "";
		if(str!=null){
			//System.out.println("The length of the string "+ str +" before padding is="
				//+ str.length());
		}
		if (str != null && str.length() > 0 && str.length() <= requiredLength) {
			padding = requiredLength - str.length();

			if (padding > 0) {

				for (int i = 0; i < padding; i++) {

					blankPadding = blankPadding + DatumConstants.SINGLEBLANK;
				}

			}
		} else if (str != null && str.length() > requiredLength) {
			blankPadding = "";
			str = str.substring(0, requiredLength);
		} else {
			str="";
			blankPadding = "";
			for (int i = 0; i < requiredLength; i++) {
				blankPadding = blankPadding + DatumConstants.SINGLEBLANK;
			}
		}

		str = str + blankPadding;

		//System.out.println("now the length of the string "+ str +"is=" + str.length());
		return str;
	}
	
	
	/**
	 * 
	 * @param zip
	 * @param zip4
	 * @return
	 */
	
	public String formatZipCode(final String zip, final String zip4){
		
		String ozip = "";
		
	    if(zip!=null && zip.length()>0){
	    	if(zip4!=null && zip4.length()>0){
	    		ozip=addBlankPadding(zip+DatumConstants.HYPHEN+zip4, 10);
	    	}
	    	else{
	    		ozip=addBlankPadding(zip, 10);
	    	}
	    }
		return addBlankPadding(ozip,10);
		
	}
	
	
	
	/**
	 * adding spacing to have consistent width in the fields.
	 * 
	 * @param field
	 * @return
	 */
	public static String addSpacing(String fields[]) {
		String fieldAfterPadding = "";
		int spacing = 25;
		int no_of_spaces = 25;

		if (fields != null) {
			for (int ind = 0; ind < fields.length; ind++) {
				String field = fields[ind];
				String padding = "";

				
				
				if (field != null) {
					no_of_spaces = spacing - field.length();
				}

				for (int i = 0; i < no_of_spaces; i++) {
					padding = padding + " ";
				}

				if (field != null) {
					fieldAfterPadding = fieldAfterPadding+ field + padding;
				} else {
					fieldAfterPadding = padding;
				}
				
			}
		}
		return fieldAfterPadding;

	}
	
	
	public static void main(String[] args) {
		/*FormatFields ff = new FormatFields();
		
		System.out.println(ff.getOName("adsf", "asdfddddd", null, null));
		String cityVal = ff.addBlankPadding(null, 20);
		System.out.println(cityVal + " len="+cityVal.length());
		
		*/
		
		String[] fields = {"First column","Second Col","Third Col"};
		String[] fields2 = {"112222222222.11 ","23232323.20","2.222"};
		
		System.out.println(addSpacing(fields));
		System.out.println(addSpacing(fields2));
		
		
	}
	
	
	
	
	
	
	

}