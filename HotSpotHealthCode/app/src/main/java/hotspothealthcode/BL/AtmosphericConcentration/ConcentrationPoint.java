package hotspothealthcode.BL.AtmosphericConcentration;

/**
 * Created by Giladl on 09/01/2016.
 */
public class ConcentrationPoint
{
    private double x;
    private double y;
    private double z;

    public ConcentrationPoint(double x, double y, double z) {
        this.z = z;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
