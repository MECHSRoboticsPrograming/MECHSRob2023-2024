package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class OperatorInterface {
    private XboxController Pilot_Joystick;
    

    public OperatorInterface(){
        this.Pilot_Joystick = new XboxController(RobotConstants.Controller_Port_Pilot);
    }

    public double leftDriveStick (){
        return this.Pilot_Joystick.getRawAxis(RobotConstants.Pilot_Drive_Channel_L);
      }
      
    public double rightDriveStick (){
        return this.Pilot_Joystick.getRawAxis(RobotConstants.Pilot_Drive_Channel_R);
      }
    
    public boolean intakeButton(){
      return this.Pilot_Joystick.getBButtonPressed();
    }

    public boolean reverseIntakeButton(){
      return this.Pilot_Joystick.getXButtonPressed();
    }

    
}
