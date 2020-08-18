#/bin/bash
pushd .

cd /home/sladm/scripts/serviceOrderCreation/

java -Xms512m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=512m -cp ServiceOrderCreation.jar com.servicelive.serviceordercreation.MainApp serviceOrderCreation.properties

java -Xms512m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=512m -cp ServiceOrderCreation.jar com.servicelive.serviceordercreation.MainApp serviceOrderCreationUpdate.properties

cp 2009* /home/sladm/scripts/serviceOrderCreation/archive/

mv 2009* /appl/sl/iss/indata/

popd
