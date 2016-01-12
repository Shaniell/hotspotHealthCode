package hotspothealthcode.BL.AtmosphericConcentration.Functions;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;

/**
 * Created by Giladl on 12/01/2016.
 */
public class MomentumPlumeRiseFunc implements UnivariateDifferentiableFunction
{
    private double momentumFlux;
    private double s;
    private double physicalStackHeight;
    private double windSpeedAtReferenceHeight;
    private double referanceHeight;
    private double p;

    public MomentumPlumeRiseFunc(double momentumFlux,
                                 double s,
                                 double physicalStackHeight,
                                 double windSpeedAtReferenceHeight,
                                 double referanceHeight,
                                 double p)
    {
        this.momentumFlux = momentumFlux;
        this.s = s;
        this.physicalStackHeight = physicalStackHeight;
        this.windSpeedAtReferenceHeight = windSpeedAtReferenceHeight;
        this.referanceHeight = referanceHeight;
        this.p = p;
    }

    @Override
    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        return t.subtract(this.physicalStackHeight).pow(3).multiply(Math.sqrt(this.s)).pow(-1)
                .multiply(3.375 * this.momentumFlux)
                .subtract(t.divide(this.referanceHeight).pow(p).multiply(this.windSpeedAtReferenceHeight));
    }

    @Override
    public double value(double x) {
        return ((3.375 * this.momentumFlux) / (Math.sqrt(this.s) * Math.pow(x - this.physicalStackHeight, 3))) -
                this.windSpeedAtReferenceHeight * Math.pow(x / this.referanceHeight, this.p);
    }
}
