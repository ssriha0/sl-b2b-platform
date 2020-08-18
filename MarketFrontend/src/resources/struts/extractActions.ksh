 cat *.xml | grep action | grep -v result | grep -v param | grep name | awk -F'name='  ' {print $2 }' | awk -F '\"' ' {print $2} ' | awk -F'*' '{ print "4,"$1 }' > /cygdrive/c/actions.csv
