package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Commands;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class CoralEndEffector extends SubsystemBase {

    // Motors for controlling the wrist, elbow, and intake
    private final SparkMax wristMotor;
    private RelativeEncoder wristEncoder;

    private final SparkMax elbowMotor;
    private RelativeEncoder elbowEncoder;

    private final SparkMax intakeMotor;
    private final SparkMax intakeMotorFollower; //Need to set this one to follow the other

    private double elbowAngle = 0;
    private double wristAngle = 0;


    private PIDController elbowPid;
    private PIDController wristPid;
    private double ELBOW_COUNTS_PER_DEGREE = 1.867;
    private double WRIST_COUNTS_PER_DEGREE = 2.9167; //Counts of hall effect sensor for one degree
    private double GRAVITY_COMPENSATION  = 0.1; //Compensates for gravity

    public CoralEndEffector() {
        // Linking motors to CAN IDs
        wristMotor = new SparkMax(11, MotorType.kBrushless); // Adjust CAN IDs accordingly
        elbowMotor = new SparkMax(12, MotorType.kBrushless);
        intakeMotor = new SparkMax(9, MotorType.kBrushless);
        intakeMotorFollower = new SparkMax(10, MotorType.kBrushless); 

        this.elbowEncoder = elbowMotor.getEncoder();
        this.wristEncoder = wristMotor.getEncoder();
    }

    // Functionality to rotate wrist
    public Command rotateWristLeft() {
        return Commands.startEnd(() -> wristMotor.set(-0.5), () -> wristMotor.set(0));
    }

    public Command rotateWristRight() {
        return Commands.startEnd(() -> wristMotor.set(0.5), () -> wristMotor.set(0));
    }

    // Functionality to rotate elbow
    public Command rotateElbowUp() {
        return Commands.startEnd(() -> elbowMotor.set(0.5), () -> elbowMotor.set(0));
    }

    public Command rotateElbowDown() {
        return Commands.startEnd(() -> elbowMotor.set(-0.5), () -> elbowMotor.set(0));
    }

    // Functionality for coral intake
    public Command intakeCoral() {
        return Commands.startEnd(() -> {
            intakeMotor.set(1);
            intakeMotorFollower.set(1);
        }, () -> {
            intakeMotor.set(0);
            intakeMotorFollower.set(0);
        });
    }

    public Command ejectCoral() {
        return Commands.startEnd(() -> {
            intakeMotor.set(-1);
            intakeMotorFollower.set(-1);
        }, () -> {
            intakeMotor.set(0);
            intakeMotorFollower.set(0);
        });
    }
}


