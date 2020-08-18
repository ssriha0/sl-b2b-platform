update lu_audit_reason_codes set external_descr = 'The associated document you submitted does not match the credentials that provided on your application. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	56;
update lu_audit_reason_codes set external_descr = 'The associated document you submitted is unreadable. Please review, make the necessary changes and upload the new document within your profile.' where reason_cd =	57;
update lu_audit_reason_codes set external_descr = 'The associated document you submitted has expired. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	58;
update lu_audit_reason_codes set external_descr = 'There is no supporting document for the associated credential you submitted on your application. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	59;
update lu_audit_reason_codes set external_descr = 'The credential listed does not support the technical skill listed. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	60;
update lu_audit_reason_codes set external_descr = 'This associated credential provided on your application is unknown. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	61;
update lu_audit_reason_codes set external_descr = 'The associated credential provided on your application is invalid. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	62;
update lu_audit_reason_codes set external_descr = 'The number provided on your application for this credential is invalid. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	63;
update lu_audit_reason_codes set external_descr = 'The associated credential provided on your application is past expiration. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	65;


update lu_audit_reason_codes set external_descr = 'The associated document you submitted does not match the credentials you provided on your application. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	26;
update lu_audit_reason_codes set external_descr = 'The associated document you submitted is unreadable. Please review, make the necessary changes and upload the new document within your profile.' where reason_cd =	27;
update lu_audit_reason_codes set external_descr = 'The associated document you submitted has expired. Please <a href = \'http://community.servicelive.com/docs/ServiceLive-Verification-Guide.pdf\'><b>Click here</b></a> to see the ServiceLive Verification Guide. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	28;
update lu_audit_reason_codes set external_descr = 'There is no supporting document for this credential you submitted on your application. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	29;
update lu_audit_reason_codes set external_descr = 'The insurance coverage supplied by you does not meet the requirements. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	30;
update lu_audit_reason_codes set external_descr = 'It is very important that, when filling out all Certificates of Insurance and any other required forms, the same name is used on each form. All transmissions also must contain this exact name.  For example, if "Inc." is part of your corporate name, then "Inc." must be carried throughout the paperwork. If you operate as Doe, John d/b/a John Doe Appliance Repair, then that exact name should appear consistently throughout the paperwork. Please review, make the necessary changes and upload the new document within your profile.',reason_name = 'Name Not Consistent' where reason_cd =	31;
update lu_audit_reason_codes set external_descr = 'The associated credential provided on your application is invalid. ServiceLive does not recognize personal reference letters, tax receipts, insurance bill, etc., as credentials. Please review, make the necessary changes and upload the new credential within your profile.' where reason_cd =	32;
update lu_audit_reason_codes set external_descr = 'The Insurance Carrier listed is not A- or better. Verification requires your insurance carrier is rated A- or better in the current Best\'s Insurance Reports. You may visit and register at <a href = \'http://www3.ambest.com/ratings/default.asp\'><b>A.M. Best</b></a> Company to check your carrier\'s rating.  Please review, make the necessary changes and upload the new certificate within your profile.',reason_name = 'Insurance Carrier Not A-' where reason_cd =	33;
update lu_audit_reason_codes set external_descr = 'Please be sure to add Sears, Roebuck and Co. and affiliates and subsidiaries as the additional insured and assign as the certificate holder. The address must read: 1560 Cable Ranch Rd, Building A San Antonio, TX 78245. Please <a href = \'http://community.servicelive.com/docs/ServiceliveInsuranceRequirements.pdf\'><b>Click here</b></a> to see the ServiceLive Insurance Requirements Guide for details. Please review, make the necessary changes and upload the new certificate within your profile.',reason_name = 'Additional Insured - Inaccurate' where reason_cd =	35;

-- Additonal template changes done --

DELIMITER $$

update template set template_source ='
<html>
<head>
<style type="text/css">
	#email_body {
		font-family: Arial;
		color: #505050;
		font-size: 8.5pt;
		font-weight: normal;
		font-style: normal;
		text-align: left;
		background-color: #FFFFFF;
	}
</style>
</head>
<body>
<img src="cid:emailLogo"><br/><br/>
<div id="email_body">
<strong><b>Hello ${PrimeContact},</b></strong>
<br/><br/><strong>Thank you for completing your application on ServiceLive.com</strong> <br/><br/>
We appreciate the time you spent to provide us with your business and team member information. We have begun our review of your application and have found that the following company item(s) require action on your part before your company will be eligible to receive service orders from ServiceLive.<br/><br/><br/>
Credential Type:   	&nbsp; ${auditEmailVO.credentialType}<br/>
Name of Credential:   	&nbsp; ${auditEmailVO.credentialName}<br/>
Credential Number:   	&nbsp; ${auditEmailVO.credentialNumber}<br/>
Status:		    	&nbsp; ${auditEmailVO.targetStateName}<br/><br/><br/>
Reason Explanation:<br/>
${auditEmailVO.reasonDescription}<br/><br/>
                   
If you have any questions about the outstanding items or about becoming a ServiceLive Service Provider, please contact us at:<br/><br/><br/>
Email: contact@servicelive.com<br/>
ServiceLive, Inc <br/>
1560 Cable Ranch Rd <br/>
Building A <br/> 
San Antonio, TX78245 <br/><br/>
Thank you! We look forward to doing business with you.<br/>
</div>
</body>
</html>'
where template_id=2
$$

DELIMITER ;

DELIMITER $$

update template set template_source ='
<html>
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
</style>
</head>
<body>
<img src="cid:emailLogo"><br/><br/>
<div id="email_body">

<strong><b>Hello ${PrimeContact},</b></strong>
</br></br>
<strong>Thank you for completing your application on ServiceLive.com.   </strong><br/><br/><br/>

We appreciate the time you spent to provide us with your business and team member information. We have begun our review of your application and have found that the following team member item(s) require action on your part before the team member will be eligible to receive service orders from ServiceLive.<br/><br/><br/>

Team Member:          	&nbsp; ${auditEmailVO.resourceName}<br/>
Credential Type:   	&nbsp; ${auditEmailVO.credentialType} <br/>
Name of Credential:   	&nbsp; ${auditEmailVO.credentialName} <br/>
Credential Number:   	&nbsp; ${auditEmailVO.credentialNumber} <br/>
Status:		      	&nbsp; ${auditEmailVO.targetStateName}<br/><br/><br/>

Reason Explanation:<br/>
${auditEmailVO.reasonDescription}<br/><br/>                
		    
If you have any questions about the outstanding items or about becoming a ServiceLive Service Provider, please contact us at:  <br/><br/>

Email: contact@servicelive.com<br/>
ServiceLive <br/>
5500 Trillium<br/>
Elm Building<br/> 
Hoffman Estates, IL 60192<br/><br/>

Thank you! We look forward to doing business with you.<br/>
</div>
</body>
</html>'
where template_id=3
$$

DELIMITER ;

