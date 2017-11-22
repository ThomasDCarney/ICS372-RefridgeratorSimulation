/**
 * Class: ICS 372 - Object Oriented Design and Implementation <br>
 * Instructor: Habtamu Bogale <br>
 * Description: Group Project #2, Simulation of a refrigerator. <br>
 * Due: 11/06/2015 <br><br>
 * 
 * This class creates the user interface with which to view the status
 * and change settings of the refrigerator.
 * 
 * @author Hanna Prussog, Laura Andersoon, Kevin Turgeon, Tom Carney
 * @version 1.0
 * @since 10/28/2015
 */

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RefrigeratorGUI extends JFrame implements ActionListener { 
    
    // The GUI only really maintains three objects, rest are actual GUI elements.
    private Clock clock = new Clock(this);
    private ElectricCooler fridge;
    private ElectricCooler freezer;
    
    // Stored by GUI, not loaded from file but will default to values in the file.
    private int currentRoomTemp;      
    private int dFridgeTemp;
    private int dFreezerTemp;
    
    // Stored by GUI but loaded from file.
    private int highestRoomTemp;
    private int lowestRoomTemp;
    private int fridgeLT;
    private int fridgeHT;
    private int freezerLT;
    private int freezerHT;
    private int fridgeIdleClosedRiseTime;
    private int fridgeIdleOpenRiseTime;
    private int freezerIdleClosedRiseTime;
    private int freezerIdleOpenRiseTime;
    private int fridgeActiveDiff;
    private int freezerActiveDiff;
    private int fridgeCoolTime;
    private int freezerCoolTime;

    // GUI Elements
    private JPanel inputPanel = new JPanel();
    private JPanel doorPanel = new JPanel();
    private JPanel statusPanel = new JPanel();
    
    private JLabel roomTempLabel;
    private JLabel dFridgeTempLabel;
    private JLabel dFreezerTempLabel;
    private JLabel fridgeDoorStatus = new JLabel("Fridge Door: Closed");
    private JLabel freezerDoorStatus = new JLabel("Freezer Door: Closed");
    private JLabel fridgeLightStatus = new JLabel("Fridge Light: OFF");
    private JLabel fridgeTempLabel = new JLabel("Fridge Temp: " + currentRoomTemp);
    private JLabel fridgeStatusLabel = new JLabel("Fridge Status: IDLE");
    private JLabel freezerLightStatus = new JLabel("Freezer Light: OFF");
    private JLabel freezerTempLabel = new JLabel("Freezer Temp: " + currentRoomTemp);
    private JLabel freezerStatusLabel = new JLabel("Freezer Status: IDLE");
    
    private JTextField roomTempField = new JTextField();
    private JTextField dFridgeTempField = new JTextField();
    private JTextField dFreezerTempField = new JTextField();
    
    private JButton setRoomTempButton = new JButton("Set Room Temp");
    private JButton setDFridgeTempButton = new JButton("Set Desired Fridge Temp");
    private JButton setDFreezerTempButton = new JButton("Set Desired Freezer Temp");
    private JButton openFridgeButton = new JButton("Open Fridge");
    private JButton openFreezerButton = new JButton("Open Freezer");
    private JButton closeFridgeButton = new JButton("Close Fridge");
    private JButton closeFreezerButton = new JButton("Close Freezer");
    
    
    /**
     * Constructor to instantiate a new RefridgeratorGUI.
     */
    public RefrigeratorGUI() {
        
        // GUI extends JFrame
        super("Refrigerator");                  
        this.setSize(1000, 300);
        this.setLayout(new GridLayout(3,1));
        
        try{
            
            BufferedReader br = new BufferedReader(new FileReader("Temperature.txt"));
            fridgeLT = Integer.parseInt(br.readLine());
            fridgeHT = Integer.parseInt(br.readLine());
            freezerLT = Integer.parseInt(br.readLine());
            freezerHT = Integer.parseInt(br.readLine());
            lowestRoomTemp = Integer.parseInt(br.readLine());
            highestRoomTemp = Integer.parseInt(br.readLine());
            fridgeIdleClosedRiseTime = Integer.parseInt(br.readLine());
            fridgeIdleOpenRiseTime = Integer.parseInt(br.readLine());
            freezerIdleClosedRiseTime = Integer.parseInt(br.readLine());
            freezerIdleOpenRiseTime = Integer.parseInt(br.readLine());
            fridgeActiveDiff = Integer.parseInt(br.readLine());
            freezerActiveDiff = Integer.parseInt(br.readLine());
            fridgeCoolTime = Integer.parseInt(br.readLine());
            freezerCoolTime = Integer.parseInt(br.readLine());               
            br.close();
            
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }catch (IOException e){
            System.out.println("IO Exception");
        }catch (NumberFormatException e){
            System.out.println("Please enter 14 Integers");
        }
     
        //end try catch
        
        // Stored in and editable in GUI but need defaults, will error on the low side.
        currentRoomTemp = lowestRoomTemp;
        dFridgeTemp = fridgeLT;
        dFreezerTemp = freezerLT;
        
        roomTempLabel = new JLabel("Room Temp: " + currentRoomTemp);
        dFridgeTempLabel = new JLabel("Desired Fridge Temp: " + dFridgeTemp);
        dFreezerTempLabel = new JLabel("Desired Freezer Temp: " + dFreezerTemp);
        
        // Create both units. In reality the getRoomTemp() would be an actual thermostat but
        // for now just pretending they start at room temp.
        fridge = new ElectricCooler(this, fridgeLT, fridgeHT, fridgeIdleClosedRiseTime, 
                                    fridgeIdleOpenRiseTime, fridgeActiveDiff, fridgeCoolTime, 
                                    getRoomTemp()); // starts at room temp!
        freezer = new ElectricCooler(this, freezerLT, freezerHT, freezerIdleClosedRiseTime, 
                                    freezerIdleOpenRiseTime, freezerActiveDiff, freezerCoolTime, 
                                    getRoomTemp()); // starts at room temp!
        
        // Setup inputPanel.
        inputPanel.setSize(1000, 100);
        inputPanel.setBackground(Color.CYAN);
        inputPanel.setLayout(new GridLayout(3, 3));
        
        setRoomTempButton.addActionListener(this);
        setDFridgeTempButton.addActionListener(this);
        setDFreezerTempButton.addActionListener(this);
        
        inputPanel.add(roomTempLabel);
        inputPanel.add(roomTempField);
        inputPanel.add(setRoomTempButton);
        inputPanel.add(dFridgeTempLabel);
        inputPanel.add(dFridgeTempField);
        inputPanel.add(setDFridgeTempButton);
        inputPanel.add(dFreezerTempLabel);
        inputPanel.add(dFreezerTempField);
        inputPanel.add(setDFreezerTempButton);
        
        // Setup doorPanel.
        doorPanel.setSize(1000, 100);
        doorPanel.setBackground(Color.GREEN);
        doorPanel.setLayout(new GridLayout(2, 3));
        
        openFridgeButton.addActionListener(this);
        closeFridgeButton.addActionListener(this);
        openFreezerButton.addActionListener(this);
        closeFreezerButton.addActionListener(this);
        
        doorPanel.add(fridgeDoorStatus);
        doorPanel.add(openFridgeButton);
        doorPanel.add(closeFridgeButton);
        doorPanel.add(freezerDoorStatus);
        doorPanel.add(openFreezerButton);
        doorPanel.add(closeFreezerButton);
        
        // Setup statusPanel.
        statusPanel.setSize(1000, 100);
        statusPanel.setBackground(Color.ORANGE);
        statusPanel.setLayout(new GridLayout(2, 3));
        statusPanel.add(fridgeLightStatus);
        statusPanel.add(fridgeTempLabel);
        statusPanel.add(fridgeStatusLabel);
        statusPanel.add(freezerLightStatus);
        statusPanel.add(freezerTempLabel);
        statusPanel.add(freezerStatusLabel);
        
        // Finish setting up the JFrame.
        this.add(inputPanel);
        this.add(doorPanel);
        this.add(statusPanel);
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    
    } // end RefridgeratorGUI constructor
    
    /**
     * The units being simulated process information on a schedule regulated by 
     * "ticks" generated by a shared clock. This method is used to coordinate those
     * activities.
     */
    public void processTick() {
        
        fridge.processTick();
        freezer.processTick();
        updateFridgeStatus();
        updateFreezerStatus();
        
    } // end processTick
    
    
    /**
     * Method attached to ActionListener interface. This is used to handle what happens
     * when the GUI's various buttons are pushed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource().equals(setRoomTempButton)) {
            
            System.out.println("Set room temp button pushed!");
            updateRoomTemp();
            
        } else if (e.getSource().equals(setDFridgeTempButton)) {
              
            System.out.println("Set desired fridge temp button pushed!");
            fridge.setDesiredTemp(getDesiredFridgeTemp());
            dFridgeTempLabel.setText("Desired Fridge Temp: " + fridge.getDesiredTemp());
            dFridgeTempField.setText("");
              
        } else if (e.getSource().equals(setDFreezerTempButton)) {
              
            System.out.println("Set desired freezer temp button pushed!");
            freezer.setDesiredTemp(getDesiredFreezerTemp());
            dFreezerTempLabel.setText("Desired Freezer Temp: " + freezer.getDesiredTemp());
            dFreezerTempField.setText("");  
            
        } else if (e.getSource().equals(openFridgeButton)) {
            
            System.out.println("Open fridge button pushed!");
            fridge.openDoor();
            updateFridgeStatus();
            
        } else if (e.getSource().equals(closeFridgeButton)) {
            
            System.out.println("Close fridge button pushed!");
            fridge.closeDoor();
            updateFridgeStatus();
            
        } else if (e.getSource().equals(openFreezerButton)) {
            
            System.out.println("Open freezer button pushed!");
            freezer.openDoor();
            updateFreezerStatus();
            
        } else if (e.getSource().equals(closeFreezerButton)) {
            
            System.out.println("Close freezer button pushed!");
            freezer.closeDoor();
            updateFreezerStatus();
            
        } // end IF
    
    } // end actionPerformed
    
    
    /**
     * This method will return the current room temperature.
     * 
     * @return - The current room temp.
     */
    public int getRoomTemp() {
        
        return currentRoomTemp;
        
    } // end getRoomTemp
    
    
    /**
     * This method is used to update the fridge status values.
     */
    private void updateFridgeStatus() {
        
        fridgeTempLabel.setText("Fridge Temp: " + fridge.getCurrentTemp());
        
        fridgeLightStatus.setText((fridge.isLightOn() ? 
                                   "Fridge Light: ON" : "Fridge Light: OFF"));
        
        fridgeDoorStatus.setText((fridge.isDoorOpen() ? 
                                  "Fridge Door: Open" : "Fridge Door: Closed"));
        
        fridgeStatusLabel.setText((fridge.isActive() ? 
                                   "Fridge Status: COOLING" : "Fridge Status: IDLE"));
        
    } // end updateFridgeStatus
    
    
    /**
     * This method is used to update the freezer status values.
     */
    private void updateFreezerStatus() {
        
        freezerTempLabel.setText("Freezer Temp: " + freezer.getCurrentTemp());
        
        freezerLightStatus.setText((freezer.isLightOn() ? 
                                    "Freezer Light: ON" : "Freezer Light: OFF"));

        freezerDoorStatus.setText((freezer.isDoorOpen() ? 
                                   "Freezer Door: Open" : "Freezer Door: Closed"));

        freezerStatusLabel.setText((freezer.isActive() ? 
                                    "Freezer Status: COOLING" : "Freezer Status: IDLE"));
        
    } // end updateFreezerStatus
    
    
    /**
     * This method is used to verify and update the new room temp value entered
     * by the user.
     */
    private void updateRoomTemp() {
        
        try {
            
            int newTemp =((int)Integer.parseInt(roomTempField.getText()));
            
            if (newTemp < lowestRoomTemp) { 
                newTemp = lowestRoomTemp;
            }
            
            if (newTemp > highestRoomTemp) {
                newTemp = highestRoomTemp;            
            }
                

                
                currentRoomTemp = newTemp;
                roomTempLabel.setText("Room Temp: " + currentRoomTemp);
                roomTempField.setText("");
                
        }   catch(NumberFormatException nfe) {

            roomTempField.setText("");
            
        }
            
    } // end updateRoomTemp
        

    
    
    /**
     * This method is used to get new desired fridge temp from that user input
     * field.
     * 
     * @return - The value entered by the user or -1000 if the user enters anything
     *           but an integer.
     */
    private int getDesiredFridgeTemp() {
        
        try {
            
            return ((int)Integer.parseInt(dFridgeTempField.getText()));
            
        } catch(NumberFormatException nfe) {
            
            return -1000; // Cause the cooler to error on the side of too cold.
            
        }
        
    } // end getDesiredFridgeTemp
    
    
    /**
     * This method is used to get new desired freezer temp from that user input
     * field.
     * 
     * @return - The value entered by the user or -1000 if the user enters anything
     *           but an integer.
     */
    private int getDesiredFreezerTemp() {
        
        try {
            
            return ((int)Integer.parseInt(dFreezerTempField.getText()));
            
        } catch(NumberFormatException nfe) {
            
            return -1000; // Cause the cooler to error on the side of too cold.
            
        }
        
    } // end getDesiredFreezerTemp
    
    
    /**
     * This gets the ball rolling by creating a new RefrigeratorGUI
     */
    public static void main(String[] args) {
        
        RefrigeratorGUI myGUI = new RefrigeratorGUI();
        
    } // end main

} // end RefrigeratorGUI