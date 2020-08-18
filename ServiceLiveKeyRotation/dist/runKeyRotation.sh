echo "Starting ServiceLive Key Rotation Batch::"
echo `date`

echo "Please enter your enterprise user id"
read USERID

echo "Please enter your enterprise password"
read -s PASSWORD

echo "Please enter key rotation passphrase"
read -s PASSPHRASE

java -Xmx512m -jar exeKeyRotation.jar $USERID $PASSWORD $PASSPHRASE

exitStatus=$?

echo "Exit Status : " $exitStatus
if [[ $exitStatus -ne 0 ]]
 then	
	echo "There is an error in completing the ServiceLive Key Rotation Batch. Check the logs for more information"
else
	echo "ServiceLive Key Rotation Batch completed successfully"
fi

echo `date`
