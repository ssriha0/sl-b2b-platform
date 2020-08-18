update template set template_source=
'<HTML>
 <BODY>
 <p>
  * This note is sent to inform you that the following Assurant Incident in ServiceLive has received an
     Assurant Incident Cancellation Request.*
</p>
<br />
<p>
The following Service Order needs attention:
</p>
<p>
<b>** SERVICE ORDER DETAILS **</b>  <br />
=====================================================================  <br />
<b> Service Order Id  #:</b> $SO_ID  <br />
<b> Service Order Status  #:</b> $STATUS_DESC  <br />
<b> Service Order Sub Status  #:</b> $SUBSTATUS_DESC  <br />
<b> Incident Id #:</b> $INCIDENTID <br />
=====================================================================  <br />
<br />
Please follow the steps below:- <br/>

	1. Login to ServiceLive and access the Service Order listed above <br/>
	2. From the Incident Tracker, click on the Incident ID link <br/>
	3. Select Cancellation by Assurant option from the drop down and enter the Reason 
	     as Canceling per Assurant request and click on Send Response button.<br/>

<p>
If you have any questions please call the ServiceLive Support Team at 888-549-0640.<br/>

Thank you! 


</p>

</BODY>
</HTML>'
where template_id=235;