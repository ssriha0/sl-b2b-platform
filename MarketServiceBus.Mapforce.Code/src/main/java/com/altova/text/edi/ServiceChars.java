////////////////////////////////////////////////////////////////////////
//
// ServiceStringAdvice.java
//
// This file was generated by MapForce 2011sp1.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the MapForce Documentation for further details.
// http://www.altova.com/mapforce
//
////////////////////////////////////////////////////////////////////////

package com.altova.text.edi;

import java.io.Writer;
import java.io.IOException;

public class ServiceChars {

	public static final byte None=0;
	public static final byte ComponentSeparator = 1;
	public static final byte DataElementSeparator = 2;
	public static final byte SegmentTerminator = 3;
	public static final byte ReleaseCharacter = 4;
	public static final byte DecimalSeparator = 5;
	public static final byte RepetitionSeparator = 6;
    public static final byte SubComponentSeparator = 7;
	
	char[] mSeparators = "\0:+\\?.!&".toCharArray();
	
	public char getSeparator(byte sep)
	{
		return sep>7?'\0':mSeparators[sep];	
	}
	
	public char getComponentSeparator() {
		return mSeparators[ComponentSeparator];
	}

	public void setComponentSeparator(char componentSeparator) {
		mSeparators[ComponentSeparator] = componentSeparator;
	}

	public char getDataElementSeparator() {
		return mSeparators[DataElementSeparator];
	}

	public void setDataElementSeparator(char dataElementSeparator) {
		mSeparators[DataElementSeparator] = dataElementSeparator;
	}

	public char getDecimalSeparator() {
		 return mSeparators[DecimalSeparator];
	}

	public void setDecimalSeparator(char decimalSeparator) {
		mSeparators[DecimalSeparator] = decimalSeparator;
	}

	public char getReleaseCharacter() {
		return mSeparators[ReleaseCharacter];
	}

	public void setReleaseCharacter(char releaseCharacter) {
		mSeparators[ReleaseCharacter] = releaseCharacter;
	}

	public char getSegmentTerminator() {
		return mSeparators[SegmentTerminator];
	}

	public void setSegmentTerminator(char segmentTerminator) {
		mSeparators[SegmentTerminator] = segmentTerminator;
	}
	
	public char getRepetitionSeparator() {
		return mSeparators[RepetitionSeparator];
	}

	public void setRepetitionSeparator(char repetitionSeparator) {
		mSeparators[RepetitionSeparator] = repetitionSeparator;
	}
    
	public char getSubComponentSeparator() {
		return mSeparators[SubComponentSeparator];
	}

	public void setSubComponentSeparator(char subcomponentSeparator) {
		mSeparators[SubComponentSeparator] = subcomponentSeparator;
	}
	
	public void serialize(Writer stream) throws IOException {
		stream.write("UNA");
		stream.write(getComponentSeparator());
		stream.write(getDataElementSeparator());
		stream.write(getDecimalSeparator());
		if( getReleaseCharacter() != '\0' )
			stream.write(getReleaseCharacter());
		else
			stream.write( ' ');

		if( getRepetitionSeparator() != '\0' )
			stream.write(getRepetitionSeparator());
		else
			stream.write( ' ');

		stream.write(getSegmentTerminator());
	}
}
