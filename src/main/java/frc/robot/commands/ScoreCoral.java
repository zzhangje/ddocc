package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.intake.Intake;

public class ScoreCoral extends Command {
  private final Intake intake;
  private final Command command;

  public ScoreCoral(Intake intake) {
    this.intake = intake;
    this.command = new SequentialCommandGroup(
      // TODO: add commands here
    );
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    command.initialize();
  }

  @Override
  public void execute() {
    command.execute();
  }

  @Override
  public void end(boolean interrupted) {
    command.end(interrupted);
  }

  @Override
  public boolean isFinished() {
    return command.isFinished();
  }
}
