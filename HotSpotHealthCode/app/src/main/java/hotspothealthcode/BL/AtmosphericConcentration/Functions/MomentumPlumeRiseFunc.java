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

        /*return t.divide(this.referanceHeight).pow(this.p).multiply(this.windSpeedAtReferenceHeight)
                .multiply(t.subtract(this.physicalStackHeight).pow(3.0))
                .subtract(3.375 * Math.pow(this.s, -0.5) * this.momentumFlux);*/

        return t.divide(this.referanceHeight).pow(this.p).multiply(this.windSpeedAtReferenceHeight)
                .multiply(t.subtract(this.physicalStackHeight).pow(3.0))
                .subtract(Math.pow(1.5 * Math.pow(this.s, -1.0 / 6.0), 3.0) * this.momentumFlux);
    }

    @Override
    public double value(double x) {
        /*return -1.0 + ((-0.5 * this.momentumFlux * this.p * Math.pow((x / this.referanceHeight), -p - 1.0)) /
               (Math.pow(this.s, 1.0 / 6.0) * this.windSpeedAtReferenceHeight * this.referanceHeight *
                Math.pow((this.momentumFlux * Math.pow(x / this.referanceHeight, -p)) / this.windSpeedAtReferenceHeight, 2.0 / 3.0)));*/

        /*return ((3.375 * this.momentumFlux) / (Math.sqrt(this.s) * Math.pow(x - this.physicalStackHeight, 3.0))) -
                this.windSpeedAtReferenceHeight * Math.pow(x / this.referanceHeight, this.p);*/

        return Math.pow(x / this.referanceHeight, this.p) *
                (Math.pow(x - this.physicalStackHeight, 3.0)) -
                (Math.pow(1.5 * Math.pow(this.s, -1.0 / 6.0), 3.0) * this.momentumFlux);
    }
}
