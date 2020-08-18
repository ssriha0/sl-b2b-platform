
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>


<%@ attribute name="id" required="true"%>
<%@ attribute name="oldClass" required="false"%>

<%@ tag body-content="scriptless" %>

<c:set var="foundError" value="false"/>
<c:forEach items="${errors}" var="error">
	<%-- '${error.fieldId}' - '${id}'<br> --%>
	<c:if test="${error.fieldId == id}">
		<c:set var="foundError" value="true"/>
	</c:if>
</c:forEach>

<c:choose>
	<c:when test="${foundError}">
		<p class="${oldClass} errorBox">
			<jsp:doBody/>
		</p>
	</c:when>	
	<c:otherwise>
		<c:choose>
			<c:when test="${oldClass} != null">
				<p class="${oldClass}">		
					<jsp:doBody/>
				</p>
			</c:when>
			<c:otherwise>
				<jsp:doBody/>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>


