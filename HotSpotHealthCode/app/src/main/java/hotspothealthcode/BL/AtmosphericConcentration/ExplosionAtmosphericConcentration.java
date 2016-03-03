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

    //region Effective Release Height

    private double calcEffectiveReleaseHeight()
    {
        return 76.0 * Math.pow(this.explosiveAmount, 0.25);
    }

    private double calcEffectiveReleaseHeightWithGreenField()
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

    //region Atmospheric Concentration

    @Override
    public OutputResult calcAtmosphericConcentration() {

        double effectiveReleaseHeight;

        if(this.isGreenField)
        {
            effectiveReleaseHeight = this.calcEffectiveReleaseHeightWithGreenField();
        }
        else
        {
            effectiveReleaseHeight = this.calcEffectiveReleaseHeight();
        }

        double windSpeed = this.calcWindSpeed(this.terrainType, effectiveReleaseHeight);

        OutputResult outputResult = this.getOutputResult(effectiveReleaseHeight, windSpeed);

        outputResult.addValue(ResultField.MODEL_TYPE, "General Explosion");

        return outputResult;
    }

    //endregion
}
