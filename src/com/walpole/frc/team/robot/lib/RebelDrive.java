package com.walpole.frc.team.robot.lib;

import com.walpole.frc.team.robot.Robot;
import com.walpole.frc.team.robot.subsystems.Drive.Shifter;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class RebelDrive extends RobotDrive {

	private NegInertiaCalc lowSpeedNic;
	private NegInertiaCalc highSpeedNic;
	private static double STRAIGHT_THROTTLE = 1;
	private static double TURN_THROTTLE = 0.85;
	private static double SLOW = 0.7;

	public RebelDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor,
			SpeedController rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		lowSpeedNic = new NegInertiaCalc(2);
		highSpeedNic = new NegInertiaCalc(4);
	}

	public void rebelArcadeDrive(GenericHID stick, Shifter gear) {
		if (Shifter.Low.equals(gear)) {
			arcadeDrive(stick.getY(), stick.getRawAxis(4));
		} else {
			arcadeDrive(stick.getY(), highSpeedNic.calculate(stick.getRawAxis(4)), true);
		}
	}
	
	public void rebelArcadeDrive(double driveSpeed, double turnSpeed) {
		if (Shifter.Low.equals(Robot.drive.getShift())) {
			arcadeDrive(driveSpeed * SLOW, turnSpeed, true);
		} else {
			arcadeDrive(driveSpeed * STRAIGHT_THROTTLE, lowSpeedNic.calculate(turnSpeed * TURN_THROTTLE), true);
		}
	}
}