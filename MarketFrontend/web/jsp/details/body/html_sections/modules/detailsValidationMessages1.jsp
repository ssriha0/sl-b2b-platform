<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!-- Display Client Side Validation Messages if any -->
<div class="errorBox clearfix" id="validationResponseMessage" style="width: 675px; overflow-y:hidden; visibility:hidden;">
</div>
<!-- Display Server Side Validation Messages -->
<s:if test="%{errors.size > 0}">
<div class="errorBox clearfix" style="width: 675px; overflow-y:hidden;visibility:visible">
			<s:iterator  value="errors" status="error">
			<p class="errorMsg">
				&nbsp;&nbsp;&nbsp;&nbsp;${errors[error.index].fieldId} -  ${errors[error.index].msg}
			</p>
			</s:iterator>
</div>
<br>
</s:if>
<s:if test="%{warnings.size > 0}">
<div class="warningBox clearfix" style="width: 675px; overflow-y:hidden;visibility:visible">

		<s:iterator  value="warnings" status="warning"  >
			<p class="warningMsg contentWellPane">
				&nbsp;&nbsp;&nbsp;&nbsp;<span>${warnings[warning.index].fieldId} -  ${warnings[warning.index].msg}</span>
			</p>
		</s:iterator>
</div>
<br>
</s:if>