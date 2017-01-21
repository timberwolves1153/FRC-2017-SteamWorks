
package org.usfirst.frc.team1153.robot.subsystems;

import org.usfirst.frc.team1153.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Counter extends Subsystem {
	
	private DigitalInput lightSensor;
	
	public int counter;
	
	public Counter () {
		
		lightSensor = new DigitalInput(RobotMap.LIGHT_SENSOR);
		this.counter = counter;
		
	}
	
	public boolean getLightSensor() {
		return lightSensor.get();
	}
	
	public int getRPMCount() {
		return counter;
	}
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

