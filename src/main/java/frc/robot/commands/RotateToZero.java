/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.analog.adis16448.frc.ADIS16448_IMU.IMUAxis;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RotateToZero extends CommandBase {
  public static final double FeedForward = 0.13;
  public static final double kProportion = 0.15;
  public static double error; /*= targetAngle - getRotation();*/
  public double targetAngle;
  // public ADIS16448_IMU imu;
  /**
   * Creates a new GyroDrive.
   */
  public RotateToZero() {
    // imu = new ADIS16448_IMU(IMUAxis.kX, Port.kMXP, 4);
    addRequirements(RobotContainer.drive);
    this.targetAngle = 0.0;
    // Use addRequirements() here to declare subsystem dependencies.
  }
  public RotateToZero(double targetAngle) {
    // imu = new ADIS16448_IMU(IMUAxis.kX, Port.kMXP, 4);
    addRequirements(RobotContainer.drive);
    this.targetAngle = targetAngle;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turnToAngle(targetAngle, 0.0);
  }

	public double getRotation() {
		// return imu.getAngleZ();
		return RobotContainer.drive.getRotation();
	}

  public void turnToAngle(double targetAngle, double turnSpeed) {
    error = (targetAngle - (getRotation())%360) / 360;
    SmartDashboard.putString("error","" + error);
    if (error > 0) {
      RobotContainer.drive.arcadeDrive(0.0, FeedForward + (kProportion * error));
    } else {
      RobotContainer.drive.arcadeDrive(0.0, -FeedForward + (kProportion * error));
    }
    SmartDashboard.putString("Rotation ammount", "" + (FeedForward + kProportion * error) );
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return (error * 360) <= .20 && (error * 360) >= -.20;
  }
}
