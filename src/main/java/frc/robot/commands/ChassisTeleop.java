package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.chassis.Chassis;
import java.util.function.DoubleSupplier;

public class ChassisTeleop extends Command {
  private final Chassis chassis;
  private final DoubleSupplier fowardSupplier;
  private final DoubleSupplier rotationSupplier;

  public ChassisTeleop(
      Chassis chassis, DoubleSupplier fowardSupplier, DoubleSupplier rotationSupplier) {
    this.chassis = chassis;
    this.fowardSupplier = fowardSupplier;
    this.rotationSupplier = rotationSupplier;
    addRequirements(chassis);
  }

  @Override
  public void execute() {
    double forward = 5.0 * Math.abs(fowardSupplier.getAsDouble()) * fowardSupplier.getAsDouble();
    double rotation =
        3.0 * Math.abs(rotationSupplier.getAsDouble()) * rotationSupplier.getAsDouble();
    if (forward < 0) {
      double leftSpeed = forward + rotation;
      double rightSpeed = forward - rotation;
      chassis.setWheelsVelocities(leftSpeed, rightSpeed);
    } else {
      double leftSpeed = forward - rotation;
      double rightSpeed = forward + rotation;
      chassis.setWheelsVelocities(leftSpeed, rightSpeed);
    }
  }

  @Override
  public void end(boolean interrupted) {
    chassis.setWheelsVelocities(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
