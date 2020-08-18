import org.apache.tools.ant.Target;
import org.apache.tools.ant.Project;
import static helpers.FILESEP;

/* 
 * Perform actions against generated projects (those listed in build-deps.properties as 'generated'):
 *  - Invoked by -build-missing-generated target to attempt to build any missing generated projects found by initJavaBuildClasspath.groovy.
 *  - Invoked by -depends target (via clean-generated and getlatest-generated) to perform an action against ALL dependent projects.
 */
class Depends {

    private Project project;
    private Properties props;
    private Target target;
    private Vector succeededTargets;
    private String branchDir;
    private List shitlist = [];  // build-deps we've tried/failed to find already 
    private String mydeps;
    private List mydepsList;

    static final String DEPLISTKEY= "depends.depList";
    

    Depends (Project project, Object props, Target target) {
        this.project=project;
        this.props=(Properties)props;
        this.target=target;
        this.branchDir= props.get ("sl-common.branch-dir");
        this.succeededTargets= new Vector();
        this.mydeps= props.get(project.name + ".generated");
        this.mydepsList= helpers.getListFromProp(mydeps);

        switch (target.name) {
            case "-build-missing-generated":
                assertDeployablesExist();
                buildMissingGenerated();
                break;

            case "-depends":
                // setNewProperty will ONLY set property if it hasn't already been set:
                project.setNewProperty("target", "dist");
                doDepends(null);
                break;

            case "showdepends":
                showDepends();
                break;

            default:
                helpers.fail (project, "What???");
        }
    }


    // Fail the build if any [non]deployables are missing from CLASSPATH
    private void assertDeployablesExist() {
        def missing= [];
        def errmsg= new StringBuilder();

        ["deployables", "nondeployables"].each { depType ->
            missing= getMissingJars(depType);
            if (missing.size()) {
                errmsg += "\n${depType} specified in ${project.name}'s build-deps are missing:\n\t- " + missing.join ('\n\t- ');
            }
        }

        if (errmsg.length()) {
            helpers.fail(project, errmsg);
        }
    }


    // Return a list of any missing jars for the given a depType [generated, [non]deployable]
    private List getMissingJars(String depType) {
        List missing= [];
        List badnames= [];

        List deps= (depType == "generated" 
            ? mydepsList 
            : helpers.getListFromProp(props.get(project.name + "." + depType)));

        deps.each { dep ->
            File file= helpers.getDepFile (props, depType, dep);
            if (!file.isFile())
                missing << dep;

            if ("generated" != depType && !helpers.hasVersion(file)) {
                badnames << file;
            }
        }
    
        if (badnames.size()) {
            println ("### WARNING: ${depType} JAR filenames lack version number (e.g. file-2.3.jar, file-20090814.jar): ");
            badnames.each { println ("\t- " + it.absolutePath); }
        }

        return missing;
    }


    // Build any dependencies missing from CLASSPATH, along with any missing deps' deps
    private void buildMissingGenerated() {
        // List mydeps= helpers.getListFromProp(props.get(project.name + ".generated"));
        List alldeps= getDepsDeps(mydepsList);
        List missingGenerated= getMissingJars("generated");

        // Build all 'generated' that don't exist in SLCB/lib/generated
        if (missingGenerated.size()) {
            println ("### The following jars specified in ${project.name}'s build-deps.generated were NOT found in SLCommonBuild/lib/generated:");
            missingGenerated.each { println ("\t- " + it); }
            println "### ...building them now...";
            project.setProperty("target", "dist");
            doDepends(missingGenerated);
        }

        // if one of those generated deps has a dep missing dep, we could fail, or even build a war/ear with missing jars.
        // so, create a list of missing generated deps deps and build 'em.
        for (Iterator iter= alldeps.iterator(); iter.hasNext(); ) {
            def dep= iter.next();
            if (mydeps.contains(dep)) {
                iter.remove();
            } else {
                // if jarfile exists, we don't need to build it
                File f= helpers.getDepFile (props, "generated", dep);
                if (f?.isFile()) {
                    iter.remove();
                }
            }
        }

        if (alldeps.size()) {
            println "===============================================================================";
            println " Some generated deps are missing generated deps.  Building now:";
            alldeps.each { println "\t- ${it}"; }
            println "===============================================================================";
            project.setProperty("target", "dist");
            doDepends(alldeps);
        }
    }


    // Invoke key specified in 'target' property for projects in depends list (or all, if depends==null)
    // depends is a List<String> containing the 'generated' property values as specified in build-deps.
    private void doDepends(List depends) {
        // Get full dep tree.  Reason: targets like clean-generated COULD
        //   call clean-generated on their deps, but that'd result in a lot of redundancy.  The 
        //   alldeps list we generate here is unique and stuff.
        if (!depends) {
            if (null == mydepsList || mydepsList.isEmpty()) {
                return;
            } 
            depends= getDepsDeps(mydepsList);
        }

        depends.each { dep ->
            // Find and invoke target named for dependency, if it exists, e.g. "MarketCommon" or "MarketFrontend.war"
            if (null != (target= project.targets.get(dep))) {
                executeTarget (succeededTargets, target);
            } else {
                // invoke default target on dep antfile, or FAIL
                // We look for build-asl.xml first, then build.xml
                String projdir= props.get ("sl-common.branch-dir") + "/" + dep;
                def found=false;  // can't break from a .each
                ["build-asl.xml", "build.xml"].each { antfile ->
                    if (!found && new File("${projdir}/build/${antfile}").exists()) {
                        found=true;
                        project.setProperty ("groovy-call-ant-dir", "${projdir}");
                        project.setProperty ("groovy-call-ant-file", "build/${antfile}");
                    }
                }
                if (!project.getProperty("groovy-call-ant-dir"))
                    helpers.fail (project, "No build[-asl].xml found for ${dep} under ${projdir}/build");

                Target gca= project.targets.get("-groovy-call-ant")
                gca.performTasks();
            }
        }

        project.log ("-------------------------------------------------------------------------------\n"
            + "   Continuing with ${project.name}... \n"
            + "-------------------------------------------------------------------------------", project.MSG_INFO);
    }


    // target.performTasks does NOT respect dependencies, or track when theyve been fired
    private void executeTarget (Vector succeededTargets, Target target) {
        Vector targetDepends= new Vector();
        Target depTarget;

        target.dependencies.each { 
            depTarget= project.targets.get(it);
            if (!succeededTargets.contains(depTarget)) {
                succeededTargets << depTarget;
                targetDepends << depTarget;
            }
        }
        project.executeSortedTargets(targetDepends)

        // ...and then fire the main target, and add to our used list
        target.performTasks()
        succeededTargets.add (target)
    }


    // Return a list of all of the dependencies your deps depend on, depth first.
    // Caches results in a property for fast reentry.
    private List getDepsDeps (List deps) {

        List alldeps= [];
        List newdeps= new ArrayList(deps);
        String alldepsProp= properties.get ("depends.alldeps");

        if (alldepsProp==null) {
                                                                        
            deps.each { dep ->
                def depList= props.getProperty (DEPLISTKEY) ?: "";
                props.setProperty (DEPLISTKEY, depList + " -> " + dep + "\n");

                addDeps(alldeps, newdeps, dep, "    ");
            }
            alldepsProp= "";
            alldeps.each { alldepsProp += "${it} "; }
            project.setProperty ("depends.alldeps", alldepsProp);
        } else {
            alldeps= helpers.getListFromProp(alldepsProp);
        }

        return alldeps;
    }


    // Add 'dep' to 'mydeps', along with dependency tree for 'dep'
    // This is the recursive part of getDepsDeps()
    private void addDeps (List alldeps, List newdeps, String dep, String padding) {

        def noext= /\.[jw]ar/
        String depProjPath= dep.replaceAll (noext, "").trim();
        String depProjName= depProjPath.replaceAll (/.*\//, "").trim();
        File file= new File (branchDir + FILESEP + depProjPath + FILESEP + "build" + FILESEP + "build-deps.properties");

        if (!shitlist.contains(depProjPath)) try {
            shitlist << depProjPath;
            Properties depBuildDepsProps= new Properties();
            depBuildDepsProps.load (new BufferedInputStream(new FileInputStream(file)));

            List generated= helpers.getListFromProp(depBuildDepsProps.get (depProjName + ".generated"));

            if (generated && generated.size()) {
                generated.each { depDep ->
                    depDep= depDep.trim();
                    String depDepNoExt= depDep.replaceAll (noext, "");

                    def depList= props.getProperty (DEPLISTKEY) ?: "";

                    // ...but only add it to the alldeps list if we haven't already!
                    if (depDepNoExt.length() && !newdeps.contains(depDepNoExt)) {
                        props.setProperty (DEPLISTKEY, depList + padding + "-> " + depDep + "\n");
                        newdeps << depDepNoExt;
                        addDeps (alldeps, newdeps, depDep, padding + "   ");
                    } else {
                        props.setProperty (DEPLISTKEY, depList + padding + "-> [" + depDep + "]\n");
                    }
                }
            }
        } catch (FileNotFoundException fnf) {
            // Assume this is for a legacy buildfile that doesn't have a build-deps.
            project.log ("### Can't find " + file.absolutePath + ".  Legacy build.xml file?", project.MSG_INFO);
        } catch (Exception ex) {
            project.log ("### Can't read from " + depProjName + "'s build dependencies: \n\t" + ex.message, project.MSG_WARN);
        }

        dep= dep.replaceAll (noext, "").trim();
        alldeps << dep.trim();
    }


    // Vomit our 'generated' dependency tree to the console.
    private void showDepends () {
        getDepsDeps(mydepsList);
        def depDeps= props.getProperty (DEPLISTKEY);
        if (depDeps) {
            println "${project.name}\n${depDeps}";
        }
    }

}

new Depends(project, properties, target);
