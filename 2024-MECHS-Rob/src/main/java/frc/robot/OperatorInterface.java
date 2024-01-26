package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class OperatorInterface {
    private XboxController Pilot_Joystick;
    private XboxController Co_Pilot_Joystick;

    public OperatorInterface(){
        this.Pilot_Joystick = new XboxController(RobotConstants.Controller_Port_Pilot);
        this.Co_Pilot_Joystick = new XboxController(RobotConstants.Controller_Port_Co_Pilot);
    }

    public double leftDriveStick (){
        return this.Pilot_Joystick.getRawAxis(RobotConstants.Pilot_Drive_Channel_L);
      }
      
      public double rightDriveStick (){
        return this.Pilot_Joystick.getRawAxis(RobotConstants.Pilot_Drive_Channel_R);
      }
}
