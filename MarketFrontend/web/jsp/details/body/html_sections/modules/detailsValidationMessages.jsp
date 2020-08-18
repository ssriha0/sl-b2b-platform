<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

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


