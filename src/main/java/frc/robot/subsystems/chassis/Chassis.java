package frc.robot.subsystems.chassis;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.dashboard.Alert;
import frc.lib.dashboard.LoggedTunableNumber;
import frc.lib.interfaces.motor.GenericWheelIO;
import frc.lib.interfaces.motor.GenericWheelIOInputsAutoLogged;
import frc.lib.interfaces.motor.GenericWheelIOKraken;
import frc.lib.interfaces.motor.GenericWheelIOSim;
import frc.robot.Constants;

public class Chassis extends SubsystemBase {
  static {
    final var driveGains = ChassisConfig.getDriveGains();
    ChassisConfig.driveKp.initDefault(driveGains.kp());
    ChassisConfig.driveKd.initDefault(driveGains.kd());
    ChassisConfig.driveKs.initDefault(driveGains.ks());
  }

  private final GenericWheelIO leftIO;
  private final GenericWheelIO rightIO;
  private final GenericWheelIOInputsAutoLogged leftInputs = new GenericWheelIOInputsAutoLogged();
  private final GenericWheelIOInputsAutoLogged rightInputs = new GenericWheelIOInputsAutoLogged();
  private final Alert leftOfflineAlert = new Alert("Chassis Left Offline", Alert.AlertType.WARNING);
  private final Alert rightOfflineAlert =
      new Alert("Chassis Right Offline", Alert.AlertType.WARNING);

  @Override
  public void periodic() {
    leftIO.updateInputs(leftInputs);
    rightIO.updateInputs(rightInputs);

    leftOfflineAlert.set(!leftInputs.connected);
    rightOfflineAlert.set(!rightInputs.connected);

    LoggedTunableNumber.ifChanged(
        hashCode(),
        () -> {
          leftIO.setPdf(
              ChassisConfig.driveKp.get(),
              ChassisConfig.driveKd.get(),
              ChassisConfig.driveKs.get());
          rightIO.setPdf(
              ChassisConfig.driveKp.get(),
              ChassisConfig.driveKd.get(),
              ChassisConfig.driveKs.get());
        },
        ChassisConfig.driveKp,
        ChassisConfig.driveKd,
        ChassisConfig.driveKs);
  }

  private Chassis(GenericWheelIO leftIO, GenericWheelIO rightIO) {
    this.leftIO = leftIO;
    this.rightIO = rightIO;
  }

  public static Chassis createReal() {
    return new Chassis(
        new GenericWheelIOKraken(
                "Left Drive", Constants.Ports.Can.LEFT_DRIVE_MASTER, ChassisConfig.getDriveConfig())
            .withFollower(Constants.Ports.Can.LEFT_DRIVE_SLAVE, true),
        new GenericWheelIOKraken(
                "Right Drive",
                Constants.Ports.Can.RIGHT_DRIVE_MASTER,
                ChassisConfig.getDriveConfig())
            .withFollower(Constants.Ports.Can.RIGHT_DRIVE_SLAVE, true));
  }

  public static Chassis createSim() {
    return new Chassis(
        new GenericWheelIOSim(
            2,
            0.025,
            ChassisConfig.DRIVE_REDUCTION,
            ChassisConfig.driveKp.get(),
            ChassisConfig.driveKd.get()),
        new GenericWheelIOSim(
            2,
            0.025,
            ChassisConfig.DRIVE_REDUCTION,
            ChassisConfig.driveKp.get(),
            ChassisConfig.driveKd.get()));
  }

  public static Chassis createIO() {
    return new Chassis(new GenericWheelIO() {}, new GenericWheelIO() {});
  }
}
