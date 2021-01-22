/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.GetSmol;
import frc.robot.commands.RotateToZero;
import frc.robot.commands.LimelightCommands.AutoAlign;
import frc.robot.commands.Shooter.FastBallWithHintOfLime;
import frc.robot.commands.Shooter.PrepHoodShot;
import frc.robot.commands.Shooter.ResetBallsShot;
import frc.robot.commands.SnekLoader.Load;
import frc.robot.commands.pathing.PathList;
import frc.robot.commands.pathing.RunPath;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SixBallTrench extends Auto340Command {
  /**
   * Creates a new SixBallTrench.
   */
  public SixBallTrench() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new ResetBallsShot(),
      parallel(new PrepHoodShot(),new AutoAlign()),
    new FastBallWithHintOfLime().withTimeout(3.0),
    new GetSmol(),
    new RotateToZero(),
    race (new RunPath(PathList.TRENCH_SIX_BALL.GRAB_THREE), new Load()),
    parallel (new RunPath(PathList.TRENCH_SIX_BALL.POSITION_SHOOT_THREE), new PrepHoodShot()),
    new AutoAlign(),
    new FastBallWithHintOfLime().withTimeout(3.5),
    new GetSmol()
    );
  }

  public String getSetUpInstructions(){
    return "";


}

  public String getAutoDescription(){
    return "";
  
  }
}
