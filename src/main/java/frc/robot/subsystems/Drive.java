/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.CANCoderConfiguration;

import java.util.ArrayList;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.analog.adis16448.frc.ADIS16448_IMU.IMUAxis;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.CANCoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PCM_LED;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import frc.robot.commands.DriveXOne;
import frc.robot.commands.Shooter.SmartLimeShot;

/**
 * <h1>Drive</h1> Moves the robot between two places. Always uses the Xbox ONE
 * controller command unless explicitly told otherwise<br>
 * <br>
 * Overall gear ratio: 44/5
 */
public class Drive extends SubsystemBase {
	private double leftSpeed, rightSpeed; // Makes math easier for fancy drive

	// RIP IMU
	// private static ADIS16448_IMU imu;
	// private static ADXRS450_Gyro gyro; 
	public ADIS16448_IMU imu;
	// private static CANCoder canLeft;
	private static Encoder encLeft, encRight;
	private static WPI_TalonFX driveLeftA, driveLeftB, driveRightA, driveRightB;
	private static PCM_LED led,led2;
	private static CANCoderConfiguration canConfig;
	Orchestra band;

	// factor to convert sensor values to a distance in inches
	private static final double kValueToInches = 0.0009766 * 25.4;
	private AnalogInput ultrasonic;

	/**
	 * Set up the Sparks and encoders with the ports specified in {@link Constants},
	 * and the IMU with the Y-axis as yaw
	 */
	public Drive() {
		ultrasonic = new AnalogInput(Constants.ULTRASONIC_PORT);
		imu = new ADIS16448_IMU(IMUAxis.kX, Port.kMXP, 4); //The parameter here is the
		// axis the IMU interprets as being yaw. This will depend on how the RIO is
		// oriented
		//imu.calibrate();
		//imu.reset();

		encLeft = new Encoder(Constants.DRIVE_ENCODER_LEFT_CHANNEL_A, Constants.DRIVE_ENCODER_LEFT_CHANNEL_B);
		encRight = new Encoder(Constants.DRIVE_ENCODER_RIGHT_CHANNEL_A, Constants.DRIVE_ENCODER_RIGHT_CHANNEL_B);

		//encLeft = new CANCoder(Constants.DRIVE_ENCODER_LEFT_CHANNEL);
		//encRight = new CANCoder(Constants.DRIVE_ENCODER_RIGHT_CHANNEL);

		//encLeft.getPosition(); //Returns absolute OR relative angle
		//encLeft.configGetParameter()
		//ParamEnum
		//CANCoderConfiguration variable = new CANCoderConfiguration();
		//variable.unitString = "";
		canConfig = new CANCoderConfiguration();
		canConfig.unitString = "inches";
		// canLeft = new CANCoder(Constants.DRIVE_CANCODER_LEFT_CHANNEL);
		// canLeft.configAllSettings(canConfig);
		
		

		
		


		/*
		*	http://www.ctr-electronics.com/downloads/api/java/html/classcom_1_1ctre_1_1phoenix_1_1sensors_1_1_c_a_n_coder.html#abab03d930fe99ad2ac9677e03095c310
		*	This link goes to the code for Falcon encoders
		*	Gear ratio = 4:3
		* 	Wheel circum 6in * pi
		*	>Relative angle of rotation
		* 	>Convert to distance
		*/

		//encLeft = new CANCoder(Constants.DRIVE_CANCODER_LEFT_CHANNEL);
		//encRight = new CANCoder(Constants.DRIVE_CANCODER_RIGHT_CHANNEL);

		// 256 ticks to 6pi inches = .0234375pi inches per tick
		encLeft.setDistancePerPulse(.0234375 * Math.PI);
		encRight.setDistancePerPulse(.0234375 * Math.PI);

		encLeft.setReverseDirection(true);
		encRight.setReverseDirection(true);
		
			driveLeftA = new WPI_TalonFX(Constants.DRIVE_LEFT_CHANNEL_A);
			driveLeftB = new WPI_TalonFX(Constants.DRIVE_LEFT_CHANNEL_B);
			driveRightA = new WPI_TalonFX(Constants.DRIVE_RIGHT_CHANNEL_A);
			driveRightB = new WPI_TalonFX(Constants.DRIVE_RIGHT_CHANNEL_B);
		

		// TODO: Commented out PCM LED due to null exception
		// led = new PCM_LED(Constants.SECONDARY_PCM_LED_LEFT, Constants.LED_PCM_CHANNEL_LEFT);
		// led2 = new PCM_LED(Constants.SECONDARY_PCM_LED_RIGHT,Constants.LED_PCM_CHANNEL_RIGHT);

		// TODO: consider enslaving B motors to A motors

		// Negate all speeds to the left side to account for mirrored axes
		driveLeftA.setInverted(true);
		driveLeftB.setInverted(true);


		ArrayList<TalonFX> instruments = new ArrayList<>();
		instruments.add(driveLeftA);
		instruments.add(driveLeftB);
		instruments.add(driveRightA);
		instruments.add(driveRightB);
		
		band = new Orchestra(instruments);
		band.loadMusic("ImperialMarch.chrp");
	
	}
	public void setBrakeMode(NeutralMode mode){
		
		driveLeftA.setNeutralMode(mode);
		driveLeftB.setNeutralMode(mode);
		driveRightA.setNeutralMode(mode);
		driveRightB.setNeutralMode(mode);
	}

	public void playMusic() {
		band.play();
	}

	public void stopMusic() {
		band.stop();
	}

	// @Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveXOne());
	}

	/**
	 * @return left encoder raw count
	 */
	public int getLeftEncoder() {
		return encLeft.get();
	}

	// public double getCanEncoder() { 
		// return canLeft.getPosition(); // Gets position of encoder
	// }
	/**
	 * @return right encoder raw count
	 */
	public int getRightEncoder() {
		return encRight.get();
	}

	/**
	 * @return encoder-derived left rail speed
	 */
	public double getLeftSpeed() {
		return encLeft.getRate();
	}

	/**
	 * @return encoder-derived right rail speed
	 */
	public double getRightSpeed() {
		return encRight.getRate();
	}

	/**
	 * @return left encoder distance
	 */
	public double getLeftDistance() {
		return encLeft.getDistance();
	}

	/**
	 * @return right encoder distance
	 */
	public double getRightDistance() {
		return encRight.getDistance();
	}

	/**
	 * Zero the left encoder
	 */
	public void resetLeftEncoder() {
		encLeft.reset();
	}

	/**
	 * Zero the right encoder
	 */
	public void resetRightEncoder() {
		encRight.reset();
	}

	/**
	 * Zero both encoders
	 */
	public void resetBothEncoders() {
		encLeft.reset();
		encRight.reset();
	}

	public double getSonicDistance() {
		return ultrasonic.getAverageVoltage() / kValueToInches * 2.76;
	}

	/**
	 * Set the speed, as a percentage of max forward speed, of the left drive rail
	 * 
	 * @param speed percentage of max forward speed
	 */
	public void setDriveLeft(double speed) {
		if (speed < -1) {
			speed = -1;
		} else if (speed > 1) {
			speed = 1;
		}
		driveLeftA.set(speed);
		driveLeftB.set(speed);
		// driveLeftA.setVoltage(speed*12);
		// driveLeftB.setVoltage(speed*12);
	}

	/**
	 * Set the speed, as a percentage of max forward speed, of the right drive rail
	 * 
	 * @param speed percentage of max forward speed
	 */
	public void setDriveRight(double speed) {
		if (speed < -1) {
			speed = -1;
		} else if (speed > 1) {
			speed = 1;
		}
		driveRightA.set(speed);
		driveRightB.set(speed);
		// driveRightA.setVoltage( speed*12);
		// driveRightB.setVoltage( speed*12);
	}

	/**
	 * Set the drive train to a given speed, as a percentage of max forward
	 * speed<br>
	 * Both rails will be at equal speed
	 */
	public void setDriveBoth(double speed) {
		setDriveLeft(speed);
		setDriveRight(speed);
	}

	/**
	 * Set the drive train to a given speed, as a percentage of max forward
	 * speed<br>
	 * Rails are independently sped
	 */
	public void setDriveBoth(double lSpeed, double rSpeed) {


		setDriveLeft(lSpeed);
		setDriveRight(rSpeed);
	}

	/**
	 * Stop the drive train
	 */
	public void stopDrive() {
		setDriveBoth(0);
	}

	/**
	 * Get the robot's yaw angle
	 */
	public double getRotation() {
		// return imu.getAngleZ();
		return -imu.getAngle();
	}

	/**
	 * Zero the gyro
	 */
	public void gyroReset() {
		// imu.reset();
		imu.reset();
	}

	/**
	 * Calibrate the gyro
	 */
	public void gyroCalibrate() {
		// imu.calibrate();
	}

	
	  //Turns the LEDs on/off (for Limelight assitance)
	  
	  //@param isOn {@code true} to turn them on, {@code false} to turn them off
	 
	public void setLEDs(boolean isOn) {
		// led2.set(isOn);
		// led.set(isOn);
	}

	/**
	 * One joystick drive mode. One stick axis speeds forward/backwards, the other
	 * adds rotation on robot yaw axis
	 * 
	 * @param moveValue   forward/backward speed, as a percentage of max forward
	 *                    speed
	 * @param rotateValue rotation speed, as a percentage of max rightward rotation
	 *                    speed
	 */
	public void arcadeDrive(double moveValue, double rotateValue) {
		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftSpeed = moveValue - rotateValue;
				rightSpeed = Math.max(moveValue, rotateValue);
			} else {
				leftSpeed = Math.max(moveValue, -rotateValue);
				rightSpeed = moveValue + rotateValue;
			}
		} else {
			if (rotateValue > 0.0) {
				leftSpeed = -Math.max(-moveValue, rotateValue);
				rightSpeed = moveValue + rotateValue;
			} else {
				leftSpeed = moveValue - rotateValue;
				rightSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}

		setDriveBoth(leftSpeed, rightSpeed); // This new drivebase is too fast
	}

	/**
	 * One joystick drive mode. One stick axis speeds forward/backwards, the other
	 * adds rotation on robot yaw axis<br>
	 * This one also controls mantis banebot wheels
	 * 
	 * @param moveValue   forward/backward speed, as a percentage of max forward
	 *                    speed
	 * @param rotateValue rotation speed, as a percentage of max rightward rotation
	 *                    speed
	 */
	public void arcadeDriveWithMantis(double moveValue, double rotateValue) {
		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftSpeed = moveValue - rotateValue;
				rightSpeed = Math.max(moveValue, rotateValue);
			} else {
				leftSpeed = Math.max(moveValue, -rotateValue);
				rightSpeed = moveValue + rotateValue;
			}
		} else {
			if (rotateValue > 0.0) {
				leftSpeed = -Math.max(-moveValue, rotateValue);
				rightSpeed = moveValue + rotateValue;
			} else {
				leftSpeed = moveValue - rotateValue;
				rightSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}

		setDriveBoth(leftSpeed * .2, rightSpeed * .2); // This new drivebase is too fast
	}

	@Override
	public void periodic() {
			SmartDashboard.putString("AngleX", ""+imu.getGyroAngleX());
			SmartDashboard.putString("AngleY", ""+imu.getGyroAngleY());
			SmartDashboard.putString("AngleZ", ""+imu.getGyroAngleZ());
			SmartDashboard.putString("Angle", ""+this.getRotation());
		
	}
}
