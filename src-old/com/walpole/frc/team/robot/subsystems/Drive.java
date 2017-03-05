package com.walpole.frc.team.robot.subsystems;

import com.walpole.frc.team.robot.subsystems.Drive;

import com.walpole.frc.team.robot.lib.RebelDrive;
import com.walpole.frc.team.robot.lib.DummyPIDOutput;
import com.walpole.frc.team.robot.Constants;
import com.walpole.frc.team.robot.Robot;
import com.walpole.frc.team.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {

	private RebelDrive robotDrive;

	// Set this to true to quickly enable preferences
	private static final boolean prefsEnabled = false;

	private SpeedController leftFrontVictor;
	private SpeedController leftBackVictor;
	private SpeedController rightFrontVictor;
	private SpeedController rightBackVictor;

	private DoubleSolenoid transmission;
	
	private Relay relayLED;

	private double encoderP;
	private double encoderI;
	private double encoderD;

	private double gyroP;
	private double gyroI;
	private double gyroD;

	private Encoder leftEncoder;
	private DummyPIDOutput leftEncoderOutput;
	private PIDController leftEncoderPID;
	private Encoder rightEncoder;
	private DummyPIDOutput rightEncoderOutput;
	private PIDController rightEncoderPID;

	//private RebelGyro gyro;
	//private AnalogGyro gyro;
	private AHRS gyro;
	private PIDController gyroPID;
	private DummyPIDOutput gyroOutput;
	private boolean turnIsFinished;
	private double driveTolerance = 15;

	// TODO: What is the difference? Maybe remove one for clarity?
	public enum Shifter {
		High, Low
	}

	private Shifter currGear = Shifter.Low;

	public Drive() {
		leftFrontVictor = new Victor(RobotMap.LEFT_FRONT_MOTOR);
		leftBackVictor = new Victor(RobotMap.LEFT_BACK_MOTOR);
		rightFrontVictor = new Victor(RobotMap.RIGHT_FRONT_MOTOR);
		rightBackVictor = new Victor(RobotMap.RIGHT_BACK_MOTOR);

		transmission = new DoubleSolenoid(RobotMap.TRANSMISSION_SOLENOID_A, RobotMap.TRANSMISSION_SOLENOID_B);
		
		relayLed = new Relay(RobotMap.HOPPER_LED_STRIP);

		leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B, false, EncodingType.k4X);
		leftEncoderOutput = new DummyPIDOutput();
		leftEncoderPID = new PIDController(encoderP, encoderI, encoderD, leftEncoder, leftEncoderOutput);
		leftEncoderPID.setOutputRange(-1, 1);
		
		rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_A, RobotMap.RIGHT_ENCODER_B, false, EncodingType.k4X);
		rightEncoder.setReverseDirection(true);
		rightEncoderOutput = new DummyPIDOutput();
		rightEncoderPID = new PIDController(encoderP, encoderI, encoderD, rightEncoder, rightEncoderOutput);
		rightEncoderPID.setOutputRange(-1, 1);

		//gyro = new RebelGyro();
		//gyro.startThread();
		//gyro = new AnalogGyro(new AnalogInput(RobotMap.GYRO));
		try {
		    gyro = new AHRS(SerialPort.Port.kUSB);
		} catch (RuntimeException ex) {
	        DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		    // TODO Add fallback code for when the gyro cannot instantiate
		}
		gyroOutput = new DummyPIDOutput();
		gyroPID = new PIDController(gyroP, gyroI, gyroD, gyro, gyroOutput);
		gyroPID.setOutputRange(-1, 1);
		gyroPID.setInputRange(-180, 180);
		gyroPID.setContinuous();

		robotDrive = new RebelDrive(leftFrontVictor, leftBackVictor, rightFrontVictor, rightBackVictor);
	}

	/**
	 * Load PID values from preferences and write them to variables
	 */
	private void loadPIDValues() {
		if (prefsEnabled) {
			encoderP = Robot.prefs.getDouble("encoderP", Constants.encoderP);
			encoderI = Robot.prefs.getDouble("encoderI", Constants.encoderI);
			encoderD = Robot.prefs.getDouble("encoderD", Constants.encoderD);
			
			gyroP = Robot.prefs.getDouble("gyroP", Constants.gyroP);
			gyroI = Robot.prefs.getDouble("gyroI", Constants.gyroI);
			gyroD = Robot.prefs.getDouble("gyroD", Constants.gyroD);
		} else {
			encoderP = Constants.encoderP;
			encoderI = Constants.encoderI;
			encoderD = Constants.encoderD;
			
			gyroP = Constants.gyroP;
			gyroI = Constants.gyroI;
			gyroD = Constants.gyroD;
		}
	}

	/**
	 * Update the proportional, integral, and derivative values and update the PID Controllers
	 */
	public void updatePIDControllers() {
		loadPIDValues();

		leftEncoderPID.setPID(encoderP, encoderI, encoderD);
		rightEncoderPID.setPID(encoderP, encoderI, encoderD);
		gyroPID.setPID(gyroP, gyroI, gyroD);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Used to find how many encoder ticks per given inches
	 * @param Inches
	 * @return Ticks
	 */
	public double convertInchesToTicks(int inches) {
		return Constants.ticksPerInch * inches;
	}

	public boolean isOnTarget() {
		return Math.abs(leftEncoderPID.getError()) < driveTolerance
				|| Math.abs(rightEncoderPID.getError()) < driveTolerance;
	}
	
	// LED Methods
	
	public void setLEDred() {
		relayLED.set(Value.kOn);
	}

	public void setLEDBlue() {
		relayLED.set(Value.kOff);
	}
	
	// Shifting Methods
	
	public Shifter getShift() {
		return currGear;
	}

	public void shiftHigh() {
		transmission.set(DoubleSolenoid.Value.kReverse);
		currGear = Shifter.High;
	}

	public void shiftLow() {
		transmission.set(DoubleSolenoid.Value.kForward);
		currGear = Shifter.Low;
	}
	
	// Motor/Drive Methods
	
	public void drive(Joystick joystick) {
		double moveValue = Constants.TELE_DRIVE_SPEED * joystick.getRawAxis(RobotMap.JOYSTICK_LEFT_Y);
		double rotateValue = Constants.TELE_TURN_SPEED * joystick.getRawAxis(RobotMap.JOYSTICK_RIGHT_X);
		robotDrive.rebelArcadeDrive(moveValue, rotateValue);
	}

	public double getLeftMotorPower() {
		// Return a negated value because the left motors are backwards
		return -leftBackVictor.get();
	}

	public double getRightMotorPower() {
		return rightBackVictor.get();
	}
	
	public void stopDrive() {
		robotDrive.stopMotor();
	}
	
	public void driveAtSpeed(double speed) {
		robotDrive.arcadeDrive(speed, 0);
	}
	
	public void turnAtSpeed(double speed) {
		robotDrive.arcadeDrive(0, speed);
	}

	public void arcadeDrive(double driveSpeed, double turnSpeed) {
		robotDrive.arcadeDrive(driveSpeed, turnSpeed, false);
	}

	public void tankDrive(double leftDrive, double rightDrive) {
		robotDrive.tankDrive(leftDrive, rightDrive, false);
	}
	
	// Encoder Methods
	
	public int getLeftEncoderCount() {
		return leftEncoder.get();
	}

	public int getRightEncoderCount() {
		return rightEncoder.get();
	}
	
	public void enableDrivePID() {
		leftEncoderPID.enable();
		rightEncoderPID.enable();
	}
	
	public void disableDrivePID() {
		leftEncoderPID.disable();
		rightEncoderPID.disable();
	}
	
	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public double getLeftPIDError() {
		return leftEncoderPID.getError();
	}

	public double getRightPIDError() {
		return rightEncoderPID.getError();
	}

	public double getLeftPIDOutput() {
		return leftEncoderPID.get();
	}

	public double getRightPIDOutput() {
		return rightEncoderPID.get();
	}
	
	public void setMaxDrivePIDOutput(double drivingSpeed) {
		leftEncoderPID.setOutputRange(-drivingSpeed, drivingSpeed);
		rightEncoderPID.setOutputRange(-drivingSpeed, drivingSpeed);
	}
	
	public void setDrivePIDSetPoint(double setPoint) {
		leftEncoderPID.setSetpoint(setPoint);
		rightEncoderPID.setSetpoint(setPoint);
	}
	
	public double getRightEncoderSetpoint() {
		return rightEncoderPID.getSetpoint();
	}

	public double getLeftEncoderSetpoint() {
		return leftEncoderPID.getSetpoint();
	}
	
	// Gyro Methods
	
    public double getGyroYaw() {
    	return gyro.getYaw();
    }
	
    public boolean checkGyroCalibration() {
    	return gyro.isCalibrating();
    }
    
	public void enableGyroPID() {
		gyroPID.enable();
	}
	
	public void disableGyroPID() {
		gyroPID.disable();
	}
	
	// We no longer want to reset the gyro because of the way the navX gyro works
	/*public void resetGyro() {
		gyro.reset();
	}*/
	
	public double getGyroPIDError() {
		return gyroPID.getError();
	}
	
	public double getGyroPIDOutput() {
		return gyroPID.get();
	}
	
	public void setMaxGyroOutput(double turningSpeed) {
		gyroPID.setOutputRange(-turningSpeed, turningSpeed);
	}
	
	public void setTurnPIDSetpoint(double setPoint) {
		gyroPID.setSetpoint(setPoint);
	}
	
	public boolean turnIsFinished() {
		return this.turnIsFinished;
	}
}