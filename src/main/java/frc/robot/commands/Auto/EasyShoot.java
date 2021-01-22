/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.GetSmol;
import frc.robot.commands.LimelightCommands.AutoAlign;
import frc.robot.commands.Shooter.FastBallWithHintOfLime;
import frc.robot.commands.Shooter.FullInitShot;
import frc.robot.commands.Shooter.PrepHoodShot;
import frc.robot.commands.Shooter.ResetBallsShot;
import frc.robot.commands.pathing.PathList;
import frc.robot.commands.pathing.RunPath;

public class EasyShoot extends Auto340Command {
  /**
   * Creates a new EasyShoot.
   */
  public EasyShoot() {
     // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super
    (new ResetBallsShot(),new PrepHoodShot(), new AutoAlign(),new FastBallWithHintOfLime().withTimeout(2.5),new GetSmol(), new RunPath(PathList.EASY_SHOOT.THIRTY_SIX_INCH)
     );

  }
  @Override
  public String getSetUpInstructions(){
    return "Set up instructions: position so robot is pointed at the target, and can still drive over init line";
  }
  @Override
  public String getAutoDescription(){
    return "Aims, shoots, drives forward 3 feet";
  }
}
