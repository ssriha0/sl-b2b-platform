#!/bin/bash
pushd .

cd /home/sladm/scripts/serviceOrderCreation/

java -Xms512m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=512m  -jar  ServiceOrderCreation.jar serviceOrderCreation.properties

cp 2009* /home/sladm/scripts/serviceOrderCreation/archive/

mv 2009* /appl/sl/iss/indata/

popd
