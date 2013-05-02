import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class HelloWorld {
  public static void main(String[] args) {
    LCD.drawString("Hello World!", 0, 0);
    while (true) {
      Button.waitForAnyPress();
      Motor.A.forward();
      Motor.C.backward();
      Button.waitForAnyPress();
      Motor.A.stop();
      Motor.C.stop();
    }
  }
}

