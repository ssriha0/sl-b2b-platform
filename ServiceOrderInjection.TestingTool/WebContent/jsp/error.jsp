<%@ taglib prefix="s" uri="/struts-tags" %>  

<s:if test="hasActionErrors()">
	<div class="error">
		<s:actionerror />
	</div>
</s:if>