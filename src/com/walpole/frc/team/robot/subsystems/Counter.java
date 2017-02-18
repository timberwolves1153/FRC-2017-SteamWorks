
package com.walpole.frc.team.robot.subsystems;

import com.walpole.frc.team.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Counter extends Subsystem {

	private DigitalInput lightSensor;

	private int counter;

	public Counter() {

		lightSensor = new DigitalInput(RobotMap.LIGHT_SENSOR);
		
	}

	public void reset() {
		this.counter = 0;
	}

	public boolean getLightSensor() {
		return lightSensor.get();
	}

	public int getRPMCount() {
		return counter;
	}

	public void increment() {
		counter++;
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}