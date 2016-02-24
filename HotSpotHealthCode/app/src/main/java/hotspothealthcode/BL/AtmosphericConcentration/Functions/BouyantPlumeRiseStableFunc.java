package hotspothealthcode.BL.AtmosphericConcentration.Functions;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;

/**
 * Created by Giladl on 12/01/2016.
 */
public class BouyantPlumeRiseStableFunc implements UnivariateDifferentiableFunction
{
    private double buoyancyFlux;
    private double s;
    private double physicalStackHeight;
    private double windSpeedAtReferenceHeight;
    private double referanceHeight;
    private double p;

    public BouyantPlumeRiseStableFunc(double buoyancyFlux,
                                      double s,
                                      double physicalStackHeight,
                                      double windSpeedAtReferenceHeight,
                                      double referanceHeight,
                                      double p)
    {
        this.buoyancyFlux = buoyancyFlux;
        this.s = s;
        this.physicalStackHeight = physicalStackHeight;
        this.windSpeedAtReferenceHeight = windSpeedAtReferenceHeight;
        this.referanceHeight = referanceHeight;
        this.p = p;
    }

    @Override
    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        return t.subtract(this.physicalStackHeight).pow(3.0).multiply(this.s).pow(-1.0)
                .multiply(17.576 * this.buoyancyFlux)
                .subtract(t.divide(this.referanceHeight).pow(p).multiply(this.windSpeedAtReferenceHeight));
    }

    @Override
    public double value(double x) {
        return ((17.576 * this.buoyancyFlux) / (this.s * Math.pow(x - this.physicalStackHeight, 3.0))) -
                this.windSpeedAtReferenceHeight * Math.pow(x / this.referanceHeight, this.p);
    }
}
