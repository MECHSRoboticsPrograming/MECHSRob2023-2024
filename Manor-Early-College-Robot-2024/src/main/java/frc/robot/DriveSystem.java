package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class DriveSystem {
    private PWMSparkMax Motor_BLT;
    private PWMSparkMax Motor_BRT;
    private PWMSparkMax Motor_FLT;
    private PWMSparkMax Motor_FRT;

    public DriveSystem(){
        this.Motor_BLT = new PWMSparkMax(RobotConstants.Motor_Channel_BLT);
        this.Motor_BLT = new PWMSparkMax(RobotConstants.Motor_Channel_BRT);
        this.Motor_BLT = new PWMSparkMax(RobotConstants.Motor_Channel_FLT);
        this.Motor_BLT = new PWMSparkMax(RobotConstants.Motor_Channel_FRT);

    }

    public void update(double Lmove, double Rmove){
        this.Motor_FLT.set(Lmove);
        this.Motor_BLT.set(Lmove);
        this.Motor_FRT.set(Rmove);
        this.Motor_BRT.set(Rmove);

    }
        
    
}
