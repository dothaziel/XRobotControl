package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.gamepad.OI;
import frc.robot.subsystems.OMSBase;

public class TeleopOMS extends CommandBase {
    public OI oi = RobotContainer.oi;
    public OMSBase oms = RobotContainer.oms;

    public double InputX;
    public double InputY;

    public TeleopOMS() {
        addRequirements(oms);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        InputX = -oi.getRightDriveX();
        InputY = oi.getRightDriveY();

        oms.DriveOMS(InputX, InputY);

        double[] cartesian = Globals.getGlobals().getCartesian();

        SmartDashboard.putNumber("xCartesian", cartesian[0]);
        SmartDashboard.putNumber("yCartesian", cartesian[1]);
    }

    @Override
    public void end(boolean interrupted) {
        oms.SetSpeedZero();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}