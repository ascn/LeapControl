package leapcontrol;

import com.leapmotion.leap.*;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Toolkit;
import java.awt.Dimension;

/**
 *
 * @author achan
 */
public class ControlListener extends Listener {
    
    @Override
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }
    
    @Override
    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
        
        if (controller.policyFlags() != Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES) {
            controller.setPolicyFlags(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
        }
        
    }
    
    @Override
    public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }
    
    @Override
    public void onExit(Controller controller) {
        System.out.println("Exited");
    }
    
    @Override
    public void onFrame(Controller controller) {
        
        Robot robot = null;
        
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(ControlListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        robot.setAutoDelay(10);
        Frame currentFrame = controller.frame();
        long currentFrameID = currentFrame.id();
        HandList allHands = currentFrame.hands();
        Hand hand;
        FingerList allFingers;
        Finger finger;
        Vector coordinates;
        int x, y, z;
        
        Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenRes.getWidth();
        double height = screenRes.getHeight();
        
        for (int i = 0; i < allHands.count(); i++) {
            hand = allHands.get(i);
            if (hand.isRight() == true) {
                allFingers = hand.fingers();
                for (int j = 0; j < allFingers.count(); j++) {
                    finger = allFingers.get(j);
                    if (finger.isExtended() == true && finger.type() == Finger.Type.TYPE_INDEX) {
                        coordinates = finger.tipPosition();
                        x = (int) ((coordinates.getX() + 150) * (width / 500));
                        y = (int) ((coordinates.getY() - 430) * -(height / 300));
                        z = (int) coordinates.getZ();
                        robot.mouseMove(x, y);
                    }
                }                
            }
        }
        
        controller.config().setFloat("Gesture.KeyTap.MinDownVelocity", 30.0f);
        controller.config().setFloat("Gesture.KeyTap.MinDistance", 1.0f);
        controller.config().setFloat("Gesture.Swipe.MinVelocity", 400.0f);
        controller.config().save();
        
        GestureList gestures = currentFrame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);
            
            switch (gesture.type()) {
                case TYPE_KEY_TAP:
                    KeyTapGesture keyTap = new KeyTapGesture(gesture);
                    HandList keyHands = keyTap.hands();
                    for (int j = 0; j < keyHands.count(); j++) {
                        Hand gestHand = keyHands.get(j);
                        if (gestHand.isLeft() == true) {
                            leftClick(robot);
                        }
                    }
                    break;
                case TYPE_SWIPE:
                    SwipeGesture swipe = new SwipeGesture(gesture);
                    Vector vecDir = swipe.direction();
                    String dir;
                    if (Math.abs(vecDir.getX()) < Math.abs(vecDir.getY())) {
                        dir = "vertical";
                    }
                    else {
                        dir = "horizontal";
                    }
                    switch (dir) {
                        case "vertical":
                            float speed = swipe.speed();
                            float scrollAmount = speed / 100;
                            if (vecDir.getY() < 0) {
                                swipe(robot, scrollAmount);
                            }
                            else {
                                swipe(robot, -scrollAmount);
                            }
                        case "horizontal":
                            /* robot.keyPress(KeyEvent.VK_CONTROL);
                            robot.keyPress(KeyEvent.VK_ALT);
                            robot.keyPress(KeyEvent.VK_RIGHT);
                            robot.keyRelease(KeyEvent.VK_RIGHT);
                            robot.keyRelease(KeyEvent.VK_ALT);
                            robot.keyRelease(KeyEvent.VK_CONTROL); */
                    }
                    break;
            }
        }
        
    }
    
    private void leftClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(50);
    }
    
    private void rightClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
        robot.delay(50);
    }
    
    private void swipe(Robot robot, float amt) {
        int scroll = (int) Math.round(amt);
        robot.mouseWheel(scroll);
        robot.delay(50);
    }
    
}
