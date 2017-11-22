/**
 * Class: ICS 372 - Object Oriented Design and Implementation <br>
 * Instructor: Habtamu Bogale <br>
 * Description: Group Project #2, Simulation of a refrigerator. <br>
 * Due: 11/06/2015 <br><br>
 * 
 * This is the electric cooler class which defines the objects used to
 * build a "refrigerator". An electric cooler is essentially just an 
 * insulated box that chills to/keeps a desired temperature. Two of these
 * are needed to make a single fridge-freezer combo.
 * 
 * @author Hanna Prussog, Laura Andersoon, Kevin Turgeon, Tom Carney
 * @version 1.0
 * @since 10/28/2015
 */

public class ElectricCooler {
    
    // A means to reference the various possible states.
    public enum States {IDLE_CLOSED, IDLE_OPEN, ACTIVE_CLOSED, ACTIVE_OPEN};
    
    // Create the various states the cooler can have.
    private State idleClosedState = new IdleClosedState(this);
    private State idleOpenState = new IdleOpenState(this);
    private State activeClosedState = new ActiveClosedState(this);
    private State activeOpenState = new ActiveOpenState(this);
    
    // We'll assume starting idle and closed... likely to change quick and often.
    private State currentState = idleClosedState;
    
    // Keep track of the GUI representing it.
    private RefrigeratorGUI gui;
    
    // These are the values loaded via file and passed during instantiation.
    private int currentTemp;
    private int desiredTemp;
    private int lowestTempSetting;
    private int highestTempSetting;
    private int idleClosedRiseTime;
    private int idleOpenRiseTime;
    private int goActiveDiff;
    private int coolTime;
    
    // Status variables
    private boolean lightOn = false;
    private boolean doorOpen = false;
    private boolean active = false;
    
    
    /**
     * Constructor to instantiate a new ElectricCooler.
     * 
     * @param gui - The GUI representing the cooler.
     * 
     * @param low - The lowest possible temperature setting.
     * 
     * @param high - The highest possible temperature setting.
     * 
     * @param idleClosedRiseTime - The time to raise one degree when idle and door 
     *          closed.
     * 
     * @param idleOpenRiseTime - The time to raise one degree when idle and door 
     *          open.
     * 
     * @param activeDiff - The temperature differential (actual - desired) that 
     *          will activate the fridge.
     * 
     * @param coolTime - The time to lower temp by one degree when active and door
     *          is closed.
     * 
     * @param currentTemp - An initial starting temp for the fridge.
     */
    public ElectricCooler(RefrigeratorGUI gui, int low, int high, int idleClosedRiseTime, 
                          int idleOpenRiseTime, int activeDiff, int coolTime, int currentTemp) {
        
        this.gui = gui;
        lowestTempSetting = low;
        highestTempSetting = high;
        this.idleClosedRiseTime = idleClosedRiseTime;
        this.idleOpenRiseTime = idleOpenRiseTime;
        goActiveDiff = activeDiff;
        this.coolTime = coolTime;
        this.currentTemp = currentTemp;
        this.desiredTemp = low;
        
    } // end ElectricCooler constructor
    
public int getRoomTemp() {
        
        return gui.getRoomTemp();
        
    }
    
    /**
     * This method will update the coolers desired temp. If The value provided is
     * too high or low, the nearest value in range will be assigned.
     * 
     * @param newTemp - The new desired temp.
     */
    public void setDesiredTemp(int newTemp) {
        
        // This portion update the temp.
        if(newTemp > highestTempSetting) {
            
            desiredTemp = highestTempSetting;
            
        } else if(newTemp < lowestTempSetting) {
            
            desiredTemp = lowestTempSetting;
            
        } else {
            
            desiredTemp = newTemp;
            
        } // end if
        
        // Current state will decide what to do based on the event.
        currentState.changeDesiredTemp();
        
    } // end setTemp
    
    
    /**
     * This method will return the desired temp.
     * 
     * @return - The cooler's desired temp.
     */
    public int getDesiredTemp() {
        
        return desiredTemp;
        
    } // end getDesiredTemp
    
    
    /**
     * This method will return the current temp.
     * 
     * @return - The cooler's current temp.
     */
    public int getCurrentTemp() {
        
        return currentTemp;
        
    } // end getCurrentTemp
    
    
    /**
     * This method is used to 
     * @param newTemp
     */
    public void setCurrentTemp(int newTemp) {
        
        // Short of lighting on fire, actual temp shouldn't be 
        // able to go above room temp.
        if(newTemp <= gui.getRoomTemp()) {
            
            currentTemp = newTemp;
            
        }
        
    } // end setCurrentTemp
    
    
    /**
     * This method will return the temperature differential that turn the cooler on.
     * 
     * @return - The temperature differential that activates the cooler.
     */
    public int getGoActiveDiff() {
        
        return goActiveDiff;
        
    } // end getGoActiveDiff
    
    
    /**
     * This method will return the one degree rise time when idle and closed.
     * 
     * @return - The one degree rise time when idle and closed.
     */
    public int getIdleClosedRiseTime() {
        
        return idleClosedRiseTime;
        
    } // end getIdleClosedRiseTime
    
    
    /**
     * This method will return the one degree rise time when idle and open.
     * 
     * @return - The one degree rise time when idle and open.
     */
    public int getIdleOpenRiseTime() {
        
        return idleOpenRiseTime;
        
    } // end getIdleOpenRiseTime
    
    
    /**
     * This method will return the one degree cool time when active and closed.
     * 
     * @return - The one degree cool time.
     */
    public int getCoolTime() {
        
        return coolTime;
        
    } // end getCoolTime
    
    
    /**
     * This method tells the current state to handle a tick event.
     */
    public void processTick() {
        
        currentState.processTick();
        
    } // end processTick
    
    
    /**
     * This method returns whether the cooler's light is on or not.
     * 
     * @return - A boolean true if the light is on, otherwise false.
     */
    public boolean isLightOn() {
        
        return lightOn;
        
    } // end isLightOn
    
    
    /**
     * This method toggles the light off and on like a switch.
     */
    public void toggleLight() {
        
        lightOn = !lightOn;
        
    } // end toggleLight
    
    
    /**
     * This method returns whether the cooler's door is open or not.
     * 
     * @return - A boolean true if the light is on, otherwise false.
     */
    public boolean isDoorOpen() {
        
        return doorOpen;
        
    } // end isDoorOpen
    
    
    /**
     * This method toggles the door open and closed.
     */
    public void toggleDoor() {
        
        doorOpen = !doorOpen;
        
    } // end toggleDoor
    
    
    /**
     * This method returns whether the cooler is currently active or not.
     * 
     * @return - A boolean true if active, false if idle.
     */
    public boolean isActive() {
        
        return active;
        
    } // end isActive
    
    
    /**
     * Each state decides when and to which state to change based on a particular event.
     * This method facilitates that decision and any other needed housekeeping.
     * 
     * @param state - A "States" value specifying the new state.
     */
    public void changeState(States state) {
        
        switch(state) {
        case ACTIVE_CLOSED:
            System.out.println("Going Active Closed");
            currentState = activeClosedState;
            active = true;
            break;
        case ACTIVE_OPEN:
            System.out.println("Going Active Open");
            currentState = activeOpenState;
            active = true;
            break;
        case IDLE_CLOSED:
            System.out.println("Going Idle Closed");
            currentState = idleClosedState;
            active = false;
            break;
        case IDLE_OPEN:
            System.out.println("Going Idle Open");
            currentState = idleOpenState;
            active = false;
            break;
        default:
            break;
        }
        
    } // end changeState
    
    
    /**
     * This method tells the current state to handle the open door event.
     */
    public void openDoor() {
        
        currentState.openDoor();
        
    } // end openDoor
    
    
    /**
     * This method tells the current state to handle a close door event.
     */
    public void closeDoor() {
        
        currentState.closeDoor();
        
    } // end closeDoor
    
} // end ElectricCooler