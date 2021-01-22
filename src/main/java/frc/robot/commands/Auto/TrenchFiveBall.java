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
import frc.robot.commands.SnekLoader.Load;
import frc.robot.commands.pathing.PathList;
import frc.robot.commands.pathing.RunPath;

public class TrenchFiveBall extends Auto340Command {
  /**
   * Creates a new TrenchFiveBall.
   */
  
  public TrenchFiveBall() {
    super(new ResetBallsShot(),
    race (new RunPath(PathList.TRENCH_FIVE_BALL.STEP_ONE),new Load()),
      parallel(new RunPath(PathList.TRENCH_FIVE_BALL.STEP_TWO), new PrepHoodShot()),
      new AutoAlign(),
      new FastBallWithHintOfLime(),
      new GetSmol()
    );
  }
  @Override
  public String getSetUpInstructions(){
    return "Set up instructions: Line up with balls of allied trench run, parallel to the wall, position robot so init line is under the back bumper";
  }
  @Override
  public String getAutoDescription(){
    return "Drives back and gets the closest two balls in the allied trench. Drives forward to front side of trench, aims and shoots.";
  }
}
