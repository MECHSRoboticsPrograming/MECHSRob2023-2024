package frc.robot;
import edu.wpi.first.wpilibj.Joystick;


public class OperatorInterface {
    private Joystick Pilot_Joystick;
    private Joystick Co_Pilot_Joystick;

    public OperatorInterface(){
        this.Pilot_Joystick = new Joystick(RobotConstants.Controller_Port_Pilot);
        this.Co_Pilot_Joystick = new Joystick(RobotConstants.Controller_Port_Co_Pilot);
    }

    public double leftDriveStick (){
        return this.Pilot_Joystick.getRawAxis(RobotConstants.Pilot_Drive_Channel_L) * 0.2;
      }
      
      public double rightDriveStick (){
        return this.Pilot_Joystick.getRawAxis(RobotConstants.Pilot_Drive_Channel_R) * 0.2;
      }
    
}
