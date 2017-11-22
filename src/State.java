/**
 * Class: ICS 372 - Object Oriented Design and Implementation <br>
 * Instructor: Habtamu Bogale <br>
 * Description: Group Project #2, Simulation of a refrigerator. <br>
 * Due: 11/06/2015 <br><br>
 * 
 * This is an interface that each state must implement.
 * 
 * @author Hanna Prussog, Laura Andersoon, Kevin Turgeon, Tom Carney
 * @version 1.0
 * @since 10/28/2015
 */

public interface State {
    
    public void openDoor();
    public void closeDoor();
    public void changeDesiredTemp();
    public void processTick();
    
} // end State