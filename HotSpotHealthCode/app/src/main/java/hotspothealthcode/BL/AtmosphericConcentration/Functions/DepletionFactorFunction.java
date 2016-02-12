package hotspothealthcode.BL.AtmosphericConcentration.Functions;

import org.apache.commons.math3.analysis.UnivariateFunction;

/**
 * Created by Giladl on 12/02/2016.
 */
public class DepletionFactorFunction implements UnivariateFunction
{
    private double effectiveHeight = 10;

    @Override
    public double value(double x) {

        double val = 1 /
                     ((0.2 * x) *
                             Math.exp(0.5 * Math.pow(this.effectiveHeight / (0.2 * x), 2)));

        return val;
    }

    protected double calcSigmaZ(double x){
        return 0.2 * x;
    }
}
