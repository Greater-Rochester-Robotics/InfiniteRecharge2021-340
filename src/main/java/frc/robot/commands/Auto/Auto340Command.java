/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public abstract class Auto340Command extends SequentialCommandGroup{
public Auto340Command(){
  super();
}

public Auto340Command(Command ... cmd){
  super(cmd);
}

  public String getSetUpInstructions(){return "";};

  public String getAutoDescription(){return "";};
}
