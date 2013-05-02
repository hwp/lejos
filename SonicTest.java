import lejos.nxt.*;
import lejos.util.Delay;

public class SonicTest {
  public static void main(String[] args) throws Exception {
    UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);

    while (!Button.ESCAPE.isDown()) {
      LCD.clear();
      LCD.drawString(sonic.getVendorID(), 0, 0);
      LCD.drawString(sonic.getProductID(), 0, 1);
      LCD.drawString(sonic.getVersion(), 0, 2);
      LCD.drawInt(sonic.getDistance(), 0, 3);
      Delay.msDelay(1000);
    }
  }
}

