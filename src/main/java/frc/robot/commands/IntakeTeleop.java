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
    // TODO: implement intake teleop control
    
    // when both buttons are pressed, do nothing
    // when inject button is pressed, intake
    // when eject button is pressed, outtake

    // when both buttons are pressed, do nothing
    // when arm up button is pressed, raise the arm
    // when arm down button is pressed, lower the arm
  }

  @Override
  public void end(boolean interrupted) {
    // TODO: stop the rollers and hold the arm position
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
