<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
function expandCustomRef(path)
{
jQuery("#customreference p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#customreferenceId").slideToggle(300);

var ob=document.getElementById('compCustImg').src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('compCustImg').src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('compCustImg').src=path+"/images/widgets/arrowRight.gif";
}
}
	
</script>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

		<div id="customreference" class="menugroup_list">
  <p class="menugroup_head" onclick="expandCustomRef('${staticContextPath}')">&nbsp;<img id="compCustImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Custom References</p>
    <div class="menugroup_body" id="customreferenceId">
    
    <p></p>
    <div class="customRefError">
			<span style="color:#FF8000;">
				<b id="customRefError"></b>
				<ul>
                	<li style="margin-left:30px;"> Only these characters: <b>a-z</b>, <b>A-Z</b>, <b>0-9</b>,  <b>&quot;.&quot;</b> (dot),
                    	 <b>&quot;-&quot;</b> (dash), <b>&quot;/&quot;</b> (forward slash) and space are accepted.
                    </li>
                </ul>
				<p>Please correct and try again. If you think your number is correct, click &quot;Submit For Payment&quot; to continue. 
				This may delay order closure and payment.</p>
			</span>
	</div>
	
	<!-- Priority 5B changes -->
	<input type="hidden" name="modelError" id="modelError" value="" />
	<input type="hidden" name="serialError" id="serialError" value="" />
	<input type="hidden" id="modelValidated" value="" />
	<input type="hidden" id="serialValidated" value="" />
	<input type="hidden" id="isModelSerialSame" value="" />
	
	<c:if test="${null != modelRule && not empty modelRule}">	
		<input type="hidden" id="modelrulescount" value="${fn:length(modelRule)}" />		
		<c:forEach items="${modelRule}" var="rule" varStatus="status">			
			<input type="hidden" id="modelregex${status.count}" value="${rule.regex}" />
			<input type="hidden" id="modelmsg${status.count}" value="${rule.errorMsg}" />			
		</c:forEach>
	</c:if>
	
	<c:if test="${null != serialRule && not empty serialRule}">
		<input type="hidden" id="serialrulescount" value="${fn:length(serialRule)}" />		
		<c:forEach items="${serialRule}" var="rule" varStatus="status">			
			<input type="hidden" id="serialregex${status.count}" value="${rule.regex}" />
			<input type="hidden" id="serialmsg${status.count}" value="${rule.errorMsg}" />			
		</c:forEach>
	</c:if>
	
	<p>
		The Buyer has asked you to specify a value for the reference fields below.  <br/>
		<fmt:message bundle="${serviceliveCopyBundle}"key="required.field" />
		<br/>
 
	</p>

		<table width="600" cellpadding="0" cellspacing="0">
		</table>
	


	<table cellpadding="0" cellspacing="0" width="600">
		<tr>
			<td width="45" valign="top">
				<p>
					<label>
						<b><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.custom.ref.type"/></b>
					</label>
				</p>
			</td>
			<td width="100">
				<p>
					<label>
						<b><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.custom.ref.value"/></b>
					</label>
				</p>
			</td>
		</tr>
	<s:iterator value="buyerRefs" status="counter">
		<tr>
			<td width="45" valign="top">
				<p>
					<label>
						<c:if test="${required == 1 }">
						<span class="req">*</span>
						</c:if>
						${referenceType}
					</label>
				</p>
			</td>
			<td width="100">
				<p>
				  <tags:fieldError id="referenceValue">
					<s:textfield name="%{'buyerRefs[' + #counter.index + '].referenceValue'}" value="%{buyerRefs[#counter.index].referenceValue}" id="referenceValue%{referenceType}" 
								cssStyle="width: 150px;" cssClass="shadowBox grayText" theme="simple" maxlength="5000" onblur="validateNumber('%{referenceType}');"/>
				  </tags:fieldError>										
				</p>
			</td>
		</tr>
	</s:iterator>
	
		<tr>
			<td>
			</td>

			<td valign="top">
			</td>
		</tr>

	</table>

</div>
</div>


