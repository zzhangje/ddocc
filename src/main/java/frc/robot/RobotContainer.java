// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lib.math.PoseUtil;
import frc.lib.service.CommandSelector;
import frc.lib.service.GamePieceVisualizer;
import frc.lib.service.TunableManager;
import frc.lib.service.Visualizer;
import frc.reefscape.Field;
import frc.reefscape.TrajectorySet;
import frc.robot.Constants.AscopeAssets;
import frc.robot.Constants.Misc;
import frc.robot.Constants.Ports;
import frc.robot.commands.ChassisTeleop;
import frc.robot.commands.ForceIdle;
import frc.robot.commands.PickCoral;
import frc.robot.commands.ScoreCoral;
import frc.robot.subsystems.chassis.Chassis;
import frc.robot.subsystems.intake.Intake;
import lombok.Getter;

public class RobotContainer {
  @Getter private static final Odometry odometry = new Odometry();
  @Getter private static final TrajectorySet trajectorySet = new TrajectorySet();

  // driver
  @Getter // for this project only
  CommandXboxController driver = new CommandXboxController(Ports.Joystick.DRIVER);

  // subsystem
  @Getter // for this project only
  private final Chassis chassis;
  @Getter // for this project only
  private final Intake intake;

  // service
  private final CommandSelector autoCmdSelector = new CommandSelector("Auto");

  // state
  private boolean s_hasCoral = true;

  public RobotContainer() {
    if (Constants.MODE.equals(Constants.Mode.REAL)) {
      chassis = Chassis.createReal();
      intake = Intake.createReal();
    } else if (Constants.MODE.equals(Constants.Mode.SIM)) {
      chassis = Chassis.createSim();
      intake = Intake.createSim(() -> s_hasCoral);

      GamePieceVisualizer coral =
          new GamePieceVisualizer(
              "Coral",
              0.3,
              0.5,
              PoseUtil.repeat(Field.PRESET_CORAL_POSES, 5),
              PoseUtil.tolist(Field.Reef.CORAL_POSES),
              (s_hasCoral ? 1 : 0));

      Visualizer visualizer = new Visualizer();
      configureVisualization(visualizer);
      configureSimulation(visualizer, coral);
    } else {
      chassis = Chassis.createIO();
      intake = Intake.createIO();
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

  private void configureBindings() {
    chassis.setDefaultCommand(
        new ChassisTeleop(chassis, () -> -driver.getLeftY(), () -> -driver.getRightX()));
    // intake.setDefaultCommand(
    //     new IntakeTeleop(
    //         intake,
    //         () -> driver.getRightTriggerAxis() > 0.2,
    //         () -> driver.getLeftTriggerAxis() > 0.2,
    //         () -> driver.leftBumper().getAsBoolean(),
    //         () -> driver.rightBumper().getAsBoolean()));

    driver.a().onTrue(new ForceIdle(intake));
    driver.x().onTrue(new PickCoral(intake));
    driver.b().onTrue(new ScoreCoral(intake));
  }

  private void configureAuto() {
    new Trigger(() -> DriverStation.isAutonomousEnabled())
        .onTrue(autoCmdSelector.stop())
        .onFalse(autoCmdSelector.run());
  }

  private void configureSimulation(Visualizer visualizer, GamePieceVisualizer coral) {
    new Trigger(() -> intake.getRollerVelocityRPM() < 0)
        .whileTrue(
            Commands.run(
                    () -> {
                      if (!s_hasCoral) {
                        Boolean ret =
                            coral.tryPick(
                                new Pose3d(odometry.getEstimatedPose())
                                    .plus(
                                        visualizer
                                            .getComponentTransform(AscopeAssets.INTAKE)
                                            .plus(Misc.intake_T_coral)));
                        if (ret) {
                          s_hasCoral = true;
                        }
                      }
                    })
                .withName("Sim/Try Pick Coral"));

    new Trigger(() -> intake.getRollerVelocityRPM() > 0)
        .whileTrue(
            Commands.run(
                    () -> {
                      if (s_hasCoral) {
                        Boolean ret =
                            coral.tryScore(
                                new Pose3d(odometry.getEstimatedPose())
                                    .plus(
                                        visualizer
                                            .getComponentTransform(AscopeAssets.INTAKE)
                                            .plus(Misc.intake_T_coral)));
                        if (ret) {
                          s_hasCoral = false;
                        }
                      }
                    })
                .withName("Sim/Try Score Coral"));

    new Trigger(() -> s_hasCoral)
        .onChange(joystickRumblerCommand(driver, 0.2, RumbleType.kBothRumble));
  }

  private void configureDebugGroup() {
    TunableManager debugGroup = new TunableManager("DebugGroup");
    debugGroup.register(Constants.DebugGroup.CHASSIS);
    debugGroup.register(Constants.DebugGroup.INTAKE);
    debugGroup.register(Constants.DebugGroup.ODOMETRY);
    new Trigger(Constants.IS_LIVE_DEBUG).onTrue(debugGroup.run()).onFalse(debugGroup.stop());
  }

  private void configureVisualization(Visualizer visualizer) {
    visualizer.registerVisualizedComponent(
        Visualizer.BASE_FRAME,
        "chassis",
        AscopeAssets.CHASSIS,
        () -> new Transform3d(0.0, 0.0, 0.095, new Rotation3d(Math.PI / 2.0, 0.0, Math.PI / 2.0)));
    visualizer.registerVisualizedComponent(
        "chassis",
        "intake",
        AscopeAssets.INTAKE,
        () ->
            new Transform3d(
                0.0,
                0.1225,
                0.2695,
                new Rotation3d(-Units.degreesToRadians(intake.getPivotDegree()), Math.PI, 0.0)));
    visualizer.registerVisualizedComponent(
        "intake",
        "coral",
        AscopeAssets.CORAL,
        () -> s_hasCoral ? Misc.intake_T_coral : new Transform3d(1e9, 1e9, 1e9, new Rotation3d()));
    visualizer.print();
  }

  public Command getAutonomousCommand() {
    return autoCmdSelector.getCommand();
  }
}
