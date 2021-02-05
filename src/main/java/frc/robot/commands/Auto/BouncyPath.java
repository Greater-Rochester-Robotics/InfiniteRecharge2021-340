// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import frc.robot.commands.pathing.PathList;
import frc.robot.commands.pathing.RunPath;

public class BouncyPath extends Auto340Command {
  /** Creates a new BouncyPath. */

  public BouncyPath() {
    super(
      (new RunPath(PathList.AUTONAV_BOUNCE_PATH.TURN_INTO_FIRST_BOUNCE)),
      (new RunPath(PathList.AUTONAV_BOUNCE_PATH.FIRST_BOUNCE_ONE,false)),
      (new RunPath(PathList.AUTONAV_BOUNCE_PATH.FIRST_BOUNCE_TWO,false)),
      (new RunPath(PathList.AUTONAV_BOUNCE_PATH.SECOND_BOUNCE,false)),
      (new RunPath(PathList.AUTONAV_BOUNCE_PATH.TURN_OUT_OF_BOUNCE,false))
    );
  
  }
}
