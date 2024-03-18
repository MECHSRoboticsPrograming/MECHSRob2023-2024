// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

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
        if (m_timer.get() < 4.0) {
          shootingSequence(0.0);
          
        }else if (m_timer.get() < 5.0){
          this._driveSystem.update(0, 0);
          this._intakeSystem.update(false, false);
          this._shooterSystem.update( 0.0, 0.0, false);
          
        }else if(m_timer.get() < 8.0){
          shootingSequence(5.0);
        }else{
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

 


  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if (RobotConstants.inTeleopMacroMode == false) {
      System.out.println("In manual mode");
      if (this._Ops.sequenceInitialization()) {
        RobotConstants.inTeleopMacroMode = true;
        m_timer.restart();
      }
      else {
        this._driveSystem.update(this._Ops.leftDriveStick(), this._Ops.rightDriveStick());
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
    double initialBackupDuration = 1.0;
    double noteSeparationDuration = 0.35;
    double shooterWindupDuration = 1.0;
    double backupDuration = 2.0;
    if (shooter_Timer < initialBackupDuration) {
      double initialBackupSpeed = -0.2;
      this._driveSystem.update(initialBackupSpeed, initialBackupSpeed);
    }
    else if (shooter_Timer < initialBackupDuration + noteSeparationDuration) {
      double noteSeparationSpeed = 0.35;
      this._intakeSystem.reverse();
      this._driveSystem.update(noteSeparationSpeed, noteSeparationSpeed);
     
    }
    else if (shooter_Timer < shooterWindupDuration + initialBackupDuration + noteSeparationDuration){
      this._driveSystem.update(0.0, 0.0);
      this._shooterSystem.shoot();
    }
    else if (shooter_Timer < shooterWindupDuration + backupDuration + initialBackupDuration + noteSeparationDuration) {
        // speed goes from -1 to 1
        // back up and spin intake at the same time.
        double backup_speed = -0.15;
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
