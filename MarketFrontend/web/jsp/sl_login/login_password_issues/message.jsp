<%@ taglib prefix="s" uri="/struts-tags" %>

 	<s:if test="hasActionErrors() or hasFieldErrors()">
 	<div style="margin: 10px 0pt;" class="errorBox clearfix" id="errorMessages" >
          <ul>
			<ul>
				<s:actionerror />
				<s:fielderror />
          </ul>
          </ul>
        </div>

    </s:if>
    <s:if test="hasActionMessages()">
 	<div style="margin: 10px 10pt;" class="actionMessageBox clearfix">
          <ul>
			<ul>
				<s:actionmessage />
	        </ul>
          </ul>
        </div>
     </s:if>


