<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="notesAndHistoryTabs">
<ul>
<li><a href="#notesTable"><span>Notes</span></a></li>
<li><a href="#historyTable"><span>Change History</span></a></li>
</ul>

<table id="notesTable" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<th>Date/Note Entered by</th>
		<th>Notes</th>
	</tr>
	<c:forEach items="${noteList}" var="note">
		<tr>
			<td>
				<fmt:formatDate value="${note.date}" pattern="M/d/yyyy h:mm a z" />
				<br/>
				${note.enteredByName}
				<c:if test="${note.enteredByID != null && note.enteredByID != ''}">
				(ID#${note.enteredByID})
				</c:if>
			</td>
			<c:set var="maxChunkLength" value="${0+50}" />
			<c:set var="theNote" value="${fn:trim(note.notes)}" />
			<c:if test="${fn:length(theNote) > maxChunkLength && fn:indexOf(theNote,' ') <= 0}">
				<c:set var="noteLength" value="${fn:length(theNote)}" />
				<c:set var="timesIn"><fmt:parseNumber integerOnly="true" value="${noteLength / maxChunkLength +1}" /></c:set>
				<c:forEach begin="1" end="${timesIn}" varStatus="status">
	<c:set var="theNote"><c:out value="${fn:substring(theNote, 0, maxChunkLength*status.index)}" /><c:out value=" " /><c:out value="${fn:substring(theNote, maxChunkLength*status.index, noteLength)}" /></c:set>
				</c:forEach>
			</c:if>
			<td><div id="spforProFirm" >
					<c:if test="${note.serviceProNameForFirm != null && note.serviceProNameForFirm != ''}">
					${note.serviceProNameForFirm}
					</c:if>
					<c:if test="${note.serviceProIdForFirm != null }">
					(ID#${note.serviceProIdForFirm})
					</c:if>
				</div>
			<span class="noteText"><c:out value="${theNote}" /></span>
			</td>
		</tr>
	</c:forEach>
</table>

<table id="historyTable" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<th>
			Date/Change<br/>
			Entered by
		</th>
		<th>
			Network
		</th>
		<th>
			Action
		</th>
		<th>
			Reason
		</th>
	</tr>
	<c:forEach items="${firmChangeHistoryList}" var="row">
		<tr>
			<td>
				<fmt:formatDate value="${row.dateChanged}" pattern="M/d/yyyy" />
				<br/>
				<fmt:formatDate value="${row.dateChanged}" pattern="h:mm a z" />
				<br/>
				<b>${row.changeEnteredByName}</b>
				<c:if test="${row.changeEnteredByID != null}">
				<br>
				(ID# ${row.changeEnteredByID})
				</c:if>
			</td>
			<td>
				${row.network}
			</td>
			<td>
				<b>${row.action}</b>
				<br/>
				${row.validityDate}
				<br/>
				<c:set var="maxChunkLength" value="${0+50}" />
				<c:set var="theNote" value="${fn:trim(row.comment)}" />
				<c:if test="${fn:length(theNote) > maxChunkLength && fn:indexOf(theNote,' ') <= 0}">
					<c:set var="noteLength" value="${fn:length(theNote)}" />
					<c:set var="timesIn"><fmt:parseNumber integerOnly="true" value="${noteLength / maxChunkLength +1}" /></c:set>
					<c:forEach begin="1" end="${timesIn}" varStatus="status">
						<c:set var="theNote"><c:out value="${fn:substring(theNote, 0, maxChunkLength*status.index)}" /><c:out value=" " /><c:out value="${fn:substring(theNote, maxChunkLength*status.index, noteLength)}" /></c:set>
					</c:forEach>
				</c:if>
			<c:set var="newLine" value="<%= \"\n\" %>" />
  			<span class="noteText"><div style="width: 200px; word-wrap: break-word;">${fn:replace(theNote, newLine, '<br />')}</div></span>
				
			</td>
			<td>
				${row.reason}
			</td>
		</tr>
	</c:forEach>
</table>
</div>