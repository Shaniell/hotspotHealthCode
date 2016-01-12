package hotspothealthcode.BL.AtmosphericConcentration.Functions;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;

/**
 * Created by Giladl on 12/01/2016.
 */
public class BouyantFuelFirePlumeRiseFunc implements UnivariateDifferentiableFunction
{
    private double buoyancyFlux;
    private double distance;
    private double windSpeedAtReferenceHeight;
    private double referanceHeight;
    private double p;

    public BouyantFuelFirePlumeRiseFunc(double buoyancyFlux,
                                        double distance,
                                        double windSpeedAtReferenceHeight,
                                        double referanceHeight,
                                        double p)
    {
        this.buoyancyFlux = buoyancyFlux;
        this.distance = distance;
        this.windSpeedAtReferenceHeight = windSpeedAtReferenceHeight;
        this.referanceHeight = referanceHeight;
        this.p = p;
    }

    @Override
    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        return t.multiply(this.windSpeedAtReferenceHeight).multiply(t.divide(2 * this.referanceHeight).pow(this.p))
                .subtract(1.6 * Math.pow(this.buoyancyFlux, 1/3) * Math.pow(this.distance, 2/3));
    }

    @Override
    public double value(double x) {
        return x * this.windSpeedAtReferenceHeight * Math.pow(x / (2 * this.referanceHeight), this.p) -
                (1.6 * Math.pow(this.buoyancyFlux, 1/3) * Math.pow(this.distance, 2/3));
    }
}
