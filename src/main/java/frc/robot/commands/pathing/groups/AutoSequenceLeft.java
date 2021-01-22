/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------

package frc.robot.commands.pathing.groups;

import edu.wpi.first.wpilibj.command.CommandGroupBase; 
import edu.wpi.first.wpilibj.command.WaitForChildren;  this doesn't work, replace with WaitUntilCommand and check for if commands are finished
import frc.robot.commands.DriveAutoAlign;
import frc.robot.commands.ElevatorExitStartConfig;
import frc.robot.commands.manual.ManualManipulatorWristDown;
import frc.robot.commands.pathing.PathList;
import frc.robot.commands.pathing.RunPath;

public class AutoSequenceLeft extends CommandGroup {
	public AutoSequenceLeft() {
		addParallel(new RunPath(PathList.LEFT_ROCKET.CLOSE_HATCH));
		addParallel(new ManualManipulatorWristDown());                        add parallel does things similtaneously
		addSequential(new ElevatorExitStartConfig());
        addSequential(new WaitForChildren());
        
		addSequential(new DriveAutoAlign());                                  addsequential does things in sequence
	}
}


commented out cause all of it's broken, also just examples of last years code */