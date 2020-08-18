package extra.crispy;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import static com.newco.marketplace.utils.Test.factorial;


public class Bacon extends HttpServlet 
{

	protected final Logger logger = Logger.getLogger(getClass());
	

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String ns= request.getParameter("n");
		Integer n=10;
		if (ns != null) try {
			n= Integer.parseInt (ns);
		} catch (NumberFormatException nfe) {}
		
		
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML><HEAD><TITLE>A Servlet</TITLE></HEAD><BODY>");
		
		out.println("<pre>MarketCommon::com.newco.marketplace.utils.Test.factorial(" + n + ") = " + factorial(n));

		out.println("<br><br><img src=/BaconWeb/images/bacon800.jpg>");
		out.println("  </BODY></HTML>");
		out.flush();
		out.close();
		
		log.warn("BACON!!!!");		
	}

}
