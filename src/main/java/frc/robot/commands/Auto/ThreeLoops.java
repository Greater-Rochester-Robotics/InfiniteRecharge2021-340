// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import frc.robot.commands.pathing.PathList;
import frc.robot.commands.pathing.RunPath;

public class ThreeLoops extends Auto340Command {
  /** Creates a new ThreeLoops. */
public ThreeLoops() {
  super(
    (new RunPath(PathList.THREE_LOOPS.STAGE_ONE)),
    (new RunPath(PathList.THREE_LOOPS.LOOP_ONE,false)),
    (new RunPath(PathList.THREE_LOOPS.LOOP_ONE_MORE,false)),
    (new RunPath(PathList.THREE_LOOPS.STAGE_TWO,false)),
    (new RunPath(PathList.THREE_LOOPS.LOOP_TWO,false)),
    (new RunPath(PathList.THREE_LOOPS.LOOP_TWO_MORE,false)),
    (new RunPath(PathList.THREE_LOOPS.LOOP_THREE,false)),
    (new RunPath(PathList.THREE_LOOPS.STAGE_THREE,false))
);
  }

}
