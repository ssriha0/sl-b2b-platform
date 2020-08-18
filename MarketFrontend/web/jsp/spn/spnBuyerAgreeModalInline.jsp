<div class="modal-header">
	<h5 class="left"><span id="buyerAgreeModalBuyerName"></span>&nbsp;&nbsp;<span id="buyerAgreeModalSpnName"></span>&nbsp;&nbsp;Agreements</h5>
</div>
<form id="buyerAgreeModalForm" action="spnBuyerAgreeModal_acceptBuyerAgreementAjax.action" method="POST">
<div class="modal-content">
	<input type="hidden" id="buyerAgreeModalDocId" name="spnDocId" value="" />
	<div class="clearfix">
		<h3 class="left"><b><span class="buyerAgreeModalDocTitle"></span></b></h3>
		<a href="#printlink" class="right print" onClick="printdoc('${frameName}');">Print Agreement</a>
	</div>
	<div class="error" id="buyerAgreeModalError" style="display:none"></div>
	<div style="height: 130px; text-align: left; overflow: auto;">
		<span id="buyerAgreeModalDocData"></span>
	</div>
	<div class="clearfix buttonarea">	
		<input class="default left jqmClose agreeDocButtons" value="Cancel" type="button">
		<input class="default left jqmClose showDocButtons" value="Close" type="button">
		<input class="action right agreeDocButtons" value="I Agree" type="button" id="buyerAgreeModalSubmit" onClick="return false;">
	</div>
</div>
</form>