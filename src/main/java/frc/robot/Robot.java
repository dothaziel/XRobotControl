package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.Auto.MoveForward;
import frc.robot.subsystems.Indicators;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.cameraserver.CameraServer;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.util.ArrayList;
import java.util.List;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private Globals globals = Globals.getGlobals();
  private Indicators indicators = new Indicators();

  private RobotContainer m_robotContainer;

  private int hMin = 100;
  private int hMax = 140;
  private int sMin = 150;
  private int sMax = 255;
  private int vMin = 0;
  private int vMax = 255;
  private int minArea = 700;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(640, 480);

    CvSink cvSink = CameraServer.getInstance().getVideo();
    CvSource outputStream = CameraServer.getInstance().putVideo("USB Camera", 640, 480);
    CvSource outputStreamMask = CameraServer.getInstance().putVideo("Mask", 640, 480);

    Mat frame = new Mat();
    Mat hsv = new Mat();
    Mat mask = new Mat();
    Mat result = new Mat();
    Mat hierarchy = new Mat();

    indicators.handleLights();

    new Thread(() -> {
      while (!Thread.interrupted()) {
        if (cvSink.grabFrame(frame) == 0) {
          outputStream.notifyError(cvSink.getError());
          continue;
        }
        // Procesamiento de la imagen
        Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);
        Scalar lower = new Scalar(hMin, sMin, vMin);
        Scalar upper = new Scalar(hMax, sMax, vMax);

        Core.inRange(hsv, lower, upper, mask);

        Core.bitwise_and(frame, frame, result, mask);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        List<MatOfPoint> filteredContours = new ArrayList<>();
        for (MatOfPoint contour : contours) {
          double area = Imgproc.contourArea(contour);
          if (area > minArea) {
            filteredContours.add(contour);
          }
        }

        if (!filteredContours.isEmpty()) {
          MatOfPoint contour = filteredContours.get(0); // Primer contorno detectado
          double area = Imgproc.contourArea(contour);

          if (area > minArea) {
              Rect rect = Imgproc.boundingRect(contour);
              Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 255, 0));

              // Calcular el centro del contorno
              Point center = new Point(rect.x + rect.width / 2.0, rect.y + rect.height / 2.0);

              // Convertir a coordenadas cartesianas
              double xCartesian = 2 * (center.x / frame.width()) - 1;
              double yCartesian = -2 * (center.y / frame.height()) + 1; // Corregido para invertir Y

              globals.setXCartesian(xCartesian);
              globals.setYCartesian(yCartesian);
          }
      }

        /*
         * for (MatOfPoint contour : contours) { double area =
         * Imgproc.contourArea(contour); if (area > minArea) { Rect rect =
         * Imgproc.boundingRect(contour); Imgproc.rectangle(frame, rect.tl(), rect.br(),
         * new Scalar(0, 255, 0), 2); } }
         */
        outputStream.putFrame(frame);
        outputStreamMask.putFrame(mask);
      }
    }).start();
  }

  @Override
  public void robotPeriodic() {
    indicators.handleLights();
    CommandScheduler.getInstance().run();
    SmartDashboard.putBoolean("Running", StateHandler.get().isRunning());
  }

  @Override
  public void disabledInit() {
    indicators.handleLights();
    if (null == RobotContainer.autoChooser) {
      RobotContainer.autoChooser = new SendableChooser<>();
    }

    RobotContainer.autoChooser.setDefaultOption("Drive Forward", "Drive Forward");
    RobotContainer.autoMode.put("Drive Forward", new MoveForward());
    SmartDashboard.putData(RobotContainer.autoChooser);
  }

  @Override
  public void disabledPeriodic() {
    indicators.handleLights();
  }

  @Override
  public void autonomousInit() {
    indicators.handleLights();
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    indicators.handleLights();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
