/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Set;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import frc.robot.RobotContainer.Axis;

/**
 * Drives the robot via Arcade Drive on the driver's Xbox ONE controller
 */
public class DriveXOne extends CommandBase {

    /**
     * Continuously drive the robot via Arcade Drive
     */
    public DriveXOne() {
        addRequirements(RobotContainer.drive);
    }

    @Override
    public void initialize() {
        RobotContainer.drive.setBrakeMode(NeutralMode.Brake);
    }
    /**
     * Left for full speed, right stick slow stick
     */
    @Override
    public void execute() {
        // Robot.drive.arcadeDrive(Robot.oi.getDriverAxis(Axis.LEFT_Y),
        // Robot.oi.getDriverAxis(Axis.LEFT_X)); //Poll driver's left axes to drive

        // Dual-stick code from 2018 PowerUp
        // Gives precedence to LS, runs slower RS otherwise
        // SmartDashboard.putString("Rotation Value", ""+ RobotContainer.drive.getRotation());
        if (Math.abs(Robot.robotContainer.getDriverAxis(Axis.LEFT_X)) >= .05
                || Math.abs(Robot.robotContainer.getDriverAxis(Axis.LEFT_Y)) >= .05) {
            RobotContainer.drive.arcadeDrive(Robot.robotContainer.getDriverAxis(Axis.LEFT_Y) * .85,
                    Robot.robotContainer.getDriverAxis(Axis.LEFT_X) * .95);
        } else if (Math.abs(Robot.robotContainer.getDriverAxis(Axis.RIGHT_X)) >= .15
                || Math.abs(Robot.robotContainer.getDriverAxis(Axis.RIGHT_Y)) >= .15) {
                    RobotContainer.drive.arcadeDrive(Robot.robotContainer.getDriverAxis(Axis.RIGHT_Y) * .3,
                    Robot.robotContainer.getDriverAxis(Axis.RIGHT_X) * .45);
        } else {
            RobotContainer.drive.setDriveBoth(0);
        }

        // SmartDashboard.putString("distance left", ""+RobotContainer.drive.getLeftDistance());
		// SmartDashboard.putString("distance right", ""+RobotContainer.drive.getRightDistance());
        // System.out.println(Robot.drive.getRotation());
        // System.out.println(RobotContainer.drive.getCanEncoder()); // prints value
    }

    @Override
    public boolean isFinished() {
        return false; // Never finish. There's no reason to
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.drive.setBrakeMode(NeutralMode.Brake);
        RobotContainer.drive.setDriveBoth(Constants.ZERO_SPEED); // Stop moving if this command ends for some reason (e.g.interrupt for climb)
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return m_requirements;
    }
}