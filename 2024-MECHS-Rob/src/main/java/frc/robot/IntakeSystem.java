package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
public class IntakeSystem {
    private VictorSPX Motor_Intake;

    public IntakeSystem() {
        
        this.Motor_Intake = new VictorSPX(RobotConstants.Motor_Channel_Intake);
    }

    public void update(boolean Intake_Status, boolean reverseIntakeButton) {
        if (Intake_Status == true) {
            this.Motor_Intake.setInverted(false);
            this.Motor_Intake.set(VictorSPXControlMode.PercentOutput, 0.5);
       } else if (reverseIntakeButton == true) {
            this.Motor_Intake.setInverted(true);
            this.Motor_Intake.set(VictorSPXControlMode.PercentOutput, 0.5);
        } else {
            this.Motor_Intake.set(VictorSPXControlMode.PercentOutput, 0.0);
        }
        // Posible logic issue here could better nest the inverted toggle value
    }
}
