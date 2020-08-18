<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />	
<c:set var="OVERVIEW_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH%>" />
<c:set var="BUYER_TERM_COND_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_BUYER_TERM_COND_LENGTH%>" />
<c:set var="SPECIAL_INSTRUCTION_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH%>" />
<c:set var="TASK_COMMENTS_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_SCOPE_OF_WORK_TASK_COMMENTS_LENGTH%>" />

 <script src="${staticContextPath}/javascript/tinymce/tinymce.min.js"></script>
<script type="text/javascript">

var max_chars_spl_instructions = 5000; //max characters
var chars_without_html_spl_instructions = 0;

tinymce.init({ 
	  selector:'.taskCommentsTinyMce',
	  menubar: false,statusbar: false,
	  plugins: ["autolink autoresize link paste"],
	  toolbar1: '| undo redo | bold italic underline | formatselect | fontsizeselect |',
	  toolbar2: '| alignleft aligncenter alignright alignjustify | outdent indent | bullist numlist | link unlink | removeformat |',
	  block_formats:'Paragraph=p;Heading 1=h1;Heading 2=h2;Heading 3=h3;Heading 4=h4;Heading 5=h5;Heading 6=h6',
	  autoresize_max_height: 100,
	  setup: function (editor) {
	        editor.on('change', function () {
	            editor.save();
	        });
	        editor.on('keyup', function (editor, evt) {
	        	var id = this.id;
	        	var idlength = id.length;
	        	var lastChar = id.charAt(idlength-1);
	        	chars_without_html_spl_instructions = (tinyMCE.activeEditor.getContent().replace(/(<([^>]+)>)/ig, "")).length;
	        	var key = editor.keyCode;
	        	document.getElementById('task_comments_leftChars_'+this.id.charAt(this.id.length-1)).value = max_chars_spl_instructions - chars_without_html_spl_instructions;
              if (chars_without_html_spl_instructions >= max_chars_spl_instructions && key != 8 && key != 46) {
              	editor.stopPropagation();
              	editor.preventDefault();
              }
	        });
	        editor.on('keydown', function (editor, evt) {
	        	var id = this.id;
	        	var idlength = id.length;
	        	var lastChar = id.charAt(idlength-1);
	        	chars_without_html_spl_instructions = (tinyMCE.activeEditor.getContent().replace(/(<([^>]+)>)/ig, "")).length;
	        	var key = editor.keyCode;
	        	document.getElementById('task_comments_leftChars_'+this.id.charAt(this.id.length-1)).value = max_chars_spl_instructions - chars_without_html_spl_instructions;
              if (chars_without_html_spl_instructions >= max_chars_spl_instructions && key != 8 && key != 46) {
              	editor.stopPropagation();
              	editor.preventDefault();
              }
	        });
	  }
});
</script>
<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="Service Categories & Tasks" id=""
	class="contentWellPane">
	
	<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.descripton"/>
	<tags:fieldError id="MainServiceCategory" oldClass="paddingBtm">
		<label>
			<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.main.serv.cat"/>
			<font color="red">*</font>
		</label>
		<br>
		<p>
		<s:select name="mainServiceCategoryId" id="mainServiceCategory" 
			headerKey="-1" headerValue="-- Select One --" list="skillTreeMainCat"
			listKey="nodeId" listValue="nodeName" cssStyle="width: 190px;"
			size="1" theme="simple" value="%{#attr['catIdSession']}"
					onchange="loadSkillsAndCategories('mainServiceCategory', 'soWizardScopeOfWorkCreate', '%{#request['contextPath']}','%{#request['SERVICE_ORDER_ID']}'); toggleAltTaskDisabled('addAnotherTask','mainServiceCategory');"
			/>
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
		<c:when test="${!scopeOfWorkDTO.containsTasks}">
			<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.no.tasks"/>
		</c:when>
		<c:otherwise>

			<s:hidden id="selectedCategoryId" name="selectedCategoryId" value="">
			</s:hidden>
			
			<s:iterator value="addonInfo" status="addonInfoStat">
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].sku" id="addonInfo[%{#addonInfoStat.index}].sku"
									value="%{addonInfo[#addonInfoStat.index].sku == null || 
										  				addonInfo[#addonInfoStat.index].sku == ''? '' : addonInfo[#addonInfoStat.index].sku}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].description" id="addonInfo[%{#addonInfoStat.index}].description"
									value="%{addonInfo[#addonInfoStat.index].description == null || 
										  				addonInfo[#addonInfoStat.index].description == ''? '' : addonInfo[#addonInfoStat.index].description}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].scopeOfWork" id="addonInfo[%{#addonInfoStat.index}].scopeOfWork"
									value="%{addonInfo[#addonInfoStat.index].scopeOfWork == null || 
										  				addonInfo[#addonInfoStat.index].scopeOfWork == ''? '' : addonInfo[#addonInfoStat.index].scopeOfWork}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].retailPrice" id="addonInfo[%{#addonInfoStat.index}].retailPrice"
									value="%{addonInfo[#addonInfoStat.index].retailPrice}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].margin" id="addonInfo[%{#addonInfoStat.index}].margin"
									value="%{addonInfo[#addonInfoStat.index].margin}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].miscInd" id="addonInfo[%{#addonInfoStat.index}].miscInd"
									value="%{addonInfo[#addonInfoStat.index].miscInd}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].quantity" id="addonInfo[%{#addonInfoStat.index}].quantity"
									value="%{addonInfo[#addonInfoStat.index].quantity}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].serviceFee" id="addonInfo[%{#addonInfoStat.index}].serviceFee"
									value="%{addonInfo[#addonInfoStat.index].serviceFee}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].coverage" id="addonInfo[%{#addonInfoStat.index}].coverage"
									value="%{addonInfo[#addonInfoStat.index].coverage}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].autoGenInd" id="addonInfo[%{#addonInfoStat.index}].autoGenInd"
									value="%{addonInfo[#addonInfoStat.index].autoGenInd}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].addonPermitTypeId" id="addonInfo[%{#addonInfoStat.index}].addonPermitTypeId"
									value="%{addonInfo[#addonInfoStat.index].addonPermitTypeId}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].soId" id="addonInfo[%{#addonInfoStat.index}].soId"
									value="%{addonInfo[#addonInfoStat.index].soId}">
				</s:hidden>
				<s:hidden name="addonInfo[%{#addonInfoStat.index}].addonId" id="addonInfo[%{#addonInfoStat.index}].addonId"
									value="%{addonInfo[#addonInfoStat.index].addonId}">
				</s:hidden>
			</s:iterator>
			
			<s:iterator value="tasks" status="iterStat">
				<c:choose>
					<c:when
						test="${scopeOfWorkDTO.tasks[iterStat.index].taskName ==null}">
						<c:set var="taskDojoTitle" value="Task ${iterStat.count}" />
					</c:when>
					<c:otherwise>
						<c:set var="taskDojoTitle"
							value="Task ${iterStat.count} - ${scopeOfWorkDTO.tasks[iterStat.index].taskName}" />
					</c:otherwise>
				</c:choose>
				<div dojoType="dijit.TitlePane" title="${taskDojoTitle}"
					id="${iterStat.index}" class="dijitTitlePaneSubTitle"
					open="${iterStat.last ? true : false}">

					<p>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.task.name"/>
						<br>
						<s:textfield
							name="scopeOfWorkDTO.tasks[%{#iterStat.index}].taskName"
							value="%{scopeOfWorkDTO.tasks[#iterStat.index].taskName}"
							cssStyle="width: 200px;" cssClass="shadowBox" theme="simple">
						</s:textfield>
						<s:a href="javascript:confirmDeleteTask('%{#iterStat.index}')">Delete Task</s:a>

						<%-- 
						<input type="image" src="${staticContextPath}/images/common/spacer.gif" align="right"
								width="20" height="20"
								style="background-image: url(${staticContextPath}/images/btn/taskRemove.gif);"
								class="btn20Bevel"
								onclick="return deletePart('soWizardPartsCreate', '${contextPath}', ${sowPartDTO.index});">	
						 --%>
						<%-- 
						<input type="submit" 
						class="btn20Bevel" value="Delete Task" name="method:deleteTask" id="soWizardScopeOfWorkCreate__deleteTask"/>
					 --%>
					</p>
					<table class="categoryTable" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<p>
									<label>
										<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.category"/>
									</label>
								</p>
								<s:select id="categorySelection_%{#iterStat.index}"
									name="tasks[%{#iterStat.index}].categoryId"
									onchange="updateElementValue('category_%{#iterStat.index}', 'categorySelection_%{#iterStat.index}', 'subCategory_%{#iterStat.index}','subCategorySelection_%{#iterStat.index}');
												  loadSubcategories('categorySelection_%{#iterStat.index}', 'selectedCategoryId','soWizardScopeOfWorkCreate', '%{#request['contextPath']}', %{#iterStat.index},'%{#request['soId']}')"
									key="%{tasks[#iterStat.index].categoryId}" headerKey="-1"
									headerValue="-- Select One --" list="categorySelection"
									listKey="nodeId" listValue="nodeName" cssStyle="width: 190px;"
									size="5" theme="simple"
									disabled="false"
									value="%{tasks[#iterStat.index].categoryId}" />

								<s:hidden id="category_%{#iterStat.index}"
									name="tasks[%{#iterStat.index}].category"
									value="%{tasks[#iterStat.index].category == null || 
										  				tasks[#iterStat.index].category == ''? '' : tasks[#iterStat.index].category}">
								</s:hidden>
							</td>
							<td>
								<p>
									<label>
										<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.subcategory"/>
									</label>
								</p>
								<s:select id="subCategorySelection_%{#iterStat.index}"
									name="tasks[%{#iterStat.index}].subCategoryId"
									key="%{tasks[#iterStat.index].subCategoryId}" headerKey="-1"
									headerValue="-- Select One --"
									list="%{scopeOfWorkDTO.tasks[#iterStat.index].subCategoryList}"
									listKey="nodeId" listValue="nodeName" cssStyle="width: 190px;"
									size="5" theme="simple"
									disabled="false"
									value="%{tasks[#iterStat.index].subCategoryId}"
									onchange="updateElementValue('subCategory_%{#iterStat.index}', 'subCategorySelection_%{#iterStat.index}', null,null);
									loadSubcategoriesSkills('subCategorySelection_%{#iterStat.index}', 'selectedCategoryId','soWizardScopeOfWorkCreate', '%{#request['contextPath']}', %{#iterStat.index},'%{#request['soId']}')" />

								<s:hidden id="subCategory_%{#iterStat.index}"
									name="tasks[%{#iterStat.index}].subCategory"
									value="%{tasks[#iterStat.index].subCategory == null || 
										  				tasks[#iterStat.index].subCategory == ''? '' : tasks[#iterStat.index].subCategory}">

								</s:hidden>
							</td>
							<td class="column3">
								<p>
									<label>
										<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.skill"/>
									</label>
								</p>
								<s:select id="skillSelection_%{#iterStat.index}"
									name="tasks[%{#iterStat.index}].skillId"
									key="%{tasks[#iterStat.index].skillId}" headerKey="-1"
									headerValue="-- Select One --" list="skillSelection"
									listKey="serviceTypeId" listValue="description"
									cssStyle="width: 190px;" size="5" theme="simple"
									disabled="false"
									value="%{tasks[#iterStat.index].skillId}"
									onchange="updateElementValue('skill_%{#iterStat.index}', 'skillSelection_%{#iterStat.index}', null,null);loadCatSubcatSkills('categorySelection_%{#iterStat.index}','subCategorySelection_%{#iterStat.index}','skillSelection_%{#iterStat.index}','soWizardScopeOfWorkCreate', '%{#request['contextPath']}', %{#iterStat.index},'%{#request['soId']}')" />
								<s:hidden id="skill_%{#iterStat.index}"
									name="tasks[%{#iterStat.index}].skill"
									value="%{tasks[#iterStat.index].skill == null || 
										  				tasks[#iterStat.index].skill == ''? '' : tasks[#iterStat.index].skill}">
								</s:hidden>
							</td>
						</tr>
					</table>

					<div class="clear"></div>
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.cat.tasks.task.comments"/>
						</label>
					</p>
					<s:textarea name="tasks[%{#iterStat.index}].comments" id="task_comments_%{#iterStat.index}"
						value="%{tasks[#iterStat.index].comments}" cssClass=" taskCommentsTinyMce shadowBox"
						cssStyle="width: 620px" theme="simple" />
	    				<s:textfield id="task_comments_leftChars_%{#iterStat.index}" name="tasks_%{#iterStat.index}_comments_leftChars" size="4" maxlength="4" value="5000" readonly="true"/> <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>
 	 				<br/>


				</div>
			</s:iterator>
		</c:otherwise>
	</c:choose>
	

    <s:div id="addAnotherTask"  
    	cssClass="clearfix"
    	cssStyle="display:%{mainServiceCategoryId != -1 ? 'block' : 'none'}"
    	theme="simple">
		<p>
			<input type="button" id="addAnotherTask" 
					  		onclick="tinyMCE.triggerSave();newco.jsutils.doFormSubmit('soWizardScopeOfWorkCreate_addTask.action','soWizardScopeOfWorkCreate')"
					  		class="btn20Bevel"
							style="background-image: url(${staticContextPath}/images/btn/addAnotherTask.gif); width:105px; height:18px;"
					  		src="${staticContextPath}/images/common/spacer.gif"
			/>
		</p>
	</s:div>

	<!-- END TASK FORM -->

	<p align="right">
		<a href="#wizard_top"><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.back.to.top"/></a>
	</p>
</div>
