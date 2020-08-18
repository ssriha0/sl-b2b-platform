INSERT   INTO template
              (template_id,
               template_type_id,
               template_name,
               template_subject,
               template_source,
               sort_order,
               created_date,
               modified_date,
               modified_by,
               template_from,
               priority,
               eid)
   VALUES (
          89,
          1,
          'Finance ACH Warning Code Email',
          'Finance ACH Cleared',
          '<html>
<body>
<img src="cid:emailLogo"><br></br>
<B>$CURRENT_DATE</B><br></br>
<B>Hello $FNAME $LNAME,</B><br></br>
<B>Thank you for requesting funds from your ServiceLive Bucks Balance.</B><br></br>
Transaction ID: <B>$TRANSACTION_ID </B>    Amount: <B>$TRANS_AMOUNT</B><br></br>
We successfully processed your request to withdraw funds from your ServiceLive Bucks Balance; however, we were notified by your financial 
institution the routing number we have on file is incorrect for the account number you specified. You were not charged a fee in connection 
with this transactional error message.<br></br>

Description of Error:  <B>$RETURN_CODE_DESC</B><br/><br/>
Please login to ServiceLive.com, navigate to the Manage Accounts tab under your ServiceLive Wallet and update your financial institutions account 
information by clicking on the edit entry button at the bottom of the screen (Re-enter the correct routing and account number.  Please ensure the 
routing number is not from a deposit slip as deposit slips do not have the correct routing number for your account.  If you have any questions regarding 
the correct routing number please contact your financial institution for assistance).
<br/><br/>
If you have any questions about this transaction, please contact us at:<br/><br/>
Email: contact@servicelive.com<br></br>
ServiceLive, Inc.<br>
1560 Cable Ranch Rd., Building A<br>
San Antonio, TX 78245<br></br>
(888) 549-0640<br><br><br>

Thank you! We appreciate your business.<br></br>
This payment service is powered by Integrated Payment Systems, Inc.<br></br>
<B><a href="http://${SERVICE_URL}/termsAndConditions_displayBucksAgreement.action">Payment Terms & Conditions</a></B>
</body>
</html>',null,now(),now(),'agupt02','support@servicelive.com',1,null
       );
