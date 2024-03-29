
package com.walpole.frc.team.robot.commands;

import com.walpole.frc.team.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ClimbUpCommand extends Command {

    public ClimbUpCommand() {
	requires(Robot.climb); // for this command, we require the
					// climb subsystem
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	Robot.climb.climbUp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(!Robot.climb.getLimitSwitchState() | !Robot.climb.getOtherLimitSwitchState() == true) {
    		return true;
    	} else {
    		return false;
    	}
//    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
	Robot.climb.stopClimb(); // calling the stopClimb method
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }
}