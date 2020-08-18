
<%@page import="com.newco.marketplace.web.dto.provider.TeamCredentialsDto"%>
<%@page import="java.io.OutputStream"%>

<% 
// This is a kludgy way to display this document	
	try
	{	
		OutputStream o = response.getOutputStream();

	   TeamCredentialsDto teanDTO = (TeamCredentialsDto) session.getAttribute("teamCredentialsDto");
	   String imageName = teanDTO.getCredentialDocumentFileName();

	   byte[] imgData = (byte[]) teanDTO.getCredentialDocumentBytes();   
       response.setContentType(teanDTO.getCredentialDocumentExtention());
       
       response.setContentLength(imgData.length);
	   response.setHeader("Content-Disposition", "attachment; filename=\""+imageName+"\"");
	   response.setHeader("Expires", "0");
	   response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");	
	   response.setHeader("Pragma", "public");

       o.write(imgData);
       o.flush(); 
       o.close();
    }catch(Exception a_Ex)
    {
    	System.out.println(" Inside Document View JSP - Exception ");
    }
   
%>
</html:form>
