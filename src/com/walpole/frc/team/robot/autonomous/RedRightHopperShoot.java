package com.walpole.frc.team.robot.autonomous;

import com.walpole.frc.team.robot.commands.DriveForwardWithEncoder;
import com.walpole.frc.team.robot.commands.DriveForwardWithGyroEncoder;
import com.walpole.frc.team.robot.commands.ShooterShootCommand;
import com.walpole.frc.team.robot.commands.ShooterStopCommand;
import com.walpole.frc.team.robot.commands.TurnWithGyroCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class RedRightHopperShoot extends CommandGroup {

	private static final int inchesToDrive = 82;
	private static final int degrees = 90;
	private static final int secondsToWait = 1;
	private static final int inchesToPushPad = 33;
	private static final int secondsToWaitTwo = 3;

	public RedRightHopperShoot() {
		super();
		
		// TODO Need command that retracts the top plate of the gear tray
		addSequential(new DriveForwardWithGyroEncoder(inchesToDrive ,2));
		addSequential(new WaitCommand(secondsToWait));
		addSequential(new TurnWithGyroCommand(degrees));
		addSequential(new DriveForwardWithGyroEncoder(inchesToPushPad ,2));
		addSequential(new WaitCommand(secondsToWaitTwo));
		addSequential(new TurnWithGyroCommand(degrees));
		addSequential(new DriveForwardWithGyroEncoder(5, 0.6));
		addSequential(new ShooterShootCommand(2));
		addSequential(new WaitCommand(8)); 
		addSequential(new ShooterStopCommand());
	}
}