package com.servicelive.spn.excel;

/**
 * 
 * @author svanloo
 *
 */
public class Function {
	private String functionCall;

	/**
	 * 
	 * @param functionCall
	 */
	public Function(String functionCall) {
		this.functionCall = functionCall;
	}

	@Override
	public String toString() {
		return functionCall;
	}
}
