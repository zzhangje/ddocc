package frc.robot.subsystems.chassis;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.lib.dashboard.LoggedTunableNumber;
import frc.robot.Constants;
import frc.robot.Constants.DebugGroup;

class ChassisConfig {
  static final LoggedTunableNumber driveKp = new LoggedTunableNumber(DebugGroup.CHASSIS, "DriveKp");
  static final LoggedTunableNumber driveKd = new LoggedTunableNumber(DebugGroup.CHASSIS, "DriveKd");
  static final LoggedTunableNumber driveKs = new LoggedTunableNumber(DebugGroup.CHASSIS, "DriveKs");

  static Gains getDriveGains() {
    return switch (Constants.MODE) {
      case REAL -> new Gains(10.0, 0.1, 0.2);
      case SIM, REPLAY -> new Gains(5.0, 0.0, 0.1);
    };
  }

  static final double DRIVE_REDUCTION = 10.71; // 10.71:1

  static TalonFXConfiguration getDriveConfig() {
    var config = new TalonFXConfiguration();

    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    var gains = getDriveGains();
    config.Slot0 = new Slot0Configs().withKP(gains.kp()).withKD(gains.kd()).withKS(gains.ks());

    config.TorqueCurrent.PeakForwardTorqueCurrent = 120.0;
    config.TorqueCurrent.PeakReverseTorqueCurrent = -120.0;

    config.CurrentLimits.SupplyCurrentLimit = 40.0;
    config.CurrentLimits.SupplyCurrentLimitEnable = false;
    config.CurrentLimits.StatorCurrentLimit = 200.0;
    config.CurrentLimits.StatorCurrentLimitEnable = true;

    config.Feedback.SensorToMechanismRatio = DRIVE_REDUCTION;

    return config;
  }

  record Gains(double kp, double kd, double ks) {}
}
