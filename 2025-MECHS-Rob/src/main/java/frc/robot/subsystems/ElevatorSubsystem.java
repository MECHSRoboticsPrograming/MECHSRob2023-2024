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

//import java.util.logging.Level;
//import javax.lang.model.util.ElementScanner14;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import static frc.robot.Constants.CanIDConstants.*;
import static frc.robot.Constants.ElevatorConstants.*;

public class ElevatorSubsystem extends SubsystemBase {

  private SparkMax elevatorMotorR;
  private RelativeEncoder encoderL;
  private PIDController elevatorPid;
  private SparkMax elevatorMotorL;
  
  private double levelNumber = 0; //intializes elevator level
  private double newHeight = 0; //used later to change height

  private double COUNTS_PER_INCH = 95.0; //Number of counts the hall effect sensor makes when the elevator moves one inch. 1.76in diameter pitch, 25:1 reduction, cascade, 42 counts per rev
  private double GRAVITY_COMPENSATION = 0.1; //Compensates for force of gravity in PID

  public ElevatorSubsystem() {
    this.elevatorMotorR = new SparkMax(elevatorMotorRCan, MotorType.kBrushless);
    this.elevatorMotorL = new SparkMax(elevatorMotorLCan, MotorType.kBrushless); //Need to update so that motor 14 follows motor 13
    
    this.encoderL = elevatorMotorL.getEncoder();
    this.elevatorPid = new PIDController(0.1, 0, 0); //Need to tune
  }

  public double getHeight() {
    return encoderL.getPosition() / COUNTS_PER_INCH;
  }

  public void setPosition(double targetHeight) {
    double pidOutput = elevatorPid.calculate(getHeight(), targetHeight);
    double motorOutput = pidOutput + GRAVITY_COMPENSATION;
    motorOutput = Math.min(Math.max(motorOutput, -1.0), 1.0); //ensures motor output is within valid range
    elevatorMotorL.set(motorOutput);
  }
  
  public Command elevatorReturnZero() {
      levelNumber = 0; //resets level number and returns to min height
      return runOnce(() -> setPosition(minExtension));
  }

  public Command elevatorIncrement() {
    levelNumber += 1; //increments level number first, then sets setpoint height
    if (levelNumber == 1) {
      newHeight = l1Setpoint;
    } else if (levelNumber == 2) {
      newHeight = l2Setpoint;
    } else if (levelNumber == 3) {
      newHeight = l3Setpoint;
    } else if (levelNumber == 4) {
      newHeight = l4Setpoint;
    } else {
      elevatorReturnZero();
    }
    return runOnce(() -> setPosition(newHeight));
  }

  //manual movement commands
  public Command moveUp() {
    return startEnd(
      () -> {
        this.elevatorMotorR.set(1);
        this.elevatorMotorL.set(-1);
      },
      () -> {
        this.elevatorMotorR.set(0);
        this.elevatorMotorL.set(0);
      }
    );
  }

  public Command moveDown() {
    return startEnd(
      ()-> {
        this.elevatorMotorR.set(-1);
        this.elevatorMotorL.set(1);
      },
      ()-> {
        this.elevatorMotorR.set(0); 
        this.elevatorMotorL.set(0);
      }
    );
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //SmartDashboard.putNumber("Elevator Height", getHeight());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}