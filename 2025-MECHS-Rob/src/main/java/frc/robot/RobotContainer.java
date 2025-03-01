// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.CoralEndEffector;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import swervelib.SwerveInputStream;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final CoralEndEffector coralEndEffector = new CoralEndEffector();
  private final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();
  private final SwerveSubsystem drivebase = new SwerveSubsystem();
  private final ClimberSubsystem climber = new ClimberSubsystem();
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    drivebase.setDefaultCommand(!Robot.isSimulation() ? driveFieldOrientedAngularVelocity : driveFieldOrientedDirectAngleSim);
  }

SwerveInputStream driveAngularVelocity = SwerveInputStream.of(drivebase.getSwerveDrive(), 
                                                            () -> m_driverController.getLeftY() * -1,
                                                            () -> m_driverController.getLeftX() * -1)
                                                            .withControllerRotationAxis(m_driverController::getRightX)
                                                            .deadband(OperatorConstants.DEADBAND)
                                                            .scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);

  SwerveInputStream driveDirectAngle = driveAngularVelocity.copy().withControllerHeadingAxis(m_driverController::getRightX,
                                                                                             m_driverController::getRightY)
                                                                                             .headingWhile(true);

  Command driveFieldOrientedDirectAngle = drivebase.driveFieldOriented(driveDirectAngle);
  
  Command driveFieldOrientedAngularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);

SwerveInputStream driveAngularVelocitySim = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                  () -> -m_driverController.getLeftY(),
                                                                  () -> -m_driverController.getLeftX())
                                                                .withControllerRotationAxis(() -> m_driverController.getRawAxis(2))
                                                                .deadband(OperatorConstants.DEADBAND)
                                                                .scaleTranslation(0.8)
                                                                .allianceRelativeControl(true);

SwerveInputStream driveDirectAngleSim = driveAngularVelocitySim.copy()
                                                                .withControllerHeadingAxis(() -> Math.sin(
                                                                                                m_driverController.getRawAxis(
                                                                                                    2) * Math.PI) * (Math.PI * 2),
                                                                                            () -> Math.cos(
                                                                                                m_driverController.getRawAxis(
                                                                                                    2) * Math.PI) *
                                                                                                  (Math.PI * 2))
                                                                .headingWhile(true);

Command driveFieldOrientedDirectAngleSim = drivebase.driveFieldOriented(driveDirectAngleSim);



  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    m_driverController.b().whileTrue(coralEndEffector.intakeCoral());
    m_driverController.x().whileTrue(coralEndEffector.ejectCoral());
    m_driverController.a().whileTrue(coralEndEffector.toggleWrist());
    m_driverController.rightBumper().whileTrue(coralEndEffector.toggleElbow());

    m_driverController.pov(0).whileTrue(elevatorSubsystem.elevatorIncrement());
    m_driverController.pov(180).whileTrue(elevatorSubsystem.elevatorReturnZero());

    m_driverController.pov(90).whileTrue(climber.retractClimber());
    m_driverController.pov(270).whileTrue(climber.deployClimber());

    m_driverController.leftTrigger().whileTrue(elevatorSubsystem.moveUp());
    m_driverController.rightTrigger().whileTrue(elevatorSubsystem.moveDown());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return drivebase.getAutonomousCommand("New Auto");
  }
}
