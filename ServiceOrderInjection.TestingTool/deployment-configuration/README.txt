The following configuration needs to be applied to the JBoss Node where this tool is deployed:
1. Copy the external-configuration/configuration.properties file to some place accessible to the node.
	- make a note of the full path to this file
2. Edit the file copied in the previous step and make sure that all of the configuration values are correct
	- create the missing folders, as needed
3. Edit the <jboss_home>/server/<node>/deploy/properties-service.xml file to add the system properties defined in the jboss-configuration/properties-service.xml file
	- add an entry for integrationtest.config.file pointing to the file copied in the previous steps.
4. Make sure the datasources defined in the jboss-configuration/mysql-ds.xml file are defined in <jboss_home>/server/<node>/deploy/mysql-ds.xml
5. If deploying this in an INT/QA environment, make sure that the Apache configuration is forwarding URLs to the appropriate mod_jk worker