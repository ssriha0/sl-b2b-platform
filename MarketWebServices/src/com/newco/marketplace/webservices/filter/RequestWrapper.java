package com.newco.marketplace.webservices.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
 
/*
 * Maintenance History
 * $Log: RequestWrapper.java,v $
 * Revision 1.1  2008/02/05 18:17:33  pbhinga
 * Added code to capture/audit SOAP Messages (XML request & response) for the calls made to the Service Live WebServices. This would help debug the WebService code issue. Reviewed by Gordon Jackson.
 *
 */

public class RequestWrapper extends HttpServletRequestWrapper {
 
    private ByteArrayInputStream bais; 
    private ByteArrayOutputStream baos;
    private BufferedServletInputStream bsis;
    private byte[] buffer;
    private Date initDate = new Date();
 
    /**
     * Constructor
     * @param req HttpServletRequest
     * @throws IOException
     */
    public RequestWrapper(HttpServletRequest req) throws IOException {
        super(req);
 
        // Read InputStream and store its content in a buffer.
        InputStream is = req.getInputStream();
        baos = new ByteArrayOutputStream();
 
        byte[] buf = new byte[1024];
        int letti;
 
        while ((letti = is.read(buf)) > 0)
            baos.write(buf, 0, letti);
 
        buffer = baos.toByteArray();
    }
 
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getInputStream()
     */
    @Override
	public ServletInputStream getInputStream() {
 
        try {
 
            // Generate a new InputStream by stored buffer
            bais = new ByteArrayInputStream(buffer);
 
            // Instantiate a subclass of ServletInputStream
            // (Only ServletInputStream or subclasses of it are accepted by the servlet engine!)
            bsis = new BufferedServletInputStream(bais);
        } catch (Exception ex) {
            ex.printStackTrace();
        }  
            
        return bsis;
         
    }
 
    /**
     * @return initDate value
     */
    public Date getInitDate() {
        return initDate;
    }
 
    /**
     * @param initDate Sets the date value
     */
    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }
}
