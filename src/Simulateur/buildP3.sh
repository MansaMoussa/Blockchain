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
if [ "$#" -eq 0 ];
  then java Noeud_Participant 4242 367
fi
if [ "$#" -eq 2 ];
  then java Noeud_Participant 4242 367 $1 $2 #I'm sending $2 to $1
fi
