<%@ taglib prefix="s" uri="/struts-tags" %>

 	<s:if test="hasActionErrors() or hasFieldErrors()">
 	<div style="margin: 10px 0pt; padding-left: 30px;" class="errorBox error clearfix" id="errorMessages" >
          <ul>
			<ul>
				<s:actionerror />
				<s:fielderror />
          </ul>
          </ul>
        </div>

    </s:if>
    <s:if test="hasActionMessages()">
 	<div style="margin: 10px 0pt;background: #E6EFC2; color: #264409; border:2px solid #C6D880;" class="actionMessageBox clearfix">
          <ul>
			<ul>
				<b><s:actionmessage /></b>
	        </ul>
          </ul>
        </div>
     </s:if>


