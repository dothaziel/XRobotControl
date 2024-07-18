package frc.robot.subsystems;

import com.studica.frc.ServoContinuous;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class OMSBase extends SubsystemBase {

    private ServoContinuous ServoX;
    private ServoContinuous ServoY;

    public OMSBase() {
        ServoX = new ServoContinuous(Constants.SERVO_X);
        ServoY = new ServoContinuous(Constants.SERVO_Y);
    }

    public void DriveOMS(double x, double y) {
        ServoX.set(x);
        ServoY.set(y);
    }

    public void SetSpeedZero() {
        ServoX.set(0.0);
        ServoY.set(0.0);
    }
}