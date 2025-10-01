package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.intake.Intake;

public class PickCoral extends Command {
  private final Intake intake;
  private final Command command;

  public PickCoral(Intake intake) {
    this.intake = intake;
    this.command = new SequentialCommandGroup(
      
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
