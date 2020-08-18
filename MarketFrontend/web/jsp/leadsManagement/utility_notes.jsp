<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
function countAreaChars(areaName,counter,limit, evnt)
{
	
	if (areaName.value.length>limit) {
		areaName.value=areaName.value.substring(0,limit);
		//alert("The field limit is " + limit + " characters.");
		
		//Stop all further events generated (Event Bubble) for the characters user has already typed in .
		//For IE
		if (!evnt) var evnt = window.event;
		evnt.cancelBubble = true;
		//For FireFox
		if (evnt.stopPropagation) evnt.stopPropagation();
	}
	else
		counter.value = limit - areaName.value.length;
}
</script>

<div class="tab-pane" id="notes">
	<!-- For displaying error messages -->
	
	<div id="responseMsg"
		style="margin-left: 5px; width: 56%; margin-left: 20px; display: none;"
		class="buyerLeadError"></div>
		<!-- For displaying error messages -->
	<a href="#" class="btn btn-default full btn-add-note" role="button"
		style="margin-top: 10px; margin-left: 10px; margin-bottom: 10px;"><i
		class="glyphicon glyphicon-file"></i> Add Note</a>
	<!-- For Adding new note -->
	<div class="row" id="widget-add-note"
		style="display: none; margin-left: 5px;">
		<form class="col-md-7">
			<label for="note_subject">
				Subject
			</label>
			<input type="text" id="note_subject" name="note_subject" maxlength="250"></input>
			<label for="note_message">
				Message
			</label>
			<textarea id="note_message" name="note_message" onfocus="if(this.value==this.defaultValue)this.value='';" onblur="if(this.value=='')this.value=this.defaultValue;"
             onkeyup="countAreaChars(this.form.note_message, this.form.comment_leftChars, 750, event);"
             onkeydown="countAreaChars(this.form.note_message, this.form.comment_leftChars, 750, event);"></textarea><br/>
			<div><input type="text" id="comment_leftChars" name="comment_leftChars" value="750" maxlength="3" size="3" readonly="readonly" style="width:60px;height30px; cursor:default; background: white;"><span style="float:right;margin-right:305px;margin-top:-30px;">Characters remaining</span></div>
			<button type="submit" class="btn btn-default full submitNote"
				style="margin-top: 10px; margin-left: 0px; margin-bottom: 10px;"
				id="addNote">
				Save
			</button>
			<input type="hidden" name="addNoteLeadId" id="addNoteLeadId"
				value="${lmTabDTO.lead.leadId}" />
			<input type="hidden" name="addNoteLeadStatus" id="addNoteLeadStatus"
				value="${lmTabDTO.lead.leadStatus}" />
		</form>
	</div>
	 
	<!-- For Adding new note -->
	<!-- /.note -->
	<div id="addingNote"
		style="margin-left: 20px; padding-top: 10px; display: none;">
		Adding Note...
	</div>
	<!-- For displaying the notes already added for the lead -->
	<c:if test="${lmTabDTO.lead.notes!=null}">
		<c:forEach items="${lmTabDTO.lead.notes.noteList}" var="note">
		<!-- For viewing the notes -->
		 <ul>
		  <li class="note viewNotes" id="viewNoteDiv_${note.noteId}" style="border-top: 1px solid #DDDDDD; margin-top: 10px; padding-left: 10px;word-wrap:break-word;">
			
				<c:if test="${note.roleId==1}">
				<a href="#"
					class="edit-note tooltip-target update-target"
					data-placement="left" data-original-title="Edit Note"
					id="editProvLeadNotes_${note.noteId}"> <i
					class="glyphicon glyphicon-pencil"></i> </a>
				</c:if>
				<h3>
					${note.subject}
				</h3>
				<p>
					${note.message}
				<p>
				<div class="secondary-info">
					<span class="pull-right">Posted by ${note.noteBy}</span>
					<time>
					${note.noteDate}
					</time>
				</div>
			
			</li>
			</ul>
			<!-- /.note -->
			<!-- For viewing the notes -->
			<!-- For editing the notes -->
			<div id="editNoteDiv_${note.noteId}"
				style="display: none; border-top: 1px solid #DDDDDD;"
				class="editNotes">
				<label for="note_subject" style="margin-left: 20px;">
					Subject
				</label>
				<input type="text" id="note_subject_edit_${note.noteId}"
					name="note_subject" value="${note.subject}"
					style="width: 57%; margin-left: 20px;" maxlength="250"></input>
				<label for="note_message" style="margin-left: 20px;">
					Message
				</label>
				<textarea id="note_message_edit_${note.noteId}" name="note_message"
					style="width: 57%; margin-left: 20px;"
					onkeyup="countAreaChars(this,comment_leftChars_edit_${note.noteId}, 750, event);"
             		onkeydown="countAreaChars(this,comment_leftChars_edit_${note.noteId}, 750, event);">${note.message}</textarea>
             	<div><input type="text" id="comment_leftChars_edit_${note.noteId}" name="comment_leftChars_${note.noteId}" value="750" maxlength="3" size="3" readonly="readonly" style="width:60px;height30px; cursor:default; background: white;margin-left:20px;margin-top:10px;"><span style="float:left;margin-left:100px;margin-top:-30px;">Characters remaining</span></div>
				<button type="submit" class="btn btn-default full submitNote"
					style="margin-top: 10px; margin-left: 20px; margin-bottom: 10px;"
					id="editNote_${note.noteId}">
					Save
				</button>
				<button type="submit" class="btn btn-default full cancel"
					style="margin-top: 10px; margin-left: 20px; margin-bottom: 10px;"
					id="cancel_${note.noteId}">
					Cancel
				</button>
			</div>
			<!-- For editing the notes -->
			<div id="updatingNote_${note.noteId}"
				style="margin-left: 20px; padding-top: 10px; padding-bottom: 10px; display: none;">
				Updating Note...
			</div>
			<!-- Hidden Variables -->
			<input type="hidden" name="leadNoteId" id="leadNoteId"
				value="${note.noteId}" />
			<input type="hidden" name="addNoteLeadId" id="addNoteLeadId"
				value="${lmTabDTO.lead.leadId}" />
			<!-- Hidden Variables -->
			
		</c:forEach>
	</c:if>
	<!-- For displaying the notes already added for the lead -->
</div>