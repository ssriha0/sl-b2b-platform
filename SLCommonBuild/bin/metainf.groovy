/*
 * Print manifest info and md5sum for any file that has a manifest.
 *   Steve Hunter
 */

import java.util.jar.*;
import java.security.MessageDigest;


public class metainf {

	private def shortList= [
		"Created-By", 
		"Implementation-Version", 
		"Build-Date", 
		"Build-Username", 
		"Build-Hostname", 
		"Build-java-home", 
		"Build-os-name", 
		"Build-os-version"];

    public static void main (String [] args) 
    {
    	if (!args.length) {
    		System.err.println ("Usages: \n\tmetainf file.jar [file2.jar ...]\n\tmetainf path/to/*.jar\n\tAlso works with war and ear files.");
    		System.exit(1);
    	}
    	new metainf().processArgs(Arrays.asList(args));    		
    }


	void processArgs (List argv) 
	{    
    	argv.each { fn ->
    		File file= new File(fn);
    		if (file.isFile()) {
	            println "\n\n${file.name}\n---------------------------------------------";
				def mf= getManifest(file);
				if (mf) {
					def attrs = mf.getMainAttributes();
					if (!"Service Live".equals(attrs.getValue("Implementation-Vendor"))) {
						attrs.keySet().each   { printf ("%-25s %s\n", "${it}:", attrs.getValue(it)); }
	    			} else {
	    				shortList.each { printf ("%-25s %s\n", "${it}:", attrs.getValue(it)); }
	    			}					
	    			printf ("%-25s %s\n", "md5sum:", getChecksum(file));
				}
			}    		
        }	
	}
	
	
	Manifest getManifest(File file) 
	{
		try {
	        return new JarFile(file, false, JarFile.OPEN_READ).manifest;
        } catch (Throwable t) {
        	System.err.println "Unable to get manifest: ${t.message}";
        	return null;
        }
	}


	// courtesy http://www.javalobby.org/forums/thread.jspa?threadID=84420
	String getChecksum (File f)
	{
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			InputStream is = new FileInputStream(f);				
			byte[] buffer = new byte[8192];
			int read = 0;
			while((read = is.read(buffer)) > 0) {
				digest.update(buffer, 0, read);
			}		
			byte[] md5sum = digest.digest();
			BigInteger bigInt = new BigInteger(1, md5sum);
			return bigInt.toString(16);
		} catch (Throwable t) {
			System.err.println "Unable to process file for MD5: ${t.message}";
			return "";
		} finally {
			try { is.close(); }
			catch(IOException e) {
				throw new RuntimeException("Unable to close input stream for MD5 calculation", e);
			}
		}		
	}
}
