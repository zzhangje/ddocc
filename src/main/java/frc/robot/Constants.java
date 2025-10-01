package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.RobotBase;
import frc.lib.interfaces.CanId;
import java.util.function.BooleanSupplier;

public class Constants {
  public static final double LOOP_PERIOD_SEC = 0.02;

  public static BooleanSupplier IS_LIVE_DEBUG = () -> true;

  public static final Mode MODE =
      RobotBase.isReal() ? Mode.REAL : RobotBase.isSimulation() ? Mode.SIM : Mode.REPLAY;

  public static final boolean ENABLE_SINGLE_TAG_POSE = false;

  public enum Mode {
    REAL,
    SIM,
    REPLAY
  }

  public final class Misc {
    public static final Transform3d intake_T_coral =
        new Transform3d(-0.095, 0.205, -0.075, new Rotation3d(0.0, 0.0, 0.0));
  }

  public final class AscopeAssets {
    public static final int CHASSIS = 0;
    public static final int INTAKE = 1;
    public static final int CORAL = 2;
  }

  public final class DebugGroup {
    public static final String CHASSIS = "Chassis";
    public static final String INTAKE = "Intake";
    public static final String ODOMETRY = "Odometry";
  }

  public final class Ports {
    public static final class Can {
      public static final String RIO_BUS = "";
      public static final String CHASSIS_CANIVORE_BUS = "chassis";
      public static final String SUPERSTRUCTURE_CANIVORE_BUS = "super";

      // Chassis
      public static final CanId LEFT_DRIVE_MASTER = new CanId(0, CHASSIS_CANIVORE_BUS);
      public static final CanId LEFT_DRIVE_SLAVE = new CanId(1, CHASSIS_CANIVORE_BUS);
      public static final CanId RIGHT_DRIVE_MASTER = new CanId(2, CHASSIS_CANIVORE_BUS);
      public static final CanId RIGHT_DRIVE_SLAVE = new CanId(3, CHASSIS_CANIVORE_BUS);

      // Intake
      public static final CanId GROUND_INTAKE_ROLLER = new CanId(0, SUPERSTRUCTURE_CANIVORE_BUS);
      public static final CanId GROUND_INTAKE_PIVOT = new CanId(1, SUPERSTRUCTURE_CANIVORE_BUS);
    }

    public static final class Digital {}

    public static final class Joystick {
      public static final int DRIVER = 0;
    }
  }
}
