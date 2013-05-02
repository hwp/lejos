import lejos.nxt.*;
import lejos.robotics.localization.*;
import lejos.robotics.navigation.*;
import lejos.util.Delay;

public class Homing {
  private static final int INTERVAL = 200;

  private static void printPose(Pose p) {
    LCD.clear();
    LCD.drawString("X:" + p.getX(), 0, 0);
    LCD.drawString("Y:" + p.getY(), 0, 1);
    LCD.drawString("A:" + p.getHeading(), 0, 2);
  }

  private static void changeSpeed(Pose p, DifferentialPilot pilot) {
    pilot.steer(p.getY());
  }

  private static Pose minus(Pose p1, Pose p2) {
    double dx = p1.getX() - p2.getX();
    double dy = p1.getY() - p2.getY();

    double x = dx * Math.cos(p2.getHeading() / 180 * Math.PI)
                + dy * Math.sin(p2.getHeading() / 180 * Math.PI);
    double y = dy * Math.cos(p2.getHeading() / 180 * Math.PI)
                - dx * Math.sin(p2.getHeading() / 180 * Math.PI);
    double h = p1.getHeading() - p2.getHeading();

    return new Pose((float)x, (float)y, (float)h);
  }

  public static void main(String[] args) {
    DifferentialPilot pilot = new DifferentialPilot(56, 120, Motor.C, Motor.A);
    OdometryPoseProvider odom = new OdometryPoseProvider(pilot);
    
    Pose goal = new Pose(1000, 0, 0);
    Pose cg = goal;
    while (cg.getX() * cg.getX() + cg.getY() * cg.getY() > 10) {
      Pose pose = odom.getPose();
      cg = minus(goal, pose);
      changeSpeed(cg, pilot);

      Delay.msDelay(INTERVAL);
      printPose(pose);
    }

    pilot.stop();
    Button.waitForAnyPress();
  }  
}

