import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.apache.tools.ant.types.ZipFileSet;
import static helpers.FILESEP;

/*
 * Use build-deps.properties file to generate Ant FileSets for the java build and test classpaths, 
 *   and (if applicable) the list of deployables to include in an EAR archive.
 *
 * Make a list of any generated projects missing from classpath, for use by 'depends.groovy'
 *
 * Fail if 3rd party [non]deployables are missing from SLCommonBuild/lib/...
 */
class InitJavaBuildClasspath {

    private Project project;
    private Properties props;
    // private StringBuilder missingDeployables;
    // private StringBuilder missingGenerated;
    private String branchDir;

    public InitJavaBuildClasspath(Project project, Object props) {
        def pathref;

        this.project=project;
        this.props=(Properties)props;
        this.branchDir= props.get ("sl-common.branch-dir");

        // missingGenerated= new StringBuilder();
        // missingDeployables= new StringBuilder();

        // Create the java build classpath contents
        Path javaBuildClasspath= new Path(project);

        // Add our build-deps entries to it
        ["deployables", "nondeployables", "generated"].each {
            pathref= getBuildPathRef ("${it}");
            if (pathref)
                javaBuildClasspath.add (pathref);
        }

        // if (missingDeployables.length()) {
        //     helpers.fail (project, "### The following files from your build-deps.properties file were not found:\n" + missingDeployables.toString());
        // }

        // if (missingGenerated.length()) {
        //     project.setProperty ("_missingGenerated", missingGenerated.toString());
        // }

        // Set the java build classpath to the project
        project.addReference ("java-build.classpath", javaBuildClasspath);

        // Do the same for test classpath, adding a ref to the above build path and project dist jar
        Path javaTestClasspath= new Path(project);
        javaTestClasspath.add (getBuildPathRef(null));
        Path subpath= javaTestClasspath.createPath();
        subpath.setRefid (new Reference(project, "java-build.classpath"));
        project.addReference ("java-test.classpath", javaTestClasspath);
        // helpers.printRefs(project);
    }

    
    private Object getBuildPathRef (String depName) {
        ZipFileSet earLibFileset= null;
        String refName, fqDepName;
        Path genpath= new Path(project);
        File file;
        Boolean isTestPath= (depName==null);

        if (isTestPath) {
            refName= "java-test.classpath";       
            fqDepName= project.name + ".testdeps";
            depName= "nondeployables";
        } else {
            refName= "java-build." + depName + "-classpath";
            fqDepName= project.name + "." + depName;
            if (!depName.equals("nondeployables")) {
                earLibFileset= new ZipFileSet();
                earLibFileset.prefix = 'lib';
                earLibFileset.dir = new File(props.get (depName));
            }
        }

        List mydeps= helpers.getListFromProp(props.get (fqDepName));

        if (mydeps != null) {
            mydeps.each { item ->
                // if (null != (file= getDepFile (depName,item))) {                   
                if (null != (file= helpers.getDepFile (props, depName, item))) {                   
                    genpath.setLocation (file);
                    if (earLibFileset) {
                        earLibFileset.includes= file.name;
                    }
                }
            }
            if (isTestPath) {
                // Add our project jar to the test classpath
                genpath.setLocation (new File (props.get ("common.dist-dir") + FILESEP + props.get("java-build.dist-filename")));
            }

            if (genpath.size())
                project.addReference (refName, genpath);
    
        } else {
            project.log ("### " + fqDepName + " not defined.  Set it in your " + project.name + "/build/build-deps.properties.", project.MSG_WARN);
        }
    
        // add the ear lib fileset as a ref
        if (earLibFileset) {
            if (!earLibFileset.hasPatterns()) {
                // we still need the fileset ref, or else the 'ear' target will balk,
                // but without any patterns, the entire directory is included, so:
                earLibFileset.excludes="*";
            }
            project.addReference("java-build.ear-lib-fileset." + depName, earLibFileset);
        }
    
        return (project.getReference (refName));
    }
}

new InitJavaBuildClasspath(project, properties);
