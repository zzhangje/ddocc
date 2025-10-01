package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.dashboard.Alert;
import frc.lib.dashboard.LoggedTunableNumber;
import frc.lib.interfaces.motor.GenericArmIO;
import frc.lib.interfaces.motor.GenericArmIOInputsAutoLogged;
import frc.lib.interfaces.motor.GenericArmIOKraken;
import frc.lib.interfaces.motor.GenericArmIOSim;
import frc.lib.interfaces.motor.GenericRollerIO;
import frc.lib.interfaces.motor.GenericRollerIOInputsAutoLogged;
import frc.lib.interfaces.motor.GenericRollerIOKraken;
import frc.lib.interfaces.motor.GenericRollerIOSim;
import frc.robot.Constants.Ports.Can;
import org.littletonrobotics.junction.Logger;

public class Intake extends SubsystemBase {
  public double getPivotDegree() {
    // TODO: implement
    return 0.0;
  }

  public void setPivotDegree(double degree) {
    // TODO: implement
  }

  public double getRollerVelocityRPM() {
    // TODO: implement
    return 0.0;
  }

  public void setRollerVoltage(double voltage) {
    // TODO: implement
  }

  static {
    // TODO: implement
  }

  private final GenericArmIO pivotIO;
  private final GenericRollerIO rollerIO;
  // TODO: implement

  @Override
  public void periodic() {
    // TODO: implement
  }

  private Intake(GenericArmIO pivotIO, GenericRollerIO rollerIO) {
    this.pivotIO = pivotIO;
    this.rollerIO = rollerIO;
  }

  public static Intake createReal() {
    return new Intake(
        new GenericArmIOKraken(
            "Intake Pivot",
            Can.GROUND_INTAKE_PIVOT,
            IntakeConfig.getPivotConfig(),
            IntakeConfig.HOME_DEGREE),
        new GenericRollerIOKraken(
            "Intake Roller", Can.GROUND_INTAKE_ROLLER, new TalonFXConfiguration()));
  }

  public static Intake createSim() {
    return new Intake(
        new GenericArmIOSim(
            DCMotor.getFalcon500Foc(1),
            IntakeConfig.PIVOT_REDUCTION,
            0.025,
            0.1,
            Units.degreesToRadians(IntakeConfig.MIN_DEGREE),
            Units.degreesToRadians(IntakeConfig.MAX_DEGREE),
            Units.degreesToRadians(IntakeConfig.HOME_DEGREE)),
        new GenericRollerIOSim(DCMotor.getFalcon500(1), IntakeConfig.ROLLER_REDUCTION, 0.001));
  }

  public static Intake createIO() {
    return new Intake(new GenericArmIO() {}, new GenericRollerIO() {});
  }
}
