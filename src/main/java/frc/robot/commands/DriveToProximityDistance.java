/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DriveToProximityDistance extends CommandBase {
  double distance;
  double offset;

  /**
   * Creates a new DriveToProximityDistance.
   */
  public DriveToProximityDistance(double distance) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drive);
    this.distance = distance;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    offset = distance - RobotContainer.drive.getSonicDistance();
    if (Math.abs(offset) < 12) {
      RobotContainer.drive.setDriveBoth(0.4);
    } else if (Math.abs(offset) <= 12) {
      RobotContainer.drive.setDriveBoth(Math.pow(offset, 0.5) * 0.1);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.drive.stopDrive();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (RobotContainer.drive.getSonicDistance() <= 1 + distance) &&
      (RobotContainer.drive.getSonicDistance() >= distance - 1);

  }
}
