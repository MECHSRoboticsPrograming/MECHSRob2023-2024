package frc.robot;


import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class IntakeSystem {
    private CANSparkMax  Motor_Intake;
    private CANSparkMax Motor_Intake2;
    private SparkPIDController pid_controller;

    public IntakeSystem() {
        
        this.Motor_Intake = new CANSparkMax(RobotConstants.Motor_Channel_Intake, MotorType.kBrushed);
        this.Motor_Intake2 = new CANSparkMax(RobotConstants.Motor_Channel_Intake2, MotorType.kBrushless);
        this.Motor_Intake.setInverted(false);
        this.Motor_Intake2.setInverted(true);
        this.pid_controller = this.Motor_Intake2.getPIDController();
    }

    public void update(boolean Intake_Status, boolean reverseIntakeButton) {
        if (Intake_Status == true) {
            this.feedNote();
       } else if (reverseIntakeButton == true) {
            this.reverse();
        } else {
            this.Motor_Intake.set(0);
            this.Motor_Intake2.set(0);
        }
        // Posible logic issue here could better nest the inverted toggle value
    }

    public void feedNote() {
        this.Motor_Intake.set(0.25);
        this.Motor_Intake2.set(0.25);
    }

    public void reverse() {
        this.Motor_Intake.set(-0.25);
        this.Motor_Intake2.set(-0.25);
    }
}
