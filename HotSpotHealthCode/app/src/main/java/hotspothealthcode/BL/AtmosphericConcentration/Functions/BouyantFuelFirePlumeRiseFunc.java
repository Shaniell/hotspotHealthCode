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
    private double windSpeedAtReferenceHeight;
    private double referanceHeight;
    private double p;
    private double xc;

    public BouyantFuelFirePlumeRiseFunc(double buoyancyFlux,
                                        double windSpeedAtReferenceHeight,
                                        double referanceHeight,
                                        double p,
                                        double xc)
    {
        this.buoyancyFlux = buoyancyFlux;
        this.windSpeedAtReferenceHeight = windSpeedAtReferenceHeight;
        this.referanceHeight = referanceHeight;
        this.p = p;
        this.xc = xc;
    }

    @Override
    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        return t.divide(this.referanceHeight * 2).pow(this.p).multiply(this.windSpeedAtReferenceHeight)
                .multiply(t)
                .subtract(1.6 * Math.pow(this.buoyancyFlux, 1.0 / 3.0) * Math.pow(this.xc, 2.0 / 3.0));
    }

    @Override
    public double value(double x) {
        return Math.pow(x / (2 * this.referanceHeight), this.p) * this.windSpeedAtReferenceHeight *
                (x) -
                (1.6 * Math.pow(this.buoyancyFlux, 1.0 / 3.0) * Math.pow(this.xc, 2.0 / 3.0));
    }
}
