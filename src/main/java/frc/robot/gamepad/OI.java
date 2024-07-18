package frc.robot.gamepad;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
    Joystick drivePad;

    public OI() {
        drivePad = new Joystick(GamepadConstants.USB_PORT);
    }

    public double getLeftDriveY() {
        double joy = drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_Y);
        if (Math.abs(joy) < 0.05)
            return 0.0;
        else
            return joy;
    }
    public double getRightDriveX() {
        double joy = drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_X);
        if (Math.abs(joy) < 0.1)
            return 0.0;
        else
            return joy;
    }
    public double getRightDriveY() {
        double joy = drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_Y);
        if (Math.abs(joy) < 0.1)
            return 0.0;
        else
            return joy;
    }

    /**
     * @return the x-axis value from the drivePad left Joystick
     */
    public double getLeftDriveX() {
        double joy = drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_X);
        if (Math.abs(joy) < 0.05)
            return 0.0;
        else
            return joy;
    }

    public double getRightBumpper() {
        boolean b = drivePad.getRawButton(GamepadConstants.RIGHT_TRIGGER);
        if(b) {
            return 1;
        } else {
            return 0.0;
        }
    }

    public double getLeftBumpper() {
        boolean b = drivePad.getRawButton(GamepadConstants.LEFT_TRIGGER);
        if(b) {
            return 1;
        } else {
            return 0.0;
        }
    }
}