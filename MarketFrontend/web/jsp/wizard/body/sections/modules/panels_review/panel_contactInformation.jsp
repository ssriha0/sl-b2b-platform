<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
<%@ taglib uri="/struts-tags" prefix="s"%>


<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="Contact Information"
	class="contentWellPane">
	<p>
		All the contact information is outlined below. The service location contact information includes the address for the job site. The primary contacts for the provider will be the service location contact and the buyer unless alternate contacts have been provided. 
	</p>
	<table cellpadding="0" cellspacing="0"> <!-- Contact Table -->
		<tr>
			<td width="50%" class="contactTableCell" style="padding-left: 5px">  <!-- Service Location Contact Information Cell -->
				<p style="padding-top: 0px;">
					<strong>Service Location Contact Information<c:if test="${reviewDTO.shareContactInd}"> <span class="locationAsterisks">***</span></c:if></strong>
					<s:if test="%{reviewDTO.providerContact == null}">
					 <br />[Information Not Entered by User]<br><br><br><br />
					</s:if>
					<br />
					<s:else>
					 <s:if test="%{reviewDTO.providerContact.individualName!=null}">
					${reviewDTO.providerContact.individualName}<br />
					 </s:if>
						<s:if test="%{reviewDTO.providerContact.streetAddress!=null}">
					${reviewDTO.providerContact.streetAddress}<br />
					</s:if> 
					
					<s:if test="%{reviewDTO.providerContact.streetAddress2!=null}">
                    ${reviewDTO.providerContact.streetAddress2}<br />
                    </s:if> 
				    ${reviewDTO.providerContact.cityStateZip}<br />
					</s:else>
						   
				    
				</p>
				<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">  <!-- Service Location Contact Information Phone -->
					<tr>
						<td width="100" class="column1">
							<s:if test="%{reviewDTO.providerContact != null}">
								<s:if test="%{reviewDTO.locationContact.phoneWork!=null}">
									<strong>Work Phone</strong>
									<br />
								</s:if>
								<s:if test="%{reviewDTO.providerContact.phoneMobile!= null}">
									<strong>Mobile Phone</strong>
									<br />
								</s:if>
								<s:if test="%{reviewDTO.providerContact.phoneHome!= null}">
									<strong>Home Phone</strong>
									<br />
								</s:if>
								<s:if test="%{reviewDTO.providerContact.pager!= null}">
									<strong>Pager</strong>
									<br />
								</s:if>
								<s:if test="%{reviewDTO.providerContact.other!= null}">
									<strong>other</strong>
									<br />
								</s:if>
								<s:if test="%{reviewDTO.providerContact.fax!= null}">
									<strong>Fax</strong>
									<br />
								</s:if>
								<s:if test="%{reviewDTO.providerContact.email!= null}">
								<strong>E-mail<br /></strong>
								</s:if>
							</s:if>
						</td>
					
					<td class="column2" width="200">
						<s:if test="%{reviewDTO.providerContact != null}">
							<s:if test="%{reviewDTO.locationContact.phoneWork!=null}">
								${reviewDTO.providerContact.phoneWork}<br />
							</s:if>
							<s:if test="%{reviewDTO.providerContact.phoneMobile!= null}">
								${reviewDTO.providerContact.phoneMobile}<br />
							</s:if>
							<s:if test="%{reviewDTO.providerContact.phoneHome!= null}">
								${reviewDTO.providerContact.phoneHome}<br />
							</s:if>
							<s:if test="%{reviewDTO.providerContact.pager!= null}">
							${reviewDTO.providerContact.pager}<br />
							</s:if>
							<s:if test="%{reviewDTO.providerContact.other!= null}">
							${reviewDTO.providerContact.other}<br />
							</s:if>
							<s:if test="%{reviewDTO.providerContact.fax!= null}">
							${reviewDTO.providerContact.fax}<br />
							</s:if>
							<s:if test="%{reviewDTO.providerContact.email!= null}">
							${reviewDTO.providerContact.email}<br />
							</s:if>
						</s:if>
					</td>
					</tr>
				</table>  <!-- Service Location Contact Information Phone -->
			</td>  <!-- Service Location Contact Information Cell -->
			<td>&nbsp;&nbsp;&nbsp;</td>
			<!-- column 2 -->
			<td width="50%" class="contactTableCell" style="padding-left: 5px">  <!-- Service Location Alternate Contact Cell -->
				<p style="padding-top: 0px;">
					<strong>Service Location Alternate Contact<c:if test="${reviewDTO.shareContactInd}"> <span class="locationAsterisks">***</span></c:if></strong>
					<s:if test="%{reviewDTO.locationAlternateContact == null}">
		         	  <br />[Information Not Selected by User]<br />
					 </s:if>
			      <s:else>
					<br/>
					${reviewDTO.locationAlternateContact.individualName}
					<br/>
				  </s:else>	
				  <br>
				  <br>
				  <br>
				</p>
				
				<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding"> <!-- Service Location Alternate Contact Phone -->
				  	<tr>		    
				 <s:if test="%{reviewDTO.locationAlternateContact!= null}">
						<td class="column2">
							<s:if
								test="%{reviewDTO.locationAlternateContact.phoneWork!=null}">
								<strong>WorkPhone <br />
								</strong>
							</s:if>
							<s:if
								test="%{reviewDTO.locationAlternateContact.phoneMobile!= null}">
								<strong>MobilePhone <br />
								</strong>
							</s:if>
							<s:if
								test="%{reviewDTO.locationAlternateContact.phoneHome!= null}">
								<strong>HomePhone <br />
								</strong>
							</s:if>
							<s:if test="%{reviewDTO.locationAlternateContact.pager!= null}">
								<strong>Pager <br />
								</strong>
							</s:if>
							<s:if test="%{reviewDTO.locationAlternateContact.other!= null}">
								<strong>other <br />
								</strong>
							</s:if>
							<s:if test="%{reviewDTO.locationAlternateContact.fax!= null}">
								<strong>Fax <br />
								</strong>
							</s:if>
								<s:if test="%{reviewDTO.locationAlternateContact.email!= null}">
							<strong>E-mail<br />
							</strong>
								</s:if>
						</td>
						<td class="column3">
							<s:if
								test="%{reviewDTO.locationAlternateContact.phoneWork!=null}">	
			                	${reviewDTO.locationAlternateContact.phoneWork}<br />
							</s:if>
							<s:if
								test="%{reviewDTO.locationAlternateContact.phoneMobile!= null}">	
				             	${reviewDTO.locationAlternateContact.phoneMobile}<br />
							</s:if>
							<s:if
								test="%{reviewDTO.locationAlternateContact.phoneHome!= null}">	
					             ${reviewDTO.locationAlternateContact.phoneHome}<br />
							</s:if>
							<s:if test="%{reviewDTO.locationAlternateContact.pager!= null}">	
				                ${reviewDTO.locationAlternateContact.pager}<br />
							</s:if>
							<s:if test="%{reviewDTO.locationAlternateContact.other!= null}">	
				            	${reviewDTO.locationAlternateContact.other}<br />
							</s:if>
							<s:if test="%{reviewDTO.locationAlternateContact.fax!= null}">	
				            	${reviewDTO.locationAlternateContact.fax}<br />
							</s:if>
					    		<s:if test="%{reviewDTO.locationAlternateContact.email!= null}">
					    		${reviewDTO.locationAlternateContact.email}<br />
					    		</s:if>
						</td>
					</s:if>
					</tr>			
				</table>  <!-- Service Location Alternate Contact Phone -->
			</td>  <!-- Service Location Alternate Contact Cell -->
		</tr>
		<tr>
			<td colspan="3"><p style="padding-bottom: 20px"><strong><c:if test="${reviewDTO.shareContactInd}"><span class="locationAsterisks">***</span><span> You've chosen to share this contact information with the providers you are sending this service order to.</span></c:if></strong></p></td>
		</tr>
		<tr>
			<td class="contactTableCell" style="padding-left: 5px">  <!-- Buyer Contact Information Cell -->
				<p style="padding-top: 0px;">
					<strong>Buyer Contact Information</strong>
					<br />
					${reviewDTO.buyerContact.individualName}&nbsp;(User Id#&nbsp;${reviewDTO.buyerContact.individualID})
					<br />
					 ${reviewDTO.buyerContact.companyName}<s:if test="%{reviewDTO.companyID != null}">	
					 (ID#&nbsp;${reviewDTO.companyID})</s:if> 
					<br />
					<s:if test="%{reviewDTO.buyerContact.streetAddress != null}">	
					${reviewDTO.buyerContact.streetAddress}<br /> 
					 </s:if>
					<s:if test="%{reviewDTO.buyerContact.streetAddress2 != null}">
                    ${reviewDTO.buyerContact.streetAddress2}<br />	
                    </s:if>
					${reviewDTO.buyerContact.cityStateZip}
				</p>
				<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">  <!-- Buyer Contact Information Phone -->
					<tr>
					<s:if test="%{reviewDTO.buyerContact != null}">					
				    	<td class="column2">
					    <s:if test="%{reviewDTO.buyerContact.phoneWork!=null}">	
			          	<strong>WorkPhone <br /></strong>
					    </s:if>
                        <s:if test="%{reviewDTO.buyerContact.phoneMobile!= null}">	
						<strong>MobilePhone <br /></strong>
					    </s:if>
                        <s:if test="%{reviewDTO.buyerContact.phoneHome!= null}">	
						<strong>HomePhone <br /></strong>
					    </s:if>
                        <s:if test="%{reviewDTO.buyerContact.pager!= null}">	
				    	<strong>Pager <br /></strong>
					   </s:if>
                       <s:if test="%{reviewDTO.buyerContact.other!= null}">	
						<strong>other <br /></strong>
					   </s:if>
					   <s:if test="%{reviewDTO.buyerContact.phonePrimary!= null}">	
						<strong>WorkPhone <br /></strong>
					   </s:if><s:if test="%{reviewDTO.buyerContact.phoneAlternate!= null}">	
						<strong>MobilePhone <br /></strong>
					   </s:if>
                      <s:if test="%{reviewDTO.buyerContact.fax!= null}">	
						<strong>Fax <br /></strong>
					  </s:if>
                      <s:if test="%{reviewDTO.buyerContact.email!= null}">
                       <strong>E-mail<br /></strong>
                       </s:if>
                     </td> 
                <td class="column3">
                    <s:if test="%{reviewDTO.buyerContact.phoneWork!=null}">	
			     	${reviewDTO.buyerContact.phoneWork}<br />
					</s:if>
                    <s:if test="%{reviewDTO.buyerContact.phoneMobile!= null}">	
					${reviewDTO.buyerContact.phoneMobile}<br />
					</s:if>
                    <s:if test="%{reviewDTO.buyerContact.phoneHome!= null}">	
					${reviewDTO.buyerContact.phoneHome}<br />
					</s:if>
                    <s:if test="%{reviewDTO.buyerContact.pager!= null}">	
					${reviewDTO.buyerContact.pager}<br />
					</s:if>
                    <s:if test="%{reviewDTO.buyerContact.other!= null}">	
					${reviewDTO.buyerContact.other}<br />
				    </s:if>
				     <s:if test="%{reviewDTO.buyerContact.phonePrimary!= null}">	
					${reviewDTO.buyerContact.phonePrimary}<br />
				    </s:if>
				     <s:if test="%{reviewDTO.buyerContact.phoneAlternate!= null}">	
					${reviewDTO.buyerContact.phoneAlternate}<br />
				    </s:if>
                    <s:if test="%{reviewDTO.buyerContact.fax!= null}">	
				   	${reviewDTO.buyerContact.fax}<br />
					</s:if>
					<s:if test="%{reviewDTO.buyerContact.email!= null}">
                     ${reviewDTO.buyerContact.email}<br />
					 </s:if>
				  </td>	
				</s:if>
				</tr>
			</table>  <!-- Buyer Contact Information Phone -->
            </td>  <!-- Buyer Contact Information Cell -->
            <td></td>
            <td class="contactTableCell" style="padding-left: 5px">  <!-- Buyer Alternate Contact Information Cell -->
				<p style="padding-top: 0px;">
					<strong>Buyer Alternate Contact</strong>
					<s:if test="%{reviewDTO.buyerSupportContact == null}">
					   <br />[Information Not Selected by User]<br><br><br><br />
					</s:if>
					<s:else>
						<br />
					${reviewDTO.buyerSupportContact.individualName}
					<br />
					<s:if test="%{reviewDTO.buyerSupportContact.streetAddress!=null}">
					${reviewDTO.buyerSupportContact.streetAddress}<br /> 
					</s:if>
					
					<s:if test="%{reviewDTO.buyerSupportContact.streetAddress2!=null}">
                    ${reviewDTO.buyerSupportContact.streetAddress2}<br />	
                    </s:if>
					${reviewDTO.buyerSupportContact.cityStateZip}
					</s:else>
					<br />
				</p>

				<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">  <!-- Buyer Alternate Contact Phone -->
					<tr>
					<s:if test="%{reviewDTO.buyerSupportContact!= null}">
						<td class="column2">
							<s:if test="%{reviewDTO.buyerSupportContact.phoneWork!=null}">
								<strong>WorkPhone <br /> </strong>
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.phoneMobile!= null}">
								<strong>MobilePhone <br /> </strong>
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.phoneHome!= null}">
								<strong>HomePhone <br /> </strong>
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.phoneAlternate!= null}">
								<strong>Phone <br /> </strong>
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.pager!= null}">
								<strong>Pager <br /> </strong>
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.other!= null}">
								<strong>other <br /> </strong>
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.fax!= null}">
								<strong>Fax <br /> </strong>
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.email!= null}">
							<strong>E-mail<br /> </strong>
							</s:if>
						</td>
						<td class="column3">
							<s:if test="%{reviewDTO.buyerSupportContact.phoneWork!=null}">	
			                	${reviewDTO.buyerSupportContact.phoneWork}<br />
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.phoneMobile!= null}">	
				             	${reviewDTO.buyerSupportContact.phoneMobile}<br />
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.phoneHome!= null}">	
					             ${reviewDTO.buyerSupportContact.phoneHome}<br />
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.phoneAlternate!= null}">	
					             ${reviewDTO.buyerSupportContact.phoneAlternate}<br />
							</s:if>							
							<s:if test="%{reviewDTO.buyerSupportContact.pager!= null}">	
				                ${reviewDTO.buyerSupportContact.pager}<br />
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.other!= null}">	
				            	${reviewDTO.locationAlternateContact.other}<br />
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.fax!= null}">	
				            	${reviewDTO.buyerSupportContact.fax}<br />
							</s:if>
							<s:if test="%{reviewDTO.buyerSupportContact.email!= null}">
							${reviewDTO.buyerSupportContact.email}
							</s:if>
							<br />
						</td>
					</s:if>
					</tr>
			    </table>  <!-- Buyer Alternate Contact Phone -->
		    </td>  <!-- Buyer Alternate Contact Information Cell -->
		</tr>
	</table>
</div>
