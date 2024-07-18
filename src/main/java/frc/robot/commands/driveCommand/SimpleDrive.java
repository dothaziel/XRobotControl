package frc.robot.commands.driveCommand;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.StateHandler;
import frc.robot.subsystems.DriveBase;

public class SimpleDrive extends CommandBase {
    private static final DriveBase driveBase = RobotContainer.driveBase;

    private double x;
    private double y;
    private double z;

    public SimpleDrive(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

        addRequirements(driveBase);
    }

    @Override
    public void initialize() {
        StateHandler.get().setRunning(true);
    }

    @Override
    public void execute() {
        driveBase.DriveHolonomic(this.x, this.y, this.z);
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