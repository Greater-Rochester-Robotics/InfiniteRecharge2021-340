/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    private static VictorSPX leftArm, rightArm;
    private static Solenoid brakeSolenoid;
  /**
   * Creates a new Climber.
   */
  public Climber() {
        leftArm = new VictorSPX(Constants.LEFT_CLIMBER);
        rightArm = new VictorSPX(Constants.RIGHT_CLIMBER);
        rightArm.setInverted(true);
        // rightArm.set(ControlMode.Follower, Constants.CLIMBER_LEFT_ARM);
        leftArm.configVoltageCompSaturation(12.8);
        leftArm.enableVoltageCompensation(true);
        rightArm.configVoltageCompSaturation(12.8);
        rightArm.enableVoltageCompensation(true);

        leftArm.setNeutralMode(NeutralMode.Brake);
        rightArm.setNeutralMode(NeutralMode.Brake);

        brakeSolenoid = new Solenoid(Constants.CLIMBER_BRAKE_CHANNEL);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // public void armsContract() {
  //   leftArm.set(ControlMode.PercentOutput, Constants.CLIMBER_CONTRACT_SPEED);
  //   rightArm.set(ControlMode.PercentOutput, Constants.CLIMBER_CONTRACT_SPEED);
  // }

  public void leftArmExtend() {
    leftArm.set(ControlMode.PercentOutput, Constants.CLIMBER_EXTEND_SPEED);

  }
  public void rightArmExtend() {
    rightArm.set(ControlMode.PercentOutput, Constants.CLIMBER_EXTEND_SPEED);
  }

  public void stop() {
    leftArm.set(ControlMode.PercentOutput, 0.0);
    rightArm.set(ControlMode.PercentOutput, 0.0);
  }

  public void leftStop() {
    leftArm.set(ControlMode.PercentOutput, 0.0);
  }  
  
  public void rightStop() {
    rightArm.set(ControlMode.PercentOutput, 0.0);
  }
  public void hardStop(){
    leftArm.set(ControlMode.PercentOutput, -0.20);
    rightArm.set(ControlMode.PercentOutput, -0.20); 
  }

  public void leftArmContract() {
    leftArm.set(ControlMode.PercentOutput, Constants.CLIMBER_CONTRACT_SPEED);
  }

  public void rightArmContract() {
    rightArm.set(ControlMode.PercentOutput, Constants.CLIMBER_CONTRACT_SPEED);

  }
  
  public void engageBrake(){
    brakeSolenoid.set(false);
  }

  public void releaseBrake(){
    brakeSolenoid.set(true);
  }
}
