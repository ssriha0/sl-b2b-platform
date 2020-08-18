<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="offerings" border="0" cellspacing="0">

	<c:choose>
		<c:when test="${not empty SkillAssignInfo.serviceTypes }">
			<tr>
				<th>
					&nbsp;
				</th>
				<c:forEach items="${SkillAssignInfo.serviceTypes}" var="stype">
					<th>
						${stype.description}
					</th>
				</c:forEach>
			</tr>
		</c:when>
		<c:otherwise>
			<p>
				Currently, there are no skills categories are available for this
				service provider.
			</p>
		</c:otherwise>
	</c:choose>



	<c:choose>
		<c:when test="${not empty SkillAssignInfo.skillTreeList }">

			<c:forEach items="${SkillAssignInfo.skillTreeList}" var="snode">
				<c:if test="${snode.level > 1}">
					<tr>
						<c:if test="${snode.level == 2 }">
							<td class="title">
						</c:if>
						<c:if test="${snode.level  > 2 }">
							<td >
						</c:if>
								${snode.nodeName}
							</td>
						<c:forEach items="${snode.serviceTypes}" var="snodetype">
							<c:if test="${snodetype.active == true}">
								<td>
									<img src="${staticContextPath}/images/icons/02.png" alt="" />
								</td>
							</c:if>
							<c:if test="${snodetype.active == false}">
								<td>
									&nbsp;
								</td>
							</c:if>
						</c:forEach>
					</tr>
				</c:if>
			</c:forEach>

		</c:when>
		<c:otherwise>
			<p>
				Currently, there are no skills categories are available for this
				service provider.
			</p>
		</c:otherwise>
	</c:choose>



</table>



