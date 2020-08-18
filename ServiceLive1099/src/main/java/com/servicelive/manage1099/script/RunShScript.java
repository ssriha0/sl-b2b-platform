package com.servicelive.manage1099.script;

import java.util.logging.Level;

import com.servicelive.manage1099.constants.DatumConstants;
import com.servicelive.manage1099.constants.FileConstants;
import com.servicelive.manage1099.constants.MessageConstants;
import com.servicelive.manage1099.log.Log;
import com.servicelive.manage1099.util.CommonUtil;

/**
 * 
 * @author mjoshi1
 *
 */
public class RunShScript {

	
	/**
	 * Runs the script file from the following location FileConstants.PGP_ENCRIPTION_SCRIPT
	 * @return
	 */
	public static int runScriptFile() throws Exception {
		
		int status =0;
		try
		{
				//System.out.println("Run Command: "+preparePGPScript());
				Process proc = Runtime.getRuntime().exec(preparePGPScript());
				// Wait here until the PGP file is created. The java code waits here until PGP is complete.
				//
				int result = proc.waitFor();
				
				System.out.println(" The result from script is :"+result);
				
				if (result!=0){
					status =0;
					System.out.println("******************Error Error Error Error  *********************");
					System.out.println("******************Error running GPG command *********************");
					Log.writeLog(Level.SEVERE, "Error running GPG command :"+FileConstants.PGP_ENCRIPTION_SCRIPT);
					throw new Exception( MessageConstants.ERROR_PROCESSING_FILE +preparePGPScript());
					
				}
		}
		catch(Exception e)
		{
			
			status =1;
			System.out.println("******************Error Error Error Error  *********************");
			System.out.println("******************Error running GPG command *********************");
			e.printStackTrace();
			Log.writeLog(Level.SEVERE, "Error running GPG command :"+FileConstants.PGP_ENCRIPTION_SCRIPT +"   "+e.getStackTrace());
			throw new Exception(MessageConstants.ERROR_PROCESSING_FILE +"   "+preparePGPScript());
		}
		return status;
	}
	
	
	
	
	
	
	/**
	 * Builds the command
	 * e.g. gpg --yes -eq --trust-model always -r "asandler@kmart.com" -o <INPUTFILENAME>.pgp <INPUTFILENAME>
	 * @return
	 */
	
	public static String preparePGPScript(){
		StringBuilder command = new StringBuilder(FileConstants.PGP_ENCRYPTION_SCRIPT_FILE);
		command.append(DatumConstants.SINGLEBLANK);
		command.append(FileConstants.FILE_NAME_WITH_PATH);
		command.append(FileConstants.PGP_EXTENSION);
		command.append(DatumConstants.SINGLEBLANK);
		command.append(FileConstants.FILE_NAME_WITH_PATH);
		
		//System.out.println("The complete command is ="+command.toString());
		return command.toString();
	}
	
	

}
