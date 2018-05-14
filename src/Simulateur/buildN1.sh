#!/bin/bash
set -v

################################################################################
###/!\/!\/!\/!\/!\/!\ TOUJOURS ./destroy avant de ./build  /!\/!\/!\/!\/!\/!\###
################################################################################
javac *.java
rmic BlockchainImpl
rmic NoeudBlockImpl

#cp BlockchainImpl_Stub.class .
#mv BlockchainImpl_Stub.class BlockchainImpl_Stub_NB1.class

#cp NoeudBlockImpl_Stub.class .
#mv NoeudBlockImpl_Stub.class NoeudBlockImpl_Stub_NB1.class
################################################################################
################  On envoit ce dont le Participant1 aura besoin ################
################################################################################
################################################################################
######### On personnalise les noms de la class qu'on enverra au voisin #########
################   On envoit ce dont mon peer NB2 aura besoin   ################
################################################################################
################################################################################
#On le remmet avec les bons noms pour compatibilité avec les classes existentes#
################################################################################
####  I'll be waiting for 5sec in order to let my peer to launch his server ####
################################################################################
rmiregistry 2222 &
rmiregistry 1234 &



#******************************************************************************#
#******************************** S T A R T ***********************************#
#******************************************************************************#
#******************************* Noeud_Block1 *********************************#
java -Djava.security.manager -Djava.security.policy=java.policy Noeud_Block 2222 4242 1234 5678
