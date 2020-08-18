package com.newco.marketplace.webservices.filter;
import java.io.ByteArrayInputStream;

import javax.servlet.ServletInputStream;
 
/*
 * Maintenance History
 * $Log: BufferedServletInputStream.java,v $
 * Revision 1.1  2008/02/05 18:17:33  pbhinga
 * Added code to capture/audit SOAP Messages (XML request & response) for the calls made to the Service Live WebServices. This would help debug the WebService code issue. Reviewed by Gordon Jackson.
 *
 */

public class BufferedServletInputStream extends ServletInputStream {
 
    ByteArrayInputStream bais;
 
    /**
     * Constructor
     * @param bais
     */
    public BufferedServletInputStream(ByteArrayInputStream bais) {
        this.bais = bais;
    }
 
    /* (non-Javadoc)
     * @see java.io.InputStream#available()
     */
    @Override
	public int available() {
        return bais.available();
    }
 
    /* (non-Javadoc)
     * @see java.io.InputStream#read()
     */
    @Override
	public int read() {
        return bais.read();
    }
 
    /* (non-Javadoc)
     * @see java.io.InputStream#read(byte[], int, int)
     */
    @Override
	public int read(byte[] buf, int off, int len) {
        return bais.read(buf, off, len);
    }
 
}

