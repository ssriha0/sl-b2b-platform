<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="zipcodesSelected" scope="request" value="<%=request.getAttribute("selectdzipcodesJson")%>"/>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>

<div id="generalInformationTab" style="height:1839px;overflow-x: hidden;overflow-y:auto">
<div id="content_right_header_text">
   	<%@ include file="../message.jsp"%>
</div>

<h3 class="paddingBtm">
<s:property value="generalInfoDto.fullResoueceName"/>
(User Id# <s:property value="generalInfoDto.resourceId"/>)
</h3>

<s:form name="generalInfoForm" id="generalInfoForm" action="generalInfoAction" method="post" theme="simple" validate="true">

<input type="hidden" name="generalInfoDto.vendorId" value="${generalInfoDto.vendorId}" />
<input type="hidden" name="generalInfoDto.resourceId" value="${generalInfoDto.resourceId}" />
<input type="hidden" name="generalInfoDto.contactId" value="${generalInfoDto.contactId}" />
<input type="hidden" name="generalInfoDto.locationId" value="${generalInfoDto.locationId}" />
<input id="generalInfoDtoAction" type="hidden" name="generalInfoDto.action" value="${generalInfoDto.action}" />
<input type="hidden" name="generalInfoDto.userNameExists" value="${generalInfoDto.userNameExists}" />
<input type="hidden" name="generalInfoDto.oldDispatcherInd" value="${generalInfoDto.dispatchInd}" />
<c:choose>
<c:when test="${generalInfoDto.ssn == null || generalInfoDto.ssn ==' '}">
<input type="hidden" name="generalInfoDto.ssn" value="${legalSSN}"/> 
</c:when>
<c:otherwise>
<input type="hidden" name="generalInfoDto.ssn" value="${generalInfoDto.ssn}"/> 
</c:otherwise>
</c:choose>
<input type="hidden" name="generalInfoDto.userNameAdmin" value="${generalInfoDto.userNameAdmin}"/> 
<input type="hidden" name="generalInfoDto.ssnOldValue" value="${generalInfoDto.ssnOldValue}"/>
<input type="hidden" name="generalInfoDto.popNovalue" value="${generalInfoDto.popNovalue}"/>

<c:choose>
<c:when test="${generalInfoDto.ssnRight == null or generalInfoDto.ssnRight == ''}">
	<s:hidden name="generalInfoDto.ssnValInd" value="1"/>  
</c:when>
<c:otherwise>
	<input type="hidden" name="generalInfoDto.ssnValInd" value="${generalInfoDto.ssnValInd}"/> 
</c:otherwise>
</c:choose>

<s:set name="disableInd" value="false"></s:set>
<s:set name="addUserInd" value="false"></s:set>
<s:set name="saveInd" value="%{generalInfoDto.popNovalue}"></s:set>
<c:if test="${generalInfoDto.resourceId != null}">
	<s:set name="disableInd" value="true"></s:set>	
	<input type="hidden" name="generalInfoDto.zipcodesUpated" value="true"/>
</c:if>
<input type="hidden" name="generalInfoDto.zipcodesCovered" value="${generalInfoDto.zipcodesCovered}"/>
<input type="hidden" name="zipcodes" value='${zipcodesSelected}'/>
<input type="hidden" name="zipcodesToPost" value='${zipcodesSelected}'/>
<input type="hidden" name="coverageRadius"/>
<input type="hidden" name="generalInfoDto.mapboxUrl" value="${generalInfoDto.mapboxUrl}"/>

<p class="paddingBtm">ServiceLive simplifies project management by letting your service providers accept and manage their own orders. That means less administrative work for you and your staff and more time in the field for your service pro.</p>


<c:if test="${generalInfoDto.resourceId != null}">
<div class="darkGrayModuleHdr">Profile Photo</div>
<div class="grayModuleContent grayModuleContentPhoto">
  
  	<iframe id="uploadPics" name="uploadPicsiframeID"
			src="generalInfoPicture_execute.action?resourceId=${generalInfoDto.resourceId}" 
			width="690" height="110" FRAMEBORDER="0" scrolling="no" marginwidth="0" marginheight="0">
	</iframe>
</div>
</c:if>

<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">Role in the Marketplace</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Will this person perform service on orders from the marketplace?</p>
  <p class="paddingBtm ">
<s:set name="resourceInd" value="0"></s:set>
  <c:if test="${generalInfoDto.resourceInd == null or generalInfoDto.resourceInd == ''}">
  	<s:set name="generalInfoDto.resourceInd" value="1"></s:set>
  	<s:set name="resourceInd" value="1"></s:set>
  </c:if>
  
  <c:choose>
  <c:when test="${generalInfoDto.userExistInd > 0 }">
  <c:choose>
  <c:when test="${disableInd}">
  	<s:radio id="testvalue" name="generalInfoDto.resourceInd" labelposition="top" list="#{'1':'Yes', '0':'No'}" theme="simple"/>
  	</c:when>
  	<c:otherwise>
  	  	<s:radio id="testvalue" value="1" name="generalInfoDto.resourceInd" labelposition="top" list="#{'1':'Yes', '0':'No'}" theme="simple"/>
  	</c:otherwise>
  	</c:choose>
  </c:when>
  
  <c:otherwise>
    <s:set name="generalInfoDto.resourceInd" value="1"></s:set>
  	<s:hidden name="generalInfoDto.resourceInd" value="1" ></s:hidden>
  	<s:set name="resourceInd" value="1"></s:set>
  	<s:radio id="testvalue" value="1" name="resourceInd" disabled="true" labelposition="top" list="#{'1':'Yes', '0':'No'}" theme="simple"/>
  </c:otherwise>
  </c:choose>
    </p>
  
  <div class="clear"></div>
  In order for your company to participate in the marketplace, you must have 'Service Pro Profiles' complete for each Service Pro in your company who will be performing work in customer's homes. 
  Please select 'Yes' if you would like to register a Service Pro Now.
</div>

<c:if test="${disableInd}">
	<div class="darkGrayModuleHdr">Marketplace Status</div>
	<div class="grayModuleContent mainWellContent clearfix">
	  <p>Set the status for your service pro to 'active' or 'inactive,' depending on his or her availability. Orders will not
	be sent to the service pro while status is set to 'inactive.' Status can be set back to 'active' at any time.</p>
	  <p class="paddingBtm ">
	 
	 	<c:choose>
    		<c:when test="${generalInfoDto.marketPlaceInd == null or generalInfoDto.marketPlaceInd == ''}">
	  			<s:radio  value="1" name ="generalInfoDto.marketPlaceInd" labelposition="top" list="#{'1':'Active', '0':'InActive'}" theme="simple"/>
	  		</c:when>
	  		<c:otherwise>
    			<s:radio  name ="generalInfoDto.marketPlaceInd" labelposition="top" list="#{'1':'Active', '0':'InActive'}" theme="simple"/>
    		</c:otherwise>
    	</c:choose>
	</div>
</c:if>


<div class="darkGrayModuleHdr">Personal Information</div>
<div class="grayModuleContent mainWellContent clearfix">

  <p>Please enter the personal information requested below. The Social Security number, which will be encrypted for security, will be used as the unique identifier for this service pro on the ServiceLive network. </p>
  <table cellpadding="0" cellspacing="0" width="650px">
   <!----------------------**************** EDITING  EXISTING USER SSN START*************************---------->
  	<c:choose>
  	<c:when test="${disableInd}">
  	<tr>
      <td width="15px"><strong>Name</strong><br />
       	<p class="paddingBtm">
       		${generalInfoDto.lastName} ${generalInfoDto.firstName} ${generalInfoDto.middleName} ${generalInfoDto.suffix}
       		
       		<input type="hidden" name="generalInfoDto.firstName" value="${generalInfoDto.firstName}" />
       		<input type="hidden" name="generalInfoDto.middleName" value="${generalInfoDto.middleName}"/>
       		<input type="hidden" name="generalInfoDto.lastName" value="${generalInfoDto.lastName}"/>
       		<input type="hidden" name="generalInfoDto.suffix" value="${generalInfoDto.suffix}"/>
       		<input type="hidden" name="generalInfoDto.lockedSsn" value="${generalInfoDto.lockedSsn}"/>
       		<input type="hidden" id="saveInd" value="${generalInfoDto.popNovalue}"/>
       		
       	</p>
      </td>

      <td width="15px">
      <div><strong>Social Security Number</strong>
      
       <s:set name="disableSSNInd" value="false"></s:set>
			
			<c:if test="${generalInfoDto.lockedSsn}">
				<s:set name="disableSSNInd" value="true"></s:set>	
			</c:if>
	      
	      <c:choose>
	       <c:when test="${fieldErrors['generalInfoDto.ssn'] == null}">  
	       		<p class="paddingBtm">
	       	</c:when>
	       	<c:otherwise>
	    		<p class="errorBox">
	    		<s:set name="disableSSNInd" value="false"></s:set>
			</c:otherwise>
			</c:choose>         
			<c:choose>
		  	<c:when test="${disableSSNInd}">
		  		###-##-<s:property value="%{generalInfoDto.ssnRight}" />
		  		<s:hidden name="generalInfoDto.ssnRight" value="%{generalInfoDto.ssnRight}"/>
		  	</c:when>
		  	<c:otherwise>
		  		<c:set var="ssnLeftVal" value=""/>	
		  		<c:set var="ssnMiddleVal" value=""/>
		  		 <c:if test="${saveInd}">
		  		 
			  		<c:set var="ssnLeftVal" value="###"/>
			  		<c:set var="ssnMiddleVal" value="##"/>	
			  	</c:if>
			  	<c:if test="${!saveInd}">
			  		
			  	    <c:set var="ssnLeftVal" value="${generalInfoDto.ssnLeft}"/>
			  		<c:set var="ssnMiddleVal" value="${generalInfoDto.ssnMiddle}"/>
			  	</c:if>
			  	
			  	<input type="text" name="generalInfoDto.ssnLeft" style="width: 35px;" 
			  	maxlength="3" class="shadowBox grayText" value="${ssnLeftVal}"
			  	onchange="setSSN('document.generalInfoForm');"
			  	/>
			  	-
		        <input type="text" name="generalInfoDto.ssnMiddle" style="width: 25px;" 
		        maxlength="2" class="shadowBox grayText" value="${ssnMiddleVal}"
		        onchange="setSSN(document.generalInfoForm);"/>
				-
		  	    <input type="text" name="generalInfoDto.ssnRight" style="width: 35px;" 
		  	    maxlength="4" class="shadowBox grayText" value="${generalInfoDto.ssnRight}"
		  	    onchange="setSSN(document.generalInfoForm);"/>	
		   </c:otherwise>
		   </c:choose>
	  	    </div>
	  	   	  <c:if test="${disableSSNInd == false}">
			      <div style="float:left;">
			        <div class="ssnText"> 
			        <table> 
			     		<tr>
			     			<td>
			     				<img src="${staticContextPath}/images/s_icons/information.png">
			     			</td>
			     			<td>
			     			<span>Please review to ensure you have entered your Social Security Number correctly.Failure to do so may delay our ability to approve you ready for market.</span>
			     			</td>
			     			</tr>
			     		</table>
			         	
			         </div>
			         </div>
			        
  			</c:if>	    
      
      </td>
      
    </tr>
  	</c:when>
  
    <c:otherwise>
    	 <!----------------------**************** EDITING  EXISTING USER  SSN END*************************---------->
     <!----------------------**************** ADDING NEW USER SSN START*************************---------->
	    <tr>
	      <td>
	      <c:choose>
	      <c:when test="${fieldErrors['generalInfoDto.firstName'] == null}">  
	       		<p class="paddingBtm">
	       	</c:when>
	       	<c:otherwise>
	    		<p class="errorBox">
			</c:otherwise>
			</c:choose>
			 Legal First Name <br/>
			 <!-- 
	        <s:textfield name="generalInfoDto.firstName"  onfocus="clearField('generalInfoForm_generalInfoDto_firstName');" 
	        cssStyle="width: 230px;color: #666666; font-style: italic;" cssClass="shadowBox grayText"  
	        value="%{generalInfoDto.firstName == null || generalInfoDto.firstName == ''?'Enter your LEGAL first name':generalInfoDto.firstName}" 
	        onblur="checkIfEmpty('generalInfoForm_generalInfoDto_firstName','first')"/>
	        -->
	        <s:textfield name="generalInfoDto.firstName"   
	        cssStyle="width: 230px;" cssClass="shadowBox grayText"  
	        value="%{generalInfoDto.firstName}" 
	        />
	        
	      </td>
	      <td width="50"></td>
	      <td><p class="paddingBtm">
	       Legal Middle Name<br />
	      		<s:textfield name="generalInfoDto.middleName" theme="simple" cssStyle="width: 230px;" cssClass="shadowBox grayText" value="%{generalInfoDto.middleName}"/>
	  	    optional </td>
	    </tr>
	    <tr>
	      <td> 
	    <c:choose>
	      	<c:when test="${fieldErrors['generalInfoDto.lastName'] == null}">  
	       		<p class="paddingBtm">
	       		</c:when>
	       	<c:otherwise>
	    		<p class="errorBox">
			</c:otherwise>
		</c:choose>
			Legal Last Name<br />
			<!-- 
	       	<s:textfield name="generalInfoDto.lastName" onfocus="clearField('generalInfoForm_generalInfoDto_lastName');" 
	       	cssStyle="width: 230px;color: #666666;font-style: italic;" cssClass="shadowBox grayText" 
	       	value="%{generalInfoDto.lastName == null || generalInfoDto.lastName ==''?'Enter your LEGAL last name':generalInfoDto.lastName}" 
	       	onblur="checkIfEmpty('generalInfoForm_generalInfoDto_lastName','last')"/>
	       	-->
	       	<s:textfield name="generalInfoDto.lastName" 
	       	cssStyle="width: 230px;" cssClass="shadowBox grayText" 
	       	value="%{generalInfoDto.lastName}" 
	       />
	      </td>
	      <td></td>
	      <td> <p class="paddingBtm">
	      Suffix (Jr., II, etc.)<br />
				<s:textfield name="generalInfoDto.suffix" theme="simple" cssStyle="width: 110px;" cssClass="shadowBox grayText" value="%{generalInfoDto.suffix}"/>
	  	    optional </td>
	    </tr>
	    <tr>
	      <td> 
	      	<div>
	     		 <div style="float: left;">
	      			Social Security Number <br /> 
		      
				     	<s:set name="disableSSNInd" value="false"></s:set>
				       <c:choose>
				       <c:when test="${fieldErrors['generalInfoDto.ssn'] == null}">  
				       		<p class="paddingBtm">
				       	</c:when>
				       	<c:otherwise>
				    		<p class="errorBox">
				    		<s:set name="disableSSNInd" value="false"></s:set>
						</c:otherwise>
						</c:choose>
					     
					     <c:choose>
					  	<c:when test="${disableSSNInd}">
					  		###-##-<s:property value="%{generalInfoDto.ssnRight}" />
					  		<s:hidden name="generalInfoDto.ssnRight" value="%{generalInfoDto.ssnRight}"/>
					  	</c:when>
					  	<c:otherwise>
						  	<input type="text" name="generalInfoDto.ssnLeft" style="width: 35px;" 
						  	maxlength="3" class="shadowBox grayText" value="${generalInfoDto.ssnLeft}"
						  	onchange="setSSN(document.generalInfoForm);"
						  	/>
						  	-
					        <input type="text" name="generalInfoDto.ssnMiddle" style="width: 25px;" 
					        maxlength="2" class="shadowBox grayText" value="${generalInfoDto.ssnMiddle}"
					        onchange="setSSN(document.generalInfoForm);"/>
							-
					  	    <input type="text" name="generalInfoDto.ssnRight" style="width: 35px;" 
					  	    maxlength="4" class="shadowBox grayText" value="${generalInfoDto.ssnRight}"
					  	    onchange="setSSN(document.generalInfoForm);"/>
					  	   
				  	    </c:otherwise>
				  	    </c:choose>
			  	    </div>
			  	    	 <c:if test="${disableSSNInd == false}">
			       <div style="float:left;">
			     		<div class="ssnText"> 
			     		 <table> 
			     		<tr>
			     			<td>
			     				<img src="${staticContextPath}/images/s_icons/information.png">
			     			</td>
			     			<td>
			     			<span>Please review to ensure you have entered your Social Security Number correctly.Failure to do so may delay our ability to approve you ready for market.</span>
			     			</td>
			     			</tr>
			     		</table>
			     
			         			
			       </div>
			         </div>
  			</c:if>	
		       </div>
		        
	      </td> 
	       
	    </tr>
	        
    </c:otherwise>
    </c:choose>
  <!----------------------**************** ADDING NEW USER SSN END*************************---------->
  </table>
  <br clear="all" />
</div>
<div class="darkGrayModuleHdr">Job Title &amp; Role </div>
<div class="grayModuleContent mainWellContent clearfix">
	<p>What is this person's job title and role within your company? More than one role can be assigned to each
person you register.</p>
  <div style="float: left;">
      <p>Role within company (Check all that apply.)</p>
      <table cellpadding="0" cellspacing="3px">
        <tr>
          <td colspan="3"><p>
          	<input type="checkbox" name="generalInfoDto.ownerInd" value="1" <c:if test="${addUserInd}"> disabled="disabled" </c:if> <c:if test="${generalInfoDto.ownerInd == 1}"> checked="checked" </c:if>  /> Owner/Principal</p>
          </td>
        </tr>
        <tr>
          <td colspan="3">
            <p>
            <input type="checkbox" name="generalInfoDto.managerInd" value="1" <c:if test="${addUserInd}"> disabled="disabled" </c:if> <c:if test="${generalInfoDto.managerInd == 1}"> checked="checked" </c:if> /> Manager</p>
          </td>
        </tr>
        <tr>
          <td colspan="3">
            <p>
            <input type="checkbox" name="generalInfoDto.adminInd" value="1" <c:if test="${addUserInd}"> disabled="disabled" </c:if> <c:if test="${generalInfoDto.adminInd == 1}"> checked="checked" disabled="true" </c:if> /> Administrator</p>
            <c:if test="${generalInfoDto.adminInd == 1}"> 
            	<s:hidden name="generalInfoDto.adminInd" value="1"/>   
            </c:if>
          </td>
        </tr>
        <tr>
          <td>
          	<input type="checkbox" name="generalInfoDto.dispatchInd" value="1" <c:if test="${addUserInd}"> disabled="disabled" </c:if> <c:if test="${generalInfoDto.dispatchInd == 1}"> checked="checked" </c:if> />
          </td>
          <td>
          	Dispatcher/Scheduler 
          </td>
          <td>       	
         		-For access to view, search and manage ALL orders for your company, also check "MANAGE SERVICE ORDERS"
				permission 		under Provider Permissions section in next tab.          		
          	
       <!--    	<p><input type="checkbox" name="sp_<s:property value="generalInfoDto.sproInd"/>"onclick="javascript:enableChkSP(this);" value="%{generalInfoDto.sproInd}" <c:if test="${generalInfoDto.sproInd == 1}"> checked="checked" disabled="true" </c:if> /> Service Provider</p> -->
       
          </td>
        </tr>
        <tr>
          <td colspan="3">
          <p><input type="checkbox" name="generalInfoDto.sproInd" value="1" <c:if test="${addUserInd}">disabled="disabled" </c:if> <c:if test="${generalInfoDto.sproInd == 1}"> checked="checked" disabled="true"</c:if> /> Service Provider</p>
          <c:if test="${generalInfoDto.sproInd == 1}"> 
            	<s:hidden name="generalInfoDto.sproInd" value="1"/>   
            </c:if>
          </td>
        </tr>
        <tr>
          <td colspan="3">
            <p><input type="checkbox" name="generalInfoDto.otherInd" value="1" <c:if test="${addUserInd}"> disabled="disabled" </c:if> <c:if test="${generalInfoDto.otherInd == 1}"> checked="checked" </c:if> /> Other</p>
          </td>
        </tr>
      </table>
      <table cellpadding="0" cellspacing="0">
        <tr>
          <td>
          	<p>Job Title</p>
         </td>
        </tr>
       	<tr>
          <td>
      		<p><s:textfield name="generalInfoDto.otherJobTitle" theme="simple" maxlength="100" cssStyle="width: 230px;" cssClass="shadowBox grayText" value="%{generalInfoDto.otherJobTitle}"/></p>
          </td>
          <td>
          	&nbsp;&nbsp;Optional
          </td>
        </tr>
      </table>
  </div>  
  <br clear="all" />
</div>
<div class="darkGrayModuleHdr">Dispatch Address &amp; Coverage Area</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>The dispatch address helps determine the service pro's distance from the service location. It does not have to
be the same as your business address. The more areas your service providers cover, the more orders you have the
potential to receive. </p>
  <table cellpadding="0" cellspacing="0">
    <tr>
      <td style="padding-bottom:8px;"> Street Name <span class="req">*</span><br />
      <c:choose>
       <c:when test="${fieldErrors['generalInfoDto.dispAddStreet1'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>  
		</c:choose>
			<c:choose>
          	<c:when test="${generalInfoDto.dispAddStreet1 == null}">
		  		<s:textfield name="generalInfoDto.dispAddStreet1" theme="simple" cssStyle="width: 336px;" cssClass="shadowBox grayText" onfocus="clearTextbox(this)" />
		  	</c:when>
		  	<c:otherwise>
		  		<s:textfield name="generalInfoDto.dispAddStreet1" theme="simple" cssStyle="width: 336px;" cssClass="shadowBox grayText" value="%{generalInfoDto.dispAddStreet1}"/> 
		  	</c:otherwise>
		  	</c:choose>
      </td>
      <td width="20"></td>
      <td> Apt. #<br />
         
         	<c:choose>
		  	<c:when test="${generalInfoDto.dispAddApt == null}">
		  		<s:textfield name="generalInfoDto.dispAddApt" theme="simple" cssStyle="width: 70px;" cssClass="shadowBox grayText" onfocus="clearTextbox(this)" />
		  	</c:when>
		  	<c:otherwise>
		  		<s:textfield name="generalInfoDto.dispAddApt" theme="simple" cssStyle="width: 70px;" cssClass="shadowBox grayText" value="%{generalInfoDto.dispAddApt}"/>
		  	</c:otherwise>
		  	</c:choose>
      </td>
    </tr>
    <tr>
      <td>
       
       	<c:choose>
		  	<c:when test="${generalInfoDto.dispAddStreet2 == null}">
		  		<s:textfield name="generalInfoDto.dispAddStreet2" theme="simple" cssStyle="width: 336px;" cssClass="shadowBox grayText" onfocus="clearTextbox(this)" />
		  	</c:when>
		  	<c:otherwise>
		  		<s:textfield name="generalInfoDto.dispAddStreet2" theme="simple" cssStyle="width: 336px;" cssClass="shadowBox grayText" value="%{generalInfoDto.dispAddStreet2}"/> 
		  	</c:otherwise>
		  </c:choose>
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
  </table>
  <table cellpadding="0" cellspacing="0">
    <tr>
      <td width="150"> City<span class="req">*</span><br />
      <c:choose>
       <c:when test="${fieldErrors['generalInfoDto.dispAddCity'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>   
			<c:choose>         
		  	<c:when test="${generalInfoDto.dispAddCity == null}">
		  		<s:textfield name="generalInfoDto.dispAddCity" theme="simple" cssStyle="width: 130px;" cssClass="shadowBox grayText" onfocus="clearTextbox(this)" />
		  	</c:when>
		  	<c:otherwise>
		  		<s:textfield name="generalInfoDto.dispAddCity" theme="simple" cssStyle="width: 130px;" cssClass="shadowBox grayText" value="%{generalInfoDto.dispAddCity}"/> 
		  	</c:otherwise>
		  	</c:choose>
	    
      </td>
      <td width="82"> State<br />
      
       <c:choose>
       <c:when test="${fieldErrors['generalInfoDto.dispAddState'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>
					<s:select id="dispAddState" name="generalInfoDto.dispAddState"
						list="#application['stateCodes']" value="generalInfoDto.dispAddState" listKey="type" listValue="descr"></s:select>
					
				</td>
      <td> Zip <span class="req">*</span><br />
      	<c:choose>
       <c:when test="${fieldErrors['generalInfoDto.dispAddZip'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise> 
		</c:choose>   
			<c:choose>     
		  	<c:when test="${generalInfoDto.dispAddZip == null}">
		  		<s:textfield name="generalInfoDto.dispAddZip" theme="simple" cssStyle="width: 80px;" maxlength="10" cssClass="shadowBox grayText" onfocus="clearTextbox(this)" />
		  	</c:when>
		  	<c:otherwise>
		  			<!-- 	<s:textfield name="generalInfoDto.dispAddZip" theme="simple" cssStyle="width: 80px;" maxlength="10" cssClass="shadowBox grayText" value="%{generalInfoDto.dispAddZip}"/> -->
		  	<s:textfield name="generalInfoDto.dispAddZip" id="dispatchZIP" theme="simple"
									cssStyle="width: 80px;" maxlength="10"
									cssClass="shadowBox grayText"
									value="%{generalInfoDto.dispAddZip}"
									onfocus="this.oldvalue = %{generalInfoDto.dispAddZip}"
									onchange="setSelectedZipCodes(this);this.oldvalue = this.value" />
		  	</c:otherwise>
		  	</c:choose>
	    
      </td>
      
    </tr>
	<tr>
	<td colspan="3">
	 <div id="msg" align="left" style="display:none font-size: 12px; font: Calibri; color: red; white-space:nowrap; "/>
	</td>
	</tr>
   <tr>
	<td colspan="3">
	 &nbsp;
	</td>
	</tr>	
    <tr>
     <td> Geographical Range <span class="req">*</span><br />
       <c:choose>
       <c:when test="${fieldErrors['generalInfoDto.dispAddGeographicRange'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>         		
		<s:select name="generalInfoDto.dispAddGeographicRange" list="generalInfoDto.geographicalRange"
			value="generalInfoDto.dispAddGeographicRange" onfocus="this.oldvalue = %{generalInfoDto.dispAddGeographicRange}"
									onchange="setSelectedRadius(this, dispatchZIP);this.oldvalue = this.value" listValue="descr" listKey="id" headerKey="" headerValue="Select One" cssStyle="width:125px;"></s:select>
				
      </td>
      <td colspan="3">&nbsp;</td>
      <td>
			 <%-- <c:choose>
			  <c:when test="${fieldErrors['generalInfoDto.zipcodesCovered'] == null}">  
					<p class="paddingBtm">
				</c:when>
			  <c:otherwise>
				<p class="errorBox" style="padding-left: 254px;margin-left: -254px">
			  </c:otherwise>
			</c:choose>	 --%>
			<p class="errorBox" style="padding-left: 254px;margin-left: -254px; margin-top: 12px;">
			<a href="#" onclick="verifyDispatchZipcode('${contextPath}','verifyDispatchZipcode.action','/jsp/providerRegistration/modules/provider_zipcode_coverage.jsp?zipcode=','&radius=','generalInfoDto.dispAddZip','generalInfoDto.dispAddGeographicRange', 'generalInfoDto.mapboxUrl')"><u
							style="padding-left: -100px; white-space: nowrap; font-size: 12px; font: Calibri; color: blue; padding-top: 17px;margin-left: -251px">Open Map to edit and confirm current zip code coverage </u></a>
								<span style="color:red">*</span>
		</td>
    </tr>
	<tr>
	<td colspan="3">
	<div id="coverageMsg" style="display:none font-size: 12px; font: Calibri; color: red; white-space:nowrap; margin-top: -11px;"/>
	</td>
	</tr>
	
  </table>
  <br clear="all" />
</div>
<div class="darkGrayModuleHdr">Billing Information</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Enter your service pro's hourly rate. Buyers will be able to see the preferred rate, but the service pro will still be able to accept orders that have a fixed spend limit or lower hourly rate. </p>
  Preferred Billing Rate <span class="req">*</span>
    <c:choose>
     <c:when test="${fieldErrors['generalInfoDto.hourlyRate'] == null}">  
       		<p class="paddingBtm">
       	</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise> 
	</c:choose>
		  	$ 
		  	<c:choose>
		  	<c:when test="${generalInfoDto.hourlyRate == null}">
		  		<s:textfield name="generalInfoDto.hourlyRate" theme="simple" cssStyle="width: 80px;" cssClass="shadowBox grayText" onfocus="clearTextbox(this)" />
		  	</c:when>
		  	<c:otherwise>
		  		<s:textfield name="generalInfoDto.hourlyRate" theme="simple" cssStyle="width: 80px;" cssClass="shadowBox grayText" value="%{generalInfoDto.hourlyRate}"/> 
		  	</c:otherwise>
		  	</c:choose>
	Per Hour 
  <br clear="all" />
</div>
<div class="darkGrayModuleHdr">Work Schedule</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Enter the hours the service provider is available to work.</p>
  <table cellpadding="0" cellspacing="0">
    <tr height="24">
      <td width="112">Monday</td>
      <c:choose>
      <c:when test="${generalInfoDto.monNaInd =='1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${generalInfoDto.monStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
     </c:choose>
      <td width="117"><input type="radio" value="24" name="mon" ${checkedStatus24 } onclick="updateScheduleCombo(this);" />
      				<input type="hidden" name="generalInfoDto.mon24Ind" value="${generalInfoDto.mon24Ind}" />
        24 hrs/day</td>
      <td width="65"><input type="radio" value="1" name="mon" ${checkedStatusFrom } onclick="updateScheduleCombo(this);" />
        From</td>
      <td width="101">
      	<s:select name="generalInfoDto.monStart" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" value="generalInfoDto.monStart" listValue="descr" listKey="descr" cssStyle="width: 85px;"/>
      </td>
      <td width="27">to</td>
      <td width="106">
      	<s:select name="generalInfoDto.monEnd" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" value="generalInfoDto.monEnd" listValue="descr" listKey="descr" cssStyle="width: 85px;"/>
      </td>
      <td><input type="radio" value="na" name="mon" ${checkedStatusNa } onclick="updateScheduleCombo(this);" />
      		<input type="hidden" name="generalInfoDto.monNaInd" value="${generalInfoDto.monNaInd}" />
        not available</td>
    </tr>
    <tr height="24">
      <td>Tuesday</td>
      <c:choose>
       <c:when test="${generalInfoDto.tueNaInd == '1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${generalInfoDto.tueStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
      
      <td width="117"><input type="radio" value="24" name="tue" ${checkedStatus24 } onclick="updateScheduleCombo(this);" />
      				<input type="hidden" name="generalInfoDto.tue24Ind" value="${generalInfoDto.tue24Ind}" />
        24 hrs/day</td>
      <td width="65"><input type="radio" value="1" name="tue" ${checkedStatusFrom } onclick="updateScheduleCombo(this);" />
        From</td>
      <td width="101">
      	<s:select name="generalInfoDto.tueStart" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.tueStart" cssStyle="width: 85px;"/>
      </td>
      <td width="27">to</td>
      <td width="106">
      	<s:select name="generalInfoDto.tueEnd" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.tueEnd" cssStyle="width: 85px;"/>
      </td>
      <td><input type="radio" value="na" name="tue" ${checkedStatusNa } onclick="updateScheduleCombo(this);" />
      		<input type="hidden" name="generalInfoDto.tueNaInd" value="${generalInfoDto.tueNaInd}" />
        not available</td>
    </tr>
    <tr height="24">
      <td>Wednesday</td>
      <c:choose>
       <c:when test="${generalInfoDto.wedNaInd == '1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${generalInfoDto.wedStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
      
      <td width="117"><input type="radio" value="24" name="wed" ${checkedStatus24 } onclick="updateScheduleCombo(this);" />
      				<input type="hidden" name="generalInfoDto.wed24Ind" value="${generalInfoDto.wed24Ind}" />
        24 hrs/day</td>
      <td width="65"><input type="radio" value="1" name="wed" ${checkedStatusFrom } onclick="updateScheduleCombo(this);" />
        From</td>
      <td width="101">
      	<s:select name="generalInfoDto.wedStart" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.wedStart" cssStyle="width: 85px;"/>
      </td>
      <td width="27">to</td>
      <td width="106">
      	<s:select name="generalInfoDto.wedEnd" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.wedEnd" cssStyle="width: 85px;"/>
      </td>
      <td><input type="radio" value="na" name="wed" ${checkedStatusNa } onclick="updateScheduleCombo(this);" />
      		<input type="hidden" name="generalInfoDto.wedNaInd" value="${generalInfoDto.wedNaInd}" />
        not available</td>
    </tr>
    <tr height="24">
      <td>Thursday</td>
      <c:choose>
       <c:when test="${generalInfoDto.thuNaInd == '1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${generalInfoDto.thuStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
      <td width="117"><input type="radio" value="24" name="thu" ${checkedStatus24 } onclick="updateScheduleCombo(this);" />
      				<input type="hidden" name="generalInfoDto.thu24Ind" value="${generalInfoDto.thu24Ind}" />
        24 hrs/day</td>
      <td width="65"><input type="radio" value="1" name="thu" ${checkedStatusFrom } onclick="updateScheduleCombo(this);" />
        From</td>
      <td width="101">
      	<s:select name="generalInfoDto.thuStart" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.thuStart" cssStyle="width: 85px;"/>
      </td>
      <td width="27">to</td>
      <td width="106">
      	<s:select name="generalInfoDto.thuEnd" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.thuEnd" cssStyle="width: 85px;"/>
      </td>
      <td><input type="radio" value="na" name="thu" ${checkedStatusNa } onclick="updateScheduleCombo(this);" />
      		<input type="hidden" name="generalInfoDto.thuNaInd" value="${generalInfoDto.thuNaInd}" />
        not available</td>
    </tr>
    <tr height="24">
      <td>Friday</td>
      <c:choose>
       <c:when test="${generalInfoDto.friNaInd == '1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${generalInfoDto.friStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
      
      <td width="117"><input type="radio" value="24" name="fri" ${checkedStatus24 } onclick="updateScheduleCombo(this);" />
      				<input type="hidden" name="generalInfoDto.fri24Ind" value="${generalInfoDto.fri24Ind}" />
        24 hrs/day</td>
      <td width="65"><input type="radio" value="1" name="fri" ${checkedStatusFrom } onclick="updateScheduleCombo(this);" />
        From</td>
      <td width="101">
      	<s:select name="generalInfoDto.friStart" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.friStart" cssStyle="width: 85px;"/>
      </td>
      <td width="27">to</td>
      <td width="106">
      	<s:select name="generalInfoDto.friEnd" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.friEnd" cssStyle="width: 85px;"/>
      </td>
      <td><input type="radio" value="na" name="fri" ${checkedStatusNa } onclick="updateScheduleCombo(this);" />
      		<input type="hidden" name="generalInfoDto.friNaInd" value="${generalInfoDto.friNaInd}" />
        not available</td>
    </tr>
    <tr height="24">
   
      <td>Saturday</td>
       <c:choose>
      <c:when test="${generalInfoDto.satNaInd == '1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${generalInfoDto.satStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
      <td width="117"><input type="radio" value="24" name="sat" ${checkedStatus24 } onclick="updateScheduleCombo(this);" />
      				<input type="hidden" name="generalInfoDto.sat24Ind" value="${generalInfoDto.sat24Ind}" />
        24 hrs/day</td>
      <td width="65"><input type="radio" value="1" name="sat" ${checkedStatusFrom } onclick="updateScheduleCombo(this);" />
        From</td>
      <td width="101">
      	<s:select name="generalInfoDto.satStart" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.satStart" cssStyle="width: 85px;"/>
      </td>
      <td width="27">to</td>
      <td width="106">
      	<s:select name="generalInfoDto.satEnd" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.satEnd" cssStyle="width: 85px;"/>
      </td>
      <td><input type="radio" value="na" name="sat" ${checkedStatusNa } onclick="updateScheduleCombo(this);" />
      		<input type="hidden" name="generalInfoDto.satNaInd" value="${generalInfoDto.satNaInd}" />
        not available</td>
    </tr>
    <tr height="24">
      <td>Sunday</td>
      <c:choose>
       <c:when test="${generalInfoDto.sunNaInd =='1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${generalInfoDto.sunStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
      <td width="117"><input type="radio" value="24" name="sun" ${checkedStatus24 } onclick="updateScheduleCombo(this);" />
      				<input type="hidden" name="generalInfoDto.sun24Ind" value="${generalInfoDto.sun24Ind}" />
        24 hrs/day</td>
      <td width="65"><input type="radio" value="1" name="sun" ${checkedStatusFrom } onclick="updateScheduleCombo(this);" />
        From</td>
      <td width="101">
      	<s:select name="generalInfoDto.sunStart" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.sunStart" cssStyle="width: 85px;"/>
      </td>
      <td width="27">to</td>
      <td width="106">
      	<s:select name="generalInfoDto.sunEnd" disabled="%{#attr['comboDisableStatus']}" list="generalInfoDto.scheduleTimeList" listValue="descr" listKey="descr" value="generalInfoDto.sunEnd" cssStyle="width: 85px;"/>
      </td>
      <td><input type="radio" value="na" name="sun" ${checkedStatusNa } onclick="updateScheduleCombo(this);" />
      		<input type="hidden" name="generalInfoDto.sunNaInd" value="${generalInfoDto.sunNaInd}" />
        not available</td>
    </tr>
  </table>
  <br clear="all" />
</div>
<div class="darkGrayModuleHdr">User Rights</div>
<div class="grayModuleContent mainWellContent clearfix">
  
  <c:choose>
  <c:when test="hasFieldErrors()">  
       	<div class="paddingBtm">
       	<s:set name="isAnyError" value="true"></s:set>       	
  </c:when>
  <c:otherwise>
  	<s:set name="isAnyError" value="false"></s:set>
  </c:otherwise>
  </c:choose>
  <c:choose>
  <c:when test="${fieldErrors['generalInfoDto.userName'] == null}">  
       	<div class="paddingBtm">
       	<s:set name="isError" value="false"></s:set>       	
  </c:when>  
  <c:otherwise>
   		<div class="errorBox">
   		<s:set name="isError" value="true"></s:set>
   		<s:set name="radChkUser" value="1"></s:set>
  </c:otherwise>
  </c:choose> 
  <c:if test="${generalInfoDto.editUserName}">
 		 <s:set name="radChkUser" value="1"></s:set>
  </c:if>
  <c:if test="${generalInfoDto.userName == null or generalInfoDto.userName == '' or isAnyError or 
  	(generalInfoDto.userName != null and generalInfoDto.userName != '' and generalInfoDto.editUserName == true)}">
  
  <p>This section allows you to give an associate login rights to your account. You will be able to assign specific user rights on the 'Marketplace Preferences' tab</p>
	
	  <p>Would you like to create login access for this user  <s:radio  name="radChkUser" onclick="javascript:enableChkUserRadio(this);" labelposition="top" list="#{'1':'Yes', '0':'No'}" theme="simple"/></p>
  </c:if> 
  
		<c:choose>
         <c:when test="${generalInfoDto.userName != null and generalInfoDto.userName != '' 
        				and (not isAnyError) and generalInfoDto.editUserName == false}">
        		 <c:choose>
	  			 <c:when test="${fn:trim(generalInfoDto.userName) != ''}">
	  			 	<p> You have already assigned a username for this user: </p><br>
  		    		<p> User Name<br>
  		    		<s:textfield name="generalInfoDto.userName" readonly="true" value="%{generalInfoDto.userName}"/></p>
  		    	 </c:when>
	  			 <c:otherwise>
	  				<p> User Name<br>
		  			<s:textfield id="userId" name="generalInfoDto.userName"></s:textfield>
		  		 </c:otherwise>	 
		  		</c:choose>		
  		</c:when>
  		<c:otherwise>
	  		<c:choose>
	  		<c:when test="${not isError and !generalInfoDto.editUserName}">
		  		<div id ="userVal" style="visibility:hidden;">
		  	</c:when>
		  	<c:otherwise>
		  		<div id ="userVal">
		  	</c:otherwise>
		  	</c:choose>
	  		 	 <p> User Name<br/>
 				
 				   <c:choose>
	  				<c:when test="${not isError and generalInfoDto.resourceId != null}">
	  					<c:choose>
	  							<c:when test="${not isError and generalInfoDto.userName != null and generalInfoDto.userName != '' and generalInfoDto.userNameExists}">
	  								<s:textfield id="userId" name="generalInfoDto.userName" readonly="true" ></s:textfield>
	  							</c:when>
	  							<c:otherwise>
		  							<s:textfield id="userId" name="generalInfoDto.userName"></s:textfield>
		  						</c:otherwise>				
	  					</c:choose>
	  				</c:when>
 					<c:otherwise>
		  				<s:textfield id="userId" name="generalInfoDto.userName"></s:textfield>
		  			</c:otherwise>	
 				   </c:choose>
 				
	  		</div>	
  		</c:otherwise>
  		</c:choose>
  </p>
  <br clear="all" />

<!-- This section is out of scope for iteration 13 release  -->
<!-- START OF OUT OF SCOPE -->
<!-- 
<div class="darkGrayModuleHdr">Service Provider Photo</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Please provide a photograph of the service pro, if available. This photograph will be used to identify the
provider at the service location. The photo should be no larger than 84 pixels by 123 pixels. Individual file
size is limited to 1MB. If you would like to add a new photo, remove the existing photo first.</p>
 
    <div style="float: left;"><img src="${staticContextPath}/images/images_registration/sp_registration/sp_photo.gif" width="100" height="139" alt="" /></div>
    <div style="float: left; padding: 0px 0px 0px 20px;">
      <p><strong>Select file to upload</strong></p>
      <input type="text" style="width: 370px;" class="shadowBox grayText" value="[Select file]" onfocus="clearTextbox(this)" />
      <br />
      <br />
      <div>
        <table style="border:1px #9f9f9f solid;" cellpadding="0" cellspacing="0" width="378">
          <tr style="background:#4cbcdf;color:#ffffff;height:22px;">
            <td style="padding-top:5px;padding-left:10px;" width="275"><strong>File Name</strong></td>
            <td style="padding-top:5px;"><strong>File Size</strong></td>
          </tr>
          <tr>
            <td style="padding:10px 0px 10px 10px;"><input type="checkbox" />
              Dparker.jpg</td>
            <td style="padding:10px 0px 0px 0px;">725kb</td>
          </tr>
        </table>
      </div>
      <br />
      <input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/images_registration/btn/view.gif);"class="btnBevel" />
      <input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/images_registration/btn/remove.gif);"class="btn20Bevel" />
    </div>
    <div style="float: left;padding:16px 0px 0px 10px;">
      <p>
        <input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/images_registration/btn/browse.gif);"class="btnBevel" />
        <input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="61" height="20" style="background-image:url(${staticContextPath}/images/images_registration/btn/attach.gif);"class="btn20Bevel" />
      </p>
      <p style="line-height: 20px;"> Preferred file types include:<br />
        JPG | PDF | GIF </p>
    </div>
  </div>
-->  
<!-- END OF OUT OF SCOPE -->

<div class="clearfix">
  <div class="formNavButtons">
		
	<s:submit action="generalInfoActiondoSave" value="" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/save.gif);width:50px;height:20px;"  cssClass="btn20Bevel"  onclick="setAction('Save','generalInfoDto');setIndicator();setZipcodes();"> </s:submit>	
	<s:submit action="generalInfoActiondoNext" value="" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/next.gif);width:50px;height:20px;"  cssClass="btn20Bevel"  onclick="setAction('Next','generalInfoDto');setIndicator();setZipcodes();"> </s:submit>

  <br/> <br/>
  	<c:if test="${sessionScope.userStatus=='editUser'}">
    	 	<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
    			<s:submit action="generalInfoActionupdateProfile" type="image"   
    			src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" 
     			cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/updateProfile.gif);width:120px;height:20px;"
				cssClass="btn20Bevel" onclick="setAction('Update','generalInfoDto');setZipcodes();">
				</s:submit>
			</c:if>	
	</c:if>	
	
	<c:if test="${sessionScope.resourceStatus != null and sessionScope.resourceStatus != '' }">
			<c:if test="${generalInfoDto.flagAdmin}">
				<c:if test="${generalInfoDto.primaryIndicator != 1}">
						<s:submit type="image" 
						onclick="submitDeleteUser()"
						src="%{#request['staticContextPath']}/images/common/spacer.gif"
						cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/removeUser.gif); width:88px; height:20px;"
						cssClass="btn20Bevel" 
						value="">
						</s:submit>
					</c:if>
			</c:if>	
	</c:if>			
    <input type="hidden" name="generalInfoDto.flagAdmin" value="${generalInfoDto.flagAdmin}"/>
    
     <c:if test="${sessionScope.userStatus !='editUser'}">
   			<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
    			<s:submit action="generalInfoActionupdateProfile" type="image"   
    			src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" 
     			cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/submitRegistration.gif);width:140px;height:20px;"
				cssClass="btn20Bevel" onclick="setAction('Update','generalInfoDto');setZipcodes();">
				</s:submit>
      			</c:if>
   			
    </c:if>
	</div>
	
  <div class="bottomRightLink"><a href='<s:url action="manageUserActiondoLoadUsers" includeParams="none"/>'>Cancel</a></div>
  
</div>
</s:form>
</div> 
  
