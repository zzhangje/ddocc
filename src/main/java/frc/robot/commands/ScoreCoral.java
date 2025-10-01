package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;

public class ScoreCoral extends Command {
  private final Intake intake;

  public ScoreCoral(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    intake.setPivotDegree(20);
  }

  @Override
  public void execute() {
    if (Math.abs(intake.getPivotDegree() - 20) < 5.0) {
      intake.setRollerVoltage(1);
    } else {
      intake.setRollerVoltage(0);
    }
  }

  @Override
  public void end(boolean interrupted) {
    intake.setRollerVoltage(0);
    intake.setPivotDegree(0);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(intake.getPivotDegree() - 20) < 5.0 && !intake.hasCoral();
  }
}
