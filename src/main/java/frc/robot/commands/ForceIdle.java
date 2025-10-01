package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;

public class ForceIdle extends Command {
  private final Intake intake;

  public ForceIdle(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    intake.setPivotDegree(0);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    intake.setPivotDegree(intake.getPivotDegree());
  }

  @Override
  public boolean isFinished() {
    return Math.abs(intake.getPivotDegree()) < 3.0;
  }
}
