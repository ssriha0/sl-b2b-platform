import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;

/*
 * Anything to do with JBoss is in here, currently just [un]deploy jar/war/ear archives.
 */
class JBoss 
{
    private AntBuilder ant;
    private Project project;
    private Properties props;
    private Target target;
    private List aliases;


    /* Init me privates, invoke handlers based on the name of the target we were invoked from */
    JBoss (AntBuilder ant, Project project, Object props, Target target) {
        this.ant=ant;
        this.project=project;
        this.props=(Properties)props;
        this.target=target;

        aliases= props.get("sl-jboss.server.aliases")?.replaceAll("[,;: \t]"," ")?.split(" ");

        if (aliases==null || !aliases.size()) {
            helpers.fail (project, "sl-jboss.server.aliases not defined.  RTFM @ SLCommonBuild/doc.");
        }

        switch (target.name) {
            case "jboss-jar-deploy":
                deployJar();
                break;

            case "jboss-war-deploy":
                deployWear('war');
                break;

            case "jboss-war-undeploy":
                undeployWear('war');
                break;

            case "jboss-ear-deploy":
                deployWear('ear');
                break;

            case "jboss-ear-undeploy":
                undeployWear('ear');
                break;

            default:
                println ("Eh?");
        }
    }


    // Copy our jar AND all dependent jars to deploy area(s).
    // Get list of dependent jars from ???
    private void deployJar() {
        helpers.fail(project, "N'yet!!!");
    }


    /* Deploy current project (type 'war' or 'ear') to all servers/nodes the properties
     *   say it should live in (provided the server/NODE/deploy/ dirs exist).
     */
    private void deployWear(String type) {
        File archive= new File (props.get ("java-" + type + ".dist-file"));

        if (!archive.isFile()) {
            helpers.fail (project, "Can't find src file: " + archive.absolutePath);
        }

        aliases.each { alias ->
            def serverDir= props.get ("sl-jboss.server." + alias + ".server-dir");
            def nodes= props.get("sl-jboss.server." + alias + ".nodes")?.replaceAll("[,;: \t]"," ")?.split(" ");
            
            nodes.each { node ->
                def deployablesKey= "sl-jboss.server." + alias + "." + node + ".deployables";
                def deployables= props.get (deployablesKey)?.replaceAll("[,;: \t]"," ")?.split(" ");
                
                if (!Arrays.asList(deployables).contains(archive.name)) {
                    project.log ("Not deploying ${archive.name} to '${alias}' node '${node}' since ${archive.name} isn't listed in ${deployablesKey}", project.MSG_INFO);
                } else {

                    File deployDir= new File("${serverDir}/${node}/deploy");
                    File explodeDir = new File (deployDir.absolutePath + helpers.FILESEP + archive.name);
    
                    if (!deployDir.isDirectory()) {
                        project.log (deployDir.absolutePath + " doesn't exist.  Skipping.", project.MSG_INFO);
                    } else {
                        if ("true".equals(props.get("sl-jboss.explode-deployed"))) {
                            if (explodeDir.isFile()) {
                                helpers.fail (project, "sl-jboss.explode-deployed=true, but ${archive.name} exists as a file in the JBoss deploy dir.  Do an undeploy, delete the file, or alter the property in your host.properties file or something.  PATH:  ${explodeDir.absolutePath}");
                            } 
                            if (!explodeDir.isDirectory())
                                explodeDir.mkdir();
                            // TODO: ant.unwar works, but why bother unbundling everything, when we already have the src files?
                            //      java-war.web-content-dir + java-war.libs-dir
                            println "\nExploding ${type} to ${deployDir.absolutePath}...";
                            ant.unwar (src:archive, dest:explodeDir, overwrite:true);
                        } else {
                            if (explodeDir.isDirectory())
                                helpers.fail (project, "sl-jboss.explode-deployed=false, but ${archive.name} exists as a directory in the JBoss deploy dir.  Do an undeploy, delete the directory, or alter the property in your host.properties file or something.  PATH:  ${explodeDir.absolutePath}");
                            else {
                                println "\nCopying ${archive.name} to ${deployDir.absolutePath}...";
                                ant.copy (file:archive, todir:deployDir ,overwrite:true, verbose:true);
                            }
                        }
                    }
                }
            }
        }
    }


    /* Undeploy all instances of current project (type 'war' or 'ear') from all servers/nodes the 
     *   properties say it should live in.
     */
    private void undeployWear(String type) {
        File archive= new File (props.get ("java-" + type + ".dist-file"));

        aliases.each { alias ->
            def serverDir= props.get ("sl-jboss.server." + alias + ".server-dir");
            def nodes= props.get("sl-jboss.server." + alias + ".nodes")?.replaceAll("[,;: \t]"," ")?.split(" ");

            nodes.each { node ->
                def deployablesKey= "sl-jboss.server." + alias + "." + node + ".deployables";
                def deployables= props.get (deployablesKey)?.replaceAll("[,;: \t]"," ")?.split(" ");

                if (Arrays.asList(deployables).contains(archive.name)) {

                    File deployDir= new File("${serverDir}/${node}/deploy");
                    File explodeDir = new File (deployDir.absolutePath + helpers.FILESEP + archive.name);

                    if (!deployDir.isDirectory()) {
                        project.log (deployDir.absolutePath + " doesn't exist.  Skipping.", project.MSG_INFO);
                    } else {
                        if (explodeDir.isFile()) {
                            ant.delete (file:explodeDir, verbose:true, failonerror:false);
                        } else if (explodeDir.isDirectory()) {
                            ant.delete (dir:explodeDir, failonerror:false);
                        }
                    }
                }
            }
        }
    }
}


new JBoss (ant, project, properties, target);

