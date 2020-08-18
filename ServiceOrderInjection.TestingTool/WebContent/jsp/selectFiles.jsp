<%@ taglib prefix="s" uri="/struts-tags" %>

<s:include value="/jsp/header.jsp" />

<div class="directions">
<h1>Step 1/3: Select test files</h1>
<p>Select the test files to be included in this batch.</p>
<s:include value="/jsp/error.jsp" />
</div>


<s:form action="sendToEsb" theme="simple">

<div class="checkboxes">
	<div class="title">
		<strong>OMS Test Files</strong>
		<a href="#" class="checkAllInList">(select all)</a>
	</div>
	<input type="hidden" class="allSelected" />
	<s:checkboxlist name="omsList" list="omsFiles" />
</div>

<div class="checkboxes">
	<div class="title">
		<strong>HSR Test Files</strong>
		<a href="#" class="checkAllInList">(select all)</a>
	</div>
	<input type="hidden" class="allSelected" />
	<s:checkboxlist name="hsrList" list="hsrFiles" />
</div>

<div class="checkboxes">
	<div class="title">
		<strong>Assurant Test Files</strong>
		<a href="#" class="checkAllInList">(select all)</a>
	</div>
	<input type="hidden" class="allSelected" />
	<s:checkboxlist name="assurantList" list="assurantFiles" />
</div>

<p><s:submit value="Continue" /></p>

</s:form>

<script type="text/javascript">
	$(function() {
		$('.checkboxes label').after('<br />');
		$('.allSelected').each(function() {
			if ($(this).val() == 'true') {
				$(this).parents('.checkboxes').find('.checkAllInList').text('(unselect all)');
			}
		});
		$('.checkAllInList').click(function() {
			allChecked = $(this).parents('.checkboxes').find('.allSelected');
			if (allChecked.val() == 'true') {
				$(this).text('(select all)');
				$(this).parents('.checkboxes').find('input[type=checkbox]').attr('checked', false);
				allChecked.val('false');
			} else {
				$(this).text('(unselect all)');
				$(this).parents('.checkboxes').find('input[type=checkbox]').attr('checked', true);
				allChecked.val('true');
			}
			return false;
		});
	});
</script>

<s:include value="/jsp/footer.jsp" />