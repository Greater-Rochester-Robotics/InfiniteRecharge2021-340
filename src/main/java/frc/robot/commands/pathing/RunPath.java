package frc.robot.commands.pathing;

import java.util.function.Function;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;
import frc.robot.RobotContainer;


public class RunPath extends CommandBase {
	private final double arcDivisor = 15;

	private double leftSpeed = 0;
	private double rightSpeed = 0;
	private boolean resetGyro = true;
	private boolean die = false;
	// private double length = -1;

	// private boolean reset = true;

	private final Path path;

	private final Function<Double, Double> speed;

	// private Animation animation;

	public RunPath(final Path path, final double speed) {
		// Use addRequirements() here to declare subsystem dependencies
		// eg. addRequirements(RobotContainer.chassis);
		addRequirements(RobotContainer.drive);
		this.path = path;
		this.leftSpeed = -speed;
		this.rightSpeed = -speed;
		this.speed = x -> speed;
		this.schedule(false); // makes it so that this command cannot be interrupted by another command that
								// contains the same requirements
	}

	public RunPath(final Path path, final Function<Double, Double> speed) {
		// Uses addRequirements() here to declare subsystem dependencies
		// eg. addRequirements(RobotContainer.chassis);
		addRequirements(RobotContainer.drive);
		this.path = path;
		this.speed = speed;
		this.leftSpeed = speed.apply(0.0);
		this.rightSpeed = speed.apply(0.0);
		// this.schedule(false);
	}

	public RunPath(final Path path, boolean resetGyro) {
		this(path, path.getSpeed());
		this.resetGyro = resetGyro;
	}
	public RunPath(final Path path) {
		this(path, path.getSpeed());
	}

	public RunPath(final Path path, final SpeedGenerator speedGenerator) {
		this(path, speedGenerator.getSpeedFunction(path));
	}

	// public RunPath(Path path, Function<Double, Double> speed, Animation
	// animation) {
	// addRequirements(RobotContainer.drive);
	// this.path = path;
	// this.speed = speed;
	// this.leftSpeed = speed.apply(0.0);
	// this.rightSpeed = speed.apply(0.0);
	// this.animation = animation;

	// this.schedule(false);
	// }

	public double dydx(final double s) {
		final PathSegment segment = path.getPathAtDistance(s);
		return segment.getDerivative().apply((s - path.getTotalOfCompletedPaths(s)) / segment.getLength());
	}

	// Called just before this Command runs the first time
	@Override
	public void initialize() {
		RobotContainer.drive.setDriveBoth(leftSpeed * .15, rightSpeed * .15);
		RobotContainer.drive.resetBothEncoders();
		if (this.resetGyro) {
			RobotContainer.drive.gyroReset();
		}
		System.out.println("RUNPATH INIT");
	}

	private double getDistance() {
		return Math.abs((RobotContainer.drive.getRightDistance() + RobotContainer.drive.getLeftDistance()) / 2);
	}

	private double deltaAngle(final double currentAngle) {
		final double currentSlope = Math.tan(currentAngle * Math.PI / 180);
		final double nextSlope = dydx(getDistance());

		final double angle = Math.atan((nextSlope - currentSlope) / (1 + currentSlope * nextSlope)) * 180 / Math.PI;

		// System.out.println("m1: " + currentSlope + " m2: " + nextSlope + " dTheta: " + angle);
		// System.out.println("Encoder: " + getDistance() + " dydx: " + dydx(getDistance()));
		return angle;
	}

	public double speed() {
		// System.out.println(-speed.apply(getDistance()/path.getTotalLength()));
		return -speed.apply(getDistance() / path.getTotalLength());
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {

		leftSpeed = speed();
		rightSpeed = speed();
		// System.out.println("error: " + error);
		
		if (Math.abs(getDistance()) > 3) {
			if(RobotContainer.drive.getLeftDistance() != 0.0 && RobotContainer.drive.getRightDistance() != 0.0){
				final double error = -deltaAngle(RobotContainer.drive.getRotation()); //may be radians
				SmartDashboard.putString("Path error", "" + error);
				if(Math.abs(error) < 180){
					final double speed = leftSpeed;
					final double ls = (leftSpeed + ((error) / (arcDivisor / Math.abs(speed))));
					final double rs = (rightSpeed - ((error) / (arcDivisor / Math.abs(speed))));
					RobotContainer.drive.setDriveBoth(ls * .6/* < .15 ? .15 : ls *.69 */, rs * .6/* < .15 ? .15 : rs *.69 */);

					// animate based off of distance, from 0.0 to 1.0
					// if (animation != null) {
					// animation.animate(getDistance() / path.getTotalLength());

					// for (Keyframe kf : animation) {
					// // addSequential(kf.getCommandConsumer().getCommand());
					// }
					// }

				}
				else{
					RobotContainer.drive.setDriveBoth(0,0);
					die = true;
					System.err.println("Stopping path robot spinning");	
				}
			}
			else{
				RobotContainer.drive.setDriveBoth(0,0);
				die = true;
				System.err.println("Stopping path encoder unplugged");
			}
		} else {
			RobotContainer.drive.setDriveBoth(leftSpeed * .6/* < .15 ? .15 : leftSpeed *.69 */,
					rightSpeed * .6/* < .15 ? .15 : rightSpeed *.69 */);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		try {
			// System.out.println(path.getPathAtDistance(RobotContainer.drive.getRightDistance()).getLength());
			return Math.abs(getDistance()) > (path.getTotalLength())||die;
		} catch (final Exception e) {
            System.err.println("Unexpected error in RunPath.isFinished()");
            System.err.println(e);
            return true;
        }
    }

	// Called once after isFinished returns true
	@Override
	public void end(boolean interrupted) {
		RobotContainer.drive.setDriveBoth(0, 0);
		// if (animation != null)
		// 	animation.reset();
	}

}