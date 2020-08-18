<jsp:include page="../../layout/common/commonIncludes.jsp"/>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<div dojoType="ContentPane" layoutAlign="left" style="width: 210px; height: 550px;" >
		
	<div dojoType="TitlePane" label="Search Service Orders" 
							  labelNodeClass="label" containerNodeClass="content" open="false">
		<s:form>
						<table>
							<tr>
								<td>
									Search By:<br>
									<select name="searchByDropdown" id ="searchByDropdown">
										<c:forEach var="option" items="${searchByOptions}">
											<option value="${option.value}">${option.label}</option>
										</c:forEach>						
									</select>
								</td>
							</tr>
							<tr>
								<td>
									Keyword:<br>
									<input type="text" name="searchByKeyword" id ="searchByKeyword"/>
								</td>
							</tr>
							<tr>
								<td>					
									<button style="font-family: sans-serif; font-size: 9px"  onclick="searchTab(searchByDropdown.value, searchByKeyword.value)">Submit</button><br>
								</td>
							</tr>
						</table>
		</s:form>
	</div>
	<div dojoType="TitlePane" label="Order Express Menu" id="orderExpress" labelNodeClass="label" containerNodeClass="content" open="false">
							
 <span id="orderNo" class="liveWidgetInnerdiv">Service Order #: </span>
      <br><span id="title" class="liveWidgetInnerdiv">Title: </span></br>
      <br><span id="status" class="liveWidgetInnerdiv">  Staus: </span></br>
      <br><span id="spendLimit" class="liveWidgetInnerdiv">Maximum Price : </span></br>
      <br><span id="providerName" class="liveWidgetInnerdiv">Provider Name : </span></br>
      <br><span id="buyerName" class="liveWidgetInnerdiv">Buyer Name: </span></br>
	</div>
	
	
	<div dojoType="TitlePane" label="Add Note" id="addNote"
							  labelNodeClass="label" containerNodeClass="content" open="false">
		<s:form>
			<span class="liveWidgetInnerdiv">Subject: </span>
			<input type="text" size="20" style="font-size: 9px;width: 150px;">
			<br><span class="liveWidgetInnerdiv">Message: </span>
			<s:textarea rows="1" cols="20"></s:textarea>
			</br>	
			<a href="${contextPath}/showMonitor.action?listType=Draft">Advanced</a>		
			<br>
			<a href="${contextPath}/showMonitor.action?listType=Draft">Annotation Options</a>
			&nbsp;&nbsp;
			<button style="font-family: sans-serif; font-size: 9px">Submit</button><br>
		</s:form>
	</div>
		
	<div dojoType="TitlePane" label="Increase Maximum Price" id="spendLimit"
							  labelNodeClass="label" containerNodeClass="content" open="false">

						<table border=0 width="100%">
							<tr>
								<td>
									Increase Maximum Price  ?
								</td>
							</tr>
							<tr>
								<td>
									<b>Current Maximum Price:</b> $105.00
								</td>
							</tr>
							<tr>
								<td>
									Add $ <input type="text"/>
								</td>
							</tr>
							<tr>
								<td>
									<b>New Maximum Price:</b>
								</td>
							</tr>
							<tr>
								<td>
									<b>Reason:</b>
								</td>
							</tr>
							<tr>
								<td>
									<textarea name="comment" rows="1" ></textarea>
								</td>
							</tr>
							<tr>
								<td align="center">
									<input type="button" value="Calculate" />
									<input type="button" value="Submit" />
								</td>
							</tr>
							<tr>
								<td>
									Advanced Financial Options
								</td>
							</tr>							
						</table>

							
	</div>
	
	<div dojoType="TitlePane" label="Attach Document" id="attachDoc"
							  labelNodeClass="label" containerNodeClass="content" open="false">
		<s:form action="doUpload" enctype="multipart/form-data" method="POST">
			<span class="liveWidgetInnerdiv">Documents:</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- nput type="file" name="upload" value="Browse File A ..." /-->
			<s:file name="upload" label="File" theme="simple" />
			<br><span class="liveWidgetInnerdiv">Windows2.jpg</span></br>
			<button style="font-family: sans-serif; font-size: 9px">View</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button style="font-family: sans-serif; font-size: 9px">Delete</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<s:submit label="Upload"/>			
		</s:form>
	</div> 
	
</div>
