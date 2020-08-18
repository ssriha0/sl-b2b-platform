<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<c:if test="${errors[0] != null}" >
<div class="errorBox clearfix" width="100%">
	<c:forEach items="${errors}" var="error">
		<p class="errorMsg">
			${error.fieldId} -  ${error.msg}
		</p>
	</c:forEach>
</div>

<br>
</c:if>
<s:if test="%{warnings.size > 0}">
<div class="warningBox clearfix" width="100%">

		<s:iterator  value="warnings" status="warning"  >
			<p class="warningMsg contentWellPane">
				<span>${warnings[warning.index].fieldId} -  ${warnings[warning.index].msg}</span>
			</p>
		</s:iterator>
</div>
<br>
</s:if>