package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class DriveSystem {
    private CANSparkMax Motor_BLT;
    private CANSparkMax Motor_BRT;
    private CANSparkMax Motor_FLT;
    private CANSparkMax Motor_FRT;

    public DriveSystem(){
        this.Motor_BLT = new CANSparkMax(RobotConstants.Motor_Channel_BLT, MotorType.kBrushed);
        this.Motor_BRT = new CANSparkMax(RobotConstants.Motor_Channel_BRT, MotorType.kBrushed);
        this.Motor_FLT = new CANSparkMax(RobotConstants.Motor_Channel_FLT, MotorType.kBrushed);
        this.Motor_FRT = new CANSparkMax(RobotConstants.Motor_Channel_FRT, MotorType.kBrushed);


        this.Motor_BLT.setInverted(false);                                                                                                                                                                                                                                                                                                                                                                                              
        this.Motor_BRT.setInverted(true);
        this.Motor_FLT.setInverted(false);
        this.Motor_FRT.setInverted(true);
    }

    public void update(double Lmove, double Rmove){
        this.Motor_FLT.set(Lmove);
        this.Motor_BLT.set(Lmove);
        this.Motor_FRT.set(Rmove);
        this.Motor_BRT.set(Rmove);

    }
}

