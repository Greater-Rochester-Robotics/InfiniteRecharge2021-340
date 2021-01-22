/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.LimelightCommands;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ObtainDistance extends CommandBase {
  /**
   * Creates a new GetDistance.
   */
  public ObtainDistance() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.limelight.setPipeline(0); //set pipeline to zero
    RobotContainer.limelight.setLightState(3); //turns on limelight led

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.drive.setLEDs(true); //also turn on PCM LEDs
	  // constnagt.
    RobotContainer.limelight.getDistance();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.limelight.setLightState(1); //turns off limelight led
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
