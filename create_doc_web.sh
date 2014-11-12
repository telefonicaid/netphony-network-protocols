#!/bin/bash
PCEP_FILE='test.txt';
DIRS='src/es/tid/pce/pcep/messages/
src/es/tid/pce/pcep/objects/'
echo $PCEP_FILE
echo "#Detailed PCEP Implementation Support ("`cat VERSION`")" >> $PCEP_FILE
for dire in $DIRS; 
do 
	echo '##'`cat $dire/package.html | head -2 | tail -1` >> $PCEP_FILE 
	FILES=`ls $dire`
	for file in $FILES; 
	do
		echo $file
	done;
done;
