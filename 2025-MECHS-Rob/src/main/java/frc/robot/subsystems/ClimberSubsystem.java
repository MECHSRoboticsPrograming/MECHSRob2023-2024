package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;


public class ClimberSubsystem extends SubsystemBase {
    private SparkMax climberMotor;

    public ClimberSubsystem() {
        this.climberMotor = new SparkMax(15, MotorType.kBrushless);
    }

    public Command deployClimber() {
        return startEnd(() -> climberMotor.set(1), () -> climberMotor.set(0));
    }

    public Command retractClimber() {
        return startEnd(() -> climberMotor.set(-1), () -> climberMotor.set(0));
    }
}