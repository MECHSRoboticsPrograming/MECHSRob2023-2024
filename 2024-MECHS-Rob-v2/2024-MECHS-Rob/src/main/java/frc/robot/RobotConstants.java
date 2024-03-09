
package frc.robot;

public class RobotConstants {
    //Drive System: for RoboRio  
    public static final int Motor_Channel_FLT = 3;
    public static final int Motor_Channel_BLT = 4;
    public static final int Motor_Channel_FRT = 1;
    public static final int Motor_Channel_BRT = 2;
    public static final int Motor_Channel_Shooter_1 = 5;
    public static final int Motor_Channel_Shooter_2 = 6;
    public static final int Motor_Channel_Intake = 7;

    
    //These are our constants for our drive system, Motor_Channel is the
    // Indication of which channel said commands will go and activate.
    //B stand for back, F stands for front, L stands for left, R stands 
    //for right, T stands for tank which will be the current wheel 
    //platform we will use. 

    //Pilot and CoPilot Ports
    public static final int Controller_Port_Pilot = 0;
    
    //Subject to change for inputs might have to do extra code for 
    //Logitech controllers

    //Pilot Joystick Channels
    public static final int Pilot_Drive_Channel_L= 1;
    public static final int Pilot_Drive_Channel_R= 5;
    //Subject to change port need to use game tools and xbox controller 
    //
    
    public static boolean inTeleopMacroMode = false;
    //Needed for logitech controllers subject to change
}