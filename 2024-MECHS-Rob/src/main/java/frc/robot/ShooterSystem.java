package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ShooterSystem {
    private CANSparkMax Shooter_Motor;
    public ShooterSystem(){
        this.Shooter_Motor = new CANSparkMax(RobotConstants.Motor_Channel_Shooter, MotorType.kBrushed);
    }
    public void update(double speakerShooterTrigger, double ampShooterTrigger, boolean reverseShooterButton){
        if (speakerShooterTrigger > 0.5){
            this.Shooter_Motor.set(0.5); 
        } else{
            this.Shooter_Motor.set(0);
        }

        if (ampShooterTrigger > 0.5) {
            this.Shooter_Motor.set(0.2);
            
        } else {
            this.Shooter_Motor.set(0);
        }

        if(reverseShooterButton = true){
            this.Shooter_Motor.setInverted(true);
            this.Shooter_Motor.set(.2);
        }else{
            this.Shooter_Motor.set(0);
        }
    }

}
