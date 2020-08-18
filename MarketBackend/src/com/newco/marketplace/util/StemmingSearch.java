package com.newco.marketplace.util;

public class StemmingSearch {
	static String test = "USA";
	public static void main (String arg[]) {

		String input = "USA";	
		System.out.println(compare(test, input));
	}
	
	
	/**
	 * 1) Try to match complete string after removing non alpha chars.
	 * 2) First char must match. Second word must match.
	 * 
	 * @param data
	 * @param userInput
	 * @return
	 */
	public static boolean compare(String data, String userInput) {
		System.out.println("Matching " + userInput + " with " + data);
		boolean flag = partialMatch(data, userInput);
		if (flag) return flag;
		//find out how many words input has
		data = data.replaceAll("\\s+", " ");
		System.out.println(data);
		String nameArray[] = data.split(" ");
		
		String tmp = data.replaceAll("\\W", "");
		int dataLen = tmp.length();
        int halfDataLen = (dataLen / 2) + 1;
        
		String input = userInput.replaceAll("\\s", " ");
		String inputArray[] = input.split(" ");
		int inputlen = inputArray.length;
		if (nameArray.length < inputlen)
			inputlen = nameArray.length;
		
        boolean firstWordMatch = false;
        boolean firstCharMatch = false;
		// make sure that first word's length is more that 3 char and it is a match
        int dataIndex = 0;
        int inputIndex = 0;
        
        String i0 = inputArray[inputIndex].replaceAll("\\W", "");
		String n0 = nameArray[dataIndex].replaceAll("\\W", "");
		
		if (inputArray[0].length() > 3) {			
			firstWordMatch = partialMatch(n0, i0);
			System.out.println("First Word Matched");
		} else if (i0.charAt(0) == n0.charAt(0)) {
			firstCharMatch = true;
			System.out.println("First Char Matched");
		}
		
		if (firstWordMatch == false && firstCharMatch == false)
			return false;
		
		if (firstWordMatch == true) {
			if (i0.length() > halfDataLen){
				return true;
			}
		}
		
		dataIndex ++ ;
		inputIndex ++;
		
		if (inputlen > 1) { //check for second word
			String i1 = inputArray[inputIndex].replaceAll("\\W", "");
			String n1 = nameArray[dataIndex].replaceAll("\\W", "");
			flag = partialMatch(n1, i1);
			if (flag)  {
				return true;
			}
		}
		
		//Check third word of company with 2 & third word of input 
		dataIndex ++;
		if (firstWordMatch == true && inputlen > inputIndex) {
			String i2 = inputArray[inputIndex].replaceAll("\\W", "");
			String n2 = nameArray[dataIndex].replaceAll("\\W", "");
			flag = partialMatch(n2, i2);
			if (flag)  {
				return true;
			} 
		}
		
		inputIndex ++;
		if (firstWordMatch == true && inputlen > inputIndex) {
			String i2 = inputArray[inputIndex].replaceAll("\\W", "");
			String n2 = nameArray[dataIndex].replaceAll("\\W", "");
			flag = partialMatch(n2, i2);
			if (flag)  {
				return true;
			} 
		}
		return false;
	}
	
	private static boolean partialMatch(String data, String userInput) {
		if (userInput == null || data == null) 
			return false;
		String name = data.replaceAll("\\W", "");
		String input = userInput.replaceAll("\\W", "");
		name = name.toLowerCase();
		input = input.toLowerCase();
		int namelen = (int)(name.length() /2) + 1; // length is more than 50% of the input string
		
		if (namelen < input.length()) { 
			int i = name.indexOf(input);
			if (i != -1) {
				System.out.println("Matched:" + name);
				return true;
			}
		}
		return false;
	}
}
