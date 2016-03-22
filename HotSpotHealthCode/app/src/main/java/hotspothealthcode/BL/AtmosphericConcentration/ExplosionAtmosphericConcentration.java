package hotspothealthcode.BL.AtmosphericConcentration;

import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.ResultField;

/**
 * Created by Giladl on 29/02/2016.
 */
public class ExplosionAtmosphericConcentration extends AtmosphericConcentration {

    //region Data Members

    private static double POUNDS = 2.20462262185;

    private double explosiveAmount;
    private boolean isGreenField;

    //endregion

    //region setters

    public void setExplosiveAmount(double explosiveAmount) {
        this.explosiveAmount = explosiveAmount * ExplosionAtmosphericConcentration.POUNDS;
    }

    public void setIsGreenField(boolean isGreenField) {
        this.isGreenField = isGreenField;
    }

    //endregion

    //region Cloud Top

    private double calcCloudTop()
    {
        double cloudTop;

        if(this.isGreenField)
        {
            cloudTop = this.calcCloudTopWithGreenField();
        }
        else
        {
            cloudTop = this.calcCloudTopWithoutGreenField();
        }

        return cloudTop;
    }

    private double calcCloudTopWithoutGreenField()
    {
        return 76.0 * Math.pow(this.explosiveAmount, 0.25);
    }

    private double calcCloudTopWithGreenField()
    {
        double effectiveReleaseHeight;

        if (this.pasquillStability.getStabilityType() == PasquillStabilityType.TYPE_A ||
            this.pasquillStability.getStabilityType() == PasquillStabilityType.TYPE_B ||
            this.pasquillStability.getStabilityType() == PasquillStabilityType.TYPE_C) {

            effectiveReleaseHeight = 27.4 * Math.pow(this.explosiveAmount, 0.48);

        }
        else
        {
            effectiveReleaseHeight = 23.3 * Math.pow(this.explosiveAmount, 0.44);
        }

        return effectiveReleaseHeight;
    }

    //endregion

    //region Effective Release Height

    private double calcEffectiveReleaseHeight()
    {
        return this.calcCloudTop() * 0.8;
    }

    //endregion

    //region Deviation Calculation

    @Override
    protected double calcDy()
    {
        double currentDy = 999999;
        double prevDy;
        double sigmaY = (0.2 * this.calcCloudTop()) / 2.0;

        prevDy = this.calcVirtualSourceDistanceForSigmaY(this.terrainType, 0, sigmaY);

        // While the current dy is bigger then 10 % of the prev dy
        while (currentDy - prevDy > (0.1 * prevDy))
        {
            currentDy = this.calcVirtualSourceDistanceForSigmaY(this.terrainType, prevDy, sigmaY);

            prevDy = currentDy;
        }

        return currentDy;
    }

    @Override
    protected double calcDz()
    {
        double currentDz = 999999;
        double prevDz;
        double sigmaZ = 0.2 * this.calcCloudTop();

        prevDz = this.calcVirtualSourceDistanceForSigmaZ(this.terrainType, 0, sigmaZ);

        // While the current dy is bigger then 10 % of the prev dy
        while (currentDz - prevDz > (0.1 * prevDz))
        {
            currentDz = this.calcVirtualSourceDistanceForSigmaY(this.terrainType, prevDz, sigmaZ);

            prevDz = currentDz;
        }

        return currentDz;
    }

    //endregion

    //region Atmospheric Concentration

    @Override
    public OutputResult calcAtmosphericConcentration() {

        double effectiveReleaseHeight = this.calcEffectiveReleaseHeight();

        double windSpeed = this.calcWindSpeed(this.terrainType, effectiveReleaseHeight);

        OutputResult outputResult = this.getOutputResult(effectiveReleaseHeight, windSpeed);

        outputResult.addValue(ResultField.MODEL_TYPE, "General Explosion");

        return outputResult;
    }

    //endregion
}
