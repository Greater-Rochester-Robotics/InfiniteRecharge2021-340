/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import frc.robot.commands.pathing.PathList;
import frc.robot.commands.pathing.RunPath;

public class BarrelRace extends Auto340Command {
  /**
   * Creates a new BarrelRace.
   */
  public BarrelRace() {
    super(
    (new RunPath(PathList.AUTONAV_BARREL.FIRST_CURVE)),
    (new RunPath(PathList.AUTONAV_BARREL.INTO_CIRCLE)),
    (new RunPath(PathList.AUTONAV_BARREL.CIRCLE)),
    (new RunPath(PathList.AUTONAV_BARREL.OUT_OF_CIRCLE)),
    (new RunPath(PathList.AUTONAV_BARREL.SECOND_CURVE))
      );
  }

 
}
