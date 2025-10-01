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
    // TODO: set the intake angle
  }

  @Override
  public void execute() {
    // TODO: wait for intake to arrive before starting the roller
  }

  @Override
  public void end(boolean interrupted) {
    // TODO: reset intake and stop the roller
  }

  @Override
  public boolean isFinished() {
    // TODO: if the roller score the coral
    return false;
  }
}
