// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final double DEADBAND = 0.05;
  }
  //public static final double maxSpeed = Units.feetToMeters(15);
    public static final double maxSpeed = 15;

  public static class ElevatorConstants {
    public static final double maxExtension = 72;
    public static final double minExtension = 0;
    public static final double l1Setpoint = 20;
    public static final double l2Setpoint = 40;
    public static final double l3Setpoint = 60;
    public static final double l4Setpoint = 72;
    public static final double stationSetpoint = 22;
  }

  public static final class EndEffectorConstants {
    public static final double elbowMaxAngle = 60;
    public static final double elbowMinAngle = 0;
    public static final double wristMaxAngle = 90;
    public static final double wristMinAngle = 0;
  }

  public static final class CanIDConstants {
    public static final int intakeMotorCan = 9;
    public static final int intakeMotorFollowerCan = 10;
    public static final int wristMotorCan = 11;
    public static final int elbowMotorCan = 12;
    public static final int elevatorMotorRCan = 13;
    public static final int elevatorMotorLCan = 14;
    public static final int climberMotorCan = 15;
  }
}
