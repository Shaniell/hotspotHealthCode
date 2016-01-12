package hotspothealthcode.BL.AtmosphericConcentration.Functions;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;

/**
 * Created by Giladl on 12/01/2016.
 */
public class WindSpeedFunc implements UnivariateDifferentiableFunction {
    private double windSpeedAtReferenceHeight;
    private double referanceHeight;
    private double p;

    public WindSpeedFunc(double windSpeedAtReferenceHeight,
                         double referanceHeight,
                         double p)
    {
        this.windSpeedAtReferenceHeight = windSpeedAtReferenceHeight;
        this.referanceHeight = referanceHeight;
        this.p = p;
    }

    @Override
    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        return t.divide(this.referanceHeight).pow(p).multiply(this.windSpeedAtReferenceHeight);
    }

    @Override
    public double value(double x) {
        return this.windSpeedAtReferenceHeight * Math.pow(x / this.referanceHeight, this.p);
    }
}
