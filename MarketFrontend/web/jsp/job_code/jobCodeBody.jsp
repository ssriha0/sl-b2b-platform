<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="JobCodeBody"/>
	</jsp:include>	

<c:choose>
<c:when test="${isJobCodeError == 'true'}">
	<div style="color:red" align="center">${jobCodeMsg}</div>	
</c:when>
<c:otherwise>
	<div style="color:blue" align="center">${jobCodeMsg}</div>	
</c:otherwise>
</c:choose>
<div id="mapJob" class="clearfix" style="margin: 20px;">
	<s:form name="jobCodeForm" action="jobCode_" id="jobCodeForm" theme="simple">
		<fieldset style="border: 1px solid #CCC;">
			<legend
				style="margin: 4px; padding: 0 10px; color: #00A0D2; font-size: 12px;">
				Map Job Code
			</legend>
			<table border="0" cellspacing="4" cellpadding="2"
				style="margin: 10px">
				<tr>
					<td>
						<label for="jobCode">
							Job Code
						</label>
						<br />
						<s:textfield theme="simple" id="jobCode" name="jobCode" cssClass="shadowBox"
							cssStyle="width: 200px;" value="%{jobCode}" />
					</td>
					<td>
						<label for="specCode">
							Speciality Code
						</label>
						<br />
						<s:textfield theme="simple" id="specCode" name="specCode" cssClass="shadowBox"
							cssStyle="width: 200px;" value="%{specCode}" 
							onblur="javascript:fnGetJobCodeDetails(this)"/>
					</td>					
				</tr>
				<tr>
					<td>
						<label for="majorHeading">
							Major Heading Description
						</label>
						<br />
						<s:textfield theme="simple" id="majorHeadingDescr" name="majorHeadingDescr" cssClass="shadowBox"
							cssStyle="width: 200px;" value="%{majorHeadingDescr}" />						
					</td>
					<td>
						<label for="subHeading">
							Sub Heading Description
						</label>
						<br />
						<s:textfield theme="simple" id="subHeadingDescr" name="subHeadingDescr" cssClass="shadowBox"
							cssStyle="width: 200px;" value="%{subHeadingDescr}" />						
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<label for="incDescription">
							Inclusion Description
						</label>
						<br />
						<s:textarea cssClass="inputBox" name="inclusionDescr" id="inclusionDescr"
						cssStyle="width: 408px;" value="%{inclusionDescr}">
						</s:textarea>	
						<s:hidden id="taskName" name="taskName" cssClass="shadowBox"
							cssStyle="width: 155px;" value="%{taskName}" />
						<s:hidden id="taskComment" name="taskComment" cssClass="shadowBox" 
							cssStyle="width: 155px;"	value="%{taskComment}" />												
					</td>
				</tr>
				<tr>
					<td>
						<label for="mainCategory">
							Main Category
						</label>
						<br />
						<s:select name="mainCategoryId" id="mainServiceCategory"
							headerKey="-1" headerValue="-- Select One --" list="skillTreeMainCat"
							listKey="nodeId" listValue="nodeName" cssStyle="width: 190px;"
							size="1" theme="simple" value="mainCategoryId"
							onchange="newco.jsutils.loadSkillsAndCategories2('mainServiceCategory', 'jobCode',true)" 
							disabled="skuTaskList.size > 0 ? true: false"/>					
					</td>
					<td>
						<label for="templateName">
							Template Name
						</label>
						<br />
						<s:select name="templateId" id="templateList"
							headerKey="-1" headerValue="-- Select One --" list="templateList"
							cssStyle="width: 190px;"
							size="1" theme="simple" value="templateId"
							/>
					</td>
				</tr>
			</table>
		</fieldset>

		<p></p>

		<fieldset style="border: 1px solid #CCC;">
			<legend
				style="margin: 4px; padding: 0 10px; color: #00A0D2; font-size: 12px;">
				Existing Tasks
			</legend>
			
			<s:iterator value="skuTaskList" status="iterStat">			
				<table border="0" cellspacing="4" cellpadding="2"
					style="margin: 10px">
	
					<!-- begin row loop -->
					<tr>						
						<td>
							<label for="category">
								Category
							</label>
							<br />
							<s:textfield
								name="skuTaskList[%{#iterStat.index}].categoryName"
								id="categoryName_%{#iterStat.index}"
								value="%{skuTaskList[#iterStat.index].categoryName}"
								cssStyle="width: 155px;" cssClass="shadowBox" theme="simple"
								disabled="true">
							</s:textfield>
							<s:hidden 
								name="skuTaskList[%{#iterStat.index}].categoryId"
								id="categoryId_%{#iterStat.index}"
								value="%{skuTaskList[#iterStat.index].categoryId}">
							</s:hidden>							
						</td>
						<td>
							<label for="subCategory">
								Sub Category
							</label>
							<br />	
							<s:textfield
								name="skuTaskList[%{#iterStat.index}].subCategoryName"
								id="subCategoryName_%{#iterStat.index}"
								value="%{skuTaskList[#iterStat.index].subCategoryName}"
								cssStyle="width: 155px;" cssClass="shadowBox" theme="simple"
								disabled="true">
							</s:textfield>
							<s:hidden 
								name="skuTaskList[%{#iterStat.index}].subCategoryId"
								id="subCategoryId_%{#iterStat.index}"
								value="%{skuTaskList[#iterStat.index].subCategoryId}">
							</s:hidden>							
						</td>
						<td>
							<label for="skill">
								Skill
							</label>
							<br />
							<s:textfield
								name="skuTaskList[%{#iterStat.index}].skillName"
								id="skillName_%{#iterStat.index}"
								value="%{skuTaskList[#iterStat.index].skillName}"
								cssStyle="width: 155px;" cssClass="shadowBox" theme="simple"
								disabled="true">
							</s:textfield>
							<s:hidden 
								name="skuTaskList[%{#iterStat.index}].skillId"
								id="skillId_%{#iterStat.index}"
								value="%{skuTaskList[#iterStat.index].skillId}">
							</s:hidden>							
						</td>
						<td>
							<br />
							<s:a cssStyle="color: red;" href="javascript:confirmSkuTaskDelete('%{#iterStat.index}')">Remove</s:a>
						</td>
					</tr>
					<!-- end row -->
				</table>
			</s:iterator>
		</fieldset>

		<p></p>

		<s:hidden id="selectedCategoryId" name="selectedCategoryId" value="">
		</s:hidden>
		<fieldset style="border: 1px solid #CCC;">
			<legend
				style="margin: 4px; padding: 0 10px; color: #00A0D2; font-size: 12px;">
				Add New Tasks
			</legend>			
			<table border="0" cellspacing="4" cellpadding="2"
				style="margin: 10px">
				<tr>
					<td>
						<label for="taskName">
							Task Name
						</label>
						<br />
						<s:textfield id="taskName" name="taskName" cssClass="shadowBox"
							cssStyle="width: 155px;" value="%{taskName}" 
							disabled="true" />
					</td>
					<td>
						<label for="taskComments">
							Task Comments
						</label>
						<br />
						<s:textfield id="taskComment" name="taskComment"
							cssClass="shadowBox" cssStyle="width: 155px;"	value="%{taskComment}" 
							disabled="true" />
					</td>
					<td>
						<label for="category">
							Category
						</label>
						<br />
						<s:select id="categorySelection_0"
							name="newSkuTask.categoryId"
							key="%{newSkuTask.categoryId}"
							onchange="newco.jsutils.loadSubcategories2('categorySelection_0', 'selectedCategoryId','jobCode', 0, true)"
							headerKey="-1"
							headerValue="-- Select One --" 
							list="categorySelection"
							listKey="nodeId" listValue="nodeName" cssStyle="width: 155px;"
							size="1" theme="simple"							
							value="%{newSkuTask.categoryId}" />					
					</td>
					<td>
						<label for="subCategory">
							Sub Category
						</label>
						<br />
						<s:select id="subCategorySelection_0"
									name="newSkuTask.subCategoryId"
									key="%{newSkuTask.subCategoryId}" 
									headerKey="-1"
									headerValue="-- Select One --"
									list="subCategorySelection"
									listKey="nodeId" listValue="nodeName" cssStyle="width: 155px;"
									size="1" theme="simple"									
									value="%{newSkuTask.subCategoryId}"
									/>							
					</td>
					<td>
						<label for="skill">
							Skill
						</label>
						<br />
						<s:select id="skillSelection_0"
							name="newSkuTask.skillId"
							headerKey="-1"
							headerValue="-- Select One --" 
							list="skillSelection"
							listKey="serviceTypeId" listValue="description"
							cssStyle="width: 155px;" size="1" theme="simple"							
							value="%{newSkuTask.skillId}"
							/>									
					</td>
					<td>
						<br />
						<s:submit 
							type="submit" 
							value="Add Task" 
							cssClass="shadowBox"
							cssStyle="background: #DDD; padding: 1px 2px" 
							method="addSkuTask"/>
					</td>
				</tr>
			</table>
		</fieldset>
		<table border="0" cellspacing="0" cellpadding="0"
			style="width: 100%; margin: 10px 0">
			<tr>
				<td>
					<input type="button" value="Return To Main" class="shadowBox"
							style="background: #DDD; padding: 1px 2px" onclick="javascript:fnReturnToDashboard()"/>	
				</td>
				<td style="text-align: right;">  
					<!--  		
					<input type="submit" value="Save Job Code" class="shadowBox"
						style="background: #DDD; padding: 1px 2px" 
						onclick="javascript:fnSaveJobCodeDetails()"/>
					-->
					<s:submit 
							type="submit" 
							value="Save Job Code" 
							cssClass="shadowBox"
							cssStyle="background: #DDD; padding: 1px 2px" 
							method="saveJobCode"/>
					
				</td>
			</tr>
		</table>
	</s:form>
</div>
<div dojoType="dijit.Dialog" id="jobCodeCriteria" title="Job Code Mapping">
	<b>Validation Error</b><br>
	<div id="message"></div>
</div>