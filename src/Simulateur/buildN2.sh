﻿#!/bin/bash
set -v

################################################################################
###/!\/!\/!\/!\/!\/!\ TOUJOURS ./destroy avant de ./build  /!\/!\/!\/!\/!\/!\###
################################################################################
javac *.java
rmic BlockchainImpl
rmic NoeudBlockImpl


################################################################################
####  I'll be waiting for 5sec in order to let my peer to launch his server ####
################################################################################
rmiregistry 4242 &
rmiregistry 5678 &



#******************************************************************************#
#******************************** S T A R T ***********************************#
#******************************************************************************#
#******************************* Noeud_Block2 *********************************#
java -Djava.security.manager -Djava.security.policy=java.policy Noeud_Block 4242 2222 5678 1234