package frc.lib.interfaces.motor;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Constants;

public class GenericWheelIOSim implements GenericWheelIO {
  private final DCMotorSim sim;
  private final PIDController pid = new PIDController(0, 0, 0);
  private final SlewRateLimiter limiter = new SlewRateLimiter(2.5);
  private double appliedVoltageVolts = 0.0;

  public GenericWheelIOSim(
      int numMotors, double JKgMetersSquared, double reduction, double kP, double kD) {
    sim =
        new DCMotorSim(
            LinearSystemId.createDCMotorSystem(
                DCMotor.getKrakenX60(numMotors), JKgMetersSquared, reduction),
            DCMotor.getKrakenX60(numMotors));
  }

  @Override
  public void updateInputs(GenericWheelIOInputs inputs) {
    if (DriverStation.isDisabled()) {
      stop();
      setVoltage(limiter.calculate(appliedVoltageVolts));
    } else {
      limiter.reset(appliedVoltageVolts);
    }
    sim.update(Constants.LOOP_PERIOD_SEC);

    inputs.connected = true;
    inputs.positionRad = sim.getAngularPositionRad();
    inputs.velRadPerSec = sim.getAngularVelocityRadPerSec();
    inputs.outputVoltageVolt = appliedVoltageVolts;
    inputs.supplyCurrentAmp = Math.abs(sim.getCurrentDrawAmps());
  }

  @Override
  public void setVoltage(double voltageVolt) {
    sim.setInputVoltage(MathUtil.clamp(voltageVolt, -12.0, 12.0));
  }

  @Override
  public void setPdf(double kp, double kd, double ks) {
    pid.setP(kp);
    pid.setD(kd);
  }

  @Override
  public void setVelocity(double velRadPerSec, double torqueAmp) {
    // setVoltage(pid.calculate(sim.getAngularVelocityRadPerSec(), velRadPerSec));
    sim.setState(
        sim.getAngularPositionRad()
            + (velRadPerSec + sim.getAngularVelocityRadPerSec()) / 2.0 * Constants.LOOP_PERIOD_SEC,
        velRadPerSec);
  }

  @Override
  public void setCurrent(double currentAmp) {
    setVoltage(currentAmp * 12.0 / 40.0);
  }

  @Override
  public void stop() {
    setVoltage(0.0);
  }

  double getPositionRad() {
    return sim.getAngularPositionRad();
  }

  double getVelocityRadPerSec() {
    return sim.getAngularVelocityRadPerSec();
  }
}
