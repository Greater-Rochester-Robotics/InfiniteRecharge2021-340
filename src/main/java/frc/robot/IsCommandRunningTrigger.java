/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * A trigger designed to tell when a command is running.
 *  Good for using with another trigger with .and(). Also
 *  good for use with .negate(). 
 * 
 * This works, it could be better and has some ways commented out.
 * 
 * @author Rob-Heslin
 */
public class IsCommandRunningTrigger extends Trigger{
    // private CommandBase command;
    private SubsystemBase subsystem;
    // private String subsystemName;
    private String commandName;
    /**
     * Creates a new IsCommandRunningTrigger.
     * 
     * This is a Trigger that operates on whether a command is running
     *  or not. When using, make sure the subsystem of the Command is 
     *  construct or a null pointer error will occur.
     * 
     * @param command A copy of the Command we are interested in.
     */
	// public IsCommandRunningTrigger(CommandBase command) {
    //     this.command = command;
    //     Set<Subsystem> requirements = command.getRequirements();
    //     // this.subsystemName = command.getSubsystem();
    //     this.commandName = command.getName();
    // }
    
    public IsCommandRunningTrigger(SubsystemBase subsystem, CommandBase command) {
        // System.out.println("Subsystem: " + subsystem.getName() + "Command: " + command.getName());
        this.commandName = command.getName();
        this.subsystem = subsystem;
        // this.subsystemName = command.getSubsystem();
        // this.commandName = command.getName();
    }

    public boolean get(){
        if(this.subsystem.getCurrentCommand() == null){
            return false;
        }
        else{
            String currentCommand = this.subsystem.getCurrentCommand().getName();
            return currentCommand.contains(commandName);
        }

        // return CommandScheduler.getInstance().isScheduled(this.command);
    }
}
