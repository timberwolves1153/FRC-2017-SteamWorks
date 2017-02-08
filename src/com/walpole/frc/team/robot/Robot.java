package com.walpole.frc.team.robot;



import com.walpole.frc.team.robot.subsystems.Climb;
import com.walpole.frc.team.robot.subsystems.Drive;
import com.walpole.frc.team.robot.subsystems.Gear;
import com.walpole.frc.team.robot.subsystems.Collector;
import com.walpole.frc.team.robot.subsystems.Drive;
import com.walpole.frc.team.robot.subsystems.Shooter;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.IterativeRobot;
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
	public static final Collector collector = new Collector();
	public static final Shooter shooter = new Shooter();
	public static final Drive drive = new Drive();
	public static final Climb climb = new Climb();
	public static final Gear gear = new Gear();
	public static OI oi = new OI();
	public static CountRPM countRPM = new CountRPM();
	public ArrayList<Long> rotationTimeList = new ArrayList<Long>();
//	private static final int IMG_WIDTH = 320;
//	private static final int IMG_HEIGHT = 240; 
	
//	private VisionThread visionThread;;
//	private double centerX = 0.0; 
	
	
	private final Object imgLock = new Object();  

    private Command autonomousCommand;
    SendableChooser chooser;
//  NetworkTable table;
    
   

    /**
     * This function is run when  the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
//		 AxisCamera camera = CameraServer.getInstance().addAxisCamera("axis-camera-vision","10.11.91.71");
//	        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
//	       
//       
//        visionThread = new VisionThread(camera, new GripPipeline(), pipeline -> {
//        	if (!pipeline.filterContoursOutput().isEmpty()) {
//        		Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
//        		synchronized (imgLock) {
//        			centerX = r.x + (r.width / 2);
//        		}
//        	}
//        });
//        
//     visionThread.start();
//        
//        
    }
	
    
    public static void updateDashboard() {
		SmartDashboard.putNumber("Shooter Power", shooter.getSpeed());
//		SmartDashboard.putNumber("Shooter Speed", shooter.shooterEncoder.get());
		SmartDashboard.putNumber("RPS", Robot.countRPM.getRPS());
//		SmartDashboard.putBoolean("Light Sensor", Robot.countRPM.getLightSensor());
    	SmartDashboard.putBoolean("Limit Switch", climb.getLimitSwitch().get()); // Write the state of the limit switch to the SmartDashboard
    	SmartDashboard.putNumber("Left Encoder Value", drive.getLeftEncoderCount());
    	SmartDashboard.putNumber("Right Motor Power Value", drive.getRightMotorPower());
    	SmartDashboard.putNumber("Left Motor Power Value", drive.getLeftMotorPower());
//		SmartDashboard.putNumber("Right Encoder Value", driveSubsystem.getRightEncoderCount());
    	SmartDashboard.putNumber("Gyro Angle", drive.getGyroCount());
    	SmartDashboard.putNumber("Target Tick Count", Constants.ticksPerInch * 10);
    	
    }
    
    
    public void countRotations() {
    	countRPM.check();
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		updateDashboard();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
//        autonomousCommand = new DriveForwardWithEncoder(10);//(Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        
//        	double centerX;
//        	synchronized (imgLock) {
//        		centerX = this.centerX;
//        	}
//        	
//        	double turn = centerX - (IMG_WIDTH / 2);
        
        updateDashboard();

    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();    
        updateDashboard();
//        Robot.shooter.turnLightOn();
        Scheduler.getInstance().run();
        drive.drive(oi.getDriverJoystick());
        updateDashboard();
        countRotations();      
//        double[] defaultValue = new double[0];
//        double[] areas = table.getNumberArray("area", defaultValue);
//     	System.out.print("areas: ");
//     	for (double area : areas) {
//     		System.out.print(area + " ");
//     		
//     	}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
}
