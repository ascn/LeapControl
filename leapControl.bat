@ECHO off
javac -classpath LeapJava.jar ./leapcontrol/*.java
java -classpath "LeapJava.jar;." leapcontrol/LeapControl