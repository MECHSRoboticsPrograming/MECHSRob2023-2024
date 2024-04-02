// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.math.MathUtil;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final Timer m_timer = new Timer();
  private OperatorInterface _Ops;
  private DriveSystem _driveSystem;
  private IntakeSystem _intakeSystem;
  private ShooterSystem _shooterSystem;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    this._Ops = new OperatorInterface();
    this._driveSystem = new DriveSystem();
    this._intakeSystem = new IntakeSystem();
    this._shooterSystem = new ShooterSystem();
    CameraServer.startAutomaticCapture();
  

    SmartDashboard.putNumber("DB/noteSeparationSpeed", 0.3);
    SmartDashboard.putNumber("DB/initialBackupSpeed", -0.23);
    SmartDashboard.putNumber("DB/backup_speed", -0.2);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        if (m_timer.get() < 5.5) {
          shootingSequence(0.0);
          
        }else if (m_timer.get() < 6.5){
          this._driveSystem.update(0, 0);
          this._intakeSystem.update(false, false);
          this._shooterSystem.update( 0.0, 0.0, false);
        }
        else if (m_timer.get() < 8.0){
          this._driveSystem.update(-0.4, -0.4);
          this._intakeSystem.update(true, false);
        }
        // else if (m_timer.get() < 9.5){
        //   this._driveSystem.update(0.4, 0.4);

        // }
        // else if(m_timer.get() < 13.5){
        //   shootingSequence(9.5);}
        else{
          this._driveSystem.update(0, 0);
          this._intakeSystem.update(false, false);
          this._shooterSystem.update( 0.0, 0.0, false);
        }
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }
 public double D_L(double left, double right) {
    double rawVal = left - right;
    double clampedVal = MathUtil.clamp(rawVal, -1.0, 1.0);
    return clampedVal;  
  }

  public double D_R(double left, double right) {
    double rawVal = left + right;
    double clampedVal = MathUtil.clamp(rawVal, -1.0, 1.0);
    return clampedVal; 

  }
 


  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if (RobotConstants.inTeleopMacroMode == false) {
      if (this._Ops.sequenceInitialization()) {
        RobotConstants.inTeleopMacroMode = true;
        m_timer.restart();
      }
      else {
        double lMove = D_L(this._Ops.leftDriveStick(), this._Ops.rightDriveStick());
        double rMove = D_R(this._Ops.leftDriveStick(), this._Ops.rightDriveStick());
        this._driveSystem.update(lMove, rMove);

        this._intakeSystem.update(this._Ops.intakeButton(), this._Ops.reverseIntakeButton());
        this._shooterSystem.update(this._Ops.speakerShooterTrigger(), this._Ops.ampShooterTrigger(), this._Ops.reverseShooterButton());
      }
    }
    else if (RobotConstants.inTeleopMacroMode == true){
      System.out.println("In sequence");
      if (this._Ops.sequenceCanelButton()) {
        RobotConstants.inTeleopMacroMode = false;
      }
      else {
        // run the sequence   
        this.shootingSequence(0.0);
      }
    }
  }

  public void shootingSequence(double shooter_Offset_Time_Diffrence) {
    double shooter_Timer = m_timer.get() - shooter_Offset_Time_Diffrence;
    double initialBackupDuration = 0.7;
    
    double noteSeparationDuration = 0.6;
    double shooterWindupDuration = 1.0;
    double backupDuration = 2.0;
    if (shooter_Timer < initialBackupDuration) {
      //double initialBackupSpeed = -0.25;
      double initialBackupSpeed =  SmartDashboard.getNumber("DB/initialBackupSpeed", -0.23);
      this._driveSystem.update(initialBackupSpeed, initialBackupSpeed);
      this._shooterSystem.update(0.0, 0.0, false);
    }
    else if (shooter_Timer < initialBackupDuration + noteSeparationDuration) {
      //double noteSeparationSpeed = 0.35;
      double noteSeparationSpeed =  SmartDashboard.getNumber("DB/noteSeparationSpeed", 0.3);
      this._intakeSystem.reverse();
      this._shooterSystem.update(0.0, 0.0, true);
      this._driveSystem.update(noteSeparationSpeed, noteSeparationSpeed);
     
    }
    else if (shooter_Timer < shooterWindupDuration + initialBackupDuration + noteSeparationDuration){
      this._driveSystem.update(0.0, 0.0);
      this._shooterSystem.shoot();
    }
    else if (shooter_Timer < shooterWindupDuration + backupDuration + initialBackupDuration + noteSeparationDuration) {
        // speed goes from -1 to 1
        // back up and spin intake at the same time.
        double backup_speed = SmartDashboard.getNumber("DB/backup_speed", -0.2);
        this._driveSystem.update(backup_speed, backup_speed);
        this._intakeSystem.feedNote();
    }
    else {
      RobotConstants.inTeleopMacroMode = false;
    }
  }
  

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
