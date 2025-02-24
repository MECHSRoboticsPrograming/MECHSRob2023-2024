package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class CoralEndEffector extends SubsystemBase {

    // Motors for controlling the wrist, elbow, and intake
    private SparkMax wristMotor;
    private RelativeEncoder wristEncoder;

    private SparkMax elbowMotor;
    private RelativeEncoder elbowEncoder;

    private SparkMax intakeMotor;
    private SparkMax intakeMotorFollower; //Need to set this one to follow the other

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

        this.elbowPid = new PIDController(0.1, 0, 0);
        this.wristPid = new PIDController(0.1, 0, 0);
    }

    public double getElbowAngle() {
        return elbowEncoder.getPosition() / ELBOW_COUNTS_PER_DEGREE;
    }

    public double getWristAngle() {
        return wristEncoder.getPosition() / WRIST_COUNTS_PER_DEGREE;
    }

    public void setElbowAngle(double targetElbowAngle) {
        double elbowPidOutput = elbowPid.calculate(getElbowAngle(), targetElbowAngle);
        double elbowMotorOutput = elbowPidOutput + GRAVITY_COMPENSATION;

        elbowMotorOutput = Math.min(Math.max(elbowMotorOutput, -1.0), 1.0);
        elbowMotor.set(elbowMotorOutput);
    }

    public void setWristAngle(double targetWristAngle) {
        double wristPidOutput = wristPid.calculate(getWristAngle(), targetWristAngle);

        double wristMotorOutput = Math.min(Math.max(wristPidOutput, -1.0), 1.0);
        wristMotor.set(wristMotorOutput);
    }

    public Command toggleElbow() {
        if (elbowAngle == 0) {
            elbowAngle = 60;
        }
        else {
            elbowAngle = 0;
        }
        return runOnce(() -> setElbowAngle(elbowAngle));
    }

    public Command toggleWrist() {
        if (wristAngle == 0) {
            wristAngle = 90;
        }
        else {
            wristAngle = 0;
        }
        return runOnce(() -> setWristAngle(wristAngle));
    }

    // Functionality to rotate wrist
    public Command rotateWristLeft() {
        return startEnd(() -> wristMotor.set(-0.5), () -> wristMotor.set(0));
    }

    public Command rotateWristRight() {
        return startEnd(() -> wristMotor.set(0.5), () -> wristMotor.set(0));
    }

    // Functionality to rotate elbow
    public Command rotateElbowUp() {
        return startEnd(() -> elbowMotor.set(0.5), () -> elbowMotor.set(0));
    }

    public Command rotateElbowDown() {
        return startEnd(() -> elbowMotor.set(-0.5), () -> elbowMotor.set(0));
    }

    // Functionality for coral intake
    public Command intakeCoral() {
        return startEnd(() -> {
            intakeMotor.set(1);
            intakeMotorFollower.set(1);
        }, () -> {
            intakeMotor.set(0);
            intakeMotorFollower.set(0);
        });
    }

    public Command ejectCoral() {
        return startEnd(() -> {
            intakeMotor.set(-1);
            intakeMotorFollower.set(-1);
        }, () -> {
            intakeMotor.set(0);
            intakeMotorFollower.set(0);
        });
    }
}


