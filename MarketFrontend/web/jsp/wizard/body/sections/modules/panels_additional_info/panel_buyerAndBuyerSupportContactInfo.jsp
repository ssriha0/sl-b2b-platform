<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="Buyer & Buyer Support Contact Information" id="" class="contentWellPane">
  <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addifo.buyer.support.description"/>
  <br/>
  <strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addifo.buyer.support.buyer.contact.label"/></strong>
  <table cellpadding="0" cellspacing="0" class="contactInfoTable" style="margin-top:0px;">
  
	  <tr> 
	  		<td class="column1"> 					
				<s:label value="%{altBuyerSupportLocationContact.firstName}" 
				 	 	 theme="simple" cssStyle="width: 160px;"/>
				<s:label value="%{altBuyerSupportLocationContact.lastName}" 
				 	 	 theme="simple" cssStyle="width: 160px;"/> 	
				<s:label value="(User Id#" theme="simple"/>	
				<s:label value="%{altBuyerSupportLocationContact.resourceId})" 
				 	 	 theme="simple" cssStyle="width: 160px;"/> 					
				<!-- <s:label value=")" theme="simple"/> -->			 	 	 			 	
			</td>
			<td  class="column2">
				<strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.primary.label.workphone"/></strong>
			</td>
			<td  class="column3">
				<s:label value="%{altBuyerSupportLocationContact.phones[0].phone}" 
				 	 	 theme="simple" cssStyle="width: 30px;"/>
							 &nbsp;&nbsp;&nbsp;
				<s:label value="Ext." theme="simple"/>	
				<s:label value="%{altBuyerSupportLocationContact.phones[0].ext}" 
				 	 	 theme="simple" cssStyle="width: 45px;"/>							 	 	 
			</td>
	  </tr>
	  <tr> 
	  		<td> 					
				<s:label value="%{altBuyerSupportLocationContact.businessName}" 
				 	 	 theme="simple" cssStyle="width: 160px;"/> 
				<s:label value="(ID#" theme="simple"/>	
				<s:label value="%{companyId})" 
				 	 	 theme="simple" cssStyle="width: 160px;"/> 					
				<!-- <s:label value=")" theme="simple"/>	-->		 	 	 			 	
			</td>
			<td  class="column2">
				<strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.primary.label.mobilephone"/></strong>
			</td>	
			<td  class="column3">
				<s:label value="%{altBuyerSupportLocationContact.phones[1].phone}" 
				 	 	 theme="simple" cssStyle="width: 30px;"/> 
					 	  	 	 	
							 	 	 
			</td>					
	  </tr>	 
	  <tr> 
	  		<td>
	  			<s:if test="%{altBuyerSupportLocationContact.streetName1 != null &&  altBuyerSupportLocationContact.streetName1 != ''}">	
					<s:label value="%{altBuyerSupportLocationContact.streetName1}" 
				 	 	 theme="simple" />
				 	 	 <s:if test="%{altBuyerSupportLocationContact.aptNo != null && altBuyerSupportLocationContact.aptNo != ''}">, </s:if>
				</s:if>
	  			<s:if test="%{altBuyerSupportLocationContact.aptNo != null && altBuyerSupportLocationContact.aptNo != ''}"> 				 	 	 
					<s:label value="%{altBuyerSupportLocationContact.aptNo}" 
					 	 	 theme="simple" />
				</s:if>
			</td>	
			<td  class="column2">
				<strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.fax"/></strong>
			</td>
			<td  class="column3">
				<s:label value="%{altBuyerSupportLocationContact.phones[2].phone}" 
				 	 	 theme="simple" cssStyle="width: 30px;"/> 
					  	 	 	
			</td>			
	  </tr>	
	  <tr>
			<td>
				<s:label value="%{altBuyerSupportLocationContact.StreetName2}" 
				 	 	 theme="simple" cssStyle="width: 160px;"/> 					
			</td>	
			<td  class="column2">
				<strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.email"/></strong>
			</td>
			<td  class="column3">
				<s:label value="%{altBuyerSupportLocationContact.email}" 
				 	 	 theme="simple" cssStyle="width: 30px;" /> 
			</td>			
	  </tr>
	  <tr>
			<td>
				<s:if test="%{altBuyerSupportLocationContact.city != null &&  altBuyerSupportLocationContact.city != ''}">	
					<s:label value="%{altBuyerSupportLocationContact.city}" 
				 	 	 theme="simple" cssStyle="width: 160px;"/>
				 	 <s:if test="%{altBuyerSupportLocationContact.state != '-1'}">, </s:if>
				</s:if>
				
				
				<s:if test="%{altBuyerSupportLocationContact.state != '-1'}">		
					<s:label value="%{altBuyerSupportLocationContact.state}" 
					 	 	 theme="simple" cssStyle="width: 160px;"/> 	
					<s:if test="%{altBuyerSupportLocationContact.zip != null && altBuyerSupportLocationContact.zip != ''}">, </s:if>
				</s:if>
				
				<s:if test="%{altBuyerSupportLocationContact.zip != null && altBuyerSupportLocationContact.zip != ''}">						 	 	 					 	 	 				
					<s:label value="%{altBuyerSupportLocationContact.zip}" 
					 	 	 theme="simple" cssStyle="width: 160px;"/>
				</s:if>
			</td>	
			<td  class="column2">
				<strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.primary.label.co.rating"/></strong>
			</td>
			<td  class="column3">			
				<s:label value="%{altBuyerSupportLocationContact.rating}/5.0" theme="simple"/>
			</td>	
	  </tr>	  

    
  </table>
  <p>
    <s:checkbox  id="altBuyerSupportLocationContactFlg" name="altBuyerSupportLocationContactFlg" value="%{altBuyerSupportLocationContactFlg}" theme="simple"  onclick="toggleAltContactDisabled('altBuyerDropdown')"/>
    <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.primary.label.alt.buyer.label"/>
  </p>


	<s:if test="%{altBuyerSupportLocationContactFlg}">
    	<div id="altBuyerDropdown" name="altBuyerDropdown" style="display:block"> 
	</s:if>
	<s:else>
    	<div id="altBuyerDropdown" name="altBuyerDropdown" style="display:none">
	</s:else>
		<select name="selectedAltBuyerContact.contactId"
			style="width: 200px;">
			<option value="">
				<fmt:message bundle="${serviceliveCopyBundle}" key="search.please.select"/>
			</option>

			<c:forEach var="altContact" items="${altBuyerContacts}">
				<c:choose>
					<c:when test="${(altContact.resourceId == selectedAltBuyerContact.resourceId)}"> <%-- Show the specified alternate buyer as selected --%>
						<option selected="selected" value="${altContact.contactId}">
							${altContact.lastName}, ${altContact.firstName} (ID#
							${altContact.resourceId})
						</option>
					</c:when>
					<c:otherwise>
						<option value="${altContact.contactId}">
							${altContact.lastName}, ${altContact.firstName} (ID#
							${altContact.resourceId})
						</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
	</div>
</div>


	
