/**
 * Class: ICS 372 - Object Oriented Design and Implementation <br>
 * Instructor: Habtamu Bogale <br>
 * Description: Group Project #2, Simulation of a refrigerator. <br>
 * Due: 11/06/2015 <br><br>
 * 
 * This class is a clock which is used to synchronize refrigerator 
 * processes second by second.
 * 
 * @author Hanna Prussog, Laura Andersoon, Kevin Turgeon, Tom Carney
 * @version 1.0
 * @since 10/28/2015
 */

public class Clock implements Runnable {

    private RefrigeratorGUI gui;
    
    public Clock(RefrigeratorGUI gui) {
        
        this.gui = gui;
        new Thread(this).start();
        
        
    } // end Clock constructor
    
    @Override
    public void run() {
        
        try{
            
            while(true) {
                
                Thread.sleep(1000);
                gui.processTick();
                
            }
            
        } catch(InterruptedException ie) {
            
            System.out.println("Exception in clock thread!");
            
        }
        
    }
    
    
    
    
    
}