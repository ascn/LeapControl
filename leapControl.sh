#!/bin/sh

# Change to the path of the native libraries
export LD_LIBRARY_PATH=$PWD

javac -classpath LeapJava.jar ./leapcontrol/*.java

java -classpath "./LeapJava.jar:." leapcontrol/LeapControl