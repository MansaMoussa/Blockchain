#!/bin/bash
set -v

################################################################################
###/!\/!\/!\/!\/!\/!\ TOUJOURS ./destroy avant de ./build  /!\/!\/!\/!\/!\/!\###
################################################################################
javac *.java




#******************************************************************************#
#******************************** S T A R T ***********************************#
#******************************************************************************#
#**************************** Noeud_Participant2 ******************************#
java Noeud_Participant 2222 1