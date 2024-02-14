package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class IntakeSystem {
    private CANSparkMax Motor_Intake;

    public IntakeSystem(){
        this.Motor_Intake = new CANSparkMax(RobotConstants.Motor_Channel_Intake, MotorType.kBrushed);
    }

    public void update(boolean Intake_Status, boolean reverseIntakeButton){
        if (Intake_Status == true){
            this.Motor_Intake.set(.5);
        } else if (reverseIntakeButton == true) {
            this.Motor_Intake.setInverted(true);
            this.Motor_Intake.set(0.5);
        } else{
            this.Motor_Intake.set(0);
        }
        //Posible logic issue here could better nest the inverted toggle value
    }
}
