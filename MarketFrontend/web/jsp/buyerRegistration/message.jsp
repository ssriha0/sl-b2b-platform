<%@ taglib prefix="s" uri="/struts-tags" %>
 	<s:if test="hasActionErrors() or hasFieldErrors()"> 
 	<div style="margin: 10px 0pt; width: 680px" class="errorBox clearfix" align="left" >
 	 	<p class="errorMsg"><strong>Error:  Please check the following and try again.</strong> </p>
	
				<s:actionerror />
				<s:fielderror />
    
        </div>
    	
    </s:if>
    <s:if test="hasActionMessages()"> 
 	<div style="margin: 10px 10pt; width: 650px" class="actionMessageBox clearfix" align="left">
				<s:actionmessage />
     </div>
     </s:if>


