<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<s:iterator value="dtoList" status="dto">
	<div dojoType="dijit.TitlePane" title="Parts ${soId}" class="contentWellPane">
		<div class="hrText">
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.scopeofwork.cat.tasks.parts.materials" />
		</div>
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="wizard.scopeofwork.cat.tasks.parts.description" />
		<p>
			<label>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.scopeofwork.cat.tasks.parts.supplied.by" />
			</label>
			<br />

			<span class="formFieldOffset"> <s:radio
					list="buyerProviderMap" name="partsSuppliedBy" id="partsSuppliedBy"
					cssClass="antiRadioOffsets" value="partsSuppliedBy"
					onclick="fnShowOrHidePartsPanel();" theme="simple" /> </span>
		</p>
		<c:set var="hidepanel" scope="request" value="${partsSuppliedBy}" />
		<c:if test="${hidepanel==3 || hidepanel==2}">
			<div>
				<br />
				<br />
			</div>
			<!-- NEW NESTED MODULE - Task List -->
			<div id="PartsPanels" style="display: none;">
				<s:iterator value="parts" status="sowPartDTO">

					<!-- NEW NESTED MODULE -->

					<div dojoType="dijit.TitlePane"
						title="Part ${sowPartDTO.count} - ${parts[sowPartDTO.index].manufacturer} - ${parts[sowPartDTO.index].modelNumber}"
						class="dijitTitlePaneSubTitle" open="${sowPartDTO.last}">


	<div style="width: 550px" class="nohrText">
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.parts.product.label.info" />
		</div>
		
		<table cellspacing="0">
			<tr>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.manufacturer"/>
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.product.label"/>
				</td>
			</tr>
			<tr>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} -->  Manufacturer" oldClass="paddingBtm">
						<s:textfield size="50" maxlength="100"
							name="parts[%{#sowPartDTO.index}].manufacturer"
							value="%{parts[#sowPartDTO.index].manufacturer}" theme="simple"
							cssStyle="width: 150px;" cssClass="shadowBox grayText" />
					</tags:fieldError>	
				</td>
				<td>
					&nbsp;&nbsp;
				</td>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Product Line" oldClass="paddingBtm">
							<s:textfield size="50" maxlength="100"
										name="parts[%{#sowPartDTO.index}].productLine"
										value="%{parts[#sowPartDTO.index].productLine}" theme="simple"
								cssStyle="width: 400px;" cssClass="shadowBox grayText" />
					</tags:fieldError>	
				</td>
			</tr>
		</table>
		<table cellspacing="0">
			<tr>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.model.number" />
				</td>
				<td></td>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.serial.number" />
				</td>
			</tr>
			<tr>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Model Number" oldClass="paddingBtm">
						<s:textfield size="50" maxlength="100"
							name="parts[%{#sowPartDTO.index}].modelNumber"
							value="%{parts[#sowPartDTO.index].modelNumber}" theme="simple"
							cssStyle="width: 150px;" cssClass="shadowBox grayText" />
					</tags:fieldError>	
				</td>
				<td>
					&nbsp;&nbsp;
				</td>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Serial Number"
					oldClass="paddingBtm">
							<s:textfield size="50" maxlength="50"
						name="parts[%{#sowPartDTO.index}].serialNumber"
						value="%{parts[#sowPartDTO.index].serialNumber}" theme="simple"
						cssStyle="width: 150px;" cssClass="shadowBox grayText" />
					</tags:fieldError>	
				</td>
			</tr>
		</table>
		
		<div style="width: 550px" class="nohrText">
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.parts.part.label.info" />
		</div>
		
		<table cellspacing="0">
			<tr>

				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.order.number"/>
				</td>
				<td></td>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.purchase.order.number"/>
				</td>
				<td></td>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.label.part.status" />
				</td>
			</tr>
			<tr>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Order Number">
						<s:textfield name="parts[%{#sowPartDTO.index}].orderNumber" size="10" maxlength="100"
							 			value="%{parts[#sowPartDTO.index].orderNumber}" 
							 			theme="simple" 
							 			cssStyle="width: 90px;"
							  			cssClass="shadowBox grayText"  />
					</tags:fieldError>	
				</td>

				<td>
					&nbsp;&nbsp;
				</td>

				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> PO Number">
						<s:textfield name="parts[%{#sowPartDTO.index}].purchaseOrderNumber" size="10" maxlength="100"
							 			value="%{parts[#sowPartDTO.index].purchaseOrderNumber}" 
							 			theme="simple" 
							 			cssStyle="width: 90px;"
							  			cssClass="shadowBox grayText"  />
					</tags:fieldError>	
				</td>
				<td>
					&nbsp;&nbsp;
				</td>
				<td>
					<select name="parts[${sowPartDTO.index}].partStatusId"
								id="parts[${sowPartDTO.index}].partStatusId" style="width: 250px;">
		            	 <c:forEach var="lookupVO" items="${partStatus}" >
		            	 	<c:choose>
		             		<c:when test="${lookupVO.id == parts[sowPartDTO.index].partStatusId}">
									<option selected="selected" value="${lookupVO.id}">
			                         		${lookupVO.type}
			                  		</option>
							</c:when>	
		             		<c:otherwise>
				                    <option value="${lookupVO.id}">
				                         ${lookupVO.type}
				                  </option>
		                  </c:otherwise>
		                  </c:choose>
			             </c:forEach>
          			 </select>		    
				</td>
			</tr>
		</table>
		<table cellspacing="0">
			<tr>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}"key="wizard.label.qty" />&nbsp;&nbsp;<font color="red">*</font>
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.part.description"/>&nbsp;&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Quantity" oldClass="paddingBtm">
						<s:textfield name="parts[%{#sowPartDTO.index}].quantity"
								size="10" maxlength="10"
								value="%{parts[#sowPartDTO.index].quantity}" theme="simple"
								cssStyle="width: 30px;" cssClass="shadowBox grayText" />
					</tags:fieldError>	
				</td>
				<td>
					&nbsp;&nbsp;
				</td>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Part Description" oldClass="paddingBtm">
					<s:textarea name="parts[%{#sowPartDTO.index}].partDesc"
							 			value="%{parts[#sowPartDTO.index].partDesc}" 
							 			theme="simple"
							 			cssStyle="width: 520px;"
							  			cssClass="shadowBox grayText"  
							  			onkeydown="limitCharsTextarea(this, 500, 'partDesclimitinfo');"
						  			onkeyup="limitCharsTextarea(this, 500, 'partDesclimitinfo');"/>	
					</tags:fieldError>
					<div id="partDesclimitinfo">
						<span><i><b>500</b>characters remaining</i></span>
					</div>		
				</td>
			</tr>
		</table>
		<br/>
		<div dojoType="dijit.TitlePane"   title="More Part Information (OEM and Vendor Numbers, Part dimensions)" class="dijitTitlePaneSubTitle" open="false">
		
		<table cellspacing="0">
				<tr>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.addl.part.info"/>
					</td>
				</tr>
				<tr>
					<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Additional Part Information">
							<s:textarea name="parts[%{#sowPartDTO.index}].additionalPartInfo" 
								 			value="%{parts[#sowPartDTO.index].additionalPartInfo}" 
								 			theme="simple" 
								 			cssStyle="width: 565px;"
								  			cssClass="shadowBox grayText"
							onkeydown="limitCharsTextarea(this,1000,'additionalPartDesclimitinfo');" 
							onkeyup="limitCharsTextarea(this,1000,'additionalPartDesclimitinfo');"  
							  />
					</tags:fieldError>	
					<div id="additionalPartDesclimitinfo">
						<span><i><b>1000</b>characters remaining</i></span></td>
					</div>
				</tr>
			</table>
			<table cellspacing="0">
				<tr>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.manufacturer.part.number"/>
					</td>
					<td></td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.vendor.part.number"/>
					</td>
					<td></td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.alt.part.ref.1"/>
					</td>
					<td></td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.alt.part.ref.2"/>
					</td>
					
				</tr>
				<tr>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> OEM Number" oldClass="paddingBtm">
							<s:textfield size="50" maxlength="100" name="parts[%{#sowPartDTO.index}].manufacturerPartNumber"
										 value="%{parts[#sowPartDTO.index].manufacturerPartNumber}" 
										 theme="simple" cssStyle="width: 170px;"
										 cssClass="shadowBox grayText"  />
						</tags:fieldError>	
					</td>
					<td>
						&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Vendor Part Number" oldClass="paddingBtm">
						<s:textfield size="50" maxlength="100" name="parts[%{#sowPartDTO.index}].vendorPartNumber"
										 value="%{parts[#sowPartDTO.index].vendorPartNumber}" 
										 theme="simple" cssStyle="width: 170px;"
										 cssClass="shadowBox grayText"  />
						</tags:fieldError>	
					</td>
					<td>
						&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Alt Part Ref1" oldClass="paddingBtm">
						<s:textfield size="50" maxlength="100" name="parts[%{#sowPartDTO.index}].altPartRef1"
										 value="%{parts[#sowPartDTO.index].altPartRef1}" 
										 theme="simple" cssStyle="width: 110px;"
										 cssClass="shadowBox grayText"  />
						</tags:fieldError>	
					</td>
					<td>
						&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Alt Part Ref2" oldClass="paddingBtm">
						<s:textfield size="50" maxlength="100" name="parts[%{#sowPartDTO.index}].altPartRef2"
										 value="%{parts[#sowPartDTO.index].altPartRef2}" 
										 theme="simple" cssStyle="width: 110px;"
										 cssClass="shadowBox grayText"  />
						</tags:fieldError>	
					</td>
				</tr>
			</table>
						
			<table>
				<tr>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="wizard.label.std.measurement" />
					</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="wizard.label.length" />
					</td>
					<td>&nbsp;</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="wizard.label.width" />
					</td>
					<td>&nbsp;</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="wizard.label.height" />
					</td>
					<td>&nbsp;</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="wizard.label.weight" />
					</td>
				</tr>
				<tr>
					<td>
						<span class="formFieldOffset"> <s:radio list="measurement"
						name="parts[%{#sowPartDTO.index}].standard"
						id="parts[%{#sowPartDTO.index}].standard"
						cssClass="antiRadioOffsets" cssStyle="width: 250px;"
						value="%{parts[#sowPartDTO.index].standard}" theme="simple" />
						</span>
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Length">
							<s:textfield name="parts[%{#sowPartDTO.index}].length"
								size="10" maxlength="10"
								value="%{parts[#sowPartDTO.index].length}"
								id="parts[%{#sowPartDTO.index}].length" theme="simple"
								cssStyle="width: 75px;" cssClass="shadowBox grayText" />
						</tags:fieldError>
					</td>
					<td>
						&nbsp;&nbsp;x&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Width">
							<s:textfield name="parts[%{#sowPartDTO.index}].width"
								size="10" maxlength="10"
								value="%{parts[#sowPartDTO.index].width}"
								id="parts[%{#sowPartDTO.index}].width" theme="simple"
								cssStyle="width: 75px;" cssClass="shadowBox grayText" />
						</tags:fieldError>
					</td>
					<td>
						&nbsp;&nbsp;x&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Height">
							<s:textfield name="parts[%{#sowPartDTO.index}].height"
								size="10" maxlength="10"
								value="%{parts[#sowPartDTO.index].height}"
								id="parts[%{#sowPartDTO.index}].height" theme="simple"
								cssStyle="width: 75px;" cssClass="shadowBox grayText" />
						</tags:fieldError>
					</td>
					<td>
						&nbsp;&nbsp;|&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Weight">
							<s:textfield name="parts[%{#sowPartDTO.index}].weight"
								size="10" maxlength="10"
								value="%{parts[#sowPartDTO.index].weight}"
								id="parts[%{#sowPartDTO.index}].weight" theme="simple"
								cssStyle="width: 75px;" cssClass="shadowBox grayText" />
						</tags:fieldError>
					</td>
				</tr>
			</table>
		</div>
		<div dojoType="dijit.TitlePane"   title="Part Pick Up Information (if applicable)" class="dijitTitlePaneSubTitle" open="false">

			<div style="width: 550px">
				<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.parts.parts.pickup.text" />
			</div>
	
			<table width="350" cellpadding="0" cellspacing="0"
				style="margin-top: 0px;">
				<tr>
					<td colspan="2">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.pickup.location.name"/>
						</label>
						<br />
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.businessName" size="50" maxlength="100" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.businessName}" 
								 	 theme="simple" cssStyle="width: 342px;"
								 	 cssClass="shadowBox grayText"  />
					</td>
				</tr>
			</table>
			<table width="450" cellpadding="0" cellspacing="0">
				<tr>
					<td width="365">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.street.name"/>
						</label>
						<br />
	
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.streetName1" size="30" maxlength="30" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.streetName1}" 
								 	 theme="simple" cssStyle="width: 342px;"
								 	 cssClass="shadowBox grayText"  />							
					</td>
					<td width="85">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.apt.num"/>
						</label>
						<br />
	
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.aptNo" size="10" maxlength="10" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.aptNo}" 
								 	 theme="simple" cssStyle="width: 80px;"
								 	 cssClass="shadowBox grayText"  />						
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.streetName2" size="30" maxlength="30" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.streetName2}" 
								 	 theme="simple" cssStyle="width: 342px;"
								 	 cssClass="shadowBox grayText"  />							
					</td>
				</tr>
			</table>
			<table width="380" cellpadding="0" cellspacing="0">
				<tr>
					<td width="145">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.city"/>
						</label>
						<br />
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.city" size="30" maxlength="30" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.city}" 
								 	 theme="simple" cssStyle="width: 130px;"
								 	 cssClass="shadowBox grayText"  />						
					</td>&nbsp;&nbsp;
					<td width="100">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.state"/>
						</label>
						<br />
			              <select name="parts[${sowPartDTO.index}].pickupContactLocation.state"   id="parts[${sowPartDTO.index}].pickupContactLocation.state" style="width: 110px;">
								<option value="">Select State</option>                                     
					             <c:forEach var="lookupVO" items="${stateCodes}" >
					             	<c:choose>
					             		<c:when test="${lookupVO.type == parts[sowPartDTO.index].pickupContactLocation.state }">
												<option selected="selected" value="${lookupVO.type}">
						                         		${lookupVO.descr}
						                  		</option>
										</c:when>	
					             		<c:otherwise>
							                    <option value="${lookupVO.type}">
							                         ${lookupVO.descr}
							                  </option>
					                  </c:otherwise>
					                </c:choose>
	 				             </c:forEach>
			             </select>		             
						</td>&nbsp;&nbsp;
					<td width="40px">
					<tags:fieldError id="Part ${sowPartDTO.count} --> Zip">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.zip"/>
						</label>
						<br />
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.zip" size="10" maxlength="5" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.zip}" 
								 	 theme="simple" cssStyle="width: 40px;"
								 	 cssClass="shadowBox grayText"  />
								 	 </tags:fieldError>									
					</td>&nbsp;&nbsp;
					
					<td width="130">
					<br />
					<tags:fieldError id="Zipcode" >		
						- <s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.zip4" size="10" maxlength="4" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.zip4}" 
								 	 theme="simple" cssStyle="width: 30px;"
								 	 cssClass="shadowBox grayText"  />	
							 </tags:fieldError>						
					</td>
				</tr>
				</table>
				<table>
				<tr>
					<td colspan="2">
					
				</td>
				
				
				</tr>
	
			</table>
			<c:forEach items="${parts[sowPartDTO.index].pickupContactLocation.phones}" varStatus="sOWPhoneDTO" >
			<table width="450" cellpadding="0" cellspacing="0">
				<tr>
					<c:if test="${sOWPhoneDTO.index == 0}">
							<td width="175">
							<tags:fieldError id="Part ${sowPartDTO.count} --> phone">	
								<label><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.num"/></label>
								<br />
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].areaCode" size="3" maxlength="3" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].areaCode}" 
								 	 theme="simple" cssStyle="width: 30px;"
								 	 cssClass="shadowBox grayText"  />	
								 	 
								-
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart1" size="3" maxlength="3" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart1}" 
								 	 theme="simple" cssStyle="width: 30px;"
								 	 cssClass="shadowBox grayText"  />							
								-
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart2" size="3" maxlength="4" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart2}" 
								 	 theme="simple" cssStyle="width: 45px;"
								 	 cssClass="shadowBox grayText"  />	
							</tags:fieldError>	
							
							<td width="75">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.ext"/>
						</label>
						<br />
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].ext" size="6" maxlength="6" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].ext}" 
								 	 theme="simple" cssStyle="width: 55px;"
								 	 cssClass="shadowBox grayText"  />							
					</td>
					<td width="130">
					<tags:fieldError id="Part ${sowPartDTO.count} --> Phone Type">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.type"/>
						</label>
						<br />
						<s:select 	
					    	name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phoneClassId"
					   		headerKey="-1"
					        headerValue="Select One"
					     	cssStyle="width: 120px;" size="1"
					      	theme="simple"
							list="#session.phoneTypes"
							listKey="id"
							listValue="descr"
						/>		
					</tags:fieldError>             
					</td>
						</c:if>		
						<c:if test="${sOWPhoneDTO.index == 1}">
							<td width="175">
							<tags:fieldError id="Part ${sowPartDTO.count} --> Alternate_phone">
								<label><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.alt"/></label>
								<br />
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].areaCode" size="3" maxlength="3" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].areaCode}" 
								 	 theme="simple" cssStyle="width: 30px;"
								 	 cssClass="shadowBox grayText"  />	
								 	 
								-
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart1" size="3" maxlength="3" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart1}" 
								 	 theme="simple" cssStyle="width: 30px;"
								 	 cssClass="shadowBox grayText"  />							
								-
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart2" size="3" maxlength="4" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart2}" 
								 	 theme="simple" cssStyle="width: 45px;"
								 	 cssClass="shadowBox grayText"  />	
							</tags:fieldError>	
							</td>
							<td width="75">
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.ext"/>
							</label>
							<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].ext" size="6" maxlength="6" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].ext}" 
								 	 theme="simple" cssStyle="width: 55px;"
								 	 cssClass="shadowBox grayText"  />							
							</td>
						<td width="130">
						<tags:fieldError id="Part ${sowPartDTO.count} --> Alternate Phone Type">
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.type"/>
							</label>
							<br />
								<s:select 	
							    	name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phoneClassId"
							   		headerKey="-1"
							        headerValue="Select One"
							     	cssStyle="width: 120px;" size="1"
							      	theme="simple"
									list="#session.phoneTypes"
									listKey="id"
									listValue="descr"
								/>		
							</tags:fieldError>  
						</c:if>
				</tr>
	
	
			</table>
			</c:forEach>
			<table width="550px">
				<tr>
					<td colspan="3" align="left">
						<fmt:message bundle="${serviceliveCopyBundle}"  key="wizard.parts.contact.information" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"  key="wizard.label.firstname" />
					</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"  key="wizard.label.lastname" />
					</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.email"/>
					</td>
				</tr>
				<tr>
					<td width="183">
						<tags:fieldError id="Part ${sowPartDTO.count} --> First Name" >
							<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.firstName" size="50" maxlength="50" 
					 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.firstName}" 
					 	 theme="simple" cssStyle="width: 160px;"
						 	 cssClass="shadowBox grayText"  />
						 </tags:fieldError>
					</td>
					<td width="183">
						<tags:fieldError id="Part ${sowPartDTO.count} --> Last Name" >
							<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.lastName" size="50" maxlength="50" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.lastName}" 
								 	 theme="simple" cssStyle="width: 160px;"
								 	 cssClass="shadowBox grayText"  />
						</tags:fieldError>							
					</td>
					<td width="183">
						<tags:fieldError id="Part ${sowPartDTO.count} --> Email" >		
							<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.email" maxlength="255" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.email}" 
								 	 theme="simple" cssStyle="width: 160px;"
								 	 cssClass="shadowBox grayText"  />	
						</tags:fieldError>
					</td>
				</tr>
			</table>
	</div>

	<div dojoType="dijit.TitlePane"   title="Shipping Information (if applicable)" class="dijitTitlePaneSubTitle" open="false">
		<table>
				<tr>
					<td colspan="4" align="left">
						<fmt:message bundle="${serviceliveCopyBundle}"  key="wizard.parts.shipping.text" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"  key="wizard.parts.shipping.label.carrier" />
					</td>
					<td></td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.track.number"/>
					</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.label.shipDate"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Shipping Carrier">
							<select name="parts[${sowPartDTO.index}].shippingCarrierId"
								id="parts[${sowPartDTO.index}].shippingCarrierId" style="width: 150px;"
								onchange="checkSelectType('parts[${sowPartDTO.index}].shippingCarrierId','parts[${sowPartDTO.index}].otherShippingCarrier')">
								<option value="">
									Select Shipping
								</option>
								<c:forEach var="lookupVO" items="${shippingCarrier}">
									<c:choose>
									<c:when
										test="${lookupVO.id == parts[sowPartDTO.index].shippingCarrierId }">
										<option selected="selected" value="${lookupVO.id}">
											${lookupVO.descr}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${lookupVO.id}">
											${lookupVO.descr}
										</option>
									</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</tags:fieldError>
					</td>&nbsp;&nbsp;
					<td>
						<s:textfield name="parts[%{#sowPartDTO.index}].otherShippingCarrier"
									id="parts[%{#sowPartDTO.index}].otherShippingCarrier"
						    value="%{parts[#sowPartDTO.index].otherShippingCarrier}"
							size="30" maxlength="30" theme="simple"
							cssStyle=" width: 150px;%{parts[sowPartDTO.index].otherShippingCarrier != null && 
							                        parts[sowPartDTO.index].shippingCarrierId != null && 
							  						parts[sowPartDTO.index].otherShippingCarrier != '' ||
							  						parts[sowPartDTO.index].shippingCarrierId == '3' ? 'display:block;' : 'display:none;'}"
							cssClass="shadowBox grayText" />
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Shipping Tracking No" >
							<s:textfield name="parts[%{#sowPartDTO.index}].shippingTrackingNo" size="50" maxlength="50"
						 			value="%{parts[#sowPartDTO.index].shippingTrackingNo}" 
						 			theme="simple" 
						 			cssStyle="width: 130px;"
						  			cssClass="shadowBox grayText"  />
						 </tags:fieldError>						
					</td>&nbsp;&nbsp;
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Ship Date" >		
						<input type="text" dojoType="dijit.form.DateTextBox" style="width: 80px;"
										class="shadowBox" id="parts[<s:property value="%{#sowPartDTO.index}"/>].shipDate"
										name="parts[<s:property value="%{#sowPartDTO.index}"/>].shipDate"
										value="<s:property value="%{parts[#sowPartDTO.index].shipDate}"/>"
										constraints="{strict: 'true',datePattern:'MM-dd-yy'}" invalidMessage="* The date format is mm-dd-yy. Date field will not be saved if not corrected! "
										required="false"
										lang="en-us"
											/>			
						</tags:fieldError>
					</td>
					<td>&nbsp;</td>
				</tr>
		</table>
	</div>
	<div dojoType="dijit.TitlePane"   title="Part and Core Part Return Shipping Information (if applicable)" class="dijitTitlePaneSubTitle" open="false">

	<table>
			<tr>
				<td><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.core.return.carrier"/></td>
				<td>&nbsp;&nbsp;</td>
				<td><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.core.return.track.number"/></td>
				<td>&nbsp;&nbsp;</td>
				<td><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.core.return.track.date"/></td>
			</tr>
			<tr>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Part Return Carrier">
							<select name="parts[${sowPartDTO.index}].returnCarrierId"
								id="parts[${sowPartDTO.index}].returnCarrierId" style="width: 200px;"
								onchange="checkSelectType('parts[${sowPartDTO.index}].returnCarrierId','parts[${sowPartDTO.index}].otherReturnCarrier')">
								<option value="">
									Select Shipping
								</option>
								<c:forEach var="lookupVO" items="${shippingCarrier}">
									<c:choose>
									<c:when
										test="${lookupVO.id == parts[sowPartDTO.index].returnCarrierId }">
										<option selected="selected" value="${lookupVO.id}">
											${lookupVO.descr}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${lookupVO.id}">
											${lookupVO.descr}
										</option>
									</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</tags:fieldError>
				</td>
				<td align="left">
						<s:textfield
							name="parts[%{#sowPartDTO.index}].otherReturnCarrier" size="30"
							id="parts[%{#sowPartDTO.index}].otherReturnCarrier"
							value="%{parts[#sowPartDTO.index].otherReturnCarrier}"
							maxlength="30" theme="simple"
							cssStyle=" width: 150px;%{parts[sowPartDTO.index].otherReturnCarrier!= null &&
							                         parts[sowPartDTO.index].returnCarrierId!= null && 
							  						parts[sowPartDTO.index].otherReturnCarrier != '' ||
							  						parts[sowPartDTO.index].returnCarrierId == '3' ? 'display:block;' : 'display:none;'}"
							cssClass="shadowBox grayText"  />
				</td>
				<td>
					<s:textfield name="parts[%{#sowPartDTO.index}].returnTrackingNo"
						size="50" maxlength="50"
						value="%{parts[#sowPartDTO.index].returnTrackingNo}"
						theme="simple" cssStyle="width: 150px;"
						cssClass="shadowBox grayText" />
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>
					<input type="text" dojoType="dijit.form.DateTextBox"
							class="shadowBox"
							id="parts[<s:property value="%{#sowPartDTO.index}"/>].returnTrackDate"
							name="parts[<s:property value="%{#sowPartDTO.index}"/>]./>].returnTrackDate"
							value="<s:property value="%{parts[#sowPartDTO.index]./>].returnTrackDate}"/>"
							constraints="{strict: 'true',datePattern:'MM-dd-yy'}"
							invalidMessage="* The date format is mm-dd-yy. Date field will not be saved if not corrected! "
							required="false" lang="en-us" />
					</td>
				</tr>
			</table>
			</div>
		</div>
	</div>

	</s:iterator>
	<div id="addPartBtn" class="clearfix" style="display: none;">
	<p>
		<input type="button" id="addNewPart"
			onclick="javascript:previousButton('soWizardPartsCreate_addNewPart.action','soWizardPartsCreate','tab3');"
			class="btn20Bevel"
			style="background-image: url(${staticContextPath}/images/btn/addAnotherPart.gif); width: 116px; height: 20px;"
			src="${staticContextPath}/images/common/spacer.gif" />
	</p>
	</div>
	</div>
	</c:if>
	<c:if test="${hidepanel==1}">
			<div>
				<br />
				<br />
			</div>
			<!-- NEW NESTED MODULE - Task List -->
			<div id="PartsPanels">
				<s:iterator value="parts" status="sowPartDTO">

					<!-- NEW NESTED MODULE -->

					<div dojoType="dijit.TitlePane"
						title="Part ${sowPartDTO.count} - ${parts[sowPartDTO.index].manufacturer} - ${parts[sowPartDTO.index].modelNumber}"
						class="dijitTitlePaneSubTitle" open="${sowPartDTO.last}">
	<div style="width: 550px" class="nohrText">
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.parts.product.label.info" />
		</div>
		
		<table cellspacing="0">
			<tr>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.manufacturer"/>
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.product.label"/>
				</td>
			</tr>
			<tr>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} -->  Manufacturer" oldClass="paddingBtm">
						<s:textfield size="50" maxlength="100"
							name="parts[%{#sowPartDTO.index}].manufacturer"
							value="%{parts[#sowPartDTO.index].manufacturer}" theme="simple"
							cssStyle="width: 150px;" cssClass="shadowBox grayText" />
					</tags:fieldError>	
				</td>
				<td>
					&nbsp;&nbsp;
				</td>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Product Line" oldClass="paddingBtm">
							<s:textfield size="50" maxlength="100"
										name="parts[%{#sowPartDTO.index}].productLine"
										value="%{parts[#sowPartDTO.index].productLine}" theme="simple"
								cssStyle="width: 400px;" cssClass="shadowBox grayText" />
					</tags:fieldError>	
				</td>
			</tr>
		</table>
		<table cellspacing="0">
			<tr>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.model.number" />
				</td>
				<td></td>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.serial.number" />
				</td>
			</tr>
			<tr>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Model Number" oldClass="paddingBtm">
						<s:textfield size="50" maxlength="100"
							name="parts[%{#sowPartDTO.index}].modelNumber"
							value="%{parts[#sowPartDTO.index].modelNumber}" theme="simple"
							cssStyle="width: 150px;" cssClass="shadowBox grayText" />
					</tags:fieldError>	
				</td>
				<td>
					&nbsp;&nbsp;
				</td>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Serial Number"
					oldClass="paddingBtm">
							<s:textfield size="50" maxlength="50"
						name="parts[%{#sowPartDTO.index}].serialNumber"
						value="%{parts[#sowPartDTO.index].serialNumber}" theme="simple"
						cssStyle="width: 150px;" cssClass="shadowBox grayText" />
					</tags:fieldError>	
				</td>
			</tr>
		</table>
		
		<div style="width: 550px" class="nohrText">
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.parts.part.label.info" />
		</div>
		
		<table cellspacing="0">
			<tr>

				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.order.number"/>
				</td>
				<td></td>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.purchase.order.number"/>
				</td>
				<td></td>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.label.part.status" />
				</td>
			</tr>
			<tr>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Order Number">
						<s:textfield name="parts[%{#sowPartDTO.index}].orderNumber" size="10" maxlength="100"
							 			value="%{parts[#sowPartDTO.index].orderNumber}" 
							 			theme="simple" 
							 			cssStyle="width: 90px;"
							  			cssClass="shadowBox grayText"  />
					</tags:fieldError>	
				</td>

				<td>
					&nbsp;&nbsp;
				</td>

				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> PO Number">
						<s:textfield name="parts[%{#sowPartDTO.index}].purchaseOrderNumber" size="10" maxlength="100"
							 			value="%{parts[#sowPartDTO.index].purchaseOrderNumber}" 
							 			theme="simple" 
							 			cssStyle="width: 90px;"
							  			cssClass="shadowBox grayText"  />
					</tags:fieldError>	
				</td>
				<td>
					&nbsp;&nbsp;
				</td>
				<td>
					<select name="parts[${sowPartDTO.index}].partStatusId"
								id="parts[${sowPartDTO.index}].partStatusId" style="width: 250px;">
		            	 <c:forEach var="lookupVO" items="${partStatus}" >
		            	 	<c:choose>
		             		<c:when test="${lookupVO.id == parts[sowPartDTO.index].partStatusId}">
									<option selected="selected" value="${lookupVO.id}">
			                         		${lookupVO.type}
			                  		</option>
							</c:when>	
		             		<c:otherwise>
				                    <option value="${lookupVO.id}">
				                         ${lookupVO.type}
				                  </option>
		                  </c:otherwise>
		                  </c:choose>
			             </c:forEach>
          			 </select>		    
				</td>
			</tr>
		</table>
		<table cellspacing="0">
			<tr>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}"key="wizard.label.qty" />&nbsp;&nbsp;<font color="red">*</font>
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.part.description"/>&nbsp;&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Quantity" oldClass="paddingBtm">
						<s:textfield name="parts[%{#sowPartDTO.index}].quantity"
								size="10" maxlength="3"
								value="%{parts[#sowPartDTO.index].quantity}" theme="simple"
								cssStyle="width: 30px;" cssClass="shadowBox grayText" onchange="checkQtyValue(this)"/>
					</tags:fieldError>	
				</td>
				<td>
					&nbsp;&nbsp;
				</td>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Part Description" oldClass="paddingBtm">
					<s:textarea name="parts[%{#sowPartDTO.index}].partDesc"
							 			value="%{parts[#sowPartDTO.index].partDesc}" 
							 			theme="simple"
							 			cssStyle="width: 520px;"
							  			cssClass="shadowBox grayText"  
							  			onkeydown="limitCharsTextarea(this, 500, 'partDesclimitinfo');"
						  			onkeyup="limitCharsTextarea(this, 500, 'partDesclimitinfo');"/>	
					</tags:fieldError>	
					<div id="partDesclimitinfo">
						<span><i><b>500</b>characters remaining</i></span>
					</div>
				</td>
			</tr>
		</table>
		<br/>
		<div dojoType="dijit.TitlePane"   title="More Part Information (OEM and Vendor Numbers, Part dimensions)" class="dijitTitlePaneSubTitle" open="false">
		
			<table cellspacing="0">
				<tr>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.addl.part.info"/>
					</td>
				</tr>
				<tr>
					<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Additional Part Information">
							<s:textarea name="parts[%{#sowPartDTO.index}].additionalPartInfo" 
								 			value="%{parts[#sowPartDTO.index].additionalPartInfo}" 
								 			theme="simple" 
								 			cssStyle="width: 565px;"
								  			cssClass="shadowBox grayText"
							onkeydown="limitCharsTextarea(this,1000,'additionalPartDesclimitinfo');" 
							onkeyup="limitCharsTextarea(this,1000,'additionalPartDesclimitinfo');"  
							  />
					</tags:fieldError>	
					<div id="additionalPartDesclimitinfo">
						<span><i><b>1000</b>characters remaining</i></span></td>
					</div>
				</tr>
			</table>
			<table cellspacing="0">
				<tr>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.manufacturer.part.number"/>
					</td>
					<td></td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.vendor.part.number"/>
					</td>
					<td></td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.alt.part.ref.1"/>
					</td>
					<td></td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.alt.part.ref.2"/>
					</td>
					
				</tr>
				<tr>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> OEM Number" oldClass="paddingBtm">
							<s:textfield size="50" maxlength="100" name="parts[%{#sowPartDTO.index}].manufacturerPartNumber"
										 value="%{parts[#sowPartDTO.index].manufacturerPartNumber}" 
										 theme="simple" cssStyle="width: 170px;"
										 cssClass="shadowBox grayText"  />
						</tags:fieldError>	
					</td>
					<td>
						&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Vendor Part Number" oldClass="paddingBtm">
						<s:textfield size="50" maxlength="100" name="parts[%{#sowPartDTO.index}].vendorPartNumber"
										 value="%{parts[#sowPartDTO.index].vendorPartNumber}" 
										 theme="simple" cssStyle="width: 170px;"
										 cssClass="shadowBox grayText"  />
						</tags:fieldError>	
					</td>
					<td>
						&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Alt Part Ref1" oldClass="paddingBtm">
						<s:textfield size="50" maxlength="100" name="parts[%{#sowPartDTO.index}].altPartRef1"
										 value="%{parts[#sowPartDTO.index].altPartRef1}" 
										 theme="simple" cssStyle="width: 110px;"
										 cssClass="shadowBox grayText"  />
						</tags:fieldError>	
					</td>
					<td>
						&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Alt Part Ref2" oldClass="paddingBtm">
						<s:textfield size="50" maxlength="100" name="parts[%{#sowPartDTO.index}].altPartRef2"
										 value="%{parts[#sowPartDTO.index].altPartRef2}" 
										 theme="simple" cssStyle="width: 110px;"
										 cssClass="shadowBox grayText"  />
						</tags:fieldError>	
					</td>
				</tr>
			</table>
						
			<table>
				<tr>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="wizard.label.std.measurement" />
					</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="wizard.label.length" />
					</td>
					<td>&nbsp;</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="wizard.label.width" />
					</td>
					<td>&nbsp;</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="wizard.label.height" />
					</td>
					<td>&nbsp;</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="wizard.label.weight" />
					</td>
				</tr>
				<tr>
					<td>
						<span class="formFieldOffset"> <s:radio list="measurement"
						name="parts[%{#sowPartDTO.index}].standard"
						id="parts[%{#sowPartDTO.index}].standard"
						cssClass="antiRadioOffsets" cssStyle="width: 250px;"
						value="%{parts[#sowPartDTO.index].standard}" theme="simple" />
						</span>
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Length">
							<s:textfield name="parts[%{#sowPartDTO.index}].length"
								size="10" maxlength="10"
								value="%{parts[#sowPartDTO.index].length}"
								id="parts[%{#sowPartDTO.index}].length" theme="simple"
								cssStyle="width: 75px;" cssClass="shadowBox grayText" />
						</tags:fieldError>
					</td>
					<td>
						&nbsp;&nbsp;x&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Width">
							<s:textfield name="parts[%{#sowPartDTO.index}].width"
								size="10" maxlength="10"
								value="%{parts[#sowPartDTO.index].width}"
								id="parts[%{#sowPartDTO.index}].width" theme="simple"
								cssStyle="width: 75px;" cssClass="shadowBox grayText" />
						</tags:fieldError>
					</td>
					<td>
						&nbsp;&nbsp;x&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Height">
							<s:textfield name="parts[%{#sowPartDTO.index}].height"
								size="10" maxlength="10"
								value="%{parts[#sowPartDTO.index].height}"
								id="parts[%{#sowPartDTO.index}].height" theme="simple"
								cssStyle="width: 75px;" cssClass="shadowBox grayText" />
						</tags:fieldError>
					</td>
					<td>
						&nbsp;&nbsp;|&nbsp;&nbsp;
					</td>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Weight">
							<s:textfield name="parts[%{#sowPartDTO.index}].weight"
								size="10" maxlength="10"
								value="%{parts[#sowPartDTO.index].weight}"
								id="parts[%{#sowPartDTO.index}].weight" theme="simple"
								cssStyle="width: 75px;" cssClass="shadowBox grayText" />
						</tags:fieldError>
					</td>
				</tr>
			</table>
		</div>
		<div dojoType="dijit.TitlePane"   title="Part Pick Up Information (if applicable)" class="dijitTitlePaneSubTitle" open="false">

			<div style="width: 550px">
				<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.parts.parts.pickup.text" />
			</div>
	
			<table width="350" cellpadding="0" cellspacing="0"
				style="margin-top: 0px;">
				<tr>
					<td colspan="2">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.pickup.location.name"/>
						</label>
						<br />
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.businessName" size="50" maxlength="100" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.businessName}" 
								 	 theme="simple" cssStyle="width: 342px;"
								 	 cssClass="shadowBox grayText"  />
					</td>
				</tr>
			</table>
			<table width="450" cellpadding="0" cellspacing="0">
				<tr>
					<td width="365">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.street.name"/>
						</label>
						<br />
	
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.streetName1" size="30" maxlength="30" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.streetName1}" 
								 	 theme="simple" cssStyle="width: 342px;"
								 	 cssClass="shadowBox grayText"  />							
					</td>
					<td width="85">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.apt.num"/>
						</label>
						<br />
	
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.aptNo" size="10" maxlength="10" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.aptNo}" 
								 	 theme="simple" cssStyle="width: 80px;"
								 	 cssClass="shadowBox grayText"  />						
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.streetName2" size="30" maxlength="30" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.streetName2}" 
								 	 theme="simple" cssStyle="width: 342px;"
								 	 cssClass="shadowBox grayText"  />							
					</td>
				</tr>
			</table>
			<table width="380" cellpadding="0" cellspacing="0">
				<tr>
					<td width="145">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.city"/>
						</label>
						<br />
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.city" size="30" maxlength="30" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.city}" 
								 	 theme="simple" cssStyle="width: 130px;"
								 	 cssClass="shadowBox grayText"  />						
					</td>&nbsp;&nbsp;
					<td width="100">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.state"/>
						</label>
						<br />
			              <select name="parts[${sowPartDTO.index}].pickupContactLocation.state"   id="parts[${sowPartDTO.index}].pickupContactLocation.state" style="width: 110px;">
								<option value="">Select State</option>                                     
					             <c:forEach var="lookupVO" items="${stateCodes}" >
					             	<c:choose>
					             		<c:when test="${lookupVO.type == parts[sowPartDTO.index].pickupContactLocation.state }">
												<option selected="selected" value="${lookupVO.type}">
						                         		${lookupVO.descr}
						                  		</option>
										</c:when>	
					             		<c:otherwise>
							                    <option value="${lookupVO.type}">
							                         ${lookupVO.descr}
							                  </option>
					                  </c:otherwise>
					                 </c:choose>
	 				             </c:forEach>
			             </select>		             
						</td>&nbsp;&nbsp;
					<td width="40px">
					<tags:fieldError id="Part ${sowPartDTO.count} --> Zip">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.zip"/>
						</label>
						<br />
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.zip" size="10" maxlength="5" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.zip}" 
								 	 theme="simple" cssStyle="width: 40px;"
								 	 cssClass="shadowBox grayText"  />
								 	 </tags:fieldError>									
					</td>&nbsp;&nbsp;
					
					<td width="130">
					<br />
					<tags:fieldError id="Zipcode" >		
						- <s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.zip4" size="10" maxlength="4" 
								 	 value="%{parts[%{#attr['sowPartDTO']O.index}].pickupContactLocation.zip4}" 
								 	 theme="simple" cssStyle="width: 30px;"
								 	 cssClass="shadowBox grayText"  />	
							 </tags:fieldError>						
					</td>
				</tr>
				</table>
				<table>
				<tr>
					<td colspan="2">
					
				</td>
				
				
				</tr>
	
			</table>
			<c:forEach items="${parts[sowPartDTO.index].pickupContactLocation.phones}" varStatus="sOWPhoneDTO" >
			<table width="450" cellpadding="0" cellspacing="0">
				<tr>
					<c:if test="${sOWPhoneDTO.index == 0}">
							<td width="175">
							<tags:fieldError id="Part ${sowPartDTO.count} --> phone">	
								<label><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.num"/></label>
								<br />
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].areaCode" size="3" maxlength="3" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].areaCode}" 
								 	 theme="simple" cssStyle="width: 30px;"
								 	 cssClass="shadowBox grayText"  />	
								 	 
								-
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart1" size="3" maxlength="3" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart1}" 
								 	 theme="simple" cssStyle="width: 30px;"
								 	 cssClass="shadowBox grayText"  />							
								-
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart2" size="3" maxlength="4" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phonePart2}" 
								 	 theme="simple" cssStyle="width: 45px;"
								 	 cssClass="shadowBox grayText"  />	
							</tags:fieldError>	
							
							<td width="75">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.ext"/>
						</label>
						<br />
						<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].ext" size="6" maxlength="6" 
								 	 value="%{parts[%{#attr['sowPartDTO'].index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].ext}" 
								 	 theme="simple" cssStyle="width: 55px;"
								 	 cssClass="shadowBox grayText"  />							
					</td>
					<td width="130">
					<tags:fieldError id="Part ${sowPartDTO.count} --> Phone Type">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.type"/>
						</label>
						<br />
						<s:select 	
					    	name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr['sOWPhoneDTO'].index}].phoneClassId"
					   		headerKey="-1"
					        headerValue="Select One"
					     	cssStyle="width: 120px;" size="1"
					      	theme="simple"
							list="#session.phoneTypes"
							listKey="id"
							listValue="descr"
						/>		
					</tags:fieldError>             
					</td>
						</c:if>		
						<c:if test="${sOWPhoneDTO.index == 1}">
							<td width="175">
							<tags:fieldError id="Part ${sowPartDTO.count} --> Alternate_phone">
								<label><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.alt"/></label>
								<br />
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#sOWPhoneDTO.index}].areaCode" size="3" maxlength="3" 
								 	 value="%{parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#sOWPhoneDTO.index}].areaCode}" 
								 	 theme="simple" cssStyle="width: 30px;"
								 	 cssClass="shadowBox grayText"  />	
								 	 
								-
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#sOWPhoneDTO.index}].phonePart1" size="3" maxlength="3" 
								 	 value="%{parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#sOWPhoneDTO.index}].phonePart1}" 
								 	 theme="simple" cssStyle="width: 30px;"
								 	 cssClass="shadowBox grayText"  />							
								-
								<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#sOWPhoneDTO.index}].phonePart2" size="3" maxlength="4" 
								 	 value="%{parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#sOWPhoneDTO.index}].phonePart2}" 
								 	 theme="simple" cssStyle="width: 45px;"
								 	 cssClass="shadowBox grayText"  />	
							</tags:fieldError>	
							</td>
							<td width="75">
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.ext"/>
							</label>
							<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#sOWPhoneDTO.index}].ext" size="6" maxlength="6" 
								 	 value="%{parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#sOWPhoneDTO.index}].ext}" 
								 	 theme="simple" cssStyle="width: 55px;"
								 	 cssClass="shadowBox grayText"  />							
							</td>
						<td width="130">
						<tags:fieldError id="Part ${sowPartDTO.count} --> Alternate Phone Type">
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.phone.type"/>
							</label>
							<br />
								<s:select 	
							    	name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#sOWPhoneDTO.index}].phoneClassId"
							   		headerKey="-1"
							        headerValue="Select One"
							     	cssStyle="width: 120px;" size="1"
							      	theme="simple"
									list="#session.phoneTypes"
									listKey="id"
									listValue="descr"
								/>		
							</tags:fieldError>  
						</c:if>
				</tr>
	
	
			</table>
			</c:forEach>
			<table width="550px">
				<tr>
					<td colspan="3" align="left">
						<fmt:message bundle="${serviceliveCopyBundle}"  key="wizard.parts.contact.information" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"  key="wizard.label.firstname" />
					</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"  key="wizard.label.lastname" />
					</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.email"/>
					</td>
				</tr>
				<tr>
					<td width="183">
						<tags:fieldError id="Part ${sowPartDTO.count} --> First Name" >
							<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.firstName" size="50" maxlength="50" 
					 	 value="%{parts[%{#sowPartDTO.index}].pickupContactLocation.firstName}" 
					 	 theme="simple" cssStyle="width: 160px;"
						 	 cssClass="shadowBox grayText"  />
						 </tags:fieldError>
					</td>
					<td width="183">
						<tags:fieldError id="Part ${sowPartDTO.count} --> Last Name" >
							<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.lastName" size="50" maxlength="50" 
								 	 value="%{parts[%{#sowPartDTO.index}].pickupContactLocation.lastName}" 
								 	 theme="simple" cssStyle="width: 160px;"
								 	 cssClass="shadowBox grayText"  />
						</tags:fieldError>							
					</td>
					<td width="183">
						<tags:fieldError id="Part ${sowPartDTO.count} --> Email" >		
							<s:textfield name="parts[%{#sowPartDTO.index}].pickupContactLocation.email" maxlength="255" 
								 	 value="%{parts[%{#sowPartDTO.index}].pickupContactLocation.email}" 
								 	 theme="simple" cssStyle="width: 160px;"
								 	 cssClass="shadowBox grayText"  />	
						</tags:fieldError>
					</td>
				</tr>
			</table>
	</div>
	
	<div dojoType="dijit.TitlePane"   title="Shipping Information (if applicable)" class="dijitTitlePaneSubTitle" open="false">
		<table>
				<tr>
					<td colspan="4" align="left">
						<fmt:message bundle="${serviceliveCopyBundle}"  key="wizard.parts.shipping.text" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}"  key="wizard.parts.shipping.label.carrier" />
					</td>
					<td></td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.track.number"/>
					</td>
					<td>
						<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.label.shipDate"/>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Shipping Carrier">
							<select name="parts[${sowPartDTO.index}].shippingCarrierId"
								id="parts[${sowPartDTO.index}].shippingCarrierId" style="width: 150px;"
								onchange="checkSelectType('parts[${sowPartDTO.index}].shippingCarrierId','parts[${sowPartDTO.index}].otherShippingCarrier')">
								<option value="">
									Select Shipping
								</option>
								<c:forEach var="lookupVO" items="${shippingCarrier}">
									<c:choose>
									<c:when
										test="${lookupVO.id == parts[sowPartDTO.index].shippingCarrierId }">
										<option selected="selected" value="${lookupVO.id}">
											${lookupVO.descr}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${lookupVO.id}">
											${lookupVO.descr}
										</option>
									</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</tags:fieldError>
					</td>&nbsp;&nbsp;
					<td>
						<s:textfield name="parts[%{#sowPartDTO.index}].otherShippingCarrier"
									id="parts[%{#sowPartDTO.index}].otherShippingCarrier"
						    value="%{parts[#sowPartDTO.index].otherShippingCarrier}"
							size="30" maxlength="30" theme="simple"
							cssStyle=" width: 150px;%{parts[sowPartDTO.index].otherShippingCarrier != null && 
							                        parts[sowPartDTO.index].shippingCarrierId != null && 
							  						parts[sowPartDTO.index].otherShippingCarrier != '' ||
							  						parts[sowPartDTO.index].shippingCarrierId == '3' ? 'display:block;' : 'display:none;'}"
							cssClass="shadowBox grayText" />
					</td>&nbsp;&nbsp;
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Shipping Tracking No" >
							<s:textfield name="parts[%{#sowPartDTO.index}].shippingTrackingNo" size="50" maxlength="50"
						 			value="%{parts[#sowPartDTO.index].shippingTrackingNo}" 
						 			theme="simple" 
						 			cssStyle="width: 130px;"
						  			cssClass="shadowBox grayText"  />
						 </tags:fieldError>							
					</td>&nbsp;&nbsp;
					<td>
						<tags:fieldError id="Part ${sowPartDTO.count} --> Ship Date" >		
						<input type="text" dojoType="dijit.form.DateTextBox" style="width: 80px;"
										class="shadowBox" id="parts[<s:property value="%{#sowPartDTO.index}"/>].shipDate"
										name="parts[<s:property value="%{#sowPartDTO.index}"/>].shipDate"
										value="<s:property value="%{parts[#sowPartDTO.index].shipDate}"/>"
										constraints="{strict: 'true',datePattern:'MM-dd-yy'}" invalidMessage="* The date format is mm-dd-yy. Date field will not be saved if not corrected! "
										required="false"
										lang="en-us"
											/>			
						</tags:fieldError>
					</td>
					<td>&nbsp;</td>
				</tr>
		</table>
	</div>
	<div dojoType="dijit.TitlePane"   title="Part and Core Part Return Shipping Information (if applicable)" class="dijitTitlePaneSubTitle" open="false">
	<table>
			<tr>
				<td><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.core.return.carrier"/></td>
				<td>&nbsp;&nbsp;</td>
				<td><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.core.return.track.number"/></td>
				<td>&nbsp;&nbsp;</td>
				<td><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.core.return.track.date"/></td>
			</tr>
			<tr>
				<td>
					<tags:fieldError id="Part ${sowPartDTO.count} --> Part Return Carrier">
							<select name="parts[${sowPartDTO.index}].returnCarrierId"
								id="parts[${sowPartDTO.index}].returnCarrierId" style="width: 200px;"
								onchange="checkSelectType('parts[${sowPartDTO.index}].returnCarrierId','parts[${sowPartDTO.index}].otherReturnCarrier')">
								<option value="">
									Select Shipping
								</option>
								<c:forEach var="lookupVO" items="${shippingCarrier}">
									<c:choose>
									<c:when
										test="${lookupVO.id == parts[sowPartDTO.index].returnCarrierId }">
										<option selected="selected" value="${lookupVO.id}">
											${lookupVO.descr}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${lookupVO.id}">
											${lookupVO.descr}
										</option>
									</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</tags:fieldError>
				</td>
				<td align="left">
						<s:textfield
							name="parts[%{#sowPartDTO.index}].otherReturnCarrier" size="30"
							id="parts[%{#sowPartDTO.index}].otherReturnCarrier"
							value="%{parts[#sowPartDTO.index].otherReturnCarrier}"
							maxlength="30" theme="simple"
							cssStyle=" width: 150px;%{parts[sowPartDTO.index].otherReturnCarrier!= null &&
							                         parts[sowPartDTO.index].returnCarrierId!= null && 
							  						parts[sowPartDTO.index].otherReturnCarrier != '' ||
							  						parts[sowPartDTO.index].returnCarrierId == '3' ? 'display:block;' : 'display:none;'}"
							cssClass="shadowBox grayText"  />
				</td>
				<td>
					<s:textfield name="parts[%{#sowPartDTO.index}].returnTrackingNo"
						size="50" maxlength="50"
						value="%{parts[#sowPartDTO.index].returnTrackingNo}"
						theme="simple" cssStyle="width: 150px;"
						cssClass="shadowBox grayText" />
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>
					<input type="text" dojoType="dijit.form.DateTextBox"
							class="shadowBox"
							id="parts[<s:property value="%{#sowPartDTO.index}"/>].returnTrackDate"
							name="parts[<s:property value="%{#sowPartDTO.index}"/>]./>].returnTrackDate"
							value="<s:property value="%{parts[#sowPartDTO.index]./>].returnTrackDate}"/>"
							constraints="{strict: 'true',datePattern:'MM-dd-yy'}"
							invalidMessage="* The date format is mm-dd-yy. Date field will not be saved if not corrected! "
							required="false" lang="en-us" />
				</td>
			</tr>
		</table>
	</div>
	</div>
	</div>

				</s:iterator>
				<div id="addPartBtn" class="clearfix">
				<p>
					<input type="button" id="addNewPart"
						onclick="javascript:previousButton('soWizardPartsCreate_addNewPart.action','soWizardPartsCreate','tab3');"
						class="btn20Bevel"
						style="background-image: url(${staticContextPath}/images/btn/addAnotherPart.gif); width: 116px; height: 20px;"
						src="${staticContextPath}/images/common/spacer.gif" />
				</p>
			</div>
			</div>
		</c:if>
	</div>
</s:iterator>
