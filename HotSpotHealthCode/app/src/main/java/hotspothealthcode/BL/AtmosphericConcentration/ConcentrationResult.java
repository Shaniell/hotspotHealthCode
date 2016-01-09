package hotspothealthcode.BL.AtmosphericConcentration;

import hotspothealthcode.BL.AtmosphericConcentration.ConcentrationPoint;

/**
 * Created by Giladl on 09/01/2016.
 */
public class ConcentrationResult
{
    private double concentration;
    private double arrivalTime; // HH:MM
    private ConcentrationPoint point;

    public ConcentrationResult(ConcentrationPoint point,
                               double concentration,
                               double arrivalTime)
    {

        this.point = point;
        this.concentration = concentration;
        this.arrivalTime = arrivalTime;
    }

    public ConcentrationPoint getPoint() {
        return point;
    }

    public double getConcentration() {
        return concentration;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }
}
