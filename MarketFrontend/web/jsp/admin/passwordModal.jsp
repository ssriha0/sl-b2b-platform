<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="org.owasp.esapi.ESAPI"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String modelIdVar=request.getParameter("modalId");
String modelIdNew=ESAPI.encoder().canonicalize(modelIdVar);
String modelId=ESAPI.encoder().encodeForHTML(modelIdNew);
String name=request.getParameter("name");
String emailVar=request.getParameter("email");
String emailNew=ESAPI.encoder().canonicalize(emailVar);
String email=ESAPI.encoder().encodeForHTML(emailNew);
String zipVar=request.getParameter("zip");
String zipNew=ESAPI.encoder().canonicalize(zipVar);
String zip=ESAPI.encoder().encodeForHTML(zipNew);
String phoneVar=request.getParameter("phone");
String phoneNew=ESAPI.encoder().canonicalize(phoneVar);
String phone=ESAPI.encoder().encodeForHTML(phoneNew);
String submitAction=request.getParameter("action");
%>

<div id="modalPasswordReset<%=modelId%>" class="jqmWindowPasswordReset">                	                                                                                
  <div class="jqmWindowPasswordResetTop">
  <div href="#" class="jqmWindowPasswordResetTopLeft">Reset Password</div>
	  <a href="#" class="jqmClose"><img src="${staticContextPath}/images/btn/x.gif" border="0"/></a> </div>
										   		
		<div class="jqmWindowPasswordResetContent">					                     
		  <h2>Are you sure you want to reset the password for <%=name%> (#<%=modelId%>)?</h2>	                      
		  <p>An email with a temporary password will be sent to the email address on this account.</p>
		  
		  <% if (email != null) { %>
		  <p>
		  	<b>Email :</b> <%=email %> <br>
		     <b>Zip :</b> <%=zip %> <br>
		  	<b>Phone Number: </b><%=phone %>
		  </p>
		  <% } %>                
						                    		
			<div class="jqmWindowPasswordResetBtm">
				<div class="jqmWindowPasswordResetBtmLeft cancel"> <a href="javascript:hideResetModal()">Cancel</a></div>
				<div class="jqmWindowPasswordResetBtmRight"> 
					    <% if (submitAction != null) { %>
						<img src="${staticContextPath}/images/btn/spacer.gif" alt=""  class="jqmClose btnPSContinue continue" 
						onClick="javascript:submitResetPassword('<%=submitAction%>', '<%=modelId%>')"/>
						<% } else { %>
						<img src="${staticContextPath}/images/btn/spacer.gif" alt=""  class="jqmClose btnPSContinue continue" 
						onClick="javascript:submitResetPassword('<%=modelId%>')"/>
						<% } %>	                            			
					</div>															
				</div>	
			</div>	
</div>	  <!-- modalPasswordReset -->