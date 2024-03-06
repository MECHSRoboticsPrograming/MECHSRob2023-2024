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
      return this.Pilot_Joystick.getRightBumper();
    }

    public boolean reverseIntakeButton(){
      return this.Pilot_Joystick.getLeftBumper();
    } 
    public double ampShooterTrigger(){
      return this.Pilot_Joystick.getLeftTriggerAxis();
    }
    
    public double speakerShooterTrigger(){
      return this.Pilot_Joystick.getRightTriggerAxis();
    }

    public boolean reverseShooterButton(){
      return this.Pilot_Joystick.getYButton();
    }

    
}
