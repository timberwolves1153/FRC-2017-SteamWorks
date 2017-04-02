package com.walpole.frc.team.robot;


import com.walpole.frc.team.robot.commands.ClimbDownCommand;
import com.walpole.frc.team.robot.commands.ClimbUpCommand;
import com.walpole.frc.team.robot.commands.ClimbWithoutLimitSwitch;
import com.walpole.frc.team.robot.commands.ExtendGearPusherCommand;
import com.walpole.frc.team.robot.commands.FireBallFlapperCommand;
import com.walpole.frc.team.robot.commands.GearCollectorOff;
import com.walpole.frc.team.robot.commands.GearCollectorOut;
import com.walpole.frc.team.robot.commands.MoveGearCollectorOutAutoCommand;
import com.walpole.frc.team.robot.commands.GearCollectorIn;
import com.walpole.frc.team.robot.commands.ReleaseGearCommand;
import com.walpole.frc.team.robot.commands.RetainGearCommand;
import com.walpole.frc.team.robot.commands.RetractBallFlapperCommand;
import com.walpole.frc.team.robot.commands.RetractGearPusherCommand;

import com.walpole.frc.team.robot.commands.ShooterShootCommand;
import com.walpole.frc.team.robot.commands.ShooterStopCommand;
import com.walpole.frc.team.robot.commands.TurnLightOffCommand;
import com.walpole.frc.team.robot.commands.TurnLightOnCommand;
import com.walpole.frc.team.robot.lib.RebelTrigger;
import com.walpole.frc.team.robot.commands.ClimbDownCommand;
import com.walpole.frc.team.robot.commands.ClimbUpCommand;
import com.walpole.frc.team.robot.commands.ShiftHighCommand;
import com.walpole.frc.team.robot.commands.ShiftLowCommand;
import com.walpole.frc.team.robot.commands.StopClimbCommand;
import com.walpole.frc.team.robot.commands.TurboModeOff;
import com.walpole.frc.team.robot.commands.TurboModeOn;
import com.walpole.frc.team.robot.lib.RebelTrigger;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    private Joystick opStick = new Joystick(RobotMap.OPERATOR_STICK);
    private Joystick driverStick = new Joystick(RobotMap.DRIVER_JOYSTICK);
    
    // Reserved for Climbing
    Button opTriggerL = new RebelTrigger(opStick, 2);
    Button opTriggerR = new RebelTrigger(opStick, 3);
    
    Button opA = new JoystickButton(opStick, 1);
    Button opB = new JoystickButton(opStick, 2);
    Button opX = new JoystickButton(opStick, 3);
    Button opY = new JoystickButton(opStick, 4);
    
    Button opBumperL = new JoystickButton(opStick, 5);
    Button opBumperR = new JoystickButton(opStick, 6);
    
    Button opStart = new JoystickButton(opStick, 8);
    Button opBack = new JoystickButton(opStick, 9);
    
	
	private Button drLT = new RebelTrigger(driverStick, 2);
	private Button drRT = new RebelTrigger(driverStick, 3);
	private Button drRB = new JoystickButton(driverStick, 6);     //the drLb and drRb are the left and right bumpers on the XBOX controller
	private Button drLB = new JoystickButton(driverStick, 5 );


public OI() {
	opBumperL.whileHeld(new ReleaseGearCommand());
	opBumperL.whenReleased(new RetainGearCommand());
	//opBumperR.whenReleased(new ExtendGearPusherCommand());
//	opBumperR.whileHeld(new RetractGearPusherCommand());

	drLT.whenPressed(new ShiftHighCommand());
	drLT.whenReleased(new ShiftLowCommand());

	//drRB.whenPressed(new ClimbWithoutLimitSwitch());    
	opBumperR.whenReleased(new StopClimbCommand()); //Changed to BumperL due to change in climb motor for PROTO 
	opBumperR.whenPressed(new ClimbUpCommand());     

	// when right bumper is released, robot motor will stop spinning

	opBumperL.whileHeld(new ClimbDownCommand());  //Changed to BumperR due to change in climb motor for PROTO   
	opBumperL.whenReleased(new StopClimbCommand());   
	
	
	
	drRT.whenPressed(new TurboModeOn());
	drRT.whenReleased(new TurboModeOff());
	
	opTriggerL.whileHeld(new FireBallFlapperCommand()); // This is a test
	opTriggerL.whenReleased(new RetractBallFlapperCommand());
	
	opTriggerR.whenPressed(new ShooterShootCommand());
	opTriggerR.whenReleased(new ShooterStopCommand());
	
	
	opA.whenPressed(new GearCollectorIn());
	opA.whenReleased(new GearCollectorOff());
	opB.whenPressed(new GearCollectorOut());
	opB.whenReleased(new GearCollectorOff());

	opY.whenPressed(new MoveGearCollectorOutAutoCommand(31, 0.4));

	}

	public Joystick getDriverJoystick() {
		return driverStick;
	}

	public Joystick getOperatorJoystick() {
		return opStick;
	}
}
