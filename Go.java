import lejos.nxt.*;
import lejos.util.Delay;

public class Go {
  private static final int RESPONSE_TIME = 100;

  private static UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);

  private static void forward(double distance) {
    Motor.A.setSpeed(400);
    Motor.C.setSpeed(400);
    Motor.A.forward();
    Motor.C.forward();
    Delay.msDelay((int)(5 * distance * 1000) - RESPONSE_TIME);
//  Motor.A.stop();
//  Motor.C.stop();
  }

  private static void turnLeft(double degree) {
    Motor.A.setSpeed(400);
    Motor.C.setSpeed(400);
    Motor.A.forward();
    Motor.C.backward();
    Delay.msDelay((int)(degree * 5) - RESPONSE_TIME);
//  Motor.A.stop();
//  Motor.C.stop();
  }

  private static void turnRight(double degree) {
    Motor.A.setSpeed(400);
    Motor.C.setSpeed(400);
    Motor.A.backward();
    Motor.C.forward();
    Delay.msDelay((int)(degree * 5) - RESPONSE_TIME);
//  Motor.A.stop();
//  Motor.C.stop();
  }

  private static void stop() {
    Motor.A.stop();
    Motor.C.stop();
  }

  private static void go(double x, double y) {
    double angle = Math.atan2(y, x) * 180 / Math.PI;
/*
    LCD.clear();
    LCD.drawInt((int)angle, 0, 0);
    stop();
    Button.waitForAnyPress();
*/  
/*
    if (x < 0) {
      angle += (angle > 0 ? -1 : 1) * 180;
    }
*/
    if (angle > 0) {
      turnLeft(angle);  
    }
    else if(angle < 0) {
      turnRight(-angle);  
    }
    forwardAvoidObstacle(Math.sqrt(x * x + y * y));
    stop();
  }
  
  private static void forwardAvoidObstacle(double distance) {
    final double SEGMENT = .05;
    final double AWAY_DIS = .1;

    if (distance < 0.005) {
      return;
    }

    if (sonic.getDistance() > 30) {
      double dis = distance < SEGMENT ? distance : SEGMENT;
      forward(dis);
      forwardAvoidObstacle(distance - dis);
    }
    else {
      go(0, AWAY_DIS);
      go(-AWAY_DIS, -distance);
    }
  }

  public static void main(String[] args) {
    go(1, 0);
  }
}

