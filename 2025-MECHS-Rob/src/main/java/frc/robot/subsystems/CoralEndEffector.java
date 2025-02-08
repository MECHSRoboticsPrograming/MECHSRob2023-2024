package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Commands;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class CoralEndEffector extends SubsystemBase {

    // Motors for controlling the wrist, elbow, and intake
    private final SparkMax wristMotor;
    private final SparkMax elbowMotor;
    private final SparkMax intakeMotor;

    public CoralEndEffector() {
        // Linking motors to CAN IDs
        wristMotor = new SparkMax(15, MotorType.kBrushless); // Adjust CAN IDs accordingly
        elbowMotor = new SparkMax(16, MotorType.kBrushless);
        intakeMotor = new SparkMax(17, MotorType.kBrushless);
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
        return Commands.startEnd(() -> intakeMotor.set(1), () -> intakeMotor.set(0));
    }

    public Command ejectCoral() {
        return Commands.startEnd(() -> intakeMotor.set(-1), () -> intakeMotor.set(0));
    }
}

