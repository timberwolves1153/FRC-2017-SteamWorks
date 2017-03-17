package com.walpole.frc.team.robot.commands;

import com.walpole.frc.team.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveGearCollectorOutCommand extends Command {
	
	private double speed;
	private double setPoint;
	
public MoveGearCollectorOutCommand(int setPoint, int speed) {
	requires(Robot.floorGear); 
	this.speed = speed; 
	this.setPoint = setPoint; 
	
}

	@Override
	protected void initialize() {
		Robot.floorGear.resetGearEncoder();
		Robot.floorGear.getGearPIDSetPoint(); 
		Robot.floorGear.enableGearPID();
		Robot.floorGear.setGearEncoderPIDSetpoint(setPoint);
		Robot.floorGear.setMaxGearCollectorPIDOutput(speed);
		Robot.floorGear.ArmDown();
		
	}

	@Override
	protected void execute() {
		double output = Robot.floorGear.getGearPIDOutput(); 
		Robot.floorGear.setGearMotor(output);
		
		
	}

	@Override
	protected boolean isFinished() {
		
		double error = Math.abs(Robot.floorGear.getGearPIDError()); 
		
		//Encoders Only:
		//return error < 100; 
		
		/*//LimitSwitch only:
		 if (!Robot.floorGear.getGearLimitSwitchState() == true) {
    		return true;
    	} else {
    		return false;
    	}*/
		 
		/*//Encoders and Limit Switch:
		return (error < 100 | Robot.floorGear.getGearLimitSwitchState());  */
		 
		//Testing Purposes:
		return false;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}
	

}
