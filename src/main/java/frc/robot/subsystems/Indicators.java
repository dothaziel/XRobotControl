package frc.robot.subsystems;

import com.studica.frc.ServoContinuous;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.StateHandler;

public class Indicators {

    private DigitalOutput led_stopped;
    private DigitalOutput led_running;

    public Indicators() {
        led_stopped = new DigitalOutput(Constants.STOPPED_LED);
        led_running = new DigitalOutput(Constants.RUNNING_LED);
    }

    public void handleLights() {
        if(StateHandler.get().isRunning()) {
            led_running.set(false);
            led_stopped.set(true);
        } else {
            led_stopped.set(false);
            led_running.set(true);
        }
    }
}