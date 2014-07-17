LeapControl
===========

LeapControl is an addition to the multitude of operating system control programs for the Leap Motion 3D Tracking device. It currently only supports OSX and Linux, and was built for use with the [LeapMotionRecorder](https://github.com/alexanderchan97/LeapMotionRecorder). It is written in Java.

# <a name="installation"></a>Installation
Place all the file in the same directory. Download the [LeapSDK](https://developer.leapmotion.com) and copy `LeapJava.jar` into the same directory. For OSX, also copy `libLeap.dylib` and `libLeapJava.dylib` into the directory. For Linux, copy `libLeap.so` and `libLeapJava.so` into the directory. Now, run `leapControl.sh`.


# <a name="usage"></a>Usage
LeapControl uses two hands to allow for computer control. The right hand is used for moving the cursor, and the left hand is used for clicking. A click is performed by "keytapping", or when "the tip of a finger rotates down toward the palm and then springs back to approximately the original position, as if tapping."
LeapControl also uses swipes for scrolling. A vertical swipe with the right hand will scroll up or down. The amount of lines that is scrolled is directly proportional to the speed of the swipe.