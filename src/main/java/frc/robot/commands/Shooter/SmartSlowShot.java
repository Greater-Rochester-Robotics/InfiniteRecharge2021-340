package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.LimelightCommands.AutoAlign;
import frc.robot.commands.LimelightCommands.LimelightOff;
import frc.robot.commands.LimelightCommands.LimelightOn;

public class SmartSlowShot extends SequentialCommandGroup {
    /**
     * Creates a new LimeHoodShot.
     */
    public SmartSlowShot() {
      // Add your commands in the super() call, e.g.
      // super(new FooCommand(), new BarCommand());
      //Make a new limelight command dealing with all of the shooting and getting of distance
      super(new LimelightOn(), new AutoAlign() ,new ShootWithLimelight(), new LimelightOff());
    }
  }
  