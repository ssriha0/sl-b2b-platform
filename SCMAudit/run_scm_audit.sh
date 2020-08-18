#/bin/bash
pushd .

SCMAUDIT_HOME=/home/slapp/scmaudit
echo $SCMAUDIT_HOME
cd $SCMAUDIT_HOME
SCMAUDIT_CP=commons-httpclient-3.1.jar:SCMAudit.jar:mail-1.4.jar:activation-1.1.jar:commons-codec-1.3.jar:commons-logging-1.1.jar:xfire-all-1.2.6.jar
echo $SCMAUDIT_CP
# -DSCMAUDIT_DEBUG=true
java -cp $SCMAUDIT_CP com.servicelive.scmaudit.CodeCommitLogMonitor

popd

