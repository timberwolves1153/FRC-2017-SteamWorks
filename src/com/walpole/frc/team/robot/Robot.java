
package com.walpole.frc.team.robot;

import com.walpole.frc.team.robot.commands.DriveBackwardsWithEncoder;
import com.walpole.frc.team.robot.commands.DriveForwardWithEncoder;
import com.walpole.frc.team.robot.commands.DriveForwardWithSeconds;
import com.walpole.frc.team.robot.commands.ExampleCommand;
import com.walpole.frc.team.robot.commands.ShiftHighCommand;
import com.walpole.frc.team.robot.commands.TurnWithGyroCommand;
import com.walpole.frc.team.robot.subsystems.Climb;
import com.walpole.frc.team.robot.subsystems.Drive;

import Autonomous.DeliverAGear;
import Autonomous.DeliverAGearLeft;
import Autonomous.DeliverAGearRight;
import Autonomous.DriveAndTurn;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static final Drive driveSubsystem = new Drive();
    public static OI oi;
    public static final Climb climbSubsystem = new Climb();

    private Command autonomousCommand;
    SendableChooser chooser;
    private Preferences prefs = Preferences.getInstance();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
	oi = new OI();
	chooser = new SendableChooser();
	chooser.addDefault("Default Auto", new ExampleCommand());
	// chooser.addObject("My Auto", new MyAutoCommand());
	SmartDashboard.putData("Auto mode", chooser);

	chooser.addObject("Deliver a Gear", new DeliverAGear());
	chooser.addObject("Deliver a Gear Left Side", new DeliverAGearLeft());
	chooser.addObject("Deliver a Gear Right Side", new DeliverAGearRight());
    }

    public void updateDashboard() {
	SmartDashboard.putBoolean("Limit Switch", climbSubsystem.getLimitSwitch().get()); // Write
	// the state of the limit switch to the Smart Dashboard
	SmartDashboard.putNumber("Left Encoder Value", driveSubsystem.getLeftEncoderCount());
	SmartDashboard.putNumber("Right Motor Power Value", driveSubsystem.getRightMotorPower());
	SmartDashboard.putNumber("Left Motor Power Value", driveSubsystem.getLeftMotorPower());
	// SmartDashboard.putNumber("Right Encoder Value",
	// driveSubsystem.getRightEncoderCount());
	SmartDashboard.putNumber("Gyro Angle", driveSubsystem.getGyroAngle());
	SmartDashboard.putNumber("Target Tick Count", Constants.ticksPerInch * 10);
	SmartDashboard.putNumber("Gyro Error", driveSubsystem.getGyroPIDError());
	SmartDashboard.putNumber("Gyro PID Output", driveSubsystem.getGyroPIDOutput());
	SmartDashboard.putBoolean("Gyro Is Finished", driveSubsystem.turnIsFinished());
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    public void disabledInit() {

    }

    public void disabledPeriodic() {
	Scheduler.getInstance().run();
	updateDashboard();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     *
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    public void autonomousInit() {
	// autonomousCommand = new DriveForwardWithEncoder(95);//(Command)
	// chooser.getSelected();
	driveSubsystem.updatePIDControllers();
	double desiredRotationDegrees = prefs.getDouble("degrees", 90);
	// autonomousCommand = new TurnWithGyroCommand(desiredRotationDegrees);
	double desiredSeconds = prefs.getDouble("seconds", 1);
	// autonomousCommand = new DriveForwardWithSeconds(desiredSeconds);
	// autonomousCommand = new TurnWithGyroCommand(desiredRotationDegrees);
	// autonomousCommand = new DriveAndTurn(desiredSeconds,
	// desiredRotationDegrees);
	// autonomousCommand = new DriveBackwardsWithEncoder(42);
	autonomousCommand = new DeliverAGear();

	/*
	 * String autoSelected = SmartDashboard.getString("Auto Selector",
	 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
	 * = new MyAutoCommand(); break; case "Default Auto": default:
	 * autonomousCommand = new ExampleCommand(); break; }
	 */

	// schedule the autonomous command (example)
	if (autonomousCommand != null)
	    autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
	Scheduler.getInstance().run();
	updateDashboard();
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
	// teleop starts running. If you want the autonomous to
	// continue until interrupted by another command, remove
	// this line or comment it out.
	if (autonomousCommand != null)
	    autonomousCommand.cancel();

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
	Scheduler.getInstance().run();
	driveSubsystem.drive(oi.getDriverJoystick());
	updateDashboard();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
	LiveWindow.run();
    }

}
