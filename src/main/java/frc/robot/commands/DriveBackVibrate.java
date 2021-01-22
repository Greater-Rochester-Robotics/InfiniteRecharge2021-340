/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;;

public class DriveBackVibrate extends CommandBase {
  public DriveBackVibrate() {
	  addRequirements(RobotContainer.drive);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
	  System.out.println("DriveBackVibrate Started");
	Robot.robotContainer.setDriverRumble(0, 1);
	
	    withTimeout(.5); // this automatically unschedules the command
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
	RobotContainer.drive.setDriveBoth(.35,.35);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean isFinished) {
	  System.out.println("DriveBackVibrate Ended");
	RobotContainer.drive.setDriveBoth(0,0);
	Robot.robotContainer.setDriverRumble(0,0);

  }
}