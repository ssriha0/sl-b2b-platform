
<%@page import="com.newco.marketplace.web.dto.provider.LicensesAndCertDto"%>
<%@page import="java.io.OutputStream"%>

<% 
		try
		{
			OutputStream o = response.getOutputStream();

	   		LicensesAndCertDto licenceDto = (LicensesAndCertDto) session.getAttribute("licensesAndCertDTO");
	   	    String imageName = licenceDto.getCredentialDocumentFileName();

		   byte[] imgData = (byte[]) licenceDto.getCredentialDocumentBytes();   
		   
	       response.setContentType(licenceDto.getCredentialDocumentExtention());
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
			System.out.println(" Inside Document View - Exception ");
		}
   
%>
</html:form>
