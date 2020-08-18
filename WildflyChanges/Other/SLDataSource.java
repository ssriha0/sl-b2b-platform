package com.newco.marketplace.datasource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class represents an encapsulated DataSource that allows transparent
 * switching between jdbc-based connections and jndi-based connections. To use
 * jdbc, simply set a VM system property like this: -Dusejdbc=true. When this
 * system property is set, the SLDataSource will connect using the jdbc-based
 * parameters. In the absence of the "usejdbc=true" system property, the
 * SLDataSource will connect using the jndi-based parameters. <br>
 * Having this kind of abstraction is useful when a developer is testing a
 * persistent class locally, outside of an application server environment, and
 * they need a database connection. They can simply set the "usejdbc" property
 * and the DataSource will automatically switch to jdbc mode. However, when the
 * app runs inside of an application server (for example, wrapped by an EJB),
 * the absence of the "usejdbc" property will cause the DataSource to operate
 * with JNDI.
 *
 * @author Swamy Patsa
 *
 */
public class SLDataSource implements DataSource {

	/**
     * The data source.
     */
    private DataSource dataSource = null;

    /**
     * The JNDI name for lookup.
     */
    private String jndiName = null;

    /**
     * The JDBC drive class name.
     */
    private String driverClassName = null;

    /**
     * The url for the database.
     */
    private String url = null;

    /**
     * The username for the database.
     */
    private String username = null;

    /**
     * The password for the database/
     */
    private String password = null;

    /**
     * The logger for this class.
     */
    private static Log logger =
        LogFactory.getLog(SLDataSource.class.getName());

    /**
     * The jdbc class name key.
     */
    private static final String JDBCCLASS = "jdbc.classname";

    /**
     * The jdbc url key.
     */
    private static final String JDBCURL = "jdbc.url";

    /**
     * The jdbc password key.
     */
    private static final String JDBCPSSWD = "jdbc.password";

    /**
     * The jdbc user name key.
     */
    private static final String JDBCUSER = "jdbc.user";

    /**
     * Constructor for SLDataSource.
     */
    public SLDataSource() {
        super();
    }

    /**
     * Retrieves a configured Data Source.
     *
     * @return The configured data source.
     */
    private DataSource getConfiguredDataSource() {
        if (dataSource == null) {

            String useJdbc = System.getProperty("usejdbc");

            if (useJdbc != null && useJdbc.trim().equals("true")) {
                logger.info(
                    "JDBC system property has been set -- using local JDBC" +
                    " driver.");

                //look for a properties file in the classpath for jdbc
                // properties
                String jdbcPropsFile = System.getProperty("jdbcpropsfile");

                if (jdbcPropsFile != null) {

                    try {

                        dataSource = getPropsJDBC(jdbcPropsFile.trim());

                    } catch (IOException e) {
                        logger.error(
                            "The properties file could not be parsed!" +
                            "  Defaulting to the Spring JDBC.",
                            e);
                        dataSource = getDefaultJDBC();
                    }
                } else {
                    //  use a straight jdbc DataSource
                    dataSource = getDefaultJDBC();
                }

            } else {

                try {

                    java.util.Properties parms = new java.util.Properties();
                    javax.naming.Context ctx =
                        new javax.naming.InitialContext(parms);

                    // Lookup through the naming service to retrieve a
                    // datasource
                    dataSource = (DataSource) ctx.lookup(getJndiName());
                } catch (Exception e) {
                    logger.error("Error attempting to get JNDI DataSource.", e);

                }

                if (dataSource == null) {
                    throw new NullPointerException(
                        "Unable to locate JNDI DataSource with name '" +
                            getJndiName() +
                            "'");
                }
            }
        }

        return dataSource;

    }

    /**
     * Sets the default JDBC connection from the spring application context.
     *
     * @return The data source.
     */
    private DataSource getDefaultJDBC() {
        BasicDataSource dataSource1 = new BasicDataSource();
        dataSource1.setDriverClassName(getDriverClassName());
        dataSource1.setUrl(getUrl());
        dataSource1.setUsername(getUsername());
        dataSource1.setPassword(getPassword());
        return dataSource1;
    }

    /**
     * Retrieves the JDBC properties.
     *
     * @param fileName  The name of the file containing the JDBC properties.
     * @return  The data source.
     * @throws IOException  Exception if file not found.
     */
    private DataSource getPropsJDBC(final String fileName) throws IOException {

        Properties props = loadProps(fileName);

        BasicDataSource dataSource2 = new BasicDataSource();

        dataSource2.setDriverClassName(props.getProperty(JDBCCLASS));
        dataSource2.setUrl(props.getProperty(JDBCURL));
        dataSource2.setUsername(props.getProperty(JDBCUSER));
        dataSource2.setPassword(props.getProperty(JDBCPSSWD));

        return dataSource2;
    }

    /**
     * Default method description of loadProps.
     *
     * @param fileName The name of the file containing the properties.
     * @return  The JDBC properties.
     * @throws IOException  Exception if file not found.
     */
    private Properties loadProps(final String fileName) throws IOException {
        Properties props = new Properties();
        InputStream in = null;

        try {
            in = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            logger.info(
                "File '" +
                    fileName +
                    "' not found on the system, trying classpath...");
        }

        //loaded file sucessfully
        if (in != null) {
            logger.info(
                "File '" + fileName + "' was found on the system, loading...");
            props.load(in);
            return props;
        }
        //try and get it off of the classpath
        in = this.getClass().getClassLoader().getResourceAsStream(fileName);

        if (in != null) {
            logger.info(
                "File '" + fileName + "' found on the classpath, loading...");
            props.load(in);
            return props;
        }

        throw new IOException(
            "Could not load '" + fileName + "' as a file or on the classpath");

    }

    /**
     * @see javax.sql.DataSource#getLoginTimeout()
     */
    public final int getLoginTimeout() throws SQLException {
        return getConfiguredDataSource().getLoginTimeout();
    }

    /**
     * @see javax.sql.DataSource#setLoginTimeout(int)
     */
    public final void setLoginTimeout(final int seconds) throws SQLException {
        getConfiguredDataSource().setLoginTimeout(seconds);
    }

   /**
    * @see javax.sql.DataSource#getLogWriter()
    */
    public final PrintWriter getLogWriter() throws SQLException {
        return getConfiguredDataSource().getLogWriter();
    }

    /**
     * @see javax.sql.DataSource#setLogWriter(java.io.PrintWriter)
     */
    public final void setLogWriter(final PrintWriter out) throws SQLException {
        getConfiguredDataSource().setLogWriter(out);

    }

    /**
     * @see javax.sql.DataSource#getConnection()
     */
    public final Connection getConnection() throws SQLException {
        return getConfiguredDataSource().getConnection();
    }

    /**
     * @see javax.sql.DataSource#getConnection(java.lang.String,
     *  java.lang.String)
     */
    public final Connection getConnection(final String tempUsername,
                                          final String tempPassword)
        throws SQLException {
        return getConfiguredDataSource().getConnection(tempUsername,
                                                       tempPassword);
    }

    /**
     * Gets the driver class name. This is pertinent only for JDBC-based
     * connections.
     *
     * @return The driver class name.
     */
    public final String getDriverClassName() {
        return driverClassName;
    }

    /**
     * Gets the JNDI name defined for the DataSource. This is pertinent only for
     * JNDI-based connections.
     *
     * @return The JNDI name.
     */
    public final String getJndiName() {
        return jndiName;
    }

    /**
     * Gets the password for the DataSource. This is pertinent only for
     * JDBC-based connections.
     *
     * @return The password.
     */
    public final String getPassword() {
        return password;
    }

    /**
     * Gets the url connection string for the DataSource. This is pertinent only
     * for JDBC-based connections.
     *
     * @return The url.
     */
    public final String getUrl() {
        return url;
    }

    /**
     * Gets the username for the DataSource. This is pertinent only for
     * JDBC-based connections.
     *
     * @return The user name.
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Sets the driver class name for the DataSource. This is pertinent only for
     * JDBC-based connections.
     *
     * @param string    The driver class name.
     */
    public final void setDriverClassName(final String string) {
        driverClassName = string;
    }

    /**
     * Sets the JNDI name for the DataSource. This is pertinent only for
     * JNDI-based connections.
     *
     * @param string    The JNDI name.
     */
    public final void setJndiName(final String string) {
        jndiName = string;
    }

    /**
     * Sets the password for the DataSource. This is pertinent only for
     * JDBC-based connections.
     *
     * @param string    The password.
     */
    public final void setPassword(final String string) {
        password = string;
    }

    /**
     * Sets the URL connection string for the DataSource. This is pertinent only
     * for JDBC-based connections.
     *
     * @param string    The url.
     */
    public final void setUrl(final String string) {
        url = string;
    }

    /**
     * Sets the username for the DataSource. This is pertinent only for
     * JDBC-based connections.
     *
     * @param string    The user name.
     */
    public final void setUsername(final String string) {
        username = string;
    }

    /**
     * 
     */
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

    /**
     * 
     */
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}
