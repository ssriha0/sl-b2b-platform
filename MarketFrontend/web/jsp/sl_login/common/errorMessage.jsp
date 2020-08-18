<%@ taglib prefix="s" uri="/struts-tags" %>

<!-- 
  Based on new css.
 -->

 
 	<s:if test="hasActionErrors() or hasFieldErrors()">
 	<div class="error" id="actionMsg" >
          <ul>
			<ul>
				<s:actionerror />
				<s:fielderror />
          </ul>
          </ul>
        </div>

    </s:if>
    <s:if test="hasActionMessages()">
 	<div class="success" id="actionMsg">
          <ul>
			<ul>
				<s:actionmessage />
	        </ul>
          </ul>
        </div>
     </s:if>

