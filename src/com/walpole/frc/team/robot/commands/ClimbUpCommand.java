
package com.walpole.frc.team.robot.commands;

import com.walpole.frc.team.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbUpCommand extends Command {

    public ClimbUpCommand() {
    	requires(Robot.climbSubsystem);    //for this command, we require the climb subsystem
        // Use requires() here to declare subsystem dependencies
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.climbSubsystem.climbUp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.climbSubsystem.getLimitSwitch().get();     //this is referring to whether the limit switch if true or not
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.climbSubsystem.stopClimb();         //calling the stopClimb method
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	

    }
    
}