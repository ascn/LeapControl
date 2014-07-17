#!/bin/sh

# Change to the path of the native libraries
export LD_LIBRARY_PATH=/home/achan/Desktop/.developer/LeapControl/

javac -classpath LeapJava.jar ./leapcontrol/*.java

java -classpath "./LeapJava.jar:." leapcontrol/LeapControl