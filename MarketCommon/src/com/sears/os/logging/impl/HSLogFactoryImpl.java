
package com.sears.os.logging.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

/**
 * <p>Concrete subclass of {@link LogFactory} specific to log4j.
 *
 * @deprecated Per discussion on COMMONS-DEV, the behind-the-scenes use
 *  of this class as a proxy factory has been removed.  For 1.0, you
 *  can still request it directly if you wish, but it doesn't really
 *  do anything useful, and will be removed in 1.1.
 *
 * @author Costin Manolache
 */
public final class HSLogFactoryImpl extends LogFactory {

    public HSLogFactoryImpl() {
        super();

    }

    /**
     * The configuration attributes for this {@link LogFactory}.
     */
    private Hashtable attributes = new Hashtable();

    // previously returned instances, to avoid creation of proxies
    private Hashtable instances = new Hashtable();

    // --------------------------------------------------------- Public Methods

    /**
     * Return the configuration attribute with the specified name (if any),
     * or <code>null</code> if there is no such attribute.
     *
     * @param name Name of the attribute to return
     */
    public Object getAttribute(String name) {
        return (attributes.get(name));
    }


    /**
     * Return an array containing the names of all currently defined
     * configuration attributes.  If there are no such attributes, a zero
     * length array is returned.
     */
    public String[] getAttributeNames() {
        Vector names = new Vector();
        Enumeration keys = attributes.keys();
        while (keys.hasMoreElements()) {
            names.addElement((String) keys.nextElement());
        }
        String results[] = new String[names.size()];
        for (int i = 0; i < results.length; i++) {
            results[i] = (String) names.elementAt(i);
        }
        return (results);
    }


    /**
     * Convenience method to derive a name from the specified class and
     * call <code>getInstance(String)</code> with it.
     *
     * @param clazz Class for which a suitable Log name will be derived
     *
     * @exception LogConfigurationException if a suitable <code>Log</code>
     *  instance cannot be returned
     */

    public Log getInstance(Class clazz)
        throws LogConfigurationException
    {
        Log instance = (Log) instances.get(clazz);

        if( instance != null )
            return instance;

        instance=new HSLogger( Logger.getLogger( clazz ));
        instances.put( clazz, instance );

        return instance;
    }


    public Log getInstance(String name)
        throws LogConfigurationException
    {
        Log instance = (Log) instances.get(name);
        if( instance != null )
            return instance;
        instance=new HSLogger( Logger.getLogger( name ));
        instances.put( name, instance );
        return instance;
    }


    /**
     * Release any internal references to previously created {@link Log}
     * instances returned by this factory.  This is useful environments
     * like servlet containers, which implement application reloading by
     * throwing away a ClassLoader.  Dangling references to objects in that
     * class loader would prevent garbage collection.
     */
    public void release() {

        instances.clear();

        // what's the log4j mechanism to cleanup ???
    }


    /**
     * Remove any configuration attribute associated with the specified name.
     * If there is no such attribute, no action is taken.
     *
     * @param name Name of the attribute to remove
     */
    public void removeAttribute(String name) {
        attributes.remove(name);
    }


    /**
     * Set the configuration attribute with the specified name.  Calling
     * this with a <code>null</code> value is equivalent to calling
     * <code>removeAttribute(name)</code>.
     *
     * @param name Name of the attribute to set
     * @param value Value of the attribute to set, or <code>null</code>
     *  to remove any setting for this attribute
     */
    public void setAttribute(String name, Object value) {
        if (value == null) {
            attributes.remove(name);
        } else {
            attributes.put(name, value);
        }
    }

}
