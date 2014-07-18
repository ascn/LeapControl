package leapcontrol;

import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.AWTException;
import com.leapmotion.leap.Controller;
import java.io.IOException;

/**
 *
 * @author achan
 */
public class LeapControl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ControlListener listener = new ControlListener();
        Controller controller = new Controller();
        
        controller.addListener(listener);
        
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        controller.removeListener(listener);
        
    }
    
}
