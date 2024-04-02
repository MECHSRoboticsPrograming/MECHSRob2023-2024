package frc.robot;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

public class ShooterSystem {
    private VictorSPX Shooter_Motor1;
    private VictorSPX Shooter_Motor2;
    public ShooterSystem(){
        this.Shooter_Motor1 = new VictorSPX(RobotConstants.Motor_Channel_Shooter_1);
        this.Shooter_Motor2 = new VictorSPX(RobotConstants.Motor_Channel_Shooter_2);
    }
    public void update(double speakerShooterTrigger, double ampShooterTrigger, boolean reverseShooterButton){
       
        if (speakerShooterTrigger > 0.5){
            this.shoot();
        } else if( ampShooterTrigger > 0.5){
            this.Shooter_Motor1.setInverted(false);
            this.Shooter_Motor1.set(VictorSPXControlMode.PercentOutput, 0.5);
            this.Shooter_Motor2.setInverted(true);
            this.Shooter_Motor2.set(VictorSPXControlMode.PercentOutput, 0.5);
        } else if (reverseShooterButton == true){
            this.Shooter_Motor1.setInverted(true);
            this.Shooter_Motor1.set(VictorSPXControlMode.PercentOutput, 0.75);
            this.Shooter_Motor2.setInverted(false);
            this.Shooter_Motor2.set(VictorSPXControlMode.PercentOutput, 0.75);
        }
        else{
            this.Shooter_Motor1.set(VictorSPXControlMode.PercentOutput, 0);
            this.Shooter_Motor2.set(VictorSPXControlMode.PercentOutput, 0);
        }
    }

    public void shoot() {
        this.Shooter_Motor1.setInverted(false);
        this.Shooter_Motor1.set(VictorSPXControlMode.PercentOutput, 1.0); 
        this.Shooter_Motor2.setInverted(true);
        this.Shooter_Motor2.set(VictorSPXControlMode.PercentOutput, 1.0);
    }
}
