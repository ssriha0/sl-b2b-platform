import org.apache.tools.ant.Project;


class helpers {

    public static final String FILESEP= System.getProperty("file.separator");


    // Same as ant.fail(message), but this one works.
    static void fail (Project project, String message) {
        // ant.fail(message);  <-- this doesn't include metainf for some reason
        def task= project.getTaskDefinitions().get("fail").newInstance();
        task.setMessage (message);  
        task.execute();
    }


    // Dump all Ant properties to the console
    static void dumpProps(Properties props) {
        println ("\nPROPERTIES:");
        props.keySet().each { key ->
            println ("\t" + key + " = " + props.getProperty (key));
        }
    }


    // Dump all Ant references to the console
    static void printRefs(Project project) {
        println '\nPROJECT REFERENCES'
        project.references.each { entry ->
            println "$entry.key = \"$entry.value\""
            println "$entry.key is-a " + entry.value.class.name
        } 
    }


    // Ensure jar file names contain a version number.
    //   OK:  spring2.5.jar, hibernate-3.2.5.ga.jar, commons-io-1.4.jar, j2ee-v20030731.jar
    //  NOT:  spring2.jar (needs minor version 2.0, 2.5)
    //        axis.jar (note it's v1.4 in the MANIFEST.mf, so rename to axis-1.4.jar)
    //        j2ee.jar (empty manifest, so use version based date of files within: j2ee-20030731.jar)
    static Boolean hasVersion (File file) {
        if (!file || !file.isFile()) 
            return true;

        String fn= file.getName();
        return (!file.isFile() || file.getAbsolutePath().contains("generated")
            || fn.equals("ejb3-persistence.jar")
            || fn.equals("ibatis-common-2.jar")
            || fn.equals("ibatis-sqlmap-2.jar")
            || fn.equals("hibernate3.jar")
            || fn =~ /.*\d\.\d.*/ || fn =~ /.*\d{8}.jar/);
    }


    // Given a property value string (e.g. Project.generated), 
    //   return all comma/space delimited values as a List of Strings
    static List getListFromProp (String prop) {
        List list= [];
        if (prop && prop.length()) {
            String deps= prop.replaceAll("[,;: \t]+", " ").replaceAll(" +"," ");
            def x= deps?.split(" ");
            x?.each { list << it; }
        }
        return list;
    }


    // Find and return dependent file under SLCommonBuild/lib/...
    // depName = [generated,[non]deployables]
    // proj=some.jar, or subdir/some[.jar], or /deployables/subdir/some[.jar], or some.war
    static File getDepFile (props, depName, proj) {
        File file;

        proj= proj?.trim();
        if (!proj)
            return null;

        String projBase= proj;

        // Stuff like "Wallet/Wallet.ValueLink.EJB" is still compiled to 'generated', not 'generated/Wallet',
        //   so chop off the directory references for generated projects here:
        int i= projBase.lastIndexOf ('/');
        if (i>0) {
            projBase= projBase.substring(i+1);
        }

        if (depName == "generated") {
            i= proj.indexOf ('.war');
            if (i > 0) {
                proj= proj.substring(0,i)

                // e.g. proj == MarketFrontend.war, so look for class files in MFE's classes dir
                ["bin", "target/classes"].each {
                    File tmpfile= new File (branchDir + FILESEP + projBase + FILESEP + "${it}");
                    println "@@@ initJavaBuildClasspath.groovy.getDepFile(): file = ${file.absolutePath}";
                    if (tmpfile.isDirectory() && tmpfile.listFiles().length)
                        file=tmpfile;
                }

                if (!file) {
                    println "@@@ initJavaBuildClasspath.groovy.getDepFile(): missing ${proj} = ${projBase}";
                    // missingGenerated.append(proj + " ");
                }
            } else {
                // allow 'generated' list to contain '.jar'
                i= proj.indexOf ('.jar');
                if (i > 0) {
                    proj= proj.substring(0,i);
                }
            }
        }

        // proj refers to a jarfile,
        // with an absolute path to jar, rooted under SLCB/lib, e.g. /deployables/some/file.jar
        // or a relative path to jar, so look under to SLCB/lib/{depName}
        String buildLibDir= props.get ("java-build.lib-dir");
        String libpath= ((proj.startsWith("/"))
            ? buildLibDir
            : props.get (depName) + FILESEP);

        file= new File (libpath + projBase);
        if (!file.exists() && !proj.endsWith(".war") && !proj.endsWith(".jar"))
            file= new File (libpath + projBase + ".jar");

        return file;
    }


    static void logFilesMissingVersionNumbers (project, List files) {           // // // // // // // // // // // // // // // // // // // // // // TO DO
        // careful here - files is probably alldeps, a list of strings.  
        files.each { depfile ->
            if (!hasVersion(file)) {
                project.log ("### JAR FILENAME LACKS VERSION NUMBER (e.g. file-2.3.jar, or file-20090814.jar): " + file.name, project.MSG_WARN);
            }
        }
    }
}



