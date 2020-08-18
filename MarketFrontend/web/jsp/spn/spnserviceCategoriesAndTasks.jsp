<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="OVERVIEW_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH%>" />
<c:set var="BUYER_TERM_COND_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_BUYER_TERM_COND_LENGTH%>" />
<c:set var="SPECIAL_INSTRUCTION_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH%>" />
<c:set var="TASK_COMMENTS_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_SCOPE_OF_WORK_TASK_COMMENTS_LENGTH%>" />

<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="SPN Service Categories & Tasks" id=""
	class="">
	<tags:fieldError id="MainServiceCategory" oldClass="paddingBtm">
		<label>
			<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.main.serv.cat"/>
			<font color="red">*</font>
		</label>
		<br>
		<p>
		<s:select name="theCriteria.mainServiceCategoryId" id="mainServiceCategory"
			headerKey="-1" headerValue="-- Select One --" list="skillTreeMainCat"
			listKey="nodeId" listValue="nodeName" cssStyle="width: 190px;"
			size="1" theme="simple" value="theCriteria.mainServiceCategoryId"
					onchange="newco.jsutils.loadSkillsAndCategories2('mainServiceCategory', 'spnBuyerCriteriaBuilderAction');toggleAltTaskDisabled('addAnotherTask','mainServiceCategory');"
			disabled="%{(theCriteria.mainServiceCategoryId != -1? true : false) && (theCriteria.tasks.size  > 0)}" />
	</p>
	</tags:fieldError>
	
	<div class="clear">
	</div>

	<div class="clear">
	</div>
	<!-- NEW NESTED WIDGET-->
	<%-- PREPARE THIS JSP BEGIN	--%>

	<%--<c:set var="mainServiceCat" value="${mainServiceCategories}"/>--%>
	<%-- PREPARE THIS JSP END --%>
	<!-- BEGIN TASK FORM -->
	<c:choose>
		<c:when test="${spnCriteria.theCriteria.containsTasks == false}">
			<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.no.tasks"/>
		</c:when>
		<c:otherwise>

			<s:hidden id="selectedCategoryId" name="theCriteria.selectedCategoryId" value="">
			</s:hidden>

			
			<s:iterator value="theCriteria.tasks" status="iterStat">
				<c:choose>
					<c:when
						test="${theCriteria.tasks[iterStat.index].taskName ==null}">
						<c:set var="taskDojoTitle" value="SPN Criteria ${iterStat.count}" />
					</c:when>
					<c:otherwise>
						<c:set var="taskDojoTitle"
							value="SPN Criteria ${iterStat.count} - ${theCriteria.tasks[iterStat.index].taskName}" />
					</c:otherwise>
				</c:choose>
				<div dojoType="dijit.TitlePane" title="${taskDojoTitle}"
					id="${iterStat.index}" class="dijitTitlePaneSubTitle"
					open="${iterStat.last ? true : false}">

					<h5><s:a href="javascript:newco.jsutils.confirmDeleteTask('spnBuyerCriteriaBuilderAction','%{#iterStat.index}')">Delete Criteria</s:a></h5><br>
					<table class="categoryTable" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<p>
									<label>
										<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.category"/>
									</label>
								</p>
								<s:select id="categorySelection_%{#iterStat.index}"
									name="theCriteria.tasks[%{#iterStat.index}].categoryId"
									onchange="updateElementValue('category_%{#iterStat.index}', 'categorySelection_%{#iterStat.index}', 'subCategory_%{#iterStat.index}');
												  newco.jsutils.loadSubcategories2('categorySelection_%{#iterStat.index}', 'selectedCategoryId','spnBuyerCriteriaBuilderAction', %{#iterStat.index})"
									key="%{theCriteria.tasks[#iterStat.index].categoryId}" headerKey="-1"
									headerValue="-- Select One --" list="categorySelection"
									listKey="nodeId" listValue="nodeName" cssStyle="width: 190px;"
									size="3" theme="simple"
									disabled="%{theCriteria.mainServiceCategoryId == -1 || theCriteria.tasks[#iterStat.index].isSaved ? true : false}"
									value="%{theCriteria.tasks[#iterStat.index].categoryId}" cssClass="spnCriteriaCat"/>

								<s:hidden id="category_%{#iterStat.index}"
									name="theCriteria.tasks[%{#iterStat.index}].category"
									value="%{theCriteria.tasks[#iterStat.index].category == null || 
										  				theCriteria.tasks[#iterStat.index].category == ''? '' : theCriteria.tasks[#iterStat.index].category}">
								</s:hidden>
							</td>
							<td>
								<p>
									<label>
										<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.subcategory"/>
									</label>
								</p>
								<s:select id="subCategorySelection_%{#iterStat.index}"
									name="theCriteria.tasks[%{#iterStat.index}].subCategoryId"
									key="%{theCriteria.tasks[#iterStat.index].subCategoryId}" headerKey="-1"
									headerValue="-- Select One --"
									list="%{theCriteria.tasks[#iterStat.index].subCategoryList}"
									listKey="nodeId" listValue="nodeName" cssStyle="width: 190px;"
									size="3" theme="simple"
									disabled="%{theCriteria.tasks[#iterStat.index].categoryId == -1 || tasks[#iterStat.index].isSaved ? true : false}"
									value="%{theCriteria.tasks[#iterStat.index].subCategoryId}"
									onchange="updateElementValue('subCategory_%{#iterStat.index}', 'subCategorySelection_%{#iterStat.index}', null)" />

								<s:hidden id="subCategory_%{#iterStat.index}"
									name="theCriteria.tasks[%{#iterStat.index}].subCategory"
									value="%{theCriteria.tasks[#iterStat.index].subCategory == null || 
										  				theCriteria.tasks[#iterStat.index].subCategory == ''? '' : theCriteria.tasks[#iterStat.index].subCategory}">

								</s:hidden>
							</td>
							<td class="column3">
								<p>
									<label>
										<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.skill"/>
									</label>
								</p>
								<s:select id="skillSelection_%{#iterStat.index}"
									name="theCriteria.tasks[%{#iterStat.index}].skillId"
									key="%{theCriteria.tasks[#iterStat.index].skillId}" headerKey="-1"
									headerValue="-- Select One --" list="skillSelection"
									listKey="serviceTypeId" listValue="description"
									cssStyle="width: 190px;" size="3" theme="simple"
									disabled="%{theCriteria.mainServiceCategoryId == -1 || tasks[#iterStat.index].isSaved ? true : false}"
									value="%{theCriteria.tasks[#iterStat.index].skillId}"
									onchange="updateElementValue('skill_%{#iterStat.index}', 'skillSelection_%{#iterStat.index}', null)"
									cssClass="spnCriteriaSkill" />
								<s:hidden id="skill_%{#iterStat.index}"
									name="theCriteria.tasks[%{#iterStat.index}].skill"
									value="%{theCriteria.tasks[#iterStat.index].skill == null || 
										  				theCriteria.tasks[#iterStat.index].skill == ''? '' : theCriteria.tasks[#iterStat.index].skill}">
								</s:hidden>
							</td>
						</tr>
					</table>
				</div>
				<div class="clear"></div>
			</s:iterator>
		</c:otherwise>
	</c:choose>
	

    <s:div id="addAnotherTask"  
    	cssClass="clearfix"
    	cssStyle="display:%{theCriteria.mainServiceCategoryId != -1 ? 'block' : 'none'}"
    	theme="simple">
		<p>
			<input type='button' value=""  class="btn20Bevel"
				style="background-image: url(${staticContextPath}/images/add_criteria.gif);
				            width:108px; height:20px;"
				src="${staticContextPath}/images/common/spacer.gif"
				onclick="newco.jsutils.doSPNCriteriaValidation('add')"/>

		</p>
	</s:div>
	<!-- END TASK FORM -->
</div>
<div class="clearfix">
	<div class="formNavButtons">
<input type='button' value=""  class="btn20Bevel"
				style="background-image: url(${staticContextPath}/images/btn/save.gif);
				            width:50px; height:20px;"
				src="${staticContextPath}/images/common/spacer.gif"
				onclick="newco.jsutils.doSPNCriteriaValidation('softPersistCriteria')"/>
				
<input type='button' value=""  class="btn20Bevel"
				style="background-image: url(${staticContextPath}/images/btn/cancel.gif);
				            width:58px; height:20px;"
				src="${staticContextPath}/images/common/spacer.gif"
				onclick="newco.jsutils.goToLocationFA('spnBuyerCriteriaBuilderAction','displayPage','spnBuyerBuilderAction')"/>

</div></div>
