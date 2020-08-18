<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="partsInd" value="<%=request.getAttribute("partsIndicatorSOW")%>" />

<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="Parts" class="contentWellPane">
	<p>
	
		<c:choose>
		<c:when test="${partsInd==0}">
			Buyer has not specified parts for this service order. Either parts are not required or provider will supply them.
		</c:when>	
		<c:otherwise>			
			Please note the parts information below. Detailed pick-up location
			information is included if pick-up is required.
		</c:otherwise>
		</c:choose>
	</p>
	<div><br/><br/></div>
	<!-- NEW NESTED MODULE -->
	<c:forEach var="part" items="${reviewDTO.partsList}">
	<div dojoType="dijit.TitlePane" title="${part.title}" id=""
		class="dijitTitlePaneSubTitle" open="false">
		<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding" border=0>
			<tr>
				<td width="30%"> 
					<strong>Manufacturer&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td width="70%">
					<div style="overflow:auto">
						${part.manufacturer}<br>
					</div>
				</td>
			</tr>
			<tr>
				<td width="30%">
					<strong>Model Number&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td width="70%">
					<div style="overflow:auto">
						${part.modelNumber}<br>
					</div>
				</td>
			</tr>
			<tr>
			<td width="30%">
					<strong>Serial Number&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td width="70%">
					<div style="overflow:auto">
						${part.serialNumber}<br>
					</div>
				</td>
			</tr>
			<tr>
			<td width="30%">
					<strong>Manufacturer OEM Part Number&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td width="70%">
					<div style="overflow:auto">
						${part.manufacturerPartNumber}<br>
					</div>
				</td>
			</tr>
			<tr>
			<td width="30%">
					<strong>Vendor Part Number&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td width="70%">
					<div style="overflow:auto">
						${part.vendorPartNumber}<br>
					</div>
				</td>
			</tr>
			<tr>
			<td width="30%">
					<strong>Part Type&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td width="70%">
					<div style="overflow:auto">
						${part.partType}<br>
					</div>
				</td>
			</tr>
			<tr>
				<td width="30%">
					<strong>Qty&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td width="70%">
					${part.qty}<br>
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td width="30%">
					<strong>Order Number&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td width="70%">
					${part.orderNumber}<br>
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td width="30%">
					<strong>Purchase Order Number&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td width="70%">
					${part.purchaseOrderNumber}<br>
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<strong>Part Status&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td>
		          <c:forEach var="lookupVO" items="${partStatus}" >
		             		<c:if test="${lookupVO.id == part.partStatusId }">
									 ${lookupVO.type}
			                                	
							</c:if>	
		           </c:forEach>
				</td>
			</tr>
			<tr>
				<td width="30%">
					<strong>Size&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td width="70%">
					${part.size}<br>
				</td>
			</tr>
			
			<tr>			
				<td width="30%">
					<strong>Weight&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td width="70%">
					${part.weight}<br>
				</td>
			</tr>
			
		</table>
		<br><br>
		
		<strong>Description</strong><br>
		
			${part.description}<br><br>
		<br></br><br></br>	
		<strong>Additional Part Info</strong><br>
		
			${part.additionalPartInfo}<br><br>
		
	
		<strong>Shipping Information</strong>
		<hr class="noSpace" />
		<table cellpadding="0" cellspacing="0" >

			<tr>
				<td>
					<strong>Shipping Carrier&nbsp;&nbsp;&nbsp;</strong>
				</td>
				<td>
				          <c:forEach var="lookupVO" items="${shippingCarrier}" >
				             		<c:if test="${lookupVO.id == part.shippingCarrierId }">
											 ${lookupVO.descr}
					                                	
									</c:if>	
				           </c:forEach>
				           <c:if test="${part.otherShippingCarrier!=null}">
								${part.otherShippingCarrier}
							</c:if>
				</td>
			</tr>
			<tr>
				<td>
					<strong>Shipping Tracking Number&nbsp;&nbsp;</strong>
				</td>
				<td>
					${part.shippingTrackingNumber}
				</td>
			</tr>
			<tr>
				<td width="200">
					<strong>Shipping Date</strong>
				</td>
				<td>
					${part.shipDateFormatted}
				</td>
			</tr>
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>


			<tr>
				<td>
					<strong>Core Return Carrier&nbsp;&nbsp;</strong>
				</td>
				
				<td >
				       <c:forEach var="lookupVO" items="${shippingCarrier}" >
				             		<c:if test="${lookupVO.id == part.coreReturnCarrierId }">
											 ${lookupVO.descr}
					                  	
									</c:if>	
				           </c:forEach>
				           <c:if test="${part.otherCoreReturnCarrier!=null}">
							${part.otherCoreReturnCarrier}
						</c:if>
				
				
				</td>
			</tr>
			<tr>
				<td>
					<strong>Core Return Tracking Number&nbsp;&nbsp;</strong>
				</td>
				<td>
					${part.coreReturnTrackingNumber}
				</td>
			</tr>
			<tr>
				<td width="200">
					<strong>Core Return Date</strong>
				</td>
				<td>
					${part.returnTrackDateFormatted}
				</td>
			</tr>
		</table>
		<br><br>
		<p>
			<strong>Pick-up/Merchandise Location Information</strong>
		</p>
		<hr class="noSpace" />
		<table cellpadding="0" cellspacing="0" class="noMargin">
			<tr>
				<td  class="column1">
					<p>
						<c:if test="${(null == part.contact.streetAddress || '' == part.contact.streetAddress) && (null == part.contact.streetAddress2 || '' == part.contact.streetAddress2) && (null == part.contact.cityStateZip || '' == part.contact.cityStateZip)}">
							Product At Job Site
						</c:if>
					    <c:if test="${part.contact.individualName!=null}">
						${part.contact.individualName}
						<br />
						</c:if>
						<c:if test="${part.contact.companyName!=null}">
						${part.contact.companyName}
						<br />
						</c:if>
						<c:if test="${part.contact.streetAddress!=null}">
						${part.contact.streetAddress}
						<br />
						</c:if>
						<c:if test="${part.contact.streetAddress2!=null}">
						${part.contact.streetAddress2}
						<br />
						</c:if>
					    <c:if test="${part.contact.cityStateZip!=null}">
						${part.contact.cityStateZip}
						</c:if>
					</p>
				</td>
				<td width="15">
				</td>	   
			  <td class="column2">
                    <p>
                   	<c:if test="${part.contact.phoneWork!=null}">
						<strong>Work Phone</strong><br />
					</c:if>
					
					<c:if test="${part.contact.phoneMobile!= null}">
						<strong>Mobile Phone</strong><br />
					</c:if>
					
					<c:if test="${part.contact.phoneHome!= null}">
						<strong>HomePhone</strong><br />
					</c:if>
					
					<c:if test="${part.contact.pager!= null}">
						<strong>Pager</strong><br />
					</c:if>
					
					<c:if test="${part.contact.other!= null}">
						<strong>Other</strong><br />
					</c:if>
		           </p>
		          <td width="15">
				 </td>  
		            
		          <td class="column3" >     
                   <p>
					<c:if test="${part.contact.phoneWork!=null}">
						${part.contact.phoneWork}<br />
					</c:if>
					
					<c:if test="${part.contact.phoneMobile!= null}">
					    ${part.contact.phoneMobile}<br />
					</c:if>
					
					<c:if test="${part.contact.phoneHome!= null}">
						${part.contact.phoneHome}<br />
					</c:if>
					
					<c:if test="${part.contact.pager!= null}">
						${part.contact.pager}<br />
					</c:if>
					
					<c:if test="${part.contact.other!= null}">
						${part.contact.other}<br />
					</c:if>
				        
		            </p>
		            
				</td> 
		     				
				<td>
					
					<br />
					<br />
					<!--  <span class="mapThis" onmouseout="popUp(event,'mapThis')"
						onmouseover="popUp(event,'mapThis')"><img
							src="${staticContextPath}/images/icons/mapThis.gif"
							alt="Map This Location" class="inlineBtn" /> </span>-->
				</td>
			
		</table>
		   
			</div>
	</c:forEach>
		
</div>
