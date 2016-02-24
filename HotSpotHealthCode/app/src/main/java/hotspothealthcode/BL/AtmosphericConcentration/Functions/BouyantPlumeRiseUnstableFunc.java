package hotspothealthcode.BL.AtmosphericConcentration.Functions;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;

/**
 * Created by Giladl on 12/01/2016.
 */
public class BouyantPlumeRiseUnstableFunc implements UnivariateDifferentiableFunction
{
    private double buoyancyFlux;
    private double physicalStackHeight;
    private double windSpeedAtReferenceHeight;
    private double referanceHeight;
    private double p;
    private double xc;

    public BouyantPlumeRiseUnstableFunc(double buoyancyFlux,
                                        double physicalStackHeight,
                                        double windSpeedAtReferenceHeight,
                                        double referanceHeight,
                                        double p,
                                        double xc)
    {
        this.buoyancyFlux = buoyancyFlux;
        this.physicalStackHeight = physicalStackHeight;
        this.windSpeedAtReferenceHeight = windSpeedAtReferenceHeight;
        this.referanceHeight = referanceHeight;
        this.p = p;
        this.xc = xc;
    }

    @Override
    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        /*return t.divide(this.referanceHeight).pow(-this.p).multiply(-1.6 * Math.pow(this.buoyancyFlux, 1.0 / 3.0) * this.p *
        Math.pow(this.xc, 2.0 / 3.0)).divide(t.multiply(this.windSpeedAtReferenceHeight)).add(-1.0);*/

        return t.divide(this.referanceHeight).pow(this.p).multiply(this.windSpeedAtReferenceHeight)
                .multiply(t.subtract(this.physicalStackHeight))
                .subtract(1.6 * Math.pow(this.buoyancyFlux, 1.0 / 3.0) * Math.pow(this.xc, 2.0 / 3.0));
/*
        return t.subtract(this.physicalStackHeight).pow(-1.0)
                .multiply(1.6 * Math.pow(this.buoyancyFlux, 1.0 / 3.0) * Math.pow(this.xc, 2.0 / 3.0))
                .subtract(t.divide(this.referanceHeight).pow(this.p).multiply(this.windSpeedAtReferenceHeight));*/
    }

    @Override
    public double value(double x) {
        /*return ((-1.6 * Math.pow(this.buoyancyFlux, 1.0 / 3.0) * this.p * Math.pow(this.xc, 2.0 / 3.0) * Math.pow(x / this.referanceHeight, -this.p)) /
                (x * this.windSpeedAtReferenceHeight)) - 1.0;*/

        /*return ((1.6 * Math.pow(this.buoyancyFlux, 1.0 / 3.0) * Math.pow(this.xc, 2.0 / 3.0)) /
               (x - this.physicalStackHeight)) - (this.windSpeedAtReferenceHeight * (Math.pow(x / this.referanceHeight, this.p)));*/

        return Math.pow(x / this.referanceHeight, this.p) *
               (x - this.physicalStackHeight) -
               (1.6 * Math.pow(this.buoyancyFlux, 1.0 / 3.0) * Math.pow(this.xc, 2.0 / 3.0));


    }
}
