package com.newco.marketplace.webservices.filter;

 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/*
 * Maintenance History
 * $Log: ResponseStream.java,v $
 * Revision 1.1  2008/02/05 18:17:33  pbhinga
 * Added code to capture/audit SOAP Messages (XML request & response) for the calls made to the Service Live WebServices. This would help debug the WebService code issue. Reviewed by Gordon Jackson.
 *
 */

public class ResponseStream extends ServletOutputStream {
 
	private static final Logger logger = Logger.getLogger(SLSOAPMessageAuditorFilter.class);
	
    // abstraction of the output stream used for compression
    protected OutputStream bufferedOutput = null;
 
    // state keeping variable for if close() has been called
    protected boolean closed = false;
 
    // reference to original response
    protected HttpServletResponse response = null;
 
    // reference to the output stream to the client's browser
    protected ServletOutputStream output = null;
 
    // default size of the in-memory buffer
    private int bufferSize = 128000;
 
    public String clonedResponseString;
 

    /**
     * Constructor
     * @param response HttpServletResponse
     * @throws IOException
     */
    public ResponseStream(HttpServletResponse response) throws IOException {
        super();
        closed = false;
        this.response = response;
        this.output = response.getOutputStream();
        bufferedOutput = new ByteArrayOutputStream(bufferSize);
    }
 
    /**
     * @return The Response String
     */
    public String getData() {
        return clonedResponseString;
    }
 
    /* (non-Javadoc)
     * @see java.io.OutputStream#close()
     */
    @Override
	public void close() throws IOException {
        String methodName = "close()";
        logger.debug(methodName + ": Entering");
 
        // verify the stream is yet to be closed
        if (closed) {
            return;
        }
 
        // if we buffered everything in memory, gzip it
        if (bufferedOutput instanceof ByteArrayOutputStream) {
 
            // get the content
            ByteArrayOutputStream baos = (ByteArrayOutputStream) bufferedOutput;
 
            byte[] clonedBytes = baos.toByteArray();
 
            clonedResponseString = new String(clonedBytes);
 
            // set appropriate HTTP headers
            response.setContentLength(clonedBytes.length);
            output.write(clonedBytes);
            output.flush();
            output.close();
            closed = true;
        }
 
        logger.debug(methodName + ": Exiting");
    }
 
    /* (non-Javadoc)
     * @see java.io.OutputStream#flush()
     */
    @Override
	public void flush() throws IOException {
 
        if (!closed)
            bufferedOutput.flush();
    }
 
    /* (non-Javadoc)
     * @see java.io.OutputStream#write(int)
     */
    @Override
	public void write(int b) throws IOException {
 
        if (closed) {
            throw new IOException("Cannot write to a closed output stream");
        }
 
        // write the byte to the temporary output
        bufferedOutput.write((byte) b);
    }
 
    /* (non-Javadoc)
     * @see java.io.OutputStream#write(byte[])
     */
    @Override
	public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }
 
    /* (non-Javadoc)
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    @Override
	public void write(byte[] b, int off, int len) throws IOException {
 
        if (closed) {
            throw new IOException("Cannot write to a closed output stream");
        }
 
        // write the content to the buffer
        bufferedOutput.write(b, off, len);
    }
 
    /**
     * @return True if ResponseStream is closed
     */
    public boolean closed() {
        return (this.closed);
    }
 
    public void reset() {
        //noop
    }
 
}
 