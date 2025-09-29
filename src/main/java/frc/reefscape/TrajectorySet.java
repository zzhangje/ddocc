package frc.reefscape;

import choreo.Choreo;
import choreo.trajectory.DifferentialSample;
import choreo.trajectory.Trajectory;
import frc.lib.dashboard.AllianceValue;

public class TrajectorySet {
  // public final AllianceValue<Trajectory<SwerveSample>> j2LeftCoralStation;
  // public final AllianceValue<Trajectory<SwerveSample>> e2RightCoralStation;
  // public final AllianceValue<Trajectory<SwerveSample>> k2LeftCoralStation;
  // public final AllianceValue<Trajectory<SwerveSample>> l2LeftCoralStation;
  // public final AllianceValue<Trajectory<SwerveSample>> c2RightCoralStation;
  // public final AllianceValue<Trajectory<SwerveSample>> d2RightCoralStation;
  // public final AllianceValue<Trajectory<SwerveSample>> ef2Net;
  // public final AllianceValue<Trajectory<SwerveSample>> ij2Net;
  // public final AllianceValue<Trajectory<SwerveSample>> gh2Net;

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
