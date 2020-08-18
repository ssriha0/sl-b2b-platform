###################################################################
####Point should be done before running the script/class####

1.Keep a backup of 'user_profile' table.because in case of any issue we can revert the data.
2.keep the backup data securely.
##Important Notes:##
You should execute this Run_Encrypto_jar.sh/bat script for one time.
you should not try it for 2nd time after getting the success message (Updated all Password Successfully).
otherwise 'user_profile' password will be modified again and users will not able to login with his credentials.
 
###################################################################

1.Edit the connection properties in dbConnection.properties file.
2.uncomment datasource url,username and password.
3.Please change the IP and port according to the database.
4.provide the user name and password for that database.

Run_Encrypto_jar.sh/bat :
1.This is the script which is responsible for applying new hashing algorithm 
to existing password of 'user_profile' table.

##############################################################
############Backup and Revert Query for data base###################

###First Check in the database, backup table alredy exist or not if yes then verify few user with passwords.
If not then create and backup the user_profile data.

Query to verify:
Select *from 'user_profile_backup'


1.Create Query for backup table(Create a backup table):

 CREATE TABLE user_profile_backup LIKE user_profile;
 INSERT user_profile_backup SELECT * FROM user_profile;


2.Revert Query(It will restore data from backup table):
(First check the count of both table if count is mismatched than check may be few new user is added to 'use_profile' table)

SET SQL_SAFE_UPDATES = 0;
 
UPDATE user_profile up, user_profile_backup upb
 
SET up.password = upb.password
 
WHERE up.user_name = upb.user_name ;
SET SQL_SAFE_UPDATES = 1;





###Maintain the BackUp Database List Here######
***Check the data base table already exist or not***

Select *from 'user_profile_backup'

Backup already done for below environment. (user_profile_backup)
##Dev Env##

Env2
Env3


###QA ENV##
QA3


##API TEST ENV##

1.API Test
