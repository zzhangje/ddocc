package frc.lib.interfaces.motor;

import org.littletonrobotics.junction.AutoLog;

public interface GenericWheelIO {
  @AutoLog
  class GenericWheelIOInputs {
    public boolean connected;

    public double velRadPerSec;
    public double positionRad;
    public double outputVoltageVolt;
    public double supplyCurrentAmp;
    public double statorCurrentAmp;
  }

  default void updateInputs(GenericWheelIOInputs inputs) {}

  default void setVelocity(double velRadPerSec, double torqueAmp) {}

  default void setPdf(double kp, double kd, double ks) {}

  default void setCurrent(double currentAmp) {}

  default void setVoltage(double voltageVolt) {}

  default void stop() {}
}
