package com.walpole.frc.team.robot.subsystems;

import com.walpole.frc.team.robot.RobotMap;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	
	private Victor shooterMotor;
	private Victor agitatorMotor;
	    
    static double shooterP = 0.008;
    static double shooterI = 0;
    static double shooterD = 0.015;
    static double shooterF = 0.014;
    
	private PIDController shooterPID;
	
	public long shootingStartTime = System.currentTimeMillis();
	
	private Counter shooterCounter = new Counter(RobotMap.LIGHT_SENSOR);
	
	public Shooter() {
	
		shooterMotor = new Victor(RobotMap.SHOOTER_MOTOR);
		agitatorMotor = new Victor(RobotMap.AGITATOR_MOTOR);
		
		shooterCounter.setUpDownCounterMode();
		shooterCounter.setDistancePerPulse(1);
		shooterCounter.setMaxPeriod(0.01);
		shooterCounter.setPIDSourceType(PIDSourceType.kRate);

		shooterPID = new PIDController(shooterP, shooterI, shooterD, shooterF, shooterCounter, shooterMotor);
		shooterPID.setSetpoint(1905/60);
		shooterPID.setContinuous(false);
    	shooterPID.setOutputRange(0, 0.9);
    	shooterPID.disable();
	}
	
	public double getRPS() {
		return shooterCounter.getRate();
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        
    }
    public void shoot() {
    	shooterPID.enable();
    	if (shooterPID.getError() < 5 && shooterPID.getError() > -5) { // && shootingStartTime > 300 + System.currentTimeMillis()) {
    		agitatorOn();
    	} else { 
    		agitatorOff();
    	} 
    }
    
    public double shooterPIDError() {
    	return shooterPID.getError();
    }
    
    public void shootWhenWeDontHaveALightSensor() {
//    	agitatorMotor.set(-1);
    	shooterMotor.set(0.8);
//    	if (System.currentTimeMillis() - 1000 >= shootingStartTime) {
//    		agitatorMotor.set(-1);
//    	} else {
//    		agitatorMotor.set(0);
//    	}
    }
    
    public void stopShooting() {
    	shooterPID.disable();
    	agitatorOff();
    	shooterMotor.set(0);
    }
    
    public void agitatorOn() {
    	agitatorMotor.set(-1);
    }
    
    public void agitatorOff() {
    	agitatorMotor.set(0);
    }
    
    public void setShooterSpeed(double speed) {
    	shooterMotor.set(speed);
    }
    
    public double getShooterMotorPower() {
    	return shooterMotor.get();
    }
}