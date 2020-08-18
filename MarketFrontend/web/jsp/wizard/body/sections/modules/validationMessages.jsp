<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="%{errors.size > 0}">
<div class="errorBox clearfix" width="100%">
<%--	<label class="error"><strong>ERRORS EXIST</strong></label>--%>
			<label>
						<font size =1.5 color="red"> &nbsp; &nbsp;&nbsp; Mandatory fields required</font>
					
			</label>
			<s:iterator  value="errors" status="error">
			<p class="errorMsg">
				<!-- ${errors[error.index].fieldId} -  --> &nbsp; &nbsp; ${errors[error.index].msg}
			</p>
			
			</s:iterator>

</div>
<br>
</s:if>
<s:if test="%{warnings.size > 0}">
<div class="warningBox clearfix" width="100%" style="height:auto;">
		<label>
					<font size=1.5>	&nbsp; Warnings! Mandatory fields required while posting the order</font>
						
			</label>
		<s:iterator  value="warnings" status="warning"  >
			<p class="warningMsg contentWellPane">
				<span><!-- ${warnings[warning.index].fieldId} - -->  ${warnings[warning.index].msg}</span>
			</p>
			
		</s:iterator>
</div>
<br>
</s:if>