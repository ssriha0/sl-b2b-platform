echo "Starting Tokenization Batch::"
echo `date`

echo "Please enter the mode run"

read -s MODE

export JAVA_HOME=/usr/java/jdk1.5.0_22

export PATH=$JAVA_HOME/bin:$PATH

echo $JAVA_HOME

java -Xmx512m -jar tokenization.jar $MODE

exitStatus=$?

echo "Exit Status : " $exitStatus
if [[ $exitStatus -ne 0 ]]
 then	
	echo "There is an error in Starting Tokenization Batch. Check the logs for more information"
else
	echo "Starting Tokenization Batch Batch completed successfully"
fi

echo `date`

