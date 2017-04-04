package com.walpole.frc.team.robot.commands;

import com.walpole.frc.team.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearLEDUpdateStateCommand extends Command {

    public GearLEDUpdateStateCommand() {
    	requires(Robot.floorGear);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	Robot.floorGear.gearLEDOff();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.floorGear.pickedUpGearLED();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.floorGear.gearLEDOff();
    }
}
