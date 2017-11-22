# ICS372-RefridgeratorSimulation
This is a simulation of combination fridge/freezer unit.

# Purpose
This was our first exposure to the "State Machine" design pattern. It was also one of our first attempts with the observer/observable pattern and multi threaded programming where a clock is used to synchronize events.

in this we have a single "Electric Cooler" class which is used to create the fridge and freezer halves of a full refridgerator. Each cooler can exist in one of four states which result from the door being open/closed and whether it's actively cooling or not. 
