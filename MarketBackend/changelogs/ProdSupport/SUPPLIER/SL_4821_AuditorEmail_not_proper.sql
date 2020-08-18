update template set template_source='<html>
<head>
<style type="text/css">
            #email_body {
                        font-family:Arial;
                        color: #505050;
                        font-size: 8.5pt;
                        font-weight: normal;
                        font-style: normal;
                        text-align: left;
                        background-color: #FFFFFF;
            }
a:link
          {color:blue;
          text-decoration:underline;
          text-underline:single;}
</style>
</head>
<body>
<img src="cid:emailLogo"><br/><br/>
<div id="email_body">
<strong><b>Congratulations ${PrimeContact},</b></strong>
<br/><br/>
<p>Thank you for entering the ${auditEmailVO.credentialName}
licenses or certifications information within ServiceLive.
${auditEmailVO.firstName} &nbsp; ${auditEmailVO.lastName} credentials are now <b>&quot;Reviewed&quot;</b>. </p>
<p>Note: ${auditEmailVO.firstName} will need to upload the actual license, certification,
or identification card to receive a <b>&quot;Verified&quot;</b> ServiceLive logo within their profile&quot;  </p>
<p>Provided your Firm is ServiceLive Approved this license or certification will be visible
to all buyers in the market place, however it will not post a <b>&quot;Verified&quot;</b> Logo beside the description.
These credentials will allow you to accept orders on projects that require licenses and certifications,
although some buyers may request <b>&quot;Verified&quot;</b> documents.
To ensure you have maximum service acceptance we recommend you add proof of the credential to allow it to be verified.
Adding the proof of documentation to the site gives the buyer a greater comfort level and acts as a great differentiator within the
ServiceLive platform to distinguish you from the competition and demonstrate why your Firm is the most capable to do the job.</p>
<p>Should you have any questions about the Sears Provider Network please feel free to contact Sears ServiceLive Recruitment at (800) 497-4402.</p>
<p>If you need an immediate help call the ServiceLive Support Team at (888) 549-0640, weekdays between 7:00 AM and 7:00 PM Central Standard Time or
if you would like to attend one of our online facilitated training sessions please click the following link to see the latest schedule:</p>
<p>If you need an immediate help call the ServiceLive Support Team at (888) 549-0640, weekdays between 7:00 AM and 7:00 PM Central Standard Time or if you would like to attend one of our online facilitated training sessions please click the following link to see the latest schedule:</p>
<p><a href="http://community.servicelive.com/docs/webcast.htm">
http://community.servicelive.com/docs/webcast.htm</a></p>
<p>Regards</p>
ServiceLive Team<br/>
3333 Beverly<br/>
5500 Trillium<br/>
Elm Building<br/>
Hoffman Estates, IL 60192
</div>
</body>
</html>'  where template_id=233