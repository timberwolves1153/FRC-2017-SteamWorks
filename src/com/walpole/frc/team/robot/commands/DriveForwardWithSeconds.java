package com.walpole.frc.team.robot.commands;

import com.walpole.frc.team.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForwardWithSeconds extends Command {
    
    private long startTimeMillis; 
    private double speed;
    private double secondsToDrive;

    public DriveForwardWithSeconds(double secondsToDrive) {
	requires(Robot.driveSubsystem);
	this.speed = 0.6;
	this.secondsToDrive = secondsToDrive;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	startTimeMillis = System.currentTimeMillis(); 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	Robot.driveSubsystem.driveAtSpeed(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return System.currentTimeMillis() - startTimeMillis >= secondsToDrive * 1000;  
    }

    // Called once after isFinished returns true
    protected void end() {
	Robot.driveSubsystem.stopDrive();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

    }
}   

