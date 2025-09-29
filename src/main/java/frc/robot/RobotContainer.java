// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lib.service.CommandSelector;
import frc.lib.service.GamePieceVisualizer;
import frc.lib.service.TunableManager;
import frc.lib.service.Visualizer;
import frc.reefscape.TrajectorySet;
import frc.robot.Constants.Ports;
import frc.robot.subsystems.chassis.Chassis;
import lombok.Getter;

public class RobotContainer {
  @Getter private static final Odometry odometry = new Odometry();
  @Getter private static final TrajectorySet trajectorySet = new TrajectorySet();

  // driver
  CommandXboxController driver = new CommandXboxController(Ports.Joystick.DRIVER);

  // subsystem
  @Getter // for this project only
  private final Chassis chassis;

  // service
  private final CommandSelector autoCmdSelector = new CommandSelector("Auto");

  public RobotContainer() {
    if (Constants.MODE.equals(Constants.Mode.REAL)) {
      chassis = Chassis.createReal();
    } else if (Constants.MODE.equals(Constants.Mode.SIM)) {
      chassis = Chassis.createSim();
    } else {
      chassis = Chassis.createIO();
    }

    configureBindings();
    configureAuto();
    configureDebugGroup();

    System.out.println("##########################################");
    System.out.println("# RobotContainer Initialization Complete #");
    System.out.println("#               Mode: " + Constants.MODE + "               #");
    System.out.println("##########################################");
  }

  private Command joystickRumblerCommand(
      CommandXboxController controller, double timeoutSecs, RumbleType rumbleType) {
    return Commands.startEnd(
            () -> controller.getHID().setRumble(rumbleType, 1.0),
            () -> controller.getHID().setRumble(rumbleType, 0.0))
        .withTimeout(timeoutSecs)
        .withName("Joystick Rumbler");
  }

  private void configureBindings() {}

  private void configureAuto() {
    new Trigger(() -> DriverStation.isAutonomousEnabled())
        .onTrue(autoCmdSelector.stop())
        .onFalse(autoCmdSelector.run());
  }

  private void configureSimulation(
      Visualizer visualizer, GamePieceVisualizer coral, GamePieceVisualizer algae) {}

  private void configureDebugGroup() {
    TunableManager debugGroup = new TunableManager("DebugGroup");
    debugGroup.register(Constants.DebugGroup.ARM);
    debugGroup.register(Constants.DebugGroup.CHASSIS);
    debugGroup.register(Constants.DebugGroup.ODOMETRY);
    new Trigger(Constants.IS_LIVE_DEBUG).onTrue(debugGroup.run()).onFalse(debugGroup.stop());
  }

  private void configureVisualization(Visualizer visualizer) {
    visualizer.print();
  }

  public Command getAutonomousCommand() {
    return autoCmdSelector.getCommand();
  }
}
