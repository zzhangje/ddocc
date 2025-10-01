package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.lib.dashboard.LoggedTunableNumber;
import frc.robot.Constants;
import frc.robot.Constants.DebugGroup;

public class IntakeConfig {
  static final LoggedTunableNumber pivotKp = new LoggedTunableNumber(DebugGroup.INTAKE, "PivotKp");
  static final LoggedTunableNumber pivotKd = new LoggedTunableNumber(DebugGroup.INTAKE, "PivotKd");
  static final LoggedTunableNumber pivotKs = new LoggedTunableNumber(DebugGroup.INTAKE, "PivotKs");
  static final LoggedTunableNumber pivotKg = new LoggedTunableNumber(DebugGroup.INTAKE, "PivotKg");

  static Gains getPivotGains() {
    return switch (Constants.MODE) {
      case REAL -> new Gains(10.0, 0.1, 0.2, 0.0);
      case SIM, REPLAY -> new Gains(0.25, 0.0, 0.0, 0.0);
    };
  }

  static final double PIVOT_REDUCTION = 10.71; // 10.71:1
  static final double ROLLER_REDUCTION = 10.71; // 10.71:1
  static final double HOME_DEGREE = 0.0;
  static final double MIN_DEGREE = 0.0;
  static final double MAX_DEGREE = 90.0;

  static TalonFXConfiguration getPivotConfig() {
    var config = new TalonFXConfiguration();

    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    var gains = getPivotGains();
    config.Slot0 =
        new Slot0Configs()
            .withKP(gains.kp())
            .withKD(gains.kd())
            .withKS(gains.ks())
            .withKG(gains.kg());

    config.TorqueCurrent.PeakForwardTorqueCurrent = 120.0;
    config.TorqueCurrent.PeakReverseTorqueCurrent = -120.0;

    config.CurrentLimits.SupplyCurrentLimit = 40.0;
    config.CurrentLimits.SupplyCurrentLimitEnable = false;
    config.CurrentLimits.StatorCurrentLimit = 200.0;
    config.CurrentLimits.StatorCurrentLimitEnable = true;

    config.Feedback.SensorToMechanismRatio = PIVOT_REDUCTION;

    return config;
  }

  record Gains(double kp, double kd, double ks, double kg) {}
}
