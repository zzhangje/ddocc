package frc.reefscape;

import choreo.Choreo;
import choreo.trajectory.DifferentialSample;
import choreo.trajectory.Trajectory;
import frc.lib.dashboard.AllianceValue;

public class TrajectorySet {
  public final AllianceValue<Trajectory<DifferentialSample>> score1 = loadTrajectory("score1");
  public final AllianceValue<Trajectory<DifferentialSample>> leave1 = loadTrajectory("leave1");
  public final AllianceValue<Trajectory<DifferentialSample>> collect1 = loadTrajectory("collect1");

  public TrajectorySet() {}

  private AllianceValue<Trajectory<DifferentialSample>> loadTrajectory(String name) {
    var trajectory = Choreo.loadTrajectory(name);
    if (trajectory.isEmpty()) {
      throw new NullPointerException("[TrajectoryLoader]: " + name + ".traj not found");
    }

    @SuppressWarnings("unchecked")
    var blue = (Trajectory<DifferentialSample>) trajectory.get();
    System.out.println("[TrajectoryLoader]: " + name + ".traj loaded");

    return new AllianceValue<>(blue, blue.flipped());
  }
}
