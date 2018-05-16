# Blockchain
Blockchain simulator.

How to build ?

Make sure you've cleaned everything before trying to build something

	cd src/Simulateur/
	./buildN1.sh	#On a first terminal window to launch the first Node
	./buildN2.sh	#On a second terminal window to launch the second Node
	./buildP1.sh	#On a third terminal window to connect the first Participant to the first Node
								# and/or to know how much money we've got
	./buildP2.sh	#On a fourth terminal window to connect the first Participant to the first Node
								# and/or to know how much money we've got
	./buildP3.sh	#On a fifth terminal window to connect the second Participant to the first Node
								# and/or to know how much money we've got

How to clean ?

	cd src/Simulateur/
	./destroy.sh

How to send Money ?
	1 <= x <=3
	./buildPx.sh [participantID] [moneyIwoulLikeToSend]
