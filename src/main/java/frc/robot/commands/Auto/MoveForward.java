package frc.robot.commands.Auto;

import frc.robot.commands.driveCommand.SimpleDrive;

public class MoveForward extends AutoCommand {
    public MoveForward() {
        super(new SimpleDrive(0.0, -1.0, 0.0).withTimeout(5));
    }
}