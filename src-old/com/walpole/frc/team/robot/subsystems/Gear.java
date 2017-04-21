package com.walpole.frc.team.robot.subsystems;

import com.walpole.frc.team.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gear extends Subsystem {

	private Solenoid gearSolenoidRetainer;
	private Solenoid gearPusherSolenoid;
	private Solenoid ballIntakeFlapper;

	public Gear() {
		gearSolenoidRetainer = new Solenoid(RobotMap.GEAR_SOLENOID_A);
		gearPusherSolenoid = new Solenoid(RobotMap.GEAR_PUSHER_SOLENOID_A);
		ballIntakeFlapper = new Solenoid(RobotMap.BALL_INTAKE_SOLENOID);

		init();
	}

	private void init() {
		gearSolenoidRetainer.set(true);
		gearPusherSolenoid.set(false);
		ballIntakeFlapper.set(true);
	}

	public void retractGearRetainer() {
		gearSolenoidRetainer.set(true);
	}

	public void keepGear() {
		gearSolenoidRetainer.set(false);
	}

	public void retractGearPusher() {
		gearPusherSolenoid.set(false);

	}

	public void fireGearPusher() {
		gearPusherSolenoid.set(true);
	}

	public void getGearState() {
		gearSolenoidRetainer.get();
	}

	public void fireBallFlapper() {
		// the false/true can be changed
		ballIntakeFlapper.set(false);
	}

	public void retractBallFlapper() {
		// the false/true can be changed
		ballIntakeFlapper.set(true);
	}

	@Override
	protected void initDefaultCommand() {
	}
}