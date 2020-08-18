<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<h4><strong>${spnMonitorVO.spnName}</strong></h4><br/>
${spnMonitorVO.spnDescription}
<c:if test="${not empty spnMonitorVO.spnInstruction}">
	<h5><strong>How to Join</strong></h5><br/>
	${spnMonitorVO.spnInstruction}
</c:if>
<c:if test="${not empty spnMonitorVO.spnInfoDocuments}">
	<h5><strong>Learn More</strong></h5>
	<p>Read these attachments to learn more about this opportunity and how to join. Click to view and print.</p>
	<div id="documents" class="clearfix">
	<c:forEach items="${spnMonitorVO.spnInfoDocuments}" var="docList">
		<c:set var="docClass" value=""/>
		<c:set var="docClassWord" value="application/msword"/>
		<c:set var="docClassPdf" value="application/pdf"/>
		<c:set var="docClassGIF" value="image/gif"/>
		<c:set var="docClassJPEG" value="image/jpeg"/>
		<c:set var="docClassJPG" value="image/jpg"/>
		<c:set var="docClassPNG" value="image/png"/>
		<c:set var="docClassBMP" value="image/bmp"/>
		<c:set var="docClassText" value="Text"/>
		<c:set var="docClassXML" value="XML"/>
		<c:set var="docType" value=""/>
		<c:if test="${docList.format==docClassPdf}">
			<c:set var="docClass" value="pdf"/>
			<c:set var="docType" value="(Adobe Acrobat file, requires Acrobat Reader to open)"/>
		</c:if>
		<c:if test="${docList.format==docClassWord}">
			<c:set var="docClass" value="doc"/>
			<c:set var="docType" value="(Microsoft Word File)"/>
		</c:if>
		<c:if test="${docList.format==docClassGIF}">
			<c:set var="docClass" value="pic"/>
			<c:set var="docType" value="(GIF Image)"/>
		</c:if>
		<c:if test="${docList.format==docClassJPEG}">
			<c:set var="docClass" value="pic"/>
			<c:set var="docType" value="(JPEG Image)"/>
		</c:if>
		<c:if test="${docList.format==docClassJPG}">
			<c:set var="docClass" value="pic"/>
			<c:set var="docType" value="(JPG Image)"/>
		</c:if>
		<c:if test="${docList.format==docClassPNG}">
			<c:set var="docClass" value="pic"/>
			<c:set var="docType" value="(PNG Image)"/>
		</c:if>
		<c:if test="${docList.format==docClassBMP}">
			<c:set var="docClass" value="pic"/>
			<c:set var="docType" value="(BMP Image)"/>
		</c:if>
		<dl class="${docClass}">
			<dt><a href="${contextPath}/spnMonitorAction_loadDocument.action?&docID=${docList.docId}" target="_docWindow">${docList.documentTitle}</a></dt>
			<dd>${docType}</dd>
		</dl>
	</c:forEach>
	</div>
</c:if>