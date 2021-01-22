/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*
This Hippo shot path moves back a little bit, then intakes balls that our teammates give us, all while shooting 
at the same time.
*/
package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.pathing.PathList;
import frc.robot.commands.pathing.RunPath;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class HungryHippoShot extends Auto340Command {
  /**
   * Creates a new HungryHippoShot.
   */
  public HungryHippoShot() {
    super(
    new RunPath(PathList.HUNGRY_HIPPO_SHOT.SCOOT_BACK),
    new HungryHippoBelly()
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    );
  }
  @Override
  public String getSetUpInstructions(){
    return "Set up instructions: Set up on init line near allied target zone, position robot so init line is under back bumpers";
  }
  @Override
  public String getAutoDescription(){
    return "Drives forward, aims at target, continues to shoot and load. So teammates can feed us";
  }
}
