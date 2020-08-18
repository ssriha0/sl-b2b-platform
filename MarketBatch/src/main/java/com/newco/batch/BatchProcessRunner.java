package com.newco.batch;

public class BatchProcessRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ABatchProcess theProcess = null;
		try {
			System.out.println("running " + args[0]);
			theProcess = (ABatchProcess) Class.forName(args[0]).newInstance();
			theProcess.setArgs(args);
			theProcess.process(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
