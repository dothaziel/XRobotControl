package frc.robot;

import edu.wpi.first.wpilibj.DigitalOutput;

public class StateHandler {
    private static StateHandler instance;

    private boolean running = false;

    public StateHandler() {
    }

    public static StateHandler get() {
        if(StateHandler.instance == null) {
            StateHandler.instance = new StateHandler();
        }

        return StateHandler.instance;
    }

    public void setRunning(boolean state) {
        StateHandler.get().running = state;
    }
    public boolean isRunning() {
        return StateHandler.get().running;
    }
}