<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="org.owasp.esapi.ESAPI"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String modelIdVar=request.getParameter("modalId");
String modelIdNew=ESAPI.encoder().canonicalize(modelIdVar);
String modelId=ESAPI.encoder().encodeForHTML(modelIdNew);
String nameVar=request.getParameter("name");
String nameVarNew = ESAPI.encoder().canonicalize(nameVar);
String name = ESAPI.encoder().encodeForHTML(nameVarNew);
String email=request.getParameter("email");
String zip=request.getParameter("zip");
String phone=request.getParameter("phone");
String submitActionVar=request.getParameter("action");
String submitActionVarNew = ESAPI.encoder().canonicalize(submitActionVar);
String submitAction = ESAPI.encoder().encodeForHTML(submitActionVarNew);
%>

<div id="modalPasswordReset<%=modelId%>" class="jqmWindowPasswordReset">
  <div class="jqmWindowPasswordResetTop">
  <div href="#" class="jqmWindowPasswordResetTopLeft">Reset Password</div>
	  <a href="#" class="jqmClose"><img src="${staticContextPath}/images/btn/x.gif" border="0"/></a> </div>

		<div class="jqmWindowPasswordResetContent">
		  <h2>Are you sure you want to reset the password for <%=name%> (key:<%=modelId%>)?</h2>

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


<div id="modalRemoveApp<%=modelId%>" class="jqmWindowPasswordReset">
  <div class="jqmWindowPasswordResetTop">
  <div href="#" class="jqmWindowPasswordResetTopLeft">Remove Application</div>
	  <a href="#" class="jqmClose"><img src="${staticContextPath}/images/btn/x.gif" border="0"/></a> </div>

		<div class="jqmWindowPasswordResetContent">
		  <h2>Are you sure you want to remove application <%=name%> (key:<%=modelId%>)?</h2>

			<div class="jqmWindowPasswordResetBtm">
				<div class="jqmWindowPasswordResetBtmLeft cancel"> <a href="javascript:hideModal('modalRemoveApp<%=modelId%>')">Cancel</a></div>
				<div class="jqmWindowPasswordResetBtmRight">
					    <% if (submitAction != null) { %>
						<img src="${staticContextPath}/images/btn/spacer.gif" alt=""  class="jqmClose btnPSContinue continue"
						onClick="javascript:submitResetPassword('<%=submitAction%>', '<%=modelId%>')"/>
						<% } else { %>
						<img src="${staticContextPath}/images/btn/spacer.gif" alt=""  class="jqmClose btnPSContinue continue"
						onClick="javascript:submitRemoveApplication('<%=modelId%>')"/>
						<% } %>
					</div>
				</div>
			</div>
</div>	  <!-- modalPasswordReset -->

