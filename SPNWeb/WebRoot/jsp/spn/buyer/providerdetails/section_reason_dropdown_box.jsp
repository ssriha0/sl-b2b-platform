<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${fn:length(reasonOptions) > 0}">
	<label>Reason<span class="req">*</span>
	</label>
	<select id="reason" name="reason" class="reasonsSelect">
		<option value="-1">
			-- Select Reason --
		</option>
		<c:forEach items="${reasonOptions}" var="option">
			<option value="${option.value}">
				${option.label}
			</option>
		</c:forEach>
	</select>
</c:if>