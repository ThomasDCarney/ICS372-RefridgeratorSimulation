/**
 * Class: ICS 372 - Object Oriented Design and Implementation <br>
 * Instructor: Habtamu Bogale <br>
 * Description: Group Project #2, Simulation of a refrigerator. <br>
 * Due: 11/06/2015 <br><br>
 * 
 * This is one concrete implementation of the state interface. This handles
 * events when the cooler is active and door is closed.
 * 
 * @author Hanna Prussog, Laura Andersoon, Kevin Turgeon, Tom Carney
 * @version 1.0
 * @since 10/28/2015
 */

public class ActiveClosedState implements State {

    //Keeps track of when the freezer should cool down.
    private int coolSeconds = 0;
    private int coolMinutes = 0;
    
    //Keeps track of when the freezer should heat up.
    private int heatSeconds = 0;
    private int heatMinutes = 0;
    
    private ElectricCooler cooler;  // The cooler to which this state is attached.
    
    
    /**
     * Constructor for a new ActiveClosedState.
     * 
     * @param cooler - The ElectricCooler this state is attached to.
     */
    public ActiveClosedState(ElectricCooler cooler) {
        
        this.cooler = cooler;
        
    } // end ActiveClosedState constructor
    
    
    /**
     * This method handles the open door event for the state.
     */
    @Override
    public void openDoor() {
        
        cooler.toggleLight();
        cooler.toggleDoor();
        cooler.changeState(ElectricCooler.States.ACTIVE_OPEN);
        
        System.out.println("Door opening!");
        
    } // end openDoor

    
    /**
     * This method handles the close door event for the state
     */
    @Override
    public void closeDoor() {

        // Door already closed, do nothing
        
    } // end closeDoor

    
    /**
     * This method handles the change in desired temp for the state.
     */
    @Override
    public void changeDesiredTemp() {

        if(cooler.getCurrentTemp() < cooler.getDesiredTemp()) {
            
            cooler.changeState(ElectricCooler.States.IDLE_CLOSED);
            
        }
        
    } // end changeDesiredTemp

    
    /**
     * This method handles the process tick event for the state.
     * What happens as time passes.
     */
    @Override
    public void processTick() {
        
        coolSeconds++;
        heatSeconds++;
        
        if(cooler.getCurrentTemp() <= cooler.getDesiredTemp()) {
            
            cooler.changeState(ElectricCooler.States.IDLE_CLOSED);
            
        }
        
        if(coolSeconds == 5) { // for testing, 5 sec = 1 minute
            
            coolMinutes++;
            coolSeconds = 0;

        }

        if(heatSeconds == 5) { // for testing, 5 sec = 1 minute
            
            heatMinutes++;
            heatSeconds = 0;

        }        
        
        // in the active states the rise and cool times compete.
        if(coolMinutes == cooler.getCoolTime()) {
            
            cooler.setCurrentTemp(cooler.getCurrentTemp() - 1);
            coolMinutes = 0;

        }
        
        if(heatMinutes == cooler.getIdleClosedRiseTime()) {
            
            cooler.setCurrentTemp(cooler.getCurrentTemp() + 1);
            heatMinutes = 0;

        }        
        
        System.out.println("Tick cool: " + coolSeconds + " , heat: " + heatSeconds);

    } // end processTick
    
} // end ActiveClosed