/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.LimelightCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class AutoAlign extends CommandBase {

  private int numTimesWithinTolerance =0;
  /**
   * Creates a new AutoAlign.
   */
  public AutoAlign() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.limelight, RobotContainer.drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.limelight.setStreamMode(0);
    RobotContainer.limelight.setLightState(3);// Turns the LEDs on
    RobotContainer.limelight.setPipeline(0);// Turns the limelight into hue mode?
    numTimesWithinTolerance = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.drive.setLEDs(true); //also turn on PCM LEDs
	  // constnagt.
    // double moveValue=RobotContainer.limelight.angleToTarget()*.1; //Need to test values to rotate
    double rotateValue = 0;
    if(Math.abs(RobotContainer.limelight.angleToTarget())>7.5){
      rotateValue = RobotContainer.limelight.angleToTarget()*.02;
    } else if(Math.abs(RobotContainer.limelight.angleToTarget())>1 && Math.abs(RobotContainer.limelight.angleToTarget()) < 7.5){
      rotateValue= (Math.abs(RobotContainer.limelight.angleToTarget())/RobotContainer.limelight.angleToTarget())*.11;
    } else {
      rotateValue = 0;
      numTimesWithinTolerance ++;
    }


    // write a function for rotateValue, so that it is driven by angleToTarget
    //Old code that might help align
		// if (Math.abs(RobotContainer.limelight.angleToTarget()) < .5){
		// 	rotateValue = 0;
		// }else{
		// 	double variable = .5;
		// 	//finds the speed we want to move in proportion to how far away we are from the target, modify the variable value to vary the
		// 	//speed and accuracy at which the limelight finds the target 
		// 	rotateValue = (((RobotContainer.limelight.angleToTarget())/35)*(moveValue+.45)) * variable;
		// }
		
		double rightSpeed = rotateValue;
		double leftSpeed = -(rotateValue);
		// System.out.println("Right speed: " +rightSpeed);
		// System.out.println("Left speed: " +leftSpeed);
		RobotContainer.drive.setDriveLeft(leftSpeed);
		RobotContainer.drive.setDriveRight(rightSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.limelight.setLightState(1);//Turns off LEDs
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return numTimesWithinTolerance >=25;
  }
}
