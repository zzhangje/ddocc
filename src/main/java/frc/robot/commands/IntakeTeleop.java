package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;
import java.util.function.BooleanSupplier;

public class IntakeTeleop extends Command {
  private final Intake intake;
  private final BooleanSupplier rollerInjectSupplier;
  private final BooleanSupplier rollerEjectSupplier;
  private final BooleanSupplier armUpSupplier;
  private final BooleanSupplier armDownSupplier;

  public IntakeTeleop(
      Intake intake,
      BooleanSupplier rollerInjectSupplier,
      BooleanSupplier rollerEjectSupplier,
      BooleanSupplier armUpSupplier,
      BooleanSupplier armDownSupplier) {
    this.intake = intake;
    this.rollerInjectSupplier = rollerInjectSupplier;
    this.rollerEjectSupplier = rollerEjectSupplier;
    this.armUpSupplier = armUpSupplier;
    this.armDownSupplier = armDownSupplier;
    addRequirements(intake);
  }

  @Override
  public void execute() {
    if (rollerInjectSupplier.getAsBoolean()) {
      intake.setRollerVoltage(1.0);
    } else if (rollerEjectSupplier.getAsBoolean()) {
      intake.setRollerVoltage(-1.0);
    } else {
      intake.setRollerVoltage(0.0);
    }

    if (armUpSupplier.getAsBoolean()) {
      intake.setPivotDegree(intake.getPivotDegree() + 1.5);
    } else if (armDownSupplier.getAsBoolean()) {
      intake.setPivotDegree(intake.getPivotDegree() - 1.5);
    } else {
      intake.setPivotDegree(intake.getPivotDegree());
    }
  }

  @Override
  public void end(boolean interrupted) {
    intake.setRollerVoltage(0.0);
    intake.setPivotDegree(intake.getPivotDegree());
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
