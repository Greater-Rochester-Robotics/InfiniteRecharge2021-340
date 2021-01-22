/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import frc.robot.commands.GetSmol;
import frc.robot.commands.RotateToZero;
import frc.robot.commands.LimelightCommands.AutoAlign;
import frc.robot.commands.Shooter.FastBallWithHintOfLime;
import frc.robot.commands.Shooter.SpinUpShooterWheel;
import frc.robot.commands.Shooter.PrepHoodShot;
import frc.robot.commands.SnekLoader.Load;
import frc.robot.commands.pathing.PathList;
import frc.robot.commands.pathing.RunPath;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TheEighthHokage extends Auto340Command {
  /**
   * Assigns a Eighth Hokage to protect the village.
   */
  public TheEighthHokage() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
    race(new Load(false), new RunPath(PathList.THE_EIGHTH_HOKAGE.STAGE_ONE)),
      new Load(false).withTimeout(0.5),
      parallel(new RunPath(PathList.THE_EIGHTH_HOKAGE.STAGE_TWO), new PrepHoodShot()),
      new AutoAlign(),
      new FastBallWithHintOfLime().withTimeout(3.0),
      new RotateToZero(-23.0).withTimeout(1.5),
    race( new Load(false),new SpinUpShooterWheel(), new RunPath(PathList.THE_EIGHTH_HOKAGE.STAGE_THREE)),
    // race(new NewPrepPlus(), new RunPath(PathList.THE_EIGHTH_HOKAGE.STAGE_THREE_POINT_ONE)),
    // race(new NewPrepPlus(), new RunPath(PathList.THE_EIGHTH_HOKAGE.STAGE_THREE_POINT_TWO)),
    // race(new NewPrepPlus(), new RunPath(PathList.THE_EIGHTH_HOKAGE.STAGE_THREE_POINT_THREE)),
    race(new Load(false), new SpinUpShooterWheel(), new RunPath(PathList.THE_EIGHTH_HOKAGE.STAGE_FOUR) ),
    new AutoAlign(),
    new FastBallWithHintOfLime().withTimeout(4.0),
    new GetSmol()
      );
  }
  @Override
public String getSetUpInstructions(){
  return "Set up instruction: line up right bumper with trench line, parallel with wall, position robot so init line is between the back two robot wheels";
}
 @Override
public String getAutoDescription(){
  return "Drives forward and takes two balls from the enemy trench. Drives forward close to the init line and closer to the target. Aims, shoots.";
}



}
