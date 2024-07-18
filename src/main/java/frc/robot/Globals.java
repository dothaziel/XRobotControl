package frc.robot;

public class Globals {

    private static Globals instance;

    private double xCartesian;
    private double yCartesian;

    public Globals() {

    }

    public static Globals getGlobals() {
        if(Globals.instance == null) {
            Globals.instance = new Globals();
        }

        return Globals.instance;
    }

    public double[] getCartesian() {
        return new double[] { getGlobals().xCartesian, getGlobals().yCartesian };
    }

    public void setXCartesian(double value) {
        getGlobals().xCartesian = value;
    }

    public void setYCartesian(double value) {
        getGlobals().yCartesian = value;
    }
}