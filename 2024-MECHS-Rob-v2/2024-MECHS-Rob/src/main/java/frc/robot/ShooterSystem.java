package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ShooterSystem {
    private CANSparkMax Shooter_Motor1;
    private CANSparkMax Shooter_Motor2;
    public ShooterSystem(){
        this.Shooter_Motor1 = new CANSparkMax(RobotConstants.Motor_Channel_Shooter_1, MotorType.kBrushed);
        this.Shooter_Motor2 = new CANSparkMax(RobotConstants.Motor_Channel_Shooter_2, MotorType.kBrushed);
    }
    public void update(double speakerShooterTrigger, double ampShooterTrigger, boolean reverseShooterButton){
       
        if (speakerShooterTrigger > 0.5){
            this.shoot();
        } else if( ampShooterTrigger > 0.5){
            this.Shooter_Motor1.setInverted(false);
            this.Shooter_Motor1.set(0.5);
            this.Shooter_Motor2.setInverted(true);
            this.Shooter_Motor2.set(0.5);
        } else if (reverseShooterButton == true){
            this.Shooter_Motor1.setInverted(true);
            this.Shooter_Motor1.set(0.75);
            this.Shooter_Motor2.setInverted(false);
            this.Shooter_Motor2.set(0.75);
        }
        else{
            this.Shooter_Motor1.set(0);
            this.Shooter_Motor2.set(0);
        }
    }

    public void shoot() {
        this.Shooter_Motor1.setInverted(false);
        this.Shooter_Motor1.set(1.0); 
        this.Shooter_Motor2.setInverted(true);
        this.Shooter_Motor2.set(1.0);
    }
}
