#!/bin/bash
set -v

################################################################################
###/!\/!\/!\/!\/!\/!\ TOUJOURS ./destroy avant de ./build  /!\/!\/!\/!\/!\/!\###
################################################################################
javac *.java












#******************************************************************************#
#******************************** S T A R T ***********************************#
#******************************************************************************#
#**************************** Noeud_Participant3 ******************************#
java Noeud_Participant 4242 367
