<%@ taglib prefix="s" uri="/struts-tags" %>

	
 	<s:if test="hasActionErrors() or hasFieldErrors()">
 	<div class="error">
    		<ul>
    			 
				<s:actionerror />
				<s:fielderror />
				
				<s:iterator value="errors" status="status">
					<li>
						<s:property value="value"/>
					</li>
				</s:iterator>

				<s:iterator value="fieldErrors" status="status">
					<li>
						<s:property value="value"/>
					</li>
				</s:iterator>
				
          </ul>
        </div>
    </s:if>
    <s:if test="hasActionMessages()">
 	<div class="error">
			<ul>
				<s:actionmessage />
	        </ul>
         </div>
     </s:if>


