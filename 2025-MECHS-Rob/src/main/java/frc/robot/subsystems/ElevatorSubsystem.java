// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Rename the class to the name of the subsystem that you're working on.
// In this case, since we're working on the elevator, we can rename this
// to something like ElevatorSubsystem

	// This is the constructor method. Make sure to rename this to
	// EXACTLY match the name that you give to the class, including
	// capitalization.


package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;


public class ElevatorSubsystem extends SubsystemBase {

  private SparkMax motor0;
  private SparkMax motor1;

  /** Creates a new ExampleSubsystem. */
  public ElevatorSubsystem() {

    this.motor0 = new SparkMax(13, MotorType.kBrushless);
    this.motor1 = new SparkMax(14, MotorType.kBrushless);
  }

    

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command ElevatorMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  public Command moveUp() {
    return startEnd(
      () -> {
        this.motor0.ElevatorSubsystem;
        this.motor1.ElevatorSubsystem;

      },

      () -> {

        this.motor0.ElevatorSubsystem;
        this.motor1.ElevatorSubsystem;
      }
    );

  }

  public Command moveDown() {
    return startEnd(
      ()-> {
        this.motor0.ElevatorSubsystem;
        this.motor1.ElevatorSubsystem;
      },

      ()-> {

        this.motor0.ElevatorSubsystem ;
        this.motor1.ElevatorSubsystem;
      }
   );

  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */

  

  public boolean ElevatorCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
