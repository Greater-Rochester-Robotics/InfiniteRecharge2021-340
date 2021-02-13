/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//TODO: remember to use TODO: remember to use TODO: remember to use TODO: remember to use TODO: etc.
//recursive todo reminding you to use todo to locate things
package frc.robot;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveGyroReset;
import frc.robot.commands.DriveXOne;
import frc.robot.commands.GetSmol;
import frc.robot.commands.RotateToZero;
import frc.robot.commands.Auto.Auto340Command;
import frc.robot.commands.Auto.EasyShoot;
import frc.robot.commands.Climber.ClimberCoDriverFunction;
import frc.robot.commands.Climber.ClimberHardStop;
import frc.robot.commands.Climber.LeftClimberArmDown;
import frc.robot.commands.Climber.RightClimberArmDown;
import frc.robot.commands.Climber.Stop;
import frc.robot.commands.Harvester.PickHarvesterUp;
import frc.robot.commands.Harvester.SetHarvesterDown;
import frc.robot.commands.Harvester.SpitBalls;
import frc.robot.commands.LimelightCommands.LimelightOff;
import frc.robot.commands.LimelightCommands.LimelightOn;
import frc.robot.commands.LimelightCommands.ObtainDistance;
import frc.robot.commands.Shooter.FullInitShot;
import frc.robot.commands.Shooter.FullTrenchAutoShotTest;
import frc.robot.commands.Shooter.FullWallShot;
import frc.robot.commands.Shooter.LowGoal;
import frc.robot.commands.Shooter.PrepHoodShot;
import frc.robot.commands.Shooter.PrepWallShot;
import frc.robot.commands.Shooter.RaiseCobraHood;
import frc.robot.commands.Shooter.ShootWithLimelight;
import frc.robot.commands.Shooter.SmartLimeShot;
import frc.robot.commands.Shooter.SmartSlowShot;
import frc.robot.commands.Shooter.StopShoot;
import frc.robot.commands.Shooter.WallShot;
import frc.robot.commands.SnekLoader.ChoochedLoad;
import frc.robot.commands.SnekLoader.Load;
import frc.robot.commands.SnekLoader.Regurgitate;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Harvester;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SnekLoader;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  final Joystick driver = new Joystick(0);
  final Joystick coDriver = new Joystick(1);

  ////////////////////
  // DRIVER BUTTONS //
  ////////////////////

  final Button driverA = new JoystickButton(driver, 1);
  final Button driverB = new JoystickButton(driver, 2);
  final Button driverX = new JoystickButton(driver, 3);
  final Button driverY = new JoystickButton(driver, 4);
  final Button driverLB = new JoystickButton(driver, 5);
  final Button driverRB = new JoystickButton(driver, 6);
  final Button driverBack = new JoystickButton(driver, 7);
  final Button driverStart = new JoystickButton(driver, 8);
  final Button driverLS = new JoystickButton(driver, 9);
  final Button driverRS = new JoystickButton(driver, 10);
  final Button driverDUp = new DPad(driver, DPad.Direction.UP);
  final Button driverDDown = new DPad(driver, DPad.Direction.DOWN);
  final Button driverDLeft = new DPad(driver, DPad.Direction.LEFT);
  final Button driverDRight = new DPad(driver, DPad.Direction.RIGHT);
  final Button driverLTButton = new JoyTriggerButton(driver, .3, Axis.LEFT_TRIGGER);
  final Button driverRTButton = new JoyTriggerButton(driver, .3, Axis.RIGHT_TRIGGER);

  final Button driverDRightButton = new DPad(driver, DPad.Direction.RIGHT);

  ///////////////////////
  // CO-DRIVER BUTTONS //
  ///////////////////////

  final Button coDriverA = new JoystickButton(coDriver, 1);
  final Button coDriverB = new JoystickButton(coDriver, 2);
  final Button coDriverX = new JoystickButton(coDriver, 3);
  final Button coDriverY = new JoystickButton(coDriver, 4);
  final Button coDriverLB = new JoystickButton(coDriver, 5);
  final Button coDriverRB = new JoystickButton(coDriver, 6);
  final Button coDriverBack = new JoystickButton(coDriver, 7);
  final Button coDriverStart = new JoystickButton(coDriver, 8);
  final Button coDriverLS = new JoystickButton(coDriver, 9);
  final Button coDriverRS = new JoystickButton(coDriver, 10);
  final Button coDriverDUp = new DPad(coDriver, DPad.Direction.UP);
  final Button coDriverDDown = new DPad(coDriver, DPad.Direction.DOWN);
  final Button coDriverDLeft = new DPad(coDriver, DPad.Direction.LEFT);
  final Button coDriverDRight = new DPad(coDriver, DPad.Direction.RIGHT);
  final Button coDriverLTButton = new JoyTriggerButton(coDriver, .7, Axis.LEFT_TRIGGER);
  final Button coDriverRTButton = new JoyTriggerButton(coDriver, .7, Axis.RIGHT_TRIGGER);

  IsCommandRunningTrigger isClimberRunning;

  public static SnekLoader snekLoader;
  public static Harvester harvester;
  public static Drive drive;
  public static Shooter shooter;
  // public static ColorWheel colorWheel;
  public static Limelight limelight;
  public static Climber climber;

  public static final boolean isFalconFx = true;
  public static boolean testButtonsBound = false;
  public static SendableChooser<String> autoChooser;
  public static Map<String, Auto340Command> autoModes = new HashMap<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    drive = new Drive();
    CommandScheduler.getInstance().setDefaultCommand(drive, new DriveXOne());
    shooter = new Shooter();
    // colorWheel = new ColorWheel();
    harvester = new Harvester();
    snekLoader = new SnekLoader();
    limelight = new Limelight();
    limelight.setStreamMode(0);
    limelight.setLightState(1);
    climber = new Climber();
    isClimberRunning = new IsCommandRunningTrigger(climber, new ClimberCoDriverFunction());
    // Configure the button bindings
    configureButtonBindings();

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Testing buttons
    // driverBack.whenPressed(new PlayMusic());
    // driverBack.whenPressed(new StopMusic());
    // driverDRight.whenPressed(new TrenchFiveBall());
    // driverDLeft.whenPressed(new FullTrenchRun());
    // driverY.whenPressed(new ToggleTestMode());

    /* Actual Comp button layout */
    driverA.whenPressed(new Load());
    driverA.whenReleased(new GetSmol());
    
    driverB.whenPressed(new LimelightOn());
    driverB.whenReleased(new LimelightOff());
    driverX.whenPressed(new Regurgitate());
    driverX.whenReleased(new GetSmol());
    driverY.whenPressed(new ChoochedLoad());
    driverY.whenReleased(new GetSmol());
    driverDUp.whenPressed(new PickHarvesterUp());
    driverDDown.whenPressed(new SetHarvesterDown());
    driverDLeft.whileHeld(new ClimberHardStop());
    driverDRight.whenPressed(new RaiseCobraHood());
    // driverStart.whenPressed(new GetSmol());
    driverBack.whenPressed(new StopShoot());
    driverRS.whenPressed(new GetSmol());
    driverRB.whenPressed(new FullInitShot());
    driverRB.whenReleased(new GetSmol());
    driverLB.whenPressed(new LowGoal());
    driverLB.whenReleased(new GetSmol());
    driverRTButton.and(driverStart.negate()).whenActive(new SmartLimeShot());
    driverRTButton.and(driverStart).whenActive(new SmartSlowShot());
    driverRTButton.whenReleased(new GetSmol());
    driverLTButton.and(driverStart.negate()).whenActive(new FullWallShot());
    driverLTButton.and(driverStart).whenActive(new WallShot());
    //driverLTButton.whenPressed(new FullWallShot());
    driverLTButton.whenReleased(new GetSmol());
    
    //coDriverB.whenPressed(new ObtainDistance());
    coDriverA.whenPressed(new LowGoal());
    coDriverA.whenReleased(new GetSmol());
    coDriverY.whenPressed(new DriveGyroReset());
    coDriverB.whenPressed(new PrepHoodShot().withTimeout(1.5));
    coDriverX.whenPressed(new PrepWallShot().withTimeout(1.5));
    coDriverDLeft.whenPressed(new RotateToZero());
    coDriverBack.whenPressed(new StopShoot());
    

    coDriverDDown.toggleWhenPressed(new ClimberCoDriverFunction());

  }

  public void bindTestButtons() {
    if (!testButtonsBound) {
      testButtonsBound = true;
      // put test buttons here for test controller

    }
  }

  public enum Axis {
    LEFT_X(0), LEFT_Y(1), LEFT_TRIGGER(2), RIGHT_TRIGGER(3), RIGHT_X(4), RIGHT_Y(5);

    private int axis;

    private Axis(int axis) {
      this.axis = axis;
    }

    public int getAxisNumber() {
      return axis;
    }
  }

  /**
   * 
   * @param axis
   * @return
   */
  public double getDriverAxis(Axis axis) {
    return (driver.getRawAxis(axis.getAxisNumber()) < -.1 || driver.getRawAxis(axis.getAxisNumber()) > .1)
        ? driver.getRawAxis(axis.getAxisNumber())
        : 0;
  }

  /**
   * Accessor method to set driver rumble function
   * 
   * @param leftRumble
   * @param rightRumble
   */
  public void setDriverRumble(double leftRumble, double rightRumble) {
    driver.setRumble(RumbleType.kLeftRumble, leftRumble);
    driver.setRumble(RumbleType.kRightRumble, rightRumble);
  }

  /**
   * 
   * @param axis
   * @return
   */
  public double getCoDriverAxis(Axis axis) {
    return (coDriver.getRawAxis(axis.getAxisNumber()) < -.1 || coDriver.getRawAxis(axis.getAxisNumber()) > .1)
        ? coDriver.getRawAxis(axis.getAxisNumber())
        : 0;
  }

  /**
   * Accessor method to set codriver rumble function
   * 
   * @param leftRumble
   * @param rightRumble
   */
  public void setCoDriverRumble(double leftRumble, double rightRumble) {
    coDriver.setRumble(RumbleType.kLeftRumble, leftRumble);
    coDriver.setRumble(RumbleType.kRightRumble, rightRumble);
  }

  public boolean getCoDriverButton(int buttonNum) {
    return coDriver.getRawButton(buttonNum);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // = Shuffleboard.getTab("Competition").get
    String mode = RobotContainer.autoChooser.getSelected();
    SmartDashboard.putString("Chosen Auto Mode", mode);
    return autoModes.getOrDefault(mode, new EasyShoot());//new Command();

    // return new TrenchFiveBall();
  }
}
