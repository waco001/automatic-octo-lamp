package org.usfirst.frc.team4628.robot;


import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
public class Robot extends SampleRobot {
	RobotDrive myRobot;  // class that handles basic drive operations
	Victor arm, auxLeft, auxRight;
	Victor push;
	Joystick driveStick, auxStick;  // driveStick
	Button raise, lower, shoot, suck, tip;
	//Servo push;
	int RAISE, LOWER, SHOOT, SUCK, TIP; //0: not moving, 1: shoot, 2: suck;
	double currentTime;
	AnalogGyro gyro;
	double kp;
	CameraServer camera;
	public Robot() {
		camera = CameraServer.getInstance();
    	camera.setQuality(50);
    	camera.startAutomaticCapture("cam0");
		myRobot = new RobotDrive(3, 2, 0, 1);
		myRobot.setExpiration(0.1);
		driveStick = new Joystick(0);
		auxStick = new Joystick(1);
		raise = new JoystickButton(auxStick, 11);
		lower = new JoystickButton(auxStick, 11);
		auxLeft = new Victor(5);
		auxRight = new Victor(4);
		//push = new Servo(9);
		RAISE=6;
		LOWER=7;
		SHOOT=3;
		SUCK=2;
		TIP=1;
		arm = new Victor(6);
		gyro = new AnalogGyro(1);
		kp = 0.03;
	}


	/**
	 * Runs the motors with tank steering.
	 */
	public void operatorControl() {
		myRobot.setSafetyEnabled(true);
		while (isOperatorControl() && isEnabled()) {
				myRobot.arcadeDrive(driveStick);
				Timer.delay(0.005);		// wait for a motor update time
			if (auxStick.getRawButton(TIP)){ 
				push.set(1);
				//push.setAngle(80);
			}
			else { 
				push.set(-1);
				//push.setAngle(140);
			}
			if (auxStick.getRawButton(RAISE)){ 
				arm.set(1.0);
			}
			else { 
				if (auxStick.getRawButton(LOWER)){ 
					arm.set(-0.3);
				}
				else { 
					arm.set(0);
				}
			}
			if (auxStick.getRawButton(SHOOT)){
				auxRight.set(1.0);
				auxLeft.set(-1.0);
			}
			else { 
				if (auxStick.getRawButton(SUCK)){ 
					auxRight.set(-0.4);
					auxLeft.set(0.4);
				}
				else { 
					auxRight.set(0.0);
					auxLeft.set(0.0);
				}
			}
		}
	}
	public void autonomous(){
		Timer myTimer = new Timer();
		myTimer.start();
		int DistanceToGo = 15; //FEET! change dis
		int DistanceToGo2 = DistanceToGo * 12; // DONT TOUCH THIS
		double circumference = 2*4*Math.PI; // No touchy
		double revolutions = DistanceToGo2/circumference;
		
		while (isAutonomous() && isEnabled()) {
			// If is has been less than 2 seconds since autonomous started, drive forwards
		    if(myTimer.get() < (0.4 * revolutions)){
		        myRobot.drive(-0.4, 0.0);
		    }

		    // If more than 2 seconds have elapsed, stop driving and turn off the timer
		    else {
		        myRobot.drive(0.0, 0.0);
		        myTimer.stop();
		    }
		}
	}
}
