package frc.robot;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.Teleop;
import frc.robot.commands.TeleopOMS;
import frc.robot.commands.Auto.AutoCommand;
import frc.robot.commands.Auto.MoveForward;
import frc.robot.gamepad.OI;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Indicators;
import frc.robot.subsystems.OMSBase;

public class RobotContainer {
  public static DriveBase driveBase;
  public static OI oi;
  public static OMSBase oms;
  // private final DepthCamera camera;

  public static SendableChooser<String> autoChooser;
  public static Map<String, AutoCommand> autoMode = new HashMap<>();

  public RobotContainer() {
    driveBase = new DriveBase();
    oi = new OI();
    oms = new OMSBase();

    driveBase.setDefaultCommand(new Teleop());
    oms.setDefaultCommand(new TeleopOMS());

    // camera = new DepthCamera();
  }

  public Command getAutonomousCommand() {
    String mode = autoChooser.getSelected();
    SmartDashboard.putString("Chosen Auto Mode", mode);
    return autoMode.getOrDefault(mode, new MoveForward());
  }
}
