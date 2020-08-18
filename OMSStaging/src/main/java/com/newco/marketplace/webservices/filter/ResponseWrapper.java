package com.newco.marketplace.webservices.filter;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
 
/*
 * Maintenance History
 * $Log: ResponseWrapper.java,v $
 * Revision 1.1  2008/02/05 18:17:33  pbhinga
 * Added code to capture/audit SOAP Messages (XML request & response) for the calls made to the Service Live WebServices. This would help debug the WebService code issue. Reviewed by Gordon Jackson.
 *
 */

public class ResponseWrapper extends HttpServletResponseWrapper {
 
    protected HttpServletResponse origResponse = null;
    protected ServletOutputStream stream = null;
    protected PrintWriter writer = null;
    private Date initDate = new Date();
 
    /**
     * Constructor
     * @param response
     */
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        origResponse = response;
    }
 
    /**
     * @return Create a new output stream
     * @throws IOException
     */
    public ServletOutputStream createOutputStream() throws IOException {
        return (new ResponseStream(origResponse));
    }
 
    /**
     * @return The data from the stream
     */
    public String getData() {
 
        if (stream != null) {
            return ((ResponseStream) stream).getData();
        }
 
        return "";
    }
 
    /**
     * Close the created writer and/or stream objects if not already closed.
     */
    public void finishResponse() {
 
        try {
 
            if (writer != null) {
                writer.close();
            } else {
 
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (IOException e) {
        	//FIXME Shouldn't we do something with this error?
        }
    }
 
    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#flushBuffer()
     */
    @Override
	public void flushBuffer() throws IOException {
        stream.flush();
    }
 
    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#getOutputStream()
     */
    @Override
	public ServletOutputStream getOutputStream() throws IOException {
 
        if (writer != null) {
            throw new IllegalStateException(
                "getWriter() has already been called!");
        }
 
        if (stream == null) {
            stream = createOutputStream();
        }
 
        return (stream);
    }
 
    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#getWriter()
     */
    @Override
	public PrintWriter getWriter() throws IOException {
 
        if (writer != null) {
            return (writer);
        }
 
        if (stream != null) {
            throw new IllegalStateException(
                "getOutputStream() has already been called!");
        }
 
        stream = createOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(stream,
                    origResponse.getCharacterEncoding()));
 
        return (writer);
    }
 
    @Override
	public void setContentLength(int length) {
    	// intentionally blank
    }
 
    /**
     * @return Value of initDate
     */
    public Date getInitDate() {
        return initDate;
    }
 
    /**
     * @param initDate Set the date
     */
    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }
}

