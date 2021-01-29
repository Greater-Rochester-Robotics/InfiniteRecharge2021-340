/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.Auto.Auto340Command;
import frc.robot.commands.Auto.BarrelRace;
import frc.robot.commands.Auto.BouncyPath;
import frc.robot.commands.Auto.ColorWheelSteal;
import frc.robot.commands.Auto.EasyShoot;
import frc.robot.commands.Auto.FullTrenchRun;
import frc.robot.commands.Auto.HungryHippoShot;
import frc.robot.commands.Auto.SixBallTrench;
import frc.robot.commands.Auto.TheEighthHokage;
import frc.robot.commands.Auto.TrenchFiveBall;
import frc.robot.commands.Auto.TwoBallShot;
import frc.robot.commands.Climber.ClimberCoDriverFunction;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {


  
  public static RobotContainer robotContainer;
  private Command m_autonomousCommand;
  NetworkTableEntry autoEntry = null;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    robotContainer = new RobotContainer();
    // String configJson = "{\"width\":40,\"height\":40,\"fps\":10}";//\"pixel format\":\"MJPEG\",
    CameraServer.getInstance().startAutomaticCapture();//.setConfigJson(configJson);//setResolution(150, 150);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    RobotContainer.limelight.setLightState(1);
    if (null == RobotContainer.autoChooser){
      RobotContainer.autoChooser = new SendableChooser<>();
    }
    RobotContainer.autoChooser.setDefaultOption("Easy Shoot", "Easy Shoot");
    RobotContainer.autoModes.put("Easy Shoot", new EasyShoot());

    registerAutoMode(RobotContainer.autoChooser, "Color Wheel Steal", new ColorWheelSteal());

    registerAutoMode(RobotContainer.autoChooser, "Hungry Hippo Shot", new HungryHippoShot());

    registerAutoMode(RobotContainer.autoChooser, "Trench Five Ball", new TrenchFiveBall());

    registerAutoMode(RobotContainer.autoChooser, "Full Trench Run", new FullTrenchRun());

    registerAutoMode(RobotContainer.autoChooser, "Two Ball Shot", new TwoBallShot());

    registerAutoMode(RobotContainer.autoChooser, "Six Ball Trench", new SixBallTrench());
    
    registerAutoMode(RobotContainer.autoChooser, "Magic Eight Ball", new TheEighthHokage());

    registerAutoMode(RobotContainer.autoChooser, "Barrel Racing 64", new BarrelRace());

    registerAutoMode(RobotContainer.autoChooser, "Bouncy Path", new BouncyPath());
    
    SmartDashboard.putData(RobotContainer.autoChooser);
    // maybe the heck stuff here to make sure its only done once:
    //Shuffleboard.getTab("Competition").add(RobotContainer.autoChooser);

  }

  private void registerAutoMode(SendableChooser<String> chooser, String auto, Auto340Command autoCmd) {
    chooser.addOption(auto, auto);
    RobotContainer.autoModes.put(auto, autoCmd);
  }

  @Override
  public void disabledPeriodic() {
    RobotContainer.limelight.setLightState(1);
    String mode = RobotContainer.autoChooser.getSelected();
    // SmartDashboard.putString("AutoInstrutions", RobotContainer.autoModes.get(mode6+).getAutoDescription());
    SmartDashboard.putString("Chosen Auto Mode", mode);
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = robotContainer.getAutonomousCommand();
    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // String mode = RobotContainer.autoChooser.getSelected();
    // SmartDashboard.putString("Chosen Auto Mode", mode);

  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    // robotContainer.bindTestButtons();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
