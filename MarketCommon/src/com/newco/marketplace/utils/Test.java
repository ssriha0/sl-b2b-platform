package com.newco.marketplace.utils;

import java.util.HashSet;

public class Test{
	public static void main(String args[])
	{
		/*** build sql in clause start ***/
						String strDesc = "Chandrika Harini";
						strDesc = strDesc.toUpperCase();
						char[] cDesc = strDesc.toCharArray();
							int charLength = cDesc.length;
							System.out.println("CharLength-----"+charLength);
							//int arrLength = (factorial(charLength))/(2 * factorial(charLength-2));
							int arrLength = (charLength * (charLength-1)) / 2; 
							System.out.println("ArrayLength-----"+arrLength);
							
							int intRepetitionCtr = 0;
							String sCharacterd[]= new String[arrLength];
							 HashSet hCollection =  new HashSet();
								int counter = 0;
							 for(int i = 0; i < cDesc.length ; i++)
							 {
								String sCharacters = "";
									for(int j = i; j < cDesc.length ; j++)
									{
										// if the character is the single quote, make it 2 single quotes as
										// required by the sql statement.
										String sCharacter ="";
										if (cDesc[j] == '\'')
										{
											sCharacter = "" + "''";
										}
										else
										{	
												sCharacter = "" + cDesc[j];
										}
										sCharacters = sCharacters + sCharacter;                
										System.out.println("scharacters here is =="+sCharacters);
										
											        				
										// add only uique string values to the sql In clause
										if (hCollection.add(sCharacters) == true && sCharacters.length()>1)
										{
											System.out.println("scharacter inside unique=="+sCharacters);
											if(!isStringNull(sCharacters))
											{
												sCharacterd[counter] = new String();
												sCharacterd[counter] = sCharacters;
												System.out.println("output String here is------------"+counter+"="+sCharacterd[counter]);
												counter++;
											}
										         	               
										}
										else if(hCollection.add(sCharacters) == false && sCharacters.length()>1)
										{
											intRepetitionCtr = intRepetitionCtr + 1;
											System.out.println("scharacter inside repeat=="+sCharacters);
										}  
								
									}		
			
							}
							System.out.println("Counter size " + counter);
							String sCharacterFinal[]= new String[counter];
							if(intRepetitionCtr > 0)
							{
								System.arraycopy(sCharacterd,0,sCharacterFinal,0,counter);
								for(int i=0;i<counter;i++)
									System.out.println("Final array i= " + i + "--" + sCharacterFinal[i]);
								
							}
								
	
		
	//	System.out.println("output String here is------------"+sCharacterd.length);	
		 
		
	}
	/**
		 * To find a factorial of a given number
		 * @param n
		 * @return int
		 */		 
		public static int factorial(int n) {
				int ans = 1;
				for (int i = 1; i <= n; i++)
				   ans = ans * i;
				return ans;
			}
	public static boolean isStringNull(String strResult)
					{
						if((strResult == null) || (strResult.equals("")))
						return true;
						return false;
					}
		
}