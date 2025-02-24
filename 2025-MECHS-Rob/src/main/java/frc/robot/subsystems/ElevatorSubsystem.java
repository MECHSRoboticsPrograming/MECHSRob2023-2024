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

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.logging.Level;

import javax.lang.model.util.ElementScanner14;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;


public class ElevatorSubsystem extends SubsystemBase {

  private SparkMax motorR;
  private RelativeEncoder encoderL;
  private PIDController pid;
  private SparkMax motorL;
  
  private double levelNumber = 0; //intializes elevator level

  private double COUNTS_PER_INCH = 95.0; //Number of rotations the motor makes to move the elevaor one inch. 1.76in diameter pitch, 25:1 reduction, cascade, 42 counts per rev
  private double GRAVITY_COMPENSATION = 0.1; //Compensates for force of gravity in PID

  /** Creates a new ExampleSubsystem. */
  public ElevatorSubsystem() {

    this.motorR = new SparkMax(13, MotorType.kBrushless);
    this.motorL = new SparkMax(14, MotorType.kBrushless); //Need to update so that motor 14 follows motor 13

    this.encoderL = motorL.getEncoder();
    this.pid = new PIDController(0.1, 0, 0); //Need to tune

  }

  public double getHeight() {
    return encoderL.getPosition() / COUNTS_PER_INCH;
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

  public void setPosition(double targetHeight) {
    //Calculates pid
    double pidOutput = pid.calculate(getHeight(), targetHeight);

    //Adds gravity compensation
    double motorOutput = pidOutput + GRAVITY_COMPENSATION;

    //Ensures output is within max speed
    motorOutput = Math.min(Math.max(motorOutput, -1.0), 1.0);

    //Sets motor speed
    motorL.set(motorOutput);
  }

  public Command elevatorIncrement() {
    //Increments desired elevator level, then sets position
    return runOnce(
      () -> {
        levelNumber += 1;
        if (levelNumber == 1) {
          setPosition(20.0);
        }
        else if (levelNumber == 2) {
          setPosition(40.0);
        }
        else if (levelNumber == 3) {
          setPosition(60.0);
        }
        else if (levelNumber == 4) {
          setPosition(70);
        }
        else if (levelNumber == 5) {
          setPosition(0);
          levelNumber = 0;
        }
      });
  }

  public Command elevatorReturnZero() {
    //Returns the elevator to zero
    return runOnce(
      () -> {
        setPosition(0.0);
        levelNumber = 0;
      }
    );
  }

  public Command moveUp() {
    return startEnd(
      () -> {
        this.motorR.set(1);
        this.motorL.set(-1);

      },

      () -> {

        this.motorR.set(0);
        this.motorL.set(0);
      }
    );

  }

  public Command moveDown() {
    return startEnd(
      ()-> {
        this.motorR.set(-1);
        this.motorL.set(1);
      },

      ()-> {

        this.motorR.set(0); 
        this.motorL.set(0);
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
