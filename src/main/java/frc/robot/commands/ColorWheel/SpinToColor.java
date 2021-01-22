/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ColorWheel;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ColorWheel.ColorName;

public class SpinToColor extends CommandBase {
public ColorName colorDetected;
private ColorName wantedColor;

  public SpinToColor() {
    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(RobotContainer.colorWheel);
  }



  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // SmartDashboard.putBoolean("Spinning", true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    // colorDetected = RobotContainer.colorWheel.getColor();
    if (DriverStation.getInstance().getGameSpecificMessage().length() > 0) {
      wantedColor = ColorName.valueOf (String.valueOf(DriverStation.getInstance().getGameSpecificMessage().charAt(0)));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // SmartDashboard.putBoolean("Spinning", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return colorDetected.getTargetColor() == wantedColor;

  }
}
