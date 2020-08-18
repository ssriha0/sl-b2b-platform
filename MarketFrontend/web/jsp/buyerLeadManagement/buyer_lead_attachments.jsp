<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<div id="attachmentLoadingLogo" style="display: block;">
<img src="${staticContextPath}/images/loading.gif" width="442px" height="285px" style="position:relative;top:0px;left:-136px;" >
</div> 
<div id="attachmentDiv" class="contentPane" style="display:none;">
    <br>
	<p class="viewNotesInfo">
	ServiceLive keeps a record of all the documents that are uploaded into the system relevant to each lead. Use Lead attachments to view all documents and photos related to your lead.
	</p>
    <br>
	
	<form method="POST" id="attachmentForm">
	  <div class="viewNotesTable">
		    <table class="leadTableLook" cellpadding="0" cellspacing="0" style="margin: 0; table-layout: fixed; width:100%;">
			<tr>
				<th class="col1 first odd" width="34%">
					File Name
				</th>
				<th class="col2 even" width="33%">
					Uploaded by
				</th>
				<th  class="col3 odd" width="33%">
					Uploaded Date & Time
				</th>
			</tr>
			<c:forEach items="${attachments}" var="attachment"> 
				<tr>
					<td class="col1 first odd"  width="34%" style="word-wrap:break-word;">
						${attachment.fileName }(${attachment.docSize } <span style="color:#00A0D2;">Kb</span>)
						<u onclick="viewDoc('${attachment.documentId}','${attachment.docCategoryId}','${attachment.docPath}');"
							id="viewDoc" style="color:#00A0D2;float:right;cursor: pointer">
							View&nbsp;
						</u>
					</td>
					<td  class="col2 even" width="33%" style="word-wrap:break-word;">
						${attachment.createdBy }
						<br>${attachment.companyName }(ID ${attachment.vendorId })
					</td>
					<td  class="col3 odd" width="33%" style="word-wrap:break-word;">
						<fmt:formatDate value="${attachment.createdDate }" type="both"
								dateStyle="medium" timeStyle="medium" />
					</td>
				</tr>
			</c:forEach>	
			<c:if test="${empty attachments}"> 
	   		<tr>
		   		<td class="col1 first odd" colspan="3"  width="100%" >
		   			<p>No attachment Found.</p>
		   		</td>
	   		</tr>
	   	</c:if>	
		</table>
		
	</div>
	</form>
	
</div>

