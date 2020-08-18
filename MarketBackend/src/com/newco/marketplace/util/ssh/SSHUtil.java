package com.newco.marketplace.util.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHUtil {

	private static final Logger logger = Logger.getLogger(SSHUtil.class);

	/**
	 * @param outBytes
	 * @param scpCommand
	 * @throws JSchException
	 * @throws IOException
	 */
	public static void scpTo(byte[] outBytes, String scpCommand) throws JSchException, IOException {
		Channel channel = null;
		OutputStream out = null;
		String rfile = null;
		boolean success = false;
		try {
			// user:password@host:/dir/filename
			String creds = scpCommand.substring(0, scpCommand.indexOf('@'));
			String[] acreds = creds.split(":");
			String userName = acreds[0];
			String password = acreds[1];
			scpCommand = scpCommand.substring(scpCommand.indexOf('@') + 1);
			String host = scpCommand.substring(0, scpCommand.indexOf(':'));
			rfile = scpCommand.substring(scpCommand.indexOf(':') + 1);
			String command = "scp -p -t " + rfile;

			channel = openSecureChannel(userName, host, password);
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect();
			if (checkAck(in) != 0) {
				throw new Exception();
			}

			// send "C0644 filesize filename", where filename should not
			// include '/'
			long filesize = outBytes.length;
			command = "C0644 " + filesize + " file \n";
			out.write(command.getBytes());
			out.flush();
			if (checkAck(in) != 0) {
				throw new Exception();
			}
			out.write(outBytes);
			out.flush();
			byte[] buf=new byte[1];
			buf[0]=0;
			out.write(buf, 0, 1); 
			out.flush();
			if (checkAck(in) != 0) {
				throw new Exception();
			}
			logger.info("File has been ftp'd successfully to host");
			success = true;
		} catch (Exception e) {
			logger.error("Error ftp'ing file.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ioEx) {
					logger.fatal("Could not close output stream in SSHUtil.scpTo()", ioEx);
					success = false;
				}
			}
			closeChannel(channel);
		}
		if (!success) {
			throw new IOException("Error FTPing file ");
		}
	}
	/**
	 * This method is used to rename a file in a remote directory.
	 * @param sourceFileName
	 * @param destFileName
	 * @param mvCommand
	 * @throws JSchException 
	 * @throws IOException 
	 */
	public static void mvTo(String sourceFileName, String destFileName, String mvCommand) throws JSchException, IOException {
		Channel channel = null;
		OutputStream out = null;
		boolean success = false;
		String directory = null;
		try {
			// user:password@host:/dir
			String creds = mvCommand.substring(0, mvCommand.indexOf('@'));
			String[] acreds = creds.split(":");
			String userName = acreds[0];
			String password = acreds[1];
			mvCommand = mvCommand.substring(mvCommand.indexOf('@') + 1);
			String host = mvCommand.substring(0, mvCommand.indexOf(':'));
			directory = mvCommand.substring(mvCommand.indexOf(':') + 1);
			String command = new StringBuilder("mv ").append(directory).append("/").append(sourceFileName).append(" ").append(directory).append("/").append(destFileName).toString();

			channel = openSecureChannel(userName, host, password);
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote mv
			out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect();
			if (checkAck(in) > 0) {
				throw new Exception();
			}
			out.write(command.getBytes());
			out.flush();
			if (checkAck(in) > 0) {
				throw new Exception();
			}
			logger.info(sourceFileName + " has been renamed to "+destFileName+ " successfully ");
			success = true;
		} catch (Exception e) {
			logger.error("Error ftp'ing file.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ioEx) {
					logger.fatal("Could not close output stream in SSHUtil.mvTo()", ioEx);
					success = false;
				}
			}
			closeChannel(channel);
		}
		if (!success) {
			throw new IOException("Error renaming file ");
		}
	}

	static Channel openSecureChannel(String userName, String host, String password) throws JSchException {
		
		JSch jsch = new JSch();
		Session session = jsch.getSession(userName, host, 22);
		session.setPassword(password.getBytes());

		Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);

		session.connect();
		Channel channel = session.openChannel("exec");
		
		return channel;
		
	}
	
	static int checkAck(InputStream in) {
		int b = 0;
		try {
			b = in.read();
			// b may be 0 for success,
			// 1 for error,
			// 2 for fatal error,
			// -1
			if (b == 0)
				return b;
			if (b == -1)
				return b;

			if (b == 1 || b == 2) {
				StringBuffer sb = new StringBuffer();
				int c;
				do {
					c = in.read();
					sb.append((char) c);
				} while (c != '\n');
				if (b == 1) { // error
					logger.error(sb.toString());
				}
				if (b == 2) { // fatal error
					logger.error(sb.toString());
				}
			}
			return b;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;

	}
	
	static void closeChannel(Channel channel) {
		Session session = null;
		try {
			session = channel.getSession();
		} catch (JSchException e) {
			logger.error(e.getMessage(), e);
		}
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}
}
