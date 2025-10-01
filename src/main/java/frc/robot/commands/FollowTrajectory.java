package frc.robot.commands;

import choreo.trajectory.DifferentialSample;
import choreo.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.chassis.Chassis;
import java.util.function.Supplier;
import org.littletonrobotics.junction.Logger;

public class FollowTrajectory extends Command {
  private final Chassis chassis;
  private final Supplier<Trajectory<DifferentialSample>> trajectorySupplier;
  private DifferentialSample setpoint = null;
  private final Timer timer = new Timer();

  public FollowTrajectory(
      Chassis chassis, Supplier<Trajectory<DifferentialSample>> trajectorySupplier) {
    this.chassis = chassis;
    this.trajectorySupplier = trajectorySupplier;
    addRequirements(chassis);
  }

  @Override
  public void initialize() {
    trajectorySupplier.get().getInitialSample(false).get();
    Logger.recordOutput(
        "Chassis/FollowTrajectory/TrajectoryPoses", trajectorySupplier.get().getPoses());
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    setpoint = trajectorySupplier.get().sampleAt(timer.get(), false).get();
    chassis.setWheelsVelocities(setpoint.vl, setpoint.vr);
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(trajectorySupplier.get().getTotalTime());
  }

  @Override
  public void end(boolean interrupted) {
    timer.stop();
    chassis.stop();
  }
}
