package frc.robot.subsystems;

import com.studica.frc.TitanQuad;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveBase extends SubsystemBase {
    private TitanQuad FR_MOTOR;
    private TitanQuad FL_MOTOR;
    private TitanQuad RR_MOTOR;
    private TitanQuad RL_MOTOR;

    public DriveBase() {
        FR_MOTOR = new TitanQuad(Constants.TITAN_ID, Constants.FR_MOTOR);
        FL_MOTOR = new TitanQuad(Constants.TITAN_ID, Constants.FL_MOTOR);
        RR_MOTOR = new TitanQuad(Constants.TITAN_ID, Constants.RR_MOTOR);
        RL_MOTOR = new TitanQuad(Constants.TITAN_ID, Constants.RL_MOTOR);

        FL_MOTOR.setInverted(true);
        RL_MOTOR.setInverted(true);
    }


    public static double[] calculateMotorSpeeds(double x, double y, double rotation) {
        double frontLeft = x + y + rotation;
        double frontRight = -x + y - rotation;
        double backLeft = -x + y + rotation;
        double backRight = x + y - rotation;

        // Normalizar las velocidades para que ninguna exceda 1
        double maxMagnitude = Math.max(Math.max(Math.abs(frontLeft), Math.abs(frontRight)),
                                       Math.max(Math.abs(backLeft), Math.abs(backRight)));

        if (maxMagnitude > 1.0) {
            frontLeft /= maxMagnitude;
            frontRight /= maxMagnitude;
            backLeft /= maxMagnitude;
            backRight /= maxMagnitude;
        }

        return new double[]{frontLeft, frontRight, backLeft, backRight};
    }

    public void DriveHolonomic(double InputX, double InputY, double Rotation) {
        // double angle = Math.atan(InputY / InputX);
        // double speed_fl = InputY * Math.cos(angle);
        // double speed_rr = InputX * Math.sin(angle);

        // SmartDashboard.putNumber("Arctan", angle);
        // SmartDashboard.putNumber("Speed FL", speed_fl);
        // SmartDashboard.putNumber("Speed RR", speed_rr);

        // FL_MOTOR.set(speed_fl);
        // RR_MOTOR.set(speed_rr);

        double[] speeds = calculateMotorSpeeds(InputX, InputY, Rotation);

        FL_MOTOR.set(speeds[0]);
        FR_MOTOR.set(speeds[1]);
        RL_MOTOR.set(speeds[2]);
        RR_MOTOR.set(speeds[3]);
    } 

    public void moveForward(double speed) {
        FR_MOTOR.set(speed);
        FL_MOTOR.set(speed);
        RR_MOTOR.set(speed);
        RL_MOTOR.set(speed);
    }

    public void SetSpeedZero() {
        FR_MOTOR.set(0.0);
        FL_MOTOR.set(0.0);
    }
}