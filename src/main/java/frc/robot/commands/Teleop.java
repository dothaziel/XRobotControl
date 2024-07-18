package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.StateHandler;
import frc.robot.gamepad.OI;
import frc.robot.subsystems.DriveBase;

public class Teleop extends CommandBase {
    public DriveBase driveBase = RobotContainer.driveBase;
    public OI oi = RobotContainer.oi;

    public double InputX;
    public double InputY;
    public double Rotation;

    public double BumperR;
    public double BumperL;

    public Teleop() {
        addRequirements(driveBase);
    }

    @Override
    public void initialize() {
        StateHandler.get().setRunning(true);
    }

    public double getRotation() {
        BumperR = oi.getRightBumpper();
        BumperL = -oi.getLeftBumpper();
        return BumperR + BumperL;
    }

    @Override
    public void execute() {
        InputX = oi.getLeftDriveX();
        InputY = oi.getLeftDriveY();
        Rotation = getRotation();

        driveBase.DriveHolonomic(-InputX, InputY, -Rotation);
    }

    @Override
    public void end(boolean interrupted) {
        driveBase.SetSpeedZero();
        StateHandler.get().setRunning(false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}